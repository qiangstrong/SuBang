package com.subang.util;

/**
 * @author Qiang 对一些没有封装在bean中的字段经行校验。 这类属性无法通过Spring校验机制进行校验。
 */
public class Validator extends BaseUtil {

	public static final int CELLNUM_LENGTH = 11;

	public static boolean ValidCellnum(String cellnum) {
		cellnum = cellnum.trim();
		if (cellnum.length() != CELLNUM_LENGTH) {
			return false;
		}
		char c;
		for (int i = 0; i < CELLNUM_LENGTH; i++) {
			c = cellnum.charAt(i);
			if (c < '0' || c > '9') {
				return false;
			}
		}
		return true;
	}
}
