package com.partyfinder;

import com.facebook.Session;
import com.facebook.widget.LoginButton;
import com.partyfinder.AppConstant.PartyFinderConstants;
import com.partyfinder.CustomView.CustomAsyncTask;
import com.partyfinder.CustomView.CustomDialogBox;
import com.partyfinder.app.utils.AppUtils;
import com.partyfinder.drawerfragment.FragmentFriends;
import com.partyfinder.drawerfragment.FragmentOrderHistory;
import com.partyfinder.drawerfragment.FragmentOrderHistoryUpdate;
import com.partyfinder.drawerfragment.FragmentRecommended;
import com.partyfinder.managers.SettingPrefrences;
import com.partyfinder.model.PushTypeGeneric;
import com.partyfinder.model.UserLoginItem;
import com.partyfinder.tabfragment.ActivityFeedFragment;
import com.partyfinder.tabfragment.UpcomingEventsFragment;
import com.partyfinder.tabfragment.WhatsHotFragment;
import com.partyfinder.widget.SlideHolder;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;

public class MainActivity extends FragmentActivity {

	//	public FragmentTabHost mTabHost;

	private ListView navList;
	private LinearLayout mDrawerLayout;
	private LinearLayout mLeftDrawerLayout;
	private Fragment navDrawerFrag;
	private SlideHolder mSlideHolder;
	private FragmentTransaction tx = null;
	private Resources res; 
	private ImageView imageViewProfile;
	public  static volatile MainActivity mMainActivity;

	private Context mContext;
	private UserLoginItem mUserLoginItem;
	private TextView tV_userName;
	private TextView tV_userFriends;
	private boolean isInternetPresent = false;
	private ProgressBar progressBarMyPro;
	private Fragment mFragment;
	private ImageView mainMenu,whatsHot,actvityFeeds,upcomingEvent;
	public  LinearLayout tabHostLayout;
	private TextView mainMenutxt,whatsHotTxt,actFeedTxt,upcomngTxt;
	private LinearLayout mainMenuLay,upcomingLay,whatsHotLay,activityFeedLay;
	private PushTypeGeneric pushItem;
	private CustomAsyncTask mAsyncTask;
	private LayoutInflater inflator;
	private TextView mTextView;
	private FragmentOrderHistoryUpdate mOrderHistoryUpdateFragment; 


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mContext=this;

		/*getWindow().setFlags(
				WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
				WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);*/


		inflator=LayoutInflater.from(mContext);
		pushItem=(PushTypeGeneric) getIntent().getSerializableExtra(PartyFinderConstants.GCM_PUSHEXTRA);

		mSlideHolder = (SlideHolder) findViewById(R.id.slideHolder);
		mSlideHolder.setEnabled(true);
		final String[] data ={getResources().getString(R.string.Home),getResources().getString(R.string.Recommend),getResources().getString(R.string.Events), getResources().getString(R.string.Venues), getResources().getString(R.string.Friends), getResources().getString(R.string.Favourites), getResources().getString(R.string.myOrders),getResources().getString(R.string.signOut)};
		final String[] fragments ={


				"com.partyfinder.drawerfragment.FragmentHome",
				"com.partyfinder.drawerfragment.FragmentRecommended",
				"com.partyfinder.drawerfragment.FragmentEvents",
				"com.partyfinder.drawerfragment.FragmentVenues",
				"com.partyfinder.drawerfragment.FragmentFriends",
				"com.partyfinder.drawerfragment.FragmentFavorites",
				"com.partyfinder.drawerfragment.FragmentOrderHistory"

		};


