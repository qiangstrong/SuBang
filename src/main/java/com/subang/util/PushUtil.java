package com.subang.util;

import com.subang.domain.Notice.Code;
import com.subang.domain.Order.OrderType;
import com.umeng.push.AndroidNotification;
import com.umeng.push.PushClient;
import com.umeng.push.android.AndroidCustomizedcast;

public class PushUtil extends BaseUtil {
	private static String appkey = null;
	private static String appsecret = null;
	private static PushClient client = new PushClient();

	public static void init() {
		appkey = SuUtil.getAppProperty("appkey_umeng_worker");
		appsecret = SuUtil.getAppProperty("appsecret_umeng_worker");
	}

	public static boolean send(String[] cellnums, OrderType orderType) {
		if (cellnums.length == 0) {
			return true;
		}

		String text;
		if (orderType == OrderType.record) {
			text = "您的商城订单有变动，请注意查看。";
		} else {
			text = "您的订单有变动，请注意查看。";
		}

		boolean result = false;
		try {
			AndroidCustomizedcast customizedcast = new AndroidCustomizedcast(appkey, appsecret);
			customizedcast.setAlias(toAlias(cellnums), WebConst.ALIAS_TYPE);
			customizedcast.setTicker(text);
			customizedcast.setTitle("订单变动");
			customizedcast.setText(text);
			customizedcast.goAppAfterOpen();
			customizedcast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
			customizedcast.setProductionMode();
			result = client.send(customizedcast);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (!result) {
			LOG.error("错误码:" + WebConst.LOG_TAG + "; 错误信息:" + "向取衣员推送订单变动失败。");
			SuUtil.notice(Code.push, "向取衣员推送订单变动失败。号码：" + toAlias(cellnums));
		}
		return result;
	}

	private static String toAlias(String[] cellnums) {
		StringBuffer buffer = new StringBuffer();
		for (String cellnum : cellnums) {
			buffer.append(cellnum);
			buffer.append(",");
		}
		String alias = buffer.toString();
		alias = alias.subSequence(0, alias.length() - 1).toString();
		return alias;
	}

	public static void main(String[] args) {

	}

}
