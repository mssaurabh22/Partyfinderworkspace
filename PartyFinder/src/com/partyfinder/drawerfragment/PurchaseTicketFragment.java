package com.partyfinder.drawerfragment;

import java.util.ArrayList;

import com.partyfinder.R;
import com.partyfinder.CustomView.CustomAsyncTask;
import com.partyfinder.CustomView.CustomDialogBox;
import com.partyfinder.CustomView.TaskListener;
import com.partyfinder.adapters.PurchaseTicketAdapter;
import com.partyfinder.customInterface.PurchaseTicketsCallBack;
import com.partyfinder.managers.ContentManager;
import com.partyfinder.managers.SettingPrefrences;
import com.partyfinder.model.EventItem;
import com.partyfinder.model.TicketDetailsItem;
import com.partyfinder.model.UserLoginItem;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RelativeLayout;

public class PurchaseTicketFragment extends Fragment implements PurchaseTicketsCallBack{

	
	public EventItem mEventItem;
	private ListView addticket_listview;
	private UserLoginItem mLoginItem;
//	private String TAG="PurchaseTicketActivity class";
	private ArrayList<TicketDetailsItem> ticketDetList;
	private TaskListener mTicketTaskListener;
	private PurchaseTicketAdapter mAdapter;
	private Context mContext;
	private CustomAsyncTask mAsyncTask;
	private ProgressBar mProgbar;
	private ImageButton goForpurchaseTckimg,backButtonimg;
	private RelativeLayout goForpurchaseTck,backButton;
	private ArrayList<TicketDetailsItem> selectedTck;
	private FragmentManager mFragmentManager;
	private FragmentTransaction mFragmentTransect;
	private Fragment mFragment;
	ProgressDialog mProgressDialog ;
	private boolean AbleToPurchase = false;
	public PurchaseTicketFragment(EventItem eventItem) {
		// TODO Auto-generated constructor stub
		mEventItem=eventItem;
		
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mFragmentManager=getActivity().getSupportFragmentManager();
		
		mContext=getActivity().getApplicationContext();
		mLoginItem=SettingPrefrences.getUserPrefrence(mContext);
		super.onCreate(savedInstanceState);
	}

	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		View view=inflater.inflate(R.layout.addtickets,null);
		addticket_listview=(ListView)view.findViewById(R.id.addticket_listview);
		mProgbar=(ProgressBar) view.findViewById(R.id.addTicketProg);
		goForpurchaseTckimg=(ImageButton)view. findViewById(R.id.addticket_forward_btn);
		backButtonimg=(ImageButton) view.findViewById(R.id.addtcket_back_btn);
		goForpurchaseTck=(RelativeLayout)view. findViewById(R.id.forwardButton_layout);
		goForpurchaseTck.bringToFront();
		backButton=(RelativeLayout) view.findViewById(R.id.backButton_layout);
		backButton.bringToFront();
		
		backButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				mProgressDialog.dismiss();
//				mFragmentTransect=mFragmentManager.beginTransaction();
				if(mAsyncTask!=null){
					mAsyncTask.cancel(true);
					mAsyncTask=null;
				}
				mFragmentManager.popBackStack();
			
			}
		});
		
		
		backButtonimg.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				mProgressDialog.dismiss();
