package com.subang.domain;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class Info implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	@NotNull
	@Length(max = 1000)
	private String about;
	@NotNull
	@Length(max = 1000)
	private String term;
	@NotNull
	@Length(max = 12)
	private String phone;

	public Info() {
	}

	public Info(Integer id, String about, String term, String phone) {
		this.id = id;
		this.about = about;
		this.term = term;
		this.phone = phone;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
