package com.subang.bean;

import java.sql.Timestamp;

import com.subang.domain.Payment.PayType;
import com.subang.domain.Record;
import com.subang.domain.face.Filter;

public class RecordDetail extends Record implements Filter {

	private static final long serialVersionUID = 1L;

	private String name;
	private String icon;
	private Double money;
	private Integer score;
	private Integer payType;

	public RecordDetail() {
	}

	public RecordDetail(Integer id, String orderno, Integer state, Timestamp time, Integer goodsid,
			Integer userid, Integer addrid, Integer workerid, String name, String icon,
			Double money, Integer score, Integer payType) {
		super(id, orderno, state, time, goodsid, userid, addrid, workerid);
		this.name = name;
		this.icon = icon;
		this.money = money;
		this.score = score;
		this.payType = payType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
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

	public void setPayType(PayType payType) {
		this.payType = payType.ordinal();
	}

	public String getPaymentDes() {
		PayType payType = getPayTypeEnum();
		if (payType == null) {
			return null;
		}
		if (payType == PayType.balance) {
			if (money == null) {
				return null;
			}
			return "金额：" + money.toString();
		} else {
			if (score == null) {
				return null;
			}
			return "积分：" + score.toString();
		}
	}

	public void doFilter(Object object) {
		super.doFilter(object);
		RecordDetail recordDetail = (RecordDetail) object;
		if (this.name == null) {
			recordDetail.name = null;
		}
		if (this.icon == null) {
			recordDetail.icon = null;
		}
		if (this.money == null) {
			recordDetail.money = null;
		}
		if (this.score == null) {
			recordDetail.score = null;
		}
		if (this.payType == null) {
			recordDetail.payType = null;
		}
	}

}
