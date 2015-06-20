package com.subang.controller.weixin;

import java.io.OutputStream;
import java.sql.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import weixin.popular.util.JsonUtil;

import com.subang.bean.AddrDetail;
import com.subang.controller.BaseController;
import com.subang.domain.History;
import com.subang.domain.Order;
import com.subang.domain.User;
import com.subang.util.Common;
import com.subang.util.TimeUtil;
import com.subang.util.TimeUtil.Option;
import com.subang.util.WebConst;

@Controller("orderController_weixin")
@RequestMapping("/weixin/order")
public class OrderController extends BaseController {

	private static final String VIEW_PREFIX = WebConst.WEIXIN_PREFIX + "/order";
	private static final String INDEX_PAGE = VIEW_PREFIX + "/index";

	@RequestMapping("/index")
	public ModelAndView index(HttpSession session,@RequestParam("type") int stateType) {
		ModelAndView view = new ModelAndView();
		List<Order> orders = frontUserService.searchOrderByUseridAndState(getUser(session).getId(),stateType);
		view.addObject("orders", orders);
		view.setViewName(INDEX_PAGE);
		return view;
	}

	@RequestMapping("/detail")
	public ModelAndView detail(@RequestParam("orderid") Integer orderid) {
		ModelAndView view = new ModelAndView();
		Order order = frontUserService.getOrder(orderid);
		List<History> historys = frontUserService.listHistoryByOrderid(orderid);
		AddrDetail addrDetail = frontUserService.getAddrDetail(order.getAddrid());
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
		Common.outputStreamWrite(outputStream, json);
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
		frontUserService.addOrder(order);
		view.addObject("order", order);
		view.setViewName(VIEW_PREFIX + "/result");
		return view;
	}

	@RequestMapping("/fetch")
	public ModelAndView fetch(@RequestParam("orderid") Integer orderid) {
		ModelAndView view = new ModelAndView();
		frontUserService.fetchOrder(orderid);
		view.setViewName("redirect:" + INDEX_PAGE + ".html?type=" + WebConst.ORDER_STATE_UNDONE);
		return view;
	}

	@RequestMapping("/cancel")
	public ModelAndView cancel(@RequestParam("orderid") Integer orderid) {
		ModelAndView view = new ModelAndView();
		frontUserService.cancelOrder(orderid);
		view.setViewName("redirect:" + INDEX_PAGE + ".html?type=" + WebConst.ORDER_STATE_UNDONE);
		return view;
	}

	private void prepare(ModelAndView view, User user) {

		AddrDetail addrDetail = frontUserService.getAddrDetail(user.getAddrid());
		view.addObject("defaultAddr", addrDetail);
		List<AddrDetail> addrDetails = frontUserService.listAddrDetailByUserid(user.getId());
		view.addObject("addrDetails", addrDetails);

		List<Option> dates = TimeUtil.getDateOptions();
		view.addObject("dates", dates);

		List<Option> times = TimeUtil.getTimeOptions(dates.get(0).getValue());
		view.addObject("times", times);
	}
}
