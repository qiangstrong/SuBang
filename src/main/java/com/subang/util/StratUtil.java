package com.subang.util;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StratUtil {
	
	private static SimpleDateFormat sdf_orderno = new SimpleDateFormat("yyMMdd");
	private static NumberFormat nf = NumberFormat.getInstance();
	private static int count_order=0;
	
	public static void reset(){
		count_order=0;
	}
	
	public static int getScore(Float price) {
		return price.intValue();
	}

	public static String getOrderno(Integer workerid) {
		String dateSTr = sdf_orderno.format(new Date());
		String workerStr=intToStr(workerid, 3);
		String noStr=intToStr(count_order++, 4);
		return dateSTr+workerStr+noStr;
	}
	
	public static String intToStr(int value,int length){
        nf.setGroupingUsed(false);
        nf.setMaximumIntegerDigits(length);
        nf.setMinimumIntegerDigits(length);
        return nf.format(value);
	}
	
	public static void main(String[] args) {
		System.out.println(getOrderno(4));
	}
}
