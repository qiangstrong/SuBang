package com.subang.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import com.subang.domain.face.Filter;

public class Payment implements Filter, Serializable {
	private static final long serialVersionUID = 1L;

	public enum PayType {
		balance, weixin, alipay;
		public static PayType toPayType(String arg) {
			return PayType.values()[new Integer(arg)];
		}
	}

	private Integer id;
	private Integer type;
	private Double moneyTicket;
	private String prepay_id; // 微信预支付id
	private Timestamp time;
	private Integer orderid;

	public Payment() {
		this.moneyTicket = 0.0;
	}

	public Payment(Integer id, Integer type, Double moneyTicket, String prepay_id, Timestamp time,
			Integer orderid) {
		this.id = id;
		this.type = type;
		this.moneyTicket = moneyTicket;
		this.prepay_id = prepay_id;
		this.time = time;
		this.orderid = orderid;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getType() {
		return type;
	}

	public PayType getTypeEnum() {
		if (type == null) {
			return null;
		}
		return PayType.values()[type];
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public void setType(PayType type) {
		this.type = type.ordinal();
	}

	public Double getMoneyTicket() {
		return moneyTicket;
	}

	public void setMoneyTicket(Double moneyTicket) {
		this.moneyTicket = moneyTicket;
	}

	public String getPrepay_id() {
		return prepay_id;
	}

	public void setPrepay_id(String prepay_id) {
		this.prepay_id = prepay_id;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public Integer getOrderid() {
		return orderid;
	}

	public void setOrderid(Integer orderid) {
		this.orderid = orderid;
	}

	public void doFilter(Object object) {
		Payment payment = (Payment) object;
		if (this.id == null) {
			payment.id = null;
		}
		if (this.type == null) {
			payment.type = null;
		}
		if (this.moneyTicket == null) {
			payment.moneyTicket = null;
		}
		if (this.prepay_id == null) {
			payment.prepay_id = null;
		}
		if (this.time == null) {
			payment.time = null;
		}
		if (this.orderid == null) {
			payment.orderid = null;
		}
	}

}
