package com.subang.domain;

import java.io.Serializable;

public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	public enum Sex {
		unknown, male, female
	}

	private Integer id;
	private String openid;
	private String name;
	private String nickname;
	private String cellnum;
	private int score;
	private String photo; // 用户微信头像的地址
	private int sex;
	private String country;
	private String province;
	private String city; // 利用微信接口获取的城市
	private Integer addrid; // 用户的默认地址

	public User() {
	}

	public User(Integer id, String openid, String name, String nickname, String cellnum, int score,
			String photo, int sex, String country, String province, String city, Integer addrid) {
		this.id = id;
		this.openid = openid;
		this.name = name;
		this.nickname = nickname;
		this.cellnum = cellnum;
		this.score = score;
		this.photo = photo;
		this.sex = sex;
		this.country = country;
		this.province = province;
		this.city = city;
		this.addrid = addrid;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getCellnum() {
		return cellnum;
	}

	public void setCellnum(String cellnum) {
		this.cellnum = cellnum;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public int getSex() {
		return sex;
	}

	public Sex getSexEnum() {
		return Sex.values()[sex];
	}
	
	public void setSex(int sex) {
		this.sex = sex;
	}

	public void setSex(Sex sex) {
		this.sex = sex.ordinal();
	}
	
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Integer getAddrid() {
		return addrid;
	}

	public void setAddrid(Integer addrid) {
		this.addrid = addrid;
	}
	
}
