package com.subang.controller.weixin;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import weixin.popular.bean.paymch.MchPayNotify;
import weixin.popular.util.MapUtil;
import weixin.popular.util.StreamUtils;
import weixin.popular.util.XMLConverUtil;

import com.alipay.util.SignUtil;
import com.subang.bean.PayArg;
import com.subang.bean.PrepayResult;
import com.subang.controller.BaseController;
import com.subang.domain.Balance;
import com.subang.domain.Order;
import com.subang.domain.Rebate;
import com.subang.domain.User;
import com.subang.domain.Payment.PayType;
import com.subang.util.PayUtil;
import com.subang.util.Setting;
import com.subang.util.SuUtil;
import com.subang.util.WebConst;

@Controller("balanceController_weixin")
@RequestMapping("/weixin/balance")
public class BalanceController extends BaseController {

	private static final String VIEW_PREFIX = WebConst.WEIXIN_PREFIX + "/balance";
	private static final String INDEX_PAGE = VIEW_PREFIX + "/index";

	@RequestMapping("/index")
	public ModelAndView index(HttpSession session) {
		ModelAndView view = new ModelAndView();
		User user = getUser(session);
		view.addObject("user", user);
		List<Balance> balances = balanceDao.findBalanceByUseridAndState(user.getId(),
				Order.State.paid);
		view.addObject("balances", balances);
		view.setViewName(INDEX_PAGE);
		return view;
	}

	@RequestMapping("parapay")
	public ModelAndView parapay(HttpSession session) {
		ModelAndView view = new ModelAndView();
		User user = getUser(session);
		view.addObject("user", user);
		PayArg payArg = new PayArg();
		view.addObject("payArg", payArg);
		List<Rebate> rebates = rebateDao.findAll();
		view.addObject("rebates", rebates);
		view.setViewName(VIEW_PREFIX + "/parapay");
		return view;
	}

	@RequestMapping("/prepay")
	public ModelAndView prepay(HttpServletRequest request, @Valid PayArg payArg,
			BindingResult result) {
		ModelAndView view = new ModelAndView();
		User user = getUser(request.getSession());
		if (result.hasErrors() || payArg.getMoney() == null || payArg.getMoney() == 0.0) {
			view.addObject("user", user);
			List<Rebate> rebates = rebateDao.findAll();
			view.addObject("rebates", rebates);
			if (payArg.getMoney() == null) {
				view.addObject(KEY_INFO_MSG, "充值金额不能为空");
			} else if (payArg.getMoney() == 0.0) {
				view.addObject(KEY_INFO_MSG, "充值金额不能小于0.1元");
			}
			view.setViewName(VIEW_PREFIX + "/parapay");
			return view;
		}

		payArg.setClient(User.Client.weixin);
		PrepayResult prepayResult = userService.prepay(payArg, user.getId(), request);

		view.addObject("user", user);
		if (prepayResult.getCodeEnum() == PrepayResult.Code.fail) {
			view.addObject(KEY_INFO_MSG, "支付失败。" + prepayResult.getMsg());
			view.setViewName(VIEW_PREFIX + "/payresult");
		} else {
			switch (payArg.getPayTypeEnum()) {
			case weixin: {
				view.addObject("json", prepayResult.getArg());
				view.setViewName(VIEW_PREFIX + "/wxprepay");
				break;
			}
			case alipay: {
				view.addObject("list", prepayResult.getArg());
				view.setViewName(VIEW_PREFIX + "/aliprepay");
				break;
			}
			}
		}
		return view;
	}

	@RequestMapping("/wxpay")
	public void pay(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/xml;charset=UTF-8");

		// 获取请求数据
		String xml = StreamUtils.copyToString(request.getInputStream(), Charset.forName("utf-8"));
		Map<String, String> map = MapUtil.xmlToMap(xml);

		if (!map.get("return_code").equals("SUCCESS")) {
			LOG.error("错误码:" + map.get("return_code") + "; 错误信息:" + map.get("return_msg"));
			PayUtil.wxpayError(response);
			return;
		}

		if (!map.get("result_code").equals("SUCCESS")) {
			LOG.error("错误码:" + map.get("err_code") + "; 错误信息:" + map.get("err_code_des"));
			PayUtil.wxpayError(response);
			return;
		}

		if (!PayUtil.wxVerify(map)) {
			LOG.error("错误码:" + WebConst.LOG_TAG + "; 错误信息:" + "签名校验失败。");
			PayUtil.wxpayError(response);
			return;
		}

		MchPayNotify payNotify = XMLConverUtil.convertToObject(MchPayNotify.class, xml);
		userService.payBalance(payNotify.getOut_trade_no());
		PayUtil.wxpaySucc(response);
	}

	@RequestMapping("/alipay")
	public void alipay(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/xml;charset=UTF-8");

		// 获取请求数据
		Map<String, String> map = com.alipay.util.MapUtil.genParamMap(request.getParameterMap());

		if (!SignUtil.verify(map)) {
			LOG.error("错误码:" + WebConst.LOG_TAG + "; 错误信息:" + "签名校验失败。");
			PayUtil.alipayError(response);
			return;
		}

		String trade_status = map.get("trade_status");
		if (trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")) {
			userService.payBalance(map.get("out_trade_no"));
		}
		PayUtil.alipaySucc(response);
	}

	@RequestMapping("alireturn")
	public ModelAndView alireturn(HttpServletRequest request) {
		ModelAndView view = new ModelAndView();
		view.setViewName("redirect:" + WebConst.WEIXIN_PREFIX + "/user/index.html");
		return view;
	}

	/*
	 * 用户下单分享后，由前端通通知后台，为用户增加余额。存在用户恶意请求这个地址，获取余额的风险。
	 * orderid是为了防止这种风险的参数，但目前没有使用这个参数
	 */
	@RequestMapping("share")
	public void share(HttpServletRequest request, @RequestParam("orderid") Integer orderid,
			HttpServletResponse response) {
		PayArg payArg = new PayArg();
		payArg.setPayType(PayType.share);
		payArg.setMoney(Setting.shareMoney);
		User user = getUser(request.getSession());
		userService.prepay(payArg, user.getId(), request);
		SuUtil.outputJsonOK(response);
	}

}
