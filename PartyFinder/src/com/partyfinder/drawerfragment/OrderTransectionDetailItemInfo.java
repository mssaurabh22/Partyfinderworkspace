package com.partyfinder.drawerfragment;

import com.partyfinder.R;
import com.partyfinder.adapters.GetOderTransectionItemsDetailsAdapter;
import com.partyfinder.drawerfragment.FragmentOrderHistory;
import com.partyfinder.model.OrderTransectionDetailsItems;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

public class OrderTransectionDetailItemInfo extends Fragment{
	private Context mContext;
	private Activity mActivity;
	private ProgressBar mProgressBar;
	private OrderTransectionDetailsItems mOrderTransectionDetailItem;
	private GetOderTransectionItemsDetailsAdapter mOrderItemsDetailsAdapter;
	private FragmentManager mFragmentManager;
	private FragmentTransaction mFragmentTransect;
	private Fragment  mFragment;
	private FrameLayout mFrameLayout,mtabFrame;
	private ListView mListview;
	private ImageView mBackButton;
	private RelativeLayout mBack;

	
	public OrderTransectionDetailItemInfo(){
		
	}
	public OrderTransectionDetailItemInfo(OrderTransectionDetailsItems mOrderTransection, OrderTransectionDetailsItems orderItem, Activity act, Context ctx){

		mContext = ctx;
		mOrderTransectionDetailItem=orderItem;
		mActivity=act;
		mOrderTransectionDetailItem = mOrderTransection;
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		ViewGroup root = (ViewGroup) inflater.inflate(R.layout.order_ticket_listview, null);
		mContext=getActivity().getApplicationContext();
		mFragmentManager = getActivity().getSupportFragmentManager();
//		mFragmentManager=getFragmentManager();
		mListview = (ListView) root.findViewById(R.id.orderTicketListView);
		mProgressBar = (ProgressBar) root.findViewById(R.id.orderProgressBar);
		mBackButton=(ImageView) root.findViewById(R.id.orderTicketbackButton);
		mFrameLayout=(FrameLayout) root.findViewById(R.id.fragmentContainer);
		mtabFrame=(FrameLayout) root.findViewById(R.id.realtabcontent);
		mBack=(RelativeLayout) root.findViewById(R.id.backButton_layout);
//		mTabHost=(LinearLayout) root.findViewById(R.id.TabLayout);
		mFrameLayout.bringToFront();
		
		
	mBackButton.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			mFragmentManager.popBackStack();	
			/*mFragmentTransect=mFragmentManager.beginTransaction();
			mFragment=new FragmentOrderHistory();
			mFragmentTransect.replace(R.id.fragmentContainer, mFragment);
//			mFragmentTransect.addToBackStack(null);
			mFragmentTransect.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			mFragmentTransect.commitAllowingStateLoss();*/
			
		}
	});

	mBack.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			mFragmentManager.popBackStack();	
			mFragmentTransect=mFragmentManager.beginTransaction();
			mFragment=new FragmentOrderHistory();
			mFragmentTransect.replace(R.id.fragmentContainer, mFragment);
//			mFragmentTransect.addToBackStack(null);
			mFragmentTransect.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			mFragmentTransect.commitAllowingStateLoss();
			
		}
	});


		mOrderItemsDetailsAdapter = new GetOderTransectionItemsDetailsAdapter(mOrderTransectionDetailItem, mContext);
		mListview.setAdapter(mOrderItemsDetailsAdapter);




		mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {



			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				Log.i("", "orderConfirmationClicking....." +position);
			
				mFragmentTransect=mFragmentManager.beginTransaction();
				mFragment=new OrderTicketConfirmation(mOrderTransectionDetailItem, mOrderTransectionDetailItem.getOrderTicketItems().get(position), mActivity, mContext);
				mFragmentTransect.replace(R.id.fragmentContainer, mFragment);
				mFragmentTransect.addToBackStack(null);
				mFragmentTransect.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
				mFragmentTransect.commitAllowingStateLoss();
				

			}
		});




		return root;
	}

	
    

}
