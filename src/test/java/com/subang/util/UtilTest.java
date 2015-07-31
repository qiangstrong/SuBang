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
	
	private static final Logger LOG = Logger.getLogger ( UtilTest.class.getName());
	
	@Test
	public void test() throws Exception {
		RenderReverseResult result=GeocodingAPI.
				renderReverse("mHcUBs0GiAfAhArXPy7H9YZf", CoordType.wgs84ll, new LatLng("41.765789", "123.413399"));
		System.out.println(result.getResult().getFormatted_address());

		pause();
	}

	public void pause() {
	}
}
