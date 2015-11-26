package com.partyfinder.drawerfragment;

import java.util.ArrayList;

import com.partyfinder.R;
import com.partyfinder.CustomView.CustomAsyncTask;
import com.partyfinder.CustomView.TaskListener;
import com.partyfinder.managers.ContentManager;
import com.partyfinder.model.VenuesItem;
import com.partyfinder.model.UserLoginItem;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.widget.ProgressBar;

public class FavoritesVenueSwipe extends Activity{
	
//	private SwipeMenuListView mListView;
	private ArrayList<VenuesItem> mFavouriteVenueItemsList;
	private ProgressBar mProgressBar;
	private UserLoginItem mUserLoginItem;
	private Context mContext;
	private CustomAsyncTask mAsyncTask;
	
	/*public static Fragment newInstance(Context context) {
		FavoritesVenueSwipe f = new FavoritesVenueSwipe();

		return f;
	}*/
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_favorites);
		mContext=this;
		mProgressBar=(ProgressBar)findViewById(R.id.proBarLoadList);
}

	

	private void loadFavVenuesData(){
		if(mAsyncTask!=null){
			mAsyncTask.cancel(true);
			mAsyncTask=null;
		}
			mAsyncTask=new CustomAsyncTask(mTaskListener, mProgressBar, mContext);
			mAsyncTask.execute();
	}
	
	TaskListener mTaskListener=new TaskListener() {
		
		@Override
		public void updateUI() {
			// TODO Auto-generated method stub
			if(mFavouriteVenueItemsList!=null){
				if(mFavouriteVenueItemsList.size()>0){
					Log.i("", "FavouriteVenueItemsList size:  "+mFavouriteVenueItemsList.size());
//					mFavouriteVenuesAdapter=new FavouriteVenuesAdapter(mContext, mFavouriteVenueItemsList);
//					mListView.setAdapter(mFavouriteVenuesAdapter);
				}
			}
	
		}
		
		@Override
		public void execute() {
			// TODO Auto-generated method stub
			try {
				Log.i("","Execute method is calling...");
				mFavouriteVenueItemsList=ContentManager.getInstance().parseFavouriteItems(mUserLoginItem);
				Log.i("", "list size:"+mFavouriteVenueItemsList );
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	};
	
	
	private int dp2px(int dp) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
				getResources().getDisplayMetrics());
	}
	
	

}
