package com.partyfinder;

import java.util.ArrayList;

import com.costum.android.widget.PullAndLoadListView;
import com.costum.android.widget.PullToRefreshListView.OnRefreshListener;
import com.partyfinder.CustomView.CustomAsyncTask;
import com.partyfinder.CustomView.CustomDialogBox;
import com.partyfinder.CustomView.TaskListener;
import com.partyfinder.adapters.UpcomingEventSkipLoginAdapter;
import com.partyfinder.app.utils.AppUtils;
import com.partyfinder.managers.ContentManager;
import com.partyfinder.model.EventItem;
import com.partyfinder.model.UserLoginItem;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

public class SkipActivity extends Activity{

//	private PullToRefreshListView mListView;
	PullAndLoadListView mListView;
	private ArrayList<EventItem> mUpcomingEventItemList;
	private UpcomingEventSkipLoginAdapter upcomingEventAdaper;
	private RelativeLayout searchEventLayout;
	private EditText et_Eventsearch;
	private ImageView resetText, imageViewMap,imageViewSearch;
	private boolean isInternetConnected=false;
	private  ProgressBar progressBarUpcomingEvent;
	private Context mContext;
	private UserLoginItem mUserLoginItem;
	private CustomAsyncTask mAsyncTask;
	private Fragment mFragment;
	private FragmentManager mFragmentManager;
	private FragmentTransaction mFragmentTransection;
	private ArrayList<EventItem> tempArrayList;
	private ImageView back_btn_events;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.upcoming_event_skip_login_layout);
		mContext = this;
		
		
		progressBarUpcomingEvent=(ProgressBar)findViewById(R.id.progressbar_upcomingEvent);
//		mListView = (ListView)v.findViewById(R.id.upcominglist);
		mListView = (PullAndLoadListView)findViewById(R.id.upcominglist);
		searchEventLayout=(RelativeLayout)findViewById(R.id.layouts_search);
		et_Eventsearch=(EditText)findViewById(R.id.etEventsearch);
		resetText=(ImageView)findViewById(R.id.imgViewReset);
		imageViewSearch=(ImageView)findViewById(R.id.img_SearchIcon);
		imageViewMap=(ImageView)findViewById(R.id.imgView_Map);
		back_btn_events=(ImageView)findViewById(R.id.back_btn_events);
		
		
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				
			     
			     finish();
			     overridePendingTransition(R.anim.enter_from_left,R.anim.exit_to_right);
			}
		});
		
	mListView.setOnRefreshListener(new OnRefreshListener() {
			
			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				loadUpcomingEventData();
			}
		});
		
/*
		searchEventLayout.setOnClickListener(mClickListener);
		resetText.setOnClickListener(mClickListener);
		imageViewMap.setOnClickListener(mClickListener);
		imageViewSearch.setOnClickListener(mClickListener);
		et_Eventsearch.addTextChangedListener(searchWatcher);*/

		isInternetConnected=AppUtils.isConnectingToInternet(mContext);
		if (isInternetConnected) {
			loadUpcomingEventData();
		} else {
			CustomDialogBox.showAlert(getResources().getString(R.string.infoDialo),getResources().getString(R.string.NoInternetMessage), mContext);
//			Toast.makeText(getActivity(),"Conecte seu dispositivo com conexão de Internet ativa", Toast.LENGTH_SHORT).show();  
			progressBarUpcomingEvent.setVisibility(View.GONE);
		}
		
		back_btn_events.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				overridePendingTransition(R.anim.enter_from_left,R.anim.exit_to_right);
			}
		});
		
		
		
	}
	
	private void loadUpcomingEventData(){
		if(mAsyncTask!=null){
			mAsyncTask.cancel(true);
			mAsyncTask=null;
		}
			mAsyncTask=new CustomAsyncTask(mTaskListener, progressBarUpcomingEvent, mContext);
			mAsyncTask.execute();
	}
	
	
	TaskListener mTaskListener=new TaskListener() {
		
		@Override
		public void updateUI() {
			mListView.onRefreshComplete();
			// TODO Auto-generated method stub
			if(mUpcomingEventItemList!=null){
				
				Log.d("", "Upcoming Event List : "+mUpcomingEventItemList.size());
				if(mUpcomingEventItemList.size()>0){
					upcomingEventAdaper=new UpcomingEventSkipLoginAdapter(mContext, mUpcomingEventItemList);
					mListView.setAdapter(upcomingEventAdaper);
				}
			}
			
		}
		
		@Override
		public void execute() {
			// TODO Auto-generated method stub
			try {
				Log.i("", "execute() calling");
				mUpcomingEventItemList=ContentManager.getInstance().parseUpcomingEventItem(mUserLoginItem);
			} catch (Exception e) {
				// TODO: handle exception
			}
			
		}
	};


	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		if(mAsyncTask!=null){
			mAsyncTask.cancel(true);
			mAsyncTask=null;
		}
		super.onPause();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
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
