package com.subang.controller.app;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.subang.controller.BaseController;
import com.subang.domain.Category;
import com.subang.domain.City;
import com.subang.domain.User;

@Controller("regionController_app")
@RequestMapping("/app/region")
public class RegionController extends BaseController {

	@RequestMapping("/index")
	public ModelAndView index(HttpSession session,
			@RequestParam(value = "cityid", required = false) Integer cityid) {
		ModelAndView view = new ModelAndView();
		User user = getUser(session);
		List<Category> categorys = regionService.listByCityid(user.getId(), cityid);
		view.addObject("categorys", categorys);
		City city = cityDao.get(cityid);
		view.addObject("city", city);

		return view;
	}

	@RequestMapping("list")
	public ModelAndView list() {
		ModelAndView view = new ModelAndView();
		List<City> citys = cityDao.findAll();
		view.addObject("citys", citys);

		return view;
	}

	@RequestMapping("scope")
	public ModelAndView scope(@RequestParam("cityid") Integer cityid) {
		ModelAndView view = new ModelAndView();
		City city = cityDao.get(cityid);
		view.addObject("city", city);
		List<City> citys = cityDao.findAll();
		view.addObject("citys", citys);
		return view;
	}

}
