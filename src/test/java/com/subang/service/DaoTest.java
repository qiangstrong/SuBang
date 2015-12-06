package com.subang.service;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.subang.domain.Balance;
import com.subang.domain.Order;
import com.subang.domain.Order.OrderType;
import com.subang.util.StratUtil;
import com.subang.util.UtilTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext.xml" })
public class DaoTest extends BaseService {

	private static final Logger LOG = Logger.getLogger(UtilTest.class.getName());

	@Test
	public void test() {
		Balance balance = new Balance();
		balance.setMoney(1.0);
		balance.setUserid(1);
		balance.setState(Order.State.accepted);
		balance.setOrderno(StratUtil.getOrderno(OrderType.balance));
		balanceDao.save(balance);
		pause();
	}

	public void pause() {

	}
}
