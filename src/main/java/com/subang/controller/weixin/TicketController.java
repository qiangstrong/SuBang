package com.subang.controller.weixin;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.subang.bean.Result;
import com.subang.bean.TicketDetail;
import com.subang.controller.BaseController;
import com.subang.tool.SuException;
import com.subang.util.SuUtil;
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

	@RequestMapping("/exg")
	public void exchange(HttpSession session, @RequestParam("codeno") String codeno,
			HttpServletResponse response) {
		Result result = new Result();
		result.setCode(Result.OK);
		try {
			userService.addTicketByCode(getUser(session).getId(), codeno);
		} catch (SuException e) {
			result.setCode(Result.ERR);
			result.setMsg(e.getMessage());
		}
		SuUtil.outputJson(response, result);
	}

}
