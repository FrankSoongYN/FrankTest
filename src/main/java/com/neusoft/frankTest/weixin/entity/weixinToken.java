package com.neusoft.frankTest.weixin.entity;

public class weixinToken {
	
	private String access_token;
	private long expires_in;
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	public long getExpires_in() {
		return expires_in;
	}
	public void setExpires_in(long expires_in) {
		this.expires_in = expires_in;
	}
	@Override
	public String toString() {
		return "weixinToken [access_token=" + access_token + ", expires_in="
				+ expires_in + "]";
	}
	
	
}
