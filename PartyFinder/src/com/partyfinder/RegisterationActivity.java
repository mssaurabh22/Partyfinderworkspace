package com.partyfinder;

import java.util.ArrayList;

import com.partyfinder.AppConstant.PartyFinderConstants;
import com.partyfinder.CustomView.CustomAsyncTask;
import com.partyfinder.CustomView.CustomDialog;
import com.partyfinder.CustomView.CustomDialogBox;
import com.partyfinder.CustomView.TaskListener;

import com.partyfinder.app.utils.AppUtils;
import com.partyfinder.app.utils.GCMClient;
import com.partyfinder.managers.ContentManager;
import com.partyfinder.model.RegistrationStatus;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class RegisterationActivity extends Activity {

	private	EditText firstName;
	private	EditText userGender;
	private	EditText email;
	private	EditText confirmEmail;
	private	EditText password;
	private	EditText confirmPassword;
	private	Button registrationBtn;
	private ImageView Img_backBtn;

	//declared variables for user inputs
	public String userFirstName;
	public String mUserGender;
	public String userEmail;
	public String userConfirmEmail;
	public String userPass;
	public String userConfirmPass;
	//	public String fullName;
	public static String TAG = "RegistrationActivityClass ";
	private GCMClient gcmClient;
	private String regId;
	boolean isInternetPresent = false;
	public String url = PartyFinderConstants.URL_REGISTRATION;
	private Context mContext = RegisterationActivity.this;
	private CustomAsyncTask mRegistrationTask;
	private ProgressBar mProgBar;
	private Intent mIntent;

	private RegistrationStatus mRegStatucCode = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_layout);
		gcmClient=new GCMClient(this);
		regId=gcmClient.getRegistrationId();
		if(regId.isEmpty()){
			Log.i("",TAG+"Registration Id : "+regId);
			gcmClient.getGCMUsingBackground();
		}

		firstName = (EditText)findViewById(R.id.eT_userFirstName);
		userGender = (EditText)findViewById(R.id.eT_userLastName);
		email = (EditText)findViewById(R.id.eT_email);
		confirmEmail = (EditText)findViewById(R.id.eT_confirmEmail);
		password = (EditText)findViewById(R.id.eText_password);
		confirmPassword = (EditText)findViewById(R.id.eT_confirmPassword);
		registrationBtn = (Button)findViewById(R.id.btn_SignUp);
		Img_backBtn=(ImageView)findViewById(R.id.btn_regisBck);
		mProgBar=(ProgressBar) findViewById(R.id.aaaaaaaaa);
		mProgBar.bringToFront();
		Img_backBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		registrationBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				isInternetPresent = AppUtils.isConnectingToInternet(mContext);
				if (isInternetPresent) {

					// get user inputs values
					userFirstName = firstName.getText().toString();
					mUserGender = userGender.getText().toString();
					//concat firsname and lastname
					//					fullName = userFirstName.concat(" ").concat(userLastName);
					userEmail = email.getText().toString();
					userConfirmEmail = confirmEmail.getText().toString();
					userPass = password.getText().toString();
					Log.i("","User password"+userPass);
					userConfirmPass = confirmPassword.getText().toString();
					//match email and confirmEmail for equality
					/*boolean isFirstNameEmpty = userNameCheck(userFirstName);
					boolean isEmailEmpty = checkEmail(userEmail);*/
					boolean isEmailMatching = matchEmailAddress(userEmail, userConfirmEmail);
					boolean isPasswordMatching = matchPassword(userPass, userConfirmPass);


					if(validation().equalsIgnoreCase("userFirstName")){
						//						Toast.makeText(mContext, "Por favor, infome seu nome.", Toast.LENGTH_SHORT).show();
						//						 CustomToast.showToastMessage(mContext,"Por favor, infome seu nome.",500);
						CustomDialogBox.showAlert(getResources().getString(R.string.infoDialo),"Por favor, infome seu nome.", mContext);
					}else if(validation().equalsIgnoreCase("userEmail")){
						//						Toast.makeText(mContext, "E-mail em branco. Por favor, indique o e-mail", Toast.LENGTH_SHORT).show();
						//						 CustomToast.showToastMessage(mContext,"E-mail em branco. Por favor, indique o e-mail",500);
						CustomDialogBox.showAlert(getResources().getString(R.string.infoDialo),"E-mail em branco. Por favor, indique o e-mail", mContext);
					}else if(!isEmailMatching){
						//						Toast.makeText(mContext, "E-mails não conferem.", Toast.LENGTH_SHORT).show();
						//						 CustomToast.showToastMessage(mContext,"E-mails não conferem.",500);
						CustomDialogBox.showAlert(getResources().getString(R.string.infoDialo),"E-mails não conferem.", mContext);
					}else if(validation().equalsIgnoreCase("userPass")){
						//						Toast.makeText(mContext, "Senha em branco. Por favor, informe a senha.", Toast.LENGTH_SHORT).show();
						//						 CustomToast.showToastMessage(mContext,"Senha em branco. Por favor, informe a senha.",500);
						CustomDialogBox.showAlert(getResources().getString(R.string.infoDialo),"Senha em branco. Por favor, informe a senha.", mContext);
					}else if(!isPasswordMatching){
						//						Toast.makeText(mContext, "Senhas não conferem.", Toast.LENGTH_SHORT).show();
						//						 CustomToast.showToastMessage(mContext,"Senhas não conferem.",500);
						CustomDialogBox.showAlert(getResources().getString(R.string.infoDialo),"Senhas não conferem.", mContext);
					}else if(validation().equalsIgnoreCase("userGender")){
						//						Toast.makeText(mContext, "Erro no sexo informado", Toast.LENGTH_SHORT).show();
						//						 CustomToast.showToastMessage(mContext,"Erro no sexo informado",500);
						CustomDialogBox.showAlert(getResources().getString(R.string.infoDialo),"Erro no sexo informado", mContext);
					}else if(userGender.getText().toString().equalsIgnoreCase("Masc") || userGender.getText().toString().equalsIgnoreCase("Fem")){ 

						executeRegisterTask();
					}else{
						//ExecuteRegisterTask();
						//						    	 CustomToast.showToastMessage(mContext,"Erro no sexo informado",500);
						CustomDialogBox.showAlert(getResources().getString(R.string.infoDialo),"Erro no sexo informado", mContext);
						//						      Toast.makeText(mContext, "Erro no sexo informado", Toast.LENGTH_SHORT).show();
					}

					/*	}else if((!mUserGender.equalsIgnoreCase("male")) || (!mUserGender.equalsIgnoreCase("female"))){//|| 
						Toast.makeText(mContext, "User gender should be male or female only.", Toast.LENGTH_SHORT).show();

					}else{
						//ExecuteRegisterTask();
						executeRegisterTask();
					}*/

					/*		if(validation().equalsIgnoreCase("userFirstName")){

					}
					 */
				} else {
					//					CustomToast.showToastMessage(mContext, "Conecte seu dispositivo com conexão de Internet ativa",500);
					CustomDialogBox.showAlert(getResources().getString(R.string.infoDialo),getResources().getString(R.string.NoInternetMessage), mContext);
					//					Toast.makeText(getApplicationContext(),"Conecte seu dispositivo com conexão de Internet ativa", Toast.LENGTH_SHORT).show(); 
				}




			}
		});
	}

	private void executeRegisterTask(){

		TaskListener mTaskListener =new TaskListener() {

			@Override
			public void updateUI() {
				// TODO Auto-generated method stub
				if(mRegStatucCode!=null){
					Log.i("", "Status code :"+mRegStatucCode.getStatusCode());
					if(mRegStatucCode.getStatusCode().equalsIgnoreCase("1")){
						//						progressDialog.dismiss();
						//						Toast.makeText(mContext, "Registrado com sucesso. Por favor, vá para seção de login.", Toast.LENGTH_SHORT).show();
						//						CustomToast.showToastMessage(mContext, "Registrado com sucesso. Por favor, vá para seção de login.", 500);
						showAlert(getResources().getString(R.string.infoDialo),"Registrado com sucesso. Por favor, vá para seção de login.", mContext);
						mIntent = new Intent(RegisterationActivity.this, LoginActivity.class);

					}else{
						//					   Toast.makeText(mContext, "registração falhou", Toast.LENGTH_SHORT).show();

						showAlert(getResources().getString(R.string.infoDialo),"Registração falhou", mContext);
						//						CustomToast.showToastMessage(mContext, "registração falhou", 500);
						//						mIntent = new Intent(RegisterationActivity.this, RegisterationActivity.class);
					}
				}else{
					//						Toast.makeText(mContext,"Desculpe Internet é lenta",Toast.LENGTH_SHORT);
					//						 CustomToast.showToastMessage(mContext, "Desculpe Internet é lenta", 500);
					CustomDialogBox.showAlert(getResources().getString(R.string.infoDialo),"Desculpe Internet é lenta", mContext);
				}
			}

			@Override
			public void execute() {
				// TODO Auto-generated method stub
				ArrayList<String> userInputs = new ArrayList<String>();

				userInputs.add(userFirstName);
				userInputs.add(userPass);
				userInputs.add(mUserGender);
				userInputs.add("");
				userInputs.add(userEmail);
				userInputs.add("");
				userInputs.add("");
				userInputs.add(regId);


				mRegStatucCode = ContentManager.getInstance().parseRegistrationStatusCode(preparedUserRegistrationXml(userInputs));

			}
		};
		if(mRegistrationTask!=null){
			mRegistrationTask.cancel(true);
			mRegistrationTask=null;
		}
		mRegistrationTask=new CustomAsyncTask(mTaskListener, mProgBar,mContext);
		mRegistrationTask.execute();
	}




	private void showAlert(String title,String msg,Context context){

		final CustomDialog dialog=new CustomDialog(context,R.layout.popup_window);
		Button okButton=(Button) dialog.findViewById(R.id.popup_window_button);

		TextView tittle=(TextView) dialog.findViewById(R.id.popup_window_textinfo);
		tittle.setText(title);
		TextView msgBody=(TextView) dialog.findViewById(R.id.popup_window_infodetail);
		msgBody.setText(msg);
		okButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(mIntent!=null){
					startActivity(mIntent);
					finish();	
				}

				dialog.dismiss();
			}
		});
		dialog.show();
	}

	// Asyn Task by me 
	/*private void ExecuteRegisterTask(){
	new RegistrationTask().execute();*/
	//}
	/*	private class RegistrationTask extends AsyncTask<Void, Void, RegistrationStatus>{



		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			mProgBar.setVisibility(View.VISIBLE);

			super.onPreExecute();
		}

		@SuppressWarnings("static-access")
		@Override
		protected RegistrationStatus doInBackground(Void... params) {
			// TODO Auto-generated method stub
			RegistrationStatus mRegStatucCode = null;
			String xml;
			ArrayList<String> userInputs = new ArrayList<String>();
			userInputs.add(userFirstName);
			userInputs.add(userPass);
			userInputs.add("mUserGender");
			userInputs.add("");
			userInputs.add(userEmail);
			userInputs.add("");
			userInputs.add("");
			userInputs.add(regId); //regId

			xml = preparedUserRegistrationXml(userInputs);
			Log.i("",TAG+" Prepared XML : "+xml+" Url : "+url);

			HttpClient mHttpClient = new DefaultHttpClient();
			HttpPost mHttpPost = new HttpPost(url);
			mHttpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
			mHttpPost.setHeader("Accept", "application/xml");
			mHttpPost.setHeader("Content-Type", "application/xml");

			try {
				StringEntity se = new StringEntity(xml);
				se.setContentType("text/xml");
				mHttpPost.setEntity(se);

				HttpResponse response = mHttpClient.execute(mHttpPost);

				InputStream inputStream = response.getEntity().getContent();


				mRegStatucCode = ContentManager.getInstance().parseRegistrationStatusCode(inputStream);

				Log.d("", TAG + mRegStatucCode.getStatusCode());


			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return mRegStatucCode;
		}

		@Override
		protected void onPostExecute(RegistrationStatus mStatucCode) {
			// TODO Auto-generated method stub
			//super.onPostExecute(result);

			Log.i("","mStatus code :"+mStatucCode.getStatusCode());
			if(mStatucCode.getStatusCode().equals("​1")){
				Log.i("","mStatus code : inside the 1 status"+mStatucCode.getStatusCode());
				mProgBar.setVisibility(View.GONE);
//				CustomToast.showToastMessage(mContext, "Registrado com sucesso. Por favor, vá para seção de login.", 500);
				CustomDialogBox.showAlert(getResources().getString(R.string.infoDialo),"Registrado com sucesso. Por favor, vá para seção de login.", mContext);	
//				Toast.makeText(mContext, "Registrado com sucesso. Por favor, vá para seção de login.", Toast.LENGTH_SHORT).show();
				Intent mIntent = new Intent(RegisterationActivity.this, LoginActivity.class);
				startActivity(mIntent);
				finish();

			}else{
				Log.i("","mStatus code : inside the 0 status"+mStatucCode.getStatusCode());
				finish();
//				Toast.makeText(mContext, "registração falhou", Toast.LENGTH_SHORT).show();
//				CustomToast.showToastMessage(mContext, "registração falhou", 500);
				CustomDialogBox.showAlert(getResources().getString(R.string.infoDialo),"registração falhou", mContext);
				Intent mIntent = new Intent(RegisterationActivity.this, RegisterationActivity.class);
				startActivity(mIntent);
			}
		}



	}*/



	private String preparedUserRegistrationXml(ArrayList<String> data){
		String tempXml;

		tempXml="<?xml version="+"\"1.0\""+" encoding="+"\"utf-8\""+" ?>"
				+"<Table>"+
				"<vc_user_name>%s</vc_user_name>"+
				"<vc_user_pwd>%s</vc_user_pwd>"+
				"<vc_user_gender>%s</vc_user_gender>"+
				"<vc_date_of_birth>%s</vc_date_of_birth>"+
				"<vc_user_email>%s</vc_user_email>"+
				"<pf_user_image>%s</pf_user_image>"+
				"<vc_user_fb_id>%s</vc_user_fb_id>"+
				"<UUID>%s</UUID>"+
				"</Table>";

		return  String.format(tempXml,data.get(0),data.get(1),data.get(2),data.get(3),data.get(4),data.get(5),data.get(6),data.get(7));
	}

	private boolean matchEmailAddress(String email, String confirmEmail){
		this.userEmail = email;
		this.userConfirmEmail = confirmEmail;

		if(email.equalsIgnoreCase(confirmEmail)){
			return true;
		}else{
			//Toast.makeText(mContext, "Confirm Email is not matching", Toast.LENGTH_SHORT).show();
			return false;
		}

	}

	private boolean matchPassword(String pass, String confirmPass){
		this.userPass = pass;
		this.userConfirmPass = confirmPass;

		if(pass.equalsIgnoreCase(confirmPass)){
			return true;
		}else{
			//Toast.makeText(mContext, "Confirm Email is not matching", Toast.LENGTH_SHORT).show();
			return false;
		}

	}
	private String validation(){
		if(userFirstName.isEmpty())
			return "userFirstName";
		if(userEmail.isEmpty()){
			return "userEmail";
		}
		if(userPass.isEmpty()){
			return "userPass";
		}if(mUserGender.isEmpty()){
			return"userGender";
		}

		return "";
	}

	private boolean userNameCheck(String userName){

		this.userFirstName = userName;
		if(userName.isEmpty()){
			return false;
		}else{
			return true;
		}

	}

	private boolean checkEmail(String email){

		this.userEmail = email;
		if(email.isEmpty()){
			return false;
		}else{
			return true;
		}

	}




	/*	
	public void showToastMessage(String text, int duration){
        final Toast toast = Toast.makeText(mContext, text, Toast.LENGTH_SHORT);
        toast.show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                toast.cancel();
            }
        }, duration);
}
	 */



	/*
	public void showAlert(String title,String msg){

		  final CustomDialog dialog=new CustomDialog(mContext,R.layout.popup_window);
		  Button okButton=(Button) dialog.findViewById(R.id.popup_window_button);
		  okButton.setOnClickListener(new OnClickListener() {

		   @Override
		   public void onClick(View v) {
		    // TODO Auto-generated method stub
		    dialog.dismiss();
		   }
		  });
		  dialog.show();
		 }*/





}
