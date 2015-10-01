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
		Info info = null;
		try {
			info = jdbcTemplate.queryForObject(sql, args, new BeanPropertyRowMapper<Info>(
					Info.class));
		} catch (EmptyResultDataAccessException e) {
		}
		return info;
	}

	public void update(Info info) {
		String sql = "update info_t set phone=? where id=?";
		Object[] args = { info.getPhone(), info.getId() };
		jdbcTemplate.update(sql, args);
	}

	public List<Info> findALL() {
		String sql = "select * from info_t";
		List<Info> infos = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Info>(Info.class));
		return infos;
	}
}
