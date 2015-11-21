package com.subang.controller.back;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.subang.bean.OrderDetail;
import com.subang.bean.PageState;
import com.subang.bean.SearchArg;
import com.subang.controller.BaseController;
import com.subang.domain.Clothes;
import com.subang.domain.History;
import com.subang.domain.Laundry;
import com.subang.domain.Order;
import com.subang.domain.Worker;
import com.subang.exception.SuException;
import com.subang.util.SuUtil;
import com.subang.util.WebConst;

@Controller
@RequestMapping("/back/order")
public class OrderController extends BaseController {

	private static final String INDEX_PAGE = WebConst.BACK_PREFIX + "/order/index";
	private static final String KEY_DATA = "orderDetails";

	@RequestMapping("/index")
	public ModelAndView index(HttpSession session, @RequestParam("type") int type) {
		ModelAndView view = new ModelAndView();

		if (type == WebConst.INDEX_BREAK) {
			savePageState(session, new SearchArg(WebConst.SEARCH_NULL, null));
		}
		PageState pageState = getPageState(session);

		List<OrderDetail> orderDetails = orderService.searchOrder(pageState.getSearchArg());
		view.addObject(KEY_DATA, orderDetails);
		view.addObject(KEY_ERR_MSG, session.getAttribute(KEY_ERR_MSG));
		session.removeAttribute(KEY_ERR_MSG);
		view.addObject(KEY_INFO_MSG, session.getAttribute(KEY_INFO_MSG));
		session.removeAttribute(KEY_INFO_MSG);
		view.setViewName(INDEX_PAGE);
		return view;
	}

	@RequestMapping("/list")
	public ModelAndView list(HttpSession session) {
		ModelAndView view = new ModelAndView();
		SearchArg searchArg = new SearchArg(WebConst.SEARCH_ALL, null);
		savePageState(session, searchArg);
		List<OrderDetail> orderDetails = orderService.searchOrder(searchArg);
		view.addObject(KEY_DATA, orderDetails);
		view.setViewName(INDEX_PAGE);
		return view;
	}

	@RequestMapping("/search")
	public ModelAndView search(HttpSession session, SearchArg searchArg) {
		ModelAndView view = new ModelAndView();
		savePageState(session, searchArg);
		List<OrderDetail> orderDetails = orderService.searchOrder(searchArg);
		view.addObject(KEY_DATA, orderDetails);
		view.setViewName(INDEX_PAGE);
		return view;
	}

	@RequestMapping("/delete")
	public ModelAndView delete(HttpSession session, @RequestParam("orderids") String orderids) {
		ModelAndView view = new ModelAndView();
		orderService.deleteOrders(SuUtil.getIds(orderids));
		session.setAttribute(KEY_INFO_MSG, "删除成功。");
		view.setViewName("redirect:" + WebConst.BACK_PREFIX + "/order/index.html?type=1");
		return view;
	}

	@RequestMapping("/cancel")
	public ModelAndView cancel(HttpSession session, @RequestParam("orderids") String orderids) {
		ModelAndView view = new ModelAndView();
		boolean isException = false;
		try {
			orderService.cancelOrders(SuUtil.getIds(orderids));
		} catch (SuException e) {
			session.setAttribute(KEY_INFO_MSG, "取消订单失败。" + e.getMessage());
			isException = true;
		}
		if (!isException) {
			session.setAttribute(KEY_INFO_MSG, "取消订单成功。");
		}
		view.setViewName("redirect:" + WebConst.BACK_PREFIX + "/order/index.html?type=1");
		return view;
	}

	@RequestMapping("/showmodify")
	public ModelAndView showModify(@RequestParam("orderid") Integer orderid) {
		ModelAndView view = new ModelAndView();
		Order order = orderDao.get(orderid);
		prepare(view);
		view.addObject("order", order);
		view.setViewName(WebConst.BACK_PREFIX + "/order/modify");
		return view;
	}

