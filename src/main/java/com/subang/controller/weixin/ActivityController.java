package com.subang.controller.weixin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.subang.controller.BaseController;
import com.subang.domain.TicketType;
import com.subang.domain.User;
import com.subang.util.Setting;
import com.subang.util.SuUtil;
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

	// 推荐有奖
	@RequestMapping("/promote")
	public ModelAndView promote(HttpServletRequest request) {
		ModelAndView view = new ModelAndView();
		User user = getUser(request.getSession());
		view.addObject("url", SuUtil.getPromPath(request, user.getCellnum()));
		view.addObject("salaryLimit", Setting.salaryLimit);
		view.addObject("prom", Setting.prom);
		view.addObject("config", SuUtil.getJsapiConfig(request));
		view.setViewName(VIEW_PREFIX + "/promote");
		return view;
	}

	// 下单后分享红包
	@RequestMapping("/share")
	public ModelAndView share(HttpServletRequest request, @RequestParam("orderid") Integer orderid) {
		ModelAndView view = new ModelAndView();
		view.addObject("orderid", orderid);
		view.addObject("url", SuUtil.getSharePath(request));
		view.addObject("shareMoney", Setting.shareMoney);
		view.addObject("config", SuUtil.getJsapiConfig(request));
		view.setViewName(VIEW_PREFIX + "/share");
		return view;
	}
}
