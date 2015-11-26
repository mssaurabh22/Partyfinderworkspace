package com.partyfinder.adapters;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.partyfinder.R;
import com.partyfinder.app.utils.AppUtils;
import com.partyfinder.customInterface.PurchaseTicketsCallBack;
import com.partyfinder.model.EventItem;
import com.partyfinder.model.TicketDetailsItem;

public class PurchaseTicketAdapter extends BaseAdapter {

	private ArrayList<TicketDetailsItem> mTicketItems;
	//	private ArrayList<PurchaseTicketItem>mTicketItemsPass=null;
	private LayoutInflater layoutInflater;
	private Context mContext;
	private EventItem mEventItem;
	private ViewHolder viewHolder ;
	int selectItem;
	private ArrayList<TicketDetailsItem> selectedList;

	//	View convertView;
	public PurchaseTicketAdapter(Context context,ArrayList<TicketDetailsItem> mTicketItems,Activity act,EventItem eventItem,PurchaseTicketsCallBack mPurchaseCallBack){
		super();
		this.mTicketItems=mTicketItems;
		this.mContext=context;
		mEventItem=eventItem;
		layoutInflater= LayoutInflater.from(this.mContext );
		selectedList=new ArrayList<TicketDetailsItem>();
	}

	public ArrayList<TicketDetailsItem> getmTicketItems() {
		return mTicketItems;
	}

	public void setmTicketItems(ArrayList<TicketDetailsItem> mTicketItems) {
		this.mTicketItems = mTicketItems;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub

		return mTicketItems.size();


	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		/*Log.i("","Item Position"+mEventList.get(position));
		return mEventList.get(position*/


		return mTicketItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Log.i("","calling getView :  "+position);

		if(convertView==null){

			convertView=layoutInflater.inflate(R.layout.addticketslistdetail, null);

			viewHolder= new ViewHolder();
			viewHolder.dc_price=(TextView) convertView.findViewById(R.id.dc_price);
			viewHolder.consumption_text=(TextView)convertView.findViewById(R.id.consumption_text);
			viewHolder.vip_text=(TextView)convertView.findViewById(R.id.ticketType);
			viewHolder.edison_club_text=(TextView)convertView.findViewById(R.id.edison_club_text);
			viewHolder.event_date=(TextView)convertView.findViewById(R.id.event_date);
			viewHolder.event_time=(TextView)convertView.findViewById(R.id.event_time);
			viewHolder.plusImage=(ImageButton) convertView.findViewById(R.id.plusButton);
			viewHolder.minusImage=(ImageButton) convertView.findViewById(R.id.minus_btn);
			viewHolder.calculateTxt=(TextView) convertView.findViewById(R.id.calculate_text);

			convertView.setTag(viewHolder); 
		}else{
			viewHolder= (ViewHolder)convertView.getTag();
		}



		final TicketDetailsItem ticketItem=  mTicketItems.get(position);
		Log.i("","ticket price : "+ticketItem.getTicketPrice()+" consumption text : "+ticketItem.getTicketConsumption()+" ticket type : "+ticketItem.getTicketType());
		viewHolder.dc_price.setText("R$"+ticketItem.getTicketPrice());
		viewHolder.consumption_text.setText("R$"+AppUtils.formatToatal(Double.parseDouble(ticketItem.getTicketConsumption())));
		viewHolder.vip_text.setText(ticketItem.getTicketType());
		viewHolder.calculateTxt.setText(ticketItem.getTotalCount()+"");
		viewHolder.edison_club_text.setText(mEventItem.getVcCompanyName());
		Log.i("","Company name : "+mEventItem.getVcCompanyName()+" event date : "+mEventItem.getDtEventDate()+" event time : "+mEventItem.getDtEventStartTime());


		String arr[]=AppUtils.changeDateFormatArray(mEventItem.getDtEventDate());

		viewHolder.event_date.setText(arr[0]+". "+modifyDate(arr[1]));


		viewHolder.event_time.setText(mEventItem.getDtEventStartTime());



		viewHolder.plusImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				int totalCount=ticketItem.getTotalCount();
				if(totalCount<Integer.parseInt(ticketItem.getNoOfTicket())){

					//				ticketItem.setTotalCount(totalCount+1);

					mTicketItems.get(position).setTotalCount(totalCount+1);
					double totalPrice=mTicketItems.get(position).getTotalCount()*Double.parseDouble(mTicketItems.get(position).getTicketPrice());
					mTicketItems.get(position).setTotalTicketPrice(totalPrice);

					notifyDataSetChanged();

				}

			}

		});

		viewHolder.minusImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				int totalCount=ticketItem.getTotalCount();
				if(totalCount>0){
					ticketItem.setTotalCount(totalCount-1);
					mTicketItems.get(position).setTotalCount(totalCount-1);
					double totalPrice=mTicketItems.get(position).getTotalCount()*Double.parseDouble(mTicketItems.get(position).getTicketPrice());
					mTicketItems.get(position).setTotalTicketPrice(totalPrice);
					notifyDataSetChanged();
				}
				//				if(selectedList.get(position)!=null){
				//					selectedList.add(mTicketItems.get(position));
				//				}else{
				//					selectedList.add(position, mTicketItems.get(position));
				//				}
				//				callback.selectedTicket(selectedList);
			}
		});

		return convertView;	

	}

	@Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		return super.getItemViewType(position);
	}





	private class ViewHolder{
		TextView dc_price;
		TextView consumption_text;
		TextView vip_text;
		TextView edison_club_text;
		TextView event_date;
		TextView event_time;
		ImageButton plusImage;
		ImageButton minusImage;
		TextView calculateTxt;	
	}
	public static String modifyDate(String date){
		String tempString;
		if(date.length()==1){
			tempString="0"+date;
		}else{
			tempString=date;
		}
		return tempString;
	}

}
