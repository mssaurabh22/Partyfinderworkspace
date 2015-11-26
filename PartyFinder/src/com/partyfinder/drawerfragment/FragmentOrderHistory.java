package com.partyfinder.drawerfragment;

import java.util.ArrayList;



import com.costum.android.widget.PullAndLoadListView;
import com.costum.android.widget.PullToRefreshListView.OnRefreshListener;
import com.partyfinder.MainActivity;
import com.partyfinder.R;
import com.partyfinder.AppConstant.PartyFinderConstants;

import com.partyfinder.CustomView.CustomAsyncTask;
import com.partyfinder.CustomView.CustomDialogBox;
import com.partyfinder.CustomView.TaskListener;
import com.partyfinder.adapters.GetOrderTransectionDetailsAdapter;
import com.partyfinder.app.utils.AppUtils;
import com.partyfinder.managers.ContentManager;
import com.partyfinder.managers.SettingPrefrences;
import com.partyfinder.model.OrderTicketItems;
import com.partyfinder.model.OrderTransectionDetailsItems;
import com.partyfinder.model.UserLoginItem;
import com.partyfinder.widget.SlideHolder;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.AbsListView.OnScrollListener;

public class FragmentOrderHistory extends Fragment {
	private Context mContext;

	private Activity mActivity;
	private GetOrderTransectionDetailsAdapter mOrderTransectionAdapter;
	private ArrayList<OrderTransectionDetailsItems> mOrderTransectionList;
	private OrderTransectionDetailsItems mOrderTransection;
	private ArrayList<OrderTicketItems> mOrderTicketList;
	private UserLoginItem userLoginItem;
	//	private ListView mListView;
//	private PullToRefreshListView mListView;
	PullAndLoadListView mListView;
//	PullToUpdateListView mListView;
	private FragmentManager mFragmentManager;
	private FragmentTransaction mFragmentTransect;
	private Fragment  mFragment;
	//private Button transectionStatusButton;
	private CustomAsyncTask mAsycnTask;
	private ProgressBar mProgressBar;
	private FrameLayout mFrameLayout;
	private RelativeLayout morderHistoryWholeLayout;
	private ImageView btn_back;
	private LayoutInflater inflator;
	private SlideHolder mSlideHolder;
	
	public FragmentOrderHistory(){

	}


	public static Fragment newInstance(Context context) {
		FragmentOrderHistory f = new FragmentOrderHistory();
		return f;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		ViewGroup root = (ViewGroup) inflater.inflate(R.layout.order_history, null);
//		root.setLayerType(View.LAYER_TYPE_HARDWARE, null);
		mContext=getActivity().getApplicationContext();
		inflator=LayoutInflater.from(getActivity());
		View layerView = inflator.inflate(R.layout.order_history,  null, false);
//		layerView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
		
		
		
		//		 mFragmentManager=getFragmentManager();
		mFragmentManager = getActivity().getSupportFragmentManager();
		//		mListView = (ListView) root.findViewById(R.id.orderDetailListView);
//		mListView = (PullToRefreshListView) root.findViewById(R.id.orderDetailListView);
		mListView = (PullAndLoadListView) root.findViewById(R.id.orderDetailListView);		
		mListView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
		morderHistoryWholeLayout = (RelativeLayout) root.findViewById(R.id.orderHistoryWholeLayout);
		morderHistoryWholeLayout.setLayerType(View.LAYER_TYPE_HARDWARE, null);
		mSlideHolder=(SlideHolder) getActivity().findViewById(R.id.slideHolder);
		mProgressBar = (ProgressBar) root.findViewById(R.id.orderProgressBar);
		userLoginItem=SettingPrefrences.getUserPrefrence(mContext);
		btn_back=(ImageView) root.findViewById(R.id.btn_back);
		btn_back.setVisibility(View.GONE);
		mFrameLayout=(FrameLayout) root.findViewById(R.id.fragmentContainer);
		mFrameLayout.bringToFront();
		loadFriendListData();

		mListView.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				RefreshloadFriendListData();
			}
		});

		
mListView.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				mSlideHolder.setEnabled(true);
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				mSlideHolder.setEnabled(false);
			}
		});

