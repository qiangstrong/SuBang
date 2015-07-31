package com.subang.dao;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.subang.bean.AddrDetail;
import com.subang.domain.Addr;

@Repository
public class AddrDao extends BaseDao<Addr> {

	public Addr get(Integer id) {
		String sql = "select * from addr_t where id=?";
		Object[] args = { id };
		Addr addr=null;
		try {
			addr = jdbcTemplate.queryForObject(sql, args, new BeanPropertyRowMapper<Addr>(
					Addr.class));
		} catch (EmptyResultDataAccessException e) {
		}
		return addr;
	}

	public void save(Addr addr) {
		String sql = "insert into addr_t values(null,?,?,?,?,?,?)";
		Object[] args = { addr.isValid(), addr.getName(), addr.getCellnum(), addr.getDetail(),
				addr.getUserid(), addr.getRegionid() };
		jdbcTemplate.update(sql, args);
	}

	public void update(Addr addr) {
		String sql = "update addr_t set valid=?,name=?,cellnum=?,detail=?,userid=?,regionid=? where id=?";
		Object[] args = { addr.isValid(), addr.getName(), addr.getCellnum(), addr.getDetail(),
				addr.getUserid(), addr.getRegionid(), addr.getId() };
		jdbcTemplate.update(sql, args);
	}

	public void delete(Integer id) {
		String sql = "delete from addr_t where id=?";
		Object[] args = { id };
		jdbcTemplate.update(sql, args);
	}

	public List<Addr> findAll() {
		String sql = "select * from addr_t";
		List<Addr> addrs = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Addr>(Addr.class));
		return addrs;
	}

	// 返回一个用户所有有效的地址
	public List<Addr> findByUserid(Integer userid) {
		String sql = "select * from addr_t where userid=? and valid=1";
		Object[] args = { userid };
		List<Addr> addrs = jdbcTemplate.query(sql, args,
				new BeanPropertyRowMapper<Addr>(Addr.class));
		return addrs;
	}

	public AddrDetail getAddrDetail(Integer id) {
		String sql = "select * from addrdetail_v where id=?";
		Object[] args = { id };
		AddrDetail addrDetail = null;
		try {
			addrDetail = jdbcTemplate.queryForObject(sql, args,
					new BeanPropertyRowMapper<AddrDetail>(AddrDetail.class));
		} catch (EmptyResultDataAccessException e) {
		}
		return addrDetail;
	}

	public List<AddrDetail> findAddrDetailByUserid(Integer userid) {
		String sql = "select * from addrdetail_v where userid=? and valid=1";
		Object[] args = { userid };
		List<AddrDetail> addrDetails = jdbcTemplate.query(sql, args,
				new BeanPropertyRowMapper<AddrDetail>(AddrDetail.class));
		return addrDetails;
	}
}
