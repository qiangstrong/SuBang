package com.subang.controller.back;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.subang.bean.AddrDetail;
import com.subang.bean.MsgArg;
import com.subang.bean.PageArg;
import com.subang.bean.PageArg.ArgType;
import com.subang.bean.PageState;
import com.subang.bean.PayArg;
import com.subang.bean.PayArg.Client;
import com.subang.bean.SearchArg;
import com.subang.bean.TicketDetail;
import com.subang.controller.BaseController;
import com.subang.domain.Balance;
import com.subang.domain.Order;
import com.subang.domain.Payment.PayType;
import com.subang.domain.Ticket;
import com.subang.domain.TicketType;
import com.subang.domain.User;
import com.subang.tool.BackStack;
import com.subang.tool.SuException;
import com.subang.util.SuUtil;
import com.subang.util.WebConst;

@Controller
@RequestMapping("/back/user")
public class UserController extends BaseController {

	private static final String VIEW_PREFIX = WebConst.BACK_PREFIX + "/user";
	private static final String INDEX_PAGE = VIEW_PREFIX + "/index";
	private static final String KEY_DATA = "users";

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

		List<User> users = userService.searchUser(pageState.getSearchArg());
		view.addObject(KEY_DATA, users);
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
			payArg.setClient(Client.back);
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

		List<Balance> balances = balanceDao.findDetailByUseridAndState(userid, Order.State.paid);
		view.addObject("balances", balances);
		User user = userDao.get(userid);
		String desMsg = "用户：" + user.getCellnum() + "。此用户的余额记录如下：";
		view.addObject(KEY_DES_MSG, desMsg);
		view.setViewName(VIEW_PREFIX + "/balance");
		return view;
	}

	@RequestMapping("/order")
	public ModelAndView listOrder(HttpSession session, @RequestParam("userid") Integer userid) {
		ModelAndView view = new ModelAndView();
		SearchArg searchArg = new SearchArg(WebConst.SEARCH_ORDER_USERID, userid.toString());
		setPageArg(session, searchArg);
		view.setViewName("redirect:" + WebConst.BACK_PREFIX + "/order/index.html");
		return view;
	}

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

		view.setViewName(VIEW_PREFIX + "/addr");
		return view;
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
}
