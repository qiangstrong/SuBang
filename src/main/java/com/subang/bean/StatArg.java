package com.subang.bean;

import java.sql.Date;
import java.sql.Timestamp;

import com.subang.util.ComUtil;
import com.subang.util.WebConst;

public class StatArg extends PageArg {

	private int type0;
	private int type1;
	private int type2;
	private Timestamp startTime;
	private Timestamp endTime;

	public StatArg() {
		super(ArgType.para);
		this.type0 = WebConst.STAT_NULL;
	}

	public StatArg(int type0, int type1, int type2, Timestamp startTime, Timestamp endTime) {
		super(ArgType.para);
		this.type0 = type0;
		this.type1 = type1;
		this.type2 = type2;
		this.startTime = startTime;
		this.endTime = endTime;
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

	public Timestamp getStartTime() {
		return startTime;
	}

	public String getStartTimeDes() {
		if (startTime == null) {
			return "无期限";
		}
		return new Date(startTime.getTime()).toString();
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public Timestamp getEndTime() {
		return endTime;
	}

	public String getEndTimeDes() {
		if (endTime == null) {
			return "无期限";
		}
		return new Date(endTime.getTime()).toString();
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	public void pre() {
		if (startTime == null) {
			startTime = ComUtil.firstTime;
		}
		if (endTime == null) {
			endTime = ComUtil.lastTime;
		}
	}

	public void after() {
		if (startTime.equals(ComUtil.firstTime)) {
			startTime = null;
		}
		if (endTime.equals(ComUtil.lastTime)) {
			endTime = null;
		}
	}

}
