package com.subang.util;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import weixin.popular.util.JsonUtil;

import com.subang.util.TimeUtil.Option;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext.xml" })
public class UtilTest {

	private static final Logger LOG = Logger.getLogger("sdfas");

	@Test
	public void test() throws Exception {
		Option option = new Option(null, "Qing");
		String json = JsonUtil.toJSONString(option);

		pause();
	}

	public void pause() {
	}
}
