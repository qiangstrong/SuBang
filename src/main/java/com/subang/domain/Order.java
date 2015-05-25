package com.subang.domain;

import java.io.Serializable;
import java.sql.Date;

public class Order implements Serializable {

	private static final long serialVersionUID = 1L;

	public enum Category {
		clothes, shoe
	}

	public enum State {
		accepted, fetched, finished, canceled;
		
		public static State toState(String arg){
			return State.values()[new Integer(arg)];
		}
	}

	protected Integer id;
	protected String orderno; // 订单号
	protected int category;
	protected int state;
	protected Float price;
	protected Date date; // 用户指定的取件日期
	protected int time; // 用户指定的取件时间，时间间隔为（time，time+1）
	protected String comment;
	protected Integer userid;
	protected Integer addrid;
	protected Integer workerid;
	protected Integer laundryid;

	public Order() {
	}

	public Order(Integer id, String orderno, int category, int state, Float price, Date date,
			int time, String comment, Integer userid, Integer addrid, Integer workerid,
			Integer laundryid) {
		this.id = id;
		this.orderno = orderno;
		this.category = category;
		this.state = state;
		this.price = price;
		this.date = date;
		this.time = time;
		this.comment = comment;
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

	public int getCategory() {
		return category;
	}

	public Category getCategoryEnum() {
		return Category.values()[category];
	}
	
	public String getCategoryDes(){
		String description=null;
		switch(getCategoryEnum()){
		case clothes:
			description="衣服";
			break;
		case shoe:
			description="鞋";
			break;
		}
		return description;
	}
	
	public void setCategory(int category) {
		this.category = category;
	}

	public void setCategory(Category category) {
		this.category = category.ordinal();
	}
	
	public int getState() {
		return state;
	}

	public State getStateEnum() {
		return State.values()[state];
	}
	
	public String getStateDes(){
		String description=null;
		switch (getStateEnum()) {
		case accepted:
			description="已接受";
			break;
		case fetched:
			description="已取走";
			break;
		case finished:
			description="已完成";
			break;
		case canceled:
			description="已取消";
			break;
		}
		return description;
	}
	
	public void setState(int state) {
		this.state = state;
	}

	public void setState(State state) {
		this.state = state.ordinal();
	}
	
	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getTime() {
		return time;
	}

	public String getTimeDes(){
		String description="";
		description=time+":00-"+(time+1)+":00";
		return description;
	}
	
	public void setTime(int time) {
		this.time = time;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
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

	

}
