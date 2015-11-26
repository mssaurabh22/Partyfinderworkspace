package com.partyfinder.drawerfragment;

import java.util.ArrayList;
import java.util.Calendar;

import org.w3c.dom.Text;

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
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class AddNewCardFragment extends Fragment {
	private EditText cardUserName, editText, creditCardSec, cardVal,userCpf,userBirthday,userTelephone;
	private Button btn_SaveUserInfo;
	private ImageView backButton;
	private Card carDetail;
	private CardHolder cardHolder;
	private Cards cardList;
	private String cardName, cardNumber, cardCode, cardValidity,mUserDob,mUserCpf,mUserTel;
	private Context mContext;
	private UserLoginItem mLoginItem;
	private String message;
	private String TAG = "AddNewCardActivity class";
	private TextView mcardNoValidationTextView;
	private TextWatcher cpfMask;
	private TextWatcher mCpfMask;

	private RelativeLayout mBackButtonLayout;
	private FragmentManager mFragmentManager;
	public AddNewCardFragment() {

	}
	public AddNewCardFragment(Card cardItem){
		carDetail=cardItem;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mContext = getActivity().getApplicationContext();
		cardList = SettingPrefrences.getCardPrefrence(mContext); 
		mLoginItem = SettingPrefrences.getUserPrefrence(mContext);
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
//		 getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		View view = inflater.inflate(R.layout.card_register_layout, null);
		getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN | WindowManager.LayoutParams.SOFT_INPUT_MASK_ADJUST);
		mFragmentManager=getActivity().getSupportFragmentManager();
		cardUserName = (EditText) view.findViewById(R.id.CardUserName);
		userBirthday= (EditText) view.findViewById(R.id.UserDateOfBirth);
		userCpf=(EditText) view.findViewById(R.id.UserCPFno);
		userTelephone=(EditText) view.findViewById(R.id.UserTelephoneNo);
		mCpfMask= Mask.insert("##-##########", userTelephone);
		userTelephone.addTextChangedListener(mCpfMask);
		editText = (EditText) view.findViewById(R.id.UserCreditCard);
		mBackButtonLayout=(RelativeLayout) view.findViewById(R.id.backButton_layout);
		backButton=(ImageView) view.findViewById(R.id.img_billingInfo_bck);
		creditCardSec=(EditText) view.findViewById(R.id.creditCardSec);
		cardVal=(EditText) view.findViewById(R.id.cardVal);
		
		 cpfMask = Mask.insert("###.###.###-##", userCpf);
		 userCpf.addTextChangedListener(cpfMask);
		 userBirthday.addTextChangedListener(twDob);
		
		if(carDetail!=null){

			cardUserName.setText(carDetail.getCardName());
			editText.setText(carDetail.getCardNumber());
			creditCardSec.setText(carDetail.getLastThreeDigit());
			cardVal.setText(carDetail.getCardVal());
			userBirthday.setText(carDetail.getBirthDate());
			userCpf.setText(carDetail.getCpfNo());
			userTelephone.setText(carDetail.getTelephone());
		}

		backButton.setOnClickListener(new OnClickListener() {

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

		editText.addTextChangedListener(new TextWatcher() {
			private boolean spaceDeleted;
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// check if a space was deleted
				CharSequence charDeleted = s.subSequence(start, start + count);
				spaceDeleted = " ".equals(charDeleted.toString());
			}

			public void afterTextChanged(Editable editable) {
				editText.removeTextChangedListener(this);
				int cursorPosition = editText.getSelectionStart();
				String withSpaces = formatText(editable);
				editText.setText(withSpaces);
				editText.setSelection(cursorPosition + (withSpaces.length() - editable.length()));
				if (spaceDeleted) {
					editText.setSelection(editText.getSelectionStart() - 1);
					spaceDeleted = false;
				}
				editText.addTextChangedListener(this);
			}

			private String formatText(CharSequence text)
			{
				StringBuilder formatted = new StringBuilder();
				int count = 0;
				for (int i = 0; i < text.length(); ++i)
				{
					if (Character.isDigit(text.charAt(i)))
					{
						if (count % 4 == 0 && count > 0)
							formatted.append(" ");
						formatted.append(text.charAt(i));
						++count;
					}
				}
				return formatted.toString();
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
				}else if(cardNumber.equalsIgnoreCase("")){
					CustomDialogBox.showAlert(getResources().getString(R.string.infoDialo),getResources().getString(R.string.creadit_card_empty_msg), getActivity());
				}else if(!CreaditCardValidator.isValid(Long.parseLong(cardNumber.replaceAll("\\s+","")))){
					CustomDialogBox.showAlert(getResources().getString(R.string.infoDialo), getResources().getString(R.string.invalid_card), getActivity());
				}else if(cardValidity.equalsIgnoreCase("")){
					CustomDialogBox.showAlert(getResources().getString(R.string.infoDialo),getResources().getString(R.string.card_expiry_msg), getActivity());
				}else if(cardCode.equalsIgnoreCase("")){
					CustomDialogBox.showAlert(getResources().getString(R.string.infoDialo),getResources().getString(R.string.empty_security_code_msg), getActivity());
				}else if(mUserCpf.equalsIgnoreCase("")){
					CustomDialogBox.showAlert(getResources().getString(R.string.infoDialo),"Por favor, insira o número de CPF.", getActivity());
				}else if(mUserDob.equalsIgnoreCase("")){
						CustomDialogBox.showAlert(getResources().getString(R.string.infoDialo),"Por favor, indique o aniversário.", getActivity());
				}else if(mUserTel.equalsIgnoreCase("")){
							CustomDialogBox.showAlert(getResources().getString(R.string.infoDialo),"Por favor, indique o número de telefone.", getActivity());
							
				}else{
					addCardHolderInList();
					mFragmentManager.popBackStack();
				}
			}
		});
		return view;
	}

	public static class FourDigitCardFormatWatcher implements TextWatcher {
		// Change this to what you want... ' ', '-' etc..
		private static final char space = ' ';
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
		}
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		}
		@Override
		public void afterTextChanged(Editable s) {
			if (s.length() > 0 && (s.length() % 5) == 0) {
				final char c = s.charAt(s.length() - 1);
				if (space == c) {
					s.delete(s.length() - 1, s.length());
				}
			}
			if (s.length() > 0 && (s.length() % 5) == 0) {
				char c = s.charAt(s.length() - 1);
				if (Character.isDigit(c) && TextUtils.split(s.toString(), String.valueOf(space)).length <= 3) {
					s.insert(s.length() - 1, String.valueOf(space));
				}
			}
		}
	}

	private void addCardHolderInList(){
		boolean isCardExist = false;
		boolean isCardHolderExist=false;
		int index=0;
		if(cardList!=null){
			ArrayList<CardHolder> cardHolderList=cardList.getCardHolderList();
			if(cardHolderList.size()>0){
				for(int k=0;k<cardHolderList.size();k++){
					if(cardHolderList.get(k).getUserCode().equalsIgnoreCase(mLoginItem.getVc_user_code())){
						isCardHolderExist=true;
						index=k;
						break;
					}	
				}

				if(isCardHolderExist){
					cardHolder=cardHolderList.get(index);
					ArrayList<Card> cardDetailList=cardHolder.getCardList();
					if(cardDetailList.size()>0){
						for(int j=0;j<cardDetailList.size();j++){
							if(cardDetailList.get(j).getCardNumber().equalsIgnoreCase(cardNumber)){
								isCardExist=true;
								break;
							}
						}
						if(isCardExist){
							message=getResources().getString(R.string.card_alreadyAdded);
							CustomDialogBox.showAlert(getResources().getString(R.string.infoDialo),message, getActivity());
						}else{
							cardHolder.getCardList().add(addCard());
							saveCardToExistingCardHolder(cardList);
						}
					}else{
						cardHolder.getCardList().add(addCard());
						saveCardToExistingCardHolder(cardList);
					}
				}else{
					cardList.getCardHolderList().add(addCardInCardHolder());
					saveCardToExistingCardHolder(cardList);
				}
			}	
		}else{
			saveCardIntoSharedPref();
		}
	}

	private void saveCardIntoSharedPref(){
		cardList=new Cards();
		cardList.getCardHolderList().add(addCardInCardHolder());
		SettingPrefrences.saveCardPrefrence(cardList, mContext);
		CustomDialogBox.showAlert(getResources().getString(R.string.infoDialo),getResources().getString(R.string.card_added), getActivity());
	}
	private void saveCardToExistingCardHolder(Cards cardsItem){
		SettingPrefrences.saveCardPrefrence(cardsItem, mContext);
		CustomDialogBox.showAlert(getResources().getString(R.string.infoDialo),getResources().getString(R.string.card_added), getActivity());
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
		carDetail.setNewAdded(false);
		return carDetail;
	}

	TextWatcher tw = new TextWatcher() {
		private String current = "";
		private String mmyyyy = "MMYY";
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
					clean = clean + mmyyyy.substring(clean.length());
				}else{
					int mon  = Integer.parseInt(clean.substring(0,2));
					int year = Integer.parseInt(clean.substring(2,4));
					if(mon > 12) mon = 12;
					cal.set(Calendar.MONTH, mon-1);
					cal.set(Calendar.YEAR, year); 
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
	
	TextWatcher twDob = new TextWatcher() {
		private String current = "";
		private String ddmmyyyy = "DDMMYYYY";
		private Calendar cal = Calendar.getInstance();


		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {

			if (!s.toString().equals(current)) {
				String clean = s.toString().replaceAll("[^\\d.]", "");
				String cleanC = current.replaceAll("[^\\d.]", "");

				int cl = clean.length();
				int sel = cl;
				for (int i = 2; i <= cl && i < 6; i += 2) {
					sel++;
				}
				//Fix for pressing delete next to a forward slash
				if (clean.equals(cleanC)) sel--;

				if (clean.length() < 8){

					clean = clean + ddmmyyyy.substring(clean.length());
				}else{
					//This part makes sure that when we finish entering numbers
					//the date is correct, fixing it otherwise
					int day  = Integer.parseInt(clean.substring(0,2));
					int mon  = Integer.parseInt(clean.substring(2,4));
					int year = Integer.parseInt(clean.substring(4,8));

					if(mon > 12) mon = 12;
					cal.set(Calendar.MONTH, mon-1);
					year = (year<1900)?1900:(year>2100)?2100:year;
					cal.set(Calendar.YEAR, year); 
					// ^ first set year for the line below to work correctly
					//with leap years - otherwise, date e.g. 29/02/2012
					//would be automatically corrected to 28/02/2012 

					day = (day > cal.getActualMaximum(Calendar.DATE))? cal.getActualMaximum(Calendar.DATE):day;
					clean = String.format("%02d%02d%02d",day, mon, year);
				}

				clean = String.format("%s/%s/%s", clean.substring(0, 2),
						clean.substring(2, 4),
						clean.substring(4, 8));

				sel = sel < 0 ? 0 : sel;
				current = clean;
				userBirthday.setText(current);
				userBirthday.setSelection(sel < current.length() ? sel : current.length());
			}
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub

		}

	};

}
