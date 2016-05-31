package com.subang.bean;

import javax.validation.constraints.Min;

import com.subang.util.WebConst;

public class Pagination {

	private Integer type;
	@Min(1)
	private Integer pageno; // 页号，从1开始编号
	private Integer pagenum; // 页数
	private Integer recordnum; // 记录数

	public Pagination() {
		type = WebConst.CUR;
		pageno = 1;
		pagenum = 1;
		recordnum = 0;
	}

	public Pagination(Integer type, Integer pageno, Integer pagenum, Integer recordnum) {
		this.type = type;
		this.pageno = pageno;
		this.pagenum = pagenum;
		this.recordnum = recordnum;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getPageno() {
		return pageno;
	}

	public void setPageno(Integer pageno) {
		this.pageno = pageno;
	}

	public Integer getPagenum() {
		return pagenum;
	}

	public void setPagenum(Integer pagenum) {
		this.pagenum = pagenum;
	}

	public Integer getRecordnum() {
		return recordnum;
	}

	public void setRecordnum(Integer recordnum) {
		this.recordnum = recordnum;
		pagenum = (recordnum + WebConst.PAGE_SIZE - 1) / WebConst.PAGE_SIZE;
		if (pagenum == 0) {
			pagenum = 1;
		}
	}

	// 计算offset
	public Integer getOffset() {
		return (pageno - 1) * WebConst.PAGE_SIZE;
	}

	public void calc() {
		if (type == WebConst.PREV) {
			pageno--;
		} else if (type == WebConst.NEXT) {
			pageno++;
		}
	}

	// 确保recordnum有效后，再调用此函数
	public void round() {
		if (pageno < 1) {
			pageno = 1;
		} else if (pageno > pagenum) {
			pageno = pagenum;
		}
	}
}
