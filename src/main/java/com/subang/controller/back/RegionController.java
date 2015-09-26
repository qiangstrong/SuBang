package com.subang.controller.back;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.subang.bean.Area;
import com.subang.bean.SearchArg;
import com.subang.controller.BaseController;
import com.subang.domain.Category;
import com.subang.domain.City;
import com.subang.domain.District;
import com.subang.domain.Region;
import com.subang.domain.Service;
import com.subang.domain.Worker;
import com.subang.exception.SuException;
import com.subang.util.SuUtil;
import com.subang.util.WebConst;

@Controller
@RequestMapping("/back/region")
public class RegionController extends BaseController {

	@RequestMapping("/city")
	public ModelAndView listCity(HttpSession session) {
		ModelAndView view = new ModelAndView();
		invalidtePageState(session);
		List<City> citys = cityDao.findAll();
		view.addObject("citys", citys);
		view.addObject(KEY_ERR_MSG, session.getAttribute(KEY_ERR_MSG));
		session.removeAttribute(KEY_ERR_MSG);
		view.addObject(KEY_INFO_MSG, session.getAttribute(KEY_INFO_MSG));
		session.removeAttribute(KEY_INFO_MSG);
		view.setViewName(WebConst.BACK_PREFIX + "/region/city");
		return view;
	}

	@RequestMapping("incomplete")
	public ModelAndView listIncomplete() {
		ModelAndView view = new ModelAndView();
		List<Area> areas = regionDao.findIncomplete();
		view.addObject("areas", areas);
		String desMsg = "没有分配取衣员的区域如下：";
		view.addObject(KEY_DES_MSG, desMsg);
		view.setViewName(WebConst.BACK_PREFIX + "/region/area");
		return view;
	}

	@RequestMapping("/showaddcity")
	public ModelAndView showAddCity() {
		ModelAndView view = new ModelAndView();
		view.addObject("city", new City());
		view.setViewName(WebConst.BACK_PREFIX + "/region/addcity");
		return view;
	}

	@RequestMapping("/addcity")
	public ModelAndView addCity(HttpServletRequest request, @Valid City city, BindingResult result) {
		ModelAndView view = new ModelAndView();
		if (!result.hasErrors()) {
			boolean isException = false;
			try {
				MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
				MultipartFile scope = multipartRequest.getFile("scopeImg");
				regionService.addCity(city, scope);
			} catch (SuException e) {
				view.addObject(KEY_INFO_MSG, "添加失败。" + e.getMessage());
				view.addObject("city", city);
				isException = true;
			}
			if (!isException) {
				view.addObject(KEY_INFO_MSG, "添加成功。");
			}
		}
		view.setViewName(WebConst.BACK_PREFIX + "/region/addcity");
		return view;
	}

	@RequestMapping("deletecity")
	public ModelAndView deleteCity(HttpSession session, @RequestParam("cityids") String cityids) {
		ModelAndView view = new ModelAndView();
		boolean isException = false;
		try {
			regionService.deleteCitys(SuUtil.getIds(cityids));
		} catch (SuException e) {
			session.setAttribute(KEY_INFO_MSG, "删除失败。" + e.getMessage());
			isException = true;
		}
		if (!isException) {
			session.setAttribute(KEY_INFO_MSG, "删除成功。");
		}
		view.setViewName("redirect:" + WebConst.BACK_PREFIX + "/region/city.html");
		return view;
	}

	@RequestMapping("/showmodifycity")
	public ModelAndView showModifyCity(@RequestParam("cityid") Integer cityid) {
		ModelAndView view = new ModelAndView();
		City city = cityDao.get(cityid);
		view.addObject("city", city);
		view.setViewName(WebConst.BACK_PREFIX + "/region/modifycity");
		return view;
	}

