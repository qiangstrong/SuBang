package com.subang.controller.app;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.subang.bean.Identity;
import com.subang.bean.OrderDetail;
import com.subang.bean.PayArg;
import com.subang.bean.PrepayResult;
import com.subang.bean.PrepayResult.Code;
import com.subang.bean.Result;
import com.subang.controller.BaseController;
import com.subang.domain.Clothes;
import com.subang.domain.History;
import com.subang.domain.Order;
import com.subang.domain.User;
import com.subang.domain.Worker;
import com.subang.tool.SuException;
import com.subang.util.SuUtil;
import com.subang.util.WebConst;

@Controller("orderController_app")
@RequestMapping("/app/order")
public class OrderController extends BaseController {

	@RequestMapping("/userlist")
	public void userList(Identity identity, @RequestParam("type") Integer stateType,
			@RequestParam(value = "filter", required = false) String filter,
			HttpServletResponse response) {
		User user = getUser(identity);
		List<OrderDetail> orderDetails = orderService.searchOrderByUseridAndState(user.getId(),
				stateType);
		SuUtil.doFilter(filter, orderDetails, OrderDetail.class);
		SuUtil.outputJson(response, orderDetails);
	}

	@RequestMapping("/workerlist")
	public void workerList(Identity identity, @RequestParam("type") Integer stateType,
			@RequestParam(value = "filter", required = false) String filter,
			HttpServletResponse response) {
		Worker worker = getWorker(identity);
		List<OrderDetail> orderDetails = orderService.searchOrderByWorkeridAndState(worker.getId(),
				stateType);
		SuUtil.doFilter(filter, orderDetails, OrderDetail.class);
		SuUtil.outputJson(response, orderDetails);
	}

	@RequestMapping("/get")
	public void get(@RequestParam("type") Integer getType, @RequestParam("arg") String arg,
			HttpServletResponse response) {
		OrderDetail orderDetail = null;
		if (getType == WebConst.ORDER_GET_ID) {
			Integer orderid = Integer.decode(arg);
			orderDetail = orderDao.getDetail(orderid);
		} else {
			String barcode = arg;
			orderDetail = orderDao.getDetailByBarcode(barcode);
			if (orderDetail == null) {
				orderDetail = new OrderDetail();
			}
		}
		SuUtil.outputJson(response, orderDetail);
	}

	@RequestMapping("/add")
	public void add(Identity identity, @Valid Order order, BindingResult result,
			HttpServletResponse response) {
		List<Result> results = SuUtil.getResults(result.getFieldErrors());
		if (!result.hasErrors()) {
			User user = getUser(identity);
			order.setUserid(user.getId());
			orderService.addOrder(order);
		}
		SuUtil.outputJson(response, results);
	}

	@RequestMapping("/price")
	public void price(@RequestParam("orderid") Integer orderid,
			@RequestParam("money") Double money, HttpServletResponse response) {
		try {
			orderService.priceOrder(orderid, money);
		} catch (SuException e) {
			e.printStackTrace();
		}
		SuUtil.outputJsonOK(response);
	}

	@RequestMapping("/fetch")
	public void fetch(@RequestParam("orderid") Integer orderid,
			@RequestParam("barcode") String barcode, HttpServletResponse response) {
		try {
			orderService.fetchOrder(orderid, barcode);
		} catch (SuException e) {
			e.printStackTrace();
		}
		SuUtil.outputJsonOK(response);
	}

	@RequestMapping("/comment")
	public void comment(@RequestParam("orderid") Integer orderid,
			@RequestParam("comment") String comment, HttpServletResponse response) {
		orderService.commentOrder(orderid, comment);
		SuUtil.outputJsonOK(response);
	}

	@RequestMapping("/cancel")
	public void cancel(@RequestParam("orderid") Integer orderid, HttpServletResponse response) {
		orderService.cancelOrder(orderid);
		SuUtil.outputJsonOK(response);
	}

	@RequestMapping("/deliver")
	public void deliver(@RequestParam("orderid") Integer orderid, HttpServletResponse response) {
		try {
			orderService.deliverOrder(orderid);
		} catch (SuException e) {
			e.printStackTrace();
		}
		SuUtil.outputJsonOK(response);
	}

	@RequestMapping("/remark")
	public void remark(@RequestParam("orderid") Integer orderid,
			@RequestParam("remark") String remark, HttpServletResponse response) {
		try {
			orderService.remarkOrder(orderid, remark);
		} catch (SuException e) {
			e.printStackTrace();
		}
		SuUtil.outputJsonOK(response);
	}

	// app应该做充分的校验，确保参数正确
	@RequestMapping("/prepay")
	public void prepay(HttpServletRequest request, PayArg payArg, BindingResult result,
			HttpServletResponse response) {
		PrepayResult prepayResult = null;
		if (result.hasErrors() || payArg.getOrderid() == null) {
			prepayResult = new PrepayResult();
			prepayResult.setCode(Code.fail);
			prepayResult.setMsg("参数错误");
		} else {
			try {
				prepayResult = orderService.prepay(payArg, request);
			} catch (SuException e) {
				e.printStackTrace();
			}
		}
		SuUtil.outputJson(response, prepayResult);
	}

	@RequestMapping("/history")
	public void listHistory(@RequestParam("orderid") Integer orderid, HttpServletResponse response) {
		List<History> historys = historyDao.findByOrderid(orderid);
		SuUtil.outputJson(response, historys);
	}

	@RequestMapping("/clothes")
	public void listClothes(@RequestParam("orderid") Integer orderid, HttpServletResponse response) {
		List<Clothes> clothess = clothesDao.findByOrderid(orderid);
		SuUtil.outputJson(response, clothess);
	}
}
