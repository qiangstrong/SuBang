package com.subang.controller.app;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.subang.bean.Identity;
import com.subang.controller.BaseController;
import com.subang.domain.City;
import com.subang.domain.District;
import com.subang.domain.Location;
import com.subang.domain.Region;
import com.subang.domain.User;
import com.subang.util.SuUtil;

@Controller("regionController_app")
@RequestMapping("/app/region")
public class RegionController extends BaseController {

	// app启动时，提交坐标，获取当前城市的id
	@RequestMapping("setlocation")
	public void setLocation(Identity identity, Location location, HttpServletResponse response) {
		User user = getUser(identity);
		userService.updateLocation(user.getId(), location);
		Integer cityid = regionService.getCityid(location);
		SuUtil.outputJson(response, cityid);
	}

	@RequestMapping("listcity")
	public void listCity(@RequestParam(value = "filter", required = false) String filter,
			HttpServletResponse response) {
		List<City> citys = cityDao.findAll();
		SuUtil.doFilter(filter, citys, City.class);
		SuUtil.outputJson(response, citys);
	}

	@RequestMapping("listdistrict")
	public void listDistrict(@RequestParam("cityid") Integer cityid,
			@RequestParam(value = "filter", required = false) String filter,
			HttpServletResponse response) {
		List<District> districts = districtDao.findByCityid(cityid);
		SuUtil.doFilter(filter, districts, District.class);
		SuUtil.outputJson(response, districts);
	}

	@RequestMapping("listregion")
	public void listRegion(@RequestParam("districtid") Integer districtid,
			@RequestParam(value = "filter", required = false) String filter,
			HttpServletResponse response) {
		List<Region> regions = regionDao.findByDistrictid(districtid);
		SuUtil.doFilter(filter, regions, Region.class);
		SuUtil.outputJson(response, regions);
	}

	// 可以获取到当前城市的服务范围信息
	@RequestMapping("getcity")
	public void getCity(@RequestParam("cityid") Integer cityid, HttpServletResponse response) {
		City city = cityDao.get(cityid);
		SuUtil.outputJson(response, city);
	}

	@RequestMapping("test")
	public void test(Identity identity, HttpServletResponse response) {

		SuUtil.outputJsonOK(response);
	}

}
