package com.subang.domain;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import com.subang.domain.face.Filter;

public class Addr implements Filter, Serializable {

	private static final long serialVersionUID = 1L;

	protected Integer id;
	protected Boolean valid;
	@NotNull
	@Length(min = 1, max = 4)
	protected String name;
	@NotNull
	@Pattern(regexp = "\\d{11}")
	protected String cellnum;
	@NotNull
	@Length(max = 50)
	protected String detail;
	protected Integer userid;
	@NotNull
	protected Integer regionid;

	public Addr() {
		this.valid = true;
	}

	public Addr(Integer id, Boolean valid, String name, String cellnum, String detail,
			Integer userid, Integer regionid) {
		this.id = id;
		this.valid = valid;
		this.name = name;
		this.cellnum = cellnum;
		this.detail = detail;
		this.userid = userid;
		this.regionid = regionid;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(Boolean valid) {
		this.valid = valid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCellnum() {
		return cellnum;
	}

	public void setCellnum(String cellnum) {
		this.cellnum = cellnum;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public Integer getRegionid() {
		return regionid;
	}

	public void setRegionid(Integer regionid) {
		this.regionid = regionid;
	}

	public void doFilter(Object object) {
		Addr addr = (Addr) object;
		if (this.id == null) {
			addr.setId(null);
		}
		if (this.valid == null) {
			addr.setValid(null);
		}
		if (this.name == null) {
			addr.setName(null);
		}
		if (this.cellnum == null) {
			addr.setCellnum(null);
		}
		if (this.detail == null) {
			addr.setDetail(null);
		}
		if (this.userid == null) {
			addr.userid = null;
		}
		if (this.regionid == null) {
			addr.regionid = null;
		}
	}

}
