package com.subang.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Qiang 策略类。完成一些机制的具体实现。 比如完成订单号生产的具体策略；积分功能计算的具体策略等。
 */
public class StratUtil extends BaseUtil {

	private static final String dataPath = "data/StatUtil.dat";

	private static SimpleDateFormat sdf_orderno = new SimpleDateFormat("yyMMdd");

	public static void init() {

	}

	public static void deinit() {

	}

	public static void reset() {

	}

	public static int getScore(Float price) {
		return price.intValue();
	}

	public static String getOrderno() {

		String no_date = sdf_orderno.format(new Date());
		StringBuffer on_random = new StringBuffer();
		for (int i = 0; i < WebConst.ORDERNO_RANDOM_LENGTH; i++) {
			on_random.append(ComUtil.random.nextInt(10));
		}
		return no_date + on_random.toString();

	}

}
