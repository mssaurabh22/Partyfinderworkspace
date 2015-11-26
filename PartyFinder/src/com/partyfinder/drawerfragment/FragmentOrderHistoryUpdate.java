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
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.AbsListView.OnScrollListener;

public class FragmentOrderHistoryUpdate extends Fragment {


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
	private ImageView btn_back;
	private boolean flg = false;
	private RelativeLayout mbtn_back_Layout;
	private FragmentTransaction mFragmentTransection;
	private Bundle savedState;
	private boolean saved;
	private Fragment mCurrentFragment;
	private FragmentOrderHistoryUpdate mfg;
	private SlideHolder mSlideHolder;

	public FragmentOrderHistoryUpdate(){
		
		setArguments(new Bundle());
	}



	public static Fragment newInstance(Context context) {
		FragmentOrderHistoryUpdate f = new FragmentOrderHistoryUpdate();
		return f;
	}












	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		ViewGroup root = (ViewGroup) inflater.inflate(R.layout.order_history, null);

		try {


			mContext=getActivity().getApplicationContext();
			//		 mFragmentManager=getFragmentManager();
			mFragmentManager = getActivity().getSupportFragmentManager();
			mCurrentFragment = new FragmentOrderHistoryUpdate();
			//		mFragmentManager.saveFragmentInstanceState(mFragmentUpdate);
			Bundle mySavedInstanceState = getArguments();
			//		mListView = (ListView) root.findViewById(R.id.orderDetailListView);
			mbtn_back_Layout = (RelativeLayout) root.findViewById(R.id.btn_back_Layout);
			mListView = (	PullAndLoadListView ) root.findViewById(R.id.orderDetailListView);
//			mListView = (PullToRefreshListView) root.findViewById(R.id.orderDetailListView);
			mProgressBar = (ProgressBar) root.findViewById(R.id.orderProgressBar);
			mSlideHolder= (SlideHolder) getActivity().findViewById(R.id.slideHolder);
			userLoginItem = SettingPrefrences.getUserPrefrence(mContext);
			btn_back = (ImageView) root.findViewById(R.id.btn_back);

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

/*
			mListView.setOnRefreshListener(new IonRefreshListener() {
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


			mbtn_back_Layout.setVisibility(View.VISIBLE);

			mbtn_back_Layout.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(mAsycnTask!=null){
						mAsycnTask.cancel(true);
						mAsycnTask=null;
					}
					FragmentManager manager = getActivity().getSupportFragmentManager();
					FragmentTransaction trans = manager.beginTransaction();
					trans.remove(mCurrentFragment);
					trans.commit();
					mFragmentManager.popBackStack();
//					getActivity().getSupportFragmentManager().beginTransaction().remove(mfg).commit();
					
				  
				
						
				}
			});

			savedState =savedInstanceState;



		} catch (Exception e) {
			// TODO: handle exception
		}
		return root;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub

		//	    	outState.putBundle("SAVEDFRAGMENT", savedState);
		getArguments().putBundle("SAVEDFRAGMENT", savedState);
		Log.i("", "Fragment Order History Update :  onSaveInstanceState is calling : ");

		super.onSaveInstanceState(outState);
	}



	@Override
	public void onActivityCreated(Bundle savedInstanceState) {

		if (savedState != null) {
			// Restore last state for checked position.
			savedInstanceState = savedState;
		}

		super.onActivityCreated(savedInstanceState);
	}











	/*	@Override
	public void onViewStateRestored(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		savedInstanceState.getBundle("SAVEDFRAGMENT");

		Log.i("", "Fragment Order History Update :  onViewStateRestored is calling : ");
		super.onViewStateRestored(savedInstanceState);
	}
	 */


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
								Log.i("", "xxxxxxx  "+position);

								if(mOrderTransectionList.get(position-1).getTransectionStatusCode().equalsIgnoreCase(PartyFinderConstants.ORDER_STATUS_CONFIRMED1) || mOrderTransectionList.get(position-1).getTransectionStatusCode().equalsIgnoreCase(PartyFinderConstants.ORDER_STATUS_CONFIRMED2)){


									mOrderTransection = mOrderTransectionList.get(position);
									mFragmentTransect=mFragmentManager.beginTransaction();
									mFragment=new OrderTransectionDetailItemInfo(mOrderTransection,mOrderTransectionList.get(position), mActivity, mContext);
									mFragmentTransect.replace(R.id.fragmentContainer, mFragment);
									mFragmentTransect.addToBackStack(null);
									mFragmentTransect.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

									mFragmentTransect.commitAllowingStateLoss();


								}else{
									//							Toast.makeText(mContext, "Transection is not Approved Or Rejected...", Toast.LENGTH_SHORT).show();
									//									userTransectionStatusDialogBox();
									CustomDialogBox.showAlert("info", "Os QR Codés estarão disponíveis quando o pagto for aprovado.", getActivity());


								}
							}

						});
					}
				}

				if(mAsycnTask!=null){
					mAsycnTask.cancel(true);
					mAsycnTask=null;
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
									CustomDialogBox.showAlert("info", "Os QR Codés estarão disponíveis quando o pagto for aprovado.", getActivity());


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
		Log.i("", "Fragment Order History Update : onPause is calling :");
		if(mAsycnTask!=null){
			mAsycnTask.cancel(true);
			mAsycnTask=null;
		}
//		getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
		super.onPause();
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		Log.i("", "Fragment Order History Update : onStop is calling :");
		if(mAsycnTask!=null){
			mAsycnTask.cancel(true);
			mAsycnTask=null;
		}
//		getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
		super.onStop();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub

		Log.i("", "Fragment Order History Update :  onDestroy is calling : ");
		if(mAsycnTask!=null){
			mAsycnTask.cancel(true);
			mAsycnTask=null;
		}
//		getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
		super.onDestroy();
	}



	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		Log.i("", "Fragment Order History Update :  onAttach is calling : ");
		if(mAsycnTask!=null){
			mAsycnTask.cancel(true);
			mAsycnTask=null;
		}
		super.onAttach(activity);
	}



	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		Log.i("", "Fragment Order History Update :  onDestroyView is calling : ");
		if(mAsycnTask!=null){
			mAsycnTask.cancel(true);
			mAsycnTask=null;
		}
		super.onDestroyView();
	} 



	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		Log.i("", "Fragment Order History Update :  onDetach is calling : ");
		if(mAsycnTask!=null){
			mAsycnTask.cancel(true);
			mAsycnTask=null;
		}
		super.onDetach();
	}






}


