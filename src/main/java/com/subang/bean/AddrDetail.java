package com.subang.bean;

import com.subang.domain.Addr;

public class AddrDetail extends Addr {
	
	private static final long serialVersionUID = 1L;
	
	private String cityname;
	private String districtname;
	private String regionname;
	
	public AddrDetail() {		
	}

	public AddrDetail(Integer id, boolean valid, String name, String cellnum, String detail,
			Integer userid, Integer regionid, String cityname, String districtname,
			String regionname) {
		super(id, valid, name, cellnum, detail, userid, regionid);
		this.cityname = cityname;
		this.districtname = districtname;
		this.regionname = regionname;
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
	
	public String toString(){
		StringBuffer description = new StringBuffer();
		description.append(name+" ");
		description.append(cellnum +" ");
		description.append(cityname+" ");
		description.append(districtname+" ");
		description.append(regionname+" ");
		description.append(detail);
		return description.toString();
	}

}
