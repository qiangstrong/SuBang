package com.subang.dao;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.subang.domain.Laundry;
import com.subang.util.ComUtil;

@Repository
public class LaundryDao extends BaseDao<Laundry> {

	public Laundry get(Integer id) {
		String sql = "select * from laundry_t where id=?";
		Object[] args = { id };
		Laundry laundry=null;
		try {
			laundry = jdbcTemplate.queryForObject(sql, args,
					new BeanPropertyRowMapper<Laundry>(Laundry.class));
		} catch (EmptyResultDataAccessException e) {
		}
		return laundry;
	}

	public void save(Laundry laundry) {
		String sql = "insert into laundry_t values(null,?,?,?,?)";
		Object[] args = { laundry.getName(), laundry.getCellnum(), laundry.getDetail(),
				laundry.getComment() };
		jdbcTemplate.update(sql, args);
	}

	public void update(Laundry laundry) {
		String sql = "update laundry_t set name=? ,cellnum=? ,detail=? ,comment=? where id=?";
		Object[] args = { laundry.getName(), laundry.getCellnum(), laundry.getDetail(),
				laundry.getComment(), laundry.getId() };
		jdbcTemplate.update(sql, args);
	}

	public void delete(Integer id) {
		String sql = "delete from laundry_t where id=?";
		Object[] args = { id };
		jdbcTemplate.update(sql, args);
	}

	public List<Laundry> findAll() {
		String sql = "select * from laundry_t";
		List<Laundry> laundrys = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Laundry>(
				Laundry.class));
		return laundrys;
	}

	public List<Laundry> findByName(String name) {
		String sql = "select * from laundry_t where name like ?";
		Object[] args = { ComUtil.getLikeStr(name) };
		List<Laundry> laundrys = jdbcTemplate.query(sql, args, new BeanPropertyRowMapper<Laundry>(
				Laundry.class));
		return laundrys;
	}

	public List<Laundry> findByCellnum(String cellnum) {
		String sql = "select * from laundry_t where cellnum like ?";
		Object[] args = { ComUtil.getLikeStr(cellnum) };
		List<Laundry> laundrys = jdbcTemplate.query(sql, args, new BeanPropertyRowMapper<Laundry>(
				Laundry.class));
		return laundrys;
	}
}
