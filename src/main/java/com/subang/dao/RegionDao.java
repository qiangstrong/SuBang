package com.subang.dao;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.subang.bean.Area;
import com.subang.domain.Region;
import com.subang.util.Common;

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
		String sql = "select * from region_t where name like ?";
		Object[] args = { Common.getLikeStr(name) };
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

	public List<Area> findAreaByArea(Area area) {
		String sql = "SELECT city_t.id 'cityid', city_t.name 'cityname', district_t.id 'districtid', "
				+ " district_t.name 'districtname', region_t.id 'regionid', region_t.name 'regionname', "
				+ " region_t.workerid 'workerid' "
				+ " FROM city_t, district_t, region_t "
				+ " where city_t.id=district_t.cityid and district_t.id=region_t.districtid and "
				+ "city_t.name like ? and district_t.name like ? and region_t.name like ?";
		Object[] args = { Common.getLikeStr(area.getCityname()),
				Common.getLikeStr(area.getDistrictname()), Common.getLikeStr(area.getRegionname()) };
		List<Area> areas = jdbcTemplate.query(sql, args,
				new BeanPropertyRowMapper<Area>(Area.class));
		return areas;
	}
	
	public List<Area> findAreaByWorkerid(Integer workerid) {
		String sql = "SELECT city_t.id 'cityid', city_t.name 'cityname', district_t.id 'districtid', "
				+ " district_t.name 'districtname', region_t.id 'regionid', region_t.name 'regionname', "
				+ " region_t.workerid 'workerid' "
				+ " FROM city_t, district_t, region_t "
				+ " where city_t.id=district_t.cityid and district_t.id=region_t.districtid and "
				+ "region_t.workerid=?";
		Object[] args = { workerid };
		List<Area> areas = jdbcTemplate.query(sql, args,
				new BeanPropertyRowMapper<Area>(Area.class));
		return areas;
	}

}
