package com.partyfinder.drawerfragment;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.partyfinder.MainActivity;
import com.partyfinder.R;
import com.partyfinder.AppConstant.PartyFinderConstants;
import com.partyfinder.CustomView.CustomAsyncTask;
import com.partyfinder.CustomView.TaskListener;
import com.partyfinder.app.utils.AppUtils;
import com.partyfinder.app.utils.GCMClient;
import com.partyfinder.managers.ContentManager;
import com.partyfinder.managers.SettingPrefrences;
import com.partyfinder.model.PushTypeGeneric;
import com.partyfinder.model.RegistrationStatus;
import com.partyfinder.model.UserFacebookInfoItem;
import com.partyfinder.model.UserLoginItem;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

public class FacebookLoginFragment extends Fragment{
	
	private final String TAG="Facebook Login Fragment Class";
	private UiLifecycleHelper uiHelper;
	private Context mcontext;
	private UserFacebookInfoItem mFacebookItem;
	private GCMClient gcmClient;
	private UserLoginItem mUserLoginItem;
	private CustomAsyncTask mSyncTask;
	private RegistrationStatus mRegStatucCode = null;
	private PushTypeGeneric pushItem;
	private ProgressBar mFacebookPro;





	private void onSessionStateChange(final Session session, SessionState state, Exception exception) {
		if (state.isOpened()) {
			/* make the API call */
			new Request(
					session,
					"/me",
					null,
					HttpMethod.GET,
					new Request.Callback() {
						public void onCompleted(Response response) {
							/* handle the result */
							Log.i("","Faceboook Response : "+response.getRawResponse());
							mFacebookItem=new UserFacebookInfoItem();

							try {
								JSONObject obj=new JSONObject(response.getRawResponse());
								String fbId=obj.getString("id");

								//final String name = obj.getString("name");





								mFacebookItem.setName(obj.getString("name"));

								mFacebookItem.setUserFbID(obj.getString("id"));
								//								mFacebookItem.setUserFbID("1462002930757063");
								String mailId;
								String birth_day;
								String gender;
								try{
									mailId=obj.getString("email");
									if(mailId!=null){
										mFacebookItem.setEmail(mailId);
									}else{
										mFacebookItem.setEmail(mFacebookItem.getUserFbID()+"@facebook.com");
									}
								}catch(Exception e){
									mFacebookItem.setEmail(mFacebookItem.getUserFbID()+"@facebook.com");
								}

								try{
									gender=obj.getString("gender");
									if(gender!=null){
										mFacebookItem.setGender(gender);
									}else{
										mFacebookItem.setGender("");
									}
								}catch(Exception e){
									mFacebookItem.setGender("");
								}

								try{
									birth_day=obj.getString("birthday");
									if(birth_day!=null){
										mFacebookItem.setDateOfBirth(birth_day);	
									}else{
										mFacebookItem.setDateOfBirth("");
									}
								}catch(Exception e){
									mFacebookItem.setDateOfBirth("");
								}


								//mFacebookItem.set

								/*if(mailId!=null){
									mFacebookItem.setEmail(mailId);
								}else{
									mFacebookItem.setEmail(mFacebookItem.getUserFbID()+"@facebook.com");	
								}*/




								mFacebookItem.setProfilePic("https://graph.facebook.com/"+fbId+"/picture?type=large");
								mFacebookItem.setUuid(gcmClient.getRegistrationId());
								mFacebookItem.setPassword(mFacebookItem.getUserFbID());
								//								mFacebookItem.setPassword("1462002930757063");
								Log.e("",TAG+"User Email: "+mFacebookItem.getEmail());
								Log.e("TAG",  "User Fb Id : "+mFacebookItem.getUserFbID());
								//								mSession=session;
								session.closeAndClearTokenInformation();
Log.i("",TAG+"Got facebook object"+mFacebookItem.getEmail()+" Gender : "+mFacebookItem.getGender()+" Date of birth : "+mFacebookItem.getDateOfBirth()
		+"facebook Id : "+mFacebookItem.getUserFbID() +"facebook UserName : "+mFacebookItem.getName()+"facebook User profile pic : "+mFacebookItem.getProfilePic());
								loginTask();

							} catch (JSONException e) {
								// TODO Auto-generated catch block
								Log.i("","Facebook Exception :"+e);

								/*e.printStackTrace();*/
							}

						}
					}
					).executeAsync();


			Log.i(TAG, "Logged in...");



		} else if (state.isClosed()) {

			Log.i(TAG, "Logged out...");
		}
	}

	private Session.StatusCallback callback = new Session.StatusCallback() {
		@Override
		public void call(Session session, SessionState state, Exception exception) {
			onSessionStateChange(session, state, exception);
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		uiHelper = new UiLifecycleHelper(getActivity(), callback);
		uiHelper.onCreate(savedInstanceState);
		mcontext=getActivity().getApplicationContext();

		getActivity().getIntent().getSerializableExtra(PartyFinderConstants.GCM_PUSHEXTRA);
		mFacebookPro=(ProgressBar) getActivity().findViewById(R.id.facebookProg);
		gcmClient=new GCMClient(mcontext);
		String regId=gcmClient.getRegistrationId();
		if(regId.isEmpty() || regId ==null){
			gcmClient.getGCMUsingBackground();
		}

		// For scenarios where the main activity is launched and user
		// session is not null, the session state change notification
		// may not be triggered. Trigger it if it's open/closed.
		
		Session session = Session.getActiveSession();
		if (session != null &&
				(session.isOpened() || session.isClosed()) ) {
			onSessionStateChange(session, session.getState(), null);
		}

		uiHelper.onResume();
	}


	@Override
	public void onResume() {
		super.onResume();
		uiHelper.onResume();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);


		uiHelper.onActivityResult(requestCode, resultCode, data);


	}
	@Override
	public void onPause() {
		super.onPause();
		uiHelper.onPause();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		uiHelper.onDestroy();

	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		uiHelper.onSaveInstanceState(outState);
	}

