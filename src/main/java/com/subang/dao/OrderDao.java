package com.subang.dao;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.subang.bean.OrderDetail;
import com.subang.bean.Pagination;
import com.subang.bean.SearchArg;
import com.subang.domain.Order;
import com.subang.domain.Order.State;
import com.subang.util.ComUtil;
import com.subang.util.WebConst;

@Repository
public class OrderDao extends BaseDao<Order> {

	public Order get(Integer id) {
		String sql = "select * from order_t where id=?";
		Object[] args = { id };
		Order order = null;
		try {
			order = jdbcTemplate.queryForObject(sql, args, new BeanPropertyRowMapper<Order>(
					Order.class));
		} catch (EmptyResultDataAccessException e) {
		}
		return order;
	}

	public Order getByOrderno(String orderno) {
		String sql = "select * from order_t where orderno=?";
		Object[] args = { orderno };
		Order order = null;
		try {
			order = jdbcTemplate.queryForObject(sql, args, new BeanPropertyRowMapper<Order>(
					Order.class));
		} catch (EmptyResultDataAccessException e) {
		}
		return order;
	}

	public void save(Order order) {
		String sql = "insert into order_t values(null,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] args = { order.getOrderno(), order.getState(), order.getMoney(),
				order.getFreight(), order.getDate(), order.getTime(), order.getUserComment(),
				order.getWorkerComment(), order.getRemark(), order.getBarcode(),
				order.getCategoryid(), order.getUserid(), order.getAddrid(), order.getWorkerid(),
				order.getLaundryid() };
		jdbcTemplate.update(sql, args);
	}

	public void update(Order order) {
		String sql = "update order_t set orderno=? ,state=? ,money=? ,freight=?, date=? ,time=? ,"
				+ "user_comment=? ,worker_comment=? ,remark=? ,barcode=? ,categoryid=? ,userid=? ,"
				+ "addrid=? ,workerid=? ,laundryid=? where id=?";
		Object[] args = { order.getOrderno(), order.getState(), order.getMoney(),
				order.getFreight(), order.getDate(), order.getTime(), order.getUserComment(),
				order.getWorkerComment(), order.getRemark(), order.getBarcode(),
				order.getCategoryid(), order.getUserid(), order.getAddrid(), order.getWorkerid(),
				order.getLaundryid(), order.getId() };
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

	public List<Order> findAll(Pagination pagination) {
		String sql = "select * from order_t";
		String sql1 = "select count(*) from order_t";
		List<Order> orders = findByPage(pagination, sql, sql1, new Object[] {});
		return orders;
	}

	public List<Order> findByOrderno(String orderno) {
		String sql = "select * from order_t where orderno like ?";
		Object[] args = { ComUtil.getLikeStr(orderno) };
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

	public List<Order> findByUseridAndState(Integer userid, State state) {
		String sql = "select * from order_t where userid=? and state=?";
		Object[] args = { userid, state.ordinal() };
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

	public OrderDetail getDetail(Integer orderid) {
		String sql = "select * from orderdetail_v where id=?";
		Object[] args = { orderid };
		OrderDetail orderDetail = null;
		try {
			orderDetail = jdbcTemplate.queryForObject(sql, args,
					new BeanPropertyRowMapper<OrderDetail>(OrderDetail.class));
		} catch (EmptyResultDataAccessException e) {
		}
		return orderDetail;
	}

	public OrderDetail getDetailByBarcode(String barcode) {
		String sql = "select * from orderdetail_v where barcode=?";
		Object[] args = { barcode };
		OrderDetail orderDetail = null;
		try {
			orderDetail = jdbcTemplate.queryForObject(sql, args,
					new BeanPropertyRowMapper<OrderDetail>(OrderDetail.class));
		} catch (EmptyResultDataAccessException e) {
		}
		return orderDetail;
	}

	public List<OrderDetail> findDetail(SearchArg searchArg) {
		String sql = "call find(?,?,?,?,?,0,1000)";
		Object[] args = { searchArg.getType(), searchArg.getUpperid(), searchArg.getArg(),
				searchArg.getStartTime(), searchArg.getEndTime() };
		List<OrderDetail> orderDetails = jdbcTemplate.query(sql, args,
				new BeanPropertyRowMapper<OrderDetail>(OrderDetail.class));
		return orderDetails;
	}

	public List<OrderDetail> findDetail(SearchArg searchArg, Pagination pagination) {
		String sql1 = "call countOrder(?,?,?,?,?)";
		Object[] args1 = { searchArg.getType(), searchArg.getUpperid(), searchArg.getArg(),
				searchArg.getStartTime(), searchArg.getEndTime() };
		int recordnum = jdbcTemplate.queryForInt(sql1, args1);
		pagination.setRecordnum(recordnum);
		pagination.round();

		String sql = "call findOrder(?,?,?,?,?,?,?)";
		Object[] args = { searchArg.getType(), searchArg.getUpperid(), searchArg.getArg(),
				searchArg.getStartTime(), searchArg.getEndTime(), pagination.getOffset(),
				WebConst.PAGE_SIZE };

		List<OrderDetail> orderDetails = jdbcTemplate.query(sql, args,
				new BeanPropertyRowMapper<OrderDetail>(OrderDetail.class));
		return orderDetails;
	}

	public List<OrderDetail> findDetailAll() {
		String sql = "select * from orderdetail_v";
		List<OrderDetail> orderDetails = jdbcTemplate.query(sql,
				new BeanPropertyRowMapper<OrderDetail>(OrderDetail.class));
		return orderDetails;
	}

	public List<OrderDetail> findDetailAll(Pagination pagination) {
		String sql = "select * from orderdetail_v";
		String sql1 = "select count(*) from orderdetail_v";
		List<OrderDetail> orderDetails = findByPage(pagination, sql, sql1, new Object[] {},
				OrderDetail.class);
		return orderDetails;
	}

	public List<OrderDetail> findDetailByState(State state) {
		String sql = "select * from orderdetail_v where state=?";
		Object[] args = { state.ordinal() };
		List<OrderDetail> orderDetails = jdbcTemplate.query(sql, args,
				new BeanPropertyRowMapper<OrderDetail>(OrderDetail.class));
		return orderDetails;
	}

	public List<OrderDetail> findDetailByOrderno(String orderno) {
		String sql = "select * from orderdetail_v where orderno like ?";
		Object[] args = { ComUtil.getLikeStr(orderno) };
		List<OrderDetail> orderDetails = jdbcTemplate.query(sql, args,
				new BeanPropertyRowMapper<OrderDetail>(OrderDetail.class));
		return orderDetails;
	}

	public List<OrderDetail> findDetailByUserid(Integer userid) {
		String sql = "select * from orderdetail_v where userid= ?";
		Object[] args = { userid };
		List<OrderDetail> orderDetails = jdbcTemplate.query(sql, args,
				new BeanPropertyRowMapper<OrderDetail>(OrderDetail.class));
		return orderDetails;
	}

	public List<OrderDetail> findDetailByWorkerid(Integer workerid) {
		String sql = "select * from orderdetail_v where workerid= ?";
		Object[] args = { workerid };
		List<OrderDetail> orderDetails = jdbcTemplate.query(sql, args,
				new BeanPropertyRowMapper<OrderDetail>(OrderDetail.class));
		return orderDetails;
	}

	public List<OrderDetail> findDetailByLaundryid(Integer laundryid) {
		String sql = "select * from orderdetail_v where laundryid= ?";
		Object[] args = { laundryid };
		List<OrderDetail> orderDetails = jdbcTemplate.query(sql, args,
				new BeanPropertyRowMapper<OrderDetail>(OrderDetail.class));
		return orderDetails;
	}

	public List<OrderDetail> findDetailByUseridAndState(Integer userid, State state,
			boolean limitNum) {
		String sql = "select * from orderdetail_v where userid=? and state=?";
		if (limitNum) {
			sql += " limit " + WebConst.ORDER_NUM;
		}
		Object[] args = { userid, state.ordinal() };
		List<OrderDetail> orderDetails = jdbcTemplate.query(sql, args,
				new BeanPropertyRowMapper<OrderDetail>(OrderDetail.class));
		return orderDetails;
	}

	public List<OrderDetail> findDetailByWorkeridAndState(Integer workerid, State state,
			boolean limitNum) {
		String sql = "select * from orderdetail_v where workerid=? and state=?";
		if (limitNum) {
			sql += " limit " + WebConst.ORDER_NUM;
		}
		Object[] args = { workerid, state.ordinal() };
		List<OrderDetail> orderDetails = jdbcTemplate.query(sql, args,
				new BeanPropertyRowMapper<OrderDetail>(OrderDetail.class));
		return orderDetails;
	}
}