		/*View view=inflator.inflate(R.layout.slidemenutext, null);
		RelativeLayout.LayoutParams mapLayer = new RelativeLayout.LayoutParams(10000,10000);
		mTextView= (TextView)view.findViewById(android.R.id.text1);
		mTextView.setLayoutParams(mapLayer);
		 */


		ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, R.layout.slidemenutext, data);



		navList = (ListView) findViewById(com.partyfinder.R.id.drawer);
		mLeftDrawerLayout = (LinearLayout)findViewById(com.partyfinder.R.id.leftdrawer);
		mDrawerLayout = (LinearLayout)findViewById(R.id.drawerlayout);
		progressBarMyPro=(ProgressBar)findViewById(R.id.imageLoadMypro);

		mainMenuLay=(LinearLayout) findViewById(R.id.mainMenuLayout);
		upcomingLay=(LinearLayout) findViewById(R.id.upcomingEventLayout);
		activityFeedLay=(LinearLayout) findViewById(R.id.activityFeedLayout);
		whatsHotLay=(LinearLayout) findViewById(R.id.whatsHotLayout);

		whatsHotTxt=(TextView) findViewById(R.id.whatsHotTxt);
		mainMenutxt=(TextView) findViewById(R.id.mainMenuTxt);
		actFeedTxt=(TextView) findViewById(R.id.actFeedTxt);
		upcomngTxt=(TextView) findViewById(R.id.upcomingEventTxt);

		/* getData From SharedPrefernces*/

		mUserLoginItem = SettingPrefrences.getUserPrefrence(mContext);
		Log.i("","User name"+ mUserLoginItem.getVc_user_name());

		tV_userFriends=(TextView)findViewById(R.id.tV_userFriends);
		tV_userFriends.setText(mUserLoginItem.getFriend_count()+" Amigos");

		tV_userName=(TextView)findViewById(R.id.tv_username);
		tV_userName.setText(mUserLoginItem.getVc_user_name());



		imageViewProfile=(ImageView)findViewById(R.id.ImgView_profile);
		Log.i("","Image url :"+mUserLoginItem.getVc_image_url());

		Picasso.with(mContext).load(mUserLoginItem.getPf_user_image())
		.error(R.drawable.no_thumbnail).into(imageViewProfile,new Callback() {

			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				progressBarMyPro.setVisibility(View.GONE);	
			}

			@Override
			public void onError() {
				// TODO Auto-generated method stub
				progressBarMyPro.setVisibility(View.GONE);	
			}
		});




		imageViewProfile.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				//	Intent in= new Intent(MainActivity.this,)
				isInternetPresent = AppUtils.isConnectingToInternet(mContext);
				if( isInternetPresent){
					UserLoginItem mUserLoginItem=SettingPrefrences.getUserPrefrence(mContext);
					Intent in=new Intent(MainActivity.this,MyProfileActivity.class);


					in.putExtra(PartyFinderConstants.EXTRA_GET_FRIENDS_ITEM, mUserLoginItem);
					startActivity(in);
					Log.i("","Button Clicked");
				}else{
					//					Toast.makeText(getApplicationContext(),"Conecte seu dispositivo com conexão de Internet ativa", Toast.LENGTH_SHORT).show();  
					CustomDialogBox.showAlert(getResources().getString(R.string.infoDialo),getResources().getString(R.string.NoInternetMessage), mContext);		
				}
			}
		});
		navList.setAdapter(adapter);
		navList.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view, final int pos,long id){
				//				view.setLayerType(, paint)
				isInternetPresent = AppUtils.isConnectingToInternet(mContext);
				if( isInternetPresent){
					if(data[pos].equalsIgnoreCase(getResources().getString(R.string.signOut))){
						Log.d("", "sign out clicked");

						/*	if(mFragment!=null){
							tx = getSupportFragmentManager().beginTransaction();
							tx.detach(mFragment);
							tx.commitAllowingStateLoss();
						}*/
						//						tx.remove(mFragment);
						SettingPrefrences.deleteUserPrefrence(mContext);
						doFacebookLogout();
						/*if(mAsyncTask!=null){
							mAsyncTask.cancel(true);
							mAsyncTask=null;
						}*/
						Intent in= new Intent(mContext,HomeActivity.class);
						startActivity(in);

						Log.d("","remove setting preference") ;
						finish();

					}else if(data[pos].equalsIgnoreCase(getResources().getString(R.string.Home))){
						//						mTabHost.setCurrentTab(1);
						mSlideHolder.toggle();
						actvityFeeds.setBackground(getResources().getDrawable(R.drawable.activityfeedw));
						mainMenu.setBackground(getResources().getDrawable(R.drawable.mainmenuc));
						whatsHot.setBackground(getResources().getDrawable(R.drawable.whatshotc));
						upcomingEvent.setBackground(getResources().getDrawable(R.drawable.upcomingeventc));
						TextView[] unpressed={mainMenutxt,whatsHotTxt,upcomngTxt};
						setSelectedColor(actFeedTxt,unpressed);
						if(mFragment!=null){
							tx = getSupportFragmentManager().beginTransaction();
							tx.detach(mFragment);
							tx.commitAllowingStateLoss();
						}

						Log.i("","TabHostBar actvityFeeds is calling");

						mFragment=new ActivityFeedFragment();//.getInstance();
						tx = getSupportFragmentManager().beginTransaction();
						tx.replace(R.id.realtabcontent,mFragment);
						//						tx.addToBackStack(null);
						tx.commitAllowingStateLoss();


						/* }else if (data[pos].equalsIgnoreCase("Recommended")) {
			        		 mTabHost.setCurrentTab(1);
			        		 mSlideHolder.toggle();

						}else if (data[pos].equalsIgnoreCase("Events")) {
							 mTabHost.setCurrentTab(1);
			        		 mSlideHolder.toggle();

						}else if (data[pos].equalsIgnoreCase("Venues")) {
							mTabHost.setCurrentTab(1);
			        		 mSlideHolder.toggle();
						}else if (data[pos].equalsIgnoreCase("Friends")) {
							mTabHost.setCurrentTab(1);
			        		 mSlideHolder.toggle();
						}else if (data[pos].equalsIgnoreCase("Favorites")) {
							mTabHost.setCurrentTab(1);
			        		 mSlideHolder.toggle();
						}else if (data[pos].equalsIgnoreCase("Order History")) {
							mTabHost.setCurrentTab(1);
			        		 mSlideHolder.toggle();*/
					} else{
						Log.d("", "else block excute ");
						//						mTabHost.setCurrentTab(0);
						//						if(selectedIndex!=-1){
						clearBackStack();
						mainMenu.setBackground(getResources().getDrawable(R.drawable.mainmenuw));
						whatsHot.setBackground(getResources().getDrawable(R.drawable.whatshotc));
						upcomingEvent.setBackground(getResources().getDrawable(R.drawable.upcomingeventc));
						actvityFeeds.setBackground(getResources().getDrawable(R.drawable.activityfeedc));
						TextView[] unpressed={whatsHotTxt,upcomngTxt,actFeedTxt};
						setSelectedColor(mainMenutxt,unpressed);
						//						}
						if(navDrawerFrag!=null){
							tx = getSupportFragmentManager().beginTransaction();
							tx.detach(navDrawerFrag);
							navDrawerFrag=null;
							tx.commitAllowingStateLoss();
						}
						navDrawerFrag=Fragment.instantiate(MainActivity.this, fragments[pos]);
						tx = getSupportFragmentManager().beginTransaction();
						//						tx.addToBackStack(null);
						tx.replace(R.id.realtabcontent,navDrawerFrag);
						tx.commitAllowingStateLoss();
						mSlideHolder.toggle();	 
					}
				}else{
					CustomDialogBox.showAlert(getResources().getString(R.string.infoDialo),getResources().getString(R.string.NoInternetMessage), mContext);				
					//					Toast.makeText(getApplicationContext(),"Conecte seu dispositivo com conexão de Internet ativa", Toast.LENGTH_SHORT).show();  
				}

			}

		});

		tabHostLayout=(LinearLayout) findViewById(R.id.tabhostlayout111111);
		mainMenu=(ImageView) findViewById(R.id.mainMenu);
		whatsHot=(ImageView) findViewById(R.id.whatsHot);
		upcomingEvent=(ImageView) findViewById(R.id.upcomingEvent);
		actvityFeeds=(ImageView) findViewById(R.id.activityFeed);
		if(pushItem!=null){
			if(pushItem.getPushType().equalsIgnoreCase("1")){
				showFriendList();
			}else if(pushItem.getPushType().equalsIgnoreCase("2")){
				showFriendList();
			}else if(pushItem.getPushType().equalsIgnoreCase("3")){
				showRecomended();
			}else if(pushItem.getPushType().equalsIgnoreCase("4")){
				showOrderConfirm();

				/*whatsHot.setBackground(getResources().getDrawable(R.drawable.whathotw));
				upcomingEvent.setBackground(getResources().getDrawable(R.drawable.upcomingeventc));
				actvityFeeds.setBackground(getResources().getDrawable(R.drawable.activityfeedc));
				mainMenu.setBackground(getResources().getDrawable(R.drawable.mainmenuc));
				TextView[] unpressed={mainMenutxt,actFeedTxt,upcomngTxt};
				setSelectedColor(whatsHotTxt,unpressed);
				mFragment=new WhatsHotFragment();
				tx = getSupportFragmentManager().beginTransaction();
				tx.replace(R.id.realtabcontent,mFragment );
				tx.commitAllowingStateLoss();*/
			}
		}else{
			whatsHot.setBackground(getResources().getDrawable(R.drawable.whathotw));
			upcomingEvent.setBackground(getResources().getDrawable(R.drawable.upcomingeventc));
			actvityFeeds.setBackground(getResources().getDrawable(R.drawable.activityfeedc));
			mainMenu.setBackground(getResources().getDrawable(R.drawable.mainmenuc));
			TextView[] unpressed={mainMenutxt,actFeedTxt,upcomngTxt};
			setSelectedColor(whatsHotTxt,unpressed);
			mFragment=new WhatsHotFragment();//.getInstance();
			tx = getSupportFragmentManager().beginTransaction();
			tx.replace(R.id.realtabcontent,mFragment );
			//			tx.addToBackStack(null);
			tx.commitAllowingStateLoss();
		}




		res = getResources();


		mainMenuLay.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.i("","TabHostBar mainMenu is calling");
				mSlideHolder.setEnabled(true);
				//				mainMenu.setBackground(getResources().getDrawable(R.drawable.mainmenuw));
				//				whatsHot.setBackground(getResources().getDrawable(R.drawable.whatshotc));
				//				upcomingEvent.setBackground(getResources().getDrawable(R.drawable.upcomingeventc));
				//				actvityFeeds.setBackground(getResources().getDrawable(R.drawable.activityfeedc));
				//				if(selectedIndex==2){
				//					whatsHot.setBackground(getResources().getDrawable(R.drawable.whathotw));
				//					upcomingEvent.setBackground(getResources().getDrawable(R.drawable.upcomingeventc));
				//					actvityFeeds.setBackground(getResources().getDrawable(R.drawable.activityfeedc));
				//					mainMenu.setBackground(getResources().getDrawable(R.drawable.mainmenuc));
				//				}
				//				if(selectedIndex==3){
				//					upcomingEvent.setBackground(getResources().getDrawable(R.drawable.upcomingeventw));
				//					actvityFeeds.setBackground(getResources().getDrawable(R.drawable.activityfeedc));
				//					mainMenu.setBackground(getResources().getDrawable(R.drawable.mainmenuc));
				//					whatsHot.setBackground(getResources().getDrawable(R.drawable.whatshotc));
				//				}
				//				if(selectedIndex==4){
				//					upcomingEvent.setBackground(getResources().getDrawable(R.drawable.upcomingeventw));
				//					actvityFeeds.setBackground(getResources().getDrawable(R.drawable.activityfeedc));
				//					mainMenu.setBackground(getResources().getDrawable(R.drawable.mainmenuc));
				//					whatsHot.setBackground(getResources().getDrawable(R.drawable.whatshotc));
				//				}
				mSlideHolder.toggle();
				mSlideHolder.setEnabled(false);
			}
		});
		whatsHotLay.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				whatsHot.setBackground(getResources().getDrawable(R.drawable.whathotw));
				upcomingEvent.setBackground(getResources().getDrawable(R.drawable.upcomingeventc));
				actvityFeeds.setBackground(getResources().getDrawable(R.drawable.activityfeedc));
				mainMenu.setBackground(getResources().getDrawable(R.drawable.mainmenuc));
				TextView[] unpressed={mainMenutxt,actFeedTxt,upcomngTxt};
				setSelectedColor(whatsHotTxt,unpressed);
				if(navDrawerFrag!=null){
					tx = getSupportFragmentManager().beginTransaction();
					tx.detach(navDrawerFrag);
					tx.commitAllowingStateLoss();
				}
				if(mFragment!=null){
					tx = getSupportFragmentManager().beginTransaction();
					tx.detach(mFragment);
					tx.commitAllowingStateLoss();
				}
				Log.i("","TabHostBar whatshot is calling");
