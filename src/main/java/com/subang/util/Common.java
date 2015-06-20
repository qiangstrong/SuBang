package com.subang.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.Timer;

import javax.servlet.ServletContext;

import org.springframework.web.multipart.MultipartFile;

public class Common {

	public static Random random = new Random();
	public static Timer timer = new Timer();

	private static SimpleDateFormat sdf_orderno = new SimpleDateFormat("yyyyMMddHHmmss");
	private static ServletContext servletContext = null;
	private static Properties properties = null;

	public static void init(ServletContext servletContext) {
		Common.servletContext = servletContext;
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

	public static ServletContext getServletContext() {
		return servletContext;
	}

	public static String getProperty(String key) {
		return properties.getProperty(key);
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

	public static void saveUrl(String url, String desPath) {
		try {
			BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
			BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(new File(
					servletContext.getRealPath(desPath))));
			byte[] buf = new byte[2048];
			int length = in.read(buf);
			while (length != -1) {
				out.write(buf, 0, length);
				length = in.read(buf);
			}
			in.close();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean outputStreamWrite(OutputStream outputStream, String text) {
		try {
			outputStream.write(text.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	// 用户绑定手机号时，产生验证码
	public static String getUserAuthcode() {
		StringBuffer authcode = new StringBuffer();
		for (int i = 0; i < WebConst.AUTHCODE_LENGTH; i++) {
			authcode.append(random.nextInt(10));
		}
		return authcode.toString();
	}

	public static int getcScore(Float price) {
		return price.intValue();
	}

	public static String getOrderno() {
		String no_date = sdf_orderno.format(new Date());
		StringBuffer on_random = new StringBuffer();
		for (int i = 0; i < WebConst.ORDERNO_RANDOM_LENGTH; i++) {
			on_random.append(random.nextInt(10));
		}
		return no_date + on_random.toString();
	}

	public static String getUserPhoto(String openid) {
		return "image/photo/" + openid + ".jpg";
	}

}
