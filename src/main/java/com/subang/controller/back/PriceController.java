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

import com.subang.bean.SearchArg;
import com.subang.controller.BaseController;
import com.subang.domain.Category;
import com.subang.domain.ClothesType;
import com.subang.domain.Price;
import com.subang.exception.SuException;
import com.subang.util.SuUtil;
import com.subang.util.WebConst;

@Controller
@RequestMapping("/back/price")
public class PriceController extends BaseController {

	@RequestMapping("/category")
	public ModelAndView listCategory(HttpSession session) {
		ModelAndView view = new ModelAndView();
		invalidtePageState(session);
		List<Category> categorys = categoryDao.findAll();
		view.addObject("categorys", categorys);

		view.addObject(KEY_ERR_MSG, session.getAttribute(KEY_ERR_MSG));
		session.removeAttribute(KEY_ERR_MSG);
		view.addObject(KEY_INFO_MSG, session.getAttribute(KEY_INFO_MSG));
		session.removeAttribute(KEY_INFO_MSG);

		view.setViewName(WebConst.BACK_PREFIX + "/price/category");
		return view;
	}

	@RequestMapping("/showaddcategory")
	public ModelAndView showAddCategory() {
		ModelAndView view = new ModelAndView();
		view.addObject("category", new Category());
		view.setViewName(WebConst.BACK_PREFIX + "/price/addcategory");
		return view;
	}

	@RequestMapping("/addcategory")
	public ModelAndView addCategory(HttpServletRequest request, @Valid Category category,
			BindingResult result) {
		ModelAndView view = new ModelAndView();
		if (!result.hasErrors()) {
			boolean isException = false;
			try {
				MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
				MultipartFile icon = multipartRequest.getFile("iconImg");
				priceService.addCategory(category, icon);
			} catch (SuException e) {
				view.addObject(KEY_INFO_MSG, "添加失败。" + e.getMessage());
				view.addObject("category", category);
				isException = true;
			}
			if (!isException) {
				view.addObject(KEY_INFO_MSG, "添加成功。");
			}
		}
		view.setViewName(WebConst.BACK_PREFIX + "/price/addcategory");
		return view;
	}

	@RequestMapping("/deletecategory")
	public ModelAndView deleteCategory(HttpSession session,
			@RequestParam("categoryids") String categoryids) {
		ModelAndView view = new ModelAndView();
		boolean isException = false;
		try {
			priceService.deleteCategorys(SuUtil.getIds(categoryids));
		} catch (SuException e) {
			session.setAttribute(KEY_INFO_MSG, "删除失败。" + e.getMessage());
			isException = true;
		}
		if (!isException) {
			session.setAttribute(KEY_INFO_MSG, "删除成功。");
		}
		view.setViewName("redirect:" + WebConst.BACK_PREFIX + "/price/category.html");
		return view;
	}

	@RequestMapping("/showmodifycategory")
	public ModelAndView showModifyCategory(@RequestParam("categoryid") Integer categoryid) {
		ModelAndView view = new ModelAndView();
		Category category = categoryDao.get(categoryid);
		view.addObject("category", category);
		view.setViewName(WebConst.BACK_PREFIX + "/price/modifycategory");
		return view;
	}

	@RequestMapping("/modifycategory")
	public ModelAndView modifyCategory(HttpServletRequest request, @Valid Category category,
			BindingResult result) {
		ModelAndView view = new ModelAndView();
		if (category.getId() == null) {
			view.addObject(KEY_INFO_MSG, "修改失败。发生错误。");
			view.addObject("category", category);
		} else if (!result.hasErrors()) {
			boolean isException = false;
			try {
				MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
				MultipartFile icon = multipartRequest.getFile("iconImg");
				priceService.modifyCategory(category, icon);
			} catch (SuException e) {
				view.addObject(KEY_INFO_MSG, "修改失败。" + e.getMessage());
				view.addObject("category", category);
				isException = true;
			}
			if (!isException) {
				view.addObject(KEY_INFO_MSG, "修改成功。");
			}
		}
		view.setViewName(WebConst.BACK_PREFIX + "/price/modifycategory");
		return view;
	}

	@RequestMapping("/price.html")
	public ModelAndView listPrice(HttpSession session,
			@RequestParam("categoryid") Integer categoryid) {
		ModelAndView view = new ModelAndView();
		List<Price> prices = priceDao.findByCategoryid(categoryid);
		view.addObject("prices", prices);
		view.addObject("categoryid", categoryid);

		Category category = categoryDao.get(categoryid);
		String desMsg = "类别名称：" + category.getName() + "。此类别下的价格如下：";
		view.addObject(KEY_DES_MSG, desMsg);

		view.addObject(KEY_ERR_MSG, session.getAttribute(KEY_ERR_MSG));
		session.removeAttribute(KEY_ERR_MSG);
		view.addObject(KEY_INFO_MSG, session.getAttribute(KEY_INFO_MSG));
		session.removeAttribute(KEY_INFO_MSG);

		view.setViewName(WebConst.BACK_PREFIX + "/price/price");
		return view;
	}

