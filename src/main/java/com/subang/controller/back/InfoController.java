package com.subang.controller.back;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.subang.controller.BaseController;
import com.subang.domain.Faq;
import com.subang.domain.Feedback;
import com.subang.domain.Info;
import com.subang.domain.Notice;
import com.subang.exception.SuException;
import com.subang.util.SuUtil;
import com.subang.util.WebConst;

@Controller
@RequestMapping("/back/info")
public class InfoController extends BaseController {

	@RequestMapping("/info.html")
	public ModelAndView index(HttpSession session) {
		ModelAndView view = new ModelAndView();
		invalidtePageState(session);
		List<Info> infos = infoDao.findALL();
		view.addObject("infos", infos);
		view.setViewName(WebConst.BACK_PREFIX + "/info/info");
		return view;
	}

	@RequestMapping("/showmodifyinfo")
	public ModelAndView showModify(@RequestParam("infoid") Integer infoid) {
		ModelAndView view = new ModelAndView();
		Info info = infoDao.get(infoid);
		view.addObject("info", info);
		view.setViewName(WebConst.BACK_PREFIX + "/info/modifyinfo");
		return view;
	}

	@RequestMapping("/modifyinfo")
	public ModelAndView modify(@Valid Info info, BindingResult result) {
		ModelAndView view = new ModelAndView();
		if (info.getId() == null) {
			view.addObject(KEY_INFO_MSG, "修改失败。发生错误。");
		} else if (!result.hasErrors()) {
			infoService.modifyInfo(info);
			view.addObject(KEY_INFO_MSG, "修改成功。");
		}
		view.setViewName(WebConst.BACK_PREFIX + "/info/modifyinfo");
		return view;
	}

	@RequestMapping("/faq")
	public ModelAndView listFaq(HttpSession session) {
		ModelAndView view = new ModelAndView();
		invalidtePageState(session);
		List<Faq> faqs = faqDao.findAll();
		view.addObject("faqs", faqs);
		view.addObject(KEY_ERR_MSG, session.getAttribute(KEY_ERR_MSG));
		session.removeAttribute(KEY_ERR_MSG);
		view.addObject(KEY_INFO_MSG, session.getAttribute(KEY_INFO_MSG));
		session.removeAttribute(KEY_INFO_MSG);
		view.setViewName(WebConst.BACK_PREFIX + "/info/faq");
		return view;
	}

	@RequestMapping("/showaddfaq")
	public ModelAndView showAddFaq() {
		ModelAndView view = new ModelAndView();
		view.addObject("faq", new Faq());
		view.setViewName(WebConst.BACK_PREFIX + "/info/addfaq");
		return view;
	}

	@RequestMapping("/addfaq")
	public ModelAndView addFaq(@Valid Faq faq, BindingResult result) {
		ModelAndView view = new ModelAndView();
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
		view.setViewName(WebConst.BACK_PREFIX + "/info/addfaq");
		return view;
	}

	@RequestMapping("/deletefaq")
	public ModelAndView deleteFaq(HttpSession session, @RequestParam("faqids") String faqids) {
		ModelAndView view = new ModelAndView();
		infoService.deleteFaqs(SuUtil.getIds(faqids));
		session.setAttribute(KEY_INFO_MSG, "删除成功。");
		view.setViewName("redirect:" + WebConst.BACK_PREFIX + "/info/faq.html");
		return view;
	}

	@RequestMapping("/showmodifyfaq")
	public ModelAndView showModifyFaq(@RequestParam("faqid") Integer faqid) {
		ModelAndView view = new ModelAndView();
		Faq faq = faqDao.get(faqid);
		view.addObject("faq", faq);
		view.setViewName(WebConst.BACK_PREFIX + "/info/modifyfaq");
		return view;
	}

	@RequestMapping("/modifyfaq")
	public ModelAndView modifyFaq(@Valid Faq faq, BindingResult result) {
		ModelAndView view = new ModelAndView();
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
		view.setViewName(WebConst.BACK_PREFIX + "/info/modifyfaq");
		return view;
	}

	@RequestMapping("/feedback")
	public ModelAndView listFeedback(HttpSession session) {
		ModelAndView view = new ModelAndView();
		invalidtePageState(session);
		List<Feedback> feedbacks = feedbackDao.findAll();
		view.addObject("feedbacks", feedbacks);
		view.addObject(KEY_ERR_MSG, session.getAttribute(KEY_ERR_MSG));
		session.removeAttribute(KEY_ERR_MSG);
		view.addObject(KEY_INFO_MSG, session.getAttribute(KEY_INFO_MSG));
		session.removeAttribute(KEY_INFO_MSG);
		view.setViewName(WebConst.BACK_PREFIX + "/info/feedback");
		return view;
	}

	@RequestMapping("/deletefeedback")
	public ModelAndView deleteFeedback(HttpSession session,
			@RequestParam("feedbackids") String feedbackids) {
		ModelAndView view = new ModelAndView();
		infoService.deleteFeedbacks(SuUtil.getIds(feedbackids));
		session.setAttribute(KEY_INFO_MSG, "删除成功。");
		view.setViewName("redirect:" + WebConst.BACK_PREFIX + "/info/feedback.html");
		return view;
	}

	@RequestMapping("/notice")
	public ModelAndView listNotice(HttpSession session) {
		ModelAndView view = new ModelAndView();
		invalidtePageState(session);
		List<Notice> notices = noticeDao.findAll();
		view.addObject("notices", notices);
		view.addObject(KEY_ERR_MSG, session.getAttribute(KEY_ERR_MSG));
		session.removeAttribute(KEY_ERR_MSG);
		view.addObject(KEY_INFO_MSG, session.getAttribute(KEY_INFO_MSG));
		session.removeAttribute(KEY_INFO_MSG);
		view.setViewName(WebConst.BACK_PREFIX + "/info/notice");
		return view;
	}

	@RequestMapping("/deletenotice")
	public ModelAndView deleteNotice(HttpSession session,
			@RequestParam("noticeids") String noticeids) {
		ModelAndView view = new ModelAndView();
		infoService.deleteNotices(SuUtil.getIds(noticeids));
		session.setAttribute(KEY_INFO_MSG, "删除成功。");
		view.setViewName("redirect:" + WebConst.BACK_PREFIX + "/info/notice.html");
		return view;
	}
}
