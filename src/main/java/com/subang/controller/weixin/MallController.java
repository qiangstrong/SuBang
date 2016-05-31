package com.subang.controller.weixin;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.subang.bean.AddrDetail;
import com.subang.bean.RecordDetail;
import com.subang.controller.BaseController;
import com.subang.domain.Goods;
import com.subang.domain.Payment.PayType;
import com.subang.domain.Record;
import com.subang.domain.TicketType;
import com.subang.domain.User;
import com.subang.tool.SuException;
import com.subang.util.WebConst;

@Controller("mallController_weixin")
@RequestMapping("/weixin/mall")
public class MallController extends BaseController {

	private static final String VIEW_PREFIX = WebConst.WEIXIN_PREFIX + "/mall";
	private static final String INDEX_PAGE = VIEW_PREFIX + "/index";

	@RequestMapping("/index")
	public ModelAndView index(HttpSession session,
			@RequestParam(value = "type", required = false) Integer type) {
		ModelAndView view = new ModelAndView();
		User user = getUser(session);
		view.addObject("user", user);
		if (type == null) {
			type = WebConst.GOODS_TYPE_ALL;
		}
		List<TicketType> ticketTypes = null;
		List<Goods> goodss = null;
		if (type == WebConst.GOODS_TYPE_REAL) {
			ticketTypes = new ArrayList<TicketType>();
			goodss = goodsDao.findValidAll();
		} else if (type == WebConst.GOODS_TYPE_VIRTUAL) {
			ticketTypes = ticketTypeDao.findDetailValidAll();
			goodss = new ArrayList<Goods>();
		} else {
			ticketTypes = ticketTypeDao.findDetailValidAll();
			goodss = goodsDao.findValidAll();
		}
		view.addObject("ticketTypes", ticketTypes);
		view.addObject("goodss", goodss);

		view.setViewName(INDEX_PAGE);
		return view;
	}

	@RequestMapping("/goodsdetail")
	public ModelAndView getGoodsDetail(HttpSession session, @RequestParam("type") Integer type,
			@RequestParam("goodsid") Integer goodsid,
			@RequestParam(value = "addrid", required = false) Integer addrid) {
		ModelAndView view = new ModelAndView();
		view.addObject("type", type);
		if (type == WebConst.GOODS_TYPE_REAL) {
			User user = getUser(session);
			if (addrid == null) {
				addrid = user.getAddrid();
			}
			if (addrid != null) {
				view.addObject("addrDetail", addrDao.getDetail(addrid));
			}
			Goods goods = goodsDao.get(goodsid);
			view.addObject("goods", goods);
		} else if (type == WebConst.GOODS_TYPE_VIRTUAL) {
			TicketType ticketType = ticketTypeDao.getDetail(goodsid);
			view.addObject("ticketType", ticketType);
		}
		view.setViewName(VIEW_PREFIX + "/goodsdetail");
		return view;
	}

	@RequestMapping("/addr")
	public ModelAndView listAddr(HttpSession session, @RequestParam("type") Integer type,
			@RequestParam("goodsid") Integer goodsid) {
		ModelAndView view = new ModelAndView();
		List<AddrDetail> addrDetails = addrDao.findDetailByUserid(getUser(session).getId());
		view.addObject("type", type);
		view.addObject("goodsid", goodsid);
		view.addObject("addrDetails", addrDetails);
		view.setViewName(VIEW_PREFIX + "/addr");
		return view;
	}

	@RequestMapping("/addrecord")
	public ModelAndView addRecord(HttpSession session, @RequestParam("type") Integer type,
			@RequestParam("goodsid") Integer goodsid,
			@RequestParam(value = "addrid", required = false) Integer addrid,
			@RequestParam(value = "paytype", required = false) Integer payType) {
		ModelAndView view = new ModelAndView();
		User user = getUser(session);
		view.addObject(KEY_INFO_MSG, "兑换成功。");
		if (type == WebConst.GOODS_TYPE_REAL) {
			Record record = new Record();
			record.setGoodsid(goodsid);
			record.setUserid(user.getId());
			record.setAddrid(addrid);
			try {
				recordService.addRecord(record, PayType.values()[payType]);
			} catch (SuException e) {
				view.addObject(KEY_INFO_MSG, "兑换失败。" + e.getMessage());
			}
		} else if (type == WebConst.GOODS_TYPE_VIRTUAL) {
			try {
				userService.addTicketByScore(user.getId(), goodsid);
			} catch (SuException e) {
				view.addObject(KEY_INFO_MSG, "兑换失败。" + e.getMessage());
			}
		}
		view.setViewName(VIEW_PREFIX + "/addresult");
		return view;
	}

	@RequestMapping("record")
	public ModelAndView listRecord(HttpSession session) {
		ModelAndView view = new ModelAndView();
		User user = getUser(session);
		List<RecordDetail> recordDetails = recordService.searchRecordByUserid(user.getId());
		view.addObject("recordDetails", recordDetails);
		view.setViewName(VIEW_PREFIX + "/record");
		return view;
	}

	@RequestMapping("recorddetail")
	public ModelAndView getRecordDetail(@RequestParam("recordid") Integer recordid) {
		ModelAndView view = new ModelAndView();
		Record record = recordDao.get(recordid);
		Goods goods = goodsDao.get(record.getGoodsid());
		view.addObject("record", record);
		view.addObject("goods", goods);
		view.setViewName(VIEW_PREFIX + "/recorddetail");
		return view;
	}
}
