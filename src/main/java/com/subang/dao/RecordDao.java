package com.subang.dao;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.subang.bean.Pagination;
import com.subang.bean.RecordDetail;
import com.subang.bean.SearchArg;
import com.subang.domain.Order.State;
import com.subang.domain.Record;
import com.subang.util.WebConst;

@Repository
public class RecordDao extends BaseDao<Record> {
	public Record get(Integer id) {
		String sql = "select * from record_t where id=?";
		Object[] args = { id };
		Record record = null;
		try {
			record = jdbcTemplate.queryForObject(sql, args, new BeanPropertyRowMapper<Record>(
					Record.class));
		} catch (EmptyResultDataAccessException e) {
		}
		return record;
	}

	public Record getByOrderno(String orderno) {
		String sql = "select * from record_t where orderno=?";
		Object[] args = { orderno };
		Record record = null;
		try {
			record = jdbcTemplate.queryForObject(sql, args, new BeanPropertyRowMapper<Record>(
					Record.class));
		} catch (EmptyResultDataAccessException e) {
		}
		return record;
	}

	public void save(Record record) {
		String sql = "insert into record_t values(null,?,?,?,?,?,?,?)";
		Object[] args = { record.getOrderno(), record.getState(), record.getTime(),
				record.getGoodsid(), record.getUserid(), record.getAddrid(), record.getWorkerid() };
		jdbcTemplate.update(sql, args);
	}

	public void update(Record record) {
		String sql = "update record_t set orderno=?, state=?, time=?, goodsid=?, userid=?, addrid=?, workerid=? where id=?";
		Object[] args = { record.getOrderno(), record.getState(), record.getTime(),
				record.getGoodsid(), record.getUserid(), record.getAddrid(), record.getWorkerid(),
				record.getId() };
		jdbcTemplate.update(sql, args);
	}

	public void delete(Integer id) {
		String sql = "delete from record_t where id=?";
		Object[] args = { id };
		jdbcTemplate.update(sql, args);
	}

	public List<Record> findAll() {
		String sql = "select * from record_t";
		List<Record> records = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Record>(
				Record.class));
		return records;
	}

	public RecordDetail getDetail(Integer recordid) {
		String sql = "select * from recorddetail_v where id=?";
		Object[] args = { recordid };
		RecordDetail recordDetail = null;
		try {
			recordDetail = jdbcTemplate.queryForObject(sql, args,
					new BeanPropertyRowMapper<RecordDetail>(RecordDetail.class));
		} catch (EmptyResultDataAccessException e) {
		}
		return recordDetail;
	}

	public List<RecordDetail> findDetail(SearchArg searchArg, Pagination pagination) {
		String sql1 = "call countRecord(?,?,?,?,?)";
		Object[] args1 = { searchArg.getType(), searchArg.getUpperid(), searchArg.getArg(),
				searchArg.getStartTime(), searchArg.getEndTime() };
		int recordnum = jdbcTemplate.queryForInt(sql1, args1);
		pagination.setRecordnum(recordnum);
		pagination.round();

		String sql = "call findRecord(?,?,?,?,?,?,?)";
		Object[] args = { searchArg.getType(), searchArg.getUpperid(), searchArg.getArg(),
				searchArg.getStartTime(), searchArg.getEndTime(), pagination.getOffset(),
				WebConst.PAGE_SIZE };

		List<RecordDetail> recordDetails = jdbcTemplate.query(sql, args,
				new BeanPropertyRowMapper<RecordDetail>(RecordDetail.class));
		return recordDetails;
	}

	public List<RecordDetail> findDetailByUseridAndState(Integer userid, State state,
			boolean limitNum) {
		String sql = "select * from recorddetail_v where userid=? and state=?";
		if (limitNum) {
			sql += " limit " + WebConst.ORDER_NUM;
		}
		Object[] args = { userid, state.ordinal() };
		List<RecordDetail> recordDetails = jdbcTemplate.query(sql, args,
				new BeanPropertyRowMapper<RecordDetail>(RecordDetail.class));
		return recordDetails;
	}

	public List<RecordDetail> findDetailByWorkeridAndState(Integer workerid, State state,
			boolean limitNum) {
		String sql = "select * from recorddetail_v where workerid=? and state=?";
		if (limitNum) {
			sql += " limit " + WebConst.ORDER_NUM;
		}
		Object[] args = { workerid, state.ordinal() };
		List<RecordDetail> recordDetails = jdbcTemplate.query(sql, args,
				new BeanPropertyRowMapper<RecordDetail>(RecordDetail.class));
		return recordDetails;
	}

	public int findNumByAddrid(Integer addrid) {
		String sql = "select count(*) from record_t where addrid=?";
		Object[] args = { addrid };
		int num = jdbcTemplate.queryForInt(sql, args);
		return num;
	}
}
