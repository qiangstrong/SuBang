package com.subang.dao;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.subang.domain.City;
import com.subang.util.Common;

@Repository
public class CityDao extends BaseDao<City> {

	public City get(Integer id) {
		String sql = "select * from city_t where id=?";
		Object[] args = { id };
		City city = null;
		try {
			city = jdbcTemplate.queryForObject(sql, args, new BeanPropertyRowMapper<City>(
					City.class));
		} catch (EmptyResultDataAccessException e) {
		}
		return city;
	}

	public void save(City city) {
		String sql = "insert into city_t values(null,?)";
		Object[] args = { city.getName() };
		jdbcTemplate.update(sql, args);
	}

	public void update(City city) {
		String sql = "update city_t set name=? where id=?";
		Object[] args = { city.getName(), city.getId() };
		jdbcTemplate.update(sql, args);
	}

	public void delete(Integer id) {
		String sql = "delete from city_t where id=?";
		Object[] args = { id };
		jdbcTemplate.update(sql, args);
	}

	public List<City> findAll() {
		String sql = "select * from city_t";
		List<City> citys = jdbcTemplate.query(sql, new BeanPropertyRowMapper<City>(City.class));
		return citys;
	}

	public List<City> findAllValid() {
		String sql = "select distinct cityid `id`, cityname `name` from area_v";
		List<City> citys = jdbcTemplate.query(sql, new BeanPropertyRowMapper<City>(City.class));
		return citys;
	}

	public List<City> findByName(String name) {
		String sql = "select * from city_t where name like ?";
		Object[] args = { Common.getLikeStr(name) };
		List<City> citys = jdbcTemplate.query(sql, args,
				new BeanPropertyRowMapper<City>(City.class));
		return citys;
	}

	public List<City> findValidByName(String name) {
		String sql = "select distinct cityid `id`, cityname `name` from area_v where cityname like ?";
		Object[] args = { Common.getLikeStr(name) };
		List<City> citys = jdbcTemplate.query(sql, args,
				new BeanPropertyRowMapper<City>(City.class));
		return citys;
	}

}
