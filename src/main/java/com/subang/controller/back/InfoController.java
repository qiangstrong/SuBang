package com.subang.controller.back;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.subang.bean.MsgArg;
import com.subang.bean.PageArg;
import com.subang.bean.PageState;
import com.subang.controller.BaseController;
import com.subang.domain.Faq;
import com.subang.domain.Feedback;
import com.subang.domain.Info;
import com.subang.domain.Notice;
import com.subang.tool.BackStack;
import com.subang.tool.SuException;
import com.subang.util.SuUtil;
import com.subang.util.WebConst;

@Controller
@RequestMapping("/back/info")
public class InfoController extends BaseController {

	private static final String VIEW_PREFIX = WebConst.BACK_PREFIX + "/info";

	@RequestMapping("/info/back")
	public ModelAndView infoBack(HttpSession session) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		backStack.pop();
		view.setViewName("redirect:" + VIEW_PREFIX + "/info.html");
		return view;
	}

	@RequestMapping("/info.html")
	public ModelAndView listInfo(HttpSession session) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		backStack.clear("info/info");

		if (!backStack.isTop("info/info")) {
			backStack.push(new PageState("info/info", null));
		}
		PageArg pageArg = getPageArg(session);
		if (pageArg != null) {
			// 此页面不接受para类型的参数
			switch (pageArg.getArgType()) {
			case msg:
				MsgArg msgArg = (MsgArg) pageArg;
				view.addObject(msgArg.getKey(), msgArg.getMsg());
				break;
			}
		}

		List<Info> infos = infoDao.findALL();
		view.addObject("infos", infos);
		view.setViewName(VIEW_PREFIX + "/info");
		return view;
	}

	@RequestMapping("/showmodifyinfo")
	public ModelAndView showModifyInfo(HttpSession session, @RequestParam("infoid") Integer infoid) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		backStack.push(new PageState("info/modifyinfo", null));

		Info info = infoDao.get(infoid);
		if (info == null) {
			setPageArg(session, new MsgArg(KEY_INFO_MSG, "修改失败。数据不存在。"));
			view.setViewName("redirect:" + backStack.getBackLink("info/modifyinfo"));
			return view;
		}
		view.addObject("info", info);
		view.addObject(KEY_BACK_LINK, backStack.getBackLink("info/modifyinfo"));
		view.setViewName(VIEW_PREFIX + "/modifyinfo");
		return view;
	}

	@RequestMapping("/modifyinfo")
	public ModelAndView modifyInfo(HttpSession session, @Valid Info info, BindingResult result) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		if (info.getId() == null) {
			view.addObject(KEY_INFO_MSG, "修改失败。发生错误。");
		} else if (!result.hasErrors()) {
			infoService.modifyInfo(info);
			view.addObject(KEY_INFO_MSG, "修改成功。");
		}
		view.addObject(KEY_BACK_LINK, backStack.getBackLink("info/modifyinfo"));
		view.setViewName(VIEW_PREFIX + "/modifyinfo");
		return view;
	}

	@RequestMapping("/faq/back")
	public ModelAndView faqBack(HttpSession session) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		backStack.pop();
		view.setViewName("redirect:" + VIEW_PREFIX + "/faq.html");
		return view;
	}

	@RequestMapping("/faq")
	public ModelAndView listFaq(HttpSession session) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		backStack.clear("info/faq");

		if (!backStack.isTop("info/faq")) {
			backStack.push(new PageState("info/faq", null));
		}
		PageArg pageArg = getPageArg(session);
		if (pageArg != null) {
			// 此页面不接受para类型的参数
			switch (pageArg.getArgType()) {
			case msg:
				MsgArg msgArg = (MsgArg) pageArg;
				view.addObject(msgArg.getKey(), msgArg.getMsg());
				break;
			}
		}

		List<Faq> faqs = faqDao.findAll();
		view.addObject("faqs", faqs);
		view.setViewName(VIEW_PREFIX + "/faq");
		return view;
	}

	@RequestMapping("/showaddfaq")
	public ModelAndView showAddFaq(HttpSession session) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		backStack.push(new PageState("info/addfaq", null));

		view.addObject("faq", new Faq());
		view.addObject(KEY_BACK_LINK, backStack.getBackLink("info/addfaq"));
		view.setViewName(VIEW_PREFIX + "/addfaq");
		return view;
	}

	@RequestMapping("/addfaq")
	public ModelAndView addFaq(HttpSession session, @Valid Faq faq, BindingResult result) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		if (!result.hasErrors()) {
			boolean isException = false;
			try {
				infoService.addFaq(faq);
			} catch (SuException e) {
				view.addObject(KEY_INFO_MSG, "添加失败。" + e.getMessage());
				isException = true;
			}
			if (!isException) {
				view.addObject(KEY_INFO_MSG, "添加成功。");
			}
		}
		view.addObject(KEY_BACK_LINK, backStack.getBackLink("info/addfaq"));
		view.setViewName(VIEW_PREFIX + "/addfaq");
		return view;
	}

	@RequestMapping("/deletefaq")
	public ModelAndView deleteFaq(HttpSession session, @RequestParam("faqids") String faqids) {
		ModelAndView view = new ModelAndView();
		infoService.deleteFaqs(SuUtil.getIds(faqids));
		setPageArg(session, new MsgArg(KEY_INFO_MSG, "删除成功。"));
		view.setViewName("redirect:" + VIEW_PREFIX + "/faq.html");
		return view;
	}

	@RequestMapping("/showmodifyfaq")
	public ModelAndView showModifyFaq(HttpSession session, @RequestParam("faqid") Integer faqid) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		backStack.push(new PageState("info/modifyfaq", null));

		Faq faq = faqDao.get(faqid);
		if (faq == null) {
			setPageArg(session, new MsgArg(KEY_INFO_MSG, "修改失败。数据不存在。"));
			view.setViewName("redirect:" + backStack.getBackLink("info/modifyfaq"));
			return view;
		}
		view.addObject("faq", faq);
		view.addObject(KEY_BACK_LINK, backStack.getBackLink("info/modifyfaq"));
		view.setViewName(VIEW_PREFIX + "/modifyfaq");
		return view;
	}

	@RequestMapping("/modifyfaq")
	public ModelAndView modifyFaq(HttpSession session, @Valid Faq faq, BindingResult result) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		if (faq.getId() == null) {
			view.addObject(KEY_INFO_MSG, "修改失败。发生错误。");
		} else if (!result.hasErrors()) {
			boolean isException = false;
			try {
				infoService.modifyFaq(faq);
			} catch (SuException e) {
				view.addObject(KEY_INFO_MSG, "修改失败。" + e.getMessage());
				view.addObject("faq", faq);
				isException = true;
			}
			if (!isException) {
				view.addObject(KEY_INFO_MSG, "修改成功。");
			}
		}
		view.addObject(KEY_BACK_LINK, backStack.getBackLink("info/modifyfaq"));
		view.setViewName(VIEW_PREFIX + "/modifyfaq");
		return view;
	}

	@RequestMapping("/feedback/back")
	public ModelAndView feedbackBack(HttpSession session) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		backStack.pop();
		view.setViewName("redirect:" + VIEW_PREFIX + "/feedback.html");
		return view;
	}

	@RequestMapping("/feedback")
	public ModelAndView listFeedback(HttpSession session) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		backStack.clear("info/feedback");

		if (!backStack.isTop("info/feedback")) {
			backStack.push(new PageState("info/feedback", null));
		}
		PageArg pageArg = getPageArg(session);
		if (pageArg != null) {
			// 此页面不接受para类型的参数
			switch (pageArg.getArgType()) {
			case msg:
				MsgArg msgArg = (MsgArg) pageArg;
				view.addObject(msgArg.getKey(), msgArg.getMsg());
				break;
			}
		}

		List<Feedback> feedbacks = feedbackDao.findAll();
		view.addObject("feedbacks", feedbacks);
		view.setViewName(VIEW_PREFIX + "/feedback");
		return view;
	}

	@RequestMapping("/deletefeedback")
	public ModelAndView deleteFeedback(HttpSession session,
			@RequestParam("feedbackids") String feedbackids) {
		ModelAndView view = new ModelAndView();
		infoService.deleteFeedbacks(SuUtil.getIds(feedbackids));
		setPageArg(session, new MsgArg(KEY_INFO_MSG, "删除成功。"));
		view.setViewName("redirect:" + VIEW_PREFIX + "/feedback.html");
		return view;
	}

	@RequestMapping("/notice/back")
	public ModelAndView noticeBack(HttpSession session) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		backStack.pop();
		view.setViewName("redirect:" + VIEW_PREFIX + "/notice.html");
		return view;
	}

	@RequestMapping("/notice")
	public ModelAndView listNotice(HttpSession session) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		backStack.clear("info/notice");

		if (!backStack.isTop("info/notice")) {
			backStack.push(new PageState("info/notice", null));
		}
		PageArg pageArg = getPageArg(session);
		if (pageArg != null) {
			// 此页面不接受para类型的参数
			switch (pageArg.getArgType()) {
			case msg:
				MsgArg msgArg = (MsgArg) pageArg;
				view.addObject(msgArg.getKey(), msgArg.getMsg());
				break;
			}
		}

		List<Notice> notices = noticeDao.findAll();
		view.addObject("notices", notices);
		view.setViewName(VIEW_PREFIX + "/notice");
		return view;
	}

	@RequestMapping("/deletenotice")
	public ModelAndView deleteNotice(HttpSession session,
			@RequestParam("noticeids") String noticeids) {
		ModelAndView view = new ModelAndView();
		infoService.deleteNotices(SuUtil.getIds(noticeids));
		setPageArg(session, new MsgArg(KEY_INFO_MSG, "删除成功。"));
		view.setViewName("redirect:" + VIEW_PREFIX + "/notice.html");
		return view;
	}
}
