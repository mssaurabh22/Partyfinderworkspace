package com.partyfinder.drawerfragment;


import java.io.IOException;
import java.util.ArrayList;



import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.baoyz.swipemenulistview.SwipeMenuListView.OnMenuItemClickListener;
//import com.baoyz.swipemenulistview.SwipeMenuListView.OnRefreshListener;
//import com.baoyz.swipemenulistview.SwipeMenuListView.OnRefreshListener;
import com.baoyz.swipemenulistview.SwipeMenuListView.OnSwipeListener;

import com.partyfinder.EventDetailsActivity;
import com.partyfinder.MainActivity;
import com.partyfinder.R;
import com.partyfinder.VenuesDetailsActivity;
import com.partyfinder.AppConstant.PartyFinderConstants;
import com.partyfinder.CustomView.CustomAsyncTask;
import com.partyfinder.CustomView.CustomDialogBox;
import com.partyfinder.CustomView.TaskListener;
import com.partyfinder.adapters.RecommendedAdapter;
import com.partyfinder.app.utils.AppUtils;
import com.partyfinder.managers.ContentManager;
import com.partyfinder.managers.SettingPrefrences;
import com.partyfinder.model.AddFavoriteVanuesStatus;
import com.partyfinder.model.EventItem;
import com.partyfinder.model.RecommendItem;
import com.partyfinder.model.UserLoginItem;
import com.partyfinder.model.VenuesItem;
import com.partyfinder.widget.SlideHolder;

import eu.erikw.PullToRefreshListView.OnRefreshListener;



import android.content.Context;
import android.content.Intent;
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
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
 
public class FragmentRecommended extends Fragment {
	
//	private ListView mListView;
	private SwipeMenuListView mListView;
	private ArrayList<RecommendItem> mRecommendedList;
	private CustomAsyncTask mAsyncTask;
	private UserLoginItem userLoginItem;
	private VenuesItem mVenueItem;
	private EventItem mEventItem;
	private Context mContext;
	private ProgressBar mProgressBar;
	private RecommendedAdapter mRecommendedAdapter;
	private ViewGroup root;
	private EditText mSearchingEditText;
	private RelativeLayout mSearchBar,img_SearchIcon_layout;
	private ImageView mSearchIcon,mSearchBarCancel;
	private FragmentManager mfragmentManager;
	private Fragment mFragment;
	private AddFavoriteVanuesStatus venueStatus;
	private SlideHolder mSlideHolde;
	private FragmentTransaction mFragmentTransect;
	public FragmentRecommended(){
		
	}
	
	public static FragmentRecommended getInstance() {
    	FragmentRecommended f = new FragmentRecommended();
 
        return f;
    }
    public static Fragment newInstance(Context context) {
    	FragmentRecommended f = new FragmentRecommended();
 
        return f;
    }
 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
         root = (ViewGroup) inflater.inflate(R.layout.fragment_recommended, null);
        mContext=getActivity().getApplicationContext();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING|WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
        mfragmentManager=getActivity().getSupportFragmentManager();
        userLoginItem=SettingPrefrences.getUserPrefrence(mContext);
//        mListView=(ListView) root.findViewById(R.id.listViewRec);
        mListView=(SwipeMenuListView) root.findViewById(R.id.listViewRec);
//        mListView.setItemsCanFocus(false);
        img_SearchIcon_layout=(RelativeLayout) root.findViewById(R.id.img_SearchIcon_layout);
        mSearchBar=(RelativeLayout) root.findViewById(R.id.layouts_searchRec);
        mSearchIcon=(ImageView) root.findViewById(R.id.search_recommended);
        mSearchBarCancel = (ImageView) root.findViewById(R.id.imgViewResetRec);
        mProgressBar=(ProgressBar) root.findViewById(R.id.proBar_loadRec);
        mSearchingEditText=(EditText) root.findViewById(R.id.SearchingEditText);
        mSearchingEditText.addTextChangedListener(searchWatcher);
        mSlideHolde= (SlideHolder) getActivity().findViewById(R.id.slideHolder);
        mSearchBarCancel.setOnClickListener(mOnClickListener);
        mSearchIcon.setOnClickListener(mOnClickListener);
        img_SearchIcon_layout.setOnClickListener(mOnClickListener);
        
