package com.subang.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.subang.domain.Admin;

@Repository
public class AdminDao extends BaseDao<Admin> {

	public Admin findMatch(Admin admin) {
		String sql = "select * from admin_t where username=? and password=?";
		Object[] args = { admin.getUsename(), admin.getPassword() };
		admin = null;
		try {
			admin = jdbcTemplate.queryForObject(sql, args, new BeanPropertyRowMapper<Admin>(
					Admin.class));
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
