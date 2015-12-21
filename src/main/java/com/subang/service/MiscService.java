package com.subang.service;

import org.springframework.stereotype.Service;

import com.subang.bean.AppInfo;
import com.subang.util.SuUtil;

@Service
public class MiscService extends BaseService {

	public boolean checkApp(AppInfo appInfo) {
		boolean result;
		String minverStr = null;
		int minver;
		switch (appInfo.getUser()) {
		case AppInfo.USER_USER:
			switch (appInfo.getOs()) {
			case AppInfo.OS_ANDROID:
				minverStr = "minver_user_android";
				break;
			case AppInfo.OS_IOS:
				minverStr = "minver_user_ios";
				break;
			}
			break;
		case AppInfo.USER_WORKER:
			switch (appInfo.getOs()) {
			case AppInfo.OS_ANDROID:
				minverStr = "minver_worker_android";
				break;
			case AppInfo.OS_IOS:
				minverStr = "minver_worker_ios";
				break;
			}
			break;
		case AppInfo.USER_CHECKER:
			switch (appInfo.getOs()) {
			case AppInfo.OS_ANDROID:
				minverStr = "minver_checker_android";
				break;
			case AppInfo.OS_IOS:
				minverStr = "minver_checker_ios";
				break;
			}
			break;
		}
		minver = Integer.valueOf(SuUtil.getAppProperty(minverStr));
		if (appInfo.getVersion() < minver) {
			result = false;
		} else {
			result = true;
		}
		return result;
	}
}
