package com.partyfinder.drawerfragment;

import java.util.ArrayList;



import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.baoyz.swipemenulistview.SwipeMenuListView.OnMenuItemClickListener;
//import com.baoyz.swipemenulistview.SwipeMenuListView.OnRefreshListener;
//import com.baoyz.swipemenulistview.SwipeMenuListView.OnRefreshListener;
import com.baoyz.swipemenulistview.SwipeMenuListView.OnSwipeListener;
import com.partyfinder.MainActivity;
import com.partyfinder.R;
import com.partyfinder.VenuesDetailsActivity;

import com.partyfinder.AppConstant.PartyFinderConstants;
import com.partyfinder.CustomView.CustomAsyncTask;
import com.partyfinder.CustomView.CustomDialogBox;
import com.partyfinder.CustomView.TaskListener;
import com.partyfinder.adapters.FavouriteVenuesAdapter;
import com.partyfinder.app.utils.AppUtils;

import com.partyfinder.managers.ContentManager;
import com.partyfinder.managers.SettingPrefrences;

import com.partyfinder.model.AddFavoriteVanuesStatus;
import com.partyfinder.model.VenuesItem;
import com.partyfinder.model.UserLoginItem;
import com.partyfinder.widget.SlideHolder;

import eu.erikw.PullToRefreshListView.OnRefreshListener;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

public class FragmentFavorites extends Fragment {
	//	private UserLoginItem userLoginItem;
	//private ListView mListView;
	private ArrayList<VenuesItem>mFavouriteVenueItemsList;
	private FavouriteVenuesAdapter mFavouriteVenuesAdapter;
	private ProgressBar mProgressBar;
	private UserLoginItem mUserLoginItem;
	private String mUserCode="";
	private boolean isInternetConnected=false;
	private Context mContext;
	private Activity mActivity;
	private CustomAsyncTask mAsyncTask;
	private ImageView mSearchIcon,mSearchCancelIcon,mbackbtn;
	private EditText mSearchingEditText;
	private FragmentManager mFragmentManager;
	private RelativeLayout mSearchingLayout,img_SearchIcon_layout,imgViewResetf_layout;
	private SwipeMenuListView mListView;
	private AddFavoriteVanuesStatus mAddFavoriteVanues;

	private VenuesItem mVenuesItem;
	private UserLoginItem mLoginItem;
	private VenuesItem favouriteVenueItem;
	ArrayList<VenuesItem> mFavouriteVenueItems;
	private int deletedPosition;

	private FragmentTransaction mFragmentTransect;
	private static FragmentFavorites mFragment;
	private Fragment fragment;
	String url = PartyFinderConstants.URL_FAVOURITE_VENUES;
	private SlideHolder mSlideHolder;

	public FragmentFavorites(){

	}

	public static Fragment newInstance(Context context) {
		FragmentFavorites f = new FragmentFavorites();

		return f;
	}


	public static FragmentFavorites getInstance(){
		if(mFragment==null)
			mFragment=new FragmentFavorites();

		return mFragment;
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub

		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		mContext = getActivity().getApplicationContext();
		mActivity=getActivity();
		mLoginItem=SettingPrefrences.getUserPrefrence(mActivity);
		mFragmentManager= getActivity().getSupportFragmentManager();
		ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_favorites, null);
		mProgressBar = (ProgressBar)root.findViewById(R.id.proBarLoadList);
		img_SearchIcon_layout=(RelativeLayout) root.findViewById(R.id.img_SearchIcon_layout);
		imgViewResetf_layout=(RelativeLayout) root.findViewById(R.id.imgViewResetf_layout);

		mbackbtn=(ImageView) root.findViewById(R.id.btn_back);
		mbackbtn.setVisibility(View.GONE);
		mSearchIcon = (ImageView) root.findViewById(R.id.search_icon);
		mSearchCancelIcon = (ImageView) root.findViewById(R.id.imgViewResetf);
		mSearchingLayout = (RelativeLayout) root.findViewById(R.id.layouts_searchh);
		mSearchingEditText = (EditText) root.findViewById(R.id.etEventsearch);
		mListView = (SwipeMenuListView)root.findViewById(R.id.listView_fav);


