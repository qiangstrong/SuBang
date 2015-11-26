package com.subang.controller.weixin;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.subang.controller.BaseController;
import com.subang.domain.TicketType;
import com.subang.util.WebConst;

@Controller("activityController_weixin")
@RequestMapping("/weixin/activity")
public class ActivityController extends BaseController {

	private static final String VIEW_PREFIX = WebConst.WEIXIN_PREFIX + "/activity";
	private static final String INDEX_PAGE = VIEW_PREFIX + "/index";

	@RequestMapping("/index")
	public ModelAndView index() {
		ModelAndView view = new ModelAndView();
		List<TicketType> ticketTypes = ticketTypeDao.findDetailValidAll();
		view.addObject("ticketTypes", ticketTypes);
		view.setViewName(INDEX_PAGE);
		return view;
	}

	@RequestMapping("/detail")
	public ModelAndView getDetail(@RequestParam("tickettypeid") Integer ticketTypeid) {
		ModelAndView view = new ModelAndView();
		TicketType ticketType = ticketTypeDao.getDetail(ticketTypeid);
		view.addObject("ticketType", ticketType);
		view.setViewName(VIEW_PREFIX + "/detail");
		return view;
	}

}
