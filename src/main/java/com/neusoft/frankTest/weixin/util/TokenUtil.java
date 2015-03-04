package com.neusoft.frankTest.weixin.util;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.neusoft.frankTest.weixin.entity.weixinToken;
import com.neusoft.frankTest.weixin.service.TokenService;

public class TokenUtil {
	private static final Logger logger = Logger.getLogger(TokenService.class);
	
	private static String token=null;
	
	private  final String apiId="wx1afdef8c2d55426f";
	private  final String appSercrete="673a6e5defd8a8e106929d67318d17d8";
	private  final String url="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=";
	
	private static CloseableHttpClient client = HttpClientBuilder.create().build();
	
	public void setToken(){
		
		HttpGet request = new HttpGet(url+apiId+"&secret="+appSercrete);
		CloseableHttpResponse response = null;
		try {
			response = client.execute(request);
			String html = IOUtils.toString(response.getEntity().getContent());
			weixinToken wt= JSON.parseObject(html, weixinToken.class);
			token=wt.getAccess_token();
			logger.info(wt);
			//return ;
		} catch (IOException e) {
			
		}
			
	}
	
	public String getToken(){
		return token;
	}
	
}
