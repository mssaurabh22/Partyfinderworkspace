package com.partyfinder;

import java.util.ArrayList;

import com.partyfinder.AppConstant.PartyFinderConstants;
import com.partyfinder.CustomView.CustomAsyncTask;
import com.partyfinder.CustomView.TaskListener;
import com.partyfinder.adapters.GetFriendsFavouritesAdapter;
import com.partyfinder.managers.ContentManager;
import com.partyfinder.managers.SettingPrefrences;
import com.partyfinder.model.VenuesItem;
import com.partyfinder.model.FriendListItem;
import com.partyfinder.model.UserLoginItem;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;

public class MyfriendFavourite extends Activity{
	private Context mContext;
	private ListView mListView;
	
	private GetFriendsFavouritesAdapter mFavouriteAdapter;
	private ArrayList<VenuesItem>mFavouriteVenueItemsList;
	private FriendListItem mFrndListItem;
	private ImageView mBackButton;
	private CustomAsyncTask mAsyncTask;
	private ProgressBar progressBarLoadFriendList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_friends_favourite);
		mContext=this;
		Object object=getIntent().getSerializableExtra(PartyFinderConstants.EXTRA_GET_FRIENDS_ITEM);
		mFrndListItem = (FriendListItem) object;
		SettingPrefrences.getUserPrefrence(mContext);
		mBackButton=(ImageView) findViewById(R.id.rel_btn_back);
		mBackButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		mListView = (ListView) findViewById(R.id.listvIew_accept_friends_req);
		progressBarLoadFriendList=(ProgressBar) findViewById(R.id.proBar_loadfriends);
	
		
		loadSearchFriendList();
		
	}
	
	private void loadSearchFriendList(){
		
		TaskListener mSearchFrndTask=new TaskListener() {
			
			@Override
			public void updateUI() {
				// TODO Auto-generated method stub
				if(mFavouriteVenueItemsList!=null){
					if(mFavouriteVenueItemsList.size()>0){
						mFavouriteAdapter = new GetFriendsFavouritesAdapter(mContext, mFavouriteVenueItemsList);
						mListView.setAdapter(mFavouriteAdapter);
					}
				}
				
				
			}
			
			@Override
			public void execute() {
				// TODO Auto-generated method stub
//				try {
					mFavouriteVenueItemsList=ContentManager.getInstance().parseFriendsFavouriteItems(mFrndListItem);
			
//				} catch (Exception e) {
//					// TODO: handle exception
//				}
			}
		};
		if(mAsyncTask!=null){
			mAsyncTask.cancel(true);
			mAsyncTask=null;
		}
			mAsyncTask=new CustomAsyncTask(mSearchFrndTask, progressBarLoadFriendList, mContext);
			mAsyncTask.execute();
	}
	

	
	
	

}
