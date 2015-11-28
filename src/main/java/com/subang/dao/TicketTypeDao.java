package com.subang.dao;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.subang.domain.TicketType;

@Repository
public class TicketTypeDao extends BaseDao<TicketType> {
	public TicketType get(Integer id) {
		String sql = "select * from ticket_type_t where id=?";
		Object[] args = { id };
		TicketType ticketType = null;
		try {
			ticketType = jdbcTemplate.queryForObject(sql, args,
					new BeanPropertyRowMapper<TicketType>(TicketType.class));
		} catch (EmptyResultDataAccessException e) {
		}
		return ticketType;
	}

	public void save(TicketType ticketType) {
		String sql = "insert into ticket_type_t values(null,?,?,?,?,?,?,?)";
		Object[] args = { ticketType.getName(), ticketType.getIcon(), ticketType.getMoney(),
				ticketType.getScore(), ticketType.getDeadline(), ticketType.getComment(),
				ticketType.getCategoryid() };
		jdbcTemplate.update(sql, args);
	}

	public void update(TicketType ticketType) {
		String sql = "update ticket_type_t set name=?,icon=?,money=?,score=?,deadline=?,comment=?,categoryid=? where id=?";
		Object[] args = { ticketType.getName(), ticketType.getIcon(), ticketType.getMoney(),
				ticketType.getScore(), ticketType.getDeadline(), ticketType.getComment(),
				ticketType.getCategoryid(), ticketType.getId() };
		jdbcTemplate.update(sql, args);
	}

	public void delete(Integer id) {
		String sql = "delete from ticket_type_t where id=?";
		Object[] args = { id };
		jdbcTemplate.update(sql, args);
	}

	public List<TicketType> findAll() {
		String sql = "select * from ticket_type_t";
		List<TicketType> ticketTypes = jdbcTemplate.query(sql,
				new BeanPropertyRowMapper<TicketType>(TicketType.class));
		return ticketTypes;
	}

	public void deleteInvalid() {
		String sql = "delete from ticket_type_t where now()>=deadline";
		jdbcTemplate.update(sql);
	}

	public TicketType getDetail(Integer ticketTypeid) {
		String sql = "select * from ticket_typedetail_v where id=?";
		Object[] args = { ticketTypeid };
		TicketType ticketType = null;
		try {
			ticketType = jdbcTemplate.queryForObject(sql, args,
					new BeanPropertyRowMapper<TicketType>(TicketType.class));
		} catch (EmptyResultDataAccessException e) {
		}
		return ticketType;
	}

	public List<TicketType> findDetailAll() {
		String sql = "select * from ticket_typedetail_v";
		List<TicketType> ticketTypes = jdbcTemplate.query(sql,
				new BeanPropertyRowMapper<TicketType>(TicketType.class));
		return ticketTypes;
	}

	public List<TicketType> findDetailValidAll() {
		String sql = "select * from ticket_typedetail_v where (now()<deadline or deadline is null)";
		List<TicketType> ticketTypes = jdbcTemplate.query(sql,
				new BeanPropertyRowMapper<TicketType>(TicketType.class));
		return ticketTypes;
	}

}