//				mFragmentTransect=mFragmentManager.beginTransaction();
				if(mAsyncTask!=null){
					mAsyncTask.cancel(true);
					mAsyncTask=null;
				}
				mFragmentManager.popBackStack();
				
			}
		});
		
		
		
		
		
		goForpurchaseTckimg.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(AbleToPurchase){
				selectedTck=new ArrayList<TicketDetailsItem>();
				for(int i=0;i<mAdapter.getmTicketItems().size();i++){
					if(mAdapter.getmTicketItems().get(i).getTotalCount()>0)
						selectedTck.add(mAdapter.getmTicketItems().get(i));
				}
				
//				for(int i=0;i<selectedTck.size();i++){
//				Log.i("","Selected ticket : "+i+" : "+selectedTck.get(i).getTotalCount());
//				}
				if(selectedTck.size()>0){
				/*PurchaseTicketDetailActivity mpurchaseDet=new PurchaseTicketDetailActivity(selectedTck);
				Intent theIntent=new Intent(mContext,mpurchaseDet.getClass());
				theIntent.putExtra("FILES_TO_SEND", filterPurchasedTicket(selectedTck));
				theIntent.putExtra(PartyFinderConstants.EXTRA_EVENTITEM, mEventItem);
				startActivity(theIntent);*/
					
					mFragmentTransect=mFragmentManager.beginTransaction();
					if(mFragment!=null){
						mFragmentTransect.detach(mFragment);
						mFragment=null;
					}
					mFragment=new PurchaseTicketDeatailsFragment(mEventItem, filterPurchasedTicket(selectedTck));
					mFragmentTransect.replace(R.id.fragmentContainer, mFragment);
					mFragmentTransect.addToBackStack(null);
					mFragmentTransect.commitAllowingStateLoss();
					
				
				}else{
					
					/*Dialog dialog=new Dialog(getActivity());
					dialog.setTitle(R.string.addTicketToMakePurchase);
					dialog.show();
					*/
					CustomDialogBox.showAlert(getResources().getString(R.string.errorDialog), getResources().getString(R.string.addTicketToMakePurchase), getActivity());
//					CustomToast.showToastMessage(mContext, getResources().getString(R.string.addTicketToMakePurchase), 1000);
				}
				}
			}
		});
		
		
		
		goForpurchaseTck.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(AbleToPurchase){
				selectedTck=new ArrayList<TicketDetailsItem>();
				for(int i=0;i<mAdapter.getmTicketItems().size();i++){
					if(mAdapter.getmTicketItems().get(i).getTotalCount()>0)
						selectedTck.add(mAdapter.getmTicketItems().get(i));
				}
				
//				for(int i=0;i<selectedTck.size();i++){
//				Log.i("","Selected ticket : "+i+" : "+selectedTck.get(i).getTotalCount());
//				}
				if(selectedTck.size()>0){
				/*PurchaseTicketDetailActivity mpurchaseDet=new PurchaseTicketDetailActivity(selectedTck);
				Intent theIntent=new Intent(mContext,mpurchaseDet.getClass());
				theIntent.putExtra("FILES_TO_SEND", filterPurchasedTicket(selectedTck));
				theIntent.putExtra(PartyFinderConstants.EXTRA_EVENTITEM, mEventItem);
				startActivity(theIntent);*/
					
					mFragmentTransect=mFragmentManager.beginTransaction();
					if(mFragment!=null){
						mFragmentTransect.detach(mFragment);
						mFragment=null;
					}
					mFragment=new PurchaseTicketDeatailsFragment(mEventItem, filterPurchasedTicket(selectedTck));
					mFragmentTransect.replace(R.id.fragmentContainer, mFragment);
					mFragmentTransect.addToBackStack(null);
					mFragmentTransect.commitAllowingStateLoss();
					
				
				}else{
					
					/*Dialog dialog=new Dialog(getActivity());
					dialog.setTitle(R.string.addTicketToMakePurchase);
					dialog.show();
					*/
					CustomDialogBox.showAlert(getResources().getString(R.string.errorDialog), getResources().getString(R.string.addTicketToMakePurchase), getActivity());
//					CustomToast.showToastMessage(mContext, getResources().getString(R.string.addTicketToMakePurchase), 1000);
				}
			}}
		});
		loadTicketTask();
		return view;
	}
	
	
	
	private ArrayList<TicketDetailsItem> filterPurchasedTicket(ArrayList<TicketDetailsItem> ticketDetailList){
		ArrayList<TicketDetailsItem> tempArray=new ArrayList<TicketDetailsItem>();
		for(int i=0;i<ticketDetailList.size();i++){
			if(ticketDetailList.get(i).getTotalCount()!=0){
				tempArray.add(ticketDetailList.get(i));
			}
		}
		return tempArray;
		
		
		
	}
	private void loadTicketTask(){
		
		mTicketTaskListener=new TaskListener() {
			
			@Override
			public void updateUI() {
				// TODO Auto-generated method stub
				
				if(ticketDetList!=null){
					if(ticketDetList.size()>0){
						
			
//				Log.i("",TAG+" [ TICKET DETAILS LIST ]"+ticketDetList.size());
				mAdapter=new PurchaseTicketAdapter(mContext, ticketDetList,getActivity(),mEventItem,PurchaseTicketFragment.this);
				
				addticket_listview.setAdapter(mAdapter);
				AbleToPurchase = true;
				addticket_listview.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						// TODO Auto-generated method stub
//						Log.i(""," Wahid ......"+position);
					}
				});
					}
				}
			}
			
			@Override
			public void execute() {
				// TODO Auto-generated method stub
				ticketDetList=ContentManager.getInstance().parseTicketList(mEventItem);
			}
		};
		if(mAsyncTask!=null){
			mAsyncTask.cancel(true);
			mAsyncTask=null;
			
		}
	 mProgressDialog = new ProgressDialog(getActivity(),R.style.NewCustomDialog);
//		mProgressDialog.setMessage(getResources().getString(R.string.ProcessingText));
	
		mProgressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
	
		
		mAsyncTask=new CustomAsyncTask(mTicketTaskListener, mProgbar, mContext);
		mAsyncTask.execute();
	}
	@Override
	public void selectedTicket(ArrayList<TicketDetailsItem> selectedItemList) {
		// TODO Auto-generated method stub
//		Log.i("","Selected Ticket : "+selectedItemList.size());
		selectedTck=selectedItemList;
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		if(mAsyncTask!=null){
			mAsyncTask.cancel(true);
			mAsyncTask=null;
		}
		super.onDestroy();
	}
	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		if(mAsyncTask!=null){
			mAsyncTask.cancel(true);
			mAsyncTask=null;
		}
		super.onDestroyView();
	}
	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		if(mAsyncTask!=null){
			mAsyncTask.cancel(true);
			mAsyncTask=null;
		}
		super.onDetach();
	}
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		if(mAsyncTask!=null){
			mAsyncTask.cancel(true);
			mAsyncTask=null;
		}
		super.onPause();
	}
	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		if(mAsyncTask!=null){
			mAsyncTask.cancel(true);
			mAsyncTask=null;
		}
		super.onStop();
	}
	
	

}
