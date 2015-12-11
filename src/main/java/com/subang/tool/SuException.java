package com.subang.tool;

public class SuException extends Exception {
	
	public SuException() {
	}

	/**
	 * @param msg 只显示原因，不显示结果
	 */
	public SuException(String msg) {
		super(msg);
	}
}
