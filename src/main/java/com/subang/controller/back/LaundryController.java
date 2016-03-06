package com.subang.controller.back;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.subang.bean.MsgArg;
import com.subang.bean.PageArg;
import com.subang.bean.PageArg.ArgType;
import com.subang.bean.PageState;
import com.subang.bean.SearchArg;
import com.subang.controller.BaseController;
import com.subang.domain.Article;
import com.subang.domain.Cost;
import com.subang.domain.Laundry;
import com.subang.tool.BackStack;
import com.subang.tool.SuException;
import com.subang.util.SuUtil;
import com.subang.util.WebConst;

@Controller
@RequestMapping("/back/laundry")
public class LaundryController extends BaseController {

	private static final String VIEW_PREFIX = WebConst.BACK_PREFIX + "/laundry";
	private static final String INDEX_PAGE = VIEW_PREFIX + "/index";
	private static final String KEY_DATA = "laundrys";

	/**
	 * 商家
	 */
	@RequestMapping("/index/back")
	public ModelAndView indexBack(HttpSession session) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		backStack.pop();
		view.setViewName("redirect:" + INDEX_PAGE + ".html");
		return view;
	}

	@RequestMapping("/index")
	public ModelAndView index(HttpSession session) {
		ModelAndView view = new ModelAndView();

		// 根页面，清空backStack
		BackStack backStack = getBackStack(session);
		backStack.clear("laundry/index");

		// 解析页面参数，在backStack中，保存页面状态
		PageArg pageArg = getPageArg(session);
		if (pageArg == null || pageArg.getArgType() != ArgType.para) {
			PageArg paraArg = new SearchArg(WebConst.SEARCH_NULL, null);
			backStack.pushOptional(new PageState("laundry/index", paraArg));
		}
		if (pageArg != null) {
			switch (pageArg.getArgType()) {
			case para:
				backStack.push(new PageState("laundry/index", pageArg));
				break;
			case msg:
				MsgArg msgArg = (MsgArg) pageArg;
				view.addObject(msgArg.getKey(), msgArg.getMsg());
				break;
			}
		}

		PageState pageState = backStack.peek();
		view.addObject(KEY_PAGE_STATE, pageState);
		List<Laundry> laundrys = laundryService.searchLaundry(pageState.getSearchArg());
		view.addObject(KEY_DATA, laundrys);
		view.setViewName(INDEX_PAGE);
		return view;
	}

	@RequestMapping("/list")
	public ModelAndView list(HttpSession session) {
		ModelAndView view = new ModelAndView();
		SearchArg searchArg = new SearchArg(WebConst.SEARCH_ALL, null);
		setPageArg(session, searchArg);
		view.setViewName("redirect:" + INDEX_PAGE + ".html");
		return view;
	}

	@RequestMapping("/search")
	public ModelAndView search(HttpSession session, SearchArg searchArg) {
		ModelAndView view = new ModelAndView();
		setPageArg(session, searchArg);
		view.setViewName("redirect:" + INDEX_PAGE + ".html");
		return view;
	}

	@RequestMapping("/showadd")
	public ModelAndView showAdd(HttpSession session) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		backStack.push(new PageState("laundry/add", null));

		view.addObject("laundry", new Laundry());
		view.addObject(KEY_BACK_LINK, backStack.getBackLink("laundry/add"));
		view.setViewName(VIEW_PREFIX + "/add");
		return view;
	}

	@RequestMapping("/add")
	public ModelAndView add(HttpSession session, @Valid Laundry laundry, BindingResult result) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		if (!result.hasErrors()) {
			boolean isException = false;
			try {
				laundryService.addLaundry(laundry);
			} catch (SuException e) {
				view.addObject(KEY_INFO_MSG, "添加失败。" + e.getMessage());
				view.addObject("laundry", laundry);
				isException = true;
			}
			if (!isException) {
				view.addObject(KEY_INFO_MSG, "添加成功。");
			}
		}
		view.addObject(KEY_BACK_LINK, backStack.getBackLink("laundry/add"));
		view.setViewName(VIEW_PREFIX + "/add");
		return view;
	}

	@RequestMapping("/delete")
	public ModelAndView delete(HttpSession session, @RequestParam("laundryids") String laundryids) {
		ModelAndView view = new ModelAndView();
		boolean isException = false;
		try {
			laundryService.deleteLaundrys(SuUtil.getIds(laundryids));
		} catch (SuException e) {
			setPageArg(session, new MsgArg(KEY_INFO_MSG, "删除失败。" + e.getMessage()));
			isException = true;
		}
		if (!isException) {
			setPageArg(session, new MsgArg(KEY_INFO_MSG, "删除成功。"));
		}
		view.setViewName("redirect:" + INDEX_PAGE + ".html");
		return view;
	}

	@RequestMapping("/showmodify")
	public ModelAndView showModify(HttpSession session, @RequestParam("laundryid") Integer laundryid) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		backStack.push(new PageState("laundry/modify", null));

		Laundry laundry = laundryDao.get(laundryid);
		if (laundry == null) {
			setPageArg(session, new MsgArg(KEY_INFO_MSG, "修改失败。数据不存在。"));
			view.setViewName("redirect:" + backStack.getBackLink("laundry/modify"));
			return view;
		}
		view.addObject("laundry", laundry);
		view.addObject(KEY_BACK_LINK, backStack.getBackLink("laundry/modify"));
		view.setViewName(VIEW_PREFIX + "/modify");
		return view;
	}

	@RequestMapping("/modify")
	public ModelAndView modify(HttpSession session, @Valid Laundry laundry, BindingResult result) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		if (laundry.getId() == null) {
			view.addObject(KEY_INFO_MSG, "修改失败。发生错误。");
			view.addObject("laundry", laundry);
		} else if (!result.hasErrors()) {
			boolean isException = false;
			try {
				laundryService.modifyLaundry(laundry);
			} catch (SuException e) {
				view.addObject(KEY_INFO_MSG, "修改失败。" + e.getMessage());
				view.addObject("laundry", laundry);
				isException = true;
			}
			if (!isException) {
				view.addObject(KEY_INFO_MSG, "修改成功。");
			}
		}
		view.addObject(KEY_BACK_LINK, backStack.getBackLink("laundry/modify"));
		view.setViewName(VIEW_PREFIX + "/modify");
		return view;
	}

	@RequestMapping("/order")
	public ModelAndView listOrder(HttpSession session, @RequestParam("laundryid") Integer laundryid) {
		ModelAndView view = new ModelAndView();
		SearchArg searchArg = new SearchArg(WebConst.SEARCH_ORDER_LAUNDRYID, laundryid.toString());
		setPageArg(session, searchArg);
		view.setViewName("redirect:" + WebConst.BACK_PREFIX + "/order/index.html");
		return view;
	}

	/**
	 * 商户价格
	 */
	@RequestMapping("/cost/back")
	public ModelAndView costBack(HttpSession session) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		backStack.pop();
		PageState pageState = backStack.peek();
		view.setViewName("redirect:" + VIEW_PREFIX + "/cost.html?laundryid="
				+ pageState.getUpperid());
		return view;
	}

	@RequestMapping("/cost")
	public ModelAndView listCost(HttpSession session, @RequestParam("laundryid") Integer laundryid) {
		ModelAndView view = new ModelAndView();

		BackStack backStack = getBackStack(session);
		backStack.push(new PageState("laundry/cost", laundryid));
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

		Laundry laundry = laundryDao.get(laundryid);
		String desMsg = "商家名称：" + laundry.getName() + "。此商家的价格如下：";
		view.addObject(KEY_DES_MSG, desMsg);

		List<Cost> costs = costDao.findDetailByLaundryid(laundryid);
		view.addObject("costs", costs);
		view.addObject("laundryid", laundryid);

		view.setViewName(VIEW_PREFIX + "/cost");
		return view;
	}

	@RequestMapping("/showaddcost")
	public ModelAndView showAddCost(HttpSession session,
			@RequestParam("laundryid") Integer laundryid) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		backStack.push(new PageState("laundry/addcost", null));

		Cost cost = new Cost();
		cost.setLaundryid(laundryid);
		view.addObject("cost", cost);

		List<Article> articles = articleDao.findAll();
		view.addObject("articles", articles);

		view.addObject(KEY_BACK_LINK, backStack.getBackLink("laundry/addcost"));
		view.setViewName(VIEW_PREFIX + "/addcost");
		return view;
	}

	@RequestMapping("/addcost")
	public ModelAndView addCost(HttpSession session, @Valid Cost cost, BindingResult result) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		if (!result.hasErrors()) {
			boolean isException = false;
			try {
				laundryService.addCost(cost);
			} catch (SuException e) {
				view.addObject(KEY_INFO_MSG, "添加失败。" + e.getMessage());
				view.addObject("cost", cost);
				isException = true;
			}
			if (!isException) {
				view.addObject(KEY_INFO_MSG, "添加成功。");
			}
		}

		List<Article> articles = articleDao.findAll();
		view.addObject("articles", articles);

		view.addObject(KEY_BACK_LINK, backStack.getBackLink("laundry/addcost"));
		view.setViewName(VIEW_PREFIX + "/addcost");
		return view;
	}

	@RequestMapping("/deletecost")
	public ModelAndView deleteCost(HttpSession session, @RequestParam("costids") String costids) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		backStack.push(new PageState("laundry/deletecost", null));

		boolean isException = false;
		List<Integer> costidList = SuUtil.getIds(costids);
		try {
			laundryService.deleteCosts(costidList);
		} catch (SuException e) {
			setPageArg(session, new MsgArg(KEY_INFO_MSG, "删除失败。" + e.getMessage()));
			isException = true;
		}
		if (!isException) {
			setPageArg(session, new MsgArg(KEY_INFO_MSG, "删除成功。"));
		}
		view.setViewName("redirect:" + VIEW_PREFIX + "/cost/back.html");
		return view;
	}

	@RequestMapping("/showmodifycost")
	public ModelAndView showModifyCost(HttpSession session, @RequestParam("costid") Integer costid) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		backStack.push(new PageState("laundry/modifycost", null));

		Cost cost = costDao.get(costid);
		if (cost == null) {
			setPageArg(session, new MsgArg(KEY_INFO_MSG, "修改失败。数据不存在。"));
			view.setViewName("redirect:" + backStack.getBackLink("laundry/modifycost"));
			return view;
		}
		view.addObject("cost", cost);
		List<Article> articles = articleDao.findAll();
		view.addObject("articles", articles);
		view.addObject(KEY_BACK_LINK, backStack.getBackLink("laundry/modifycost"));
		view.setViewName(VIEW_PREFIX + "/modifycost");
		return view;
	}

	@RequestMapping("/modifycost")
	public ModelAndView modifyCost(HttpSession session, @Valid Cost cost, BindingResult result) {
		ModelAndView view = new ModelAndView();
		BackStack backStack = getBackStack(session);
		if (cost.getId() == null) {
			view.addObject(KEY_INFO_MSG, "修改失败。发生错误。");
			view.addObject("cost", cost);
		} else if (!result.hasErrors()) {
			boolean isException = false;
			try {
				laundryService.modifyCost(cost);
			} catch (SuException e) {
				view.addObject(KEY_INFO_MSG, "修改失败。" + e.getMessage());
				view.addObject("cost", cost);
				isException = true;
			}
			if (!isException) {
				view.addObject(KEY_INFO_MSG, "修改成功。");
			}
		}
		List<Article> articles = articleDao.findAll();
		view.addObject("articles", articles);
		view.addObject(KEY_BACK_LINK, backStack.getBackLink("laundry/modifycost"));
		view.setViewName(VIEW_PREFIX + "/modifycost");
		return view;
	}
}
