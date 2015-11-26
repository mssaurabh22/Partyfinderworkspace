package com.partyfinder;

import java.util.ArrayList;

import com.partyfinder.CustomView.CustomAsyncTask;
import com.partyfinder.CustomView.CustomDialogBox;
import com.partyfinder.CustomView.TaskListener;
import com.partyfinder.app.utils.AppUtils;
import com.partyfinder.drawerfragment.FragmentFavorites;
import com.partyfinder.drawerfragment.FragmentRecommended;
import com.partyfinder.drawerfragment.FragmentVenues;
import com.partyfinder.managers.ContentManager;
import com.partyfinder.managers.SettingPrefrences;
import com.partyfinder.model.AddFavoriteVanuesStatus;
import com.partyfinder.model.VenuesItem;
import com.partyfinder.model.FriendListItem;
import com.partyfinder.model.RecommendVenueStatus;
import com.partyfinder.model.UserLoginItem;
import com.partyfinder.model.VanuesLikeStatus;
import com.squareup.picasso.Picasso;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class VenuesDetailsActivity extends Fragment {
	private final String TAG="VenuesDetailsActivity";
	private static Context mContext;
	private TextView textViewAdmin,textViewCountery,textViewTelephone,textViewDescription;
	private ImageView imageViewVenues;
	private ImageView imageViewFav;
	private ImageView vanueLikeImageView;
	private ImageView recommendImageView;
	private ImageButton mVenuesBackBtn;
	private static   VenuesItem mVenuesItem;
	private ProgressBar progress;
	private CustomAsyncTask mLikeTask,mFavouriteTask,mRecommended;
	private static Dialog mDialog;
	private ImageView backIcon;

	//private Button btn_ShowAgenda;
	private TextView viewAgenda;

	private String mCurrentCompanyCode="";
	private ProgressBar progressBar;
	private UserLoginItem mLoginItem;
private RelativeLayout mBack;
	private VanuesLikeStatus mVanueLikeStatus;
	private RecommendVenueStatus mRecommendVenueStatus;
	private AddFavoriteVanuesStatus mAddFavoriteVanues;
	private RelativeLayout textViewLayout;
	private ArrayList<FriendListItem> mSelectedFrndList;
	private static VenuesDetailsActivity mVenuesDetailsActivity;
	private static String xmlString;
	private FragmentTransaction mFragmentTransect;
	private FragmentManager mFragmentManager;
	private int navigate;



	public VenuesDetailsActivity(){

	}



	//	private VenuesItem mVenueItem;
	public static  VenuesDetailsActivity getInstance() {
		// TODO Auto-generated constructor stub
		if(mVenuesDetailsActivity!=null){
			return mVenuesDetailsActivity;
			
		}else{
			return new VenuesDetailsActivity();
		}
	}

	public VenuesDetailsActivity(VenuesItem venuesItem,int navigateFrom){
		mVenuesItem=venuesItem;
		navigate=navigateFrom;

	}



	public ArrayList<FriendListItem> getmSelectedFrndList() {
		return mSelectedFrndList;
	}

	/*	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
//		getActivity().getResources().getLayout(R.layout.activity_main);

//	MainActivity.GetInstanceTab().mTabHost.setVisibility(View.VISIBLE);
	Log.i("", "Tab Host Object.."+getActivity().getClass().getName());
	}*/
	/*@Override
	public void onResume() {
		// TODO Auto-generated method stub
		//		Log.i("", "Tab Host Object....Resume "+MainActivity.GetInstanceTab().mTabHost);
		MainActivity.GetInstanceTab().tabHostLayout=(LinearLayout) getActivity().findViewById(R.id.tabhostlayout111111);
		MainActivity.GetInstanceTab().tabHostLayout.setVisibility(View.GONE);
		super.onResume();
	}


	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		//		Log.i("", "Tab Host Object....Pause "+MainActivity.GetInstanceTab().mTabHost);
		MainActivity.GetInstanceTab().tabHostLayout=(LinearLayout) getActivity().findViewById(R.id.tabhostlayout111111);
		MainActivity.GetInstanceTab().tabHostLayout.setVisibility(View.GONE);
		super.onPause();
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		//		Log.i("", "Tab Host Object....Start "+MainActivity.GetInstanceTab().mTabHost);
		MainActivity.GetInstanceTab().tabHostLayout=(LinearLayout) getActivity().findViewById(R.id.tabhostlayout111111);
		MainActivity.GetInstanceTab().tabHostLayout.setVisibility(View.GONE);
		super.onStart();
	}
*/
	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		//		Log.i("", "Tab Host Object....Stop "+MainActivity.GetInstanceTab().mTabHost);

		super.onStop();
	}




	public void setmSelectedFrndList(ArrayList<FriendListItem> mSelectedFrndList) {
		this.mSelectedFrndList = mSelectedFrndList;
	}

	/* Bottam task*/
	private TextView mTextViewTolalLikes;
	private RelativeLayout mRelativeLayout;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		ViewGroup root = (ViewGroup) inflater.inflate(R.layout.venue_detail, null);
		//        mContext=getActivity().getApplicationContext();

		mContext= this.getActivity().getApplicationContext();
		mFragmentManager = getActivity().getSupportFragmentManager();
		mRelativeLayout=(RelativeLayout) root.findViewById(R.id.textViewLayout);
		mBack=(RelativeLayout) root.findViewById(R.id.backButton_layout);
		mRelativeLayout.bringToFront();
		/*LayoutInflater inr = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inr.inflate(R.layout.activity_main,null);
        mTabHost = (FragmentTabHost) view.findViewById(android.R.id.tabhost);*/

		//MainActivity.GetInstanceTab().mTabHost=(FragmentTabHost) getActivity().findViewById(android.R.id.tabhost);
		//  MainActivity.GetInstanceTab().mTabHost.setVisibility(View.GONE);
		MainActivity.GetInstanceTab().tabHostLayout=(LinearLayout) getActivity().findViewById(R.id.tabhostlayout111111);
		MainActivity.GetInstanceTab().tabHostLayout.setVisibility(View.GONE);

		initView(root);
		//        MainActivity.GetInstanceTab().mTabHost.setVisibility(View.VISIBLE);




		//	mVenuesItem = (VenuesItem)bundle.getSerializable(PartyFinderConstants.EXTRA_VENUESITEM);
		mLoginItem=SettingPrefrences.getUserPrefrence(mContext);


		textViewLayout.bringToFront();


		if(mVenuesItem.getVc_fav_venue().equalsIgnoreCase("0")){
			//imageViewFav.setBackgroundColor(Color.GREEN);
			imageViewFav.setBackgroundResource(R.drawable.star);
		}else {
			//imageViewFav.setBackgroundColor(Color.RED);
			imageViewFav.setBackgroundResource(R.drawable.star1);
		}

		if(mVenuesItem.getVc_like_status().equalsIgnoreCase("0")){
			//vanueLikeImageView.setBackgroundColor(Color.GREEN);
			vanueLikeImageView.setBackgroundResource(R.drawable.like);


		}else{
			//vanueLikeImageView.setBackgroundColor(Color.RED);
			vanueLikeImageView.setBackground(getResources().getDrawable(R.drawable.like1));
//			mTextViewTolalLikes.setTextColor(getResources().getColor(R.color.bblue));
		}


		viewAgenda.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				//				Intent mIntent=new Intent(mContext,ViewAgendaActivity.class);
				//				mIntent.putExtra("venuesItem", mVenuesItem);
				//				startActivity(mIntent);

				mFragmentTransect=mFragmentManager.beginTransaction();
				Fragment mFragmenViewAgendaActivity = new ViewAgendaActivity(mVenuesItem);
				mFragmentTransect.replace(R.id.fragmentContainer, mFragmenViewAgendaActivity);
				mFragmentTransect.addToBackStack("VenuesDetailsActivity");
				mFragmentTransect.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
