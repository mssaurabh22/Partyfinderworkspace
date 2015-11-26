package com.partyfinder.adapters;

import java.util.ArrayList;


import com.partyfinder.AppConstant.PartyFinderConstants;
import com.partyfinder.app.utils.AppUtils;
import com.partyfinder.model.EventItem;
import com.squareup.picasso.Picasso;

import com.partyfinder.EventDetailsActivity;
import com.partyfinder.R;
import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class EventAdapter extends BaseAdapter {

	private ArrayList<EventItem> mEventList;
	private ArrayList<EventItem>mEventItemPass=null;
	private Context context;
	private LayoutInflater inflater;


	public EventAdapter(Context context, ArrayList<EventItem> mEvent_List) {
		super();
		this.mEventList = mEvent_List;
		this.context = context;
		inflater = LayoutInflater.from(this.context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		Log.d("", "lsit size :"+mEventList.size());
		return mEventList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		Log.i("","Item Position"+mEventList.get(position));
		return mEventList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(final int position, View mConvertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder mViewHolder;
		if(mConvertView==null){
			mConvertView = inflater.inflate(R.layout.upcoming_events_layouts, null);
			mViewHolder = new ViewHolder();
			mViewHolder.eventImage = (ImageView)mConvertView.findViewById(R.id.eventImage);
			mViewHolder.clubName = (TextView)mConvertView.findViewById(R.id.club_name);
			mViewHolder.partyName = (TextView)mConvertView.findViewById(R.id.party_name);
			mViewHolder.clubLocationText = (TextView)mConvertView.findViewById(R.id.club_location_text);
			mViewHolder.monthNmae = (TextView)mConvertView.findViewById(R.id.month_name);
			mViewHolder.timeText = (TextView)mConvertView.findViewById(R.id.time_text);
			mViewHolder.mProgressBar = (ProgressBar)mConvertView.findViewById(R.id.imageLoadingProgressBar);
			mConvertView.setTag(mViewHolder);
		}else {
			mViewHolder = (ViewHolder)mConvertView.getTag();

			mViewHolder.mProgressBar.setVisibility(View.VISIBLE);

		}

		EventItem eventItem = (EventItem)getItem(position);



		Picasso.with(context).load(eventItem.getVcImageUrl()).into(mViewHolder.eventImage);
		mViewHolder.mProgressBar.setVisibility(View.GONE);
		//mViewHolder.eventImage.setImageBitmap(bitmap);
		mViewHolder.clubName.setText(eventItem.getVcCompanyName());
		mViewHolder.partyName.setText(eventItem.getVcEventName());
		//mViewHolder.clubLocationText.setText(eventItem.getVcEventAdd());

		String userActivityText = eventItem.getVcEventAdd()+", "+
				eventItem.getVcEventCity()+", "+

				      eventItem.getVcEventState();

		mViewHolder.clubLocationText.setText(Html.fromHtml(userActivityText), TextView.BufferType.SPANNABLE);
		Log.i("","Descrption Value 0 :"+eventItem.getDtEventDesc());

		//convert numeric date format into alphabetically
		String month = eventItem.getDtEventDate();
		mViewHolder.monthNmae.setText(AppUtils.changeDateFormat(month));
		mViewHolder.timeText.setText(eventItem.getDtEventStartTime());
		mConvertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(context,EventDetailsActivity.class);

				////.........
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.putExtra(PartyFinderConstants.EXTRA_EVENTITEM,
						(mEventList.get(position)));
				context.startActivity(intent);

			}
		});
		return mConvertView;
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
