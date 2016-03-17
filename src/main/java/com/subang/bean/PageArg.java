package com.subang.bean;

public class PageArg {

	public enum ArgType {
		para, msg
	}

	protected ArgType argType;
	protected Pagination pagination;

	public PageArg() {
		pagination = new Pagination();
	}

	public PageArg(ArgType argType) {
		this.argType = argType;
		pagination = new Pagination();
	}

	public ArgType getArgType() {
		return argType;
	}

	public void setArgType(ArgType argType) {
		this.argType = argType;
	}

	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

}
