package com.subang.dao;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.subang.bean.TicketDetail;
import com.subang.domain.Ticket;

@Repository
public class TicketDao extends BaseDao<Ticket> {
	public Ticket get(Integer id) {
		String sql = "select * from ticket_t where id=?";
		Object[] args = { id };
		Ticket ticket = null;
		try {
			ticket = jdbcTemplate.queryForObject(sql, args, new BeanPropertyRowMapper<Ticket>(
					Ticket.class));
		} catch (EmptyResultDataAccessException e) {
		}
		return ticket;
	}

	public void save(Ticket ticket) {
		String sql = "insert into ticket_t values(null,?,?,?)";
		Object[] args = { ticket.getDeadline(), ticket.getUserid(), ticket.getTicketTypeid() };
		jdbcTemplate.update(sql, args);
	}

	public void update(Ticket ticket) {
		String sql = "update ticket_t set deadline=?,userid=?,ticket_typeid=? where id=?";
		Object[] args = { ticket.getDeadline(), ticket.getUserid(), ticket.getTicketTypeid(),
				ticket.getId() };
		jdbcTemplate.update(sql, args);
	}

	public void delete(Integer id) {
		String sql = "delete from ticket_t where id=?";
		Object[] args = { id };
		jdbcTemplate.update(sql, args);
	}

	public List<Ticket> findAll() {
		String sql = "select * from ticket_t";
		List<Ticket> tickets = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Ticket>(
				Ticket.class));
		return tickets;
	}
	
	public TicketDetail getDetail(Integer ticketid){
		String sql = "select * from ticketdetail_v where id=?";
		Object[] args = { ticketid };
		TicketDetail ticketDetail = null;
		try {
			ticketDetail = jdbcTemplate.queryForObject(sql, args,
					new BeanPropertyRowMapper<TicketDetail>(TicketDetail.class));
		} catch (EmptyResultDataAccessException e) {
		}
		return ticketDetail;
	}
	
	public List<TicketDetail> findValidDetailByUserid(Integer userid) {
		String sql = "select * from ticketdetail_v where  userid=? and (now()<deadline or deadline is null);";
		Object[] args = { userid };
		List<TicketDetail> ticketDetails = jdbcTemplate.query(sql, args,
				new BeanPropertyRowMapper<TicketDetail>(TicketDetail.class));
		return ticketDetails;
	}
	
	public void deleteInvalid(){
		String sql = "delete from ticket_t where now()>=deadline";
		jdbcTemplate.update(sql);
	}
}
