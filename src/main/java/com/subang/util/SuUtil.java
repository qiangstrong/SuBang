package com.subang.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.FieldError;
import org.springframework.web.multipart.MultipartFile;

import weixin.popular.util.JsonUtil;

import com.subang.bean.Result;
import com.subang.dao.NoticeDao;
import com.subang.domain.Filter;
import com.subang.domain.Notice;
import com.subang.domain.Notice.CodeType;
import com.subang.exception.SuException;

/**
 * @author Qiang 与业务相关的公用函数
 */
public class SuUtil extends BaseUtil {

	private static ServletContext servletContext = null;
	private static Properties su_properties = null;
	private static Properties app_properties = null;
	private static NoticeDao noticeDao = null;

	private static final String RESULT_OK;

	static {
		RESULT_OK = JsonUtil.toJSONString(new Result(Result.OK, null));
	}

	public void setNoticeDao(NoticeDao noticeDao) {
		SuUtil.noticeDao = noticeDao;
	}

	public static void init(ServletContext servletContext) {
		SuUtil.servletContext = servletContext;
		su_properties = new Properties();
		app_properties = new Properties();
		String path = null;
		InputStream in = null;
		try {
			path = servletContext.getRealPath("WEB-INF/classes/subang.properties");
			in = new BufferedInputStream(new FileInputStream(path));
			su_properties.load(in);
			in.close();
			path = servletContext.getRealPath("WEB-INF/classes/app.properties");
			in = new BufferedInputStream(new FileInputStream(path));
			app_properties.load(in);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static ServletContext getServletContext() {
		return servletContext;
	}

	public static String getRealPath(String path) {
		return servletContext.getRealPath(path);
	}

	// 获取与app相关的配置
	public static String getAppProperty(String key) {
		return app_properties.getProperty(key);
	}

	// 获取与业务相关的配置信息
	public static String getSuProperty(String key) {
		return su_properties.getProperty(key);
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
	public static void saveMultipartFile(Boolean isReplace, MultipartFile srcFile, String desPath)
			throws SuException {
		String path = servletContext.getRealPath(desPath);
		File file = new File(path);
		if (!isReplace && file.exists()) {
			throw new SuException("文件重名。");
		}
		try {
			srcFile.transferTo(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void deleteFile(String path) {
		if (path != null) {
			File file = new File(servletContext.getRealPath(path));
			file.delete();
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

	public static boolean outputJson(HttpServletResponse response, Object object) {
		try {
			response.setContentType("application/json;charset=UTF-8");
			OutputStream outputStream = response.getOutputStream();
			String json = JsonUtil.toJSONString(object);
			SuUtil.outputStreamWrite(outputStream, json);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static boolean outputJsonOK(HttpServletResponse response) {

		try {
			response.setContentType("application/json;charset=UTF-8");
			OutputStream outputStream = response.getOutputStream();
			SuUtil.outputStreamWrite(outputStream, RESULT_OK);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static <T> void doFilter(String filterJson, List<T> list, Class<T> clazz) {
		if (filterJson == null) {
			return;
		}
		Filter filter = (Filter) JsonUtil.parseObject(filterJson, clazz);
		for (T t : list) {
			filter.doFilter(t);
		}
	}

	public static List<Result> getResults(List<FieldError> errors) {
		List<Result> results = new ArrayList<Result>();
		for (FieldError error : errors) {
			results.add(new Result(error.getField(), error.getDefaultMessage()));
		}
		return results;
	}

	// 用户绑定手机号时，产生验证码
	public static String getUserAuthcode() {
		StringBuffer authcode = new StringBuffer();
		for (int i = 0; i < WebConst.AUTHCODE_LENGTH; i++) {
			authcode.append(ComUtil.random.nextInt(10));
		}
		return authcode.toString();
	}

	public static String getUserPhoto(String openid) {
		return "image/photo/" + openid + ".jpg";
	}

	public static void notice(CodeType type, String msg) {
		noticeDao.save(new Notice(type.ordinal(), msg));
	}
}
