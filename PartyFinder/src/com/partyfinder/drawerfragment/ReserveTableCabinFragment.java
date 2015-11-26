package com.partyfinder.drawerfragment;

import java.util.ArrayList;

import com.google.android.gms.internal.mp;
import com.partyfinder.EventDetailsActivity;
import com.partyfinder.R;
import com.partyfinder.ReserveTableActivity;
import com.partyfinder.CustomView.CustomAsyncTask;
import com.partyfinder.CustomView.CustomDialogBox;
import com.partyfinder.CustomView.TaskListener;
import com.partyfinder.CustomView.ZoomView;
import com.partyfinder.floorPlan.ImageAdapter;
import com.partyfinder.floorPlan.MyFragment;
import com.partyfinder.floorPlan.MyLinearLayout;
import com.partyfinder.floorPlan.MyPagerAdapter;
import com.partyfinder.managers.ContentManager;
import com.partyfinder.model.EventItem;
import com.partyfinder.model.ReserveTableItem;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources.Theme;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.Gallery;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class ReserveTableCabinFragment extends Fragment {
	private EventItem mEventItem;
	private FragmentManager mFragmentManager;
	private FragmentTransaction mFragmentTransaction;
	private Context mContext;
	private HorizontalScrollView mHorizontalScrollView;
	private RelativeLayout tableImgLayout,mDummyLayer;
	private ZoomView zoomView;
	ImageAdapter mImageAdapter;
	private ImageView background;
	private RelativeLayout imgView,mmapLayer;
	private RelativeLayout adaptrHeader,adaptrSubHeader,mgalleryAllContent;
	private TaskListener mTaskListener;
	private CustomAsyncTask mAsyncTask;
	public  static int PAGES=1;
	// You can choose a bigger number for LOOPS, but you know, nobody will fling
	// more than 1000 times just in order to test your "infinite" ViewPager :D 
	public final static int LOOPS = 10; 
	public static int FIRST_PAGE = PAGES * LOOPS / 2;
	public final static float BIG_SCALE = 0.9f;
	public final static float SMALL_SCALE = 0.9f;
	public final static float DIFF_SCALE = BIG_SCALE - SMALL_SCALE;
	public MyPagerAdapter adapter;
	public ViewPager pager;
	private ProgressBar mreserveTableProgressBar;
	private int oldPosition=-1,newPos;
	private boolean isFirst=false;
	private ImageButton backButton,mtable_forward_btn;
	private Gallery gallery;
	private ScrollView mmapScrollView;
	//	private FragmentManager mFragmentManager;
	//	private FragmentTransaction mFragmentTransect;
	private Fragment mFragment;
	private View view;
	private ProgressDialog mProgressDialog;

	private ArrayList<ReserveTableItem> reserveTableList;
	private ArrayList<ReserveTableItem> TableCabinTicketList;
	private ReserveTableItem mReservTabItem;
	private boolean AbleToPurchase = false;

	public ReserveTableCabinFragment(){

	}
	/*	public static ReserveTableCabinFragment getInstance() {
		ReserveTableCabinFragment f = new ReserveTableCabinFragment();

        return f;
    }*/
	public static Fragment newInstance(Context context) {
		ReserveTableCabinFragment f = new ReserveTableCabinFragment();

		return f;
	}


	public ReserveTableCabinFragment(EventItem eventItem){
		mEventItem=eventItem;
	}



	@Override
	public void onCreate(Bundle savedInstanceState) {

		// TODO Auto-generated method stub
		mFragmentManager=getActivity().getSupportFragmentManager();
		mContext=getActivity().getApplicationContext();

		super.onCreate(savedInstanceState);
	}



	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		view=inflater.inflate(R.layout.purchase_ticket_layout, null);

		RelativeLayout mTableLAyer = (RelativeLayout) view.findViewById(R.id.tableLayer);
		mProgressDialog= new ProgressDialog(getActivity());

		backButton=(ImageButton) view.findViewById(R.id.table_back_btn);
		mreserveTableProgressBar=(ProgressBar) view.findViewById(R.id.reserveTableProgressBar);
		mtable_forward_btn=(ImageButton) view.findViewById(R.id.table_forward_btn);
		backButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				mProgressDialog.dismiss();
				if(mAsyncTask!=null){
					mAsyncTask.cancel(true);
					mAsyncTask=null;
				}
				mFragmentManager.popBackStack();
			}
		});



		loadReserveTkt();

		mtable_forward_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//				Log.i("", "on Click new position  :" +newPos);
				if(AbleToPurchase){
				Log.i("", "on Click old position  :" +oldPosition);
				if(oldPosition!=-1){



					TableCabinTicketList = new ArrayList<ReserveTableItem>();
					mReservTabItem=reserveTableList.get(newPos);
					TableCabinTicketList.add(0,mReservTabItem);



					mFragmentTransaction=mFragmentManager.beginTransaction();
					if(mFragment!=null){
						mFragmentTransaction.detach(mFragment);
						mFragment=null;
					}
					mFragment=new PurchaseTableDeatailsFragment(mEventItem,TableCabinTicketList);
					mFragmentTransaction.add(R.id.fragmentContainer, mFragment);
					mFragmentTransaction.addToBackStack(null);
					mFragmentTransaction.commitAllowingStateLoss();
				
				}else{
					CustomDialogBox.showAlert(getResources().getString(R.string.infoDialo),getResources().getString(R.string.addTicketToMakePurchase), getActivity());
				}
				}
			}
		});


		/*	View v = ((LayoutInflater)getActivity().getSystemService(mContext.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.map_layer, null, false);
		v.setLayoutParams(new RelativeLayout.LayoutParams(
				reserveTableList.get(0).getFloorBitmap().getWidth(),reserveTableList.get(0).getFloorBitmap().getHeight()));

		mHorizontalScrollView =(HorizontalScrollView)v.findViewById(R.id.horizontallscroll);

		mDummyLayer = (RelativeLayout) v.findViewById(R.id.dummyLayer);
		tableImgLayout= (RelativeLayout)view.findViewById(R.id.tableLayer);
//		mtableImgLayout= (LinearLayout)findViewById(R.id.tableLayer);
		zoomView = new com.partyfi nder.CustomView.ZoomView(mContext);
		zoomView.setMiniMapHeight(100);
		zoomView.setMinimumWidth(100);
		//		 zoomView.
		zoomView.addView(v);
		tableImgLayout.addView(zoomView); 
//		mtableImgLayout.addView(zoomView);

//		mProgbar=(ProgressBar) findViewById(R.id.reserveTableProgbar);
//		mContext=this;
		background=(ImageView) v.findViewById(R.id.backgroundImg);*/

		return view;
	}

	void loadReserveTkt(){

		mTaskListener=new TaskListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void updateUI() {

				if(reserveTableList!=null){
					if (reserveTableList.size()>0) {




						Log.i("", "reserve table update ui is calling/....."+reserveTableList);
						PAGES=reserveTableList.size();

						View v = ((LayoutInflater)getActivity().getSystemService(mContext.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.map_layer, null, false);
						v.setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
						mmapLayer=(RelativeLayout) v.findViewById(R.id.mapLayer);
						ScrollView mScrollView = (ScrollView) v.findViewById(R.id.mapScrollView);
						/*	RelativeLayout.LayoutParams mapLayer = new RelativeLayout.LayoutParams(reserveTableList.get(0).getFloorBitmap().getWidth(),reserveTableList.get(0).getFloorBitmap().getHeight());*/
						mmapLayer.setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));

						RelativeLayout.LayoutParams mapLayer = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
						//					mmapLayer.setLayoutParams(mapLayer);
						mHorizontalScrollView =(HorizontalScrollView)v.findViewById(R.id.horizontallscroll);
						mmapScrollView=(ScrollView) v.findViewById(R.id.mapScrollView);
						mDummyLayer = (RelativeLayout) v.findViewById(R.id.dummyLayer);


						FrameLayout.LayoutParams frameParams = new FrameLayout.LayoutParams(reserveTableList.get(0).getFloorBitmap().getWidth(),reserveTableList.get(0).getFloorBitmap().getHeight());
						RelativeLayout.LayoutParams mapLayer1 = new RelativeLayout.LayoutParams(reserveTableList.get(0).getFloorBitmap().getWidth(),reserveTableList.get(0).getFloorBitmap().getHeight());
						//					mmapLayer.setLayoutParams(mapLayer1);
						//					mScrollView.setLayoutParams(mapLayer1);
						tableImgLayout= (RelativeLayout)view.findViewById(R.id.tableLayer);
						//					mtableImgLayout= (LinearLayout)findViewById(R.id.tableLayer);
						zoomView = new com.partyfinder.CustomView.ZoomView(mContext);
						zoomView.setMiniMapHeight(50);
						zoomView.setMinimumWidth(50);
						zoomView.setMaxZoom(4.0f);
						//		 zoomView.
						//					mmapLayer.setScaleX(0.7f);
						//					mmapLayer.setScaleY(0.7f);
						mmapScrollView.setLayoutParams(mapLayer1);
						AbleToPurchase =true;
						float density = getActivity().getResources().getDisplayMetrics().density;
						Log.i("", "Density  :" +density);

						if(density==1.5){

							if(reserveTableList.get(0).getFloorBitmap().getWidth()>850 || reserveTableList.get(0).getFloorBitmap().getHeight()>560){
								mDummyLayer.setScaleX(0.70f);
								mDummyLayer.setScaleY(0.70f);
								mDummyLayer.setPivotX(2f);
								mDummyLayer.setPivotY(2f);
								//							Log.i("", "mDummyLayer Condition Running...");

							}else{

								mDummyLayer.setScaleX(0.75f);
								mDummyLayer.setScaleY(0.75f);
								mDummyLayer.setPivotX(2f);
								mDummyLayer.setPivotY(2f);
							}
						}else if(density==2.0){

							if(reserveTableList.get(0).getFloorBitmap().getWidth()>850 || reserveTableList.get(0).getFloorBitmap().getHeight()>560){
								mDummyLayer.setScaleX(0.85f);
								mDummyLayer.setScaleY(0.85f);
								mDummyLayer.setPivotX(2f);
								mDummyLayer.setPivotY(2f);
								//							Log.i("", "mDummyLayer Condition Running...");

							}else{

								mDummyLayer.setScaleX(0.80f);
								mDummyLayer.setScaleY(0.80f);
								mDummyLayer.setPivotX(2f);
								mDummyLayer.setPivotY(2f);
							}


						}else if(density==3.0){

							if(reserveTableList.get(0).getFloorBitmap().getWidth()>850 || reserveTableList.get(0).getFloorBitmap().getHeight()>560){
								mDummyLayer.setScaleX(1f);
								mDummyLayer.setScaleY(1f);
								mDummyLayer.setPivotX(2f);
								mDummyLayer.setPivotY(2f);
								//							Log.i("", "mDummyLayer Condition Running...");

							}else{
								//
								mDummyLayer.setScaleX(1.10f);
								mDummyLayer.setScaleY(1.10f);
								mDummyLayer.setPivotX(2f);
								mDummyLayer.setPivotY(2f);
							}

						}else if(density==4.0){

							if(reserveTableList.get(0).getFloorBitmap().getWidth()>850 || reserveTableList.get(0).getFloorBitmap().getHeight()>560){
								mDummyLayer.setScaleX(1.15f);
								mDummyLayer.setScaleY(1.15f);
								mDummyLayer.setPivotX(2f);
								mDummyLayer.setPivotY(2f);
								//							Log.i("", "mDummyLayer Condition Running...");

							}else{

								mDummyLayer.setScaleX(1.20f);
								mDummyLayer.setScaleY(1.20f);
								mDummyLayer.setPivotX(2f);
								mDummyLayer.setPivotY(2f);
							}
						}




						zoomView.addView(v);

						tableImgLayout.addView(zoomView); 

						//					mtableImgLayout.addView(zoomView);

						//					mProgbar=(ProgressBar) findViewById(R.id.reserveTableProgbar);
						//					mContext=this;
						background=(ImageView) v.findViewById(R.id.backgroundImg);
						/*pager = (ViewPager) view.findViewById(R.id.myviewpager);

					adapter = new MyPagerAdapter((EventDetailsActivity)getActivity(),mFragmentManager,ReserveTableCabinFragment.this);

					pager.setAdapter(adapter);
					pager.setOnPageChangeListener(adapter);


					// Set current item to the middle page so we can fling to both
					// directions left and right
					FIRST_PAGE = PAGES * LOOPS / 2;
					Log.i("","First Page : "+FIRST_PAGE);
					pager.setCurrentItem(FIRST_PAGE);

					// Necessary or the pager will only have one extra page to show
					// make this at least however many pages you can see
					pager.setOffscreenPageLimit(3);

					// Set margin for pages as a negative number, so a part of next and 
					// previous pages will be showed
					pager.setPageMargin(-350);*/

						Picasso.with(mContext).load(reserveTableList.get(0).getImageUrl()).into(background,new Callback() {

							@Override
							public void onSuccess() {
								// TODO Auto-generated method stub
								mreserveTableProgressBar.setVisibility(View.GONE);	

							}

							@Override
							public void onError() {
								// TODO Auto-generated method stub
								mreserveTableProgressBar.setVisibility(View.VISIBLE);	
							}
						});
						//						Log.i("", "mapImageUrl" +reserveTableList.get(0).getImageUrl());


						View adapterView = ((LayoutInflater)getActivity().getSystemService(mContext.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.item_renderar, null, false);
						mgalleryAllContent=(RelativeLayout) adapterView.findViewById(R.id.galleryAllContent);
						//						adaptrHeader = (RelativeLayout) adapterView.findViewById(R.id.galleryHeader);
						//						adaptrSubHeader = (RelativeLayout) adapterView.findViewById(R.id.galleryContentLayout);
						gallery = (Gallery) view.findViewById(R.id.mygallery);

						gallery.setAdapter(new ImageAdapter(mContext ,reserveTableList));
						gallery.setSpacing(70);
						//					gallery.
						//						gallery.setOnItemClickListener(new OnItemClickListener() {
						gallery.setOnItemClickListener(new OnItemClickListener() {
							@Override
							public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
								// TODO Auto-generated method stub
								gallery.clearChildFocus(view);
								view.clearFocus();



								view.setSelected(false);
								view.setFocusable(false);
								view.setFocusableInTouchMode(false);

								int mSelectedItem = position;
								mImageAdapter = new ImageAdapter(mContext, reserveTableList);
								gallery.clearFocus();
								mImageAdapter.notifyDataSetChanged();
								gallery.requestLayout();
								gallery.invalidate();




								mReservTabItem=reserveTableList.get((int)id);

								if(mReservTabItem.getSold().equalsIgnoreCase("0")){
									newPos=(int)id;
									if(newPos!=oldPosition){
										selectImage();
										mImageAdapter.notifyDataSetChanged();
										view.findViewById(R.id.galleryContent).setVisibility(View.VISIBLE);
										gallery.requestLayout();
										gallery.invalidate();
										///////////////////////
										// Extra //

										//           mViewHolder.lugares=(LinearLayout) convertView.findViewById(R.id.lugares);
										//mViewHolder.consumacao=(LinearLayout) convertView.findViewById(R.id.consumacao);
										//   mViewHolder.consumacao2=(LinearLayout) convertView.findViewById(R.id.consumacao2);
										///////////////////////
										view.findViewById(R.id.lugares).setVisibility(View.VISIBLE);
										view.findViewById(R.id.consumacao).setVisibility(View.VISIBLE);
										view.findViewById(R.id.consumacao2).setVisibility(View.GONE);
















										zoomView.smoothZoomTo(1.0f, getWidth() / 3.0f, getHeight() / 3.0f);
										new Handler().postDelayed(new Runnable() {
											@Override
											public void run() {
												//												              	  mHorizontalScrollView.scrollTo(100, 0);
												mHorizontalScrollView.fullScroll(HorizontalScrollView.FOCUS_LEFT);
												mmapScrollView.fullScroll(ScrollView.FOCUS_UP);
											}
										}, 100L);


										///////////////////////
										// Extra //
										///////////////////////


									}
									view.setBackgroundResource(R.drawable.gallerybackground);
									//							adaptrHeader.setBackgroundColor(getResources().getColor(R.color.galleryAdapterHeaderColor));
									//							adaptrSubHeader.setBackgroundColor(getResources().getColor(R.color.galleryAdapterSubHeaderColor));
								}else{
									CustomDialogBox.showAlert("info", getResources().getString(R.string.cabinUnavaliabl), getActivity());

								}

							}

							private float getHeight() {
								// TODO Auto-generated method stub
								return 0;
							}

							private float getWidth() {
								// TODO Auto-generated method stub
								return 0;
							}
						});

						//						Log.i("","slelected postion : "+((Integer.MAX_VALUE/2) - (Integer.MAX_VALUE/2) % 12));
						gallery.setSelection((Integer.MAX_VALUE/2)-(Integer.MAX_VALUE/2) % 12,false );


						RelativeLayout.LayoutParams lp1 = new RelativeLayout.LayoutParams(reserveTableList.get(0).getFloorBitmap().getWidth(),reserveTableList.get(0).getFloorBitmap().getHeight());

						//						Log.i("", "Graph height"+reserveTableList.get(0).getFloorBitmap().getWidth()+" height : "+reserveTableList.get(0).getFloorBitmap().getHeight());
						background.setImageBitmap(reserveTableList.get(0).getFloorBitmap());




						//			tableImgLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.wwwwwwwwwwwww));


						//			matrix=new Matrix();
						//			sgd=new ScaleGestureDetector(mContext,new ScaleListener());
						for (int i = 0; i <reserveTableList.size(); i++) {
							//					Log.i("", "mapImageUrl" +mReservTabItem.getSelectedImage());

							mReservTabItem=reserveTableList.get(i);
							imgView=makeTextView(mReservTabItem);
							//					imgView.setTag(mReservTabItem.getSelectedImage());


							//				 Picasso.with(mContext).load(mReservTabItem.getDefaultImg()).into(imgView);
							/*if(mReservTabItem.getSold().equalsIgnoreCase("0")){
						imgView.setImageBitmap(mReservTabItem.getDefaultImage());
						}else{
						imgView.setImageBitmap(mReservTabItem.getSoldImage());	
						}*/
							RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
							lp.setMargins(Integer.parseInt(mReservTabItem.getxCordinate()), Integer.parseInt(mReservTabItem.getyCordinate()), 0, 0);
							imgView.setLayoutParams(lp);
							imgView.setId(i);
							imgView.bringToFront();
							imgView.getChildAt(0).setId(i);
							imgView.getChildAt(0).setOnClickListener(new OnClickListener() {

								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub

									gallery.setSelection((Integer.MAX_VALUE/2) - (Integer.MAX_VALUE/2) % 12,false );

									Log.i("", "11111111111111");
									ImageView imgV=(ImageView) v;
									mReservTabItem=reserveTableList.get(imgV.getId());
									if(mReservTabItem.getSold().equalsIgnoreCase("0")){

										RelativeLayout imgV1=(RelativeLayout) imgV.getParent();
										//										Log.i("","Relative layout Click : "+imgV.getId());
										newPos=imgV.getId();
										//							pager.setCurrentItem(imgV.getId());
										if(newPos!=oldPosition){
											selectImage();
										}

										// this one 

										//							FIRST_PAGE=
										//							FIRST_PAGE = PAGES * LOOPS / 3;

										int pos = newPos % reserveTableList.size();
										//									 mImageAdapter = new ImageAdapter(mContext, reserveTableList);
										//										gallery.onKeyDown(KeyEvent.KEYCODE_DPAD_LEFT, null);

										MotionEvent e1 = MotionEvent.obtain(
												SystemClock.uptimeMillis(), 
												SystemClock.uptimeMillis(),  
												MotionEvent.ACTION_DOWN, 89.333336f, 265.33334f, 0);
										MotionEvent e2 = MotionEvent.obtain(
												SystemClock.uptimeMillis(), 
												SystemClock.uptimeMillis(), 
												MotionEvent.ACTION_UP, 300.0f, 238.00003f, 0);




										gallery.onFling(e1, e2, -550, 0);

										//											mImageAdapter.notifyDataSetChanged();
										//										gallery.findViewById(R.id.galleryContent).setVisibility(View.VISIBLE);
										gallery.getSelectedView().findViewById(R.id.galleryContent).setVisibility(View.VISIBLE);
										//										gallery.
										gallery.setSelection(pos,true);

										//									gallery.getChildAt(gallery.getSelectedItemPosition()).setBackgroundColor(Color.RED);
										//									mImageAdapter.notifyDataSetChanged();

										//									adaptrHeader.setBackgroundColor(getResources().getColor(R.color.galleryAdapterHeaderColor));
										//									adaptrSubHeader.setBackgroundColor(getResources().getColor(R.color.galleryAdapterSubHeaderColor));
										Log.i("","Relative layout Click : "+pos);
									}
									//							pager.setCurrentItem(pos);
									//	ok



									//									Log.i("","Relative layout Click11111111 : "+imgV.getParent());
								}
							});
							imgView.setOnClickListener(new OnClickListener() {

								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									RelativeLayout imgV=(RelativeLayout) v;
									Log.i("","Relative layout Click : "+imgV.getId());

									//							pager.setCurrentItem(imgV.getId());
									//							selectImage(imgV);


								}
							});
							mDummyLayer.addView(imgView);

							mDummyLayer.setTag(i);



							//							Log.i("", "Added into Dummy layer"+i);

						}


						new Handler().postDelayed(new Runnable() {
							@Override
							public void run() {
								//	              	  mHorizontalScrollView.scrollTo(100, 0);
								//							mHorizontalScrollView.fullScroll(HorizontalScrollView.FOCUS_RIGHT);

							}
						}, 1000L);


					}else{
						//						Log.i("", "Custom Dialog Box: (size<0)");
						CustomDialogBox.showAlert(getResources().getString(R.string.infoDialo), getResources().getString(R.string.noTicketAvalb), getActivity());
					}
				}else{
					//					Log.i("", "Custom Dialog Box:(null)");
					CustomDialogBox.showAlert(getResources().getString(R.string.infoDialo), getResources().getString(R.string.noTicketAvalb), getActivity());
				}
			}

			@Override
			public void execute() {
				reserveTableList=ContentManager.getInstance().parseReserveTable(mEventItem);
				//				PAGES=5;//reserveTableList.size();
				Log.i("","Reserve Table List : "+reserveTableList.size());
			}
		};
		if(mAsyncTask!=null){
			mAsyncTask.cancel(true);
			mAsyncTask=null;
		}
		ProgressDialog mProgressDialog = new ProgressDialog(getActivity());
