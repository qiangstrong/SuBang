package com.subang.bean;

import javax.validation.constraints.Digits;

import com.subang.domain.Payment.PayType;
import com.subang.util.ComUtil;

public class PayArg {
	private Integer payType;
	private Integer orderid;
	private Integer ticketid; // 若没有使用优惠券，不传参数即可。后台得到的为null
	@Digits(integer = 3, fraction = 1)
	private Double money; // 对于订单支付，不传此参数

	public PayArg() {
	}

	public PayArg(Integer payType, Integer orderid, Integer ticketid, Double money) {
		super();
		this.payType = payType;
		this.orderid = orderid;
		this.ticketid = ticketid;
		this.money = money;
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

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	public Integer getOrderid() {
		return orderid;
	}

	public void setOrderid(Integer orderid) {
		this.orderid = orderid;
	}

	public Integer getTicketid() {
		return ticketid;
	}

	public void setTicketid(Integer ticketid) {
		this.ticketid = ticketid;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = ComUtil.round(money);
	}

}
