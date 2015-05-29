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
		case WebConstant.STAT_USER:
			statItems=statDao.findByStatArg(statArg);
			break;
		}	
		preproccess(statItems);
		return statItems;
	}
	
	private void preproccess(List<StatItem> statItems){
		for (StatItem statItem : statItems) {
			if (statItem.getQuantity()==null) {
				statItem.setQuantity(0);
			}
		}
	}
}
