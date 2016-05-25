package com.subang.controller.app;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.subang.bean.AddrData;
import com.subang.bean.AddrDetail;
import com.subang.bean.Identity;
import com.subang.bean.PayArg;
import com.subang.bean.PrepayResult;
import com.subang.bean.PrepayResult.Code;
import com.subang.bean.Result;
import com.subang.bean.TicketDetail;
import com.subang.controller.BaseController;
import com.subang.domain.Addr;
import com.subang.domain.Balance;
import com.subang.domain.Location;
import com.subang.domain.Order;
import com.subang.domain.Payment.PayType;
import com.subang.domain.User;
import com.subang.domain.User.Client;
import com.subang.tool.SuException;
import com.subang.util.Setting;
import com.subang.util.SmsUtil;
import com.subang.util.SmsUtil.SmsType;
import com.subang.util.StratUtil;
import com.subang.util.StratUtil.ScoreType;
import com.subang.util.SuUtil;

@Controller("userController_app")
@RequestMapping("/app/user")
public class UserController extends BaseController {

	@RequestMapping("/get")
	public void get(Identity identity, HttpServletResponse response) {
		User user = getUser(identity);
		SuUtil.outputJson(response, user);
	}

	// 请求后台向特定的手机发送验证码
	@RequestMapping("/authcode")
	public void getAuthcode(@RequestParam("cellnum") String cellnum,
			@RequestParam("authcode") String authcode, HttpServletResponse response) {
		Result result = new Result();
		if (!SmsUtil.send(cellnum, SmsType.authcode, SmsUtil.toAuthcodeContent(authcode))) {
			result.setCode(Result.ERR);
			result.setMsg("发送验证码错误。");
		} else {
			result.setCode(Result.OK);
		}
		SuUtil.outputJson(response, result);
	}

	// 使用验证过的手机号登录或注册
	@RequestMapping("/login")
	public void login(String cellnum, HttpServletResponse response) {
		User matchUser = userDao.getByCellnum(cellnum);
		if (matchUser == null) {
			User user = new User();
			user.setCellnum(cellnum);
			user.setClient(Client.user);
			try {
				userService.addUser(user);
			} catch (Exception e) {
				e.printStackTrace();
			}
			matchUser = userDao.getByCellnum(cellnum);
		}
		StratUtil.updateScore(matchUser.getId(), ScoreType.login, null);
		SuUtil.outputJsonOK(response);
	}

	// 访问这个url不需要认证信息;但目前app访问这个链接，都可以提供认证信息;
	@RequestMapping("/chkcellnum")
	public void chkCellnum(@RequestParam("cellnum") String cellnum, HttpServletResponse response) {
		Result result = new Result();
		if (!userService.checkCellnum(cellnum)) {
			result.setCode(Result.ERR);
			result.setMsg("该手机号码已经被注册。");
			SuUtil.outputJson(response, result);
		} else {
			result.setCode(Result.OK);
			SuUtil.outputJson(response, result);
		}
	}

	@RequestMapping("/chgcellnum")
	public void chgCellnum(Identity identity, @RequestParam("cellnum") String cellnum,
			HttpServletResponse response) {
		Result result = new Result();
		result.setCode(Result.OK);

		User user = getUser(identity);
		user.setCellnum(cellnum);
		try {
			userService.modifyUser(user);
		} catch (SuException e) {
			result.setCode(Result.ERR);
			result.setMsg("该手机号码已经被注册。");
		}
		SuUtil.outputJson(response, result);
	}

	@RequestMapping("/addr")
	public void listAddr(Identity identity,
			@RequestParam(value = "filter", required = false) String filter,
			HttpServletResponse response) {
		User user = getUser(identity);
		List<AddrDetail> addrDetails = addrDao.findDetailByUserid(user.getId());
		SuUtil.doFilter(filter, addrDetails, AddrDetail.class);
		SuUtil.outputJson(response, addrDetails);
	}

	@RequestMapping("/getdefaultaddr")
	public void getDefaultAddr(Identity identity, HttpServletResponse response) {
		Integer addrid = getUser(identity).getAddrid();
		AddrDetail addrDetail = addrDao.getDetail(addrid);
		SuUtil.outputJson(response, addrDetail);
	}

	@RequestMapping("/getaddrdata")
	public void getAddrData(Identity identity, HttpServletResponse response) {
		User user = getUser(identity);
		AddrData addrData = regionService.getAddrDataByUserid(user.getId());
		addrData.doFilter();
		SuUtil.outputJson(response, addrData);
	}

