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
import com.subang.domain.Article;
import com.subang.domain.Category;
import com.subang.domain.ClothesType;
import com.subang.domain.Color;
import com.subang.domain.Price;
import com.subang.tool.BackStack;
import com.subang.tool.SuException;
import com.subang.util.SuUtil;
import com.subang.util.WebConst;

@Controller
@RequestMapping("/back/price")
public class PriceController extends BaseController {

	private static final String VIEW_PREFIX = WebConst.BACK_PREFIX + "/price";

	/**
	 * 类别
	 */
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

	/**
	 * 小类
	 */
	@RequestMapping("/price/back")
	public ModelAndView priceBack(HttpSession session) {
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
		String desMsg = "类别名称：" + category.getName() + "。此类别下的小类如下：";
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

	/**
	 * 衣物类型
	 */
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

	/**
	 * 物品
	 */
	@RequestMapping("/article/back")
	public ModelAndView articleBack(HttpSession session) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		backStack.pop();
		view.setViewName("redirect:" + VIEW_PREFIX + "/article.html");
		return view;
	}

	@RequestMapping("/article")
	public ModelAndView listArticle(HttpSession session) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		backStack.clear("price/article");

		if (!backStack.isTop("price/article")) {
			backStack.push(new PageState("price/article", null));
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

		List<Article> articles = articleDao.findAll();
		view.addObject("articles", articles);
		view.setViewName(VIEW_PREFIX + "/article");
		return view;
	}

	@RequestMapping("/showaddarticle")
	public ModelAndView showAddArticle(HttpSession session) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		backStack.push(new PageState("price/addarticle", null));

