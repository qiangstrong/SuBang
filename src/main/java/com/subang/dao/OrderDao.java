package com.subang.dao;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.subang.util.Common;
import com.subang.util.WebConstant;

import com.subang.bean.OrderDetail;
import com.subang.bean.StatItem;
import com.subang.domain.Order;
import com.subang.domain.Order.State;

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

	public List<Order> findByOrderno(String orderno) {
		String sql = "select * from order_t where orderno like ?";
		Object[] args = { Common.getLikeStr(orderno) };
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

	public List<OrderDetail> findOrderDetailAll() {
		String sql = "select order_t.*, worker_t.name 'workername', worker_t.cellnum 'workercellnum', "
				+ "addr_t.name 'addrname', addr_t.cellnum 'addrcellnum', city_t.name 'cityname', "
				+ "district_t.name 'districtname', region_t.name 'regionname', addr_t.detail 'addrdetail' "
				+ "from order_t, worker_t, addr_t, city_t, district_t, region_t "
				+ "where order_t.workerid=worker_t.id and order_t.addrid=addr_t.id and addr_t.regionid=region_t.id "
				+ "and region_t.districtid=district_t.id and district_t.cityid=city_t.id ";
		List<OrderDetail> orderDetails = jdbcTemplate.query(sql,
				new BeanPropertyRowMapper<OrderDetail>(OrderDetail.class));
		return orderDetails;
	}

	public List<OrderDetail> findOrderDetailByState(State state) {
		String sql = "select order_t.*, worker_t.name 'workername', worker_t.cellnum 'workercellnum', "
				+ "addr_t.name 'addrname', addr_t.cellnum 'addrcellnum', city_t.name 'cityname', "
				+ "district_t.name 'districtname', region_t.name 'regionname', addr_t.detail 'addrdetail' "
				+ "from order_t, worker_t, addr_t, city_t, district_t, region_t "
				+ "where order_t.workerid=worker_t.id and order_t.addrid=addr_t.id and addr_t.regionid=region_t.id "
				+ "and region_t.districtid=district_t.id and district_t.cityid=city_t.id "
				+ "and order_t.state=?";
		Object[] args = { state.ordinal() };
		List<OrderDetail> orderDetails = jdbcTemplate.query(sql, args,
				new BeanPropertyRowMapper<OrderDetail>(OrderDetail.class));
		return orderDetails;
	}

	public List<OrderDetail> findOrderDetailByOrderno(String orderno) {
		String sql = "select order_t.*, worker_t.name 'workername', worker_t.cellnum 'workercellnum', "
				+ "addr_t.name 'addrname', addr_t.cellnum 'addrcellnum', city_t.name 'cityname', "
				+ "district_t.name 'districtname', region_t.name 'regionname', addr_t.detail 'addrdetail' "
				+ "from order_t, worker_t, addr_t, city_t, district_t, region_t "
				+ "where order_t.workerid=worker_t.id and order_t.addrid=addr_t.id and addr_t.regionid=region_t.id "
				+ "and region_t.districtid=district_t.id and district_t.cityid=city_t.id and "
				+ "order_t.orderno like ?";
		Object[] args = { Common.getLikeStr(orderno) };
		List<OrderDetail> orderDetails = jdbcTemplate.query(sql, args,
				new BeanPropertyRowMapper<OrderDetail>(OrderDetail.class));
		return orderDetails;
	}

	public List<OrderDetail> findOrderDetailByUserid(Integer userid) {
		String sql = "select order_t.*, worker_t.name 'workername', worker_t.cellnum 'workercellnum', "
				+ "addr_t.name 'addrname', addr_t.cellnum 'addrcellnum', city_t.name 'cityname', "
				+ "district_t.name 'districtname', region_t.name 'regionname', addr_t.detail 'addrdetail' "
				+ "from order_t, worker_t, addr_t, city_t, district_t, region_t "
				+ "where order_t.workerid=worker_t.id and order_t.addrid=addr_t.id and addr_t.regionid=region_t.id "
				+ "and region_t.districtid=district_t.id and district_t.cityid=city_t.id and "
				+ "order_t.userid= ?";
		Object[] args = { userid };
		List<OrderDetail> orderDetails = jdbcTemplate.query(sql, args,
				new BeanPropertyRowMapper<OrderDetail>(OrderDetail.class));
		return orderDetails;
	}

	public List<OrderDetail> findOrderDetailByLaundryid(Integer laundryid) {
		String sql = "select order_t.*, worker_t.name 'workername', worker_t.cellnum 'workercellnum', "
				+ "addr_t.name 'addrname', addr_t.cellnum 'addrcellnum', city_t.name 'cityname', "
				+ "district_t.name 'districtname', region_t.name 'regionname', addr_t.detail 'addrdetail' "
				+ "from order_t, worker_t, addr_t, city_t, district_t, region_t "
				+ "where order_t.workerid=worker_t.id and order_t.addrid=addr_t.id and addr_t.regionid=region_t.id "
				+ "and region_t.districtid=district_t.id and district_t.cityid=city_t.id and "
				+ "order_t.laundryid= ?";
		Object[] args = { laundryid };
		List<OrderDetail> orderDetails = jdbcTemplate.query(sql, args,
				new BeanPropertyRowMapper<OrderDetail>(OrderDetail.class));
		return orderDetails;
	}

	
	/**
	 * 只统计处于已完成状态的订单
	 */
	public List<StatItem> statOrderNumByRegion() {
		String sql = Common.getProperty("statOrderNumByRegion");
		List<StatItem> statItems = jdbcTemplate.query(sql, new BeanPropertyRowMapper<StatItem>(
				StatItem.class));
		return statItems;
	}

	public List<StatItem> statOrderNumByDistrict() {
		String sql =Common.getProperty("statOrderNumByDistrict");
		List<StatItem> statItems = jdbcTemplate.query(sql, new BeanPropertyRowMapper<StatItem>(
				StatItem.class));
		return statItems;
	}

	public List<StatItem> statOrderNumByCity() {
		String sql = Common.getProperty("statOrderNumByCity");
		List<StatItem> statItems = jdbcTemplate.query(sql, new BeanPropertyRowMapper<StatItem>(
				StatItem.class));
		return statItems;
	}

	public List<StatItem> statOrderNumByUser() {
		String sql = Common.getProperty("statOrderNumByUser");
		List<StatItem> statItems = jdbcTemplate.query(sql, new BeanPropertyRowMapper<StatItem>(
				StatItem.class));
		return statItems;
	}

	public List<StatItem> statPriceAvgByUser() {
		String sql =Common.getProperty("statPriceAvgByUser");
		List<StatItem> statItems = jdbcTemplate.query(sql, new BeanPropertyRowMapper<StatItem>(
				StatItem.class));
		return statItems;
	}
}
