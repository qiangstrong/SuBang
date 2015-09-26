package com.subang.controller.weixin;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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

import weixin.popular.api.PayMchAPI;
import weixin.popular.bean.paymch.MchBaseResult;
import weixin.popular.bean.paymch.MchNotifyResult;
import weixin.popular.bean.paymch.MchPayNotify;
import weixin.popular.bean.paymch.Unifiedorder;
import weixin.popular.bean.paymch.UnifiedorderResult;
import weixin.popular.util.ExpireSet;
import weixin.popular.util.JsonUtil;
import weixin.popular.util.MapUtil;
import weixin.popular.util.PayUtil;
import weixin.popular.util.SignatureUtil;
import weixin.popular.util.StreamUtils;
import weixin.popular.util.XMLConverUtil;

import com.subang.bean.AddrDetail;
import com.subang.controller.BaseController;
import com.subang.domain.History;
import com.subang.domain.Order;
import com.subang.domain.User;
import com.subang.domain.Worker;
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
	public ModelAndView index(HttpSession session, @RequestParam("type") int stateType) {
		ModelAndView view = new ModelAndView();
		List<Order> orders = orderService.searchOrderByUseridAndState(getUser(session).getId(),
				stateType);
		view.addObject("orders", orders);
		view.setViewName(INDEX_PAGE);
		return view;
	}

	@RequestMapping("/detail")
	public ModelAndView detail(@RequestParam("orderid") Integer orderid) {
		ModelAndView view = new ModelAndView();
		Order order = orderDao.get(orderid);
		List<History> historys = historyDao.findByOrderid(orderid);
		AddrDetail addrDetail = addrDao.getDetail(order.getAddrid());
		view.addObject("order", order);
		view.addObject("historys", historys);
		view.addObject("addrDetail", addrDetail);
		view.setViewName(VIEW_PREFIX + "/detail");
		return view;
	}

	@RequestMapping("/showadd")
	public ModelAndView showAdd(HttpSession session) {
		ModelAndView view = new ModelAndView();
		User user = getUser(session);
		prepare(view, user);
		view.setViewName(VIEW_PREFIX + "/add");
		return view;
	}

	@RequestMapping("/select")
	public void getTime(@RequestParam("date") Date date, HttpServletResponse response)
			throws Exception {
		response.setContentType("application/json;charset=UTF-8");
		OutputStream outputStream = response.getOutputStream();
		List<Option> times = TimeUtil.getTimeOptions(date);
		String json = JsonUtil.toJSONString(times);
		SuUtil.outputStreamWrite(outputStream, json);
		return;
	}

	@RequestMapping("/add")
	public ModelAndView add(HttpSession session, @Valid Order order, BindingResult result) {
		ModelAndView view = new ModelAndView();
		User user = getUser(session);
		if (result.hasErrors()) {
			prepare(view, user);
			view.setViewName(VIEW_PREFIX + "/add");
			return view;
		}
		order.setUserid(user.getId());
		orderService.addOrder(order);
		Worker worker = workerDao.get(order.getWorkerid());
		view.addObject("order", order);
		view.addObject("worker", worker);
		view.setViewName(VIEW_PREFIX + "/result");
		return view;
	}

	@RequestMapping("/fetch")
	public ModelAndView fetch(@RequestParam("orderid") Integer orderid) {
		ModelAndView view = new ModelAndView();
		orderService.fetchOrder(orderid);
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

	@RequestMapping("/prepay")
	public ModelAndView prepay(HttpServletRequest request, @RequestParam("orderid") Integer orderid) {
		ModelAndView view = new ModelAndView();
		HttpSession session = request.getSession();
		User user = getUser(session);
		Order order = orderDao.get(orderid);
		String prepay_id = orderService.getPrepay_id(user, order, request);
		if (prepay_id == null) {
			view.addObject(KEY_INFO_MSG, "支付失败。可能是您的余额不足");
		} else {
			String json = PayUtil.generateMchPayJsRequestJson(prepay_id,
					SuUtil.getAppProperty("appid"), SuUtil.getAppProperty("apikey"));
			view.addObject("json", json);
			view.addObject("order", order);
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
	private void prepare(ModelAndView view, User user) {

		view.addObject("defaultAddrid", user.getAddrid());
		List<AddrDetail> addrDetails = addrDao.findDetailByUserid(user.getId());
		view.addObject("addrDetails", addrDetails);

		List<Option> dates = TimeUtil.getDateOptions();
		view.addObject("dates", dates);

		List<Option> times = TimeUtil.getTimeOptions(dates.get(0).getValue());
		view.addObject("times", times);
	}

}
