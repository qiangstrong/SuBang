package com.subang.dao;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.subang.domain.Balance;
import com.subang.domain.Balance.BalanceType;
import com.subang.domain.Order;

@Repository
public class BalanceDao extends BaseDao<Balance> {
	public Balance get(Integer id) {
		String sql = "select * from balance_t where id=?";
		Object[] args = { id };
		Balance balance = null;
		try {
			balance = jdbcTemplate.queryForObject(sql, args, new BeanPropertyRowMapper<Balance>(
					Balance.class));
		} catch (EmptyResultDataAccessException e) {
		}
		return balance;
	}

	public Balance getByOrderno(String orderno) {
		String sql = "select * from balance_t where orderno=?";
		Object[] args = { orderno };
		Balance balance = null;
		try {
			balance = jdbcTemplate.queryForObject(sql, args, new BeanPropertyRowMapper<Balance>(
					Balance.class));
		} catch (EmptyResultDataAccessException e) {
		}
		return balance;
	}

	public void save(Balance balance) {
		String sql = "insert into balance_t values(null,?,?,?,?,?,?)";
		Object[] args = { balance.getType(), balance.getOrderno(), balance.getState(),
				balance.getMoney(), balance.getTime(), balance.getUserid() };
		jdbcTemplate.update(sql, args);
	}

	public void update(Balance balance) {
		String sql = "update balance_t set type=?, orderno=?, state=?, money=?, time=?, userid=? where id=?";
		Object[] args = { balance.getType(), balance.getOrderno(), balance.getState(),
				balance.getMoney(), balance.getTime(), balance.getUserid(), balance.getId() };
		jdbcTemplate.update(sql, args);
	}

	public void delete(Integer id) {
		String sql = "delete from balance_t where id=?";
		Object[] args = { id };
		jdbcTemplate.update(sql, args);
	}

	public List<Balance> findAll() {
		String sql = "select * from balance_t";
		List<Balance> balances = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Balance>(
				Balance.class));
		return balances;
	}

	public Balance getDetail(Integer balanceid) {
		String sql = "select balance_t.*, payment_t.type `pay_type` from balance_t, payment_t"
				+ " where balance_t.orderno=payment_t.orderno and balance_t.id=?";
		Object[] args = { balanceid };
		Balance balance = null;
		try {
			balance = jdbcTemplate.queryForObject(sql, args, new BeanPropertyRowMapper<Balance>(
					Balance.class));
		} catch (EmptyResultDataAccessException e) {
		}
		return balance;
	}

	public List<Balance> findBalanceByUseridAndState(Integer userid, Order.State state) {
		String sql = "select balance_t.*, payment_t.type `pay_type` from balance_t, payment_t"
				+ " where balance_t.orderno=payment_t.orderno and balance_t.type=? and balance_t.userid=? and balance_t.state=? order by time asc";
		Object[] args = { BalanceType.balance.ordinal(), userid, state.ordinal() };
		List<Balance> balances = jdbcTemplate.query(sql, args, new BeanPropertyRowMapper<Balance>(
				Balance.class));
		return balances;
	}

	public List<Balance> findSalaryByUseridAndState(Integer userid, Order.State state) {
		String sql = "select balance_t.*, payment_t.type `pay_type` from balance_t, payment_t"
				+ " where balance_t.orderno=payment_t.orderno and balance_t.type=? and balance_t.userid=? and balance_t.state=? order by time asc";
		Object[] args = { BalanceType.salary.ordinal(), userid, state.ordinal() };
		List<Balance> balances = jdbcTemplate.query(sql, args, new BeanPropertyRowMapper<Balance>(
				Balance.class));
		return balances;
	}
}
