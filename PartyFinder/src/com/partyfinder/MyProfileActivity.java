package com.partyfinder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.partyfinder.AppConstant.PartyFinderConstants;
import com.partyfinder.CustomView.CustomAsyncTask;
import com.partyfinder.CustomView.CustomDialogBox;
import com.partyfinder.CustomView.TaskListener;
import com.partyfinder.adapters.UserActivityFeedsAdapter;
import com.partyfinder.app.utils.AppUtils;
import com.partyfinder.managers.ContentManager;
import com.partyfinder.managers.SettingPrefrences;
import com.partyfinder.model.FriendListItem;
import com.partyfinder.model.StatusCode;
import com.partyfinder.model.UserActivityFeeds;
import com.partyfinder.model.UserLoginItem;
import com.partyfinder.model.userPicUploadModel;
import com.partyfinder.parser.UserPicUploadParser;
import com.squareup.picasso.Picasso;

public class MyProfileActivity extends Activity {


	private ImageView ImgUserProfile,ImgUserProfile2;
	private ImageButton ImgViewSetting, ImgViewBack;
	private TextView tv_userName,tv_userLocation,tv_userAge,tv_userFriends,mbtn_myfriend_friends,mbtn_myfriend_favourite;
	private UserLoginItem mUserLoginItem;
	private Context mContext;
	private ListView ListView_UserFeed;
	private ProgressBar mProgressBar;
	private String mCurrentUserCode="";
	private UserLoginItem mLoginItem;
	private Object object;
	private	ArrayList<UserActivityFeeds >mActivityFeedItemList;
	private	UserActivityFeedsAdapter activityFeedAdapter;
	private FriendListItem mFrndListItem;
	private String mUsercode;
	private CustomAsyncTask mAsyncTask;
	private ImageView sendDeleteFrnd;
	private String sendXml,deleteXml;
	private StatusCode mSendStatusCode,mDeleteStatusCode;
	private LinearLayout mFooterLayout;
	private TextView mProfileHeading;
	private ImageView mSettingBtn;
	private RelativeLayout mplus_btn_layout,mbackButton_layout;
	private RelativeLayout backLayout,profileSetting,msettingButton_layout;
	TextView EditProfilePicTxt;
	String img_Decodable_Str;
	
	
@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile);
		mContext=this;
		mLoginItem=SettingPrefrences.getUserPrefrence(mContext);
		EditProfilePicTxt=(TextView) findViewById(R.id.EditProfilePicTxt);
		msettingButton_layout=(RelativeLayout) findViewById(R.id.settingButton_layout);
		mbackButton_layout=(RelativeLayout) findViewById(R.id.backButton_layout);
		tv_userName=(TextView)findViewById(R.id.profile_userName);
		mSettingBtn=(ImageView) findViewById(R.id.profile_SettingIcon);
		mProfileHeading=(TextView) findViewById(R.id.upcomingeventheading);
		tv_userFriends=(TextView)findViewById(R.id.userFrnds);
		tv_userAge=(TextView)findViewById(R.id.userAge);
		tv_userLocation=(TextView)findViewById(R.id.userLoacation);
		ImgUserProfile=(ImageView)findViewById(R.id.profile_userimage);
		ImgUserProfile2=(ImageView)findViewById(R.id.profile_userimage2);
		sendDeleteFrnd=(ImageView) findViewById(R.id.profile_sendplus);
		mFooterLayout=(LinearLayout) findViewById(R.id.profile_bottom_layout);
		mplus_btn_layout=(RelativeLayout) findViewById(R.id.plus_btn_layout);
		mbtn_myfriend_favourite=(TextView)findViewById(R.id.btn_myfriend_favourite);
		mbtn_myfriend_friends=(TextView)findViewById(R.id.btn_myfriend_friends);
		backLayout=(RelativeLayout) findViewById(R.id.backButton_layout);
		profileSetting=(RelativeLayout) findViewById(R.id.settingButton_layout);
		object=getIntent().getSerializableExtra(PartyFinderConstants.EXTRA_GET_FRIENDS_ITEM);
		
		if(object instanceof FriendListItem){
			mFrndListItem=(FriendListItem) object;
			mUsercode=mFrndListItem.getUserCode();
			mFooterLayout.setVisibility(View.VISIBLE);
			mFooterLayout.bringToFront();
			EditProfilePicTxt.setVisibility(View.GONE);
			mProfileHeading.setText("Perfil");
			mSettingBtn.setVisibility(View.GONE);
			Picasso.with(mContext).load(mFrndListItem.getImageUrl()).error(R.drawable.no_thumbnail).into(ImgUserProfile);
			tv_userFriends.setText(mFrndListItem.getUserFriendCount());
			tv_userAge.setText(mFrndListItem.getUserAge());
			tv_userLocation.setText(mFrndListItem.getUserCity()+" "+mFrndListItem.getUserState());
			tv_userName.setText(mFrndListItem.getUserName());
			if(mFrndListItem.getIsFriend().equalsIgnoreCase("1")){
				sendDeleteFrnd.setImageDrawable(getResources().getDrawable(R.drawable.minus));
				mplus_btn_layout.setOnClickListener(deleteFriend);
			}else {
				sendDeleteFrnd.setImageDrawable(getResources().getDrawable(R.drawable.sendrequest));
				mplus_btn_layout.setOnClickListener(sendFriendRequest);
			}
				mbtn_myfriend_friends.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent theIntent = new Intent(mContext,MyFriendsFriendList.class);
					theIntent.putExtra(PartyFinderConstants.EXTRA_GET_FRIENDS_ITEM, mFrndListItem);
					startActivity(theIntent);
				}
			});

			mbtn_myfriend_favourite.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent theIntent = new Intent(mContext,MyfriendFavourite.class);
					theIntent.putExtra(PartyFinderConstants.EXTRA_GET_FRIENDS_ITEM, mFrndListItem);
					startActivity(theIntent);
				}
			});
		}else{
			mUserLoginItem=SettingPrefrences.getUserPrefrence(mContext); 
			mFooterLayout.setVisibility(View.GONE);
			EditProfilePicTxt.setVisibility(View.VISIBLE);
			mCurrentUserCode =mUserLoginItem.getVc_user_code();
			Picasso.with(mContext).load(mUserLoginItem.getPf_user_image())
			.error(R.drawable.no_thumbnail).into(ImgUserProfile);
			tv_userName.setText(mUserLoginItem.getVc_user_name());
			tv_userFriends.setText(mUserLoginItem.getFriend_count());
			tv_userAge.setText(mUserLoginItem.getAge());
			tv_userLocation.setText(mUserLoginItem.getVc_user_city()+" "+mUserLoginItem.getVc_user_state());
			mUsercode=mUserLoginItem.getVc_user_code();
			sendDeleteFrnd.setVisibility(View.VISIBLE);
			sendDeleteFrnd.setImageDrawable(getResources().getDrawable(R.drawable.sendrequestblur));
			sendDeleteFrnd.setClickable(false);

			EditProfilePicTxt.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent theIntent = new Intent(mContext,UserProfilePicEditingActivity.class);
					startActivity(theIntent);
					overridePendingTransition(R.anim.enter_from_right, R.anim.enter_from_right);
				}
			});
	}
		mProgressBar=(ProgressBar)findViewById(R.id.progressBar1);
		ListView_UserFeed=(ListView)findViewById(R.id.userFeedsList);
		ImgViewSetting=(ImageButton)findViewById(R.id.profile_SettingIcon);
		ImgViewBack=(ImageButton)findViewById(R.id.profile_backBtn);
		ImgViewSetting.setOnClickListener(mOnClickListener);
		ImgViewBack.setOnClickListener(mOnClickListener);
		profileSetting.setOnClickListener(mOnClickListener);
		mbackButton_layout.setOnClickListener(mOnClickListener);
		msettingButton_layout.setOnClickListener(mOnClickListener);
		
		backLayout.setOnClickListener(mOnClickListener);
		loadActivityFeedData();
	}
	OnClickListener mOnClickListener=new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.profile_SettingIcon:
				Intent in =new Intent(MyProfileActivity.this,UserSettingActivity.class);
				startActivity(in);
				break;
	
			case R.id.profile_backBtn:
				Log.i("","back button clicked ");
				finish();
				break;
		
			case R.id.backButton_layout:
				Log.i("","back button clicked ");
				finish();
				break;
			case R.id.settingButton_layout:
				Intent intt =new Intent(MyProfileActivity.this,UserSettingActivity.class);
				startActivity(intt);
				break;
			}	
		}
	};

	OnClickListener sendFriendRequest=new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			sendFrndRequest();
			CustomDialogBox.showAlert(getResources().getString(R.string.infoDialo), getResources().getString(R.string.friendRequestSend_text),mContext);
		}
	};

	OnClickListener deleteFriend=new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			deleteFriend();
			CustomDialogBox.showAlert(getResources().getString(R.string.infoDialo), getResources().getString(R.string.friendDeleted_text),mContext);
		}
	};

	private void loadActivityFeedData(){
		if(mAsyncTask!=null){
			mAsyncTask.cancel(true);
			mAsyncTask=null;
		}
		mAsyncTask=new CustomAsyncTask(mListener, mProgressBar, mContext);
		mAsyncTask.execute();
	}

	TaskListener mListener=new TaskListener() {

		@Override
		public void updateUI() {
			// TODO Auto-generated method stub
			if(mActivityFeedItemList!=null){
				if(mActivityFeedItemList.size()>0){
					activityFeedAdapter = new UserActivityFeedsAdapter(mContext,mActivityFeedItemList);
					ListView_UserFeed.setAdapter(activityFeedAdapter);
				}
			}
		}
		@Override
		public void execute() {
			// TODO Auto-generated method stub
			try {
				mActivityFeedItemList = ContentManager.getInstance().parseUserActivityFeedItem1(mUsercode);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};


	private void sendFrndRequest(){
		if(mAsyncTask!=null){
			mAsyncTask.cancel(true);
			mAsyncTask=null;
		}
		mAsyncTask=new CustomAsyncTask(sendFrndRequest, mProgressBar, mContext);
		mAsyncTask.execute();
	}

	TaskListener sendFrndRequest=new TaskListener() {

		@Override
		public void updateUI() {
			// TODO Auto-generated method stub
		}

		@Override
		public void execute() {
			// TODO Auto-generated method stub
			ArrayList<String> data=new ArrayList<String>();
			data.add(mLoginItem.getVc_user_code());
			data.add(mFrndListItem.getUserCode());
			sendXml=AppUtils.preparedSendFriendRequestXml(data);
			mSendStatusCode=ContentManager.getInstance().parserSendFriendRequest(sendXml);
		}
	};

	private void deleteFriend(){
		if(mAsyncTask!=null){
			mAsyncTask.cancel(true);
			mAsyncTask=null;
		}
		mAsyncTask=new CustomAsyncTask(deleteFrndRequest, mProgressBar, mContext);
		mAsyncTask.execute();
	}

	TaskListener deleteFrndRequest=new TaskListener() {

		@Override
		public void updateUI() {
			// TODO Auto-generated method stub
			sendDeleteFrnd.setImageDrawable(getResources().getDrawable(R.drawable.sendrequest));
			mFrndListItem.setIsFriend("0");

		}

		@Override
		public void execute() {
			// TODO Auto-generated method stub
			ArrayList<String> data=new ArrayList<String>();
			data.add(mLoginItem.getVc_user_code());
			data.add(mFrndListItem.getUserCode());
			deleteXml=AppUtils.preparedSendFriendRequestXml(data);
			mDeleteStatusCode=ContentManager.getInstance().parserDeleteFriend(deleteXml);
		}
	};
	private void initScreen(){


		if(object instanceof FriendListItem){
			mFrndListItem=(FriendListItem) object;
			mUsercode=mFrndListItem.getUserCode();
			mFooterLayout.setVisibility(View.VISIBLE);
			mFooterLayout.bringToFront();
			mProfileHeading.setText("Perfil");
			mSettingBtn.setVisibility(View.GONE);
			Picasso.with(mContext).load(mFrndListItem.getImageUrl()).error(R.drawable.no_thumbnail).into(ImgUserProfile);
			tv_userFriends.setText(mFrndListItem.getUserFriendCount());
			tv_userAge.setText(mFrndListItem.getUserAge());
			tv_userLocation.setText(mFrndListItem.getUserCity()+" "+mFrndListItem.getUserState());
			tv_userName.setText(mFrndListItem.getUserName());
			if(mFrndListItem.getIsFriend().equalsIgnoreCase("1")){
				sendDeleteFrnd.setImageDrawable(getResources().getDrawable(R.drawable.minus));
				mplus_btn_layout.setOnClickListener(deleteFriend);
			}else {
				sendDeleteFrnd.setImageDrawable(getResources().getDrawable(R.drawable.sendrequest));
				mplus_btn_layout.setOnClickListener(sendFriendRequest);
			}
			mbtn_myfriend_friends.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent theIntent = new Intent(mContext,MyFriendsFriendList.class);
					theIntent.putExtra(PartyFinderConstants.EXTRA_GET_FRIENDS_ITEM, mFrndListItem);
					startActivity(theIntent);
				}
			});
			mbtn_myfriend_favourite.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent theIntent = new Intent(mContext,MyfriendFavourite.class);
					theIntent.putExtra(PartyFinderConstants.EXTRA_GET_FRIENDS_ITEM, mFrndListItem);
					startActivity(theIntent);
				}
			});
		}else{
			mUserLoginItem=SettingPrefrences.getUserPrefrence(mContext); 
			mFooterLayout.setVisibility(View.GONE);
			mCurrentUserCode =mUserLoginItem.getVc_user_code();
			Picasso.with(mContext).load(mUserLoginItem.getPf_user_image())
			.error(R.drawable.no_thumbnail).into(ImgUserProfile);
			tv_userName.setText(mUserLoginItem.getVc_user_name());
			tv_userFriends.setText(mUserLoginItem.getFriend_count());
			tv_userAge.setText(mUserLoginItem.getAge());
			tv_userLocation.setText(mUserLoginItem.getVc_user_city()+" "+mUserLoginItem.getVc_user_state());
			mUsercode=mUserLoginItem.getVc_user_code();

			sendDeleteFrnd.setVisibility(View.VISIBLE);
			sendDeleteFrnd.setImageDrawable(getResources().getDrawable(R.drawable.sendrequestblur));
			sendDeleteFrnd.setClickable(false);
		}
	}


	@Override
	protected void onResume() {
		// TODO Auto-generated method stub

		initScreen();
		mLoginItem=SettingPrefrences.getUserPrefrence(mContext);
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		mLoginItem=SettingPrefrences.getUserPrefrence(mContext);
		if(mAsyncTask!=null){
			mAsyncTask.cancel(true);
			mAsyncTask=null;
		}
		super.onPause();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		mLoginItem=SettingPrefrences.getUserPrefrence(mContext);
	
		super.onStart();
	}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		mLoginItem=SettingPrefrences.getUserPrefrence(mContext);
		if(mAsyncTask!=null){
			mAsyncTask.cancel(true);
			mAsyncTask=null;
		}
		super.onStop();
	}
	


	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		if(mAsyncTask!=null){
			mAsyncTask.cancel(true);
			mAsyncTask=null;
		}
		super.onDestroy();
	}
}
