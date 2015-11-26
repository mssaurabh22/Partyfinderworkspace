package com.partyfinder;

import java.util.ArrayList;

import com.google.android.gms.internal.mc;
import com.partyfinder.CustomView.CustomAsyncTask;
import com.partyfinder.CustomView.TaskListener;
import com.partyfinder.managers.ContentManager;
import com.partyfinder.model.PromoterItem;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;

public class SelectPromoter extends Fragment{
	
	private FragmentManager mFragmentManager;
	private FrameLayout mFrameLayout;
	private ListView mListView;
	private CustomAsyncTask mAsycnTask;
	private Context mContext;
	private ArrayList<PromoterItem>mPromoterList;
	
	
	public SelectPromoter() {
		// TODO Auto-generated constructor stub
	}
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		ViewGroup root=(ViewGroup) inflater.inflate(R.layout.select_promoter, null);
		mFragmentManager=getActivity().getSupportFragmentManager();
		mListView=(ListView) root.findViewById(R.id.selectpromoter_listview);
		mFrameLayout=(FrameLayout) root.findViewById(R.id.fragmentContainer);
		mFrameLayout.bringToFront();
		
		
		return root;
	}
	
	
	private void loadPromoterList(){

		if(mAsycnTask!=null){
			mAsycnTask.cancel(true);
			mAsycnTask=null;
		}
		mAsycnTask=new CustomAsyncTask(mSelectPromoterListener, mContext);
		mAsycnTask.execute();
	}
	
	TaskListener mSelectPromoterListener=new TaskListener() {
		
		@Override
		public void updateUI() {
			// TODO Auto-generated method stub
			if(mPromoterList!=null){
				if(mPromoterList.size()>0){
					
				}
					
				
			}
			
		}
		
		@Override
		public void execute() {
			// TODO Auto-generated method stub
		//	mPromoterList=ContentManager.getInstance().parsePromoter(url, xml);
			
		}
	};
	

}
