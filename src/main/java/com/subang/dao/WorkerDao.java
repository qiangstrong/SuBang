package com.subang.dao;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.subang.domain.Worker;
import com.subang.util.Common;

@Repository
public class WorkerDao extends BaseDao<Worker> {

	public Worker get(Integer id) {
		String sql = "select * from worker_t where id=?";
		Object[] args = { id };
		Worker worker = jdbcTemplate.queryForObject(sql, args, new BeanPropertyRowMapper<Worker>(
				Worker.class));
		return worker;
	}

	public void save(Worker worker) {
		String sql = "insert into worker_t values(null,?,?,?)";
		Object[] args = { worker.getName(), worker.getCellnum(), worker.getComment() };
		jdbcTemplate.update(sql, args);
	}

	public void update(Worker worker) {
		String sql = "update worker_t set name=? ,cellnum=? ,comment=? where id=?";
		Object[] args = { worker.getName(), worker.getCellnum(), worker.getComment(),
				worker.getId() };
		jdbcTemplate.update(sql, args);
	}

	public void delete(Integer id) {
		String sql = "delete from worker_t where id=?";
		Object[] args = { id };
		jdbcTemplate.update(sql, args);
	}

	public List<Worker> findAll() {
		String sql = "select * from worker_t";
		List<Worker> workers = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Worker>(
				Worker.class));
		return workers;
	}

	public List<Worker> findByName(String name) {
		String sql = "select * from worker_t where name like ?";
		Object[] args = { Common.getLikeStr(name) };
		List<Worker> workers = jdbcTemplate.query(sql, args, new BeanPropertyRowMapper<Worker>(
				Worker.class));
		return workers;
	}

	public List<Worker> findByCellnum(String cellnum) {
		String sql = "select * from worker_t where cellnum like ?";
		Object[] args = { Common.getLikeStr(cellnum) };
		List<Worker> workers = jdbcTemplate.query(sql, args, new BeanPropertyRowMapper<Worker>(
				Worker.class));
		return workers;
	}
}
