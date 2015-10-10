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
import com.subang.domain.Notice.CodeType;
import com.subang.domain.Region;
import com.subang.exception.SuException;
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
			throw new SuException("部分城市没有成功删除。请先删除城市的所有订单，再尝试删除城市。");
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
			throw new SuException("部分区没有成功删除。请先删除区的所有订单，再尝试删除区。");
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
			throw new SuException("部分小区没有成功删除。请先删除小区的所有订单，再尝试删除小区。");
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

	public void deleteServices(List<Integer> serviceids) throws SuException {
		for (Integer serviceid : serviceids) {
			serviceDao.delete(serviceid);
		}
	}

	/**
	 * 前台关于区域的查询
	 */
	public AddrData getAddrDataByUserid(Integer userid) {
		AddrData addrData = new AddrData();

		List<Location> locations = locationDao.findByUserid(userid);
		Location location = ComUtil.getFirst(locations);
		GeoLoc geoLoc = LocUtil.getGeoLoc(location);

		addrData.setCitys(cityDao.findAllValid());
		addrData.setDefaultRegionid(null);

		if (geoLoc == null) {
			addrData.setDefaultCityid(null);
			addrData.setDistricts(districtDao.findValidByCityid(addrData.getCitys().get(0).getId()));
			addrData.setDefaultDistrictid(null);
			addrData.setRegions(regionDao.findByDistrictid(addrData.getDistricts().get(0).getId()));
			addrData.setDetail(null);
			return addrData;
		}

		City defaultCity = ComUtil.getFirst(cityDao.findValidByName(geoLoc.getCity()));
		if (defaultCity == null) {
			addrData.setDefaultCityid(null);
			addrData.setDistricts(districtDao.findValidByCityid(addrData.getCitys().get(0).getId()));
			addrData.setDefaultDistrictid(null);
			addrData.setRegions(regionDao.findByDistrictid(addrData.getDistricts().get(0).getId()));
			addrData.setDetail(geoLoc.getDetail());
			return addrData;
		}

		addrData.setDefaultCityid(defaultCity.getId());
		addrData.setDistricts(districtDao.findValidByCityid(addrData.getDefaultCityid()));
		District defaultDistrict = ComUtil.getFirst(districtDao.findValidByCityidAndName(
				addrData.getDefaultCityid(), geoLoc.getDistrict()));
		if (defaultDistrict == null) {
			addrData.setDefaultDistrictid(null);
			addrData.setRegions(regionDao.findByDistrictid(addrData.getDistricts().get(0).getId()));
			addrData.setDetail(geoLoc.getDetail());
			return addrData;
		}

		addrData.setDefaultDistrictid(defaultDistrict.getId());
		addrData.setRegions(regionDao.findByDistrictid(addrData.getDefaultDistrictid()));
		addrData.setDetail(geoLoc.getDetail());
		return addrData;
	}

	public AddrData getAddrDataByRegionid(Integer regionid) {
		AddrData addrData = new AddrData();
		Area area = regionDao.getArea(regionid);
		addrData.setDefaultCityid(area.getCityid());
		addrData.setDefaultDistrictid(area.getDistrictid());
		addrData.setDefaultRegionid(area.getRegionid());
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

	public Integer getCityid(Location location) {
		City city = null;
		if (location != null) {
			GeoLoc geoLoc = LocUtil.getGeoLoc(location);
			if (geoLoc != null) {
				city = ComUtil.getFirst(cityDao.findByName(geoLoc.getCity()));
			}
			if (city != null) {
				return city.getId();
			}
			if (location.getCityid() != null) {
				return location.getCityid();
			}
		}
		city = ComUtil.getFirst(cityDao.findByName(SuUtil.getSuProperty("defaultCityname")));
		return city.getId();
	}

	public Integer getCityid(Integer userid) {
		Location location = ComUtil.getFirst(locationDao.findByUserid(userid));
		return getCityid(location);
	}

	/**
	 * 数据检查
	 */
	public void check() {
		List<Area> areas = regionDao.findIncomplete();
		if (!areas.isEmpty()) {
			SuUtil.notice(CodeType.incomplete, "存在区域没有分配工作人员。");
		}
	}
}
