package com.subang.bean;

import com.subang.util.WebConst;

public class PageState {

	private String name;
	private Object arg;

	public PageState() {
	}

	public PageState(String name, Object arg) {
		this.name = name;
		this.arg = arg;
	}

	public String getName() {
		return name;
	}

	public String calcBackLink() {
		return WebConst.BACK_PREFIX + "/" + name + "/back.html";
	}

	public void setName(String name) {
		this.name = name;
	}

	public Object getArg() {
		return arg;
	}

	public void setArg(Object arg) {
		this.arg = arg;
	}

	/*
	 * getter方法说明了arg的用途
	 */
	public SearchArg getSearchArg() {
		return (SearchArg) arg;
	}

	public StatArg getStatArg() {
		return (StatArg) arg;
	}

	// upperid：区管理界面的upperid就是其所属城市的id
	public Integer getUpperid() {
		return (Integer) arg;
	}
}
