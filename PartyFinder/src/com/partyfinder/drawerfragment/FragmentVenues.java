package com.partyfinder.drawerfragment;

import java.util.ArrayList;



import com.costum.android.widget.PullAndLoadListView;
import com.costum.android.widget.PullToRefreshListView.OnRefreshListener;
import com.partyfinder.MainActivity;
import com.partyfinder.R;
import com.partyfinder.VenuesDetailsActivity;
import com.partyfinder.AppConstant.PartyFinderConstants;
import com.partyfinder.CustomView.CustomAsyncTask;
import com.partyfinder.CustomView.CustomDialogBox;
import com.partyfinder.CustomView.TaskListener;
import com.partyfinder.adapters.VenuesAdapter;
import com.partyfinder.app.utils.AppUtils;

import com.partyfinder.managers.ContentManager;
import com.partyfinder.managers.SettingPrefrences;
import com.partyfinder.model.UserLoginItem;
import com.partyfinder.model.VenuesItem;
import com.partyfinder.widget.SlideHolder;



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
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

public class FragmentVenues extends Fragment {
	//	private ListView mListView;
//	private PullToRefreshListView mListView;
	PullAndLoadListView mListView;
//	PullToUpdateListView mListView;
	private ArrayList<VenuesItem>mVenuesItems;
	private VenuesAdapter mVenuesAdapter;
	private UserLoginItem mUserLoginItem;
	private String mCurrentUserCode;
	private boolean isInternetConnected=false;
	private ProgressBar progressBarLoadVanues;
	private Context mContext;
	private CustomAsyncTask mAsyncTask;
	private static FragmentVenues mFragment;
	private ImageView mSearchIcon,mCancelSearch;
	private RelativeLayout mSearchLayout,img_SearchIcon_layout;
	private EditText mSearchingEditText;
	private FragmentManager mFragmentManager;
	private FragmentTransaction mFragmentTransect;
	private SlideHolder mSlideHolder;
	String url =PartyFinderConstants.URL_VENUES;
	
	public FragmentVenues(){
		
	}
	public static Fragment newInstance(Context context) {
		FragmentVenues f = new FragmentVenues();

		return f;
	}

	public static FragmentVenues getInstance(){
		if(mFragment==null)
			mFragment=new FragmentVenues();

		return mFragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_venues, null);

		//		   MainActivity.GetInstanceTab().mTabHost=(FragmentTabHost) getActivity().findViewById(android.R.id.tabhost);
		//		   MainActivity.GetInstanceTab().mTabHost.setVisibility(View.GONE);

		mContext=getActivity().getApplicationContext();
		mFragmentManager=getFragmentManager();

		progressBarLoadVanues =(ProgressBar)root.findViewById(R.id.progressBarLoadVanues);
		mSearchIcon = (ImageView) root.findViewById(R.id.btn_SettingIcon);
		img_SearchIcon_layout=(RelativeLayout) root.findViewById(R.id.img_SearchIcon_layout);
		mCancelSearch=(ImageView) root.findViewById(R.id.imgViewReset);
		mSlideHolder=(SlideHolder) getActivity().findViewById(R.id.slideHolder);
		mSearchLayout=(RelativeLayout) root.findViewById(R.id.layouts_search);
//		mListView=(ListView)root.findViewById(R.id.listViewVanues);
//		mListView=(PullToRefreshListView)root.findViewById(R.id.listViewVanues);
		mListView=(PullAndLoadListView)root.findViewById(R.id.listViewVanues);
		
		mListView.setOnRefreshListener(new OnRefreshListener() {
			
			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				loadVenuesItemData();
			}
		});
		/*mListView.setOnRefreshListener(new IonRefreshListener() {
			  @Override
			    public void onRefreshUp() {
			    //your code
//					mSlideHolder.setEnabled(false);
					isInternetConnected=AppUtils.isConnectingToInternet(getActivity());

					if (isInternetConnected) {
						//						loadActivityFeedData();
						Log.i("", "Inside pull to update :");
						loadVenuesItemData();
						

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
/*		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				mFragmentTransect=mFragmentManager.beginTransaction();
				Fragment mFragmentVenueList = new VenuesDetailsActivity(mVenuesList.get(position));
				mFragmentTransect.replace(R.id.fragmentContainer, mFragmentVenueList);
				mFragmentTransect.addToBackStack(null);
				mFragmentTransect.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
				
				mFragmentTransect.commitAllowingStateLoss();
			}
		});*/
		mSearchingEditText=(EditText) root.findViewById(R.id.etEventsearch);

		mSearchingEditText.addTextChangedListener(searchWatcher);
		mSearchIcon.setOnClickListener(mOnClickListener);
		mCancelSearch.setOnClickListener(mOnClickListener);
		img_SearchIcon_layout.setOnClickListener(mOnClickListener);

		mUserLoginItem=SettingPrefrences.getUserPrefrence(getActivity());
		mCurrentUserCode=mUserLoginItem.getVc_user_code();
		Log.i("","User code"+mUserLoginItem.getVc_user_code());

		isInternetConnected=AppUtils.isConnectingToInternet(getActivity());
		if (isInternetConnected) {
			loadVenuesItemData();

		} else {
//			Toast.makeText(getActivity(),"Conecte seu dispositivo com conexão de Internet ativa", Toast.LENGTH_SHORT).show(); 
			CustomDialogBox.showAlert(getResources().getString(R.string.infoDialo),getResources().getString(R.string.NoInternetMessage), getActivity());
		}

		return root;
	}


	OnClickListener mOnClickListener =new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.imgViewReset:
				mSearchLayout.setVisibility(View.GONE);

				break;
			case R.id.btn_SettingIcon:
				mSearchLayout.setVisibility(View.VISIBLE);

				break;
				

			case R.id.img_SearchIcon_layout:
				mSearchLayout.setVisibility(View.VISIBLE);

				break;
			}
		}
	};

	 public void reloadFragment(FragmentManager manager){
		  mFragmentManager=manager;//=getActivity().getSupportFragmentManager();
		  mFragmentTransect=mFragmentManager.beginTransaction();
		  if(mFragment!=null){
		   mFragmentTransect.detach(mFragment);
		   mFragment=null;
		  }
		  
		  mFragmentTransect=mFragmentManager.beginTransaction();
		  Fragment mFragmentVenueList = new FragmentVenues();
		  mFragmentTransect.replace(R.id.fragmentContainer, mFragmentVenueList);
//		  mFragmentTransect.addToBackStack(null);
		  mFragmentTransect.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		  mFragmentTransect.commitAllowingStateLoss();
		 }


