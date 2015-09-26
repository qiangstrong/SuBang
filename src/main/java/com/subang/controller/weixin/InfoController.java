package com.subang.controller.weixin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.subang.controller.BaseController;
import com.subang.domain.Info;
import com.subang.util.WebConst;

@Controller("infoController_weixin")
@RequestMapping("/weixin/info")
public class InfoController extends BaseController{
	
	private static final String VIEW_PREFIX = WebConst.WEIXIN_PREFIX + "/info";
	
	@RequestMapping("/price")
	public ModelAndView getPrice() {
		ModelAndView view = new ModelAndView();
		Info info=infoService.getInfo();
		view.addObject("price_path", info.getPrice_path());
		view.addObject("price_text", info.getPrice_text());
		view.setViewName(VIEW_PREFIX+"/price");
		return view;
	}
	
	@RequestMapping("/scope")
	public ModelAndView getScope() {
		ModelAndView view = new ModelAndView();
		Info info=infoService.getInfo();
		view.addObject("scope_path", info.getScope_path());
		view.addObject("scope_text", info.getScope_text());
		view.setViewName(VIEW_PREFIX+"/scope");
		return view;
	}
	
	@RequestMapping("/about")
	public ModelAndView getAbout() {
		ModelAndView view = new ModelAndView();
		Info info=infoService.getInfo();
		view.addObject("about", info.getAbout());
		view.addObject("phone", info.getPhone());
		view.setViewName(VIEW_PREFIX+"/about");
		return view;
	}
	
	@RequestMapping("/term")
	public ModelAndView getTerm() {
		ModelAndView view = new ModelAndView();
		Info info=infoService.getInfo();
		view.addObject("term", info.getTerm());
		view.setViewName(VIEW_PREFIX+"/term");
		return view;
	}
}
