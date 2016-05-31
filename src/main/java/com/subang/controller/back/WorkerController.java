package com.subang.controller.back;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.subang.bean.Area;
import com.subang.bean.MsgArg;
import com.subang.bean.PageArg;
import com.subang.bean.PageArg.ArgType;
import com.subang.bean.PageState;
import com.subang.bean.SearchArg;
import com.subang.controller.BaseController;
import com.subang.domain.Worker;
import com.subang.tool.BackStack;
import com.subang.tool.SuException;
import com.subang.util.SuUtil;
import com.subang.util.WebConst;

@Controller
@RequestMapping("/back/worker")
public class WorkerController extends BaseController {

	private static final String VIEW_PREFIX = WebConst.BACK_PREFIX + "/worker";
	private static final String INDEX_PAGE = VIEW_PREFIX + "/index";
	private static final String KEY_DATA = "workers";

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
		backStack.clear("worker/index");

		PageArg pageArg = getPageArg(session);
		if (pageArg == null || pageArg.getArgType() != ArgType.para) {
			PageArg paraArg = new SearchArg(WebConst.SEARCH_NULL, null);
			backStack.pushOptional(new PageState("worker/index", paraArg));
		}
		if (pageArg != null) {
			switch (pageArg.getArgType()) {
			case para:
				backStack.push(new PageState("worker/index", pageArg));
				break;
			case msg:
				MsgArg msgArg = (MsgArg) pageArg;
				view.addObject(msgArg.getKey(), msgArg.getMsg());
				break;
			}
		}

		PageState pageState = backStack.peek();
		view.addObject(KEY_PAGE_STATE, pageState);

		List<Worker> workers = workerService.searchWorker(pageState.getSearchArg());
		view.addObject(KEY_DATA, workers);

		view.setViewName(INDEX_PAGE);
		return view;
	}

	@RequestMapping("/list")
	public ModelAndView list(HttpSession session) {
		ModelAndView view = new ModelAndView();
		SearchArg searchArg = new SearchArg(WebConst.SEARCH_ALL, null);
		setPageArg(session, searchArg);
		view.setViewName("redirect:" + INDEX_PAGE + ".html");
		return view;
	}

	@RequestMapping("/search")
	public ModelAndView search(HttpSession session, SearchArg searchArg) {
		ModelAndView view = new ModelAndView();
		setPageArg(session, searchArg);
		view.setViewName("redirect:" + INDEX_PAGE + ".html");
		return view;
	}

	@RequestMapping("/showadd")
	public ModelAndView showAdd(HttpSession session) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		backStack.push(new PageState("worker/add", null));

		view.addObject("worker", new Worker());
		view.addObject(KEY_BACK_LINK, backStack.getBackLink("worker/add"));
		view.setViewName(VIEW_PREFIX + "/add");
		return view;
	}

	@RequestMapping("/add")
	public ModelAndView add(HttpSession session, @Valid Worker worker, BindingResult result) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		if (!result.hasErrors()) {
			boolean isException = false;
			try {
				workerService.addWorker(worker);
			} catch (SuException e) {
				view.addObject(KEY_INFO_MSG, "添加失败。" + e.getMessage());
				view.addObject("worker", worker);
				isException = true;
			}
			if (!isException) {
				view.addObject(KEY_INFO_MSG, "添加成功。");
			}
		}
		view.addObject(KEY_BACK_LINK, backStack.getBackLink("worker/add"));
		view.setViewName(VIEW_PREFIX + "/add");
		return view;
	}

	@RequestMapping("/delete")
	public ModelAndView delete(HttpSession session, @RequestParam("workerids") String workerids) {
		ModelAndView view = new ModelAndView();
		boolean isException = false;
		try {
			workerService.deleteWorkers(SuUtil.getIds(workerids));
		} catch (SuException e) {
			setPageArg(session, new MsgArg(KEY_INFO_MSG, "删除失败。" + e.getMessage()));
			isException = true;
		}
		if (!isException) {
			setPageArg(session, new MsgArg(KEY_INFO_MSG, "删除成功。"));
		}
		view.setViewName("redirect:" + INDEX_PAGE + ".html");
		return view;
	}

	@RequestMapping("/showmodify")
	public ModelAndView showModify(HttpSession session, @RequestParam("workerid") Integer workerid) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		backStack.push(new PageState("worker/modify", null));

		Worker worker = workerDao.get(workerid);
		if (worker == null) {
			setPageArg(session, new MsgArg(KEY_INFO_MSG, "修改失败。数据不存在。"));
			view.setViewName("redirect:" + backStack.getBackLink("worker/modify"));
			return view;
		}
		view.addObject("worker", worker);
		view.addObject(KEY_BACK_LINK, backStack.getBackLink("worker/modify"));
		view.setViewName(VIEW_PREFIX + "/modify");
		return view;
	}

	@RequestMapping("/modify")
	public ModelAndView modify(HttpSession session, @Valid Worker worker, BindingResult result) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		if (worker.getId() == null) {
			view.addObject(KEY_INFO_MSG, "修改失败。发生错误。");
			view.addObject("worker", worker);
		} else if (!result.hasErrors()) {
			boolean isException = false;
			try {
				workerService.modifyWorker(worker);
			} catch (SuException e) {
				view.addObject(KEY_INFO_MSG, "修改失败。" + e.getMessage());
				view.addObject("worker", worker);
				isException = true;
			}
			if (!isException) {
				view.addObject(KEY_INFO_MSG, "修改成功。");
			}
		}
		view.addObject(KEY_BACK_LINK, backStack.getBackLink("worker/modify"));
		view.setViewName(VIEW_PREFIX + "/modify");
		return view;
	}

	@RequestMapping("/order")
	public ModelAndView listOrder(HttpSession session, @RequestParam("workerid") Integer workerid) {
		ModelAndView view = new ModelAndView();
		SearchArg searchArg = new SearchArg(WebConst.SEARCH_ORDER_WORKERID, workerid.toString());
		setPageArg(session, searchArg);
		view.setViewName("redirect:" + WebConst.BACK_PREFIX + "/order/index.html");
		return view;
	}

	@RequestMapping("/record")
	public ModelAndView listRecord(HttpSession session, @RequestParam("workerid") Integer workerid) {
		ModelAndView view = new ModelAndView();
		SearchArg searchArg = new SearchArg(WebConst.SEARCH_ORDER_WORKERID, workerid.toString());
		setPageArg(session, searchArg);
		view.setViewName("redirect:" + WebConst.BACK_PREFIX + "/record/index.html");
		return view;
	}

	@RequestMapping("/area/back")
	public ModelAndView areaBack(HttpSession session) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		backStack.pop();
		PageState pageState = backStack.peek();
		view.setViewName("redirect:" + VIEW_PREFIX + "/area.html?workerid="
				+ pageState.getUpperid());
		return view;
	}

	@RequestMapping("/area")
	public ModelAndView listArea(HttpSession session, @RequestParam("workerid") Integer workerid) {
		ModelAndView view = new ModelAndView();

		BackStack backStack = getBackStack(session);
		backStack.push(new PageState("worker/area", workerid));

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

		Worker worker = workerDao.get(workerid);
		String desMsg = "姓名：" + worker.getName() + "。此工作人员负责的区域如下：";
		view.addObject(KEY_DES_MSG, desMsg);

		List<Area> areas = regionDao.findAreaByWorkerid(workerid);
		view.addObject("areas", areas);

		view.addObject(KEY_BACK_LINK, backStack.getBackLink("worker/area"));
		view.setViewName(WebConst.BACK_PREFIX + "/region/area");
		return view;
	}
}
