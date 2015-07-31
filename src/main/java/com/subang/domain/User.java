package com.subang.domain;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import com.subang.util.Common;

public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	public static User toUser(weixin.popular.bean.User user_weixin) {
		User user = new User();
		user.valid = true;
		user.openid = user_weixin.getOpenid();
		user.nickname = user_weixin.getNickname();
		user.score = 0;
		user.photo = Common.getUserPhoto(user.openid);
		user.sex = user_weixin.getSex();
		user.country = user_weixin.getCountry();
		user.province = user_weixin.getProvince();
		user.city = user_weixin.getCity();
		return user;
	}

	public enum Sex {
		unknown, male, female
	}

	private Integer id;
	private boolean valid; // 默认为true，用户取消关注后，为false
	@NotNull
	private String openid;
	@Length(max = 4)
	private String name;
	@NotNull
	@Length(max = 100)
	private String nickname;
	@Length(max = 50)
	private String password;
	@Pattern(regexp = "\\d{11}")
	private String cellnum;
	private int score;
	private double money;
	@Length(max = 100)
	private String photo; // 用户微信头像的地址
	private int sex;
	private String country;
	private String province;
	private String city; // 利用微信接口获取的城市
	private Integer addrid; // 用户的默认地址

	public User() {
		this.score = 0;
		this.money = 0;
	}

	public User(Integer id, boolean valid, String openid, String name, String nickname,
			String password, String cellnum, int score, double money, String photo, int sex,
			String country, String province, String city, Integer addrid) {
		this.id = id;
		this.valid = valid;
		this.openid = openid;
		this.name = name;
		this.nickname = nickname;
		this.password = password;
		this.cellnum = cellnum;
		this.score = score;
		this.money = money;
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

	public boolean isValid() {
		return valid;
	}

	public String getValidDes() {
		if (valid) {
			return "是";
		}
		return "否";
	}

	public void setValid(boolean valid) {
		this.valid = valid;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCellnum() {
		return cellnum;
	}

	public String getCellnumDes() {
		if (cellnum == null) {
			return "未绑定";
		}
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

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
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
