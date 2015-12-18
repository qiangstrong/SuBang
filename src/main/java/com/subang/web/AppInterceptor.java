package com.subang.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.subang.bean.AppArg;
import com.subang.bean.Identity;
import com.subang.controller.BaseController;
import com.subang.domain.User;
import com.subang.domain.Worker;
import com.subang.util.SuUtil;
import com.subang.util.WebConst;

public class AppInterceptor extends BaseController implements HandlerInterceptor {

	private static final String URI_PREFIX = WebConst.CONTEXT_PREFIX + WebConst.APP_PREFIX;
	private static final String[] FREE_URIS = { "/user/login.html", "/user/logincellnum.html",
			"/user/add.html", "/user/chkcellnum.html", "/worker/login.html",
			"/worker/logincellnum.html" };

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2)
			throws Exception {
		if (isResURI(request.getRequestURI()) && !validate(request)) {
			SuUtil.outputJson(response, null);
			return false;
		}
		return true;
	}

	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2,
			ModelAndView arg3) throws Exception {
	}

	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2,
			Exception arg3) throws Exception {
	}

	public boolean validate(HttpServletRequest request) {
		Integer type = null;
		try {
			type = new Integer(request.getParameter("type_auth"));
		} catch (Exception e) {
		}

		// 为分拣人员提供的认证方式，以后再修改这一部分
		if (type == Identity.OTHER) {
			return true;
		}

		String cellnum = request.getParameter("cellnum_auth");
		String timestamp = request.getParameter("timestamp_auth");
		String signature = request.getParameter("signature_auth");

		if (type == null || cellnum == null || timestamp == null || signature == null) {
			return false;
		}
		String password = null;
		if (type == Identity.USER) {
			User user = userDao.getByCellnum(cellnum);
			if (user == null) {
				return false;
			}
			password = user.getPassword();
		} else if (type == Identity.WORKER) {
			Worker worker = workerDao.getByCellnum(cellnum);
			if (worker == null) {
				return false;
			}
			password = worker.getPassword();
		} else {
			return false;
		}

		AppArg appArg = new AppArg(type, cellnum, password, timestamp, signature);
		if (!appArg.validate()) {
			return false;
		}
		return true;
	}

	private boolean isResURI(String requestUri) {
		if (!isFreeURI(requestUri)) {
			return true;
		}
		return false;
	}

	private boolean isFreeURI(String requestUri) {
		requestUri = requestUri.substring(URI_PREFIX.length());
		for (String uri : FREE_URIS) {
			if (requestUri.equals(uri)) {
				return true;
			}
		}
		return false;
	}
}
