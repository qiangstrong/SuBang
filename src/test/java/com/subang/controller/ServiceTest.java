package com.subang.controller;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import weixin.popular.util.JsonUtil;

import com.subang.bean.AddrDetail;
import com.subang.domain.District;
import com.subang.domain.Region;
import com.subang.exception.BackException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext.xml" })
public class ServiceTest extends BaseController {

	@Test
	public void test() {
		List<AddrDetail> addrDetails=frontUserService.listAddrDetailByUserid(9);

		pause();
	}

	public void pause() {

	}
}