	@RequestMapping("/modifycity")
	public ModelAndView modifyCity(HttpServletRequest request, @Valid City city,
			BindingResult result) {
		ModelAndView view = new ModelAndView();
		if (city.getId() == null) {
			view.addObject(KEY_INFO_MSG, "修改失败。发生错误。");
			view.addObject("city", city);
		} else if (!result.hasErrors()) {
			boolean isException = false;
			try {
				MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
				MultipartFile scope = multipartRequest.getFile("scopeImg");
				regionService.modifyCity(city, scope);
			} catch (SuException e) {
				view.addObject(KEY_INFO_MSG, "修改失败。" + e.getMessage());
				view.addObject("city", city);
				isException = true;
			}
			if (!isException) {
				view.addObject(KEY_INFO_MSG, "修改成功。");
			}
		}
		view.setViewName(WebConst.BACK_PREFIX + "/region/modifycity");
		return view;
	}

	@RequestMapping("/district")
	public ModelAndView listDistrict(HttpSession session, @RequestParam("cityid") Integer cityid) {
		ModelAndView view = new ModelAndView();
		List<District> districts = districtDao.findByCityid(cityid);
		view.addObject("districts", districts);
		view.addObject("cityid", cityid);

		City city = cityDao.get(cityid);
		String desMsg = "城市名称：" + city.getName() + "。此城市的区如下：";
		view.addObject(KEY_DES_MSG, desMsg);

		view.addObject(KEY_ERR_MSG, session.getAttribute(KEY_ERR_MSG));
		session.removeAttribute(KEY_ERR_MSG);
		view.addObject(KEY_INFO_MSG, session.getAttribute(KEY_INFO_MSG));
		session.removeAttribute(KEY_INFO_MSG);

		view.setViewName(WebConst.BACK_PREFIX + "/region/district");
		return view;
	}

	@RequestMapping("/showadddistrict")
	public ModelAndView showAddDistrict(@RequestParam("cityid") Integer cityid) {
		ModelAndView view = new ModelAndView();
		District district = new District();
		district.setCityid(cityid);
		view.addObject("district", district);
		view.setViewName(WebConst.BACK_PREFIX + "/region/adddistrict");
		return view;
	}

	@RequestMapping("/adddistrict")
	public ModelAndView addDistrict(@Valid District district, BindingResult result) {
		ModelAndView view = new ModelAndView();
		if (!result.hasErrors()) {
			boolean isException = false;
			try {
				regionService.addDistrict(district);
			} catch (SuException e) {
				view.addObject(KEY_INFO_MSG, "添加失败。" + e.getMessage());
				view.addObject("district", district);
				isException = true;
			}
			if (!isException) {
				view.addObject(KEY_INFO_MSG, "添加成功。");
			}
		}
		view.setViewName(WebConst.BACK_PREFIX + "/region/adddistrict");
		return view;
	}

	@RequestMapping("deletedistrict")
	public ModelAndView deleteDistrict(HttpSession session,
			@RequestParam("districtids") String districtids) {
		ModelAndView view = new ModelAndView();
		boolean isException = false;
		List<Integer> districtidList = SuUtil.getIds(districtids);
		try {
			regionService.deleteDistricts(districtidList);
		} catch (SuException e) {
			session.setAttribute(KEY_INFO_MSG, "删除失败。" + e.getMessage());
			isException = true;
		}
		if (!isException) {
			session.setAttribute(KEY_INFO_MSG, "删除成功。");
		}
		District district = districtDao.get(districtidList.get(0));
		view.setViewName("redirect:" + WebConst.BACK_PREFIX + "/region/district.html?cityid="
				+ district.getCityid());
		return view;
	}

	@RequestMapping("/showmodifydistrict")
	public ModelAndView showModifyDistrict(@RequestParam("districtid") Integer districtid) {
		ModelAndView view = new ModelAndView();
		District district = districtDao.get(districtid);
		view.addObject("district", district);
		view.setViewName(WebConst.BACK_PREFIX + "/region/modifydistrict");
		return view;
	}

