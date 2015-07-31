package com.subang.util;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.cloopen.rest.sdk.CCPRestSmsSDK;
import com.subang.bean.OrderDetail;

public class SmsUtil {

	protected static final Logger LOG = Logger.getLogger(SmsUtil.class.getName());

	public interface SmsType {
		String authcode = "templateId_authcode";
		String accept = "templateId_accept";
		String cancel = "templateId_cancel";
	}

	private static String STATUS_SUCC = "000000";
	private static CCPRestSmsSDK restAPI;

	public static void init() {
		restAPI = new CCPRestSmsSDK();
		restAPI.init(Common.getProperty("serverIP_sms"), Common.getProperty("serverPort_sms"));
		restAPI.setAccount(Common.getProperty("accountSid_sms"),
				Common.getProperty("accountToken_sms"));
		restAPI.setAppId(Common.getProperty("appId_sms"));
	}

	public static boolean send(String cellnum, String type, String[] content) {
		HashMap<String, Object> result = restAPI.sendTemplateSMS(cellnum, Common.getProperty(type),
				content);
		if (result.get("statusCode").equals(STATUS_SUCC)) {
			return true;
		}
		LOG.error("错误码:" + result.get("statusCode") + "; 错误信息:" + result.get("statusMsg"));
		return false;
	}

	public static String[] toWorkerContent(OrderDetail orderDetail) {
		String[] content = new String[5];
		content[0] = orderDetail.getOrderno();
		content[1] = orderDetail.getAddrname();
		content[2] = orderDetail.getDateDes() + " " + orderDetail.getTimeDes();
		content[3] = orderDetail.getCityname() + "," + orderDetail.getDistrictname() + ","
				+ orderDetail.getRegionname() + "," + orderDetail.getAddrdetail();
		content[4] = orderDetail.getAddrcellnum();
		return content;
	}

	public static String[] toUserContent(String authcode) {
		return new String[] { authcode };
	}
}
