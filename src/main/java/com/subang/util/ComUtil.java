package com.subang.util;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.Timer;

import com.subang.bean.SearchArg;

/**
 * @author Qiang 与业务无关的公用函数
 */
public class ComUtil extends BaseUtil {

	public static Random random = new Random();
	public static Timer timer = new Timer();
	public static Timestamp firstTime, lastTime;
	public static SearchArg curDayArg;

	public static void init() {
		firstTime = new Timestamp(0);
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(firstTime.getTime());
		calendar.add(Calendar.YEAR, 200);
		lastTime = new Timestamp(calendar.getTimeInMillis());
		calcCurDayArg();
	}

	public static void reset() {
		calcCurDayArg();
	}

	private static void calcCurDayArg() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		Timestamp today = new Timestamp(calendar.getTimeInMillis());
		calendar.add(Calendar.DATE, 1);
		Timestamp tomorrow = new Timestamp(calendar.getTimeInMillis());
		curDayArg = new SearchArg();
		curDayArg.setType(WebConst.SEARCH_ALL);
		curDayArg.setStartTime(today);
		curDayArg.setEndTime(tomorrow);
	}

	public static NumberFormat getNumberFormat() {
		return NumberFormat.getInstance();
	}

	public static SimpleDateFormat getDateFormat() {
		return new SimpleDateFormat("yyyy-MM-dd");
	}

	public static SimpleDateFormat getDatetimeFormat() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	}

	// 用于对app的请求签名
	public static SimpleDateFormat getTimestampFormat() {
		return new SimpleDateFormat("yyyyMMddHHmmss");
	}

	public static SimpleDateFormat getOrdernoFormat() {
		return new SimpleDateFormat("yyMMdd");
	}

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

	// 把一个整数转换为固定长度的字符串
	public static String intToStr(int value, int length) {
		NumberFormat nf = getNumberFormat();
		nf.setGroupingUsed(false);
		nf.setMaximumIntegerDigits(length);
		nf.setMinimumIntegerDigits(length);
		return nf.format(value);
	}

	// 生成指定长度的由数字组成的随机字符串
	public static String getRandomStr(int length) {
		StringBuffer str = new StringBuffer();
		for (int i = 0; i < length; i++) {
			str.append(ComUtil.random.nextInt(10));
		}
		return str.toString();
	}

	public static String getDes(Object object) {
		if (object == null) {
			return "未确定";
		}
		return object.toString();
	}

	public static String getDateDes(Date date) {
		if (date == null) {
			return null;
		}
		return date.toString();
	}

	public static String getTimeDes(Integer time) {
		if (time == null) {
			return null;
		}
		String description = "";
		description = time + ":00-" + (time + 1) + ":00";
		return description;
	}

	public static Date toDate(String dateValue) {
		Date date = null;
		try {
			date = new Date(getDateFormat().parse(dateValue).getTime());
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
		return getTimestampFormat().format(date);
	}

	// 时间戳是否过期
	public static boolean isTimestampValid(String timestamp) {
		java.util.Date date_old = null;
		try {
			date_old = getTimestampFormat().parse(timestamp);
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

	public static Double round(Double d) {
		if (d == null) {
			return null;
		}
		return BigDecimal.valueOf(d).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public static Double round(Double d, int scale) {
		if (d == null) {
			return null;
		}
		return BigDecimal.valueOf(d).setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public static boolean equal(double d1, double d2) {
		if (Math.abs(d1 - d2) < 0.1) {
			return true;
		}
		return false;
	}

	// 计算文件的后缀名
	public static String getSuffix(String filename) {
		int index = filename.lastIndexOf(".");
		if (index == -1) {
			return "";
		}
		return filename.substring(index);
	}

	public static void main(String[] args) {
		boolean result = isTimestampValid("20151216195528");
		System.out.println(result);
	}
}
