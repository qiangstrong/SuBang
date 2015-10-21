package com.subang.controller.weixin;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.subang.controller.BaseController;
import com.subang.domain.Clothes;
import com.subang.domain.Price;
import com.subang.util.WebConst;

@Controller("priceController_weixin")
@RequestMapping("/weixin/price")
public class PriceController extends BaseController {
	private static final String VIEW_PREFIX = WebConst.WEIXIN_PREFIX + "/price";

	@RequestMapping("/index")
	public ModelAndView index(@RequestParam("categoryid") Integer categoryid) {
		ModelAndView view = new ModelAndView();
		List<Price> prices = priceDao.findByCategoryid(categoryid);
		List<List<Clothes>> clothessList = new ArrayList<List<Clothes>>();
		for (Price price : prices) {
			clothessList.add(clothesDao.findByOrderid(price.getId()));
		}
		view.addObject("prices", prices);
		view.addObject("clothessList", clothessList);
		view.setViewName(VIEW_PREFIX + "/index");
		return view;
	}

	@RequestMapping("/bag")
	public String getBag() {
		return VIEW_PREFIX + "/bag";
	}
}
