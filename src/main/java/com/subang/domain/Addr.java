package com.subang.domain;

import java.io.Serializable;

public class Addr implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private boolean valid;
	private String name;
	private String cellnum;
	private String detail;
	private Integer userid;
	private Integer regionid;

	public Addr() {
	}

	public Addr(Integer id, boolean valid, String name, String cellnum,
			String detail, Integer userid, Integer regionid) {
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

	public void setValid(boolean valid) {
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

}
