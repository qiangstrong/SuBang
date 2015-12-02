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
import com.subang.domain.Banner;
import com.subang.domain.Category;
import com.subang.domain.Rebate;
import com.subang.domain.TicketType;
import com.subang.exception.SuException;
import com.subang.util.SuUtil;
import com.subang.util.WebConst;

@Controller
@RequestMapping("/back/activity")
public class ActivityController extends BaseController {

	private static final String VIEW_PREFIX = WebConst.BACK_PREFIX + "/activity";

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

		view.setViewName(VIEW_PREFIX + "/tickettype");
		return view;
	}

	@RequestMapping("/showaddtickettype")
	public ModelAndView showAddTicketType() {
		ModelAndView view = new ModelAndView();
		TicketType ticketType = new TicketType();
		view.addObject("ticketType", ticketType);

		List<Category> categorys = categoryDao.findAll();
		view.addObject("categorys", categorys);

		afterTicketType(categorys, ticketType);
		view.setViewName(VIEW_PREFIX + "/addtickettype");
		return view;
	}

	@RequestMapping("/addtickettype")
	public ModelAndView addTicketType(HttpServletRequest request, @Valid TicketType ticketType,
			BindingResult result) {
		ModelAndView view = new ModelAndView();
		preTicketType(ticketType);
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

		afterTicketType(categorys, ticketType);
		view.setViewName(VIEW_PREFIX + "/addtickettype");
		return view;
	}

	@RequestMapping("/deletetickettype")
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
		view.setViewName("redirect:" + VIEW_PREFIX + "/tickettype.html");
		return view;
	}

	@RequestMapping("/showmodifytickettype")
	public ModelAndView showModifyTicketType(@RequestParam("ticketTypeid") Integer ticketTypeid) {
		ModelAndView view = new ModelAndView();
		TicketType ticketType = ticketTypeDao.get(ticketTypeid);
		view.addObject("ticketType", ticketType);

		List<Category> categorys = categoryDao.findAll();
		categorys.add(new Category(0, "通用", null, null));
		view.addObject("categorys", categorys);

		afterTicketType(categorys, ticketType);
		view.setViewName(VIEW_PREFIX + "/modifytickettype");
		return view;
	}

	@RequestMapping("/modifytickettype")
	public ModelAndView modifyTicketType(HttpServletRequest request, @Valid TicketType ticketType,
			BindingResult result) {
		ModelAndView view = new ModelAndView();
		preTicketType(ticketType);
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

		afterTicketType(categorys, ticketType);
		view.setViewName(VIEW_PREFIX + "/modifytickettype");
		return view;
	}

	private void preTicketType(TicketType ticketType) {
		if (ticketType.getCategoryid() == 0) {
			ticketType.setCategoryid(null);
		}
	}

	private void afterTicketType(List<Category> categorys, TicketType ticketType) {
		categorys.add(new Category(0, "通用", null, null));
		if (ticketType.getCategoryid() == null) {
			ticketType.setCategoryid(0);
		}
	}

	@RequestMapping("/banner")
	public ModelAndView listBanner(HttpSession session) {
		ModelAndView view = new ModelAndView();
		invalidtePageState(session);
		List<Banner> banners = bannerDao.findAll();
		view.addObject("banners", banners);

		view.addObject(KEY_ERR_MSG, session.getAttribute(KEY_ERR_MSG));
		session.removeAttribute(KEY_ERR_MSG);
		view.addObject(KEY_INFO_MSG, session.getAttribute(KEY_INFO_MSG));
		session.removeAttribute(KEY_INFO_MSG);

		view.setViewName(VIEW_PREFIX + "/banner");
		return view;
	}

	@RequestMapping("/showaddbanner")
	public ModelAndView showAddBanner() {
		ModelAndView view = new ModelAndView();
		view.addObject("banner", new Banner());
		view.setViewName(VIEW_PREFIX + "/addbanner");
		return view;
	}

	@RequestMapping("/addbanner")
	public ModelAndView addBanner(HttpServletRequest request, @Valid Banner banner,
			BindingResult result) {
		ModelAndView view = new ModelAndView();
		if (!result.hasErrors()) {
			boolean isException = false;
			try {
				MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
				MultipartFile icon = multipartRequest.getFile("iconImg");
				activityService.addBanner(banner, icon);
			} catch (SuException e) {
				view.addObject(KEY_INFO_MSG, "添加失败。" + e.getMessage());
				view.addObject("banner", banner);
				isException = true;
			}
			if (!isException) {
				view.addObject(KEY_INFO_MSG, "添加成功。");
			}
		}
		view.setViewName(VIEW_PREFIX + "/addbanner");
		return view;
	}

	@RequestMapping("/deletebanner")
	public ModelAndView deleteBanner(HttpSession session,
			@RequestParam("bannerids") String bannerids) {
		ModelAndView view = new ModelAndView();
		activityService.deleteBanners(SuUtil.getIds(bannerids));
		session.setAttribute(KEY_INFO_MSG, "删除成功。");
		view.setViewName("redirect:" + VIEW_PREFIX + "/banner.html");
		return view;
	}

	@RequestMapping("/showmodifybanner")
	public ModelAndView showModifyBanner(@RequestParam("bannerid") Integer bannerid) {
		ModelAndView view = new ModelAndView();
		Banner banner = bannerDao.get(bannerid);
		view.addObject("banner", banner);
		view.setViewName(VIEW_PREFIX + "/modifybanner");
		return view;
	}

	@RequestMapping("/modifybanner")
	public ModelAndView modifyBanner(HttpServletRequest request, @Valid Banner banner,
			BindingResult result) {
		ModelAndView view = new ModelAndView();
		if (banner.getId() == null) {
			view.addObject(KEY_INFO_MSG, "修改失败。发生错误。");
			view.addObject("banner", banner);
		} else if (!result.hasErrors()) {
			boolean isException = false;
			try {
				MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
				MultipartFile icon = multipartRequest.getFile("iconImg");
				activityService.modifyBanner(banner, icon);
			} catch (SuException e) {
				view.addObject(KEY_INFO_MSG, "修改失败。" + e.getMessage());
				view.addObject("banner", banner);
				isException = true;
			}
			if (!isException) {
				view.addObject(KEY_INFO_MSG, "修改成功。");
			}
		}
		view.setViewName(VIEW_PREFIX + "/modifybanner");
		return view;
	}

	@RequestMapping("/rebate")
	public ModelAndView listRebate(HttpSession session) {
		ModelAndView view = new ModelAndView();
		invalidtePageState(session);
		List<Rebate> rebates = rebateDao.findAll();
		view.addObject("rebates", rebates);
		view.addObject(KEY_ERR_MSG, session.getAttribute(KEY_ERR_MSG));
		session.removeAttribute(KEY_ERR_MSG);
		view.addObject(KEY_INFO_MSG, session.getAttribute(KEY_INFO_MSG));
		session.removeAttribute(KEY_INFO_MSG);
		view.setViewName(WebConst.BACK_PREFIX + "/activity/rebate");
		return view;
	}

	@RequestMapping("/showaddrebate")
	public ModelAndView showAddRebate() {
		ModelAndView view = new ModelAndView();
		view.addObject("rebate", new Rebate());
		view.setViewName(WebConst.BACK_PREFIX + "/activity/addrebate");
		return view;
	}

	@RequestMapping("/addrebate")
	public ModelAndView addRebate(@Valid Rebate rebate, BindingResult result) {
		ModelAndView view = new ModelAndView();
		if (!result.hasErrors()) {
			boolean isException = false;
			try {
				activityService.addRebate(rebate);
			} catch (SuException e) {
				view.addObject(KEY_INFO_MSG, "添加失败。" + e.getMessage());
				isException = true;
			}
			if (!isException) {
				view.addObject(KEY_INFO_MSG, "添加成功。");
			}
		}
		view.setViewName(WebConst.BACK_PREFIX + "/activity/addrebate");
		return view;
	}

	@RequestMapping("/deleterebate")
	public ModelAndView deleteRebate(HttpSession session,
			@RequestParam("rebateids") String rebateids) {
		ModelAndView view = new ModelAndView();
		activityService.deleteRebates(SuUtil.getIds(rebateids));
		session.setAttribute(KEY_INFO_MSG, "删除成功。");
		view.setViewName("redirect:" + WebConst.BACK_PREFIX + "/activity/rebate.html");
		return view;
	}

	@RequestMapping("/showmodifyrebate")
	public ModelAndView showModifyRebate(@RequestParam("rebateid") Integer rebateid) {
		ModelAndView view = new ModelAndView();
		Rebate rebate = rebateDao.get(rebateid);
		view.addObject("rebate", rebate);
		view.setViewName(WebConst.BACK_PREFIX + "/activity/modifyrebate");
		return view;
	}

	@RequestMapping("/modifyrebate")
	public ModelAndView modifyRebate(@Valid Rebate rebate, BindingResult result) {
		ModelAndView view = new ModelAndView();
		if (rebate.getId() == null) {
			view.addObject(KEY_INFO_MSG, "修改失败。发生错误。");
		} else if (!result.hasErrors()) {
			boolean isException = false;
			try {
				activityService.modifyRebate(rebate);
			} catch (SuException e) {
				view.addObject(KEY_INFO_MSG, "修改失败。" + e.getMessage());
				view.addObject("rebate", rebate);
				isException = true;
			}
			if (!isException) {
				view.addObject(KEY_INFO_MSG, "修改成功。");
			}
		}
		view.setViewName(WebConst.BACK_PREFIX + "/activity/modifyrebate");
		return view;
	}
}