        mListView.setOnRefreshListener(new OnRefreshListener() {
			
			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				 loadRecommendedItem();
			}
		});
/*        
    	mListView.setOnRefreshListener(new IonRefreshListener() {
			  @Override
			    public void onRefreshUp() {
			    //your code
//					mSlideHolder.setEnabled(false);
					 loadRecommendedItem();
				
			    }

			    @Override
			    public void onRefeshDown() {
			    //your code
			    	mSlideHolde.setEnabled(false);
			    	mListView.onRefreshDownComplete(null);
			    }

			        });*/
        

        loadRecommendedItem();
        
    	
        
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
     				RecommendItem mRecommend=mRecommendedList.get(position);

     				switch (index) {
     				
     				case 0:
     	//  Deleting Process  //
     					
     					
     					if(mRecommend.getmObject() instanceof EventItem){
     						
     						
     						mEventItem=(EventItem) mRecommend.getmObject();
     						ArrayList<String> delRecommArrayList=new ArrayList<String>();
     						delRecommArrayList.add(userLoginItem.getVc_user_code());
     						delRecommArrayList.add(mRecommend.getmFriendItem().getUserCode());	
     						delRecommArrayList.add(mEventItem.getVcEventCode());
     						String	urlDelrecommend=PartyFinderConstants.URL_DELETE_RECOMMENDED_ITEM;
     						String	xmlDelRecomm=AppUtils.preparedDeleteRecommendXml(delRecommArrayList);
     						mRecommendedList.remove(position);
//     						notifyDataSetChanged();
     						mRecommendedAdapter.notifyDataSetChanged();
     						deleteRecommend(urlDelrecommend,xmlDelRecomm);
     						
     						
     					}else{
     						
     						
     						mVenueItem=(VenuesItem) mRecommend.getmObject();
     						ArrayList<String> delRecommArrayList=new ArrayList<String>();
     						delRecommArrayList.add(userLoginItem.getVc_user_code());
     						delRecommArrayList.add(mRecommend.getmFriendItem().getUserCode());
     						delRecommArrayList.add(mVenueItem.getVc_company_code());
     						String	urlDelrecommend=PartyFinderConstants.URL_DELETE_RECOMMENDED_ITEM;
     						String	xmlDelRecomm=AppUtils.preparedDeleteRecommendXml(delRecommArrayList);
     						mRecommendedList.remove(position);
     						mRecommendedAdapter.notifyDataSetChanged();
     						deleteRecommend(urlDelrecommend,xmlDelRecomm);
     						
     					}
     					
     		
     					break;
     					
     				}
     				
     				return false;
     				
     			}
     		});
     		
     		// set SwipeListener
     		mListView.setOnSwipeListener(new OnSwipeListener() {
     			
     			@Override
     			public void onSwipeStart(int position) {
     				// swipe start
     			}
     			
     			@Override
     			public void onSwipeEnd(int position) {
     				// swipe end
     			}
     		});
     		
     	
   
        return root;
    }
    
    OnClickListener mOnClickListener =new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.imgViewResetRec:
				mSearchBar.setVisibility(View.GONE);
		
				break;
			case R.id.search_recommended:
				mSearchBar.setVisibility(View.VISIBLE);
			
				break;
				
			case R.id.img_SearchIcon_layout:
				mSearchBar.setVisibility(View.VISIBLE);
			
				break;
			}
		}
	};
    
	
	public void reloadFragment(FragmentManager manager){
		mfragmentManager=manager;//=getActivity().getSupportFragmentManager();
		  mFragmentTransect=mfragmentManager.beginTransaction();
		  if(mFragment!=null){
		   mFragmentTransect.detach(mFragment);
		   mFragment=null;
		  }
		  
		  mFragmentTransect=mfragmentManager.beginTransaction();
		  Fragment mFragmentVenueList = new FragmentRecommended();
		  mFragmentTransect.replace(R.id.fragmentContainer, mFragmentVenueList);
//		  mFragmentTransect.addToBackStack(null);
		  mFragmentTransect.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		  mFragmentTransect.commitAllowingStateLoss();
		 }
    
	private void loadRecommendedItem(){
		if(mAsyncTask!=null){
			mAsyncTask.cancel(true);
			mAsyncTask=null;
		}
			mAsyncTask=new CustomAsyncTask(mRecommendedListener, mProgressBar, mContext);
			mAsyncTask.execute();
	}
	
    TaskListener mRecommendedListener=new TaskListener() {
		
		@Override
		public void updateUI() {
			mListView.onRefreshComplete();
			// TODO Auto-generated method stub
			mRecommendedAdapter=new RecommendedAdapter(mRecommendedList, mContext,mfragmentManager,FragmentRecommended.this.getActivity(),mSlideHolde);
			mListView.setAdapter(mRecommendedAdapter);
			
			mListView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					RecommendItem mRecommend=mRecommendedList.get(position);

					if(mRecommend.getmObject() instanceof EventItem){
						mEventItem=(EventItem) mRecommend.getmObject();
						Intent intent = new Intent(mContext,EventDetailsActivity.class);
						
						////.........
						intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						intent.putExtra(PartyFinderConstants.EXTRA_EVENTITEM,mEventItem);
						mContext.startActivity(intent);
					}else{
						mVenueItem=(VenuesItem) mRecommend.getmObject();
						mFragmentTransect=mfragmentManager.beginTransaction();
						mFragment=new VenuesDetailsActivity(mVenueItem,1);
						mFragmentTransect.replace(R.id.fragmentContainer, mFragment);
						mFragmentTransect.addToBackStack(null);
						mFragmentTransect.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
						mFragmentTransect.commitAllowingStateLoss();
						
					}
				}
			});
		}
		
		@Override
		public void execute() {
			// TODO Auto-generated method stub
			try {
				mRecommendedList=ContentManager.getInstance().parseRecommendedItem(userLoginItem);
//				Log.i("", "RecommendedList Size"+mRecommendedList.get(0).getmFriendItem().getUserName());
//				Log.i("", "RecommendedList Size 1... "+mRecommendedList.get(0).getmObject());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Log.i("", "Exception execute() method"+e.toString());
				e.printStackTrace();
			}
		}
	};
 
	
	
