package com.subang.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import weixin.popular.api.PayMchAPI;
import weixin.popular.bean.pay.PayAppRequest;
import weixin.popular.bean.paymch.Unifiedorder;
import weixin.popular.bean.paymch.UnifiedorderResult;
import weixin.popular.util.PayUtil;
import weixin.popular.util.StringUtils;

import com.alipay.api.PayAPI;
import com.alipay.bean.AlipayOrder;
import com.subang.bean.OrderDetail;
import com.subang.bean.PayArg;
import com.subang.bean.PrepayResult;
import com.subang.bean.SearchArg;
import com.subang.bean.TicketDetail;
import com.subang.domain.Addr;
import com.subang.domain.Clothes;
import com.subang.domain.History;
import com.subang.domain.Laundry;
import com.subang.domain.Order;
import com.subang.domain.Order.OrderType;
import com.subang.domain.Order.State;
import com.subang.domain.Payment;
import com.subang.domain.Payment.PayType;
import com.subang.domain.Region;
import com.subang.domain.User;
import com.subang.domain.Worker;
import com.subang.tool.SuException;
import com.subang.util.ComUtil;
import com.subang.util.PushUtil;
import com.subang.util.StratUtil;
import com.subang.util.StratUtil.ScoreType;
import com.subang.util.SuUtil;
import com.subang.util.WebConst;

@Service
public class OrderService extends BaseService {

	@Autowired
	protected UserService userService; // 余额支付，需要生成balance订单

	/**
	 * 订单
	 */
	// 后台查找订单
	public List<OrderDetail> searchOrder(SearchArg searchArg) {
		List<OrderDetail> orderDetails = null;
		searchArg.pre();
		switch (searchArg.getType()) {
		case WebConst.SEARCH_NULL:
			orderDetails = new ArrayList<OrderDetail>();
			break;
		case WebConst.SEARCH_ALL:
		case WebConst.SEARCH_ORDER_STATE:
		case WebConst.SEARCH_ORDER_ORDERNO:
		case WebConst.SEARCH_ORDER_USERID:
		case WebConst.SEARCH_ORDER_WORKERID:
		case WebConst.SEARCH_ORDER_LAUNDRYID:
			orderDetails = orderDao.findDetail(searchArg);
			break;
		case WebConst.SEARCH_ORDER_USER_NICKNAME:
			orderDetails = new ArrayList<OrderDetail>();
			List<User> users1 = userDao.findByNickname(searchArg.getArg());
			SearchArg searchArg1 = new SearchArg();
			searchArg1.setType(WebConst.SEARCH_ORDER_USERID);
			searchArg1.setStartTime(searchArg.getStartTime());
			searchArg1.setEndTime(searchArg.getEndTime());
			for (User user : users1) {
				searchArg1.setArg(user.getId().toString());
				orderDetails.addAll(orderDao.findDetail(searchArg1));
			}
			break;
		case WebConst.SEARCH_ORDER_USER_CELLNUM:
			orderDetails = new ArrayList<OrderDetail>();
			List<User> users2 = userDao.findByCellnum(searchArg.getArg());
			SearchArg searchArg2 = new SearchArg();
			searchArg2.setType(WebConst.SEARCH_ORDER_USERID);
			searchArg2.setStartTime(searchArg.getStartTime());
			searchArg2.setEndTime(searchArg.getEndTime());
			for (User user : users2) {
				searchArg2.setArg(user.getId().toString());
				orderDetails.addAll(orderDao.findDetail(searchArg2));
			}
			break;
		case WebConst.SEARCH_ORDER_LAUNDRY_NAME:
			orderDetails = new ArrayList<OrderDetail>();
			List<Laundry> laundrys = laundryDao.findByName(searchArg.getArg());
			SearchArg searchArg3 = new SearchArg();
			searchArg3.setType(WebConst.SEARCH_ORDER_LAUNDRYID);
			searchArg3.setStartTime(searchArg.getStartTime());
			searchArg3.setEndTime(searchArg.getEndTime());
			for (Laundry laundry : laundrys) {
				searchArg3.setArg(laundry.getId().toString());
				orderDetails.addAll(orderDao.findDetail(searchArg3));
			}
			break;
		default:
			orderDetails = new ArrayList<OrderDetail>();
		}
		searchArg.after();
		return orderDetails;
	}

