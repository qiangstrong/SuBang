package com.subang.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import weixin.popular.api.SnsAPI;
import weixin.popular.bean.SnsToken;

import com.subang.controller.BaseController;
import com.subang.domain.User;
import com.subang.util.StratUtil;
import com.subang.util.StratUtil.ScoreType;
import com.subang.util.SuUtil;
import com.subang.util.WebConst;

/**
 * @author Qiang
 */
public class WeixinInterceptor extends BaseController implements HandlerInterceptor {

	private static final String URI_PREFIX = WebConst.CONTEXT_PREFIX + WebConst.WEIXIN_PREFIX;
	private static final String[] FREE_URIS = { "/index.html", "/order/pay.html", "/info/faq.html",
			"/info/showfeedback.html", "/info/feedback.html", "/price/index.html",
			"/region/scope.html", "/acticity/detail.html" };
	private static final String[] REG_URIS = { "/user/login.html", "/user/showregcellnum.html",
			"/user/cellnum.html", "/user/regauthcode.html", "/user/showregpassword.html",
			"/user/regpassword.html" };

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2)
			throws Exception {
		String code = request.getParameter("code");
		if (code == null) {
			User user = getUser(request.getSession());
			String openid = getOpenid(request.getSession());
			if ((user == null && isResURI(request.getRequestURI()))
					|| (openid == null && user == null && isRegURI(request.getRequestURI()))) {
				String redirect_uri = request.getRequestURL() + "?" + request.getQueryString();
				String url = SnsAPI.connectOauth2Authorize(SuUtil.getAppProperty("appid"),
						redirect_uri, false, null);
				response.sendRedirect(url);
				return false;
			}
		} else {
			SnsToken snsToken = SnsAPI.oauth2AccessToken(SuUtil.getAppProperty("appid"),
					SuUtil.getAppProperty("appsecret"), code);
			if (snsToken.getErrcode() != null) {
				// 可能的原因是：用户手动输入URL,伪造了code
				request.getRequestDispatcher("/content/weixin/common/error.htm").forward(request,
						response);
				return false;
			}
			if (isRegURI(request.getRequestURI())) {
				setOpenid(request.getSession(), snsToken.getOpenid());
			} else {
				User user = userDao.getByOpenid(snsToken.getOpenid());
				if (user == null) {
					// 用户还未注册
					setOpenid(request.getSession(), snsToken.getOpenid());
					request.getRequestDispatcher("/WEB-INF/content/weixin/user/login.jsp").forward(
							request, response);
					return false;
				}
				StratUtil.updateScore(user.getId(), ScoreType.login, null);
				setUser(request.getSession(), user);
			}
		}
		return true;
	}

	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2,
			ModelAndView arg3) throws Exception {
	}

	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2,
			Exception arg3) throws Exception {
	}

	// 是否是受限uri
	private boolean isResURI(String requestUri) {
		if (!isFreeURI(requestUri) && !isRegURI(requestUri)) {
			return true;
		}
		return false;
	}

	// 是否是与注册或登录相关的uri
	private boolean isFreeURI(String requestUri) {
		requestUri = requestUri.substring(URI_PREFIX.length());
		for (String uri : FREE_URIS) {
			if (requestUri.equals(uri)) {
				return true;
			}
		}
		return false;
	}

	// 是否是与注册或登录相关的uri
	private boolean isRegURI(String requestUri) {
		requestUri = requestUri.substring(URI_PREFIX.length());
		for (String uri : REG_URIS) {
			if (requestUri.equals(uri)) {
				return true;
			}
		}
		return false;
	}
}
