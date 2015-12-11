package com.subang.controller.back;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.subang.controller.BaseController;
import com.subang.domain.Admin;
import com.subang.tool.BackStack;
import com.subang.util.WebConst;

/**
 * @author Qiang 系统首页，登录，退出
 */
@Controller
@RequestMapping("/back")
public class MainController extends BaseController {

	@RequestMapping("/index")
	public ModelAndView index(HttpSession session) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		backStack.clear("index");
		view.addObject("admin", getAdmin(session));
		view.setViewName(WebConst.BACK_PREFIX + "/index");
		return view;
	}

	@RequestMapping("/showlogin")
	public ModelAndView showLogin() {
		ModelAndView view = new ModelAndView();
		view.addObject("admin", new Admin());
		view.setViewName(WebConst.BACK_PREFIX + "/login");
		return view;
	}

	@RequestMapping("/login")
	public ModelAndView login(HttpSession session, Admin admin,
			@RequestParam("authcode") String authcodeFront) {
		ModelAndView view = new ModelAndView();

		String authcodeBack = (String) session.getAttribute(WebConst.KEY_ADMIN_AUTHCODE);
		// 测试阶段这么写
		if (false && !authcodeBack.equalsIgnoreCase(authcodeFront.trim())) {
			view.addObject(KEY_INFO_MSG, "登录失败。验证码错误。");
			view.addObject("admin", admin);
			view.setViewName(WebConst.BACK_PREFIX + "/login");
			return view;
		}
		Admin matchAdmin = adminDao.findByAdmin(admin);
		if (matchAdmin == null) {
			view.addObject(KEY_INFO_MSG, "登录失败。用户名或密码错误。");
			view.addObject("admin", admin);
			view.setViewName(WebConst.BACK_PREFIX + "/login");
			return view;
		}
		initBackStack(session);
		setAdmin(session, matchAdmin);
		view.setViewName("redirect:" + WebConst.BACK_PREFIX + "/index.html");
		return view;
	}

	@RequestMapping("/logout")
	public ModelAndView logout(HttpSession session) {
		ModelAndView view = new ModelAndView();
		session.invalidate();
		view.setViewName("redirect:" + WebConst.BACK_PREFIX + "/showlogin.html");
		return view;
	}
}
