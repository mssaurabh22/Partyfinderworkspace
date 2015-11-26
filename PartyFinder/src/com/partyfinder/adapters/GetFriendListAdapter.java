package com.partyfinder.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import android.widget.RelativeLayout;
import android.widget.TextView;


import com.partyfinder.MyProfileActivity;
import com.partyfinder.R;

import com.partyfinder.AppConstant.PartyFinderConstants;
import com.partyfinder.model.FriendListItem;

import com.squareup.picasso.Picasso;

public class GetFriendListAdapter extends BaseAdapter {

	private ArrayList<FriendListItem> mFriendListItemsList;
	private LayoutInflater inflater;
	private Context context;

	public GetFriendListAdapter(Context context, ArrayList<FriendListItem> mGetFriendList) {
		super();
		Log.i("", "GetFriendListAdapter const calling....");
		this.mFriendListItemsList = mGetFriendList;
		this.context = context;
		inflater = LayoutInflater.from(this.context);
		Log.i("","Requested friend list : "+	this.mFriendListItemsList.size());
	}

	
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		Log.i("", "call 12....");
		return mFriendListItemsList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		Log.i("","Item Position"+position);
		return mFriendListItemsList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder mViewHolder;
		Log.i("", "getView() method calling....");
		if(convertView==null){
			
			convertView = inflater.inflate(R.layout.circle_image_item_forall_layout, null);
		
			mViewHolder = new ViewHolder();
			
			mViewHolder.layout_activityfeed_item=(RelativeLayout)convertView.findViewById(R.id.layout_activityfeed_item);
		
			mViewHolder.layout_friendList_item = (RelativeLayout)convertView.findViewById(R.id.layout_friendList_item);
		
			mViewHolder.layout_activityfeed_item.setVisibility(View.GONE);
			
			mViewHolder.layout_friendList_item.setVisibility(View.VISIBLE);
			
			mViewHolder.userFriendName = (TextView)convertView.findViewById(R.id.textView_userName);
			
			mViewHolder.userFriendImage=(ImageView)convertView.findViewById(R.id.imageView_user_friend);
			
			convertView.setTag(mViewHolder);
		}else {
			
			mViewHolder = (ViewHolder)convertView.getTag();

		}

		/*GetFriendListItem mFriendListItem = (GetFriendListItem)getItem(position);*/
		FriendListItem mFriendListItem = (FriendListItem)getItem(position);
		
		try {
			/*Picasso.with(context).load(mFriendListItem.getVc_image_url())*/
		if(mFriendListItem.getImageUrl()!=null){
			Log.i("", "image url....: "+mFriendListItem.getImageUrl());
		Picasso.with(context).load(mFriendListItem.getImageUrl()).error(R.drawable.no_thumbnail).into(mViewHolder.userFriendImage);
		}
		} catch (Exception e) {
			// TODO: handle exception
			Log.i("","Url Null"+e);
		}
		
	/*	mViewHolder.userFriendName.setText(mFriendListItem.getVc_user_name());
		Log.i("", "Frnd name....: "+mFriendListItem.getVc_user_name());*/
		
		mViewHolder.userFriendName.setText(mFriendListItem.getUserName());
		Log.i("", "Frnd name....: "+mFriendListItem.getUserName());
		//Log.i("", "Frnd name: "+mViewHolder.userFriendName.getText().toString());




		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.i("", "friendlist on click ");
				Intent intent = new Intent(context,MyProfileActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.putExtra(PartyFinderConstants.EXTRA_GET_FRIENDS_ITEM,
						(mFriendListItemsList.get(position)));

		        context.startActivity(intent);
			}
		});


		return convertView;
	}
	public class ViewHolder{
		private	ImageView userFriendImage;
		private TextView  userFriendName;
		private RelativeLayout layout_activityfeed_item;
		private RelativeLayout layout_friendList_item; 
	}

}
