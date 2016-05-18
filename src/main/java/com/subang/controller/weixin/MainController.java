package com.subang.controller.weixin;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import weixin.popular.bean.EventMessage;
import weixin.popular.bean.xmlmessage.XMLMessage;
import weixin.popular.bean.xmlmessage.XMLNewsMessage;
import weixin.popular.bean.xmlmessage.XMLNewsMessage.Article;
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
	protected void index(HttpServletRequest request, HttpServletResponse response, WeixinArg getArg)
			throws Exception {
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
				} else if (eventMessage.getEvent().equals("CLICK")) {
					click(eventMessage, response);
				}
			} else if (eventMessage.getMsgType().equals("text")) {
				procText(eventMessage, request, response);
			} else {
				procOthers(eventMessage, request, response);
			}
		}
	}

	/*
	 * 事件推送处理
	 */
	private void subscribe(EventMessage eventMessage, HttpServletResponse response)
			throws Exception {
		XMLTextMessage xmlTextMessage = new XMLTextMessage(eventMessage.getFromUserName(),
				eventMessage.getToUserName(), SuUtil.getMsgProperty("welcome"));
		xmlTextMessage.outputStreamWrite(response.getOutputStream());
	}

	private void unsubscribe(EventMessage eventMessage, HttpServletResponse response) {
	}

	private void location(EventMessage eventMessage, HttpServletResponse response) throws Exception {
		response.flushBuffer();
		User user = userDao.getByOpenid(eventMessage.getFromUserName());
		if (user != null) {
			Location location = new Location();
			location.setLatitude(eventMessage.getLatitude());
			location.setLongitude(eventMessage.getLongitude());
			userService.updateLocation(user.getId(), location);
		}
	}

	private void click(EventMessage eventMessage, HttpServletResponse response) throws Exception {
		XMLTextMessage xmlTextMessage;
		if (eventMessage.getEventKey().equals("contact")) {
			xmlTextMessage = new XMLTextMessage(eventMessage.getFromUserName(),
					eventMessage.getToUserName(), SuUtil.getMsgProperty("contact"));
		} else {
			xmlTextMessage = new XMLTextMessage(eventMessage.getFromUserName(),
					eventMessage.getToUserName(), SuUtil.getMsgProperty("default"));
		}
		xmlTextMessage.outputStreamWrite(response.getOutputStream());
	}

	/*
	 * 文本消息处理
	 */
	private void procText(EventMessage eventMessage, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Integer msgid = null;
		try {
			msgid = Integer.valueOf(eventMessage.getContent());
		} catch (Exception e) {
		}
		if (msgid == null) {
			XMLTextMessage xmlTextMessage = new XMLTextMessage(eventMessage.getFromUserName(),
					eventMessage.getToUserName(), SuUtil.getMsgProperty("default"));
			xmlTextMessage.outputStreamWrite(response.getOutputStream());
			return;
		}

		XMLMessage xmlMessage = null;
		Article article;
		List<Article> articles;
		switch (msgid) {
		case 1:
			article = new Article();
			article.setTitle("如何下单");
			article.setDescription("如何下单");
			article.setPicurl(SuUtil.getBasePath(request) + "image/weixin/news/11.jpg");
			article.setUrl(SuUtil.getBasePath(request) + "image/weixin/news/12.jpg");
			articles = new ArrayList<XMLNewsMessage.Article>();
			articles.add(article);
			xmlMessage = new XMLNewsMessage(eventMessage.getFromUserName(),
					eventMessage.getToUserName(), articles);
			break;
		case 2:
			article = new Article();
			article.setTitle("价目表");
			article.setDescription("价目表");
			article.setPicurl(SuUtil.getBasePath(request) + "image/weixin/news/21.jpg");
			article.setUrl(SuUtil.getBasePath(request) + "image/weixin/news/22.jpg");
			articles = new ArrayList<XMLNewsMessage.Article>();
			articles.add(article);
			xmlMessage = new XMLNewsMessage(eventMessage.getFromUserName(),
					eventMessage.getToUserName(), articles);
			break;
		case 3:
			article = new Article();
			article.setTitle("优惠券使用");
			article.setDescription("优惠券使用");
			article.setPicurl(SuUtil.getBasePath(request) + "image/weixin/news/31.jpg");
			article.setUrl(SuUtil.getBasePath(request) + "content/weixin/ticket/intro.htm");
			articles = new ArrayList<XMLNewsMessage.Article>();
			articles.add(article);
			xmlMessage = new XMLNewsMessage(eventMessage.getFromUserName(),
					eventMessage.getToUserName(), articles);
			break;
		case 4:
			article = new Article();
			article.setTitle("服务范围");
			article.setDescription("服务范围");
			article.setPicurl(SuUtil.getBasePath(request) + "image/weixin/news/41.jpg");
			article.setUrl(SuUtil.getBasePath(request) + "weixin/region/scope.html?cityid=1");
			articles = new ArrayList<XMLNewsMessage.Article>();
			articles.add(article);
			xmlMessage = new XMLNewsMessage(eventMessage.getFromUserName(),
					eventMessage.getToUserName(), articles);
			break;
		case 5:
			article = new Article();
			article.setTitle("优惠活动");
			article.setDescription("优惠活动");
			article.setPicurl(SuUtil.getBasePath(request) + "image/weixin/news/51.jpg");
			article.setUrl(SuUtil.getBasePath(request) + "image/weixin/news/52.jpg");
			articles = new ArrayList<XMLNewsMessage.Article>();
			articles.add(article);
			xmlMessage = new XMLNewsMessage(eventMessage.getFromUserName(),
					eventMessage.getToUserName(), articles);
			break;
		case 6:
			xmlMessage = new XMLTextMessage(eventMessage.getFromUserName(),
					eventMessage.getToUserName(), SuUtil.getMsgProperty("pay"));
			break;
		default:
			xmlMessage = new XMLTextMessage(eventMessage.getFromUserName(),
					eventMessage.getToUserName(), SuUtil.getMsgProperty("default"));
			break;
		}
		xmlMessage.outputStreamWrite(response.getOutputStream());
	}

	/*
	 * 其他消息处理
	 */
	private void procOthers(EventMessage eventMessage, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		XMLTextMessage xmlTextMessage = new XMLTextMessage(eventMessage.getFromUserName(),
				eventMessage.getToUserName(), SuUtil.getMsgProperty("default"));
		xmlTextMessage.outputStreamWrite(response.getOutputStream());
	}

}
