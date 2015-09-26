package com.subang.util;

import java.util.HashMap;

import com.cloopen.rest.sdk.CCPRestSmsSDK;
import com.subang.bean.OrderDetail;
import com.subang.domain.Notice.CodeType;

public class SmsUtil extends BaseUtil {

	public interface SmsType {
		String authcode = "templateId_authcode";
		String accept = "templateId_accept";
		String cancel = "templateId_cancel";
	}

	private static String STATUS_SUCC = "000000";
	private static CCPRestSmsSDK restAPI;

	public static void init() {
		restAPI = new CCPRestSmsSDK();
		restAPI.init(SuUtil.getAppProperty("serverIP_sms"), SuUtil.getAppProperty("serverPort_sms"));
		restAPI.setAccount(SuUtil.getAppProperty("accountSid_sms"),
				SuUtil.getAppProperty("accountToken_sms"));
		restAPI.setAppId(SuUtil.getAppProperty("appId_sms"));
	}

	public static boolean send(String cellnum, String type, String[] content) {
		HashMap<String, Object> result = restAPI.sendTemplateSMS(cellnum, SuUtil.getAppProperty(type),
				content);
		if (result.get("statusCode").equals(STATUS_SUCC)) {
			return true;
		}
		SuUtil.notice(CodeType.sms, "向用户发送短信失败。号码："+cellnum);
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
