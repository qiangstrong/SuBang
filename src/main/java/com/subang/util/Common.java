package com.subang.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.springframework.web.multipart.MultipartFile;

import bsh.This;

public class Common {

	private static ServletContext servletContext = null;
	private static Properties properties = null;
	
	public static ServletContext getServletContext() {
		return servletContext;
	}

	public static void setServletContext(ServletContext servletContext) {
		if (Common.servletContext==null) {
			Common.servletContext = servletContext;
		}	
	}
	
	public static String getProperty(String key){
		return properties.getProperty(key);
	}

	public static void loadProperties(){
		if (properties==null) {
			properties = new Properties();
			String path=servletContext.getRealPath("WEB-INF/classes/sql.properties");
			InputStream in=null;
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
}
