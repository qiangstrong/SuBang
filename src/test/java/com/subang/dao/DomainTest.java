package com.subang.dao;

import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.subang.bean.StatItem;
import com.subang.domain.*;
import com.subang.domain.User.Sex;
import com.subang.util.Common;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext.xml" })
public class DomainTest {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Test
	public void test() {
		String sql = "call subang.statOrderNumByRegion()";
		List<StatItem> statItems = jdbcTemplate.query(sql, new BeanPropertyRowMapper<StatItem>(
				StatItem.class));
		pause();
	}
		
	public void pause() {

	}
}
