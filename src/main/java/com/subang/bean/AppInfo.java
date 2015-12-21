package com.subang.bean;

public class AppInfo {

	public static final int USER_USER = 0;
	public static final int USER_WORKER = 1;
	public static final int USER_CHECKER = 2;

	public static final int OS_ANDROID = 0;
	public static final int OS_IOS = 1;

	private Integer user;
	private Integer os;
	private Integer version;

	public AppInfo() {
	}

	public AppInfo(Integer user, Integer os, Integer version) {
		super();
		this.user = user;
		this.os = os;
		this.version = version;
	}

	public Integer getUser() {
		return user;
	}

	public void setUser(Integer user) {
		this.user = user;
	}

	public Integer getOs() {
		return os;
	}

	public void setOs(Integer os) {
		this.os = os;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

}
