package com.subang.domain;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

import javax.validation.constraints.NotNull;

import com.subang.domain.face.Filter;

public class Ticket implements Filter, Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	private Timestamp deadline;
	private Integer userid;
	@NotNull
	private Integer ticketTypeid;

	public Ticket() {
	}

	public Ticket(Integer id, Timestamp deadline, Integer userid, Integer ticketTypeid) {
		this.id = id;
		this.deadline = deadline;
		this.userid = userid;
		this.ticketTypeid = ticketTypeid;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Timestamp getDeadline() {
		return deadline;
	}

	public String getDeadlineDes() {
		if (deadline == null) {
			return "无期限";
		}
		return new Date(deadline.getTime()).toString();
	}

	public void setDeadline(Timestamp deadline) {
		this.deadline = deadline;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public Integer getTicketTypeid() {
		return ticketTypeid;
	}

	public void setTicketTypeid(Integer ticketTypeid) {
		this.ticketTypeid = ticketTypeid;
	}

	public void doFilter(Object object) {
		Ticket ticket = (Ticket) object;
		if (this.id == null) {
			ticket.id = null;
		}
		if (this.deadline == null) {
			ticket.deadline = null;
		}
		if (this.userid == null) {
			ticket.userid = null;
		}
		if (this.ticketTypeid == null) {
			ticket.ticketTypeid = null;
		}
	}

}
