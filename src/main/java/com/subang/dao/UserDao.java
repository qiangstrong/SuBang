package com.subang.dao;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.subang.bean.StatItem;
import com.subang.domain.User;
import com.subang.util.Common;
import com.subang.util.WebConst;

@Repository
public class UserDao extends BaseDao<User> {

	public User get(Integer id) {
		String sql = "select * from user_t where id=?";
		Object[] args = { id };
		User user = jdbcTemplate.queryForObject(sql, args, new BeanPropertyRowMapper<User>(
				User.class));
		return user;
	}

	public void save(User user) {
		String sql = "insert into user_t values(null,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] args = { user.isValid(), user.getOpenid(), user.getName(), user.getNickname(),
				user.getPassword(), user.getCellnum(), user.getScore(), user.getPhoto(),
				user.getSex(), user.getCountry(), user.getProvince(), user.getCity(),
				user.getAddrid() };
		jdbcTemplate.update(sql, args);
	}

	public void update(User user) {
		String sql = "update user_t set valid=?, openid=? ,name=? ,nickname=? ,password=? ,cellnum=? ,score=? ,"
				+ "photo=? ,sex=? ,country=? ,province=? ,city=? ,addrid=? where id=?";
		Object[] args = { user.isValid(), user.getOpenid(), user.getName(), user.getNickname(),
				user.getPassword(), user.getCellnum(), user.getScore(), user.getPhoto(),
				user.getSex(), user.getCountry(), user.getProvince(), user.getCity(),
				user.getAddrid(), user.getId() };
		jdbcTemplate.update(sql, args);
	}

	public void delete(Integer id) {
		String sql = "delete from user_t where id=?";
		Object[] args = { id };
		jdbcTemplate.update(sql, args);
	}

	public List<User> findAll() {
		String sql = "select * from user_t";
		List<User> users = jdbcTemplate.query(sql, new BeanPropertyRowMapper<User>(User.class));
		return users;
	}

	public List<User> findAll(int pageno) {
		int offset = (pageno - 1) * WebConst.PAGE_SIZE;
		String sql = "select * from user_t";
		List<User> users = findByPage(sql, new Object[] {}, offset, WebConst.PAGE_SIZE);
		return users;
	}

	public User findByOpenid(String openid) {
		String sql = "select * from user_t where openid=?";
		Object[] args = { openid };
		User user = null;
		try {
			user=jdbcTemplate.queryForObject(sql, args, new BeanPropertyRowMapper<User>(User.class));
		} catch (EmptyResultDataAccessException e) {
		}
		return user;
	}

	public List<User> findByNickname(String nickname) {
		String sql = "select * from user_t where nickname like ?";
		Object[] args = { Common.getLikeStr(nickname) };
		List<User> users = jdbcTemplate.query(sql, args,
				new BeanPropertyRowMapper<User>(User.class));
		return users;
	}

	public List<User> findByCellnum(String cellnum) {
		String sql = "select * from user_t where cellnum like ?";
		Object[] args = { Common.getLikeStr(cellnum) };
		List<User> users = jdbcTemplate.query(sql, args,
				new BeanPropertyRowMapper<User>(User.class));
		return users;
	}
}