		imgViewResetf_layout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mSearchingLayout.setVisibility(View.GONE);
				Log.i("", "vvvvvvvvvvvv   button clicked :");
			}
		});


		mListView.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				loadFavVenuesData();

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
						loadFavVenuesData();


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


		mSlideHolder=(SlideHolder)getActivity().findViewById(R.id.slideHolder);



		// step 1. create a MenuCreator
		SwipeMenuCreator creator = new SwipeMenuCreator() {

			@Override
			public void create(SwipeMenu menu) {  
				// create "delete" item
				SwipeMenuItem deleteItem = new SwipeMenuItem(
						getActivity().getApplicationContext());
				// set item background
				deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
						0x3F, 0x25)));
				// set item width
				deleteItem.setWidth(dp2px(90));
				// set a icon
				deleteItem.setTitle("Delete");
				deleteItem.setTitleColor(getResources().getColor(R.color.White));
				deleteItem.setTitleSize(getResources().getDimensionPixelSize(R.dimen.delete_item_text_size));
				// add to menu
				menu.addMenuItem(deleteItem);
			}
		};

		// set creator
		mListView.setMenuCreator(creator);

		// step 2. listener item click event
		mListView.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {


				switch (index) {

				case 0:
					//  Deleting Process  //

					mVenuesItem= favouriteVenueItem;
					deletedPosition=position;
					mVenuesItem=mFavouriteVenueItemsList.get(deletedPosition);

					mFavouriteVenueItemsList.remove(deletedPosition);
					mFavouriteVenuesAdapter.notifyDataSetChanged(); 
					executeDeleteFavTask();



					break;
				}
				return false;
			}
		});

		mListView.setOnSwipeListener(new OnSwipeListener() {

			@Override
			public void onSwipeStart(int position) {
				// TODO Auto-generated method stub
				Log.i("", "Swiping Start"+position);
				mSlideHolder.setEnabled(false);
			}

			@Override
			public void onSwipeEnd(int position) {
				// TODO Auto-generated method stub
				Log.i("", "Swiping End"+position);	
				if(position==-1){
					mSlideHolder.setEnabled(true);
				}
			}
		});

		img_SearchIcon_layout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mSearchingLayout.setVisibility(View.VISIBLE);	
			}
		});
		/*	mSearchCancelIcon.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});*/




		mSearchingEditText.addTextChangedListener(searchWatcher);
		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {

				// TODO Auto-generated method stub

			}

		});		

		isInternetConnected=AppUtils.isConnectingToInternet(getActivity());
		if (isInternetConnected) {
			mUserLoginItem=SettingPrefrences.getUserPrefrence(getActivity());
			mUserCode=mUserLoginItem.getVc_user_code();

			Log.i("","User code: "+mUserLoginItem.getVc_user_code());

			loadFavVenuesData();

		} else {

			//   mProgressBar.setVisibility(View.GONE);
			CustomDialogBox.showAlert(getResources().getString(R.string.infoDialo),getResources().getString(R.string.NoInternetMessage), getActivity());
			//			Toast.makeText(getActivity(),"Conecte seu dispositivo com conexão de Internet ativa", Toast.LENGTH_SHORT).show(); 

		}


		return root;
	}



	private void executeDeleteFavTask(){
		TaskListener delFav=new TaskListener() {

			@Override
			public void updateUI() {
				// TODO Auto-generated method stub
				if(mAddFavoriteVanues.getStatus().equalsIgnoreCase("0")){
					//					mFavouriteVenueItemsList.remove(deletedPosition);
					//					notifyDataSetChanged(); 
				}
			}

			@Override
			public void execute() {
				// TODO Auto-generated method stub
				//				VenuesItem mVevItem=mFavouriteVenueItemsList.get(deletedPosition);

				ArrayList<String> delFavArrayList=new ArrayList<String>();
				delFavArrayList.add(mVenuesItem.getVc_company_code());
				delFavArrayList.add(mLoginItem.getVc_user_code());
				delFavArrayList.add("N");
				String xml=AppUtils.preparedVanueFavouriteXml(delFavArrayList);
				mAddFavoriteVanues=ContentManager.getInstance().parserAddFavoriteVanuesStatus(xml);

			}
		};

		if(mAsyncTask!=null){
			mAsyncTask.cancel(true);
			mAsyncTask=null;
		}
		mAsyncTask=new CustomAsyncTask(delFav, mContext);
		mAsyncTask.execute();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	private void loadFavVenuesData(){
		if(mAsyncTask!=null){
			mAsyncTask.cancel(true);
			mAsyncTask=null;
		}
		mAsyncTask=new CustomAsyncTask(mTaskListener, mProgressBar, mContext);
		mAsyncTask.execute();
	}


	public void reloadFragment(FragmentManager manager){
		mFragmentManager=manager;//=getActivity().getSupportFragmentManager();
		mFragmentTransect=mFragmentManager.beginTransaction();
		if(mFragment!=null){
			mFragmentTransect.detach(mFragment);
			mFragment=null;
		}

		mFragmentTransect=mFragmentManager.beginTransaction();
		mFragment = new FragmentFavorites();
		mFragmentTransect.replace(R.id.fragmentContainer, mFragment);
		//		  mFragmentTransect.addToBackStack(null);
		mFragmentTransect.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		mFragmentTransect.commitAllowingStateLoss();
	}
	TaskListener mTaskListener=new TaskListener() {

		@Override
		public void updateUI() {

			// TODO Auto-generated method stub


			if(mFavouriteVenueItemsList!=null){
				if(mFavouriteVenueItemsList.size()>0){
					mListView.onRefreshComplete();
					Log.i("", "FavouriteVenueItemsList size:  "+mFavouriteVenueItemsList.size());
					mFavouriteVenuesAdapter=new FavouriteVenuesAdapter(mContext, mFavouriteVenueItemsList,mFragmentManager,FragmentFavorites.this.getActivity(),mSlideHolder);
					mListView.setAdapter(mFavouriteVenuesAdapter);
					mListView.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> parent,
								View view, int position, long id) {
							// TODO Auto-generated method stub

							mFragmentTransect=mFragmentManager.beginTransaction();
							fragment=new VenuesDetailsActivity(mFavouriteVenueItemsList.get(position),0);
							mFragmentTransect.replace(R.id.fragmentContainer, fragment);
							mFragmentTransect.addToBackStack(null);
							mFragmentTransect.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
							mFragmentManager.popBackStack();
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
				Log.i("","Execute method is calling...");
				mFavouriteVenueItemsList=ContentManager.getInstance().parseFavouriteItems(mUserLoginItem);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	};


	private TextWatcher searchWatcher=new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence txt, int start, int before, int count) {
			int textlength = txt.length();
			if(mFavouriteVenueItemsList!=null && mFavouriteVenueItemsList.size()>0){
				ArrayList<VenuesItem> tempArrayList = new ArrayList<VenuesItem>();
				for(VenuesItem c: mFavouriteVenueItemsList){
					if (textlength <= c.getNomeFantasia().length()) {
						if (c.getNomeFantasia().toLowerCase().contains(txt.toString().toLowerCase())) {
							tempArrayList.add(c);
						}
					}
				}
				//	
				mFavouriteVenuesAdapter=new FavouriteVenuesAdapter(mContext, tempArrayList,mFragmentManager,FragmentFavorites.this.getActivity(),mSlideHolder);
				mListView.setAdapter(mFavouriteVenuesAdapter);

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
	public void onResume() {
		// TODO Auto-generated method stub
		MainActivity.GetInstanceTab().tabHostLayout=(LinearLayout) getActivity().findViewById(R.id.tabhostlayout111111);
		MainActivity.GetInstanceTab().tabHostLayout.setVisibility(View.VISIBLE);
		super.onResume();
	}


	private int dp2px(int dp) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
				getResources().getDisplayMetrics());
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