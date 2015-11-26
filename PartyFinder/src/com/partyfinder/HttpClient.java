package com.partyfinder;

import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.util.Log;
public class HttpClient {

	static String TAG="[ HttpClientClass ]";
	
	static{
		
	}
	public static InputStream getInputStreamRequest(String url){
		InputStream inputStreamResponse = null ;
		HttpGet mHttpGet = new HttpGet(url);
		DefaultHttpClient httpclient = new DefaultHttpClient();        
        HttpResponse response = null;
		try {
			response = httpclient.execute(mHttpGet);
			inputStreamResponse= response.getEntity().getContent();
		} catch (Exception e) {
			Log.i("",TAG+"[ GET REQUEST METHOD ] [Exception] : "+e.toString());
			inputStreamResponse=null;
		} 
        
		return inputStreamResponse;
			
	}
	
	public static InputStream postInputStreamRequest(String url){
		return null;
		
	}
	
	public static String getStringRequest(String url){
		String stringResponse = null ;
		HttpGet mHttpGet = new HttpGet(url);
		DefaultHttpClient httpclient = new DefaultHttpClient();        
        HttpResponse response = null;
		try {
			response = httpclient.execute(mHttpGet);
			stringResponse= EntityUtils.toString(response.getEntity());
		} catch (Exception e) {
			Log.i("",TAG+"[ GET REQUEST METHOD ] [Exception] : "+e.toString());
			stringResponse=null;
		} 
        
		return stringResponse;
	}
	
	public static InputStream postStringRequest(String url){
		return null;	
	}
}
