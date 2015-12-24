package com.subang.dao;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.subang.domain.Category;

@Repository
public class CategoryDao extends BaseDao<Category> {

	public Category get(Integer id) {
		String sql = "select * from category_t where id=?";
		Object[] args = { id };
		Category category = null;
		try {
			category = jdbcTemplate.queryForObject(sql, args, new BeanPropertyRowMapper<Category>(
					Category.class));
		} catch (EmptyResultDataAccessException e) {
		}
		return category;
	}

	public void save(Category category) {
		String sql = "insert into category_t values(null,?,?,?)";
		Object[] args = { category.getName(), category.getIcon(), category.getComment() };
		jdbcTemplate.update(sql, args);
	}

	public void update(Category category) {
		String sql = "update category_t set name=?,icon=?,comment=? where id=?";
		Object[] args = { category.getName(), category.getIcon(), category.getComment(),
				category.getId() };
		jdbcTemplate.update(sql, args);
	}

	public void delete(Integer id) {
		String sql = "delete from category_t where id=?";
		Object[] args = { id };
		jdbcTemplate.update(sql, args);
	}

	public List<Category> findAll() {
		String sql = "select * from category_t";
		List<Category> categorys = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Category>(
				Category.class));
		return categorys;
	}

	public List<Category> findByCityid(Integer cityid) {
		String sql = "select category_t.* ,service_t.valid, service_t.seq from service_t,category_t "
				+ "where category_t.id=service_t.categoryid and service_t.cityid=? order by seq asc";
		Object[] args = { cityid };
		List<Category> categorys = jdbcTemplate.query(sql, args,
				new BeanPropertyRowMapper<Category>(Category.class));
		return categorys;
	}
}
