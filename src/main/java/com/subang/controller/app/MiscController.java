package com.subang.controller.app;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.subang.bean.AppInfo;
import com.subang.bean.Identity;
import com.subang.bean.Result;
import com.subang.controller.BaseController;
import com.subang.domain.User;
import com.subang.util.SuUtil;
import com.subang.util.WebConst;

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

	// 在app中登录积分商城
	@RequestMapping("/login")
	public ModelAndView login(HttpSession session, Identity identity) {
		ModelAndView view = new ModelAndView();
		User user = getUser(identity);
		setUser(session, user);
		view.setViewName("redirect:" + WebConst.WEIXIN_PREFIX + "/mall/index.html");
		return view;
	}
}
