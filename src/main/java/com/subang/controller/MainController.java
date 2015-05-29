package com.subang.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.subang.bean.PageState;
import com.subang.bean.SearchArg;
import com.subang.domain.Admin;
import com.subang.util.Common;
import com.subang.util.WebConstant;


/**
 * @author Qiang
 * 系统首页，登录，退出
 */
@Controller
public class MainController extends BaseController {

	@RequestMapping("/index")
	public ModelAndView index(HttpSession session) {
		ModelAndView view = new ModelAndView();
		invalidtePageState(session);
		view.setViewName("index");
		return view;
	}
	
	@RequestMapping("/showlogin")
	public ModelAndView showLogin(){
		ModelAndView view = new ModelAndView();
		view.addObject("admin", new Admin());
		view.setViewName("login");
		return view;
	}
	
	@RequestMapping("/login")
	public ModelAndView login(HttpSession session, Admin admin){
		ModelAndView view = new ModelAndView();
		Admin matchAdmin=backAdminService.getAdminByMatch(admin);
		if (matchAdmin!=null) {
			setAdmin(session, matchAdmin);
			session.setMaxInactiveInterval(WebConstant.SESSION_INTERVAL);
			Common.setServletContext(session.getServletContext());
			Common.loadProperties();
			view.setViewName("redirect:/index.html?type=0");
		}else {
			view.addObject(KEY_INFO_MSG, "登录失败。用户名或密码错误。");
			view.addObject("admin", admin);
			view.setViewName("login");
		}
		return view;
	}
	
	@RequestMapping("/logout")
	public ModelAndView logout(HttpSession session){
		ModelAndView view = new ModelAndView();
		session.invalidate();
		view.setViewName("redirect:/showlogin.html");
		return view;
	}
}
