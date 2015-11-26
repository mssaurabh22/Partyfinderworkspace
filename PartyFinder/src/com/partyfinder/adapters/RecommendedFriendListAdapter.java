package com.partyfinder.adapters;

import java.util.ArrayList;

import com.partyfinder.R;
import com.partyfinder.model.FriendListItem;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

public class RecommendedFriendListAdapter extends BaseAdapter{
	
	private ArrayList<FriendListItem> mFrndListItem;


	private FriendListItem mFrndList;
	private LayoutInflater inflater;
	private Context mContext;
	
	public ArrayList<FriendListItem> getmFrndListItem() {
		return mFrndListItem;
	}

	public void setmFrndListItem(ArrayList<FriendListItem> mFrndListItem) {
		this.mFrndListItem = mFrndListItem;
	}
	
	public RecommendedFriendListAdapter(Context ctx,ArrayList<FriendListItem> frndlist) {
		// TODO Auto-generated constructor stub
		mContext=ctx;
		mFrndListItem=frndlist;
		inflater=LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mFrndListItem.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mFrndListItem.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder;
		if(convertView==null){
			convertView=inflater.inflate(R.layout.recommended_friend_list, null);
			viewHolder=new ViewHolder();
			viewHolder.frndImage=(ImageView) convertView.findViewById(R.id.recommendFrndImage);
			viewHolder.recommendFrndName=(TextView) convertView.findViewById(R.id.recommendedFrndName);
			viewHolder.recommendCheckBox=(CheckBox) convertView.findViewById(R.id.checkFrndForRecommend);
			convertView.setTag(viewHolder);
		}
			viewHolder=(ViewHolder) convertView.getTag();
		
		
		
//		viewHolder.recommendCheckBox.bringToFront();
		mFrndList=(FriendListItem) getItem(position);
		
		Log.i("", "frnd imagexxxxxxxxx: "+mFrndList.getUserName());
		try{
		Picasso.with(mContext).load(mFrndList.getUserImage()).into(viewHolder.frndImage);
		}catch(Exception e){
			
		}

		viewHolder.recommendFrndName.setText(mFrndList.getUserName());

		viewHolder.recommendCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean checkFlag) {
				// TODO Auto-generated method stub
				if(checkFlag){
					Log.i("", "checked.....");
					mFrndListItem.get(position).setCheckStatus(true);
				}else {
					Log.i("", "unChecked........");
					mFrndListItem.get(position).setCheckStatus(false);
				}
			}
		});
		
		return convertView;
	}
	
	public class ViewHolder{
		ImageView frndImage;
		TextView recommendFrndName;
		CheckBox recommendCheckBox;
		
	}

}
