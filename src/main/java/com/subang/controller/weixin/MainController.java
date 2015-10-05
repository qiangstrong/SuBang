package com.subang.controller.weixin;

import java.io.InputStreamReader;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import weixin.popular.bean.EventMessage;
import weixin.popular.bean.xmlmessage.XMLTextMessage;
import weixin.popular.util.ExpireSet;
import weixin.popular.util.XMLConverUtil;

import com.subang.bean.WeixinArg;
import com.subang.controller.BaseController;
import com.subang.domain.Location;
import com.subang.domain.User;
import com.subang.util.SuUtil;
import com.subang.util.WebConst;

@Controller("mainController_weixin")
@RequestMapping("/weixin")
public class MainController extends BaseController {

	private static ExpireSet<String> expireSet = new ExpireSet<String>(WebConst.EXPIRED_INTERVAL);

	@RequestMapping("/index")
	protected void service(HttpServletRequest request, HttpServletResponse response,
			WeixinArg getArg) throws Exception {
		response.setContentType("text/xml");
		ServletInputStream inputStream = request.getInputStream();
		// 验证请求签名
		if (!getArg.validate()) {
			return;
		}

		// 首次请求申请验证,返回echostr
		if (getArg.getEchostr() != null) {
			SuUtil.outputStreamWrite(response.getOutputStream(), getArg.getEchostr());
			return;
		}
		if (inputStream != null) {
			EventMessage eventMessage = XMLConverUtil.convertToObject(EventMessage.class,
					new InputStreamReader(inputStream, "utf-8"));
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
				} else if (eventMessage.getEvent().equals("LOCATION")) {
					location(eventMessage, response);
				}
			}
		}
	}

	private void subscribe(EventMessage eventMessage, HttpServletResponse response)
			throws Exception {
		XMLTextMessage xmlTextMessage = new XMLTextMessage(eventMessage.getFromUserName(),
				eventMessage.getToUserName(), SuUtil.getAppProperty("welcomeMsg"));
		xmlTextMessage.outputStreamWrite(response.getOutputStream());
	}

	private void unsubscribe(EventMessage eventMessage, HttpServletResponse response) {
	}

	private void location(EventMessage eventMessage, HttpServletResponse response) {
		User user = userDao.getByOpenid(eventMessage.getFromUserName());
		if (user != null) {
			Location location = new Location();
			location.setLatitude(eventMessage.getLatitude());
			location.setLongitude(eventMessage.getLongitude());
			userService.updateLocation(user.getId(), location);
		}
	}
}
