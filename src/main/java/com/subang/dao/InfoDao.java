package com.subang.dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;

import com.subang.domain.Info;

public class InfoDao extends BaseDao<Info> {

	public Info find() {
		String sql = "select * from info_t";
		Info info = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<Info>(Info.class));
		return info;
	}

	public void update(Info info) {
		String sql = "update info_t set price_path=?, price_text=?, scope_path=?, scope_text=?, about=?, term=?, phone=?"
				+ " where id=?";
		Object[] args = { info.getPrice_path(), info.getPrice_text(), info.getScope_path(),
				info.getScope_text(), info.getAbout(), info.getTerm(), info.getPhone(),
				info.getId() };
		jdbcTemplate.update(sql, args);
	}
}
