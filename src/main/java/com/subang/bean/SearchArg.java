package com.subang.bean;

import java.sql.Date;
import java.sql.Timestamp;

import org.hibernate.validator.constraints.Length;

import com.subang.util.ComUtil;
import com.subang.util.WebConst;

public class SearchArg extends PageArg {

	private int type;
	private Integer upperid;
	@Length(max = 100)
	private String arg;
	private Timestamp startTime;
	private Timestamp endTime;

	public SearchArg() {
		super(ArgType.para);
		this.type = WebConst.SEARCH_ALL;
		this.upperid = null;
	}

	public SearchArg(int type, String arg) {
		super(ArgType.para);
		this.type = type;
		this.upperid = null;
		this.arg = arg;
	}

	public SearchArg(Integer upperid, int type, String arg) {
		super(ArgType.para);
		this.type = type;
		this.upperid = upperid;
		this.arg = arg;
	}

	public SearchArg(int type, Integer upperid, String arg, Timestamp startTime, Timestamp endTime) {
		super(ArgType.para);
		this.type = type;
		this.upperid = upperid;
		this.arg = arg;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Integer getUpperid() {
		return upperid;
	}

	public void setUpperid(Integer upperid) {
		this.upperid = upperid;
	}

	public String getArg() {
		return arg;
	}

	public void setArg(String arg) {
		this.arg = arg;
	}

	public Timestamp getStartTime() {
		return startTime;
	}

	public String getStartTimeDes() {
		if (startTime == null) {
			return null;
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
			return null;
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
