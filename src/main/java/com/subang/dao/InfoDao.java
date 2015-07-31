package com.subang.dao;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.subang.domain.Info;

@Repository
public class InfoDao extends BaseDao<Info> {

	public Info get(Integer id) {
		String sql = "select * from info_t where id=?";
		Object[] args = { id };
		Info info=null;
		try {
			info = jdbcTemplate.queryForObject(sql, args, new BeanPropertyRowMapper<Info>(
					Info.class));
		} catch (EmptyResultDataAccessException e) {
		}
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

	public List<Info> findALL() {
		String sql = "select * from info_t";
		List<Info> infos = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Info>(Info.class));
		return infos;
	}
}
