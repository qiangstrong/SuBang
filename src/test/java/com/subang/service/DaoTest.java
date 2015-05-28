package com.subang.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

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
