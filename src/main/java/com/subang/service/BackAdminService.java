package com.subang.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.subang.bean.Area;
import com.subang.bean.SearchArg;
import com.subang.domain.Admin;
import com.subang.domain.City;
import com.subang.domain.District;
import com.subang.domain.Info;
import com.subang.domain.Laundry;
import com.subang.domain.Region;
import com.subang.domain.Worker;
import com.subang.exception.BackException;
import com.subang.util.WebConstant;
import com.sun.corba.se.spi.orbutil.threadpool.Work;

/**
 * @author Qiang 后台对管理员，工作人员，商家，区域和产品运营的管理
 */
@Service
public class BackAdminService extends BaseService {

	/**
	 * 与管理员相关的操作
	 */
	public Admin getAdminByMatch(Admin admin) {
		return adminDao.findByMatch(admin);
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
	public List<Worker> searchWorker(SearchArg searchArg) {
		List<Worker> workers = null;
		switch (searchArg.getType()) {
		case WebConstant.SEARCH_NULL:
			workers = new ArrayList<Worker>();
			break;
		case WebConstant.SEARCH_ALL:
			workers = workerDao.findAll();
			break;
		case WebConstant.SEARCH_NAME:
			workers = workerDao.findByName(searchArg.getArg());
			break;
		case WebConstant.SEARCH_CELLNUM:
			workers = workerDao.findByCellnum(searchArg.getArg());
			break;
		case WebConstant.SEARCH_AREA:
			workers = seachWorkerByArea(Area.toArea(searchArg.getArg()));
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

	public void addWorker(Worker worker) {
		workerDao.save(worker);
	}

	public Worker getWorker(Integer workerid) {
		return workerDao.get(workerid);
	}

	public void modifyWorker(Worker worker) {
		workerDao.update(worker);
	}

	public void deleteWorker(List<Integer> workerids) throws BackException {
		boolean isAll = true;
		for (Integer workerid : workerids) {
			try {
				workerDao.delete(workerid);
			} catch (DataIntegrityViolationException e) {
				isAll = false;
			}
		}
		if (!isAll) {
			throw new BackException("部分工作人员没有成功删除。请先删除工作人员负责的订单，再尝试删除工作人员。");
		}
	}

	/**
	 * 与商家相关的操作
	 */
	public List<Laundry> searchLaundry(SearchArg searchArg) {
		List<Laundry> laundrys = null;
		switch (searchArg.getType()) {
		case WebConstant.SEARCH_NULL:
			laundrys = new ArrayList<Laundry>();
			break;
		case WebConstant.SEARCH_ALL:
			laundrys = laundryDao.findAll();
			break;
		case WebConstant.SEARCH_NAME:
			laundrys = laundryDao.findByName(searchArg.getArg());
			break;
		case WebConstant.SEARCH_CELLNUM:
			laundrys = laundryDao.findByCellnum(searchArg.getArg());
			break;
		}
		return laundrys;
	}

	public void addLaundry(Laundry laundry) throws BackException {
		try {
			laundryDao.save(laundry);
		} catch (DuplicateKeyException e) {
			throw new BackException("商家名称不能相同。");
		}
	}

	public Laundry getLaundry(Integer laundryid) {
		return laundryDao.get(laundryid);
	}

	public void modifyLaundry(Laundry laundry) throws BackException {
		try {
			laundryDao.update(laundry);
		} catch (DuplicateKeyException e) {
			throw new BackException("商家名称不能相同。");
		}
	}

	public void deleteLaundry(List<Integer> laundryids) throws BackException {
		boolean isAll = true;
		for (Integer laundryid : laundryids) {
			try {
				laundryDao.delete(laundryid);
			} catch (DataIntegrityViolationException e) {
				isAll = false;
			}
		}
		if (!isAll) {
			throw new BackException("部分商家没有成功删除。请先删除商家负责的订单，再尝试删除商家。");
		}
	}

	/**
	 * 与区域相关的操作
	 */

	public List<Area> listAreaByWorkerid(Integer workerid) {
		return regionDao.findAreaByWorkerid(workerid);
	}

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

	public City getCity(Integer cityid) {
		return cityDao.get(cityid);
	}

	public void modifyCity(City city) throws BackException {
		try {
			cityDao.update(city);
		} catch (DuplicateKeyException e) {
			throw new BackException("城市名称不能相同。");
		}
	}

	public void deleteCity(List<Integer> cityids) throws BackException {
		boolean isAll = true;
		for (Integer cityid : cityids) {
			try {
				cityDao.delete(cityid);
			} catch (DataIntegrityViolationException e) {
				isAll = false;
			}
		}
		if (!isAll) {
			throw new BackException("部分城市没有成功删除。请先删除城市的所有订单，再尝试删除城市。");
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

	public District getDistrict(Integer districtid) {
		return districtDao.get(districtid);
	}

	public void modifyDistrict(District district) throws BackException {
		try {
			districtDao.update(district);
		} catch (DuplicateKeyException e) {
			throw new BackException("同一城市中区名称不能相同。");
		}
	}

	public void deleteDistrict(List<Integer> districtids) throws BackException {
		boolean isAll = true;
		for (Integer districtid : districtids) {
			try {
				districtDao.delete(districtid);
			} catch (DataIntegrityViolationException e) {
				isAll = false;
			}
		}
		if (!isAll) {
			throw new BackException("部分区没有成功删除。请先删除区的所有订单，再尝试删除区。");
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

	public Region getRegion(Integer regionid) {
		return regionDao.get(regionid);
	}

	public void modifyRegion(Region region) throws BackException {
		try {
			regionDao.update(region);
		} catch (DuplicateKeyException e) {
			throw new BackException("同一区中小区名称不能相同。");
		}
	}

	public void deleteRegion(List<Integer> regionids) throws BackException {
		boolean isAll = true;
		for (Integer regionid : regionids) {
			try {
				regionDao.delete(regionid);
			} catch (DataIntegrityViolationException e) {
				isAll = false;
			}
		}
		if (!isAll) {
			throw new BackException("部分小区没有成功删除。请先删除小区的所有订单，再尝试删除小区。");
		}
	}

	/**
	 * 与产品运营相关的操作
	 */
	public List<Info> listInfo() {
		return infoDao.findALL();
	}

	public Info getInfo(Integer infoid){
		return infoDao.get(infoid);
	}
	
	public void modifyInfo(Info info) {
		infoDao.update(info);
	}
}
