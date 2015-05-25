package com.subang.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.subang.bean.PageState;
import com.subang.bean.SearchArg;
import com.subang.domain.Admin;
import com.subang.service.BackAdminService;
import com.subang.service.BackStatService;
import com.subang.service.BackUserService;
import com.subang.service.FrontUserService;

public class BaseController {

	@Autowired
	protected BackAdminService backAdminService;
	@Autowired
	protected BackUserService backUserService;
	@Autowired
	protected BackStatService backStatService;
	@Autowired
	protected FrontUserService frontUserService;

	protected static final String KEY_ADMIN = "admin";
	protected static final String KEY_DES_MSG = "desMsg";		//显示下一级列表的描述信息，比如：订单历史，用户地址等
	protected static final String KEY_INFO_MSG = "infoMsg";		//直接显示此类消息
	protected static final String KEY_ERR_MSG = "errMsg";		//弹出显示此类消息，很少用到此种类型
	protected static final String KEY_PAGE_STATE = "pageState";

	protected PageState getPageState(HttpSession session) {
		return (PageState) session.getAttribute(KEY_PAGE_STATE);
	}

	protected void savePageState(HttpSession session, SearchArg searchArg) {
		PageState pageState = getPageState(session);
		if (pageState == null) {
			pageState = new PageState();
			session.setAttribute(KEY_PAGE_STATE, pageState);
		}
		pageState.setSearchArg(searchArg);
	}

	protected Admin getAdmin(HttpSession session) {
		return (Admin) session.getAttribute(KEY_ADMIN);
	}

	protected void setAdmin(HttpSession session, Admin admin) {
		session.setAttribute(KEY_ADMIN, admin);
	}

}
