package com.subang.dao;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.subang.domain.Snapshot;

@Repository
public class SnapshotDao extends BaseDao<Snapshot> {

	public Snapshot get(Integer id) {
		String sql = "select * from snapshot_t where id=?";
		Object[] args = { id };
		Snapshot snapshot = null;
		try {
			snapshot = jdbcTemplate.queryForObject(sql, args, new BeanPropertyRowMapper<Snapshot>(
					Snapshot.class));
		} catch (EmptyResultDataAccessException e) {
		}
		return snapshot;
	}

	public void save(Snapshot snapshot) {
		String sql = "insert into snapshot_t values(null,?,?)";
		Object[] args = { snapshot.getIcon(), snapshot.getClothesid() };
		jdbcTemplate.update(sql, args);
	}

	public void update(Snapshot snapshot) {
		String sql = "update snapshot_t set  icon=?, clothesid=? where id=?";
		Object[] args = { snapshot.getIcon(), snapshot.getClothesid(), snapshot.getId() };
		jdbcTemplate.update(sql, args);
	}

	public void delete(Integer id) {
		String sql = "delete from snapshot_t where id=?";
		Object[] args = { id };
		jdbcTemplate.update(sql, args);
	}

	public List<Snapshot> findAll() {
		String sql = "select * from snapshot_t";
		List<Snapshot> snapshots = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Snapshot>(
				Snapshot.class));
		return snapshots;
	}

	public List<Snapshot> findByClothesid(Integer clothesid) {
		String sql = "select * from snapshot_t where clothesid=?";
		Object[] args = { clothesid };
		List<Snapshot> snapshots = jdbcTemplate.query(sql, args,
				new BeanPropertyRowMapper<Snapshot>(Snapshot.class));
		return snapshots;
	}
}
