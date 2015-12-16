package com.subang.domain;

import java.io.Serializable;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.subang.domain.face.Filter;
import com.subang.util.ComUtil;

public class Price implements Filter, Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	@NotNull
	@Min(0)
	@Digits(integer = 4, fraction = 0)
	private Double money;
	@Length(max = 100)
	private String comment;
	@NotNull
	private Integer categoryid;

	public Price() {
	}

	public Price(Integer id, Double money, String comment, Integer categoryid) {
		this.id = id;
		this.money = money;
		this.comment = comment;
		this.categoryid = categoryid;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getMoney() {
		return money;
	}

	public String getMoneyDes() {
		return String.valueOf(Math.round(money));
	}

	public void setMoney(Double money) {
		this.money = ComUtil.round(money, 0);
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
		if (this.money == null) {
			price.money = null;
		}
		if (this.comment == null) {
			price.comment = null;
		}
		if (this.categoryid == null) {
			price.categoryid = null;
		}
	}

}
