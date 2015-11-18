package com.subang.controller.app;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.subang.controller.BaseController;
import com.subang.domain.Info;
import com.subang.util.SuUtil;

@Controller("infoController_app")
@RequestMapping("/app/info")
public class InfoController extends BaseController {

	@RequestMapping("/get")
	public void get(HttpServletResponse response) {
		Info info = infoService.getInfo();
		SuUtil.outputJson(response, info);
	}

	@RequestMapping("/addfeedback")
	public void addFeedback(@RequestParam("comment") String comment, HttpServletResponse response) {
		infoService.addFeedback(comment);
		SuUtil.outputJsonOK(response);
	}
}
