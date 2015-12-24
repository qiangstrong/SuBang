package com.subang.dao;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.subang.domain.Service;

@Repository
public class ServiceDao extends BaseDao<Service> {
	public Service get(Integer id) {
		String sql = "select * from service_t where id=?";
		Object[] args = { id };
		Service service = null;
		try {
			service = jdbcTemplate.queryForObject(sql, args, new BeanPropertyRowMapper<Service>(
					Service.class));
		} catch (EmptyResultDataAccessException e) {
		}
		return service;
	}

	public void save(Service service) {
		String sql = "insert into service_t values(null,?,?,?,?)";
		Object[] args = { service.getValid(), service.getSeq(), service.getCityid(),
				service.getCategoryid() };
		jdbcTemplate.update(sql, args);
	}

	public void update(Service service) {
		String sql = "update service_t set valid=?, seq=?, cityid=?, categoryid=? where id=?";
		Object[] args = { service.getValid(), service.getSeq(), service.getCityid(),
				service.getCategoryid(), service.getId() };
		jdbcTemplate.update(sql, args);
	}

	public void delete(Integer id) {
		String sql = "delete from service_t where id=?";
		Object[] args = { id };
		jdbcTemplate.update(sql, args);
	}

	public List<Service> findAll() {
		String sql = "select * from service_t";
		List<Service> services = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Service>(
				Service.class));
		return services;
	}

	public Service getDetail(Integer serviceid) {
		String sql = "select service_t.* ,category_t.name `categoryname` from service_t,category_t "
				+ "where category_t.id=service_t.categoryid and service_t.id=?";
		Object[] args = { serviceid };
		Service service = null;
		try {
			service = jdbcTemplate.queryForObject(sql, args, new BeanPropertyRowMapper<Service>(
					Service.class));
		} catch (EmptyResultDataAccessException e) {
		}
		return service;
	}

	public List<Service> findDetailByCityid(Integer cityid) {
		String sql = "select service_t.* ,category_t.name `categoryname` from service_t,category_t "
				+ "where category_t.id=service_t.categoryid and service_t.cityid=? order by seq asc";
		Object[] args = { cityid };
		List<Service> services = jdbcTemplate.query(sql, args, new BeanPropertyRowMapper<Service>(
				Service.class));
		return services;
	}
}
