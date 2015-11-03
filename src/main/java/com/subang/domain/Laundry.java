package com.subang.domain;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import com.subang.domain.face.Filter;

public class Laundry implements Filter, Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	@NotNull
	@Length(min = 1, max = 10)
	private String name;
	@NotNull
	@Pattern(regexp = "\\d{11}")
	private String cellnum;
	@NotNull
	@Length(max = 50)
	private String detail; // 商家详细地址
	@Length(max = 100)
	private String comment;

	public Laundry() {
	}

	public Laundry(Integer id, String name, String cellnum, String detail, String comment) {
		this.id = id;
		this.name = name;
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

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public void doFilter(Object object) {
		Laundry laundry = (Laundry) object;
		if (this.id == null) {
			laundry.id = null;
		}
		if (this.name == null) {
			laundry.name = null;
		}
		if (this.cellnum == null) {
			laundry.cellnum = null;
		}
		if (this.detail == null) {
			laundry.detail = null;
		}
		if (this.comment == null) {
			laundry.comment = null;
		}
	}

}
