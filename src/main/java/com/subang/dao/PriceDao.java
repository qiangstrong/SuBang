package com.subang.dao;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.subang.domain.Price;

@Repository
public class PriceDao extends BaseDao<Price> {
	public Price get(Integer id) {
		String sql = "select * from price_t where id=?";
		Object[] args = { id };
		Price price = null;
		try {
			price = jdbcTemplate.queryForObject(sql, args, new BeanPropertyRowMapper<Price>(
					Price.class));
		} catch (EmptyResultDataAccessException e) {
		}
		return price;
	}

	public void save(Price price) {
		String sql = "insert into price_t values(null,?,?,?)";
		Object[] args = { price.getMoney(), price.getComment(), price.getCategoryid() };
		jdbcTemplate.update(sql, args);
	}

	public void update(Price price) {
		String sql = "update price_t set money=?,comment=?,categoryid=? where id=?";
		Object[] args = { price.getMoney(), price.getComment(), price.getCategoryid(),
				price.getId() };
		jdbcTemplate.update(sql, args);
	}

	public void delete(Integer id) {
		String sql = "delete from price_t where id=?";
		Object[] args = { id };
		jdbcTemplate.update(sql, args);
	}

	public List<Price> findAll() {
		String sql = "select * from price_t";
		List<Price> prices = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Price>(Price.class));
		return prices;
	}

	public List<Double> findAllMoney() {
		String sql = "select distinct money from price_t order by money asc";
		List<Double> moneys = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Double>(
				Double.class));
		return moneys;
	}

	public List<Price> findByCategoryid(Integer categoryid) {
		String sql = "select * from price_t where categoryid=? order by money asc";
		Object[] args = { categoryid };
		List<Price> prices = jdbcTemplate.query(sql, args, new BeanPropertyRowMapper<Price>(
				Price.class));
		return prices;
	}
}
