package com.subang.controller.back;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.subang.bean.AddrData;
import com.subang.bean.AddrDetail;
import com.subang.bean.MsgArg;
import com.subang.bean.PageArg;
import com.subang.bean.PageArg.ArgType;
import com.subang.bean.PageState;
import com.subang.bean.Pagination;
import com.subang.bean.PayArg;
import com.subang.bean.Result;
import com.subang.bean.SearchArg;
import com.subang.bean.TicketDetail;
import com.subang.controller.BaseController;
import com.subang.domain.Addr;
import com.subang.domain.Balance;
import com.subang.domain.Category;
import com.subang.domain.Order;
import com.subang.domain.Payment.PayType;
import com.subang.domain.Ticket;
import com.subang.domain.TicketType;
import com.subang.domain.User;
import com.subang.domain.User.Client;
import com.subang.tool.BackStack;
import com.subang.tool.SuException;
import com.subang.util.SuUtil;
import com.subang.util.TimeUtil;
import com.subang.util.TimeUtil.Option;
import com.subang.util.WebConst;

@Controller
@RequestMapping("/back/user")
public class UserController extends BaseController {

	private static final String VIEW_PREFIX = WebConst.BACK_PREFIX + "/user";
	private static final String INDEX_PAGE = VIEW_PREFIX + "/index";

	private static final String KEY_DATA = "users";
	private static final String KEY_CELLNUM = "cellnum";
	private static final String KEY_TIMERTASK = "timerTask_authcode";

