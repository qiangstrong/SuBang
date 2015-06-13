package com.subang.bean;

import com.subang.util.WebConst;


public class SearchArg{

	private int type;
	private String arg;

	public SearchArg() {	
		this.type=WebConst.SEARCH_ALL;
	}

	public SearchArg(int type, String arg) {
		this.type = type;
		this.arg = arg;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getArg() {
		return arg;
	}

	public void setArg(String arg) {
		this.arg = arg;
	}

}
