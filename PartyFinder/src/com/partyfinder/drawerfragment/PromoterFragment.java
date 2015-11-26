package com.partyfinder.drawerfragment;

import java.util.ArrayList;

import com.partyfinder.R;
import com.partyfinder.AppConstant.PartyFinderConstants;
import com.partyfinder.CustomView.CustomAsyncTask;
import com.partyfinder.CustomView.TaskListener;
import com.partyfinder.adapters.PromoterListviewAdapter;
import com.partyfinder.app.utils.AppUtils;
import com.partyfinder.managers.ContentManager;
import com.partyfinder.managers.SettingPrefrences;
import com.partyfinder.model.EventItem;
import com.partyfinder.model.OrderAssignItem;
import com.partyfinder.model.PromoterItem;
import com.partyfinder.model.UserLoginItem;

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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;

public class PromoterFragment extends Fragment{
	private ArrayList<PromoterItem> promoterList;
	private ListView listView ;
	private CustomAsyncTask mAsyncTask;
	private ProgressBar mProgbar;
	private TaskListener mTaskListener;
	private EventItem mEventItem;
	private UserLoginItem mUserLoginItem;
	private Context mContext;
	private PromoterListviewAdapter mAdtapter;
	private double mPurchaseGrossTotal;
	private OrderAssignItem mAssignOrder;
	private PromoterItem mPromoterItem;
	private FragmentManager mFragmentManager;
	private FragmentTransaction mFragmentTransect;
	private String submitPromoterStatus;
	private Fragment mFragment;
	private String TAG="PromoterFragment class";
	private ImageView forward;
	 
	
	public PromoterFragment(){
		
	}
	public PromoterFragment(EventItem eventItem){
		mEventItem=eventItem;
	}
	public PromoterFragment(EventItem eventItem,ArrayList<PromoterItem> mPromoterList,OrderAssignItem assignOrder,double grossTotal){
		mEventItem=eventItem;
		promoterList=mPromoterList;
		mAssignOrder=assignOrder;
		mPurchaseGrossTotal=grossTotal;
	}
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mContext=getActivity().getApplicationContext();
		mUserLoginItem=SettingPrefrences.getUserPrefrence(mContext);
		mFragmentManager=getActivity().getSupportFragmentManager();
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view=inflater.inflate(R.layout.select_promoter, null);
		listView = (ListView) view. findViewById(R.id.selectpromoter_listview);
		mProgbar=(ProgressBar) view.findViewById(R.id.promProgbar);
		forward=(ImageView) view.findViewById(R.id.selectpromoter_forwardimage);
		if(promoterList!=null){
			if(promoterList.size()>0){
				mAdtapter=new PromoterListviewAdapter(mContext, promoterList, PromoterFragment.this.getActivity());
				listView.setAdapter(mAdtapter);
			}
		}
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				mPromoterItem=promoterList.get(position);
				Log.i("",TAG+"Promoter Item selected : "+position);
			
			}
		});
		forward.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.i("",TAG+"Promoter Item selected : "+mPromoterItem.getPromoterName());
				submitPromoter();
			}
		});
//		loadPromoter();
		
		return view;
	}
	
	private void submitPromoter(){
		
		mTaskListener=new TaskListener() {
			
			@Override
			public void updateUI() {
				// TODO Auto-generated method stub
//				if(promoterList!=null){
//					if(promoterList.size()>0){
//						mAdtapter=new PromoterListviewAdapter(mContext, promoterList, PromoterFragment.this.getActivity());
//						listView.setAdapter(mAdtapter);
//					}
//				}
				
				if(submitPromoterStatus.equalsIgnoreCase("1")){
					mFragmentTransect=mFragmentManager.beginTransaction();
					if(mFragment!=null){
						mFragmentTransect.detach(mFragment);
						mFragment=null;
					}
					mFragment=new ConfirmOrderFragment(mAssignOrder, mEventItem,mPurchaseGrossTotal);
					mFragmentTransect.replace(R.id.fragmentContainer, mFragment);
					mFragmentTransect.addToBackStack(null);
					mFragmentTransect.commitAllowingStateLoss();
				}
			}
			
			@Override
			public void execute() {
				// TODO Auto-generated method stub
				ArrayList<String> xmldata=new ArrayList<String>();
				xmldata.add(mAssignOrder.getOrderCode());
				xmldata.add(mPromoterItem.getPromoterCode());
				String promXml=AppUtils.preparedSubmitPromoterXml(xmldata);
				String promterUrl=PartyFinderConstants.URL_SUMIT_PROMOTER;
			    submitPromoterStatus=ContentManager.getInstance().submitPromoter(promterUrl, promXml);
//				String getPro=PartyFinderConstants.URL_GET_PROMOTER+mEventItem.getVcEventCode()+"/"+mUserLoginItem.getVc_user_code();//"E00027/U00016"
//				promoterList=ContentManager.getInstance().parsePromoter(getPro);
//				promoterList=ContentManager.getInstance().parsePromoter(url)	
			}
		};
		if(mAsyncTask!=null){
			mAsyncTask.cancel(true);
			mAsyncTask=null;
		}
		mAsyncTask=new CustomAsyncTask(mTaskListener, mProgbar,mContext);
		mAsyncTask.execute();
	}
	
	

}
