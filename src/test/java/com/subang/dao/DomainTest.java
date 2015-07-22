package com.subang.dao;

import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
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
		String username="";
		String password="\" or \"1";
		String sql = "select * from admin_t where username=? and password=?";
		Object[] args = { username,password };
		List<Admin> admins=null;
		try {
			admins= jdbcTemplate.query(sql, args, new BeanPropertyRowMapper<Admin>(
					Admin.class));
		} catch (Exception e) {
			pause();
		}
		pause();
	}
	
	public void test1(){
/*
		String str=
		System.out.println(str);*/
	}
	
	public void pause() {

	}
}
