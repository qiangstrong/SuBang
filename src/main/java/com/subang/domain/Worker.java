package com.subang.domain;

import java.io.Serializable;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

public class Worker implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private boolean core; // 是否是核心取衣员
	@Length(min = 1, max = 4)
	private String name;
	@Pattern(regexp = "\\d{11}")
	private String cellnum;
	@Length(max = 50)
	private String comment; // 取衣员的备注

	public Worker() {
	}

	public Worker(Integer id, boolean core, String name, String cellnum, String comment) {
		this.id = id;
		this.setCore(core);
		this.name = name;
		this.cellnum = cellnum;
		this.comment = comment;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public boolean isCore() {
		return core;
	}

	public String getCoreDes(){
		if(core){
			return "是";
		}
		return "否";
	}
	
	public void setCore(boolean core) {
		this.core = core;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCellnum() {
		return cellnum;
	}

	public void setCellnum(String cellnum) {
		this.cellnum = cellnum;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
