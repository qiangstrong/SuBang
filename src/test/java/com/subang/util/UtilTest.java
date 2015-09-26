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
import com.subang.domain.Notice.CodeType;
import com.sun.xml.internal.ws.policy.sourcemodel.ModelNode.Type;

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
		SuUtil.notice(CodeType.sms, "短信发送失败。");
		
		pause();
	}

	public void pause() {
	}
}
