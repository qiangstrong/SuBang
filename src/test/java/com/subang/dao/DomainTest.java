package com.subang.dao;

import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.subang.domain.*;
import com.subang.domain.User.Sex;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext.xml" })
public class DomainTest {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	
	public void testCity1() {
		City city = new City();
		city.setName("沈阳");
		String sqlStr = "insert into city_t(name) values(?)";
		Object[] args = { city.getName() };
		jdbcTemplate.update(sqlStr, args);
	}
	
	
	public void testCity2() {
		final City city = new City();
		String name = "沈阳";
		String sqlStr = "select * from city_t where name=?";
		jdbcTemplate.query(sqlStr, new Object[] { name }, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				city.setId(rs.getInt("id"));
				city.setName(rs.getString("name"));
			}
		});
		System.out.println(city.getId());
	}
	
	
	public void testAddr1()
	{
		Addr addr=new Addr(null,false,"小明","15500000005","沈阳",1,1);
		String sqlStr="insert into addr_t values(null,?,?,?,?,?,?)";
		Object[] args = {addr.isValid(),addr.getName(),addr.getCellnum(),addr.getDetail(),addr.getUserid(),addr.getRegionid() };
		jdbcTemplate.update(sqlStr, args);
	}
	
	
	public void testAddr2()
	{
		final Addr addr=new Addr();
		addr.setId(1);
		String sqlStr = "select * from addr_t where id=?";
		jdbcTemplate.query(sqlStr, new Object[] { addr.getId() }, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				addr.setValid(rs.getBoolean("valid"));
			}
		});
		System.out.println(addr.isValid());
	}
	
	@Test
	public void testUser2()
	{
		final User user=new User();
		user.setId(1);
		String sqlStr = "select * from user_t where id=?";
		jdbcTemplate.query(sqlStr, new Object[] { user.getId() }, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				user.setSex(rs.getInt("sex"));
				user.setCountry(rs.getString("country"));
			}
		});
		System.out.println(user.getCountry());
	}
}
