package com.subang.domain;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

public class Addr implements Serializable {

	private static final long serialVersionUID = 1L;

	protected  Integer id;
	protected  boolean valid;
	@NotNull
	@Length(min = 1, max = 4)
	protected  String name;
	@NotNull
	@Pattern(regexp = "\\d{11}")
	protected  String cellnum;
	@NotNull
	@Length(max = 50)
	protected  String detail;
	
	protected  Integer userid;
	@NotNull
	protected  Integer regionid;

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
