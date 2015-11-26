
package com.partyfinder.tabfragment;

import java.io.IOException;
import java.util.ArrayList;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.costum.android.widget.PullAndLoadListView;
import com.costum.android.widget.PullToRefreshListView.OnRefreshListener;
import com.partyfinder.R;
import com.partyfinder.AppConstant.PartyFinderConstants;
import com.partyfinder.CustomView.CustomAsyncTask;
import com.partyfinder.CustomView.CustomDialogBox;
import com.partyfinder.CustomView.TaskListener;
import com.partyfinder.adapters.ActivityFeedAdapter;
import com.partyfinder.app.utils.AppUtils;
import com.partyfinder.drawerfragment.FragmentOrderHistoryUpdate;
import com.partyfinder.managers.ContentManager;
import com.partyfinder.managers.SettingPrefrences;
import com.partyfinder.model.ActivityFeedItem;
import com.partyfinder.model.UserLoginItem;
import com.partyfinder.widget.SlideHolder;







public class ActivityFeedFragment extends Fragment{



	//	ListView mListView;
	PullAndLoadListView mListView;
	//	PullToRefreshListView mListView;
	//	PullToUpdateListView mListView;
	//	com.markupartist.android.widget.PullToRefreshListView mListView;
	ArrayList<ActivityFeedItem> mActivityFeedItemList;
	ActivityFeedAdapter activityFeedAdapter;
	public static ActivityFeedFragment mActivityFeedFragment;
	//	private LoadActivityFeedTask mLoadActivityFeedTask;
	private boolean isInternetConnected= false;
	private ProgressBar mProgBar;
	private Context mContext;
	private UserLoginItem mUserLoginItem;
	private CustomAsyncTask mAsyncTask;
	private ArrayList<ActivityFeedItem> tempArrayList;
	private ImageView order_history_icon;
	private Fragment mFragment;
	private FragmentManager mFragmentManager;
	private FragmentTransaction mFragmentTransection;
	private Dialog mZoomDialog;
	private RelativeLayout morder_history_icon_Layout;
	private String BUNDLE_STRING = "ACTIVITYFEED_SAVEDLIST";
	private String BUNDLE_STRING_INSIDE_SAVEINSTANCE = "ACTIVITYFEED_SAVEDLIST_ONSTANCE";
	private Bundle SavedData;
	private SlideHolder mSlideHolder;


	String url = PartyFinderConstants.URL_ACTIVITY_FEED;

	public ActivityFeedFragment(){

	}
	public static ActivityFeedFragment fragment;

	public static ActivityFeedFragment getInstance(){


		if(fragment==null){

			fragment=new ActivityFeedFragment();

		}else{

			return fragment;// = ActivityFeedFragment.mActivityFeedFragment.getArguments().getBundle("mContent");
			//					(ActivityFeedFragment) getActivity().getSupportFragmentManager().getFragment(
			//                    savedInstanceState, "mContent");;
		}

		return fragment;
	}


	@SuppressWarnings("unchecked")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// inflate the layout for this fragment
		mContext=getActivity().getApplicationContext();
		mFragmentManager=getActivity().getSupportFragmentManager();
		//		fragment.setRetainInstance(true);
		//		mFragmentManager.saveFragmentInstanceState(fragment);
		mUserLoginItem=SettingPrefrences.getUserPrefrence(mContext);
		View v = inflater.inflate(R.layout.tab_activity_feed, container, false);

		mProgBar=(ProgressBar)v.findViewById(R.id.proBar_activityfeed);
		morder_history_icon_Layout = (RelativeLayout) v.findViewById(R.id.order_history_icon_Layout);
		mSlideHolder=(SlideHolder) getActivity().findViewById(R.id.slideHolder);
		//		 mListView = (PullToRefreshListView)v.findViewById(R.id.activity_feed_list);
		mListView = (PullAndLoadListView)v.findViewById(R.id.activity_feed_list);
		//		mListView = (PullToUpdateListView)v.findViewById(R.id.activity_feed_list);


