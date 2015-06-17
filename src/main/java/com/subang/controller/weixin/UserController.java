package com.subang.controller.weixin;

import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.subang.controller.BaseController;
import com.subang.domain.User;
import com.subang.util.Common;
import com.subang.util.SmsUtil;
import com.subang.util.Validator;
import com.subang.util.WebConst;

@Controller("userController_weixin")
@RequestMapping("/weixin/user")
public class UserController extends BaseController {

	private static final String VIEW_PREFIX = WebConst.WEIXIN_PREFIX + "/user";
	private static final String INDEX_PAGE = VIEW_PREFIX + "/index";

	private static int STATE_CELLNUM = 0;
	private static int STATE_AUTHCODE = 1;

	private static String KEY_CELLNUM = "cellnum";

	@RequestMapping("/index")
	public ModelAndView index(HttpSession session) {
		ModelAndView view = new ModelAndView();
		view.addObject("user", getUser(session));
		view.setViewName(INDEX_PAGE);
		return view;
	}

	@RequestMapping("/showvalidate")
	public ModelAndView showValidate() {
		ModelAndView view = new ModelAndView();
		view.addObject("state", STATE_CELLNUM);
		view.setViewName(VIEW_PREFIX + "/validate");
		return view;
	}

	@RequestMapping("/cellnum")
	public ModelAndView getCellnum(final HttpSession session, @RequestParam("cellnum") String cellnum) {
		ModelAndView view = new ModelAndView();	
		if (!Validator.ValidCellnum(cellnum)) {
			view.addObject(KEY_INFO_MSG, "手机号码格式不正确。");
			view.addObject("state", STATE_CELLNUM);
			view.addObject(KEY_CELLNUM, cellnum);
			view.setViewName(VIEW_PREFIX + "/validate");
			return view;
		}
		
		String authcode = Common.getUserAuthcode();
		session.setAttribute(KEY_CELLNUM, cellnum);
		session.setAttribute(WebConst.KEY_USER_AUTHCODE, authcode);
		if (!SmsUtil.send(cellnum, SmsUtil.toUserContent(authcode))) {
			view.addObject(KEY_INFO_MSG, "发送验证码错误。");
			view.addObject("state", STATE_CELLNUM);
			view.addObject(KEY_CELLNUM, cellnum);
			view.setViewName(VIEW_PREFIX + "/validate");
			return view;
		}

		Timer timer = new Timer();
		TimerTask task = new TimerTask() {

			@Override
			public void run() {
				session.removeAttribute(WebConst.KEY_USER_AUTHCODE);
			}
		};
		timer.schedule(task, WebConst.AUTHCODE_INTERVAL);

		view.addObject("state", STATE_AUTHCODE);
		view.setViewName(VIEW_PREFIX + "/validate");
		return view;
	}

	@RequestMapping("/authcode")
	public ModelAndView getAuthcode(HttpSession session,
			@RequestParam("authcode") String authcodeFront) {
		ModelAndView view = new ModelAndView();
		session.removeAttribute(KEY_INFO_MSG);
		String authcodeBack = (String) session.getAttribute(WebConst.KEY_USER_AUTHCODE);
		if (authcodeBack == null) {
			view.addObject(KEY_INFO_MSG, "验证码已失效，请重新获取验证码。");
			view.addObject("state", STATE_CELLNUM);
			view.addObject(KEY_CELLNUM, (String) session.getAttribute(KEY_CELLNUM));
			view.setViewName(VIEW_PREFIX + "/validate");
			return view;
		}
		if (!authcodeBack.equalsIgnoreCase(authcodeFront.trim())) {
			view.addObject(KEY_INFO_MSG, "验证码输入错误，请重新输入。");
			view.addObject("state", STATE_AUTHCODE);
			view.setViewName(VIEW_PREFIX + "/validate");
			return view;
		}
		User user = getUser(session);
		user.setCellnum((String) session.getAttribute(KEY_CELLNUM));
		frontUserService.modifyUser(user);
		session.removeAttribute(KEY_CELLNUM);
		session.removeAttribute(WebConst.KEY_USER_AUTHCODE);
		view.addObject("user", getUser(session));
		view.setViewName(INDEX_PAGE);
		return view;
	}
}
