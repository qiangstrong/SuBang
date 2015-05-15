package com.subang.dao;

import static org.junit.Assert.*;

import java.sql.Timestamp;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.subang.domain.Addr;
import com.subang.domain.History;
import com.subang.domain.Order;
import com.subang.domain.User;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext.xml" })
public class AddrDaoTest {

	@Autowired
	private AddrDao addrDao;
	
	@Test
	public void test() {

		addrDao.get(4);
		pause();
	}

	public void pause(){
		
	}
	
}