	// 用户查找订单
	public List<OrderDetail> searchOrderByUseridAndState(Integer userid, int stateType) {
		List<OrderDetail> orderDetails = new ArrayList<OrderDetail>();
		switch (stateType) {
		case WebConst.ORDER_STATE_UNDONE:
			orderDetails.addAll(orderDao.findDetailByUseridAndState(userid, State.accepted));
			orderDetails.addAll(orderDao.findDetailByUseridAndState(userid, State.priced));
			orderDetails.addAll(orderDao.findDetailByUseridAndState(userid, State.paid));
			orderDetails.addAll(orderDao.findDetailByUseridAndState(userid, State.fetched));
			orderDetails.addAll(orderDao.findDetailByUseridAndState(userid, State.checked));
			break;
		case WebConst.ORDER_STATE_DONE:
			orderDetails.addAll(orderDao.findDetailByUseridAndState(userid, State.delivered));
			orderDetails.addAll(orderDao.findDetailByUseridAndState(userid, State.remarked));
			orderDetails.addAll(orderDao.findDetailByUseridAndState(userid, State.canceled));
			break;
		}
		return orderDetails;
	}

	// 取衣员查找订单
	public List<OrderDetail> searchOrderByWorkeridAndState(Integer workerid, int stateType) {
		List<OrderDetail> orderDetails = new ArrayList<OrderDetail>();
		switch (stateType) {
		case WebConst.ORDER_STATE_FETCH:
			orderDetails.addAll(orderDao.findDetailByWorkeridAndState(workerid, State.accepted));
			orderDetails.addAll(orderDao.findDetailByWorkeridAndState(workerid, State.priced));
			orderDetails.addAll(orderDao.findDetailByWorkeridAndState(workerid, State.paid));
			break;
		case WebConst.ORDER_STATE_DELIVER:
			orderDetails.addAll(orderDao.findDetailByWorkeridAndState(workerid, State.fetched));
			orderDetails.addAll(orderDao.findDetailByWorkeridAndState(workerid, State.checked));
			break;
		case WebConst.ORDER_STATE_FINISH:
			orderDetails.addAll(orderDao.findDetailByWorkeridAndState(workerid, State.delivered));
			orderDetails.addAll(orderDao.findDetailByWorkeridAndState(workerid, State.remarked));
			break;
		}
		return orderDetails;
	}

	// 用户添加订单。 工作人员使用app查询自己的订单，不再给工作人员发送短信。 下单的时候生成支付信息（由数据库触发器保证）
	public void addOrder(Order order) {

		order.setState(State.accepted);
		Addr addr = addrDao.get(order.getAddrid());
		Region region = regionDao.get(addr.getRegionid());
		Integer workerid = region.getWorkerid();
		if (workerid == null) {
			List<Worker> coreWorkers = workerDao.findByCore();
			workerid = coreWorkers.get(ComUtil.random.nextInt(coreWorkers.size())).getId();
		}
		order.setWorkerid(workerid);

		boolean flag;
		do {
			try {
				order.setOrderno(StratUtil.getOrderno(OrderType.order));
				orderDao.save(order);
				flag = false;
			} catch (DuplicateKeyException e) {
				flag = true;
			}
		} while (flag);

		order = orderDao.getByOrderno(order.getOrderno());

		History history = new History();
		history.setOperation(State.accepted);
		history.setOrderid(order.getId());
		historyDao.save(history);

		User user = userDao.get(order.getUserid());
		user.setAddrid(order.getAddrid());
		userDao.update(user);

		Worker worker = workerDao.get(workerid);
		PushUtil.send(new String[] { worker.getCellnum() });
	}

