package com.subang.controller.back;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.subang.bean.MsgArg;
import com.subang.bean.OrderDetail;
import com.subang.bean.PageArg;
import com.subang.bean.PageArg.ArgType;
import com.subang.bean.PageState;
import com.subang.bean.PayArg;
import com.subang.bean.PrepayResult;
import com.subang.bean.SearchArg;
import com.subang.controller.BaseController;
import com.subang.domain.Clothes;
import com.subang.domain.History;
import com.subang.domain.Laundry;
import com.subang.domain.Order;
import com.subang.domain.Payment.PayType;
import com.subang.domain.User;
import com.subang.domain.Worker;
import com.subang.tool.BackStack;
import com.subang.tool.SuException;
import com.subang.util.ComUtil;
import com.subang.util.SuUtil;
import com.subang.util.WebConst;

@Controller
@RequestMapping("/back/order")
public class OrderController extends BaseController {

	private static final String VIEW_PREFIX = WebConst.BACK_PREFIX + "/order";
	private static final String INDEX_PAGE = VIEW_PREFIX + "/index";
	private static final String KEY_DATA = "orderDetails";

	@RequestMapping("/index/back")
	public ModelAndView indexBack(HttpSession session) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		backStack.pop();
		view.setViewName("redirect:" + INDEX_PAGE + ".html");
		return view;
	}

	@RequestMapping("/index")
	public ModelAndView index(HttpSession session) {
		ModelAndView view = new ModelAndView();

		BackStack backStack = getBackStack(session);
		backStack.clear("order/index");

		PageArg pageArg = getPageArg(session);
		if (pageArg == null || pageArg.getArgType() != ArgType.para) {
			PageArg paraArg = new SearchArg(WebConst.SEARCH_NULL, null);
			backStack.pushOptional(new PageState("order/index", paraArg));
		}
		if (pageArg != null) {
			switch (pageArg.getArgType()) {
			case para:
				backStack.push(new PageState("order/index", pageArg));
				break;
			case msg:
				MsgArg msgArg = (MsgArg) pageArg;
				view.addObject(msgArg.getKey(), msgArg.getMsg());
				break;
			}
		}

		PageState pageState = backStack.peek();
		view.addObject(KEY_PAGE_STATE, pageState);
		view.addObject(KEY_DES_MSG, getDesMsg(pageState.getSearchArg()));

		List<OrderDetail> orderDetails = orderService.searchOrder(pageState.getSearchArg());
		view.addObject(KEY_DATA, orderDetails);
		view.addObject("totalMoney", getTotalMoney(orderDetails));
		view.addObject("searchArg", pageState.getSearchArg());
		view.setViewName(INDEX_PAGE);
		return view;
	}

	private String getDesMsg(SearchArg searchArg) {
		String desMsg = null;
		Integer id;
		switch (searchArg.getType()) {
		case WebConst.SEARCH_ORDER_USERID:
			id = Integer.valueOf(searchArg.getArg());
			User user = userDao.get(id);
			desMsg = "手机号：" + user.getCellnum() + "。此用户的订单如下：";
			break;
		case WebConst.SEARCH_ORDER_WORKERID:
			id = Integer.valueOf(searchArg.getArg());
			Worker worker = workerDao.get(id);
			desMsg = "姓名：" + worker.getName() + "。此工作人员的订单如下：";
			break;
		case WebConst.SEARCH_ORDER_LAUNDRYID:
			id = Integer.valueOf(searchArg.getArg());
			Laundry laundry = laundryDao.get(id);
			desMsg = "名称：" + laundry.getName() + "。此商家的订单如下：";
			break;
		}
		return desMsg;
	}

	private Double getTotalMoney(List<OrderDetail> orderDetails) {
		Double totalMoney = 0.0;
		Double money;
		for (OrderDetail orderDetail : orderDetails) {
			money = orderDetail.getTotalMoney();
			if (money != null) {
				totalMoney += money;
			}
		}
		return totalMoney;
	}

	@RequestMapping("/list")
	public ModelAndView list(HttpSession session) {
		ModelAndView view = new ModelAndView();
		SearchArg searchArg = new SearchArg(WebConst.SEARCH_ALL, null);
		setPageArg(session, searchArg);
		view.setViewName("redirect:" + INDEX_PAGE + ".html");
		return view;
	}

	@RequestMapping("/curday")
	public ModelAndView curDay(HttpSession session) {
		ModelAndView view = new ModelAndView();
		setPageArg(session, ComUtil.curDayArg);
		view.setViewName("redirect:" + INDEX_PAGE + ".html");
		return view;
	}

	@RequestMapping("/search")
	public ModelAndView search(HttpSession session, @Valid SearchArg searchArg, BindingResult result) {
		ModelAndView view = new ModelAndView();
		if (result.hasErrors()) {
			setPageArg(session, new MsgArg(KEY_INFO_MSG, "查询参数错误。"));
		} else {
			setPageArg(session, searchArg);
		}
		view.setViewName("redirect:" + INDEX_PAGE + ".html");
		return view;
	}

	@RequestMapping("/detail")
	public ModelAndView getDetail(HttpSession session, @RequestParam("orderid") Integer orderid) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		backStack.push(new PageState("order/detail", null));

		OrderDetail orderDetail = orderDao.getDetail(orderid);
		view.addObject("orderDetail", orderDetail);
		view.setViewName(VIEW_PREFIX + "/detail");
		return view;
	}

	@RequestMapping("/delete")
	public ModelAndView delete(HttpSession session, @RequestParam("orderids") String orderids) {
		ModelAndView view = new ModelAndView();
		orderService.deleteOrders(SuUtil.getIds(orderids));
		setPageArg(session, new MsgArg(KEY_INFO_MSG, "删除成功。"));
		view.setViewName("redirect:" + INDEX_PAGE + ".html");
		return view;
	}

	@RequestMapping("/cancel")
	public ModelAndView cancel(HttpSession session, @RequestParam("orderids") String orderids) {
		ModelAndView view = new ModelAndView();
		boolean isException = false;
		try {
			orderService.cancelOrders(SuUtil.getIds(orderids));
		} catch (SuException e) {
			setPageArg(session, new MsgArg(KEY_INFO_MSG, "取消失败。" + e.getMessage()));
			isException = true;
		}
		if (!isException) {
			setPageArg(session, new MsgArg(KEY_INFO_MSG, "取消成功。"));
		}
		view.setViewName("redirect:" + INDEX_PAGE + ".html");
		return view;
	}

	@RequestMapping("/pay")
	public ModelAndView pay(HttpSession session, @RequestParam("orderid") Integer orderid) {
		ModelAndView view = new ModelAndView();
		boolean isException = false;
		PrepayResult prepayResult = null;
		try {
			PayArg payArg = new PayArg();
			payArg.setClient(PayArg.Client.back);
			payArg.setPayType(PayType.balance);
			payArg.setOrderid(orderid);
			prepayResult = orderService.prepay(payArg, null);
		} catch (SuException e) {
			setPageArg(session, new MsgArg(KEY_INFO_MSG, "支付失败。" + e.getMessage()));
			isException = true;
		}
		if (!isException) {
			if (prepayResult.getCodeEnum() != PrepayResult.Code.succ) {
				setPageArg(session, new MsgArg(KEY_INFO_MSG, prepayResult.getMsg()));
			} else {
				setPageArg(session, new MsgArg(KEY_INFO_MSG, "支付成功。"));
			}

		}
		view.setViewName("redirect:" + INDEX_PAGE + ".html");
		return view;
	}

	@RequestMapping("/showmodify")
	public ModelAndView showModify(HttpSession session, @RequestParam("orderid") Integer orderid) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		backStack.push(new PageState("order/modify", null));

		Order order = orderDao.get(orderid);
		if (order == null) {
			setPageArg(session, new MsgArg(KEY_INFO_MSG, "修改失败。数据不存在。"));
			view.setViewName("redirect:" + backStack.getBackLink("order/modify"));
			return view;
		}
		prepare(view);
		view.addObject("order", order);
		view.addObject(KEY_BACK_LINK, backStack.getBackLink("order/modify"));
		view.setViewName(VIEW_PREFIX + "/modify");
		return view;
	}

	@RequestMapping("/modify")
	public ModelAndView modify(HttpSession session, Order order) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		if (order.getId() == null) {
			view.addObject(KEY_INFO_MSG, "修改失败。发生错误。");
		} else {
			orderService.modifyOrder(order);
		}
		prepare(view);
		view.addObject("order", order);
		view.addObject(KEY_BACK_LINK, backStack.getBackLink("order/modify"));
		view.setViewName(VIEW_PREFIX + "/modify");
		return view;
	}

	private void prepare(ModelAndView view) {
		SearchArg searchArg = new SearchArg(WebConst.SEARCH_ALL, null);
		List<Worker> workers = workerService.searchWorker(searchArg);
		view.addObject("workers", workers);
		List<Laundry> laundrys = laundryService.searchLaundry(searchArg);
		view.addObject("laundrys", laundrys);
	}

	@RequestMapping("/history")
	public ModelAndView listHistory(HttpSession session, @RequestParam("orderid") Integer orderid) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		backStack.push(new PageState("order/history", null));

		List<History> historys = historyDao.findByOrderid(orderid);
		view.addObject("historys", historys);
		Order order = orderDao.get(orderid);
		String desMsg = "订单号：" + order.getOrderno() + "。此订单的操作历史如下：";
		view.addObject(KEY_DES_MSG, desMsg);
		view.setViewName(VIEW_PREFIX + "/history");
		return view;
	}

	@RequestMapping("/check")
	public ModelAndView check(HttpSession session, @RequestParam("orderid") Integer orderid) {
		ModelAndView view = new ModelAndView();
		boolean isException = false;
		try {
			orderService.checkOrder(orderid);
		} catch (SuException e) {
			setPageArg(session, new MsgArg(KEY_INFO_MSG, "添加明细失败。" + e.getMessage()));
			isException = true;
		}
		if (!isException) {
			setPageArg(session, new MsgArg(KEY_INFO_MSG, "添加明细成功。"));
		}
		view.setViewName("redirect:" + INDEX_PAGE + "/back.html");
		return view;
	}

	@RequestMapping("/clothes/back")
	public ModelAndView clothesBack(HttpSession session) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		backStack.pop();
		PageState pageState = backStack.peek();
		view.setViewName("redirect:" + VIEW_PREFIX + "/clothes.html?orderid="
				+ pageState.getUpperid());
		return view;
	}

	@RequestMapping("/clothes")
	public ModelAndView listClothes(HttpSession session, @RequestParam("orderid") Integer orderid) {
		ModelAndView view = new ModelAndView();

		BackStack backStack = getBackStack(session);
		backStack.push(new PageState("order/clothes", orderid));
		PageArg pageArg = getPageArg(session);
		if (pageArg != null) {
			// 此页面不接受para类型的参数
			switch (pageArg.getArgType()) {
			case msg:
				MsgArg msgArg = (MsgArg) pageArg;
				view.addObject(msgArg.getKey(), msgArg.getMsg());
				break;
			}
		}

		Order order = orderDao.get(orderid);
		String desMsg = "订单号：" + order.getOrderno() + "。此订单的物品明细如下：";
		view.addObject(KEY_DES_MSG, desMsg);

		List<Clothes> clothess = clothesDao.findByOrderid(orderid);
		view.addObject("clothess", clothess);
		view.addObject("orderid", orderid);
		view.setViewName(VIEW_PREFIX + "/clothes");
		return view;
	}

	@RequestMapping("/showaddclothes")
	public ModelAndView showAddClothes(HttpSession session, @RequestParam("orderid") Integer orderid) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		backStack.push(new PageState("order/addclothes", null));

		Clothes clothes = new Clothes();
		clothes.setOrderid(orderid);
		view.addObject("clothes", clothes);
		view.addObject(KEY_BACK_LINK, backStack.getBackLink("order/addclothes"));
		view.setViewName(VIEW_PREFIX + "/addclothes");
		return view;
	}

	@RequestMapping("/addclothes")
	public ModelAndView addClothes(HttpSession session, @Valid Clothes clothes, BindingResult result) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		if (!result.hasErrors()) {
			orderService.addClothes(clothes);
			view.addObject(KEY_INFO_MSG, "添加成功。");
		}
		view.addObject(KEY_BACK_LINK, backStack.getBackLink("order/addclothes"));
		view.setViewName(VIEW_PREFIX + "/addclothes");
		return view;
	}

	@RequestMapping("/deleteclothes")
	public ModelAndView deleteClothes(HttpSession session,
			@RequestParam("clothesids") String clothesids) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		backStack.push(new PageState("order/deleteclothes", null));

		List<Integer> clothesidList = SuUtil.getIds(clothesids);
		orderService.deleteClothess(clothesidList);
		setPageArg(session, new MsgArg(KEY_INFO_MSG, "删除成功。"));
		view.setViewName("redirect:" + VIEW_PREFIX + "/clothes/back.html");
		return view;
	}

}