		view.addObject("article", new Article());
		view.addObject(KEY_BACK_LINK, backStack.getBackLink("price/addarticle"));
		view.setViewName(VIEW_PREFIX + "/addarticle");
		return view;
	}

	@RequestMapping("/addarticle")
	public ModelAndView addArticle(HttpSession session, @Valid Article article, BindingResult result) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		if (!result.hasErrors()) {
			boolean isException = false;
			try {
				priceService.addArticle(article);
			} catch (SuException e) {
				view.addObject(KEY_INFO_MSG, "添加失败。" + e.getMessage());
				isException = true;
			}
			if (!isException) {
				view.addObject(KEY_INFO_MSG, "添加成功。");
			}
		}
		view.addObject(KEY_BACK_LINK, backStack.getBackLink("price/addarticle"));
		view.setViewName(VIEW_PREFIX + "/addarticle");
		return view;
	}

	@RequestMapping("/deletearticle")
	public ModelAndView deleteArticle(HttpSession session,
			@RequestParam("articleids") String articleids) {
		ModelAndView view = new ModelAndView();
		boolean isException = false;
		try {
			priceService.deleteArticles(SuUtil.getIds(articleids));
		} catch (SuException e) {
			setPageArg(session, new MsgArg(KEY_INFO_MSG, "删除失败。" + e.getMessage()));
			isException = true;
		}
		if (!isException) {
			setPageArg(session, new MsgArg(KEY_INFO_MSG, "删除成功。"));
		}
		view.setViewName("redirect:" + VIEW_PREFIX + "/article.html");
		return view;
	}

	@RequestMapping("/showmodifyarticle")
	public ModelAndView showModifyArticle(HttpSession session,
			@RequestParam("articleid") Integer articleid) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		backStack.push(new PageState("price/modifyarticle", null));

		Article article = articleDao.get(articleid);
		if (article == null) {
			setPageArg(session, new MsgArg(KEY_INFO_MSG, "修改失败。数据不存在。"));
			view.setViewName("redirect:" + backStack.getBackLink("price/modifyarticle"));
			return view;
		}
		view.addObject("article", article);
		view.addObject(KEY_BACK_LINK, backStack.getBackLink("price/modifyarticle"));
		view.setViewName(VIEW_PREFIX + "/modifyarticle");
		return view;
	}

	@RequestMapping("/modifyarticle")
	public ModelAndView modifyArticle(HttpSession session, @Valid Article article,
			BindingResult result) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		if (article.getId() == null) {
			view.addObject(KEY_INFO_MSG, "修改失败。发生错误。");
		} else if (!result.hasErrors()) {
			boolean isException = false;
			try {
				priceService.modifyArticle(article);
			} catch (SuException e) {
				view.addObject(KEY_INFO_MSG, "修改失败。" + e.getMessage());
				view.addObject("article", article);
				isException = true;
			}
			if (!isException) {
				view.addObject(KEY_INFO_MSG, "修改成功。");
			}
		}
		view.addObject(KEY_BACK_LINK, backStack.getBackLink("price/modifyarticle"));
		view.setViewName(VIEW_PREFIX + "/modifyarticle");
		return view;
	}

	/**
	 * 颜色
	 */
	@RequestMapping("/color/back")
	public ModelAndView colorBack(HttpSession session) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		backStack.pop();
		view.setViewName("redirect:" + VIEW_PREFIX + "/color.html");
		return view;
	}

	@RequestMapping("/color")
	public ModelAndView listColor(HttpSession session) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		backStack.clear("price/color");

		if (!backStack.isTop("price/color")) {
			backStack.push(new PageState("price/color", null));
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

		List<Color> colors = colorDao.findAll();
		view.addObject("colors", colors);
		view.setViewName(VIEW_PREFIX + "/color");
		return view;
	}

	@RequestMapping("/showaddcolor")
	public ModelAndView showAddColor(HttpSession session) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		backStack.push(new PageState("price/addcolor", null));

		view.addObject("color", new Color());
		view.addObject(KEY_BACK_LINK, backStack.getBackLink("price/addcolor"));
		view.setViewName(VIEW_PREFIX + "/addcolor");
		return view;
	}

	@RequestMapping("/addcolor")
	public ModelAndView addColor(HttpSession session, @Valid Color color, BindingResult result) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		if (!result.hasErrors()) {
			boolean isException = false;
			try {
				priceService.addColor(color);
			} catch (SuException e) {
				view.addObject(KEY_INFO_MSG, "添加失败。" + e.getMessage());
				isException = true;
			}
			if (!isException) {
				view.addObject(KEY_INFO_MSG, "添加成功。");
			}
		}
		view.addObject(KEY_BACK_LINK, backStack.getBackLink("price/addcolor"));
		view.setViewName(VIEW_PREFIX + "/addcolor");
		return view;
	}

	@RequestMapping("/deletecolor")
	public ModelAndView deleteColor(HttpSession session, @RequestParam("colorids") String colorids) {
		ModelAndView view = new ModelAndView();
		boolean isException = false;
		try {
			priceService.deleteColors(SuUtil.getIds(colorids));
		} catch (SuException e) {
			setPageArg(session, new MsgArg(KEY_INFO_MSG, "删除失败。" + e.getMessage()));
			isException = true;
		}
		if (!isException) {
			setPageArg(session, new MsgArg(KEY_INFO_MSG, "删除成功。"));
		}
		view.setViewName("redirect:" + VIEW_PREFIX + "/color.html");
		return view;
	}

	@RequestMapping("/showmodifycolor")
	public ModelAndView showModifyColor(HttpSession session,
			@RequestParam("colorid") Integer colorid) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		backStack.push(new PageState("price/modifycolor", null));

		Color color = colorDao.get(colorid);
		if (color == null) {
			setPageArg(session, new MsgArg(KEY_INFO_MSG, "修改失败。数据不存在。"));
			view.setViewName("redirect:" + backStack.getBackLink("price/modifycolor"));
			return view;
		}
		view.addObject("color", color);
		view.addObject(KEY_BACK_LINK, backStack.getBackLink("price/modifycolor"));
		view.setViewName(VIEW_PREFIX + "/modifycolor");
		return view;
	}

	@RequestMapping("/modifycolor")
	public ModelAndView modifyColor(HttpSession session, @Valid Color color, BindingResult result) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		if (color.getId() == null) {
			view.addObject(KEY_INFO_MSG, "修改失败。发生错误。");
		} else if (!result.hasErrors()) {
			boolean isException = false;
			try {
				priceService.modifyColor(color);
			} catch (SuException e) {
				view.addObject(KEY_INFO_MSG, "修改失败。" + e.getMessage());
				view.addObject("color", color);
				isException = true;
			}
			if (!isException) {
				view.addObject(KEY_INFO_MSG, "修改成功。");
			}
		}
		view.addObject(KEY_BACK_LINK, backStack.getBackLink("price/modifycolor"));
		view.setViewName(VIEW_PREFIX + "/modifycolor");
		return view;
	}
}