	// 取衣员计价
	public void priceOrder(Integer orderid, double money) throws SuException {
		Order order = orderDao.get(orderid);
		State state = order.getStateEnum();
		if (state != State.accepted && state != State.priced) {
			throw new SuException("由于订单状态不符，没有完成指定操作。");
		}
		money = ComUtil.round(money);
		order.setMoney(money);
		if (money < new Double(SuUtil.getSuProperty("orderMoney"))) {
			order.setFreight(new Double(SuUtil.getSuProperty("orderFreight")));
		} else {
			order.setFreight(0.0);
		}
		order.setState(State.priced);
		orderDao.update(order);

		if (state == State.accepted) {
			History history = new History();
			history.setOperation(State.priced);
			history.setOrderid(order.getId());
			historyDao.save(history);
		} else {
			historyDao.updateTime(orderid, State.priced);
		}
	}

	// 取衣员扫码，订单进入已取走状态
	public void fetchOrder(Integer orderid, String barcode) throws SuException {
		Order order = orderDao.get(orderid);
		if (order.getStateEnum() != State.paid) {
			throw new SuException("由于订单状态不符，没有完成指定操作。");
		}
		order.setBarcode(barcode);
		order.setState(State.fetched);
		orderDao.update(order);

		History history = new History();
		history.setOperation(State.fetched);
		history.setOrderid(order.getId());
		historyDao.save(history);
	}

	// 取衣员对订单添加备注
	public void commentOrder(Integer orderid, String comment) {
		Order order = orderDao.get(orderid);
		order.setWorkerComment(comment);
		orderDao.update(order);
	}

	// 后台完成订单分配商家，手动分配取衣员的功能
	public void modifyOrder(Order order) {
		Order order_old = orderDao.get(order.getId());
		// 如果订单被其他管理人员删除，修改操作已经没有意义，直接返回
		if (order_old == null) {
			return;
		}

		Integer workerid_old = order_old.getWorkerid();
		Integer workerid = order.getWorkerid();
		if (!workerid_old.equals(workerid)) {
			Worker worker_old = workerDao.get(workerid_old);
			Worker worker = workerDao.get(workerid);
			PushUtil.send(new String[] { worker_old.getCellnum(), worker.getCellnum() });
		}

		order_old.setLaundryid(order.getLaundryid());
		order_old.setWorkerid(workerid);
		orderDao.update(order_old);
	}

	// 管理人员完成对物品明细的添加
	public void checkOrder(Integer orderid) throws SuException {
		Order order = orderDao.get(orderid);
		State state = order.getStateEnum();
		if (state != State.fetched && state != State.checked) {
			throw new SuException("由于订单状态不符，没有完成指定操作。");
		}
		order.setState(State.checked);
		orderDao.update(order);

		if (state == State.fetched) {
			History history = new History();
			history.setOperation(State.checked);
			history.setOrderid(order.getId());
			historyDao.save(history);
		} else {
			historyDao.updateTime(orderid, State.checked);
		}

	}

	public void deliverOrder(Integer orderid) throws SuException {
		Order order = orderDao.get(orderid);
		if (order.getStateEnum() != State.checked) {
			throw new SuException("由于订单状态不符，没有完成指定操作。");
		}
		order.setState(State.delivered);
		orderDao.update(order);

		History history = new History();
		history.setOperation(State.delivered);
		history.setOrderid(order.getId());
		historyDao.save(history);
	}

	public void remarkOrder(Integer orderid, String remark) throws SuException {
		Order order = orderDao.get(orderid);
		if (order.getStateEnum() != State.delivered) {
			throw new SuException("由于订单状态不符，没有完成指定操作。");
		}
		order.setRemark(remark);
		order.setState(State.remarked);
		orderDao.update(order);

		History history = new History();
		history.setOperation(State.remarked);
		history.setOrderid(order.getId());
		historyDao.save(history);

		StratUtil.updateScore(order.getUserid(), ScoreType.remark, null);
	}

