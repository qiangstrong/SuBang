package com.subang.bean;

public class StatArg {

	private int type0;
	private int type1;
	private int type2;

	public StatArg() {
		this.type0=0;
	}

	public StatArg(int type0, int type1, int type2) {
		this.type0 = type0;
		this.type1 = type1;
		this.type2 = type2;
	}

	public int getType0() {
		return type0;
	}

	public void setType0(int type0) {
		this.type0 = type0;
	}

	public int getType1() {
		return type1;
	}

	public void setType1(int type1) {
		this.type1 = type1;
	}

	public int getType2() {
		return type2;
	}

	public void setType2(int type2) {
		this.type2 = type2;
	}

}
