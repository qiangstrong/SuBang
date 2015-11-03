package com.subang.domain;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.subang.domain.face.Filter;

public class Clothes implements Filter, Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	@NotNull
	@Length(min = 1, max = 10)
	private String name;
	@NotNull
	@Length(min = 1, max = 4)
	private String color;
	@Length(max = 100)
	private String flaw;
	private Integer orderid;

	public Clothes() {
	}

	public Clothes(Integer id, String name, String color, String flaw, Integer orderid) {
		this.id = id;
		this.name = name;
		this.color = color;
		this.flaw = flaw;
		this.orderid = orderid;
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

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getFlaw() {
		return flaw;
	}

	public void setFlaw(String flaw) {
		this.flaw = flaw;
	}

	public Integer getOrderid() {
		return orderid;
	}

	public void setOrderid(Integer orderid) {
		this.orderid = orderid;
	}

	public void doFilter(Object object) {
		Clothes clothes = (Clothes) object;
		if (this.id == null) {
			clothes.id = null;
		}
		if (this.name == null) {
			clothes.name = null;
		}
		if (this.color == null) {
			clothes.color = null;
		}
		if (this.flaw == null) {
			clothes.flaw = null;
		}
		if (this.orderid == null) {
			clothes.orderid = null;
		}
	}

}
