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
import com.subang.bean.PageState;
import com.subang.bean.SearchArg;
import com.subang.controller.BaseController;
import com.subang.domain.Worker;
import com.subang.exception.SuException;
import com.subang.util.SuUtil;
import com.subang.util.WebConst;

@Controller
@RequestMapping("/back/worker")
public class WorkerController extends BaseController {

	private static final String INDEX_PAGE = WebConst.BACK_PREFIX + "/worker/index";
	private static final String KEY_DATA = "workers";

	@RequestMapping("/index")
	public ModelAndView index(HttpSession session, @RequestParam("type") int type) {
		ModelAndView view = new ModelAndView();

		if (type == WebConst.INDEX_BREAK) {
			savePageState(session, new SearchArg(WebConst.SEARCH_NULL, null));
		}
		PageState pageState = getPageState(session);

		List<Worker> workers = workerService.searchWorker(pageState.getSearchArg());
		view.addObject(KEY_DATA, workers);
		view.addObject(KEY_ERR_MSG, session.getAttribute(KEY_ERR_MSG));
		session.removeAttribute(KEY_ERR_MSG);
		view.addObject(KEY_INFO_MSG, session.getAttribute(KEY_INFO_MSG));
		session.removeAttribute(KEY_INFO_MSG);
		view.setViewName(INDEX_PAGE);
		return view;
	}

	@RequestMapping("/list")
	public ModelAndView list(HttpSession session) {
		ModelAndView view = new ModelAndView();
		SearchArg searchArg = new SearchArg(WebConst.SEARCH_ALL, null);
		savePageState(session, searchArg);
		List<Worker> workers = workerService.searchWorker(searchArg);
		view.addObject(KEY_DATA, workers);
		view.setViewName(INDEX_PAGE);
		return view;
	}

	@RequestMapping("/search")
	public ModelAndView search(HttpSession session, SearchArg searchArg) {
		ModelAndView view = new ModelAndView();
		savePageState(session, searchArg);
		List<Worker> workers = workerService.searchWorker(searchArg);
		view.addObject(KEY_DATA, workers);
		view.setViewName(INDEX_PAGE);
		return view;
	}

	@RequestMapping("/showadd")
	public ModelAndView showAdd() {
		ModelAndView view = new ModelAndView();
		view.addObject("worker", new Worker());
		view.setViewName(WebConst.BACK_PREFIX + "/worker/add");
		return view;
	}

	@RequestMapping("/add")
	public ModelAndView add(@Valid Worker worker, BindingResult result) {
		ModelAndView view = new ModelAndView();
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
		view.setViewName(WebConst.BACK_PREFIX + "/worker/add");
		return view;
	}

	@RequestMapping("/delete")
	public ModelAndView delete(HttpSession session, @RequestParam("workerids") String workerids) {
		ModelAndView view = new ModelAndView();
		boolean isException = false;
		try {
			workerService.deleteWorkers(SuUtil.getIds(workerids));
		} catch (SuException e) {
			session.setAttribute(KEY_INFO_MSG, "删除失败。" + e.getMessage());
			isException = true;
		}
		if (!isException) {
			session.setAttribute(KEY_INFO_MSG, "删除成功。");
		}
		view.setViewName("redirect:" + WebConst.BACK_PREFIX + "/worker/index.html?type=1");
		return view;
	}

	@RequestMapping("/showmodify")
	public ModelAndView showModify(@RequestParam("workerid") Integer workerid) {
		ModelAndView view = new ModelAndView();
		Worker worker = workerDao.get(workerid);
		view.addObject("worker", worker);
		view.setViewName(WebConst.BACK_PREFIX + "/worker/modify");
		return view;
	}

	@RequestMapping("/modify")
	public ModelAndView modify(@Valid Worker worker, BindingResult result) {
		ModelAndView view = new ModelAndView();
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
		view.setViewName(WebConst.BACK_PREFIX + "/worker/modify");
		return view;
	}

	@RequestMapping("/order")
	public ModelAndView listOrder(HttpSession session, @RequestParam("workerid") Integer workerid) {
		ModelAndView view = new ModelAndView();
		SearchArg searchArg = new SearchArg(WebConst.SEARCH_ORDER_WORKERID, workerid.toString());
		savePageState(session, searchArg);
		Worker worker = workerDao.get(workerid);
		String msg = "姓名：" + worker.getName() + ",手机号：" + worker.getCellnum() + "。此工作人员负责的订单如下：";
		session.setAttribute(KEY_INFO_MSG, msg);
		view.setViewName("redirect:" + WebConst.BACK_PREFIX + "/order/index.html?type=1");
		return view;
	}

	@RequestMapping("/area")
	public ModelAndView listArea(Integer workerid) {
		ModelAndView view = new ModelAndView();
		List<Area> areas = regionDao.findAreaByWorkerid(workerid);
		view.addObject("areas", areas);
		Worker worker = workerDao.get(workerid);
		String desMsg = "姓名：" + worker.getName() + "。此工作人员负责的区域如下：";
		view.addObject(KEY_DES_MSG, desMsg);
		view.setViewName(WebConst.BACK_PREFIX + "/region/area");
		return view;
	}
}
