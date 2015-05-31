package com.subang.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.subang.bean.OrderDetail;
import com.subang.domain.Addr;
import com.subang.domain.History;
import com.subang.domain.Info;
import com.subang.domain.Order;
import com.subang.domain.Region;
import com.subang.domain.User;
import com.subang.domain.History.Operation;
import com.subang.domain.Order.State;
import com.subang.domain.Worker;
import com.subang.exception.BackException;
import com.subang.util.Common;
import com.subang.util.SMS;
import com.subang.util.WebConstant;

@Service
public class FrontUserService extends CommUserService {

	/**
	 * 与用户相关的操作
	 */
	public User getUserByOpenid(String openid) {
		return userDao.findByOpenid(openid);
	}

	public void addUser(User userFront) {
		User userBack = getUserByOpenid(userFront.getOpenid());
		if (userBack == null) {
			userDao.save(userFront);
		} else {
			userBack.setValid(true);
			userBack.setNickname(userFront.getNickname());
			userBack.setSex(userFront.getSex());
			userBack.setCountry(userFront.getCountry());
			userBack.setProvince(userFront.getProvince());
			userBack.setCity(userFront.getCity());
			userDao.update(userBack);
		}
	}

	/**
	 * 与用户地址相关的操作
	 */
	public void addAddr(Addr addr) {
		addrDao.save(addr);
	}

	public void deleteAddr(Integer addrid) {
		Addr addr = addrDao.get(addrid);
		User user = userDao.get(addr.getUserid());
		if (orderDao.findNumByAddrid(addrid) == 0) {
			addrDao.delete(addrid);
		} else {
			addr.setValid(false);
			addrDao.update(addr);
		}
		if (user.getAddrid() == addrid) {
			List<Addr> addrs = addrDao.findByUserid(user.getId());
			if (!addrs.isEmpty()) {
				user.setAddrid(addrs.get(0).getId());
				userDao.update(user);
			}
		}
	}

	/**
	 * 与订单相关的操作
	 */
	public List<Order> searchOrderByState(int stateType) {
		List<Order> orders = new ArrayList<Order>();
		switch (stateType) {
		case WebConstant.ORDER_STATE_UNDONE:
			orders.addAll(orderDao.findByState(State.accepted));
			orders.addAll(orderDao.findByState(State.fetched));
			break;
		case WebConstant.ORDER_STATE_DONE:
			orders.addAll(orderDao.findByState(State.finished));
			orders.addAll(orderDao.findByState(State.canceled));
			break;
		}
		return orders;
	}

	public void addOrder(Order order) {
		order.setState(State.accepted);
		order.setOrderno(Common.getOrderno());

		Addr addr = addrDao.get(order.getAddrid());
		Region region = regionDao.get(addr.getRegionid());
		Integer workerid = region.getWorkerid();
		if (workerid == null) {
			List<Worker> coreWorkers = workerDao.findByCore();
			workerid = coreWorkers.get(Common.random.nextInt(coreWorkers.size())).getId();
		}
		order.setWorkerid(workerid);
		orderDao.save(order);

		order = orderDao.getByOrderno(order.getOrderno());

		History history = new History();
		history.setOperation(Operation.accept);
		history.setOrderid(order.getId());
		historyDao.save(history);

		Worker worker = workerDao.get(order.getWorkerid());
		OrderDetail orderDetail = orderDao.getOrderDetail(order.getId());
		SMS.send(worker.getCellnum(), SMS.toWorkerContent(Operation.accept, orderDetail));
	}

	public boolean fetchOrder(Integer orderid) {
		Order order = orderDao.get(orderid);
		if (order.getStateEnum()==State.accepted) {
			order.setState(State.fetched);
			orderDao.update(order);

			History history = new History();
			history.setOperation(Operation.fetch);
			history.setOrderid(order.getId());
			historyDao.save(history);
			return true;
		}
		return false;
	}
	
	/**
	 * 与产品运营相关的操作
	 */
	public Info getInfo(){
		return infoDao.findALL().get(0);
	}
}