	@RequestMapping("/showaddprice")
	public ModelAndView showAddPrice(@RequestParam("categoryid") Integer categoryid) {
		ModelAndView view = new ModelAndView();
		Price price = new Price();
		price.setCategoryid(categoryid);
		view.addObject("price", price);
		view.setViewName(WebConst.BACK_PREFIX + "/price/addprice");
		return view;
	}

	@RequestMapping("/addprice")
	public ModelAndView addPrice(@Valid Price price, BindingResult result) {
		ModelAndView view = new ModelAndView();
		if (!result.hasErrors()) {
			boolean isException = false;
			try {
				priceService.addPrice(price);
			} catch (SuException e) {
				view.addObject(KEY_INFO_MSG, "添加失败。" + e.getMessage());
				view.addObject("price", price);
				isException = true;
			}
			if (!isException) {
				view.addObject(KEY_INFO_MSG, "添加成功。");
			}
		}
		view.setViewName(WebConst.BACK_PREFIX + "/price/addprice");
		return view;
	}

	@RequestMapping("/deleteprice")
	public ModelAndView deletePrice(HttpSession session, @RequestParam("priceids") String priceids) {
		ModelAndView view = new ModelAndView();
		boolean isException = false;
		List<Integer> priceidList = SuUtil.getIds(priceids);
		try {
			priceService.deletePrices(priceidList);
		} catch (SuException e) {
			session.setAttribute(KEY_INFO_MSG, "删除失败。" + e.getMessage());
			isException = true;
		}
		if (!isException) {
			session.setAttribute(KEY_INFO_MSG, "删除成功。");
		}
		Price price = priceDao.get(priceidList.get(0));
		view.setViewName("redirect:" + WebConst.BACK_PREFIX + "/price/price.html?categoryid="
				+ price.getCategoryid());
		return view;
	}

	@RequestMapping("/showmodifyprice")
	public ModelAndView showModifyPrice(@RequestParam("priceid") Integer priceid) {
		ModelAndView view = new ModelAndView();
		Price price = priceDao.get(priceid);
		view.addObject("price", price);
		view.setViewName(WebConst.BACK_PREFIX + "/price/modifyprice");
		return view;
	}

	@RequestMapping("/modifyprice")
	public ModelAndView modifyPrice(@Valid Price price, BindingResult result) {
		ModelAndView view = new ModelAndView();
		if (price.getId() == null) {
			view.addObject(KEY_INFO_MSG, "修改失败。发生错误。");
			view.addObject("price", price);
		} else if (!result.hasErrors()) {
			boolean isException = false;
			try {
				priceService.modifyPrice(price);
			} catch (SuException e) {
				view.addObject(KEY_INFO_MSG, "修改失败。" + e.getMessage());
				view.addObject("price", price);
				isException = true;
			}
			if (!isException) {
				view.addObject(KEY_INFO_MSG, "修改成功。");
			}
		}
		view.setViewName(WebConst.BACK_PREFIX + "/price/modifyprice");
		return view;
	}

	@RequestMapping("/clothestype")
	public ModelAndView listClothesType(HttpSession session,
			@RequestParam("categoryid") Integer categoryid, @RequestParam("type") int type) {
		ModelAndView view = new ModelAndView();
		Category category = categoryDao.get(categoryid);
		List<ClothesType> clothesTypes = null;
		String desMsg = null;
		if (type == WebConst.SEARCH_NULL) {
			type = getPageState(session).getSearchArg().getType();
		}
		if (type == WebConst.SEARCH_INCOMPLETE) {
			clothesTypes = clothesTypeDao.findIncomplete(categoryid);
			desMsg = "类别名称：" + category.getName() + "。此类别下不完整的衣物类型如下：";
			savePageState(session, new SearchArg(WebConst.SEARCH_INCOMPLETE, null));
		} else {
			clothesTypes = clothesTypeDao.findDetailByCategoryid(categoryid);
			desMsg = "类别名称：" + category.getName() + "。此类别的衣物类型如下：";
			savePageState(session, new SearchArg(WebConst.SEARCH_ALL, null));
		}
		view.addObject("clothesTypes", clothesTypes);
		view.addObject(KEY_DES_MSG, desMsg);
		view.addObject("categoryid", categoryid);

		view.addObject(KEY_ERR_MSG, session.getAttribute(KEY_ERR_MSG));
		session.removeAttribute(KEY_ERR_MSG);
		view.addObject(KEY_INFO_MSG, session.getAttribute(KEY_INFO_MSG));
		session.removeAttribute(KEY_INFO_MSG);

		view.setViewName(WebConst.BACK_PREFIX + "/price/clothestype");
		return view;
	}

