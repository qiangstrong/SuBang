package com.subang.dao;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.subang.utility.WebConstant;

import com.subang.domain.Order;

@Repository
public class OrderDao extends BaseDao<Order> {

	public Order get(Integer id) {
		String sql = "select * from order_t where id=?";
		Object[] args = { id };
		Order order = jdbcTemplate.queryForObject(sql, args, new BeanPropertyRowMapper<Order>(
				Order.class));
		return order;
	}

	public void save(Order order) {
		String sql = "insert into order_t values(null,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] args = { order.getOrderno(), order.getCategory(), order.getState(),
				order.getPrice(), order.getDate(), order.getTime(), order.getComment(),
				order.getUserid(), order.getAddrid(), order.getWorkerid(), order.getLaundryid() };
		jdbcTemplate.update(sql, args);
	}

	public void update(Order order) {
		String sql = "update order_t set orderno=? ,category=? ,state=? ,price=? ,date=? ,time=? ,"
				+ "comment=? ,userid=? ,addrid=? ,workerid=? ,laundryid=? where id=?";
		Object[] args = { order.getOrderno(), order.getCategory(), order.getState(),
				order.getPrice(), order.getDate(), order.getTime(), order.getComment(),
				order.getUserid(), order.getAddrid(), order.getWorkerid(), order.getLaundryid(),
				order.getId() };
		jdbcTemplate.update(sql, args);
	}

	public void delete(Integer id) {
		String sql = "delete from order_t where id=?";
		Object[] args = { id };
		jdbcTemplate.update(sql, args);
	}

	public List<Order> findAll() {
		String sql = "select * from order_t";
		List<Order> orders = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Order>(Order.class));
		return orders;
	}

	public List<Order> findAll(int pageno) {
		int offset = (pageno - 1) * WebConstant.PAGE_SIZE;
		String sql = "select * from order_t";
		List<Order> orders = findByPage(sql, new Object[] {}, offset, WebConstant.PAGE_SIZE);
		return orders;
	}

	public Order findByOrderno(String orderno) {
		String sql = "select * from order_t where orderno=?";
		Object[] args = { orderno };
		Order order = null;
		try {
			jdbcTemplate.queryForObject(sql, args, new BeanPropertyRowMapper<Order>(Order.class));
		} catch (EmptyResultDataAccessException e) {
		}
		return order;
	}

	public List<Order> findByUserid(String userid) {
		String sql = "select * from order_t where userid=?";
		Object[] args = { userid };
		List<Order> orders = jdbcTemplate.query(sql, args, new BeanPropertyRowMapper<Order>(
				Order.class));
		return orders;
	}
}
