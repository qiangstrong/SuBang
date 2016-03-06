package com.subang.util;

import java.util.Date;

import com.subang.dao.UserDao;
import com.subang.domain.Order;
import com.subang.domain.User;

/**
 * @author Qiang 策略类。完成一些机制的具体实现。 比如完成订单号生产的具体策略；积分功能计算的具体策略等。
 */
public class StratUtil extends BaseUtil {

	private static final String dataPath = "data/StatUtil.dat";

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
		String no_date = ComUtil.getOrdernoFormat().format(new Date());
		return no_date + orderType.ordinal() + ComUtil.getRandomStr(WebConst.ORDERNO_RANDOM_LENGTH);
	}

	// 可以获取积分的事件类型
	public enum ScoreType {
		login, balance, nobalance, remark
	}

	public static void updateScore(Integer userid, ScoreType type, Object arg) {
		User user = userDao.get(userid);
		long deltaScore = 0;
		switch (type) {
		case login: {
			if (!user.getLogin()) {
				user.setLogin(true);
				deltaScore = 1;
			}
			break;
		}
		case balance: {
			deltaScore = Math.round((Double) arg) * 2;
			break;
		}
		case nobalance: {
			deltaScore = Math.round((Double) arg);
			break;
		}
		case remark: {
			deltaScore = 5;
			break;
		}
		}
		user.setScore(user.getScore() + (int) deltaScore);
		userDao.update(user);
	}

}
