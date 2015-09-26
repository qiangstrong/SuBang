package com.subang.dao;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.subang.domain.Feedback;

@Repository
public class FeedbackDao extends BaseDao<Feedback> {
	public Feedback get(Integer id) {
		String sql = "select * from feedback_t where id=?";
		Object[] args = { id };
		Feedback feedback = null;
		try {
			feedback = jdbcTemplate.queryForObject(sql, args, new BeanPropertyRowMapper<Feedback>(
					Feedback.class));
		} catch (EmptyResultDataAccessException e) {
		}
		return feedback;
	}

	public void save(Feedback feedback) {
		String sql = "insert into feedback_t values(null,now(),?)";
		Object[] args = { feedback.getComment() };
		jdbcTemplate.update(sql, args);
	}

	public void update(Feedback feedback) {
		String sql = "update feedback_t set time=?,comment=? where id=?";
		Object[] args = { feedback.getTime(), feedback.getComment(), feedback.getId() };
		jdbcTemplate.update(sql, args);
	}

	public void delete(Integer id) {
		String sql = "delete from feedback_t where id=?";
		Object[] args = { id };
		jdbcTemplate.update(sql, args);
	}

	public List<Feedback> findAll() {
		String sql = "select * from feedback_t order by time desc";
		List<Feedback> feedbacks = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Feedback>(
				Feedback.class));
		return feedbacks;
	}
}
