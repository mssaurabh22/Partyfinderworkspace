package com.partyfinder.adapters;

import java.util.ArrayList;

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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.partyfinder.R;
import com.partyfinder.CustomView.CustomAsyncTask;
import com.partyfinder.CustomView.TaskListener;
import com.partyfinder.app.utils.AppUtils;
import com.partyfinder.managers.ContentManager;
import com.partyfinder.managers.SettingPrefrences;
import com.partyfinder.model.AddFavoriteVanuesStatus;
import com.partyfinder.model.VenuesItem;
import com.partyfinder.model.UserLoginItem;
import com.partyfinder.widget.SlideHolder;
import com.squareup.picasso.Picasso;

public class FavouriteVenuesAdapter extends BaseAdapter {

	private ArrayList<VenuesItem> mFavouriteVenueItemsList;
	LayoutInflater inflater;
	private Context context;
private VenuesItem mVenuesItem;
	private UserLoginItem mLoginItem;
	private AddFavoriteVanuesStatus mAddFavoriteVanues;
	private CustomAsyncTask mAsyncTask;
	private ViewHolder mViewHolder;
	private VenuesItem favouriteVenueItem;
	private int deletedPosition;
	private String TAG="FavouriteVenuesAdapter class";
	private SlideHolder mSlideHolder;
	
	public FavouriteVenuesAdapter(Context context, ArrayList<VenuesItem> mFavouriteVenueItems, FragmentManager mFragmentManager2, Activity mAct,SlideHolder slideHolder) {
		super();

		this.mFavouriteVenueItemsList = mFavouriteVenueItems;
		this.context = context;
		inflater =LayoutInflater.from(context);//.getLayoutInflater();
		mLoginItem=SettingPrefrences.getUserPrefrence(this.context);
		mSlideHolder=slideHolder;
//		mSlideHolder=(SlideHolder) mActivity.findViewById(R.id.slideHolder);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		Log.d("", "lsit size :"+mFavouriteVenueItemsList.size());
		return mFavouriteVenueItemsList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		Log.i("","Item Position"+mFavouriteVenueItemsList.get(position));
		return mFavouriteVenueItemsList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		// TODO Auto-generated method stub

		if(convertView==null){
			inflater = LayoutInflater.from(context);//.getLayoutInflater();
			convertView = inflater.inflate(R.layout.cstm_listview_for_vanues, null);

			mViewHolder = new ViewHolder();

			mViewHolder.eventImage = (ImageView)convertView.findViewById(R.id.im_vc_image_url);
			mViewHolder.clubName = (TextView)convertView.findViewById(R.id.surrender);
			mViewHolder.partyName = (TextView)convertView.findViewById(R.id.tv_vc_event_name);
			mViewHolder.clubLocationText = (TextView)convertView.findViewById(R.id.tv_vc_event_addr);
			mViewHolder.mProgressBar = (ProgressBar)convertView.findViewById(R.id.smallProgressBar);
		//	mViewHolder.delFavButton=(Button) convertView.findViewById(R.id.delFavButton);
			mViewHolder.mlayout1=(RelativeLayout) convertView.findViewById(R.id.textview5);
			mViewHolder.mlayout2=(RelativeLayout) convertView.findViewById(R.id.textview4);
			mViewHolder.imageViewLayout=(RelativeLayout) convertView.findViewById(R.id.imageView);
			mViewHolder.textviewLayout=(RelativeLayout) convertView.findViewById(R.id.textview);
			convertView.setTag(mViewHolder);
		}else {
			mViewHolder = (ViewHolder)convertView.getTag();

			mViewHolder.mProgressBar.setVisibility(View.VISIBLE);

		}
		
		
		mViewHolder.imageViewLayout.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				mSlideHolder.setEnabled(true);
				return false;
			}
		});
		
		
		mViewHolder.textviewLayout.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
//				mSlideHolder.setEnabled(false);
				return false;
				
			}
		});

		favouriteVenueItem = (VenuesItem)getItem(position);
		mViewHolder.mlayout1.setVisibility(View.GONE);
		mViewHolder.mlayout2.setVisibility(View.GONE);
		Picasso.with(context).load(favouriteVenueItem.getVc_image_url()).into(mViewHolder.eventImage);
		mViewHolder.mProgressBar.setVisibility(View.GONE);
		mViewHolder.clubName.setText(favouriteVenueItem.getNomeFantasia());
		mViewHolder.partyName.setText(favouriteVenueItem.getVc_addr_street());
		mViewHolder.clubLocationText.setText(favouriteVenueItem.getVc_city_name());

		/*mViewHolder.delFavButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mVenuesItem= favouriteVenueItem;
				deletedPosition=position;
				mVenuesItem=mFavouriteVenueItemsList.get(deletedPosition);
				
				mFavouriteVenueItemsList.remove(deletedPosition);
				notifyDataSetChanged(); 
				executeDeleteFavTask();



			}
		});*/

	/*	convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
					Intent intent = new Intent(context,VenuesDetailsActivity.class);
				intent.putExtra(PartyFinderConstants.EXTRA_VENUESITEM,
						(mVenuesList.get(position)));

		        context.startActivity(intent);

				showDialog();


				Log.i("", "Fav Listview position"+position);

			}
		});
		*/
/*convertView.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

    	mFragmentTransect=mFragmentManager.beginTransaction();
		mFragment=new VenuesDetailsActivity(mFavouriteVenueItemsList.get(position),0);
		mFragmentTransect.replace(R.id.fragmentContainer, mFragment);
		mFragmentTransect.addToBackStack(null);
		mFragmentTransect.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		mFragmentManager.popBackStack();
		mFragmentTransect.commitAllowingStateLoss();
        
        
		
	}
});*/

		return convertView;

	}
	

	private void executeDeleteFavTask(){
		TaskListener delFav=new TaskListener() {

			@Override
			public void updateUI() {
				// TODO Auto-generated method stub
				if(mAddFavoriteVanues.getStatus().equalsIgnoreCase("0")){
//					mFavouriteVenueItemsList.remove(deletedPosition);
//					notifyDataSetChanged(); 
				}
			}

			@Override
			public void execute() {
				// TODO Auto-generated method stub
//				VenuesItem mVevItem=mFavouriteVenueItemsList.get(deletedPosition);
				Log.i("",TAG+"Deleted Position : "+deletedPosition+" Venue Name : "+mVenuesItem.getNomeFantasia());
				ArrayList<String> delFavArrayList=new ArrayList<String>();
				delFavArrayList.add(mVenuesItem.getVc_company_code());
				delFavArrayList.add(mLoginItem.getVc_user_code());
				delFavArrayList.add("N");
				String xml=AppUtils.preparedVanueFavouriteXml(delFavArrayList);
				mAddFavoriteVanues=ContentManager.getInstance().parserAddFavoriteVanuesStatus(xml);

			}
		};

		if(mAsyncTask!=null){
			mAsyncTask.cancel(true);
			mAsyncTask=null;
		}
		mAsyncTask=new CustomAsyncTask(delFav, context);
		mAsyncTask.execute();
	}


	public class ViewHolder{
		private	ImageView eventImage;
		private TextView  clubName;
		private TextView  partyName;
		private TextView  clubLocationText;
		private ProgressBar mProgressBar;
		//private Button delFavButton;
		private RelativeLayout mlayout1,mlayout2,imageViewLayout,textviewLayout;
	}
}
