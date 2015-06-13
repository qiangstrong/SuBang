package com.subang.util;

import com.subang.bean.OrderDetail;
import com.subang.domain.History.Operation;

public class SmsUtil {

	public static boolean send(String cellnum, String content) {
		return true;
	}

	public static String toWorkerContent(Operation operation, OrderDetail orderDetail) {
		StringBuffer content = new StringBuffer();
		if (operation == Operation.accept) {
			content.append("用户下单。");
		} else if (operation == Operation.cancel) {
			content.append("用户取消订单。");
		}
		content.append("用户信息：" + orderDetail.getAddrname() + "," + orderDetail.getAddrcellnum()
				+ "," + orderDetail.getCityname() + "," + orderDetail.getDistrictname() + ","
				+ orderDetail.getRegionname() + "," + orderDetail.getAddrdetail() + "。");
		content.append("订单信息：" + "," + orderDetail.getOrderno() + "," + orderDetail.getCategoryDes()
				+ "," + orderDetail.getDate() + "," + orderDetail.getTimeDes() + "。");
		return content.toString();
	}

	public static String toUserContent(String authcode) {
		StringBuffer content = new StringBuffer();
		content.append("【速帮到家】您的验证码是");
		content.append(authcode);
		content.append(",本验证码5分钟内有效。如非本人操作，请忽略本短信。");
		return content.toString();
	}
}
