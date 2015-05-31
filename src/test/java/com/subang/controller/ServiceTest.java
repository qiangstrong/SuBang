package com.subang.controller;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.subang.exception.BackException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext.xml" })
public class ServiceTest extends BaseController {

	@Test
	public void test() {

		List<Integer> laundryids = new ArrayList<Integer>();
		laundryids.add(1);
		laundryids.add(2);
		try {
			backAdminService.deleteLaundrys(laundryids);
		} catch (BackException e) {
			e.printStackTrace();
		}

		pause();
	}

	public void pause() {

	}
}
