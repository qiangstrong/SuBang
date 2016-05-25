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
		User user = userDao.get(21);
		setUser(request.getSession(), user);
		SuUtil.outputStreamWrite(response.getOutputStream(), "登录成功。");
	}

	@RequestMapping("/setopenid")
	public void setopenid(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		User user = userDao.get(21);
		setOpenid(request.getSession(), user.getOpenid());
		// setOpenid(request.getSession(), "o64-8vkEc7aqJ2SAHbO0_3BPa0KE");
		SuUtil.outputStreamWrite(response.getOutputStream(), "openid登录成功。");
	}

	@RequestMapping("/index")
	public ModelAndView index(HttpServletRequest request) {
		ModelAndView view = new ModelAndView();
		StringBuffer homeUrl = request.getRequestURL();
		String queryString = request.getQueryString();
		if (queryString != null) {
			homeUrl.append("?").append(queryString);
		}

		String url = homeUrl.toString();
		System.err.println(url);
		String[] jsApiList = { "checkJsApi", "onMenuShareTimeline" };
		String configStr = JsUtil.generateConfigJson(TicketManager.getDefaultTicket(), false,
				SuUtil.getAppProperty("appid"), url, jsApiList);
		view.addObject("configStr", configStr);
		view.setViewName(VIEW_PREFIX + "/index");
		return view;
	}
}
