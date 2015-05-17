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
import com.subang.domain.District;
import com.subang.domain.Order.State;
import com.subang.domain.Worker;
import com.subang.utility.Common;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext.xml" })
public class DaoTest extends BaseService{

	@Test
	public void test() {
		List<StatItem> statItems=userDao.statUserNumByCity();
		pause();
	}

	
	public void test1()
	{
		String str=Common.getLikeStr("");
		pause();
	}
	
	public void pause()
	{
		
	}
}