/*	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.i(TAG, "calling onResume()...."+progressBarLoadVanues);
		updateVenuesList();
		MainActivity.GetInstanceTab().tabHostLayout=(LinearLayout) getActivity().findViewById(R.id.tabhostlayout111111);
        MainActivity.GetInstanceTab().tabHostLayout.setVisibility(View.VISIBLE);
        
	}*/

	/*@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		updateVenuesList();
		Log.i(TAG, "calling onStart()...."+progressBarLoadVanues);
	}



	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		updateVenuesList();
		super.onPause();
		Log.i(TAG, "calling onPause()...."+progressBarLoadVanues);
	}*/
	
	
	
	
	

	private  void loadVenuesItemData(){
		TaskListener mVenuesLoadTask =new TaskListener() {

			@Override
			public void updateUI() {
				mListView.onRefreshComplete();
				// TODO Auto-generated method stub
				if(mVenuesItems!=null){
					if(mVenuesItems.size()>0){
						Log.i("", "VenuesItems size: "+mVenuesItems);
						mVenuesAdapter=new VenuesAdapter(mContext, mVenuesItems,mFragmentManager,FragmentVenues.this.getActivity());
						mListView.setAdapter(mVenuesAdapter);
						mListView.setOnItemClickListener(new OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
								// TODO Auto-generated method stub
								mFragmentTransect=mFragmentManager.beginTransaction();
								Fragment mFragmentVenueList = new VenuesDetailsActivity(mVenuesItems.get(position),2);
								mFragmentTransect.replace(R.id.fragmentContainer, mFragmentVenueList);
								mFragmentTransect.addToBackStack(null);
								mFragmentTransect.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
								
								mFragmentTransect.commitAllowingStateLoss();
							}
						});
					}
				}

			}

			@Override
			public void execute() {
				// TODO Auto-generated method stub
				try {
					mVenuesItems=ContentManager.getInstance().parseVenuesItem(mUserLoginItem);
				} catch (Exception e) {
					// TODO: handle exception
				}

			}
		};
		if(mAsyncTask!=null){
			mAsyncTask.cancel(true);
			mAsyncTask=null;
		}
		mAsyncTask=new CustomAsyncTask(mVenuesLoadTask,progressBarLoadVanues, mContext);
		mAsyncTask.execute();
	}

	




	public  void  updateVenuesList(){

		loadVenuesItemData();
	}


	private TextWatcher searchWatcher=new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence txt, int start, int before, int count) {
			int textlength = txt.length();

			if(mVenuesItems!=null && mVenuesItems.size()>0){
				ArrayList<VenuesItem> tempArrayList = new ArrayList<VenuesItem>();
				for(VenuesItem c: mVenuesItems){
					if (textlength <= c.getNomeFantasia().length()) {
						if (c.getNomeFantasia().toLowerCase().contains(txt.toString().toLowerCase())) {
							tempArrayList.add(c);
						}
					}
				}
				//	
				mVenuesAdapter=new VenuesAdapter(mContext, tempArrayList,mFragmentManager,FragmentVenues.this.getActivity());
				mListView.setAdapter(mVenuesAdapter);
				

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

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		Log.i("", "xx  onAttach Calling(Casas) : ");
		
		super.onAttach(activity);
	}


	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		
		Log.i("", "xx  onDestroy Calling(Casas) :");
		super.onDestroy();
	}


	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		
		Log.i("", "xx  onDetach Calling(Casas) : ");
		super.onDetach();
	}


	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		if(mAsyncTask!=null){
			mAsyncTask.cancel(true);
			mAsyncTask=null;
		}
		Log.i("", "xx  onPause Calling(Casas) : ");
		super.onPause();
	}


	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		
		Log.i("", "xx  onStart Calling(Casas) : ");
		super.onStart();
	}


	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		if(mAsyncTask!=null){
			mAsyncTask.cancel(true);
			mAsyncTask=null;
		}
		Log.i("", "xx  onStop Calling(Casas) : ");
		super.onStop();
	}
		
	
	
}