package com.partyfinder.tabfragment;
import java.io.IOException;
import java.util.ArrayList;

import pt.fjss.pulltoupdatelibrary.IonRefreshListener;
import pt.fjss.pulltoupdatelibrary.PullToUpdateListView;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.partyfinder.R;
import com.partyfinder.CustomView.CustomAsyncTask;
import com.partyfinder.CustomView.CustomDialogBox;
import com.partyfinder.CustomView.TaskListener;
import com.partyfinder.adapters.UpcomingEventAdapter;
import com.partyfinder.app.utils.AppUtils;
import com.partyfinder.drawerfragment.FragmentOrderHistoryUpdate;
import com.partyfinder.managers.ContentManager;
import com.partyfinder.managers.SettingPrefrences;
import com.partyfinder.model.EventItem;
import com.partyfinder.model.UserLoginItem;
import com.partyfinder.widget.SlideHolder;






public class UpcomingEventsFragment extends Fragment{


	//	private PullToRefreshListView mListView;
	//	PullAndLoadListView mListView;
	PullToUpdateListView mListView;
	//	com.markupartist.android.widget.PullToRefreshListView mListView;
	private ArrayList<EventItem> mUpcomingEventItemList;
	private UpcomingEventAdapter upcomingEventAdaper;
	private RelativeLayout searchEventLayout,mimgView_Map_Layout,mimg_SearchIcon_layout;
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
	public static UpcomingEventsFragment fragment;
	private SlideHolder mSlideHolder;
	//	boolean refresh;

