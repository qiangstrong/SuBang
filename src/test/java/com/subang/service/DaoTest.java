package com.subang.service;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.subang.domain.City;
import com.subang.util.UtilTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext.xml" })
public class DaoTest extends BaseService {

	private static final Logger LOG = Logger.getLogger(UtilTest.class.getName());

	@Test
	public void test() {
		City city = cityDao.get(null);
		pause();
	}

	public void test1() {
		System.out.println("qiang");
		pause();
	}

	public void pause() {

	}
}
