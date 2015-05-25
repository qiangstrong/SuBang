package com.subang.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.subang.domain.Info;
import com.subang.util.Common;

@Controller
@RequestMapping("/info")
public class InfoController extends BaseController {

	private static final String INDEX_PAGE = "/info/index";
	private static final String KEY_DATA = "infos";

	@RequestMapping("/index")
	public ModelAndView index(HttpSession session) {
		ModelAndView view = new ModelAndView();
		session.removeAttribute(KEY_PAGE_STATE);
		List<Info> infos = backAdminService.listInfo();
		view.addObject(KEY_DATA, infos);
		view.setViewName(INDEX_PAGE);
		return view;
	}

	@RequestMapping("/showmodify")
	public ModelAndView showModify(@RequestParam("infoid") Integer infoid) {
		ModelAndView view = new ModelAndView();
		Info info = backAdminService.getInfo(infoid);
		view.addObject("info", info);
		view.setViewName("info/modify");
		return view;
	}

	@RequestMapping("/modify")
	public ModelAndView modify(HttpServletRequest request, Info info) {
		ModelAndView view = new ModelAndView();

		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile price_img = multipartRequest.getFile("price_img");
		MultipartFile scope_img = multipartRequest.getFile("scope_img");
		Common.saveMultipartFile(price_img, info.getPrice_path());
		Common.saveMultipartFile(scope_img, info.getScope_path());

		backAdminService.modifyInfo(info);
		view.addObject("info", info);
		view.addObject(KEY_INFO_MSG, "修改成功。");
		view.setViewName("info/modify");
		return view;
	}
}
