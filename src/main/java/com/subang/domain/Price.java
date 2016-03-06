package com.subang.domain;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.subang.domain.face.Filter;

public class Price implements Filter, Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	@NotNull
	@Length(min = 1, max = 10)
	private String name;
	@Length(max = 100)
	private String comment;
	@NotNull
	private Integer categoryid;

	public Price() {
	}

	public Price(Integer id, String name, String comment, Integer categoryid) {
		this.id = id;
		this.name = name;
		this.comment = comment;
		this.categoryid = categoryid;
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

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Integer getCategoryid() {
		return categoryid;
	}

	public void setCategoryid(Integer categoryid) {
		this.categoryid = categoryid;
	}

	public void doFilter(Object object) {
		Price price = (Price) object;
		if (this.id == null) {
			price.id = null;
		}
		if (this.name == null) {
			price.name = null;
		}
		if (this.comment == null) {
			price.comment = null;
		}
		if (this.categoryid == null) {
			price.categoryid = null;
		}
	}

}
