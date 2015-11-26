package com.partyfinder;

import java.util.Arrays;

import com.partyfinder.AppConstant.PartyFinderConstants;
import com.partyfinder.CustomView.CustomDialogBox;
import com.partyfinder.app.utils.AppUtils;
import com.partyfinder.drawerfragment.FacebookLoginFragment;
import com.partyfinder.drawerfragment.FragmentOrderHistory;
import com.partyfinder.drawerfragment.UpcomingEventSkipLogin;
import com.partyfinder.model.PushTypeGeneric;
import com.partyfinder.tabfragment.UpcomingEventsFragment;


import android.app.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import android.support.v4.app.FragmentManager;

public class HomeActivity extends FragmentActivity {

	private Button loginButton;
	private Button signUpButton;

	private  PushTypeGeneric pushItem;
	boolean isInternetPresent = false;
	private Context mContext;
	private FragmentManager mFragmentManager;
	private FragmentTransaction mFragmentTransaction;
	private com.facebook.widget.LoginButton fbLoginBt;
	private FacebookLoginFragment fbLoginFrag;
	private TextView skipLogin;
	private Fragment mFragment;

	private FragmentTransaction mFragmentTransection;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_activity);
		mContext=this;


		pushItem=(PushTypeGeneric) getIntent().getSerializableExtra(PartyFinderConstants.GCM_PUSHEXTRA);
		mFragmentManager= getSupportFragmentManager();
		mFragmentTransaction=mFragmentManager.beginTransaction();

		if(fbLoginFrag!=null){
			mFragmentTransaction.detach(fbLoginFrag);
			fbLoginFrag=null;
		}

		fbLoginFrag = new FacebookLoginFragment();
		mFragmentTransaction.replace(R.id.fragmentContainer, fbLoginFrag);
		mFragmentTransaction .commitAllowingStateLoss();


		skipLogin=(TextView) findViewById(R.id.skipLogin);
		loginButton=(Button)findViewById(R.id.btn_login);
		fbLoginBt=(com.facebook.widget.LoginButton)findViewById(R.id.authButton);
		//	fbLoginBt.setBackgroundResource(R.drawable.facebookbutton);
		//	fbLoginBt.setText("Conecte com o Facebook");
		fbLoginBt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//			Toast.makeText(mContext,"Not Working...",Toast.LENGTH_LONG).show();
			}
		});
		fbLoginBt.setFragment(fbLoginFrag);
		fbLoginBt.setReadPermissions( Arrays.asList(
				new String[] {"user_status","user_education_history","user_interests","public_profile","user_birthday","email"}));//"age_range" ,,"user_location", "user_birthday", "user_likes"





		loginButton=(Button)findViewById(R.id.btn_login);
		loginButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent in= new Intent(HomeActivity.this,LoginActivity.class); 
				in.putExtra(PartyFinderConstants.GCM_PUSHEXTRA, pushItem);
				startActivity(in);
				finish();


			}
		});
		signUpButton=(Button)findViewById(R.id.btn_signup);
		signUpButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent in= new Intent(HomeActivity.this,RegisterationActivity.class); 	
				startActivity(in);

			}
		});

		skipLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				/*	mFragmentManager= getSupportFragmentManager();
		        mFragmentTransection=mFragmentManager.beginTransaction();
		        if(mFragment!=null){
		         mFragmentTransection.detach(mFragment);
		         mFragment=null;
		        }

		        mFragment=new UpcomingEventSkipLogin();

		        mFragmentTransection.replace(R.id.fragmentContainer, mFragment);
		        mFragmentTransection.addToBackStack(null);

		        mFragmentTransection.commitAllowingStateLoss();*/


				Intent theIntent = new Intent(mContext,SkipActivity.class);
				startActivity(theIntent);
				overridePendingTransition(R.anim.enter_from_right, R.anim.enter_from_right);
			
			}
		});


	}



	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		finish();
		super.onBackPressed();
	}




	@Override
	protected void onDestroy() {
		finish();
		super.onDestroy();
	}
	
	


}
