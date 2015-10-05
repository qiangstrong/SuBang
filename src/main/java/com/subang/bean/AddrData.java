package com.subang.bean;

import java.util.List;

import com.subang.domain.City;
import com.subang.domain.District;
import com.subang.domain.Region;

public class AddrData {
	private List<City> citys;
	private List<District> districts;
	private List<Region> regions;
	private Integer defaultCityid;
	private Integer defaultDistrictid;
	private Integer defaultRegionid;
	private String detail;

	public AddrData() {
	}

	public AddrData(List<City> citys, List<District> districts, List<Region> regions,
			Integer defaultCityid, Integer defaultDistrictid, Integer defaultRegionid, String detail) {
		this.citys = citys;
		this.districts = districts;
		this.regions = regions;
		this.defaultCityid = defaultCityid;
		this.defaultDistrictid = defaultDistrictid;
		this.defaultRegionid = defaultRegionid;
		this.detail = detail;
	}

	public List<City> getCitys() {
		return citys;
	}

	public void setCitys(List<City> citys) {
		this.citys = citys;
	}

	public List<District> getDistricts() {
		return districts;
	}

	public void setDistricts(List<District> districts) {
		this.districts = districts;
	}

	public List<Region> getRegions() {
		return regions;
	}

	public void setRegions(List<Region> regions) {
		this.regions = regions;
	}

	public Integer getDefaultCityid() {
		return defaultCityid;
	}

	public void setDefaultCityid(Integer defaultCityid) {
		this.defaultCityid = defaultCityid;
	}

	public Integer getDefaultDistrictid() {
		return defaultDistrictid;
	}

	public void setDefaultDistrictid(Integer defaultDistrictid) {
		this.defaultDistrictid = defaultDistrictid;
	}

	public Integer getDefaultRegionid() {
		return defaultRegionid;
	}

	public void setDefaultRegionid(Integer defaultRegionid) {
		this.defaultRegionid = defaultRegionid;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public void doFilter() {
		City filterCity = new City(1, "", null, null);
		for (City city : citys) {
			filterCity.doFilter(city);
		}
		District filterDistrict = new District(1, "", null);
		for (District district : districts) {
			filterDistrict.doFilter(district);
		}
		Region filterRegion = new Region(1, "", null, null);
		for (Region region : regions) {
			filterRegion.doFilter(region);
		}
	}
}
