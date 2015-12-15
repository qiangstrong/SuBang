package com.subang.util;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import weixin.popular.bean.paymch.MchNotifyResult;
import weixin.popular.util.SignatureUtil;
import weixin.popular.util.XMLConverUtil;

public class PayUtil {
	/*
	 * 微信支付有关的函数
	 */
	public static boolean wxVerify(Map<String, String> map) {
		String appid = map.get("appid");
		String apikey = null;
		if (appid.equals(SuUtil.getAppProperty("appid"))) {
			apikey = SuUtil.getAppProperty("apikey");
		} else if (appid.equals(SuUtil.getAppProperty("appid_user"))) {
			apikey = SuUtil.getAppProperty("apikey_user");
		} else if (appid.equals(SuUtil.getAppProperty("appid_worker"))) {
			apikey = SuUtil.getAppProperty("apikey_worker");
		}
		String sign = SignatureUtil.generateSign(map, apikey);
		if (!sign.equals(map.get("sign"))) {
			return false;
		}
		return true;
	}

	public static void wxpayError(HttpServletResponse response) throws Exception {
		MchNotifyResult notifyResult = new MchNotifyResult();
		notifyResult.setReturn_code("FAIL");
		notifyResult.setReturn_msg("ERROR");
		response.getOutputStream().write(XMLConverUtil.convertToXML(notifyResult).getBytes());
	}

	public static void wxpaySucc(HttpServletResponse response) throws Exception {
		MchNotifyResult notifyResult = new MchNotifyResult();
		notifyResult.setReturn_code("SUCCESS");
		notifyResult.setReturn_msg("OK");
		response.getOutputStream().write(XMLConverUtil.convertToXML(notifyResult).getBytes());
	}

	/*
	 * 支付宝有关的函数
	 */
	public static void alipayError(HttpServletResponse response) throws Exception {
		SuUtil.outputStreamWrite(response.getOutputStream(), "fail");
	}

	public static void alipaySucc(HttpServletResponse response) throws Exception {
		SuUtil.outputStreamWrite(response.getOutputStream(), "success");
	}
}
