package com.subang.controller.weixin;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import weixin.popular.api.UserAPI;
import weixin.popular.bean.EventMessage;
import weixin.popular.bean.xmlmessage.XMLTextMessage;
import weixin.popular.support.TokenManager;
import weixin.popular.util.ExpireSet;
import weixin.popular.util.XMLConverUtil;

import com.subang.bean.weixin.GetArg;
import com.subang.controller.BaseController;
import com.subang.domain.User;
import com.subang.util.Common;
import com.subang.util.WebConst;

@Controller("mainController_weixin")
@RequestMapping("/weixin")
public class MainController extends BaseController {

	private static ExpireSet<String> expireSet = new ExpireSet<String>(WebConst.EXPIRED_INTERVAL);

	@RequestMapping("/index")
	protected void service(HttpServletRequest request, HttpServletResponse response, GetArg getArg)
			throws Exception {
		response.setContentType("text/xml");
		ServletInputStream inputStream = request.getInputStream();
		// 验证请求签名
		if (!getArg.validate()) {
			return;
		}

		// 首次请求申请验证,返回echostr
		if (getArg.getEchostr() != null) {
			Common.outputStreamWrite(response.getOutputStream(), getArg.getEchostr());
			return;
		}

		if (inputStream != null) {
			EventMessage eventMessage = XMLConverUtil.convertToObject(EventMessage.class,
					inputStream);
			String expireKey = eventMessage.getFromUserName() + "__" + eventMessage.getToUserName()
					+ "__" + eventMessage.getMsgId() + "__" + eventMessage.getCreateTime();
			if (expireSet.contains(expireKey)) {
				return; // 重复通知不作处理
			}
			expireSet.add(expireKey);

			if (eventMessage.getMsgType().equals("event")) {
				if (eventMessage.getEvent().equals("subscribe")) {
					subscribe(eventMessage, response);
				} else if (eventMessage.getEvent().equals("unsubscribe")) {
					unsubscribe(eventMessage, response);
				}
			}
		}
	}

	private void subscribe(EventMessage eventMessage, HttpServletResponse response)
			throws Exception {
		XMLTextMessage xmlTextMessage = new XMLTextMessage(eventMessage.getFromUserName(),
				eventMessage.getToUserName(), Common.getProperty("welcomeMsg"));
		xmlTextMessage.outputStreamWrite(response.getOutputStream());
		response.flushBuffer();
		
		weixin.popular.bean.User user_weixin = UserAPI.userInfo(TokenManager.getDefaultToken(),
				eventMessage.getFromUserName());
		User user = User.toUser(user_weixin);
		String headimgurl = user_weixin.getHeadimgurl();
		if (headimgurl != null && !headimgurl.equals("")) {
			Common.saveUrl(headimgurl, user.getPhoto());
		}
		frontUserService.addUser(user);
	}

	private void unsubscribe(EventMessage eventMessage, HttpServletResponse response) {
		User user = frontUserService.getUserByOpenid(eventMessage.getFromUserName());
		if (user != null) {
			user.setValid(false);
			frontUserService.modifyUser(user);
		}
	}
}
