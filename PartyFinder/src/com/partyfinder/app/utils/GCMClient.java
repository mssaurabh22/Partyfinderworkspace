package com.partyfinder.app.utils;

import java.io.IOException;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.partyfinder.AppConstant.PartyFinderConstants;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.util.Log;

public class GCMClient {
	
	private Context context;
	private GoogleCloudMessaging gcm;
	private String regid;
	private String msg;
	private String TAG="";
	
	public GCMClient(Context context){
		this.context=context;
		TAG="GCM CLIENT LOG";
	}
	@SuppressWarnings("unchecked")
	public void getGCMUsingBackground(){
		if (gcm == null) {
            gcm = GoogleCloudMessaging.getInstance(context);
        }
			regid = getRegistrationId();
			Log.i("",TAG+"   :  "+regid );
			if(regid.isEmpty()){
				
				new AsyncTask(){

					@Override
					protected String doInBackground(Object... params) {

						String msg = "";
					    try {
					        if (gcm == null) {
					            gcm = GoogleCloudMessaging.getInstance(context);
					        }
					        regid = gcm.register(PartyFinderConstants.SENDER_ID);
					        msg = "Device registered, registration ID=" + regid;

					        // You should send the registration ID to your server over HTTP,
					        // so it can use GCM/HTTP or CCS to send messages to your app.
					        // The request to your server should be authenticated if your app
					        // is using accounts.
//					        sendRegistrationIdToBackend();

					        // For this demo: we don't need to send it because the device
					        // will send upstream messages to a server that echo back the
					        // message using the 'from' address in the message.

					        // Persist the regID - no need to register again.
					        storeRegistrationId(context, regid);
//					        Log.i("",TAG+"   :  "+regid );
					    } catch (IOException ex) {
					        msg = "Error :" + ex.getMessage();
					        // If there is an error, don't just keep trying to register.
					        // Require the user to click a button again, or perform
					        // exponential back-off.
					    }
					    return msg;

					}
					
				}.execute(null,null,null);
//				gcm = GoogleCloudMessaging.getInstance(context);
//				regid = gcm.register("587523728313");
			
			}
	}
	private SharedPreferences getGCMPreferences(Context context) {
	    // This sample app persists the registration ID in shared preferences, but
	    // how you store the regID in your app is up to you.
	    return context.getSharedPreferences(PartyFinderConstants.GCM_SHAREDKEY,
	            Context.MODE_PRIVATE);
	}
	private int getAppVersion(Context context) {
	    try {
	        PackageInfo packageInfo = context.getPackageManager()
	                .getPackageInfo(context.getPackageName(), 0);
	        return packageInfo.versionCode;
	    } catch (NameNotFoundException e) {
	        // should never happen
	        throw new RuntimeException("Could not get package name: " + e);
	    }
	}
	private void storeRegistrationId(Context context, String regId) {
	    final SharedPreferences prefs = getGCMPreferences(context);
	    int appVersion = getAppVersion(context);
	   
//	    Log.i(TAG, "Saving regId on app version " + appVersion);
	    SharedPreferences.Editor editor = prefs.edit();
	    editor.putString(PartyFinderConstants.GCM_SHAREDKEY, regId);
	    editor.putInt(PartyFinderConstants.APP_VERSION, appVersion);
	    editor.commit();
	}
	public String getRegistrationId() {
	    final SharedPreferences prefs = getGCMPreferences(context);
	    String registrationId = prefs.getString(PartyFinderConstants.GCM_SHAREDKEY, "");
	    if (registrationId.isEmpty()) {
	    	 return "";
	    }  
	    
	    // Check if app was updated; if so, it must clear the registration ID
	    // since the existing regID is not guaranteed to work with the new
	    // app version.
	    int registeredVersion = prefs.getInt(PartyFinderConstants.APP_VERSION, Integer.MIN_VALUE);
	    int currentVersion = getAppVersion(context);
	    if (registeredVersion != currentVersion) {
//	    	if(AppConstants.GCM_MESSAGE_TEST_LOG)
//	        Log.i(TAG, TAG+" App version changed.");
	        return "";
	    }
	    return registrationId;
	}
}
