package com.subang.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.subang.bean.SearchArg;
import com.subang.domain.Laundry;
import com.subang.tool.SuException;
import com.subang.util.WebConst;

@Service
public class LaundryService extends BaseService  {
	public List<Laundry> searchLaundry(SearchArg searchArg) {
		List<Laundry> laundrys = null;
		switch (searchArg.getType()) {
		case WebConst.SEARCH_NULL:
			laundrys = new ArrayList<Laundry>();
			break;
		case WebConst.SEARCH_ALL:
			laundrys = laundryDao.findAll();
			break;
		case WebConst.SEARCH_NAME:
			laundrys = laundryDao.findByName(searchArg.getArg());
			break;
		case WebConst.SEARCH_CELLNUM:
			laundrys = laundryDao.findByCellnum(searchArg.getArg());
			break;
		default:
			laundrys = new ArrayList<Laundry>();
		}
		return laundrys;
	}

	public void addLaundry(Laundry laundry) throws SuException {
		try {
			laundryDao.save(laundry);
		} catch (DuplicateKeyException e) {
			throw new SuException("商家名称不能相同。");
		}
	}

	public void modifyLaundry(Laundry laundry) throws SuException {
		try {
			laundryDao.update(laundry);
		} catch (DuplicateKeyException e) {
			throw new SuException("商家名称不能相同。");
		}
	}

	public void deleteLaundrys(List<Integer> laundryids) throws SuException {
		boolean isAll = true;
		for (Integer laundryid : laundryids) {
			try {
				laundryDao.delete(laundryid);
			} catch (DataIntegrityViolationException e) {
				isAll = false;
			}
		}
		if (!isAll) {
			throw new SuException("部分商家没有成功删除。请先删除商家负责的订单，再尝试删除商家。");
		}
	}
}
