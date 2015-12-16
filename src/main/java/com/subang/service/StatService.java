package com.subang.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.subang.bean.StatArg;
import com.subang.bean.StatItem;
import com.subang.util.WebConst;

@Service
public class StatService extends BaseService {

	public List<StatItem> stat(StatArg statArg) {
		List<StatItem> statItems = null;
		statArg.pre();
		switch (statArg.getType0()) {
		case WebConst.STAT_NULL:
			statItems = new ArrayList<StatItem>();
			break;
		case WebConst.STAT_AREA:
		case WebConst.STAT_USER:
			statItems = statDao.find(statArg);
			break;
		default:
			statItems = new ArrayList<StatItem>();
			break;
		}
		preproccess(statItems);
		statArg.after();
		return statItems;
	}

	private void preproccess(List<StatItem> statItems) {
		for (StatItem statItem : statItems) {
			if (statItem.getQuantity() == null) {
				statItem.setQuantity(0);
			}
		}
	}
}
