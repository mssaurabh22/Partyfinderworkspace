package com.partyfinder;

import java.util.ArrayList;

import com.partyfinder.adapters.OrderSummaryAdapter;
import com.partyfinder.managers.SettingPrefrences;
import com.partyfinder.model.TicketDetailsItem;
import com.partyfinder.model.UserLoginItem;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class PurchaseTicketDetailActivity extends Activity{

	private UserLoginItem mUserLogin;
	private Context mContex;
	private ImageView mBackButton;
	private TextView userName,city,state,grossTicketToatal;
	private ArrayList<TicketDetailsItem> mTicketList;
	private ListView orderSumListview;
	private OrderSummaryAdapter mOrdersummaryAdapter;
	public PurchaseTicketDetailActivity() {
		// TODO Auto-generated constructor stub
	}
	PurchaseTicketDetailActivity(ArrayList<TicketDetailsItem> ticketList){
		mTicketList=ticketList;
		Log.i("","Calling constructor...");
		
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Log.i("","Calling onCreate...");
		setContentView(R.layout.ordersummary);
		mContex=this;
		mUserLogin=SettingPrefrences.getUserPrefrence(mContex);
		mBackButton=(ImageView) findViewById(R.id.back_img);
		userName=(TextView) findViewById(R.id.ordSumaryUserName);
		grossTicketToatal=(TextView) findViewById(R.id.totaPrice);
		userName.setText(mUserLogin.getVc_user_name());
	
		city=(TextView) findViewById(R.id.ordSumryCity);
		city.setText(mUserLogin.getVc_user_city());
		state=(TextView) findViewById(R.id.ordSumryState);
		state.setText(mUserLogin.getVc_user_state());
	
		orderSumListview=(ListView) findViewById(R.id.orderSumryList);
		
		mTicketList =(ArrayList<TicketDetailsItem>)getIntent().getSerializableExtra("FILES_TO_SEND");
		Log.i("","Slected TicktSize : "+mTicketList.size());
		
		double grossTicketTotal = 0;
		for(int i=0;i<mTicketList.size();i++){
			grossTicketTotal=mTicketList.get(i).getTotalTicketPrice()+grossTicketTotal;
		}
		grossTicketToatal.setText("R$"+grossTicketTotal);
		
		mOrdersummaryAdapter=new OrderSummaryAdapter(mTicketList,mContex);
		orderSumListview.setAdapter(mOrdersummaryAdapter);
	}
	
	
	
	
	
	
	

	
}
