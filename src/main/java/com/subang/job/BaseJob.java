package com.subang.job;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.subang.dao.AddrDao;
import com.subang.dao.AdminDao;
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

public abstract class BaseJob extends QuartzJobBean {
	protected static final Logger LOG = Logger.getLogger(BaseJob.class.getName());

	@Autowired
	protected AddrDao addrDao;
	@Autowired
	protected AdminDao adminDao;
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
}
