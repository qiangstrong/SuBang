package com.subang.domain;

import java.io.Serializable;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.subang.domain.face.Filter;
import com.subang.util.ComUtil;
import com.subang.util.SuUtil;

public class ClothesType implements Filter, Serializable {

	public static String iconPath = "image/info/clothes_type/";

	private static final long serialVersionUID = 1L;

	private Integer id;
	@NotNull
	@Length(min = 1, max = 10)
	private String name;
	@Min(0)
	@Digits(integer = 4, fraction = 1)
	private Double money; // 单位元
	@Length(max = 100)
	private String icon;
	@NotNull
	private Integer categoryid;
	private Integer priceid;

	private String pricename;

	public ClothesType() {
	}

	public ClothesType(Integer id, String name, Double money, String icon, Integer categoryid,
			Integer priceid, String pricename) {
		super();
		this.id = id;
		this.name = name;
		this.money = money;
		this.icon = icon;
		this.categoryid = categoryid;
		this.priceid = priceid;
		this.pricename = pricename;
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

	public Double getMoney() {
		return money;
	}

	public String getMoneyDes() {
		return ComUtil.getDes(money);
	}

	public void setMoney(Double money) {
		this.money = ComUtil.round(money);
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public void calcIcon(String iconName) {
		do {
			icon = iconPath + SuUtil.getFilename(iconName);
		} while (SuUtil.fileExist(icon));
	}

	public Integer getCategoryid() {
		return categoryid;
	}

	public void setCategoryid(Integer categoryid) {
		this.categoryid = categoryid;
	}

	public Integer getPriceid() {
		return priceid;
	}

	public void setPriceid(Integer priceid) {
		this.priceid = priceid;
	}

	public String getPricename() {
		return pricename;
	}

	public void setPricename(String pricename) {
		this.pricename = pricename;
	}

	public void doFilter(Object object) {
		ClothesType clothesType = (ClothesType) object;
		if (this.id == null) {
			clothesType.id = null;
		}
		if (this.name == null) {
			clothesType.name = null;
		}
		if (this.money == null) {
			clothesType.money = null;
		}
		if (this.icon == null) {
			clothesType.icon = null;
		}
		if (this.categoryid == null) {
			clothesType.categoryid = null;
		}
		if (this.priceid == null) {
			clothesType.priceid = null;
		}
		if (this.pricename == null) {
			clothesType.pricename = null;
		}
	}

}