	private void loginTask(){
		Log.i("",TAG+"Executing Logintask : "+mFacebookItem);

		TaskListener mTaskListener =new TaskListener() {

			@Override
			public void updateUI() {
				// TODO Auto-generated method stub
				Log.i(TAG,"Executing login task update UI method : "+mUserLoginItem);
				if(mUserLoginItem!=null){
					Log.i(TAG,"Executing login task update UI method login object Not null : "+mUserLoginItem);
					mUserLoginItem.setVc_user_email(mFacebookItem.getEmail());
					mUserLoginItem.setVc_user_pwd(mFacebookItem.getUserFbID());
					SettingPrefrences.saveUserPrefrence(mUserLoginItem,mcontext);
					Log.i("",TAG+"gdghfh"+mUserLoginItem.getVc_user_email());
					Intent intent = new Intent(mcontext,MainActivity.class);
					intent.putExtra(PartyFinderConstants.GCM_PUSHEXTRA, pushItem);
					startActivity(intent);
					getActivity().finish();


				}else{
					Log.i(TAG,"Executing login task update UI method login object is null : "+mUserLoginItem);
					registrationTask();
				}
			}

			@Override
			public void execute() {
				Log.i("",TAG+"Executing Logintask execute method : "+mFacebookItem);
				mUserLoginItem=ContentManager.getInstance().getLogin(mcontext, mFacebookItem.getEmail(),mFacebookItem.getUserFbID());

				Log.i(TAG,"Finish login task execute method : "+mUserLoginItem);

			}
		};
		if(mSyncTask!=null){
			mSyncTask.cancel(true);
			mSyncTask=null;
		}

		//		ProgressBar mProgbar=new ProgressBar(mcontext);
		ProgressDialog daailog=new ProgressDialog(getActivity());
		daailog.setMessage(getResources().getString(R.string.ProcessingText));

		mSyncTask=new CustomAsyncTask(mTaskListener, daailog,mcontext);
		mSyncTask.execute();
	}




	private void registrationTask(){

		TaskListener mRegTasklistener=new TaskListener() {

			@Override
			public void updateUI() {
				// TODO Auto-generated method stub

				Log.i(TAG,"Executing registration task update UI method : "+mUserLoginItem);
				if(mUserLoginItem!=null){
					Log.i(TAG,"Executing login task update UI method login object Not null : "+mUserLoginItem);
					SettingPrefrences.saveUserPrefrence(mUserLoginItem,mcontext);
					Log.i("",TAG+"gdghfh"+mUserLoginItem.getVc_user_email());
					Intent intent = new Intent(mcontext,MainActivity.class);
					startActivity(intent);
					getActivity().finish();


				}else{
					Toast.makeText(mcontext, getResources().getString(R.string.Already_Register),Toast.LENGTH_SHORT).show();
				}

			}

			@SuppressWarnings("static-access")
			@Override
			public void execute() {
				// TODO Auto-generated method stub
				Log.i("",TAG+"Registration task execute method Status code : ");	
				ArrayList<String> userInputs = new ArrayList<String>();

				userInputs.	add(mFacebookItem.getName());
				userInputs.	add(mFacebookItem.getPassword());
				userInputs.	add("");
				userInputs.	add(mFacebookItem.getDateOfBirth());

				userInputs.	add(mFacebookItem.getEmail());
				userInputs.	add(mFacebookItem.getProfilePic());
				userInputs.	add(mFacebookItem.getUserFbID());
				userInputs.	add(mFacebookItem.getUuid());
				String xml=AppUtils.preparedUserRegistrationXml(userInputs);
				Log.i("",TAG+"Prepared xml by facebook : "+xml);

				mRegStatucCode = ContentManager.getInstance().parseRegistrationStatusCode(xml);
				if(mRegStatucCode!=null){
					Log.i("",TAG+"Registration Status code : "+mRegStatucCode.getStatusCode());	

					if(mRegStatucCode.getStatusCode().equalsIgnoreCase("1")){
						Log.i("",TAG+"Registration Status code : "+mRegStatucCode.getStatusCode());	
						mUserLoginItem=ContentManager.getInstance().getLogin( mcontext, mFacebookItem.getEmail(),mFacebookItem.getUserFbID());
						mUserLoginItem.setVc_user_email(mFacebookItem.getEmail());
						mUserLoginItem.setVc_user_pwd(mFacebookItem.getUserFbID());
						//						LoginCredential mCred=new LoginCredential();
						//						mCred.setUserName(mFacebookItem.getEmail());
						SettingPrefrences.saveUserPrefrence(mUserLoginItem,mcontext);
					}else{

					}
				}
			}
		};
		if(mSyncTask!=null){
			mSyncTask.cancel(true);
			mSyncTask=null;
		}
		//		ProgressBar mProgbar=new ProgressBar(mcontext);
		ProgressDialog daailog=new ProgressDialog(getActivity());
		daailog.setMessage(getResources().getString(R.string.ProcessingText));
		mSyncTask=new CustomAsyncTask(mRegTasklistener,daailog,mcontext);
		mSyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

	}


}
