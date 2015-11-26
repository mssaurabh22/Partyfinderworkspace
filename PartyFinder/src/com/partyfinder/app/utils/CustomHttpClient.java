package com.partyfinder.app.utils;

import java.io.InputStream;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.partyfinder.AppConstant.PartyFinderConstants;

import android.util.Log;
public class CustomHttpClient {

	static String TAG="[ HttpClientClass ]";

	static{

	}
	public static InputStream getInputStreamRequest(String url){
		InputStream inputStreamResponse = null ;
		//		Uri uri=new Uri(url);
		HttpGet mHttpGet = new HttpGet(url);
	

		mHttpGet.setHeader("User-Agent", "android");
		
		
		DefaultHttpClient httpclient = new DefaultHttpClient();        
		HttpResponse response = null;
		try {
			response = httpclient.execute(mHttpGet);
			inputStreamResponse= response.getEntity().getContent();
			//			Log.i("",TAG+"[ GET REQUEST METHOD ] : "+EntityUtils.toString(response.getEntity()));
		} catch (Exception e) {
			Log.i("",PartyFinderConstants.MAIN_EXCEPTION+TAG+"[ GET REQUEST METHOD ] [Exception] : "+e.toString());
			inputStreamResponse=null;
		} 

		
		
		return inputStreamResponse;

	}



	/*public static int postMundipaggRequest(String url,String xml) {

		DefaultHttpClient mHttpClient = new DefaultHttpClient();
		InputStream inputStream = null;
		int responseCode = 0;
		try{
			HttpPost mHttpPost = new HttpPost(url);


			//			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
			//	        nameValuePairs.add(new BasicNameValuePair("MerchantKey", "1ef11b35-c8d0-4ef2-bb6e-db04a8fae49d"));
			//	        mHttpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			mHttpPost.addHeader("Content-Type", "text/xml");
			mHttpPost.addHeader("MerchantKey","1ef11b35-c8d0-4ef2-bb6e-db04a8fae49d");
			
//			StringEntity se = new StringEntity(xml);
			StringEntity se = new StringEntity(xml,HTTP.UTF_8);
			//			se.setContentType("text/xml");
			mHttpPost.setEntity(se);

			HttpResponse response = mHttpClient.execute(mHttpPost);
			//			
			//			 inputStream = response.getEntity().getContent();

			responseCode=response.getStatusLine().getStatusCode();
			Log.i("",TAG+"Mundipagg Response : "+ EntityUtils.toString(response.getEntity()));
		}catch(Exception e){
			Log.i("",PartyFinderConstants.MAIN_EXCEPTION+TAG+"[postInputStreamRequest]"+e.toString());
		}
		return responseCode;

	}*/
	
	public static int postMundipaggRequest(String url,String xml) {

		  DefaultHttpClient mHttpClient = new DefaultHttpClient();
		  InputStream inputStream = null;
		  int responseCode = 0;
		  try{
		   HttpPost mHttpPost = new HttpPost(url);


		   mHttpPost.addHeader("Content-Type", "application/xml");
		   mHttpPost.setHeader("User-Agent", "android");
//		   mHttpPost.addHeader("MerchantKey","1ef11b35-c8d0-4ef2-bb6e-db04a8fae49d");
		   StringEntity se = new StringEntity(xml,HTTP.UTF_8);
		   //   se.setContentType("text/xml");
		   mHttpPost.setEntity(se);

		   HttpResponse response = mHttpClient.execute(mHttpPost);
		   //   
		   //    inputStream = response.getEntity().getContent();

		   responseCode=response.getStatusLine().getStatusCode();
		   Log.i("",TAG+"Mundipagg Response : "+ EntityUtils.toString(response.getEntity()));
		  }catch(Exception e){
		   Log.i("",PartyFinderConstants.MAIN_EXCEPTION+TAG+"[postInputStreamRequest]"+e.toString());
		  }
		  return responseCode;

		 }

	public static InputStream postInputStreamRequest(String url,String xml) {

		DefaultHttpClient mHttpClient = new DefaultHttpClient();
		InputStream inputStream = null;
		try{
			HttpPost mHttpPost = new HttpPost(url);
			mHttpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
			StringEntity se = new StringEntity(xml);
			se.setContentType("text/xml");
			//		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			//	    nameValuePairs.add(new BasicNameValuePair("xml",xml));

			mHttpPost.setEntity(se);
			//	    form = new UrlEncodedFormEntity(nameValuePairs,"UTF-8");
			//		mHttpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"UTF-8"));

			HttpResponse response = mHttpClient.execute(mHttpPost);

			HttpEntity resEntity = response.getEntity();  

			/*String resp = EntityUtils.toString(resEntity);
        Log.i(TAG,"postSyncXML srv response:"+resp);*/
			inputStream = response.getEntity().getContent();
		}catch(Exception e){
			Log.i("",PartyFinderConstants.MAIN_EXCEPTION+TAG+"[postInputStreamRequest]"+e.toString());
		}
		return inputStream;

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


	public static InputStream postInputStreamRequest(String url) {

		DefaultHttpClient mHttpClient = new DefaultHttpClient();
		InputStream inputStream = null;
		try{
			HttpPost mHttpPost = new HttpPost(url);
			//  mHttpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
			//  StringEntity se = new StringEntity(xml);
			//  se.setContentType("text/xml");



			//  mHttpPost.setEntity(se);

			HttpResponse response = mHttpClient.execute(mHttpPost);

			inputStream = response.getEntity().getContent();
		}catch(Exception e){
			Log.i("",PartyFinderConstants.MAIN_EXCEPTION+TAG+"[postInputStreamRequest]"+e.toString());
		}
		return inputStream;

	}
}
