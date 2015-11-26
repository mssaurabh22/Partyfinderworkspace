package com.partyfinder.drawerfragment;

import java.util.ArrayList;
import java.util.Calendar;

import com.partyfinder.R;
import com.partyfinder.CustomView.CustomDialogBox;
import com.partyfinder.app.utils.CreaditCardValidator;
import com.partyfinder.app.utils.Mask;
import com.partyfinder.managers.SettingPrefrences;
import com.partyfinder.model.Card;
import com.partyfinder.model.CardHolder;
import com.partyfinder.model.Cards;
import com.partyfinder.model.UserLoginItem;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class UpdateAddNewCardFragment extends Fragment{
	
	private EditText cardUserName, editText, creditCardSec, cardVal,userCpf,userBirthday,userTelephone;
	private Button btn_SaveUserInfo;
	private ImageView backButton;
	private CardHolder mCardHolder;
	private Card carDetail;
	private CardHolder cardHolder;
	private Cards cardList;
	private String cardName, cardNumber, cardCode, cardValidity,mUserDob,mUserCpf,mUserTel;
	private Context mContext;
	private UserLoginItem mLoginItem;
	private String message;
	private String TAG = "AddNewCardActivity class";
	int keyDel;
	String a;
	String a0;
	int isAppent = 0;
	final String ch = " ";
	int cardPos;
	private TextWatcher mCpfMask;

	private FragmentManager mFragmentManager;
	private int cardHolderIndex,cardIndex;
	private ArrayList<CardHolder> mCardHolderList;
	private TextWatcher cpfMask;
	
	public UpdateAddNewCardFragment() {

	}
	public UpdateAddNewCardFragment(ArrayList<CardHolder> cardHolderList,int cardHolderPos,int cardPos){
		mCardHolder=cardHolderList.get(cardHolderPos);
		carDetail=mCardHolder.getCardList().get(cardPos);
		mCardHolderList=cardHolderList;
		
//		cardPos=position;
		cardHolderIndex=cardHolderPos;
		cardIndex=cardPos;
//		carDetail=mCardHolder.getCardList().get(position);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mContext = getActivity().getApplicationContext();
		mFragmentManager=getActivity().getSupportFragmentManager();
		cardList = SettingPrefrences.getCardPrefrence(mContext);
		mLoginItem = SettingPrefrences.getUserPrefrence(mContext);
		
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		View view = inflater.inflate(R.layout.card_register_layout, null);
		getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
		cardUserName = (EditText) view.findViewById(R.id.CardUserName);
		editText = (EditText) view.findViewById(R.id.UserCreditCard);
		backButton=(ImageView) view.findViewById(R.id.img_billingInfo_bck);
		userBirthday= (EditText) view.findViewById(R.id.UserDateOfBirth);
		userCpf=(EditText) view.findViewById(R.id.UserCPFno);
		cpfMask = Mask.insert("###.###.###-##", userCpf);
		 userCpf.addTextChangedListener(cpfMask);
		userTelephone=(EditText) view.findViewById(R.id.UserTelephoneNo);
		mCpfMask= Mask.insert("##-##########", userTelephone);
		userTelephone.addTextChangedListener(mCpfMask);
		creditCardSec=(EditText) view.findViewById(R.id.creditCardSec);
		cardVal=(EditText) view.findViewById(R.id.cardVal);
		
		if(carDetail!=null){
			
			cardUserName.setText(carDetail.getCardName());
			editText.setText(carDetail.getCardNumber());
			creditCardSec.setText(carDetail.getLastThreeDigit());
			cardVal.setText(carDetail.getCardVal());
			userBirthday.setText(carDetail.getBirthDate());
			userCpf.setText(carDetail.getCpfNo());
			userTelephone.setText(carDetail.getTelephone());
		
		backButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				mFragmentTransect=mFragmentManager.beginTransaction();
//				mFragmentTransect=mFragmentManager.beginTransaction();
				mFragmentManager.popBackStack();
			}
		});
		
		editText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

				boolean flag = true;
				if (s.length() > 19) {
					editText.setText(a0);
					editText.setSelection(editText.getText().length());
					return;
				}
				String eachBlock[] = s.toString().split(ch);
				for (int i = 0; i < eachBlock.length; i++) {
					if (eachBlock[i].length() > 4) {
						flag = false;
					}
				}
				if (a0.length() > s.toString().length()) {
					keyDel = 1;
				}
				if (flag) {
					if (keyDel == 0) {

						if (((editText.getText().length() + 1) % 5) == 0) {

							if (s.toString().split(ch).length <= 3) {
								isAppent = 1;
								editText.setText(s + ch);
								isAppent = 0;
								editText.setSelection(editText.getText()
										.length());
								a = editText.getText().toString();
								return;
							}
						}
						if (isAppent == 0) {
							String str = s.toString();
							if (str.lastIndexOf(ch) == str.length() - 1) {
								str = str.substring(0, str.lastIndexOf(ch));
								keyDel = 1;
								editText.setText(str);
								keyDel = 0;
								editText.setSelection(editText.getText()
										.length());
								a = editText.getText().toString();
								return;
							}
						}

					} else {
						String str = s.toString();
						if (str.length() > 0
								&& str.lastIndexOf(ch) == str.length() - 1) {
							str = str.substring(0, str.lastIndexOf(ch));
							keyDel = 1;
							editText.setText(str);
							keyDel = 0;
							editText.setSelection(editText.getText().length());
							a = editText.getText().toString();
							return;
						} else {
							a = editText.getText().toString();
							keyDel = 0;
						}
					}

				} else {
					String str = s.toString();
					str = str.substring(0, str.length() - 1) + ch
							+ str.substring(str.length() - 1, str.length());

					a = str;
					editText.setText(a);
					editText.setSelection(editText.getText().length());
				}

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				a0 = s.toString();
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});
		
	
		cardVal.addTextChangedListener(tw);
		
		btn_SaveUserInfo=(Button)view. findViewById(R.id.btn_SaveUserInfo);
		btn_SaveUserInfo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				cardName=cardUserName.getText().toString();
				cardNumber=editText.getText().toString();
				cardCode=creditCardSec.getText().toString();
				cardValidity=cardVal.getText().toString();
				mUserCpf=userCpf.getText().toString();
				mUserDob=userBirthday.getText().toString();
				mUserTel=userTelephone.getText().toString();
				
				if(cardName.equalsIgnoreCase("")){
					CustomDialogBox.showAlert(getResources().getString(R.string.infoDialo),getResources().getString(R.string.creadit_cardname_empty_msg), getActivity());
//					Toast.makeText(mContext, getResources().getString(R.string.creadit_cardname_empty_msg), Toast.LENGTH_SHORT).show();
					
				}else if(cardNumber.equalsIgnoreCase("")){
					CustomDialogBox.showAlert(getResources().getString(R.string.infoDialo),getResources().getString(R.string.creadit_card_empty_msg), getActivity());
//					Toast.makeText(mContext, getResources().getString(R.string.creadit_card_empty_msg), Toast.LENGTH_SHORT).show();
				}else if(!CreaditCardValidator.isValid(Long.parseLong(cardNumber.replaceAll("\\s+","")))){
//					Toast.makeText(mContext, getResources().getString(R.string.invalid_card), Toast.LENGTH_SHORT).show();
					CustomDialogBox.showAlert(getResources().getString(R.string.infoDialo),getResources().getString(R.string.invalid_card), getActivity());
				}else if(cardValidity.equalsIgnoreCase("")){
					CustomDialogBox.showAlert(getResources().getString(R.string.infoDialo),getResources().getString(R.string.card_expiry_msg), getActivity());
//					Toast.makeText(mContext, getResources().getString(R.string.card_expiry_msg), Toast.LENGTH_SHORT).show();
				}else if(cardCode.equalsIgnoreCase("")){
					CustomDialogBox.showAlert(getResources().getString(R.string.infoDialo),getResources().getString(R.string.empty_security_code_msg), getActivity());
//					Toast.makeText(mContext, getResources().getString(R.string.empty_security_code_msg), Toast.LENGTH_SHORT).show();
				}else if(mUserCpf.equalsIgnoreCase("")){
					CustomDialogBox.showAlert(getResources().getString(R.string.infoDialo),"Por favor, insira o número de CPF.", getActivity());
				}else if(mUserDob.equalsIgnoreCase("")){
						CustomDialogBox.showAlert(getResources().getString(R.string.infoDialo),"Por favor, indique o aniversário.", getActivity());
				}else if(mUserTel.equalsIgnoreCase("")){
							CustomDialogBox.showAlert(getResources().getString(R.string.infoDialo),"Por favor, indique o número de telefone.", getActivity());
			}else{
//					addCardHolderInList();
					updateCard();
					userTransectionStatusDialogBox();
//					CustomDialogBox.showAlert(getResources().getString(R.string.infoDialo),getResources().getString(R.string.card_added), getActivity());
//					Toast.makeText(mContext, getResources().getString(R.string.card_added), Toast.LENGTH_SHORT).show();
				}
			
				
				
			}
		});
		}
		return view;
		
	}
	
	private void updateCard(){
		
		carDetail.setCardName(cardName);
		carDetail.setCardNumber(cardNumber);
		carDetail.setCardVal(cardValidity);
		carDetail.setLastThreeDigit(cardCode);
		carDetail.setBirthDate(mUserDob);
		carDetail.setCpfNo(mUserCpf);
		carDetail.setTelephone(mUserTel);
		mCardHolderList.get(cardHolderIndex).getCardList().set(cardIndex, carDetail);//add(cardIndex, carDetail);
		Cards cardList=SettingPrefrences.getCardPrefrence(mContext);
		cardList.setCardHolderList(mCardHolderList);
		SettingPrefrences.saveCardPrefrence(cardList, mContext);
		
		
	}
	
	private void addCardHolderInList(){
		boolean isCardExist = false;
		boolean isCardHolderExist=false;
		int index=0;
		if(cardList!=null){
			ArrayList<CardHolder> cardHolderList=cardList.getCardHolderList();
			Log.i("",TAG+"here 1");
			if(cardHolderList.size()>0){
				Log.i("",TAG+"here 2");
				for(int k=0;k<cardHolderList.size();k++){
					if(cardHolderList.get(k).getUserCode().equalsIgnoreCase(mLoginItem.getVc_user_code())){
						isCardHolderExist=true;
						index=k;
						break;
					}	
				}
				
				if(isCardHolderExist){
					
					cardHolder=cardHolderList.get(index);
					Log.i("",TAG+"Existed cardholder: "+cardHolder.getUserCode());
					ArrayList<Card> cardDetailList=cardHolder.getCardList();
					if(cardDetailList.size()>0){
						Log.i("",TAG+"Existed card Size: "+cardDetailList.size());
						for(int j=0;j<cardDetailList.size();j++){
							Log.i("",TAG+"Exixting Card Number : "+cardDetailList.get(j).getCardNumber()+" Inputed card Number : "+cardNumber);
							if(cardDetailList.get(j).getCardNumber().equalsIgnoreCase(cardNumber)){
						
								isCardExist=true;
								break;
							}
						}
						
						if(isCardExist){
							message=getResources().getString(R.string.card_alreadyAdded);
							Log.i("",TAG+"here 3");
							Log.i("", TAG+message);
							Toast.makeText(mContext, message,Toast.LENGTH_SHORT).show();
						}else{
						  /*carDetail=new Card();
							carDetail.setCardNumber(cardNumber);
							carDetail.setCardName(cardName);
							carDetail.setLastThreeDigit(cardCode);
							carDetail.setCardVal(cardValidity);
							cardHolder.getCardList().add(carDetail);*/
							cardHolder.getCardList().add(addCard());
							Log.i("",TAG+"here 4");
							Log.i("",TAG+"CardList Size after added new one : "+cardList.getCardHolderList().size());
							saveCardToExistingCardHolder(cardList);
							
						}
					}
					
					
					
				}else{
					cardList.getCardHolderList().add(addCardInCardHolder());
					
					saveCardToExistingCardHolder(cardList);
					
				}
				
				
				
			
			}	
		}else{
			Log.i("",TAG+"here 3");
			saveCardIntoSharedPref();
			Log.i("",TAG+"CardList Size for initial user : "+cardList.getCardHolderList().size());
		}
		
		
	}
	
	private void saveCardIntoSharedPref(){
		cardList=new Cards();
		cardList.getCardHolderList().add(addCardInCardHolder());
		SettingPrefrences.saveCardPrefrence(cardList, mContext);
//		userTransectionStatusDialogBox();
		CustomDialogBox.showAlert(getResources().getString(R.string.infoDialo),getResources().getString(R.string.card_added), getActivity());
//		Toast.makeText(mContext,getResources().getString(R.string.card_added),Toast.LENGTH_SHORT).show();
	}
	private void saveCardToExistingCardHolder(Cards cardsItem){
		SettingPrefrences.saveCardPrefrence(cardsItem, mContext);
		userTransectionStatusDialogBox();
		CustomDialogBox.showAlert(getResources().getString(R.string.infoDialo),getResources().getString(R.string.card_added), getActivity());
//		Toast.makeText(mContext, getResources().getString(R.string.card_added),Toast.LENGTH_SHORT).show();
	}
	
	private CardHolder addCardInCardHolder(){
		cardHolder=new CardHolder();
		cardHolder.setUserCode(mLoginItem.getVc_user_code());
		cardHolder.getCardList().add(addCard());
		return cardHolder;
	}
	private Card addCard(){
		carDetail=new Card();
		carDetail.setCardNumber(cardNumber);
		carDetail.setCardName(cardName);
		carDetail.setLastThreeDigit(cardCode);
		carDetail.setCardVal(cardValidity);
		carDetail.setBirthDate(mUserDob);
		carDetail.setCpfNo(mUserCpf);
		carDetail.setTelephone(mUserTel);
//		mCardHOlder.getCardList().add(carDetail);
		return carDetail;
	}
	
	TextWatcher tw = new TextWatcher() {
		 private String current = "";
		 private String mmyy = "MMYY";
		 private Calendar cal = Calendar.getInstance();


		 @Override
		 public void onTextChanged(CharSequence s, int start, int before, int count) {
		  
		  if (!s.toString().equals(current)) {
		   String clean = s.toString().replaceAll("[^\\d.]", "");
		   String cleanC = current.replaceAll("[^\\d.]", "");

		   int cl = clean.length();
		   int sel = cl;
		   for (int i = 2; i <= cl && i < 4; i += 2) {
		    sel++;
		   }
		   //Fix for pressing delete next to a forward slash
		   if (clean.equals(cleanC)) sel--;

		   if (clean.length() < 4){

		    clean = clean + mmyy.substring(clean.length());
		   }else{
		    //This part makes sure that when we finish entering numbers
		    //the date is correct, fixing it otherwise
//		    int day  = Integer.parseInt(clean.substring(0,2));
		    int mon  = Integer.parseInt(clean.substring(0,2));
		    int year = Integer.parseInt(clean.substring(2,4));

		    if(mon > 12) mon = 12;
		    cal.set(Calendar.MONTH, mon-1);
//		    year = (year<00)?1:(year>99)?99:year;
		    cal.set(Calendar.YEAR, year); 
		    // ^ first set year for the line below to work correctly
		    //with leap years - otherwise, date e.g. 29/02/2012
		    //would be automatically corrected to 28/02/2012 

//		    day = (day > cal.getActualMaximum(Calendar.DATE))? cal.getActualMaximum(Calendar.DATE):day;
		    clean = String.format("%02d%02d", mon, year);
		   }

		   clean = String.format("%s/%s", 
		     clean.substring(0, 2),
		     clean.substring(2, 4));

		   sel = sel < 0 ? 0 : sel;
		   current = clean;
		   cardVal.setText(current);
		   cardVal.setSelection(sel < current.length() ? sel : current.length());
		  }
		 }

		 @Override
		 public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

		 @Override
		 public void afterTextChanged(Editable s) {
		  // TODO Auto-generated method stub

		 }

		};
		
		public void userTransectionStatusDialogBox(){				
			final Dialog userTransectionStatus=new Dialog(getActivity());
			userTransectionStatus.setContentView(R.layout.order_rejection_pending_alert_popup);
			userTransectionStatus.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
			Button transectionStatusButton =(Button) userTransectionStatus.findViewById(R.id.orderTransectionStatusButton);
			userTransectionStatus.setCancelable(false);
			userTransectionStatus.show();
			transectionStatusButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					userTransectionStatus.dismiss();
					mFragmentManager.popBackStack();
				}
			});

		}
		


}
