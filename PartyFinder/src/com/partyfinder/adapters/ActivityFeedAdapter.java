package com.partyfinder.adapters;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.partyfinder.R;
import com.partyfinder.model.ActivityFeedItem;
import com.partyfinder.tabfragment.ActivityFeedFragment;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;


public class ActivityFeedAdapter extends BaseAdapter{

	ArrayList<ActivityFeedItem> activityFeedList = new ArrayList<ActivityFeedItem>();
	LayoutInflater inflater;
	Context mContext;
	Activity mAct;
	boolean flag;
	private Dialog mZoomDialog;

	public ActivityFeedAdapter(Context context, ArrayList<ActivityFeedItem> activityFeedList, Activity act) {
		super();
		this.activityFeedList = activityFeedList;
		this.mContext = context;
		this.mAct=act;
		inflater = LayoutInflater.from(this.mContext);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		//		Log.d("", "ListSize :"+activityFeedList.size());
		return activityFeedList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return activityFeedList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final ViewHolder mViewHolder;
		if(convertView == null){
			
			convertView = inflater.inflate(R.layout.circle_image_item_forall_layout, null);
			mViewHolder = new ViewHolder();
			mViewHolder.muser_image_layout=(RelativeLayout) convertView.findViewById(R.id.user_image_layout);

			mViewHolder.userImage = (ImageView)convertView.findViewById(R.id.user_image);
			mViewHolder.progressBarLoadImage=(ProgressBar)convertView.findViewById(R.id.imageLoadproBarAcFeed);
			//			mViewHolder.userName = (TextView)convertView.findViewById(R.id.username);
			mViewHolder.activityName = (TextView)convertView.findViewById(R.id.activity);
			//			mViewHolder.activityType = (TextView)convertView.findViewById(R.id.activity_type);
			mViewHolder.timeStamp = (TextView)convertView.findViewById(R.id.timestamp);


			convertView.setTag(mViewHolder);
			
		}else{
			
			mViewHolder = (ViewHolder)convertView.getTag();

		}
		//geting the each item postion
		ActivityFeedItem activityItem = (ActivityFeedItem)getItem(position);

		Log.i("","User Image :"+activityItem.getActivityUserImage());




		Picasso.with(mContext)
		.load(activityItem.getActivityUserImage())
		.error(R.drawable.no_thumbnail)
		.into(mViewHolder.userImage, new Callback() {

			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				mViewHolder.progressBarLoadImage.setVisibility(View.GONE);
			}

			@Override
			public void onError() {
				// TODO Auto-generated method stub
				mViewHolder.progressBarLoadImage.setVisibility(View.GONE);


			}
		});

		mViewHolder.muser_image_layout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(final View v) {
				// TODO Auto-generated method stub
				/*	mZoomDialog = new Dialog(mAct);

				mZoomDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				Window window = mZoomDialog.getWindow();
				window.setGravity(Gravity.CENTER);

				mZoomDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
				mZoomDialog.setContentView(R.layout.activityfeed_user_zoomimage);

				ImageView userImageView = (ImageView) mZoomDialog.findViewById(R.id.userImageZoom);

				ActivityFeedItem activityItem = (ActivityFeedItem)getItem(position);

				Log.i("","User Image :"+activityItem.getActivityUserImage());

				Picasso.with(mAct).load(activityItem.getActivityUserImage()).into(userImageView);




				mZoomDialog.show();*/
			}
		});
		/*Glide.with(context).load(activityItem.getActivityUserImage()).into(mViewHolder.userImage);*/

		//Concat text of user's activityFeed

		String userActivityText = 

				"<font color='#F78181'>"+activityItem.getActivityUser()+"</font>"+" "+
						"<font color='#ADADAD'>"+activityItem.getActivityAction()+"</font>"+" "+
						"<font color='#04B4AE'>"+activityItem.getActivityType()+"</font>";

		Log.i("", "Activity Action..."+activityItem.getActivityAction());

		mViewHolder.activityName.setText(Html.fromHtml(userActivityText), TextView.BufferType.SPANNABLE);
		mViewHolder.timeStamp.setText(activityItem.getActivityTime());
		Log.i("", "Activity Action..."+activityItem.getActivityTime());

		return convertView;
	}

	private class ViewHolder {
		
		private	ImageView userImage;
		private	TextView activityName;
		private RelativeLayout muser_image_layout;
		private	TextView timeStamp;
		private	ProgressBar progressBarLoadImage;

	}

}
