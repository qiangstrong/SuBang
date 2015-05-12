package com.subang.domain;

import java.io.Serializable;

public class District implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String name;
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

}
