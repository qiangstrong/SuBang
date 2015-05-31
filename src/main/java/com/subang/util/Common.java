package com.subang.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import javax.servlet.ServletContext;

import org.springframework.web.multipart.MultipartFile;

import com.sun.org.apache.bcel.internal.generic.NEW;

import bsh.This;

public class Common {

	public static Random random = new Random();

	private static SimpleDateFormat sdf_orderno = new SimpleDateFormat("yyyyMMddHHmmss");
	private static ServletContext servletContext = null;
	private static Properties properties = null;

	public static ServletContext getServletContext() {
		return servletContext;
	}

	public static void setServletContext(ServletContext servletContext) {
		if (Common.servletContext == null) {
			Common.servletContext = servletContext;
		}
	}

	public static String getProperty(String key) {
		return properties.getProperty(key);
	}

	public static void loadProperties() {
		if (properties == null) {
			properties = new Properties();
			String path = servletContext.getRealPath("WEB-INF/classes/subang.properties");
			InputStream in = null;
			try {
				in = new BufferedInputStream(new FileInputStream(path));
				properties.load(in);
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static String getLikeStr(String str) {
		return "%" + str + "%";
	}

	public static List<Integer> getIds(String someIds) {
		String[] strIds = someIds.split(",");
		List<Integer> ids = new ArrayList<Integer>();
		for (String strId : strIds) {
			ids.add(new Integer(strId));
		}
		return ids;
	}

	/**
	 * @param desPath
	 *            相对于WebRoot的路径
	 */
	public static void saveMultipartFile(MultipartFile srcFile, String desPath) {
		String path = servletContext.getRealPath(desPath);
		File file = new File(path);
		try {
			srcFile.transferTo(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String getVericode() {
		StringBuffer vericode = new StringBuffer();
		for (int i = 0; i < WebConstant.VERI_LENGTH; i++) {
			vericode.append(random.nextInt(10));
		}
		return vericode.toString();
	}

	public static int getcScore(Float price) {
		return price.intValue();
	}

	public static String getOrderno() {
		String no_date = sdf_orderno.format(new Date());
		StringBuffer on_random=new StringBuffer();
		for (int i = 0; i < WebConstant.ORDERNO_RANDOM_LENGTH; i++) {
			on_random.append(random.nextInt(10));
		}
		return no_date+on_random.toString();
	}
}
