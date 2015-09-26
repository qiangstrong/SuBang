package com.subang.dao;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.subang.domain.Faq;
import com.subang.util.ComUtil;

@Repository
public class FaqDao extends BaseDao<Faq> {
	public Faq get(Integer id) {
		String sql = "select * from faq_t where id=?";
		Object[] args = { id };
		Faq faq = null;
		try {
			faq = jdbcTemplate.queryForObject(sql, args, new BeanPropertyRowMapper<Faq>(Faq.class));
		} catch (EmptyResultDataAccessException e) {
		}
		return faq;
	}

	public void save(Faq faq) {
		String sql = "insert into faq_t values(null,?,?)";
		Object[] args = { faq.getQuestion(), faq.getAnswer() };
		jdbcTemplate.update(sql, args);
	}

	public void update(Faq faq) {
		String sql = "update faq_t set question=?,answer=? where id=?";
		Object[] args = { faq.getQuestion(), faq.getAnswer(), faq.getId() };
		jdbcTemplate.update(sql, args);
	}

	public void delete(Integer id) {
		String sql = "delete from faq_t where id=?";
		Object[] args = { id };
		jdbcTemplate.update(sql, args);
	}

	public List<Faq> findAll() {
		String sql = "select * from faq_t";
		List<Faq> faqs = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Faq>(Faq.class));
		return faqs;
	}
	
	public List<Faq> findByQuestion(String question) {
		String sql = "select * from faq_t where question like ?";
		Object[] args = { ComUtil.getLikeStr(question) };
		List<Faq> faqs = jdbcTemplate.query(sql, args, new BeanPropertyRowMapper<Faq>(
				Faq.class));
		return faqs;
	}
}
