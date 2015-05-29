package com.subang.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.subang.bean.Area;
import com.subang.bean.PageState;
import com.subang.bean.SearchArg;
import com.subang.domain.Laundry;
import com.subang.domain.Worker;
import com.subang.exception.BackException;
import com.subang.util.Common;
import com.subang.util.WebConstant;

@Controller
@RequestMapping("/worker")
public class WorkerController extends BaseController {

	private static final String INDEX_PAGE = "/worker/index";
	private static final String KEY_DATA = "workers";

	@RequestMapping("/index")
	public ModelAndView index(HttpSession session, @RequestParam("type") int type) {
		ModelAndView view = new ModelAndView();
		
		if (type==WebConstant.INDEX_BREAK) {
			savePageState(session, new SearchArg(WebConstant.SEARCH_NULL, null));
		}
		PageState pageState=getPageState(session);


		List<Worker> workers = backAdminService.searchWorker(pageState.getSearchArg());
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
		SearchArg searchArg = new SearchArg(WebConstant.SEARCH_ALL, null);
		savePageState(session, searchArg);
		List<Worker> workers = backAdminService.searchWorker(searchArg);
		view.addObject(KEY_DATA, workers);
		view.setViewName(INDEX_PAGE);
		return view;
	}

	@RequestMapping("/search")
	public ModelAndView search(HttpSession session, SearchArg searchArg) {
		ModelAndView view = new ModelAndView();
		savePageState(session, searchArg);
		List<Worker> workers = backAdminService.searchWorker(searchArg);
		view.addObject(KEY_DATA, workers);
		view.setViewName(INDEX_PAGE);
		return view;
	}

	@RequestMapping("/showadd")
	public ModelAndView showAdd() {
		ModelAndView view = new ModelAndView();
		view.addObject("worker", new Worker());
		view.setViewName("worker/add");
		return view;
	}

	@RequestMapping("/add")
	public ModelAndView add(@Valid Worker worker, BindingResult result) {
		ModelAndView view = new ModelAndView();
		if (!result.hasErrors()) {
			backAdminService.addWorker(worker);
			view.addObject(KEY_INFO_MSG, "添加成功。");
		}
		view.setViewName("worker/add");
		return view;
	}

	@RequestMapping("/delete")
	public ModelAndView delete(HttpSession session, @RequestParam("workerids") String workerids) {
		ModelAndView view = new ModelAndView();
		boolean isException = false;
		try {
			backAdminService.deleteWorker(Common.getIds(workerids));
		} catch (BackException e) {
			session.setAttribute(KEY_INFO_MSG, "删除失败。" + e.getMessage());
			isException = true;
		}
		if (!isException) {
			session.setAttribute(KEY_INFO_MSG, "删除成功。");
		}
		view.setViewName("redirect:/worker/index.html?type=1");
		return view;
	}

	@RequestMapping("/showmodify")
	public ModelAndView showModify(@RequestParam("workerid") Integer workerid) {
		ModelAndView view = new ModelAndView();
		Worker worker = backAdminService.getWorker(workerid);
		view.addObject("worker", worker);
		view.setViewName("worker/modify");
		return view;
	}

	@RequestMapping("/modify")
	public ModelAndView modify(@Valid Worker worker,BindingResult result) {
		ModelAndView view = new ModelAndView();
		if (!result.hasErrors()) {
			backAdminService.modifyWorker(worker);
			view.addObject(KEY_INFO_MSG, "修改成功。");
		}
		view.setViewName("worker/modify");
		return view;
	}

	@RequestMapping("area")
	public ModelAndView listArea(Integer workerid) {
		ModelAndView view = new ModelAndView();
		List<Area> areas = backAdminService.listAreaByWorkerid(workerid);
		view.addObject("areas", areas);
		Worker worker = backAdminService.getWorker(workerid);
		String desMsg = "姓名：" + worker.getName() + "。此工作人员负责的区域如下：";
		view.addObject(KEY_DES_MSG, desMsg);
		view.setViewName("worker/area");
		return view;
	}
}
