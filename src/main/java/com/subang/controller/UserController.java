package com.subang.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.subang.bean.PageState;
import com.subang.bean.SearchArg;
import com.subang.domain.Addr;
import com.subang.domain.User;
import com.subang.exception.BackException;
import com.subang.util.Common;
import com.subang.util.WebConstant;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

	private static final String INDEX_PAGE = "/user/index";
	private static final String KEY_DATA = "users";

	@RequestMapping("/index")
	public ModelAndView index(HttpSession session, @RequestParam("type") int type) {
		ModelAndView view = new ModelAndView();
		PageState pageState = (PageState) session.getAttribute(KEY_PAGE_STATE);
		if (type == WebConstant.INDEX_BREAK || pageState == null) {
			pageState = new PageState(new SearchArg(WebConstant.SEARCH_NULL, null));
			session.setAttribute(KEY_PAGE_STATE, pageState);
		}

		List<User> users = backUserService.searchUser(pageState.getSearchArg());
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
		SearchArg searchArg = new SearchArg(WebConstant.SEARCH_ALL, null);
		savePageState(session, searchArg);
		List<User> users = backUserService.searchUser(searchArg);
		view.addObject(KEY_DATA, users);
		view.setViewName(INDEX_PAGE);
		return view;
	}

	@RequestMapping("/search")
	public ModelAndView search(HttpSession session, SearchArg searchArg) {
		ModelAndView view = new ModelAndView();
		savePageState(session, searchArg);
		List<User> users = backUserService.searchUser(searchArg);
		view.addObject(KEY_DATA, users);
		view.setViewName(INDEX_PAGE);
		return view;
	}

	@RequestMapping("/delete")
	public ModelAndView delete(HttpSession session, @RequestParam("userids") String userids) {
		ModelAndView view = new ModelAndView();
		backUserService.deleteUser(Common.getIds(userids));
		session.setAttribute(KEY_INFO_MSG, "删除成功。");
		view.setViewName("redirect:/user/index.html?type=1");
		return view;
	}

	@RequestMapping("/order")
	public ModelAndView listOrder(HttpSession session, @RequestParam("userid") Integer userid) {
		ModelAndView view = new ModelAndView();
		SearchArg searchArg = new SearchArg(WebConstant.SEARCH_ORDER_USERID, userid.toString());
		savePageState(session, searchArg);
		User user = backUserService.getUser(userid);
		String msg = "用户昵称：" + user.getNickname() + ",手机号：" + user.getCellnum() + "。此用户的订单如下：";
		session.setAttribute(KEY_INFO_MSG, msg);
		view.setViewName("redirect:/order/index.html?type=1");
		return view;
	}

	@RequestMapping("/addr")
	public ModelAndView listAddr(HttpSession session, @RequestParam("userid") Integer userid) {
		ModelAndView view = new ModelAndView();
		List<Addr> addrs = backUserService.listAddrByUserid(userid);
		view.addObject("addrs", addrs);
		User user = backUserService.getUser(userid);
		String desMsg = "用户昵称：" + user.getNickname() + ",手机号：" + user.getCellnum() + "。此用户的地址如下：";
		view.addObject(KEY_DES_MSG, desMsg);
		view.addObject(KEY_ERR_MSG, session.getAttribute(KEY_ERR_MSG));
		session.removeAttribute(KEY_ERR_MSG);
		view.addObject(KEY_INFO_MSG, session.getAttribute(KEY_INFO_MSG));
		session.removeAttribute(KEY_INFO_MSG);
		view.setViewName("/user/addr");
		return view;
	}

	@RequestMapping("/deleteaddr")
	public ModelAndView deleteAddr(HttpSession session, @RequestParam("addrids") String addrids) {
		ModelAndView view = new ModelAndView();
		boolean isException = false;
		List<Integer> addridList = Common.getIds(addrids);
		try {
			backUserService.deleteAddr(addridList);
		} catch (BackException e) {
			session.setAttribute(KEY_INFO_MSG, "删除失败。" + e.getMessage());
			isException = true;
		}
		if (!isException) {
			session.setAttribute(KEY_INFO_MSG, "删除成功。");
		}
		Addr addr = backUserService.getAddr(addridList.get(0));
		view.setViewName("redirect:/user/addr.html?userid=" + addr.getUserid());
		return view;
	}
}
