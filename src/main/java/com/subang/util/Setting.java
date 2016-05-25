package com.subang.util;

import com.subang.dao.InfoDao;
import com.subang.domain.Info;

/**
 * @author Qiang 缓存info表中的设置数据
 * 
 */
public class Setting extends BaseUtil {

	private static InfoDao infoDao = null;

	public static String phone;
	public static Double shareMoney;
	public static Double salaryLimit;
	public static Integer[] prom;

	public void setInfoDao(InfoDao infoDao) {
		Setting.infoDao = infoDao;
	}

	public static void init() {
		Info info = infoDao.findALL().get(0);
		prom = new Integer[3];
		phone = info.getPhone();
		shareMoney = info.getShareMoney();
		salaryLimit = info.getSalaryLimit();
		prom[0] = info.getProm0();
		prom[1] = info.getProm1();
		prom[2] = info.getProm2();
	}
}
