package com.partyfinder.adapters;

import java.util.ArrayList;

import com.partyfinder.R;
import com.partyfinder.model.OrderTicketItems;
import com.partyfinder.model.OrderTransectionDetailsItems;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class GetOderTransectionItemsDetailsAdapter extends BaseAdapter {
	
	private Context mContext;
	private OrderTransectionDetailsItems mOrderTransectionDetailItem;
	private ArrayList<OrderTicketItems> ticketList;
	private OrderTransectionDetailsItems orderItem;
	private OrderTicketItems mOrderTicketItems;
	private LayoutInflater mLayoutInflater;
	
	public GetOderTransectionItemsDetailsAdapter(OrderTransectionDetailsItems mOrderDetailList, Context ctx){
		orderItem = mOrderDetailList;
		ticketList=orderItem.getOrderTicketItems();
		mContext = ctx;
		mLayoutInflater = LayoutInflater.from(this.mContext);		
	}
	
	

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		Log.i("", "xxxxxxxxxxxx"+ticketList.size());
		
		return ticketList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return ticketList.get(position);
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
			convertView = mLayoutInflater.inflate(R.layout.order_ticket_details_listviewitem, null);
			mViewHolder = new ViewHolder();
			
			mViewHolder.mTicketConsupmtion = (TextView) convertView.findViewById(R.id.ticketConsumption);
			mViewHolder.mTicketCost=(TextView) convertView.findViewById(R.id.ticketCost);
			mViewHolder.mTicketEventName=(TextView) convertView.findViewById(R.id.ticketEventName);
			mViewHolder.mTicketEventDate=(TextView) convertView.findViewById(R.id.ticketEventDate);
			mViewHolder.mTicketType=(TextView) convertView.findViewById(R.id.ticketType);
			
			
			
			
		
			convertView.setTag(mViewHolder);
		}else{
			mViewHolder=(ViewHolder) convertView.getTag();
		}
		
		mOrderTicketItems = (OrderTicketItems)getItem(position);
		
		
		 mViewHolder.mTicketEventDate.setText(orderItem.getOrderDateFor());
	     mViewHolder.mTicketEventName.setText(orderItem.getCompanyName());
		 
	     mViewHolder.mTicketConsupmtion.setText(mOrderTicketItems.getItemCount());
//	    mViewHolder.mTicketConsupmtion.setText(mOrderTicketItems.getTicketConsumption()); 
		mViewHolder.mTicketCost.setText(mOrderTicketItems.getTicketPrice());
	    
	     
	     mViewHolder.mTicketType.setText(mOrderTicketItems.getTicketType());
	   
		
		
		return convertView;
	}
	public class ViewHolder{
		TextView mTicketType;
		TextView mTicketEventName;
		TextView mTicketEventDate;
		TextView mTicketConsupmtion;
		TextView mTicketCost;
		
	
		
	}

}
