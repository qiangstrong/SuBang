package com.subang.controller.weixin;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import weixin.popular.bean.paymch.MchNotifyResult;
import weixin.popular.bean.paymch.MchPayNotify;
import weixin.popular.util.ExpireSet;
import weixin.popular.util.MapUtil;
import weixin.popular.util.SignatureUtil;
import weixin.popular.util.StreamUtils;
import weixin.popular.util.XMLConverUtil;

import com.subang.bean.PayArg;
import com.subang.bean.PrepayResult;
import com.subang.controller.BaseController;
import com.subang.domain.Balance;
import com.subang.domain.Order;
import com.subang.domain.User;
import com.subang.util.SuUtil;
import com.subang.util.WebConst;

@Controller("balanceController_weixin")
@RequestMapping("/weixin/balance")
public class BalanceController extends BaseController {

	private static ExpireSet<String> expireSet = new ExpireSet<String>(
			WebConst.EXPIRED_PAY_INTERVAL);

	private static final String VIEW_PREFIX = WebConst.WEIXIN_PREFIX + "/balance";
	private static final String INDEX_PAGE = VIEW_PREFIX + "/index";

	@RequestMapping("/index")
	public ModelAndView index(HttpSession session) {
		ModelAndView view = new ModelAndView();
		User user = getUser(session);
		view.addObject("user", user);
		List<Balance> balances = balanceDao.findDetailByUseridAndState(user.getId(),
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
		view.setViewName(VIEW_PREFIX + "/parapay");
		return view;
	}

	@RequestMapping("/prepay")
	public ModelAndView prepay(HttpServletRequest request, @Valid PayArg payArg,
			BindingResult result) {
		ModelAndView view = new ModelAndView();
		User user = getUser(request.getSession());
		if (result.hasErrors()) {
			view.addObject("user", user);
			view.addObject("payArg", payArg);
			view.setViewName(VIEW_PREFIX + "/parapay");
			return view;
		}
		PrepayResult prepayResult = userService.prepay(payArg, user.getId(), request);

		view.addObject("user", user);
		if (prepayResult.getCode() == PrepayResult.Code.fail) {
			view.addObject(KEY_INFO_MSG, "支付失败。" + prepayResult.getMsg());
			view.setViewName(VIEW_PREFIX + "/payresult");
		} else {
			switch (payArg.getPayTypeEnum()) {
			case weixin: {
				view.addObject("json", prepayResult.getArg());
				view.setViewName(VIEW_PREFIX + "/prepay");
				break;
			}
			}
		}
		return view;
	}

	@RequestMapping("/pay")
	public void pay(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 获取请求数据
		String xml = StreamUtils.copyToString(request.getInputStream(), Charset.forName("utf-8"));
		Map<String, String> map = MapUtil.xmlToMap(xml);

		// 已处理 去重
		if (expireSet.contains(map.get("transaction_id"))) {
			return;
		}

		// 签名验证
		String sign = SignatureUtil.generateSign(map, SuUtil.getAppProperty("apikey"));
		if (!sign.equals(map.get("sign"))) {
			MchNotifyResult notifyResult = new MchNotifyResult();
			notifyResult.setReturn_code("FAIL");
			notifyResult.setReturn_msg("ERROR");
			response.getOutputStream().write(XMLConverUtil.convertToXML(notifyResult).getBytes());
		} else {
			// 对象转换
			MchPayNotify payNotify = XMLConverUtil.convertToObject(MchPayNotify.class, xml);
			expireSet.add(payNotify.getTransaction_id());
			MchNotifyResult notifyResult = new MchNotifyResult();
			notifyResult.setReturn_code("SUCCESS");
			notifyResult.setReturn_msg("OK");
			response.getOutputStream().write(XMLConverUtil.convertToXML(notifyResult).getBytes());

			userService.payBalance(payNotify.getOut_trade_no());
		}
	}
}
