package com.subang.domain;

import java.io.Serializable;

public class Info implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String price_path;
	private String price_text;
	private String scope_path;
	private String scope_text;
	private String about;
	private String term;
	private String phone;
	
	public Info() {
	}

	public Info(Integer id, String price_path, String price_text, String scope_path,
			String scope_text, String about, String term, String phone) {
		this.id = id;
		this.price_path = price_path;
		this.price_text = price_text;
		this.scope_path = scope_path;
		this.scope_text = scope_text;
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

	public String getPrice_path() {
		return price_path;
	}

	public void setPrice_path(String price_path) {
		this.price_path = price_path;
	}

	public String getPrice_text() {
		return price_text;
	}

	public void setPrice_text(String price_text) {
		this.price_text = price_text;
	}

	public String getScope_path() {
		return scope_path;
	}

	public void setScope_path(String scope_path) {
		this.scope_path = scope_path;
	}

	public String getScope_text() {
		return scope_text;
	}

	public void setScope_text(String scope_text) {
		this.scope_text = scope_text;
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
