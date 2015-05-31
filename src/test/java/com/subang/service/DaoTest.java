package com.subang.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.subang.bean.OrderDetail;
import com.subang.bean.StatArg;
import com.subang.bean.StatItem;
import com.subang.domain.History.Operation;
import com.subang.util.Common;
import com.subang.util.SMS;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext.xml" })
public class DaoTest extends BaseService {

	@Test
	public void test() {
		OrderDetail orderDetail = orderDao.getOrderDetail(1);
		String content = SMS.toUserContent("123456");
		pause();
	}

	public void test1() {
		System.out.println("qiang");
		pause();
	}

	public void pause() {

	}
}
