package com.subang.service;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.subang.bean.AddrData;
import com.subang.bean.Area;
import com.subang.bean.GeoLoc;
import com.subang.domain.Category;
import com.subang.domain.City;
import com.subang.domain.District;
import com.subang.domain.Location;
import com.subang.domain.Notice.Code;
import com.subang.domain.Region;
import com.subang.tool.SuException;
import com.subang.util.ComUtil;
import com.subang.util.LocUtil;
import com.subang.util.SuUtil;

@Service
public class RegionService extends BaseService {
	/**
	 * 与区域相关的操作
	 */
	public void addCity(City city, MultipartFile scope) throws SuException {
		if (scope.isEmpty()) {
			throw new SuException("未选择服务范围文件。");
		}
		try {
			city.calcScope(scope.getOriginalFilename());
			SuUtil.saveMultipartFile(false, scope, city.getScope());
			cityDao.save(city);
		} catch (DuplicateKeyException e) {
			throw new SuException("城市名称不能相同。");
		}
	}

	public void modifyCity(City city, MultipartFile scope) throws SuException {
		try {
			if (!scope.isEmpty()) {
				city.calcScope(scope.getOriginalFilename());
				SuUtil.saveMultipartFile(true, scope, city.getScope());
			}
			cityDao.update(city);
		} catch (DuplicateKeyException e) {
			throw new SuException("城市名称不能相同。");
		}
	}

	public void deleteCitys(List<Integer> cityids) throws SuException {
		boolean isAll = true;
		for (Integer cityid : cityids) {
			try {
				SuUtil.deleteFile(cityDao.get(cityid).getScope());
				cityDao.delete(cityid);
			} catch (DataIntegrityViolationException e) {
				isAll = false;
			}
		}
		if (!isAll) {
			throw new SuException("部分城市没有成功删除。存在用户地址引用了该城市。");
		}
	}

	public void addDistrict(District district) throws SuException {
		try {
			districtDao.save(district);
		} catch (DuplicateKeyException e) {
			throw new SuException("同一城市中区名称不能相同。");
		}
	}

	public void modifyDistrict(District district) throws SuException {
		try {
			districtDao.update(district);
		} catch (DuplicateKeyException e) {
			throw new SuException("同一城市中区名称不能相同。");
		}
	}

	public void deleteDistricts(List<Integer> districtids) throws SuException {
		boolean isAll = true;
		for (Integer districtid : districtids) {
			try {
				districtDao.delete(districtid);
			} catch (DataIntegrityViolationException e) {
				isAll = false;
			}
		}
		if (!isAll) {
			throw new SuException("部分区没有成功删除。存在用户地址引用了该区。");
		}
	}

	public void addRegion(Region region) throws SuException {
		try {
			regionDao.save(region);
		} catch (DuplicateKeyException e) {
			throw new SuException("同一区中小区名称不能相同。");
		}
	}

	public void modifyRegion(Region region) throws SuException {
		try {
			regionDao.update(region);
		} catch (DuplicateKeyException e) {
			throw new SuException("同一区中小区名称不能相同。");
		}
	}

	public void deleteRegions(List<Integer> regionids) throws SuException {
		boolean isAll = true;
		for (Integer regionid : regionids) {
			try {
				regionDao.delete(regionid);
			} catch (DataIntegrityViolationException e) {
				isAll = false;
			}
		}
		if (!isAll) {
			throw new SuException("部分小区没有成功删除。存在用户地址引用了该小区");
		}
	}

	/**
	 * 服務
	 */
	public void addService(com.subang.domain.Service service) throws SuException {
		try {
			serviceDao.save(service);
		} catch (DuplicateKeyException e) {
			throw new SuException("重复添加。");
		}
	}

	public void modifyService(com.subang.domain.Service service) throws SuException {
		try {
			serviceDao.update(service);
		} catch (DuplicateKeyException e) {
			throw new SuException("重复服务。");
		}
	}

