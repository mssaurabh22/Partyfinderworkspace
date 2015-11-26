package com.partyfinder.drawerfragment;



import java.util.ArrayList;

import pt.fjss.pulltoupdatelibrary.IonRefreshListener;
import pt.fjss.pulltoupdatelibrary.PullToUpdateListView;


import com.partyfinder.MainActivity;
import com.partyfinder.R;
import com.partyfinder.CustomView.CustomAsyncTask;
import com.partyfinder.CustomView.CustomDialogBox;
import com.partyfinder.CustomView.TaskListener;
import com.partyfinder.adapters.EventAdapter;
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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

public class FragmentEvents extends Fragment {

//	private ListView mListView;
//	private PullToRefreshListView mListView;
//	PullAndLoadListView mListView;
	PullToUpdateListView mListView;
	private ArrayList<EventItem>mEventList;
	private EventAdapter mEventAdapter;
	private boolean isInternetConnected=  false;
	private ProgressBar progressBarLoadEvent;
	private Context mContext;
	private EditText mSearchingEditText;
	private CustomAsyncTask mAsyncTask;
	private UserLoginItem userLoginItem;
	private RelativeLayout mSearchBarLayout,morder_history_icon_Layout,img_SearchIcon_layout;
	private ImageView mSearchCancel,mSearchIcon,mImGlobe;
	private Fragment mFragment;
	private FragmentManager mFragmentManager;
	private FragmentTransaction mFragmentTransection;
	private ArrayList<EventItem> tempArrayList;

	public FragmentEvents(){
		
	}
	

	public static Fragment newInstance(Context context) {
		FragmentEvents f = new FragmentEvents();
		return f;
	}

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_events, null);
		mContext=getActivity().getApplicationContext();
		mFragmentManager=getActivity().getSupportFragmentManager();
		img_SearchIcon_layout=(RelativeLayout) root.findViewById(R.id.img_SearchIcon_layout);
		progressBarLoadEvent=(ProgressBar)root.findViewById(R.id.progressBarLoadEvent);
		mSearchBarLayout=(RelativeLayout) root.findViewById(R.id.layouts_search);
		mSearchingEditText=(EditText) root.findViewById(R.id.etEventsearch);
		mSearchIcon=(ImageView)root.findViewById(R.id.SearchIcon);
		morder_history_icon_Layout=(RelativeLayout) root.findViewById(R.id.order_history_icon_Layout);
		mSearchCancel=(ImageView) root.findViewById(R.id.imgViewReset);
		mImGlobe=(ImageView) root.findViewById(R.id.im_globe);
		morder_history_icon_Layout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			/*	
				   mFragmentManager=getFragmentManager();
			        mFragmentTransection=mFragmentManager.beginTransaction();
			        if(mFragment!=null){
			         mFragmentTransection.detach(mFragment);
			         mFragment=null;
			        }
			        
			        mFragment=new GoogleMapFragmentEvent(mEventList);
			        
			     //   mFragment=new com.partyfinder.drawerfragment.GoogleMapV2(mWhathotList);
			        
			        
			        mFragmentTransection.replace(R.id.frameContainer, mFragment);
			     
//			        mFragmentTransection.attach(mFragment);
			        
			        mFragmentTransection.addToBackStack(null);
			   //     mFragmentTransection.setTransitionStyle(FragmentTransaction.TRANSIT_ENTER_MASK);
			        mFragmentTransection.commitAllowingStateLoss();
				*/
				
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
		
		
		
		
		mListView = (PullToUpdateListView)root.findViewById(R.id.eventListView);
		mListView.setOnRefreshListener(new IonRefreshListener() {
			
			@Override
			public void onRefreshUp() {
				// TODO Auto-generated method stub
				mListView.bringToFront();
				loadEventItemData();
			}
			
			@Override
			public void onRefeshDown() {
				// TODO Auto-generated method stub
				
			}
		});
		