	@RequestMapping("/modify")
	public ModelAndView modify(Order order) {
		ModelAndView view = new ModelAndView();
		if (order.getId() == null) {
			view.addObject(KEY_INFO_MSG, "修改失败。发生错误。");
		} else {
			orderService.modifyOrder(order);
		}
		view.addObject("order", order);
		prepare(view);
		view.setViewName(WebConst.BACK_PREFIX + "/order/modify");
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
	public ModelAndView listHistory(Integer orderid) {
		ModelAndView view = new ModelAndView();
		List<History> historys = historyDao.findByOrderid(orderid);
		view.addObject("historys", historys);
		Order order = orderDao.get(orderid);
		String desMsg = "订单号：" + order.getOrderno() + "。此订单的操作历史如下：";
		view.addObject(KEY_DES_MSG, desMsg);
		view.setViewName(WebConst.BACK_PREFIX + "/order/history");
		return view;
	}

	@RequestMapping("/check")
	public ModelAndView check(HttpSession session, @RequestParam("orderid") Integer orderid) {
		ModelAndView view = new ModelAndView();
		boolean isException = false;
		try {
			orderService.checkOrder(orderid);
		} catch (SuException e) {
			session.setAttribute(KEY_INFO_MSG, "添加明细失败。" + e.getMessage());
			isException = true;
		}
		if (!isException) {
			session.setAttribute(KEY_INFO_MSG, "添加明细成功。");
		}
		view.setViewName("redirect:" + WebConst.BACK_PREFIX + "/order/index.html?type=1");
		return view;
	}

	@RequestMapping("/clothes")
	public ModelAndView listClothes(HttpSession session, @RequestParam("orderid") Integer orderid) {
		ModelAndView view = new ModelAndView();
		List<Clothes> clothess = clothesDao.findByOrderid(orderid);
		view.addObject("clothess", clothess);
		view.addObject("orderid", orderid);

		Order order = orderDao.get(orderid);
		String desMsg = "订单号：" + order.getOrderno() + "。此订单的物品明细如下：";
		view.addObject(KEY_DES_MSG, desMsg);

		view.addObject(KEY_ERR_MSG, session.getAttribute(KEY_ERR_MSG));
		session.removeAttribute(KEY_ERR_MSG);
		view.addObject(KEY_INFO_MSG, session.getAttribute(KEY_INFO_MSG));
		session.removeAttribute(KEY_INFO_MSG);

		view.setViewName(WebConst.BACK_PREFIX + "/order/clothes");
		return view;
	}

	@RequestMapping("/showaddclothes")
	public ModelAndView showAddClothes(@RequestParam("orderid") Integer orderid) {
		ModelAndView view = new ModelAndView();
		Clothes clothes = new Clothes();
		clothes.setOrderid(orderid);
		view.addObject("clothes", clothes);
		view.setViewName(WebConst.BACK_PREFIX + "/order/addclothes");
		return view;
	}

	@RequestMapping("/addclothes")
	public ModelAndView addClothes(@Valid Clothes clothes, BindingResult result) {
		ModelAndView view = new ModelAndView();
		if (!result.hasErrors()) {
			orderService.addClothes(clothes);
			view.addObject(KEY_INFO_MSG, "添加成功。");
		}
		view.setViewName(WebConst.BACK_PREFIX + "/order/addclothes");
		return view;
	}

	@RequestMapping("/deleteclothes")
	public ModelAndView deleteClothes(HttpSession session,
			@RequestParam("clothesids") String clothesids) {
		ModelAndView view = new ModelAndView();
		List<Integer> clothesidList = SuUtil.getIds(clothesids);
		orderService.deleteClothess(clothesidList);
		session.setAttribute(KEY_INFO_MSG, "删除成功。");
		Clothes clothes = clothesDao.get(clothesidList.get(0));
		view.setViewName("redirect:" + WebConst.BACK_PREFIX + "/order/clothes.html?orderid="
				+ clothes.getOrderid());
		return view;
	}

}
