package com.subang.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.subang.bean.StatArg;
import com.subang.bean.StatItem;
import com.subang.util.WebConstant;

@Service
public class BackStatService extends BaseService {
	
	public List<StatItem> stat(StatArg statArg){
		List<StatItem> statItems=null;
		switch (statArg.getType0()) {
		case WebConstant.STAT_NULL:
			statItems=new ArrayList<StatItem>();
			break;
		case WebConstant.STAT_AREA:
			statItems=statByArea(statArg.getType1(), statArg.getType2());
			break;
		case WebConstant.STAT_USER:
			statItems=statByUser(statArg.getType1());
			break;
		}	
		return statItems;
	}

	public List<StatItem> statByArea(int statType, int areaType) {
		List<StatItem> statItems = null;
		switch (statType) {
		case WebConstant.STAT_AREA_ORDER:
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
		case WebConstant.STAT_AREA_USER:
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
		case WebConstant.STAT_USER_ORDER:
			statItems = orderDao.statOrderNumByUser();
			break;

		case WebConstant.STAT_USER_PRICE:
			statItems = orderDao.statPriceAvgByUser();
			for (StatItem statItem : statItems) {
				if (statItem.getQuantity()==null) {
					statItem.setQuantity(0);
				}
			}
			break;
		}
		return statItems;
	}
}
