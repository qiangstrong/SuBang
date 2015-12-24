package com.subang.domain;

import java.io.Serializable;

import org.hibernate.validator.constraints.Length;

import com.subang.domain.face.Filter;

public class Banner implements Filter, Serializable {

	public static String iconPath = "image/info/banner/";

	private static final long serialVersionUID = 1L;

	private Integer id;
	private Integer seq; // 前台显示的顺序
	@Length(max = 100)
	private String link;
	@Length(max = 100)
	private String icon; // 由后台生成，不用@NotNull校验
	@Length(max = 100)
	private String comment;

	public Banner() {
	}

	public Banner(Integer id, Integer seq, String link, String icon, String comment) {
		super();
		this.id = id;
		this.seq = seq;
		this.link = link;
		this.icon = icon;
		this.comment = comment;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
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
		Banner banner = (Banner) object;
		if (this.id == null) {
			banner.id = null;
		}
		if (this.seq == null) {
			banner.seq = null;
		}
		if (this.link == null) {
			banner.link = null;
		}
		if (this.icon == null) {
			banner.icon = null;
		}
		if (this.comment == null) {
			banner.comment = null;
		}
	}
}
