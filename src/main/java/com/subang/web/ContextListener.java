package com.subang.web;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import weixin.popular.support.TicketManager;
import weixin.popular.support.TokenManager;

import com.subang.util.SuUtil;
import com.subang.util.SmsUtil;
import com.subang.util.StratUtil;

@WebListener
public class ContextListener implements ServletContextListener {

	public void contextInitialized(ServletContextEvent sce) {
		SuUtil.init(sce.getServletContext());
		TokenManager.init(SuUtil.getAppProperty("appid"), SuUtil.getAppProperty("appsecret"));
		TicketManager.init(SuUtil.getAppProperty("appid"));
		SmsUtil.init();
		StratUtil.init();
	}

	public void contextDestroyed(ServletContextEvent sce) {
		StratUtil.deinit();
		TicketManager.destroyed();
		TokenManager.destroyed();		
	}

}
