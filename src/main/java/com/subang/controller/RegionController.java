package com.subang.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.subang.bean.SearchArg;
import com.subang.domain.City;
import com.subang.domain.District;
import com.subang.domain.Region;
import com.subang.domain.Worker;
import com.subang.exception.BackException;
import com.subang.util.Common;
import com.subang.util.WebConstant;

@Controller
@RequestMapping("/region")
public class RegionController extends BaseController {

	@RequestMapping("/city")
	public ModelAndView listCity(HttpSession session) {
		ModelAndView view = new ModelAndView();
		invalidtePageState(session);
		List<City> citys = backAdminService.listCity();
		view.addObject("citys", citys);
		view.addObject(KEY_ERR_MSG, session.getAttribute(KEY_ERR_MSG));
		session.removeAttribute(KEY_ERR_MSG);
		view.addObject(KEY_INFO_MSG, session.getAttribute(KEY_INFO_MSG));
		session.removeAttribute(KEY_INFO_MSG);
		view.setViewName("region/city");
		return view;
	}

	@RequestMapping("/showaddcity")
	public ModelAndView showAddCity() {
		ModelAndView view = new ModelAndView();
		view.addObject("city", new City());
		view.setViewName("region/addcity");
		return view;
	}

	@RequestMapping("/addcity")
	public ModelAndView addCity(@Valid City city, BindingResult result) {
		ModelAndView view = new ModelAndView();
		if (!result.hasErrors()) {
			boolean isException = false;
			try {
				backAdminService.addCity(city);
			} catch (BackException e) {
				view.addObject(KEY_INFO_MSG, "添加失败。" + e.getMessage());
				view.addObject("city", city);
				isException = true;
			}
			if (!isException) {
				view.addObject(KEY_INFO_MSG, "添加成功。");
			}
		}
		view.setViewName("region/addcity");
		return view;
	}

	@RequestMapping("deletecity")
	public ModelAndView deleteCity(HttpSession session, @RequestParam("cityids") String cityids) {
		ModelAndView view = new ModelAndView();
		boolean isException = false;
		try {
			backAdminService.deleteCity(Common.getIds(cityids));
		} catch (BackException e) {
			session.setAttribute(KEY_INFO_MSG, "删除失败。" + e.getMessage());
			isException = true;
		}
		if (!isException) {
			session.setAttribute(KEY_INFO_MSG, "删除成功。");
		}
		view.setViewName("redirect:/region/city.html");
		return view;
	}

	@RequestMapping("/showmodifycity")
	public ModelAndView showModifyCity(@RequestParam("cityid") Integer cityid) {
		ModelAndView view = new ModelAndView();
		City city = backAdminService.getCity(cityid);
		view.addObject("city", city);
		view.setViewName("region/modifycity");
		return view;
	}

	@RequestMapping("/modifycity")
	public ModelAndView modifyCity(@Valid City city, BindingResult result) {
		ModelAndView view = new ModelAndView();
		if (!result.hasErrors()) {
			boolean isException = false;
			try {
				backAdminService.modifyCity(city);
			} catch (BackException e) {
				view.addObject(KEY_INFO_MSG, "修改失败。" + e.getMessage());
				view.addObject("city", city);
				isException = true;
			}
			if (!isException) {
				view.addObject(KEY_INFO_MSG, "修改成功。");
			}
		}
		view.setViewName("region/modifycity");
		return view;
	}

	@RequestMapping("/district")
	public ModelAndView listDistrict(HttpSession session, @RequestParam("cityid") Integer cityid) {
		ModelAndView view = new ModelAndView();
		List<District> districts = backAdminService.listDistrictByCityid(cityid);
		view.addObject("districts", districts);
		view.addObject("cityid", cityid);

		City city = backAdminService.getCity(cityid);
		String desMsg = "城市名称：" + city.getName() + "。此城市的区如下：";
		view.addObject(KEY_DES_MSG, desMsg);

		view.addObject(KEY_ERR_MSG, session.getAttribute(KEY_ERR_MSG));
		session.removeAttribute(KEY_ERR_MSG);
		view.addObject(KEY_INFO_MSG, session.getAttribute(KEY_INFO_MSG));
		session.removeAttribute(KEY_INFO_MSG);

		view.setViewName("region/district");
		return view;
	}

	@RequestMapping("/showadddistrict")
	public ModelAndView showAddDistrict(@RequestParam("cityid") Integer cityid) {
		ModelAndView view = new ModelAndView();
		District district = new District();
		district.setCityid(cityid);
		view.addObject("district", district);
		view.setViewName("region/adddistrict");
		return view;
	}

	@RequestMapping("/adddistrict")
	public ModelAndView addDistrict(@Valid District district, BindingResult result) {
		ModelAndView view = new ModelAndView();
		if (!result.hasErrors()) {
			boolean isException = false;
			try {
				backAdminService.addDistrict(district);
			} catch (BackException e) {
				view.addObject(KEY_INFO_MSG, "添加失败。" + e.getMessage());
				view.addObject("district", district);
				isException = true;
			}
			if (!isException) {
				view.addObject(KEY_INFO_MSG, "添加成功。");
			}
		}
		view.setViewName("region/adddistrict");
		return view;
	}

	@RequestMapping("deletedistrict")
	public ModelAndView deleteDistrict(HttpSession session,
			@RequestParam("districtids") String districtids) {
		ModelAndView view = new ModelAndView();
		boolean isException = false;
		List<Integer> districtidList = Common.getIds(districtids);
		try {
			backAdminService.deleteDistrict(districtidList);
		} catch (BackException e) {
			session.setAttribute(KEY_INFO_MSG, "删除失败。" + e.getMessage());
			isException = true;
		}
		if (!isException) {
			session.setAttribute(KEY_INFO_MSG, "删除成功。");
		}
		City city = backAdminService.getCity(districtidList.get(0));
		view.setViewName("redirect:/region/district.html?cityid=" + city.getId());
		return view;
	}

