package com.subang.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.subang.bean.OrderDetail;
import com.subang.bean.Pagination;
import com.subang.bean.SearchArg;
import com.subang.util.ComUtil;
import com.subang.util.UtilTest;
import com.subang.util.WebConst;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext.xml" })
public class DaoTest extends BaseService {

	private static final Logger LOG = Logger.getLogger(UtilTest.class.getName());

	@Test
	public void test() {
		ComUtil.init();
		Pagination pagination = new Pagination();
		pagination.setType(WebConst.CUR);
		pagination.setPageno(1);
		SearchArg searchArg = new SearchArg();
		searchArg.pre();
		List<OrderDetail> orderDetails = orderDao.findDetail(searchArg, pagination);
		pause();
	}

	public void pause() {

	}
}
