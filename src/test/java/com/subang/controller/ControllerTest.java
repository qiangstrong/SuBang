package com.subang.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/test")
public class ControllerTest extends BaseController {

	@RequestMapping("/index")
	public void login(@RequestParam(value = "type", required = false) Integer type)
			throws Exception {
		System.out.println(type);
	}
}
