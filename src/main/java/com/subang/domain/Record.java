package com.subang.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import com.subang.domain.Order.State;
import com.subang.domain.face.Filter;
import com.subang.util.ComUtil;

public class Record implements Filter, Serializable {

	private static final long serialVersionUID = 1L;

	protected Integer id;
	protected String orderno; // 订单号
	protected Integer state;
	protected Timestamp time; // 支付完成发生的日期和时间
	protected Integer goodsid;
	protected Integer userid;
	protected Integer addrid;
	protected Integer workerid;

	public Record() {
	}

	public Record(Integer id, String orderno, Integer state, Timestamp time, Integer goodsid,
			Integer userid, Integer addrid, Integer workerid) {
		this.id = id;
		this.orderno = orderno;
		this.state = state;
		this.time = time;
		this.goodsid = goodsid;
		this.userid = userid;
		this.addrid = addrid;
		this.workerid = workerid;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOrderno() {
		return orderno;
	}

	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}

	public Integer getState() {
		return state;
	}

	public State getStateEnum() {
		if (state == null) {
			return null;
		}
		return State.values()[state];
	}

	public String getStateDes() {
		return State.toStateDes(getStateEnum());
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public void setState(State state) {
		this.state = state.ordinal();
	}

	public Timestamp getTime() {
		return time;
	}

	public String getTimeDes() {
		if (time == null) {
			return null;
		}
		return ComUtil.getDatetimeFormat().format(time);
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public Integer getGoodsid() {
		return goodsid;
	}

	public void setGoodsid(Integer goodsid) {
		this.goodsid = goodsid;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public Integer getAddrid() {
		return addrid;
	}

	public void setAddrid(Integer addrid) {
		this.addrid = addrid;
	}

	public Integer getWorkerid() {
		return workerid;
	}

	public void setWorkerid(Integer workerid) {
		this.workerid = workerid;
	}

	public void doFilter(Object object) {
		Record record = (Record) object;
		if (this.id == null) {
			record.id = null;
		}
		if (this.orderno == null) {
			record.orderno = null;
		}
		if (this.state == null) {
			record.state = null;
		}
		if (this.time == null) {
			record.time = null;
		}
		if (this.goodsid == null) {
			record.goodsid = null;
		}
		if (this.userid == null) {
			record.userid = null;
		}
		if (this.addrid == null) {
			record.addrid = null;
		}
		if (this.workerid == null) {
			record.workerid = null;
		}
	}
}
