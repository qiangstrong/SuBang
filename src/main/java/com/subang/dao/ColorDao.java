package com.subang.dao;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.subang.domain.Color;

@Repository
public class ColorDao extends BaseDao<Color> {
	public Color get(Integer id) {
		String sql = "select * from color_t where id=?";
		Object[] args = { id };
		Color color = null;
		try {
			color = jdbcTemplate.queryForObject(sql, args, new BeanPropertyRowMapper<Color>(
					Color.class));
		} catch (EmptyResultDataAccessException e) {
		}
		return color;
	}

	public void save(Color color) {
		String sql = "insert into color_t values(null,?)";
		Object[] args = { color.getName() };
		jdbcTemplate.update(sql, args);
	}

	public void update(Color color) {
		String sql = "update color_t set name=? where id=?";
		Object[] args = { color.getName(), color.getId() };
		jdbcTemplate.update(sql, args);
	}

	public void delete(Integer id) {
		String sql = "delete from color_t where id=?";
		Object[] args = { id };
		jdbcTemplate.update(sql, args);
	}

	public List<Color> findAll() {
		String sql = "select * from color_t";
		List<Color> colors = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Color>(Color.class));
		return colors;
	}

}
