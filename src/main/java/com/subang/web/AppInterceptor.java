package com.subang.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.subang.controller.BaseController;
import com.subang.domain.User;
import com.subang.util.WebConst;

public class AppInterceptor extends BaseController implements HandlerInterceptor {

	private static final String URI_PREFIX = WebConst.CONTEXT_PREFIX + WebConst.APP_PREFIX;
	private static final String[] FREE_URIS = {};

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2)
			throws Exception {
		String cellnum = request.getParameter("cellnum");
		String signature = request.getParameter("signature");
		if (isResURI(request.getRequestURI()) && !validate(cellnum, signature)) {
			// TODO: 响应
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

	public boolean validate(String cellnum, String signature) {
		if (cellnum == null || signature == null) {
			return false;
		}
		User user = userDao.getByCellnum(cellnum);
		if (user == null) {
			return false;
		}
		String password = user.getPassword();
		if (!signature.equals(DigestUtils.shaHex(cellnum + password))) {
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
