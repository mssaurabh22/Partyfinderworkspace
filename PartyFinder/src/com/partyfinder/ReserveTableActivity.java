package com.partyfinder;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import com.partyfinder.AppConstant.PartyFinderConstants;
import com.partyfinder.CustomView.CustomAsyncTask;
import com.partyfinder.CustomView.TaskListener;
import com.partyfinder.CustomView.ZoomView;

import com.partyfinder.floorPlan.FloorInterface;
import com.partyfinder.floorPlan.MyFragment;
import com.partyfinder.floorPlan.MyLinearLayout;
import com.partyfinder.floorPlan.MyPagerAdapter;
import com.partyfinder.managers.ContentManager;
import com.partyfinder.model.EventItem;
import com.partyfinder.model.ReserveTableItem;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Matrix.ScaleToFit;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ReserveTableActivity extends FragmentActivity {

	private TaskListener mTaskListener;
	private CustomAsyncTask mAsyncTask;
	private ReserveTableItem mReservTabItem;
	private Context mContext;
	//	private ProgressBar mProgbar;
	private EventItem mEventItem;
	private ArrayList<ReserveTableItem> reserveTableList;
	private ImageView background;
	private RelativeLayout tableImgLayout,mDummyLayer;
	private LinearLayout mtableImgLayout;
	//	private ImageButton imgView;
	private RelativeLayout imgView;
	private HorizontalScrollView mHorizontalScrollView;
	//	private Matrix matrix;
	//	private ScaleGestureDetector sgd;
	private ZoomView zoomView;
	private RelativeLayout main_container;
	private WebView mWebView;


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
	private FragmentManager fragmentMgr;


	//	@Override
	//	public boolean onTouchEvent(MotionEvent event) {
	//		sgd.onTouchEvent(event);
	//		return true;
	//	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.purchase_ticket_layout);
		mEventItem=(EventItem) getIntent().getSerializableExtra(PartyFinderConstants.EXTRA_EVENTITEM);
		//		main_container=(RelativeLayout) findViewById(R.id.mapLayer);
		fragmentMgr=this.getSupportFragmentManager();
		/*pager = (ViewPager) findViewById(R.id.myviewpager);

		adapter = new MyPagerAdapter(this, this.getSupportFragmentManager());
		pager.setAdapter(adapter);
		pager.setOnPageChangeListener(adapter);


		// Set current item to the middle page so we can fling to both
		// directions left and right
		pager.setCurrentItem(FIRST_PAGE);

		// Necessary or the pager will only have one extra page to show
		// make this at least however many pages you can see
		pager.setOffscreenPageLimit(3);

		// Set margin for pages as a negative number, so a part of next and 
		// previous pages will be showed
		pager.setPageMargin(-200);*/
		View v = ((LayoutInflater) getSystemService(mContext.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.map_layer, null, false);
		v.setLayoutParams(new RelativeLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

//		mHorizontalScrollView =(HorizontalScrollView)v.findViewById(R.id.horizontallscroll);
		//		  mHorizontalScrollView.
		//		  mHorizontalScrollView.scrollBy(200, 0);
		//		mHorizontalScrollView.fullScroll(HorizontalScrollView.FOCUS_RIGHT);

		//		  new Handler().postDelayed(new Runnable() {
		//			    public void run() {
		//			    	mHorizontalScrollView.fullScroll(HorizontalScrollView.FOCUS_LEFT);
		//			    }
		//			}, 10L);






		mDummyLayer = (RelativeLayout) v.findViewById(R.id.dummyLayer);
		tableImgLayout= (RelativeLayout)findViewById(R.id.tableLayer);
		//		mtableImgLayout= (LinearLayout)findViewById(R.id.tableLayer);
		zoomView = new com.partyfinder.CustomView.ZoomView(ReserveTableActivity.this);
		zoomView.setMiniMapHeight(100);
		zoomView.setMinimumWidth(100);
		//		 zoomView.
		zoomView.addView(v);
		tableImgLayout.addView(zoomView); 
		//		mtableImgLayout.addView(zoomView);

		//		mProgbar=(ProgressBar) findViewById(R.id.reserveTableProgbar);
		mContext=this;
		background=(ImageView) v.findViewById(R.id.backgroundImg);

		loadReserveTkt();


	}

	void loadReserveTkt(){

		mTaskListener=new TaskListener() {

			@Override
			public void updateUI() {
				PAGES=reserveTableList.size();
				/*pager = (ViewPager) findViewById(R.id.myviewpager);

				adapter = new MyPagerAdapter(ReserveTableActivity.this,fragmentMgr);

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
				pager.setPageMargin(-350);
				 */
				Picasso.with(mContext).load(reserveTableList.get(0).getImageUrl()).into(background, new Callback() {

					@Override
					public void onSuccess() {
						// TODO Auto-generated method stub

					}

					@Override
					public void onError() {
						// TODO Auto-generated method stub

					}
				});//.resize(622, 583)
				Log.i("", "mapImageUrl" +reserveTableList.get(0).getImageUrl());




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
					RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(mReservTabItem.getSelectedImage().getWidth(),LayoutParams.WRAP_CONTENT);
					lp.setMargins(Integer.parseInt(mReservTabItem.getxCordinate()), Integer.parseInt(mReservTabItem.getyCordinate()), 0, 0);
					imgView.setLayoutParams(lp);
					imgView.setId(Integer.parseInt(mReservTabItem.getTabCab()));
					imgView.bringToFront();
					imgView.getChildAt(0).setId(i);
					imgView.getChildAt(0).setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							ImageView imgV=(ImageView) v;
							RelativeLayout imgV1=(RelativeLayout) imgV.getParent();
							Log.i("","Relative layout Click : "+imgV1.getId());

							//							pager.setCurrentItem(imgV.getId());
							selectImage(imgV1);
							//							FIRST_PAGE=
							FIRST_PAGE = PAGES * LOOPS / 3;

							int pos = imgV1.getId() % ReserveTableActivity.PAGES;
							pager.setCurrentItem(pos);
							//							Log.i("","Relative layout Click11111111 : "+imgV.getParent());
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



					Log.i("", "Added into Dummy layer"+i);

				}


				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						//	              	  mHorizontalScrollView.scrollTo(100, 0);
//						mHorizontalScrollView.fullScroll(HorizontalScrollView.FOCUS_RIGHT);

					}
				}, 1000L);
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
		mAsyncTask=new CustomAsyncTask(mTaskListener, mContext);
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
		}else{
			floorImg.setImageBitmap(mReservTabItem.getSoldImage());	
		}
		floorImg.setLayoutParams(layoutParam);
		imgLayout.addView(floorImg);
		imgLayout.addView(floorName);
		//		imgLayout.setLayoutParams(layoutParam);


		return imgLayout;

	}

	public static Bitmap getBitmapFromURL(String src) {
		try {
			URL url = new URL(src);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoInput(true);
			connection.connect();
			InputStream input = connection.getInputStream();
			Bitmap myBitmap = BitmapFactory.decodeStream(input);
			return myBitmap;
		} catch (Exception e) {
			Log.i("","Exception in load image url: "+e.toString());
			return null;
		}
	}

	/*	private void selectImage(ImageView imgV){
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
				imgV.setImageBitmap(mReservTabItem.getSelectedImage());	
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

	private void selectImage(RelativeLayout imgV){
		Log.i("","Selected image view id : "+imgV.getId());
		mReservTabItem=reserveTableList.get(imgV.getId());
		//							ImageView imgV=(ImageView) mDummyLayer.getChildAt(imgView.getId());
		//							imgV.setImageBitmap(mReservTabItem.getSelectedImage());
		if(!mReservTabItem.getSold().equalsIgnoreCase("1")){
			/*			if(mReservTabItem.isSlect()){
				mReservTabItem.setSlect(false);
				imgV.setImageBitmap(mReservTabItem.getDefaultImage());

			}else{*/
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

	}

	/* class MyPagerAdapter extends FragmentPagerAdapter implements
	ViewPager.OnPageChangeListener {

private MyLinearLayout cur = null;
private MyLinearLayout next = null;
private ReserveTableActivity context;
private FragmentManager fm;
private float scale;

public MyPagerAdapter(ReserveTableActivity context, FragmentManager fm) {
	super(fm);
	this.fm = fm;
	this.context = context;
}

@Override
public Fragment getItem(int position) 
{
    // make the first pager bigger than others
    if (position == ReserveTableActivity.FIRST_PAGE)
    	scale = ReserveTableActivity.BIG_SCALE;     	
    else
    	scale = ReserveTableActivity.SMALL_SCALE;

    position = position % ReserveTableActivity.PAGES;
    return MyFragment.newInstance(context, position, scale);
}

@Override
public int getCount()
{		
	return ReserveTableActivity.PAGES * ReserveTableActivity.LOOPS;
}

@Override
public void onPageScrolled(int position, float positionOffset,
		int positionOffsetPixels) 
{	
	if (positionOffset >= 0f && positionOffset <= 1f)
	{
		cur = getRootView(position);
		next = getRootView(position +1);

		cur.setScaleBoth(ReserveTableActivity.BIG_SCALE 
				- ReserveTableActivity.DIFF_SCALE * positionOffset);
		next.setScaleBoth(ReserveTableActivity.SMALL_SCALE 
				+ ReserveTableActivity.DIFF_SCALE * positionOffset);
	}
}

@Override
public void onPageSelected(int position) {
//	Log.i("","On page scroll Selected : "+position);
//	ImageView imgV=(ImageView) mDummyLayer.getChildAt(position);
//	Log.i("","Selected Position image id : "+imgV.getId());
//	selectImage(imgView);
}

@Override
public void onPageScrollStateChanged(int state) {
//	Log.i("","On page scroll stateChanged : "+state);
}

private MyLinearLayout getRootView(int position)
{
//	Log.i("","Selected Position : "+position);
	return (MyLinearLayout) 
			fm.findFragmentByTag(this.getFragmentTag(position))
			.getView().findViewById(R.id.root);
}

private String getFragmentTag(int position)
{
    return "android:switcher:" + context.pager.getId() + ":" + position;
}


  class MyFragment extends Fragment {


	public MyFragment(){

	}
	public Fragment newInstance(Context context, int pos, 
			float scale)
	{
		Bundle b = new Bundle();
		b.putInt("pos", pos);
		b.putFloat("scale", scale);
		return Fragment.instantiate(context, MyFragment.class.getName(), b);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (container == null) {
			return null;
		}

		LinearLayout l = (LinearLayout) 
				inflater.inflate(R.layout.mf, container, false);

		int pos = this.getArguments().getInt("pos");
		TextView tv = (TextView) l.findViewById(R.id.text);
		tv.setText("Position = " + pos);
		Log.i("","Selected pos"+pos);

//		MyLinearLayout root = (MyLinearLayout) l.findViewById(R.id.root);
		MyLinearLayout root=(MyLinearLayout) l.findViewById(R.id.root);
		float scale = this.getArguments().getFloat("scale");
		root.setScaleBoth(scale);

		return l;
	}
}
}*/

	/*@Override
public void floorPosition(int position) {
	// TODO Auto-generated method stub
	Log.i("","Scrolled Position"+position);
}*/



}




