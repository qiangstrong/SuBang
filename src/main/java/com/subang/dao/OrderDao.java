package com.subang.dao;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.subang.bean.OrderDetail;
import com.subang.bean.StatItem;
import com.subang.domain.Order;
import com.subang.domain.Order.State;
import com.subang.util.Common;
import com.subang.util.WebConst;

@Repository
public class OrderDao extends BaseDao<Order> {

	public Order get(Integer id) {
		String sql = "select * from order_t where id=?";
		Object[] args = { id };
		Order order = jdbcTemplate.queryForObject(sql, args, new BeanPropertyRowMapper<Order>(
				Order.class));
		return order;
	}
	
	public Order getByOrderno(String orderno) {
		String sql = "select * from order_t where orderno=?";
		Object[] args = { orderno };
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
		int offset = (pageno - 1) * WebConst.PAGE_SIZE;
		String sql = "select * from order_t";
		List<Order> orders = findByPage(sql, new Object[] {}, offset, WebConst.PAGE_SIZE);
		return orders;
	}

	public List<Order> findByOrderno(String orderno) {
		String sql = "select * from order_t where orderno like ?";
		Object[] args = { Common.getLikeStr(orderno) };
		List<Order> orders = jdbcTemplate.query(sql, args, new BeanPropertyRowMapper<Order>(
				Order.class));
		return orders;
	}

	public List<Order> findByState(State state) {
		String sql = "select * from order_t where state=?";
		Object[] args = { state.ordinal() };
		List<Order> orders = jdbcTemplate.query(sql, args, new BeanPropertyRowMapper<Order>(
				Order.class));
		return orders;
	}

	public List<Order> findByUserid(Integer userid) {
		String sql = "select * from order_t where userid=?";
		Object[] args = { userid };
		List<Order> orders = jdbcTemplate.query(sql, args, new BeanPropertyRowMapper<Order>(
				Order.class));
		return orders;
	}

	public int findNumByAddrid(Integer addrid) {
		String sql = "select count(*) from order_t where addrid=?";
		Object[] args = { addrid };
		int num = jdbcTemplate.queryForInt(sql, args);
		return num;
	}

	public List<Order> findByWorkerid(Integer workerid) {
		String sql = "select * from order_t where workerid=?";
		Object[] args = { workerid };
		List<Order> orders = jdbcTemplate.query(sql, args, new BeanPropertyRowMapper<Order>(
				Order.class));
		return orders;
	}

	public List<Order> findByLaundryid(Integer laundryid) {
		String sql = "select * from order_t where laundryid=?";
		Object[] args = { laundryid };
		List<Order> orders = jdbcTemplate.query(sql, args, new BeanPropertyRowMapper<Order>(
				Order.class));
		return orders;
	}

	public OrderDetail getOrderDetail(Integer id) {
		String sql = "select * from orderdetail_v where id=?";
		Object[] args = { id };
		OrderDetail orderDetail = jdbcTemplate.queryForObject(sql, args,
				new BeanPropertyRowMapper<OrderDetail>(OrderDetail.class));
		return orderDetail;
	}

	public List<OrderDetail> findOrderDetailAll() {
		String sql = "select * from orderdetail_v";
		List<OrderDetail> orderDetails = jdbcTemplate.query(sql,
				new BeanPropertyRowMapper<OrderDetail>(OrderDetail.class));
		return orderDetails;
	}

	public List<OrderDetail> findOrderDetailByState(State state) {
		String sql = "select * from orderdetail_v where state=?";
		Object[] args = { state.ordinal() };
		List<OrderDetail> orderDetails = jdbcTemplate.query(sql, args,
				new BeanPropertyRowMapper<OrderDetail>(OrderDetail.class));
		return orderDetails;
	}

	public List<OrderDetail> findOrderDetailByOrderno(String orderno) {
		String sql = "select * from orderdetail_v where orderno like ?";
		Object[] args = { Common.getLikeStr(orderno) };
		List<OrderDetail> orderDetails = jdbcTemplate.query(sql, args,
				new BeanPropertyRowMapper<OrderDetail>(OrderDetail.class));
		return orderDetails;
	}

	public List<OrderDetail> findOrderDetailByUserid(Integer userid) {
		String sql = "select * from orderdetail_v where userid= ?";
		Object[] args = { userid };
		List<OrderDetail> orderDetails = jdbcTemplate.query(sql, args,
				new BeanPropertyRowMapper<OrderDetail>(OrderDetail.class));
		return orderDetails;
	}

	public List<OrderDetail> findOrderDetailByLaundryid(Integer laundryid) {
		String sql = "select * from orderdetail_v where laundryid= ?";
		Object[] args = { laundryid };
		List<OrderDetail> orderDetails = jdbcTemplate.query(sql, args,
				new BeanPropertyRowMapper<OrderDetail>(OrderDetail.class));
		return orderDetails;
	}
}
