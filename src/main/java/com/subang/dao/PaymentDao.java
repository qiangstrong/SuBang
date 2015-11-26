package com.subang.dao;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.subang.domain.Payment;

@Repository
public class PaymentDao extends BaseDao<Payment> {
	public Payment get(Integer id) {
		String sql = "select * from payment_t where id=?";
		Object[] args = { id };
		Payment payment = null;
		try {
			payment = jdbcTemplate.queryForObject(sql, args, new BeanPropertyRowMapper<Payment>(
					Payment.class));
		} catch (EmptyResultDataAccessException e) {
		}
		return payment;
	}

	public Payment getByOrderno(String orderno) {
		String sql = "select * from payment_t where orderno=?";
		Object[] args = { orderno };
		Payment payment = null;
		try {
			payment = jdbcTemplate.queryForObject(sql, args, new BeanPropertyRowMapper<Payment>(
					Payment.class));
		} catch (EmptyResultDataAccessException e) {
		}
		return payment;
	}

	public void save(Payment payment) {
		String sql = "insert into payment_t values(null,?,?,?,?,?)";
		Object[] args = { payment.getType(), payment.getMoneyTicket(), payment.getPrepay_id(),
				payment.getTime(), payment.getOrderno() };
		jdbcTemplate.update(sql, args);
	}

	public void update(Payment payment) {
		String sql = "update payment_t set type=?,money_ticket=?,prepay_id=?,time=?,orderno=? where id=?";
		Object[] args = { payment.getType(), payment.getMoneyTicket(), payment.getPrepay_id(),
				payment.getTime(), payment.getOrderno(), payment.getId() };
		jdbcTemplate.update(sql, args);
	}

	public void delete(Integer id) {
		String sql = "delete from payment_t where id=?";
		Object[] args = { id };
		jdbcTemplate.update(sql, args);
	}

	public List<Payment> findAll() {
		String sql = "select * from payment_t";
		List<Payment> payments = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Payment>(
				Payment.class));
		return payments;
	}
}
