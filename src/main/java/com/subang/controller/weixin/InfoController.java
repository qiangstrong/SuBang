package com.subang.controller.weixin;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.subang.controller.BaseController;
import com.subang.domain.Faq;
import com.subang.util.WebConst;

@Controller("infoController_weixin")
@RequestMapping("/weixin/info")
public class InfoController extends BaseController {

	private static final String VIEW_PREFIX = WebConst.WEIXIN_PREFIX + "/info";

	@RequestMapping("/faq")
	public ModelAndView listFaq() {
		ModelAndView view = new ModelAndView();
		List<Faq> faqs = faqDao.findAll();
		view.addObject("faqs", faqs);
		view.setViewName(VIEW_PREFIX + "/faq");
		return view;
	}

	@RequestMapping("/showaddfeedback")
	public ModelAndView showAddFeedback() {
		ModelAndView view = new ModelAndView();
		view.setViewName(VIEW_PREFIX + "/feedback");
		return view;
	}

	// 由客户端根据用户填写的信息生成字符串，并检验其长度
	@RequestMapping("/addfeedback")
	public ModelAndView addFeedback(@RequestParam("comment") String comment) {
		ModelAndView view = new ModelAndView();
		infoService.addFeedback(comment);
		view.setViewName("redirect:" + WebConst.WEIXIN_PREFIX + "/user/index.html");
		return view;
	}
}
