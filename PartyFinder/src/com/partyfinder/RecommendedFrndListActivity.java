package com.partyfinder;

import java.io.IOException;
import java.util.ArrayList;

import com.partyfinder.CustomView.CustomAsyncTask;
import com.partyfinder.CustomView.TaskListener;
import com.partyfinder.adapters.RecommendedFriendListAdapter;
import com.partyfinder.managers.ContentManager;
import com.partyfinder.managers.SettingPrefrences;
import com.partyfinder.model.FriendListItem;
import com.partyfinder.model.UserLoginItem;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;

public class RecommendedFrndListActivity extends Fragment{

	private Context mContext;
	private CustomAsyncTask mAsyncTask;
	private ListView mListView;
	private UserLoginItem mUserLoginItem;
	private ArrayList<FriendListItem> mFriendListItem;
	private ArrayList<FriendListItem>selectedFrnd;
	private ProgressBar mProgressBar;
	private ImageView okSelectedRecommendFrnd,backBtnRecommendFrnd;
	private RecommendedFriendListAdapter mRecommendFrndListAdapter;
	private FragmentManager mFragmentManager;


public RecommendedFrndListActivity(){
	
}
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		ViewGroup root = (ViewGroup) inflater.inflate(R.layout.recommended_frnd_list_layout, null);
		mContext=getActivity().getApplicationContext();
		mFragmentManager = getActivity().getSupportFragmentManager();



		mListView=(ListView) root.findViewById(R.id.friend_list);
		mUserLoginItem=SettingPrefrences.getUserPrefrence(mContext);
		mProgressBar=(ProgressBar) root.findViewById(R.id.progressBar);
		okSelectedRecommendFrnd=(ImageView)root. findViewById(R.id.okSelectedRecommendFrnd);
		backBtnRecommendFrnd=(ImageView)root. findViewById(R.id.back_btn_recommendFrnd);
		executeRecommendFrndList();


		backBtnRecommendFrnd.setOnClickListener(recommendFrndListener);
		okSelectedRecommendFrnd.setOnClickListener(recommendFrndListener);
		
		return root;
	}



	OnClickListener recommendFrndListener=new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			selectedFrnd=new ArrayList<FriendListItem>();
			if(mRecommendFrndListAdapter!=null){
			ArrayList<FriendListItem>selected=mRecommendFrndListAdapter.getmFrndListItem();
			
			for(int i=0;i<selected.size();i++){
				if(selected.get(i).isCheckStatus()){
					selectedFrnd.add(selected.get(i));

				}
			}
			}
			VenuesDetailsActivity.getInstance().displayDialog(selectedFrnd,getActivity());
			
			mFragmentManager.popBackStack();
			//			onResume();
		}
	};


	public void executeRecommendFrndList(){
		if(mAsyncTask!=null){
			mAsyncTask.cancel(true);
			mAsyncTask=null;
		}
		mAsyncTask=new CustomAsyncTask(mRecommendFrndListener,mProgressBar,mContext);
		mAsyncTask.execute();
	}


	TaskListener mRecommendFrndListener=new TaskListener() {

		@Override
		public void updateUI() {
			// TODO Auto-generated method stub
			if(mFriendListItem!=null){
				if(mFriendListItem.size()>0){
					Log.i("", "VenuesItems size: "+mFriendListItem);
					mRecommendFrndListAdapter=new RecommendedFriendListAdapter(mContext, mFriendListItem);
					mListView.setAdapter(mRecommendFrndListAdapter);
				}
			}
		}

		@Override
		public void execute() {
			// TODO Auto-generated method stub
			try {
				mFriendListItem=ContentManager.getInstance().parseGetFriendListItems(mUserLoginItem);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	};

	//	protected void onResume() {
	//		
	//		VenuesDetailsActivity.getInstance().displayDialog(selectedFrnd);
	//		finish();
	//		
	//	};

}
