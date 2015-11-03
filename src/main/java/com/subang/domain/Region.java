package com.subang.domain;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.subang.domain.face.Filter;

public class Region implements Filter, Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	@NotNull
	@Length(min = 1, max = 10)
	private String name;
	@NotNull
	private Integer districtid;
	private Integer workerid;

	public Region() {
	}

	public Region(Integer id, String name, Integer districtid, Integer workerid) {
		this.id = id;
		this.name = name;
		this.districtid = districtid;
		this.workerid = workerid;
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

	public Integer getDistrictid() {
		return districtid;
	}

	public void setDistrictid(Integer districtid) {
		this.districtid = districtid;
	}

	public Integer getWorkerid() {
		return workerid;
	}

	public void setWorkerid(Integer workerid) {
		this.workerid = workerid;
	}

	public void doFilter(Object object) {
		Region region = (Region) object;
		if (this.id == null) {
			region.id = null;
		}
		if (this.name == null) {
			region.name = null;
		}
		if (this.districtid == null) {
			region.districtid = null;
		}
		if (this.workerid == null) {
			region.workerid = null;
		}
	}

}
