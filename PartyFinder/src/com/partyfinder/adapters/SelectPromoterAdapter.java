package com.partyfinder.adapters;

import java.util.ArrayList;

import com.partyfinder.R;
import com.partyfinder.model.PromoterItem;
import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SelectPromoterAdapter extends BaseAdapter{
	private Context mContext;
	private ArrayList<PromoterItem> mPromoterItem;
	private LayoutInflater inflater;
	private Activity mActivity;
	
	
	public SelectPromoterAdapter(Context ctx,ArrayList<PromoterItem> promoterItemList,Activity act) {
		// TODO Auto-generated constructor stub
		mContext=ctx;
		mActivity=act;
		mPromoterItem=promoterItemList;
		inflater=mActivity.getLayoutInflater();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mPromoterItem.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mPromoterItem.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		ViewHolder viewHolder;
		if(convertView==null){
			convertView=inflater.inflate(R.layout.select_promoterlistview, null);
			viewHolder=new ViewHolder();
			viewHolder.promoterImage=(ImageView) convertView.findViewById(R.id.promoterImage);
			viewHolder.promoterTextView=(TextView) convertView.findViewById(R.id.promoterName);
			convertView.setTag(viewHolder);
		}else {
			viewHolder=(ViewHolder) convertView.getTag();
			
		}
		
		PromoterItem promoterItem=(PromoterItem) getItem(position);
		
		Picasso.with(mContext).load(promoterItem.getPromoterImage()).into(viewHolder.promoterImage);
		viewHolder.promoterTextView.setText(promoterItem.getPromoterName());
		
		return convertView;
	}
	
	public class ViewHolder{
		ImageView promoterImage;
		TextView promoterTextView;
	}

}
