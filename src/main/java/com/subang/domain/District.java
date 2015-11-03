package com.subang.domain;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.subang.domain.face.Filter;

public class District implements Filter, Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	@NotNull
	@Length(min = 1, max = 10)
	private String name;
	@NotNull
	private Integer cityid;

	public District() {
	}

	public District(Integer id, String name, Integer cityid) {
		this.id = id;
		this.name = name;
		this.cityid = cityid;
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

	public Integer getCityid() {
		return cityid;
	}

	public void setCityid(Integer cityid) {
		this.cityid = cityid;
	}

	public void doFilter(Object object) {
		District district = (District) object;
		if (this.id == null) {
			district.id = null;
		}
		if (this.name == null) {
			district.name = null;
		}
		if (this.cityid == null) {
			district.cityid = null;
		}
	}

}