	@RequestMapping("/index/back")
	public ModelAndView indexBack(HttpSession session) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		backStack.pop();
		view.setViewName("redirect:" + INDEX_PAGE + ".html");
		return view;
	}

	@RequestMapping("/index")
	public ModelAndView index(HttpSession session) {
		ModelAndView view = new ModelAndView();

		BackStack backStack = getBackStack(session);
		backStack.clear("user/index");

		PageArg pageArg = getPageArg(session);
		if (pageArg == null || pageArg.getArgType() != ArgType.para) {
			PageArg paraArg = new SearchArg(WebConst.SEARCH_NULL, null);
			backStack.pushOptional(new PageState("user/index", paraArg));
		}
		if (pageArg != null) {
			switch (pageArg.getArgType()) {
			case para:
				backStack.push(new PageState("user/index", pageArg));
				break;
			case msg:
				MsgArg msgArg = (MsgArg) pageArg;
				view.addObject(msgArg.getKey(), msgArg.getMsg());
				break;
			}
		}

		PageState pageState = backStack.peek();
		view.addObject(KEY_PAGE_STATE, pageState);

		List<User> users = userService.searchUser(pageState.getSearchArg(), pageState.getPageArg()
				.getPagination());
		view.addObject(KEY_DATA, users);
		view.addObject("pagination", pageState.getPageArg().getPagination());
		view.setViewName(INDEX_PAGE);
		return view;
	}

	@RequestMapping("/list")
	public ModelAndView list(HttpSession session) {
		ModelAndView view = new ModelAndView();
		SearchArg searchArg = new SearchArg(WebConst.SEARCH_ALL, null);
		setPageArg(session, searchArg);
		view.setViewName("redirect:" + INDEX_PAGE + ".html");
		return view;
	}

	@RequestMapping("/search")
	public ModelAndView search(HttpSession session, SearchArg searchArg) {
		ModelAndView view = new ModelAndView();
		setPageArg(session, searchArg);
		view.setViewName("redirect:" + INDEX_PAGE + ".html");
		return view;
	}

	@RequestMapping("/page")
	public ModelAndView paginate(HttpSession session, @Valid Pagination pagination,
			BindingResult result) {
		ModelAndView view = new ModelAndView();
		if (!result.hasErrors()) {
			pagination.calc();
			PageState pageState = getBackStack(session).peek();
			pageState.getPageArg().setPagination(pagination);
		}

		view.setViewName("redirect:" + INDEX_PAGE + ".html");
		return view;
	}

	@RequestMapping("/showadd")
	public ModelAndView showAdd(HttpSession session) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		backStack.push(new PageState("user/add", null));

		view.addObject(KEY_BACK_LINK, backStack.getBackLink("user/add"));
		view.setViewName(VIEW_PREFIX + "/add");
		return view;
	}

	// 添加用户。需要客户端校验
	@RequestMapping("/add")
	public void add(HttpSession session, @RequestParam("authcode") String authcodeFront,
			HttpServletResponse response) {
		Result result = new Result();
		String cellnum = (String) session.getAttribute(KEY_CELLNUM);
		String authcodeBack = (String) session.getAttribute(WebConst.KEY_USER_AUTHCODE);
		if (cellnum == null || authcodeBack == null) {
			result.setCode(Result.ERR);
			result.setMsg("验证码已失效，请重新获取验证码。");
			SuUtil.outputJson(response, result);
			return;
		}
		if (!authcodeBack.equalsIgnoreCase(authcodeFront.trim())) {
			result.setCode(Result.ERR);
			result.setMsg("验证码输入错误，请重新输入。");
			SuUtil.outputJson(response, result);
			return;
		}
		session.removeAttribute(KEY_CELLNUM);
		session.removeAttribute(WebConst.KEY_USER_AUTHCODE);

		User user = new User();
		user.setCellnum(cellnum);
		user.setClient(Client.back);
		boolean isException = false;
		try {
			userService.addUser(user);
		} catch (SuException e) {
			result.setCode(Result.ERR);
			result.setMsg(e.getMessage());
			isException = true;
		}
		if (!isException) {
			result.setCode(Result.OK);
		}
		SuUtil.outputJson(response, result);
	}

	@RequestMapping("/delete")
	public ModelAndView delete(HttpSession session, @RequestParam("userids") String userids) {
		ModelAndView view = new ModelAndView();
		userService.deleteUsers(SuUtil.getIds(userids));
		setPageArg(session, new MsgArg(KEY_INFO_MSG, "删除成功。"));
		view.setViewName("redirect:" + INDEX_PAGE + ".html");
		return view;
	}

	@RequestMapping("/showmodify")
	public ModelAndView showModify(HttpSession session, @RequestParam("userid") Integer userid) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		backStack.push(new PageState("user/modify", null));

		User user = userDao.get(userid);
		if (user == null) {
			setPageArg(session, new MsgArg(KEY_INFO_MSG, "修改失败。数据不存在。"));
			view.setViewName("redirect:" + backStack.getBackLink("user/modify"));
			return view;
		}
		view.addObject("user", user);
		view.addObject(KEY_BACK_LINK, backStack.getBackLink("user/modify"));
		view.setViewName(VIEW_PREFIX + "/modify");
		return view;
	}

	@RequestMapping("/modify")
	public ModelAndView modify(HttpSession session, @Valid User user, BindingResult result) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		if (user.getId() == null) {
			view.addObject(KEY_INFO_MSG, "修改失败。发生错误。");
		} else if (!result.hasErrors()) {
			boolean isException = false;
			try {
				userService.modifyUserBack(user);
			} catch (SuException e) {
				view.addObject(KEY_INFO_MSG, "修改失败。" + e.getMessage());
				isException = true;
			}
			if (!isException) {
				view.addObject(KEY_INFO_MSG, "修改成功。");
			}
		}
		view.addObject("user", user);
		view.addObject(KEY_BACK_LINK, backStack.getBackLink("user/modify"));
		view.setViewName(VIEW_PREFIX + "/modify");
		return view;
	}

	@RequestMapping("showrecharge")
	public ModelAndView showRecharge(HttpSession session, @RequestParam("userid") Integer userid) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		backStack.push(new PageState("user/recharge", null));

		PayArg payArg = new PayArg();
		payArg.setPayType(PayType.cash);
		view.addObject("payArg", payArg);
		view.addObject("userid", userid);
		view.addObject(KEY_BACK_LINK, backStack.getBackLink("user/recharge"));
		view.setViewName(VIEW_PREFIX + "/recharge");
		return view;
	}

	@RequestMapping("/recharge")
	public ModelAndView modify(HttpServletRequest request, @RequestParam("userid") Integer userid,
			@Valid PayArg payArg, BindingResult result) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(request.getSession());
		if (!result.hasErrors()) {
			payArg.setClient(User.Client.back);
			userService.prepay(payArg, userid, request);
			view.addObject(KEY_INFO_MSG, "充值成功。");
		}
		view.addObject("payArg", payArg);
		view.addObject("userid", userid);
		view.addObject(KEY_BACK_LINK, backStack.getBackLink("user/recharge"));
		view.setViewName(VIEW_PREFIX + "/recharge");
		return view;
	}

	@RequestMapping("/balance")
	public ModelAndView listBalance(HttpSession session, @RequestParam("userid") Integer userid) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		backStack.push(new PageState("user/balance", null));

		List<Balance> balances = balanceDao.findBalanceByUseridAndState(userid, Order.State.paid);
		view.addObject("balances", balances);
		User user = userDao.get(userid);
		String desMsg = "用户：" + user.getCellnum() + "。此用户的余额记录如下：";
		view.addObject(KEY_DES_MSG, desMsg);
		view.setViewName(VIEW_PREFIX + "/balance");
		return view;
	}

	@RequestMapping("/salary")
	public ModelAndView listSalary(HttpSession session, @RequestParam("userid") Integer userid) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		backStack.push(new PageState("user/salary", null));

		List<Balance> balances = balanceDao.findSalaryByUseridAndState(userid, Order.State.paid);
		view.addObject("balances", balances);
		User user = userDao.get(userid);
		String desMsg = "用户：" + user.getCellnum() + "。此用户的收益记录如下：";
		view.addObject(KEY_DES_MSG, desMsg);
		view.setViewName(VIEW_PREFIX + "/balance");
		return view;
	}

	// 列出下一级用户
	@RequestMapping("/user.html")
	public ModelAndView listUser(HttpSession session, @RequestParam("userid") Integer userid) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		backStack.push(new PageState("user/user", null));

		List<User> users = userDao.findByUserid(userid);
		view.addObject("users", users);
		User user = userDao.get(userid);
		String desMsg = "用户：" + user.getCellnum() + "。此用户的下级用户如下：";
		view.addObject(KEY_DES_MSG, desMsg);
		view.setViewName(VIEW_PREFIX + "/user");
		return view;
	}

	/**
	 * 用户地址
	 */
	@RequestMapping("/addr/back")
	public ModelAndView addrBack(HttpSession session) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		backStack.pop();
		PageState pageState = backStack.peek();
		view.setViewName("redirect:" + VIEW_PREFIX + "/addr.html?userid=" + pageState.getUpperid());
		return view;
	}

	@RequestMapping("/addr")
	public ModelAndView listAddr(HttpSession session, @RequestParam("userid") Integer userid) {
		ModelAndView view = new ModelAndView();

		BackStack backStack = getBackStack(session);
		backStack.push(new PageState("user/addr", userid));
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

		User user = userDao.get(userid);
		String desMsg = "手机号：" + user.getCellnum() + "。此用户的地址如下：";
		view.addObject(KEY_DES_MSG, desMsg);

		List<AddrDetail> addrDetails = addrDao.findDetailByUserid(userid);
		view.addObject("addrDetails", addrDetails);
		view.addObject("userid", userid);
		view.setViewName(VIEW_PREFIX + "/addr");
		return view;
	}

	@RequestMapping("/showaddaddr")
	public ModelAndView showAddAddr(HttpSession session,
			@RequestParam(value = "userid") Integer userid) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		backStack.push(new PageState("user/addaddr", null));

		Addr addr = new Addr();
		addr.setUserid(userid);
		prepareAddr(view, userDao.get(userid), addr);
		view.addObject("addr", addr);
		view.addObject(KEY_BACK_LINK, backStack.getBackLink("user/addaddr"));
		view.setViewName(VIEW_PREFIX + "/addaddr");
		return view;
	}

	@RequestMapping("/addaddr")
	public ModelAndView addAddr(HttpSession session, @Valid Addr addr, BindingResult result) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		if (!result.hasErrors()) {
			userService.addAddr(addr);
			view.addObject(KEY_INFO_MSG, "添加成功。");
		}

		prepareAddr(view, userDao.get(addr.getUserid()), addr);
		view.addObject("addr", addr);
		view.addObject(KEY_BACK_LINK, backStack.getBackLink("user/addaddr"));
		view.setViewName(VIEW_PREFIX + "/addaddr");
		return view;
	}

	private void prepareAddr(ModelAndView view, User user, Addr addr) {
		AddrData addrData;
		if (addr.getRegionid() == null) {
			addrData = regionService.getAddrDataByUserid(user.getId());
			// addr.setDetail(addrData.getDetail());
		} else {
			addrData = regionService.getAddrDataByRegionid(addr.getRegionid());
		}
		view.addObject("citys", addrData.getCitys());
		view.addObject("defaultCityid", addrData.getDefaultCityid());
		view.addObject("districts", addrData.getDistricts());
		view.addObject("defaultDistrictid", addrData.getDefaultDistrictid());
		view.addObject("regions", addrData.getRegions());
		view.addObject("defaultRegionid", addrData.getDefaultRegionid());
	}

	@RequestMapping("/deleteaddr")
	public ModelAndView deleteAddr(HttpSession session, @RequestParam("addrids") String addrids) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		backStack.push(new PageState("user/deleteaddr", null));

		boolean isException = false;
		List<Integer> addridList = SuUtil.getIds(addrids);
		try {
			userService.deleteAddrs(addridList);
		} catch (SuException e) {
			setPageArg(session, new MsgArg(KEY_INFO_MSG, "删除失败。" + e.getMessage()));
			isException = true;
		}
		if (!isException) {
			setPageArg(session, new MsgArg(KEY_INFO_MSG, "删除成功。"));
		}
		view.setViewName("redirect:" + VIEW_PREFIX + "/addr/back.html");
		return view;
	}

	/**
	 * 优惠券
	 */
	@RequestMapping("/ticket/back")
	public ModelAndView ticketBack(HttpSession session) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		backStack.pop();
		PageState pageState = backStack.peek();
		view.setViewName("redirect:" + VIEW_PREFIX + "/ticket.html?userid="
				+ pageState.getUpperid());
		return view;
	}

	@RequestMapping("/ticket")
	public ModelAndView listTicket(HttpSession session, @RequestParam("userid") Integer userid) {
		ModelAndView view = new ModelAndView();

		BackStack backStack = getBackStack(session);
		backStack.push(new PageState("user/ticket", userid));
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

		User user = userDao.get(userid);
		String desMsg = "手机号：" + user.getCellnum() + "。此用户的卡券如下：";
		view.addObject(KEY_DES_MSG, desMsg);

		List<TicketDetail> ticketDetails = ticketDao.findValidDetailByUserid(userid);
		view.addObject("ticketDetails", ticketDetails);
		view.addObject("userid", userid);
		view.setViewName(VIEW_PREFIX + "/ticket");
		return view;
	}

	@RequestMapping("/showaddticket")
	public ModelAndView showAddTicket(HttpSession session, @RequestParam("userid") Integer userid) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		backStack.push(new PageState("user/addticket", null));

		Ticket ticket = new Ticket();
		ticket.setUserid(userid);
		view.addObject("ticket", ticket);
		List<TicketType> ticketTypes = ticketTypeDao.findDetailValidAll();
		view.addObject("ticketTypes", ticketTypes);
		view.addObject(KEY_BACK_LINK, backStack.getBackLink("user/addticket"));
		view.setViewName(VIEW_PREFIX + "/addticket");
		return view;
	}

	@RequestMapping("/addticket")
	public ModelAndView addTicket(HttpSession session, @Valid Ticket ticket, BindingResult result) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		if (!result.hasErrors()) {
			userService.addTicketBack(ticket);
			view.addObject(KEY_INFO_MSG, "添加成功。");
		}
		List<TicketType> ticketTypes = ticketTypeDao.findDetailValidAll();
		view.addObject("ticketTypes", ticketTypes);
		view.addObject(KEY_BACK_LINK, backStack.getBackLink("user/addticket"));
		view.setViewName(VIEW_PREFIX + "/addticket");
		return view;
	}

	@RequestMapping("/deleteticket")
	public ModelAndView deleteTicket(HttpSession session,
			@RequestParam("ticketids") String ticketids) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		backStack.push(new PageState("user/deleteaddr", null));

		List<Integer> ticketidList = SuUtil.getIds(ticketids);
		userService.deleteTickets(ticketidList);
		setPageArg(session, new MsgArg(KEY_INFO_MSG, "删除成功。"));
		view.setViewName("redirect:" + VIEW_PREFIX + "/ticket/back.html");
		return view;
	}

	/**
	 * 订单。下单操作由于需要指定一个用户，所以放在这个模块里
	 */
	@RequestMapping("/order")
	public ModelAndView listOrder(HttpSession session, @RequestParam("userid") Integer userid) {
		ModelAndView view = new ModelAndView();
		SearchArg searchArg = new SearchArg(WebConst.SEARCH_ORDER_USERID, userid.toString());
		setPageArg(session, searchArg);
		view.setViewName("redirect:" + WebConst.BACK_PREFIX + "/order/index.html");
		return view;
	}

	@RequestMapping("/showaddorder")
	public ModelAndView showAddOrder(HttpSession session, @RequestParam("userid") Integer userid) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		backStack.push(new PageState("user/addorder", null));

		Order order = new Order();
		order.setUserid(userid);
		prepareOrder(view, userid);
		view.addObject("order", order);
		view.addObject(KEY_BACK_LINK, backStack.getBackLink("user/addorder"));
		view.setViewName(VIEW_PREFIX + "/addorder");
		return view;
	}

	@RequestMapping("/addorder")
	public ModelAndView addOrder(HttpSession session, @Valid Order order, BindingResult result) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		if (!result.hasErrors()) {
			orderService.addOrder(order);
			view.addObject(KEY_INFO_MSG, "下单成功。");
		}
		prepareOrder(view, order.getUserid());
		view.addObject("order", order);
		view.addObject(KEY_BACK_LINK, backStack.getBackLink("user/addorder"));
		view.setViewName(VIEW_PREFIX + "/addorder");
		return view;
	}

	private void prepareOrder(ModelAndView view, Integer userid) {

		User user = userDao.get(userid);
		view.addObject("defaultAddrid", user.getAddrid());

		List<AddrDetail> addrDetails = addrDao.findDetailByUserid(userid);
		view.addObject("addrDetails", addrDetails);

		List<Option> dates = TimeUtil.getDateOptions();
		view.addObject("dates", dates);

		List<Option> times = TimeUtil.getTimeOptions(dates.get(0).getValue());
		view.addObject("times", times);

		List<Category> categorys = categoryDao.findAll();
		view.addObject("categorys", categorys);
	}
}
