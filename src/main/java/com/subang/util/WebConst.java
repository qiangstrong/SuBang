package com.subang.util;

public interface WebConst {

	String CONTEXT_PREFIX = "";
	String BACK_PREFIX = "/back";
	String WEIXIN_PREFIX = "/weixin";
	String APP_PREFIX = "/app";
	String LOG_TAG = "subang";
	String ALIAS_TYPE = "subang_cellnum";
	String CITY_NAME = "葫芦岛市"; // 如果无法定位用户的城市，则使用这个城市

	String KEY_ADMIN_AUTHCODE = "admin_authcode";
	String KEY_USER_AUTHCODE = "user_authcode";

	int AUTHCODE_LENGTH = 4;
	int ICON_RANDOM_LENGTH = 6; // icon的文件名随机部分的长度
	int ORDERNO_RANDOM_LENGTH = 7;

	int AUTHCODE_INTERVAL = 5 * 60 * 1000;// 验证码过期时间，5分钟，单位毫秒
	int TIMESTAMP_INTERVAL = 5 * 60 * 1000;// 时间戳过期时间

	int PAGE_SIZE = 30;
	int PREV = 0; // 表示要查看上一页
	int CUR = 1;// 表示要查看指定页面
	int NEXT = 2;// 表示要查看下一页

	int INDEX_BREAK = 0;
	int INDEX_CONTINUE = 1;

	int SEARCH_NULL = 0;
	int SEARCH_ALL = 1;
	int SEARCH_NAME = 2; // 按用户昵称，商家名称等进行搜索
	int SEARCH_CELLNUM = 3;
	int SEARCH_AREA = 4;
	int SEARCH_INCOMPLETE = 5;
	int SEARCH_NO = 6; // 按照编号进行搜索，如订单编号，用户编号（会员号）

	int SEARCH_ORDER_STATE = 2;
	int SEARCH_ORDER_ORDERNO = 3;
	int SEARCH_ORDER_USER_NICKNAME = 4;
	int SEARCH_ORDER_USER_CELLNUM = 5;
	int SEARCH_ORDER_LAUNDRY_NAME = 6;
	int SEARCH_ORDER_USERID = 7;
	int SEARCH_ORDER_WORKERID = 8;
	int SEARCH_ORDER_LAUNDRYID = 9;
	int SEARCH_ORDER_BARCODE = 10;
	int SEARCH_ORDER_USERID_STATE = 11;
	int SEARCH_ORDER_WORKERID_STATE = 12;

	int STAT_NULL = 0;
	int STAT_AREA = 1;
	int STAT_USER = 2;

	int STAT_AREA_ORDER = 0;
	int STAT_AREA_USER = 1;

	int STAT_USER_ORDER = 0;
	int STAT_USER_PRICE = 1;

	int AREA_CITY = 0;
	int AREA_DISTRICT = 1;
	int AREA_REGION = 2;

	// 前台部分
	int ORDER_STATE_NULL = 0;
	int ORDER_STATE_UNDONE = 2;
	int ORDER_STATE_DONE = 3;

	int ORDER_STATE_FETCH = 2;
	int ORDER_STATE_DELIVER = 3;
	int ORDER_STATE_FINISH = 4;

	int ORDER_GET_ID = 1;
	int ORDER_GET_BARCODE = 2;

	int ORDER_NUM = 50; // 前台最多显示50个已完成订单

	int EXPIRED_INTERVAL = 60; // 重复通知（微信服务器发送的消息，不包括支付通知）过滤 时效60秒

	int PROM_LAYER = 3;// 推广提成的层级数
}
