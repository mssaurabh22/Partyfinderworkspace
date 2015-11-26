package com.partyfinder.adapters;

import java.util.ArrayList;

import com.partyfinder.EventDetailsActivity;
import com.partyfinder.HomeActivity;
import com.partyfinder.R;
import com.partyfinder.SplashActivity;
import com.partyfinder.AppConstant.PartyFinderConstants;
import com.partyfinder.adapters.UpcomingEventAdapter.ViewHolder;
import com.partyfinder.app.utils.AppUtils;
import com.partyfinder.model.EventItem;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class UpcomingEventSkipLoginAdapter extends BaseAdapter{

	ArrayList<EventItem> upcomingList = new ArrayList<EventItem>();
	LayoutInflater inflater;
	Context context;

	public UpcomingEventSkipLoginAdapter(Context context, ArrayList<EventItem> upcomingList) {
		super();
		Log.d("", "upcoming event const..... :");
		this.upcomingList = upcomingList;
		this.context = context;
		inflater = LayoutInflater.from(this.context);
	}


	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return upcomingList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return upcomingList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		Log.i("","getView()");
		final ViewHolder mViewHolder;
		if(convertView == null){
			convertView = inflater.inflate(R.layout.upcoming_events_layouts, null);
			mViewHolder = new ViewHolder();
			mViewHolder.eventImage = (ImageView)convertView.findViewById(R.id.eventImage);
			mViewHolder.clubName = (TextView)convertView.findViewById(R.id.club_name);
			mViewHolder.partyName = (TextView)convertView.findViewById(R.id.party_name);
			mViewHolder.clubLocationText = (TextView)convertView.findViewById(R.id.club_location_text);
			mViewHolder.monthNmae = (TextView)convertView.findViewById(R.id.month_name);
			mViewHolder.timeText = (TextView)convertView.findViewById(R.id.time_text);
			mViewHolder.mProgressBar = (ProgressBar)convertView.findViewById(R.id.imageLoadingProgressBar);
			convertView.setTag(mViewHolder);
		}else {
			mViewHolder = (ViewHolder)convertView.getTag();


		}
		EventItem eventItem = (EventItem)getItem(position);

		//Loaded event Imges uisng Picasso Library


		try {
			//			String imgUrl=eventItem.getImageUrl().substring(1);//.replace("[^a-zA-Z0-9_-]", "");
			//			Log.i("","Image URl iiiiiiii"+imgUrl);
			Picasso.with(context).load(eventItem.getVcImageUrl()).into(mViewHolder.eventImage, new Callback() {

				@Override
				public void onSuccess() {
					// TODO Auto-generated method stub
					mViewHolder.mProgressBar.setVisibility(View.GONE);
				}

				@Override
				public void onError() {
					// TODO Auto-generated method stub
					mViewHolder.mProgressBar.setVisibility(View.VISIBLE);
				}
			} );

			//mViewHolder.eventImage.setImageBitmap(bitmap);
			mViewHolder.clubName.setText(eventItem.getVcCompanyName());
			mViewHolder.partyName.setText(eventItem.getVcEventName());
			//mViewHolder.clubLocationText.setText(eventItem.getVcEventAdd());

			String userActivityText = eventItem.getVcEventAdd()+", "+
					eventItem.getVcEventCity()+", "+

					     eventItem.getVcEventState();

			mViewHolder.clubLocationText.setText(Html.fromHtml(userActivityText), TextView.BufferType.SPANNABLE);
			//convert numeric date format into alphbetically

			String arr[]=AppUtils.changeDateFormatArray(eventItem.getDtEventDate());
			//						eventMonth.setText(arr[0]);
			//						eventDate.setText(modifyDate(arr[1]));    
			//			String month = eventItem.getDtEventDate();
			mViewHolder.monthNmae.setText(arr[0]+" "+modifyDate(arr[1]));
			mViewHolder.timeText.setText(eventItem.getDtEventStartTime());
		} catch (Exception e) {
			Log.i("","Exception"+e);
			// TODO: handle exception
		}

		/*convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent in=new Intent(context,HomeActivity.class);
				in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(in);



			}
		});
*/



		return convertView;
	}
	private class ViewHolder {
		ImageView eventImage;
		TextView clubName;
		TextView partyName;
		TextView clubLocationText;
		TextView monthNmae;
		TextView timeText;
		ProgressBar mProgressBar;
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
