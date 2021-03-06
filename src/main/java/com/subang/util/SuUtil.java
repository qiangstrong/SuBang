package com.subang.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.FieldError;
import org.springframework.web.multipart.MultipartFile;

import weixin.popular.client.LocalHttpClient;
import weixin.popular.support.TicketManager;
import weixin.popular.util.JsUtil;
import weixin.popular.util.JsonUtil;

import com.subang.bean.Result;
import com.subang.dao.NoticeDao;
import com.subang.domain.Notice;
import com.subang.domain.Notice.Code;
import com.subang.domain.face.Filter;

/**
 * @author Qiang 与业务相关的公用函数
 */
public class SuUtil extends BaseUtil {

	private static ServletContext servletContext = null;
	private static Properties app_properties = null;
	private static Properties msg_properties = null;
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
		app_properties = new Properties();
		msg_properties = new Properties();
		String path = null;
		InputStream in = null;
		try {
			path = servletContext.getRealPath("WEB-INF/classes/app.properties");
			in = new BufferedInputStream(new FileInputStream(path));
			app_properties.load(in);
			in.close();
			path = servletContext.getRealPath("WEB-INF/classes/msg.properties");
			in = new BufferedInputStream(new FileInputStream(path));
			msg_properties.load(in);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		path = servletContext.getRealPath("WEB-INF/apiclient_cert.p12");
		LocalHttpClient.initMchKeyStore("PKCS12", path, getAppProperty("mch_id"));
	}

	public static ServletContext getServletContext() {
		return servletContext;
	}

	public static String getRealPath(String path) {
		return servletContext.getRealPath(path);
	}

	public static String getBasePath(HttpServletRequest request) {
		String basePath = request.getScheme() + "://" + request.getServerName() + ":"
				+ request.getServerPort() + request.getContextPath() + "/";
		return basePath;
	}

	public static String getPromPath(HttpServletRequest request, String cellnum) {
		return getBasePath(request) + "weixin/user/promote.html?cellnum=" + cellnum;
	}

	public static String getSharePath(HttpServletRequest request) {
		return getBasePath(request) + "content/weixin/activity/share.htm";
	}

	// 获取与app相关的配置
	public static String getAppProperty(String key) {
		return app_properties.getProperty(key);
	}

	// 获取与msg相关的配置
	public static String getMsgProperty(String key) {
		return msg_properties.getProperty(key);
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
	 * @param path
	 *            相对于WebRoot的路径
	 */
	public static boolean fileExist(String path) {
		String path1 = servletContext.getRealPath(path);
		File file = new File(path1);
		if (file.exists()) {
			return true;
		}
		return false;
	}

	public static void saveMultipartFile(MultipartFile srcFile, String desPath) {
		String path = servletContext.getRealPath(desPath);
		File file = new File(path);
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

	// 用户、取衣员改绑手机号时，产生验证码
	public static String getAuthcode() {
		return ComUtil.getRandomStr(WebConst.AUTHCODE_LENGTH);
	}

	// 计算管理员上传的文件名。日期+固定位数的随机字符串+上传文件的后缀
	public static String getFilename(String originalFilename) {
		String no_date = ComUtil.getOrdernoFormat().format(new Date());
		return no_date + ComUtil.getRandomStr(WebConst.ICON_RANDOM_LENGTH)
				+ ComUtil.getSuffix(originalFilename);
	}

	public static void notice(Code code, String msg) {
		noticeDao.save(new Notice(code.ordinal(), msg));
	}

	// 计算微信jsapi的配置
	public static String getJsapiConfig(HttpServletRequest request) {
		StringBuffer url = request.getRequestURL();
		String queryString = request.getQueryString();
		if (queryString != null) {
			url.append("?").append(queryString);
		}

		String[] jsApiList = { "checkJsApi", "onMenuShareTimeline", "onMenuShareAppMessage",
				"onMenuShareQQ", "onMenuShareWeibo", "onMenuShareQZone" };
		String config = JsUtil.generateConfigJson(TicketManager.getDefaultTicket(), false,
				SuUtil.getAppProperty("appid"), url.toString(), jsApiList);
		return config;
	}
}
