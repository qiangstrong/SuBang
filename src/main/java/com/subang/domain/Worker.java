package com.subang.domain;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

public class Worker implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	@NotNull
	private Boolean valid;
	@NotNull
	private Boolean core; // 是否是核心取衣员
	@NotNull
	@Length(min = 1, max = 4)
	private String name;
	@NotNull
	@Length(min = 1, max = 50)
	private String password;
	@NotNull
	@Pattern(regexp = "\\d{11}")
	private String cellnum;
	@Length(max = 50)
	private String detail; // 取衣员的备注
	@Length(max = 100)
	private String comment; // 取衣员的备注

	public Worker() {
	}

	public Worker(Integer id, Boolean valid, Boolean core, String name, String password,
			String cellnum, String detail, String comment) {
		super();
		this.id = id;
		this.valid = valid;
		this.core = core;
		this.name = name;
		this.password = password;
		this.cellnum = cellnum;
		this.detail = detail;
		this.comment = comment;
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

	public void setValid(Boolean valid) {
		this.valid = valid;
	}

	public Boolean getCore() {
		return core;
	}

	public String getCoreDes() {
		if (core) {
			return "是";
		}
		return "否";
	}

	public void setCore(Boolean core) {
		this.core = core;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
