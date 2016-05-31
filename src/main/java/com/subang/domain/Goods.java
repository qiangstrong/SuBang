package com.subang.domain;

import java.io.Serializable;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.subang.domain.face.Filter;
import com.subang.util.ComUtil;
import com.subang.util.SuUtil;

public class Goods implements Filter, Serializable {

	public static String iconPath = "image/info/goods/";
	public static String posterPath = "image/info/poster/";

	private static final long serialVersionUID = 1L;

	private Integer id;
	@NotNull
	@Length(min = 1, max = 10)
	private String name;
	@Length(max = 100)
	private String icon;
	@Length(max = 100)
	private String poster;
	@NotNull
	@Min(0)
	@Digits(integer = 4, fraction = 1)
	private Double money;
	@NotNull
	@Min(0)
	private Integer score;
	@NotNull
	@Min(0)
	private Integer count;
	@NotNull
	@Length(max = 1000)
	private String comment;

	public Goods() {
	}

	public Goods(Integer id, String name, String icon, String poster, Double money, Integer score,
			Integer count, String comment) {
		this.id = id;
		this.name = name;
		this.icon = icon;
		this.poster = poster;
		this.money = money;
		this.score = score;
		this.count = count;
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

	public String getPoster() {
		return poster;
	}

	public void setPoster(String poster) {
		this.poster = poster;
	}

	public void calcPoster(String posterName) {
		do {
			poster = posterPath + SuUtil.getFilename(posterName);
		} while (SuUtil.fileExist(poster));
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = ComUtil.round(money);
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public void doFilter(Object object) {
		Goods goods = (Goods) object;
		if (this.id == null) {
			goods.id = null;
		}
		if (this.name == null) {
			goods.name = null;
		}
		if (this.icon == null) {
			goods.icon = null;
		}
		if (this.poster == null) {
			goods.poster = null;
		}
		if (this.money == null) {
			goods.money = null;
		}
		if (this.score == null) {
			goods.score = null;
		}
		if (this.count == null) {
			goods.count = null;
		}
		if (this.comment == null) {
			goods.comment = null;
		}
	}
}
