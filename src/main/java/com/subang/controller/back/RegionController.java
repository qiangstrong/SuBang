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
import com.subang.bean.MsgArg;
import com.subang.bean.PageArg;
import com.subang.bean.PageState;
import com.subang.bean.SearchArg;
import com.subang.controller.BaseController;
import com.subang.domain.Category;
import com.subang.domain.City;
import com.subang.domain.District;
import com.subang.domain.Region;
import com.subang.domain.Service;
import com.subang.domain.Worker;
import com.subang.tool.BackStack;
import com.subang.tool.SuException;
import com.subang.util.SuUtil;
import com.subang.util.WebConst;

@Controller
@RequestMapping("/back/region")
public class RegionController extends BaseController {

	private static final String VIEW_PREFIX = WebConst.BACK_PREFIX + "/region";

	@RequestMapping("/city/back")
	public ModelAndView cityBack(HttpSession session) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		backStack.pop();
		view.setViewName("redirect:" + VIEW_PREFIX + "/city.html");
		return view;
	}

	@RequestMapping("/city")
	public ModelAndView listCity(HttpSession session) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		backStack.clear("region/city");

		if (!backStack.isTop("region/city")) {
			backStack.push(new PageState("region/city", null));
		}
		PageArg pageArg = getPageArg(session);
		if (pageArg != null) {
			// 此页面不接受para类型的参数
			switch (pageArg.getArgType()) {
			case msg:
				MsgArg msgArg = (MsgArg) pageArg;
				view.addObject(msgArg.getKey(), msgArg.getMsg());
				break;
			}
		}

		List<City> citys = cityDao.findAll();
		view.addObject("citys", citys);
		view.setViewName(VIEW_PREFIX + "/city");
		return view;
	}

	@RequestMapping("/incomplete/back")
	public ModelAndView incompleteBack(HttpSession session) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		backStack.pop();
		view.setViewName("redirect:" + VIEW_PREFIX + "/incomplete.html");
		return view;
	}

	@RequestMapping("/incomplete")
	public ModelAndView listIncomplete(HttpSession session) {
		ModelAndView view = new ModelAndView();

		BackStack backStack = getBackStack(session);
		if (!backStack.isTop("region/incomplete")) {
			backStack.push(new PageState("region/incomplete", null));
		}

		PageArg pageArg = getPageArg(session);
		if (pageArg != null) {
			// 此页面不接受para类型的参数
			switch (pageArg.getArgType()) {
			case msg:
				MsgArg msgArg = (MsgArg) pageArg;
				view.addObject(msgArg.getKey(), msgArg.getMsg());
				break;
			}
		}

		String desMsg = "没有分配取衣员的区域如下：";
		view.addObject(KEY_DES_MSG, desMsg);
		List<Area> areas = regionDao.findIncomplete();
		view.addObject("areas", areas);
		view.addObject(KEY_BACK_LINK, backStack.getBackLink("region/incomplete"));
		view.setViewName(VIEW_PREFIX + "/area");
		return view;
	}

	@RequestMapping("/showaddcity")
	public ModelAndView showAddCity(HttpSession session) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		backStack.push(new PageState("region/addcity", null));

		view.addObject("city", new City());
		view.addObject(KEY_BACK_LINK, backStack.getBackLink("region/addcity"));
		view.setViewName(VIEW_PREFIX + "/addcity");
		return view;
	}

	@RequestMapping("/addcity")
	public ModelAndView addCity(HttpServletRequest request, @Valid City city, BindingResult result) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(request.getSession());
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
		view.addObject(KEY_BACK_LINK, backStack.getBackLink("region/addcity"));
		view.setViewName(VIEW_PREFIX + "/addcity");
		return view;
	}

	@RequestMapping("/deletecity")
	public ModelAndView deleteCity(HttpSession session, @RequestParam("cityids") String cityids) {
		ModelAndView view = new ModelAndView();
		boolean isException = false;
		try {
			regionService.deleteCitys(SuUtil.getIds(cityids));
		} catch (SuException e) {
			setPageArg(session, new MsgArg(KEY_INFO_MSG, "删除失败。" + e.getMessage()));
			isException = true;
		}
		if (!isException) {
			setPageArg(session, new MsgArg(KEY_INFO_MSG, "删除成功。"));
		}
		view.setViewName("redirect:" + VIEW_PREFIX + "/city.html");
		return view;
	}

	@RequestMapping("/showmodifycity")
	public ModelAndView showModifyCity(HttpSession session, @RequestParam("cityid") Integer cityid) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		backStack.push(new PageState("region/modifycity", null));

		City city = cityDao.get(cityid);
		if (city == null) {
			setPageArg(session, new MsgArg(KEY_INFO_MSG, "修改失败。数据不存在。"));
			view.setViewName("redirect:" + backStack.getBackLink("region/modifycity"));
			return view;
		}
		view.addObject("city", city);
		view.addObject(KEY_BACK_LINK, backStack.getBackLink("region/modifycity"));
		view.setViewName(VIEW_PREFIX + "/modifycity");
		return view;
	}

	@RequestMapping("/modifycity")
	public ModelAndView modifyCity(HttpServletRequest request, @Valid City city,
			BindingResult result) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(request.getSession());
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
		view.addObject(KEY_BACK_LINK, backStack.getBackLink("region/modifycity"));
		view.setViewName(VIEW_PREFIX + "/modifycity");
		return view;
	}

	@RequestMapping("/district/back")
	public ModelAndView districtBack(HttpSession session) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		backStack.pop();
		PageState pageState = backStack.peek();
		view.setViewName("redirect:" + VIEW_PREFIX + "/district.html?cityid="
				+ pageState.getUpperid());
		return view;
	}

	@RequestMapping("/district")
	public ModelAndView listDistrict(HttpSession session, @RequestParam("cityid") Integer cityid) {
		ModelAndView view = new ModelAndView();

		BackStack backStack = getBackStack(session);
		backStack.push(new PageState("region/district", cityid));
		PageArg pageArg = getPageArg(session);
		if (pageArg != null) {
			// 此页面不接受para类型的参数
			switch (pageArg.getArgType()) {
			case msg:
				MsgArg msgArg = (MsgArg) pageArg;
				view.addObject(msgArg.getKey(), msgArg.getMsg());
				break;
			}
		}

		City city = cityDao.get(cityid);
		String desMsg = "城市名称：" + city.getName() + "。此城市的区如下：";
		view.addObject(KEY_DES_MSG, desMsg);

		List<District> districts = districtDao.findByCityid(cityid);
		view.addObject("districts", districts);
		view.addObject("cityid", cityid);
		view.setViewName(VIEW_PREFIX + "/district");
		return view;
	}

	@RequestMapping("/showadddistrict")
	public ModelAndView showAddDistrict(HttpSession session, @RequestParam("cityid") Integer cityid) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		backStack.push(new PageState("region/dddistrict", null));

		District district = new District();
		district.setCityid(cityid);
		view.addObject("district", district);
		view.addObject(KEY_BACK_LINK, backStack.getBackLink("region/dddistrict"));
		view.setViewName(VIEW_PREFIX + "/adddistrict");
		return view;
	}

	@RequestMapping("/adddistrict")
	public ModelAndView addDistrict(HttpServletRequest request, @Valid District district,
			BindingResult result) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(request.getSession());
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
		view.addObject(KEY_BACK_LINK, backStack.getBackLink("region/dddistrict"));
		view.setViewName(VIEW_PREFIX + "/adddistrict");
		return view;
	}

	@RequestMapping("/deletedistrict")
	public ModelAndView deleteDistrict(HttpSession session,
			@RequestParam("districtids") String districtids) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		backStack.push(new PageState("region/deletedistrict", null));

		boolean isException = false;
		List<Integer> districtidList = SuUtil.getIds(districtids);
		try {
			regionService.deleteDistricts(districtidList);
		} catch (SuException e) {
			setPageArg(session, new MsgArg(KEY_INFO_MSG, "删除失败。" + e.getMessage()));
			isException = true;
		}
		if (!isException) {
			setPageArg(session, new MsgArg(KEY_INFO_MSG, "删除成功。"));
		}
		view.setViewName("redirect:" + VIEW_PREFIX + "/district/back.html");
		return view;
	}

	@RequestMapping("/showmodifydistrict")
	public ModelAndView showModifyDistrict(HttpSession session,
			@RequestParam("districtid") Integer districtid) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		backStack.push(new PageState("region/modifydistrict", null));

		District district = districtDao.get(districtid);
		if (district == null) {
			setPageArg(session, new MsgArg(KEY_INFO_MSG, "修改失败。数据不存在。"));
			view.setViewName("redirect:" + backStack.getBackLink("region/modifydistrict"));
			return view;
		}
		view.addObject("district", district);
		view.addObject(KEY_BACK_LINK, backStack.getBackLink("region/modifydistrict"));
		view.setViewName(VIEW_PREFIX + "/modifydistrict");
		return view;
	}

	@RequestMapping("/modifydistrict")
	public ModelAndView modifyDistrict(HttpSession session, @Valid District district,
			BindingResult result) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
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
		view.addObject(KEY_BACK_LINK, backStack.getBackLink("region/modifydistrict"));
		view.setViewName(VIEW_PREFIX + "/modifydistrict");
		return view;
	}

	@RequestMapping("/region/back")
	public ModelAndView regionBack(HttpSession session) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		backStack.pop();
		PageState pageState = backStack.peek();
		view.setViewName("redirect:" + VIEW_PREFIX + "/region.html?districtid="
				+ pageState.getUpperid());
		return view;
	}

	@RequestMapping("/region.html")
	public ModelAndView listRegion(HttpSession session,
			@RequestParam("districtid") Integer districtid) {
		ModelAndView view = new ModelAndView();

		BackStack backStack = getBackStack(session);
		backStack.push(new PageState("region/region", districtid));
		PageArg pageArg = getPageArg(session);
		if (pageArg != null) {
			// 此页面不接受para类型的参数
			switch (pageArg.getArgType()) {
			case msg:
				MsgArg msgArg = (MsgArg) pageArg;
				view.addObject(msgArg.getKey(), msgArg.getMsg());
				break;
			}
		}

		District district = districtDao.get(districtid);
		String desMsg = "区名称：" + district.getName() + "。此区的小区如下：";
		view.addObject(KEY_DES_MSG, desMsg);

		List<Region> regions = regionDao.findByDistrictid(districtid);
		view.addObject("regions", regions);
		view.addObject("districtid", districtid);

		view.setViewName(VIEW_PREFIX + "/region");
		return view;
	}

	@RequestMapping("/showaddregion")
	public ModelAndView showAddRegion(HttpSession session,
			@RequestParam("districtid") Integer districtid) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		backStack.push(new PageState("region/addregion", null));

		Region region = new Region();
		region.setDistrictid(districtid);
		view.addObject("region", region);

		SearchArg searchArg = new SearchArg(WebConst.SEARCH_ALL, null);
		List<Worker> workers = workerService.searchWorker(searchArg);
		view.addObject("workers", workers);

		view.addObject(KEY_BACK_LINK, backStack.getBackLink("region/addregion"));
		view.setViewName(VIEW_PREFIX + "/addregion");
		return view;
	}

	@RequestMapping("/addregion")
	public ModelAndView addRegion(HttpSession session, @Valid Region region, BindingResult result) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
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

		view.addObject(KEY_BACK_LINK, backStack.getBackLink("region/addregion"));
		view.setViewName(VIEW_PREFIX + "/addregion");
		return view;
	}

	@RequestMapping("/deleteregion")
	public ModelAndView deleteRegion(HttpSession session,
			@RequestParam("regionids") String regionids) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		backStack.push(new PageState("region/deleteregion", null));

		boolean isException = false;
		List<Integer> regionidList = SuUtil.getIds(regionids);
		try {
			regionService.deleteRegions(regionidList);
		} catch (SuException e) {
			setPageArg(session, new MsgArg(KEY_INFO_MSG, "删除失败。" + e.getMessage()));
			isException = true;
		}
		if (!isException) {
			setPageArg(session, new MsgArg(KEY_INFO_MSG, "删除成功。"));
		}
		view.setViewName("redirect:" + VIEW_PREFIX + "/region/back.html");
		return view;
	}

	@RequestMapping("/showmodifyregion")
	public ModelAndView showModifyRegion(HttpSession session,
			@RequestParam("regionid") Integer regionid) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		backStack.push(new PageState("region/modifyregion", null));

		Region region = regionDao.get(regionid);
		if (region == null) {
			setPageArg(session, new MsgArg(KEY_INFO_MSG, "修改失败。数据不存在。"));
			view.setViewName("redirect:" + backStack.getBackLink("region/modifyregion"));
			return view;
		}
		view.addObject("region", region);

		SearchArg searchArg = new SearchArg(WebConst.SEARCH_ALL, null);
		List<Worker> workers = workerService.searchWorker(searchArg);
		view.addObject("workers", workers);

		view.addObject(KEY_BACK_LINK, backStack.getBackLink("region/modifyregion"));
		view.setViewName(VIEW_PREFIX + "/modifyregion");
		return view;
	}

	@RequestMapping("/modifyregion")
	public ModelAndView modifyRegion(HttpSession session, @Valid Region region, BindingResult result) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
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

		view.addObject(KEY_BACK_LINK, backStack.getBackLink("region/modifyregion"));
		view.setViewName(VIEW_PREFIX + "/modifyregion");
		return view;
	}

	@RequestMapping("/service/back")
	public ModelAndView serviceBack(HttpSession session) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		backStack.pop();
		PageState pageState = backStack.peek();
		view.setViewName("redirect:" + VIEW_PREFIX + "/service.html?cityid="
				+ pageState.getUpperid());
		return view;
	}

	@RequestMapping("/service")
	public ModelAndView listService(HttpSession session, @RequestParam("cityid") Integer cityid) {
		ModelAndView view = new ModelAndView();

		BackStack backStack = getBackStack(session);
		backStack.push(new PageState("region/service", cityid));
		PageArg pageArg = getPageArg(session);
		if (pageArg != null) {
			// 此页面不接受para类型的参数
			switch (pageArg.getArgType()) {
			case msg:
				MsgArg msgArg = (MsgArg) pageArg;
				view.addObject(msgArg.getKey(), msgArg.getMsg());
				break;
			}
		}

		City city = cityDao.get(cityid);
		String desMsg = "城市名称：" + city.getName() + "。此城市的服务如下：";
		view.addObject(KEY_DES_MSG, desMsg);

		List<Service> services = serviceDao.findDetailByCityid(cityid);
		view.addObject("services", services);
		view.addObject("cityid", cityid);

		view.setViewName(VIEW_PREFIX + "/service");
		return view;
	}

	@RequestMapping("/showaddservice")
	public ModelAndView showAddService(HttpSession session, @RequestParam("cityid") Integer cityid) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		backStack.push(new PageState("region/addservice", null));

		Service service = new Service();
		service.setCityid(cityid);
		view.addObject("service", service);

		List<Category> categories = categoryDao.findAll();
		view.addObject("categories", categories);

		view.addObject(KEY_BACK_LINK, backStack.getBackLink("region/addservice"));
		view.setViewName(VIEW_PREFIX + "/addservice");
		return view;
	}

	@RequestMapping("/addservice")
	public ModelAndView addService(HttpSession session, @Valid Service service, BindingResult result) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
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

		view.addObject(KEY_BACK_LINK, backStack.getBackLink("region/addservice"));
		view.setViewName(VIEW_PREFIX + "/addservice");
		return view;
	}

	@RequestMapping("/deleteservice")
	public ModelAndView deleteService(HttpSession session,
			@RequestParam("serviceids") String serviceids) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		backStack.push(new PageState("region/deleteservice", null));

		boolean isException = false;
		List<Integer> serviceidList = SuUtil.getIds(serviceids);
		try {
			regionService.deleteServices(serviceidList);
		} catch (SuException e) {
			setPageArg(session, new MsgArg(KEY_INFO_MSG, "删除失败。" + e.getMessage()));
			isException = true;
		}
		if (!isException) {
			setPageArg(session, new MsgArg(KEY_INFO_MSG, "删除成功。"));
		}
		view.setViewName("redirect:" + VIEW_PREFIX + "/service/back.html");
		return view;
	}
}
