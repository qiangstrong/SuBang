package com.subang.domain;

import java.io.Serializable;
import java.sql.Timestamp;

public class Notice implements Serializable {
	private static final long serialVersionUID = 1L;

	public enum CodeType {
		sms, incomplete // 短信
	}

	private Integer id;
	private Timestamp time;
	private Integer code;
	private String msg;

	public Notice() {
	}

	public Notice(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public Notice(Integer id, Timestamp time, Integer code, String msg) {
		this.id = id;
		this.time = time;
		this.code = code;
		this.msg = msg;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
