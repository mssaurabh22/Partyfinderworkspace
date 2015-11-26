package com.partyfinder;



import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class PrivacyPolicy extends Activity {
	private ImageView mBackButton;
	private Context mContext;
	private RelativeLayout mBAck;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mContext=this;
	setContentView(R.layout.privacypolicy_layout);
	mBAck=(RelativeLayout) findViewById(R.id.backButton_layout);
	mBackButton=(ImageView) findViewById(R.id.backVenue);
	mBackButton.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
//			startActivity(new Intent(mContext,UserSettingActivity.class) );
//			finish();
			finish();
		}
	});
	
	
	mBAck.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
//			startActivity(new Intent(mContext,UserSettingActivity.class) );
//			finish();
			finish();
		}
	});
	
/*	WebView view = (WebView) findViewById(R.id.privacyPolicyContent2);
	String pish = "<html><head><style type=\"text/css\">@font-face {font-family: MyFont;src: url(\"file:///android_asset/opensansregular.ttf\")}body {font-family: MyFont;font-size: small;text-align: justify;}</style></head><body>";
	String pas = "</body></html>";
	String YourText = getResources().getString(R.string.Privacy_policy_text2);
	String myHtmlString = pish + YourText + pas;
	
	
	String text;
	text = "<html><body><p align=\"justify\">";
	text+= getResources().getString(R.string.Privacy_policy_text2);
	text+= "</p></body></html>";
	view.loadData(myHtmlString, "text/html", "utf-8");
	
	
//	WebSettings webSettings = view.getSettings();
//	webSettings.setTextSize(WebSettings.TextSize.SMALLER);
	
	
	*/

	}

}