	@RequestMapping("/showaddclothestype")
	public ModelAndView showAddClothesType(@RequestParam("categoryid") Integer categoryid) {
		ModelAndView view = new ModelAndView();
		ClothesType clothesType = new ClothesType();
		clothesType.setCategoryid(categoryid);
		view.addObject("clothesType", clothesType);

		List<Price> prices = priceDao.findByCategoryid(categoryid);
		view.addObject("prices", prices);

		view.setViewName(WebConst.BACK_PREFIX + "/price/addclothestype");
		return view;
	}

	@RequestMapping("/addclothestype")
	public ModelAndView addClothesType(HttpServletRequest request, @Valid ClothesType clothesType,
			BindingResult result) {
		ModelAndView view = new ModelAndView();
		if (!result.hasErrors()) {
			boolean isException = false;
			try {
				MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
				MultipartFile icon = multipartRequest.getFile("iconImg");
				priceService.addClothesType(clothesType, icon);
			} catch (SuException e) {
				view.addObject(KEY_INFO_MSG, "添加失败。" + e.getMessage());
				view.addObject("clothesType", clothesType);
				isException = true;
			}
			if (!isException) {
				view.addObject(KEY_INFO_MSG, "添加成功。");
			}
		}
		List<Price> prices = priceDao.findByCategoryid(clothesType.getCategoryid());
		view.addObject("prices", prices);

		view.setViewName(WebConst.BACK_PREFIX + "/price/addclothestype");
		return view;
	}

	@RequestMapping("deleteclothestype")
	public ModelAndView deleteClothesType(HttpSession session,
			@RequestParam("clothesTypeids") String clothesTypeids) {
		ModelAndView view = new ModelAndView();
		boolean isException = false;
		List<Integer> clothesTypeidList = SuUtil.getIds(clothesTypeids);
		try {
			priceService.deleteClothesTypes(clothesTypeidList);
		} catch (SuException e) {
			session.setAttribute(KEY_INFO_MSG, "删除失败。" + e.getMessage());
			isException = true;
		}
		if (!isException) {
			session.setAttribute(KEY_INFO_MSG, "删除成功。");
		}
		ClothesType clothesType = clothesTypeDao.get(clothesTypeidList.get(0));
		view.setViewName("redirect:" + WebConst.BACK_PREFIX + "/price/clothestype.html?categoryid="
				+ clothesType.getCategoryid());
		return view;
	}

	@RequestMapping("/showmodifyclothestype")
	public ModelAndView showModifyClothesType(@RequestParam("clothesTypeid") Integer clothesTypeid) {
		ModelAndView view = new ModelAndView();
		ClothesType clothesType = clothesTypeDao.get(clothesTypeid);
		view.addObject("clothesType", clothesType);

		List<Price> prices = priceDao.findByCategoryid(clothesType.getCategoryid());
		view.addObject("prices", prices);

		view.setViewName(WebConst.BACK_PREFIX + "/price/modifyclothestype");
		return view;
	}

	@RequestMapping("/modifyclothestype")
	public ModelAndView modifyClothesType(HttpServletRequest request,
			@Valid ClothesType clothesType, BindingResult result) {
		ModelAndView view = new ModelAndView();
		if (clothesType.getId() == null) {
			view.addObject(KEY_INFO_MSG, "修改失败。发生错误。");
			view.addObject("clothesType", clothesType);
		} else if (!result.hasErrors()) {
			boolean isException = false;
			try {
				MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
				MultipartFile icon = multipartRequest.getFile("iconImg");
				priceService.modifyClothesType(clothesType, icon);
			} catch (SuException e) {
				view.addObject(KEY_INFO_MSG, "修改失败。" + e.getMessage());
				view.addObject("clothesType", clothesType);
				isException = true;
			}
			if (!isException) {
				view.addObject(KEY_INFO_MSG, "修改成功。");
			}
		}

		List<Price> prices = priceDao.findByCategoryid(clothesType.getCategoryid());
		view.addObject("prices", prices);

		view.setViewName(WebConst.BACK_PREFIX + "/price/modifyclothestype");
		return view;
	}
}
