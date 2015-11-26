package com.partyfinder;

import java.util.ArrayList;

import com.partyfinder.AppConstant.PartyFinderConstants;
import com.partyfinder.CustomView.CustomAsyncTask;
import com.partyfinder.CustomView.TaskListener;
import com.partyfinder.adapters.PurchaseTicketAdapter;
import com.partyfinder.customInterface.PurchaseTicketsCallBack;
import com.partyfinder.managers.ContentManager;
import com.partyfinder.managers.SettingPrefrences;
import com.partyfinder.model.EventItem;
import com.partyfinder.model.TicketDetailsItem;
import com.partyfinder.model.UserLoginItem;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;

public class PurchaseTicketActivity extends Activity implements PurchaseTicketsCallBack{
	
	public EventItem mEventItem;
	private ListView addticket_listview;
	private UserLoginItem mLoginItem;
	private String TAG="PurchaseTicketActivity class";
	private ArrayList<TicketDetailsItem> ticketDetList;
	private TaskListener mTicketTaskListener;
	private PurchaseTicketAdapter mAdapter;
	private Context mContext;
	private CustomAsyncTask mAsyncTask;
	private ProgressBar mProgbar;
	private ImageView goForpurchaseTck;
	private ArrayList<TicketDetailsItem> selectedTck;
	
	public PurchaseTicketActivity(){
		mEventItem=mEventItem;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addtickets);
		mLoginItem=SettingPrefrences.getUserPrefrence(this);
		Log.i("",TAG+"User Code : "+mLoginItem.getVc_user_code());
		mContext=this;
		Bundle bundle = getIntent().getExtras();
        mEventItem = (EventItem) bundle
				.getSerializable(PartyFinderConstants.EXTRA_EVENTITEM);
        Log.i("",TAG+"Event code : "+mEventItem.getVcEventCode());
		addticket_listview=(ListView)findViewById(R.id.addticket_listview);
		mProgbar=(ProgressBar) findViewById(R.id.addTicketProg);
		goForpurchaseTck=(ImageView) findViewById(R.id.addticket_forward_btn);
		goForpurchaseTck.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				selectedTck=mAdapter.getmTicketItems();
				for(int i=0;i<selectedTck.size();i++){
				Log.i("","Selected ticket : "+i+" : "+selectedTck.get(i).getTotalCount());
				}
				
				PurchaseTicketDetailActivity mpurchaseDet=new PurchaseTicketDetailActivity(selectedTck);
				Intent theIntent=new Intent(mContext,mpurchaseDet.getClass());
				theIntent.putExtra("FILES_TO_SEND", filterPurchasedTicket(selectedTck));
				startActivity(theIntent);
			}
		});
		mTicketTaskListener=new TaskListener() {
			
			@Override
			public void updateUI() {
				// TODO Auto-generated method stub
				Log.i("",TAG+" [ TICKET DETAILS LIST ]"+ticketDetList.size());
				mAdapter=new PurchaseTicketAdapter(mContext, ticketDetList,PurchaseTicketActivity.this,mEventItem,PurchaseTicketActivity.this);
				
				addticket_listview.setAdapter(mAdapter);
				addticket_listview.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						// TODO Auto-generated method stub
						Log.i(""," Wahid ......"+position);
					}
				});
				
			}
			
			@Override
			public void execute() {
				// TODO Auto-generated method stub
				ticketDetList=ContentManager.getInstance().parseTicketList(mEventItem);
			}
		};
		loadTicketTask();
	
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
		if(mAsyncTask!=null){
			mAsyncTask.cancel(true);
			mAsyncTask=null;
			
		}
		mAsyncTask=new CustomAsyncTask(mTicketTaskListener, mProgbar, mContext);
		mAsyncTask.execute();
	}

	@Override
	public void selectedTicket(ArrayList<TicketDetailsItem> selectedItemList) {
		// TODO Auto-generated method stub
		Log.i("","Selected Ticket : "+selectedItemList.size());
		selectedTck=selectedItemList;
	}
	
	
}
