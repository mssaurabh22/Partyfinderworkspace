package com.partyfinder;


import com.partyfinder.AppConstant.PartyFinderConstants;
import com.partyfinder.CustomView.CustomDialogBox;
import com.partyfinder.app.utils.AppUtils;
import com.partyfinder.app.utils.GCMClient;
import com.partyfinder.managers.SettingPrefrences;
import com.partyfinder.model.PushTypeGeneric;



import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public class SplashActivity  extends Activity{

	private String TAG="SplashActivityClass";
	private GCMClient gcmClient;
	private String regId;
	private boolean isInternetConnected=false;
	private Context mContext;
	private PushTypeGeneric pushItem;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
/*		new Thread(new Runnable() {
		    @Override
		    public void run() { Mint.initAndStartSession(SplashActivity.this,"f46bfbcb"); } }).start();*/
		
//		Mint.initAndStartSession(SplashActivity.this, "f46bfbcb");
		setContentView(R.layout.splash_screen_layout);
		mContext=this;

		pushItem=(PushTypeGeneric) getIntent().getSerializableExtra(PartyFinderConstants.GCM_PUSHEXTRA);

		if(pushItem!=null){
			Log.i(TAG, "Name: "+pushItem.getMessage());
		}

		isInternetConnected=AppUtils.isConnectingToInternet(mContext);
		if (isInternetConnected) {

			gcmClient=new GCMClient(this);
			regId=gcmClient.getRegistrationId();
			if(regId.isEmpty()){

				gcmClient.getGCMUsingBackground();
			}

			Log.i("",TAG+"Registration Id : "+regId);
			new Handler().postDelayed(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub

					if(SettingPrefrences.getUserPrefrence(SplashActivity.this)!=null){
						Log.i("",TAG+"mainactivity is caliling");
						Intent in=new Intent(SplashActivity.this,MainActivity.class);
						in.putExtra(PartyFinderConstants.GCM_PUSHEXTRA, pushItem);
						startActivity(in);
						finish();
					}else{
						Log.i("",TAG+"Home activity is caliling");
						Intent in=new Intent(SplashActivity.this,HomeActivity.class);
						in.putExtra(PartyFinderConstants.GCM_PUSHEXTRA, pushItem);
						startActivity(in);
						finish();


					}

				}

			},  3*1000); // wait for 5 seconds
		} else {
			CustomDialogBox.showAlert(getResources().getString(R.string.infoDialo),getResources().getString(R.string.NoInternetMessage), mContext);
//			Toast.makeText(getApplicationContext(),"Conecte seu dispositivo com conexão de Internet ativa", Toast.LENGTH_SHORT).show();  

		}










	}


}