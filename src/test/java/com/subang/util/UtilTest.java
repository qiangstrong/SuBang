package com.subang.util;

import java.io.IOException;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.baidu.map.api.GeocodingAPI;
import com.baidu.map.bean.LatLng;
import com.baidu.map.bean.RenderReverseResult;
import com.baidu.map.util.CoordType;

import weixin.popular.api.SnsAPI;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext.xml" })
public class UtilTest {
	
	private static final Logger LOG = Logger.getLogger ( "sdfas");
	
	@Test
	public void test() throws Exception {

		LOG.error("wer");
		pause();
	}

	public void pause() {
	}
}