//				mFragmentManager.popBackStack();
				mFragmentTransect.commitAllowingStateLoss();

			}
		});


		vanueLikeImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(mVenuesItem.getVc_like_status().equalsIgnoreCase("0")){
					vanueLikeImageView.setBackground(getResources().getDrawable(R.drawable.like1));
//					mTextViewTolalLikes.setTextColor(getResources().getColor(R.color.bblue));
					executeLikeTask();
				}
			}
		});

		recommendImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				displayDialog(mSelectedFrndList,getActivity());
			}
		});




		imageViewFav.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if(mVenuesItem.getVc_fav_venue().equalsIgnoreCase("0")){
					imageViewFav.setBackgroundResource(R.drawable.star1);
					executefavouriteTask();
				}

			}
		});


		backIcon=(ImageView) root.findViewById(R.id.VenueDetail_btn_back);

		backIcon.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				//				mFragmentManager.popBackStack();
				//				MainActivity.GetInstanceTab().mTabHost=(FragmentTabHost) getActivity().findViewById(android.R.id.tabhost);
				//		        MainActivity.GetInstanceTab().mTabHost.setVisibility(View.VISIBLE);
				
				/*mFragmentTransect=mFragmentManager.beginTransaction();
				Fragment mFragmenViewAgendaActivity = new FragmentVenues();
				mFragmentTransect.replace(R.id.fragmentContainer, mFragmenViewAgendaActivity);
				mFragmentTransect.addToBackStack("VenuesDetailsActivity"); 
				mFragmentTransect.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
				mFragmentManager.popBackStack();
				mFragmentTransect.commitAllowingStateLoss();
				 */
				mFragmentManager.popBackStack();
				Log.i("",TAG+"navigate value : "+navigate);
				if(navigate==1){
					FragmentRecommended.getInstance().reloadFragment(mFragmentManager);
				}else if(navigate==0){
					FragmentFavorites.getInstance().reloadFragment(mFragmentManager);
				}else{
					FragmentVenues.getInstance().reloadFragment(mFragmentManager);
				}
				
			    MainActivity.GetInstanceTab().tabHostLayout=(LinearLayout) getActivity().findViewById(R.id.tabhostlayout111111);
			    MainActivity.GetInstanceTab().tabHostLayout.setVisibility(View.VISIBLE);
				// TODO Auto-generated method stub
				//				mFragmentVenues.updateVenuesList();

				/*	FragmentVenues.getInstance().updateVenuesList();*/

			}
		});
		mBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				//				mFragmentManager.popBackStack();
				//				MainActivity.GetInstanceTab().mTabHost=(FragmentTabHost) getActivity().findViewById(android.R.id.tabhost);
				//		        MainActivity.GetInstanceTab().mTabHost.setVisibility(View.VISIBLE);
				
				/*mFragmentTransect=mFragmentManager.beginTransaction();
				Fragment mFragmenViewAgendaActivity = new FragmentVenues();
				mFragmentTransect.replace(R.id.fragmentContainer, mFragmenViewAgendaActivity);
				mFragmentTransect.addToBackStack("VenuesDetailsActivity"); 
				mFragmentTransect.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
				mFragmentManager.popBackStack();
				mFragmentTransect.commitAllowingStateLoss();
				 */
				mFragmentManager.popBackStack();
				Log.i("",TAG+"navigate value : "+navigate);
				if(navigate==1){
					FragmentRecommended.getInstance().reloadFragment(mFragmentManager);
				}else if(navigate==0){
					FragmentFavorites.getInstance().reloadFragment(mFragmentManager);
				}else{
					FragmentVenues.getInstance().reloadFragment(mFragmentManager);
				}
				
			    MainActivity.GetInstanceTab().tabHostLayout=(LinearLayout) getActivity().findViewById(R.id.tabhostlayout111111);
			    MainActivity.GetInstanceTab().tabHostLayout.setVisibility(View.VISIBLE);
				// TODO Auto-generated method stub
				//				mFragmentVenues.updateVenuesList();

				/*	FragmentVenues.getInstance().updateVenuesList();*/

			}
		});



		//btn_ShowAgenda=(Button)findViewById(R.id.btn_View_Agenda);
		//		mListView=(ListView)findViewById(R.id.listView_Agenda);
		//		mProgressBarLoadIst=(ProgressBar)findViewById(R.id.progressBar_loadList);

		//btn_ShowAgenda.setOnClickListener(mOnClickListener);
		//imageViewFavourite.setOnClickListener(mOnClickListener);



		//Set parsing data in views

		Picasso.with(mContext).load(mVenuesItem.getVc_image_url()).into(imageViewVenues);
		textViewAdmin.setText(mVenuesItem.getNomeFantasia()); 
		textViewCountery.setText(mVenuesItem.getVc_country_name());
		textViewTelephone.setText(mVenuesItem.getVc_comercial_tel());
		textViewDescription.setText(mVenuesItem.getVc_description().trim().replaceAll("\\s+", " "));
		mTextViewTolalLikes.setText(mVenuesItem.getTotalLike());


		mCurrentCompanyCode=mVenuesItem.getVc_company_code();
		Log.d("", "currnet company Code :" + mCurrentCompanyCode);


		return root;
	}


	public void displayDialog(final ArrayList<FriendListItem> frndsList,final Context ctx){

		if(mDialog!=null){
			mDialog.dismiss();
		}
		Log.i("","Friend list : "+frndsList);
		ListView mListView;

		Log.i("","GetActivity....."+getActivity());
		mDialog=new Dialog(ctx);

		mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		Window window=mDialog.getWindow();
		window.setGravity(Gravity.BOTTOM);
		mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		mDialog.setContentView(R.layout.customdialog_recommendlist_layout);
		progress=(ProgressBar) mDialog.findViewById(R.id.recommendDialogProgressBar);
		progress.bringToFront();
		Button recommendPlusButton=(Button) mDialog.findViewById(R.id.recommendPlus);
		Button recomendSendButton=(Button) mDialog.findViewById(R.id.recoomendSend);

		mListView=(ListView) mDialog.findViewById(R.id.recommendList);


		if(frndsList!=null){
			if(frndsList.size()>0){
				String[] frndName=new String[frndsList.size()];
				for(int i=0;i<frndsList.size();i++ ){
					//				frndNmae.add(frndsList.get(i).getUserName());
					frndName[i]=frndsList.get(i).getUserName();

				}
				Log.i("", "ArrayList of frnd name: "+frndName.length);
				//	ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_2,android.R.id.text2, frndName);
				ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(mContext, R.layout.customdialog_recommend_textview,R.id.recommendFrndText, frndName);
				mListView.setAdapter(arrayAdapter);
			}
		}




		recommendPlusButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				/*	Intent mIntent=new Intent(mContext, RecommendedFrndListActivity.class);
				startActivity(mIntent)	;//startActivity();
				 */				
				mFragmentManager=((FragmentActivity) ctx).getSupportFragmentManager();

				mFragmentTransect=mFragmentManager.beginTransaction();
				Fragment mFragmenRecommendedFrndListActivity = new RecommendedFrndListActivity();
				mFragmentTransect.replace(R.id.fragmentContainer, mFragmenRecommendedFrndListActivity);
				mFragmentTransect.addToBackStack("VenuesDetailsActivity");
				mFragmentTransect.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
//				mFragmentManager.popBackStack();
				mFragmentTransect.commitAllowingStateLoss();
				mDialog.dismiss();

			}
		});


		recomendSendButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub

				if(frndsList!=null){
					if(frndsList.size()>0){
						ArrayList<String> xmlData;
						for(int i=0;i<frndsList.size();i++){
							xmlData=new ArrayList<String>();
							xmlData.add(SettingPrefrences.getUserPrefrence(mContext).getVc_user_code());
							xmlData.add(frndsList.get(i).getUserCode());
							xmlData.add(mVenuesItem.getVc_company_code());
							xmlString=AppUtils.preparedSendRecommendedVenueXml(xmlData);
							Log.i("","Prepared Recomended xml : "+i+" : "+xmlString);
							executeSendRecommendedVenueTask();
						}
					}

				}else{
					CustomDialogBox.showAlert(getResources().getString(R.string.infoDialo),"Por favor, adicione nomes antes de enviar a recomendação.", getActivity());
//					Toast.makeText(ctx,"Por favor, adicione nomes antes de enviar a recomendação.", Toast.LENGTH_SHORT).show();
				}




			}
		});

		mDialog.show();
	}



	private void executeLikeTask(){
		if(mLikeTask!=null){
			mLikeTask.cancel(true);
			mLikeTask=null;
		}
		mLikeTask=new CustomAsyncTask(likeListener, progressBar,mContext);
		mLikeTask.execute();
	}
	TaskListener likeListener=new TaskListener() {

		@Override
		public void updateUI() {
			// TODO Auto-generated method stub

			if(mVanueLikeStatus.getStatus().equalsIgnoreCase("1")){
				//vanueLikeImageView.setBackgroundColor(Color.RED);
				vanueLikeImageView.setBackground(getResources().getDrawable(R.drawable.like1));
//				mTextViewTolalLikes.setTextColor(getResources().getColor(R.color.bblue));
				mTextViewTolalLikes.setText(String.valueOf(Integer.parseInt(mVenuesItem.getTotalLike())+1));
			}
		}

		@Override
		public void execute() {

			ArrayList<String> isLike=new ArrayList<String>();
			isLike.add(mVenuesItem.getVc_company_code());
			isLike.add(mLoginItem.getVc_user_code());
			isLike.add("Y");
			// TODO Auto-generated method stub
			String xml=AppUtils.preparedVanueLikeXml(isLike);
			Log.i("", "prepared xml: "+xml);
			mVanueLikeStatus=ContentManager.getInstance().parserVanueLikeStatus(xml);
			Log.i(TAG, "Vanue Status Code : "+mVanueLikeStatus.getStatus());
		}
	};


	
	private void executefavouriteTask(){
		
		TaskListener favouriteListener=new TaskListener() {

			@Override
			public void updateUI() {
				// TODO Auto-generated method stub
				if(mAddFavoriteVanues.getStatus().equalsIgnoreCase("1")){
					//imageViewFav.setBackgroundColor(Color.RED);
					imageViewFav.setBackgroundResource(R.drawable.star1);
				}

			}

			@Override
			public void execute() {
				// TODO Auto-generated method stub
				ArrayList<String> mFav=new ArrayList<String>();
				mFav.add(mVenuesItem.getVc_company_code());
				mFav.add(mLoginItem.getVc_user_code());
				mFav.add("Y");
				String xml=AppUtils.preparedVanueFavouriteXml(mFav);
				mAddFavoriteVanues=ContentManager.getInstance().parserAddFavoriteVanuesStatus(xml);
				Log.i(TAG, " Favorite Vanue Status Code : "+mAddFavoriteVanues.getStatus());
			}
		};
		if(mFavouriteTask!=null){
			mFavouriteTask.cancel(true);
			mFavouriteTask=null;
		}
		mFavouriteTask=new CustomAsyncTask(favouriteListener, progressBar,mContext);
		mFavouriteTask.execute();

	}

	


	private void executeSendRecommendedVenueTask(){
		
		TaskListener recommendedVenueListener=new TaskListener() {

			@Override
			public void updateUI() {
				// TODO Auto-generated method stub
				//Log.i("","Recomend status :"+mRecommendVenueStatus.getStatusCode());
				mDialog.dismiss();
			}

			@Override
			public void execute() {
				/*	for(int i=0;i<frndsList.size();i++){
					xmlData=new ArrayList<String>();
					xmlData.add(SettingPrefrences.getUserPrefrence(mContext).getVc_user_code());
					xmlData.add(frndsList.get(i).getUserCode());
					xmlData.add(mVenuesItem.getVc_company_code());
					String xml=AppUtils.preparedSendRecommendedVenueXml(xmlData);
					Log.i("","Prepared Recomended xml : "+i+" : "+xml);*/
				mRecommendVenueStatus=ContentManager.getInstance().parserRecommendedVenue(xmlString);
				Log.i("","Recommend status :"+mRecommendVenueStatus.getStatusCode());

			}


		};

		if(mRecommended!=null){
			mRecommended.cancel(true);
			mRecommended=null;
		}
		mRecommended=new CustomAsyncTask(recommendedVenueListener, progress,mContext);
		mRecommended.execute();

	}

	

	private void initView(View root){

		mVenuesBackBtn=(ImageButton) root.findViewById(R.id.VenueDetail_btn_back);
		textViewAdmin=(TextView)root.findViewById(R.id.tV_admin);
		textViewCountery=(TextView)root.findViewById(R.id.tv_countryv);
		textViewTelephone=(TextView)root.findViewById(R.id.tv_telephone_no);
		textViewDescription=(TextView)root.findViewById(R.id.tv_description);
		imageViewVenues=(ImageView)root.findViewById(R.id.imV_Venue);
		mTextViewTolalLikes=(TextView)root.findViewById(R.id.tv_totallike);
		imageViewFav=(ImageView)root.findViewById(R.id.icn_favourite);
		vanueLikeImageView=(ImageView) root.findViewById(R.id.vanueLike);
		recommendImageView=(ImageView) root.findViewById(R.id.recommend);
		textViewLayout=(RelativeLayout) root.findViewById(R.id.textViewLayout);
		progressBar=(ProgressBar) root.findViewById(R.id.progress_VenueDetail);
		viewAgenda=(TextView) root.findViewById(R.id.btn_View_Agenda);
		//		linearLayoutEventDetails=(LinearLayout)root.findViewById(R.id.ll_for_hide);
		//		linearLayoutListView=(LinearLayout)root.findViewById(R.id.ll_for_hideone);
		//		


	}



	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		  MainActivity.GetInstanceTab().tabHostLayout=(LinearLayout) getActivity().findViewById(R.id.tabhostlayout111111);
		    MainActivity.GetInstanceTab().tabHostLayout.setVisibility(View.GONE);
		super.onResume();
	}




}