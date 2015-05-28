package com.subang.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.subang.bean.AdminBean;
import com.subang.domain.Admin;
import com.subang.exception.BackException;

/**
 * @author Qiang
 * 杂项。修改管理员用户名、密码
 */
@Controller
@RequestMapping("/misc")
public class MiscController extends BaseController {
	
	@RequestMapping("/admin")
	public ModelAndView index(HttpSession session) {
		ModelAndView view = new ModelAndView();
		session.removeAttribute(KEY_PAGE_STATE);
		Admin admin=getAdmin(session);
		view.addObject("admin", admin);
		view.setViewName("misc/admin");
		return view;
	}

	@RequestMapping("/showmodifyadmin")
	public ModelAndView showModify(HttpSession session) {
		ModelAndView view = new ModelAndView();
		Admin admin=getAdmin(session);
		AdminBean adminBean=new AdminBean(admin);
		view.addObject("adminBean", adminBean);
		view.setViewName("misc/modifyadmin");
		return view;
	}

	@RequestMapping("/modifyadmin")
	public ModelAndView modify(HttpSession session, @Valid AdminBean adminBean,BindingResult result) {
		ModelAndView view = new ModelAndView();
		if (!result.hasErrors()) {
			if (adminBean.validate()) {
				try {
					backAdminService.modifyAdmin(adminBean);
					view.addObject(KEY_INFO_MSG, "修改成功。");
				} catch (BackException e) {
					view.addObject("adminBean", adminBean);
					view.addObject(KEY_INFO_MSG, "修改失败。"+e.getMessage());
				}
			}else {
				view.addObject(KEY_INFO_MSG, "修改失败。两次输入的密码不同。");
			}
		}	
		view.setViewName("misc/modifyadmin");
		return view;
	}
}
