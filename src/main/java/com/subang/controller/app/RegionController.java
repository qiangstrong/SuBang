package com.subang.controller.app;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.subang.controller.BaseController;
import com.subang.domain.City;
import com.subang.util.SuUtil;

@Controller("regionController_app")
@RequestMapping("/app/region")
public class RegionController extends BaseController {

	@RequestMapping("listcity")
	public void listCity(@RequestParam(value = "filter", required = false) String filter,
			HttpServletResponse response) {
		List<City> citys = cityDao.findAll();
		SuUtil.doFilter(filter, citys, City.class);
		SuUtil.outputJson(response, citys);
	}

	@RequestMapping("scope")
	public void scope(@RequestParam("cityid") Integer cityid, HttpServletResponse response) {
		City city = cityDao.get(cityid);
		SuUtil.outputJson(response, city);
	}

	@RequestMapping("test")
	public void test(HttpServletResponse response) {
		Integer a = 6;
		SuUtil.outputJson(response, a);
	}

}
