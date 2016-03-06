package com.subang.dao;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.subang.domain.Cost;

@Repository
public class CostDao extends BaseDao<Cost> {
	public Cost get(Integer id) {
		String sql = "select * from cost_t where id=?";
		Object[] args = { id };
		Cost cost = null;
		try {
			cost = jdbcTemplate.queryForObject(sql, args, new BeanPropertyRowMapper<Cost>(
					Cost.class));
		} catch (EmptyResultDataAccessException e) {
		}
		return cost;
	}

	public void save(Cost cost) {
		String sql = "insert into cost_t values(null,?,?,?)";
		Object[] args = { cost.getMoney(), cost.getLaundryid(), cost.getArticleid() };
		jdbcTemplate.update(sql, args);
	}

	public void update(Cost cost) {
		String sql = "update cost_t set money=?, laundryid=?, articleid=? where id=?";
		Object[] args = { cost.getMoney(), cost.getLaundryid(), cost.getArticleid(), cost.getId() };
		jdbcTemplate.update(sql, args);
	}

	public void delete(Integer id) {
		String sql = "delete from cost_t where id=?";
		Object[] args = { id };
		jdbcTemplate.update(sql, args);
	}

	public List<Cost> findAll() {
		String sql = "select * from cost_t";
		List<Cost> costs = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Cost>(Cost.class));
		return costs;
	}

	public Cost getDetail(Integer costid) {
		String sql = "select cost_t.* ,article_t.name `articlename` from cost_t, article_t "
				+ "where article_t.id=cost_t.articleid and cost_t.id=?";
		Object[] args = { costid };
		Cost cost = null;
		try {
			cost = jdbcTemplate.queryForObject(sql, args, new BeanPropertyRowMapper<Cost>(
					Cost.class));
		} catch (EmptyResultDataAccessException e) {
		}
		return cost;
	}

	public List<Cost> findDetailByLaundryid(Integer laundryid) {
		String sql = "select cost_t.* ,article_t.name `articlename` from cost_t, article_t "
				+ "where article_t.id=cost_t.articleid and cost_t.laundryid=?";
		Object[] args = { laundryid };
		List<Cost> costs = jdbcTemplate.query(sql, args,
				new BeanPropertyRowMapper<Cost>(Cost.class));
		return costs;
	}
}