	public void cancelOrders(List<Integer> orderids) throws SuException {
		boolean isAll = true;
		for (Integer orderid : orderids) {
			if (!cancelOrder(orderid)) {
				isAll = false;
			}
		}
		if (!isAll) {
			throw new SuException("由于订单状态不符，部分订单没有完成指定操作。");
		}
	}

	public boolean cancelOrder(Integer orderid) {
		History history = null;
		Order order = orderDao.get(orderid);
		State state = order.getStateEnum();
		if (state == State.canceled) {
			return true;
		}
		if (state != State.accepted && state != State.priced) {
			return false;
		}
		order.setState(State.canceled);
		orderDao.update(order);

		history = new History();
		history.setOperation(State.canceled);
		history.setOrderid(order.getId());
		historyDao.save(history);

		Worker worker = workerDao.get(order.getWorkerid());
		PushUtil.send(new String[] { worker.getCellnum() });
		return true;
	}

	public void deleteOrders(List<Integer> orderids) {
		for (Integer orderid : orderids) {
			deleteOrder(orderid);
		}
	}

	private void deleteOrder(Integer orderid) {
		Order order = orderDao.get(orderid);
		orderDao.delete(orderid);
		Addr addr = addrDao.get(order.getAddrid());
		if (!addr.getValid() && orderDao.findNumByAddrid(addr.getId()) == 0) {
			addrDao.delete(addr.getId());
		}
	}

	/**
	 * 支付
	 */

	// 使用优惠券支付。
	private PrepayResult payByTicket(PayArg payArg) {
		PrepayResult result = new PrepayResult();
		OrderDetail orderDetail = orderDao.getDetail(payArg.getOrderid());
		Payment payment = paymentDao.getByOrderno(orderDetail.getOrderno());
		TicketDetail ticketDetail = ticketDao.getDetail(payArg.getTicketid());
		if (ticketDetail.getCategoryid() != null) {
			if (ticketDetail.getCategoryid() != orderDetail.getCategoryid()) {
				result.setCode(PrepayResult.Code.fail);
				result.setMsg("优惠券类型不符。");
				return result;
			}
		}
		ticketDao.delete(payArg.getTicketid());
		if (orderDetail.getActualMoney() > ticketDetail.getMoney()) {
			payment.setMoneyTicket(ticketDetail.getMoney());
			paymentDao.update(payment);
			result.setCode(PrepayResult.Code.conti);
			return result;
		}
		payment.setType(PayType.balance);
		payment.setMoneyTicket(orderDetail.getActualMoney());
		paymentDao.update(payment);
		payOrder(orderDetail.getOrderno());
		result.setCode(PrepayResult.Code.succ);
		return result;
	}

	private PrepayResult payByBalance(PayArg payArg) {
		PrepayResult result = new PrepayResult();
		OrderDetail orderDetail = orderDao.getDetail(payArg.getOrderid());
		User user = userDao.get(orderDetail.getUserid());
		if (orderDetail.getActualMoney() > user.getMoney()) {
			result.setCode(PrepayResult.Code.fail);
			result.setMsg("余额不足。");
			return result;
		}

		PayArg expensePayArg = new PayArg();
		expensePayArg.setClient(payArg.getClient());
		expensePayArg.setPayType(PayType.expense);
		expensePayArg.setMoney(-orderDetail.getActualMoney());
		result = userService.prepay(expensePayArg, user.getId(), null);
		if (result.getCodeEnum() != PrepayResult.Code.succ) {
			return result;
		}

		Payment payment = paymentDao.getByOrderno(orderDetail.getOrderno());
		payment.setType(PayType.balance);
		paymentDao.update(payment);
		payOrder(orderDetail.getOrderno());
		result.setCode(PrepayResult.Code.succ);
		return result;
	}

