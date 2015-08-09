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
import weixin.popular.api.TicketAPI;
import weixin.popular.api.TokenAPI;
import weixin.popular.bean.Ticket;
import weixin.popular.bean.Token;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext.xml" })
public class UtilTest {
	
	private static final Logger LOG = Logger.getLogger ( "sdfas");
	
	@Test
	public void test() throws Exception {
		Token token = TokenAPI.token("wx0c69fe4ab533a3e5","3525d67ccc785a0985569c13cbdeb414");
		Ticket ticket = TicketAPI.ticketGetticket(token.getAccess_token());
		
		pause();
	}

	public void pause() {
	}
}
