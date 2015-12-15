package com.subang.controller.weixin;

import java.nio.charset.Charset;
import java.sql.Date;
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
import com.subang.bean.AddrDetail;
import com.subang.bean.OrderDetail;
import com.subang.bean.PayArg;
import com.subang.bean.PayArg.Client;
import com.subang.bean.PrepayResult;
import com.subang.bean.TicketDetail;
import com.subang.controller.BaseController;
import com.subang.domain.Category;
import com.subang.domain.Clothes;
import com.subang.domain.History;
import com.subang.domain.Order;
import com.subang.domain.User;
import com.subang.tool.SuException;
import com.subang.util.PayUtil;
import com.subang.util.SuUtil;
import com.subang.util.TimeUtil;
import com.subang.util.TimeUtil.Option;
import com.subang.util.WebConst;

@Controller("orderController_weixin")
@RequestMapping("/weixin/order")
public class OrderController extends BaseController {

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

	// 如果orderDetail.isTicket为false,用户可以选择一张优惠券；否则，用户不能选择。这由前端保证。
	@RequestMapping("parapay")
	public ModelAndView parapay(@RequestParam("orderid") Integer orderid,
			@RequestParam(value = "ticketid", required = false) Integer ticketid) {
		ModelAndView view = new ModelAndView();
		OrderDetail orderDetail = orderDao.getDetail(orderid);
		view.addObject("orderDetail", orderDetail);
		PayArg payArg = new PayArg();
		payArg.setOrderid(orderid);
		view.addObject("payArg", payArg);

		if (ticketid != null) {
			TicketDetail ticketDetail = ticketDao.getDetail(ticketid);
			view.addObject("ticketDetail", ticketDetail);
		}

		view.setViewName(VIEW_PREFIX + "/parapay");
		return view;
	}

	@RequestMapping("ticket")
	public ModelAndView listTicket(HttpSession session, @RequestParam("orderid") Integer orderid) {
		ModelAndView view = new ModelAndView();

		Order order = orderDao.get(orderid);
		List<TicketDetail> ticketDetails = ticketDao.findValidDetailByUseridAndCategoryid(
				getUser(session).getId(), order.getCategoryid());
		view.addObject("ticketDetails", ticketDetails);

		view.addObject("orderid", orderid);
		view.setViewName(VIEW_PREFIX + "/ticket");
		return view;
	}

	@RequestMapping("/prepay")
	public ModelAndView prepay(HttpServletRequest request, @Valid PayArg payArg,
			BindingResult result) {
		ModelAndView view = new ModelAndView();
		if (payArg.getOrderid() == null) {
			view.addObject(KEY_INFO_MSG, "订单id不能为空");
			view.setViewName(VIEW_PREFIX + "/parapay");
			return view;
		}
		if (result.hasErrors()) {
			OrderDetail orderDetail = orderDao.getDetail(payArg.getOrderid());
			view.addObject("orderDetail", orderDetail);
			view.addObject("payArg", payArg);
			if (payArg.getTicketid() != null) {
				TicketDetail ticketDetail = ticketDao.getDetail(payArg.getTicketid());
				view.addObject("ticketDetail", ticketDetail);
			}
			view.setViewName(VIEW_PREFIX + "/parapay");
			return view;
		}

		payArg.setClient(Client.weixin);
		PrepayResult prepayResult = orderService.prepay(payArg, request);

		OrderDetail orderDetail = orderDao.getDetail(payArg.getOrderid());
		view.addObject("orderDetail", orderDetail);
		if (prepayResult.getCodeEnum() == PrepayResult.Code.succ) {
			view.addObject(KEY_INFO_MSG, "支付成功。");
			view.setViewName(VIEW_PREFIX + "/payresult");
		} else if (prepayResult.getCodeEnum() == PrepayResult.Code.fail) {
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
	public void wxpay(HttpServletRequest request, HttpServletResponse response) throws Exception {
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
		orderService.payOrder(payNotify.getOut_trade_no());
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
			orderService.payOrder(map.get("out_trade_no"));
		}
		PayUtil.alipaySucc(response);
	}

	@RequestMapping("alireturn")
	public ModelAndView alireturn(HttpServletRequest request) {
		ModelAndView view = new ModelAndView();
		view.setViewName("redirect:" + INDEX_PAGE + ".html?type=" + WebConst.ORDER_STATE_UNDONE);
		return view;
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
