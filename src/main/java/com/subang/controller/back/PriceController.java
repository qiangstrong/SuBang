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

import com.subang.bean.MsgArg;
import com.subang.bean.PageArg;
import com.subang.bean.PageState;
import com.subang.bean.SearchArg;
import com.subang.controller.BaseController;
import com.subang.domain.Category;
import com.subang.domain.ClothesType;
import com.subang.domain.Price;
import com.subang.tool.BackStack;
import com.subang.tool.SuException;
import com.subang.util.SuUtil;
import com.subang.util.WebConst;

@Controller
@RequestMapping("/back/price")
public class PriceController extends BaseController {

	private static final String VIEW_PREFIX = WebConst.BACK_PREFIX + "/price";

	@RequestMapping("/category/back")
	public ModelAndView indexBack(HttpSession session) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		backStack.pop();
		view.setViewName("redirect:" + VIEW_PREFIX + "/category.html");
		return view;
	}

	@RequestMapping("/category")
	public ModelAndView listCategory(HttpSession session) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		backStack.clear("price/category");

		if (!backStack.isTop("price/category")) {
			backStack.push(new PageState("price/category", null));
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

		List<Category> categorys = categoryDao.findAll();
		view.addObject("categorys", categorys);
		view.setViewName(VIEW_PREFIX + "/category");
		return view;
	}

	@RequestMapping("/showaddcategory")
	public ModelAndView showAddCategory(HttpSession session) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		backStack.push(new PageState("price/addcategory", null));

		view.addObject("category", new Category());
		view.addObject(KEY_BACK_LINK, backStack.getBackLink("price/addcategory"));
		view.setViewName(VIEW_PREFIX + "/addcategory");
		return view;
	}

	@RequestMapping("/addcategory")
	public ModelAndView addCategory(HttpServletRequest request, @Valid Category category,
			BindingResult result) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(request.getSession());
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
		view.addObject(KEY_BACK_LINK, backStack.getBackLink("price/addcategory"));
		view.setViewName(VIEW_PREFIX + "/addcategory");
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
			setPageArg(session, new MsgArg(KEY_INFO_MSG, "删除失败。" + e.getMessage()));
			isException = true;
		}
		if (!isException) {
			setPageArg(session, new MsgArg(KEY_INFO_MSG, "删除成功。"));
		}
		view.setViewName("redirect:" + VIEW_PREFIX + "/category.html");
		return view;
	}

	@RequestMapping("/showmodifycategory")
	public ModelAndView showModifyCategory(HttpSession session,
			@RequestParam("categoryid") Integer categoryid) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		backStack.push(new PageState("price/modifycategory", null));

		Category category = categoryDao.get(categoryid);
		if (category == null) {
			setPageArg(session, new MsgArg(KEY_INFO_MSG, "修改失败。数据不存在。"));
			view.setViewName("redirect:" + backStack.getBackLink("price/modifycategory"));
			return view;
		}
		view.addObject("category", category);
		view.addObject(KEY_BACK_LINK, backStack.getBackLink("price/modifycategory"));
		view.setViewName(VIEW_PREFIX + "/modifycategory");
		return view;
	}

	@RequestMapping("/modifycategory")
	public ModelAndView modifyCategory(HttpServletRequest request, @Valid Category category,
			BindingResult result) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(request.getSession());
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
		view.addObject(KEY_BACK_LINK, backStack.getBackLink("price/modifycategory"));
		view.setViewName(VIEW_PREFIX + "/modifycategory");
		return view;
	}

	@RequestMapping("/price/back")
	public ModelAndView clothesBack(HttpSession session) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		backStack.pop();
		PageState pageState = backStack.peek();
		view.setViewName("redirect:" + VIEW_PREFIX + "/price.html?categoryid="
				+ pageState.getUpperid());
		return view;
	}

	@RequestMapping("/price.html")
	public ModelAndView listPrice(HttpSession session,
			@RequestParam("categoryid") Integer categoryid) {
		ModelAndView view = new ModelAndView();

		BackStack backStack = getBackStack(session);
		backStack.push(new PageState("price/price", categoryid));
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

		Category category = categoryDao.get(categoryid);
		String desMsg = "类别名称：" + category.getName() + "。此类别下的价格如下：";
		view.addObject(KEY_DES_MSG, desMsg);

		List<Price> prices = priceDao.findByCategoryid(categoryid);
		view.addObject("prices", prices);
		view.addObject("categoryid", categoryid);

		view.setViewName(VIEW_PREFIX + "/price");
		return view;
	}

	@RequestMapping("/showaddprice")
	public ModelAndView showAddPrice(HttpSession session,
			@RequestParam("categoryid") Integer categoryid) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		backStack.push(new PageState("price/addprice", null));

		Price price = new Price();
		price.setCategoryid(categoryid);
		view.addObject("price", price);
		view.addObject(KEY_BACK_LINK, backStack.getBackLink("price/addprice"));
		view.setViewName(VIEW_PREFIX + "/addprice");
		return view;
	}

	@RequestMapping("/addprice")
	public ModelAndView addPrice(HttpSession session, @Valid Price price, BindingResult result) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
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
		view.addObject(KEY_BACK_LINK, backStack.getBackLink("price/addprice"));
		view.setViewName(VIEW_PREFIX + "/addprice");
		return view;
	}

	@RequestMapping("/deleteprice")
	public ModelAndView deletePrice(HttpSession session, @RequestParam("priceids") String priceids) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		backStack.push(new PageState("price/deleteprice", null));

		boolean isException = false;
		List<Integer> priceidList = SuUtil.getIds(priceids);
		try {
			priceService.deletePrices(priceidList);
		} catch (SuException e) {
			setPageArg(session, new MsgArg(KEY_INFO_MSG, "删除失败。" + e.getMessage()));
			isException = true;
		}
		if (!isException) {
			setPageArg(session, new MsgArg(KEY_INFO_MSG, "删除成功。"));
		}
		view.setViewName("redirect:" + VIEW_PREFIX + "/price/back.html");
		return view;
	}

	@RequestMapping("/showmodifyprice")
	public ModelAndView showModifyPrice(HttpSession session,
			@RequestParam("priceid") Integer priceid) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		backStack.push(new PageState("price/modifyprice", null));

		Price price = priceDao.get(priceid);
		if (price == null) {
			setPageArg(session, new MsgArg(KEY_INFO_MSG, "修改失败。数据不存在。"));
			view.setViewName("redirect:" + backStack.getBackLink("price/modifyprice"));
			return view;
		}
		view.addObject("price", price);
		view.addObject(KEY_BACK_LINK, backStack.getBackLink("price/modifyprice"));
		view.setViewName(VIEW_PREFIX + "/modifyprice");
		return view;
	}

	@RequestMapping("/modifyprice")
	public ModelAndView modifyPrice(HttpSession session, @Valid Price price, BindingResult result) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
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
		view.addObject(KEY_BACK_LINK, backStack.getBackLink("price/modifyprice"));
		view.setViewName(VIEW_PREFIX + "/modifyprice");
		return view;
	}

	@RequestMapping("/clothestype/back")
	public ModelAndView clothestypeBack(HttpSession session) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		backStack.pop();
		PageState pageState = backStack.peek();
		view.setViewName("redirect:" + VIEW_PREFIX + "/clothestype.html?categoryid="
				+ pageState.getSearchArg().getUpperid() + "&type="
				+ pageState.getSearchArg().getType());
		return view;
	}

	@RequestMapping("/clothestype")
	public ModelAndView listClothesType(HttpSession session,
			@RequestParam("categoryid") Integer categoryid, @RequestParam("type") Integer type) {
		ModelAndView view = new ModelAndView();

		BackStack backStack = getBackStack(session);
		backStack.push(new PageState("price/clothestype", new SearchArg(categoryid, type, null)));
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

		PageState pageState = backStack.peek();
		view.addObject(KEY_DES_MSG, getDesMsg(pageState.getSearchArg()));

		List<ClothesType> clothesTypes = priceService.searchClothesType(pageState.getSearchArg());
		view.addObject("clothesTypes", clothesTypes);
		view.addObject("categoryid", categoryid);

		view.setViewName(VIEW_PREFIX + "/clothestype");
		return view;
	}

	private String getDesMsg(SearchArg searchArg) {
		String desMsg = null;
		Category category = categoryDao.get(searchArg.getUpperid());
		switch (searchArg.getType()) {
		case WebConst.SEARCH_INCOMPLETE:
			desMsg = "类别名称：" + category.getName() + "。此类别下不完整的衣物类型如下：";
			break;

		case WebConst.SEARCH_ALL:
			desMsg = "类别名称：" + category.getName() + "。此类别的衣物类型如下：";
			break;
		}
		return desMsg;
	}

	@RequestMapping("/showaddclothestype")
	public ModelAndView showAddClothesType(HttpSession session,
			@RequestParam("categoryid") Integer categoryid) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		backStack.push(new PageState("price/addclothestype", null));

		ClothesType clothesType = new ClothesType();
		clothesType.setCategoryid(categoryid);
		view.addObject("clothesType", clothesType);

		List<Price> prices = priceDao.findByCategoryid(categoryid);
		view.addObject("prices", prices);

		view.addObject(KEY_BACK_LINK, backStack.getBackLink("price/addclothestype"));
		view.setViewName(VIEW_PREFIX + "/addclothestype");
		return view;
	}

	@RequestMapping("/addclothestype")
	public ModelAndView addClothesType(HttpServletRequest request, @Valid ClothesType clothesType,
			BindingResult result) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(request.getSession());
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

		view.addObject(KEY_BACK_LINK, backStack.getBackLink("price/addclothestype"));
		view.setViewName(VIEW_PREFIX + "/addclothestype");
		return view;
	}

	@RequestMapping("deleteclothestype")
	public ModelAndView deleteClothesType(HttpSession session,
			@RequestParam("clothesTypeids") String clothesTypeids) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		backStack.push(new PageState("price/deleteclothestype", null));

		boolean isException = false;
		List<Integer> clothesTypeidList = SuUtil.getIds(clothesTypeids);
		try {
			priceService.deleteClothesTypes(clothesTypeidList);
		} catch (SuException e) {
			setPageArg(session, new MsgArg(KEY_INFO_MSG, "删除失败。" + e.getMessage()));
			isException = true;
		}
		if (!isException) {
			setPageArg(session, new MsgArg(KEY_INFO_MSG, "删除成功。"));
		}
		view.setViewName("redirect:" + VIEW_PREFIX + "/clothestype/back.html");
		return view;
	}

	@RequestMapping("/showmodifyclothestype")
	public ModelAndView showModifyClothesType(HttpSession session,
			@RequestParam("clothesTypeid") Integer clothesTypeid) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		backStack.push(new PageState("price/modifyclothestype", null));

		ClothesType clothesType = clothesTypeDao.get(clothesTypeid);
		if (clothesType == null) {
			setPageArg(session, new MsgArg(KEY_INFO_MSG, "修改失败。数据不存在。"));
			view.setViewName("redirect:" + backStack.getBackLink("price/modifyclothestype"));
			return view;
		}
		view.addObject("clothesType", clothesType);

		List<Price> prices = priceDao.findByCategoryid(clothesType.getCategoryid());
		view.addObject("prices", prices);

		view.addObject(KEY_BACK_LINK, backStack.getBackLink("price/modifyclothestype"));
		view.setViewName(VIEW_PREFIX + "/modifyclothestype");
		return view;
	}

	@RequestMapping("/modifyclothestype")
	public ModelAndView modifyClothesType(HttpServletRequest request,
			@Valid ClothesType clothesType, BindingResult result) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(request.getSession());
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

		view.addObject(KEY_BACK_LINK, backStack.getBackLink("price/modifyclothestype"));
		view.setViewName(VIEW_PREFIX + "/modifyclothestype");
		return view;
	}
}
