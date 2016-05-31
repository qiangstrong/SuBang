package com.subang.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import com.subang.domain.face.Filter;
import com.subang.util.ComUtil;

public class Payment implements Filter, Serializable {

	private static final long serialVersionUID = 1L;

	public enum PayType {
		balance, weixin, alipay, cash, expense, rebate, share, promote, score;

		public static PayType toPayType(String arg) {
			return PayType.values()[new Integer(arg)];
		}

		public static String toPayTypeDes(PayType payType) {
			if (payType == null) {
				return "未支付";
			}
			String description = null;
			switch (payType) {
			case balance: // 用于订单付款，商城订单。使用用户余额为订单支付
				description = "余额";
				break;
			case weixin: // 用于订单付款，用户余额
				description = "微信";
				break;
			case alipay: // 用于订单付款，用户余额
				description = "支付宝";
				break;
			case cash: // 用于订单付款，用户余额。 在管理端由管理员代为充值的，记录为cash
				description = "现金";
				break;
			case expense: // 用于用户余额。用户余额的支出，例如用余额为订单付款、为商城订单付款。
				description = "支出";
				break;
			case rebate: // 用于用户余额。比如充值1000，返现500。其中500记录为rebate
				description = "充值返现";
				break;
			case share: // 用于用户余额。比如用户下单后分享到朋友圈，返现
				description = "分享返现";
				break;
			case promote:// 用于用户收益。推广的人员下单并支付时的提成
				description = "推广收益";
				break;
			case score:// 用于商城订单。使用积分兑换商城商品
				description = "积分";
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
