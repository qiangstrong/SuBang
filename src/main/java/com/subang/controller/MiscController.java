package com.subang.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.subang.bean.AdminBean;
import com.subang.domain.Admin;
import com.subang.domain.Info;
import com.subang.exception.BackException;
import com.subang.util.Common;

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
	public ModelAndView modify(HttpSession session, AdminBean adminBean) {
		ModelAndView view = new ModelAndView();
		
		if (adminBean.validate()) {
			try {
				backAdminService.modifyAdmin(adminBean);
				view.addObject(KEY_INFO_MSG, "修改成功。");
			} catch (BackException e) {
				view.addObject(KEY_INFO_MSG, "修改失败。"+e.getMessage());
			}
		}else {
			view.addObject(KEY_INFO_MSG, "修改失败。两次输入的密码不同。");
		}
		view.addObject("adminBean", adminBean);		
		view.setViewName("misc/modifyadmin");
		return view;
	}
}
