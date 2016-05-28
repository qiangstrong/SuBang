package com.subang.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.subang.dao.AddrDao;
import com.subang.dao.AdminDao;
import com.subang.dao.ArticleDao;
import com.subang.dao.BalanceDao;
import com.subang.dao.BannerDao;
import com.subang.dao.CategoryDao;
import com.subang.dao.CityDao;
import com.subang.dao.ClothesDao;
import com.subang.dao.ClothesTypeDao;
import com.subang.dao.ColorDao;
import com.subang.dao.CostDao;
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
import com.subang.dao.RebateDao;
import com.subang.dao.RegionDao;
import com.subang.dao.ServiceDao;
import com.subang.dao.SnapshotDao;
import com.subang.dao.StatDao;
import com.subang.dao.TicketCodeDao;
import com.subang.dao.TicketDao;
import com.subang.dao.TicketTypeDao;
import com.subang.dao.UserDao;
import com.subang.dao.WorkerDao;

public class BaseService {

	protected static final Logger LOG = Logger.getLogger(BaseService.class.getName());

	@Autowired
	protected AddrDao addrDao;
	@Autowired
	protected AdminDao adminDao;
	@Autowired
	protected ArticleDao articleDao;
	@Autowired
	protected BalanceDao balanceDao;
	@Autowired
	protected BannerDao bannerDao;
	@Autowired
	protected RebateDao rebateDao;
	@Autowired
	protected CategoryDao categoryDao;
	@Autowired
	protected CityDao cityDao;
	@Autowired
	protected ClothesDao clothesDao;
	@Autowired
	protected ClothesTypeDao clothesTypeDao;
	@Autowired
	protected ColorDao colorDao;
	@Autowired
	protected CostDao costDao;
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
	protected SnapshotDao snapshotDao;
	@Autowired
	protected StatDao statDao;
	@Autowired
	protected TicketCodeDao ticketCodeDao;
	@Autowired
	protected TicketDao ticketDao;
	@Autowired
	protected TicketTypeDao ticketTypeDao;
	@Autowired
	protected UserDao userDao;
	@Autowired
	protected WorkerDao workerDao;

}
