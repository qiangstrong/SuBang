package com.subang.bean;

public class MsgArg extends PageArg {

	public String key;
	public String msg;

	public MsgArg() {
		super(ArgType.msg);
	}

	public MsgArg(String key, String msg) {
		super(ArgType.msg);
		this.key = key;
		this.msg = msg;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
