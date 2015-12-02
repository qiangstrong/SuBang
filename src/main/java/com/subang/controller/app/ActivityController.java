package com.subang.controller.app;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.subang.controller.BaseController;
import com.subang.domain.Banner;
import com.subang.domain.Rebate;
import com.subang.domain.TicketType;
import com.subang.util.SuUtil;

@Controller("activityController_app")
@RequestMapping("/app/activity")
public class ActivityController extends BaseController {

	@RequestMapping("/tickettype")
	public void listTicketType(@RequestParam(value = "filter", required = false) String filter,
			HttpServletResponse response) {
		List<TicketType> ticketTypes = ticketTypeDao.findDetailValidAll();
		SuUtil.doFilter(filter, ticketTypes, TicketType.class);
		SuUtil.outputJson(response, ticketTypes);
	}

	@RequestMapping("/gettickettype")
	public void getTicketType(@RequestParam("tickettypeid") Integer ticketTypeid,
			HttpServletResponse response) {
		TicketType ticketType = ticketTypeDao.getDetail(ticketTypeid);
		SuUtil.outputJson(response, ticketType);
	}

	@RequestMapping("/banner")
	public void listBanner(@RequestParam(value = "filter", required = false) String filter,
			HttpServletResponse response) {
		List<Banner> banners = bannerDao.findAll();
		SuUtil.doFilter(filter, banners, Banner.class);
		SuUtil.outputJson(response, banners);
	}

	@RequestMapping("/rebate")
	public void listRebate(@RequestParam(value = "filter", required = false) String filter,
			HttpServletResponse response) {
		List<Rebate> rebates = rebateDao.findAll();
		SuUtil.doFilter(filter, rebates, Rebate.class);
		SuUtil.outputJson(response, rebates);
	}
}
