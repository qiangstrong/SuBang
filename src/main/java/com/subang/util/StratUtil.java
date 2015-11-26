package com.subang.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.subang.dao.UserDao;
import com.subang.domain.Order;
import com.subang.domain.User;

/**
 * @author Qiang 策略类。完成一些机制的具体实现。 比如完成订单号生产的具体策略；积分功能计算的具体策略等。
 */
public class StratUtil extends BaseUtil {

	private static final String dataPath = "data/StatUtil.dat";

	private static SimpleDateFormat sdf_orderno = new SimpleDateFormat("yyMMdd");
	private static UserDao userDao = null;

	public void setUserDao(UserDao userDao) {
		StratUtil.userDao = userDao;
	}

	public static void init() {
	}

	public static void deinit() {
	}

	public static void reset() {
	}

	public static String getOrderno(Order.OrderType orderType) {
		String no_date = sdf_orderno.format(new Date());
		StringBuffer on_random = new StringBuffer();
		for (int i = 0; i < WebConst.ORDERNO_RANDOM_LENGTH; i++) {
			on_random.append(ComUtil.random.nextInt(10));
		}
		return no_date + orderType.ordinal() + on_random.toString();
	}

	// 可以获取积分的事件类型
	public enum ScoreType {
		login, balance, nobalance, remark
	}

	public static void updateScore(Integer userid, ScoreType type, Object arg) {
		User user = userDao.get(userid);
		int deltaScore = 0;
		switch (type) {
		case login: {
			if (!user.getLogin()) {
				user.setLogin(true);
				deltaScore = 1;
			}
			break;
		}
		case balance: {
			deltaScore = ((Integer) arg) * 2;
			break;
		}
		case nobalance: {
			deltaScore = (Integer) arg;
			break;
		}
		case remark: {
			deltaScore = 5;
			break;
		}
		}
		user.setScore(user.getScore() + deltaScore);
		userDao.update(user);
	}

}
