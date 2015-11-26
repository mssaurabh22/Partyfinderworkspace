package com.partyfinder;

import java.util.ArrayList;

import com.partyfinder.AppConstant.PartyFinderConstants;
import com.partyfinder.CustomView.CustomAsyncTask;
import com.partyfinder.CustomView.TaskListener;
import com.partyfinder.adapters.GetFriendsFriendAdapter;
import com.partyfinder.managers.ContentManager;
import com.partyfinder.managers.SettingPrefrences;
import com.partyfinder.model.FriendListItem;
import com.partyfinder.model.UserLoginItem;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;

public class MyFriendsFriendList extends Activity{
	
	private Context mContext;
	private ListView mListView;
	private GetFriendsFriendAdapter mFriendListAdapter;
	private ImageView mBackBttn;
	private ArrayList<FriendListItem> mGetFriendList;
	private FriendListItem mFrndListItem;
	private UserLoginItem mLoginItem;
	private CustomAsyncTask mAsyncTask;
	private ProgressBar progressBarLoadFriendList;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_friends_friend_layout);
		
		Object object=getIntent().getSerializableExtra(PartyFinderConstants.EXTRA_GET_FRIENDS_ITEM);
		mFrndListItem = (FriendListItem) object;
		
		mContext=this;
		mLoginItem=SettingPrefrences.getUserPrefrence(mContext);
		mBackBttn=(ImageView) findViewById(R.id.rel_btn_back);
		mBackBttn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		mListView = (ListView) findViewById(R.id.listvIew_accept_friends_req);
		progressBarLoadFriendList=(ProgressBar) findViewById(R.id.proBar_loadfriends);
		loadSearchFriendList();
	}

	
	private void loadSearchFriendList(){
		if(mAsyncTask!=null){
			mAsyncTask.cancel(true);
			mAsyncTask=null;
		}
			mAsyncTask=new CustomAsyncTask(mSearchFrndTask, progressBarLoadFriendList, mContext);
			mAsyncTask.execute();
	}
	
	TaskListener mSearchFrndTask=new TaskListener() {
		
		@Override
		public void updateUI() {
			// TODO Auto-generated method stub
			mFriendListAdapter = new GetFriendsFriendAdapter(mContext, mGetFriendList);
			mListView.setAdapter(mFriendListAdapter);
			
		}
		
		@Override
		public void execute() {
			// TODO Auto-generated method stub
			try {
				mGetFriendList=ContentManager.getInstance().parseGetFriendListItems(mFrndListItem);
		Log.i("", "FriendsFriend List..."+mGetFriendList.size());
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	};
}
