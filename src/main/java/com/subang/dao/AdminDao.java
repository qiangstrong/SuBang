package com.subang.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.subang.domain.Admin;

@Repository
public class AdminDao extends BaseDao<Admin> {

	public int getMatchCount(String username, String password) {
		String sql = "select count(*) from admin_t where username=? and password=?";
		Object[] args = new Object[] { username, password };
		int count = jdbcTemplate.queryForInt(sql, args);
		return count;
	}

	public Admin findByUsername(String username) {
		String sql = "select * from admin_t where username=?";
		Object[] args = { username };
		Admin admin = null;
		try {
			admin=jdbcTemplate.queryForObject(sql, args, new BeanPropertyRowMapper<Admin>(Admin.class));
		} catch (EmptyResultDataAccessException e) {			
		}
		return admin;
	}

	public void update(Admin admin) {
		String sql = "update admin_t set username=? ,password=? where id=?";
		Object[] args = { admin.getUsename(), admin.getPassword(), admin.getId() };
		jdbcTemplate.update(sql, args);
	}
}
