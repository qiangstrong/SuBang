package com.subang.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.subang.bean.StatItem;
import com.subang.utility.WebConstant;

@Service
public class BackStatService extends BaseService {

	public List<StatItem> statByArea(int statType, int areaType) {
		List<StatItem> statItems = null;
		switch (statType) {
		case WebConstant.AREA_STAT_ORDER:
			switch (areaType) {
			case WebConstant.AREA_REGION:
				statItems = orderDao.statOrderNumByRegion();
				break;
			case WebConstant.AREA_DISTRICT:
				statItems = orderDao.statOrderNumByDistrict();
				break;
			case WebConstant.AREA_CITY:
				statItems = orderDao.statOrderNumByCity();
				break;
			}
			break;
		case WebConstant.AREA_STAT_USER:
			switch (areaType) {
			case WebConstant.AREA_REGION:
				statItems = userDao.statUserNumByRegion();
				break;
			case WebConstant.AREA_DISTRICT:
				statItems = userDao.statUserNumByDistrict();
				break;
			case WebConstant.AREA_CITY:
				statItems = userDao.statUserNumByCity();
				break;
			}
			break;
		}

		return statItems;
	}

	public List<StatItem> statByUser(int statType) {
		List<StatItem> statItems = null;
		switch (statType) {
		case WebConstant.USER_STAT_ORDER:
			statItems = orderDao.statOrderNumByUser();
			break;

		case WebConstant.USER_STAT_PRICE:
			statItems = orderDao.statPriceAvgByUser();
			break;
		}
		return statItems;
	}
}
