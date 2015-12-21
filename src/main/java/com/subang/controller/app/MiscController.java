package com.subang.controller.app;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.subang.bean.AppInfo;
import com.subang.bean.Result;
import com.subang.controller.BaseController;
import com.subang.util.SuUtil;

@Controller("miscController_app")
@RequestMapping("/app/misc")
public class MiscController extends BaseController {

	@RequestMapping("/checkapp")
	public void checkApp(AppInfo appInfo, HttpServletResponse response) {
		Result result = new Result();
		if (miscService.checkApp(appInfo)) {
			result.setCode(Result.OK);
		} else {
			result.setCode(Result.ERR);
		}
		SuUtil.outputJson(response, result);
	}
}
