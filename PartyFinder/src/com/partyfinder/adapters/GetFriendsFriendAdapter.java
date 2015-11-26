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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class GetFriendsFriendAdapter extends BaseAdapter {
	
	private ArrayList<FriendListItem> mFriendListItemsList;
	private LayoutInflater inflater;
	private Context mContext;
	public GetFriendsFriendAdapter(Context ctx,ArrayList<FriendListItem> mGetFriendList ){
		this.mContext=ctx;
		mFriendListItemsList=mGetFriendList;
		inflater = LayoutInflater.from(this.mContext);
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
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder mViewHolder;

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
		
			Log.i("", "image url....: "+mFriendListItem.getImageUrl());
		Picasso.with(mContext).load(mFriendListItemsList.get(position).getImageUrl()).error(R.drawable.no_thumbnail).into(mViewHolder.userFriendImage);
		
		} catch (Exception e) {
			// TODO: handle exception
			Log.i("","Url Null"+e);
		}
		
	
		
		mViewHolder.userFriendName.setText(mFriendListItemsList.get(position).getUserName());
		




		

		return convertView;
	}
	public class ViewHolder{
		private	ImageView userFriendImage;
		private TextView  userFriendName;
		private RelativeLayout layout_activityfeed_item;
		private RelativeLayout layout_friendList_item; 
	}

}
