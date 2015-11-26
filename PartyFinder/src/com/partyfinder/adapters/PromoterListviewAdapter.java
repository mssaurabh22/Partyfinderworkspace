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

public class PromoterListviewAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private ArrayList<PromoterItem> promoterList;
	private Context mContext;
	public PromoterListviewAdapter(Context ctx,ArrayList<PromoterItem> promList, Activity act) {
		mContext = ctx;
		promoterList = promList;
		inflater=act.getLayoutInflater();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return promoterList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return promoterList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.select_promoterlistview,null);
			viewHolder=new ViewHolder();
			viewHolder.imageView=(ImageView) convertView.findViewById(R.id.promoterImage);
			viewHolder.textView=(TextView) convertView.findViewById(R.id.promoterName);
			convertView.setTag(viewHolder);
		}
		viewHolder=(ViewHolder) convertView.getTag();
		PromoterItem promItem=promoterList.get(position);
		viewHolder.textView.setText(promItem.getPromoterName());
		Picasso.with(mContext).load(promItem.getPromoterImage()).into(viewHolder.imageView);
		
		return convertView;
	}

	class ViewHolder {
		ImageView imageView;
		TextView textView;
	}

}
