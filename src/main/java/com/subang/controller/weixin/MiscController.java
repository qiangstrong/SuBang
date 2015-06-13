package com.subang.controller.weixin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.subang.controller.BaseController;
import com.subang.util.WebConst;

@Controller("miscController_weixin")
@RequestMapping("/weixin/misc")
public class MiscController extends BaseController{
	
	private static final String VIEW_PREFIX = WebConst.WEIXIN_PREFIX + "/misc";
	
	@RequestMapping("/activity")
	public ModelAndView getActivity() {
		ModelAndView view = new ModelAndView();
		view.setViewName(VIEW_PREFIX+"/activity");
		return view;
	}
}
