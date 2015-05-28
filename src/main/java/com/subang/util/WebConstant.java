package com.subang.util;

public interface WebConstant {

	int SESSION_INTERVAL = 3600; // session过期时间，1小时

	int PAGE_SIZE = 10;

	int INDEX_BREAK = 0;
	int INDEX_CONTINUE = 1;

	int SEARCH_NULL = 0;
	int SEARCH_ALL = 1;
	int SEARCH_NAME = 2; // 按用户昵称，商家名称等进行搜索
	int SEARCH_CELLNUM = 3;
	int SEARCH_AREA = 4;

	int SEARCH_ORDER_STATE = 2;
	int SEARCH_ORDER_USERID = 3;
	int SEARCH_ORDER_ORDERNO = 4;
	int SEARCH_ORDER_USER_NICKNAME = 5;
	int SEARCH_ORDER_USER_CELLNUM = 6;
	int SEARCH_ORDER_LAUNDRY_NAME = 7;

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
}
