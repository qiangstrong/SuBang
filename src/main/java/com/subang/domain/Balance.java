package com.subang.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;

import com.subang.domain.Order.State;
import com.subang.domain.Payment.PayType;
import com.subang.domain.face.Filter;
import com.subang.util.ComUtil;

public class Balance implements Filter, Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String orderno; // 订单号
	private Integer state;
	@Min(0)
	@Digits(integer = 4, fraction = 1)
	private Double money; // 单位元,注意使用ComUtil.round函数取一位小数。
	private Timestamp time; // 支付完成发生的日期和时间
	private Integer userid;

	private Integer payType;

	public Balance() {
	}

	public Balance(Integer id, String orderno, Integer state, Double money, Timestamp time,
			Integer userid, Integer payType) {
		super();
		this.id = id;
		this.orderno = orderno;
		this.state = state;
		this.money = money;
		this.time = time;
		this.userid = userid;
		this.payType = payType;
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

	public void setState(Integer state) {
		this.state = state;
	}

	public void setState(State state) {
		this.state = state.ordinal();
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = ComUtil.round(money);
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

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public Integer getPayType() {
		return payType;
	}

	public PayType getPayTypeEnum() {
		if (payType == null) {
			return null;
		}
		return PayType.values()[payType];
	}

	public String getPayTypeDes() {
		return PayType.toPayTypeDes(getPayTypeEnum());
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	public void doFilter(Object object) {
		Balance balance = (Balance) object;
		if (this.id == null) {
			balance.id = null;
		}
		if (this.orderno == null) {
			balance.orderno = null;
		}
		if (this.state == null) {
			balance.state = null;
		}
		if (this.money == null) {
			balance.money = null;
		}
		if (this.time == null) {
			balance.time = null;
		}
		if (this.userid == null) {
			balance.userid = null;
		}
		if (this.payType == null) {
			balance.payType = null;
		}
	}
}
