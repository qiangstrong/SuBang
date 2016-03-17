package com.subang.dao;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.subang.bean.Pagination;
import com.subang.domain.User;
import com.subang.util.ComUtil;

@Repository
public class UserDao extends BaseDao<User> {

	public User get(Integer id) {
		String sql = "select * from user_t where id=?";
		Object[] args = { id };
		User user = null;
		try {
			user = jdbcTemplate.queryForObject(sql, args, new BeanPropertyRowMapper<User>(
					User.class));
		} catch (EmptyResultDataAccessException e) {
		}
		return user;
	}

	public User getByOpenid(String openid) {
		String sql = "select * from user_t where openid=?";
		Object[] args = { openid };
		User user = null;
		try {
			user = jdbcTemplate.queryForObject(sql, args, new BeanPropertyRowMapper<User>(
					User.class));
		} catch (EmptyResultDataAccessException e) {
		}
		return user;
	}

	public User getByUserno(String userno) {
		String sql = "select * from user_t where userno=?";
		Object[] args = { userno };
		User user = null;
		try {
			user = jdbcTemplate.queryForObject(sql, args, new BeanPropertyRowMapper<User>(
					User.class));
		} catch (EmptyResultDataAccessException e) {
		}
		return user;
	}

	public User getByCellnum(String cellnum) {
		String sql = "select * from user_t where cellnum=?";
		Object[] args = { cellnum };
		User user = null;
		try {
			user = jdbcTemplate.queryForObject(sql, args, new BeanPropertyRowMapper<User>(
					User.class));
		} catch (EmptyResultDataAccessException e) {
		}
		return user;
	}

	public void save(User user) {
		String sql = "insert into user_t values(null,?,?,?,?,?,?,?,?,?,?)";
		Object[] args = { user.getLogin(), user.getOpenid(), user.getUserno(), user.getNickname(),
				user.getPassword(), user.getCellnum(), user.getScore(), user.getMoney(),
				user.getClient(), user.getAddrid() };
		jdbcTemplate.update(sql, args);
	}

	public void update(User user) {
		String sql = "update user_t set login=?,openid=? ,userno=?, nickname=? ,password=? , "
				+ "cellnum=? ,score=? ,money=?, client=?, addrid=? where id=?";
		Object[] args = { user.getLogin(), user.getOpenid(), user.getUserno(), user.getNickname(),
				user.getPassword(), user.getCellnum(), user.getScore(), user.getMoney(),
				user.getClient(), user.getAddrid(), user.getId() };
		jdbcTemplate.update(sql, args);
	}

	public void delete(Integer id) {
		String sql = "delete from user_t where id=?";
		Object[] args = { id };
		jdbcTemplate.update(sql, args);
	}

	public User findByUser(User user) {
		String sql = "select * from user_t where cellnum=? and password=?";
		Object[] args = { user.getCellnum(), user.getPassword() };
		user = null;
		try {
			user = jdbcTemplate.queryForObject(sql, args, new BeanPropertyRowMapper<User>(
					User.class));
		} catch (EmptyResultDataAccessException e) {
		} catch (IncorrectResultSizeDataAccessException e) {
		}
		return user;
	}

	public List<User> findAll() {
		String sql = "select * from user_t";
		List<User> users = jdbcTemplate.query(sql, new BeanPropertyRowMapper<User>(User.class));
		return users;
	}

	public List<User> findAll(Pagination pagination) {
		String sql = "select * from user_t";
		String sql1 = "select count(*) from user_t";
		List<User> users = findByPage(pagination, sql, sql1, new Object[] {});
		return users;
	}

	public List<User> findByUserno(String userno) {
		String sql = "select * from user_t where userno like ?";
		Object[] args = { ComUtil.getLikeStr(userno) };
		List<User> users = jdbcTemplate.query(sql, args,
				new BeanPropertyRowMapper<User>(User.class));
		return users;
	}

	public List<User> findByUserno(String userno, Pagination pagination) {
		String sql = "select * from user_t where userno like ?";
		String sql1 = "select count(*) from user_t where userno like ?";
		Object[] args = { ComUtil.getLikeStr(userno) };
		List<User> users = findByPage(pagination, sql, sql1, args);
		return users;
	}

	public List<User> findByCellnum(String cellnum) {
		String sql = "select * from user_t where cellnum like ?";
		Object[] args = { ComUtil.getLikeStr(cellnum) };
		List<User> users = jdbcTemplate.query(sql, args,
				new BeanPropertyRowMapper<User>(User.class));
		return users;
	}

	public List<User> findByCellnum(String cellnum, Pagination pagination) {
		String sql = "select * from user_t where cellnum like ?";
		String sql1 = "select count(*) from user_t where cellnum like ?";
		Object[] args = { ComUtil.getLikeStr(cellnum) };
		List<User> users = findByPage(pagination, sql, sql1, args);
		return users;
	}

	public List<User> findByNickname(String nickname) {
		String sql = "select * from user_t where nickname like ?";
		Object[] args = { ComUtil.getLikeStr(nickname) };
		List<User> users = jdbcTemplate.query(sql, args,
				new BeanPropertyRowMapper<User>(User.class));
		return users;
	}

	public int countCellnum(String cellnum) {
		String sql = "select count(*) from user_t where cellnum=?";
		Object[] args = { cellnum };
		int count = jdbcTemplate.queryForInt(sql, args);
		return count;
	}

	public void resetLogin() {
		String sql = "update user_t set login=0";
		jdbcTemplate.update(sql);
	}
}
