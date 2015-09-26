package com.subang.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.subang.dao.*;

public class BaseService {
	
	protected static final Logger LOG = Logger.getLogger ( BaseService.class.getName());
	
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

}
