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

import com.subang.bean.MsgArg;
import com.subang.bean.PageArg;
import com.subang.bean.PageState;
import com.subang.bean.Pagination;
import com.subang.controller.BaseController;
import com.subang.domain.Banner;
import com.subang.domain.Category;
import com.subang.domain.Goods;
import com.subang.domain.Rebate;
import com.subang.domain.TicketCode;
import com.subang.domain.TicketType;
import com.subang.tool.BackStack;
import com.subang.tool.SuException;
import com.subang.util.SuUtil;
import com.subang.util.WebConst;

@Controller
@RequestMapping("/back/activity")
public class ActivityController extends BaseController {

	private static final String VIEW_PREFIX = WebConst.BACK_PREFIX + "/activity";

	@RequestMapping("/tickettype/back")
	public ModelAndView ticketTypeBack(HttpSession session) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		backStack.pop();
		view.setViewName("redirect:" + VIEW_PREFIX + "/tickettype.html");
		return view;
	}

	@RequestMapping("/tickettype")
	public ModelAndView listTicketType(HttpSession session) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		backStack.clear("activity/tickettype");

		if (!backStack.isTop("activity/tickettype")) {
			backStack.push(new PageState("activity/tickettype", null));
		}
		PageArg pageArg = getPageArg(session);
		if (pageArg != null) {
			// 此页面不接受para类型的参数
			switch (pageArg.getArgType()) {
			case msg:
				MsgArg msgArg = (MsgArg) pageArg;
				view.addObject(msgArg.getKey(), msgArg.getMsg());
				break;
			}
		}

		List<TicketType> ticketTypes = ticketTypeDao.findDetailAll();
		view.addObject("ticketTypes", ticketTypes);
		view.setViewName(VIEW_PREFIX + "/tickettype");
		return view;
	}

	@RequestMapping("/showaddtickettype")
	public ModelAndView showAddTicketType(HttpSession session) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		backStack.push(new PageState("activity/addtickettype", null));

		TicketType ticketType = new TicketType();
		view.addObject("ticketType", ticketType);

		List<Category> categorys = categoryDao.findAll();
		view.addObject("categorys", categorys);

		afterTicketType(categorys, ticketType);
		view.addObject(KEY_BACK_LINK, backStack.getBackLink("activity/addtickettype"));
		view.setViewName(VIEW_PREFIX + "/addtickettype");
		return view;
	}

	@RequestMapping("/addtickettype")
	public ModelAndView addTicketType(HttpServletRequest request, @Valid TicketType ticketType,
			BindingResult result) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(request.getSession());
		preTicketType(ticketType);
		if (!result.hasErrors()) {
			boolean isException = false;
			try {
				MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
				MultipartFile icon = multipartRequest.getFile("iconImg");
				MultipartFile poster = multipartRequest.getFile("posterImg");
				activityService.addTicketType(ticketType, icon, poster);
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
		view.addObject(KEY_BACK_LINK, backStack.getBackLink("activity/addtickettype"));
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
			setPageArg(session, new MsgArg(KEY_INFO_MSG, "删除失败。" + e.getMessage()));
			isException = true;
		}
		if (!isException) {
			setPageArg(session, new MsgArg(KEY_INFO_MSG, "删除成功。"));
		}
		view.setViewName("redirect:" + VIEW_PREFIX + "/tickettype.html");
		return view;
	}

	@RequestMapping("/showmodifytickettype")
	public ModelAndView showModifyTicketType(HttpSession session,
			@RequestParam("ticketTypeid") Integer ticketTypeid) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		backStack.push(new PageState("activity/modifytickettype", null));

		TicketType ticketType = ticketTypeDao.get(ticketTypeid);
		if (ticketType == null) {
			setPageArg(session, new MsgArg(KEY_INFO_MSG, "修改失败。数据不存在。"));
			view.setViewName("redirect:" + backStack.getBackLink("activity/modifytickettype"));
			return view;
		}
		view.addObject("ticketType", ticketType);

		List<Category> categorys = categoryDao.findAll();
		view.addObject("categorys", categorys);

		afterTicketType(categorys, ticketType);
		view.addObject(KEY_BACK_LINK, backStack.getBackLink("activity/modifytickettype"));
		view.setViewName(VIEW_PREFIX + "/modifytickettype");
		return view;
	}

	@RequestMapping("/modifytickettype")
	public ModelAndView modifyTicketType(HttpServletRequest request, @Valid TicketType ticketType,
			BindingResult result) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(request.getSession());
		preTicketType(ticketType);
		if (ticketType.getId() == null) {
			view.addObject(KEY_INFO_MSG, "修改失败。发生错误。");
		} else if (!result.hasErrors()) {
			boolean isException = false;
			try {
				MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
				MultipartFile icon = multipartRequest.getFile("iconImg");
				MultipartFile poster = multipartRequest.getFile("posterImg");
				activityService.modifyTicketType(ticketType, icon, poster);
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
		view.addObject(KEY_BACK_LINK, backStack.getBackLink("activity/modifytickettype"));
		view.setViewName(VIEW_PREFIX + "/modifytickettype");
		return view;
	}

	private void preTicketType(TicketType ticketType) {
		if (ticketType.getCategoryid() == 0) {
			ticketType.setCategoryid(null);
		}
	}

	private void afterTicketType(List<Category> categorys, TicketType ticketType) {
		categorys.add(new Category(0, true, "通用", null, null));
		if (ticketType.getCategoryid() == null) {
			ticketType.setCategoryid(0);
		}
	}

	/**
	 * 商品
	 */
	@RequestMapping("/goods/back")
	public ModelAndView goodsBack(HttpSession session) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		backStack.pop();
		view.setViewName("redirect:" + VIEW_PREFIX + "/goods.html");
		return view;
	}

	@RequestMapping("/goods")
	public ModelAndView listGoods(HttpSession session) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		backStack.clear("activity/goods");

		if (!backStack.isTop("activity/goods")) {
			backStack.push(new PageState("activity/goods", null));
		}
		PageArg pageArg = getPageArg(session);
		if (pageArg != null) {
			// 此页面不接受para类型的参数
			switch (pageArg.getArgType()) {
			case msg:
				MsgArg msgArg = (MsgArg) pageArg;
				view.addObject(msgArg.getKey(), msgArg.getMsg());
				break;
			}
		}

		List<Goods> goodss = goodsDao.findAll();
		view.addObject("goodss", goodss);
		view.setViewName(VIEW_PREFIX + "/goods");
		return view;
	}

	@RequestMapping("/showaddgoods")
	public ModelAndView showAddGoods(HttpSession session) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		backStack.push(new PageState("activity/addgoods", null));

		Goods goods = new Goods();
		view.addObject("goods", goods);

		view.addObject(KEY_BACK_LINK, backStack.getBackLink("activity/addgoods"));
		view.setViewName(VIEW_PREFIX + "/addgoods");
		return view;
	}

	@RequestMapping("/addgoods")
	public ModelAndView addGoods(HttpServletRequest request, @Valid Goods goods,
			BindingResult result) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(request.getSession());
		if (!result.hasErrors()) {
			boolean isException = false;
			try {
				MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
				MultipartFile icon = multipartRequest.getFile("iconImg");
				MultipartFile poster = multipartRequest.getFile("posterImg");
				activityService.addGoods(goods, icon, poster);
			} catch (SuException e) {
				view.addObject(KEY_INFO_MSG, "添加失败。" + e.getMessage());
				isException = true;
			}
			if (!isException) {
				view.addObject(KEY_INFO_MSG, "添加成功。");
			}
		}

		view.addObject(KEY_BACK_LINK, backStack.getBackLink("activity/addgoods"));
		view.setViewName(VIEW_PREFIX + "/addgoods");
		return view;
	}

	@RequestMapping("/deletegoods")
	public ModelAndView deleteGoods(HttpSession session, @RequestParam("goodsids") String goodsids) {
		ModelAndView view = new ModelAndView();
		boolean isException = false;
		try {
			activityService.deleteGoodss(SuUtil.getIds(goodsids));
		} catch (SuException e) {
			setPageArg(session, new MsgArg(KEY_INFO_MSG, "删除失败。" + e.getMessage()));
			isException = true;
		}
		if (!isException) {
			setPageArg(session, new MsgArg(KEY_INFO_MSG, "删除成功。"));
		}
		view.setViewName("redirect:" + VIEW_PREFIX + "/goods.html");
		return view;
	}

	@RequestMapping("/showmodifygoods")
	public ModelAndView showModifyGoods(HttpSession session,
			@RequestParam("goodsid") Integer goodsid) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		backStack.push(new PageState("activity/modifygoods", null));

		Goods goods = goodsDao.get(goodsid);
		if (goods == null) {
			setPageArg(session, new MsgArg(KEY_INFO_MSG, "修改失败。数据不存在。"));
			view.setViewName("redirect:" + backStack.getBackLink("activity/modifygoods"));
			return view;
		}
		view.addObject("goods", goods);

		view.addObject(KEY_BACK_LINK, backStack.getBackLink("activity/modifygoods"));
		view.setViewName(VIEW_PREFIX + "/modifygoods");
		return view;
	}

	@RequestMapping("/modifygoods")
	public ModelAndView modifyGoods(HttpServletRequest request, @Valid Goods goods,
			BindingResult result) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(request.getSession());
		if (goods.getId() == null) {
			view.addObject(KEY_INFO_MSG, "修改失败。发生错误。");
		} else if (!result.hasErrors()) {
			boolean isException = false;
			try {
				MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
				MultipartFile icon = multipartRequest.getFile("iconImg");
				MultipartFile poster = multipartRequest.getFile("posterImg");
				activityService.modifyGoods(goods, icon, poster);
			} catch (SuException e) {
				view.addObject(KEY_INFO_MSG, "修改失败。" + e.getMessage());
				isException = true;
			}
			if (!isException) {
				view.addObject(KEY_INFO_MSG, "修改成功。");
			}
		}

		view.addObject(KEY_BACK_LINK, backStack.getBackLink("activity/modifygoods"));
		view.setViewName(VIEW_PREFIX + "/modifygoods");
		return view;
	}

	/*
	 * 优惠码
	 */
	@RequestMapping("/ticketcode/back")
	public ModelAndView ticketCodeBack(HttpSession session) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		backStack.pop();
		view.setViewName("redirect:" + VIEW_PREFIX + "/ticketcode.html");
		return view;
	}

	@RequestMapping("/ticketcode")
	public ModelAndView listTicketCode(HttpSession session) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		backStack.clear("activity/ticketcode");

		if (!backStack.isTop("activity/ticketcode")) {
			backStack.push(new PageState("activity/ticketcode", new PageArg()));
		}
		PageArg pageArg = getPageArg(session);
		if (pageArg != null) {
			// 此页面不接受para类型的参数
			switch (pageArg.getArgType()) {
			case msg:
				MsgArg msgArg = (MsgArg) pageArg;
				view.addObject(msgArg.getKey(), msgArg.getMsg());
				break;
			}
		}

		PageState pageState = backStack.peek();
		List<TicketCode> ticketCodes = ticketCodeDao
				.findAll(pageState.getPageArg().getPagination());
		view.addObject("ticketCodes", ticketCodes);
		view.addObject("pagination", pageState.getPageArg().getPagination());
		view.setViewName(VIEW_PREFIX + "/ticketcode");
		return view;
	}

	@RequestMapping("/ticketcode/page")
	public ModelAndView ticketCodePaginate(HttpSession session, @Valid Pagination pagination,
			BindingResult result) {
		ModelAndView view = new ModelAndView();
		if (!result.hasErrors()) {
			pagination.calc();
			PageState pageState = getBackStack(session).peek();
			pageState.getPageArg().setPagination(pagination);
		}

		view.setViewName("redirect:" + VIEW_PREFIX + "/ticketcode.html");
		return view;
	}

	@RequestMapping("/showaddticketcode")
	public ModelAndView showAddTicketCode(HttpSession session) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		backStack.push(new PageState("activity/addticketcode", null));

		TicketCode ticketCode = new TicketCode();
		view.addObject("ticketCode", ticketCode);

		List<TicketType> ticketTypes = ticketTypeDao.findDetailValidAll();
		view.addObject("ticketTypes", ticketTypes);

		view.addObject(KEY_BACK_LINK, backStack.getBackLink("activity/addticketcode"));
		view.setViewName(VIEW_PREFIX + "/addticketcode");
		return view;
	}

	@RequestMapping("/addticketcode")
	public ModelAndView addTicketCode(HttpServletRequest request, @Valid TicketCode ticketCode,
			BindingResult result) throws Exception {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(request.getSession());
		if (!result.hasErrors()) {
			boolean isException = false;
			try {
				MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
				MultipartFile codenoFile = multipartRequest.getFile("codenoFile");
				activityService.addTicketCode(ticketCode, codenoFile);
			} catch (SuException e) {
				view.addObject(KEY_INFO_MSG, "添加失败。" + e.getMessage());
				isException = true;
			}
			if (!isException) {
				view.addObject(KEY_INFO_MSG, "添加成功。");
			}
		}
		List<TicketType> ticketTypes = ticketTypeDao.findDetailValidAll();
		view.addObject("ticketTypes", ticketTypes);

		view.addObject(KEY_BACK_LINK, backStack.getBackLink("activity/addticketcode"));
		view.setViewName(VIEW_PREFIX + "/addticketcode");
		return view;
	}

	@RequestMapping("/deleteticketcode")
	public ModelAndView deleteTicketCode(HttpSession session,
			@RequestParam("ticketCodeids") String ticketCodeids) {
		ModelAndView view = new ModelAndView();
		activityService.deleteTicketCodes(SuUtil.getIds(ticketCodeids));
		setPageArg(session, new MsgArg(KEY_INFO_MSG, "删除成功。"));
		view.setViewName("redirect:" + VIEW_PREFIX + "/ticketcode.html");
		return view;
	}

	@RequestMapping("/banner/back")
	public ModelAndView bannerBack(HttpSession session) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		backStack.pop();
		view.setViewName("redirect:" + VIEW_PREFIX + "/banner.html");
		return view;
	}

	@RequestMapping("/banner")
	public ModelAndView listBanner(HttpSession session) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		backStack.clear("activity/banner");

		if (!backStack.isTop("activity/banner")) {
			backStack.push(new PageState("activity/banner", null));
		}
		PageArg pageArg = getPageArg(session);
		if (pageArg != null) {
			// 此页面不接受para类型的参数
			switch (pageArg.getArgType()) {
			case msg:
				MsgArg msgArg = (MsgArg) pageArg;
				view.addObject(msgArg.getKey(), msgArg.getMsg());
				break;
			}
		}

		List<Banner> banners = bannerDao.findAll();
		view.addObject("banners", banners);
		view.setViewName(VIEW_PREFIX + "/banner");
		return view;
	}

	@RequestMapping("/showaddbanner")
	public ModelAndView showAddBanner(HttpSession session) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		backStack.push(new PageState("activity/addbanner", null));

		view.addObject("banner", new Banner());
		view.addObject(KEY_BACK_LINK, backStack.getBackLink("activity/addbanner"));
		view.setViewName(VIEW_PREFIX + "/addbanner");
		return view;
	}

	@RequestMapping("/addbanner")
	public ModelAndView addBanner(HttpServletRequest request, @Valid Banner banner,
			BindingResult result) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(request.getSession());
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
		view.addObject(KEY_BACK_LINK, backStack.getBackLink("activity/addbanner"));
		view.setViewName(VIEW_PREFIX + "/addbanner");
		return view;
	}

	@RequestMapping("/deletebanner")
	public ModelAndView deleteBanner(HttpSession session,
			@RequestParam("bannerids") String bannerids) {
		ModelAndView view = new ModelAndView();
		activityService.deleteBanners(SuUtil.getIds(bannerids));
		setPageArg(session, new MsgArg(KEY_INFO_MSG, "删除成功。"));
		view.setViewName("redirect:" + VIEW_PREFIX + "/banner.html");
		return view;
	}

	@RequestMapping("/showmodifybanner")
	public ModelAndView showModifyBanner(HttpSession session,
			@RequestParam("bannerid") Integer bannerid) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		backStack.push(new PageState("activity/modifybanner", null));

		Banner banner = bannerDao.get(bannerid);
		if (banner == null) {
			setPageArg(session, new MsgArg(KEY_INFO_MSG, "修改失败。数据不存在。"));
			view.setViewName("redirect:" + backStack.getBackLink("activity/modifybanner"));
			return view;
		}
		view.addObject("banner", banner);
		view.addObject(KEY_BACK_LINK, backStack.getBackLink("activity/modifybanner"));
		view.setViewName(VIEW_PREFIX + "/modifybanner");
		return view;
	}

	@RequestMapping("/modifybanner")
	public ModelAndView modifyBanner(HttpServletRequest request, @Valid Banner banner,
			BindingResult result) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(request.getSession());
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
		view.addObject(KEY_BACK_LINK, backStack.getBackLink("activity/modifybanner"));
		view.setViewName(VIEW_PREFIX + "/modifybanner");
		return view;
	}

	@RequestMapping("/rebate/back")
	public ModelAndView rebateBack(HttpSession session) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		backStack.pop();
		view.setViewName("redirect:" + VIEW_PREFIX + "/rebate.html");
		return view;
	}

	@RequestMapping("/rebate")
	public ModelAndView listRebate(HttpSession session) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		backStack.clear("activity/rebate");

		if (!backStack.isTop("activity/rebate")) {
			backStack.push(new PageState("activity/rebate", null));
		}
		PageArg pageArg = getPageArg(session);
		if (pageArg != null) {
			// 此页面不接受para类型的参数
			switch (pageArg.getArgType()) {
			case msg:
				MsgArg msgArg = (MsgArg) pageArg;
				view.addObject(msgArg.getKey(), msgArg.getMsg());
				break;
			}
		}

		List<Rebate> rebates = rebateDao.findAll();
		view.addObject("rebates", rebates);
		view.setViewName(VIEW_PREFIX + "/rebate");
		return view;
	}

	@RequestMapping("/showaddrebate")
	public ModelAndView showAddRebate(HttpSession session) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		backStack.push(new PageState("activity/addrebate", null));

		view.addObject("rebate", new Rebate());
		view.addObject(KEY_BACK_LINK, backStack.getBackLink("activity/addrebate"));
		view.setViewName(VIEW_PREFIX + "/addrebate");
		return view;
	}

	@RequestMapping("/addrebate")
	public ModelAndView addRebate(HttpSession session, @Valid Rebate rebate, BindingResult result) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
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
		view.addObject(KEY_BACK_LINK, backStack.getBackLink("activity/addrebate"));
		view.setViewName(VIEW_PREFIX + "/addrebate");
		return view;
	}

	@RequestMapping("/deleterebate")
	public ModelAndView deleteRebate(HttpSession session,
			@RequestParam("rebateids") String rebateids) {
		ModelAndView view = new ModelAndView();
		activityService.deleteRebates(SuUtil.getIds(rebateids));
		setPageArg(session, new MsgArg(KEY_INFO_MSG, "删除成功。"));
		view.setViewName("redirect:" + VIEW_PREFIX + "/rebate.html");
		return view;
	}

	@RequestMapping("/showmodifyrebate")
	public ModelAndView showModifyRebate(HttpSession session,
			@RequestParam("rebateid") Integer rebateid) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		backStack.push(new PageState("activity/modifyrebate", null));

		Rebate rebate = rebateDao.get(rebateid);
		if (rebate == null) {
			setPageArg(session, new MsgArg(KEY_INFO_MSG, "修改失败。数据不存在。"));
			view.setViewName("redirect:" + backStack.getBackLink("activity/modifyrebate"));
			return view;
		}
		view.addObject("rebate", rebate);
		view.addObject(KEY_BACK_LINK, backStack.getBackLink("activity/modifyrebate"));
		view.setViewName(VIEW_PREFIX + "/modifyrebate");
		return view;
	}

	@RequestMapping("/modifyrebate")
	public ModelAndView modifyRebate(HttpSession session, @Valid Rebate rebate, BindingResult result) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
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
		view.addObject(KEY_BACK_LINK, backStack.getBackLink("activity/modifyrebate"));
		view.setViewName(VIEW_PREFIX + "/modifyrebate");
		return view;
	}
}
