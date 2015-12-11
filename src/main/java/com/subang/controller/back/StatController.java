package com.subang.controller.back;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.subang.bean.StatArg;
import com.subang.bean.StatItem;
import com.subang.controller.BaseController;
import com.subang.tool.BackStack;
import com.subang.util.WebConst;

@Controller
@RequestMapping("/back/stat")
public class StatController extends BaseController {

	private static final String VIEW_PREFIX = WebConst.BACK_PREFIX + "/stat";
	private static final String INDEX_PAGE = VIEW_PREFIX + "/index";
	private static final String KEY_DATA = "statItems";

	@RequestMapping("/index")
	public ModelAndView index(HttpSession session, StatArg statArg) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		backStack.clear("stat/index");
		List<StatItem> statItems = statService.stat(statArg);
		view.addObject(KEY_DATA, statItems);
		view.setViewName(INDEX_PAGE);
		return view;
	}
}
