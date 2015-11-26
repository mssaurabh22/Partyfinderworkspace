package com.partyfinder;
import java.io.IOException;
import java.util.ArrayList;

import com.partyfinder.AppConstant.PartyFinderConstants;
import com.partyfinder.CustomView.CustomAsyncTask;
import com.partyfinder.CustomView.CustomDialogBox;
import com.partyfinder.CustomView.CustomEditText;
import com.partyfinder.CustomView.TaskListener;
import com.partyfinder.adapters.RecommendedFriendListAdapter;
import com.partyfinder.app.utils.AppUtils;
import com.partyfinder.drawerfragment.PurchaseTicketFragment;
import com.partyfinder.drawerfragment.ReserveTableCabinFragment;
import com.partyfinder.managers.ContentManager;
import com.partyfinder.managers.SettingPrefrences;
import com.partyfinder.model.EventItem;
import com.partyfinder.model.FriendListItem;
import com.partyfinder.model.GuestListStatus;
import com.partyfinder.model.RecommendVenueStatus;
import com.partyfinder.model.UserLoginItem;
import com.squareup.picasso.Picasso;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

@SuppressLint("InflateParams")
public class EventDetailsActivity extends FragmentActivity{

	private ImageView eventPicIV;
	private TextView eventNameTV,eventAddrsTV,eventdateTV,eventtimeTV,companyNameTV,
	eventDescription;
	private EventItem mEventItem;
	//	 private ImageView backButton,btnRecommend,btnGuestList;
	//	 private Button BtnGuestList;
	private Dialog cusDialogGuestList;
	private Context mContext=this;
	private LinearLayout reserveTable,backBtnLayout,purchaseTicket,mrecommended,mguest_list_layout;
	private FragmentManager mFragmentManager;
	private FragmentTransaction mFragmentTransect;
	private Fragment mFragment;
	private Dialog mDialog;
	private String xmlString;
	private RelativeLayout mBack,mForwrd;
	private CustomAsyncTask mRecommended;
	private ProgressBar progress;
	//	 private Button purchaseTicket;


	private ArrayList<FriendListItem> mSelectedFrndList;

