package com.subang.util;

import com.subang.bean.Result;

/**
 * @author Qiang 对一些没有封装在bean中的字段经行校验。 这类属性无法通过Spring校验机制进行校验。
 */
public class Validator extends BaseUtil {

	public static final int CELLNUM_LENGTH = 11;
	public static final int BARCODE_LENGTH = 13;
	public static final int USERNO_LENGTH = 10;
	public static final int CODENO_LENGTH = 8; // 优惠码的最大长度

	// 11位数字
	public static Result validCellnum(String cellnum) {
		Result result = validNum(cellnum, CELLNUM_LENGTH);
		result.setMsg("手机号" + result.getMsg());
		return result;
	}

	public static Result validBarcode(String barcode) {
		Result result = validNum(barcode, BARCODE_LENGTH);
		result.setMsg("条形码" + result.getMsg());
		return result;
	}

	public static Result validUserno(String userno) {
		Result result = validNum(userno, USERNO_LENGTH);
		result.setMsg("会员号" + result.getMsg());
		return result;
	}

	public static Result validPassword(String password) {
		Result result = validLen(password, 1, 50);
		result.setMsg("密码" + result.getMsg());
		return result;
	}

	public static Result validMaxlen(String string, int maxlen) {
		Result result = new Result();
		if (string == null) {
			result.setCode(Result.ERR);
			result.setMsg("不能为空。");
			return result;
		}
		string = string.trim();
		if (string.length() > maxlen) {
			result.setCode(Result.ERR);
			result.setMsg("长度不能超过" + maxlen + "个字符。");
			return result;
		}
		result.setCode(Result.OK);
		return result;
	}

	public static Result validLen(String string, int minlen, int maxlen) {
		Result result = new Result();
		if (string == null) {
			result.setCode(Result.ERR);
			result.setMsg("不能为空。");
			return result;
		}
		string = string.trim();
		if (string.length() < minlen || string.length() > maxlen) {
			result.setCode(Result.ERR);
			result.setMsg("长度需要在" + minlen + "-" + maxlen + "之间。");
			return result;
		}
		result.setCode(Result.OK);
		return result;
	}

	public static Result validNum(String string, int len) {
		Result result = new Result();
		if (string == null) {
			result.setCode(Result.ERR);
			result.setMsg("不能为空。");
			return result;
		}
		string = string.trim();
		if (string.length() != len) {
			result.setCode(Result.ERR);
			result.setMsg("长度必须是" + len + "位的数字。");
			return result;
		}
		char c;
		for (int i = 0; i < len; i++) {
			c = string.charAt(i);
			if (c < '0' || c > '9') {
				result.setCode(Result.ERR);
				result.setMsg("长度必须是" + len + "位的数字。");
				return result;
			}
		}
		result.setCode(Result.OK);
		return result;
	}

	public static void main(String[] args) {

	}
}
