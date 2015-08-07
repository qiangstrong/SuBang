package com.subang.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @author Qiang
 * 策略类。完成一些机制的具体实现。
 * 比如完成订单号生产的具体策略；积分功能计算的具体策略等。
 */
public class StratUtil extends BaseUtil {
	
	private static final String dataPath="data/StatUtil.dat";
	
	private static SimpleDateFormat sdf_orderno = new SimpleDateFormat("yyMMdd");
	private static NumberFormat nf = NumberFormat.getInstance();
	private static int count_order=0;
	
	public static void init(){
		String path=Common.getRealPath(dataPath);
		File file=new File(path);
		try {
			if (file.exists()) {
				DataInputStream in = new DataInputStream(new FileInputStream(file));
				count_order=in.readInt();
				in.close();
				LOG.info("读取count_order成功。");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void deinit(){
		String path=Common.getRealPath(dataPath);
		File file = new File(path);
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			DataOutputStream out = new DataOutputStream(new FileOutputStream(file));
			out.writeInt(count_order);
			out.close();
			LOG.info("保存count_order成功。");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

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
	
	/**
	 * 把一个整数转换为固定长度的字符串
	 */
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
