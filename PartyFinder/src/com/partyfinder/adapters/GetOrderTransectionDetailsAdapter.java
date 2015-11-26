package com.partyfinder.adapters;

import java.util.ArrayList;

import com.partyfinder.R;
import com.partyfinder.AppConstant.PartyFinderConstants;
import com.partyfinder.model.OrderTransectionDetailsItems;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class GetOrderTransectionDetailsAdapter extends BaseAdapter {

	private Context mContext;
	private OrderTransectionDetailsItems mOrderTransectionDetailItem;
	private ArrayList<OrderTransectionDetailsItems> mOrderTransectionList;
	private LayoutInflater mLayoutInflater;



	public GetOrderTransectionDetailsAdapter(ArrayList<OrderTransectionDetailsItems> mOrderDetailList, Context ctx){
		mOrderTransectionList = mOrderDetailList;
		mContext = ctx;
		mLayoutInflater = LayoutInflater.from(this.mContext);		
	}


	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mOrderTransectionList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mOrderTransectionList.get(position);
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
			convertView = mLayoutInflater.inflate(R.layout.order_history_listview_item_layout, null);
			mViewHolder = new ViewHolder();
			mViewHolder.mEventCost= (TextView) convertView.findViewById(R.id.orderTransectionDetailOrderCost);
			mViewHolder.mEventDate=(TextView) convertView.findViewById(R.id.orderTransectionDetailEventDate);
			mViewHolder.mEventName=(TextView) convertView.findViewById(R.id.orderTransectionDetailEventName);
			mViewHolder.mEventStatus=(TextView) convertView.findViewById(R.id.orderTransectionDetailOrderStatus);
			mViewHolder.mOrderCode=(TextView) convertView.findViewById(R.id.orderTransectionDetailOrderCode);
			convertView.setTag(mViewHolder);
		}else{
			mViewHolder=(ViewHolder) convertView.getTag();
		}

		mOrderTransectionDetailItem = (OrderTransectionDetailsItems)getItem(position);

		mViewHolder.mEventCost.setText(mOrderTransectionDetailItem.getTotalPrice());
		mViewHolder.mEventDate.setText(mOrderTransectionDetailItem.getOrderDateFor());
		mViewHolder.mEventName.setText(mOrderTransectionDetailItem.getCompanyName());
		mViewHolder.mOrderCode.setText("Ordem #"+mOrderTransectionDetailItem.getOrderCode());
//		Log.i("", "vvvvvvvvvvvvvvvvvvvvvvv    inside adapter :"  +mOrderTransectionDetailItem.getOrderTicketItems().get(1));


		if(mOrderTransectionDetailItem.getTransectionStatusCode().equalsIgnoreCase(PartyFinderConstants.ORDER_STATUS_CONFIRMED1) || mOrderTransectionDetailItem.getTransectionStatusCode().equalsIgnoreCase(PartyFinderConstants.ORDER_STATUS_CONFIRMED2)){
			mViewHolder.mEventStatus.setTextColor(mContext.getResources().getColor(R.color.ggreen));
			mViewHolder.mEventStatus.setText(mOrderTransectionDetailItem.getTransectionStatus());
		}else if(mOrderTransectionDetailItem.getTransectionStatusCode().equalsIgnoreCase(PartyFinderConstants.ORDER_STATUS_CANCELED)){
			mViewHolder.mEventStatus.setTextColor(mContext.getResources().getColor(R.color.rred));
			mViewHolder.mEventStatus.setText(mOrderTransectionDetailItem.getTransectionStatus());
		}else if(mOrderTransectionDetailItem.getTransectionStatusCode().equalsIgnoreCase(PartyFinderConstants.ORDER_STATUS_REJECTED1) || mOrderTransectionDetailItem.getTransectionStatusCode().equalsIgnoreCase(PartyFinderConstants.ORDER_STATUS_REJECTED2)){
			mViewHolder.mEventStatus.setTextColor(mContext.getResources().getColor(R.color.rred));
			mViewHolder.mEventStatus.setText(mOrderTransectionDetailItem.getTransectionStatus());
		}else if(mOrderTransectionDetailItem.getTransectionStatusCode().equalsIgnoreCase(PartyFinderConstants.ORDER_STATUS_PENDING1) || mOrderTransectionDetailItem.getTransectionStatusCode().equalsIgnoreCase(PartyFinderConstants.ORDER_STATUS_PENDING2)){
			mViewHolder.mEventStatus.setTextColor(mContext.getResources().getColor(R.color.yyellow));
			mViewHolder.mEventStatus.setText(mOrderTransectionDetailItem.getTransectionStatus());
		}



		return convertView;
	}
	public class ViewHolder{

		TextView mEventDate;
		TextView mEventCost;
		TextView mEventStatus;
		TextView mOrderCode;
		TextView mEventName;

	}

}































