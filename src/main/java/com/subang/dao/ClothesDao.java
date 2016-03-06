package com.subang.dao;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.subang.domain.Clothes;

@Repository
public class ClothesDao extends BaseDao<Clothes> {

	public Clothes get(Integer id) {
		String sql = "select * from clothes_t where id=?";
		Object[] args = { id };
		Clothes clothes = null;
		try {
			clothes = jdbcTemplate.queryForObject(sql, args, new BeanPropertyRowMapper<Clothes>(
					Clothes.class));
		} catch (EmptyResultDataAccessException e) {
		}
		return clothes;
	}

	public void save(Clothes clothes) {
		String sql = "insert into clothes_t values(null,?,?,?,?,?)";
		Object[] args = { clothes.getFlaw(), clothes.getPosition(), clothes.getArticleid(),
				clothes.getColorid(), clothes.getOrderid() };
		jdbcTemplate.update(sql, args);
	}

	public void update(Clothes clothes) {
		String sql = "update clothes_t set flaw=?, position=?, articleid=?, colorid=?, orderid=? where id=?";
		Object[] args = { clothes.getFlaw(), clothes.getPosition(), clothes.getArticleid(),
				clothes.getColorid(), clothes.getOrderid(), clothes.getId() };
		jdbcTemplate.update(sql, args);
	}

	public void delete(Integer id) {
		String sql = "delete from clothes_t where id=?";
		Object[] args = { id };
		jdbcTemplate.update(sql, args);
	}

	public List<Clothes> findAll() {
		String sql = "select * from clothes_t";
		List<Clothes> clothess = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Clothes>(
				Clothes.class));
		return clothess;
	}

	public Clothes getDetail(Integer clothesid) {
		String sql = "select clothes_t.* ,article_t.name `name`, color_t.name `color` from clothes_t, article_t, color_t "
				+ "where article_t.id=clothes_t.articleid and color_t.id=clothes_t.colorid and clothes_t.id=?";
		Object[] args = { clothesid };
		Clothes clothes = null;
		try {
			clothes = jdbcTemplate.queryForObject(sql, args, new BeanPropertyRowMapper<Clothes>(
					Clothes.class));
		} catch (EmptyResultDataAccessException e) {
		}
		return clothes;
	}

	public List<Clothes> findDetailByOrderid(Integer orderid) {
		String sql = "select clothes_t.* ,article_t.name `name`, color_t.name `color` from clothes_t, article_t, color_t "
				+ "where article_t.id=clothes_t.articleid and color_t.id=clothes_t.colorid and orderid=?";
		Object[] args = { orderid };
		List<Clothes> clothess = jdbcTemplate.query(sql, args, new BeanPropertyRowMapper<Clothes>(
				Clothes.class));
		return clothess;
	}
}
