package com.subang.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import weixin.popular.api.PayMchAPI;
import weixin.popular.bean.pay.PayAppRequest;
import weixin.popular.bean.paymch.Transfers;
import weixin.popular.bean.paymch.TransfersResult;
import weixin.popular.bean.paymch.Unifiedorder;
import weixin.popular.bean.paymch.UnifiedorderResult;
import weixin.popular.util.PayUtil;
import weixin.popular.util.StringUtils;

import com.alipay.api.PayAPI;
import com.alipay.bean.AlipayOrder;
import com.subang.bean.GeoLoc;
import com.subang.bean.Pagination;
import com.subang.bean.PayArg;
import com.subang.bean.PrepayResult;
import com.subang.bean.Result;
import com.subang.bean.SearchArg;
import com.subang.domain.Addr;
import com.subang.domain.Balance;
import com.subang.domain.Balance.BalanceType;
import com.subang.domain.City;
import com.subang.domain.Location;
import com.subang.domain.Notice.Code;
import com.subang.domain.Order;
import com.subang.domain.Order.OrderType;
import com.subang.domain.Order.State;
import com.subang.domain.Payment;
import com.subang.domain.Payment.PayType;
import com.subang.domain.Rebate;
import com.subang.domain.Ticket;
import com.subang.domain.TicketCode;
import com.subang.domain.TicketType;
import com.subang.domain.User;
import com.subang.tool.SuException;
import com.subang.util.ComUtil;
import com.subang.util.LocUtil;
import com.subang.util.Setting;
import com.subang.util.StratUtil;
import com.subang.util.SuUtil;
import com.subang.util.Validator;
import com.subang.util.WebConst;

@Service
public class UserService extends BaseService {

