package com.subang.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext.xml" })
public class ServiceTest extends BaseController {

	@Test
	public void test() {
		userService.payBalance("15120617983604");
		pause();
	}

	public void pause() {

	}
}
