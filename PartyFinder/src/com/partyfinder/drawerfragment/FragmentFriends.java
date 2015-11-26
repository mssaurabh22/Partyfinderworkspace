package com.partyfinder.drawerfragment;

import java.util.ArrayList;


import com.costum.android.widget.PullAndLoadListView;
import com.costum.android.widget.PullToRefreshListView.OnRefreshListener;

import com.partyfinder.MainActivity;
import com.partyfinder.R;

import com.partyfinder.CustomView.CustomAsyncTask;
import com.partyfinder.CustomView.CustomDialogBox;
import com.partyfinder.CustomView.TaskListener;
import com.partyfinder.adapters.GetFriendListAdapter;
import com.partyfinder.adapters.GetFriendRequestAdapter;
import com.partyfinder.app.utils.AppUtils;
import com.partyfinder.managers.ContentManager;
import com.partyfinder.managers.SettingPrefrences;
import com.partyfinder.model.FriendListItem;
import com.partyfinder.model.RequestedFriendItem;
import com.partyfinder.model.UserLoginItem;
import com.partyfinder.widget.SlideHolder;




import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;

public class FragmentFriends extends Fragment {
	private final String TAG="Friends Fragment ";
	//private ListView mListView;
//	private PullToRefreshListView mListView;
	PullAndLoadListView mListView;
//	PullToUpdateListView mListView;
	private ArrayList<FriendListItem> mSearchListItem;
	private String mSearchText;
	private GetFriendListAdapter mFriendListAdapter;
	private GetFriendRequestAdapter mFriendRequestAdapter;
	private UserLoginItem mUserLoginItem;
	private boolean isInternetConnected=false;
	private ProgressBar progressBarLoadFriendList;
	//private FriendListLoadTask mListLoadTask;
	private CustomAsyncTask mAsyncTask;

	private ImageView mRelativeLayout;
	private ImageView ViewSearchIcon;
	private ImageView ResetFriendSearch;
	private EditText editTextFriendSearch;

	//	private ListView mGetFriendRequestListView;
//	private PullToRefreshListView mGetFriendRequestListView;
	PullAndLoadListView mGetFriendRequestListView;
//	PullToUpdateListView mGetFriendRequestListView;
	private FragmentManager mFragmentManager;
	//private SearchFrndTask mFriendRequestTask;
	private TextView textView;
	private RelativeLayout relativeLayoutSearch,img_SearchIcon_layout;
	private ArrayList<FriendListItem> mGetFriendList;
	private ArrayList<RequestedFriendItem> mGetFriendRequestList;
	private Context mContext;
	private static FragmentFriends mFrndRequestObj; 
	private SlideHolder mSlideHolder;
	public FragmentFriends(){

	}

