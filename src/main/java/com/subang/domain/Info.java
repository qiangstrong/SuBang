package com.subang.domain;

import java.io.Serializable;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.Length;

import com.subang.domain.face.Filter;
import com.subang.util.ComUtil;

public class Info implements Filter, Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	@Length(max = 12)
	private String phone;
	@Min(0)
	@Digits(integer = 4, fraction = 1)
	private Double shareMoney;
	@Min(0)
	@Digits(integer = 4, fraction = 1)
	private Double salaryLimit;
	@Min(0)
	private Integer prom0;
	@Min(0)
	private Integer prom1;
	@Min(0)
	private Integer prom2;

	public Info() {
	}

	public Info(Integer id, String phone, Double shareMoney, Double salaryLimit, Integer prom0,
			Integer prom1, Integer prom2) {
		this.id = id;
		this.phone = phone;
		this.shareMoney = shareMoney;
		this.salaryLimit = salaryLimit;
		this.prom0 = prom0;
		this.prom1 = prom1;
		this.prom2 = prom2;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Double getShareMoney() {
		return shareMoney;
	}

	public void setShareMoney(Double shareMoney) {
		this.shareMoney = ComUtil.round(shareMoney);
	}

	public Double getSalaryLimit() {
		return salaryLimit;
	}

	public void setSalaryLimit(Double salaryLimit) {
		this.salaryLimit = ComUtil.round(salaryLimit);
	}

	public Integer getProm0() {
		return prom0;
	}

	public void setProm0(Integer prom0) {
		this.prom0 = prom0;
	}

	public Integer getProm1() {
		return prom1;
	}

	public void setProm1(Integer prom1) {
		this.prom1 = prom1;
	}

	public Integer getProm2() {
		return prom2;
	}

	public void setProm2(Integer prom2) {
		this.prom2 = prom2;
	}

	public void doFilter(Object object) {
		Info info = (Info) object;
		if (this.id == null) {
			info.id = null;
		}
		if (this.phone == null) {
			info.phone = null;
		}
		if (this.shareMoney == null) {
			info.shareMoney = null;
		}
		if (this.salaryLimit == null) {
			info.salaryLimit = null;
		}
		if (this.prom0 == null) {
			info.prom0 = null;
		}
		if (this.prom1 == null) {
			info.prom1 = null;
		}
		if (this.prom2 == null) {
			info.prom2 = null;
		}
	}

}
