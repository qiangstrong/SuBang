package com.subang.service;

import java.util.ArrayList;
import java.util.List;

import javax.xml.registry.DeleteException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.subang.bean.Area;
import com.subang.bean.OrderDetail;
import com.subang.domain.Addr;
import com.subang.domain.History;
import com.subang.domain.Laundry;
import com.subang.domain.Order;
import com.subang.domain.History.Operation;
import com.subang.domain.Order.State;
import com.subang.domain.User;
import com.subang.domain.Worker;
import com.subang.exception.BackException;
import com.subang.utility.WebConstant;

/**
 * @author Qiang 后台对用户，地址，订单，操作历史的管理
 */
@Service
public class BackUserService extends BaseService {

	/**
	 * 与用户相关的操作
	 */
	public List<User> searchUser(int type, Object arg) {
		List<User> users = null;
		switch (type) {
		case WebConstant.SEARCH_NAME:
			users = userDao.findByNickname((String) arg);
			break;
		case WebConstant.SEARCH_CELLNUM:
			users = userDao.findByCellnum((String) arg);
			break;
		}
		return users;
	}

	public List<User> listUser() {
		return userDao.findAll();
	}

	public void modifyUser(User user) {
		userDao.update(user);
	}

	public void deleteUser(Integer userid) {
		userDao.delete(userid);
	}

	/**
	 * 与用户地址相关的操作
	 */
	public List<Addr> listAddrByUserid(Integer userid) {
		return addrDao.findByUserid(userid);
	}

	public void deleteAddr(Integer addrid) throws BackException {
		Addr addr = addrDao.get(addrid);
		User user = userDao.get(addr.getUserid());

		if (orderDao.findNumByAddrid(addrid) != 0) {
			throw new BackException("请先删除引用改地址的订单，再尝试删除此地址。");
		}
		addrDao.delete(addrid);

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
	public List<OrderDetail> searchOrder(int type, Object arg) {
		List<OrderDetail> orderDetails = null;
		switch (type) {
		case WebConstant.ORDER_SEARCH_ORDERNO:
			orderDetails = orderDao.findOrderDetailByOrderno((String) arg);
			break;
		case WebConstant.ORDER_SEARCH_USER_NICKNAME:
			orderDetails=new ArrayList<OrderDetail>();
			List<User> users1=userDao.findByNickname((String) arg);
			for(User user:users1){
				orderDetails.addAll(orderDao.findOrderDetailByUserid(user.getId()));
			}
			break;
		case WebConstant.ORDER_SEARCH_USER_CELLNUM:
			orderDetails=new ArrayList<OrderDetail>();
			List<User> users2=userDao.findByCellnum((String) arg);
			for(User user:users2){
				orderDetails.addAll(orderDao.findOrderDetailByUserid(user.getId()));
			}
			break;
		case WebConstant.ORDER_SEARCH_LAUNDRY_NAME:
			orderDetails=new ArrayList<OrderDetail>();
			List<Laundry> laundrys=laundryDao.findByName((String) arg);
			for(Laundry laundry:laundrys){
				orderDetails.addAll(orderDao.findOrderDetailByLaundryid(laundry.getId()));
			}
			break;
		}
		return orderDetails;
	}
	
	public List<OrderDetail> listOrderByState(State orderState){
		return orderDao.findOrderDetailByState(orderState);
	}
	
	public void assignOrderLaundry(Order order, Integer laundryid){
		order.setLaundryid(laundryid);
		orderDao.update(order);
	}
	
	public void finishOrder(List<Order> orders){
		History history=new History();
		history.setOperation(Operation.finish);
		for(Order order:orders){
			if(order.getStateEnum()==State.fetched){
				order.setState(State.finished);
				history.setOrderid(order.getId());
				orderDao.update(order);
				historyDao.save(history);
			}
		}
	}
	
	public void cancelOrder(List<Order> orders){
		History history=new History();
		history.setOperation(Operation.cancel);
		for(Order order:orders){
			if(order.getStateEnum()==State.accepted){
				order.setState(State.canceled);
				history.setOrderid(order.getId());
				orderDao.update(order);
				historyDao.save(history);
			}
		}
	}
	
	public void modifyOrder(Order order){
		orderDao.update(order);
	}
	
	public void deleteOrder(Integer orderid){
		Order order=orderDao.get(orderid);
		orderDao.delete(orderid);
		Addr addr=addrDao.get(order.getAddrid());
		if(!addr.isValid()&&orderDao.findNumByAddrid(addr.getId()) == 0){
			addrDao.delete(addr.getId());
		}		
	}
	
	/**
	 * 与操作历史相关的操作
	 */
	public List<History> listHistoryByOrderid(Integer orderid){
		return historyDao.findByOrderid(orderid);
	}
}