	private PrepayResult payByWeixin(PayArg payArg, HttpServletRequest request) {
		PrepayResult result = new PrepayResult();

		OrderDetail orderDetail = orderDao.getDetail(payArg.getOrderid());
		User user = userDao.get(orderDetail.getUserid());
		Payment payment = paymentDao.getByOrderno(orderDetail.getOrderno());
		String prepay_id = payment.getPrepay_id();

		String apikey = null, appid = null, mch_id = null;

		switch (payArg.getClientEnum()) {
		case weixin: {
			appid = SuUtil.getAppProperty("appid");
			mch_id = SuUtil.getAppProperty("mch_id");
			apikey = SuUtil.getAppProperty("apikey");
			break;
		}
		case user: {
			appid = SuUtil.getAppProperty("appid_user");
			mch_id = SuUtil.getAppProperty("mch_id_user");
			apikey = SuUtil.getAppProperty("apikey_user");
			break;
		}
		case worker: {
			appid = SuUtil.getAppProperty("appid_worker");
			mch_id = SuUtil.getAppProperty("mch_id_worker");
			apikey = SuUtil.getAppProperty("apikey_worker");
			break;
		}
		}

		if (prepay_id == null) {
			Unifiedorder unifiedorder = new Unifiedorder();
			switch (payArg.getClientEnum()) {
			case weixin: {
				unifiedorder.setTrade_type("JSAPI");
				unifiedorder.setOpenid(user.getOpenid());
				break;
			}
			case worker:
			case user: {
				unifiedorder.setTrade_type("APP");
				break;
			}
			}

			unifiedorder.setAppid(appid);
			unifiedorder.setMch_id(mch_id);
			unifiedorder.setNonce_str(StringUtils.getRandomStringByLength(32));
			unifiedorder.setBody("订单付款");
			unifiedorder.setOut_trade_no(orderDetail.getOrderno());
			Integer money = (int) (orderDetail.getActualMoney() * 100);
			unifiedorder.setTotal_fee(money.toString());
			unifiedorder.setSpbill_create_ip(request.getRemoteAddr());
			unifiedorder.setNotify_url(SuUtil.getBasePath(request) + "weixin/order/wxpay.html");

			UnifiedorderResult unifiedorderResult = PayMchAPI.payUnifiedorder(unifiedorder, apikey);

			if (!unifiedorderResult.getReturn_code().equals("SUCCESS")) {
				LOG.error("错误码:" + unifiedorderResult.getReturn_code() + "; 错误信息:"
						+ unifiedorderResult.getReturn_msg());
				result.setCode(PrepayResult.Code.fail);
				result.setMsg(unifiedorderResult.getReturn_msg());
				return result;
			}

			if (!unifiedorderResult.getResult_code().equals("SUCCESS")) {
				LOG.error("错误码:" + unifiedorderResult.getErr_code() + "; 错误信息:"
						+ unifiedorderResult.getErr_code_des());
				result.setCode(PrepayResult.Code.fail);
				result.setMsg(unifiedorderResult.getErr_code_des());
				return result;
			}

			payment.setPrepay_id(unifiedorderResult.getPrepay_id());
			prepay_id = payment.getPrepay_id();
		}

		Object arg = null;
		switch (payArg.getClientEnum()) {
		case weixin: {
			arg = PayUtil.generateMchPayJsRequestJson(prepay_id, appid, apikey);
			break;
		}
		case worker:
		case user: {
			PayAppRequest payApprequest = new PayAppRequest();
			payApprequest.setAppid(appid);
			payApprequest.setPartnerid(mch_id);
			payApprequest.setPrepayid(prepay_id);
			arg = PayUtil.generateMchPayAppRequest(payApprequest, apikey);
			break;
		}
		}

		payment.setType(PayType.weixin);
		paymentDao.update(payment);
		result.setCode(PrepayResult.Code.conti);
		result.setArg(arg);
		return result;
	}