	@RequestMapping("/addaddr")
	public void addAddr(Identity identity, @Valid Addr addr, BindingResult result,
			HttpServletResponse response) {
		List<Result> results = SuUtil.getResults(result.getFieldErrors());
		if (!result.hasErrors()) {
			User user = getUser(identity);
			addr.setUserid(user.getId());
			userService.addAddr(addr);
		}
		SuUtil.outputJson(response, results);
	}

	@RequestMapping("/deleteaddr")
	public void deleteAddr(@RequestParam("addrid") Integer addrid, HttpServletResponse response) {
		userService.deleteAddr(addrid);
		SuUtil.outputJsonOK(response);
	}

	@RequestMapping("/ticket")
	public void listTicket(Identity identity,
			@RequestParam(value = "categoryid", required = false) Integer categoryid,
			@RequestParam(value = "filter", required = false) String filter,
			HttpServletResponse response) {
		User user = getUser(identity);
		List<TicketDetail> ticketDetails;
		if (categoryid == null) {
			ticketDetails = ticketDao.findValidDetailByUserid(user.getId());
		} else {
			ticketDetails = ticketDao
					.findValidDetailByUseridAndCategoryid(user.getId(), categoryid);
		}
		SuUtil.doFilter(filter, ticketDetails, TicketDetail.class);
		SuUtil.outputJson(response, ticketDetails);
	}

	@RequestMapping("addticket")
	public void addTicket(Identity identity, @RequestParam("tickettypeid") Integer ticketTypeid,
			HttpServletResponse response) {
		Result result = new Result();
		result.setCode(Result.OK);
		try {
			userService.addTicket(getUser(identity).getId(), ticketTypeid);
		} catch (SuException e) {
			result.setCode(Result.ERR);
			result.setMsg(e.getMessage());
		}
		SuUtil.outputJson(response, result);
	}

	@RequestMapping("/user.html")
	public void listUser(Identity identity,
			@RequestParam(value = "filter", required = false) String filter,
			HttpServletResponse response) {
		User user = getUser(identity);
		List<User> users = userDao.findByUserid(user.getId());
		SuUtil.doFilter(filter, users, User.class);
		SuUtil.outputJson(response, users);
	}

	@RequestMapping("/balance")
	public void listBalance(Identity identity,
			@RequestParam(value = "filter", required = false) String filter,
			HttpServletResponse response) {
		User user = getUser(identity);
		List<Balance> balances = balanceDao.findBalanceByUseridAndState(user.getId(),
				Order.State.paid);
		SuUtil.doFilter(filter, balances, Balance.class);
		SuUtil.outputJson(response, balances);
	}

	@RequestMapping("/salary")
	public void listSalary(Identity identity,
			@RequestParam(value = "filter", required = false) String filter,
			HttpServletResponse response) {
		User user = getUser(identity);
		List<Balance> balances = balanceDao.findSalaryByUseridAndState(user.getId(),
				Order.State.paid);
		SuUtil.doFilter(filter, balances, Balance.class);
		SuUtil.outputJson(response, balances);
	}

	@RequestMapping("/draw")
	public void draw(HttpServletRequest request, Identity identity, HttpServletResponse response) {
		User user = getUser(identity);
		Result result = userService.subSalary(user.getId(), request);
		SuUtil.outputJson(response, result);
	}

	@RequestMapping("share")
	public void share(HttpServletRequest request, Identity identity,
			@RequestParam("orderid") Integer orderid, HttpServletResponse response) {
		PayArg payArg = new PayArg();
		payArg.setPayType(PayType.share);
		payArg.setMoney(Setting.shareMoney);
		User user = getUser(identity);
		userService.prepay(payArg, user.getId(), request);
		SuUtil.outputJsonOK(response);
	}

	@RequestMapping("/prepay")
	public void prepay(HttpServletRequest request, Identity identity, PayArg payArg,
			BindingResult result, HttpServletResponse response) {
		PrepayResult prepayResult = null;
		if (result.hasErrors() || payArg.getMoney() == null || payArg.getMoney() == 0.0) {
			prepayResult = new PrepayResult();
			prepayResult.setCode(Code.fail);
			prepayResult.setMsg("参数错误");
		} else {
			User user = getUser(identity);
			prepayResult = userService.prepay(payArg, user.getId(), request);
		}
		SuUtil.outputJson(response, prepayResult);
	}

	@RequestMapping("/setlocation")
	public void setLocation(Identity identity, Location location, HttpServletResponse response)
			throws Exception {
		User user = getUser(identity);
		SuUtil.outputJsonOK(response);
		response.flushBuffer();
		userService.updateLocation(user.getId(), location);
	}

}
