package com.subang.util;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import weixin.popular.api.SnsAPI;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext.xml" })
public class UtilTest {
	
	private static final Logger LOG = Logger.getLogger ( UtilTest.class.getName());
	@Test
	public void test() {
		String url=SnsAPI.connectOauth2Authorize(WeixinConst.APPID, "http://202.118.18.56?type=1", false, null);
		pause();
	}

	public void pause() {
	}
}
