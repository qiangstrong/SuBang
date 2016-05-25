package com.subang.controller.weixin;

import java.util.List;
import java.util.TimerTask;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.subang.bean.Result;
import com.subang.controller.BaseController;
import com.subang.domain.Balance;
import com.subang.domain.Order;
import com.subang.domain.User;
import com.subang.domain.User.Client;
import com.subang.tool.SuException;
import com.subang.util.ComUtil;
import com.subang.util.Setting;
import com.subang.util.SmsUtil;
import com.subang.util.SmsUtil.SmsType;
import com.subang.util.StratUtil;
import com.subang.util.StratUtil.ScoreType;
import com.subang.util.SuUtil;
import com.subang.util.Validator;
import com.subang.util.WebConst;

@Controller("userController_weixin")
@RequestMapping("/weixin/user")
public class UserController extends BaseController {

	private static final String VIEW_PREFIX = WebConst.WEIXIN_PREFIX + "/user";
	private static final String INDEX_PAGE = VIEW_PREFIX + "/index";

	private static final String KEY_CELLNUM = "cellnum";
	private static final String KEY_TIMERTASK = "timerTask_authcode";

	@RequestMapping("/index")
	public ModelAndView index(HttpSession session) {
		ModelAndView view = new ModelAndView();
		view.addObject("user", getUser(session));
		view.addObject("phone", Setting.phone);
		view.setViewName(INDEX_PAGE);
		return view;
	}

	/**
	 * 用户登录,注册
	 */
	// 客户端校验cellnum书写是否正确,请求后台向特定的手机发送验证码
	@RequestMapping("/authcode")
	public void getAuthcode(final HttpSession session, @RequestParam("cellnum") String cellnum,
			HttpServletResponse response) {
		Result result = new Result();

		TimerTask task = (TimerTask) session.getAttribute(KEY_TIMERTASK);
		if (task != null) {
			task.cancel();
			session.removeAttribute(WebConst.KEY_USER_AUTHCODE);
		}

		String authcode = SuUtil.getAuthcode();
		session.setAttribute(KEY_CELLNUM, cellnum);
		session.setAttribute(WebConst.KEY_USER_AUTHCODE, authcode);
		if (!SmsUtil.send(cellnum, SmsType.authcode, SmsUtil.toAuthcodeContent(authcode))) {
			result.setCode(Result.ERR);
			result.setMsg("发送验证码错误。");
			SuUtil.outputJson(response, result);
			return;
		}

		task = new TimerTask() {
			@Override
			public void run() {
				session.removeAttribute(WebConst.KEY_USER_AUTHCODE);
			}
		};
		ComUtil.timer.schedule(task, WebConst.AUTHCODE_INTERVAL);
		result.setCode(Result.OK);
		SuUtil.outputJson(response, result);
	}

	// 用户登录或注册
	@RequestMapping("/login")
	public void login(HttpSession session, @RequestParam("authcode") String authcodeFront,
			HttpServletResponse response) {
		Result result = new Result();
		String cellnum = (String) session.getAttribute(KEY_CELLNUM);
		String authcodeBack = (String) session.getAttribute(WebConst.KEY_USER_AUTHCODE);
		if (cellnum == null || authcodeBack == null) {
			result.setCode(Result.ERR);
			result.setMsg("验证码已失效，请重新获取验证码。");
			SuUtil.outputJson(response, result);
			return;
		}
		if (!authcodeBack.equalsIgnoreCase(authcodeFront.trim())) {
			result.setCode(Result.ERR);
			result.setMsg("验证码输入错误，请重新输入。");
			SuUtil.outputJson(response, result);
			return;
		}
		session.removeAttribute(KEY_CELLNUM);
		session.removeAttribute(WebConst.KEY_USER_AUTHCODE);

		User matchUser = userDao.getByCellnum(cellnum);
		if (matchUser == null) {
			User user = new User();
			user.setCellnum(cellnum);
			user.setClient(Client.weixin);
			try {
				userService.addUser(user);
			} catch (Exception e) {
				e.printStackTrace();
			}
			matchUser = userDao.getByCellnum(cellnum);
		}

		matchUser.setOpenid(getOpenid(session));
		try {
			userService.modifyUser(matchUser);
		} catch (Exception e) {
			e.printStackTrace();
		}

		StratUtil.updateScore(matchUser.getId(), ScoreType.login, null);
		setUser(session, matchUser);
		result.setCode(Result.OK);
		SuUtil.outputJson(response, result);
	}

