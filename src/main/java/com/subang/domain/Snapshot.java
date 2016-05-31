package com.subang.domain;

import java.io.Serializable;

import org.hibernate.validator.constraints.Length;

import com.subang.domain.face.Filter;
import com.subang.util.ComUtil;
import com.subang.util.SuUtil;
import com.subang.util.WebConst;

public class Snapshot implements Filter, Serializable {

	public static String iconPath = "image/info/snapshot/";

	private static final long serialVersionUID = 1L;

	private Integer id;
	@Length(max = 100)
	private String icon;
	private Integer clothesid;

	public Snapshot() {
	}

	public Snapshot(Integer id, String icon, Integer clothesid) {
		this.id = id;
		this.icon = icon;
		this.clothesid = clothesid;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public void calcIcon(String orderno, String iconName) {
		do {
			icon = iconPath + orderno + ComUtil.getRandomStr(WebConst.ICON_RANDOM_LENGTH)
					+ ComUtil.getSuffix(iconName);
		} while (SuUtil.fileExist(icon));
	}

	public Integer getClothesid() {
		return clothesid;
	}

	public void setClothesid(Integer clothesid) {
		this.clothesid = clothesid;
	}

	public void doFilter(Object object) {
		Snapshot snapshot = (Snapshot) object;
		if (this.id == null) {
			snapshot.id = null;
		}
		if (this.icon == null) {
			snapshot.icon = null;
		}
		if (this.clothesid == null) {
			snapshot.clothesid = null;
		}
	}
}
