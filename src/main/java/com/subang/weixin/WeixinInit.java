package com.subang.weixin;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

import weixin.popular.api.MenuAPI;
import weixin.popular.api.TokenAPI;
import weixin.popular.bean.BaseResult;
import weixin.popular.bean.Token;

public class WeixinInit {

	private static final Logger LOG = Logger.getLogger ( WeixinInit.class.getName());
	
	private static Properties properties = null;
	private static Token token=null;

	public static void main(String[] args) {
		init();
		deleteMenu();
		createMenu();
	}

	public static void init() {
		properties = new Properties();
		InputStream in = null;
		try {
			in=ClassLoader.getSystemResourceAsStream("subang.properties");
			properties.load(in);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		token = TokenAPI.token(getProperty("appid"), getProperty("appsecret"));
	}

	public static void createMenu() {		
		BaseResult result = MenuAPI.menuCreate(token.getAccess_token(), getProperty("menu"));
		if (!result.getErrcode().equals("0")) {
			LOG.error(result.getErrcode() + ":" + result.getErrmsg());
		}
	}
	
	public static void deleteMenu(){
		BaseResult result = MenuAPI.menuDelete(token.getAccess_token());
		if (!result.getErrcode().equals("0")) {
			LOG.error(result.getErrcode() + ":" + result.getErrmsg());
		}
	}

	public static String getProperty(String key) {
		return properties.getProperty(key);
	}
}
