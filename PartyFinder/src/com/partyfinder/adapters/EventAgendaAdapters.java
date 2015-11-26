package com.partyfinder.adapters;

import java.util.ArrayList;


import com.partyfinder.R;



import com.partyfinder.app.utils.AppUtils;
import com.partyfinder.model.GetEventAgendaItem;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class EventAgendaAdapters extends BaseAdapter {


	ArrayList<GetEventAgendaItem> mAgendaItemsList = new ArrayList<GetEventAgendaItem>();
	LayoutInflater inflater;
	Context context;

	public EventAgendaAdapters(Context context, ArrayList<GetEventAgendaItem> mAgendaItemsList) {
		super();
		this.mAgendaItemsList = mAgendaItemsList;
		this.context = context;
		inflater = LayoutInflater.from(this.context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mAgendaItemsList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mAgendaItemsList.get(position);
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
			convertView = inflater.inflate(R.layout.cstm_listview_for_event_layout, null);
			mViewHolder = new ViewHolder();
			mViewHolder.eventImage = (ImageView)convertView.findViewById(R.id.im_vc_image_url);
			mViewHolder.clubName = (TextView)convertView.findViewById(R.id.surrender);
			mViewHolder.partyName = (TextView)convertView.findViewById(R.id.tv_vc_event_name);
			mViewHolder.clubLocationText = (TextView)convertView.findViewById(R.id.tv_vc_event_addr);
			mViewHolder.monthNmae = (TextView)convertView.findViewById(R.id.tv_dt_event_date);
			mViewHolder.timeText = (TextView)convertView.findViewById(R.id.tv_dt_event_start_time);
			mViewHolder.mProgressBar = (ProgressBar)convertView.findViewById(R.id.smallProgressBar);
			convertView.setTag(mViewHolder);
		}else {
			mViewHolder = (ViewHolder)convertView.getTag();

			mViewHolder.mProgressBar.setVisibility(View.VISIBLE);

		}
		GetEventAgendaItem agendaItem = (GetEventAgendaItem)getItem(position);

		Picasso.with(context).load(agendaItem.getVc_image_url()).into(mViewHolder.eventImage);
		mViewHolder.mProgressBar.setVisibility(View.GONE);
		//mViewHolder.eventImage.setImageBitmap(bitmap);
		mViewHolder.clubName.setText(agendaItem.getVc_company_name());
		mViewHolder.partyName.setText(agendaItem.getVc_event_name());
		mViewHolder.clubLocationText.setText(agendaItem.getVc_event_addr());


		//convert numeric date format into alphbetically
		
		String month = agendaItem.getDt_event_date();
		mViewHolder.monthNmae.setText(AppUtils.changeDateFormat(month));
		mViewHolder.timeText.setText(agendaItem.getDt_event_start_time());
		
		convertView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});




		return convertView;
	}
	public class ViewHolder{
		ImageView eventImage;
		TextView clubName;
		TextView partyName;
		TextView clubLocationText;
		TextView monthNmae;
		TextView timeText;
		ProgressBar mProgressBar;
	}
}
