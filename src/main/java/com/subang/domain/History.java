package com.subang.domain;

import java.io.Serializable;
import java.sql.Timestamp;

public class History implements Serializable {

	private static final long serialVersionUID = 1L;

	public enum Operation {
		accept, // 用户下单，系统接收订单
		fetch, finish, cancel
	}

	private Integer id;
	private int operation;
	private Timestamp time; // 操作发生的日期和时间
	private Integer orderid;

	public History() {
	}

	public History(Integer id, int operation, Timestamp time,
			Integer orderid) {
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

	public int getOperation() {
		return operation;
	}
	
	public Operation getOperationEnum() {
		return Operation.values()[operation];
	}
	
	public String getOperationDes(){
		String description=null;
		switch (getOperationEnum()) {
		case accept:
			description="下单";
			break;
		case fetch:
			description="取走";
			break;
		case finish:
			description="结束";
			break;
		case cancel:
			description="取消";
			break;
		}
		return description;
	}
	
	public void setOperation(int operation) {
		this.operation = operation;
	}
	
	public void setOperation(Operation operation) {
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
