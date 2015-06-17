package com.subang.util;

public class Validator {
	
	public static final int CELLNUM_LENGTH=11;
	
	public static boolean ValidCellnum(String cellnum){
		cellnum=cellnum.trim();
		if (cellnum.length()!=CELLNUM_LENGTH) {
			return false;
		}
		char c;
		for (int i = 0; i < CELLNUM_LENGTH; i++) {
			c=cellnum.charAt(i);
			if (c<'0'||c>'9') {
				return false;
			}
		}
		return true;
	}
}
