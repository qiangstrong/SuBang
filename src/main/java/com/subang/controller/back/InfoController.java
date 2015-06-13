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

import com.subang.controller.BaseController;
import com.subang.domain.Info;
import com.subang.util.Common;
import com.subang.util.WebConst;

@Controller
@RequestMapping("/back/info")
public class InfoController extends BaseController {

	private static final String INDEX_PAGE = WebConst.BACK_PREFIX+"/info/index";
	private static final String KEY_DATA = "infos";

	@RequestMapping("/index")
	public ModelAndView index(HttpSession session) {
		ModelAndView view = new ModelAndView();
		invalidtePageState(session);
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
		view.setViewName(WebConst.BACK_PREFIX+"/info/modify");
		return view;
	}

	@RequestMapping("/modify")
	public ModelAndView modify(HttpServletRequest request, @Valid Info info ,BindingResult result) {
		ModelAndView view = new ModelAndView();
		if(info.getId()==null){
			view.addObject(KEY_INFO_MSG, "修改失败。发生错误。");
			view.addObject("info", info);
		}else if (!result.hasErrors()) {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultipartFile price_img = multipartRequest.getFile("price_img");
			MultipartFile scope_img = multipartRequest.getFile("scope_img");
			Common.saveMultipartFile(price_img, info.getPrice_path());
			Common.saveMultipartFile(scope_img, info.getScope_path());

			backAdminService.modifyInfo(info);
			view.addObject(KEY_INFO_MSG, "修改成功。");
		}else{
			view.addObject(KEY_INFO_MSG, "修改失败。请检查字段的长度。");
			view.addObject("info", info);
		}
		view.setViewName(WebConst.BACK_PREFIX+"/info/modify");
		return view;
	}
}
