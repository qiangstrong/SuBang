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
import com.subang.util.WebConstant;

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
		case WebConstant.SEARCH_NULL:
			users = new ArrayList<User>();
			break;
		case WebConstant.SEARCH_ALL:
			users = userDao.findAll();
			break;
		case WebConstant.SEARCH_NAME:
			users = userDao.findByNickname(searchArg.getArg());
			break;
		case WebConstant.SEARCH_CELLNUM:
			users = userDao.findByCellnum(searchArg.getArg());
			break;
		}
		return users;
	}

	public User getUser(Integer userid) {
		return userDao.get(userid);
	}

	public void modifyUser(User user) {
		userDao.update(user);
	}

	public void deleteUser(List<Integer> userids) {
		for (Integer userid : userids) {
			userDao.delete(userid);
		}
	}

	/**
	 * 与用户地址相关的操作
	 */
	public List<AddrDetail> listAddrDetailByUserid(Integer userid) {
		return addrDao.findAddrDetailByUserid(userid);
	}

	public Addr getAddr(Integer addrid){
		return addrDao.get(addrid);
	}
	
	public void deleteAddr(List<Integer> addrids) throws BackException {
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
			}else {
				isAll=false;
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
		case WebConstant.SEARCH_NULL:
			orderDetails = new ArrayList<OrderDetail>();
			break;
		case WebConstant.SEARCH_ALL:
			orderDetails = orderDao.findOrderDetailAll();
			break;
		case WebConstant.SEARCH_ORDER_USERID:
			orderDetails=orderDao.findOrderDetailByUserid(new Integer(searchArg.getArg()));
			break;
		case WebConstant.SEARCH_ORDER_STATE:
			orderDetails=orderDao.findOrderDetailByState(State.toState(searchArg.getArg()));
			break;
		case WebConstant.SEARCH_ORDER_ORDERNO:
			orderDetails = orderDao.findOrderDetailByOrderno(searchArg.getArg());
			break;
		case WebConstant.SEARCH_ORDER_USER_NICKNAME:
			orderDetails = new ArrayList<OrderDetail>();
			List<User> users1 = userDao.findByNickname(searchArg.getArg());
			for (User user : users1) {
				orderDetails.addAll(orderDao.findOrderDetailByUserid(user.getId()));
			}
			break;
		case WebConstant.SEARCH_ORDER_USER_CELLNUM:
			orderDetails = new ArrayList<OrderDetail>();
			List<User> users2 = userDao.findByCellnum(searchArg.getArg());
			for (User user : users2) {
				orderDetails.addAll(orderDao.findOrderDetailByUserid(user.getId()));
			}
			break;
		case WebConstant.SEARCH_ORDER_LAUNDRY_NAME:
			orderDetails = new ArrayList<OrderDetail>();
			List<Laundry> laundrys = laundryDao.findByName(searchArg.getArg());
			for (Laundry laundry : laundrys) {
				orderDetails.addAll(orderDao.findOrderDetailByLaundryid(laundry.getId()));
			}
			break;
		}
		return orderDetails;
	}

	public Order getOrder(Integer orderid){
		return orderDao.get(orderid);
	}
	
	// 完成订单分配商家，指定价格的功能
	public void modifyOrder(Order order) throws BackException {
		if (order.getStateEnum() == State.fetched) {
			orderDao.update(order);
		} else {
			throw new BackException("只能在订单已取走的状态下，完成当前操作。");
		}
	}

	public void finishOrder(List<Integer> orderids) throws BackException {
		boolean isAll = true;
		Order order = null;
		History history = new History();
		history.setOperation(Operation.finish);
		for (Integer orderid : orderids) {
			order = orderDao.get(orderid);
			if (order.getStateEnum() == State.fetched) {
				order.setState(State.finished);
				history.setOrderid(order.getId());
				orderDao.update(order);
				historyDao.save(history);
			} else {
				isAll = false;
			}
		}
		if (!isAll) {
			throw new BackException("由于订单状态不符，部分订单没有完成指定操作。");
		}
	}

	public void cancelOrder(List<Integer> orderids) throws BackException {
		boolean isAll = true;
		Order order = null;
		History history = new History();
		history.setOperation(Operation.cancel);
		for (Integer orderid : orderids) {
			order = orderDao.get(orderid);
			if (order.getStateEnum() == State.accepted) {
				order.setState(State.canceled);
				history.setOrderid(order.getId());
				orderDao.update(order);
				historyDao.save(history);
			} else {
				isAll = false;
			}
		}
		if (!isAll) {
			throw new BackException("由于订单状态不符，部分订单没有完成指定操作。");
		}
	}

	public void deleteOrder(List<Integer> orderids) throws BackException {
		boolean isAll = true;
		Order order = null;
		for (Integer orderid : orderids) {
			order = orderDao.get(orderid);
			if (order.getStateEnum() == State.finished || order.getStateEnum() == State.canceled) {
				orderDao.delete(orderid);
				Addr addr = addrDao.get(order.getAddrid());
				if (!addr.isValid() && orderDao.findNumByAddrid(addr.getId()) == 0) {
					addrDao.delete(addr.getId());
				}
			} else {
				isAll = false;
			}
		}
		if (!isAll) {
			throw new BackException("由于订单状态不符，部分订单没有完成指定操作。");
		}
	}

	/**
	 * 与操作历史相关的操作
	 */
	public List<History> listHistoryByOrderid(Integer orderid) {
		return historyDao.findByOrderid(orderid);
	}
}
