package com.subang.bean;

public class PageArg {

	public enum ArgType {
		para, msg
	}

	protected ArgType argType;

	public PageArg() {
	}

	public PageArg(ArgType argType) {
		this.argType = argType;
	}

	public ArgType getArgType() {
		return argType;
	}

	public void setArgType(ArgType argType) {
		this.argType = argType;
	}

}