	@RequestMapping("/modifydistrict")
	public ModelAndView modifyDistrict(@Valid District district, BindingResult result) {
		ModelAndView view = new ModelAndView();
		if (district.getId() == null) {
			view.addObject(KEY_INFO_MSG, "修改失败。发生错误。");
			view.addObject("district", district);
		} else if (!result.hasErrors()) {
			boolean isException = false;
			try {
				regionService.modifyDistrict(district);
			} catch (SuException e) {
				view.addObject(KEY_INFO_MSG, "修改失败。" + e.getMessage());
				view.addObject("district", district);
				isException = true;
			}
			if (!isException) {
				view.addObject(KEY_INFO_MSG, "修改成功。");
			}
		}
		view.setViewName(WebConst.BACK_PREFIX + "/region/modifydistrict");
		return view;
	}

	@RequestMapping("/region.html")
	public ModelAndView listRegion(HttpSession session,
			@RequestParam("districtid") Integer districtid) {
		ModelAndView view = new ModelAndView();
		List<Region> regions = regionDao.findByDistrictid(districtid);
		view.addObject("regions", regions);
		view.addObject("districtid", districtid);

		District district = districtDao.get(districtid);
		String desMsg = "区名称：" + district.getName() + "。此区的小区如下：";
		view.addObject(KEY_DES_MSG, desMsg);

		view.addObject(KEY_ERR_MSG, session.getAttribute(KEY_ERR_MSG));
		session.removeAttribute(KEY_ERR_MSG);
		view.addObject(KEY_INFO_MSG, session.getAttribute(KEY_INFO_MSG));
		session.removeAttribute(KEY_INFO_MSG);

		view.setViewName(WebConst.BACK_PREFIX + "/region/region");
		return view;
	}

	@RequestMapping("/showaddregion")
	public ModelAndView showAddRegion(@RequestParam("districtid") Integer districtid) {
		ModelAndView view = new ModelAndView();
		Region region = new Region();
		region.setDistrictid(districtid);
		view.addObject("region", region);

		SearchArg searchArg = new SearchArg(WebConst.SEARCH_ALL, null);
		List<Worker> workers = workerService.searchWorker(searchArg);
		view.addObject("workers", workers);

		view.setViewName(WebConst.BACK_PREFIX + "/region/addregion");
		return view;
	}

	@RequestMapping("/addregion")
	public ModelAndView addRegion(@Valid Region region, BindingResult result) {
		ModelAndView view = new ModelAndView();
		if (!result.hasErrors()) {
			boolean isException = false;
			try {
				regionService.addRegion(region);
			} catch (SuException e) {
				view.addObject(KEY_INFO_MSG, "添加失败。" + e.getMessage());
				view.addObject("region", region);
				isException = true;
			}
			if (!isException) {
				view.addObject(KEY_INFO_MSG, "添加成功。");
			}
		}
		SearchArg searchArg = new SearchArg(WebConst.SEARCH_ALL, null);
		List<Worker> workers = workerService.searchWorker(searchArg);
		view.addObject("workers", workers);

		view.setViewName(WebConst.BACK_PREFIX + "/region/addregion");
		return view;
	}

	@RequestMapping("deleteregion")
	public ModelAndView deleteRegion(HttpSession session,
			@RequestParam("regionids") String regionids) {
		ModelAndView view = new ModelAndView();
		boolean isException = false;
		List<Integer> regionidList = SuUtil.getIds(regionids);
		try {
			regionService.deleteRegions(regionidList);
		} catch (SuException e) {
			session.setAttribute(KEY_INFO_MSG, "删除失败。" + e.getMessage());
			isException = true;
		}
		if (!isException) {
			session.setAttribute(KEY_INFO_MSG, "删除成功。");
		}
		Region region = regionDao.get(regionidList.get(0));
		view.setViewName("redirect:" + WebConst.BACK_PREFIX + "/region/region.html?districtid="
				+ region.getDistrictid());
		return view;
	}

	@RequestMapping("/showmodifyregion")
	public ModelAndView showModifyRegion(@RequestParam("regionid") Integer regionid) {
		ModelAndView view = new ModelAndView();
		Region region = regionDao.get(regionid);
		view.addObject("region", region);

		SearchArg searchArg = new SearchArg(WebConst.SEARCH_ALL, null);
		List<Worker> workers = workerService.searchWorker(searchArg);
		view.addObject("workers", workers);

		view.setViewName(WebConst.BACK_PREFIX + "/region/modifyregion");
		return view;
	}

