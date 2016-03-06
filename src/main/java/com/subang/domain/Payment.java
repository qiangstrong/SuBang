package com.subang.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import com.subang.domain.face.Filter;
import com.subang.util.ComUtil;

public class Payment implements Filter, Serializable {

	private static final long serialVersionUID = 1L;

	/*
	 * 订单付款，有四种方式(balance, weixin, alipay, cash)；
	 * 如果由取衣员代为支付，记录为现金支付；后台支付，分为余额支付和现金支付。 用户充值，只有三种方式(weixin, alipay, cash)；
	 * 在管理端由管理员代为充值的，记录为cash。
	 * expense为支出类型。例如订单付款。使用余额付款时，需要生成balance订单，此时记录为expense类型。
	 */
	public enum PayType {
		balance, weixin, alipay, cash, expense;

		public static PayType toPayType(String arg) {
			return PayType.values()[new Integer(arg)];
		}

		public static String toPayTypeDes(PayType payType) {
			if (payType == null) {
				return "未支付";
			}
			String description = null;
			switch (payType) {
			case balance:
				description = "余额";
				break;
			case weixin:
				description = "微信";
				break;
			case alipay:
				description = "支付宝";
				break;
			case cash:
				description = "现金";
				break;
			case expense:
				description = "支出";
				break;
			}
			return description;
		}
	}

	private Integer id;
	private Integer type;
	private Double moneyTicket;
	private String prepay_id; // 微信预支付id
	private Timestamp time;
	private String orderno;

	public Payment() {
		this.moneyTicket = 0.0;
	}

	public Payment(Integer id, Integer type, Double moneyTicket, String prepay_id, Timestamp time,
			String orderno) {
		this.id = id;
		this.type = type;
		this.moneyTicket = moneyTicket;
		this.prepay_id = prepay_id;
		this.time = time;
		this.orderno = orderno;
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
		this.moneyTicket = ComUtil.round(moneyTicket);
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

	public String getOrderno() {
		return orderno;
	}

	public void setOrderno(String orderno) {
		this.orderno = orderno;
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
		if (this.orderno == null) {
			payment.orderno = null;
		}
	}

}