	/**
	 * 更改用户信息
	 */
	@RequestMapping("/showchgcellnum")
	public ModelAndView showChgCellnum() {
		ModelAndView view = new ModelAndView();
		view.setViewName(VIEW_PREFIX + "/chgcellnum");
		return view;
	}

	@RequestMapping("/chgcellnum")
	public void chgCellnum(HttpSession session, @RequestParam("authcode") String authcodeFront,
			HttpServletResponse response) {
		Result result = new Result();
		String cellnum = (String) session.getAttribute(KEY_CELLNUM);
		String authcodeBack = (String) session.getAttribute(WebConst.KEY_USER_AUTHCODE);
		if (authcodeBack == null) {
			result.setCode(Result.ERR);
			result.setMsg("验证码已失效，请重新获取验证码。");
			SuUtil.outputJson(response, result);
			return;
		}
		if (!authcodeBack.equalsIgnoreCase(authcodeFront.trim())) {
			result.setCode(Result.ERR);
			result.setMsg("验证码输入错误，请重新输入。");
			SuUtil.outputJson(response, result);
			return;
		}

		session.removeAttribute(WebConst.KEY_USER_AUTHCODE);
		session.removeAttribute(KEY_CELLNUM);
		result.setCode(Result.OK);

		User user = getUser(session);
		user.setCellnum(cellnum);
		try {
			userService.modifyUser(user);
		} catch (SuException e) {
			result.setCode(Result.ERR);
			result.setMsg("该手机号码已经被注册。");
		}
		SuUtil.outputJson(response, result);
	}

	// 推广注册用户
	@RequestMapping("/promote")
	public ModelAndView promote(@RequestParam("cellnum") String cellnum,
			@RequestParam(value = "cellnumProm", required = false) String cellnumProm) {
		ModelAndView view = new ModelAndView();
		User user = userDao.getByCellnum(cellnum);
		if (user == null) {
			view.addObject(KEY_INFO_MSG, "发生错误。");
			view.setViewName(VIEW_PREFIX + "/promote");
			return view;
		}
		if (cellnumProm == null) {
			view.addObject("cellnum", ComUtil.hideCellnum(cellnum));
			view.setViewName(VIEW_PREFIX + "/promote");
			return view;
		}
		if (!Validator.validCellnum(cellnumProm).isOk()) {
			view.addObject(KEY_INFO_MSG, "发生错误。");
			view.addObject("cellnum", ComUtil.hideCellnum(cellnum));
			view.setViewName(VIEW_PREFIX + "/promote");
			return view;
		}
		User userProm = userDao.getByCellnum(cellnumProm);
		if (userProm != null) {
			view.addObject(KEY_INFO_MSG, "old"); // 此处由前端根据此代码，自定义消息
			view.addObject("cellnum", ComUtil.hideCellnum(cellnum));
			view.setViewName(VIEW_PREFIX + "/promote");
			return view;
		}

		userProm = new User();
		userProm.setCellnum(cellnumProm);
		userProm.setClient(Client.promotion);
		userProm.setUserid(user.getId());
		try {
			userService.addUser(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		view.addObject(KEY_INFO_MSG, "new"); // 此处由前端根据此代码，自定义消息
		view.addObject("cellnum", ComUtil.hideCellnum(cellnum));
		view.setViewName(VIEW_PREFIX + "/promote");
		return view;
	}

	/*
	 * 收益
	 */
	@RequestMapping("/salary")
	public ModelAndView listSalary(HttpSession session) {
		ModelAndView view = new ModelAndView();
		User user = getUser(session);
		view.addObject("user", user);
		List<Balance> balances = balanceDao.findSalaryByUseridAndState(user.getId(),
				Order.State.paid);
		view.addObject("balances", balances);
		List<User> users = userDao.findByUserid(user.getId());
		view.addObject("users", users);
		view.setViewName(VIEW_PREFIX + "/salary");
		return view;
	}

	@RequestMapping("/predraw")
	public ModelAndView predraw(HttpSession session) {
		ModelAndView view = new ModelAndView();
		User user = getUser(session);
		view.addObject("user", user);
		view.setViewName(VIEW_PREFIX + "/predraw");
		return view;
	}

	@RequestMapping("/draw")
	public ModelAndView draw(HttpServletRequest request) {
		ModelAndView view = new ModelAndView();
		User user = getUser(request.getSession());
		Result result = userService.subSalary(user.getId(), request);
		if (!result.isOk()) {
			view.addObject(KEY_INFO_MSG, "提现失败。" + result.getMsg());
		} else {
			view.addObject(KEY_INFO_MSG, "提现成功。");
		}
		view.setViewName(VIEW_PREFIX + "/drawresult");
		return view;
	}
}
