package com.subang.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.subang.bean.StatItem;
import com.subang.bean.StatArg;
import com.subang.util.WebConstant;

@Controller
@RequestMapping("/stat")
public class StatController extends BaseController {

	private static final String INDEX_PAGE = "/stat/index";
	private static final String KEY_DATA = "statItems";

	@RequestMapping("/index")
	public ModelAndView index(HttpSession session, StatArg statArg) {
		ModelAndView view = new ModelAndView();
		savePageState(session, statArg);
		List<StatItem> statItems=backStatService.stat(statArg);
		view.addObject(KEY_DATA, statItems);
		view.setViewName(INDEX_PAGE);
		return view;
	}
}
