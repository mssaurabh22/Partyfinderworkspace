package com.partyfinder.drawerfragment;

import java.util.ArrayList;
import java.util.Calendar;


import com.partyfinder.R;
import com.partyfinder.CustomView.CustomAsyncTask;
import com.partyfinder.CustomView.CustomDialogBox;
import com.partyfinder.CustomView.TaskListener;
import com.partyfinder.app.utils.AppUtils;
import com.partyfinder.app.utils.Mask;

import com.partyfinder.managers.ContentManager;
import com.partyfinder.managers.SettingPrefrences;
import com.partyfinder.model.EditUserInfoItem;
import com.partyfinder.model.PostalCodeModel;

import com.partyfinder.model.UserLoginItem;


import android.content.Context;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


public class UserBillingInfoFragment extends Fragment {

	private EditText editTextUserName;
	private EditText editTextUserDOB;
	private EditText editTextUserDddress;
	private EditText editTextUserCoutry;
	private EditText editTextUserCity;
	private EditText editTextUserState;
	private EditText editTextUserCPF;
	private Button buttonSaveUserInfo;
	private ImageView buttonback;
	private ProgressBar progressBar;
	private UserLoginItem userLoginItem;
	private UserLoginItem mUserLoginItem;
	private boolean isInternetPresent = false;
	private Context mContext;
	private CustomAsyncTask mAsyncTask;
	private String UserId;
	private String UserPassword;
	public String userCPF;
	public static String TAG = "UserBillingInfoActivityClass ";
	private EditUserInfoItem editUserInfoItem=null;
	private FragmentManager mFragmentManager;
	private FragmentTransaction mFragmentTransect;
	private Fragment mFragment;
	private TextWatcher cpfMask;

