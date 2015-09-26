package com.subang.domain;

import java.io.Serializable;

public class Service implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	private Integer cityid;
	private Integer categoryid;

	private String categoryname;

	public Service() {
	}

	public Service(Integer id, Integer cityid, Integer categoryid, String categoryname) {
		this.id = id;
		this.cityid = cityid;
		this.categoryid = categoryid;
		this.setCategoryname(categoryname);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCityid() {
		return cityid;
	}

	public void setCityid(Integer cityid) {
		this.cityid = cityid;
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

}
