package com.subang.dao;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.subang.domain.Region;

@Repository
public class RegionDao extends BaseDao<Region> {

	public Region get(Integer id) {
		String sql = "select * from region_t where id=?";
		Object[] args = { id };
		Region region = jdbcTemplate.queryForObject(sql, args, new BeanPropertyRowMapper<Region>(
				Region.class));
		return region;
	}

	public void save(Region region) {
		String sql = "insert into region_t values(null,?,?,?)";
		Object[] args = { region.getName(), region.getDistrictid(), region.getWorkerid() };
		jdbcTemplate.update(sql, args);
	}

	public void update(Region region) {
		String sql = "update region_t set name=? ,districtid=? ,workerid=? where id=?";
		Object[] args = { region.getName(), region.getDistrictid(), region.getWorkerid(),
				region.getId() };
		jdbcTemplate.update(sql, args);
	}

	public void delete(Integer id) {
		String sql = "delete from region_t where id=?";
		Object[] args = { id };
		jdbcTemplate.update(sql, args);
	}

	public List<Region> findAll() {
		String sql = "select * from region_t";
		List<Region> regions = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Region>(
				Region.class));
		return regions;
	}

	public List<Region> findByName(String name) {
		String sql = "select * from region_t where name=?";
		Object[] args = { name };
		List<Region> regions = jdbcTemplate.query(sql, args, new BeanPropertyRowMapper<Region>(
				Region.class));
		return regions;
	}

	public List<Region> findByDistrictid(Integer districtid) {
		String sql = "select * from region_t where districtid=?";
		Object[] args = { districtid };
		List<Region> regions = jdbcTemplate.query(sql, args, new BeanPropertyRowMapper<Region>(
				Region.class));
		return regions;
	}
}
