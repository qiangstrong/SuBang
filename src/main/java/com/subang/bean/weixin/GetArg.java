package com.subang.bean.weixin;

import weixin.popular.util.SignatureUtil;

import com.subang.util.Common;

public class GetArg {
	private String signature;
	private String timestamp;
	private String nonce;
	private String echostr;

	public GetArg() {
	}

	public GetArg(String signature, String timestamp, String nonce, String echostr) {
		this.signature = signature;
		this.timestamp = timestamp;
		this.nonce = nonce;
		this.echostr = echostr;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getNonce() {
		return nonce;
	}

	public void setNonce(String nonce) {
		this.nonce = nonce;
	}

	public String getEchostr() {
		return echostr;
	}

	public void setEchostr(String echostr) {
		this.echostr = echostr;
	}

	public boolean validate() {
		if (signature==null||timestamp==null||nonce==null) {
			return false;
		}
		if (!signature.equals(SignatureUtil.generateEventMessageSignature(Common.getProperty("token"),
				timestamp, nonce))) {
			return false;
		}
		return true;
	}
}
