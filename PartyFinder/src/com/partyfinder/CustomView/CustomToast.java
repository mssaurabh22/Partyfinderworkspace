package com.partyfinder.CustomView;


import com.partyfinder.R;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

public class CustomToast {
private Context mContext; 
	
	
	
	public static void showToastMessage(Context mContext, String string, int duration) {
		// TODO Auto-generated method stub
		final Toast toast = Toast.makeText(mContext, string, Toast.LENGTH_SHORT);
	
        toast.show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                toast.cancel();
            }
        }, duration);
	}
	

}