	public void deleteServices(List<Integer> serviceids) throws SuException {
		for (Integer serviceid : serviceids) {
			serviceDao.delete(serviceid);
		}
	}

	/**
	 * 前台关于区域的查询。addrData.detail可能为null，其余都不为null
	 */
	public AddrData getAddrDataByUserid(Integer userid) {
		AddrData addrData = new AddrData();

		List<Location> locations = locationDao.findByUserid(userid);
		Location location = ComUtil.getFirst(locations);
		GeoLoc geoLoc = LocUtil.getGeoLoc(location);

		City defaultCity = null;
		District defaultDistrict = null;
		Region defaultRegion = null;

		addrData.setCitys(cityDao.findAllValid());

		// 城市信息
		if (geoLoc != null) {
			defaultCity = ComUtil.getFirst(cityDao.findValidByName(geoLoc.getCity()));
		}
		if (defaultCity == null) {
			defaultCity = addrData.getCitys().get(0);
		}
		addrData.setDefaultCityid(defaultCity.getId());
		addrData.setDefaultCityname(defaultCity.getName());
		addrData.setDistricts(districtDao.findValidByCityid(defaultCity.getId()));

		// 区信息
		if (geoLoc != null) {
			defaultDistrict = ComUtil.getFirst(districtDao.findValidByCityidAndName(
					defaultCity.getId(), geoLoc.getDistrict()));
		}
		if (defaultDistrict == null) {
			defaultDistrict = addrData.getDistricts().get(0);
		}
		addrData.setDefaultDistrictid(defaultDistrict.getId());
		addrData.setDefaultDistrictname(defaultDistrict.getName());
		addrData.setRegions(regionDao.findByDistrictid(defaultDistrict.getId()));

		// 小区信息
		defaultRegion = addrData.getRegions().get(0);
		addrData.setDefaultRegionid(defaultRegion.getId());
		addrData.setDefaultRegionname(defaultRegion.getName());

		// 地址详情
		if (geoLoc != null) {
			addrData.setDetail(geoLoc.getDetail());
		} else {
			addrData.setDetail(null);
		}
		return addrData;
	}

	public AddrData getAddrDataByRegionid(Integer regionid) {
		AddrData addrData = new AddrData();
		Area area = regionDao.getArea(regionid);
		addrData.setDefaultCityid(area.getCityid());
		addrData.setDefaultCityname(area.getCityname());
		addrData.setDefaultDistrictid(area.getDistrictid());
		addrData.setDefaultDistrictname(area.getDistrictname());
		addrData.setDefaultRegionid(area.getRegionid());
		addrData.setDefaultRegionname(area.getRegionname());
		addrData.setCitys(cityDao.findAllValid());
		addrData.setDistricts(districtDao.findValidByCityid(area.getCityid()));
		addrData.setRegions(regionDao.findByDistrictid(area.getDistrictid()));
		return addrData;
	}

	// 前台，获取当前城市的服务,cityid不能为null
	public List<Category> listByCityid(Integer userid, Integer cityid) {
		List<Category> categorys = null;
		Location location = ComUtil.getFirst(locationDao.findByUserid(userid));
		if (location == null) {
			location = new Location();
			location.setUserid(userid);
			location.setCityid(cityid);
			locationDao.save(location);
		} else {
			location.setCityid(cityid);
			locationDao.update(location);
		}
		categorys = categoryDao.findByCityid(cityid);
		return categorys;
	}

	public Integer getCityid(Integer userid) {
		Location location = ComUtil.getFirst(locationDao.findByUserid(userid));
		if (location != null && location.getCityid() != null) {
			return location.getCityid();
		}
		City city = ComUtil.getFirst(cityDao.findByName(SuUtil.getSuProperty("defaultCityname")));
		return city.getId();
	}

	/**
	 * 数据检查
	 */
	public void check() {
		List<Area> areas = regionDao.findIncomplete();
		if (!areas.isEmpty()) {
			SuUtil.notice(Code.incomplete, "存在区域没有分配工作人员。");
		}
	}
}
