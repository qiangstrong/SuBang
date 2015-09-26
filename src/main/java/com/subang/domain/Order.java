package com.subang.domain;

import java.io.Serializable;
import java.sql.Date;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.subang.util.ComUtil;
import com.subang.util.TimeUtil;

public class Order implements Serializable {

	private static final long serialVersionUID = 1L;

	public enum State {
		accepted, priced, paid, fetched, checked, delivered, remarked, canceled;

		public static State toState(String arg) {
			return State.values()[new Integer(arg)];
		}
	}

	protected Integer id;
	protected String orderno; // 订单号
	protected Integer state;
	@Digits(integer = 3, fraction = 1)
	protected Double money; // 单位元
	@Digits(integer = 3, fraction = 1)
	protected Double freight; // 单位元
	@NotNull
	protected Date date; // 用户指定的取件日期
	@NotNull
	protected Integer time; // 用户指定的取件时间，时间间隔为（time，time+1）
	@Length(max = 100)
	protected String userComment;
	@Length(max = 100)
	protected String workerComment;
	@Length(max = 100)
	protected String remark;
	protected String barcode;
	protected Integer categoryid;
	protected Integer userid;
	@NotNull
	protected Integer addrid;
	protected Integer workerid;
	protected Integer laundryid;

	public Order() {
	}

	public Order(Integer id, String orderno, Integer state, Double money, Double freight,
			Date date, Integer time, String userComment, String workerComment, String remark,
			String barcode, Integer categoryid, Integer userid, Integer addrid, Integer workerid,
			Integer laundryid) {
		this.id = id;
		this.orderno = orderno;
		this.state = state;
		this.money = money;
		this.freight = freight;
		this.date = date;
		this.time = time;
		this.userComment = userComment;
		this.workerComment = workerComment;
		this.remark = remark;
		this.barcode = barcode;
		this.categoryid = categoryid;
		this.userid = userid;
		this.addrid = addrid;
		this.workerid = workerid;
		this.laundryid = laundryid;
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
		return State.values()[state];
	}

	public String getStateDes() {
		String description = null;
		switch (getStateEnum()) {
		case accepted:
			description = "已接受";
			break;
		case priced:
			description = "已计价";
			break;
		case paid:
			description = "已支付";
			break;
		case fetched:
			description = "已取走";
			break;
		case checked:
			description = "已分拣";
			break;
		case delivered:
			description = "已送达";
			break;
		case remarked:
			description = "已评价";
			break;
		case canceled:
			description = "已取消";
			break;
		}
		return description;
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

	public String getMoneyDes() {
		return ComUtil.getDes(money);
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public Double getFreight() {
		return freight;
	}

	public String getFreightDes() {
		return ComUtil.getDes(freight);
	}

	public void setFreight(Double freight) {
		this.freight = freight;
	}

	public Date getDate() {
		return date;
	}

	public String getDateDes() {
		return TimeUtil.getDateDes(date);
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getTime() {
		return time;
	}

	public String getTimeDes() {
		return TimeUtil.getTimeDes(time);
	}

	public void setTime(Integer time) {
		this.time = time;
	}

	public String getUserComment() {
		return userComment;
	}

	public void setUserComment(String userComment) {
		this.userComment = userComment;
	}

	public String getWorkerComment() {
		return workerComment;
	}

	public void setWorkerComment(String workerComment) {
		this.workerComment = workerComment;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public Integer getCategoryid() {
		return categoryid;
	}

	public void setCategoryid(Integer categoryid) {
		this.categoryid = categoryid;
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

	public Integer getLaundryid() {
		return laundryid;
	}

	public void setLaundryid(Integer laundryid) {
		this.laundryid = laundryid;
	}

	public boolean isDone() {
		State stateEnum = getStateEnum();
		if (stateEnum == State.delivered || stateEnum == State.remarked
				|| stateEnum == State.canceled) {
			return true;
		}
		return false;
	}
}
