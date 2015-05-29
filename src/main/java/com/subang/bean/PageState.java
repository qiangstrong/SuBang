package com.subang.bean;

public class PageState {

	private Object contentArg;

	public PageState() {
	}

	public PageState(Object contentArg) {
		this.contentArg = contentArg;
	}

	public void setContentArg(Object contentArg) {
		this.contentArg = contentArg;
	}

	public SearchArg getSearchArg() {
		return (SearchArg) contentArg;
	}

	public StatArg getStatArg() {
		return (StatArg) contentArg;
	}
}
