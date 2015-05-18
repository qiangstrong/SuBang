package com.subang.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.subang.bean.Area;
import com.subang.dao.InfoDao;
import com.subang.domain.Admin;
import com.subang.domain.City;
import com.subang.domain.District;
import com.subang.domain.Info;
import com.subang.domain.Laundry;
import com.subang.domain.Region;
import com.subang.domain.Worker;
import com.subang.exception.BackException;
import com.subang.utility.WebConstant;

/**
 * @author Qiang 
 * 后台对管理员，工作人员，商家，区域和产品运营的管理
 */
@Service
public class BackAdminService extends BaseService {

	/**
	 * 与管理员相关的操作
	 */
	public Admin getMatchAdmin(Admin admin) {
		return adminDao.findMatch(admin);
	}

	public void modifyAdmin(Admin admin) throws BackException {
		try {
			adminDao.update(admin);
		} catch (DuplicateKeyException e) {
			throw new BackException("用户名不能相同。");
		}
	}

	/**
	 * 与工作人员相关的操作
	 */
	public List<Worker> searchWorker(int type, Object arg) {
		List<Worker> workers = null;
		switch (type) {
		case WebConstant.SEARCH_NAME:
			workers = workerDao.findByName((String) arg);
			break;
		case WebConstant.SEARCH_CELLNUM:
			workers = workerDao.findByCellnum((String) arg);
			break;
		case WebConstant.SEARCH_AREA:
			workers = seachWorkerByArea((Area) arg);
			break;
		}
		return workers;
	}

	private List<Worker> seachWorkerByArea(Area area) {
		List<Worker> workers = new ArrayList<Worker>();
		List<Area> areas = regionDao.findAreaByArea(area);
		for (Area tempArea : areas) {
			if (tempArea.getWorkerid() != null) {
				workers.add(workerDao.get(tempArea.getWorkerid()));
			}
		}
		return workers;
	}

	public List<Worker> listWorker() {
		return workerDao.findAll();
	}

	public void addWorker(Worker worker) {
		workerDao.save(worker);
	}

	public void modifyWorker(Worker worker) {
		workerDao.update(worker);
	}

	public void deleteWorker(Integer workerid) throws BackException {
		try {
			workerDao.delete(workerid);
		} catch (DataIntegrityViolationException e) {
			throw new BackException("请先删除此工作人员负责的订单，再尝试删除此工作人员。");
		}
	}

	/**
	 * 与商家相关的操作
	 */
	public List<Laundry> searchLaundry(int type, Object arg) {
		List<Laundry> laundrys = null;
		switch (type) {
		case WebConstant.SEARCH_NAME:
			laundrys = laundryDao.findByName((String) arg);
			break;
		case WebConstant.SEARCH_CELLNUM:
			laundrys = laundryDao.findByCellnum((String) arg);
			break;
		}
		return laundrys;
	}

	public List<Laundry> listLaundry() {
		return laundryDao.findAll();
	}

	public void addLaundry(Laundry laundry) throws BackException {
		try {
			laundryDao.save(laundry);
		} catch (DuplicateKeyException e) {
			throw new BackException("商家名称不能相同。");
		}
	}

	public void modifyLaundry(Laundry laundry) throws BackException {
		try {
			laundryDao.update(laundry);
		} catch (DuplicateKeyException e) {
			throw new BackException("商家名称不能相同。");
		}
	}

	public void deleteLaundry(Integer laundryid) throws BackException {
		try {
			laundryDao.delete(laundryid);
		} catch (DataIntegrityViolationException e) {
			throw new BackException("请先删除此商家负责的订单，再尝试删除此商家。");
		}
	}

	/**
	 * 与区域相关的操作
	 */
	public List<City> listCity() {
		return cityDao.findAll();
	}

	public void addCity(City city) throws BackException {
		try {
			cityDao.save(city);
		} catch (DuplicateKeyException e) {
			throw new BackException("城市名称不能相同。");
		}
	}

	public void modifyCity(City city) throws BackException {
		try {
			cityDao.update(city);
		} catch (DuplicateKeyException e) {
			throw new BackException("城市名称不能相同。");
		}
	}

	public void deleteCity(Integer cityid) throws BackException {
		try {
			cityDao.delete(cityid);
		} catch (DataIntegrityViolationException e) {
			throw new BackException("请先删除此城市的所有订单，再尝试删除此城市。");
		}
	}

	public List<District> listDistrictByCityid(Integer cityid) {
		return districtDao.findByCityid(cityid);
	}

	public void addDistrict(District district) throws BackException {	
		try {
			districtDao.save(district);
		} catch (DuplicateKeyException e) {
			throw new BackException("同一城市中区名称不能相同。");
		}
	}

	public void modifyDistrict(District district) throws BackException {		
		try {
			districtDao.update(district);
		} catch (DuplicateKeyException e) {
			throw new BackException("同一城市中区名称不能相同。");
		}
	}

	public void deleteDistrict(Integer districtid) throws BackException {
		try {
			districtDao.delete(districtid);
		} catch (DataIntegrityViolationException e) {
			throw new BackException("请先删除此区的所有订单，再尝试删除此区。");
		}
	}
	
	public List<Region> listRegionByDistrictid(Integer districtid) {
		return regionDao.findByDistrictid(districtid);
	}

	public void addRegion(Region region) throws BackException {	
		try {
			regionDao.save(region);
		} catch (DuplicateKeyException e) {
			throw new BackException("同一区中小区名称不能相同。");
		}
	}

	public void modifyRegion(Region region) throws BackException {		
		try {
			regionDao.update(region);
		} catch (DuplicateKeyException e) {
			throw new BackException("同一区中小区名称不能相同。");
		}
	}

	public void deleteRegion(Integer regionid) throws BackException {
		try {
			regionDao.delete(regionid);
		} catch (DataIntegrityViolationException e) {
			throw new BackException("请先删除此小区的所有订单，再尝试删除此小区。");
		}
	}

	/**
	 * 与产品运营相关的操作
	 */
	public Info listInfo() {
		return infoDao.find();
	}

	public void modifyInfo(Info info) {
		infoDao.update(info);
	}
}
