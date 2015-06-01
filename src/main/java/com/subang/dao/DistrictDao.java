package com.subang.dao;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.subang.domain.District;
import com.subang.util.Common;

@Repository
public class DistrictDao extends BaseDao<District> {

	public District get(Integer id) {
		String sql = "select * from district_t where id=?";
		Object[] args = { id };
		District district = jdbcTemplate.queryForObject(sql, args,
				new BeanPropertyRowMapper<District>(District.class));
		return district;
	}

	public void save(District district) {
		String sql = "insert into district_t values(null,?,?)";
		Object[] args = { district.getName(), district.getCityid() };
		jdbcTemplate.update(sql, args);
	}

	public void update(District district) {
		String sql = "update district_t set name=? ,cityid=? where id=?";
		Object[] args = { district.getName(), district.getCityid(), district.getId() };
		jdbcTemplate.update(sql, args);
	}

	public void delete(Integer id) {
		String sql = "delete from district_t where id=?";
		Object[] args = { id };
		jdbcTemplate.update(sql, args);
	}

	public List<District> findAll() {
		String sql = "select * from district_t";
		List<District> districts = jdbcTemplate.query(sql, new BeanPropertyRowMapper<District>(
				District.class));
		return districts;
	}

	public List<District> findByName(String name) {
		String sql = "select * from district_t where name like ?";
		Object[] args = { Common.getLikeStr(name) };
		List<District> districts = jdbcTemplate.query(sql, args,
				new BeanPropertyRowMapper<District>(District.class));
		return districts;
	}

	public List<District> findByCityid(Integer cityid) {
		String sql = "select * from district_t where cityid=?";
		Object[] args = { cityid };
		List<District> districts = jdbcTemplate.query(sql, args,
				new BeanPropertyRowMapper<District>(District.class));
		return districts;
	}

	public List<District> findValidByCityid(Integer cityid) {
		String sql = "select distinct districtid `id`, districtname `name`, ? `cityid` from area_v where cityid=?";
		Object[] args = { cityid, cityid };
		List<District> districts = jdbcTemplate.query(sql, args,
				new BeanPropertyRowMapper<District>(District.class));
		return districts;
	}
}
