package com.subang.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.subang.bean.OrderDetail;
import com.subang.bean.StatArg;
import com.subang.bean.StatItem;
import com.subang.domain.District;
import com.subang.domain.History.Operation;
import com.subang.domain.User;
import com.subang.util.Common;
import com.subang.util.SmsUtil;
import com.subang.util.UtilTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext.xml" })
public class DaoTest extends BaseService {

	private static final Logger LOG = Logger.getLogger ( UtilTest.class.getName());
	
	@Test
	public void test() {
		User user =userDao.get(8);
		userDao.update(user);
		pause();
	}

	public void test1() {
		System.out.println("qiang");
		pause();
	}

	public void pause() {

	}
}
