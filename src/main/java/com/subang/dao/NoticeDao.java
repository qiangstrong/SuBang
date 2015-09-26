package com.subang.dao;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.subang.domain.Notice;

@Repository
public class NoticeDao extends BaseDao<Notice> {
	public Notice get(Integer id) {
		String sql = "select * from notice_t where id=?";
		Object[] args = { id };
		Notice notice = null;
		try {
			notice = jdbcTemplate.queryForObject(sql, args, new BeanPropertyRowMapper<Notice>(
					Notice.class));
		} catch (EmptyResultDataAccessException e) {
		}
		return notice;
	}

	public void save(Notice notice) {
		String sql = "insert into notice_t values(null,now(),?,?)";
		Object[] args = { notice.getCode(), notice.getMsg() };
		jdbcTemplate.update(sql, args);
	}

	public void update(Notice notice) {
		String sql = "update notice_t set time=?,code=?,msg=? where id=?";
		Object[] args = { notice.getTime(),notice.getCode(), notice.getMsg(), notice.getId() };
		jdbcTemplate.update(sql, args);
	}

	public void delete(Integer id) {
		String sql = "delete from notice_t where id=?";
		Object[] args = { id };
		jdbcTemplate.update(sql, args);
	}

	public List<Notice> findAll() {
		String sql = "select * from notice_t order by time desc";
		List<Notice> notices = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Notice>(
				Notice.class));
		return notices;
	}
}
