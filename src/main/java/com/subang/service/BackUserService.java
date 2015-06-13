package com.subang.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.subang.bean.AddrDetail;
import com.subang.bean.OrderDetail;
import com.subang.bean.SearchArg;
import com.subang.domain.Addr;
import com.subang.domain.History;
import com.subang.domain.History.Operation;
import com.subang.domain.Laundry;
import com.subang.domain.Order;
import com.subang.domain.Order.State;
import com.subang.domain.User;
import com.subang.exception.BackException;
import com.subang.util.Common;
import com.subang.util.WebConst;

/**
 * @author Qiang 后台对用户，地址，订单，操作历史的管理
 */
@Service
public class BackUserService extends CommUserService {

	/**
	 * 与用户相关的操作
	 */
	public List<User> searchUser(SearchArg searchArg) {
		List<User> users = null;
		switch (searchArg.getType()) {
		case WebConst.SEARCH_NULL:
			users = new ArrayList<User>();
			break;
		case WebConst.SEARCH_ALL:
			users = userDao.findAll();
			break;
		case WebConst.SEARCH_NAME:
			users = userDao.findByNickname(searchArg.getArg());
			break;
		case WebConst.SEARCH_CELLNUM:
			users = userDao.findByCellnum(searchArg.getArg());
			break;
		}
		return users;
	}

	public void deleteUsers(List<Integer> userids) {
		for (Integer userid : userids) {
			userDao.delete(userid);
		}
	}

	/**
	 * 与用户地址相关的操作
	 */
	public void deleteAddrs(List<Integer> addrids) throws BackException {
		Addr addr = null;
		User user = null;
		boolean isAll = true;
		for (Integer addrid : addrids) {
			addr = addrDao.get(addrid);
			user = userDao.get(addr.getUserid());
			if (orderDao.findNumByAddrid(addrid) == 0) {
				addrDao.delete(addrid);
				if (user.getAddrid() == addrid) {
					List<Addr> addrs = addrDao.findByUserid(user.getId());
					if (!addrs.isEmpty()) {
						user.setAddrid(addrs.get(0).getId());
						userDao.update(user);
					}
				}
			} else {
				isAll = false;
			}
		}
		if (!isAll) {
			throw new BackException("部分地址没有成功删除。请先删除引用改地址的订单，再尝试删除地址。");
		}
	}

	/**
	 * 与订单相关的操作
	 */
	public List<OrderDetail> searchOrder(SearchArg searchArg) {
		List<OrderDetail> orderDetails = null;
		switch (searchArg.getType()) {
		case WebConst.SEARCH_NULL:
			orderDetails = new ArrayList<OrderDetail>();
			break;
		case WebConst.SEARCH_ALL:
			orderDetails = orderDao.findOrderDetailAll();
			break;
		case WebConst.SEARCH_ORDER_USERID:
			orderDetails = orderDao.findOrderDetailByUserid(new Integer(searchArg.getArg()));
			break;
		case WebConst.SEARCH_ORDER_STATE:
			orderDetails = orderDao.findOrderDetailByState(State.toState(searchArg.getArg()));
			break;
		case WebConst.SEARCH_ORDER_ORDERNO:
			orderDetails = orderDao.findOrderDetailByOrderno(searchArg.getArg());
			break;
		case WebConst.SEARCH_ORDER_USER_NICKNAME:
			orderDetails = new ArrayList<OrderDetail>();
			List<User> users1 = userDao.findByNickname(searchArg.getArg());
			for (User user : users1) {
				orderDetails.addAll(orderDao.findOrderDetailByUserid(user.getId()));
			}
			break;
		case WebConst.SEARCH_ORDER_USER_CELLNUM:
			orderDetails = new ArrayList<OrderDetail>();
			List<User> users2 = userDao.findByCellnum(searchArg.getArg());
			for (User user : users2) {
				orderDetails.addAll(orderDao.findOrderDetailByUserid(user.getId()));
			}
			break;
		case WebConst.SEARCH_ORDER_LAUNDRY_NAME:
			orderDetails = new ArrayList<OrderDetail>();
			List<Laundry> laundrys = laundryDao.findByName(searchArg.getArg());
			for (Laundry laundry : laundrys) {
				orderDetails.addAll(orderDao.findOrderDetailByLaundryid(laundry.getId()));
			}
			break;
		}
		return orderDetails;
	}

	// 完成订单分配商家，指定价格的功能
	public void modifyOrder(Order order) throws BackException {
		if (order.getStateEnum() == State.fetched) {
			orderDao.update(order);
		} else {
			throw new BackException("只能在订单已取走的状态下，完成当前操作。");
		}
	}

	public void finishOrders(List<Integer> orderids) throws BackException {
		boolean isAll = true;
		for (Integer orderid : orderids) {
			if (!finishOrder(orderid)) {
				isAll = false;
			}
		}
		if (!isAll) {
			throw new BackException("由于订单状态不符，部分订单没有完成指定操作。");
		}
	}

	private boolean finishOrder(Integer orderid) {
		History history = null;
		Order order = orderDao.get(orderid);
		if (order.getStateEnum() == State.fetched) {
			order.setState(State.finished);
			orderDao.update(order);

			history = new History();
			history.setOrderid(order.getId());
			history.setOperation(Operation.finish);
			historyDao.save(history);
			
			User user=userDao.get(order.getUserid());
			user.setScore(user.getScore()+Common.getcScore(order.getPrice()));
			userDao.update(user);
			
			return true;
		}
		return false;
	}

	public void cancelOrders(List<Integer> orderids) throws BackException {
		boolean isAll = true;
		for (Integer orderid : orderids) {
			if (!cancelOrder(orderid)) {
				isAll = false;
			}
		}
		if (!isAll) {
			throw new BackException("由于订单状态不符，部分订单没有完成指定操作。");
		}
	}

	public void deleteOrders(List<Integer> orderids) throws BackException {
		boolean isAll = true;
		for (Integer orderid : orderids) {
			if (!deleteOrder(orderid)) {
				isAll = false;
			}
		}
		if (!isAll) {
			throw new BackException("由于订单状态不符，部分订单没有完成指定操作。");
		}
	}
	
	private boolean deleteOrder(Integer orderid) {
		Order order = orderDao.get(orderid);
		if (order.getStateEnum() == State.finished || order.getStateEnum() == State.canceled) {
			orderDao.delete(orderid);
			Addr addr = addrDao.get(order.getAddrid());
			if (!addr.isValid() && orderDao.findNumByAddrid(addr.getId()) == 0) {
				addrDao.delete(addr.getId());
			}
			return true;
		}
		return false;
	}

}
