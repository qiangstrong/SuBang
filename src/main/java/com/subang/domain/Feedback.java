package com.subang.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class Feedback implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	private Timestamp time;
	@NotNull
	@Length(max = 1000)
	private String comment;

	public Feedback() {
	}

	public Feedback(Integer id, Timestamp time, String comment) {
		this.id = id;
		this.comment = comment;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