	private PrepayResult payByAli(PayArg payArg, HttpServletRequest request) {
		PrepayResult result = new PrepayResult();
		OrderDetail orderDetail = orderDao.getDetail(payArg.getOrderid());

		String pid = SuUtil.getAppProperty("pid_ali");
		String prikey = SuUtil.getAppProperty("prikey_ali");

		AlipayOrder alipayOrder = new AlipayOrder();
		alipayOrder.setPartner(pid);
		alipayOrder.setSeller_id(pid);
		alipayOrder.setNotify_url(SuUtil.getBasePath(request) + "weixin/order/alipay.html");
		alipayOrder.setOut_trade_no(orderDetail.getOrderno());
		alipayOrder.setSubject("pay");
		alipayOrder.setBody("pay");
		alipayOrder.setTotal_fee(orderDetail.getActualMoney().toString());

		Object arg = null;
		switch (payArg.getClientEnum()) {
		case weixin: {
			alipayOrder.setReturn_url(SuUtil.getBasePath(request) + "weixin/order/alireturn.html");
			alipayOrder.setShow_url(SuUtil.getBasePath(request) + "weixin/order/index.html?type="
					+ WebConst.ORDER_STATE_UNDONE);
			arg = PayAPI.generatePayWapRequest(alipayOrder, prikey);
			break;
		}
		case worker:
		case user: {
			arg = PayAPI.generatePayMobileRequest(alipayOrder, prikey);
			break;
		}
		}

		Payment payment = paymentDao.getByOrderno(orderDetail.getOrderno());
		payment.setType(PayType.alipay);
		paymentDao.update(payment);
		result.setCode(PrepayResult.Code.conti);
		result.setArg(arg);
		return result;
	}

	public PrepayResult prepay(PayArg payArg, HttpServletRequest request) throws SuException {
		PrepayResult result;
		OrderDetail orderDetail = orderDao.getDetail(payArg.getOrderid());
		if (orderDetail.getStateEnum() != State.priced) {
			throw new SuException("由于订单状态不符，没有完成指定操作。");
		}

		if (!orderDetail.isTicket() && payArg.getTicketid() != null) {
			result = payByTicket(payArg);
			if (result.getCodeEnum() == PrepayResult.Code.succ
					|| result.getCodeEnum() == PrepayResult.Code.fail) {
				return result;
			}
		}
		switch (payArg.getPayTypeEnum()) {
		case balance: {
			result = payByBalance(payArg);
			break;
		}
		case weixin: {
			result = payByWeixin(payArg, request);
			break;
		}
		case alipay: {
			result = payByAli(payArg, request);
			break;
		}
		default: {
			result = new PrepayResult();
			result.setCode(PrepayResult.Code.succ);
			break;
		}
		}
		return result;
	}

	public void payOrder(String orderno) {
		Order order = orderDao.getByOrderno(orderno);
		if (order.getStateEnum() == State.paid) {
			return;
		}
		order.setState(State.paid);
		orderDao.update(order);

		History history = new History();
		history.setOrderid(order.getId());
		history.setOperation(State.paid);
		historyDao.save(history);

		// 积分
		OrderDetail orderDetail = orderDao.getDetail(order.getId());
		ScoreType scoreType;
		if (orderDetail.getPayTypeEnum() == PayType.balance) {
			scoreType = ScoreType.balance;
		} else {
			scoreType = ScoreType.nobalance;
		}
		StratUtil.updateScore(orderDetail.getUserid(), scoreType, orderDetail.getActualMoney());
	}

	/**
	 * 物品明细
	 */
	public void addClothes(Clothes clothes) {
		clothesDao.save(clothes);
	}

	public void deleteClothess(List<Integer> clothesids) {
		for (Integer clothesid : clothesids) {
			clothesDao.delete(clothesid);
		}
	}
}