	private EditText mPostalCode,met_userNumber,met_userBairro,mUserEmail;
	private TextWatcher mcpfMask;
	private EditText editTextUserLogradouro;
	private EditText editTextUseret_userBairro;
	private PostalCodeModel mPostalModel;
	private String UserPostaCode;
	private TextView mpressOkBtn;


	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		mFragmentManager=getActivity().getSupportFragmentManager();
		mContext=getActivity().getApplicationContext();
		mUserLoginItem=SettingPrefrences.getUserPrefrence(mContext);
		UserId=mUserLoginItem.getVc_user_email();
		UserPassword=mUserLoginItem.getVc_user_pwd().toString().trim();
	}




	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub


		View view=inflater.inflate(R.layout.user_billing_info_layout, null);

		mUserEmail=(EditText) view.findViewById(R.id.UserEmail);
		mpressOkBtn= (TextView) view.findViewById(R.id.pressOkBtn);
		mPostalCode=(EditText) view.findViewById(R.id.PostalCode);
		mcpfMask = Mask.insert("#####-###", mPostalCode);
		mPostalCode.addTextChangedListener(mcpfMask);
		met_userNumber=(EditText) view.findViewById(R.id.et_userNumber);
		editTextUserLogradouro=(EditText)view.findViewById(R.id.et_userAddress);
		editTextUseret_userBairro=(EditText)view.findViewById(R.id.et_userBairro);

		editTextUserDOB=(EditText)view.findViewById(R.id.et_userDOB);
		editTextUserDOB.addTextChangedListener(tw);  



		editTextUserName=(EditText)view.findViewById(R.id.et_userFullName);
		editTextUserDOB=(EditText)view.findViewById(R.id.et_userDOB);
		editTextUserDOB.addTextChangedListener(tw);  
		editTextUserDddress=(EditText)view.findViewById(R.id.et_userAddress);
		//		editTextUserCoutry=(EditText)view.findViewById(R.id.et_userCountry);
		editTextUserCity=(EditText)view.findViewById(R.id.et_userCity);
		editTextUserState=(EditText)view.findViewById(R.id.et_userState);
		editTextUserCPF=(EditText) view.findViewById(R.id.et_userCPF);
		cpfMask = Mask.insert("###.###.###-##", editTextUserCPF);
		editTextUserCPF.addTextChangedListener(cpfMask);
		buttonback=(ImageView)view.findViewById(R.id.img_billingInfo_bck);
		buttonSaveUserInfo=(Button)view.findViewById(R.id.btn_SaveUserInfo);
		progressBar=(ProgressBar)view.findViewById(R.id.proBarEditUser);
		progressBar.bringToFront();


		try {
	
				
				if(mUserLoginItem.getVc_user_email().contains("@facebook.com")){
					mUserEmail.setKeyListener(null); 
					mUserEmail.setText("");
					mUserEmail.setEnabled(true);
					mUserEmail.setFocusable(true);
					mUserEmail.setClickable(true);
				}else{
					mUserEmail.setText(mUserLoginItem.getVc_user_email());
					mUserEmail.setEnabled(false);
					mUserEmail.setFocusable(false);
					mUserEmail.setClickable(false);
				}
				
				mUserEmail.setHintTextColor(getResources().getColor(R.color.Signup_hint_color));
				mUserEmail.addTextChangedListener(new TextWatcher() {

					@Override
					public void onTextChanged(CharSequence s, int start, int before, int count) {
						// TODO Auto-generated method stub
						mUserEmail.setTypeface(null, Typeface.BOLD);
					}

					@Override
					public void beforeTextChanged(CharSequence s, int start, int count,
							int after) {
						// TODO Auto-generated method stub

					}

					@Override
					public void afterTextChanged(Editable s) {
						// TODO Auto-generated method stub

					}
				});
				
			
			
			editTextUserName.setText(mUserLoginItem.getVc_user_name());
			editTextUserName.setHintTextColor(getResources().getColor(R.color.Signup_hint_color));
			editTextUserName.addTextChangedListener(new TextWatcher() {

				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {
					// TODO Auto-generated method stub
					editTextUserName.setTypeface(null, Typeface.BOLD);
				}
				@Override
				public void beforeTextChanged(CharSequence s, int start, int count,
						int after) {
					// TODO Auto-generated method stub
				}
				@Override
				public void afterTextChanged(Editable s) {
					// TODO Auto-generated method stub
				}
			});


			editTextUserDOB.setText(mUserLoginItem.getVc_date_of_birth());
			editTextUserDOB.setHintTextColor(getResources().getColor(R.color.Signup_hint_color));
			editTextUserDOB.addTextChangedListener(new TextWatcher() {

				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {
					// TODO Auto-generated method stub
					editTextUserDOB.setTypeface(null, Typeface.BOLD);
				}

				@Override
				public void beforeTextChanged(CharSequence s, int start, int count,
						int after) {
					// TODO Auto-generated method stub
				}

				@Override
				public void afterTextChanged(Editable s) {
					// TODO Auto-generated method stub
				}
			});
			editTextUserDddress.setText(mUserLoginItem.getVc_user_add());
			editTextUserDddress.setHintTextColor(getResources().getColor(R.color.Signup_hint_color));
			editTextUserDddress.addTextChangedListener(new TextWatcher() {

				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {
					// TODO Auto-generated method stub
					editTextUserDddress.setTypeface(null, Typeface.BOLD);
				}

				@Override
				public void beforeTextChanged(CharSequence s, int start, int count,
						int after) {
					// TODO Auto-generated method stub
				}

				@Override
				public void afterTextChanged(Editable s) {
					// TODO Auto-generated method stub
				}
			});
			editTextUserCoutry.setText(mUserLoginItem.getVc_user_country());
			editTextUserCoutry.setHintTextColor(getResources().getColor(R.color.Signup_hint_color));
			editTextUserCoutry.addTextChangedListener(new TextWatcher() {

				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {
					// TODO Auto-generated method stub
					editTextUserCoutry.setTypeface(null, Typeface.BOLD);
				}

				@Override
				public void beforeTextChanged(CharSequence s, int start, int count,
						int after) {
					// TODO Auto-generated method stub
				}

				@Override
				public void afterTextChanged(Editable s) {
					// TODO Auto-generated method stub
				}
			});
			editTextUserCity.setText(mUserLoginItem.getVc_user_city());
			editTextUserCity.setHintTextColor(getResources().getColor(R.color.Signup_hint_color));
			editTextUserCity.addTextChangedListener(new TextWatcher() {

				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {
					// TODO Auto-generated method stub
					editTextUserCity.setTypeface(null, Typeface.BOLD);
				}

				@Override
				public void beforeTextChanged(CharSequence s, int start, int count,
						int after) {
					// TODO Auto-generated method stub
				}

				@Override
				public void afterTextChanged(Editable s) {
					// TODO Auto-generated method stub
				}
			});
			editTextUserState.setText(mUserLoginItem.getVc_user_state());
			editTextUserState.setHintTextColor(getResources().getColor(R.color.Signup_hint_color));
			editTextUserState.addTextChangedListener(new TextWatcher() {

				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {
					// TODO Auto-generated method stub
					editTextUserState.setTypeface(null, Typeface.BOLD);
				}

				@Override
				public void beforeTextChanged(CharSequence s, int start, int count,
						int after) {
					// TODO Auto-generated method stub
				}

				@Override
				public void afterTextChanged(Editable s) {
					// TODO Auto-generated method stub
				}
			});
			if(mUserLoginItem.getVc_user_document()!=null){
				editTextUserCPF.setText(mUserLoginItem.getVc_user_document());
			}else{
			}
			editTextUserCPF.setHintTextColor(getResources().getColor(R.color.Signup_hint_color));

		} catch (Exception e) {
			// TODO: handle exception
		}

		mpressOkBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.i("", "1111  Ok btn pressed" );

				UserPostaCode = mPostalCode.getText().toString();
				Log.i("", " 1111 user postal code" +UserPostaCode);
				if(!UserPostaCode.equalsIgnoreCase("")){
					PostalCodeDetail();
				}else{

				}
			}
		});

		buttonback.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				mFragmentManager.popBackStack();
				// TODO Auto-generated method stub
			}
		});

		buttonSaveUserInfo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				isInternetPresent=AppUtils.isConnectingToInternet(mContext);
				if (isInternetPresent) {
					Log.i("", "qqqqqqqqqqq Button save info clicked : ");
					if(editTextUserName.getText().toString().equalsIgnoreCase("")) {
						CustomDialogBox.showAlert(getResources().getString(R.string.errorDialog),"Por favor, infome seu nome.", getActivity());	
					}else if(editTextUserDOB.getText().toString().contains("D")){
						CustomDialogBox.showAlert(getResources().getString(R.string.errorDialog),getResources().getString(R.string.infoIncorrect), getActivity());
					}else if(editTextUserDOB.getText().toString().contains("M")){
						CustomDialogBox.showAlert(getResources().getString(R.string.errorDialog),getResources().getString(R.string.infoIncorrect), getActivity());
					}else if(editTextUserDOB.getText().toString().contains("Y")){
						CustomDialogBox.showAlert(getResources().getString(R.string.errorDialog),getResources().getString(R.string.infoIncorrect), getActivity());
					}else if(mUserEmail.getText().toString().equalsIgnoreCase("")){
						CustomDialogBox.showAlert(getResources().getString(R.string.errorDialog),getResources().getString(R.string.infoIncorrect), mContext);
					}
					else if((editTextUserCPF.getText().toString().length())==14){
						mUserLoginItem.setVc_user_name(editTextUserName.getText().toString());
						mUserLoginItem.setVc_date_of_birth(editTextUserDOB.getText().toString());
						mUserLoginItem.setNu_pin_code(mPostalCode.getText().toString());

						mUserLoginItem.setVc_user_city(editTextUserCity.getText().toString());
						mUserLoginItem.setVc_user_state(editTextUserState.getText().toString());
						mUserLoginItem.setVc_user_document(editTextUserCPF.getText().toString());
						mUserLoginItem.setVc_user_email(mUserEmail.getText().toString());
						mUserLoginItem.setVc_user_add(editTextUserLogradouro.getText().toString());
						mUserLoginItem.setVc_user_add_street_no(met_userNumber.getText().toString());
						mUserLoginItem.setVc_neighborhood(editTextUseret_userBairro.getText().toString());
						mUserLoginItem.setVc_user_country("Brasil");
						executeEditUserTask();
					}else {
						CustomDialogBox.showAlert(getResources().getString(R.string.infoDialo),"Número de CPF inválido.", getActivity());
					}

				}else {

				}
			}
		});
		return view;
	}

	private void executeEditUserTask(){
		Log.i("", "qqqqqqqqq Listener save info calling  : ");
		TaskListener mTaskListener = new TaskListener() {
			@Override
			public void updateUI() {
				// TODO Auto-generated method stub
				if(editUserInfoItem!=null){
					if (editUserInfoItem.getStatusCode().equalsIgnoreCase("1")) {
						mFragmentTransect=mFragmentManager.beginTransaction();
						if(mFragment!=null){
							mFragmentTransect.detach(mFragment);
							mFragment=null;
						}
						mFragment=new AddNewCardFragment();
						mFragmentTransect.replace(R.id.fragmentContainer,mFragment);
						mFragmentTransect.commitAllowingStateLoss();
					}
				}
			}

			@Override
			public void execute() {
				// TODO Auto-generated method stub
				ArrayList<String> userInputs= new ArrayList<String>();
				/*		userInputs.add(mUserLoginItem.getVc_user_name());
				userInputs.add(mUserLoginItem.getVc_user_pwd());
				userInputs.add(mUserLoginItem.getVc_user_gender());
				userInputs.add(mUserLoginItem.getVc_date_of_birth());
				userInputs.add(mUserLoginItem.getVc_user_email());

				userInputs.add(mUserLoginItem.getVc_user_add());
				userInputs.add(mUserLoginItem.getVc_user_add_street_no());

				userInputs.add(mUserLoginItem.getVc_user_mobile_no());
				userInputs.add(mUserLoginItem.getVc_user_country());
				userInputs.add(mUserLoginItem.getVc_user_state());
				userInputs.add(mUserLoginItem.getVc_user_city());
				userInputs.add(mUserLoginItem.getNu_pin_code());
				userInputs.add(mUserLoginItem.getCh_fb_login_user());
				userInputs.add("1");
				userInputs.add(mUserLoginItem.getCh_user_active());
				if(mUserLoginItem.getPf_user_image()==null){
					userInputs.add("");
				}else{
					userInputs.add(mUserLoginItem.getPf_user_image());
				}
				userInputs.add("1");
				userInputs.add(mUserLoginItem.getVc_neighborhood());//14
				userInputs.add(mUserLoginItem.getUUID());
				userInputs.add(mUserLoginItem.getVc_user_document());*/
				userInputs.add(mUserLoginItem.getVc_user_name());//0
				userInputs.add(mUserLoginItem.getVc_user_pwd());//1
				userInputs.add(mUserLoginItem.getVc_user_gender());//2
				userInputs.add(mUserLoginItem.getVc_date_of_birth());//3
				userInputs.add(mUserLoginItem.getVc_user_email());//4

				userInputs.add(mUserLoginItem.getVc_user_add());//5
				userInputs.add(mUserLoginItem.getVc_user_add_street_no());
				userInputs.add(mUserLoginItem.getVc_user_mobile_no());//6
				userInputs.add(mUserLoginItem.getVc_user_country());//7
				userInputs.add(mUserLoginItem.getVc_user_state());//8

				userInputs.add(mUserLoginItem.getVc_user_city());//9
				userInputs.add(mUserLoginItem.getNu_pin_code());//10
				userInputs.add(mUserLoginItem.getCh_fb_login_user());//11

				userInputs.add("1");//12
				userInputs.add(mUserLoginItem.getCh_user_active());
				if(mUserLoginItem.getPf_user_image()==null){
					userInputs.add("");

				}else{
					userInputs.add(mUserLoginItem.getPf_user_image());
				}

				userInputs.add("1");//13
				userInputs.add(mUserLoginItem.getVc_neighborhood());//14
				userInputs.add(mUserLoginItem.getUUID());//15
				userInputs.add(mUserLoginItem.getVc_user_document());//16
				try {
					ContentManager.getInstance();
					editUserInfoItem = ContentManager.parseEditUserInfoStatus(preparedUserInfoediting(userInputs));
					userLoginItem= ContentManager.getInstance().getLogin(mContext,UserId,UserPassword);
					userLoginItem.setVc_user_email(UserId);
					userLoginItem.setVc_user_pwd(UserPassword);
					SettingPrefrences.saveUserPrefrence(userLoginItem,mContext);
				} catch (Exception e) {
					// TODO: handle exception
					Log.i("", "qqqqqqq Exception  :"  +e.toString());
				}
			}
		};
		if(mAsyncTask!=null){
			mAsyncTask.cancel(true);
			mAsyncTask=null;
		}
		mAsyncTask= new CustomAsyncTask(mTaskListener, progressBar, mContext);
		mAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

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


	/*	private String preparedUserInfoediting(ArrayList<String> data){
		String tempXml;
		tempXml="<?xml version="+"\"1.0\""+" encoding="+"\"utf-8\""+" ?>"
				+"<Table>"+
				"<vc_user_name>%s</vc_user_name>"+
				"<vc_user_pwd>%s</vc_user_pwd>"+
				"<vc_user_gender>%s</vc_user_gender>"+ 
				"<vc_date_of_birth>%s</vc_date_of_birth>"+ 
				"<vc_user_email>%s</vc_user_email>"+
				"<vc_user_add>%s</vc_user_add>"+
				"<vc_user_add_street_no>%s</vc_user_add_street_no>"+
				"<vc_user_mobile_no>%s</vc_user_mobile_no>" +
				"<vc_user_country>%s</vc_user_country>" +
				"<vc_user_state>%s</vc_user_state>" +
				"<vc_user_city>%s</vc_user_city>"+
				"<nu_pin_code>%s</nu_pin_code>"+
				"<ch_fb_login_user>%s</ch_fb_login_user>"+
				"<vc_user_fb_id>%s</vc_user_fb_id>"+
				"<ch_user_active>%s</ch_user_active>"+
				"<pf_user_image>%s</pf_user_image>"+
				"<vc_complement>%s</vc_complement>"+
				"<vc_neighborhood>%s</vc_neighborhood>"+
				"<UUID>%s</UUID>"+
				"<vc_user_document>%s</vc_user_document>"+
				"</Table>";


		Log.i("", "qqqqqqqq   edit xml : "  + String.format(tempXml,data.get(0),data.get(1),data.get(2),data.get(3),
				data.get(4),data.get(5),data.get(6),data.get(7),data.get(8),data.get(9),data.get(10),
				data.get(11),data.get(12),data.get(13),data.get(14),data.get(15),data.get(16),data.get(17),data.get(18)));
		return  String.format(tempXml,data.get(0),data.get(1),data.get(2),data.get(3),
				data.get(4),data.get(5),data.get(6),data.get(7),data.get(8),data.get(9),data.get(10),
				data.get(11),data.get(12),data.get(13),data.get(14),data.get(15),data.get(16),data.get(17),data.get(18));
	}*/

	private String preparedUserInfoediting(ArrayList<String> data){
		String tempXml;


		tempXml="<?xml version="+"\"1.0\""+" encoding="+"\"utf-8\""+" ?>"
				+"<Table>"+
				"<vc_user_name>%s</vc_user_name>"+
				"<vc_user_pwd>%s</vc_user_pwd>"+
				"<vc_user_gender>%s</vc_user_gender>"+ 
				"<vc_date_of_birth>%s</vc_date_of_birth>"+ 
				"<vc_user_email>%s</vc_user_email>"+
				"<vc_user_add>%s</vc_user_add>"+
				"<vc_user_add_street_no>%s</vc_user_add_street_no>"+
				"<vc_user_mobile_no>%s</vc_user_mobile_no>" +
				"<vc_user_country>%s</vc_user_country>" +
				"<vc_user_state>%s</vc_user_state>" +
				"<vc_user_city>%s</vc_user_city>"+
				"<nu_pin_code>%s</nu_pin_code>"+
				"<ch_fb_login_user>%s</ch_fb_login_user>"+
				"<vc_user_fb_id>%s</vc_user_fb_id>"+
				"<ch_user_active>%s</ch_user_active>"+
				"<pf_user_image>%s</pf_user_image>"+
				"<vc_complement>%s</vc_complement>"+
				"<vc_neighborhood>%s</vc_neighborhood>"+
				"<UUID>%s</UUID>"+
				"<vc_user_document>%s</vc_user_document>"+
				"</Table>";

		/*<?xml version="1.0" encoding="utf-8"?>
		<Table>
		<vc_user_name>Mmm</vc_user_name>
		<vc_user_pwd>Mmm</vc_user_pwd>
		<vc_user_gender>M</vc_user_gender>
		<vc_date_of_birth>04-04-2014</vc_date_of_birth>
		<vc_user_email>wa05@gmail.com</vc_user_email>
		<vc_user_add>1</vc_user_add>
		<vc_user_mobile_no>1</vc_user_mobile_no>
		<vc_user_country>1</vc_user_country>
		<vc_user_state>1</vc_user_state>
		<vc_user_city>1</vc_user_city>
		<nu_pin_code>1</nu_pin_code>
		<ch_fb_login_user>1</ch_fb_login_user>
		<vc_user_fb_id>1</vc_user_fb_id>
		<ch_user_active>1</ch_user_active>
		<pf_user_image>1</pf_user_image>
		<vc_complement>1</vc_complement>
		<vc_neighborhood>1</vc_neighborhood>
		<UUID>wfg98377523523ggf</UUID>
		<vc_user_document></vc_user_document>
		</Table>
		 */

		return  String.format(tempXml,data.get(0),data.get(1),data.get(2),data.get(3),
				data.get(4),data.get(5),data.get(6),data.get(7),data.get(8),data.get(9),data.get(10),
				data.get(11),data.get(12),data.get(13),data.get(14),data.get(15),data.get(16),data.get(17),data.get(18),data.get(19));
	}

	protected void loadLoginTask() {
		// TODO Auto-generated method stub
		if (mAsyncTask != null) {
			mAsyncTask.cancel(true);
			mAsyncTask = null;
		}
		mAsyncTask = new CustomAsyncTask(mLoginTaskListener, mContext);
		mAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

	}
	TaskListener mLoginTaskListener=new TaskListener() {

		@Override
		public void updateUI() {
			// TODO Auto-generated method stub
			if( mUserLoginItem!=null){
				SettingPrefrences.saveUserPrefrence(mUserLoginItem,mContext);
			}

		}

		@Override
		public void execute() {
			// TODO Auto-generated method stub
			userLoginItem= ContentManager.getInstance().getLogin(mContext,UserId,UserPassword);
		}
	};

	TextWatcher tw = new TextWatcher() {
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
				editTextUserDOB.setText(current);
				editTextUserDOB.setSelection(sel < current.length() ? sel : current.length());
			}
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
		}
	};


	protected void PostalCodeDetail() {
		// TODO Auto-generated method stub
		Log.i("", "loadLoginAscyn Task Runn");
		if (mAsyncTask != null) {
			mAsyncTask.cancel(true);
			mAsyncTask = null;
		}
		mAsyncTask = new CustomAsyncTask(mPostalTaskListener, progressBar,mContext);
		mAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

	}
	TaskListener mPostalTaskListener=new TaskListener() {

		@Override
		public void updateUI() {
			// TODO Auto-generated method stub

			if(mPostalModel!=null){
				editTextUserLogradouro.setText(mPostalModel.getUserLogradouro());
				editTextUseret_userBairro.setText(mPostalModel.getUserBairro());
				editTextUserCity.setText(mPostalModel.getUserLocalidae());
				editTextUserState.setText(mPostalModel.getUserUf());

			}else {
				CustomDialogBox.showAlert(getResources().getString(R.string.errorDialog),"Código postal errado.", mContext);
			}
		}

		@Override
		public void execute() {
			// TODO Auto-generated method stub
			Log.i("", "1111  inside  task listener ");
			String Url ="https://viacep.com.br/ws/"+UserPostaCode.replace("-", "")+"/xml/";
			Log.i("", "11111  postal code url : " +Url);
			mPostalModel= ContentManager.getInstance().getPostalCodeAddress(Url);
		}
	};


}