//		mListView = (PullToRefreshListView)root.findViewById(R.id.eventListView);
	/*	mListView.setOnRefreshListener(new OnRefreshListener() {
			
			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
//				loadRefreshEventItemData();
				mListView.bringToFront();
				loadEventItemData();
			}
		});*/
		
		mSearchingEditText.addTextChangedListener(searchWatcher);
		mSearchCancel.setOnClickListener(mOnClickListener);
		mSearchIcon.setOnClickListener(mOnClickListener);
		img_SearchIcon_layout.setOnClickListener(mOnClickListener);
		
		
		isInternetConnected=AppUtils.isConnectingToInternet(getActivity());
		if (isInternetConnected) {
			/*LoadEventData();*/
			loadEventItemData();

		} else {
			CustomDialogBox.showAlert(getResources().getString(R.string.infoDialo),getResources().getString(R.string.NoInternetMessage), getActivity());
//			Toast.makeText(getActivity(),"Conecte seu dispositivo com conexão de Internet ativa", Toast.LENGTH_SHORT).show(); 

		}

		return root;
	}


	
	private void loadEventItemData(){
		
		TaskListener mTaskListener=new TaskListener() {
			
			@Override
			public void updateUI() {
				// TODO Auto-generated method stub
				mListView.onRefreshUpComplete();
				if(mEventList!=null){
					if(mEventList.size()>0){
						Log.i("", "EventList size:  "+mEventList);
						mEventAdapter=new EventAdapter(mContext, mEventList);
						mListView.setAdapter(mEventAdapter);
					}
				}
				
			}
			
			@SuppressWarnings("static-access")
			@Override
			public void execute() {
				// TODO Auto-generated method stub
				try {
					mEventList=ContentManager.getInstance().parseEventItem(userLoginItem);
				} catch (Exception e) {
					// TODO: handle exception
				}
				
			}
		};
		if(mAsyncTask!=null){
			mAsyncTask.cancel(true);
			mAsyncTask=null;
		}
			mAsyncTask=new CustomAsyncTask(mTaskListener, progressBarLoadEvent, mContext);
			mAsyncTask.execute();
	}
	
	
	
	 OnClickListener mOnClickListener =new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				switch (v.getId()) {
				case R.id.imgViewReset:
					mSearchBarLayout.setVisibility(View.GONE);
			
					break;
				case R.id.SearchIcon:
					mSearchBarLayout.setVisibility(View.VISIBLE);
				
					break;
				case R.id.img_SearchIcon_layout:
					mSearchBarLayout.setVisibility(View.VISIBLE);
				
					break;
					
				}
			}
		};
		
		
		private TextWatcher searchWatcher=new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence txt, int start, int before, int count) {
				int textlength = txt.length();
				if(mEventList!=null && mEventList.size()>0){
					ArrayList<EventItem> tempArrayList = new ArrayList<EventItem>();
		           for(EventItem c: mEventList){
		              if (textlength <= c.getVcEventName().length()) {
		                 if (c.getVcEventName().toLowerCase().contains(txt.toString().toLowerCase())) {
		                    tempArrayList.add(c);
		                 }
		              }
		           }
//		           
				mEventAdapter=new EventAdapter(mContext, tempArrayList);
				mListView.setAdapter(mEventAdapter);

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

		@Override
		public void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			MainActivity.GetInstanceTab().tabHostLayout=(LinearLayout) getActivity().findViewById(R.id.tabhostlayout111111);
			  MainActivity.GetInstanceTab().tabHostLayout.setVisibility(View.VISIBLE);
			super.onCreate(savedInstanceState);
		}


		@Override
		public void onResume() {
			// TODO Auto-generated method stub
			MainActivity.GetInstanceTab().tabHostLayout=(LinearLayout) getActivity().findViewById(R.id.tabhostlayout111111);
			  MainActivity.GetInstanceTab().tabHostLayout.setVisibility(View.VISIBLE);
			super.onResume();
		}

		
		
	
	
/*
	private class EventLoadTask extends AsyncTask<String, Void, ArrayList<EventItem>>{

		private ProgressDialog mDialog = null;
		private Context context = getActivity();

		public EventLoadTask(Activity context){
			this.context = context;

		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			//super.onPreExecute();
			mDialog = new ProgressDialog(context);
			mDialog.setMessage("Loading...");
			mDialog.show();
			progressBarLoadEvent.setVisibility(View.VISIBLE);
		}

		@Override
		protected ArrayList<EventItem> doInBackground(String... params) {
			// TODO Auto-generated method stub
			ArrayList<EventItem> mList = null;
			String xml;
			xml = ContentManager.getXmlFromUrl(url);
			InputStream stream = new ByteArrayInputStream(xml.getBytes());
			try {
				mList = ContentManager.parseEventItem(stream);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Log.d("", "Event List :"+mList);
			return mList;
		}

		@Override
		protected void onPostExecute(ArrayList<EventItem> Events) {
			// TODO Auto-generated method stub
			progressBarLoadEvent.setVisibility(View.GONE);
            mevEventAdapter = new EventAdapter(context, Events);
			mListView.setAdapter(mevEventAdapter);

		}



	}*/
		
		
		
		private void loadRefreshEventItemData(){
			
			TaskListener mTaskListener=new TaskListener() {
				
				@Override
				public void updateUI() {
					mListView.onRefreshUpComplete();
					// TODO Auto-generated method stub
					if(mEventList!=null){
						
						mEventList.clear();
						mEventAdapter.notifyDataSetChanged();
						mEventList.addAll(tempArrayList);
						mEventAdapter.notifyDataSetChanged();
						
						/*if(mEventList.size()>0){
							Log.i("", "EventList size:  "+mEventList);
							mEventAdapter=new EventAdapter(mContext, mEventList);
							mListView.setAdapter(mEventAdapter);
						}*/
					}
					
				}
				
				@SuppressWarnings("static-access")
				@Override
				public void execute() {
					// TODO Auto-generated method stub
					try {
						tempArrayList=ContentManager.getInstance().parseEventItem(userLoginItem);
					} catch (Exception e) {
						// TODO: handle exception
					}
					
				}
			};
			if(mAsyncTask!=null){
				mAsyncTask.cancel(true);
				mAsyncTask=null;
			}
				mAsyncTask=new CustomAsyncTask(mTaskListener, progressBarLoadEvent, mContext);
				mAsyncTask.execute();
		}


		@Override
		public void onAttach(Activity activity) {
			// TODO Auto-generated method stub
			Log.i("", "xx  onAttach Calling(Event) : ");
			
			super.onAttach(activity);
		}


		@Override
		public void onDestroy() {
			// TODO Auto-generated method stub
			
			Log.i("", "xx  onDestroy Calling(Event) :");
			super.onDestroy();
		}


		@Override
		public void onDetach() {
			// TODO Auto-generated method stub
			
			Log.i("", "xx  onDetach Calling(Event) : ");
			super.onDetach();
		}


		@Override
		public void onPause() {
			// TODO Auto-generated method stub
			if(mAsyncTask!=null){
				mAsyncTask.cancel(true);
				mAsyncTask=null;
			}
			Log.i("", "xx  onPause Calling(Event) : ");
			super.onPause();
		}


		@Override
		public void onStart() {
			// TODO Auto-generated method stub
			
			Log.i("", "xx  onStart Calling(Event) : ");
			super.onStart();
		}


		@Override
		public void onStop() {
			// TODO Auto-generated method stub
			if(mAsyncTask!=null){
				mAsyncTask.cancel(true);
				mAsyncTask=null;
			}
			Log.i("", "xx  onStop Calling(Event) : ");
			super.onStop();
		}
		
		
		
		
}