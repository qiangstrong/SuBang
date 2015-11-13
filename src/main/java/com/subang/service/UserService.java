package com.subang.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.subang.bean.SearchArg;
import com.subang.domain.Addr;
import com.subang.domain.Location;
import com.subang.domain.Ticket;
import com.subang.domain.TicketType;
import com.subang.domain.User;
import com.subang.exception.SuException;
import com.subang.util.WebConst;

@Service
public class UserService extends BaseService {

	/**
	 * 用户
	 */
	// 后台查找用户
	public List<User> searchUser(SearchArg searchArg) {
		List<User> users = null;
		switch (searchArg.getType()) {
		case WebConst.SEARCH_NULL:
			users = new ArrayList<User>();
			break;
		case WebConst.SEARCH_ALL:
			users = userDao.findAll();
			break;
		case WebConst.SEARCH_CELLNUM:
			users = userDao.findByCellnum(searchArg.getArg());
			break;
		default:
			users = new ArrayList<User>();
		}
		return users;
	}

	// 检查手机号码是否被注册过
	public boolean checkCellnum(String cellnum) {
		int count = userDao.countCellnum(cellnum);
		if (count == 0) {
			return true;
		}
		return false;
	}

	// 用户注册
	public void addUser(User user) throws SuException {
		user.setLogin(false); // 涉及积分的计算，需要思考一下
		user.setScore(0);
		user.setMoney(0.0);
		try {
			userDao.save(user);
		} catch (DuplicateKeyException e) {
			throw new SuException("手机号码不能相同。");
		}
	}

	public void modifyUser(User user) throws SuException {
		try {
			userDao.update(user);
		} catch (DuplicateKeyException e) {
			throw new SuException("手机号码不能相同。");
		}
	}

	// 后台删除用户
	public void deleteUsers(List<Integer> userids) {
		for (Integer userid : userids) {
			userDao.delete(userid);
		}
	}

	/**
	 * 用户地址
	 */
	// 用户添加地址
	public void addAddr(Addr addr) {
		addr.setValid(true);
		addrDao.save(addr);
		User user = userDao.get(addr.getUserid());
		if (user.getAddrid() == null) {
			List<Addr> addrs = addrDao.findByUserid(user.getId());
			if (!addrs.isEmpty()) {
				user.setAddrid(addrs.get(0).getId());
				userDao.update(user);
			}
		}
	}

	// 管理员删除地址
	public void deleteAddrs(List<Integer> addrids) throws SuException {
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
			throw new SuException("部分地址没有成功删除。请先删除引用改地址的订单，再尝试删除地址。");
		}
	}

	// 用户删除自己的地址
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
			} else {
				user.setAddrid(null);
			}
			userDao.update(user);
		}
	}

	/**
	 * 用户位置
	 */
	public Location getLocationByUserid(Integer userid) {
		List<Location> locations = locationDao.findByUserid(userid);
		if (locations.isEmpty()) {
			return null;
		}
		return locations.get(0);
	}

	public void updateLocation(Integer userid, Location location_new) {
		Location location_old = getLocationByUserid(userid);
		if (location_old != null) {
			location_old.setLatitude(location_new.getLatitude());
			location_old.setLongitude(location_new.getLongitude());
			locationDao.update(location_old);
		} else {
			location_old = new Location();
			location_old.setLatitude(location_new.getLatitude());
			location_old.setLongitude(location_new.getLongitude());
			location_old.setUserid(userid);
			locationDao.save(location_old);
		}
	}

	/**
	 * 用户卡券
	 */
	// 用户购买卡券。对于参加活动领取的卡券，其实现放到活动管理部分
	public void addTicket(Integer userid, Integer ticketTypeid) throws SuException {
		User user = userDao.get(userid);
		TicketType ticketType = ticketTypeDao.get(ticketTypeid);
		if (user.getScore() < ticketType.getScore()) {
			throw new SuException("积分不足");
		}
		Ticket ticket = new Ticket();
		ticket.setDeadline(ticketType.getDeadline());
		ticket.setUserid(userid);
		ticket.setTicketTypeid(ticketTypeid);
		ticketDao.save(ticket);
		user.setScore(user.getScore() - ticketType.getScore());
	}

	// 管理员删除用户的卡券。除非特殊情况，管理员不应执行此操作
	public void deleteTickets(List<Integer> ticketids) {
		for (Integer ticketid : ticketids) {
			ticketDao.delete(ticketid);
		}
	}

}
