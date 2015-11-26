package com.partyfinder.CustomView;

import com.partyfinder.R;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class CustomDialogBox {

public static void showAlert(String title,String msg,Context context){
		
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
				dialog.dismiss();
			}
		});
		dialog.show();
	}



public Dialog showAlert(String title, String msg, Context context, Intent mIntent){
	
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
			
			dialog.dismiss();
		}
	});
	dialog.show();
	return dialog;
}



public static void showAlertAt(String title, String msg, Context context){
	
	final CustomDialog dialog=new CustomDialog(context,R.layout.dummy);
	Button okButton=(Button) dialog.findViewById(R.id.popup_window_button);
	
	TextView tittle=(TextView) dialog.findViewById(R.id.popup_window_textinfo);
	tittle.setText(title);
	TextView msgBody=(TextView) dialog.findViewById(R.id.popup_window_infodetail);
	msgBody.setText(msg);
	okButton.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {  
			// TODO Auto-generated method stub
			dialog.dismiss();
		}
	});
	dialog.show();
}

}
