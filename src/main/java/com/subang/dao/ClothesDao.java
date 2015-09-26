package com.subang.dao;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.subang.domain.Clothes;

@Repository
public class ClothesDao extends BaseDao<Clothes> {
	public Clothes get(Integer id) {
		String sql = "select * from clothes_t where id=?";
		Object[] args = { id };
		Clothes clothes = null;
		try {
			clothes = jdbcTemplate.queryForObject(sql, args, new BeanPropertyRowMapper<Clothes>(
					Clothes.class));
		} catch (EmptyResultDataAccessException e) {
		}
		return clothes;
	}

	public void save(Clothes clothes) {
		String sql = "insert into clothes_t values(null,?,?,?,?)";
		Object[] args = { clothes.getName(), clothes.getColor(), clothes.getFlaw(),
				clothes.getOrderid() };
		jdbcTemplate.update(sql, args);
	}

	public void update(Clothes clothes) {
		String sql = "update clothes_t set name=?,color=?,flaw=?,orderid=? where id=?";
		Object[] args = { clothes.getName(), clothes.getColor(), clothes.getFlaw(),
				clothes.getOrderid(), clothes.getId() };
		jdbcTemplate.update(sql, args);
	}

	public void delete(Integer id) {
		String sql = "delete from clothes_t where id=?";
		Object[] args = { id };
		jdbcTemplate.update(sql, args);
	}

	public List<Clothes> findAll() {
		String sql = "select * from clothes_t";
		List<Clothes> clothess = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Clothes>(
				Clothes.class));
		return clothess;
	}

	public List<Clothes> findByOrderid(Integer orderid) {
		String sql = "select * from clothes_t where orderid=?";
		Object[] args = { orderid };
		List<Clothes> clothess = jdbcTemplate.query(sql, args, new BeanPropertyRowMapper<Clothes>(
				Clothes.class));
		return clothess;
	}
}
