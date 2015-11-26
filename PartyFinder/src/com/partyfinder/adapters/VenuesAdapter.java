package com.partyfinder.adapters;

import java.util.ArrayList;

import com.partyfinder.R;
import com.partyfinder.model.VenuesItem;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class VenuesAdapter extends BaseAdapter {

	ArrayList<VenuesItem> mVenuesList;
	ArrayList<VenuesItem>mVenuesItemsPass=null;
	LayoutInflater inflater;
	private Context mContext;
	private FragmentManager mFragmentManager;
	public VenuesAdapter(Context context, ArrayList<VenuesItem> mVenuesList, FragmentManager mFragmentManager2,Activity act) {
		super();
		Log.i("", " VenuesAdapter const. calling....");
		this.mVenuesList = mVenuesList;
		this.mContext = context;
		mFragmentManager=mFragmentManager2;
		Log.i("","Fragment manager : "+mFragmentManager);
//		inflater = mActivity.getLayoutInflater();//from(this.mContext);
		inflater =LayoutInflater.from(mContext) ;//act.getLayoutInflater();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		Log.d("", "lsit size :"+mVenuesList.size());
		return mVenuesList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		Log.i("","Item Position"+mVenuesList.get(position));
		return mVenuesList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final ViewHolder mViewHolder;
		if(convertView==null){
			convertView = inflater.inflate(R.layout.venues_list_item, null);
		
			mViewHolder = new ViewHolder();
			//mViewHolder.linearLayout=(LinearLayout)convertView.findViewById(R.id.layouts);
			mViewHolder.eventImage = (ImageView)convertView.findViewById(R.id.im_vc_image_url);
			mViewHolder.clubName = (TextView)convertView.findViewById(R.id.surrender);
			mViewHolder.partyName = (TextView)convertView.findViewById(R.id.tv_vc_event_name);
			mViewHolder.clubLocationText = (TextView)convertView.findViewById(R.id.tv_vc_event_addr);
			mViewHolder.mProgressBar = (ProgressBar)convertView.findViewById(R.id.small_Progress_bar);
			//mViewHolder.linearLayout.setVisibility(View.GONE);
			convertView.setTag(mViewHolder);
		}else {
			mViewHolder = (ViewHolder)convertView.getTag();

		

		}

		VenuesItem venuesItem = (VenuesItem)getItem(position);
		Picasso.with(this.mContext).load(venuesItem.getVc_image_url()).into(mViewHolder.eventImage,new Callback() {
			
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				mViewHolder.mProgressBar.setVisibility(View.GONE);
			}
			
			@Override
			public void onError() {
				// TODO Auto-generated method stub
				mViewHolder.mProgressBar.setVisibility(View.GONE);	
			}
		});
		
		mViewHolder.clubName.setText(venuesItem.getNomeFantasia());
		mViewHolder.partyName.setText(venuesItem.getVc_addr_street());
		mViewHolder.clubLocationText.setText(venuesItem.getVc_city_name()+","+venuesItem.getVc_state_name());



		/*convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				mFragmentTransect=mFragmentManager.beginTransaction();
				Fragment mFragmentVenueList = new VenuesDetailsActivity(mVenuesList.get(position));
				mFragmentTransect.replace(R.id.fragmentContainer, mFragmentVenueList);
				mFragmentTransect.addToBackStack(null);
				mFragmentTransect.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
				
				mFragmentTransect.commitAllowingStateLoss();
				
						
			}
		});*/


		return convertView;
	}
	public class ViewHolder{
		private	ImageView eventImage;
		private TextView  clubName;
		private TextView  partyName;
		private TextView  clubLocationText;
		private ProgressBar mProgressBar;
		//private LinearLayout linearLayout;
	}
}