	/**
	 * 用户
	 */
	// 后台查找用户
	public List<User> searchUser(SearchArg searchArg, Pagination pagination) {
		List<User> users = null;
		switch (searchArg.getType()) {
		case WebConst.SEARCH_NULL:
			users = new ArrayList<User>();
			break;
		case WebConst.SEARCH_ALL:
			users = userDao.findAll(pagination);
			break;
		case WebConst.SEARCH_CELLNUM:
			users = userDao.findByCellnum(searchArg.getArg(), pagination);
			break;
		case WebConst.SEARCH_NO:
			users = userDao.findByUserno(searchArg.getArg(), pagination);
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

	// 客户端修改用户信息
	public void modifyUser(User user) throws SuException {
		try {
			userDao.update(user);
		} catch (DuplicateKeyException e) {
			throw new SuException("手机号码不能相同。");
		}
	}

	// 管理端修改用户信息
	public void modifyUserBack(User user) throws SuException {
		User user_old = userDao.get(user.getId());
		// 如果用户被其他管理人员删除，修改操作已经没有意义，直接返回
		if (user_old == null) {
			return;
		}
		if (user.getUserno() != null && user.getUserno().length() == 0) {
			user.setUserno(null);
		}
		if (user.getUserno() != null) {
			Result result = Validator.validUserno(user.getUserno());
			if (!result.isOk()) {
				throw new SuException(result.getMsg());
			}
		}
		user_old.setUserno(user.getUserno());
		user_old.setPassword(user.getPassword());
		user_old.setScore(user.getScore());
		try {
			userDao.update(user_old);
		} catch (DuplicateKeyException e) {
			throw new SuException("会员号不能相同。");
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
	public Addr addAddr(Addr addr) {
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
		return addr;
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
	public void updateLocation(Integer userid, Location location_new) {
		location_new.setTime(new Timestamp(System.currentTimeMillis()));
		GeoLoc geoLoc = LocUtil.getGeoLoc(location_new);
		City city = null;
		if (geoLoc != null) {
			city = ComUtil.getFirst(cityDao.findByName(geoLoc.getCity()));
		}

		Location location_old = ComUtil.getFirst(locationDao.findByUserid(userid));

		if (location_old != null) {
			location_old.setLatitude(location_new.getLatitude());
			location_old.setLongitude(location_new.getLongitude());
			location_old.setTime(location_new.getTime());
			if (city != null) {
				location_old.setCityid(city.getId());
			}
			locationDao.update(location_old);
		} else {
			location_old = new Location();
			location_old.setLatitude(location_new.getLatitude());
			location_old.setLongitude(location_new.getLongitude());
			location_old.setTime(location_new.getTime());
			if (city != null) {
				location_old.setCityid(city.getId());
			}
			location_old.setUserid(userid);
			locationDao.save(location_old);
		}
	}

	/**
	 * 用户卡券
	 */
	// 用户购买卡券。对于参加活动领取的卡券，其实现放到活动管理部分
	public void addTicketByScore(Integer userid, Integer ticketTypeid) throws SuException {
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
		userDao.update(user);
	}

	// 用户使用优惠码兑换卡券
	public void addTicketByCode(Integer userid, String codeno) throws SuException {
		TicketCode ticketCode = ticketCodeDao.getByCodeno(codeno);
		if (ticketCode == null || !ticketCode.isUsable()) {
			throw new SuException("优惠码已失效");
		}
		if (!ticketCode.getValid()) {
			throw new SuException("优惠码已兑换");
		}

		TicketType ticketType = ticketTypeDao.get(ticketCode.getTicketTypeid());
		Ticket ticket = new Ticket();
		ticket.setDeadline(ticketType.getDeadline());
		ticket.setUserid(userid);
		ticket.setTicketTypeid(ticketType.getId());
		ticketDao.save(ticket);

		ticketCode.setValid(false);
		ticketCodeDao.update(ticketCode);
	}

	public void addTicketBack(Ticket ticket) {
		if (ticket.getDeadline() == null) {
			TicketType ticketType = ticketTypeDao.get(ticket.getTicketTypeid());
			ticket.setDeadline(ticketType.getDeadline());
		}
		ticketDao.save(ticket);
	}

	// 管理员删除用户的卡券。除非特殊情况，管理员不应执行此操作
	public void deleteTickets(List<Integer> ticketids) {
		for (Integer ticketid : ticketids) {
			ticketDao.delete(ticketid);
		}
	}

	/**
	 * 余额
	 */
	// 由上层传递money和userid
	private Balance addBalance(Balance balance) {
		balance.setState(Order.State.accepted);
		boolean flag;
		do {
			try {
				balance.setOrderno(StratUtil.getOrderno(OrderType.balance));
				balanceDao.save(balance);
				flag = false;
			} catch (DuplicateKeyException e) {
				flag = true;
			}
		} while (flag);
		balance = balanceDao.getByOrderno(balance.getOrderno());
		return balance;
	}

	private PrepayResult payByWeixin(PayArg payArg, HttpServletRequest request) {
		PrepayResult result = new PrepayResult();

		Balance balance = balanceDao.getDetail(payArg.getOrderid());
		User user = userDao.get(balance.getUserid());
		Payment payment = paymentDao.getByOrderno(balance.getOrderno());
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
		}

		if (prepay_id == null) {

			Unifiedorder unifiedorder = new Unifiedorder();
			switch (payArg.getClientEnum()) {
			case weixin: {
				unifiedorder.setTrade_type("JSAPI");
				unifiedorder.setOpenid(user.getOpenid());
				break;
			}
			case user: {
				unifiedorder.setTrade_type("APP");
				break;
			}
			}

			unifiedorder.setAppid(appid);
			unifiedorder.setMch_id(mch_id);
			unifiedorder.setNonce_str(StringUtils.getRandomStringByLength(32));
			unifiedorder.setBody("用户充值");
			unifiedorder.setOut_trade_no(balance.getOrderno());
			Integer money = (int) (balance.getMoney() * 100);
			unifiedorder.setTotal_fee(money.toString());
			unifiedorder.setSpbill_create_ip(request.getRemoteAddr());
			unifiedorder.setNotify_url(SuUtil.getBasePath(request) + "weixin/balance/wxpay.html");

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
		Balance balance = balanceDao.getDetail(payArg.getOrderid());

		String pid = SuUtil.getAppProperty("pid_ali");
		String prikey = SuUtil.getAppProperty("prikey_ali");

		AlipayOrder alipayOrder = new AlipayOrder();
		alipayOrder.setPartner(pid);
		alipayOrder.setSeller_id(pid);
		alipayOrder.setNotify_url(SuUtil.getBasePath(request) + "weixin/balance/alipay.html");
		alipayOrder.setOut_trade_no(balance.getOrderno());
		alipayOrder.setSubject("recharge");
		alipayOrder.setBody("recharge");
		alipayOrder.setTotal_fee(balance.getMoney().toString());

		Object arg = null;
		switch (payArg.getClientEnum()) {
		case weixin: {
			alipayOrder
					.setReturn_url(SuUtil.getBasePath(request) + "weixin/balance/alireturn.html");
			alipayOrder.setShow_url(SuUtil.getBasePath(request) + "weixin/user/index.html");
			arg = PayAPI.generatePayWapRequest(alipayOrder, prikey);
			break;
		}
		case user: {
			arg = PayAPI.generatePayMobileRequest(alipayOrder, prikey);
			break;
		}
		}

		Payment payment = paymentDao.getByOrderno(balance.getOrderno());
		payment.setType(PayType.alipay);
		paymentDao.update(payment);
		result.setCode(PrepayResult.Code.conti);
		result.setArg(arg);
		return result;
	}

	private PrepayResult payByCash(PayArg payArg) {
		PrepayResult result = new PrepayResult();
		Balance balance = balanceDao.getDetail(payArg.getOrderid());
		Payment payment = paymentDao.getByOrderno(balance.getOrderno());
		payment.setType(PayType.cash);
		paymentDao.update(payment);
		payBalance(balance.getOrderno());
		result.setCode(PrepayResult.Code.succ);
		return result;
	}

	private PrepayResult payByExpense(PayArg payArg) {
		PrepayResult result = new PrepayResult();
		Balance balance = balanceDao.getDetail(payArg.getOrderid());
		User user = userDao.get(balance.getUserid());
		if (-balance.getMoney() > user.getMoney()) {
			result.setCode(PrepayResult.Code.fail);
			result.setMsg("余额不足。");
			return result;
		}
		Payment payment = paymentDao.getByOrderno(balance.getOrderno());
		payment.setType(PayType.expense);
		paymentDao.update(payment);
		payBalance(balance.getOrderno());
		result.setCode(PrepayResult.Code.succ);
		return result;
	}

	private PrepayResult payByShare(PayArg payArg) {
		PrepayResult result = new PrepayResult();
		Balance balance = balanceDao.getDetail(payArg.getOrderid());
		Payment payment = paymentDao.getByOrderno(balance.getOrderno());
		payment.setType(PayType.share);
		paymentDao.update(payment);
		payBalance(balance.getOrderno());
		result.setCode(PrepayResult.Code.succ);
		return result;
	}

	public PrepayResult prepay(PayArg payArg, Integer userid, HttpServletRequest request) {

		// 生成本地订单
		Balance balance = new Balance();
		balance.setType(BalanceType.balance);
		balance.setUserid(userid);
		balance.setMoney(payArg.getMoney());
		balance = addBalance(balance);
		payArg.setOrderid(balance.getId());

		PrepayResult result;
		switch (payArg.getPayTypeEnum()) {
		case weixin: {
			result = payByWeixin(payArg, request);
			break;
		}
		case alipay: {
			result = payByAli(payArg, request);
			break;
		}
		case cash: {
			result = payByCash(payArg);
			break;
		}
		case expense: {
			result = payByExpense(payArg);
			break;
		}
		case share: { // 传入prepay的payArg需要payType、money
			result = payByShare(payArg);
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

	public void payBalance(String orderno) {
		Balance balance = balanceDao.getByOrderno(orderno);
		if (balance.getStateEnum() == State.paid) {
			return;
		}
		balance.setState(Order.State.paid);
		balance.setTime(new Timestamp(System.currentTimeMillis()));
		balanceDao.update(balance);

		// 计算折扣
		double benefit = 0.0;
		Payment payment = paymentDao.getByOrderno(balance.getOrderno());
		if (payment.getTypeEnum() == PayType.weixin || payment.getTypeEnum() == PayType.alipay
				|| payment.getTypeEnum() == PayType.cash) {
			benefit = calcBenefit(balance);
		}

		User user = userDao.get(balance.getUserid());
		user.setMoney(user.getMoney() + balance.getMoney() + benefit);
		userDao.update(user);
	}

	// 计算折扣
	private double calcBenefit(Balance balance) {
		double benefit = 0.0;
		List<Rebate> rebates = rebateDao.findAll();
		for (Rebate rebate : rebates) {
			if (ComUtil.equal(balance.getMoney(), rebate.getMoney())) {
				benefit = rebate.getBenefit();
				break;
			}
		}

		if (!ComUtil.equal(benefit, 0.0)) {
			// 生成一个新的balance已记录返现
			Balance benifitBalance = new Balance();
			balance.setType(BalanceType.balance);
			benifitBalance.setUserid(balance.getUserid());
			benifitBalance.setMoney(benefit);
			benifitBalance = addBalance(benifitBalance);
			benifitBalance.setState(Order.State.paid);
			benifitBalance.setTime(new Timestamp(System.currentTimeMillis()));
			balanceDao.update(benifitBalance);

			Payment payment = paymentDao.getByOrderno(benifitBalance.getOrderno());
			payment.setType(PayType.rebate);
			paymentDao.update(payment);
		}

		return benefit;
	}

	/*
	 * 收益
	 */
	// 下单产生交易额时，计算提成
	public void addSalary(Integer userid, double money) {
		Balance balance = new Balance();
		balance.setType(BalanceType.salary);
		balance.setUserid(userid);
		balance.setMoney(money);
		balance = addBalance(balance);
		balance.setState(Order.State.paid);
		balance.setTime(new Timestamp(System.currentTimeMillis()));
		balanceDao.update(balance);

		Payment payment = paymentDao.getByOrderno(balance.getOrderno());
		payment.setType(PayType.promote);
		paymentDao.update(payment);

		User user = userDao.get(userid);
		user.setSalary(user.getSalary() + balance.getMoney());
		userDao.update(user);
	}

	// 用户提现；只能全部提现；只能提现到微信；只有用户在微信公众平台登录过，才能提现
	public Result subSalary(Integer userid, HttpServletRequest request) {
		Result result = new Result();
		User user = userDao.get(userid);
		if (user.getOpenid() == null) {
			result.setCode(Result.ERR);
			result.setMsg("您的账号尚未在微信公众平台登录过。暂时无法提现。");
			return result;
		}
		if (user.getSalary() < Setting.salaryLimit) {
			result.setCode(Result.ERR);
			result.setMsg("收益满" + Setting.salaryLimit + "元才可以提现");
			return result;
		}

		// 生成本地订单
		Balance balance = new Balance();
		balance.setType(BalanceType.salary);
		balance.setUserid(userid);
		balance.setMoney(-user.getSalary());
		balance = addBalance(balance);

		// 调用微信接口转账
		String appid = SuUtil.getAppProperty("appid");
		String mch_id = SuUtil.getAppProperty("mch_id");
		String apikey = SuUtil.getAppProperty("apikey");

		Transfers transfers = new Transfers();
		transfers.setMch_appid(appid);
		transfers.setMchid(mch_id);
		transfers.setNonce_str(StringUtils.getRandomStringByLength(32));
		transfers.setPartner_trade_no(balance.getOrderno());
		transfers.setOpenid(user.getOpenid());
		transfers.setCheck_name("NO_CHECK");
		Integer money = (int) (user.getSalary() * 100);
		transfers.setAmount(money.toString());
		transfers.setDesc("收益提现");
		transfers.setSpbill_create_ip(request.getRemoteAddr());

		TransfersResult transfersResult = PayMchAPI.mmpaymkttransfersPromotionTransfers(transfers,
				apikey);

		// 对错误进行记录
		if (!transfersResult.getReturn_code().equals("SUCCESS")) {
			LOG.error("错误码:" + transfersResult.getReturn_code() + "; 错误信息:"
					+ transfersResult.getReturn_msg());
			SuUtil.notice(Code.money, transfersResult.getReturn_msg());
			result.setCode(Result.ERR);
			result.setMsg(transfersResult.getReturn_msg());
			return result;
		}

		if (!transfersResult.getResult_code().equals("SUCCESS")) {
			LOG.error("错误码:" + transfersResult.getErr_code() + "; 错误信息:"
					+ transfersResult.getErr_code_des());
			SuUtil.notice(Code.money, transfersResult.getErr_code_des());
			result.setCode(Result.ERR);
			result.setMsg(transfersResult.getErr_code_des());
			return result;
		}

		// 更新本地业务状态
		balance.setState(Order.State.paid);
		balance.setTime(new Timestamp(System.currentTimeMillis()));
		balanceDao.update(balance);

		Payment payment = paymentDao.getByOrderno(balance.getOrderno());
		payment.setType(PayType.weixin);
		paymentDao.update(payment);

		user.setSalary(0.0);
		userDao.update(user);

		result.setCode(Result.OK);
		return result;
	}

	/**
	 * 自动执行
	 */
	public void reset() {
		userDao.resetLogin();
	}
}
