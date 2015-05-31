package com.subang.service;

import java.util.List;

import com.subang.bean.AddrDetail;
import com.subang.bean.OrderDetail;
import com.subang.domain.Addr;
import com.subang.domain.History;
import com.subang.domain.Order;
import com.subang.domain.User;
import com.subang.domain.History.Operation;
import com.subang.domain.Order.State;
import com.subang.domain.Worker;
import com.subang.util.SMS;

public class CommUserService extends BaseService {

	/**
	 * 与用户相关的操作
	 */
	public User getUser(Integer userid) {
		return userDao.get(userid);
	}

	public void modifyUser(User user) {
		userDao.update(user);
	}

	/**
	 * 与用户地址相关的操作
	 */
	public List<AddrDetail> listAddrDetailByUserid(Integer userid) {
		return addrDao.findAddrDetailByUserid(userid);
	}

	public Addr getAddr(Integer addrid) {
		return addrDao.get(addrid);
	}

	public AddrDetail getAddrDetail(Integer addrid) {
		return addrDao.getAddrDetail(addrid);
	}

	/**
	 * 与订单相关的操作
	 */
	public Order getOrder(Integer orderid) {
		return orderDao.get(orderid);
	}

	public boolean cancelOrder(Integer orderid) {
		History history = null;
		Order order = orderDao.get(orderid);
		if (order.getStateEnum() == State.accepted) {
			order.setState(State.canceled);
			orderDao.update(order);

			history = new History();
			history.setOperation(Operation.cancel);
			history.setOrderid(order.getId());
			historyDao.save(history);
			
			Worker worker=workerDao.get(order.getWorkerid());
			OrderDetail orderDetail=orderDao.getOrderDetail(orderid);
			SMS.send(worker.getCellnum(), SMS.toWorkerContent(Operation.cancel, orderDetail));
			
			return true;
		}
		return false;
	}

	public boolean deleteOrder(Integer orderid) {
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

	/**
	 * 与操作历史相关的操作
	 */
	public List<History> listHistoryByOrderid(Integer orderid) {
		return historyDao.findByOrderid(orderid);
	}
}
