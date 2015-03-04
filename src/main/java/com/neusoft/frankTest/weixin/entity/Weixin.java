package com.neusoft.frankTest.weixin.entity;

public class Weixin {
	private String appId;
	private String nonceStr;
	private String signature;
	private long timestamp;
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getNonceStr() {
		return nonceStr;
	}
	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	@Override
	public String toString() {
		return "Weixin [appId=" + appId + ", nonceStr=" + nonceStr
				+ ", signature=" + signature + ", timestamp=" + timestamp + "]";
	}
	
	
}
