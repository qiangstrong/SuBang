package com.subang.controller.back;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.subang.bean.AddrDetail;
import com.subang.bean.MsgArg;
import com.subang.bean.PageArg;
import com.subang.bean.PageArg.ArgType;
import com.subang.bean.PageState;
import com.subang.bean.SearchArg;
import com.subang.bean.TicketDetail;
import com.subang.controller.BaseController;
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

		view.setViewName(VIEW_PREFIX + "/ticket");
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
		view.setViewName("redirect:" + VIEW_PREFIX + "/addr/ticket.html");
		return view;
	}
}
