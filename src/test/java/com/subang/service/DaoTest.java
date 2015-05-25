package com.subang.service;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.subang.bean.Area;
import com.subang.bean.OrderDetail;
import com.subang.bean.StatItem;
import com.subang.domain.Admin;
import com.subang.domain.City;
import com.subang.domain.District;
import com.subang.domain.Order.State;
import com.subang.domain.Worker;
import com.subang.util.Common;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext.xml" })
public class DaoTest extends BaseService{
	
	@Test
	public void test() {
		String str=Common.getProperty("1");
		pause();
	}

	
	public void test1()
	{
		System.out.println("qiang");
		pause();
	}
	
	public void pause()
	{
		
	}
}
