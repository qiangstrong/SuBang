package com.subang.web;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import weixin.popular.support.TokenManager;

import com.subang.util.Common;
import com.subang.util.SmsUtil;

@WebListener
public class InitListener implements ServletContextListener {

	public void contextInitialized(ServletContextEvent sce) {
		Common.init(sce.getServletContext());
		TokenManager.init(Common.getProperty("appid"), Common.getProperty("appsecret"));
		SmsUtil.init();
	}

	public void contextDestroyed(ServletContextEvent sce) {
		TokenManager.destroyed();
	}

}
