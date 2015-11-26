package com.partyfinder.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.partyfinder.MyProfileActivity;
import com.partyfinder.R;
import com.partyfinder.AppConstant.PartyFinderConstants;
import com.partyfinder.CustomView.CustomAsyncTask;
import com.partyfinder.CustomView.TaskListener;
import com.partyfinder.drawerfragment.FragmentFriends;
import com.partyfinder.managers.ContentManager;
import com.partyfinder.managers.SettingPrefrences;
import com.partyfinder.model.ConfirmFrndStatus;
import com.partyfinder.model.FriendListItem;
import com.partyfinder.model.RequestedFriendItem;
import com.partyfinder.model.UserLoginItem;
import com.squareup.picasso.Picasso;

public class GetFriendRequestAdapter extends BaseAdapter {

	private ArrayList<Object> mFriendRequestItemsList;
	private LayoutInflater inflater;
	private Context context;
	private FriendListItem mFriendListItem;
	private CustomAsyncTask myAsyncTask;
	private ViewHolder mViewHolder;
	private ConfirmFrndStatus status=null;
	private String confirPrepXml,rejectPrepXml;
	private RequestedFriendItem mRequestedFrndItem;
	private String TAG="GetFriendRequestAdapter class";
	private UserLoginItem mLoginItem;
	private ArrayList<FriendListItem> mGetFriendList;
	private ArrayList<RequestedFriendItem> mGetFriendRequestList;
	private FragmentManager mFragmentManager;
	private FragmentTransaction mFragmentTransect;
	private Fragment  mFragment;


