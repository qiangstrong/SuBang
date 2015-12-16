package com.subang.dao;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.subang.bean.OrderDetail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext.xml" })
public class DomainTest {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Test
	public void test() {
		List<OrderDetail> orderDetails = findDetailByUserid("19");
		pause();
	}

	public List<OrderDetail> findDetailByUserid(String userid) {
		String sql = "select * from orderdetail_v where userid= ?";
		Object[] args = { userid };
		List<OrderDetail> orderDetails = jdbcTemplate.query(sql, args,
				new BeanPropertyRowMapper<OrderDetail>(OrderDetail.class));
		return orderDetails;
	}

	public void pause() {

	}
}
