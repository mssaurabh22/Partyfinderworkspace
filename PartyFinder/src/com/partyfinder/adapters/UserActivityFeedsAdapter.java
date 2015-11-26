package com.partyfinder.adapters;

import java.util.ArrayList;

import com.partyfinder.R;
import com.partyfinder.model.UserActivityFeeds;
import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class UserActivityFeedsAdapter extends BaseAdapter{

	ArrayList<UserActivityFeeds> mUserActivityFeedsList= new ArrayList<UserActivityFeeds>();
	LayoutInflater inflater;
	Context context;
	public UserActivityFeedsAdapter( Context context,ArrayList<UserActivityFeeds>mActivityFeedsList){
		super();
		this.mUserActivityFeedsList=mActivityFeedsList;
		this.context=context;
		inflater = LayoutInflater.from(this.context);

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		Log.d("", "ListSize :"+mUserActivityFeedsList.size());
		return mUserActivityFeedsList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mUserActivityFeedsList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder mViewHolder; 
		Log.i("","User Activity Date......");
		if(convertView==null){
			convertView=inflater.inflate(R.layout.myprofile_listview_layout, null);
			mViewHolder=new ViewHolder();
			mViewHolder.userName = (TextView)convertView.findViewById(R.id.ActivityUser);
//			mViewHolder.activityType = (TextView)convertView.findViewById(R.id.ActivityAction);
//			mViewHolder.activityName = (TextView)convertView.findViewById(R.id.ActivityName);
			mViewHolder.timeStamp = (TextView)convertView.findViewById(R.id.TGimeStamp);

			convertView.setTag(mViewHolder);
			
			
		}
		mViewHolder= (ViewHolder)convertView.getTag();
		
		UserActivityFeeds userActivityFeeds=(UserActivityFeeds)getItem(position);
		
		String userActivityText = "<font color='#e74f64'>"+userActivityFeeds.getActivityUser()+"</font>"+" "+
				"<font color='#ADADAD'>"+userActivityFeeds.getActivityAction()+"</font>"+" "+
					
						"<font color='#42b49a'>"+userActivityFeeds.getActivityName()+"</font>";
		Log.i("", "Activity Action..."+userActivityFeeds.getActivityAction());
		
		mViewHolder.userName.setText(Html.fromHtml(userActivityText), TextView.BufferType.SPANNABLE);
		
        /*mViewHolder.userName.setText(userActivityFeeds.getActivityUser());
        Log.i("","User Name cccccccccccccccc:"+userActivityFeeds.getActivityUser());
		mViewHolder.activityType.setText(userActivityFeeds.getActivityAction());
		Log.i("","User action :"+userActivityFeeds.getActivityAction());
		mViewHolder.activityName.setText(userActivityFeeds.getActivityName());
		Log.i("","User ActivityName :"+userActivityFeeds.getActivityName());*/
		mViewHolder.timeStamp.setText(userActivityFeeds.getActivitydate());
		Log.i("","User Activity Date:"+userActivityFeeds.getActivitydate());
		
		return convertView;
	}


	private class ViewHolder{
		private	TextView userName;
		private	TextView activityName;
		private	TextView activityType;
		private	TextView timeStamp;	


	}
}
