package com.subang.controller;

import java.sql.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.subang.domain.Order;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext.xml" })
public class ServiceTest extends BaseController {

	@Test
	public void test() {
		Order order = new Order();
		order.setUserid(21);
		order.setAddrid(1);
		order.setCategoryid(1);
		order.setDate(new Date(System.currentTimeMillis()));
		order.setTime(5);
		for (int i = 0; i < 10; i++) {
			orderService.addOrder(order);
		}
		pause();
	}

	public void pause() {

	}
}
