package com.subang.bean;

public class Identity {
	public static final int USER = 0;
	public static final int WORKER = 1;
	public static final int OTHER = 2;

	private Integer type;
	private String cellnum;

	public Identity() {
	}

	public Identity(Integer type, String cellnum) {
		this.type = type;
		this.cellnum = cellnum;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public void setType_auth(Integer type) {
		this.type = type;
	}

	public String getCellnum() {
		return cellnum;
	}

	public void setCellnum(String cellnum) {
		this.cellnum = cellnum;
	}

	public void setCellnum_auth(String cellnum) {
		this.cellnum = cellnum;
	}

}