	private LinearLayout layout,btnGuestList;
	private UserLoginItem mLoginItem;
	private EditText editText1;
	private Button addGuestBtn,sendGuestBtn;
	private CustomAsyncTask mAsyncTask;
	private int editTextId=0;
	private GuestListStatus guestList;
	private Boolean flag=false;
	private RecommendVenueStatus mRecommendVenueStatus;
	private ProgressBar sendGuestProg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.eventdetails);
		mContext=this;
		mFragmentManager=getSupportFragmentManager();
		mEventItem =(EventItem) getIntent().getSerializableExtra(PartyFinderConstants.EXTRA_EVENTITEM);
		//				.getSerializable();
		Log.i("","Event item object : "+mEventItem.getVcEventCode());
		mLoginItem=SettingPrefrences.getUserPrefrence(mContext);
		eventdateTV=(TextView)findViewById(R.id.eventDateTextView);
		companyNameTV=(TextView)findViewById(R.id.companyNameTextView);
		eventNameTV=(TextView)findViewById(R.id.eventNameTextView);
		eventAddrsTV=(TextView)findViewById(R.id.addrTextView);
		eventtimeTV=(TextView)findViewById(R.id.eventtimeTextView);
		eventPicIV=(ImageView)findViewById(R.id.EventPicImageView);
		eventDescription=(TextView)findViewById(R.id.eventDescription);
		backBtnLayout=(LinearLayout) findViewById(R.id.back_btn_layout);
		//				btnRecommend=(ImageView)findViewById(R.id.btn_recommend);
		mrecommended=(LinearLayout) findViewById(R.id.recommended);
		mguest_list_layout=(LinearLayout) findViewById(R.id.guest_list_layout);
		//		btnGuestList=(ImageView)findViewById(R.id.btn_guestlist);
		purchaseTicket=(LinearLayout)findViewById(R.id.purchase_tickets_layout);
		reserveTable=(LinearLayout) findViewById(R.id.reserve_table_layout);

		btnGuestList=(LinearLayout)findViewById(R.id.guest_list_layout);

		btnGuestList.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				sendGuestList();

			}
		});

		reserveTable.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				/*Intent intent =new Intent(mContext,ReserveTableActivity.class);
				intent.putExtra(PartyFinderConstants.EXTRA_EVENTITEM, mEventItem);
				startActivity(intent);*/


				mFragmentTransect=mFragmentManager.beginTransaction();
				if(mFragment!=null){
					mFragmentTransect.detach(mFragment);
					mFragment=null;
				}
				mFragment=new ReserveTableCabinFragment(mEventItem);
				mFragmentTransect.add(R.id.fragmentContainer, mFragment);
				mFragmentTransect.addToBackStack("EventDetailsActivity");
				mFragmentTransect.commitAllowingStateLoss();



			}
		});
		purchaseTicket.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				//				Intent intent =new Intent(mContext,PurchaseTicketActivity.class);
				//				intent.putExtra(PartyFinderConstants.EXTRA_EVENTITEM, mEventItem);
				//				startActivity(intent);
				mFragmentTransect=mFragmentManager.beginTransaction();
				if(mFragment!=null){
					mFragmentTransect.detach(mFragment);
					mFragment=null;
				}
				mFragment=new PurchaseTicketFragment(mEventItem);
				mFragmentTransect.add(R.id.fragmentContainer, mFragment);
				mFragmentTransect.addToBackStack("EventDetailsActivity");
				mFragmentTransect.commitAllowingStateLoss();


			}
		});

		backBtnLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//				mFragmentManager.popBackStack();
				finish();
			}
		});

		//Set parsing data in views
		Picasso.with(this).load(mEventItem.getVcImageUrl()).into(eventPicIV);
		//		    String month = mEventItem.getDtEventDate();
		//		    eventdateTV.setText(modifyDate(AppUtils.changeDateFormat(month)));
		String arr[]=AppUtils.changeDateFormatArray(mEventItem.getDtEventDate());
		eventdateTV.setText(arr[0]+" "+modifyDate(arr[1]));

		eventtimeTV.setText(mEventItem.getDtEventStartTime());
		companyNameTV.setText(mEventItem.getVcCompanyName());
		eventNameTV.setText(mEventItem.getVcEventName());
		eventAddrsTV.setText(mEventItem.getVcEventAdd());
		Log.i("","Descrption Value :"+mEventItem.getDtEventDesc());

		eventDescription.setText(Html.fromHtml(mEventItem.getDtEventDesc()), TextView.BufferType.SPANNABLE);
		//		eventDescription.setText(mEventItem.getDtEventDesc().trim().replaceAll("\\s+", " "));

		Log.i("", "Event Descp"+mEventItem.getDtEventDesc());
		mrecommended.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				displayDialog(mSelectedFrndList, mContext);
				//					customDialog =new Dialog(mContext);
				//					customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
				//					customDialog.getWindow().setGravity(Gravity.BOTTOM);
				//					customDialog.setContentView(R.layout.deactivat_dialog_layout);
				//					customDialog.show();
			}
		});


		mguest_list_layout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				/*cusDialogGuestList=new Dialog(mContext);
					cusDialogGuestList.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
					cusDialogGuestList.getWindow().setGravity(Gravity.BOTTOM);
					cusDialogGuestList.setContentView(R.layout.customdialog_guestlist_layout);
					cusDialogGuestList.show();*/
				sendGuestList();
			}
		});
	}


	private com.partyfinder.CustomView.CustomEditText editText() {
		final LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,getResources().getDimensionPixelSize(R.dimen.guest_list_edit_text_width));
		lparams.setMargins(18, 8, 18, 12);
		final  com.partyfinder.CustomView.CustomEditText editText = new com.partyfinder.CustomView.CustomEditText(mContext);
		editText.setLayoutParams(lparams);

		//editText.setText("Dynamic EditText!");
		editText.setMaxLines(1);

		editText.setBackground(getResources().getDrawable(R.drawable.inputbackgroundgray));
		editText.setId(editTextId);
		editTextId++;
		//  editTextList.add(editText);
		return (CustomEditText) editText;
	}

	private void sendGuestList(){

		new ArrayList<EditText>();
		cusDialogGuestList=new Dialog(mContext);
		cusDialogGuestList.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		cusDialogGuestList.getWindow().setGravity(Gravity.BOTTOM);
		cusDialogGuestList.setCancelable(true);
		cusDialogGuestList.setContentView(R.layout.customdialog_guestlist_layout);
		sendGuestProg=(ProgressBar) cusDialogGuestList.findViewById(R.id.guestListProgress);
		addGuestBtn=(Button)cusDialogGuestList.findViewById(R.id.plushBtnGuestList);
		sendGuestBtn=(Button) cusDialogGuestList.findViewById(R.id.sendGuest);
		sendGuestProg.bringToFront();
		//     layout=new RelativeLayout(mContext);
		// ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		//  layout=(LinearLayout)cusDialogGuestList.findViewById(R.id.relativelayoutText);
		layout=(LinearLayout)cusDialogGuestList.findViewById(R.id.relativelayoutText);

		editText1=editText();
		layout.addView(editText1);
		editText1=editText();
		layout.addView(editText1);
		editText1=editText();
		layout.addView(editText1);
		//layout.setLayoutParams(params);
		//scrl.removeView(layout);
		//scrl=new ScrollView(mContext);



		// layout.addView(editText());

		//scrl.addView(layout);
		//layout.removeView(editText1);
		// editText1=editText();
		// Log.i("", "Edit text"+editText1);
		// layout.addView(editText1);


		cusDialogGuestList.show();


		addGuestBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//layout.addView(editText1);

				// i++;

				// EditText editText1=new EditText(mContext);
				editText1=editText();
				//et.setText(i+")");
				layout.addView(editText1);
				editText1.requestFocus();
			}
		});

		sendGuestBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub


				for(int i=0; i<layout.getChildCount(); i++){
					EditText  text=(EditText) layout.getChildAt(i);

					if(!text.getText().toString().equalsIgnoreCase("")){
						Log.i("", "[edit text:]"+text.getText().toString());
						flag=true;
						break;
					}

				}
				if(flag==true){
					sendGuesListData(layout);
					flag=false;
				}else{

					CustomDialogBox.showAlert(getResources().getString(R.string.infoDialo),getResources().getString(R.string.beforsendguest), mContext);
				}

				//		    if(guestList.ge){

				//		     }else {
				//		      Toast.makeText(mContext,R.string.beforsendguest, Toast.LENGTH_LONG).show(); 
				//		     }

			}
		});
	}

	private void sendGuesListData(final LinearLayout layout){

		TaskListener mGuestListListener=new TaskListener() {

			@Override
			public void updateUI() {
				// TODO Auto-generated method stub
				if(guestList!=null){
					//		     Toast.makeText(mContext,guestList.getStatus().toString(), Toast.LENGTH_LONG).show(); 

					cusDialogGuestList.dismiss();
					EventDetailsActivity.this.layout.removeAllViews();
					CustomDialogBox.showAlert(getResources().getString(R.string.infoDialo),guestList.getStatus().toString(), mContext);

				}
			}

			@Override
			public void execute() {
				// TODO Auto-generated method stub
				//    EditText text=(EditText) layout.getChildAt(o);
				/* String guestXml=preparedGuesListXml(mEventItem.getVcEventCode(),mLoginItem.getVc_user_name(),text.getText().toString(), "", "");
		    String guestUrl=PartyFinderConstants.URL_ADD_GUEST_LIST;

		     guestList=ContentManager.getInstance().parseGuestList(guestUrl, guestXml);*/



				for(int i=0;i<layout.getChildCount();i++){
					EditText  text=(EditText) layout.getChildAt(i);
					if(!text.getText().toString().equalsIgnoreCase("")){
						String guestXml=preparedGuesListXml(mEventItem.getVcEventCode(),mLoginItem.getVc_user_name(),text.getText().toString(), "", "");
						String guestUrl=PartyFinderConstants.URL_ADD_GUEST_LIST;
						guestList=ContentManager.getInstance().parseGuestList(guestUrl, guestXml);
					}

				}

			}
		};

		if (mAsyncTask != null) {
			mAsyncTask.cancel(true);
			mAsyncTask = null;
		}
		mAsyncTask = new CustomAsyncTask(mGuestListListener, sendGuestProg,mContext);
		mAsyncTask.execute();

	}

	String preparedGuesListXml(String eventCode,String invitedBy,String inviteTo,String emailId,String gender){

		String tempXml="<?xml version="+"\"1.0\""+" encoding="+"\"utf-8\""+" ?>"
				+"<Table>"+
				"<vc_event_code>"+eventCode+"</vc_event_code>"+
				"<vc_invited_by>"+invitedBy+"</vc_invited_by>"+
				"<vc_name>"+inviteTo+"</vc_name>"+
				"<vc_email>"+emailId+"</vc_email>"+
				"<vc_gender>"+gender+"</vc_gender>"+
				"</Table>";
		return tempXml;
	}

	private void executeSendRecommendedVenueTask() {

		TaskListener recommendedVenueListener = new TaskListener() {

			@Override
			public void updateUI() {
				// TODO Auto-generated method stub
				// Log.i("","Recomend status :"+mRecommendVenueStatus.getStatusCode());
				mDialog.dismiss();
			}

			@Override
			public void execute() {
				/*
				 * for(int i=0;i<frndsList.size();i++){ xmlData=new
				 * ArrayList<String>();
				 * xmlData.add(SettingPrefrences.getUserPrefrence
				 * (mContext).getVc_user_code());
				 * xmlData.add(frndsList.get(i).getUserCode());
				 * xmlData.add(mVenuesItem.getVc_company_code()); String
				 * xml=AppUtils.preparedSendRecommendedVenueXml(xmlData);
				 * Log.i("","Prepared Recomended xml : "+i+" : "+xml);
				 */
				mRecommendVenueStatus = ContentManager.getInstance()
						.parserRecommendedVenue(xmlString);
				Log.i("",
						"Recommend status :"
								+ mRecommendVenueStatus.getStatusCode());

			}

		};

		if (mRecommended != null) {
			mRecommended.cancel(true);
			mRecommended = null;
		}
		mRecommended = new CustomAsyncTask(recommendedVenueListener, progress,
				mContext);
		mRecommended.execute();

	}



	public class RecommendedFrndListForEvent extends Fragment{

		private Context mContext;
		private CustomAsyncTask mAsyncTask;
		private ListView mListView;
		private UserLoginItem mUserLoginItem;
		private ArrayList<FriendListItem> mFriendListItem;
		private ArrayList<FriendListItem>selectedFrnd;
		private ProgressBar mProgressBar;
		private ImageView okSelectedRecommendFrnd,backBtnRecommendFrnd;
		private RecommendedFriendListAdapter mRecommendFrndListAdapter;
		private FragmentManager mFragmentManager;


		public RecommendedFrndListForEvent(){

		}
		/* @Override
			  protected void onCreate(Bundle savedInstanceState) {
			   // TODO Auto-generated method stub
			   super.onCreate(savedInstanceState);*/
		// setContentView(R.layout.recommended_frnd_list_layout);


		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
			ViewGroup root = (ViewGroup) inflater.inflate(R.layout.recommended_frnd_list_layout, null);
			mContext=getActivity().getApplicationContext();
			mFragmentManager = getActivity().getSupportFragmentManager();



			mListView=(ListView) root.findViewById(R.id.friend_list);
			mBack=(RelativeLayout) root.findViewById(R.id.backButton_layout);
			mForwrd=(RelativeLayout) root.findViewById(R.id.forwardButton_layout);
			mUserLoginItem=SettingPrefrences.getUserPrefrence(mContext);
			mProgressBar=(ProgressBar) root.findViewById(R.id.progressBar);
			okSelectedRecommendFrnd=(ImageView)root. findViewById(R.id.okSelectedRecommendFrnd);
			backBtnRecommendFrnd=(ImageView)root. findViewById(R.id.back_btn_recommendFrnd);
			executeRecommendFrndList();

			mBack.setOnClickListener(recommendFrndListener);
			mForwrd.setOnClickListener(recommendFrndListener);
			backBtnRecommendFrnd.setOnClickListener(recommendFrndListener);
			okSelectedRecommendFrnd.setOnClickListener(recommendFrndListener);
			return root;
		}



		OnClickListener recommendFrndListener=new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				selectedFrnd=new ArrayList<FriendListItem>();
				if(mRecommendFrndListAdapter!=null){
					ArrayList<FriendListItem>selected=mRecommendFrndListAdapter.getmFrndListItem();

					for(int i=0;i<selected.size();i++){
						if(selected.get(i).isCheckStatus()){
							selectedFrnd.add(selected.get(i));

						}
					}
				}
				displayDialog(selectedFrnd,getActivity());

				mFragmentManager.popBackStack();
				//   onResume();
			}
		};


		public void executeRecommendFrndList(){
			if(mAsyncTask!=null){
				mAsyncTask.cancel(true);
				mAsyncTask=null;
			}
			mAsyncTask=new CustomAsyncTask(mRecommendFrndListener,mProgressBar,mContext);
			mAsyncTask.execute();
		}


		TaskListener mRecommendFrndListener=new TaskListener() {

			@Override
			public void updateUI() {
				// TODO Auto-generated method stub
				if(mFriendListItem!=null){
					if(mFriendListItem.size()>0){
						Log.i("", "VenuesItems size: "+mFriendListItem);
						mRecommendFrndListAdapter=new RecommendedFriendListAdapter(mContext, mFriendListItem);
						mListView.setAdapter(mRecommendFrndListAdapter);
					}
				}
			}

			@Override
			public void execute() {
				// TODO Auto-generated method stub
				try {
					mFriendListItem=ContentManager.getInstance().parseGetFriendListItems(mUserLoginItem);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		};
	}


	public void displayDialog(final ArrayList<FriendListItem> frndsList,
			final Context ctx) {

		if (mDialog != null) {
			mDialog.dismiss();
		}
		Log.i("", "Friend list : " + frndsList);
		ListView mListView;

		Log.i("", "GetActivity....." + this);
		mDialog = new Dialog(ctx);

		mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		Window window = mDialog.getWindow();
		window.setGravity(Gravity.BOTTOM);

		mDialog.getWindow().setBackgroundDrawable(
				new ColorDrawable(android.graphics.Color.TRANSPARENT));
		mDialog.setContentView(R.layout.customdialog_recommendlist_layout);
		progress = (ProgressBar) mDialog
				.findViewById(R.id.recommendDialogProgressBar);
		progress.bringToFront();
		Button recommendPlusButton = (Button) mDialog
				.findViewById(R.id.recommendPlus);
		Button recomendSendButton = (Button) mDialog
				.findViewById(R.id.recoomendSend);

		mListView = (ListView) mDialog.findViewById(R.id.recommendList);

		if (frndsList != null) {
			if (frndsList.size() > 0) {
				String[] frndName = new String[frndsList.size()];
				for (int i = 0; i < frndsList.size(); i++) {
					// frndNmae.add(frndsList.get(i).getUserName());
					frndName[i] = frndsList.get(i).getUserName();

				}
				Log.i("", "ArrayList of frnd name: " + frndName.length);
				// ArrayAdapter<String> arrayAdapter=new
				// ArrayAdapter<String>(mContext,
				// android.R.layout.simple_list_item_2,android.R.id.text2,
				// frndName);
				try{

				}catch(Exception e){

				}
				ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
						mContext, R.layout.customdialog_recommend_textview,
						R.id.recommendFrndText, frndName);
				mListView.setAdapter(arrayAdapter);
			}
		}

		recommendPlusButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				Log.i("", "Recomend plus button is calling..");

				mFragmentTransect = mFragmentManager.beginTransaction();
				Fragment mFragmenRecommendedFrndListActivity = new RecommendedFrndListForEvent();
				mFragmentTransect.replace(R.id.fragmentContainer,
						mFragmenRecommendedFrndListActivity);
				mFragmentTransect.addToBackStack(null);
				mFragmentTransect
				.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
				// mFragmentManager.popBackStack();
				mFragmentTransect.commitAllowingStateLoss();
				mDialog.dismiss();

			}
		});

		recomendSendButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub

				if (frndsList != null) {
					if (frndsList.size() > 0) {
						ArrayList<String> xmlData;
						for (int i = 0; i < frndsList.size(); i++) {
							xmlData = new ArrayList<String>();
							xmlData.add(SettingPrefrences.getUserPrefrence(
									mContext).getVc_user_code());
							xmlData.add(frndsList.get(i).getUserCode());
							xmlData.add(mEventItem.getVcEventCode());
							xmlString = AppUtils
									.preparedSendRecommendedVenueXml(xmlData);
							Log.i("", "Prepared Recomended xml : " + i + " : "
									+ xmlString);
							executeSendRecommendedVenueTask();
						}
					}

				} else {
					/*Toast.makeText(
				       ctx,
				       ,
				       Toast.LENGTH_SHORT).show();*/

					CustomDialogBox.showAlert(getResources().getString(R.string.errorDialog), "Por favor, adicione nomes antes de enviar a recomendação.",EventDetailsActivity.this);
				}

			}
		});

		mDialog.show();
	}



	public static String modifyDate(String date){
		String tempString;
		if(date.length()==1){
			tempString="0"+date;
		}else{
			tempString=date;
		}
		return tempString;
	}


	/*@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub

	}
	 */


}
