package com.subang.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import weixin.popular.api.SnsAPI;
import weixin.popular.bean.SnsToken;

import com.subang.controller.BaseController;
import com.subang.domain.User;
import com.subang.util.Common;
import com.subang.util.WebConst;

public class WeixinInterceptor extends BaseController implements HandlerInterceptor {

	private static final String URI_PREFIX = WebConst.CONTEXT_PREFIX + WebConst.WEIXIN_PREFIX;
	private static final String[] FREE_URIS = { "/index.html", "/info/price.html",
			"/info/scope.html", "/info/about.html", "/misc/activity.html", "/login.html" };

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2)
			throws Exception {
		String code = request.getParameter("code");
		if (code == null) {
			User user = getUser(request.getSession());
			if (user == null && isRestrictedURI(request.getRequestURI())) {
				String redirect_uri = request.getRequestURL() + "?" + request.getQueryString();
				String url = SnsAPI.connectOauth2Authorize(Common.getProperty("appid"),
						redirect_uri, false, null);
				response.sendRedirect(url);
				return false;
			}
		} else {
			SnsToken snsToken = SnsAPI.oauth2AccessToken(Common.getProperty("appid"),
					Common.getProperty("appsecret"), code);
			// 可能的原因是：用户手动输入URL,伪造了code
			if (snsToken.getErrcode() != null) {
				request.getRequestDispatcher("/WEB-INF/content/weixin/common/error.jsp").forward(
						request, response);
				return false;
			}
			User user = frontUserService.getUserByOpenid(snsToken.getOpenid());
			// 可能的原因是：用户尚未关注微信号
			if (user == null) {
				request.getRequestDispatcher("/WEB-INF/content/weixin/common/prompt.jsp").forward(
						request, response);
				return false;
			}
			setUser(request.getSession(), user);
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
