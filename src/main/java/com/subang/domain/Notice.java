package com.subang.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import com.subang.domain.face.Filter;

public class Notice implements Filter, Serializable {
	private static final long serialVersionUID = 1L;

	public enum Code {
		sms, incomplete, push
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

	public void doFilter(Object object) {
		Notice notice = (Notice) object;
		if (this.id == null) {
			notice.id = null;
		}
		if (this.time == null) {
			notice.time = null;
		}
		if (this.code == null) {
			notice.code = null;
		}
		if (this.msg == null) {
			notice.msg = null;
		}
	}

}
