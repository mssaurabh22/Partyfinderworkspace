package com.partyfinder.tabfragment;



import java.util.ArrayList;


import com.partyfinder.EventDetailsActivity;
import com.partyfinder.R;

import com.partyfinder.AppConstant.PartyFinderConstants;
import com.partyfinder.CustomView.CustomAsyncTask;
import com.partyfinder.CustomView.CustomDialogBox;
import com.partyfinder.CustomView.TaskListener;
import com.partyfinder.app.utils.AppUtils;
import com.partyfinder.carousal.Carousel;
import com.partyfinder.carousal.CarouselAdapter;
import com.partyfinder.carousal.CarouselAdapter.OnItemClickListener;
import com.partyfinder.carousal.CarouselAdapter.OnItemSelectedListener;
import com.partyfinder.drawerfragment.FragmentOrderHistory;
import com.partyfinder.drawerfragment.FragmentOrderHistoryUpdate;
import com.partyfinder.drawerfragment.GoogleMapFragment;
import com.partyfinder.managers.ContentManager;
import com.partyfinder.model.EventItem;
import com.partyfinder.widget.SlideHolder;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Matrix.ScaleToFit;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnGenericMotionListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;


public class WhatsHotFragment extends Fragment {

	private ImageView mimageGlobe;
	private FragmentManager mFragmentManager;
	private FragmentTransaction mFragmentTransection;

	private TaskListener mWhathotListener;
	public static ArrayList<EventItem> mWhathotList;

	private Context mContext;
	//	private static MainActivity mMainActivity;
	private RelativeLayout carouselLayout,mim_globeLayout;
	private CustomAsyncTask mAsyncTask;
	private TextView mEventName,clubName,eventLocation,eventTime,eventDate,eventMonth;
	private final String TAG="Whats hot fragment class ";
	private LinearLayout parentLayout;
	private SlideHolder mSlideHolder;
	private int selectedPosition;
	private Fragment mFragment;
	private boolean isInternetConnected= false;
	public int mScreenWidth;
	public int mScreenHeight;
	
	private ProgressBar mprogressBar;
	public static WhatsHotFragment fragment;
	
	public static WhatsHotFragment getInstance(){
		 if(fragment==null){
		 fragment=new WhatsHotFragment();
			  }else{
		 return fragment;
	 }
			 return fragment;
			   }



	public static Fragment newInstance(Context context) {
		WhatsHotFragment f = new WhatsHotFragment();
		return f;
	}

	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		mContext=getActivity().getApplicationContext();
//		getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON, 
//		          WindowManager.LayoutParams.flag);
		        
		getScreenResolution(mContext);
		Log.i("", "screen resolution  whatshotfragment : " +"width : "+mScreenWidth +"height : "+mScreenHeight);
		//		SlideHolder.mEnabled=false;
		mFragmentManager=getActivity().getSupportFragmentManager();
		mSlideHolder=(SlideHolder) getActivity().findViewById(R.id.slideHolder);
		
		//		View v = inflater.inflate(R.layout.tab_whats_hot_layout, container, false);
		View v = inflater.inflate(R.layout.tab_whats_hot_layout, container, false);
//		v.setLayerType(View.LAYER_TYPE_NONE, null);
	
		
		mim_globeLayout=(RelativeLayout) v.findViewById(R.id.im_globeLayout);
		carouselLayout=(RelativeLayout) v.findViewById(R.id.carouselLayout);
		mprogressBar = (ProgressBar) v.findViewById(R.id.whatshotProgressbar);
		mimageGlobe = (ImageView) v.findViewById(R.id.im_globe);
		mEventName=(TextView) v.findViewById(R.id.whatsHotEventName);
		clubName=(TextView) v.findViewById(R.id.whatsHotClubName);
		eventLocation=(TextView) v.findViewById(R.id.whatsHoLocation);
		eventTime=(TextView) v.findViewById(R.id.whatsHotTime);
		parentLayout=(LinearLayout) v.findViewById(R.id.greenc);
		eventDate=(TextView) v.findViewById(R.id.whatsHoteventDate);
		eventMonth=(TextView) v.findViewById(R.id.whatsHoteventmonth);

