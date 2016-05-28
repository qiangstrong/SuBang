package com.subang.dao;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.subang.bean.Pagination;
import com.subang.domain.TicketCode;

@Repository
public class TicketCodeDao extends BaseDao<TicketCode> {
	public TicketCode get(Integer id) {
		String sql = "select * from ticket_code_t where id=?";
		Object[] args = { id };
		TicketCode ticketCode = null;
		try {
			ticketCode = jdbcTemplate.queryForObject(sql, args,
					new BeanPropertyRowMapper<TicketCode>(TicketCode.class));
		} catch (EmptyResultDataAccessException e) {
		}
		return ticketCode;
	}

	public TicketCode getByCodeno(String codeno) {
		String sql = "select * from ticket_code_t where codeno=?";
		Object[] args = { codeno };
		TicketCode ticketCode = null;
		try {
			ticketCode = jdbcTemplate.queryForObject(sql, args,
					new BeanPropertyRowMapper<TicketCode>(TicketCode.class));
		} catch (EmptyResultDataAccessException e) {
		}
		return ticketCode;
	}

	public void save(TicketCode ticketCode) {
		String sql = "insert into ticket_code_t values(null,?,?,?,?,?)";
		Object[] args = { ticketCode.getValid(), ticketCode.getCodeno(), ticketCode.getStart(),
				ticketCode.getEnd(), ticketCode.getTicketTypeid() };
		jdbcTemplate.update(sql, args);
	}

	public void update(TicketCode ticketCode) {
		String sql = "update ticket_code_t set valid=?, codeno=?, start=?, end=?, ticket_typeid=? where id=?";
		Object[] args = { ticketCode.getValid(), ticketCode.getCodeno(), ticketCode.getStart(),
				ticketCode.getEnd(), ticketCode.getTicketTypeid(), ticketCode.getId() };
		jdbcTemplate.update(sql, args);
	}

	public void delete(Integer id) {
		String sql = "delete from ticket_code_t where id=?";
		Object[] args = { id };
		jdbcTemplate.update(sql, args);
	}

	public List<TicketCode> findAll() {
		String sql = "select * from ticket_code_t";
		List<TicketCode> ticketCodes = jdbcTemplate.query(sql,
				new BeanPropertyRowMapper<TicketCode>(TicketCode.class));
		return ticketCodes;
	}

	public List<TicketCode> findAll(Pagination pagination) {
		String sql = "select * from ticket_code_t";
		String sql1 = "select count(*) from ticket_code_t";
		List<TicketCode> ticketCodes = findByPage(pagination, sql, sql1, new Object[] {});
		return ticketCodes;
	}

	public void deleteInvalid() {
		String sql = "delete from ticket_code_t where now()>=end";
		jdbcTemplate.update(sql);
	}

}
