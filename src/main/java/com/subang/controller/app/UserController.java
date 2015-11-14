package com.subang.controller.app;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.subang.bean.AddrData;
import com.subang.bean.AddrDetail;
import com.subang.bean.Identity;
import com.subang.bean.Result;
import com.subang.bean.TicketDetail;
import com.subang.controller.BaseController;
import com.subang.domain.Addr;
import com.subang.domain.Location;
import com.subang.domain.User;
import com.subang.exception.SuException;
import com.subang.util.SuUtil;

@Controller("userController_app")
@RequestMapping("/app/user")
public class UserController extends BaseController {

	@RequestMapping("/get")
	public void get(Identity identity, HttpServletResponse response) {
		User user = getUser(identity);
		SuUtil.outputJson(response, user);
	}

	@RequestMapping("/login")
	public void login(User user, HttpServletResponse response) {
		User matchUser = userDao.findByUser(user);
		Result result = new Result();
		if (matchUser == null) {
			result.setCode(Result.ERR);
			result.setMsg("手机号或密码错误。");
		} else {
			result.setCode(Result.OK);
		}
		SuUtil.outputJson(response, result);
	}

	@RequestMapping("/add")
	public void add(@Valid User user, BindingResult result, HttpServletResponse response) {
		List<Result> results = SuUtil.getResults(result.getFieldErrors());
		if (!result.hasErrors()) {
			try {
				userService.addUser(user);
			} catch (Exception e) {
				results.add(new Result(Result.ERR, "该手机号码已经被注册。"));
			}
		}
		SuUtil.outputJson(response, results);
	}

	// 注册和改变手机号都要使用这个链接，所以访问这个url不需要认证信息
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
		if (!userService.checkCellnum(cellnum)) {
			result.setCode(Result.ERR);
			result.setMsg("该手机号码已经被注册。");
			SuUtil.outputJson(response, result);
			return;
		}
		User user = getUser(identity);
		user.setCellnum(cellnum);
		try {
			userService.modifyUser(user);
		} catch (SuException e) {
		}
		result.setCode(Result.OK);
		SuUtil.outputJson(response, result);
	}

	@RequestMapping("/chgpassword")
	public void chgPassword(Identity identity, @RequestParam("password") String password,
			HttpServletResponse response) {
		User user = getUser(identity);
		user.setPassword(password);
		try {
			userService.modifyUser(user);
		} catch (SuException e) {
		}
		SuUtil.outputJsonOK(response);
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
			@RequestParam(value = "filter", required = false) String filter,
			HttpServletResponse response) {
		User user = getUser(identity);
		List<TicketDetail> ticketDetails = ticketDao.findValidDetailByUserid(user.getId());
		SuUtil.doFilter(filter, ticketDetails, TicketDetail.class);
		SuUtil.outputJson(response, ticketDetails);
	}

	@RequestMapping("/setlocation")
	public void setLocation(Identity identity, Location location, HttpServletResponse response) {
		User user = getUser(identity);
		userService.updateLocation(user.getId(), location);
		SuUtil.outputJsonOK(response);
	}
}
