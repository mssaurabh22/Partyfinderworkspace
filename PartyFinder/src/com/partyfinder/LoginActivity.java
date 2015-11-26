package com.partyfinder;

import com.partyfinder.HomeActivity;
import com.partyfinder.MainActivity;
import com.partyfinder.R;
import com.partyfinder.AppConstant.PartyFinderConstants;
import com.partyfinder.CustomView.CustomAsyncTask;
import com.partyfinder.CustomView.CustomDialogBox;
import com.partyfinder.CustomView.TaskListener;
import com.partyfinder.app.utils.AppUtils;
import com.partyfinder.app.utils.GCMClient;
import com.partyfinder.managers.ContentManager;
import com.partyfinder.managers.SettingPrefrences;
import com.partyfinder.model.PushTypeGeneric;
import com.partyfinder.model.UserLoginItem;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class LoginActivity extends Activity {

	private RelativeLayout loginLayout;
	private LinearLayout forgetPassLayout;
	private TextView  FoggetPassTV;
	private EditText passwordEdit,userNameEdit;
	private Button btnLogin;
	private String userName;
	private String password;
	private ImageView btnLoginBack;
	private Context mContext;
	private UserLoginItem userLoginItem;
	/*ForgetPassword Layouts Views*/	
	private EditText et_ForgetPassword;
	private String emailId;
	private String forgetStatus;
	private Button btn_forgetPass;
	private forgotPasswordTask mforgotPasswordTask;
	private ImageView btnForgetBack;
	private ProgressBar mProgBar;
	private CustomAsyncTask mAsyncTask;
	private ProgressBar progressBar;
	boolean isInternetPresent = false;
	private PushTypeGeneric pushItem;
	private GCMClient gcmClient;
	private String regId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_layout);
		mContext=this;
		pushItem=(PushTypeGeneric) getIntent().getSerializableExtra(PartyFinderConstants.GCM_PUSHEXTRA);
		gcmClient=new GCMClient(this);
		regId=gcmClient.getRegistrationId();
		Log.i("", "UUID   :"+regId);
		FoggetPassTV=(TextView)findViewById(R.id.tv_forgotPassword);
		loginLayout=(RelativeLayout)findViewById(R.id.linear_layout_login);
		forgetPassLayout=(LinearLayout)findViewById(R.id.linear_layout_forgotpass);
		passwordEdit = (EditText) findViewById(R.id.et_user_password);
		userNameEdit = (EditText) findViewById(R.id.et_user_email);
		btnLogin = (Button) findViewById(R.id.btn_user_login);
		et_ForgetPassword=(EditText)findViewById(R.id.eT_forgetpass);
		btn_forgetPass=(Button)findViewById(R.id.btn_forgetpass);
		btnForgetBack=(ImageView)findViewById(R.id.img_frogetbcack);
		mProgBar=(ProgressBar) findViewById(R.id.loginProgbar);
		progressBar=(ProgressBar)findViewById(R.id.progressBarForgetpass);

		mProgBar.bringToFront();

		btnForgetBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				loginLayout.setVisibility(View.VISIBLE);
				forgetPassLayout.setVisibility(View.GONE);
			}
		});

		btnLoginBack=(ImageView)findViewById(R.id.img_Loginbck);
		btnLoginBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent in=new Intent(mContext,HomeActivity.class);
				startActivity(in);
				finish();
			}
		});



		FoggetPassTV.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				loginLayout.setVisibility(View.GONE);
				forgetPassLayout.setVisibility(View.VISIBLE);
			}
		});

		btnLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub


				isInternetPresent = AppUtils.isConnectingToInternet(mContext);
				if (isInternetPresent) {

					// TODO Auto-generated method stub
					userName=userNameEdit.getText().toString().trim();
					password= passwordEdit.getText().toString().trim();
					//			String validateStatus=;
					if(validation().equalsIgnoreCase("userName")){
						@SuppressWarnings("unused")
						AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());


						//						Toast.makeText(getApplicationContext(),"Por favor, informe um e-mail válido.", Toast.LENGTH_SHORT).show(); 
						//CustomToast.showToastMessage(mContext, "Por favor, informe um e-mail válido.", 500);
						CustomDialogBox.showAlert(getResources().getString(R.string.infoDialo), "Por favor, informe um e-mail válido.",mContext);
					}else if(validation().equalsIgnoreCase("password")){
						@SuppressWarnings("unused")
						AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());

						//Toast.makeText(getApplicationContext(),"Por favor, informe a senha.", Toast.LENGTH_SHORT).show();
						CustomDialogBox.showAlert(getResources().getString(R.string.infoDialo), "Por favor, informe a senha.",mContext);
						//CustomToast.showToastMessage(mContext, "Por favor, informe a senha.", 500);
					}else{
						loadLoginTask();
					}

				} else {
					//CustomToast.showToastMessage(mContext, "Conecte seu dispositivo com conexão de Internet ativa", 500);
					CustomDialogBox.showAlert(getResources().getString(R.string.infoDialo),getResources().getString(R.string.NoInternetMessage), mContext);
					//Toast.makeText(getApplicationContext(),"Conecte seu dispositivo com conexão de Internet ativa", Toast.LENGTH_SHORT).show(); 
				}

			}

		});

		/*forgetpasswordButton*/
		btn_forgetPass.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				isInternetPresent = AppUtils.isConnectingToInternet(mContext);
				if (isInternetPresent) {

					emailId=et_ForgetPassword.getText().toString().trim();

					if(validationForgetPassword().equalsIgnoreCase("emailId")){
						//						CustomToast.showToastMessage(mContext, "Por favor, informe um e-mail válido.", 500);
						CustomDialogBox.showAlert(getResources().getString(R.string.infoDialo), "Por favor, informe um e-mail válido.",mContext);
						//						Toast.makeText(getApplicationContext(),"Por favor, informe um e-mail válido.", Toast.LENGTH_SHORT).show(); 
					}else{

						loadforgotPasswordTask();
					}		

				} else {
					CustomDialogBox.showAlert(getResources().getString(R.string.infoDialo),getResources().getString(R.string.NoInternetMessage), mContext);
					//					CustomToast.showToastMessage(mContext, "Conecte seu dispositivo com conexão de Internet ativa", 500);
					//					Toast.makeText(getApplicationContext(),"Conecte seu dispositivo com conexão de Internet ativa", Toast.LENGTH_SHORT).show(); 
				}
			}
		});



	}

	protected void loadLoginTask() {
		// TODO Auto-generated method stub 
		TaskListener mTaskListener=new TaskListener() {

			@Override
			public void updateUI() {
				// TODO Auto-generated method stub


				if( userLoginItem!=null){
					userLoginItem.setVc_user_email(userName);
					userLoginItem.setVc_user_pwd(password);
					Log.i("","user password"+userLoginItem.getVc_user_pwd());

					SettingPrefrences.saveUserPrefrence(userLoginItem,mContext);
					Log.i("","User name : "+ userLoginItem.getVc_user_name());
					Intent intent = new Intent(LoginActivity.this,MainActivity.class);
					intent.putExtra(PartyFinderConstants.GCM_PUSHEXTRA, pushItem);
					startActivity(intent);
					Log.i("","User name :");
					finish();
					//					Toast.makeText(getApplicationContext(),"Login efetuado com sucesso.", Toast.LENGTH_SHORT).show();
				}else{
					CustomDialogBox.showAlert(getResources().getString(R.string.infoDialo), "Falha no login. Por favor, verifique o e-mail e senha.",mContext);
					//					CustomToast.showToastMessage(mContext, "Falha no login. Por favor, verifique o e-mail e senha.", 500);
					//					
					//Toast.makeText(getApplicationContext(),"Falha no login. Por favor, verifique o e-mail e senha.", Toast.LENGTH_SHORT).show();
				}

			}

			@Override
			public void execute() {
				// TODO Auto-generated method stub
				userLoginItem= ContentManager.getInstance().getLogin(mContext,userName,password);
			}
		};
		if (mAsyncTask != null) {
			mAsyncTask.cancel(true);
			mAsyncTask = null;
		}
		mAsyncTask = new CustomAsyncTask(mTaskListener, mProgBar, mContext);
		mAsyncTask.execute();
	}








	private String validation(){
		if(userName.isEmpty())
			return "userName";
		if(password.isEmpty()){
			return "password";
		}
		return "";
	}
	protected void loadforgotPasswordTask() {
		// TODO Auto-generated method stub
		if(mforgotPasswordTask!=null){
			mforgotPasswordTask.cancel(true);
			mforgotPasswordTask=null;
		}
		mforgotPasswordTask=new forgotPasswordTask();
		mforgotPasswordTask.execute();	
	}

	/*forgetPass*/

	private class  forgotPasswordTask extends AsyncTask<Void, Void, Boolean>	{


		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			hideSoftKeyboard();
			progressBar.setVisibility(View.VISIBLE);

			super.onPreExecute();
		}


		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub

			try{
				ContentManager contenMgr=ContentManager.getInstance();
				forgetStatus=contenMgr.parseforget(mContext, emailId);
			}catch(Exception e){
				return false;
			}
			return true;
		}
		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			progressBar.setVisibility(View.GONE);
			if(result){
				if(forgetStatus.equalsIgnoreCase("1")){
					hideSoftKeyboard();
					CustomDialogBox.showAlert(getResources().getString(R.string.infoDialo), "Sua senha foi enviada para o e-mail cadastrado.",mContext);
					//					Toast.makeText(getApplicationContext(),"Sua senha foi enviada para o e-mail cadastrado.", Toast.LENGTH_SHORT).show(); 
					//CustomToast.showToastMessage(mContext, "Sua senha foi enviada para o e-mail cadastrado.",500);
					et_ForgetPassword.setText("");

				}else{
					hideSoftKeyboard();
					//					Toast.makeText(getApplicationContext(),"Usuário não encontrado. Por favor, registre-se.", Toast.LENGTH_SHORT).show(); 
					//CustomToast.showToastMessage(mContext, "Usuário não encontrado. Por favor, registre-se.", 500);
					et_ForgetPassword.setText("");
					CustomDialogBox.showAlert(getResources().getString(R.string.infoDialo), "Usuário não encontrado. Por favor, registre-se.",mContext);

				}

				super.onPostExecute(result);
			}

		}

	}
	private String validationForgetPassword(){
		if(emailId.isEmpty()){
			return "emailId";
		}

		return "";
	}
	/**
	 * Hides the soft keyboard
	 */
	public void hideSoftKeyboard() {
		if(getCurrentFocus()!=null) {
			InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
			inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
		}
	}

}
