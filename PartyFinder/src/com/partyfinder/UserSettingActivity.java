package com.partyfinder;

import java.util.ArrayList;

import com.partyfinder.AppConstant.PartyFinderConstants;
import com.partyfinder.CustomView.CustomDialog;
import com.partyfinder.CustomView.CustomDialogBox;
import com.partyfinder.adapters.CardAdapter;
import com.partyfinder.drawerfragment.ExistingUserCreditCard;
import com.partyfinder.drawerfragment.UpdateAddNewCardFragment;
import com.partyfinder.managers.SettingPrefrences;
import com.partyfinder.model.CardHolder;
import com.partyfinder.model.Cards;
import com.partyfinder.model.UserLoginItem;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class UserSettingActivity  extends FragmentActivity{
	private final String TAG="UserSettingActivity";
	private RelativeLayout btnBillingInfo;
	private RelativeLayout btnPrivacyPolicy;
	private RelativeLayout btnAccountPolicy;
	private RelativeLayout mBack;
	private ImageView btnSettingBack;
	private Context mContext;
	private UserLoginItem mUserLogin;
	private TextView mtbillinginfo,mtvPr,mdiv1;
	private ImageView mbtnbillinginfo,mprivacypolicy,maccountPrivacy;
	private CardHolder tempCardHolder;
	private FragmentManager mFragmentManager;
	private FragmentTransaction mFragmentTransect;
	private Fragment mFragment;
	private int cardHolderIndex,cardIndex;
	private ArrayList<CardHolder> mCardHolderList;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_setting_layout);
		mContext=this;
		mUserLogin=			SettingPrefrences.getUserPrefrence(mContext);
		mFragmentManager=	getSupportFragmentManager();

		mtbillinginfo=		(TextView) findViewById(R.id.tbillinginfo);
		mtvPr=				(TextView) findViewById(R.id.tvPr);
		mdiv1=				(TextView) findViewById(R.id.div1);
		mBack=				(RelativeLayout) findViewById(R.id.backButton_layout);

		mbtnbillinginfo=	(ImageView) findViewById(R.id.btnbillinginfo);
		mprivacypolicy=		(ImageView) findViewById(R.id.privacypolicy);
		maccountPrivacy=	(ImageView) findViewById(R.id.accountPrivacy);



		btnBillingInfo=		(RelativeLayout)findViewById(R.id.btnBillingInfo);
		btnPrivacyPolicy=	(RelativeLayout)findViewById(R.id.btnPrivacyPolicy);
		btnAccountPolicy=	(RelativeLayout)findViewById(R.id.btnAccountPolicy);
		btnSettingBack=		(ImageView)findViewById(R.id.btn_settingBck);

		btnBillingInfo.		setOnClickListener(mOnClickListener);
		btnPrivacyPolicy.	setOnClickListener(mOnClickListener);
		btnAccountPolicy.	setOnClickListener(mOnClickListener);
		btnSettingBack.		setOnClickListener(mOnClickListener);
		mBack.		   		setOnClickListener(mOnClickListener);
	}

	OnClickListener mOnClickListener =new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.btn_settingBck:
				finish();
				break;
			case R.id.backButton_layout:
				finish();
				break;
			case R.id.btnBillingInfo:
				Intent in=new Intent(mContext,UserBillingInfo.class);
				startActivity(in);
				break;
			case R.id.btnPrivacyPolicy:
				Intent pp = new Intent(mContext,PrivacyPolicy.class);
				startActivity(pp);
	
				break;
			case R.id.btnAccountPolicy:
				Cards cardsList=SettingPrefrences.getCardPrefrence(mContext);
				if(cardsList!=null){
					mCardHolderList=cardsList.getCardHolderList();
						if(cardsList.getCardHolderList().size()>0){
						for(int i=0;i<cardsList.getCardHolderList().size();i++){
							if(cardsList.getCardHolderList().get(i).getUserCode().equalsIgnoreCase(mUserLogin.getVc_user_code())){
								tempCardHolder=cardsList.getCardHolderList().get(i);
								cardHolderIndex=i;
								break;
							}
						}
					}
				}
			
				mFragmentTransect=mFragmentManager.beginTransaction();
				if(mFragment!=null){
					mFragmentTransect.detach(mFragment);
					mFragment=null;
				}
				mFragment=new ExistingUserCreditCard();
				mFragmentTransect.replace(R.id.fragmentContainer, mFragment);
				mFragmentTransect.addToBackStack(null);
				mFragmentTransect.commitAllowingStateLoss();


				break;
			}
		}
	};
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Intent theIntent = new Intent(mContext,MyProfileActivity.class);
		UserLoginItem mUserLoginItem=SettingPrefrences.getUserPrefrence(mContext);
		theIntent.putExtra(PartyFinderConstants.EXTRA_GET_FRIENDS_ITEM, mUserLoginItem);
		finish();
		super.onBackPressed();
	}	

	@SuppressWarnings("unused")
	private void showDialog(){
		final CustomDialog dailog=new CustomDialog(mContext, R.layout.existingcard_popupwindow);
		ListView existingCard=(ListView) dailog.findViewById(R.id.exisitingCardList);
		CardAdapter cardAdapter=new CardAdapter(mContext, tempCardHolder.getCardList(),this);
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
				askSecurityPopup(tempCardHolder,position);
				cardIndex=position;
				Log.i("",TAG+"Selected Card Item : "+position);
				dailog.dismiss();

			}
		});

		dailog.show();

	}

	private void askSecurityPopup(final CardHolder mCradHolder,final int position){
		final CustomDialog dailog=new CustomDialog(mContext, R.layout.existingcard_security_popupwindow);
		final EditText password=(EditText) dailog.findViewById(R.id.securityEd);
		TextView okButton=(TextView) dailog.findViewById(R.id.okButton);

		okButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String passwd=password.getText().toString();
				if(passwd.equalsIgnoreCase("")){
					CustomDialogBox.showAlert(getResources().getString(R.string.infoDialo),getResources().getString(R.string.incorrect_securitycode), mContext);
					//					Toast.makeText(mContext, getResources().getString(R.string.incorrect_securitycode),Toast.LENGTH_SHORT).show();
					dailog.dismiss();
				}else
					if (mCradHolder.getCardList().get(position).getLastThreeDigit().equalsIgnoreCase(passwd)) {

						mFragmentTransect=mFragmentManager.beginTransaction();
						if(mFragment!=null){
							mFragmentTransect.detach(mFragment);
							mFragment=null;
						}
						mFragment=new UpdateAddNewCardFragment(mCardHolderList,cardHolderIndex,cardIndex);
						mFragmentTransect.replace(R.id.fragmentContainer, mFragment);
						mFragmentTransect.addToBackStack(null);
						mFragmentTransect.commitAllowingStateLoss();
						dailog.dismiss();

					}else{
						CustomDialogBox.showAlert(getResources().getString(R.string.infoDialo),getResources().getString(R.string.incorrect_securitycode), mContext);
						//						Toast.makeText(mContext, getResources().getString(R.string.incorrect_securitycode),Toast.LENGTH_SHORT).show();
						dailog.dismiss();
					}
			}
		});
		dailog.show();
	}


}
