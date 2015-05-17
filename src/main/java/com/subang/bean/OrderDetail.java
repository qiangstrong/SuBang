package com.subang.bean;

import java.sql.Date;

import com.subang.domain.Order;

public class OrderDetail extends Order {

	private String workername;
	private String workercellnum;
	private String addrname;
	private String addrcellnum;
	private String cityname;
	private String districtname;
	private String regionname;
	private String addrdetail;
	
	public OrderDetail() {
	}

	public OrderDetail(Integer id, String orderno, int category, int state, Float price, Date date,
			int time, String comment, Integer userid, Integer addrid, Integer workerid,
			Integer laundryid, String workername, String workercellnum, String addrname,
			String addrcellnum, String cityname, String districtname, String regionname,
			String addrdetail) {
		super(id, orderno, category, state, price, date, time, comment, userid, addrid, workerid,
				laundryid);
		this.workername = workername;
		this.workercellnum = workercellnum;
		this.addrname = addrname;
		this.addrcellnum = addrcellnum;
		this.cityname = cityname;
		this.districtname = districtname;
		this.regionname = regionname;
		this.addrdetail = addrdetail;
	}

	public String getWorkername() {
		return workername;
	}

	public void setWorkername(String workername) {
		this.workername = workername;
	}

	public String getWorkercellnum() {
		return workercellnum;
	}

	public void setWorkercellnum(String workercellnum) {
		this.workercellnum = workercellnum;
	}

	public String getAddrname() {
		return addrname;
	}

	public void setAddrname(String addrname) {
		this.addrname = addrname;
	}

	public String getAddrcellnum() {
		return addrcellnum;
	}

	public void setAddrcellnum(String addrcellnum) {
		this.addrcellnum = addrcellnum;
	}

	public String getCityname() {
		return cityname;
	}

	public void setCityname(String cityname) {
		this.cityname = cityname;
	}

	public String getDistrictname() {
		return districtname;
	}

	public void setDistrictname(String districtname) {
		this.districtname = districtname;
	}

	public String getRegionname() {
		return regionname;
	}

	public void setRegionname(String regionname) {
		this.regionname = regionname;
	}

	public String getAddrdetail() {
		return addrdetail;
	}

	public void setAddrdetail(String addrdetail) {
		this.addrdetail = addrdetail;
	}
		
}
