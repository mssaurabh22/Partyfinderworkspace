package com.partyfinder;

import java.io.IOException;
import java.util.ArrayList;

import com.partyfinder.CustomView.CustomAsyncTask;
import com.partyfinder.CustomView.TaskListener;
import com.partyfinder.adapters.EventAdapter;
import com.partyfinder.managers.ContentManager;
import com.partyfinder.model.EventItem;
import com.partyfinder.model.VenuesItem;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

public class ViewAgendaActivity extends Fragment {

	private CustomAsyncTask mAsyncTask;
	private Context mContext;
	private ProgressBar mProgressBar;
	private EventAdapter mEventAdapter;
	private VenuesItem mVenuesItem;
	
	private ImageView mimg_SearchIcon;
	private ImageView mimgViewReset;
	private EditText metEventsearch;
	private RelativeLayout mlayouts_search,mBack;
	
	public static ViewAgendaActivity mFragmentViewAgendaActivity;
	private ListView mListView;
	private ArrayList<EventItem> mEventItemList;
	private ImageView mBackButton;
	private FragmentManager mFragmentManager;
	
	
public ViewAgendaActivity(){
		
	}
	
	
	public static  ViewAgendaActivity getInstance() {
		// TODO Auto-generated constructor stub
		if(mFragmentViewAgendaActivity!=null){
			return mFragmentViewAgendaActivity;
		}else{
			return new ViewAgendaActivity();
		}
	}
	
	public static Fragment newInstance(Context context) {
		ViewAgendaActivity f = new ViewAgendaActivity();

		return f;
	}
		
	
	public ViewAgendaActivity(VenuesItem mVenuesItemss) {
		// TODO Auto-generated constructor stub
		mVenuesItem=mVenuesItemss;
	}


	


	/*@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.event_view_agenda);*/
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		ViewGroup root = (ViewGroup) inflater.inflate(R.layout.event_view_agenda, null);
		mFragmentManager = getActivity().getSupportFragmentManager();
		
		 //MainActivity.GetInstanceTab().mTabHost=(FragmentTabHost) getActivity().findViewById(android.R.id.tabhost);
	      //  MainActivity.GetInstanceTab().mTabHost.setVisibility(View.VISIBLE);
		MainActivity.GetInstanceTab().tabHostLayout=(LinearLayout) getActivity().findViewById(R.id.tabhostlayout111111);
		  MainActivity.GetInstanceTab().tabHostLayout.setVisibility(View.VISIBLE);
	
	
		mContext=getActivity().getApplicationContext();
		/*mVenuesItem=(VenuesItem) getIntent().getExtras().get("venuesItem");
		if(mVenuesItem!=null){
		Log.i("","Company code : "+mVenuesItem.getVc_company_code());
		}*/
		mlayouts_search=(RelativeLayout) root.findViewById(R.id.layouts_search);
		mimg_SearchIcon=(ImageView) root.findViewById(R.id.img_SearchIcon);
		mBack=(RelativeLayout) root.findViewById(R.id.backButton_layout);
		mimgViewReset=(ImageView) root.findViewById(R.id.imgViewReset);
		metEventsearch=(EditText) root.findViewById(R.id.etEventsearch);
		
		metEventsearch.addTextChangedListener(searchWatcher);
		
		mimg_SearchIcon.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mlayouts_search.setVisibility(View.VISIBLE);
			}
		});
		mimgViewReset.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mlayouts_search.setVisibility(View.GONE);	
			}
		});
		
		
		
		mProgressBar=(ProgressBar)root.findViewById(R.id.progressBarLoadEvent);
		mListView=(ListView)root.findViewById(R.id.eventViewAgendaListView);
		mBackButton=(ImageView) root.findViewById(R.id.backBtnViewAgenda);
		
		
		
		mBackButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mFragmentManager.popBackStack();
			}
		});
		mBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mFragmentManager.popBackStack();
			}
		});
		
		
		
		
		
		executeRecommendFrndList();
		
	
	return root;
}
	
	
	public void executeRecommendFrndList(){
		if(mAsyncTask!=null){
			mAsyncTask.cancel(true);
			mAsyncTask=null;
		}
			mAsyncTask=new CustomAsyncTask(viewAgendaListener,mProgressBar,mContext);
			mAsyncTask.execute();
	}
	
	
	TaskListener viewAgendaListener=new TaskListener() {
		
		@Override
		public void updateUI() {
			// TODO Auto-generated method stub
			if(mEventItemList!=null){
				if(mEventItemList.size()>0){
					Log.i("", "EventList size:  "+mEventItemList);
					mEventAdapter=new EventAdapter(mContext, mEventItemList);
					mListView.setAdapter(mEventAdapter);
				}
			}
			
		}
		
		@Override
		public void execute() {
			// TODO Auto-generated method stub
			try {
				mEventItemList=ContentManager.getInstance().parseEventItem(mVenuesItem);
				Log.i("", "View Agenda EventList:  "+mEventItemList.size());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	};

	
	
	
	private TextWatcher searchWatcher=new TextWatcher() {
		
		@Override
		public void onTextChanged(CharSequence txt, int start, int before, int count) {
			int textlength = txt.length();
			if(mEventItemList!=null && mEventItemList.size()>0){
				ArrayList<EventItem> tempArrayList = new ArrayList<EventItem>();
	           for(EventItem c: mEventItemList){
	              if (textlength <= c.getVcEventName().length()) {
	                 if (c.getVcEventName().toLowerCase().contains(txt.toString().toLowerCase())) {
	                    tempArrayList.add(c);
	                 }
	              }
	           }
//	           
			mEventAdapter=new EventAdapter(mContext, tempArrayList);
			mListView.setAdapter(mEventAdapter);

			}

		}
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {

			
		}
		
		@Override
		public void afterTextChanged(Editable s) {
			
		}
	};


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
