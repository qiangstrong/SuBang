package com.subang.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.subang.util.SuUtil;

@Controller
@RequestMapping("/test")
public class ControllerTest extends BaseController  {
	
	@RequestMapping("/index")
	public void login(HttpServletRequest request, HttpServletResponse response) throws Exception{
		throw new Exception("测试。");
	}
}
