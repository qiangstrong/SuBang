package com.subang.domain;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class Admin implements Serializable {

	private static final long serialVersionUID = 1L;

	protected Integer id;
	@NotNull
	@Length(min = 1, max = 10)
	protected String username;
	@NotNull
	@Length(min = 1, max = 50)
	protected String password;

	public Admin() {
	}

	public Admin(Integer id, String username, String password) {
		this.id = id;
		this.username = username;
		this.password = password;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
