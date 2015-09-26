package com.subang.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import com.subang.domain.Order.State;

public class History implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private Integer operation;
	private Timestamp time; // 操作发生的日期和时间
	private Integer orderid;

	public History() {
	}

	public History(Integer id, Integer operation, Timestamp time, Integer orderid) {
		this.id = id;
		this.operation = operation;
		this.time = time;
		this.orderid = orderid;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOperation() {
		return operation;
	}

	public State getOperationEnum() {
		return State.values()[operation];
	}

	public String getOperationDes() {
		String description = null;
		switch (getOperationEnum()) {
		case accepted:
			description = "下单";
			break;
		case priced:
			description = "计价";
			break;
		case paid:
			description = "支付";
			break;
		case fetched:
			description = "取走";
			break;
		case checked:
			description = "分拣";
			break;
		case delivered:
			description = "送达";
			break;
		case remarked:
			description = "评价";
			break;
		case canceled:
			description = "取消";
			break;	
		}
		return description;
	}

	public void setOperation(int operation) {
		this.operation = operation;
	}

	public void setOperation(State operation) {
		this.operation = operation.ordinal();
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

}
