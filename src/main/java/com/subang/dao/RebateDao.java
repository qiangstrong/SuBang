package com.subang.dao;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.subang.domain.Rebate;

@Repository
public class RebateDao extends BaseDao<Rebate> {
	public Rebate get(Integer id) {
		String sql = "select * from rebate_t where id=?";
		Object[] args = { id };
		Rebate rebate = null;
		try {
			rebate = jdbcTemplate.queryForObject(sql, args, new BeanPropertyRowMapper<Rebate>(
					Rebate.class));
		} catch (EmptyResultDataAccessException e) {
		}
		return rebate;
	}

	public void save(Rebate rebate) {
		String sql = "insert into rebate_t values(null,?,?)";
		Object[] args = { rebate.getMoney(), rebate.getBenefit() };
		jdbcTemplate.update(sql, args);
	}

	public void update(Rebate rebate) {
		String sql = "update rebate_t set money=?, benefit=? where id=?";
		Object[] args = { rebate.getMoney(), rebate.getBenefit(), rebate.getId() };
		jdbcTemplate.update(sql, args);
	}

	public void delete(Integer id) {
		String sql = "delete from rebate_t where id=?";
		Object[] args = { id };
		jdbcTemplate.update(sql, args);
	}

	public List<Rebate> findAll() {
		String sql = "select * from rebate_t";
		List<Rebate> rebates = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Rebate>(
				Rebate.class));
		return rebates;
	}
}
