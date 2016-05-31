package com.subang.dao;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.subang.domain.Goods;

@Repository
public class GoodsDao extends BaseDao<Goods> {

	public Goods get(Integer id) {
		String sql = "select * from goods_t where id=?";
		Object[] args = { id };
		Goods goods = null;
		try {
			goods = jdbcTemplate.queryForObject(sql, args, new BeanPropertyRowMapper<Goods>(
					Goods.class));
		} catch (EmptyResultDataAccessException e) {
		}
		return goods;
	}

	public void save(Goods goods) {
		String sql = "insert into goods_t values(null,?,?,?,?,?,?,?)";
		Object[] args = { goods.getName(), goods.getIcon(), goods.getPoster(), goods.getMoney(),
				goods.getScore(), goods.getCount(), goods.getComment() };
		jdbcTemplate.update(sql, args);
	}

	public void update(Goods goods) {
		String sql = "update goods_t set name=?, icon=?, poster=?, money=?, score=?, count=?, comment=? where id=?";
		Object[] args = { goods.getName(), goods.getIcon(), goods.getPoster(), goods.getMoney(),
				goods.getScore(), goods.getCount(), goods.getComment(), goods.getId() };
		jdbcTemplate.update(sql, args);
	}

	public void delete(Integer id) {
		String sql = "delete from goods_t where id=?";
		Object[] args = { id };
		jdbcTemplate.update(sql, args);
	}

	public List<Goods> findAll() {
		String sql = "select * from goods_t";
		List<Goods> goodss = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Goods>(Goods.class));
		return goodss;
	}

	public List<Goods> findValidAll() {
		String sql = "select * from goods_t where count>0";
		List<Goods> goodss = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Goods>(Goods.class));
		return goodss;
	}
}
