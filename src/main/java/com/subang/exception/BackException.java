package com.subang.exception;

public class BackException extends Exception {
	
	public BackException() {
	}

	/**
	 * @param msg 只显示原因，不显示结果
	 */
	public BackException(String msg) {
		super(msg);
	}
}
