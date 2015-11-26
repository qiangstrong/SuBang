package com.subang.bean;

public class PrepayResult {

	public enum Code {
		succ, fail, conti
	};

	private Code code;
	private String msg; // 本步骤处理失败的原因
	private Object arg; // 特定于支付的参数，如prepay_id

	public PrepayResult() {
	}

	public PrepayResult(Code code, String msg, Object arg) {
		super();
		this.code = code;
		this.msg = msg;
		this.arg = arg;
	}

	public Code getCode() {
		return code;
	}

	public void setCode(Code code) {
		this.code = code;
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
