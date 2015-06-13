package com.subang.controller.back;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.subang.bean.PageState;
import com.subang.bean.SearchArg;
import com.subang.controller.BaseController;
import com.subang.domain.Laundry;
import com.subang.exception.BackException;
import com.subang.util.Common;
import com.subang.util.WebConst;

@Controller
@RequestMapping("/back/laundry")
public class LaundryController extends BaseController {

	private static final String INDEX_PAGE = WebConst.BACK_PREFIX+"/laundry/index";
	private static final String KEY_DATA = "laundrys";

	@RequestMapping("/index")
	public ModelAndView index(HttpSession session, @RequestParam("type") int type) {
		ModelAndView view = new ModelAndView();
		
		if (type==WebConst.INDEX_BREAK) {
			savePageState(session, new SearchArg(WebConst.SEARCH_NULL, null));
		}
		PageState pageState=getPageState(session);
		
		List<Laundry> laundrys = backAdminService.searchLaundry(pageState.getSearchArg());
		view.addObject(KEY_DATA, laundrys);
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
		List<Laundry> laundrys = backAdminService.searchLaundry(searchArg);
		view.addObject(KEY_DATA, laundrys);
		view.setViewName(INDEX_PAGE);
		return view;
	}

	@RequestMapping("/search")
	public ModelAndView search(HttpSession session, SearchArg searchArg) {
		ModelAndView view = new ModelAndView();
		savePageState(session, searchArg);
		List<Laundry> laundrys = backAdminService.searchLaundry(searchArg);
		view.addObject(KEY_DATA, laundrys);
		view.setViewName(INDEX_PAGE);
		return view;
	}

	@RequestMapping("/showadd")
	public ModelAndView showAdd() {
		ModelAndView view = new ModelAndView();
		view.addObject("laundry", new Laundry());
		view.setViewName( WebConst.BACK_PREFIX+"/laundry/add");
		return view;
	}

	@RequestMapping("/add")
	public ModelAndView add(@Valid Laundry laundry, BindingResult result) {
		ModelAndView view = new ModelAndView();
		if (!result.hasErrors()) {
			boolean isException = false;
			try {
				backAdminService.addLaundry(laundry);
			} catch (BackException e) {
				view.addObject(KEY_INFO_MSG, "添加失败。" + e.getMessage());
				view.addObject("laundry", laundry);
				isException = true;
			}
			if (!isException) {
				view.addObject(KEY_INFO_MSG, "添加成功。");
			}
		}
		view.setViewName( WebConst.BACK_PREFIX+"/laundry/add");
		return view;
	}

	@RequestMapping("/delete")
	public ModelAndView delete(HttpSession session, @RequestParam("laundryids") String laundryids) {
		ModelAndView view = new ModelAndView();
		boolean isException = false;
		try {
			backAdminService.deleteLaundrys(Common.getIds(laundryids));
		} catch (BackException e) {
			session.setAttribute(KEY_INFO_MSG, "删除失败。" + e.getMessage());
			isException = true;
		}
		if (!isException) {
			session.setAttribute(KEY_INFO_MSG, "删除成功。");
		}
		view.setViewName("redirect:"+ WebConst.BACK_PREFIX+"/laundry/index.html?type=1");
		return view;
	}

	@RequestMapping("/showmodify")
	public ModelAndView showModify(@RequestParam("laundryid") Integer laundryid) {
		ModelAndView view = new ModelAndView();
		Laundry laundry = backAdminService.getLaundry(laundryid);
		view.addObject("laundry", laundry);
		view.setViewName( WebConst.BACK_PREFIX+"/laundry/modify");
		return view;
	}

	@RequestMapping("/modify")
	public ModelAndView modify(@Valid Laundry laundry, BindingResult result) {
		ModelAndView view = new ModelAndView();
		if(laundry.getId()==null){
			view.addObject(KEY_INFO_MSG, "修改失败。发生错误。");
			view.addObject("laundry", laundry);
		}else if (!result.hasErrors()) {
			boolean isException = false;
			try {
				backAdminService.modifyLaundry(laundry);
			} catch (BackException e) {
				view.addObject(KEY_INFO_MSG, "修改失败。" + e.getMessage());
				view.addObject("laundry", laundry);
				isException = true;
			}
			if (!isException) {
				view.addObject(KEY_INFO_MSG, "修改成功。");
			}	
		}
		view.setViewName( WebConst.BACK_PREFIX+"/laundry/modify");
		return view;
	}
}
