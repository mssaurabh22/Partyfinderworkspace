package com.partyfinder.adapters;

import java.util.ArrayList;
import com.partyfinder.R;
import com.partyfinder.CustomView.CustomAsyncTask;
import com.partyfinder.CustomView.TaskListener;
import com.partyfinder.app.utils.AppUtils;
import com.partyfinder.managers.ContentManager;
import com.partyfinder.managers.SettingPrefrences;
import com.partyfinder.model.AddFavoriteVanuesStatus;
import com.partyfinder.model.EventItem;
import com.partyfinder.model.FriendListItem;
import com.partyfinder.model.RecommendItem;
import com.partyfinder.model.UserLoginItem;
import com.partyfinder.model.VenuesItem;
import com.partyfinder.widget.SlideHolder;
import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class RecommendedAdapter extends BaseAdapter {

	private ArrayList<RecommendItem> mRecommendedList;
	private Context mContext;
	private LayoutInflater inflater;
	private FriendListItem mFrndList;
	private VenuesItem mVenueItem;
	private EventItem mEventItem;
	private AddFavoriteVanuesStatus venueStatus;
	private CustomAsyncTask mAsyncTask;
	private UserLoginItem mLoginItem;
	private SlideHolder mSlideHolder;
	
	public RecommendedAdapter(ArrayList<RecommendItem> arrayList ,Context ctx, FragmentManager mfragmentManager2, Activity Act,SlideHolder slideHolder) {
		mRecommendedList=arrayList;
		mContext=ctx;
		//		inflater = LayoutInflater.from(mContext);//getLayoutInflater(); 
		inflater = LayoutInflater.from(mContext); 
		mLoginItem=SettingPrefrences.getUserPrefrence(mContext);//LayoutInflater.from(mContext);
		mSlideHolder=slideHolder;//(SlideHolder) mActivity.findViewById(R.id.slideHolder);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mRecommendedList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mRecommendedList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder=null;;
//		mSlideHolder=(SlideHolder)findViewById(R.id.slideHolder);
		if(convertView==null){
			convertView=inflater.inflate(R.layout.recommended_listview_layouts, null);
			viewHolder=new ViewHolder();
			
			viewHolder.imageItem=(ImageView)convertView.findViewById(R.id.imageItemRecommended);
			viewHolder.eventVenueName=(TextView) convertView.findViewById(R.id.eventVenueName);
			viewHolder.date=(TextView) convertView.findViewById(R.id.dateRecommend);
			viewHolder.recommendedBy=(TextView) convertView.findViewById(R.id.RecommendedByUser);
			viewHolder.mProgress=(ProgressBar)convertView.findViewById(R.id.progressRecommend);
		//	viewHolder.delItem=(Button) convertView.findViewById(R.id.delRecommButton);
			viewHolder.mimageLayout=(LinearLayout) convertView.findViewById(R.id.imageLayout);
			viewHolder.mtextLayout=(LinearLayout) convertView.findViewById(R.id.textLayout);
			convertView.setTag(viewHolder);

		}
		viewHolder=(ViewHolder) convertView.getTag();
		//			viewHolder.mProgress.setVisibility(View.VISIBLE);


		mFrndList=mRecommendedList.get(position).getmFriendItem();
		Log.i("", "mFrndList obj: "+mFrndList.getUserName());
		viewHolder.recommendedBy.setText(mFrndList.getUserName());
		viewHolder.date.setVisibility(View.VISIBLE);
		RecommendItem mRecommend=mRecommendedList.get(position);

		if(mRecommend.getmObject() instanceof EventItem){
			mEventItem=(EventItem) mRecommend.getmObject();
			Picasso.with(mContext).load(mEventItem.getVcImageUrl()).into(viewHolder.imageItem);
			viewHolder.eventVenueName.setText(mEventItem.getVcEventName());

			
			
			
			String arr[]=AppUtils.changeDateFormatArray(mEventItem.getDtEventDate());
//			eventMonth.setText(arr[0]);
			
			
//			eventDate.setText(modifyDate(arr[1])); 
			viewHolder.date.setText(arr[0]+" "+modifyDate(arr[1]));

			/*convertView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

//			    	mFragmentTransect=mFragmentManager.beginTransaction();
//					mFragment=new VenuesDetailsActivity();
//					mFragmentTransect.replace(R.id.fragmentContainer, mFragment);
//					mFragmentTransect.addToBackStack(null);
//					mFragmentTransect.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
//					mFragmentManager.popBackStack();
//					mFragmentTransect.commitAllowingStateLoss();
					Intent intent = new Intent(mContext,EventDetailsActivity.class);
					
					////.........
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					intent.putExtra(PartyFinderConstants.EXTRA_EVENTITEM,
							(mEventItem));
					mContext.startActivity(intent);
					
			        
					
				}
			});*/
			

		}
		else
		{

			mVenueItem=(VenuesItem) mRecommend.getmObject();
			Log.i("", "Comapny Name : "+mVenueItem.getCompanyName());
			viewHolder.eventVenueName.setText(mVenueItem.getCompanyName());
			viewHolder.date.setVisibility(View.GONE);
			Picasso.with(mContext).load(mVenueItem.getVc_image_url()).into(viewHolder.imageItem);
			
			
			/*convertView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

			    	mFragmentTransect=mFragmentManager.beginTransaction();
					mFragment=new VenuesDetailsActivity(mVenueItem);
					mFragmentTransect.replace(R.id.fragmentContainer, mFragment);
					mFragmentTransect.addToBackStack(null);
					mFragmentTransect.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
					mFragmentTransect.commitAllowingStateLoss();
			        
			        
					
				}
			});*/
			

		}
		
		viewHolder.mimageLayout.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				mSlideHolder.setEnabled(true);
				return false;
			}
		});
		
		
		viewHolder.mtextLayout.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				mSlideHolder.setEnabled(false);
				return false;
				
			}
		});
		
		
		/*viewHolder.delItem.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				RecommendItem mRecommend=mRecommendedList.get(position);

				if(mRecommend.getmObject() instanceof EventItem){
					mEventItem=(EventItem) mRecommend.getmObject();
					ArrayList<String> delRecommArrayList=new ArrayList<String>();
					delRecommArrayList.add(mLoginItem.getVc_user_code());
					delRecommArrayList.add(mRecommend.getmFriendItem().getUserCode());	
					delRecommArrayList.add(mEventItem.getVcEventCode());
					String	urlDelrecommend=PartyFinderConstants.URL_DELETE_RECOMMENDED_ITEM;
					String	xmlDelRecomm=AppUtils.preparedDeleteRecommendXml(delRecommArrayList);
					mRecommendedList.remove(position);
					notifyDataSetChanged();
					deleteRecommend(urlDelrecommend,xmlDelRecomm);
					
					
				}else{
					mVenueItem=(VenuesItem) mRecommend.getmObject();
					ArrayList<String> delRecommArrayList=new ArrayList<String>();
					delRecommArrayList.add(mLoginItem.getVc_user_code());
					delRecommArrayList.add(mRecommend.getmFriendItem().getUserCode());
					delRecommArrayList.add(mVenueItem.getVc_company_code());
					String	urlDelrecommend=PartyFinderConstants.URL_DELETE_RECOMMENDED_ITEM;
					String	xmlDelRecomm=AppUtils.preparedDeleteRecommendXml(delRecommArrayList);
					mRecommendedList.remove(position);
					notifyDataSetChanged();
					deleteRecommend(urlDelrecommend,xmlDelRecomm);
					
				}
				
			}
		});*/
		/*convertView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				RecommendItem mRecommend=mRecommendedList.get(position);

				if(mRecommend.getmObject() instanceof EventItem){
					mEventItem=(EventItem) mRecommend.getmObject();
					Intent intent = new Intent(mContext,EventDetailsActivity.class);
					
					////.........
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					intent.putExtra(PartyFinderConstants.EXTRA_EVENTITEM,mEventItem);
					mContext.startActivity(intent);
				}else{
					mVenueItem=(VenuesItem) mRecommend.getmObject();
					mFragmentTransect=mFragmentManager.beginTransaction();
					mFragment=new VenuesDetailsActivity(mVenueItem,1);
					mFragmentTransect.replace(R.id.fragmentContainer, mFragment);
					mFragmentTransect.addToBackStack(null);
					mFragmentTransect.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
					mFragmentTransect.commitAllowingStateLoss();
					
				}
//		    	mFragmentTransect=mFragmentManager.beginTransaction();
//				mFragment=new VenuesDetailsActivity();
//				mFragmentTransect.replace(R.id.fragmentContainer, mFragment);
//				mFragmentTransect.addToBackStack(null);
//				mFragmentTransect.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
//				mFragmentManager.popBackStack();
//				mFragmentTransect.commitAllowingStateLoss();
				
				
		        
				
			}
		});*/
		return convertView;
	}
	
	public void deleteRecommend(final String url,final String xml){

		TaskListener delRecomm=new TaskListener() {

			@Override
			public void updateUI() {
				// TODO Auto-generated method stub

			}

			@Override
			public void execute() {
				// TODO Auto-generated method stub
				
				venueStatus=ContentManager.getInstance().parserDeleteRecommendStatus(url,xml);
			}
		};

		if(mAsyncTask!=null){
			mAsyncTask.cancel(true);
			mAsyncTask=null;
		}
		mAsyncTask=new CustomAsyncTask(delRecomm, mContext);
		mAsyncTask.execute();

	}

	public class ViewHolder{
		ImageView imageItem;
		TextView eventVenueName;
		TextView date;
		TextView recommendedBy;
		ProgressBar mProgress;
		LinearLayout mtextLayout,mimageLayout;
		//Button delItem;
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
