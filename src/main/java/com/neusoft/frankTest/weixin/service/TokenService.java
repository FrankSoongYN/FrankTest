package com.neusoft.frankTest.weixin.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.neusoft.frankTest.weixin.entity.Weixin;
import com.neusoft.frankTest.weixin.entity.weixinToken;


public class TokenService {
	private long timestamp=0l;
	private static final Logger logger = Logger.getLogger(TokenService.class);
	
	private static String token=null;//acess_token
	private static String ticket=null; //jsapi_ticket
	
	private  final String apiId="wx1afdef8c2d55426f";
	private  final String appSercrete="673a6e5defd8a8e106929d67318d17d8";
	
	
	private static CloseableHttpClient client = HttpClientBuilder.create().build();
	
	public void setToken(){
		String url="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=";
		HttpGet request = new HttpGet(url+apiId+"&secret="+appSercrete);
		CloseableHttpResponse response = null;
		try {
			response = client.execute(request);
			String html = IOUtils.toString(response.getEntity().getContent());
			weixinToken wt= JSON.parseObject(html, weixinToken.class);
			token=wt.getAccess_token();
			System.out.println("token:"+token);
			timestamp =System.currentTimeMillis()/1000; 
			logger.info(wt);
			//return ;
		} catch (IOException e) {
			
		}
			
	}
	
	public void setJsapi_ticket(){
		String url="https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";
		String reqUrl=url.replace("ACCESS_TOKEN", token);
		HttpGet request = new HttpGet(reqUrl);
		CloseableHttpResponse response = null;
		try {
			response = client.execute(request);
			String html = IOUtils.toString(response.getEntity().getContent());
			JSONObject js=new JSONObject(html);
			ticket=js.getString("ticket");
			//return ;
		} catch (IOException e) {
			
		}
	}
	
	public String getToken(){
		if(token==null){
			setToken();
			setJsapi_ticket();
		}
		return token;
	}

	public Weixin setSignature(String url){
		Weixin wx=new Weixin();
		String noncestr="Wm3WZYTPz0wzacnW";
		//String url="http://10.22.19.251/test/weixin/index.html";
		String str="jsapi_ticket="+ticket+"&noncestr="+noncestr+"&timestamp="+timestamp+"&url="+url;
		try {
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
	        crypt.reset();	
			crypt.update(str.getBytes("UTF-8"));			 
			byte[] bytes=crypt.digest();
			String jsapi_ticket=bytetoString(bytes);
			wx.setSignature(jsapi_ticket);
			
		}catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		wx.setAppId(apiId);
		wx.setTimestamp(timestamp);
		wx.setNonceStr(noncestr);
		return wx;
	}
	public String bytetoString(byte[] digest) {
		Formatter formatter = new Formatter();
        for (byte b : digest)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
	}
	
	public static void main(String[] args) {
		
		TokenService ts=new TokenService();
		ts.getToken();
		Weixin wx=ts.setSignature("http://10.22.19.251/test/weixin/index.html");
		System.out.println(wx);
	}
	
}
