package com.subang.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.subang.controller.BaseController;
import com.subang.domain.Admin;
import com.subang.util.WebConst;

public class BackInterceptor extends BaseController implements HandlerInterceptor {

	private static final String URI_PREFIX = WebConst.CONTEXT_PREFIX + WebConst.BACK_PREFIX;
	private static final String[] FREE_URIS = { "/showlogin.html", "/login.html" };

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2)
			throws Exception {
		Admin admin = getAdmin(request.getSession());
		if (admin == null && isRestrictedURI(request.getRequestURI())) {
			response.sendRedirect(URI_PREFIX + "/showlogin.html");
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

	private boolean isRestrictedURI(String requestUri) {
		boolean result = true;
		requestUri = requestUri.substring(URI_PREFIX.length());
		for (String uri : FREE_URIS) {
			if (requestUri.equals(uri)) {
				result = false;
				break;
			}
		}
		return result;
	}
}
