package com.subang.controller.weixin;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.subang.controller.BaseController;
import com.subang.domain.Banner;
import com.subang.domain.Category;
import com.subang.domain.City;
import com.subang.domain.User;
import com.subang.util.WebConst;

@Controller("regionController_weixin")
@RequestMapping("/weixin/region")
public class RegionController extends BaseController {

	private static final String VIEW_PREFIX = WebConst.WEIXIN_PREFIX + "/region";

	@RequestMapping("/index")
	public ModelAndView index(HttpSession session,
			@RequestParam(value = "cityid", required = false) Integer cityid) {
		ModelAndView view = new ModelAndView();
		User user = getUser(session);
		if (cityid == null) {
			cityid = regionService.getCityid(user.getId());
		}
		List<Banner> banners = bannerDao.findAll();
		view.addObject("banners", banners);
		List<Category> categorys = regionService.listByCityid(user.getId(), cityid);
		view.addObject("categorys", categorys);
		City city = cityDao.get(cityid);
		view.addObject("city", city);
		view.setViewName(VIEW_PREFIX + "/index");
		return view;
	}

	@RequestMapping("/city")
	public ModelAndView listCity() {
		ModelAndView view = new ModelAndView();
		List<City> citys = cityDao.findAll();
		view.addObject("citys", citys);
		view.setViewName(VIEW_PREFIX + "/list");
		return view;
	}

	@RequestMapping("/scope")
	public ModelAndView getScope(@RequestParam("cityid") Integer cityid) {
		ModelAndView view = new ModelAndView();
		City city = cityDao.get(cityid);
		view.addObject("city", city);
		List<City> citys = cityDao.findAll();
		view.addObject("citys", citys);
		view.setViewName(VIEW_PREFIX + "/scope");
		return view;
	}

}
