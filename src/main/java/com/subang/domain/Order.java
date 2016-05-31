package com.subang.domain;

import java.io.Serializable;
import java.sql.Date;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.subang.domain.face.Filter;
import com.subang.util.ComUtil;

public class Order implements Filter, Serializable {

	private static final long serialVersionUID = 1L;

	public enum OrderType {
		order, balance, record
	}

	public enum State {
		accepted, priced, paid, fetched, checked, delivered, remarked, canceled;

		public static State toState(String arg) {
			return State.values()[new Integer(arg)];
		}

		public static String toStateDes(State state) {
			String description = null;
			if (state == null) {
				return null;
			}
			switch (state) {
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
				description = "已洗衣";
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
	}

	protected Integer id;
	protected String orderno; // 订单号
	protected Integer state;
	@Min(0)
	@Digits(integer = 4, fraction = 1)
	protected Double money; // 单位元
	@Min(0)
	@Digits(integer = 4, fraction = 1)
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
	@NotNull
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
		if (state == null) {
			return null;
		}
		return State.values()[state];
	}

	public String getStateDes() {
		return State.toStateDes(getStateEnum());
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
		this.money = ComUtil.round(money);
	}

	public Double getFreight() {
		return freight;
	}

	public String getFreightDes() {
		return ComUtil.getDes(freight);
	}

	public void setFreight(Double freight) {
		this.freight = ComUtil.round(freight);
	}

	public Date getDate() {
		return date;
	}

	public String getDateDes() {
		return ComUtil.getDateDes(date);
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getTime() {
		return time;
	}

	public String getTimeDes() {
		return ComUtil.getTimeDes(time);
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

	public String getBarcodeDes() {
		return ComUtil.getDes(barcode);
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

	public Boolean isPaid() { // 由于要使用网络传输order，boolean类型是基本类型，无法为null
		if (state == null) {
			return null;
		}
		if (state >= State.paid.ordinal() && getStateEnum() != State.canceled) {
			return true;
		}
		return false;
	}

	public Boolean isChecked() {
		if (state == null) {
			return null;
		}
		if (state >= State.checked.ordinal() && getStateEnum() != State.canceled) {
			return true;
		}
		return false;
	}

	public Boolean isDone() {
		if (state == null) {
			return null;
		}
		State stateEnum = getStateEnum();
		if (stateEnum == State.delivered || stateEnum == State.remarked
				|| stateEnum == State.canceled) {
			return true;
		}
		return false;
	}

	public void doFilter(Object object) {
		Order order = (Order) object;
		if (this.id == null) {
			order.id = null;
		}
		if (this.orderno == null) {
			order.orderno = null;
		}
		if (this.state == null) {
			order.state = null;
		}
		if (this.money == null) {
			order.money = null;
		}
		if (this.freight == null) {
			order.freight = null;
		}
		if (this.date == null) {
			order.date = null;
		}
		if (this.time == null) {
			order.time = null;
		}
		if (this.userComment == null) {
			order.userComment = null;
		}
		if (this.workerComment == null) {
			order.workerComment = null;
		}
		if (this.remark == null) {
			order.remark = null;
		}
		if (this.barcode == null) {
			order.barcode = null;
		}
		if (this.categoryid == null) {
			order.categoryid = null;
		}
		if (this.userid == null) {
			order.userid = null;
		}
		if (this.addrid == null) {
			order.addrid = null;
		}
		if (this.workerid == null) {
			order.workerid = null;
		}
		if (this.laundryid == null) {
			order.laundryid = null;
		}
	}
}