	@RequestMapping("/modifyregion")
	public ModelAndView modifyRegion(@Valid Region region, BindingResult result) {
		ModelAndView view = new ModelAndView();
		if (region.getId() == null) {
			view.addObject(KEY_INFO_MSG, "修改失败。发生错误。");
			view.addObject("region", region);
		} else if (!result.hasErrors()) {
			boolean isException = false;
			try {
				regionService.modifyRegion(region);
			} catch (SuException e) {
				view.addObject(KEY_INFO_MSG, "修改失败。" + e.getMessage());
				view.addObject("region", region);
				isException = true;
			}
			if (!isException) {
				view.addObject(KEY_INFO_MSG, "修改成功。");
			}
		}

		SearchArg searchArg = new SearchArg(WebConst.SEARCH_ALL, null);
		List<Worker> workers = workerService.searchWorker(searchArg);
		view.addObject("workers", workers);

		view.setViewName(WebConst.BACK_PREFIX + "/region/modifyregion");
		return view;
	}

	@RequestMapping("/service")
	public ModelAndView listService(HttpSession session, @RequestParam("cityid") Integer cityid) {
		ModelAndView view = new ModelAndView();
		List<Service> services = serviceDao.findDetailByCityid(cityid);
		view.addObject("services", services);
		view.addObject("cityid", cityid);

		City city = cityDao.get(cityid);
		String desMsg = "城市名称：" + city.getName() + "。此城市的服务如下：";
		view.addObject(KEY_DES_MSG, desMsg);

		view.addObject(KEY_ERR_MSG, session.getAttribute(KEY_ERR_MSG));
		session.removeAttribute(KEY_ERR_MSG);
		view.addObject(KEY_INFO_MSG, session.getAttribute(KEY_INFO_MSG));
		session.removeAttribute(KEY_INFO_MSG);

		view.setViewName(WebConst.BACK_PREFIX + "/region/service");
		return view;
	}

	@RequestMapping("/showaddservice")
	public ModelAndView showAddService(@RequestParam("cityid") Integer cityid) {
		ModelAndView view = new ModelAndView();
		Service service = new Service();
		service.setCityid(cityid);
		view.addObject("service", service);

		List<Category> categories = categoryDao.findAll();
		view.addObject("categories", categories);

		view.setViewName(WebConst.BACK_PREFIX + "/region/addservice");
		return view;
	}

	@RequestMapping("/addservice")
	public ModelAndView addService(@Valid Service service, BindingResult result) {
		ModelAndView view = new ModelAndView();
		if (!result.hasErrors()) {
			boolean isException = false;
			try {
				regionService.addService(service);
			} catch (SuException e) {
				view.addObject(KEY_INFO_MSG, "添加失败。" + e.getMessage());
				view.addObject("service", service);
				isException = true;
			}
			if (!isException) {
				view.addObject(KEY_INFO_MSG, "添加成功。");
			}
		}
		List<Category> categories = categoryDao.findAll();
		view.addObject("categories", categories);
		view.setViewName(WebConst.BACK_PREFIX + "/region/addservice");
		return view;
	}

	@RequestMapping("deleteservice")
	public ModelAndView deleteService(HttpSession session,
			@RequestParam("serviceids") String serviceids) {
		ModelAndView view = new ModelAndView();
		boolean isException = false;
		List<Integer> serviceidList = SuUtil.getIds(serviceids);
		try {
			regionService.deleteServices(serviceidList);
		} catch (SuException e) {
			session.setAttribute(KEY_INFO_MSG, "删除失败。" + e.getMessage());
			isException = true;
		}
		if (!isException) {
			session.setAttribute(KEY_INFO_MSG, "删除成功。");
		}
		Service service = serviceDao.get(serviceidList.get(0));
		view.setViewName("redirect:" + WebConst.BACK_PREFIX + "/region/service.html?cityid="
				+ service.getCityid());
		return view;
	}
}