private TextWatcher searchWatcher=new TextWatcher() {
		
		@Override
		public void onTextChanged(CharSequence txt, int start, int before, int count) {
			int textlength = txt.length();
			
			if(mRecommendedList!=null && mRecommendedList.size()>0){
				ArrayList<RecommendItem> tempArrayList = new ArrayList<RecommendItem>();
	           for(RecommendItem c: mRecommendedList){
	        	   if(c.getmObject() instanceof EventItem ){
	        		   EventItem mEvent=(EventItem) c.getmObject();
	        		   if (textlength <= mEvent.getVcEventName().length()) {
	  	                 if (mEvent.getVcEventName().toLowerCase().contains(txt.toString().toLowerCase())) {
	  	                    tempArrayList.add(c);
	  	                 }
	  	              }
	        	   }else{
	        		   VenuesItem mVenueItem=(VenuesItem) c.getmObject();
	        		   if (textlength <= mVenueItem.getCompanyName().length()) {
		  	                 if (mVenueItem.getCompanyName().toLowerCase().contains(txt.toString().toLowerCase())) {
		  	                    tempArrayList.add(c);
		  	                 }
		  	              }
	        	   }
	              
	           }
//	         
	      
			mRecommendedAdapter=new RecommendedAdapter(tempArrayList, mContext,mfragmentManager,FragmentRecommended.this.getActivity(),mSlideHolde);
			mListView.setAdapter(mRecommendedAdapter);

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

	

	private int dp2px(int dp) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
				getResources().getDisplayMetrics());
	}
	
	public void deleteRecommend(final String url,final String xml){

		TaskListener delRecomm=new TaskListener() {

			@Override
			public void updateUI() {
				// TODO Auto-generated method stub

			}

			@Override
			public void execute() {
				// TODO Auto-generated method stub
				
				venueStatus=ContentManager.getInstance().parserDeleteRecommendStatus(url,xml);
			}
		};

		if(mAsyncTask!=null){
			mAsyncTask.cancel(true);
			mAsyncTask=null;
		}
		mAsyncTask=new CustomAsyncTask(delRecomm, mContext);
		mAsyncTask.execute();

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