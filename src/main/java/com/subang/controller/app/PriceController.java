package com.subang.controller.app;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.subang.bean.Identity;
import com.subang.controller.BaseController;
import com.subang.domain.Category;
import com.subang.domain.Price;
import com.subang.domain.User;
import com.subang.util.SuUtil;

@Controller("priceController_app")
@RequestMapping("/app/price")
public class PriceController extends BaseController {

	@RequestMapping("listcategory")
	public void listCategory(Identity identity, @RequestParam("cityid") Integer cityid,
			@RequestParam(value = "filter", required = false) String filter,
			HttpServletResponse response) {
		User user = getUser(identity);
		List<Category> categorys = regionService.listByCityid(user.getId(), cityid);
		SuUtil.doFilter(filter, categorys, Category.class);
		SuUtil.outputJson(response, categorys);
	}

	@RequestMapping("getcategory")
	public void getCategory(@RequestParam("categoryid") Integer categoryid,
			HttpServletResponse response) {
		Category category = categoryDao.get(categoryid);
		SuUtil.outputJson(response, category);
	}

	@RequestMapping("listprice")
	public void listPrice(@RequestParam("categoryid") Integer categoryid,
			@RequestParam(value = "filter", required = false) String filter,
			HttpServletResponse response) {
		List<Price> prices = priceDao.findByCategoryid(categoryid);
		SuUtil.doFilter(filter, prices, Price.class);
		SuUtil.outputJson(response, prices);
	}

}
