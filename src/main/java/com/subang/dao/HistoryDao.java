package com.subang.dao;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.subang.domain.History;
import com.subang.domain.Order.State;

@Repository
public class HistoryDao extends BaseDao<History> {

	public History get(Integer id) {
		String sql = "select * from history_t where id=?";
		Object[] args = { id };
		History history = null;
		try {
			history = jdbcTemplate.queryForObject(sql, args, new BeanPropertyRowMapper<History>(
					History.class));
		} catch (EmptyResultDataAccessException e) {
		}
		return history;
	}

	public void save(History history) {
		String sql = "insert into history_t values(null,?,now(),?)";
		Object[] args = { history.getOperation(), history.getOrderid() };
		jdbcTemplate.update(sql, args);
	}

	public void update(History history) {
		String sql = "update history_t set operation=? ,time=? ,orderid=? where id=?";
		Object[] args = { history.getOperation(), history.getTime(), history.getOrderid(),
				history.getId() };
		jdbcTemplate.update(sql, args);
	}

	public void updateTime(Integer orderid, State state) {
		String sql = "update history_t set time=now() where orderid=? and operation=?";
		Object[] args = { orderid, state.ordinal() };
		jdbcTemplate.update(sql, args);
	}

	public void delete(Integer id) {
		String sql = "delete from history_t where id=?";
		Object[] args = { id };
		jdbcTemplate.update(sql, args);
	}

	public List<History> findAll() {
		String sql = "select * from history_t";
		List<History> historys = jdbcTemplate.query(sql, new BeanPropertyRowMapper<History>(
				History.class));
		return historys;
	}

	public List<History> findByOrderid(Integer orderid) {
		String sql = "select * from history_t where orderid=? order by time asc";
		Object[] args = { orderid };
		List<History> historys = jdbcTemplate.query(sql, args, new BeanPropertyRowMapper<History>(
				History.class));
		return historys;
	}

}
