package com.partyfinder.drawerfragment;

import java.util.List;

import com.partyfinder.MainActivity;
import com.partyfinder.R;
import com.partyfinder.managers.SettingPrefrences;
import com.partyfinder.model.OrderTicketItems;
import com.partyfinder.model.OrderTransectionDetailsItems;
import com.squareup.picasso.Picasso;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class OrderTicketConfirmation extends Fragment {

	private Context mContext;
	private ProgressBar mProgressBar;
	private OrderTicketItems mTicketItem;
	private TextView mOrderCode,mUserName,mCityState,mTicketCost,mTicketConsumption,mticketAndTable,mTicketCount,mTicketEventDate,mUserCpf,mUserAdd;
	private ImageView mTicketQrcodeImage,newText;
	private LinearLayout mLinearLayout;
	private SettingPrefrences mSettingPrefcn;
	private ImageView mBackButton;
	private Button MfacebookSharingButton;
	private RelativeLayout mBack,mnewQrcodeImg;
	private FragmentManager mFragmentManager;

	private OrderTransectionDetailsItems mOrderTransectionDetailitem;



	public OrderTicketConfirmation(	OrderTransectionDetailsItems orderTransectionItems,OrderTicketItems ticketItem,Activity act, Context ctx	){

		mContext=ctx;
		mOrderTransectionDetailitem = orderTransectionItems;

		mTicketItem=ticketItem;
		//		mOrdertransectionList = orderTransectionDetailsItems;


	
	}
	public OrderTicketConfirmation(){
		
	}


	@Override

	public  View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		ViewGroup root = (ViewGroup) inflater.inflate(R.layout.order_tickets_confirmation_layout, null);
	//	MainActivity.GetInstanceTab().mTabHost=(FragmentTabHost) getActivity().findViewById(android.R.id.tabhost);
      //  MainActivity.GetInstanceTab().mTabHost.setVisibility(View.GONE);
		MainActivity.GetInstanceTab().tabHostLayout=(LinearLayout) getActivity().findViewById(R.id.tabhostlayout111111);
		  MainActivity.GetInstanceTab().tabHostLayout.setVisibility(View.GONE);
        mFragmentManager = getActivity().getSupportFragmentManager();
		mContext=getActivity().getApplicationContext();





		mProgressBar = (ProgressBar) root.findViewById(R.id.orderProgressBar);
		MfacebookSharingButton=(Button) root.findViewById(R.id.btn_shareToFacebook);
		mBack=(RelativeLayout) root.findViewById(R.id.backButton_layout);
		mLinearLayout=(LinearLayout) root.findViewById(R.id.orderTicketConfirmationParentLayout);
		mLinearLayout.bringToFront();
		mBackButton=(ImageView) root.findViewById(R.id.orderTicketbackButton);
		
		
		
		   
//		newText=(ImageView) root.findViewById(R.id.newticketconfirmationQrcode);
//		newText.bringToFront();
		/* mnewQrcodeImg=(RelativeLayout) root.findViewById(R.id.newQrcodeImg);
		mnewQrcodeImg.bringToFront();*/
		
		
//		modified
		mticketAndTable=(TextView) root.findViewById(R.id.ticketAndTable);
		mticketAndTable.setText(mTicketItem.getTicketType()+" na "+mOrderTransectionDetailitem.getEventName());
		
		mBackButton.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		mFragmentManager.popBackStack();
		
		  //MainActivity.GetInstanceTab().mTabHost=(FragmentTabHost) getActivity().findViewById(android.R.id.tabhost);
		       // MainActivity.GetInstanceTab().mTabHost.setVisibility(View.VISIBLE);
		  MainActivity.GetInstanceTab().tabHostLayout=(LinearLayout) getActivity().findViewById(R.id.tabhostlayout111111);
		  MainActivity.GetInstanceTab().tabHostLayout.setVisibility(View.VISIBLE);
		//MainActivity.GetInstanceTab().mTabHost=(FragmentTabHost) getActivity().findViewById(android.R.id.tabhost);
       // MainActivity.GetInstanceTab().mTabHost.setVisibility(View.VISIBLE);
	}
});
		
		mBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mFragmentManager.popBackStack();
				
				  //MainActivity.GetInstanceTab().mTabHost=(FragmentTabHost) getActivity().findViewById(android.R.id.tabhost);
				       // MainActivity.GetInstanceTab().mTabHost.setVisibility(View.VISIBLE);
				  MainActivity.GetInstanceTab().tabHostLayout=(LinearLayout) getActivity().findViewById(R.id.tabhostlayout111111);
				  MainActivity.GetInstanceTab().tabHostLayout.setVisibility(View.VISIBLE);
				//MainActivity.GetInstanceTab().mTabHost=(FragmentTabHost) getActivity().findViewById(android.R.id.tabhost);
		       // MainActivity.GetInstanceTab().mTabHost.setVisibility(View.VISIBLE);
			}
		});
		
		
		// facebook Sharing
		
		
		MfacebookSharingButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.i("", "Facebook Sharing Clicking....");
				/*Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
				shareIntent.setType("text/plain");
				shareIntent.putExtra(android.content.Intent.EXTRA_TEXT,mOrderTransectionDetailitem.getEventImageUrl());
				PackageManager pm = v.getContext().getPackageManager();
				List<ResolveInfo> activityList = pm.queryIntentActivities(shareIntent, 0);				//queryIntentActivities(shareIntent, 0);
				for (final ResolveInfo app : activityList) {
				    if ((app.activityInfo.name).contains("facebook")) {
				        final ActivityInfo activity = app.activityInfo;
				        final ComponentName name = new ComponentName(activity.applicationInfo.packageName, activity.name);
				        shareIntent.addCategory(Intent.CATEGORY_LAUNCHER);
				        shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
				        shareIntent.setComponent(name);
				        v.getContext().startActivity(shareIntent);
				        break;
				   }
				}*/
				
				 /*  Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
				   shareIntent.setType("text/plain");
				   shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, (String) v.getTag(R.string.app_name));
				   shareIntent.putExtra(android.content.Intent.EXTRA_TEXT,mOrderTransectionDetailitem.getEventImageUrl());

				   PackageManager pm = v.getContext().getPackageManager();
				   List<ResolveInfo> activityList = pm.queryIntentActivities(shareIntent, 0);
				     for (final ResolveInfo app : activityList) 
				     {
				         if ((app.activityInfo.name).contains("facebook")) 
				         {
				           final ActivityInfo activity = app.activityInfo;
				           final ComponentName name = new ComponentName(activity.applicationInfo.packageName, activity.name);
				          shareIntent.addCategory(Intent.CATEGORY_LAUNCHER);
				          shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
				          shareIntent.setComponent(name);
				          v.getContext().startActivity(shareIntent);
				          break;
				        }
				      }*/
				
				String urlToShare = mOrderTransectionDetailitem.getEventImageUrl();
				Intent intent = new Intent(Intent.ACTION_SEND);
				intent.setType("text/plain");
				// intent.putExtra(Intent.EXTRA_SUBJECT, "Foo bar"); // NB: has no effect!
				intent.putExtra(Intent.EXTRA_TEXT, urlToShare);

				// See if official Facebook app is found
				boolean facebookAppFound = false;
				List<ResolveInfo> matches = mContext.getPackageManager().queryIntentActivities(intent, 0);
				for (ResolveInfo info : matches) {
				    if (info.activityInfo.packageName.toLowerCase().startsWith("com.facebook.katana")) {
				        intent.setPackage(info.activityInfo.packageName);
				        facebookAppFound = true;
				        break;
				    }
				}

				// As fallback, launch sharer.php in a browser
				if (!facebookAppFound) {
				    String sharerUrl = "https://www.facebook.com/sharer/sharer.php?u=" + urlToShare;
				    intent = new Intent(Intent.ACTION_VIEW, Uri.parse(sharerUrl));
				}

				startActivity(intent);
	
			}
		});


		mTicketQrcodeImage = (ImageView) root.findViewById(R.id.ticketconfirmationQrcode);
		Log.i("", "qrcodeimage url...."+mTicketItem.getImageUrl());
		Picasso.with(mContext).load(mTicketItem.getImageUrl()).into(mTicketQrcodeImage);


		mOrderCode=(TextView) root.findViewById(R.id.ticketOrderCode);
		Log.i("", "transection order code...."+mOrderTransectionDetailitem.getOrderCode());
		mOrderCode.setText(getResources().getString(R.string.order)+" "+mOrderTransectionDetailitem.getOrderCode());

		mUserName=(TextView) root.findViewById(R.id.ticketUserName);
