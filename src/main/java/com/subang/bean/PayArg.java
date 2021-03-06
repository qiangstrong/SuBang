package com.subang.bean;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import com.subang.domain.Payment.PayType;
import com.subang.domain.User.Client;
import com.subang.util.ComUtil;

public class PayArg {

	private Integer client; // 微信端，不传此参数；app端，传此参数；向service层传递时，需要传递此参数
	@NotNull
	private Integer payType;
	private Integer orderid;
	private Integer ticketid; // 若没有使用优惠券，不传参数即可。后台得到的为null
	@Digits(integer = 4, fraction = 1)
	private Double money; // 对于订单支付，不传此参数, 由于后台可以输入负值，其他非负的校验由客户端完成

	public PayArg() {
	}

	public PayArg(Integer client, Integer payType, Integer orderid, Integer ticketid, Double money) {
		super();
		this.client = client;
		this.payType = payType;
		this.orderid = orderid;
		this.ticketid = ticketid;
		this.money = money;
	}

	public Integer getClient() {
		return client;
	}

	public Client getClientEnum() {
		if (client == null) {
			return null;
		}
		return Client.values()[client];
	}

	public void setClient(Integer client) {
		this.client = client;
	}

	public void setClient(Client client) {
		this.client = client.ordinal();
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

	public void setPayType(PayType payType) {
		this.payType = payType.ordinal();
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
