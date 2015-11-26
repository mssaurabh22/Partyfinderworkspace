package com.partyfinder.drawerfragment;

import java.util.ArrayList;



import com.costum.android.widget.PullAndLoadListView;
import com.costum.android.widget.PullToRefreshListView.OnRefreshListener;
import com.partyfinder.R;
import com.partyfinder.CustomView.CustomAsyncTask;
import com.partyfinder.CustomView.CustomDialogBox;
import com.partyfinder.CustomView.TaskListener;
import com.partyfinder.adapters.UpcomingEventAdapter;
import com.partyfinder.adapters.UpcomingEventSkipLoginAdapter;
import com.partyfinder.app.utils.AppUtils;
import com.partyfinder.managers.ContentManager;
import com.partyfinder.managers.SettingPrefrences;
import com.partyfinder.model.EventItem;
import com.partyfinder.model.UserLoginItem;
import com.partyfinder.widget.SlideHolder;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.AbsListView.OnScrollListener;

public class UpcomingEventSkipLogin extends Fragment{
	
//	private PullToRefreshListView mListView;
	PullAndLoadListView mListView;
//	PullToUpdateListView mListView;
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
	private SlideHolder mSlideHolder;
	
	public UpcomingEventSkipLogin() {
		// TODO Auto-generated constructor stub
	}

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		mContext=getActivity().getApplicationContext();
		mFragmentManager= getActivity().getSupportFragmentManager();
		mUserLoginItem=SettingPrefrences.getUserPrefrence(mContext);
		
		View v = inflater.inflate(R.layout.upcoming_event_skip_login_layout, container, false);
		
		progressBarUpcomingEvent=(ProgressBar)v.findViewById(R.id.progressbar_upcomingEvent);
//		mListView = (ListView)v.findViewById(R.id.upcominglist);
		mSlideHolder=(SlideHolder) getActivity().findViewById(R.id.slideHolder);
//		mListView = (PullToRefreshListView)v.findViewById(R.id.upcominglist);
		mListView = (PullAndLoadListView)v.findViewById(R.id.upcominglist);
		searchEventLayout=(RelativeLayout)v.findViewById(R.id.layouts_search);
		et_Eventsearch=(EditText)v.findViewById(R.id.etEventsearch);
		resetText=(ImageView)v.findViewById(R.id.imgViewReset);
		imageViewSearch=(ImageView)v.findViewById(R.id.img_SearchIcon);
		imageViewMap=(ImageView)v.findViewById(R.id.imgView_Map);
		back_btn_events=(ImageView) v.findViewById(R.id.back_btn_events);

		
		mListView.setOnRefreshListener(new OnRefreshListener() {
			
			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				loadUpcomingEventData();
			}
		});
		
	/*	mListView.setOnRefreshListener(new IonRefreshListener() {
			  @Override
			    public void onRefreshUp() {
			    //your code
//					mSlideHolder.setEnabled(false);
					isInternetConnected=AppUtils.isConnectingToInternet(getActivity());

					if (isInternetConnected) {
						//						loadActivityFeedData();
						Log.i("", "Inside pull to update :");
						loadUpcomingEventData();
						

					} else {
						

						CustomDialogBox.showAlert(getResources().getString(R.string.infoDialo),"Conecte seu dispositivo com conexão de Internet ativa", getActivity());


					}
				
			    }

			    @Override
			    public void onRefeshDown() {
			    //your code
			    	mSlideHolder.setEnabled(false);
			    	mListView.onRefreshDownComplete(null);
			    }

			        });*/
		mListView.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				mSlideHolder.setEnabled(true);
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				mSlideHolder.setEnabled(false);
			}
		});
		
		
/*
		searchEventLayout.setOnClickListener(mClickListener);
		resetText.setOnClickListener(mClickListener);
		imageViewMap.setOnClickListener(mClickListener);
		imageViewSearch.setOnClickListener(mClickListener);
		et_Eventsearch.addTextChangedListener(searchWatcher);*/

		isInternetConnected=AppUtils.isConnectingToInternet(getActivity());
		if (isInternetConnected) {
			loadUpcomingEventData();
		} else {
			CustomDialogBox.showAlert(getResources().getString(R.string.infoDialo),getResources().getString(R.string.NoInternetMessage), getActivity());
//			Toast.makeText(getActivity(),"Conecte seu dispositivo com conexão de Internet ativa", Toast.LENGTH_SHORT).show();  
			progressBarUpcomingEvent.setVisibility(View.GONE);
		}
		
		back_btn_events.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
			}
		});

		
		// TODO Auto-generated method stub
		return v;
	}
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		
		if(mAsyncTask!=null){
			mAsyncTask.cancel(true);
			mAsyncTask=null;
		}
		super.onPause();
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		
		if(mAsyncTask!=null){
			mAsyncTask.cancel(true);
			mAsyncTask=null;
		}
		super.onStop();
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
	
	
	
	public void hideSoftKeyboard() {

		InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(
				Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(et_Eventsearch.getWindowToken(), 0);
	}
	
	
	
	/*private void loadRefreshUpcomingEventData(){
		
		TaskListener mTaskListener=new TaskListener() {
			
			@Override
			public void updateUI() {
				mListView.onRefreshComplete();
				// TODO Auto-generated method stub
				if(tempArrayList!=null){
					Log.d("", "Upcoming Event List : "+tempArrayList.size());
					
					
					mUpcomingEventItemList.clear();
					upcomingEventAdaper.notifyDataSetChanged();
					mUpcomingEventItemList.addAll(tempArrayList);
					upcomingEventAdaper.notifyDataSetChanged();
					if(tempArrayList.size()>0){
						upcomingEventAdaper=new UpcomingEventAdapter(mContext, mUpcomingEventItemList);
						mListView.setAdapter(upcomingEventAdaper);
					}
				}
				
			}
			
			@Override
			public void execute() {
				// TODO Auto-generated method stub
				try {
					Log.i("", "execute() calling");
					tempArrayList=ContentManager.getInstance().parseUpcomingEventItem(mUserLoginItem);
				} catch (Exception e) {
					// TODO: handle exception
				}
				
			}
		};
		if(mAsyncTask!=null){
			mAsyncTask.cancel(true);
			mAsyncTask=null;
		}
			mAsyncTask=new CustomAsyncTask(mTaskListener, mContext);
			mAsyncTask.execute();
	}
	*/
}
