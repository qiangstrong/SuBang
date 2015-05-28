package com.subang.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.subang.controller.BaseController;
import com.subang.domain.Admin;

public class BackFilter extends BaseController implements Filter {

	private static final String URI_PREFIX = "/subang";
	private static final String[] FREE_URIS = { "/showlogin.html", "/login.html" };

	public void init(FilterConfig arg0) throws ServletException {
	}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		Admin admin = getAdmin(request.getSession());
		if (admin == null && isRestrictedURI(request.getRequestURI())) {
			servletRequest.getRequestDispatcher("/showlogin.html").forward(servletRequest,
					servletResponse);
			return;
		}
		filterChain.doFilter(servletRequest, servletResponse);
	}

	public void destroy() {
	}

	private boolean isRestrictedURI(String requestUri) {
		boolean result = true;
		for (String uri : FREE_URIS) {
			if (requestUri.equals(URI_PREFIX + uri)) {
				result = false;
				break;
			}
		}
		return result;
	}
}
