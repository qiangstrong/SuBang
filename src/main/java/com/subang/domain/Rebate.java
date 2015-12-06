package com.subang.domain;

import java.io.Serializable;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.subang.domain.face.Filter;
import com.subang.util.ComUtil;

public class Rebate implements Filter, Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	@NotNull
	@Min(0)
	@Digits(integer = 4, fraction = 1)
	private Double money; // 单位元,注意使用ComUtil.round函数取一位小数。
	@NotNull
	@Min(0)
	@Digits(integer = 4, fraction = 1)
	private Double benefit; // 单位元,注意使用ComUtil.round函数取一位小数。

	public Rebate() {
	}

	public Rebate(Integer id, Double money, Double benefit) {
		super();
		this.id = id;
		this.money = money;
		this.benefit = benefit;
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

	public void setMoney(Double money) {
		this.money = ComUtil.round(money);
	}

	public Double getBenefit() {
		return benefit;
	}

	public void setBenefit(Double benefit) {
		this.benefit = ComUtil.round(benefit);
	}

	public String toString() {
		if (money == null || benefit == null) {
			return null;
		}
		return "充" + Math.round(money) + "送" + Math.round(benefit);
	}

	public void doFilter(Object object) {
		Rebate rebate = (Rebate) object;
		if (this.id == null) {
			rebate.id = null;
		}
		if (this.money == null) {
			rebate.money = null;
		}
		if (this.benefit == null) {
			rebate.benefit = null;
		}
	}

}