	@RequestMapping("/showmodifydistrict")
	public ModelAndView showModifyDistrict(@RequestParam("districtid") Integer districtid) {
		ModelAndView view = new ModelAndView();
		District district = backAdminService.getDistrict(districtid);
		view.addObject("district", district);
		view.setViewName("region/modifydistrict");
		return view;
	}

	@RequestMapping("/modifydistrict")
	public ModelAndView modifyDistrict(@Valid District district, BindingResult result) {
		ModelAndView view = new ModelAndView();
		if (!result.hasErrors()) {
			boolean isException = false;
			try {
				backAdminService.modifyDistrict(district);
			} catch (BackException e) {
				view.addObject(KEY_INFO_MSG, "修改失败。" + e.getMessage());
				view.addObject("district", district);
				isException = true;
			}
			if (!isException) {
				view.addObject(KEY_INFO_MSG, "修改成功。");
			}
		}
		view.setViewName("region/modifydistrict");
		return view;
	}

	@RequestMapping("/region.html")
	public ModelAndView listRegion(HttpSession session,
			@RequestParam("districtid") Integer districtid) {
		ModelAndView view = new ModelAndView();
		List<Region> regions = backAdminService.listRegionByDistrictid(districtid);
		view.addObject("regions", regions);
		view.addObject("districtid", districtid);

		District district = backAdminService.getDistrict(districtid);
		String desMsg = "区名称：" + district.getName() + "。此区的小区如下：";
		view.addObject(KEY_DES_MSG, desMsg);
		view.addObject("cityid", district.getCityid());

		view.addObject(KEY_ERR_MSG, session.getAttribute(KEY_ERR_MSG));
		session.removeAttribute(KEY_ERR_MSG);
		view.addObject(KEY_INFO_MSG, session.getAttribute(KEY_INFO_MSG));
		session.removeAttribute(KEY_INFO_MSG);

		view.setViewName("region/region");
		return view;
	}

	@RequestMapping("/showaddregion")
	public ModelAndView showAddRegion(@RequestParam("districtid") Integer districtid) {
		ModelAndView view = new ModelAndView();
		Region region = new Region();
		region.setDistrictid(districtid);
		view.addObject("region", region);

		SearchArg searchArg = new SearchArg(WebConstant.SEARCH_ALL, null);
		List<Worker> workers = backAdminService.searchWorker(searchArg);
		view.addObject("workers", workers);

		view.setViewName("region/addregion");
		return view;
	}

	@RequestMapping("/addregion")
	public ModelAndView addRegion(@Valid Region region, BindingResult result) {
		ModelAndView view = new ModelAndView();
		if (!result.hasErrors()) {
			boolean isException = false;
			try {
				backAdminService.addRegion(region);
			} catch (BackException e) {
				view.addObject(KEY_INFO_MSG, "添加失败。" + e.getMessage());
				view.addObject("region", region);
				isException = true;
			}
			if (!isException) {
				view.addObject(KEY_INFO_MSG, "添加成功。");
			}
		}
		SearchArg searchArg = new SearchArg(WebConstant.SEARCH_ALL, null);
		List<Worker> workers = backAdminService.searchWorker(searchArg);
		view.addObject("workers", workers);

		view.setViewName("region/addregion");
		return view;
	}

	@RequestMapping("deleteregion")
	public ModelAndView deleteRegion(HttpSession session,
			@RequestParam("regionids") String regionids) {
		ModelAndView view = new ModelAndView();
		boolean isException = false;
		List<Integer> regionidList = Common.getIds(regionids);
		try {
			backAdminService.deleteRegion(regionidList);
		} catch (BackException e) {
			session.setAttribute(KEY_INFO_MSG, "删除失败。" + e.getMessage());
			isException = true;
		}
		if (!isException) {
			session.setAttribute(KEY_INFO_MSG, "删除成功。");
		}
		District district = backAdminService.getDistrict(regionidList.get(0));
		view.setViewName("redirect:/region/region.html?cityid=" + district.getId());
		return view;
	}

	@RequestMapping("/showmodifyregion")
	public ModelAndView showModifyRegion(@RequestParam("regionid") Integer regionid) {
		ModelAndView view = new ModelAndView();
		Region region = backAdminService.getRegion(regionid);
		view.addObject("region", region);

		SearchArg searchArg = new SearchArg(WebConstant.SEARCH_ALL, null);
		List<Worker> workers = backAdminService.searchWorker(searchArg);
		view.addObject("workers", workers);

		view.setViewName("region/modifyregion");
		return view;
	}

	@RequestMapping("/modifyregion")
	public ModelAndView modifyRegion(@Valid Region region, BindingResult result) {
		ModelAndView view = new ModelAndView();
		if (!result.hasErrors()) {
			boolean isException = false;
			try {
				backAdminService.modifyRegion(region);
			} catch (BackException e) {
				view.addObject(KEY_INFO_MSG, "修改失败。" + e.getMessage());
				view.addObject("region", region);
				isException = true;
			}
			if (!isException) {
				view.addObject(KEY_INFO_MSG, "修改成功。");
			}
		}

		SearchArg searchArg = new SearchArg(WebConstant.SEARCH_ALL, null);
		List<Worker> workers = backAdminService.searchWorker(searchArg);
		view.addObject("workers", workers);

		view.setViewName("region/modifyregion");
		return view;
	}
}