		carouselLayout.setOnTouchListener(new OnTouchListener() {	
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				Log.i("",TAG+"carouselLayout onTouch is calling : ");
				//	    SlideHolder.mEnabled=false;
				mSlideHolder.setEnabled(false);

				return false;
			}
		});
		parentLayout.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				Log.i("",TAG+"onTouch is calling : ");
				//	    SlideHolder.mEnabled=true;
				mSlideHolder.setEnabled(true);
				Log.i("",TAG+"slider enable : "+mSlideHolder.isEnabled());
				return false;
			}
		});
		

		/*carouselLayout.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				Log.i("",TAG+"carouselLayout onTouch is calling : ");
//				SlideHolder.mEnabled=false;
				mSlideHolder.setEnabled(false);
				return false;
			}
		});
		parentLayout.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				Log.i("",TAG+"onTouch is calling : ");
				mSlideHolder.setEnabled(true);
				return false;
			}
		});
		 */
		mim_globeLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					
				
				mFragmentManager=getFragmentManager();
				mFragmentTransection=mFragmentManager.beginTransaction();

				if(mFragment!=null){
					mFragmentTransection.detach(mFragment);
					mFragment=null;
				}


				mFragment=new FragmentOrderHistoryUpdate();
				mFragment.setInitialSavedState(null);
				mFragmentTransection.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,R.anim.enter_from_left,R.anim.exit_to_right);
				mFragmentTransection.replace(R.id.frameContainer, mFragment);
				mFragmentTransection.addToBackStack(null);
				mFragmentTransection.commitAllowingStateLoss();

				} catch (Exception e) {
					// TODO: handle exception
				}



				/*		
				 * 		   mFragmentManager=getFragmentManager();
			        mFragmentTransection=mFragmentManager.beginTransaction();
			        if(mFragment!=null){
			         mFragmentTransection.detach(mFragment);
			         mFragment=null;
			        }

			        mFragment=new GoogleMapFragment(mWhathotList);

			     //   mFragment=new com.partyfinder.drawerfragment.GoogleMapV2(mWhathotList);


			        mFragmentTransection.replace(R.id.frameContainer, mFragment);

//			        mFragmentTransection.attach(mFragment);

			        mFragmentTransection.addToBackStack(null);
			   //     mFragmentTransection.setTransitionStyle(FragmentTransaction.TRANSIT_ENTER_MASK);
			        mFragmentTransection.commitAllowingStateLoss();
				 */

				/*mFragmentManager=getFragmentManager();
				    mFragmentTransection=mFragmentManager.beginTransaction();
				    if(mFragment!=null){
				     mFragmentTransection.detach(mFragment);
				     mFragment=null;
				    }
				    mFragment=new GoogleMapFragment(mWhathotList);
				    mFragmentTransection.replace(R.id.frameContainer, mFragment);
				    mFragmentTransection.addToBackStack(null);
				    mFragmentTransection.commitAllowingStateLoss();				
				 */




				/*	mFragmentManager=getFragmentManager();
				mFragmentTransection=mFragmentManager.beginTransaction();

				mFragment=new googlemap(mContext, mActivity);
				mFragmentTransection.add(R.id.frameContainer, mFragment);
				mFragmentTransection.addToBackStack(null);
				mFragmentTransection.commit();*/
				//				FragmentManager mfragFragmentManager=getActivity().getSupportFragmentManager();
				//				mFragmentTransection=mFragmentManager.beginTransaction();
				//			if(mFragment!=null){
				//				mFragmentTransaction.detach(mFragment);
				//				mFragment=null;
				//			}
				////			FragmentTransaction mFragmentTransaction=mfragFragmentManager.beginTransaction();
				//			mFragment=new PartyFindermapFragment(mWhathotList);
				//			mFragmentTransection.replace(R.id.frameContainer, mFragment);
				//			mFragmentTransection.addToBackStack(null);
				//			mFragmentTransection.commit();
				////				Intent	theIntent = new Intent(mContext,GoogleMapV2.class);
				//				theIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
				//
				//				theIntent.putExtra(PartyFinderConstants.EXTRA_EVENTITEM, mWhathotList.get(selectedPosition));
				//				startActivity(theIntent);
				//				overridePendingTransition(R.anim.fade_in, R.anim.fade_out); 
			}
		});


		isInternetConnected=AppUtils.isConnectingToInternet(getActivity());

		if (isInternetConnected) {

			loadWhatsHotTask();

		} else {


			CustomDialogBox.showAlert(getResources().getString(R.string.infoDialo),getResources().getString(R.string.NoInternetMessage), getActivity());

			//			Toast.makeText(getActivity(),"Conecte seu dispositivo com conexão de Internet ativa", Toast.LENGTH_SHORT).show();  

		}







		return v;
	}
	
	


	/*@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		super.onDetach();
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}*/
	private void loadWhatsHotTask(){

		mWhathotListener=new TaskListener() {

			public void updateUI() {
				// TODO Auto-generated method stub

				if(mWhathotList!=null){

					if(mWhathotList.size()>0){

						Log.i("","WhatsHot List Size : "+mWhathotList.size());	

						//						Carousel carousel = (Carousel)MainActivity.this.findViewById(R.id.carousel);

						RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

						// line no. 260  whatshotfragment 


						try
						{
							if(mScreenWidth>500 && mScreenWidth<560){
								if(mScreenHeight>940 && mScreenHeight<980){

									params.setMargins(0,-250,0,0);
									Log.i("", "540X960  ScreenHeight :"  +mScreenHeight +" ScreenWidth :"+mScreenWidth);

								}
							}else{

								params.setMargins(0,getResources().getDimensionPixelSize(R.dimen.xxxxxxxxxxxxxxxx),0,0);

							}

						}
						catch(Exception ex)
						{
							ex.printStackTrace();
						}






						final Carousel carousel = new Carousel(mContext);

						try{

							if(mScreenWidth>500 && mScreenWidth<560){
								if(mScreenHeight>940 && mScreenHeight<980){

									carousel.setPadding(0, 30, 0,30);
									Log.i("", "540X960  ScreenHeight :"  +mScreenHeight +" ScreenWidth :"+mScreenWidth);

								}
							}else{

								carousel.setPadding(0, getResources().getDimensionPixelSize(R.dimen.xxxxxxxxxxxxxxxxpadding), 0,getResources().getDimensionPixelSize(R.dimen.xxxxxxxxxxxxxxxxpadding));

							}


							carousel.setLayoutParams(params);

						}catch(Exception ex){
							ex.printStackTrace();
						}



						/*	try{
					params.setMargins(0,getResources().getDimensionPixelSize(R.dimen.xxxxxxxxxxxxxxxx),0,0);
					   }catch(Exception ex){
							ex.printStackTrace();
						}






				     final Carousel carousel = new Carousel(mContext);

				     try{
				     carousel.setPadding(0, getResources().getDimensionPixelSize(R.dimen.xxxxxxxxxxxxxxxxpadding), 0,getResources().getDimensionPixelSize(R.dimen.xxxxxxxxxxxxxxxxpadding));
				     carousel.setLayoutParams(params);

				     }catch(Exception ex){
							ex.printStackTrace();
						}*/


						carousel.setGravity(Gravity.CENTER);

						carouselLayout.addView(carousel);
						//						carousel.setmWhathotListener(WhatsHotFragment.this);

						carousel.setOnItemClickListener(new OnItemClickListener() {

							@Override
							public void onItemClick(CarouselAdapter<?> parent,
									View view, int position, long id) {
								// TODO Auto-generated method stub
								//							((CarouselItem)parent.getChildAt(position)).get
								Log.i("",TAG+"Selected Item : "+selectedPosition+mWhathotList.get(selectedPosition));
								Intent theIntent = new Intent(mContext,EventDetailsActivity.class);
								theIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
								theIntent.putExtra(PartyFinderConstants.EXTRA_EVENTITEM, mWhathotList.get(selectedPosition));
								mContext.startActivity(theIntent);

							}
						});

						/*  carousel.setOnItemClickListener(new OnItemClickListener(){

						   public void onItemClick(CarouselAdapter<?> parent, View view,
						     int position, long id) { 

						    Toast.makeText(mContext, 
						      String.format("%s has been clicked", 
						      ((CarouselItem)parent.getChildAt(position)).getIndex()), 
						      Toast.LENGTH_SHORT).show(); 
						    Log.i("",TAG+"Selected Item : "+selectedPosition+mWhathotList.get(((CarouselItem)parent.getChildAt(position)).getIndex()));
							Intent theIntent=new Intent(mContext,EventDetailsActivity.class);
							theIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
							theIntent.putExtra(PartyFinderConstants.EXTRA_EVENTITEM, mWhathotList.get(selectedPosition));
							mContext.startActivity(theIntent);
						    carousel.setSelection(((CarouselItem)parent.getChildAt(position)).getIndex(),false);

//						    Log.i("","child position"+((CarouselItem)parent.getChildAt(position)).getId());
						   }

						        });*/
						carousel.setOnTouchListener(new OnTouchListener() {

							@Override
							public boolean onTouch(View v, MotionEvent event) {
								// TODO Auto-generated method stub
								Log.i("",TAG+"carousel onTouch is calling : ");
								// SlideHolder.mEnabled=false;
								mSlideHolder.setEnabled(false);
								return false;
							}
						});
						carousel.setOnItemSelectedListener(new OnItemSelectedListener(){

							@Override
							public void onItemSelected(CarouselAdapter<?> parent,
									View view, int position, long id) {
								// TODO Auto-generated method stub

								Log.i("",TAG+"Selected position : "+ position);
								selectedPosition=position;


								showSelecetdeDesc(mWhathotList.get(position));

							}

							@Override
							public void onNothingSelected(CarouselAdapter<?> parent) {
								// TODO Auto-generated method stub
								//	SlideHolder.mEnabled=true;
							}

						}
								);
						carousel.setOnGenericMotionListener(new OnGenericMotionListener() {

							@Override
							public boolean onGenericMotion(View v, MotionEvent event) {
								// TODO Auto-generated method stub

								Log.i("", "xxxxxxxxxxxxxxxxxxxxxxx123"+v+" "+event);
								return false;
							}
						});


					}

				}
			}

			public void execute() {
				// TODO Auto-generated method stub
				//					String url="http://services.pfdev.com.br/Service1.svc/GETHOTEVENTS";
				mWhathotList=ContentManager.getInstance().parseWhatshot();
				
			}
		};
		
		if(mAsyncTask!=null){
			mAsyncTask.cancel(true);
			mAsyncTask=null;
		}
		mAsyncTask=new CustomAsyncTask(mWhathotListener,mprogressBar,mContext);
		mAsyncTask.execute();
	}


	private void showSelecetdeDesc(EventItem eventItem){

		//		String Date = eventItem.getDtEventDate();

		mEventName.setText(eventItem.getVcEventName());
		eventLocation.setText(eventItem.getVcEventAdd());
		eventTime.setText(eventItem.getDtEventStartTime());
		clubName.setText("na "+eventItem.getVcCompanyName());
		String arr[]=AppUtils.changeDateFormatArray(eventItem.getDtEventDate());
		eventMonth.setText(arr[0]);


		eventDate.setText(modifyDate(arr[1])); 
	}



	/*	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		Log.i("",TAG+"onTouch is calling : ");
		return false;
	}
	 */

	/*	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		Log.i("",TAG+"onDown is calling : ");
		return false;
	}


	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub
		Log.i("",TAG+"onShowPress is calling : ");
	}


	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		Log.i("",TAG+"onSingleTapUp is calling : ");
		return false;
	}


	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		Log.i("",TAG+"Onscroll is calling : "+distanceX+" DistanceY : "+distanceY);
		return false;
	}


	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub
		Log.i("",TAG+"onLongPress is calling : ");

	}


	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// TODO Auto-generated method stub
		return false;
	}*/

	//	@Override
	//	public void scrollWhatshot(Object obj) {
	//		// TODO Auto-generated method stub
	//		WhatsHotItem item;
	//		if(obj instanceof WhatsHotItem){
	//			item=(WhatsHotItem) obj;
	//			mEventName.setText(item.getEventName());
	//			
	//		}
	//	}






	public static String modifyDate(String date){
		String tempString;
		if(date.length()==1){
			tempString="0"+date;
		}else{
			tempString=date;
		}
		return tempString;
	}


	@Override
	public void onDetach() {
		// TODO Auto-generated method stub

		Log.i("", "xx onDetach Calling  :");
		super.onDetach();
	}


	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		if(mAsyncTask!=null){
			mAsyncTask.cancel(true);
			mAsyncTask=null;
		}
		Log.i("", "xx onPause Calling  :" );
		super.onPause();
	}


	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		if(mAsyncTask!=null){
			mAsyncTask.cancel(true);
			mAsyncTask=null;
		}
		Log.i("", "xx onStopCalling  :");

		super.onStop();
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

	public void getScreenResolution(Context mCtx){

		WindowManager wm = (WindowManager) mCtx.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();

		//		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		mScreenWidth = size.x;
		mScreenHeight = size.y;

		Log.i("", "Device   Screen Height carousel : "+mScreenHeight);
		Log.i("", "Device   Screen Height carousel : "+mScreenWidth );

	}

}
