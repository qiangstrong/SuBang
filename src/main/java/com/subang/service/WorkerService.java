package com.subang.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.subang.bean.Area;
import com.subang.bean.SearchArg;
import com.subang.domain.Notice.Code;
import com.subang.domain.Order;
import com.subang.domain.Region;
import com.subang.domain.Worker;
import com.subang.exception.SuException;
import com.subang.util.SuUtil;
import com.subang.util.WebConst;

@Service
public class WorkerService extends BaseService {
	public List<Worker> searchWorker(SearchArg searchArg) {
		List<Worker> workers = null;
		switch (searchArg.getType()) {
		case WebConst.SEARCH_NULL:
			workers = new ArrayList<Worker>();
			break;
		case WebConst.SEARCH_ALL:
			workers = workerDao.findValidAll();
			break;
		case WebConst.SEARCH_NAME:
			workers = workerDao.findByName(searchArg.getArg());
			break;
		case WebConst.SEARCH_CELLNUM:
			workers = workerDao.findByCellnum(searchArg.getArg());
			break;
		case WebConst.SEARCH_AREA:
			workers = seachWorkerByArea(Area.toArea(searchArg.getArg()));
			break;
		default:
			workers = new ArrayList<Worker>();
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

	// 检查手机号码是否被注册过
	public boolean checkCellnum(String cellnum) {
		int count = workerDao.countCellnum(cellnum);
		if (count == 0) {
			return true;
		}
		return false;
	}

	public void addWorker(Worker worker) throws SuException {
		try {
			workerDao.save(worker);
		} catch (DuplicateKeyException e) {
			throw new SuException("手机号码不能相同。");
		}
	}

	public void modifyWorker(Worker worker) throws SuException {
		try {
			workerDao.update(worker);
		} catch (DuplicateKeyException e) {
			throw new SuException("手机号码不能相同。");
		}
	}

	public void deleteWorkers(List<Integer> workerids) throws SuException {
		boolean isAll = true;
		for (Integer workerid : workerids) {
			if (!deleteWorker(workerid)) {
				isAll = false;
			}
		}
		if (!isAll) {
			throw new SuException("部分工作人员没有成功删除。可能是这些工作人员负责的订单处于未完成的状态。");
		}
	}

	private boolean deleteWorker(Integer workerid) {
		List<Order> orders = orderDao.findByWorkerid(workerid);
		if (orders.isEmpty()) {
			workerDao.delete(workerid);
			return true;
		}
		boolean isAllDone = true;
		Order order = null;
		for (int i = 0; i < orders.size() && isAllDone; i++) {
			order = orders.get(i);
			if (!order.isDone()) {
				isAllDone = false;
			}
		}
		if (!isAllDone) {
			return false;
		}

		Worker worker = workerDao.get(workerid);
		worker.setValid(false);
		workerDao.update(worker);

		List<Region> regions = regionDao.findByWorkerid(workerid);
		for (Region region : regions) {
			region.setWorkerid(null);
			regionDao.update(region);
		}
		return true;
	}

	/**
	 * 数据检查
	 */
	public void check() {
		List<Worker> workers = workerDao.findByCore();
		if (workers.isEmpty()) {
			SuUtil.notice(Code.incomplete, "系统中没有核心取衣员");
		}
	}
}
