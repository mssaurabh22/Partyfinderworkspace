package com.partyfinder.drawerfragment;

import java.text.DecimalFormat;
import java.util.ArrayList;


import com.partyfinder.PurchaseTicketDetailActivity;
import com.partyfinder.R;
import com.partyfinder.UserBillingInfo;
import com.partyfinder.AppConstant.PartyFinderConstants;
import com.partyfinder.CustomView.CustomAsyncTask;
import com.partyfinder.CustomView.CustomDialog;
import com.partyfinder.CustomView.CustomDialogBox;
import com.partyfinder.CustomView.TaskListener;
import com.partyfinder.adapters.CardAdapter;
import com.partyfinder.adapters.CurrentCards;
import com.partyfinder.adapters.OrderSummaryAdapter;
import com.partyfinder.adapters.TableSummaryAdapter;
import com.partyfinder.app.utils.AppUtils;
import com.partyfinder.app.utils.CustomHttpClient;
import com.partyfinder.managers.ContentManager;
import com.partyfinder.managers.SettingPrefrences;
import com.partyfinder.model.Card;
import com.partyfinder.model.CardHolder;
import com.partyfinder.model.Cards;
import com.partyfinder.model.EventItem;
import com.partyfinder.model.OrderAssignItem;
import com.partyfinder.model.PromoterItem;
import com.partyfinder.model.ReserveTableItem;
import com.partyfinder.model.SagupaoSessionItem;
import com.partyfinder.model.TicketDetailsItem;
import com.partyfinder.model.UserLoginItem;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources.Theme;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentManager.OnBackStackChangedListener;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class PurchaseTableDeatailsFragment extends Fragment {

	private UserLoginItem mUserLogin;
	private Context mContex;
	private ImageView mBackButton;
	private TextView userName,city,state,grossTicketToatal,convenceTax,eventname,mTicketCommisionCost;

	private ArrayList<ReserveTableItem> mTicketList;


	private ListView orderSumListview;
	private TableSummaryAdapter mOrdersummaryAdapter;
	private int grossTotal;
	private Button useCurrentCard;
	private Button addNewCard;
	private CardHolder tempCardHolder;
	private String TAG="Purchase Ticket Activity Class";
	private EventItem mEventItem;
	private CustomAsyncTask mAsyncTask;
	private double grossTicketTotal = 0;
	private OrderAssignItem mAsignOrder;
	private String status;
	private final String PURCHASE_STAT_SUCCESS="SUCCESS";
	private final String PURCHASE_STAT_FAIL="FAIL";

	private FragmentManager mFragmentManager;
	private FragmentTransaction mFragmentTransect;
	private Fragment mFragment;
	private String purchaseTicketStatusMsg=PURCHASE_STAT_FAIL;
	private String submitPromoterStatus;
	private RelativeLayout mBackButtonLayout;
	private int tempCardHolderIndex;
	private ArrayList<PromoterItem> promoterList;
	private TextView mTicketCommision;
	boolean fragmentAlreadyLoaded = false;
	private UserLoginItem userLoginItem;

	public PurchaseTableDeatailsFragment() {
		// TODO Auto-generated constructor stub
	}

	public PurchaseTableDeatailsFragment(EventItem eventItem, ArrayList<ReserveTableItem> selectedTickets){
		mTicketList=selectedTickets;
		//		Log.i("",TAG+"Price : "+selectedTickets.get(0).getPrice());
		mEventItem=eventItem;
	}


	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mContex=getActivity().getApplicationContext();
		mUserLogin=SettingPrefrences.getUserPrefrence(mContex);
		mFragmentManager=getActivity().getSupportFragmentManager();
		//	  	mFragmentManager.saveFragmentInstanceState(getParentFragment());

		super.onCreate(savedInstanceState);

	}



	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		savedInstanceState=null;
		// TODO Auto-generated method stub
		View view=inflater.inflate(R.layout.ordersummary,null);
		//		   if (savedInstanceState == null && !fragmentAlreadyLoaded) {
		//	            fragmentAlreadyLoaded = true;

		// Code placed here will be executed once

		mTicketCommision=(TextView) view.findViewById(R.id.TicketCommision);
		mBackButtonLayout=(RelativeLayout)view.findViewById(R.id.backButton_layout);
		mTicketCommisionCost = (TextView) view.findViewById(R.id.TicketCommisionCost);
		mBackButton=(ImageView)view.findViewById(R.id.back_img);
		userName=(TextView) view.findViewById(R.id.ordSumaryUserName);
		grossTicketToatal=(TextView) view.findViewById(R.id.totaPrice);
		useCurrentCard=(Button) view.findViewById(R.id.ordSumryUseCurrentCard);
		addNewCard=(Button) view.findViewById(R.id.ordSumryNewCard);

		userName.setText(mUserLogin.getVc_user_name());
		city=(TextView)view. findViewById(R.id.ordSumryCity);
		city.setText(mUserLogin.getVc_user_add());
		state=(TextView) view.findViewById(R.id.ordSumryState);
		eventname=(TextView) view.findViewById(R.id.orderSummryEventname);
		eventname.setText(mEventItem.getVcEventName());
		state.setText(mUserLogin.getVc_user_country()+" "+mUserLogin.getVc_user_state());
		View viewTax= inflater.inflate(R.layout.ticket_tax_detail, null);
		convenceTax=(TextView)viewTax. findViewById(R.id.conveceTax);
		orderSumListview=(ListView)  view.findViewById(R.id.orderSumryList);

		grossTicketTotal=calculateGrossTicketTotalAmt();
		grossTicketToatal.setText("R$"+AppUtils.formatToatal(calculateGrossTicketTotalAmt()));	
		convenceTax.setText("R$"+AppUtils.formatToatal(calculateServiceTax()));
		mTicketCommisionCost.setText("R$"+AppUtils.formatToatal(calculateServiceTax()));
		mOrdersummaryAdapter=new TableSummaryAdapter(mTicketList, mContex);

		///////////////
		//  07.sept.2015 saurabh
		///////////////

		orderSumListview.setAdapter(mOrdersummaryAdapter);
		//	orderSumListview.addFooterView(viewTax);

		///////////////
		//  07.sept.2015 saurabh
		///////////////

		mTicketCommision.setText("Taxa de conveniência "+mEventItem.getCommision()+"%");

		mBackButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mFragmentManager.popBackStack();
			}
		});
		mBackButtonLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mFragmentManager.popBackStack();
			}
		});
		/*		useCurrentCard.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Cards cardsList=SettingPrefrences.getCardPrefrence(mContex);
				if(cardsList!=null){
					if(cardsList.getCardHolderList().size()>0){
						for(int i=0;i<cardsList.getCardHolderList().size();i++){
							if(cardsList.getCardHolderList().get(i).getUserCode().equalsIgnoreCase(mUserLogin.getVc_user_code())){
								tempCardHolder=cardsList.getCardHolderList().get(i);
								break;
							}
						}
					}
				}

				if(tempCardHolder!=null){
					showDialog();
					mFragmentTransect=mFragmentManager.beginTransaction();
					if(mFragment!=null){
						mFragmentTransect.detach(mFragment);
						mFragment=null;
					}
					Log.i("","EventItem :xxxxxxx : "+mEventItem);
					mFragment=new PromoterFragment(mEventItem);
					mFragmentTransect.replace(R.id.fragmentContainer, mFragment);
					mFragmentTransect.addToBackStack(null);
					mFragmentTransect.commitAllowingStateLoss();
				}else{
					CustomDialogBox.showAlert(getResources().getString(R.string.infoDialo),getResources().getString(R.string.card_not_added), getActivity());
//					Toast.makeText(mContex,R.string.card_not_added,Toast.LENGTH_SHORT).show();
				}

			}
		});*/



		useCurrentCard.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Cards cardsList=SettingPrefrences.getCardPrefrence(mContex);
				if(cardsList!=null){
					if(cardsList.getCardHolderList().size()>0){
						for(int i=0;i<cardsList.getCardHolderList().size();i++){
							if(cardsList.getCardHolderList().get(i).getUserCode().equalsIgnoreCase(mUserLogin.getVc_user_code())){
								tempCardHolder=cardsList.getCardHolderList().get(i);
								tempCardHolderIndex=i;
								break;
							}
						}
					}
				}

				if(tempCardHolder!=null){
					if(tempCardHolder.getCardList()!=null){
						if(tempCardHolder.getCardList().size()>0)
							showDialog();
						else
							CustomDialogBox.showAlert(getResources().getString(R.string.sorryDialo),getResources().getString(R.string.card_not_added), getActivity()); 
					}

					/*mFragmentTransect=mFragmentManager.beginTransaction();
			     if(mFragment!=null){
			      mFragmentTransect.detach(mFragment);
			      mFragment=null;
			     }
			     Log.i("","EventItem :xxxxxxx : "+mEventItem);
			     mFragment=new PromoterFragment(mEventItem);
			     mFragmentTransect.replace(R.id.fragmentContainer, mFragment);
			     mFragmentTransect.addToBackStack(null);
			     mFragmentTransect.commitAllowingStateLoss();*/
				}else{
					CustomDialogBox.showAlert(getResources().getString(R.string.sorryDialo),getResources().getString(R.string.card_not_added), getActivity());
					//			     Toast.makeText(mContex,R.string.card_not_added,Toast.LENGTH_SHORT).show();
				}

			}
		});


		addNewCard.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mUserLogin=SettingPrefrences.getUserPrefrence(mContex);

				if(mUserLogin.getVc_user_document()!=null){
					if(mUserLogin.getVc_user_document().length()==14){
						if(mUserLogin.getNu_pin_code()!=null){
							if(mUserLogin.getNu_pin_code().length()==9){
								if(mUserLogin.getVc_user_state()!=null){
									if(mUserLogin.getVc_user_add()!=null){
										if(mUserLogin.getVc_user_city()!=null){
											if(mUserLogin.getVc_user_email()!=null){
												mFragmentTransect=mFragmentManager.beginTransaction();
												
												if(mFragment!=null){
													mFragmentTransect.detach(mFragment);
													mFragment=null;
												}
												mFragment=new AddNewCardFragment();
												mFragmentTransect.replace(R.id.fragmentContainer, mFragment);
												mFragmentTransect.addToBackStack(null);
												mFragmentTransect.commitAllowingStateLoss();
												
											}else{
												fragmentTransectionToAddNewCard();
											}
										}else{

											fragmentTransectionToAddNewCard();

										}

									}else{

										fragmentTransectionToAddNewCard();

									}
								}else{

									fragmentTransectionToAddNewCard();

								}
							}else{

								fragmentTransectionToAddNewCard();

							}
						}else{

							fragmentTransectionToAddNewCard();

						}


					}else{

						fragmentTransectionToAddNewCard();

					}
				}
				//				Cards cards=SettingPrefrences.getCardPrefrence(mContex);



				//				Intent theIntent=new Intent(mContex,AddNewCardActivity.class);
				//				startActivity(theIntent);
			}
		});
		//		   }
		return view;

	}

	@Override
	public void onPause() {

		super.onPause();


	}	


	@Override
	public void onResume() {
		// TODO Auto-generated method stub

		super.onResume();
	}

	private double calculateGrossTicketTotalAmt(){
		double grossTotal=0;
		double purchaseAmt=0;
		double serviceTax=0;
		for(int i=0;i<mTicketList.size();i++){
			purchaseAmt=Double.parseDouble(mTicketList.get(i).getPrice())+purchaseAmt;
		}
		if(mEventItem.getCommision()!=null){
			serviceTax=Double.parseDouble(mEventItem.getCommision());
			grossTotal=purchaseAmt+(purchaseAmt*serviceTax/100);
		}
		return grossTotal;
	}

	private double calculateServiceTax(){
		double grossTotal=0;
		double purchaseAmt1=0;
		double serviceTax=0;
		//		for(int i=0;i<mTicketList.size();i++){
		purchaseAmt1=Double.parseDouble(mTicketList.get(0).getPrice())+purchaseAmt1;
		//		}
		//		Log.i("", TAG+"Purchase Amount : "+purchaseAmt1);
		if(mEventItem.getCommision()!=null){
			serviceTax=Double.parseDouble(mEventItem.getCommision());
			//			Log.i("", TAG+"Comission  : "+serviceTax);
			grossTotal=(purchaseAmt1*serviceTax/100);
			//			Log.i("", TAG+"Gross Total  : "+grossTotal);
		}
		return grossTotal;
	}

	public void fragmentTransectionToAddNewCard(){

		mFragmentTransect=mFragmentManager.beginTransaction();
		if(mFragment!=null){
			mFragmentTransect.detach(mFragment);
			mFragment=null;
		}
		mFragment=new UserBillingInfoFragment();
		mFragmentTransect.replace(R.id.fragmentContainer, mFragment);
		mFragmentTransect.addToBackStack(null);
		mFragmentTransect.commitAllowingStateLoss();

	}
	/*	private void showDialog(){
		final CustomDialog dailog=new CustomDialog(getActivity(),R.layout.existingcard_popupwindow);
		ListView existingCard=(ListView) dailog.findViewById(R.id.exisitingCardList);
		CardAdapter cardAdapter=new CardAdapter(mContex, tempCardHolder.getCardList(),getActivity());
		existingCard.setAdapter(cardAdapter);
		Button cancel=(Button) dailog.findViewById(R.id.existingCardCancel);
		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dailog.dismiss();
			}
		});
		existingCard.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub

				askSecurityPopup(tempCardHolder.getCardList().get(position));
				Log.i("",TAG+"Selected Card Item : "+position);
				dailog.dismiss();

			}
		});

		dailog.show();

	}*/

	/*	private void showDialog(){
		final CustomDialog dailog=new CustomDialog(getActivity(), R.layout.card_popupwindow);
		dailog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		ListView existingCard=(ListView) dailog.findViewById(R.id.exisitingCardList);
		CurrentCards cardAdapter=new CurrentCards(mContex, tempCardHolder.getCardList(),getActivity());
		existingCard.setAdapter(cardAdapter);
		Button cancel=(Button) dailog.findViewById(R.id.existingCardCancel);
		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dailog.dismiss();
			}
		});
		existingCard.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				askSecurityPopup(tempCardHolder.getCardList().get(position));
				Log.i("",TAG+"Selected Card Item : "+position);

				dailog.dismiss();

			}
		});

		dailog.show();

	}*/


	private void showDialog(){
		final CustomDialog dailog=new CustomDialog(getActivity(), R.layout.card_popupwindow);
		dailog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		ListView existingCard=(ListView) dailog.findViewById(R.id.exisitingCardList);
		CurrentCards cardAdapter=new CurrentCards(mContex, tempCardHolder.getCardList(),getActivity());
		existingCard.setAdapter(cardAdapter);
		Button cancel=(Button) dailog.findViewById(R.id.existingCardCancel);
		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dailog.dismiss();
			}
		});
		existingCard.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Card card=tempCardHolder.getCardList().get(position);
				if(card.isNewAdded()){
					askSecurityPopup(card);
					dailog.dismiss();
				}else{
					mUserLogin=SettingPrefrences.getUserPrefrence(mContex);
					if(mUserLogin.getVc_user_document()!=null){
						if(mUserLogin.getVc_user_document().length()==14){
							purchaseTicket(card);
							updateCard(card,position);

							/*if(mFragment!=null){
		        mFragmentTransect.detach(mFragment);
		        mFragment=null;
		       }
		       Log.i("","EventItem :xxxxxxx : "+mEventItem);
		       mFragment=new PromoterFragment(mEventItem); 
		       mFragmentTransect.replace(R.id.fragmentContainer, mFragment);
		       mFragmentTransect.addToBackStack(null);
		       mFragmentTransect.commitAllowingStateLoss();*/
							dailog.dismiss();
						}else{
							Intent theIntent=new Intent(mContex,UserBillingInfo.class);
							theIntent.putExtra("Navigation","PurchaseTicketDet");
							startActivity(theIntent);
							dailog.dismiss();
						}
					}
					//		     hideSoftKeyboard();
				}

				Log.i("",TAG+"Selected Card Item : "+position);

				dailog.dismiss();

			}
		});

		dailog.show();

	}






	private void askSecurityPopup(final Card cardItem){
		final CustomDialog dailog=new CustomDialog(getActivity(), R.layout.existingcard_security_popupwindow);
		final EditText password=(EditText) dailog.findViewById(R.id.securityEd);
		TextView okButton=(TextView) dailog.findViewById(R.id.okButton);
		dailog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		okButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mUserLogin=SettingPrefrences.getUserPrefrence(mContex);
				hideSoftKeyboard();
				String passwd=password.getText().toString();
				if(passwd.equalsIgnoreCase("")){
					Toast.makeText(mContex, getResources().getString(R.string.incorrect_securitycode),Toast.LENGTH_SHORT).show();
					dailog.dismiss();
				}else
					if (cardItem.getLastThreeDigit().equalsIgnoreCase(passwd)) {
						if(mUserLogin.getVc_user_document()!=null){
							if(mUserLogin.getVc_user_document().length()==14){
								purchaseTicket(cardItem);

								/*if(mFragment!=null){
									mFragmentTransect.detach(mFragment);
									mFragment=null;
								}
								Log.i("","EventItem :xxxxxxx : "+mEventItem);
								mFragment=new PromoterFragment(mEventItem); 
								mFragmentTransect.replace(R.id.fragmentContainer, mFragment);
								mFragmentTransect.addToBackStack(null);
								mFragmentTransect.commitAllowingStateLoss();*/
								dailog.dismiss();
							}else{
								Intent theIntent=new Intent(mContex,UserBillingInfo.class);
								theIntent.putExtra("Navigation","PurchaseTicketDet");
								startActivity(theIntent);
								dailog.dismiss();
							}
						}
					}else{

						Toast.makeText(mContex, getResources().getString(R.string.incorrect_securitycode),Toast.LENGTH_SHORT).show();
						dailog.dismiss();
					}
			}
		});
		dailog.show();
	}



	private void updateCard(Card carDetail,int cardIndex){

		carDetail.setNewAdded(true);
		Cards cards=SettingPrefrences.getCardPrefrence(mContex);
		ArrayList<CardHolder> mCardHolderList=cards.getCardHolderList();
		mCardHolderList.get(tempCardHolderIndex).getCardList().set(cardIndex, carDetail);//add(cardIndex, carDetail);
		//  Cards cardList=SettingPrefrences.getCardPrefrence(mContext);
		cards.setCardHolderList(mCardHolderList);
		SettingPrefrences.saveCardPrefrence(cards, mContex);


	}


	private void purchaseTicket(final Card cardItem){
		//		Log.i("",TAG+"Go for purchase ticket..");
		TaskListener mTaskListener=new TaskListener() {

			@Override
			public void updateUI() {
				// TODO Auto-generated method stub
				if(purchaseTicketStatusMsg.equalsIgnoreCase(PURCHASE_STAT_SUCCESS)){
					if(promoterList!=null){

						if(submitPromoterStatus.equalsIgnoreCase("1")){
							mFragmentTransect=mFragmentManager.beginTransaction();
							if(mFragment!=null){
								mFragmentTransect.detach(mFragment);
								mFragment=null;
							}
							mFragment=new ConfirmOrderFragment(mAsignOrder, mEventItem,calculateGrossTicketTotalAmt());
							mFragmentTransect.replace(R.id.fragmentContainer, mFragment);
							mFragmentTransect.addToBackStack(null);
							mFragmentTransect.commitAllowingStateLoss();
						}else{
							if(promoterList.size()>0){

								mFragmentTransect=mFragmentManager.beginTransaction();
								if(mFragment!=null){
									mFragmentTransect.detach(mFragment);
									mFragment=null;
								}
								mFragment=new PromoterFragment(mEventItem, promoterList,mAsignOrder,calculateGrossTicketTotalAmt());
								mFragmentTransect.replace(R.id.fragmentContainer, mFragment);
								mFragmentTransect.addToBackStack(null);
								mFragmentTransect.commitAllowingStateLoss();
							}
						}



					}
					//				Toast.makeText(mContex, "Seu transection é bem sucedida", Toast.LENGTH_SHORT).show();
				}else{
					CustomDialogBox.showAlert(getResources().getString(R.string.errorDialog), "Seu transection não é sucessfull verifique informação correta ou tente novamente ..", getActivity());
					//				Toast.makeText(mContex, "Seu transection não é sucessfull verifique informação correta ou tente novamente ..", Toast.LENGTH_SHORT).show();
				}
			}

			@Override
			public void execute() {
				// TODO Auto-generated method stub
				ArrayList<String> data=new ArrayList<String>();
				data.add(mUserLogin.getVc_user_code());
				data.add(mEventItem.getVcEventCode());




				/////////////////////////////////////////////
				////////////////////////////////////////////


				mAsignOrder=ContentManager.getInstance().parseAssignOrder(AppUtils.prepareAssignOrderXml(data));
				Log.i("",TAG+" Assign Order xml : "+AppUtils.prepareAssignOrderXml(data));

				String xml=preparedOrderDummyHeader();
				Log.i("",TAG+" Dummy header xml : "+xml);
				//				taskAddDummyHeader(xml);

				status=ContentManager.getInstance().parseDummyOrderHeader(PartyFinderConstants.URL_ASSIGN_ORDER_HERADER, xml);
				Log.i("",TAG+"Dummy header response : "+status);
				int responseStatus=0;

				if(status!=null){  

					if(status.equalsIgnoreCase("success")){
						Log.i("",TAG+"Dummy header response : "+status);
						//						String sagupaoLink="https://ws.pagseguro.uol.com.br/v2/sessions?email="+"carlosbarros@partyfinder.com.br"+"&token="+"B69F7AE0E8A04998838741ACDA05B40A";		//A91E2233394F48858E8BBDAD699AD593";
						//						 B69F7AE0E8A04998838741ACDA05B40A
						String sagupaoLink="https://ws.pagseguro.uol.com.br/v2/sessions?email="+"carlosbarros@partyfinder.com.br"+"&token="+"B69F7AE0E8A04998838741ACDA05B40A";		//A91E2233394F48858E8BBDAD699AD593";

						String sagupaoSession=ContentManager.getInstance().getSagupaoSession(sagupaoLink);
						Log.i("",TAG+"SAGUPAO SESSION : "+sagupaoSession);

						if(sagupaoSession!=null){
							String CardNo=cardItem.getCardNumber();
							String tempCard=CardNo.replaceAll("\\s+","").substring(0,6);
							Log.i("",TAG+" Card Number : "+tempCard);
							String URL_SAGUPAO_CARDETAIL="https://df.uol.com.br/df-fe/mvc/creditcard/v1/getBin?tk="+sagupaoSession.trim()+"&creditCard="+tempCard;

							SagupaoSessionItem sagupaoItem=ContentManager.getInstance().getCardDetail(URL_SAGUPAO_CARDETAIL);

							String CardExpMonth=cardItem.getCardVal().substring(0, 2);//.substring(2, 4) ;
							String CardExpYear=cardItem.getCardVal().substring(cardItem.getCardVal().length()-2);

							Log.i("", "Card month : " +CardExpMonth +"  Card year : "+CardExpYear);

							String CardTokkenUrl ="https://df.uol.com.br/v2/cards?sessionId="+sagupaoSession.trim()
									+"&cardNumber="+CardNo.replaceAll("\\s", "")+"&cardBrand="+sagupaoItem.getCardBarnd()+"&cardCvv="+cardItem.getLastThreeDigit()
									+"&cardExpirationMonth="+CardExpMonth+"&cardExpirationYear=20"+CardExpYear; 
							Log.i("",TAG+"CardTokkenUrl :  "+CardTokkenUrl);
							String CardTokken=ContentManager.getInstance().getCardToken(CardTokkenUrl);
							Log.i("",TAG+"CardTokken:  "+CardTokken);





							if(sagupaoItem!=null){
								if(CardTokken!=null){
									String mundipaggXml=preparedXmlForpaymentmundipagg(cardItem,sagupaoItem,CardTokken);	
									String mundiPaggUrl="https://ws.pagseguro.uol.com.br/v2/transactions?email=carlosbarros@partyfinder.com.br&token=B69F7AE0E8A04998838741ACDA05B40A&currency=BRL";
									Log.i("",TAG+"Mundipag Prepare Xml :  "+mundipaggXml);
									//
									//
									//
									//
									//
									//
									responseStatus=CustomHttpClient.postMundipaggRequest(mundiPaggUrl, mundipaggXml);
									Log.i("", "Paymaent response :" +responseStatus);
									//															Response = Integer.toString(responseStatus);
									//								MundipuggXML = mundipaggXml;
























									if(responseStatus==200){
										purchaseTicketStatusMsg=PURCHASE_STAT_SUCCESS;
										String getPro=PartyFinderConstants.URL_GET_PROMOTER+mEventItem.getVcEventCode()+"/"+mUserLogin.getVc_user_code();//"E00027/U00016"
										promoterList=ContentManager.getInstance().parsePromoter(getPro);
										PromoterItem mProoterItem = null;
										if(promoterList!=null){
											if(promoterList.size()>0)	{
												if (promoterList.size() <= 2) {
													if (promoterList.size() < 2) {

														// for(int
														// i=0;i<promoterList.size();i++){
														// if(!promoterList.get(i).getPromoterType().equalsIgnoreCase("Club")){
														//
														// }
														// }
														mProoterItem = promoterList.get(0);
													} else if (promoterList.size() == 2) {
														for (int i = 0; i < promoterList
																.size(); i++) {
															if (!promoterList
																	.get(i)
																	.getPromoterType()
																	.equalsIgnoreCase(
																			"Club")) {
																mProoterItem = promoterList
																		.get(i);
																break;
															}
														}
													}
													ArrayList<String> xmldata=new ArrayList<String>();
													xmldata.add(mAsignOrder.getOrderCode());
													xmldata.add(mProoterItem.getPromoterCode());
													String promXml=AppUtils.preparedSubmitPromoterXml(xmldata);
													String promterUrl=PartyFinderConstants.URL_SUMIT_PROMOTER;
													submitPromoterStatus=ContentManager.getInstance().submitPromoter(promterUrl, promXml);
													//									<Table><vc_order_code>O000135</vc_order_code><vc_user_code>C00013</vc_user
													//
													//									_code></Table>
												}
											}
										}

									}

								}

							}}
						//						Log.i("",TAG+" Mundipagg Status Code : "+responseStatus);
					}
				}
			}
		};
		if(mAsyncTask!=null){
			mAsyncTask.cancel(true);
			mAsyncTask=null;
		}
		ProgressDialog progDailog=new ProgressDialog(getActivity());
		progDailog.setCancelable(false);

		//		progDailog.setTitle("Please wait ...");
		progDailog.setMessage(getResources().getString(R.string.ProcessingText));
		progDailog.setProgressStyle(progDailog.STYLE_SPINNER);
		progDailog.show();
		mAsyncTask=new CustomAsyncTask(mTaskListener, progDailog, mContex);
		mAsyncTask.execute();
	}

	private String preparedOrderDummyHeader(){
		String tempXml;
		tempXml="<?xml version="+"\"1.0\""+" encoding="+"\"utf-8\""+" ?>"
				+"<Transection>"+
				"<vc_order_code>"+mAsignOrder.getOrderCode()+"</vc_order_code>"+
				"<grossAmount>"+grossTicketTotal+"</grossAmount>"+

				"<vc_event_code>"+mEventItem.getVcEventCode()+"</vc_event_code>"+

					preparedInternalTag()
					+"</Transection>";

		//		Log.i("","Prepared XML : "+tempXml);



		return tempXml;


	}

	public void hideSoftKeyboard1() {
		if(getActivity().getCurrentFocus()!=null) {

			InputMethodManager inputMethodManager = (InputMethodManager)getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
			inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
		}
	}


	protected void hideSoftKeyboard() {
		InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
		if (imm.isActive()) {
			imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}
	//	public static void hide_keyboard_from(Context context, View view) {
	//	    InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
	//	    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
	//	}

	String preparedInternalTag(){

		final String startItem="<items>";
		final String endItem="</items>";
		final String startTypeCode="<vc_type_code>";
		final String endTypeCode="</vc_type_code>";

		final String startQuantity="<quantity>";
		final String endQuantity="</quantity>";

		final String startNoSeat="<vc_no_of_seats>";
		final String endNoSeat="</vc_no_of_seats>";
		final String startAmount="<amount>";
		final String endAmount="</amount>";
		String item="";
		for(int i=0;i<mTicketList.size();i++){
			ReserveTableItem mTicket=mTicketList.get(i);
			String floorCode="";
			if(mTicket.getTableType().equalsIgnoreCase("Table")||mTicket.getTableType().equalsIgnoreCase("mesa")){
				floorCode="TAB"+mTicket.getTabCabId();
			}else{
				floorCode="CAB"+mTicket.getTabCabId();
			}
			//			Log.i("",TAG+"Ticket type code : "+floorCode);
			item=item+
					startItem+

					startTypeCode+floorCode+endTypeCode
					+startQuantity+"0"+endQuantity
					+startNoSeat+mTicket.getNoOfSeat()+endNoSeat
					+startAmount+mTicket.getPrice()+endAmount
					+endItem;

		}
		return item;
	}

	private void taskAddDummyHeader(final String xml){

		TaskListener mtaskListener=new TaskListener() {

			@Override
			public void updateUI() {
				// TODO Auto-generated method stub
				if(status!=null){
					if(status.equalsIgnoreCase("success")){
						//						Log.i("",TAG+"Dummy header response : "+status);
					}
				}
			}

			@Override
			public void execute() {
				// TODO Auto-generated method stub
				status=ContentManager.getInstance().parseDummyOrderHeader(PartyFinderConstants.URL_ASSIGN_ORDER_HERADER, xml);
			}
		};
		ProgressDialog progDailog=new ProgressDialog(mContex);
		mAsyncTask=new CustomAsyncTask(mtaskListener, progDailog, mContex);
		mAsyncTask.execute();

	}

	private String preparedXmlForpaymentmundipagg(Card cardItem,SagupaoSessionItem sagPaoItem,String Token){

		userLoginItem=SettingPrefrences.getUserPrefrence(getActivity());
		String Tokken = Token;
		String cardValidity[]=cardItem.getCardVal().split("/");
		grossTicketTotal=grossTicketTotal*100;
		double tempTotal=calculateGrossTicketTotalAmt();

		int total=(int) (tempTotal*100);
		String CardHolderName = cardItem.getCardName();
		String CardBrand = sagPaoItem.getCardBarnd();
		CardBrand =Character.toUpperCase(CardBrand.charAt(0)) + CardBrand.substring(1);
		String MODE ="default";
		String CURRENCY ="BRL";
		String NOTIFICATIONURL = "http://www.partyfinder.com.br/services/GetPost_data.aspx";
		String METHOD ="CreditCard";
		String UserCPFno = userLoginItem.getVc_user_document().replace(".", "").replace("-", "");
		Log.i("", "1111   User cpf no.  : " +UserCPFno);
		String CArdCpfNo =cardItem.getCpfNo().replace(".", "").replace("-", "");
		Log.i("", "1111   User CArdCpfNo no.  : " +CArdCpfNo);
		String UserphoneNo = cardItem.getTelephone().substring(3, cardItem.getTelephone().length());
		Log.i("", "1111   User UserphoneNo no.  : " +UserphoneNo);
		String UserphoneNoPostalCode = cardItem.getTelephone().substring(0, 2);
		Log.i("", "1111   User UserphoneNoPostalCode no.  : " +UserphoneNoPostalCode);

		String EXTRAamount = AppUtils.newFormatToatal(calculateServiceTax());
		String VALUEamount = AppUtils.newFormatToatal(tempTotal);



		String tempXml=null;





		tempXml="<?xml version="+"\"1.0\""+" encoding="+"\"ISO-8859-1\""+"standalone="+"\"yes\""+"?>"
				+"<payment>"+
				"<mode>"+MODE+"</mode>"+
				"<currency>"+CURRENCY+"</currency>"+
				"<notificationURL>"+NOTIFICATIONURL+"</notificationURL>"+
				"<method>"+METHOD+"</method>"+

							"<items>"+
							/*"<item>"+
							"<id>"++"</id>"+
							"<description>"+total+"</description>"+
							"<amount>"+total+"</amount>"+
							"<weight>"+total+"</weight>"+
							"</item>"+*/
							preparedInternalTagForPayment()+
							"</items>"+
							"<extraAmount>"+EXTRAamount+"</extraAmount>"+
							"<reference>"+mAsignOrder.getOrderCode()+"</reference>"+

							"<sender>"+			
							"<name>"+userLoginItem.getVc_user_name()+"</name>"+
							"<email>"+userLoginItem.getVc_user_email()+"</email>"+

									"<phone>"+
									"<areaCode>"+UserphoneNoPostalCode+"</areaCode>"+
									"<number>"+UserphoneNo+"</number>"+
									"</phone>"+
									"<documents>"+
									"<document>"+
									"<type>"+"CPF"+"</type>"+
									"<value>"+UserCPFno+"</value>"+
									"</document>"+
									"</documents>"+
									"</sender>"+	
									"<shipping>"+
									"<type>"+"1"+"</type>"+
									"<address>"+
									"<street>"+mUserLogin.getVc_user_add()+"</street>"+
									"<number>(null)</number>"+
									"<complement></complement>"+
									"<district>"+mUserLogin.getVc_user_state()+"</district>"+
									"<postalCode>"+mUserLogin.getNu_pin_code()+"</postalCode>"+
									"<city>"+mUserLogin.getVc_user_city()+"</city>"+
									"<state>"+mUserLogin.getVc_user_state()+"</state>"+
									"<country>Brazil</country>"+
									"</address>"+
									"</shipping>"+
									"<creditCard>"+
									"<token>"+Tokken+"</token>"+
									"<installment>"+
									"<quantity>1</quantity>"+
									"<value>"+VALUEamount+"</value>"+
									"</installment>"+
									"<holder>"+
									"<name>"+CardHolderName+"</name>"+
									"<documents>"+
									"<document>"+
									"<type>"+"CPF"+"</type>"+
									"<value>"+CArdCpfNo+"</value>"+
									"</document>"+
									"</documents>"+
									"<birthDate>"+cardItem.getBirthDate()+"</birthDate>"+
									"<phone>"+
									"<areaCode>"+UserphoneNoPostalCode+"</areaCode>"+
									"<number>"+UserphoneNo+"</number>"+
									"</phone>"+
									"</holder>"+
									"<billingAddress>"+
									"<street>"+mUserLogin.getVc_user_add()+"</street>"+
									"<number>(null)</number>"+
									"<complement></complement>"+
									"<district>"+mUserLogin.getVc_user_state()+"</district>"+
									"<city>"+mUserLogin.getVc_user_city()+"</city>"+
									"<state>"+mUserLogin.getVc_user_state()+"</state>"+
									"<country>BRA</country>"+
									"<postalCode>"+userLoginItem.getNu_pin_code()+"</postalCode>"+
									"</billingAddress>"+
									"</creditCard>"+
									"</payment>";

		/*tempXml=
				"<CreateSaleRequest>"+
						"<CreditCardTransactionCollection>"+
						"<CreditCardTransaction>"+
						"<AmountInCents>"+total+"</AmountInCents>"+
						"<CreditCard>"+
						//				"<CreditCardBrand>"+sagPaoItem.getCardBarnd()+"</CreditCardBrand>"+
						"<CreditCardBrand>"+CardBrand+"</CreditCardBrand>"+
						"<CreditCardNumber>"+cardItem.getCardNumber().replaceAll("\\s+","")+"</CreditCardNumber>"+// http://services.partyfinder.com.br/Service1.svc/GETHOTEVENTS
						"<ExpMonth>"+cardValidity[0]+"</ExpMonth>"+
						"<ExpYear>"+cardValidity[1]+"</ExpYear>"+//
						"<HolderName>"+CardHolderName+"</HolderName>"+
						"<SecurityCode>"+cardItem.getLastThreeDigit()+"</SecurityCode>"+
						"</CreditCard>"+
						"<CreditCardOperation>"+"AuthAndCapture"+"</CreditCardOperation>"+	
						"<InstallmentCount>"+"1"+"</InstallmentCount>"+	
						"<Options>"+
						"<PaymentMethodCode>"+"0"+"</PaymentMethodCode>"+
						"<SoftDescriptorText>"+"PartyFinder"+"</SoftDescriptorText>"+
						"</Options>"+
						"</CreditCardTransaction>"+
						"</CreditCardTransactionCollection>"+	
						"<Options>"+
						"<IsAntiFraudEnabled>"+"false"+"</IsAntiFraudEnabled>"+
						"<Retries>"+"3"+"</Retries>"+
						"</Options>"+
						"<Order>"+
						"<OrderReference>"+mAsignOrder.getOrderCode()+"</OrderReference>"+
						"</Order>"+
						"</CreateSaleRequest>";*/


		return tempXml;

	}

	private String postMundiPagg(String url,String xml){
		String statusResp=null;

		return statusResp;
	}

	String preparedInternalTagForPayment(){

		double serviceTax =Double.parseDouble(mEventItem.getCommision());




		String item="";
		for(int i=0;i<mTicketList.size();i++){
			ReserveTableItem mTicket=mTicketList.get(i);
			double purchaseAmt =Double.parseDouble(mTicket.getPrice());
			double grossTotal  =purchaseAmt+(purchaseAmt*serviceTax/100);
			String amount = AppUtils.newFormatToatal(Double.parseDouble(mTicketList.get(i).getPrice()));


			item="<item>"+
					"<id>"+mTicket.getTableType()+"</id>"+
					"<description>"+mTicket.getTabCabId()+"</description>"+
					//<amount>"+AppUtils.AppendZeroArray(mTicketList.get(i).getTicketPrice())+"</amount>"+
					"<amount>"+amount+"</amount>"+
					"<quantity>1</quantity>"+
					"<weight>"+"0"+"</weight>"+
					"</item>";

		}

		return item;
	}



	public String appendZero(String cost){

		if(cost.contains(".")){
			cost = cost+"0";			
		}else{
			cost=cost+".00";
		}

		return cost;

	}
























	//-------------------------------------------------------------------------------------------------------------	

	//Previous Code	




	/*	private UserLoginItem mUserLogin;
	private Context mContex;
	private ImageView mBackButton;
	private TextView userName,city,state,grossTicketToatal;
	private ArrayList<TicketDetailsItem> mTicketList;
	private ListView orderSumListview;
	private OrderSummaryAdapter mOrdersummaryAdapter;
	private int grossTotal;
	private Button useCurrentCard;
	private Button addNewCard;
	private CardHolder tempCardHolder;
	private String TAG="Purchase Ticket Activity Class";
	private EventItem mEventItem;
	private CustomAsyncTask mAsyncTask;
	private double grossTicketTotal = 0;
	private OrderAssignItem mAsignOrder;
	private String status;


	private FragmentManager mFragmentManager;
	private FragmentTransaction mFragmentTransect;
	private Fragment mFragment;
	public PurchaseTicketDeatailsFragment() {
		// TODO Auto-generated constructor stub
	}

	public PurchaseTicketDeatailsFragment(EventItem eventItem, ArrayList<TicketDetailsItem> selectedTickets){
		mTicketList=selectedTickets;
		mEventItem=eventItem;
	}


	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mContex=getActivity().getApplicationContext();
		mUserLogin=SettingPrefrences.getUserPrefrence(mContex);
		mFragmentManager=getActivity().getSupportFragmentManager();
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view=inflater.inflate(R.layout.ordersummary, null);

		mBackButton=(ImageView)view.findViewById(R.id.back_img);
		userName=(TextView) view.findViewById(R.id.ordSumaryUserName);
		grossTicketToatal=(TextView) view.findViewById(R.id.totaPrice);
		useCurrentCard=(Button) view.findViewById(R.id.ordSumryUseCurrentCard);
		addNewCard=(Button) view.findViewById(R.id.ordSumryNewCard);

		userName.setText(mUserLogin.getVc_user_name());
		city=(TextView)view. findViewById(R.id.ordSumryCity);
		city.setText(mUserLogin.getVc_user_add());
		state=(TextView) view.findViewById(R.id.ordSumryState);
//		state.setText(mUserLogin.getVc_user_state());

		orderSumListview=(ListView)  view.findViewById(R.id.orderSumryList);
//		mTicketList =(ArrayList<TicketDetailsItem>)getIntent().getSerializableExtra("FILES_TO_SEND");
//		mEventItem=(EventItem) getIntent().getSerializableExtra(PartyFinderConstants.EXTRA_EVENTITEM);
		Log.i("","Slected TicktSize : "+mTicketList.size());		

		for(int i=0;i<mTicketList.size();i++){
			grossTicketTotal=mTicketList.get(i).getTotalTicketPrice()+grossTicketTotal;
		}

		grossTicketToatal.setText("R$"+grossTicketTotal);	
		mOrdersummaryAdapter=new OrderSummaryAdapter(mTicketList,mContex);
		orderSumListview.setAdapter(mOrdersummaryAdapter);

		mBackButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mFragmentManager.popBackStack();
			}
		});

		useCurrentCard.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Cards cardsList=SettingPrefrences.getCardPrefrence(mContex);
				if(cardsList!=null){
					if(cardsList.getCardHolderList().size()>0){
						for(int i=0;i<cardsList.getCardHolderList().size();i++){
							if(cardsList.getCardHolderList().get(i).getUserCode().equalsIgnoreCase(mUserLogin.getVc_user_code())){
								tempCardHolder=cardsList.getCardHolderList().get(i);
								break;
							}
						}
					}
				}

				if(tempCardHolder!=null){
					showDialog();
				}else{
					Toast.makeText(mContex,R.string.card_not_added,Toast.LENGTH_SHORT).show();
				}

			}
		});

		addNewCard.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				mFragmentTransect=mFragmentManager.beginTransaction();
				if(mFragment!=null){
					mFragmentTransect.detach(mFragment);
					mFragment=null;
				}
				mFragment=new AddNewCardFragment();
				mFragmentTransect.replace(R.id.fragmentContainer, mFragment);
				mFragmentTransect.addToBackStack(null);
				mFragmentTransect.commitAllowingStateLoss();
//				Intent theIntent=new Intent(mContex,AddNewCardActivity.class);
//				startActivity(theIntent);
			}
		});
		return view;
	}
	private void showDialog(){
		final CustomDialog dailog=new CustomDialog(getActivity(), R.layout.card_popupwindow);
		dailog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		ListView existingCard=(ListView) dailog.findViewById(R.id.exisitingCardList);
		CardAdapter cardAdapter=new CardAdapter(mContex, tempCardHolder.getCardList(),getActivity());
		existingCard.setAdapter(cardAdapter);
		Button cancel=(Button) dailog.findViewById(R.id.existingCardCancel);
		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dailog.dismiss();
			}
		});
		existingCard.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				askSecurityPopup(tempCardHolder.getCardList().get(position));
				Log.i("",TAG+"Selected Card Item : "+position);
				dailog.dismiss();

			}
		});

		dailog.show();

	}

	private void askSecurityPopup(final Card cardItem){
		final CustomDialog dailog=new CustomDialog(getActivity(), R.layout.existingcard_security_popupwindow);
		final EditText password=(EditText) dailog.findViewById(R.id.securityEd);
		TextView okButton=(TextView) dailog.findViewById(R.id.okButton);

		okButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String passwd=password.getText().toString();
				if(passwd.equalsIgnoreCase("")){
					Toast.makeText(mContex, getResources().getString(R.string.incorrect_securitycode),Toast.LENGTH_SHORT).show();
					dailog.dismiss();
				}else
					if (cardItem.getLastThreeDigit().equalsIgnoreCase(passwd)) {
						if(mUserLogin.getVc_user_document()!=null){
							if(mUserLogin.getVc_user_document().length()==14){
								purchaseTicket(cardItem);
								dailog.dismiss();
							}else{
								Intent theIntent=new Intent(mContex,UserBillingInfo.class);
								theIntent.putExtra("Navigation","PurchaseTicketDet");
								startActivity(theIntent);
								dailog.dismiss();
							}
						}
				}else{

					Toast.makeText(mContex, getResources().getString(R.string.incorrect_securitycode),Toast.LENGTH_SHORT).show();
					dailog.dismiss();
				}
			}
		});
		dailog.show();
	}

	private void purchaseTicket(final Card cardItem){
		Log.i("",TAG+"Go for purchase ticket..");
		TaskListener mTaskListener=new TaskListener() {

			@Override
			public void updateUI() {
				// TODO Auto-generated method stub

			}

			@Override
			public void execute() {
				// TODO Auto-generated method stub
				ArrayList<String> data=new ArrayList<String>();
				data.add(mUserLogin.getVc_user_code());
				data.add(mEventItem.getVcEventCode());
				mAsignOrder=ContentManager.getInstance().parseAssignOrder(AppUtils.prepareAssignOrderXml(data));
				String xml=preparedOrderDummyHeader();
//				taskAddDummyHeader(xml);
				status=ContentManager.getInstance().parseDummyOrderHeader(PartyFinderConstants.URL_ASSIGN_ORDER_HERADER, xml);
				if(status!=null){
					if(status.equalsIgnoreCase("success")){
						Log.i("",TAG+"Dummy header response : "+status);
						String sagupaoLink="https://ws.pagseguro.uol.com.br/v2/sessions?email="+"carlosbarros@partyfinder.com.br"+"&token="+"A91E2233394F48858E8BBDAD699AD593";

						String sagupaoSession=ContentManager.getInstance().getSagupaoSession(sagupaoLink);
						Log.i("",TAG+"SAGUPAO SESSION : "+sagupaoSession);
						String CardNo=cardItem.getCardNumber();
						String tempCard=CardNo.replaceAll("\\s+","").substring(0,6);
						Log.i("",TAG+" Card Number : "+tempCard);
						String URL_SAGUPAO_CARDETAIL="https://df.uol.com.br/df-fe/mvc/creditcard/v1/getBin?tk="+sagupaoSession.trim()+"&creditCard="+tempCard;
						SagupaoSessionItem sagupaoItem=ContentManager.getInstance().getCardDetail(URL_SAGUPAO_CARDETAIL);
						String mundipaggXml=preparedXmlForpaymentmundipagg(cardItem,sagupaoItem);		
						Log.i("",TAG+"Mundipag Prepare Xml :  "+mundipaggXml);
					}
				}
			}
		};
		if(mAsyncTask!=null){
			mAsyncTask.cancel(true);
			mAsyncTask=null;
		}
		ProgressDialog progDailog=new ProgressDialog(getActivity(),ProgressDialog.THEME_HOLO_DARK);

		progDailog.setMessage("Please Wait" +
				" while your transection is processing.");
//		progDailog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.roundedcorner_for_progress_dialogbox));
//		progDailog.setProgressStyle(progDailog.STYLE_SPINNER);
		mAsyncTask=new CustomAsyncTask(mTaskListener, progDailog, mContex);
		mAsyncTask.execute();
	}

	private String preparedOrderDummyHeader(){
		String tempXml;
		tempXml="<?xml version="+"\"1.0\""+" encoding="+"\"utf-8\""+" ?>"
				+"<Transection>"+
				"<vc_order_code>"+mAsignOrder.getOrderCode()+"</vc_order_code>"+
				"<grossAmount>"+grossTicketTotal+"</grossAmount>"+

				"<vc_event_code>"+mEventItem.getVcEventCode()+"</vc_event_code>"+

					preparedInternalTag()
				+"</Transection>";

		Log.i("","Prepared XML : "+tempXml);



		return tempXml;


	}

	String preparedInternalTag(){

		final String startItem="<items>";
		final String endItem="</items>";
		final String startTypeCode="<vc_type_code>";
		final String endTypeCode="</vc_type_code>";

		final String startQuantity="<quantity>";
		final String endQuantity="</quantity>";

		final String startNoSeat="<vc_no_of_seats>";
		final String endNoSeat="</vc_no_of_seats>";
		final String startAmount="<amount>";
		final String endAmount="</amount>";
		String item="";
		for(int i=0;i<mTicketList.size();i++){
			TicketDetailsItem mTicket=mTicketList.get(i);
			item=item+
				startItem+
					startTypeCode+mTicket.getTicketCode()+endTypeCode
					+startQuantity+mTicket.getTotalCount()+endQuantity
					+startNoSeat+mTicket.getNoOfTicket()+endNoSeat
					+startAmount+mTicket.getTotalTicketPrice()+endAmount
					+endItem;

		}
		return item;
	}

	private void taskAddDummyHeader(final String xml){

		TaskListener mtaskListener=new TaskListener() {

			@Override
			public void updateUI() {
				// TODO Auto-generated method stub
				if(status!=null){
					if(status.equalsIgnoreCase("success")){
						Log.i("",TAG+"Dummy header response : "+status);
					}
				}
			}

			@Override
			public void execute() {
				// TODO Auto-generated method stub
				status=ContentManager.getInstance().parseDummyOrderHeader(PartyFinderConstants.URL_ASSIGN_ORDER_HERADER, xml);
			}
		};
		ProgressDialog progDailog=new ProgressDialog(mContex);
		mAsyncTask=new CustomAsyncTask(mtaskListener, progDailog, mContex);
		mAsyncTask.execute();

	}

	private String preparedXmlForpaymentmundipagg(Card cardItem,SagupaoSessionItem sagPaoItem){

		<CreateSaleRequest>
		<CreditCardTransactionCollection>
			<CreditCardTransaction>
				<AmountInCents>11</AmountInCents>
				<CreditCard>
					<CreditCardBrand>Mastercard</CreditCardBrand>
					<CreditCardNumber>5445400069714925</CreditCardNumber>
					<ExpMonth>12</ExpMonth>
					<ExpYear>2017</ExpYear>
					<HolderName>manish jain</HolderName>
					<SecurityCode>845</SecurityCode>
				</CreditCard>
				<CreditCardOperation>AuthAndCapture</CreditCardOperation>
				<InstallmentCount>1</InstallmentCount>
				<Options>
					<PaymentMethodCode>0</PaymentMethodCode>
					<SoftDescriptorText>PartyFinder</SoftDescriptorText>
				</Options>
			</CreditCardTransaction>
		</CreditCardTransactionCollection>
		<Options>
			<IsAntiFraudEnabled>false</IsAntiFraudEnabled>
			<Retries>3</Retries>
		</Options>
	</CreateSaleRequest>

		grossTicketTotal=grossTicketTotal*100;
		Log.i("",TAG+"Gross Ticket Total : "+grossTicketTotal);
		String tempXml=null;
		tempXml="<?xml version="+"\"1.0\""+" encoding="+"\"utf-8\""+" ?>"
				+"<CreateSaleRequest>"+
					"<CreditCardTransactionCollection>"+
						"<CreditCardTransaction>"+
							"<AmountInCents>"+grossTicketTotal+"</AmountInCents>"+
							"<CreditCard>"+
								"<CreditCardBrand>"+sagPaoItem.getCardBarnd()+"</CreditCardBrand>"+
								"<CreditCardNumber>"+cardItem.getCardNumber().replaceAll("\\s+","")+"</CreditCardNumber>"+
								"<ExpMonth>"+"12"+"</ExpMonth>"+
								"<ExpYear>"+"2017"+"</ExpYear>"+
								"<HolderName>"+cardItem.getCardName()+"</HolderName>"+
								"<SecurityCode>"+cardItem.getLastThreeDigit()+"</SecurityCode>"+
							"</CreditCard>"+
							"<CreditCardOperation>"+"AuthAndCapture"+"</CreditCardOperation>"+	
							"<InstallmentCount>"+"1"+"</InstallmentCount>"+	
							"<Options>"+
								"<PaymentMethodCode>"+"0"+"</PaymentMethodCode>"+
								"<SoftDescriptorText>"+"PartyFinder"+"</SoftDescriptorText>"+
							"</Options>"+
						"</CreditCardTransaction>"+
					"</CreditCardTransactionCollection>"+	
					"<Options>"+
						"<IsAntiFraudEnabled>"+"false"+"</IsAntiFraudEnabled>"+
						"<Retries>"+"3"+"</Retries>"+
					"</Options>"+
				"</CreateSaleRequest>";

		return tempXml;
	}*/


}
