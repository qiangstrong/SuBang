package com.subang.domain;

import java.io.Serializable;
import java.sql.Timestamp;

public class Location implements Filter, Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String latitude;
	private String longitude;
	private Timestamp time; // 获取经纬度的时间
	private Integer cityid;
	private Integer userid;

	public Location() {
	}

	public Location(Integer id, String latitude, String longitude, Timestamp time, Integer cityid,
			Integer userid) {
		this.id = id;
		this.latitude = latitude;
		this.longitude = longitude;
		this.time = time;
		this.setCityid(cityid);
		this.userid = userid;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public Timestamp getTime() {
		return time;
	}

	public Integer getCityid() {
		return cityid;
	}

	public void setCityid(Integer cityid) {
		this.cityid = cityid;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public void doFilter(Object object) {
		Location location = (Location) object;
		if (this.id == null) {
			location.id = null;
		}
		if (this.latitude == null) {
			location.latitude = null;
		}
		if (this.longitude == null) {
			location.longitude = null;
		}
		if (this.time == null) {
			location.time = null;
		}
		if (this.cityid == null) {
			location.cityid = null;
		}
		if (this.userid == null) {
			location.userid = null;
		}
	}

}
