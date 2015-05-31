package com.subang.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext.xml" })
public class UtilTest {
	
	@Test
	public void test() {
		String orderno=Common.getOrderno();
		pause();
	}
	
	public void pause()
	{		
	}
}
