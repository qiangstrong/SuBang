package com.subang.bean;

import com.baidu.map.bean.RenderReverseResult;

public class GeoLoc {

	public static GeoLoc toGeoLoc(RenderReverseResult result) {
		GeoLoc geoLoc = new GeoLoc();
		geoLoc.setCity(result.getResult().getAddressComponent().getCity());
		geoLoc.setDistrict(result.getResult().getAddressComponent().getDistrict());
		geoLoc.setDetail(result.getResult().getFormatted_address());
		return geoLoc;
	}

	private String city;
	private String district;
	private String detail;

	public GeoLoc() {
	}

	public GeoLoc(String city, String district, String detail) {
		this.city = city;
		this.district = district;
		this.detail = detail;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

}