//				mFragment= WhatsHotFragment.getInstance();
								mFragment= new WhatsHotFragment();

				tx = getSupportFragmentManager().beginTransaction();
				tx.replace(R.id.realtabcontent,mFragment );
				tx.commitAllowingStateLoss();

			}
		});
		upcomingLay.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				upcomingEvent.setBackground(getResources().getDrawable(R.drawable.upcomingeventw));
				actvityFeeds.setBackground(getResources().getDrawable(R.drawable.activityfeedc));
				mainMenu.setBackground(getResources().getDrawable(R.drawable.mainmenuc));
				whatsHot.setBackground(getResources().getDrawable(R.drawable.whatshotc));
				TextView[] unpressed={mainMenutxt,actFeedTxt,whatsHotTxt};
				setSelectedColor(upcomngTxt,unpressed);
				if(mFragment!=null){
					tx = getSupportFragmentManager().beginTransaction();
					tx.detach(mFragment);
					tx.commitAllowingStateLoss();
				}
				if(navDrawerFrag!=null){

					tx = getSupportFragmentManager().beginTransaction();
					tx.detach(navDrawerFrag);
					tx.commitAllowingStateLoss();
				}
				Log.i("","TabHostBar upcomingEvent is calling");

//				mFragment= UpcomingEventsFragment.getInstance();
								mFragment= new UpcomingEventsFragment();


				tx = getSupportFragmentManager().beginTransaction();
				tx.replace(R.id.realtabcontent,mFragment);
				tx.commitAllowingStateLoss();
				//				mSlideHolder.toggle();	
			}
		});
		activityFeedLay.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				actvityFeeds.setBackground(getResources().getDrawable(R.drawable.activityfeedw));
				mainMenu.setBackground(getResources().getDrawable(R.drawable.mainmenuc));
				whatsHot.setBackground(getResources().getDrawable(R.drawable.whatshotc));
				upcomingEvent.setBackground(getResources().getDrawable(R.drawable.upcomingeventc));
				TextView[] unpressed={mainMenutxt,whatsHotTxt,upcomngTxt};
				setSelectedColor(actFeedTxt,unpressed);
				if(navDrawerFrag!=null){

					tx = getSupportFragmentManager().beginTransaction();
					tx.detach(navDrawerFrag);
					tx.commitAllowingStateLoss();
				}
				if(mFragment!=null){
					tx = getSupportFragmentManager().beginTransaction();
					tx.detach(mFragment);
					tx.commitAllowingStateLoss();
				}

				Log.i("","TabHostBar actvityFeeds is calling");

