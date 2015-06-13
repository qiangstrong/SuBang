package com.subang.controller.weixin;

import java.io.OutputStream;
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

import weixin.popular.util.JsonUtil;

import com.subang.bean.AddrDetail;
import com.subang.controller.BaseController;
import com.subang.domain.Addr;
import com.subang.domain.City;
import com.subang.domain.District;
import com.subang.domain.Region;
import com.subang.domain.User;
import com.subang.util.Common;
import com.subang.util.TimeUtil;
import com.subang.util.WebConst;
import com.subang.util.TimeUtil.Option;

@Controller("addrController_weixin")
@RequestMapping("/weixin/addr")
public class AddrController extends BaseController {

	private static final String VIEW_PREFIX = WebConst.WEIXIN_PREFIX + "/addr";
	private static final String INDEX_PAGE = VIEW_PREFIX + "/index";

	@RequestMapping("/index")
	public ModelAndView index(HttpSession session) {
		ModelAndView view = new ModelAndView();
		List<AddrDetail> addrDetails = frontUserService.listAddrDetailByUserid(getUser(session)
				.getId());
		view.addObject("addrDetails", addrDetails);
		view.setViewName(INDEX_PAGE);
		return view;
	}

	@RequestMapping("/showadd")
	public ModelAndView showAdd() {
		ModelAndView view = new ModelAndView();
		prepare(view);
		view.addObject("addr", new Addr());
		view.setViewName(VIEW_PREFIX + "/add");
		return view;
	}

	@RequestMapping("/select")
	public void getRegion(@RequestParam(required = false, value = "cityid") Integer cityid,
			@RequestParam(required = false, value = "districtid") Integer districtid,
			HttpServletResponse response) throws Exception {
		response.setContentType("application/json;charset=UTF-8");
		OutputStream outputStream = response.getOutputStream();

		if (cityid != null) {
			List<District> districts = frontUserService.listDistrictByCityid(cityid);
			List<Region> regions = frontUserService
					.listRegionByDistrictid(districts.get(0).getId());
			List<List> list = new ArrayList<List>();
			list.add(districts);
			list.add(regions);
			String json = JsonUtil.toJSONString(list);
			Common.outputStreamWrite(outputStream, json);
			return;
		}
		if (districtid != null) {
			List<Region> regions = frontUserService.listRegionByDistrictid(districtid);
			String json = JsonUtil.toJSONString(regions);
			Common.outputStreamWrite(outputStream, json);
			return;
		}
	}

	@RequestMapping("/add")
	public ModelAndView add(HttpSession session, @Valid Addr addr, BindingResult result) {
		ModelAndView view = new ModelAndView();
		if(result.hasErrors()){
			prepare(view);
			view.setViewName(VIEW_PREFIX + "/add");
			return view;
		}
		addr.setUserid(getUser(session).getId());
		frontUserService.addAddr(addr);
		List<AddrDetail> addrDetails = frontUserService.listAddrDetailByUserid(getUser(session)
				.getId());
		view.addObject("addrDetails", addrDetails);
		view.setViewName(INDEX_PAGE);
		return view;
	}

	@RequestMapping("/delete")
	public ModelAndView delete(HttpSession session, @RequestParam("addrid") Integer addrid) {
		ModelAndView view = new ModelAndView();
		frontUserService.deleteAddr(addrid);
		List<AddrDetail> addrDetails = frontUserService.listAddrDetailByUserid(getUser(session)
				.getId());
		view.addObject("addrDetails", addrDetails);
		view.setViewName(INDEX_PAGE);
		return view;
	}

	private void prepare(ModelAndView view) {
		
		List<City> citys = frontUserService.listCity();
		view.addObject("citys", citys);
		List<District> districts = frontUserService.listDistrictByCityid(citys.get(0).getId());
		view.addObject("districts", districts);
		List<Region> regions = frontUserService.listRegionByDistrictid(districts.get(0).getId());
		view.addObject("regions", regions);		
	}
}