//		mProgressDialog.setMessage(getResources().getString(R.string.ProcessingText));
		mProgressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

		mAsyncTask=new CustomAsyncTask(mTaskListener,mreserveTableProgressBar, mContext);
		//		mAsyncTask.execute();
		mAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
	}
	private RelativeLayout makeTextView(ReserveTableItem mReservTable){
		//		android.widget.LinearLayout.LayoutParams layoutParam=new LinearLayout.LayoutParams(Integer.parseInt(mReservTable.getWidth()),Integer.parseInt(mReservTable.getHeight()));		
		//		layoutParam.setMargins(Integer.parseInt(mReservTable.getxCordinate()), Integer.parseInt(mReservTable.getyCordinate()), 0, 0);
		//		layoutParam.setMargins(100, 100,0,0);

		RelativeLayout.LayoutParams layoutParam=new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);

		RelativeLayout.LayoutParams textViewparam=new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		//		textViewparam.setMargins(10,5,0,0);
		textViewparam.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
		RelativeLayout imgLayout=new RelativeLayout(mContext);
		imgLayout.setClickable(true);
		TextView floorName=new TextView(mContext);
		floorName.setTextColor(Color.WHITE);
		floorName.setTextSize(7);
		floorName.setText(mReservTable.getTabCab());
		floorName.setLayoutParams(textViewparam);
		final ImageView floorImg=new ImageView(mContext);
		floorImg.bringToFront();
		//		floorImg.setPadding(3, 3,3,3);

		if(mReservTabItem.getSold().equalsIgnoreCase("0")){
			floorImg.setImageBitmap(mReservTabItem.getDefaultImage());
		}else {
			floorImg.setImageBitmap(mReservTabItem.getSoldImage());	
		}
		floorImg.setLayoutParams(layoutParam);
		imgLayout.addView(floorImg);
		imgLayout.addView(floorName);
		//		imgLayout.setLayoutParams(layoutParam);


		return imgLayout;

	}

	/*	private void selectImage(RelativeLayout imgV){
		Log.i("","Selected image view id : "+imgV.getId());
		mReservTabItem=reserveTableList.get(imgV.getId());
		//							ImageView imgV=(ImageView) mDummyLayer.getChildAt(imgView.getId());
		//							imgV.setImageBitmap(mReservTabItem.getSelectedImage());
		if(!mReservTabItem.getSold().equalsIgnoreCase("1")){
			if(mReservTabItem.isSlect()){
				mReservTabItem.setSlect(false);
				imgV.setImageBitmap(mReservTabItem.getDefaultImage());

			}else{
				mReservTabItem.setSlect(true);
				ImageView imgBut=	(ImageView) imgV.getChildAt(0);
			imgBut.setImageBitmap(mReservTabItem.getSelectedImage());	
				for(int i=1;i<mDummyLayer.getChildCount();i++){
					if(mDummyLayer.getChildAt(i).getId()==imgV.getId()){

					}else{
						if(!mReservTabItem.getSold().equalsIgnoreCase("1")){
							RelativeLayout relativeLay=(RelativeLayout) mDummyLayer.getChildAt(i);
							ImageView imgV1=(ImageView) relativeLay.getChildAt(0);
							imgV1.setImageBitmap(mReservTabItem.getDefaultImage());
						}else{

						}


					}
				}
//				pager.setCurrentItem(imgV.getId());
//			}
		}

	}*/

	@SuppressLint("NewApi")
	private void selectImage(){


		//		Log.i("","Selected image view id : "+imgV.getId()+" New Position Value : "+newPos+" Sold Status : "+mReservTabItem.getSold());
		//							ImageView imgV=(ImageView) mDummyLayer.getChildAt(imgView.getId());
		//							imgV.setImageBitmap(mReservTabItem.getSelectedImage());
		//		if(mReservTabItem.getSold().equalsIgnoreCase("0")){
		/*			if(mReservTabItem.isSlect()){
				mReservTabItem.setSlect(false);
				imgV.setImageBitmap(mReservTabItem.getDefaultImage());

			}else{*/
		/*	adaptrHeader.setBackgroundColor(getResources().getColor(R.color.galleryAdapterHeaderColor));
		adaptrSubHeader.setBackgroundColor(getResources().getColor(R.color.galleryAdapterSubHeaderColor));
		gallery.invalidate();
		mImageAdapter.notifyDataSetChanged();*/
		RelativeLayout.LayoutParams galleryParam=new RelativeLayout.LayoutParams(200,180);
		//		gallery.updateViewLayout(gallery, galleryParam);


		RelativeLayout relativeLay=(RelativeLayout) mDummyLayer.getChildAt(newPos+1);
		ImageView imgV1=(ImageView) relativeLay.getChildAt(0);
		//			ImageView imgBut=	(ImageView) imgV.getChildAt(0);
		imgV1.setImageBitmap(mReservTabItem.getSelectedImage());
		//		imgV1.setFocusable(true);
		//		imgV1.setSelected(true);
		//		imgV1.getDisplay();
		//		imgV1.setSelected(getUserVisibleHint());
		imgV1.invalidate();
		mReservTabItem.setSlect(true);
		Log.i("","Old Position : "+oldPosition+" New Index : "+newPos);



		if(oldPosition!=-1){




			mReservTabItem=reserveTableList.get(oldPosition);
			relativeLay=(RelativeLayout)mDummyLayer.getChildAt(oldPosition+1);
			imgV1=(ImageView) relativeLay.getChildAt(0);
			imgV1.setImageBitmap(mReservTabItem.getDefaultImage());
			//			imgV1.getDisplay();
			//			imgV1.setSelected(getUserVisibleHint());
			//			imgV1.setSelected(true);
			//			imgV1.setFocusable(true);
			imgV1.invalidate();
		}


		oldPosition=newPos;



	}


	class MyPagerAdapter extends FragmentPagerAdapter implements ViewPager.OnPageChangeListener {

		private MyLinearLayout cur = null;
		private MyLinearLayout next = null;
		private EventDetailsActivity context;
		private FragmentManager fm;
		private float scale;
		private ReserveTableCabinFragment ctx; 

		public MyPagerAdapter(EventDetailsActivity context, FragmentManager fm,ReserveTableCabinFragment contxt) {
			super(fm);
			this.fm = fm;
			this.context = context;
			this.ctx=contxt;
		}

		@Override
		public Fragment getItem(int position) 
		{

			// make the first pager bigger than others

			if (position == FIRST_PAGE)
				scale = BIG_SCALE;     	
			else
				scale = SMALL_SCALE;

			position = position % PAGES;
			Log.i("","Get Item position : "+position);
			return MyFragment.newInstance(context, position, scale);
		}

		@Override
		public int getCount()
		{		
			return PAGES * LOOPS;
		}

		@Override
		public void onPageScrolled(int position, float positionOffset,
				int positionOffsetPixels) 
		{	
			if (positionOffset >= 0f && positionOffset <= 1f)
			{
				cur = getRootView(position);
				next = getRootView(position +1);

				cur.setScaleBoth(BIG_SCALE-DIFF_SCALE*positionOffset);
				next.setScaleBoth(SMALL_SCALE+DIFF_SCALE*positionOffset);
			}
		}

		@Override
		public void onPageSelected(int position) {

		}
		@Override
		public void onPageScrollStateChanged(int state) {

		}

		private MyLinearLayout getRootView(int position)
		{
			Log.i("","pager Item position : "+position);
			return (MyLinearLayout) 
					fm.findFragmentByTag(this.getFragmentTag(position))
					.getView().findViewById(R.id.root);
		}

		private String getFragmentTag(int position)
		{
			return "android:switcher:" + ctx.pager.getId() + ":" + position;
		}
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
