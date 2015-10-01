package com.subang.bean;

public class Result {

	public static final String OK = "ok";
	public static final String ERR = "err";

	private String code;
	private String msg;

	public Result() {

	}

	public Result(String code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
