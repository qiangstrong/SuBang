package com.subang.controller.back;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.subang.bean.AddrDetail;
import com.subang.bean.MsgArg;
import com.subang.bean.PageArg;
import com.subang.bean.PageArg.ArgType;
import com.subang.bean.PageState;
import com.subang.bean.Pagination;
import com.subang.bean.RecordDetail;
import com.subang.bean.SearchArg;
import com.subang.controller.BaseController;
import com.subang.domain.User;
import com.subang.domain.Worker;
import com.subang.tool.BackStack;
import com.subang.util.ComUtil;
import com.subang.util.SuUtil;
import com.subang.util.WebConst;

@Controller
@RequestMapping("/back/record")
public class RecordController extends BaseController {
	private static final String VIEW_PREFIX = WebConst.BACK_PREFIX + "/record";
	private static final String INDEX_PAGE = VIEW_PREFIX + "/index";
	private static final String KEY_DATA = "recordDetails";

	@RequestMapping("/index/back")
	public ModelAndView indexBack(HttpSession session) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		backStack.pop();
		view.setViewName("redirect:" + INDEX_PAGE + ".html");
		return view;
	}

	@RequestMapping("/index")
	public ModelAndView index(HttpSession session) {
		ModelAndView view = new ModelAndView();

		BackStack backStack = getBackStack(session);
		backStack.clear("record/index");

		PageArg pageArg = getPageArg(session);
		if (pageArg == null || pageArg.getArgType() != ArgType.para) {
			PageArg paraArg = new SearchArg(WebConst.SEARCH_NULL, null);
			backStack.pushOptional(new PageState("record/index", paraArg));
		}
		if (pageArg != null) {
			switch (pageArg.getArgType()) {
			case para:
				backStack.push(new PageState("record/index", pageArg));
				break;
			case msg:
				MsgArg msgArg = (MsgArg) pageArg;
				view.addObject(msgArg.getKey(), msgArg.getMsg());
				break;
			}
		}

		PageState pageState = backStack.peek();
		view.addObject(KEY_PAGE_STATE, pageState);
		view.addObject(KEY_DES_MSG, getDesMsg(pageState.getSearchArg()));

		List<RecordDetail> recordDetails = recordService.searchRecord(pageState.getSearchArg(),
				pageState.getPageArg().getPagination());
		view.addObject(KEY_DATA, recordDetails);
		view.addObject("pagination", pageState.getPageArg().getPagination());
		view.setViewName(INDEX_PAGE);
		return view;
	}

	private String getDesMsg(SearchArg searchArg) {
		String desMsg = null;
		Integer id;
		switch (searchArg.getType()) {
		case WebConst.SEARCH_ORDER_USERID:
			id = Integer.valueOf(searchArg.getArg());
			User user = userDao.get(id);
			desMsg = "手机号：" + user.getCellnum() + "。此用户的订单如下：";
			break;
		case WebConst.SEARCH_ORDER_WORKERID:
			id = Integer.valueOf(searchArg.getArg());
			Worker worker = workerDao.get(id);
			desMsg = "姓名：" + worker.getName() + "。此工作人员的订单如下：";
			break;
		}
		return desMsg;
	}

	@RequestMapping("/list")
	public ModelAndView list(HttpSession session) {
		ModelAndView view = new ModelAndView();
		SearchArg searchArg = new SearchArg(WebConst.SEARCH_ALL, null);
		setPageArg(session, searchArg);
		view.setViewName("redirect:" + INDEX_PAGE + ".html");
		return view;
	}

	@RequestMapping("/curday")
	public ModelAndView curDay(HttpSession session) {
		ModelAndView view = new ModelAndView();
		setPageArg(session, ComUtil.curDayArg);
		view.setViewName("redirect:" + INDEX_PAGE + ".html");
		return view;
	}

	@RequestMapping("/search")
	public ModelAndView search(HttpSession session, @Valid SearchArg searchArg, BindingResult result) {
		ModelAndView view = new ModelAndView();
		if (result.hasErrors()) {
			setPageArg(session, new MsgArg(KEY_INFO_MSG, "查询参数错误。"));
		} else {
			setPageArg(session, searchArg);
		}
		view.setViewName("redirect:" + INDEX_PAGE + ".html");
		return view;
	}

	@RequestMapping("/page")
	public ModelAndView paginate(HttpSession session, @Valid Pagination pagination,
			BindingResult result) {
		ModelAndView view = new ModelAndView();
		if (!result.hasErrors()) {
			pagination.calc();
			PageState pageState = getBackStack(session).peek();
			pageState.getPageArg().setPagination(pagination);
		}

		view.setViewName("redirect:" + INDEX_PAGE + ".html");
		return view;
	}

	@RequestMapping("/detail")
	public ModelAndView getDetail(HttpSession session, @RequestParam("recordid") Integer recordid) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		backStack.push(new PageState("record/detail", null));

		RecordDetail recordDetail = recordDao.getDetail(recordid);
		User user = userDao.get(recordDetail.getUserid());
		Worker worker = workerDao.get(recordDetail.getWorkerid());
		AddrDetail addrDetail = addrDao.getDetail(recordDetail.getAddrid());
		view.addObject("recordDetail", recordDetail);
		view.addObject("user", user);
		view.addObject("worker", worker);
		view.addObject("addrDetail", addrDetail);
		view.setViewName(VIEW_PREFIX + "/detail");
		return view;
	}

	@RequestMapping("/delete")
	public ModelAndView delete(HttpSession session, @RequestParam("recordids") String recordids) {
		ModelAndView view = new ModelAndView();
		recordService.deleteRecords(SuUtil.getIds(recordids));
		setPageArg(session, new MsgArg(KEY_INFO_MSG, "删除成功。"));
		view.setViewName("redirect:" + INDEX_PAGE + ".html");
		return view;
	}

	@RequestMapping("/deliver")
	public ModelAndView deliver(HttpSession session, @RequestParam("recordid") Integer recordid) {
		ModelAndView view = new ModelAndView();
		recordService.deliverRecord(recordid);
		setPageArg(session, new MsgArg(KEY_INFO_MSG, "送达成功。"));
		view.setViewName("redirect:" + INDEX_PAGE + ".html");
		return view;
	}
}
