package com.subang.domain;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

public class Ticket implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	private Timestamp deadline;
	private Integer userid;
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

}