		/*		mListView.setPullMode(PullToUpdateListView.MODE.UP_AND_DOWN);
//		mListView.setAutoLoad(true, 8);
		mListView.setPullMessageColor(Color.GREEN);
		mListView.setLoadingMessage("Updating");*/
		order_history_icon=(ImageView) v.findViewById(R.id.order_history_icon);

		isInternetConnected=AppUtils.isConnectingToInternet( getActivity());

		if (isInternetConnected) {


			loadActivityFeedData();

		} else {
			CustomDialogBox.showAlert(getResources().getString(R.string.infoDialo),getResources().getString(R.string.NoInternetMessage), getActivity());
			//			Toast.makeText(getActivity(),"Conecte seu dispositivo com conexão de Internet ativa", Toast.LENGTH_SHORT).show();  

		}


		morder_history_icon_Layout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

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
			}
		});
		mListView.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				loadActivityFeedData();
			}
		});

		/*	mListView.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				loadActivityFeedData();
			}
		});*/
		/*
		mListView.setOnRefreshListener(new IonRefreshListener() {
			  @Override
			    public void onRefreshUp() {
			    //your code
//					mSlideHolder.setEnabled(false);
					isInternetConnected=AppUtils.isConnectingToInternet(getActivity());

					if (isInternetConnected) {
						//						loadActivityFeedData();
						Log.i("", "Inside pull to update :");
						loadActivityFeedData();


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


		/*mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				view.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub


					}
				});
			}
		});*/




		/*
		mListView.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				isInternetConnected=AppUtils.isConnectingToInternet(getActivity());

				if (isInternetConnected) {
					//						loadActivityFeedData();
					loadActivityFeedData();

				} else {


					CustomDialogBox.showAlert(getResources().getString(R.string.infoDialo),"Conecte seu dispositivo com conexão de Internet ativa", getActivity());


				}


			}
		});*/



		//		loadActivityFeedData();



		return v;
	}


	private void loadActivityFeedData(){
		TaskListener mTaskListener= new TaskListener() {

			@Override
			public void updateUI() {
				// TODO Auto-generated method stub
				mListView.onRefreshComplete();
				//				mListView.onRefreshUpComplete();
				if(mActivityFeedItemList!=null){
					Log.i("", "ActivityFeed List : "+mActivityFeedItemList.size());
					if(mActivityFeedItemList.size()>0){

						activityFeedAdapter = new ActivityFeedAdapter(mContext,mActivityFeedItemList,getActivity());
						mListView.setAdapter(activityFeedAdapter);
					}
				}
			}

			@Override
			public void execute() {
				// TODO Auto-generated method stub
				try {
					Log.d("", "execute() Calling..... : ");

					mActivityFeedItemList = ContentManager.getInstance().parseActivityFeedItem(mUserLoginItem);
					Log.i("", "Inside pull to update : Ascyn task");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		if(mAsyncTask!=null){
			mAsyncTask.cancel(true);
			mAsyncTask=null;
		}
		mAsyncTask=new CustomAsyncTask(mTaskListener, mProgBar, mContext);
		mAsyncTask.execute();
	}


	/*	private class LoadActivityFeedTask extends AsyncTask<String, Void, ArrayList<ActivityFeedItem>>{

		private ProgressDialog mDialog = null;
		private Context context = getActivity();



		public LoadActivityFeedTask(Activity context) {
			this.context = context;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
				mDialog = new ProgressDialog(context);
			mDialog.setMessage("Loading...");
			mDialog.show();
			mProgBar.setVisibility(View.VISIBLE);

		}




		@SuppressWarnings("static-access")
		@Override
		protected ArrayList<ActivityFeedItem> doInBackground(String... params) {
			// TODO Auto-generated method stub
			ArrayList<ActivityFeedItem> mList = null;
			String xml;
			xml = ContentManager.getInstance().getXmlFromUrl(url);
			InputStream stream = new ByteArrayInputStream(xml.getBytes());
			try {
				mList = ContentManager.getInstance().parseActivityFeedItem(stream);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Log.d("", "ActivityFeed List : "+mList);
			return mList;
		}

		@Override
		protected void onPostExecute(ArrayList<ActivityFeedItem> activityFeeds) {
			// TODO Auto-generated method stub
			//mDialog.dismiss();
			mProgBar.setVisibility(View.GONE);
			activityFeedAdapter = new ActivityFeedAdapter(context, activityFeeds);
			mListView.setAdapter(activityFeedAdapter);
		}	

	}*/


	/*@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		LoadActivityFeedData();
	}*/


	/*	private void loadRefreshActivityFeedData(){

		TaskListener mRefreshTaskListener= new TaskListener() {

			@Override
			public void updateUI() {
				// TODO Auto-generated method stub
				mListView.onRefreshComplete();
				if(tempArrayList!=null){
					if(tempArrayList.size()>0){
					Log.i("", "ActivityFeed List : "+mActivityFeedItemList.size());
					mActivityFeedItemList.clear();
//					activityFeedAdapter.notifyDataSetChanged();
					mActivityFeedItemList.addAll(tempArrayList);
					activityFeedAdapter.notifyDataSetChanged();
//					if(mActivityFeedItemList.size()>0){
//						
//						activityFeedAdapter = new ActivityFeedAdapter(mContext,mActivityFeedItemList);
//						mListView.setAdapter(activityFeedAdapter);
//					}
				}
			}
			}
			@Override
			public void execute() {
				// TODO Auto-generated method stub
				try {
					Log.d("", "execute() Calling..... : ");

					tempArrayList = ContentManager.getInstance().parseActivityFeedItem(mUserLoginItem);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		if(mAsyncTask!=null){
			mAsyncTask.cancel(true);
			mAsyncTask=null;
		}
		mAsyncTask=new CustomAsyncTask(mRefreshTaskListener, mContext);
		mAsyncTask.execute();
	}*/

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		//		 Log.i("", " 1234567890(Data Saving) restoring : onPause view is Calling ");
		//			SavedData = saveState();

		if(mAsyncTask!=null){
			mAsyncTask.cancel(true);
			mAsyncTask=null;
		}
		//		onSaveInstanceState(new Bundle());
		super.onPause();
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		//		 Log.i("", " 1234567890(Data Saving) restoring : onStop view is Calling ");
		//			SavedData = saveState();
		//			onDestroy();

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
		Log.i("", " 1234567890(Data Saving) restoring : onDestroy view is Calling ");
		//			SavedData = saveState();

	}



	/*
	private Bundle saveState()
	{  called either from onDestroyView() or onSaveInstanceState() 
		Bundle state = new Bundle();
		//	        state.putCharSequence(App.VSTUP, vstup.getText());
		Log.i("", " 1234567890(Data Saving) restoring : arralist size inside saveState Method : "+mActivityFeedItemList.size());
		state.putSerializable(BUNDLE_STRING,  mActivityFeedItemList);
		return state;
	}*/


	/*	@Override
	public void onSaveInstanceState(Bundle outState) {
//		super.onSaveInstanceState(outState);
		Log.i("", " 1234567890(Data Saving) restoring : inside onSaveInstanceState Method : ");
		 If onDestroyView() is called first, we can use the previously savedState but we can't call saveState() anymore 
		 If onSaveInstanceState() is called first, we don't have savedState, so we need to call saveState() 
		 => (?:) operator inevitable! 
//		outState.putBundle(BUNDLE_STRING_INSIDE_SAVEINSTANCE, (SavedData != null) ? SavedData : saveState());
		outState.putSerializable(BUNDLE_STRING,  mActivityFeedItemList);
		getInstance().onSaveInstanceState(outState);
//		getActivity().getSupportFragmentManager().putFragment(outState, "mContent", fragment);
	}*/





}
