package com.subang.controller.weixin;

import java.util.TimerTask;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.subang.bean.Result;
import com.subang.controller.BaseController;
import com.subang.domain.User;
import com.subang.exception.SuException;
import com.subang.util.ComUtil;
import com.subang.util.SmsUtil;
import com.subang.util.SmsUtil.SmsType;
import com.subang.util.SuUtil;
import com.subang.util.WebConst;

@Controller("userController_weixin")
@RequestMapping("/weixin/user")
public class UserController extends BaseController {

	private static final String VIEW_PREFIX = WebConst.WEIXIN_PREFIX + "/user";
	private static final String INDEX_PAGE = VIEW_PREFIX + "/index";

	private static String KEY_CELLNUM = "cellnum";
	private static String KEY_TIMERTASK = "timerTask_authcode";

	@RequestMapping("/index")
	public ModelAndView index(HttpSession session) {
		ModelAndView view = new ModelAndView();
		view.addObject("user", getUser(session));
		view.addObject("phone", infoService.getInfo().getPhone());
		view.setViewName(INDEX_PAGE);
		return view;
	}

	/**
	 * 用户登录
	 */
	@RequestMapping("login")
	public ModelAndView login(HttpSession session, User user) {
		ModelAndView view = new ModelAndView();

		User matchUser = userDao.findByUser(user);
		if (matchUser == null) {
			view.addObject(KEY_INFO_MSG, "登录失败。手机号或密码错误。");
			view.addObject("user", user);
			view.setViewName(VIEW_PREFIX + "/login");
			return view;
		}
		matchUser.setOpenid(getOpenid(session));
		try {
			userService.modifyUser(matchUser);
		} catch (Exception e) {
		}
		setUser(session, matchUser);
		view.setViewName("redirect:" + WebConst.WEIXIN_PREFIX + "/region/index.html");
		return view;
	}

	/**
	 * 用户注册
	 */
	@RequestMapping("/showregcellnum")
	public ModelAndView showRegCellnum() {
		ModelAndView view = new ModelAndView();
		view.setViewName(VIEW_PREFIX + "/regcellnum");
		return view;
	}

	// 客户端校验cellnum书写是否正确
	@RequestMapping("/cellnum")
	public void getCellnum(final HttpSession session, HttpServletResponse response,
			@RequestParam("cellnum") String cellnum) {
		Result result = new Result();
		if (!userService.checkCellnum(cellnum)) {
			result.setCode(Result.ERR);
			result.setMsg("该手机号码已经被注册。");
			SuUtil.outputJson(response, result);
			return;
		}

		TimerTask task = (TimerTask) session.getAttribute(KEY_TIMERTASK);
		if (task != null) {
			task.cancel();
			session.removeAttribute(WebConst.KEY_USER_AUTHCODE);
		}

		String authcode = SuUtil.getUserAuthcode();
		session.setAttribute(KEY_CELLNUM, cellnum);
		session.setAttribute(WebConst.KEY_USER_AUTHCODE, authcode);
		if (!SmsUtil.send(cellnum, SmsType.authcode, SmsUtil.toUserContent(authcode))) {
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

	@RequestMapping("/regauthcode")
	public ModelAndView regAuthcode(HttpSession session,
			@RequestParam("authcode") String authcodeFront) {
		ModelAndView view = new ModelAndView();
		String authcodeBack = (String) session.getAttribute(WebConst.KEY_USER_AUTHCODE);
		if (authcodeBack == null) {
			view.addObject(KEY_INFO_MSG, "验证码已失效，请重新获取验证码。");
			view.addObject(KEY_CELLNUM, (String) session.getAttribute(KEY_CELLNUM));
			view.setViewName(VIEW_PREFIX + "/regcellnum");
			return view;
		}
		if (!authcodeBack.equalsIgnoreCase(authcodeFront.trim())) {
			view.addObject(KEY_INFO_MSG, "验证码输入错误，请重新输入。");
			view.addObject(KEY_CELLNUM, (String) session.getAttribute(KEY_CELLNUM));
			view.setViewName(VIEW_PREFIX + "/regcellnum");
			return view;
		}
		session.removeAttribute(WebConst.KEY_USER_AUTHCODE);
		view.setViewName(VIEW_PREFIX + "/regpassword");
		return view;
	}

	// 客户端校验两次密码是否相等，以及密码的长度是否合适
	@RequestMapping("/regpassword")
	public ModelAndView regPassword(HttpSession session, @RequestParam("password") String password) {
		ModelAndView view = new ModelAndView();
		String cellnum = (String) session.getAttribute(KEY_CELLNUM);
		if (cellnum == null) {
			view.addObject(KEY_INFO_MSG, "系统出错，请您重新注册。");
			view.setViewName(VIEW_PREFIX + "/regcellnum");
			return view;
		}

		User user = new User();
		user.setOpenid(getOpenid(session));
		user.setPassword(password);
		user.setCellnum((String) session.getAttribute(KEY_CELLNUM));
		try {
			userService.addUser(user);
		} catch (SuException e) {
		}
		session.removeAttribute(KEY_CELLNUM);
		user = userDao.getByOpenid(user.getOpenid());
		setUser(session, user);
		view.setViewName("redirect:" + WebConst.WEIXIN_PREFIX + "/region/index.html");
		return view;
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

	@RequestMapping("/chgauthcode")
	public ModelAndView chgAuthcode(HttpSession session,
			@RequestParam("authcode") String authcodeFront) {
		ModelAndView view = new ModelAndView();
		String cellnum = (String) session.getAttribute(KEY_CELLNUM);
		String authcodeBack = (String) session.getAttribute(WebConst.KEY_USER_AUTHCODE);
		if (authcodeBack == null) {
			view.addObject(KEY_INFO_MSG, "验证码已失效，请重新获取验证码。");
			view.addObject(KEY_CELLNUM, (String) session.getAttribute(KEY_CELLNUM));
			view.setViewName(VIEW_PREFIX + "/chgcellnum");
			return view;
		}
		if (!authcodeBack.equalsIgnoreCase(authcodeFront.trim())) {
			view.addObject(KEY_INFO_MSG, "验证码输入错误，请重新输入。");
			view.addObject(KEY_CELLNUM, (String) session.getAttribute(KEY_CELLNUM));
			view.setViewName(VIEW_PREFIX + "/chgcellnum");
			return view;
		}
		User user = getUser(session);
		user.setCellnum(cellnum);
		try {
			userService.modifyUser(user);
		} catch (SuException e) {
		}
		session.removeAttribute(WebConst.KEY_USER_AUTHCODE);
		session.removeAttribute(KEY_CELLNUM);
		view.setViewName(VIEW_PREFIX + "/index");
		return view;
	}

	@RequestMapping("/showchgpassword")
	public ModelAndView showChgPassword() {
		ModelAndView view = new ModelAndView();
		view.setViewName(VIEW_PREFIX + "/chgpasswor");
		return view;
	}

	// 客户端校验密码的长度
	@RequestMapping("/chgpassword")
	public ModelAndView chgPassword(HttpSession session, @RequestParam("password") String password) {
		ModelAndView view = new ModelAndView();
		User user = getUser(session);
		user.setPassword(password);
		try {
			userService.modifyUser(user);
		} catch (SuException e) {
		}
		view.setViewName(VIEW_PREFIX + "/index");
		return view;
	}
}
