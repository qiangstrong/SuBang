package com.subang.domain;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.subang.domain.face.Filter;

public class Service implements Filter, Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	@NotNull
	private Boolean valid;
	private Integer seq; // 前台显示的顺序
	@NotNull
	private Integer cityid;
	@NotNull
	private Integer categoryid;

	private String categoryname;

	public Service() {
	}

	public Service(Integer id, Boolean valid, Integer cityid, Integer categoryid,
			String categoryname) {
		this.id = id;
		this.valid = valid;
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

	public Boolean getValid() {
		return valid;
	}

	public String getValidDes() {
		if (valid == null) {
			return null;
		}
		if (valid) {
			return "是";
		}
		return "否";
	}

	public void setValid(Boolean valid) {
		this.valid = valid;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
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

	public void doFilter(Object object) {
		Service service = (Service) object;
		if (this.id == null) {
			service.id = null;
		}
		if (this.valid == null) {
			service.valid = null;
		}
		if (this.seq == null) {
			service.seq = null;
		}
		if (this.cityid == null) {
			service.cityid = null;
		}
		if (this.categoryid == null) {
			service.categoryid = null;
		}
		if (this.categoryname == null) {
			service.categoryname = null;
		}
	}

}
