package com.subang.controller.app;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.subang.controller.BaseController;
import com.subang.domain.Info;
import com.subang.util.SuUtil;

@Controller("infoController_app")
@RequestMapping("/app/info")
public class InfoController extends BaseController {

	@RequestMapping("get")
	public void getInfo(HttpServletResponse response) {
		Info info = infoService.getInfo();
		SuUtil.outputJson(response, info);
	}
}
