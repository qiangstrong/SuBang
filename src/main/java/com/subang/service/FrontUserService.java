package com.subang.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import weixin.popular.api.PayMchAPI;
import weixin.popular.bean.paymch.Unifiedorder;
import weixin.popular.bean.paymch.UnifiedorderResult;
import weixin.popular.util.StringUtils;

import com.subang.bean.AddrData;
import com.subang.bean.Area;
import com.subang.bean.GeoLoc;
import com.subang.bean.OrderDetail;
import com.subang.domain.Addr;
import com.subang.domain.City;
import com.subang.domain.District;
import com.subang.domain.History;
import com.subang.domain.History.Operation;
import com.subang.domain.Info;
import com.subang.domain.Location;
import com.subang.domain.Order;
import com.subang.domain.Order.State;
import com.subang.domain.Region;
import com.subang.domain.User;
import com.subang.domain.Worker;
import com.subang.exception.BackException;
import com.subang.util.Common;
import com.subang.util.LocUtil;
import com.subang.util.SmsUtil;
import com.subang.util.SmsUtil.SmsType;
import com.subang.util.StratUtil;
import com.subang.util.WebConst;

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
	 * 与用户位置相关的操作
	 */
	public Location getLocation(Integer locationid) {
		return locationDao.get(locationid);
	}

	public Location getLocationByUserid(Integer userid) {
		List<Location> locations = locationDao.findByUserid(userid);
		if (locations.isEmpty()) {
			return null;
		}
		return locations.get(0);
	}

	public void addLocation(Location location) {
		locationDao.save(location);
	}

	public void modifyLocation(Location location) {
		locationDao.update(location);
	}

	/**
	 * 与用户地址相关的操作
	 */
	public void addAddr(Addr addr) {
		addr.setValid(true);
		addrDao.save(addr);
		User user = userDao.get(addr.getUserid());
		if (user.getAddrid() == null) {
			user.setAddrid(addr.getId());
			userDao.update(user);
		}
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
	public List<Order> searchOrderByUseridAndState(Integer userid, int stateType) {
		List<Order> orders = new ArrayList<Order>();
		switch (stateType) {
		case WebConst.ORDER_STATE_UNDONE:
			orders.addAll(orderDao.findByUseridAndState(userid, State.accepted));
			orders.addAll(orderDao.findByUseridAndState(userid, State.fetched));
			orders.addAll(orderDao.findByUseridAndState(userid, State.paid));
			break;
		case WebConst.ORDER_STATE_DONE:		
			orders.addAll(orderDao.findByUseridAndState(userid, State.finished));
			orders.addAll(orderDao.findByUseridAndState(userid, State.canceled));
			break;
		}
		return orders;
	}

	public void addOrder(Order order) {
		order.setState(State.accepted);

		Addr addr = addrDao.get(order.getAddrid());
		Region region = regionDao.get(addr.getRegionid());
		Integer workerid = region.getWorkerid();
		if (workerid == null) {
			List<Worker> coreWorkers = workerDao.findByCore();
			workerid = coreWorkers.get(Common.random.nextInt(coreWorkers.size())).getId();
		}
		order.setOrderno(StratUtil.getOrderno(workerid));
		order.setWorkerid(workerid);
		orderDao.save(order);

		order = orderDao.getByOrderno(order.getOrderno());

		History history = new History();
		history.setOperation(Operation.accept);
		history.setOrderid(order.getId());
		historyDao.save(history);

		User user = userDao.get(order.getUserid());
		user.setAddrid(order.getAddrid());
		userDao.update(user);

		Worker worker = workerDao.get(order.getWorkerid());
		OrderDetail orderDetail = orderDao.getOrderDetail(order.getId());
		SmsUtil.send(worker.getCellnum(), SmsType.accept, SmsUtil.toWorkerContent(orderDetail));
	}

	public boolean fetchOrder(Integer orderid) {
		Order order = orderDao.get(orderid);
		if (order.getStateEnum() == State.accepted) {
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
	
	public void pay(Integer orderid) throws BackException {
		History history = null;
		Order order=orderDao.get(orderid);
		if (order.getPrice()!=null) {
			order.setState(State.paid);
			orderDao.update(order);
			
			history = new History();
			history.setOrderid(order.getId());
			history.setOperation(Operation.pay);
			historyDao.save(history);
			
		}else {
			throw new BackException("支付失败。订单价格未指定。");
		}
	}
	
	public void pay(String orderno) throws BackException {
		pay(orderDao.getByOrderno(orderno).getId());
	}
	
	
	/**
	 * 生成预支付id，管理员已经为订单指定价格。这点由前端保证。
	 */
	public String getPrepay_id(User user, Order order, HttpServletRequest request) {
		String prepay_id=order.getPrepay_id();
		if(prepay_id!=null){
			return prepay_id;
		}
		
		Unifiedorder unifiedorder = new Unifiedorder();
		unifiedorder.setAppid(Common.getProperty("appid"));
		unifiedorder.setMch_id(Common.getProperty("mch_id"));
		unifiedorder.setNonce_str(StringUtils.getRandomStringByLength(32));
		unifiedorder.setBody("订单付款");
		unifiedorder.setOut_trade_no(order.getOrderno());
		Integer price = (int) (order.getPrice() * 100);
		unifiedorder.setTotal_fee(price.toString());
		unifiedorder.setSpbill_create_ip(request.getRemoteAddr());
		unifiedorder.setNotify_url(Common.getProperty("notify_url"));
		unifiedorder.setTrade_type("JSAPI");
		unifiedorder.setOpenid(user.getOpenid());

		UnifiedorderResult result = PayMchAPI.payUnifiedorder(unifiedorder, Common.getProperty("apikey"));
		if (!result.getReturn_code().equals("SUCCESS")) {
			LOG.error("错误码:" + result.getReturn_code() + "; 错误信息:" + result.getReturn_msg());
			return null;
		}
		
		if (!result.getResult_code().equals("SUCCESS")) {
			LOG.error("错误码:" + result.getErr_code()+ "; 错误信息:" + result.getErr_code_des());
			return null;
		}
		
		order.setPrepay_id(result.getPrepay_id());
		orderDao.update(order);
		return result.getPrepay_id();
	}
	
	/**
	 * 与区域相关的操作
	 */
	public List<City> listCity() {
		return cityDao.findAllValid();
	}

	public List<District> listDistrictByCityid(Integer cityid) {
		return districtDao.findValidByCityid(cityid);
	}

	public List<Region> listRegionByDistrictid(Integer districtid) {
		return regionDao.findByDistrictid(districtid);
	}
	
	public AddrData getAddrData(User user){
		AddrData addrData=new AddrData();
		
		List<Location> locations=locationDao.findByUserid(user.getId());
		Location location=Common.getFirst(locations);
		GeoLoc geoLoc=LocUtil.getGeoLoc(location);
		
		addrData.setCitys(cityDao.findAllValid());
		addrData.setDefaultRegionid(null);
				
		if (geoLoc==null) {
			addrData.setDefaultCityid(null);
			addrData.setDistricts(districtDao.findValidByCityid(addrData.getCitys().get(0).getId()));
			addrData.setDefaultDistrictid(null);
			addrData.setRegions(regionDao.findByDistrictid(addrData.getDistricts().get(0).getId()));
			addrData.setDetail(null);
			return addrData;
		}
		
		City defaultCity=Common.getFirst(cityDao.findValidByName(geoLoc.getCity()));
		if (defaultCity==null) {
			addrData.setDefaultCityid(null);
			addrData.setDistricts(districtDao.findValidByCityid(addrData.getCitys().get(0).getId()));
			addrData.setDefaultDistrictid(null);
			addrData.setRegions(regionDao.findByDistrictid(addrData.getDistricts().get(0).getId()));
			addrData.setDetail(geoLoc.getDetail());
			return addrData;
		}
		
		addrData.setDefaultCityid(defaultCity.getId());
		addrData.setDistricts(districtDao.findValidByCityid(addrData.getDefaultCityid()));
		District defaultDistrict=Common.getFirst(districtDao.findValidByCityidAndName(addrData.getDefaultCityid(), geoLoc.getDistrict()));
		if (defaultDistrict==null) {
			addrData.setDefaultDistrictid(null);
			addrData.setRegions(regionDao.findByDistrictid(addrData.getDistricts().get(0).getId()));
			addrData.setDetail(geoLoc.getDetail());
			return addrData;
		}
		
		addrData.setDefaultDistrictid(defaultDistrict.getId());
		addrData.setRegions(regionDao.findByDistrictid(addrData.getDefaultDistrictid()));
		addrData.setDetail(geoLoc.getDetail());
		return addrData;
	}
	
	public AddrData getAddrData(Integer regionid){
		AddrData addrData=new AddrData();		
		Area area=regionDao.getAreaByRegionid(regionid);
		addrData.setDefaultCityid(area.getCityid());
		addrData.setDefaultDistrictid(area.getDistrictid());
		addrData.setDefaultRegionid(area.getRegionid());
		addrData.setCitys(cityDao.findAllValid());
		addrData.setDistricts(districtDao.findValidByCityid(area.getCityid()));
		addrData.setRegions(regionDao.findByDistrictid(area.getDistrictid()));
		return addrData;
	}

	/**
	 * 与产品运营相关的操作
	 */
	public Info getInfo() {
		return infoDao.findALL().get(0);
	}
}