	public GetFriendRequestAdapter(Context context, ArrayList<Object> mGetFriendRequestList, FragmentManager mFragmentManager2) {
		super();
		Log.i("", "GetFriendRequestAdapter const calling....");
		this.mFriendRequestItemsList = mGetFriendRequestList;
		mFragmentManager=mFragmentManager2;
		this.context = context;
		inflater = LayoutInflater.from(this.context);
		mLoginItem=SettingPrefrences.getUserPrefrence(context);

		Log.i("","Requested list : "+	this.mFriendRequestItemsList.size());
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		//	Log.d("", "lsit size :"+mGetFriendListPass.size());
		return mFriendRequestItemsList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		//Log.i("","Item Position"+mGetFriendListPass.get(position));
		return mFriendRequestItemsList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		if(convertView==null){
			convertView = inflater.inflate(R.layout.confirm_friend_request_layouts, null);

			mViewHolder = new ViewHolder();
//			mFragmentManager = getSupportFragmentManager();
			mViewHolder.progressBarSmall=(ProgressBar)convertView.findViewById(R.id.tosmallProgressBar);
			mViewHolder.relativeLayout=(RelativeLayout)convertView.findViewById(R.id.layout_friendList_item);
			mViewHolder.userFriendName = (TextView)convertView.findViewById(R.id.frndNameTextView);
			mViewHolder.userFriendImage=(ImageView)convertView.findViewById(R.id.frnd_image_view);
			mViewHolder.buttonConfirmFriendRequest=(Button)convertView.findViewById(R.id.btnConfirmFriendReq);
			mViewHolder.buttonConfirmFriendRequest.setVisibility(View.GONE);

			mViewHolder.buttonDeleteFriendRequest=(Button)convertView.findViewById(R.id.btnDeleteFriendReq);
			mViewHolder.buttonDeleteFriendRequest.setVisibility(View.GONE);
			mViewHolder.progBar=(ProgressBar) convertView.findViewById(R.id.mProgBar);
			convertView.setTag(mViewHolder);
		}
		mViewHolder = (ViewHolder)convertView.getTag();



		//GetFriendListItem mFriendListItem = (GetFriendListItem)getItem(position);

		if(getItem(position) instanceof FriendListItem ){
			mFriendListItem = (FriendListItem)getItem(position);
			Log.i("",TAG+" REquested Frnd Object :"+mFriendListItem);
			try {
				/*Picasso.with(context).load(mFriendListItem.getVc_image_url())*/
				Picasso.with(context).load(mFriendListItem.getImageUrl())
				.error(R.drawable.no_thumbnail)
				.into(mViewHolder.userFriendImage);

			} catch (Exception e) {
				// TODO: handle exception
				Log.i("","Url Null"+e);
			}
			mViewHolder.userFriendName.setText(mFriendListItem.getUserName());





		}else{
			mRequestedFrndItem=(RequestedFriendItem)getItem(position);
			Log.i("",TAG+" REquested Frnd Object :"+mRequestedFrndItem);
			try {
				/*Picasso.with(context).load(mFriendListItem.getVc_image_url())*/
				Picasso.with(context).load(mRequestedFrndItem.getImageUrl())
				.error(R.drawable.no_thumbnail)
				.into(mViewHolder.userFriendImage);
			} catch (Exception e) {
				// TODO: handle exception
				Log.i("","Url Null"+e);
			}
			mViewHolder.userFriendName.setText(mRequestedFrndItem.getUserName());
			mViewHolder.buttonConfirmFriendRequest.setVisibility(View.VISIBLE);
			mViewHolder.buttonDeleteFriendRequest.setVisibility(View.VISIBLE);

			mViewHolder.buttonConfirmFriendRequest.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Log.i("",TAG+" buttonConfirmFriendRequest button click");
					ArrayList<String> data=new ArrayList<String>();
					data.add(mLoginItem.getVc_user_code());
					data.add(mRequestedFrndItem.getUserCode());

					confirPrepXml=preparedFriendRequestAccept(data);
					Log.i("", "confirm prepared xml"+confirPrepXml);
					confirmFrndTask();
//					GetFriendRequestAdapter.this.notifyDataSetChanged();
					mFragmentTransect=mFragmentManager.beginTransaction();
					mFragment=new FragmentFriends();
					mFragmentTransect.replace(R.id.frameContainer, mFragment);
					mFragmentTransect.addToBackStack(null);
					mFragmentTransect.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
					mFragmentManager.popBackStack();
					mFragmentTransect.commitAllowingStateLoss();	
					
					
//					FragmentFriends.newInsatnce().refreshList();

				}
			});


			mViewHolder.buttonDeleteFriendRequest.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Log.i("",TAG+" buttonDeleteFriendRequest button click");

					ArrayList<String> data=new ArrayList<String>();
					data.add(mLoginItem.getVc_user_code());
					data.add(mRequestedFrndItem.getUserCode());

					rejectPrepXml=preparedFriendRequestAccept(data);
					Log.i("", "reject frnd prepared xml"+confirPrepXml);
					rejectFrndTask();
					
					mFragmentTransect=mFragmentManager.beginTransaction();
					mFragment=new FragmentFriends();
					mFragmentTransect.replace(R.id.frameContainer, mFragment);
					mFragmentTransect.addToBackStack(null);
					mFragmentTransect.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
					mFragmentManager.popBackStack();
					mFragmentTransect.commit();	
				}
			});
		}

		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(context,MyProfileActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.putExtra(PartyFinderConstants.EXTRA_GET_FRIENDS_ITEM,
						((FriendListItem)mFriendRequestItemsList.get(position)));

				context.startActivity(intent);

			}
		});

		return convertView;
	}
	public class ViewHolder{
		private	ImageView userFriendImage;
		private TextView  userFriendName;
		private Button buttonConfirmFriendRequest;
		private Button buttonDeleteFriendRequest;
		private ProgressBar progBar;
		private RelativeLayout relativeLayout;
		private ProgressBar progressBarSmall;

	}


	private String preparedFriendRequestReject(ArrayList<String> data){
		String tempXml;
		tempXml="<?xml version="+"\"1.0\""+" encoding="+"\"utf-8\""+" ?>"
				+"<table>"+
				"<UserId>%s</UserId>"+
				"<FriendId>%s</FriendId>"+
				"</table>";

		return String.format(tempXml, data.get(0),data.get(1));

	}

	private void rejectFrndTask(){
		if(myAsyncTask!=null){
			myAsyncTask.cancel(true);
			myAsyncTask=null;
		}
		myAsyncTask=new CustomAsyncTask(mRejectFrndRequest,mViewHolder.progBar , context);
		myAsyncTask.execute();

	}

	TaskListener mRejectFrndRequest=new TaskListener() {

		@Override
		public void updateUI() {
			// TODO Auto-generated method stub
			//	FragmentFriends.newInsatnce().refreshList();
		}

		@Override
		public void execute() {
			// TODO Auto-generated method stub
			status=ContentManager.getInstance().parserRejectFriendRequest(rejectPrepXml);
			Log.i(TAG, "reject status code: "+status);
		}
	};


	private String preparedFriendRequestAccept(ArrayList<String> data){
		String tempXml;

		tempXml="<?xml version="+"\"1.0\""+" encoding="+"\"utf-8\""+" ?>"
				+"<table>"+
				"<UserId>%s</UserId>"+
				"<FriendId>%s</FriendId>"+
				"</table>";

		return String.format(tempXml, data.get(0),data.get(1));

	}




	TaskListener mConfirmTaskListener=new TaskListener() {

		@Override
		public void updateUI() {
			// TODO Auto-generated method stub
			//			FragmentFriends.newInsatnce().refreshList();
			//			GetFriendRequestAdapter.this.notifyDataSetChanged();
			refreshList();
			
		}

		@Override
		public void execute() {
			// TODO Auto-generated method stub
			status=ContentManager.getInstance().parserConfirmFriendRequest(confirPrepXml);
		}
	};



	private void confirmFrndTask(){
		if(myAsyncTask!=null){
			myAsyncTask.cancel(true);
			myAsyncTask=null;
		}
		myAsyncTask=new CustomAsyncTask(mConfirmTaskListener,mViewHolder.progBar , context);
		myAsyncTask.execute();
	}


	private void refreshList(){
		if(myAsyncTask!=null){
			myAsyncTask.cancel(true);
			myAsyncTask=null;
		}
		myAsyncTask=new CustomAsyncTask(mFriendListRefreshListener,mViewHolder.progBar , context);
		myAsyncTask.execute();
	}


	TaskListener mFriendListRefreshListener=new TaskListener() {

		@Override
		public void updateUI() {
			// TODO Auto-generated method stub
			ArrayList<Object> mergList=new ArrayList<Object>();
			Log.i("", "RefreshList running....");
			if(mGetFriendRequestList!=null){
				if(mGetFriendRequestList.size()>0){
					mergList.addAll(mGetFriendRequestList);


				}
			}

			if(mGetFriendList!=null){
				if(mGetFriendList.size()>0){
					Log.i("", "GetFriendList Size..."+mGetFriendList.size());
					mergList.addAll(mGetFriendList);
				
					//			mFriendListAdapter=new GetFriendListAdapter(mContext, mGetFriendList);
					//			mListView.setAdapter(mFriendListAdapter);
				}
			}
			GetFriendRequestAdapter.this.mFriendRequestItemsList=mergList;
			Log.i("", "mergListSize...."+GetFriendRequestAdapter.this.mFriendRequestItemsList.size());
			

			//			
			//			mFriendRequestAdapter=new GetFriendRequestAdapter(mContext, mergList);
			//			mGetFriendRequestListView.setAdapter(mFriendRequestAdapter);
			//			Log.i("", "GetFriendRequestList Size..."+mGetFriendRequestList.size());


		}

		@Override
		public void execute() {
			// TODO Auto-generated method stub
			try {
				Log.i("", "execute() calling in get friend list");
				mGetFriendList=ContentManager.getInstance().parseGetFriendListItems(SettingPrefrences.getUserPrefrence(GetFriendRequestAdapter.this.context));
				mGetFriendRequestList=ContentManager.getInstance().parseGetRequestFriendList(SettingPrefrences.getUserPrefrence(GetFriendRequestAdapter.this.context));

			} catch (Exception e) {
				// TODO: handle exception
			}

		}
	};





}