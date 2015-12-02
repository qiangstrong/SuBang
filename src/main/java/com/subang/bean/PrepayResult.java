package com.subang.bean;

public class PrepayResult {

	public enum Code {
		succ, fail, conti
	};

	private Integer code;// 如果code为succ，只传code即可
	private String msg; // 如果code为fail，给出原因
	private Object arg; // 如果code为continue，给出参数

	public PrepayResult() {
	}

	public PrepayResult(Integer code, String msg, Object arg) {
		super();
		this.code = code;
		this.msg = msg;
		this.arg = arg;
	}

	public Integer getCode() {
		return code;
	}

	public Code getCodeEnum() {
		return Code.values()[code];
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public void setCode(Code code) {
		this.code = code.ordinal();
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getArg() {
		return arg;
	}

	public void setArg(Object arg) {
		this.arg = arg;
	}

}
