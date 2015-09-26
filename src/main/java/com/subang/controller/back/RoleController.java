package com.subang.controller.back;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.subang.controller.BaseController;
import com.subang.domain.Admin;
import com.subang.exception.SuException;
import com.subang.util.WebConst;

@Controller
@RequestMapping("/back/role")
public class RoleController extends BaseController {

	@RequestMapping("/admin")
	public ModelAndView index(HttpSession session) {
		ModelAndView view = new ModelAndView();
		invalidtePageState(session);
		Admin admin = getAdmin(session);
		view.addObject("admin", admin);
		view.setViewName(WebConst.BACK_PREFIX + "/role/admin");
		return view;
	}

	@RequestMapping("/showmodifyadmin")
	public ModelAndView showModify(HttpSession session) {
		ModelAndView view = new ModelAndView();
		Admin admin = getAdmin(session);
		view.addObject("admin", admin);
		view.setViewName(WebConst.BACK_PREFIX + "/role/modifyadmin");
		return view;
	}

	@RequestMapping("/modifyadmin")
	public ModelAndView modify(@Valid Admin admin, BindingResult result) {
		ModelAndView view = new ModelAndView();
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
		view.setViewName(WebConst.BACK_PREFIX + "/role/modifyadmin");
		return view;
	}
}
