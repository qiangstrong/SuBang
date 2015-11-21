package com.subang.controller.weixin;

import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.Date;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import weixin.popular.bean.paymch.MchNotifyResult;
import weixin.popular.bean.paymch.MchPayNotify;
import weixin.popular.util.ExpireSet;
import weixin.popular.util.MapUtil;
import weixin.popular.util.PayUtil;
import weixin.popular.util.SignatureUtil;
import weixin.popular.util.StreamUtils;
import weixin.popular.util.XMLConverUtil;

import com.subang.bean.AddrDetail;
import com.subang.bean.OrderDetail;
import com.subang.controller.BaseController;
import com.subang.domain.Category;
import com.subang.domain.Clothes;
import com.subang.domain.History;
import com.subang.domain.Order;
import com.subang.domain.User;
import com.subang.exception.SuException;
import com.subang.util.SuUtil;
import com.subang.util.TimeUtil;
import com.subang.util.TimeUtil.Option;
import com.subang.util.WebConst;

@Controller("orderController_weixin")
@RequestMapping("/weixin/order")
public class OrderController extends BaseController {

	// 重复通知过滤 时效60秒
	private static ExpireSet<String> expireSet = new ExpireSet<String>(60);

	private static final String VIEW_PREFIX = WebConst.WEIXIN_PREFIX + "/order";
	private static final String INDEX_PAGE = VIEW_PREFIX + "/index";

	@RequestMapping("/index")
	public ModelAndView index(HttpSession session, @RequestParam("type") int type) {
		ModelAndView view = new ModelAndView();
		List<OrderDetail> orderDetails = orderService.searchOrderByUseridAndState(getUser(session)
				.getId(), type);
		view.addObject("orderDetails", orderDetails);
		view.addObject("type", type);
		view.setViewName(INDEX_PAGE);
		return view;
	}

	@RequestMapping("/detail")
	public ModelAndView getDetail(@RequestParam("orderid") Integer orderid) {
		ModelAndView view = new ModelAndView();
		OrderDetail orderDetail = orderDao.getDetail(orderid);
		List<History> historys = historyDao.findByOrderid(orderid);
		List<Clothes> clothess = clothesDao.findByOrderid(orderid);

		view.addObject("orderDetail", orderDetail);
		view.addObject("historys", historys);
		view.addObject("clothess", clothess);
		view.setViewName(VIEW_PREFIX + "/detail");
		return view;
	}

	@RequestMapping("/showadd")
	public ModelAndView showAdd(HttpSession session,
			@RequestParam("categoryid") Integer categoryid,
			@RequestParam(value = "addrid", required = false) Integer addrid) {
		ModelAndView view = new ModelAndView();
		User user = getUser(session);
		Order order = new Order();
		order.setCategoryid(categoryid);
		prepare(view, user, categoryid, addrid);
		view.addObject("order", order);
		view.setViewName(VIEW_PREFIX + "/add");
		return view;
	}

	@RequestMapping("/addr")
	public ModelAndView listAddr(HttpSession session, @RequestParam("categoryid") Integer categoryid) {
		ModelAndView view = new ModelAndView();
		List<AddrDetail> addrDetails = addrDao.findDetailByUserid(getUser(session).getId());
		view.addObject("categoryid", categoryid);
		view.addObject("addrDetails", addrDetails);
		view.setViewName(VIEW_PREFIX + "/addr");
		return view;
	}

	@RequestMapping("/select")
	public void getTime(@RequestParam("date") Date date, HttpServletResponse response)
			throws Exception {
		List<Option> times = TimeUtil.getTimeOptions(date);
		SuUtil.outputJson(response, times);
		return;
	}

	@RequestMapping("/add")
	public ModelAndView add(HttpSession session, @Valid Order order, BindingResult result) {
		ModelAndView view = new ModelAndView();
		User user = getUser(session);
		if (result.hasErrors()) {
			prepare(view, user, order.getCategoryid(), order.getAddrid());
			view.setViewName(VIEW_PREFIX + "/add");
			return view;
		}
		order.setUserid(user.getId());
		orderService.addOrder(order);
		view.setViewName("redirect:" + INDEX_PAGE + ".html?type=" + WebConst.ORDER_STATE_UNDONE);
		return view;
	}

	@RequestMapping("/cancel")
	public ModelAndView cancel(@RequestParam("orderid") Integer orderid) {
		ModelAndView view = new ModelAndView();
		orderService.cancelOrder(orderid);
		view.setViewName("redirect:" + INDEX_PAGE + ".html?type=" + WebConst.ORDER_STATE_UNDONE);
		return view;
	}

	@RequestMapping("/deliver")
	public ModelAndView deliver(@RequestParam("orderid") Integer orderid) {
		ModelAndView view = new ModelAndView();
		try {
			orderService.deliverOrder(orderid);
		} catch (SuException e) {
			e.printStackTrace();
		}
		view.setViewName("redirect:" + INDEX_PAGE + ".html?type=" + WebConst.ORDER_STATE_UNDONE);
		return view;
	}

	@RequestMapping("/showremark")
	public ModelAndView showRemark(@RequestParam("orderid") Integer orderid) {
		ModelAndView view = new ModelAndView();
		view.addObject("orderid", orderid);
		view.setViewName(VIEW_PREFIX + "/remark");
		return view;
	}

	// 由客户端根据用户填写的信息生成字符串，并检验其长度
	@RequestMapping("/remark")
	public ModelAndView remark(@RequestParam("orderid") Integer orderid,
			@RequestParam("remark") String remark) {
		ModelAndView view = new ModelAndView();
		try {
			orderService.remarkOrder(orderid, remark);
		} catch (SuException e) {
			e.printStackTrace();
		}
		view.setViewName("redirect:" + INDEX_PAGE + ".html?type=" + WebConst.ORDER_STATE_DONE);
		return view;
	}

	@RequestMapping("/prepay")
	public ModelAndView prepay(HttpServletRequest request, @RequestParam("orderid") Integer orderid) {
		ModelAndView view = new ModelAndView();
		HttpSession session = request.getSession();
		User user = getUser(session);
		OrderDetail orderDetail = orderDao.getDetail(orderid);
		String prepay_id = orderService.getPrepay_id(user, orderDetail, request);
		if (prepay_id == null) {
			view.addObject(KEY_INFO_MSG, "支付失败。可能是您的余额不足");
		} else {
			String json = PayUtil.generateMchPayJsRequestJson(prepay_id,
					SuUtil.getAppProperty("appid"), SuUtil.getAppProperty("apikey"));
			view.addObject("json", json);
			view.addObject("orderDetail", orderDetail);
		}
		view.setViewName(VIEW_PREFIX + "/prepay");
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

			orderService.payOrder(payNotify.getOut_trade_no());
		}
	}

	/**
	 * 为展示层准备相应的数据 默认地址的添加删除逻辑保证：只要用户有地址，默认地址就不为空
	 */
	private void prepare(ModelAndView view, User user, Integer categoryid, Integer addrid) {

		if (addrid == null) {
			addrid = user.getAddrid();
		}
		if (addrid != null) {
			view.addObject("addrDetail", addrDao.getDetail(addrid));
		}

		List<Option> dates = TimeUtil.getDateOptions();
		view.addObject("dates", dates);

		List<Option> times = TimeUtil.getTimeOptions(dates.get(0).getValue());
		view.addObject("times", times);

		Category category = categoryDao.get(categoryid);
		view.addObject("category", category);
	}

}
