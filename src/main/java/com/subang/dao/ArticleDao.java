package com.subang.dao;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.subang.domain.Article;

@Repository
public class ArticleDao extends BaseDao<Article> {
	public Article get(Integer id) {
		String sql = "select * from article_t where id=?";
		Object[] args = { id };
		Article article = null;
		try {
			article = jdbcTemplate.queryForObject(sql, args, new BeanPropertyRowMapper<Article>(
					Article.class));
		} catch (EmptyResultDataAccessException e) {
		}
		return article;
	}

	public void save(Article article) {
		String sql = "insert into article_t values(null,?)";
		Object[] args = { article.getName() };
		jdbcTemplate.update(sql, args);
	}

	public void update(Article article) {
		String sql = "update article_t set name=? where id=?";
		Object[] args = { article.getName(), article.getId() };
		jdbcTemplate.update(sql, args);
	}

	public void delete(Integer id) {
		String sql = "delete from article_t where id=?";
		Object[] args = { id };
		jdbcTemplate.update(sql, args);
	}

	public List<Article> findAll() {
		String sql = "select * from article_t";
		List<Article> articles = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Article>(
				Article.class));
		return articles;
	}

}
