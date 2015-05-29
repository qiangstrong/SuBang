package com.subang.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.subang.bean.StatArg;
import com.subang.bean.StatItem;
import com.subang.util.Common;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext.xml" })
public class DaoTest extends BaseService{
	
	@Test
	public void test() {
		StatArg statArg=new StatArg(1,1,1);
		List<StatItem> statItems =statDao.findByStatArg(statArg);
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
