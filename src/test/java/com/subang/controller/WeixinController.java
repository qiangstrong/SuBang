package com.subang.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import weixin.popular.support.TicketManager;
import weixin.popular.util.JsUtil;

import com.subang.domain.User;
import com.subang.util.SuUtil;

@Controller
@RequestMapping("/test/weixin")
public class WeixinController extends BaseController {

	private static final String VIEW_PREFIX = "/test";

	@RequestMapping("/login")
	public void login(HttpServletRequest request, HttpServletResponse response) throws Exception {
		User user = userDao.get(1);
		setUser(request.getSession(), user);
		SuUtil.outputStreamWrite(response.getOutputStream(), "登录成功。");
	}

	@RequestMapping("/setopenid")
	public void setopenid(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		User user = userDao.get(1);
		setOpenid(request.getSession(), user.getOpenid());
		SuUtil.outputStreamWrite(response.getOutputStream(), "openid登录成功。");
	}

	@RequestMapping("/index")
	public ModelAndView index() {
		ModelAndView view = new ModelAndView();
		String url = "http://202.118.18.56/subang/test/weixin/index.html";
		String[] jsApiList = { "checkJsApi", "onMenuShareTimeline" };
		String configStr = JsUtil.generateConfigJson(TicketManager.getDefaultTicket(), false,
				SuUtil.getAppProperty("appid"), url, jsApiList);
		view.addObject("configStr", configStr);
		view.setViewName(VIEW_PREFIX + "/index");
		return view;
	}
}