	public UpcomingEventsFragment(){

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	public static UpcomingEventsFragment getInstance(){
		if(fragment==null){
			fragment=new UpcomingEventsFragment();
		}else{
			return fragment;
		}
		return fragment;
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mContext=getActivity().getApplicationContext();
		mFragmentManager= getActivity().getSupportFragmentManager();
		mUserLoginItem=SettingPrefrences.getUserPrefrence(mContext);
		mSlideHolder=(SlideHolder) getActivity().findViewById(R.id.slideHolder);
		View v = inflater.inflate(R.layout.tab_upcoming_event, container, false);
		progressBarUpcomingEvent=(ProgressBar)v.findViewById(R.id.progressbar_upcomingEvent);
		progressBarUpcomingEvent.bringToFront();
		//		mListView = (ListView)v.findViewById(R.id.upcominglist);
		mListView = (PullToUpdateListView)v.findViewById(R.id.upcominglist);
		//		mListView.onRefreshComplete();
		//		mListView = (PullToRefreshListView)v.findViewById(R.id.upcominglist);
		//		mListView = (PullToUpdateListView)v.findViewById(R.id.upcominglist);

		mimg_SearchIcon_layout=(RelativeLayout) v.findViewById(R.id.img_SearchIcon_layout);
		mimgView_Map_Layout=(RelativeLayout) v.findViewById(R.id.imgView_Map_Layout);
		searchEventLayout=(RelativeLayout)v.findViewById(R.id.layouts_search);
		et_Eventsearch=(EditText)v.findViewById(R.id.etEventsearch);
		resetText=(ImageView)v.findViewById(R.id.imgViewReset);
		imageViewSearch=(ImageView)v.findViewById(R.id.img_SearchIcon);
		imageViewMap=(ImageView)v.findViewById(R.id.imgView_Map);



		mListView.setOnRefreshListener(new IonRefreshListener() {
			@Override
			public void onRefreshUp() {
				//your code

				isInternetConnected=AppUtils.isConnectingToInternet(getActivity());

				if (isInternetConnected) {
					//						loadActivityFeedData();
					Log.i("", "Inside pull to update :");
					loadRefreshUpcomingEventData();


				} else {


					CustomDialogBox.showAlert(getResources().getString(R.string.infoDialo),"Conecte seu dispositivo com conexão de Internet ativa", getActivity());


				}

			}

			@Override
			public void onRefeshDown() {
				//your code
				mSlideHolder.setEnabled(false);
				mListView.onRefreshDownComplete("");
			}

		});


		searchEventLayout.setOnClickListener(mClickListener);
		resetText.setOnClickListener(mClickListener);
		mimgView_Map_Layout.setOnClickListener(mClickListener);
		imageViewSearch.setOnClickListener(mClickListener);
		mimg_SearchIcon_layout.setOnClickListener(mClickListener);

		et_Eventsearch.addTextChangedListener(searchWatcher);

		isInternetConnected=AppUtils.isConnectingToInternet(getActivity());
		if (isInternetConnected) {
			//			refresh=false;
			loadUpcomingEventData();
		} else {
			CustomDialogBox.showAlert(getResources().getString(R.string.infoDialo),getResources().getString(R.string.NoInternetMessage), getActivity());
			//			Toast.makeText(getActivity(),"Conecte seu dispositivo com conexão de Internet ativa", Toast.LENGTH_SHORT).show();  
			progressBarUpcomingEvent.setVisibility(View.GONE);
		}

		/*	mListView.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				loadRefreshUpcomingEventData();
			}
		});*/
		/*
		mListView.setOnRefreshListener(new IonRefreshListener() {

			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				//				loadRefreshUpcomingEventData();
				mListView.bringToFront();
//				refresh=true;
				loadUpcomingEventData();
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

		return v;
	}






	OnClickListener mClickListener= new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.imgViewReset:
				et_Eventsearch.setText("");
				searchEventLayout.setVisibility(View.GONE);
				hideSoftKeyboard();

				break;

			case R.id.img_SearchIcon:
				searchEventLayout.setVisibility(View.VISIBLE);
				break;

			case R.id.img_SearchIcon_layout:
				searchEventLayout.setVisibility(View.VISIBLE);
				break;
			case R.id.imgView_Map_Layout:
				// map put here
				/*				
				   mFragmentManager=getFragmentManager();
			        mFragmentTransection=mFragmentManager.beginTransaction();
			        if(mFragment!=null){
			         mFragmentTransection.detach(mFragment);
			         mFragment=null;
			        }

			        mFragment=new GoogleMapFragmentEvent(mUpcomingEventItemList);

			     //   mFragment=new com.partyfinder.drawerfragment.GoogleMapV2(mWhathotList);


			        mFragmentTransection.replace(R.id.frameContainer, mFragment);

//			        mFragmentTransection.attach(mFragment);

			        mFragmentTransection.addToBackStack(null);
			   //     mFragmentTransection.setTransitionStyle(FragmentTransaction.TRANSIT_ENTER_MASK);
			        mFragmentTransection.commitAllowingStateLoss();*/
				try {


					mFragmentManager=getFragmentManager();
					mFragmentTransection=mFragmentManager.beginTransaction();
					if(mFragment!=null){
						mFragmentTransection.detach(mFragment);
						mFragment=null;
					}

					mFragment=new FragmentOrderHistoryUpdate();
					mFragment.setInitialSavedState(null);
					mFragmentTransection.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,R.anim.enter_from_left,R.anim.exit_to_right);
					mFragmentTransection.replace(R.id.frameContainer, mFragment);
					mFragmentTransection.addToBackStack(null);

					mFragmentTransection.commitAllowingStateLoss();
				} catch (Exception e) {
					// TODO: handle exception
				}

				break;
			}

		}
	};


	private void loadUpcomingEventData(){




		TaskListener mTaskListener=new TaskListener() {

			@Override
			public void updateUI() {
				// TODO Auto-generated method stub



				Log.i("", "onRefreshCalling calling");


				if(mUpcomingEventItemList!=null){
					Log.d("", "Upcoming Event List : "+mUpcomingEventItemList.size());
					if(mUpcomingEventItemList.size()>0){
						//						
						upcomingEventAdaper=new UpcomingEventAdapter(mContext, mUpcomingEventItemList);
						mListView.setAdapter(upcomingEventAdaper);
					}
				}
				mListView.onRefreshUpComplete();
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

		if(mAsyncTask!=null){
			mAsyncTask.cancel(true);
			mAsyncTask=null;
		}
		mAsyncTask=new CustomAsyncTask(mTaskListener, progressBarUpcomingEvent, mContext);
		mAsyncTask.execute();
	}

	private TextWatcher searchWatcher=new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence txt, int start, int before, int count) {
			int textlength = txt.length();
			if(mUpcomingEventItemList!=null && mUpcomingEventItemList.size()>0){
				ArrayList<EventItem> tempArrayList = new ArrayList<EventItem>();
				for(EventItem c: mUpcomingEventItemList){
					if (textlength <= c.getVcEventName().length()) {
						if (c.getVcEventName().toLowerCase().contains(txt.toString().toLowerCase())) {
							tempArrayList.add(c);
						}
					}
				}
				//	           listViewAdapter =new ListViewAdapter(mContext,EventActivity.this, tempArrayList);
				//	           eventListView.setAdapter(listViewAdapter);
				upcomingEventAdaper=new UpcomingEventAdapter(mContext, tempArrayList);
				mListView.setAdapter(upcomingEventAdaper);

			}

		}
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {


		}

		@Override
		public void afterTextChanged(Editable s) {

		}
	};




	/*	

	private void LoadEventData(){
		new UpcomingEventLoadTask(getActivity()).execute();
	}

	private class UpcomingEventLoadTask extends AsyncTask<String, Void, ArrayList<UpcomingEventItem>>{

		private ProgressDialog mDialog = null;
		private Context context = getActivity();

		public UpcomingEventLoadTask(Activity context){
			this.context = context;

		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub

			//progressBarUpcomingEvent.setVisibility(View.VISIBLE);
			super.onPreExecute();


		}


		@Override
		protected ArrayList<UpcomingEventItem> doInBackground(String... params) {
			// TODO Auto-generated method stub
			ArrayList<UpcomingEventItem> mList = null;
			String xml;
			xml = ContentManager.getXmlFromUrl(url);
			 Log.i("","Url Upcoming Event :"+xml);

			InputStream stream = new ByteArrayInputStream(xml.getBytes());
			try {
				mList = ContentManager.parse(stream);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Log.i("","Exception ariese"+e);

				e.printStackTrace();
			}
			Log.d("", "Event List :"+mList);
			return mList;

		}

		@Override
		protected void onPostExecute(ArrayList<UpcomingEventItem> upcomingEvents) {
			//mDialog.dismiss();
			progressBarUpcomingEvent.setVisibility(View.GONE);
			upcomingEventAdaper = new UpcomingEventAdapter(context, upcomingEvents);
			mListView.setAdapter(upcomingEventAdaper);		

		}

	}

		@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		LoadEventData();
	}



	 */


	/**
	 * Hides the soft keyboard
	 */
	public void hideSoftKeyboard() {

		InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(
				Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(et_Eventsearch.getWindowToken(), 0);
	}



	private void loadRefreshUpcomingEventData(){

		TaskListener mTaskListener=new TaskListener() {

			@Override
			public void updateUI() {

				mListView.onRefreshUpComplete();
				// TODO Auto-generated method stub
				if(tempArrayList!=null){
					Log.d("", "Upcoming Event List : "+tempArrayList.size());


					mUpcomingEventItemList.clear();
					//					upcomingEventAdaper.notifyDataSetChanged();
					mUpcomingEventItemList.addAll(tempArrayList);
					upcomingEventAdaper.notifyDataSetChanged();
					/*	if(tempArrayList.size()>0){
						upcomingEventAdaper=new UpcomingEventAdapter(mContext, mUpcomingEventItemList);
						mListView.setAdapter(upcomingEventAdaper);
					}*/
				}

			}

			@Override
			public void execute()  {
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

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		if(mAsyncTask!=null){
			mAsyncTask.cancel(true);
			mAsyncTask=null;
		}
		super.onDestroy();
	}



}

