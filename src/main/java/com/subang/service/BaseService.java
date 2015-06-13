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
	protected CityDao cityDao;
	@Autowired
	protected DistrictDao districtDao;
	@Autowired
	protected HistoryDao historyDao;
	@Autowired
	protected InfoDao infoDao;
	@Autowired
	protected LaundryDao laundryDao;
	@Autowired
	protected OrderDao orderDao;
	@Autowired
	protected RegionDao regionDao;
	@Autowired
	protected UserDao userDao;
	@Autowired
	protected WorkerDao workerDao;
	@Autowired
	protected StatDao statDao;
}
