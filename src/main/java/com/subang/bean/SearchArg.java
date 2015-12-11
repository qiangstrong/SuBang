package com.subang.bean;

import com.subang.util.WebConst;

public class SearchArg extends PageArg {

	private Integer upperid;
	private int type;
	private String arg;

	public SearchArg() {
		super(ArgType.para);
		this.upperid = null;
		this.type = WebConst.SEARCH_ALL;
	}

	public SearchArg(int type, String arg) {
		super(ArgType.para);
		this.upperid = null;
		this.type = type;
		this.arg = arg;
	}

	public SearchArg(Integer upperid, int type, String arg) {
		super(ArgType.para);
		this.upperid = upperid;
		this.type = type;
		this.arg = arg;
	}

	public Integer getUpperid() {
		return upperid;
	}

	public void setUpperid(Integer upperid) {
		this.upperid = upperid;
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
