package com.subang.bean;


public class Area {

	public static Area toArea(String arg) {
		final int layernum=3;
		String[] names = new String[layernum];
		String[] subArgs = arg.split("\\.", -1);
		int i = 0;
		for (; i < subArgs.length&&i<layernum; i++) {
			names[i] = subArgs[i];
		}
		for (; i < layernum; i++) {
			names[i] = "";
		}
		Area area = new Area();
		area.setCityname(names[0]);
		area.setDistrictname(names[1]);
		area.setRegionname(names[2]);
		return area;
	}

	private String cityname;
	private String districtname;
	private String regionname;
	private Integer regionid;
	private Integer workerid;

	public Area() {
	}

	public Area(String cityname, String districtname, String regionname, Integer regionid,
			Integer workerid) {
		this.cityname = cityname;
		this.districtname = districtname;
		this.regionname = regionname;
		this.regionid = regionid;
		this.workerid = workerid;
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

	public Integer getRegionid() {
		return regionid;
	}

	public void setRegionid(Integer regionid) {
		this.regionid = regionid;
	}

	public Integer getWorkerid() {
		return workerid;
	}

	public void setWorkerid(Integer workerid) {
		this.workerid = workerid;
	}

}
