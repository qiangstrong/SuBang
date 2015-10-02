package com.subang.domain;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class Info implements Filter, Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	@NotNull
	@Length(max = 12)
	private String phone;

	public Info() {
	}

	public Info(Integer id, String phone) {
		this.id = id;
		this.phone = phone;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void doFilter(Object object) {
		Info info = (Info) object;
		if (this.id == null) {
			info.id = null;
		}
		if (this.phone == null) {
			info.phone = null;
		}
	}

}
