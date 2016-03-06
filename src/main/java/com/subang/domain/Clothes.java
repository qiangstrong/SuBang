package com.subang.domain;

import java.io.Serializable;

import org.hibernate.validator.constraints.Length;

import com.subang.domain.face.Filter;

public class Clothes implements Filter, Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	@Length(max = 100)
	private String flaw;
	private Integer position;
	private Integer articleid;
	private Integer colorid;
	private Integer orderid;

	private String name;
	private String color;

	public Clothes() {
	}

	public Clothes(Integer id, String flaw, Integer position, Integer articleid, Integer colorid,
			Integer orderid, String name, String color) {
		this.id = id;
		this.flaw = flaw;
		this.position = position;
		this.articleid = articleid;
		this.colorid = colorid;
		this.orderid = orderid;
		this.name = name;
		this.color = color;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFlaw() {
		return flaw;
	}

	public void setFlaw(String flaw) {
		this.flaw = flaw;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public Integer getArticleid() {
		return articleid;
	}

	public void setArticleid(Integer articleid) {
		this.articleid = articleid;
	}

	public Integer getColorid() {
		return colorid;
	}

	public void setColorid(Integer colorid) {
		this.colorid = colorid;
	}

	public Integer getOrderid() {
		return orderid;
	}

	public void setOrderid(Integer orderid) {
		this.orderid = orderid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public void doFilter(Object object) {
		Clothes clothes = (Clothes) object;
		if (this.id == null) {
			clothes.id = null;
		}
		if (this.flaw == null) {
			clothes.flaw = null;
		}
		if (this.position == null) {
			clothes.position = null;
		}
		if (this.articleid == null) {
			clothes.articleid = null;
		}
		if (this.colorid == null) {
			clothes.colorid = null;
		}
		if (this.orderid == null) {
			clothes.orderid = null;
		}
		if (this.name == null) {
			clothes.name = null;
		}
		if (this.color == null) {
			clothes.color = null;
		}
	}

}
