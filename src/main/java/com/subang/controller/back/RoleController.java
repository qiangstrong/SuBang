package com.subang.controller.back;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.subang.bean.MsgArg;
import com.subang.bean.PageArg;
import com.subang.bean.PageState;
import com.subang.controller.BaseController;
import com.subang.domain.Admin;
import com.subang.tool.BackStack;
import com.subang.tool.SuException;
import com.subang.util.WebConst;

@Controller
@RequestMapping("/back/role")
public class RoleController extends BaseController {

	private static final String VIEW_PREFIX = WebConst.BACK_PREFIX + "/role";

	@RequestMapping("/admin/back")
	public ModelAndView indexBack(HttpSession session) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		backStack.pop();
		view.setViewName("redirect:" + VIEW_PREFIX + "/admin.html");
		return view;
	}

	@RequestMapping("/admin")
	public ModelAndView getAdmin1(HttpSession session) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		backStack.clear("role/admin");

		if (!backStack.isTop("region/city")) {
			backStack.push(new PageState("role/admin", null));
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

		Admin admin = getAdmin(session);
		view.addObject("admin", admin);
		view.setViewName(VIEW_PREFIX + "/admin");
		return view;
	}

	@RequestMapping("/showmodifyadmin")
	public ModelAndView showModify(HttpSession session) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		backStack.push(new PageState("role/modifyadmin", null));

		Admin admin = getAdmin(session);
		view.addObject("admin", admin);
		view.addObject(KEY_BACK_LINK, backStack.getBackLink("role/modifyadmin"));
		view.setViewName(VIEW_PREFIX + "/modifyadmin");
		return view;
	}

	@RequestMapping("/modifyadmin")
	public ModelAndView modify(HttpSession session, @Valid Admin admin, BindingResult result) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		if (admin.getId() == null) {
			view.addObject(KEY_INFO_MSG, "修改失败。发生错误。");
		} else if (!result.hasErrors()) {
			try {
				roleService.modifyAdmin(admin);
				view.addObject(KEY_INFO_MSG, "修改成功。");
			} catch (SuException e) {
				view.addObject(KEY_INFO_MSG, "修改失败。" + e.getMessage());
			}
		}
		view.addObject(KEY_BACK_LINK, backStack.getBackLink("role/modifyadmin"));
		view.setViewName(VIEW_PREFIX + "/modifyadmin");
		return view;
	}
}
