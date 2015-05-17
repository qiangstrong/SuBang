package com.subang.utility;

public interface WebConstant {

	int PAGE_SIZE=10;
	
	String DATE = "yyyy-MM-dd";
	String TIME = "HH:mm:ss";
	String DATETIME = "yyyy-MM-dd HH:mm:ss";
	
	int SEARCH_NAME=0;
	int SEARCH_CELLNUM=1;
	int SEARCH_AREA=2;
	
	int ORDER_SEARCH_ORDERNO=0;
	int ORDER_SEARCH_USER_NICKNAME=1;
	int ORDER_SEARCH_USER_CELLNUM=2;
	int ORDER_SEARCH_LAUNDRY_NAME=3;
	
	int AREA_CITY=0;
	int AREA_DISTRICT=1;
	int AREA_REGION=2;
	
	int AREA_STAT_ORDER=0;
	int AREA_STAT_USER=1;
	
	int USER_STAT_ORDER=0;
	int USER_STAT_PRICE=1;
}
