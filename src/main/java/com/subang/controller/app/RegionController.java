package com.subang.controller.app;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.subang.bean.Identity;
import com.subang.bean.Result;
import com.subang.controller.BaseController;
import com.subang.domain.City;
import com.subang.domain.District;
import com.subang.domain.Region;
import com.subang.domain.User;
import com.subang.util.SuUtil;

@Controller("regionController_app")
@RequestMapping("/app/region")
public class RegionController extends BaseController {

	// 获取用户使用那个城市的服务
	@RequestMapping("/getcityid")
	public void getCityid(Identity identity, HttpServletResponse response) {
		User user = getUser(identity);
		Integer cityid = regionService.getCityid(user.getId());
		SuUtil.outputJson(response, cityid);
	}

	@RequestMapping("/city")
	public void listCity(@RequestParam(value = "filter", required = false) String filter,
			HttpServletResponse response) {
		List<City> citys = cityDao.findAll();
		SuUtil.doFilter(filter, citys, City.class);
		SuUtil.outputJson(response, citys);
	}

	@RequestMapping("/district")
	public void listDistrict(@RequestParam("cityid") Integer cityid,
			@RequestParam(value = "filter", required = false) String filter,
			HttpServletResponse response) {
		List<District> districts = districtDao.findByCityid(cityid);
		SuUtil.doFilter(filter, districts, District.class);
		SuUtil.outputJson(response, districts);
	}

	@RequestMapping("/region")
	public void listRegion(@RequestParam("districtid") Integer districtid,
			@RequestParam(value = "filter", required = false) String filter,
			HttpServletResponse response) {
		List<Region> regions = regionDao.findByDistrictid(districtid);
		SuUtil.doFilter(filter, regions, Region.class);
		SuUtil.outputJson(response, regions);
	}

	// 可以获取到当前城市的服务范围信息
	@RequestMapping("/getcity")
	public void getCity(@RequestParam("cityid") Integer cityid, HttpServletResponse response) {
		City city = cityDao.get(cityid);
		SuUtil.outputJson(response, city);
	}

	@RequestMapping("/test")
	public void test(@RequestParam("arg") String arg, HttpServletResponse response) {
		System.out.println(arg);
		Result result = new Result(Result.OK, "清");
		SuUtil.outputJson(response, result);
	}

}
