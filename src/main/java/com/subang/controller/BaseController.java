package com.subang.controller;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.subang.bean.Identity;
import com.subang.bean.PageState;
import com.subang.dao.AddrDao;
import com.subang.dao.AdminDao;
import com.subang.dao.BalanceDao;
import com.subang.dao.BannerDao;
import com.subang.dao.CategoryDao;
import com.subang.dao.CityDao;
import com.subang.dao.ClothesDao;
import com.subang.dao.ClothesTypeDao;
import com.subang.dao.DistrictDao;
import com.subang.dao.FaqDao;
import com.subang.dao.FeedbackDao;
import com.subang.dao.HistoryDao;
import com.subang.dao.InfoDao;
import com.subang.dao.LaundryDao;
import com.subang.dao.LocationDao;
import com.subang.dao.NoticeDao;
import com.subang.dao.OrderDao;
import com.subang.dao.PaymentDao;
import com.subang.dao.PriceDao;
import com.subang.dao.RegionDao;
import com.subang.dao.ServiceDao;
import com.subang.dao.StatDao;
import com.subang.dao.TicketDao;
import com.subang.dao.TicketTypeDao;
import com.subang.dao.UserDao;
import com.subang.dao.WorkerDao;
import com.subang.domain.Admin;
import com.subang.domain.User;
import com.subang.domain.Worker;
import com.subang.service.ActivityService;
import com.subang.service.InfoService;
import com.subang.service.LaundryService;
import com.subang.service.OrderService;
import com.subang.service.PriceService;
import com.subang.service.RegionService;
import com.subang.service.RoleService;
import com.subang.service.StatService;
import com.subang.service.UserService;
import com.subang.service.WorkerService;

public class BaseController {

	protected static final Logger LOG = Logger.getLogger(BaseController.class.getName());

	@Autowired
	protected AddrDao addrDao;
	@Autowired
	protected AdminDao adminDao;
	@Autowired
	protected BalanceDao balanceDao;
	@Autowired
	protected BannerDao bannerDao;
	@Autowired
	protected CategoryDao categoryDao;
	@Autowired
	protected CityDao cityDao;
	@Autowired
	protected ClothesDao clothesDao;
	@Autowired
	protected ClothesTypeDao clothesTypeDao;
	@Autowired
	protected DistrictDao districtDao;
	@Autowired
	protected FaqDao faqDao;
	@Autowired
	protected FeedbackDao feedbackDao;
	@Autowired
	protected HistoryDao historyDao;
	@Autowired
	protected InfoDao infoDao;
	@Autowired
	protected LaundryDao laundryDao;
	@Autowired
	protected LocationDao locationDao;
	@Autowired
	protected NoticeDao noticeDao;
	@Autowired
	protected OrderDao orderDao;
	@Autowired
	protected PaymentDao paymentDao;
	@Autowired
	protected PriceDao priceDao;
	@Autowired
	protected RegionDao regionDao;
	@Autowired
	protected ServiceDao serviceDao;
	@Autowired
	protected StatDao statDao;
	@Autowired
	protected TicketDao ticketDao;
	@Autowired
	protected TicketTypeDao ticketTypeDao;
	@Autowired
	protected UserDao userDao;
	@Autowired
	protected WorkerDao workerDao;

	@Autowired
	protected ActivityService activityService;
	@Autowired
	protected InfoService infoService;
	@Autowired
	protected LaundryService laundryService;
	@Autowired
	protected OrderService orderService;
	@Autowired
	protected PriceService priceService;
	@Autowired
	protected RegionService regionService;
	@Autowired
	protected RoleService roleService;
	@Autowired
	protected StatService statService;
	@Autowired
	protected UserService userService;
	@Autowired
	protected WorkerService workerService;

	protected static final String KEY_ADMIN = "admin";
	protected static final String KEY_USER = "user";
	protected static final String KEY_OPENID = "openid";

	protected static final String KEY_DES_MSG = "desMsg"; // 显示下一级列表的描述信息，比如：订单历史，用户地址等
	protected static final String KEY_INFO_MSG = "infoMsg"; // 直接显示此类消息
	protected static final String KEY_ERR_MSG = "errMsg"; // 弹出显示此类消息，很少用到此种类型
	protected static final String KEY_PAGE_STATE = "pageState";

	protected PageState getPageState(HttpSession session) {
		return (PageState) session.getAttribute(KEY_PAGE_STATE);
	}

	protected void savePageState(HttpSession session, Object contentArg) {
		PageState pageState = getPageState(session);
		if (pageState == null) {
			pageState = new PageState();
			session.setAttribute(KEY_PAGE_STATE, pageState);
		}
		pageState.setContentArg(contentArg);
	}

	protected void invalidtePageState(HttpSession session) {
		session.removeAttribute(KEY_PAGE_STATE);
	}

	protected Admin getAdmin(HttpSession session) {
		Integer adminid = (Integer) session.getAttribute(KEY_ADMIN);
		return adminDao.get(adminid);
	}

	protected void setAdmin(HttpSession session, Admin admin) {
		session.setAttribute(KEY_ADMIN, admin.getId());
	}

	protected User getUser(HttpSession session) {
		Integer userid = (Integer) session.getAttribute(KEY_USER);
		return userDao.get(userid);
	}

	protected void setUser(HttpSession session, User user) {
		session.setAttribute(KEY_USER, user.getId());
	}

	protected String getOpenid(HttpSession session) {
		return (String) session.getAttribute(KEY_OPENID);
	}

	protected void setOpenid(HttpSession session, String openid) {
		session.setAttribute(KEY_OPENID, openid);
	}

	protected User getUser(Identity identity) {
		return userDao.getByCellnum(identity.getCellnum());
	}

	protected Worker getWorker(Identity identity) {
		return workerDao.getByCellnum(identity.getCellnum());
	}
}
