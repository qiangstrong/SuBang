package com.subang.dao;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.subang.domain.Banner;

@Repository
public class BannerDao extends BaseDao<Banner> {

	public Banner get(Integer id) {
		String sql = "select * from banner_t where id=?";
		Object[] args = { id };
		Banner banner = null;
		try {
			banner = jdbcTemplate.queryForObject(sql, args, new BeanPropertyRowMapper<Banner>(
					Banner.class));
		} catch (EmptyResultDataAccessException e) {
		}
		return banner;
	}

	public void save(Banner banner) {
		String sql = "insert into banner_t values(null,?,?,?)";
		Object[] args = { banner.getLink(), banner.getIcon(), banner.getComment() };
		jdbcTemplate.update(sql, args);
	}

	public void update(Banner banner) {
		String sql = "update banner_t set name=?,icon=?,comment=? where id=?";
		Object[] args = { banner.getLink(), banner.getIcon(), banner.getComment(), banner.getId() };
		jdbcTemplate.update(sql, args);
	}

	public void delete(Integer id) {
		String sql = "delete from banner_t where id=?";
		Object[] args = { id };
		jdbcTemplate.update(sql, args);
	}

	public List<Banner> findAll() {
		String sql = "select * from banner_t";
		List<Banner> banners = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Banner>(
				Banner.class));
		return banners;
	}

}