	public static FragmentFriends newInsatnce(){
		if(mFrndRequestObj!=null){
			return mFrndRequestObj;
		}else{
			return new FragmentFriends();
		}
	}
	public static Fragment newInstance(Context context) {
		FragmentFriends f = new FragmentFriends();

		return f;
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		mContext=getActivity().getApplicationContext();
		ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_friends, null);
		mFragmentManager=getActivity().getSupportFragmentManager();
		mUserLoginItem=SettingPrefrences.getUserPrefrence(getActivity());
		progressBarLoadFriendList=(ProgressBar)root.findViewById(R.id.proBar_loadfriends);
		img_SearchIcon_layout=(RelativeLayout) root.findViewById(R.id.img_SearchIcon_layout);
		//		mListView=(ListView)root.findViewById(R.id.listvIew_friends);
//		mListView=(PullToRefreshListView)root.findViewById(R.id.listvIew_friends);
		mListView=(PullAndLoadListView)root.findViewById(R.id.listvIew_friends);	
		mSlideHolder=(SlideHolder) getActivity().findViewById(R.id.slideHolder);
		mRelativeLayout=(ImageView)root.findViewById(R.id.rel_btn_back);
		mRelativeLayout.setVisibility(View.GONE);
		ViewSearchIcon=(ImageView)root.findViewById(R.id.search_icon);
		relativeLayoutSearch=(RelativeLayout)root.findViewById(R.id.layouts_searchFriends);
		ResetFriendSearch=(ImageView)root.findViewById(R.id.imgViewResetFriend);
		editTextFriendSearch=(EditText)root.findViewById(R.id.etsearchFriend);
		editTextFriendSearch.addTextChangedListener(searchTextWatcher);
		mListView.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				mListView.bringToFront(); 
				loadFriendListData();
				
			}
		});
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
	mListView.setOnRefreshListener(new IonRefreshListener() {
		  @Override
		    public void onRefreshUp() {
		    //your code
//				mSlideHolder.setEnabled(false);
				isInternetConnected=AppUtils.isConnectingToInternet(getActivity());

				if (isInternetConnected) {
					//						loadActivityFeedData();
					Log.i("", "Inside pull to update :");
					loadFriendListData();
					

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

		/*Confirm Friend Views*/
		//		mGetFriendRequestListView=(ListView)root.findViewById(R.id.listvIew_accept_friends_req);
//		mGetFriendRequestListView=(PullToRefreshListView)root.findViewById(R.id.listvIew_accept_friends_req);
		mGetFriendRequestListView=(PullAndLoadListView)root.findViewById(R.id.listvIew_accept_friends_req);

		textView=(TextView)root.findViewById(R.id.dkjfs);



		mRelativeLayout.setOnClickListener(mClickListener);
		ViewSearchIcon.setOnClickListener(mClickListener);
		img_SearchIcon_layout.setOnClickListener(mClickListener);
		ResetFriendSearch.setOnClickListener(mClickListener);
		isInternetConnected=AppUtils.isConnectingToInternet(getActivity());
		if (isInternetConnected) {
			/*LoadFriendListData();*/
			loadFriendListData();
		} else {
			CustomDialogBox.showAlert(getResources().getString(R.string.infoDialo),getResources().getString(R.string.NoInternetMessage), getActivity());
			//			Toast.makeText(getActivity(),"Conecte seu dispositivo com conexão de Internet ativa", Toast.LENGTH_SHORT).show(); 

		}
		return root;
	}

	TextWatcher searchTextWatcher=new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			// TODO Auto-generated method stub
			Log.i("","Called Textwatcher"+s.toString());
			mSearchText=s.toString();
			loadSearchFriendList();
			
//			mFriendListAdapter.notifyDataSetChanged();
			progressBarLoadFriendList.setVisibility(View.VISIBLE);

		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub

		}

		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub

		}
	};




	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		loadFriendListData();
		MainActivity.GetInstanceTab().tabHostLayout=(LinearLayout) getActivity().findViewById(R.id.tabhostlayout111111);
		MainActivity.GetInstanceTab().tabHostLayout.setVisibility(View.VISIBLE);
		super.onResume();
		Log.i(TAG, "calling onResume()....");
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Log.i(TAG, "calling onStart()....");
	}

	private void loadFriendListData(){
		if(mAsyncTask!=null){
			mAsyncTask.cancel(true);
			mAsyncTask=null;
		}
		mAsyncTask=new CustomAsyncTask(mFriendListListener, progressBarLoadFriendList, mContext);
		mAsyncTask.execute();
	}

	TaskListener mFriendListListener=new TaskListener() {

		@Override
		public void updateUI() {
			mListView.onRefreshComplete();
			// TODO Auto-generated method stub
			ArrayList<Object> mergList=new ArrayList<Object>();

			if(mGetFriendRequestList!=null){
				if(mGetFriendRequestList.size()>0){
					mergList.addAll(mGetFriendRequestList);


				}
			}

			if(mGetFriendList!=null){
				if(mGetFriendList.size()>0){
					Log.i("", "GetFriendList Size..."+mGetFriendList.size());
					mergList.addAll(mGetFriendList);
					//			mFriendListAdapter=new GetFriendListAdapter(mContext, mGetFriendList);
					//			mListView.setAdapter(mFriendListAdapter);
				}
			}

			mFriendRequestAdapter=new GetFriendRequestAdapter(mContext, mergList,mFragmentManager);
			mGetFriendRequestListView.setAdapter(mFriendRequestAdapter);
			Log.i("", "GetFriendRequestList Size..."+mGetFriendRequestList.size());
		}

		@Override
		public void execute() {
			// TODO Auto-generated method stub
			try {
				Log.i("", "execute() calling in get friend list");
				mGetFriendList=ContentManager.getInstance().parseGetFriendListItems(mUserLoginItem);
				mGetFriendRequestList=ContentManager.getInstance().parseGetRequestFriendList(mUserLoginItem);
			} catch (Exception e) {
				// TODO: handle exception
			}

		}
	};




	OnClickListener mClickListener=new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			
			case R.id.img_SearchIcon_layout:
				getActivity().getWindow().setSoftInputMode(
			            WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
				mListView.setVisibility(View.GONE);
				relativeLayoutSearch.setVisibility(View.VISIBLE);
				mGetFriendRequestListView.setVisibility(View.GONE);
				break;

			case R.id.search_icon:
				getActivity().getWindow().setSoftInputMode(
			            WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
				mListView.setVisibility(View.GONE);
				relativeLayoutSearch.setVisibility(View.VISIBLE);
				mGetFriendRequestListView.setVisibility(View.GONE);
				break;
			case R.id.imgViewResetFriend:
				editTextFriendSearch.setText("");
				/*LoadFriendListData();*/
				getActivity().getWindow().setSoftInputMode(
			            WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

				relativeLayoutSearch.setVisibility(View.GONE);	
				//				mGetFriendRequestListView.setVisibility(View.GONE);
				mListView.setVisibility(View.GONE);

				loadFriendListData();
				mGetFriendRequestListView.setVisibility(View.VISIBLE);

			}
		}
	};

	private void loadSearchFriendList(){


		TaskListener mSearchFrndTask=new TaskListener() {

			@Override
			public void updateUI() {
				mListView.onRefreshComplete();
				// TODO Auto-generated method stub
				Log.i("", "GetFriend Request List Size..."+mSearchListItem.size());
				if(mSearchListItem!=null){
					if(mSearchListItem.size()>0){

						mFriendListAdapter=new GetFriendListAdapter(mContext, mSearchListItem);
//					mListView.removeView(getView());
						mListView.setAdapter(mFriendListAdapter);
						mListView.setVisibility(View.VISIBLE);
					}
				}

			}

			@Override
			public void execute() {
				// TODO Auto-generated method stub
				try {

					mSearchListItem=ContentManager.getInstance().parseSearchFriendList(mUserLoginItem, mSearchText);
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		};
		if(mAsyncTask!=null){
			mAsyncTask.cancel(true);
			mAsyncTask=null;
		}
		mAsyncTask=new CustomAsyncTask(mSearchFrndTask, progressBarLoadFriendList, mContext);
		mAsyncTask.execute();
	}



	public	void refreshList(){
		Log.i("", "calling refreshList()");
		loadFriendListData();

	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		MainActivity.GetInstanceTab().tabHostLayout=(LinearLayout) getActivity().findViewById(R.id.tabhostlayout111111);
		MainActivity.GetInstanceTab().tabHostLayout.setVisibility(View.VISIBLE);
		super.onCreate(savedInstanceState);
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
	public void onStop() {
		// TODO Auto-generated method stub
		if(mAsyncTask!=null){
			mAsyncTask.cancel(true);
			mAsyncTask=null;
		}
		Log.i("", "xx  onStop Calling(Event) : ");
		super.onStop();
	}



	/**/

}