package com.partyfinder.adapters;

import java.util.ArrayList;

import com.partyfinder.R;
import com.partyfinder.model.Card;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CurrentCards extends BaseAdapter{
	
	private Activity mActivity;
	private ArrayList<Card> mCardList;
	private Context mContext;
	private LayoutInflater inflator;
	
	public CurrentCards(Context ctx,ArrayList<Card> cardList,Activity act){
		mActivity=act;
		mContext=ctx;
		mCardList=cardList;
		inflator=LayoutInflater.from(mContext);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mCardList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		if(mCardList!=null){
			if(mCardList.size()>0)
		return mCardList.get(position);
		}
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder mViewHolder = null;
		if(convertView==null){
			convertView=inflator.inflate(R.layout.existingcard_popuplistview, null);
			mViewHolder=new ViewHolder();
			mViewHolder.cardNumber=(TextView) convertView.findViewById(R.id.cardNumberTextView);
			convertView.setTag(mViewHolder);
		}
		mViewHolder = (ViewHolder)convertView.getTag();
		Card mCard=mCardList.get(position);
		Log.i("","CardNuber....."+mCard.getCardNumber());
		String cardNumber="XXXX-XXXX-XXXX-"+mCard.getCardNumber().substring(15);
//		cardNumber.replaceAll("-","");
		
		Log.i("","CardNuber....."+cardNumber);
		mViewHolder.cardNumber.setText(cardNumber);
		
		
		
		return convertView;
	}
	
	class ViewHolder{
		TextView cardNumber;
	}

}

