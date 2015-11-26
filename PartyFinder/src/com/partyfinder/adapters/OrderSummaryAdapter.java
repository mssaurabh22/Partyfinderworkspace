package com.partyfinder.adapters;

import java.util.ArrayList;

import com.partyfinder.R;
import com.partyfinder.model.TicketDetailsItem;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class OrderSummaryAdapter extends BaseAdapter{

	private ArrayList<TicketDetailsItem> mTicketDetList;
	private Context mContext;
	private LayoutInflater mLayoutInflater;
	public OrderSummaryAdapter(ArrayList<TicketDetailsItem> orderList,Context ctx){
		mTicketDetList=orderList;
		mContext=ctx;
		mLayoutInflater=LayoutInflater.from(this.mContext);	
		Log.i("","Array List size : "+mTicketDetList.size());
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mTicketDetList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mTicketDetList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder mViewHolder;
		if(convertView==null){
			convertView = mLayoutInflater.inflate(R.layout.ordersummary_listview_layout, null);
			mViewHolder = new ViewHolder();
			mViewHolder.ticketType= (TextView) convertView.findViewById(R.id.orderSummaryTicketType);
			mViewHolder.ticketPrice=(TextView) convertView.findViewById(R.id.orderSummaryTicketprice);
			mViewHolder.totalQuantity=(TextView) convertView.findViewById(R.id.orderSummaryTicketQuantity);
			convertView.setTag(mViewHolder);
		}else{
			mViewHolder=(ViewHolder) convertView.getTag();
		}
		
		mViewHolder.ticketType.setText(mTicketDetList.get(position).getTicketType());
		mViewHolder.ticketPrice.setText("R$"+mTicketDetList.get(position).getTicketPrice());
		mViewHolder.totalQuantity.setText("Qtde: "+mTicketDetList.get(position).getTotalCount());
		return convertView;
	}
	
	class ViewHolder{
		TextView ticketType;
		TextView ticketPrice;
		TextView totalQuantity;
	}

}
