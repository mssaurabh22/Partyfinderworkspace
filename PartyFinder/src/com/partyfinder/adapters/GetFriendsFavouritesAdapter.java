package com.partyfinder.adapters;

import java.util.ArrayList;

import com.partyfinder.R;
import com.partyfinder.model.VenuesItem;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class GetFriendsFavouritesAdapter extends BaseAdapter {

	private Context mContext;
	private LayoutInflater inflater;
	private ArrayList<VenuesItem> mFriendFavouriteVenueList;
	

	public GetFriendsFavouritesAdapter(Context context, ArrayList<VenuesItem> mFavouriteVenueItemsList) {
		super();
		this.mContext = context;
		mFriendFavouriteVenueList=mFavouriteVenueItemsList;
		Log.i("", "friends favourites..."+mFriendFavouriteVenueList.size());
		inflater = LayoutInflater.from(this.mContext);
	}
	


	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		
		return mFriendFavouriteVenueList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mFriendFavouriteVenueList.get(position);
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
			convertView = inflater.inflate(R.layout.cstm_listview_for_vanues, null);
			mViewHolder = new ViewHolder();
			mViewHolder.eventImage = (ImageView)convertView.findViewById(R.id.im_vc_image_url);
			mViewHolder.clubName = (TextView)convertView.findViewById(R.id.surrender);
			mViewHolder.partyName = (TextView)convertView.findViewById(R.id.tv_vc_event_name);
			mViewHolder.clubLocationText = (TextView)convertView.findViewById(R.id.tv_vc_event_addr);
			mViewHolder.mProgressBar = (ProgressBar)convertView.findViewById(R.id.smallProgressBar);
//			mViewHolder.delFavButton=(Button) convertView.findViewById(R.id.delFavButton);
			mViewHolder.mlayout1=(RelativeLayout) convertView.findViewById(R.id.textview5);
			mViewHolder.mlayout2=(RelativeLayout) convertView.findViewById(R.id.textview4);
			
			
			
			
			convertView.setTag(mViewHolder);
		}else{
			mViewHolder = (ViewHolder)convertView.getTag();
			mViewHolder.mProgressBar.setVisibility(View.VISIBLE);
		}
		
		mViewHolder.mProgressBar.setVisibility(View.GONE);
		mViewHolder.mlayout1.setVisibility(View.GONE);
		mViewHolder.mlayout2.setVisibility(View.GONE);
//		mViewHolder.delFavButton.setVisibility(View.GONE);
		Picasso.with(mContext).load(mFriendFavouriteVenueList.get(position).getVc_image_url()).into(mViewHolder.eventImage);
		mViewHolder.mProgressBar.setVisibility(View.GONE);
		mViewHolder.clubName.setText(mFriendFavouriteVenueList.get(position).getNomeFantasia());
		mViewHolder.partyName.setText(mFriendFavouriteVenueList.get(position).getVc_addr_street());
		mViewHolder.clubLocationText.setText(mFriendFavouriteVenueList.get(position).getVc_country_name());
		
		
		
		
		
		
		return convertView;
		
		
	}

	
	
	public class ViewHolder{
	    private	ImageView eventImage;
		private TextView  clubName;
		private TextView  partyName;
		private TextView  clubLocationText;
		private ProgressBar mProgressBar;
		private RelativeLayout mlayout1,mlayout2;
	}
}
	

