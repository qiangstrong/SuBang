package com.subang.controller;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.subang.bean.PageState;
import com.subang.domain.Admin;
import com.subang.domain.User;
import com.subang.service.BackAdminService;
import com.subang.service.BackStatService;
import com.subang.service.BackUserService;
import com.subang.service.FrontUserService;
import com.subang.weixin.WeixinInit;

public class BaseController {

	protected static final Logger LOG = Logger.getLogger ( BaseController.class.getName());
	
	@Autowired
	protected BackAdminService backAdminService;
	@Autowired
	protected BackUserService backUserService;
	@Autowired
	protected BackStatService backStatService;
	@Autowired
	protected FrontUserService frontUserService;

	protected static final String KEY_ADMIN = "admin";
	protected static final String KEY_USER = "user";
	protected static final String KEY_DES_MSG = "desMsg";		//显示下一级列表的描述信息，比如：订单历史，用户地址等
	protected static final String KEY_INFO_MSG = "infoMsg";		//直接显示此类消息
	protected static final String KEY_ERR_MSG = "errMsg";		//弹出显示此类消息，很少用到此种类型
	protected static final String KEY_PAGE_STATE = "pageState";

	protected PageState getPageState(HttpSession session) {
		return (PageState) session.getAttribute(KEY_PAGE_STATE);
	}

	protected void savePageState(HttpSession session, Object contentArg) {
		PageState pageState = getPageState(session);
		if (pageState == null) {
			pageState = new PageState();
			session.setAttribute(KEY_PAGE_STATE, pageState);
		}
		pageState.setContentArg(contentArg);
	}

	protected void invalidtePageState(HttpSession session){
		session.removeAttribute(KEY_PAGE_STATE);
	}
	
	protected Admin getAdmin(HttpSession session) {
		return (Admin) session.getAttribute(KEY_ADMIN);
	}

	protected void setAdmin(HttpSession session, Admin admin) {
		session.setAttribute(KEY_ADMIN, admin);
	}
	
	protected User getUser(HttpSession session) {
		return (User) session.getAttribute(KEY_USER);
	}

	protected void setUser(HttpSession session, User user) {
		session.setAttribute(KEY_USER, user);
	}
}