//				mFragment= ActivityFeedFragment.getInstance();
								mFragment=  new ActivityFeedFragment();

				tx = getSupportFragmentManager().beginTransaction();
				tx.replace(R.id.realtabcontent,mFragment);
				tx.commitAllowingStateLoss();

				//				mSlideHolder.toggle();	
			}
		});


		/*mTabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);
		mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

		mTabHost.addTab(mTabHost.newTabSpec("mainMenu").setIndicator(getTabIndicatorMain(mTabHost.getContext(),
		R.string.main_menu, R.drawable.tab_main_menu)),MainMenuFragment.class,null);

//		LinearLayout lay=(LinearLayout)findViewById(R.id.tablayoutId);
//		ImageView imgView= getTabIndicatorMain(mTabHost.getContext(),R.string.main_menu, R.drawable.tab_main_menu);
//		lay.addView(imgView);
		mTabHost.addTab(mTabHost.newTabSpec("activityFeed").setIndicator(getTabIndicatorActivityFeed(mTabHost.getContext(),
				R.string.activity_feed_menu, R.drawable.tab_activity_feed)),
				ActivityFeedFragment.class, null);





		mTabHost.addTab(mTabHost.newTabSpec("whatsHot").setIndicator(getTabIndicatorWhatsHot(mTabHost.getContext(),
				R.string.whats_hot_menu, R.drawable.tab_whatshot)), 
				WhatsHotFragment.class, null);

		mTabHost.addTab(mTabHost.newTabSpec("upcomingEvent").setIndicator(getTabIndicatorUpcomingEvent(mTabHost.getContext(), 
				R.string.upcoming_menu, R.drawable.tab_upcoming_event)), 
				UpcomingEventsFragment.class, null);




		mTabHost.setCurrentTab(2);

//	ImageView imgView=	(ImageView) findViewById(R.id.tabImageView);
//	imgView.setOnClickListener(new OnClickListener() {
//		
//		@Override
//		public void onClick(View v) {
//			// TODO Auto-generated method stub
//			mSlideHolder.toggle();
//			View view=mTabHost.getTabWidget().getChildAt(tabIndexforMainmenu);
//			view.clearFocus();
//			Log.i("","Selelected tab : "+view);
//			view.refreshDrawableState();
//			
////			mTabHost.removeAllViews();
////			mTabHost.clearAllTabs();
//	
//		}
//	});
		mTabHost.setOnTabChangedListener(new OnTabChangeListener() {

			@Override
			public void onTabChanged(String tabId) {
				// TODO Auto-generated method stub

				if(tabId.equalsIgnoreCase("mainMenu")){
					//mTabHost.setCurrentTab(mainMenufragment);
//					Log.d("", "Tab Id :"+mainMenufragment);
//					mTabHost.setCurrentTab(tabIndexforMainmenu);
//					mTabHost.getChildAt(tabIndexforMainmenu);

//					FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//					transaction.replace(android.R.id.tabcontent,mFragment );
//					transaction.addToBackStack(null);
//					transaction.commit();
					if(navDrawerFrag!=null){

						tx = getSupportFragmentManager().beginTransaction();
						tx.detach(navDrawerFrag);
						tx.commitAllowingStateLoss();
					}
//					mTabHost.setCurrentTab(tabIndexforMainmenu);
							if(tabIndexforMainmenu==1){

						mTabHost.addTab(mTabHost.newTabSpec("activityFeed").setIndicator(getTabIndicatorActivityFeed(mTabHost.getContext(),
								R.string.activity_feed_menu, R.drawable.tab_activity_feed)),
								ActivityFeedFragment.class, null);
					}else if(tabIndexforMainmenu==2){

						mTabHost.addTab(mTabHost.newTabSpec("whatsHot").setIndicator(getTabIndicatorWhatsHot(mTabHost.getContext(),
								R.string.whats_hot_menu, R.drawable.tab_whatshot)), 
								WhatsHotFragment.class, null);
					}else if(tabIndexforMainmenu==3){
						mTabHost.addTab(mTabHost.newTabSpec("upcomingEvent").setIndicator(getTabIndicatorUpcomingEvent(mTabHost.getContext(), 
								R.string.upcoming_menu, R.drawable.tab_upcoming_event)), 
								UpcomingEventsFragment.class, null);
					}

//					mTabHost.setVisibility(tabIndexforMainmenu);
//					tabView=(View) mTabHost.getTag(tabIndexforMainmenu);
//					Log.i("","Tab View "+tabView);
//					mTabHost.addView(tabView);
					mSlideHolder.toggle();			
				}
				if(tabId.equalsIgnoreCase("activityFeed")){
					//mainMenufragment=mTabHost.getCurrentTab();
					Log.d("", "Tab Id :"+mainMenufragment);
					tabIndexforMainmenu=mTabHost.getCurrentTab();
					Log.i("","Selected tab Index : "+tabId+"  : "+tabIndexforMainmenu);
//					mFragment=ActivityFeedFragment.newInstance(mContext);
//					tabView=mTabHost.getChildAt(tabIndexforMainmenu);
					if(navDrawerFrag!=null){

						tx = getSupportFragmentManager().beginTransaction();
						tx.detach(navDrawerFrag);
						tx.commitAllowingStateLoss();
					}
				}

				if(tabId.equalsIgnoreCase("upcomingEvent")){
					mainMenufragment=mTabHost.getCurrentTab();
					Log.d("", "Tab Id :"+mainMenufragment);
					tabIndexforMainmenu=mTabHost.getCurrentTab();

					Log.i("","Selected tab Index : "+tabId+"  : "+tabIndexforMainmenu);
//					mFragment=UpcomingEventsFragment.newInstance(mContext);
					if(navDrawerFrag!=null){

						tx = getSupportFragmentManager().beginTransaction();
						tx.detach(navDrawerFrag);
						tx.commitAllowingStateLoss();
					}
				}

				if(tabId.equalsIgnoreCase("whatsHot")){
					mainMenufragment=mTabHost.getCurrentTab();
					tabIndexforMainmenu=mTabHost.getCurrentTab();
					Log.i("","Selected tab Index : "+tabId+"  : "+tabIndexforMainmenu);
					tx = getSupportFragmentManager().beginTransaction();
					Log.d("", "Tab Id :"+mainMenufragment);
//					mFragment=WhatsHotFragment.newInstance(mContext);
					if(navDrawerFrag!=null){

						tx.detach(navDrawerFrag);
						tx.commitAllowingStateLoss();
					}
				}

			}
		});*/

	}


	public static MainActivity GetInstanceTab(){

		if(mMainActivity==null){

			mMainActivity = new MainActivity();	
		}
		return mMainActivity;

	}

	private void clearBackStack(){
		for(int i=0;i<getSupportFragmentManager().getBackStackEntryCount();i++){
			getSupportFragmentManager().popBackStack();
			Log.i("","Clear backstack : "+i);
		}
	}
	private void showRecomended(){
		mainMenu.setBackground(getResources().getDrawable(R.drawable.mainmenuw));
		whatsHot.setBackground(getResources().getDrawable(R.drawable.whatshotc));
		upcomingEvent.setBackground(getResources().getDrawable(R.drawable.upcomingeventc));
		actvityFeeds.setBackground(getResources().getDrawable(R.drawable.activityfeedc));
		TextView[] unpressed={whatsHotTxt,upcomngTxt,actFeedTxt};
		setSelectedColor(mainMenutxt,unpressed);
		//	}
		if(mFragment!=null){
			tx = getSupportFragmentManager().beginTransaction();
			tx.detach(navDrawerFrag);
			mFragment=null;
			tx.commitAllowingStateLoss();
		}
		if(navDrawerFrag!=null){
			tx = getSupportFragmentManager().beginTransaction();
			tx.detach(navDrawerFrag);
			navDrawerFrag=null;
			tx.commitAllowingStateLoss();
		}
		navDrawerFrag=new FragmentRecommended();

		tx = getSupportFragmentManager().beginTransaction();
		tx.replace(R.id.realtabcontent,navDrawerFrag);
		//		tx.addToBackStack(null);
		tx.commitAllowingStateLoss();
	}

	private void showOrderConfirm(){
		mainMenu.setBackground(getResources().getDrawable(R.drawable.mainmenuw));
		whatsHot.setBackground(getResources().getDrawable(R.drawable.whatshotc));
		upcomingEvent.setBackground(getResources().getDrawable(R.drawable.upcomingeventc));
		actvityFeeds.setBackground(getResources().getDrawable(R.drawable.activityfeedc));
		TextView[] unpressed={whatsHotTxt,upcomngTxt,actFeedTxt};
		setSelectedColor(mainMenutxt,unpressed);
		//	}
		if(mFragment!=null){
			tx = getSupportFragmentManager().beginTransaction();
			tx.detach(navDrawerFrag);
			mFragment=null;
			tx.commitAllowingStateLoss();
		}
		if(navDrawerFrag!=null){
			tx = getSupportFragmentManager().beginTransaction();
			tx.detach(navDrawerFrag);
			navDrawerFrag=null;
			tx.commitAllowingStateLoss();
		}
		navDrawerFrag=new FragmentOrderHistory();

		tx = getSupportFragmentManager().beginTransaction();
		tx.replace(R.id.realtabcontent,navDrawerFrag);
		//		tx.addToBackStack(null);
		tx.commitAllowingStateLoss();
	}
	private void showFriendList(){
		mainMenu.setBackground(getResources().getDrawable(R.drawable.mainmenuw));
		whatsHot.setBackground(getResources().getDrawable(R.drawable.whatshotc));
		upcomingEvent.setBackground(getResources().getDrawable(R.drawable.upcomingeventc));
		actvityFeeds.setBackground(getResources().getDrawable(R.drawable.activityfeedc));
		TextView[] unpressed={whatsHotTxt,upcomngTxt,actFeedTxt};
		setSelectedColor(mainMenutxt,unpressed);
		//	}
		if(mFragment!=null){
			tx = getSupportFragmentManager().beginTransaction();
			tx.detach(navDrawerFrag);
			mFragment=null;
			tx.commitAllowingStateLoss();
		}
		if(navDrawerFrag!=null){
			tx = getSupportFragmentManager().beginTransaction();
			tx.detach(navDrawerFrag);
			navDrawerFrag=null;
			tx.commitAllowingStateLoss();
		}
		navDrawerFrag=new FragmentFriends();

		tx = getSupportFragmentManager().beginTransaction();
		tx.replace(R.id.realtabcontent,navDrawerFrag);
		//		tx.addToBackStack(null);
		tx.commitAllowingStateLoss();
	}
	private void setSelectedColor(TextView pressed,TextView[] unPressed){
		pressed.setTextColor(getResources().getColor(R.color.text_tab_selected));
		for(int i=0;i<unPressed.length;i++){
			unPressed[i].setTextColor(getResources().getColor(R.color.text_tab_unselected));
		}

	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub

		tabHostLayout.setVisibility(View.VISIBLE);

		super.onBackPressed();
	}

	private void doFacebookLogout(){
		//				Session session=LoginButton.sessionTracker.getOpenSession();
		//		session.closeAndClearTokenInformation();

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mUserLoginItem = SettingPrefrences.getUserPrefrence(mContext);
		tV_userName.setText(mUserLoginItem.getVc_user_name());
		tV_userFriends.setText(mUserLoginItem.getFriend_count()+" Amigos");
	}


	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		finish();
		super.onDestroy();
	}
/*@Override
protected void onSaveInstanceState(Bundle outState) {
	// TODO Auto-generated method stub
	super.onSaveInstanceState(outState);
}*/
}
