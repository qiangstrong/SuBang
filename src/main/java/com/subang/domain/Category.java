package com.subang.domain;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.subang.domain.face.Filter;

public class Category implements Filter, Serializable {

	public static String iconPath = "image/info/category/";

	private static final long serialVersionUID = 1L;

	private Integer id;
	@NotNull
	private Boolean valid;
	@NotNull
	@Length(min = 1, max = 10)
	private String name;
	@Length(max = 100)
	private String icon; // 由后台生成，不用@NotNull校验
	@Length(max = 100)
	private String comment;

	public Category() {
	}

	public Category(Integer id, Boolean valid, String name, String icon, String comment) {
		super();
		this.id = id;
		this.valid = valid;
		this.name = name;
		this.icon = icon;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public void calcIcon(String icon) {
		this.icon = iconPath + icon;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public void doFilter(Object object) {
		Category category = (Category) object;
		if (this.id == null) {
			category.id = null;
		}
		if (this.valid == null) {
			category.valid = null;
		}
		if (this.name == null) {
			category.name = null;
		}
		if (this.icon == null) {
			category.icon = null;
		}
		if (this.comment == null) {
			category.comment = null;
		}
	}

}
