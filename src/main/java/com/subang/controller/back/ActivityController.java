package com.subang.controller.back;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.subang.controller.BaseController;
import com.subang.domain.Category;
import com.subang.domain.TicketType;
import com.subang.exception.SuException;
import com.subang.util.SuUtil;
import com.subang.util.WebConst;

@Controller
@RequestMapping("/back/activity")
public class ActivityController extends BaseController {

	@RequestMapping("/tickettype")
	public ModelAndView listTicketType(HttpSession session) {
		ModelAndView view = new ModelAndView();
		invalidtePageState(session);
		List<TicketType> ticketTypes = ticketTypeDao.findDetailAll();
		view.addObject("ticketTypes", ticketTypes);

		view.addObject(KEY_ERR_MSG, session.getAttribute(KEY_ERR_MSG));
		session.removeAttribute(KEY_ERR_MSG);
		view.addObject(KEY_INFO_MSG, session.getAttribute(KEY_INFO_MSG));
		session.removeAttribute(KEY_INFO_MSG);

		view.setViewName(WebConst.BACK_PREFIX + "/activity/tickettype");
		return view;
	}

	@RequestMapping("/showaddtickettype")
	public ModelAndView showAddTicketType() {
		ModelAndView view = new ModelAndView();
		view.addObject("ticketType", new TicketType());

		List<Category> categorys = categoryDao.findAll();
		view.addObject("categorys", categorys);

		view.setViewName(WebConst.BACK_PREFIX + "/activity/addtickettype");
		return view;
	}

	@RequestMapping("/addtickettype")
	public ModelAndView addTicketType(HttpServletRequest request, @Valid TicketType ticketType,
			BindingResult result) {
		ModelAndView view = new ModelAndView();
		if (!result.hasErrors()) {
			boolean isException = false;
			try {
				MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
				MultipartFile icon = multipartRequest.getFile("iconImg");
				activityService.addTicketType(ticketType, icon);
			} catch (SuException e) {
				view.addObject(KEY_INFO_MSG, "添加失败。" + e.getMessage());
				isException = true;
			}
			if (!isException) {
				view.addObject(KEY_INFO_MSG, "添加成功。");
			}
		}
		List<Category> categorys = categoryDao.findAll();
		view.addObject("categorys", categorys);

		view.setViewName(WebConst.BACK_PREFIX + "/activity/addtickettype");
		return view;
	}

	@RequestMapping("deletetickettype")
	public ModelAndView deleteTicketType(HttpSession session,
			@RequestParam("ticketTypeids") String ticketTypeids) {
		ModelAndView view = new ModelAndView();
		boolean isException = false;
		try {
			activityService.deleteTicketTypes(SuUtil.getIds(ticketTypeids));
		} catch (SuException e) {
			session.setAttribute(KEY_INFO_MSG, "删除失败。" + e.getMessage());
			isException = true;
		}
		if (!isException) {
			session.setAttribute(KEY_INFO_MSG, "删除成功。");
		}
		view.setViewName("redirect:" + WebConst.BACK_PREFIX + "/activity/tickettype.html");
		return view;
	}

	@RequestMapping("/showmodifytickettype")
	public ModelAndView showModifyTicketType(@RequestParam("ticketTypeid") Integer ticketTypeid) {
		ModelAndView view = new ModelAndView();
		TicketType ticketType = ticketTypeDao.get(ticketTypeid);
		view.addObject("ticketType", ticketType);

		List<Category> categorys = categoryDao.findAll();
		view.addObject("categorys", categorys);

		view.setViewName(WebConst.BACK_PREFIX + "/activity/modifytickettype");
		return view;
	}

	@RequestMapping("/modifytickettype")
	public ModelAndView modifyTicketType(HttpServletRequest request, @Valid TicketType ticketType,
			BindingResult result) {
		ModelAndView view = new ModelAndView();
		if (ticketType.getId() == null) {
			view.addObject(KEY_INFO_MSG, "修改失败。发生错误。");
		} else if (!result.hasErrors()) {
			boolean isException = false;
			try {
				MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
				MultipartFile icon = multipartRequest.getFile("iconImg");
				activityService.modifyTicketType(ticketType, icon);
			} catch (SuException e) {
				view.addObject(KEY_INFO_MSG, "修改失败。" + e.getMessage());
				isException = true;
			}
			if (!isException) {
				view.addObject(KEY_INFO_MSG, "修改成功。");
			}
		}
		List<Category> categorys = categoryDao.findAll();
		view.addObject("categorys", categorys);

		view.setViewName(WebConst.BACK_PREFIX + "/activity/modifytickettype");
		return view;
	}
}