/*mListView.setOnRefreshListener(new IonRefreshListener() {
	  @Override
	    public void onRefreshUp() {


	
			RefreshloadFriendListData();			

				
	    }

	    @Override
	    public void onRefeshDown() {
	    //your code
	    	mSlideHolder.setEnabled(false);
	    	mListView.onRefreshDownComplete(null);
	    }

	        });*/
		btn_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
			}
		});

		return root;
	}




	private void loadFriendListData(){

		TaskListener mOrderDetailTaskListner= new TaskListener() {

			@Override
			public void updateUI() {

				// TODO Auto-generated method stub
				if(mOrderTransectionList!=null){
					if(mOrderTransectionList.size()>0){

						mOrderTransectionAdapter=new GetOrderTransectionDetailsAdapter(mOrderTransectionList, mContext);
						mListView.setAdapter(mOrderTransectionAdapter);

						mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> arg0, View arg1,
									int position, long arg3) {
								// TODO Auto-generated method stub
								Log.i("", "xxxxxxx item position :  "+position);
								Log.i("", "xxxxxxx status code :  "+mOrderTransectionList.get(position).getTransectionStatusCode());

								if(mOrderTransectionList.get(position-1).getTransectionStatusCode().equalsIgnoreCase(PartyFinderConstants.ORDER_STATUS_CONFIRMED1) || mOrderTransectionList.get(position-1).getTransectionStatusCode().equalsIgnoreCase(PartyFinderConstants.ORDER_STATUS_CONFIRMED2)){


									mOrderTransection = mOrderTransectionList.get(position-1);
									mFragmentTransect=mFragmentManager.beginTransaction();
									mFragment=new OrderTransectionDetailItemInfo(mOrderTransection,mOrderTransectionList.get(position), mActivity, mContext);
									mFragmentTransect.replace(R.id.fragmentContainer, mFragment);
									mFragmentTransect.addToBackStack(null);
									mFragmentTransect.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
									mFragmentTransect.commitAllowingStateLoss();


								}else{
									//							Toast.makeText(mContext, "Transection is not Approved Or Rejected...", Toast.LENGTH_SHORT).show();
									//									userTransectionStatusDialogBox();
									CustomDialogBox.showAlert("Info", "Os QR Codés estarão disponíveis quando o pagto for aprovado.", getActivity());


								}
							}

						});
					}
				}


			}


			@Override
			public void execute() {
				// TODO Auto-generated method stub

				try {

					mOrderTransectionList = ContentManager.getInstance().parseGetOrderTransectionDetails(userLoginItem);	
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		};

		if(mAsycnTask!=null){
			mAsycnTask.cancel(true);
			mAsycnTask=null;
		}
		mAsycnTask=new CustomAsyncTask(mOrderDetailTaskListner, mProgressBar, mContext);
		mAsycnTask.execute();
	}





	public void userTransectionStatusDialogBox(){				
		final Dialog userTransectionStatus=new Dialog(getActivity());
		userTransectionStatus.setContentView(R.layout.order_rejection_pending_alert_popup);
		userTransectionStatus.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		Button transectionStatusButton =(Button) userTransectionStatus.findViewById(R.id.orderTransectionStatusButton);
		userTransectionStatus.setCancelable(false);
		userTransectionStatus.show();
		transectionStatusButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				userTransectionStatus.dismiss();	
			}
		});

	}


	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		MainActivity.GetInstanceTab().tabHostLayout=(LinearLayout) getActivity().findViewById(R.id.tabhostlayout111111);
		MainActivity.GetInstanceTab().tabHostLayout.setVisibility(View.VISIBLE);
		super.onCreate(savedInstanceState);
	}


	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		MainActivity.GetInstanceTab().tabHostLayout=(LinearLayout) getActivity().findViewById(R.id.tabhostlayout111111);
		MainActivity.GetInstanceTab().tabHostLayout.setVisibility(View.VISIBLE);
		super.onResume();
	}



	private void RefreshloadFriendListData(){

		TaskListener mOrderDetailTaskListner= new TaskListener() {

			@Override
			public void updateUI() {
				mListView.onRefreshComplete();
				// TODO Auto-generated method stub
				if(mOrderTransectionList!=null){
					if(mOrderTransectionList.size()>0){

						mOrderTransectionAdapter=new GetOrderTransectionDetailsAdapter(mOrderTransectionList, mContext);
						mListView.setAdapter(mOrderTransectionAdapter);

						mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> arg0, View arg1,
									int position, long arg3) {
								// TODO Auto-generated method stub
								Log.i("", "xxxxxxx  "+position);

								if(mOrderTransectionList.get(position).getTransectionStatusCode().equalsIgnoreCase(PartyFinderConstants.ORDER_STATUS_CONFIRMED1) || mOrderTransectionList.get(position).getTransectionStatusCode().equalsIgnoreCase(PartyFinderConstants.ORDER_STATUS_CONFIRMED2)){


									mOrderTransection = mOrderTransectionList.get(position);
									mFragmentTransect=mFragmentManager.beginTransaction();
									mFragment=new OrderTransectionDetailItemInfo(mOrderTransection,mOrderTransectionList.get(position), mActivity, mContext);
									mFragmentTransect.replace(R.id.fragmentContainer, mFragment);
									mFragmentTransect.addToBackStack(null);
									mFragmentTransect.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

									mFragmentTransect.commitAllowingStateLoss();


								}else{
									//							Toast.makeText(mContext, "Transection is not Approved Or Rejected...", Toast.LENGTH_SHORT).show();
									CustomDialogBox.showAlert("Info", "Os QR Codés estarão disponíveis quando o pagto for aprovado.", getActivity());


								}
							}

						});
					}
				}


			}


			@Override
			public void execute() {
				// TODO Auto-generated method stub

				try {
					
						mOrderTransectionList = ContentManager.getInstance().parseGetOrderTransectionDetails(userLoginItem);	
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		};

		if(mAsycnTask!=null){
			mAsycnTask.cancel(true);
			mAsycnTask=null;
		}
		mAsycnTask=new CustomAsyncTask(mOrderDetailTaskListner, mContext);
		mAsycnTask.execute();
	}


	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		if(mAsycnTask!=null){
			mAsycnTask.cancel(true);
			mAsycnTask=null;
		}
		Log.i("", "xx  onPause Calling(Event) : ");
		super.onPause();
	}


	@Override
	public void onStart() {
		// TODO Auto-generated method stub

		Log.i("", "xx  onStart Calling(Event) : ");
		super.onStart();
	}


	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		if(mAsycnTask!=null){
			mAsycnTask.cancel(true);
			mAsycnTask=null;
		}
		Log.i("", "xx  onStop Calling(Event) : ");
		super.onStop();
	}

}