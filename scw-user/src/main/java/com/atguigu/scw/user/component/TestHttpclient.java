package com.atguigu.scw.user.component;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class TestHttpclient {
	
	public static void main(String[] args) throws ClientProtocolException, IOException {
		
		HttpClient httpClient = new DefaultHttpClient();
		
		HttpUriRequest request = new HttpGet("https://www.baidu.com/") ;
		HttpResponse response = httpClient.execute(request );
		
		HttpEntity entity = response.getEntity();
		
		String string = EntityUtils.toString(entity,"UTF-8");
		
		System.out.println(string);
	}
	
	
	

}
