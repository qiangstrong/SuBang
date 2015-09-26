package com.subang.util;

import java.sql.Timestamp;
import java.util.Calendar;

import com.baidu.map.api.GeocodingAPI;
import com.baidu.map.bean.LatLng;
import com.baidu.map.bean.RenderReverseResult;
import com.baidu.map.util.CoordType;
import com.subang.bean.GeoLoc;
import com.subang.domain.Location;

public class LocUtil extends BaseUtil {

	public static final int TIMEOUT = 1; // 如果位置的时间戳在一天之前，这个位置无效
	private static String STATUS_SUCC = "0";

	public static boolean isValid(Location location) {
		Timestamp timestamp = location.getTime();
		Calendar calendarLoc = Calendar.getInstance();
		calendarLoc.setTimeInMillis(timestamp.getTime());
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, -TIMEOUT);
		return calendar.before(calendarLoc);
	}

	public static GeoLoc getGeoLoc(Location location) {
		if (location == null || !isValid(location)) {
			return null;
		}
		LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
		RenderReverseResult result = GeocodingAPI.renderReverse(SuUtil.getAppProperty("ak_map"),
				CoordType.wgs84ll, latLng);
		if (!result.getStatus().equals(STATUS_SUCC)) {
			LOG.error("错误码:" + result.getStatus() + "; 错误信息:用户位置解析失败。");
			return null;
		}
		return GeoLoc.toGeoLoc(result);
	}

	public static void main(String[] args) {
	}
}
