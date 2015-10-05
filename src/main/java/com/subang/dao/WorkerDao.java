package com.subang.dao;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.subang.domain.Worker;
import com.subang.util.ComUtil;

@Repository
public class WorkerDao extends BaseDao<Worker> {

	public Worker get(Integer id) {
		String sql = "select * from worker_t where id=?";
		Object[] args = { id };
		Worker worker = null;
		try {
			worker = jdbcTemplate.queryForObject(sql, args, new BeanPropertyRowMapper<Worker>(
					Worker.class));
		} catch (EmptyResultDataAccessException e) {
		}
		return worker;
	}

	public Worker getByCellnum(String cellnum) {
		String sql = "select * from worker_t where cellnum=?";
		Object[] args = { cellnum };
		Worker worker = null;
		try {
			worker = jdbcTemplate.queryForObject(sql, args, new BeanPropertyRowMapper<Worker>(
					Worker.class));
		} catch (EmptyResultDataAccessException e) {
		}
		return worker;
	}

	public void save(Worker worker) {
		String sql = "insert into worker_t values(null,?,?,?,?,?,?,?)";
		Object[] args = { worker.getValid(), worker.getCore(), worker.getName(),
				worker.getPassword(), worker.getCellnum(), worker.getDetail(), worker.getComment() };
		jdbcTemplate.update(sql, args);
	}

	public void update(Worker worker) {
		String sql = "update worker_t set valid=?,core=?, name=? ,password=?,cellnum=? ,detail=?,comment=? where id=?";
		Object[] args = { worker.getValid(), worker.getCore(), worker.getName(),
				worker.getPassword(), worker.getCellnum(), worker.getDetail(), worker.getComment(),
				worker.getId() };
		jdbcTemplate.update(sql, args);
	}

	public void delete(Integer id) {
		String sql = "delete from worker_t where id=?";
		Object[] args = { id };
		jdbcTemplate.update(sql, args);
	}

	public Worker findByWorker(Worker worker) {
		String sql = "select * from worker_t where cellnum=? and password=? and valid=1";
		Object[] args = { worker.getCellnum(), worker.getPassword() };
		worker = null;
		try {
			worker = jdbcTemplate.queryForObject(sql, args, new BeanPropertyRowMapper<Worker>(
					Worker.class));
		} catch (EmptyResultDataAccessException e) {
		} catch (IncorrectResultSizeDataAccessException e) {
		}
		return worker;
	}

	public List<Worker> findAll() {
		String sql = "select * from worker_t";
		List<Worker> workers = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Worker>(
				Worker.class));
		return workers;
	}

	public List<Worker> findValidAll() {
		String sql = "select * from worker_t where valid=1";
		List<Worker> workers = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Worker>(
				Worker.class));
		return workers;
	}

	public List<Worker> findByName(String name) {
		String sql = "select * from worker_t where name like ? and valid=1";
		Object[] args = { ComUtil.getLikeStr(name) };
		List<Worker> workers = jdbcTemplate.query(sql, args, new BeanPropertyRowMapper<Worker>(
				Worker.class));
		return workers;
	}

	public List<Worker> findByCellnum(String cellnum) {
		String sql = "select * from worker_t where cellnum like ? and valid=1";
		Object[] args = { ComUtil.getLikeStr(cellnum) };
		List<Worker> workers = jdbcTemplate.query(sql, args, new BeanPropertyRowMapper<Worker>(
				Worker.class));
		return workers;
	}

	public List<Worker> findByCore() {
		String sql = "select * from worker_t where core=1 and valid=1";
		List<Worker> workers = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Worker>(
				Worker.class));
		return workers;
	}

	public int countCellnum(String cellnum) {
		String sql = "select count(*) from worker_t where cellnum=?";
		Object[] args = { cellnum };
		int count = jdbcTemplate.queryForInt(sql, args);
		return count;
	}
}
