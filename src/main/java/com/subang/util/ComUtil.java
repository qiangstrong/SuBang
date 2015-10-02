package com.subang.util;

import java.sql.Date;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.Timer;

/**
 * @author Qiang 与业务无关的公用函数
 */
public class ComUtil extends BaseUtil {

	public static Random random = new Random();
	public static Timer timer = new Timer();
	public static NumberFormat nf = NumberFormat.getInstance();
	public static SimpleDateFormat sdf_date = new SimpleDateFormat("yyyy-MM-dd");
	public static SimpleDateFormat sdf_datetime = new SimpleDateFormat("yyyyMMddHHmmss");

	public static String getLikeStr(String str) {
		return "%" + str + "%";
	}

	public static <T> T getFirst(List<T> list) {
		T t = null;
		if (list != null && !list.isEmpty()) {
			t = list.get(0);
		}
		return t;
	}

	/**
	 * 把一个整数转换为固定长度的字符串
	 */
	public static String intToStr(int value, int length) {
		nf.setGroupingUsed(false);
		nf.setMaximumIntegerDigits(length);
		nf.setMinimumIntegerDigits(length);
		return nf.format(value);
	}

	public static String getDes(Object object) {
		if (object == null) {
			return "未确定";
		}
		return object.toString();
	}

	public static String getDateDes(Date date) {
		return date.toString();
	}

	public static String getTimeDes(int time) {
		String description = "";
		description = time + ":00-" + (time + 1) + ":00";
		return description;
	}

	public static Date toDate(String dateValue) {
		Date date = null;
		try {
			date = new Date(ComUtil.sdf_date.parse(dateValue).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static boolean isTaday(Long time) {
		Calendar calendar_cur = Calendar.getInstance();
		Calendar calendar_old = Calendar.getInstance();
		calendar_old.setTimeInMillis(time);
		int day_cur = calendar_cur.get(Calendar.DAY_OF_YEAR);
		int day_old = calendar_old.get(Calendar.DAY_OF_YEAR);
		if (day_cur == day_old) {
			return true;
		} else {
			return false;
		}
	}

	public static String getTimestamp() {
		java.util.Date date = new java.util.Date();
		return sdf_datetime.format(date);
	}

	// 时间戳是否过期
	public static boolean isTimestampValid(String timestamp) {
		java.util.Date date_old = null;
		try {
			date_old = sdf_datetime.parse(timestamp);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (date_old == null) {
			return false;
		}
		long time_old = date_old.getTime();
		long time_cur = System.currentTimeMillis();
		if (time_cur - time_old > WebConst.TIMESTAMP_INTERVAL) {
			return false;
		}
		return true;
	}

	public static void main(String[] args) {

	}
}
