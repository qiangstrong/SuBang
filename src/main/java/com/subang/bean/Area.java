package com.subang.bean;

public class Area {

	private Integer cityid;
	private String cityname;
	private Integer districtid;
	private String districtname;
	private Integer regionid;
	private String regionname;
	private Integer workerid;

	public Area() {
	}

	public Area(Integer cityid, String cityname, Integer districtid, String districtname,
			Integer regionid, String regionname, Integer workerid) {
		this.cityid = cityid;
		this.cityname = cityname;
		this.districtid = districtid;
		this.districtname = districtname;
		this.regionid = regionid;
		this.regionname = regionname;
		this.workerid = workerid;
	}

	public Integer getCityid() {
		return cityid;
	}

	public void setCityid(Integer cityid) {
		this.cityid = cityid;
	}

	public String getCityname() {
		return cityname;
	}

	public void setCityname(String cityname) {
		this.cityname = cityname;
	}

	public Integer getDistrictid() {
		return districtid;
	}

	public void setDistrictid(Integer districtid) {
		this.districtid = districtid;
	}

	public String getDistrictname() {
		return districtname;
	}

	public void setDistrictname(String districtname) {
		this.districtname = districtname;
	}

	public Integer getRegionid() {
		return regionid;
	}

	public void setRegionid(Integer regionid) {
		this.regionid = regionid;
	}

	public String getRegionname() {
		return regionname;
	}

	public void setRegionname(String regionname) {
		this.regionname = regionname;
	}

	public Integer getWorkerid() {
		return workerid;
	}

	public void setWorkerid(Integer workerid) {
		this.workerid = workerid;
	}
}
