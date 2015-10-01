package com.subang.controller.back;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.subang.bean.AddrDetail;
import com.subang.bean.PageState;
import com.subang.bean.SearchArg;
import com.subang.bean.TicketDetail;
import com.subang.controller.BaseController;
import com.subang.domain.Addr;
import com.subang.domain.Ticket;
import com.subang.domain.User;
import com.subang.exception.SuException;
import com.subang.util.SuUtil;
import com.subang.util.WebConst;

@Controller
@RequestMapping("/back/user")
public class UserController extends BaseController {

	private static final String INDEX_PAGE = WebConst.BACK_PREFIX + "/user/index";
	private static final String KEY_DATA = "users";

	@RequestMapping("/index")
	public ModelAndView index(HttpSession session, @RequestParam("type") int type) {
		ModelAndView view = new ModelAndView();

		if (type == WebConst.INDEX_BREAK) {
			savePageState(session, new SearchArg(WebConst.SEARCH_NULL, null));
		}
		PageState pageState = getPageState(session);

		List<User> users = userService.searchUser(pageState.getSearchArg());
		view.addObject(KEY_DATA, users);
		view.addObject(KEY_ERR_MSG, session.getAttribute(KEY_ERR_MSG));
		session.removeAttribute(KEY_ERR_MSG);
		view.addObject(KEY_INFO_MSG, session.getAttribute(KEY_INFO_MSG));
		session.removeAttribute(KEY_INFO_MSG);
		view.setViewName(INDEX_PAGE);
		return view;
	}

	@RequestMapping("/list")
	public ModelAndView list(HttpSession session) {
		ModelAndView view = new ModelAndView();
		SearchArg searchArg = new SearchArg(WebConst.SEARCH_ALL, null);
		savePageState(session, searchArg);
		List<User> users = userService.searchUser(searchArg);
		view.addObject(KEY_DATA, users);
		view.setViewName(INDEX_PAGE);
		return view;
	}

	@RequestMapping("/search")
	public ModelAndView search(HttpSession session, SearchArg searchArg) {
		ModelAndView view = new ModelAndView();
		savePageState(session, searchArg);
		List<User> users = userService.searchUser(searchArg);
		view.addObject(KEY_DATA, users);
		view.setViewName(INDEX_PAGE);
		return view;
	}

	@RequestMapping("/delete")
	public ModelAndView delete(HttpSession session, @RequestParam("userids") String userids) {
		ModelAndView view = new ModelAndView();
		userService.deleteUsers(SuUtil.getIds(userids));
		session.setAttribute(KEY_INFO_MSG, "删除成功。");
		view.setViewName("redirect:" + WebConst.BACK_PREFIX + "/user/index.html?type=1");
		return view;
	}

	@RequestMapping("/order")
	public ModelAndView listOrder(HttpSession session, @RequestParam("userid") Integer userid) {
		ModelAndView view = new ModelAndView();
		SearchArg searchArg = new SearchArg(WebConst.SEARCH_ORDER_USERID, userid.toString());
		savePageState(session, searchArg);
		User user = userDao.get(userid);
		String msg = "用户昵称：" + user.getNickname() + ",手机号：" + user.getCellnum() + "。此用户的订单如下：";
		session.setAttribute(KEY_INFO_MSG, msg);
		view.setViewName("redirect:" + WebConst.BACK_PREFIX + "/order/index.html?type=1");
		return view;
	}

	@RequestMapping("/addr")
	public ModelAndView listAddr(HttpSession session, @RequestParam("userid") Integer userid) {
		ModelAndView view = new ModelAndView();
		List<AddrDetail> addrDetails = addrDao.findDetailByUserid(userid);
		view.addObject("addrDetails", addrDetails);
		User user = userDao.get(userid);
		String desMsg = "用户昵称：" + user.getNickname() + ",手机号：" + user.getCellnum() + "。此用户的地址如下：";
		view.addObject(KEY_DES_MSG, desMsg);
		view.addObject(KEY_ERR_MSG, session.getAttribute(KEY_ERR_MSG));
		session.removeAttribute(KEY_ERR_MSG);
		view.addObject(KEY_INFO_MSG, session.getAttribute(KEY_INFO_MSG));
		session.removeAttribute(KEY_INFO_MSG);
		view.setViewName(WebConst.BACK_PREFIX + "/user/addr");
		return view;
	}

	@RequestMapping("/deleteaddr")
	public ModelAndView deleteAddr(HttpSession session, @RequestParam("addrids") String addrids) {
		ModelAndView view = new ModelAndView();
		boolean isException = false;
		List<Integer> addridList = SuUtil.getIds(addrids);
		try {
			userService.deleteAddrs(addridList);
		} catch (SuException e) {
			session.setAttribute(KEY_INFO_MSG, "删除失败。" + e.getMessage());
			isException = true;
		}
		if (!isException) {
			session.setAttribute(KEY_INFO_MSG, "删除成功。");
		}
		Addr addr = addrDao.get(addridList.get(0));
		view.setViewName("redirect:" + WebConst.BACK_PREFIX + "/user/addr.html?userid="
				+ addr.getUserid());
		return view;
	}

	@RequestMapping("/ticket")
	public ModelAndView listTicket(HttpSession session, @RequestParam("userid") Integer userid) {
		ModelAndView view = new ModelAndView();
		List<TicketDetail> ticketDetails = ticketDao.findValidDetailByUserid(userid);
		view.addObject("ticketDetails", ticketDetails);
		User user = userDao.get(userid);
		String desMsg = "手机号：" + user.getCellnum() + "。此用户的卡券如下：";
		view.addObject(KEY_DES_MSG, desMsg);

		view.addObject(KEY_ERR_MSG, session.getAttribute(KEY_ERR_MSG));
		session.removeAttribute(KEY_ERR_MSG);
		view.addObject(KEY_INFO_MSG, session.getAttribute(KEY_INFO_MSG));
		session.removeAttribute(KEY_INFO_MSG);

		view.setViewName(WebConst.BACK_PREFIX + "/user/ticket");
		return view;
	}

	@RequestMapping("/deleteticket")
	public ModelAndView deleteTicket(HttpSession session,
			@RequestParam("ticketids") String ticketids) {
		ModelAndView view = new ModelAndView();
		List<Integer> ticketidList = SuUtil.getIds(ticketids);
		userService.deleteTickets(ticketidList);
		session.setAttribute(KEY_INFO_MSG, "删除成功。");
		Ticket ticket = ticketDao.get(ticketidList.get(0));
		view.setViewName("redirect:" + WebConst.BACK_PREFIX + "/user/ticket.html?userid="
				+ ticket.getUserid());
		return view;
	}
}
