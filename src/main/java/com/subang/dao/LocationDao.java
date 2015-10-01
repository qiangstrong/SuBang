package com.subang.dao;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.subang.domain.Location;

@Repository
public class LocationDao extends BaseDao<Location> {

	public Location get(Integer id) {
		String sql = "select * from location_t where id=?";
		Object[] args = { id };
		Location location = null;
		try {
			location = jdbcTemplate.queryForObject(sql, args, new BeanPropertyRowMapper<Location>(
					Location.class));
		} catch (EmptyResultDataAccessException e) {
		}
		return location;
	}

	public void save(Location location) {
		String sql = "insert into location_t values(null,?,?,now(),?,?)";
		Object[] args = { location.getLatitude(), location.getLongitude(), location.getCityid(),
				location.getUserid() };
		jdbcTemplate.update(sql, args);
	}

	public void update(Location location) {
		String sql = "update location_t set latitude=? ,longitude=? ,time=now(), cityid=?, userid=? where id=?";
		Object[] args = { location.getLatitude(), location.getLongitude(), location.getCityid(),
				location.getUserid(), location.getId() };
		jdbcTemplate.update(sql, args);
	}

	public void delete(Integer id) {
		String sql = "delete from location_t where id=?";
		Object[] args = { id };
		jdbcTemplate.update(sql, args);
	}

	public List<Location> findAll() {
		String sql = "select * from location_t";
		List<Location> locations = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Location>(
				Location.class));
		return locations;
	}

	public List<Location> findByUserid(Integer userid) {
		String sql = "select * from location_t where userid=? order by time";
		Object[] args = { userid };
		List<Location> locations = jdbcTemplate.query(sql, args,
				new BeanPropertyRowMapper<Location>(Location.class));
		return locations;
	}
}
