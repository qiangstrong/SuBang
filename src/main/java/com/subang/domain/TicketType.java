package com.subang.domain;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.subang.domain.face.Filter;

public class TicketType implements Filter, Serializable {

	public static String iconPath = "image/info/ticket/";

	private static final long serialVersionUID = 1L;

	protected Integer id;
	@NotNull
	@Length(min = 1, max = 10)
	protected String name;
	@Length(max = 100)
	protected String icon;
	@NotNull
	@Digits(integer = 3, fraction = 1)
	protected Double money;
	@NotNull
	protected Integer score;
	protected Timestamp deadline;
	@Length(max = 1000)
	private String comment;
	@NotNull
	protected Integer categoryid;

	protected String categoryname;

	public TicketType() {
	}

	public TicketType(Integer id, String name, String icon, Double money, Integer score,
			Timestamp deadline, String comment, Integer categoryid, String categoryname) {
		this.id = id;
		this.name = name;
		this.icon = icon;
		this.money = money;
		this.score = score;
		this.deadline = deadline;
		this.categoryid = categoryid;
		this.categoryname = categoryname;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public void calcIcon(String icon) {
		this.icon = iconPath + icon;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
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

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Integer getCategoryid() {
		return categoryid;
	}

	public void setCategoryid(Integer categoryid) {
		this.categoryid = categoryid;
	}

	public String getCategoryname() {
		return categoryname;
	}

	public void setCategoryname(String categoryname) {
		this.categoryname = categoryname;
	}

	public void doFilter(Object object) {
		TicketType ticketType = (TicketType) object;
		if (this.id == null) {
			ticketType.id = null;
		}
		if (this.name == null) {
			ticketType.name = null;
		}
		if (this.icon == null) {
			ticketType.icon = null;
		}
		if (this.money == null) {
			ticketType.money = null;
		}
		if (this.score == null) {
			ticketType.score = null;
		}
		if (this.deadline == null) {
			ticketType.deadline = null;
		}
		if (this.comment == null) {
			ticketType.comment = null;
		}
		if (this.categoryid == null) {
			ticketType.categoryid = null;
		}
		if (this.categoryname == null) {
			ticketType.categoryname = null;
		}

	}
}
