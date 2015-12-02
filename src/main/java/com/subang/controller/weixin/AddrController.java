package com.subang.controller.weixin;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.subang.bean.AddrData;
import com.subang.bean.AddrDetail;
import com.subang.controller.BaseController;
import com.subang.domain.Addr;
import com.subang.domain.District;
import com.subang.domain.Region;
import com.subang.domain.User;
import com.subang.util.SuUtil;
import com.subang.util.WebConst;

@Controller("addrController_weixin")
@RequestMapping("/weixin/addr")
public class AddrController extends BaseController {

	private static final String VIEW_PREFIX = WebConst.WEIXIN_PREFIX + "/addr";
	private static final String INDEX_PAGE = VIEW_PREFIX + "/index";

	@RequestMapping("/index")
	public ModelAndView index(HttpSession session) {
		ModelAndView view = new ModelAndView();
		List<AddrDetail> addrDetails = addrDao.findDetailByUserid(getUser(session).getId());
		view.addObject("addrDetails", addrDetails);
		view.setViewName(INDEX_PAGE);
		return view;
	}

	@RequestMapping("/showadd")
	public ModelAndView showAdd(HttpSession session) {
		ModelAndView view = new ModelAndView();
		Addr addr = new Addr();
		prepare(view, getUser(session), addr);
		view.addObject("addr", addr);
		view.setViewName(VIEW_PREFIX + "/add");
		return view;
	}

	@RequestMapping("/select")
	public void getRegion(@RequestParam(value = "cityid", required = false) Integer cityid,
			@RequestParam(value = "districtid", required = false) Integer districtid,
			HttpServletResponse response) {

		if (cityid != null) {
			List<District> districts = districtDao.findValidByCityid(cityid);
			List<Region> regions = regionDao.findByDistrictid(districts.get(0).getId());
			List<List> list = new ArrayList<List>();
			list.add(districts);
			list.add(regions);
			SuUtil.outputJson(response, list);
			return;
		}
		if (districtid != null) {
			List<Region> regions = regionDao.findByDistrictid(districtid);
			SuUtil.outputJson(response, regions);
			return;
		}
	}

	@RequestMapping("/add")
	public ModelAndView add(HttpSession session, @Valid Addr addr, BindingResult result) {
		ModelAndView view = new ModelAndView();
		User user = getUser(session);
		if (result.hasErrors()) {
			prepare(view, getUser(session), addr);
			view.setViewName(VIEW_PREFIX + "/add");
			return view;
		}
		addr.setUserid(user.getId());
		userService.addAddr(addr);
		List<AddrDetail> addrDetails = addrDao.findDetailByUserid(getUser(session).getId());
		view.addObject("addrDetails", addrDetails);
		view.setViewName(INDEX_PAGE);
		return view;
	}

	@RequestMapping("/delete")
	public ModelAndView delete(HttpSession session, @RequestParam("addrid") Integer addrid) {
		ModelAndView view = new ModelAndView();
		userService.deleteAddr(addrid);
		List<AddrDetail> addrDetails = addrDao.findDetailByUserid(getUser(session).getId());
		view.addObject("addrDetails", addrDetails);
		view.setViewName(INDEX_PAGE);
		return view;
	}

	private void prepare(ModelAndView view, User user, Addr addr) {
		AddrData addrData;
		if (addr.getRegionid() == null) {
			addrData = regionService.getAddrDataByUserid(user.getId());
			addr.setDetail(addrData.getDetail());
		} else {
			addrData = regionService.getAddrDataByRegionid(addr.getRegionid());
		}
		view.addObject("citys", addrData.getCitys());
		view.addObject("defaultCityid", addrData.getDefaultCityid());
		view.addObject("districts", addrData.getDistricts());
		view.addObject("defaultDistrictid", addrData.getDefaultDistrictid());
		view.addObject("regions", addrData.getRegions());
		view.addObject("defaultRegionid", addrData.getDefaultRegionid());
	}
}
