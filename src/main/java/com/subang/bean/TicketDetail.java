package com.subang.bean;

import java.sql.Timestamp;

import com.subang.domain.TicketType;
import com.subang.domain.face.Filter;

public class TicketDetail extends TicketType implements Filter {

	private static final long serialVersionUID = 1L;

	private Integer userid;
	private Integer ticketTypeid;

	public TicketDetail() {
	}

	public TicketDetail(Integer id, String name, String icon, Double money, Integer score,
			Timestamp deadline, String comment, Integer categoryid, String categoryname,
			Integer userid, Integer ticketTypeid) {
		super(id, name, icon, money, score, deadline, comment, categoryid, categoryname);
		this.userid = userid;
		this.ticketTypeid = ticketTypeid;
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
		super.doFilter(object);
		TicketDetail ticketDetail = (TicketDetail) object;
		if (this.userid == null) {
			ticketDetail.userid = null;
		}
		if (this.ticketTypeid == null) {
			ticketDetail.ticketTypeid = null;
		}
	}

}
