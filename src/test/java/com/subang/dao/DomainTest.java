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
		String sql = "update user_t set nickname=? where id=?";
		byte[] b=new byte[]{(byte) 0xf0,(byte) 0x9f,(byte) 0x98,(byte) 0x82};
		String nickname=new String(b);
		Integer id=1;
		Object[] args={nickname,id};
		jdbcTemplate.update(sql,args);
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