//		mUserName.setText(mOrderTransectionDetailitem.getUserName());
		mUserName.setText(mSettingPrefcn.getUserPrefrence(mContext).getVc_user_name());

		mTicketCost=(TextView) root.findViewById(R.id.ticketCost);
		mTicketCost.setText("R$"+mTicketItem.getTicketPrice());


		mCityState=(TextView) root.findViewById(R.id.cityState);
		mCityState.setText(mSettingPrefcn.getUserPrefrence(mContext).getVc_user_country()+" "+mSettingPrefcn.getUserPrefrence(mContext).getVc_user_state());
		
		mTicketConsumption=(TextView) root.findViewById(R.id.ticketConsumption);
		mTicketConsumption.setText("R$"+mTicketItem.getTicketConsumption());

		mTicketCount=(TextView) root.findViewById(R.id.ticketCount);
		mTicketCount.setText(mTicketItem.getItemCount());

		mTicketEventDate=(TextView) root.findViewById(R.id.ticketEventDate);
		mTicketEventDate.setText(mOrderTransectionDetailitem.getEventDate());

		mUserCpf=(TextView) root.findViewById(R.id.UserCpf);
		mUserCpf.setText(getResources().getString(R.string.id)+" "+mSettingPrefcn.getUserPrefrence(mContext).getVc_user_document());

		mUserAdd=(TextView)root.findViewById(R.id.UserAddress);
		mUserAdd.setText(mSettingPrefcn.getUserPrefrence(mContext).getVc_user_add());
		
		mticketAndTable=(TextView) root.findViewById(R.id.ticketAndTable);
//		mticketAndTable.setText(mOrderTransectionDetailitem.get)
		
		
//		mHead2=(LinearLayout) root.findViewById(R.id.head2);
//		Picasso.with(mContext).load(mTicketItem.getImageUrl()).fit().into(mHead2);
//		
		








		return root;
	}
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		  MainActivity.GetInstanceTab().tabHostLayout=(LinearLayout) getActivity().findViewById(R.id.tabhostlayout111111);
		  MainActivity.GetInstanceTab().tabHostLayout.setVisibility(View.GONE);
		super.onAttach(activity);
	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		 MainActivity.GetInstanceTab().tabHostLayout=(LinearLayout) getActivity().findViewById(R.id.tabhostlayout111111);
		  MainActivity.GetInstanceTab().tabHostLayout.setVisibility(View.GONE);
		super.onResume();
	}


}
