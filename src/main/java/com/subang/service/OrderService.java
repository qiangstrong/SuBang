package com.subang.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import weixin.popular.api.PayMchAPI;
import weixin.popular.bean.paymch.Unifiedorder;
import weixin.popular.bean.paymch.UnifiedorderResult;
import weixin.popular.util.StringUtils;

import com.subang.bean.OrderDetail;
import com.subang.bean.SearchArg;
import com.subang.bean.TicketDetail;
import com.subang.domain.Addr;
import com.subang.domain.Clothes;
import com.subang.domain.History;
import com.subang.domain.Laundry;
import com.subang.domain.Order;
import com.subang.domain.Order.State;
import com.subang.domain.Payment;
import com.subang.domain.Payment.PayType;
import com.subang.domain.Region;
import com.subang.domain.User;
import com.subang.domain.Worker;
import com.subang.exception.SuException;
import com.subang.util.ComUtil;
import com.subang.util.StratUtil;
import com.subang.util.SuUtil;
import com.subang.util.WebConst;

@Service
public class OrderService extends BaseService {

	/**
	 * 订单
	 */
	// 后台查找订单
	public List<OrderDetail> searchOrder(SearchArg searchArg) {
		List<OrderDetail> orderDetails = null;
		switch (searchArg.getType()) {
		case WebConst.SEARCH_NULL:
			orderDetails = new ArrayList<OrderDetail>();
			break;
		case WebConst.SEARCH_ALL:
			orderDetails = orderDao.findDetailAll();
			break;
		case WebConst.SEARCH_ORDER_STATE:
			orderDetails = orderDao.findDetailByState(State.toState(searchArg.getArg()));
			break;
		case WebConst.SEARCH_ORDER_ORDERNO:
			orderDetails = orderDao.findDetailByOrderno(searchArg.getArg());
			break;
		case WebConst.SEARCH_ORDER_USER_NICKNAME:
			orderDetails = new ArrayList<OrderDetail>();
			List<User> users1 = userDao.findByNickname(searchArg.getArg());
			for (User user : users1) {
				orderDetails.addAll(orderDao.findDetailByUserid(user.getId()));
			}
			break;
		case WebConst.SEARCH_ORDER_USER_CELLNUM:
			orderDetails = new ArrayList<OrderDetail>();
			List<User> users2 = userDao.findByCellnum(searchArg.getArg());
			for (User user : users2) {
				orderDetails.addAll(orderDao.findDetailByUserid(user.getId()));
			}
			break;
		case WebConst.SEARCH_ORDER_LAUNDRY_NAME:
			orderDetails = new ArrayList<OrderDetail>();
			List<Laundry> laundrys = laundryDao.findByName(searchArg.getArg());
			for (Laundry laundry : laundrys) {
				orderDetails.addAll(orderDao.findDetailByLaundryid(laundry.getId()));
			}
			break;
		case WebConst.SEARCH_ORDER_USERID:
			orderDetails = orderDao.findDetailByUserid(new Integer(searchArg.getArg()));
			break;
		case WebConst.SEARCH_ORDER_WORKERID:
			orderDetails = orderDao.findDetailByWorkerid(new Integer(searchArg.getArg()));
			break;
		case WebConst.SEARCH_ORDER_LAUNDRYID:
			orderDetails = orderDao.findDetailByLaundryid(new Integer(searchArg.getArg()));
			break;
		default:
			orderDetails = new ArrayList<OrderDetail>();
		}
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

	// 用户添加订单,工作人员使用app查询自己的订单，下单的时候生成支付信息
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
				order.setOrderno(StratUtil.getOrderno());
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

		Payment payment = new Payment();
		payment.setOrderid(order.getId());
		paymentDao.save(payment);

		User user = userDao.get(order.getUserid());
		user.setAddrid(order.getAddrid());
		userDao.update(user);
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
		if (order.getStateEnum() != State.priced) {
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
		order_old.setLaundryid(order.getLaundryid());
		order_old.setWorkerid(order.getWorkerid());
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

	// 使用优惠券支付。true：完成支付。false：由于优惠券的金额不足，还需继续支付
	public boolean payByTicket(Integer orderid, Integer ticketid) {
		Order order = orderDao.get(orderid);
		Payment payment = paymentDao.getByOrderid(orderid);
		TicketDetail ticketDetail = ticketDao.getDetail(ticketid);
		ticketDao.delete(ticketid);
		double totalMoney = order.getMoney() + order.getFreight();
		if (totalMoney <= ticketDetail.getMoney()) {
			payment.setType(PayType.balance);
			payment.setMoneyTicket(totalMoney);
			paymentDao.update(payment);
			payOrder(order.getOrderno());
			return true;
		}
		payment.setMoneyTicket(ticketDetail.getMoney());
		paymentDao.update(payment);
		return false;
	}

	// 生成预支付id，管理员已经为订单指定价格。这点由前端保证。
	public String getPrepay_id(User user, Order order, HttpServletRequest request) {
		Payment payment = paymentDao.getByOrderid(order.getId());
		String prepay_id = payment.getPrepay_id();
		if (prepay_id != null) {
			return prepay_id;
		}

		double money = order.getMoney() + order.getFreight() - payment.getMoneyTicket();

		Unifiedorder unifiedorder = new Unifiedorder();
		unifiedorder.setAppid(SuUtil.getAppProperty("appid"));
		unifiedorder.setMch_id(SuUtil.getAppProperty("mch_id"));
		unifiedorder.setNonce_str(StringUtils.getRandomStringByLength(32));
		unifiedorder.setBody("订单付款");
		unifiedorder.setOut_trade_no(order.getOrderno());
		Integer price = (int) (money * 100);
		unifiedorder.setTotal_fee(price.toString());
		unifiedorder.setSpbill_create_ip(request.getRemoteAddr());
		unifiedorder.setNotify_url(SuUtil.getAppProperty("notify_url"));
		unifiedorder.setTrade_type("JSAPI");
		unifiedorder.setOpenid(user.getOpenid());

		UnifiedorderResult result = PayMchAPI.payUnifiedorder(unifiedorder,
				SuUtil.getAppProperty("apikey"));
		if (!result.getReturn_code().equals("SUCCESS")) {
			LOG.error("错误码:" + result.getReturn_code() + "; 错误信息:" + result.getReturn_msg());
			return null;
		}

		if (!result.getResult_code().equals("SUCCESS")) {
			LOG.error("错误码:" + result.getErr_code() + "; 错误信息:" + result.getErr_code_des());
			return null;
		}

		payment.setType(PayType.weixin);
		payment.setPrepay_id(result.getPrepay_id());
		paymentDao.update(payment);
		return result.getPrepay_id();
	}

	public void payOrder(String orderno) {
		History history = null;
		Order order = orderDao.getByOrderno(orderno);
		order.setState(State.paid);
		orderDao.update(order);

		history = new History();
		history.setOrderid(order.getId());
		history.setOperation(State.paid);
		historyDao.save(history);
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
