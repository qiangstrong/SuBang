package com.subang.controller.weixin;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.subang.bean.TicketDetail;
import com.subang.controller.BaseController;
import com.subang.exception.SuException;
import com.subang.util.WebConst;

@Controller("ticketController_weixin")
@RequestMapping("/weixin/ticket")
public class TicketController extends BaseController {

	private static final String VIEW_PREFIX = WebConst.WEIXIN_PREFIX + "/ticket";
	private static final String INDEX_PAGE = VIEW_PREFIX + "/index";

	@RequestMapping("/index")
	public ModelAndView index(HttpSession session) {
		ModelAndView view = new ModelAndView();
		List<TicketDetail> ticketDetails = ticketDao.findValidDetailByUserid(getUser(session)
				.getId());
		view.addObject("ticketDetails", ticketDetails);
		view.setViewName(INDEX_PAGE);
		return view;
	}

	@RequestMapping("/add")
	public ModelAndView add(HttpSession session, @RequestParam("tickettypeid") Integer ticketTypeid) {
		ModelAndView view = new ModelAndView();
		view.addObject(KEY_INFO_MSG, "兑换成功。");
		try {
			userService.addTicket(getUser(session).getId(), ticketTypeid);
		} catch (SuException e) {
			view.addObject(KEY_INFO_MSG, "兑换失败。" + e.getMessage());
		}
		view.setViewName(VIEW_PREFIX + "/addresult");
		return view;
	}

}
