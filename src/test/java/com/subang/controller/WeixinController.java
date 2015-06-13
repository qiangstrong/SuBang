package com.subang.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.subang.domain.User;
import com.subang.util.Common;

@Controller("WeixinController")
@RequestMapping("/weixin")
public class WeixinController extends BaseController {

	@RequestMapping("/login")
	public void login(HttpServletRequest request, HttpServletResponse response) throws Exception{
		User user=frontUserService.getUser(9);
		setUser(request.getSession(), user);
		Common.outputStreamWrite(response.getOutputStream(), "登录成功。");
	}
}
