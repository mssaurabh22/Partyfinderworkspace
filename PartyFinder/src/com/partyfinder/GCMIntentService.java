package com.partyfinder;

import java.util.Calendar;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.partyfinder.AppConstant.PartyFinderConstants;
import com.partyfinder.model.PushTypeGeneric;


public class GCMIntentService extends IntentService {
	public static final int NOTIFICATION_ID = 1;
	private NotificationManager mNotificationManager;
	private NotificationCompat.Builder builder;
	
	String message;
	String eName ;
	String mMessage;
	String pushType ;
	private  PushTypeGeneric pushGenericItem;

	String initiatedAmount,response,empId,merchantId,empName,updateFundBalance;;
	public GCMIntentService() {
		super("GcmIntentService");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub
		Bundle extras = intent.getExtras();
		pushGenericItem = new PushTypeGeneric();
		pushType = intent.getExtras().getString("pushtype");
		
		mMessage = intent.getExtras().getString("message");
		eName = intent.getExtras().getString("ename");

		
//		if(mMessage.contains("recommnded")){
//			pushGenericItem.setPushType("3");
//		
//	
//		}else{
//			pushGenericItem.setPushType("2");
//		}
		pushGenericItem.setMessage(mMessage);
		pushGenericItem.seteName(eName);
		pushGenericItem.setPushType(pushType);
		
		
		Log.i("", "Notification Content::::"+pushGenericItem.getPushType()+"///"+pushGenericItem.getMessage()+"///"+pushGenericItem.geteName());

		//		   PushItem item = new PushItem();
		Log.d("TEST", "GCM msg received");
		//		   pushItem=new PushItem();

//		if(pushType.equalsIgnoreCase(PartyFinderConstants.PushTypeFriendRequestSend)){
			message=mMessage + eName;  
//		}else if(pushType.equalsIgnoreCase(PartyFinderConstants.PushTypeFriendRequestAccept)){
//			message=mMessage + eName;  

//////		}else {
////			message=mMessage + eName;  
//		}

	

	/* message = intent.getExtras().getString("message");
		   fbid = intent.getExtras().getString("id");
		   name = intent.getExtras().getString("name");
		   picture = intent.getExtras().getString("extra");

//		   item.setMessage(message);
//		   item.setFbid(fbid);
//		   item.setUsername(name);
//		   item.setPictureurl(picture);
	 * 
	 * 

		   initiatedAmount=intent.getExtras().getString("amount");
		   response=intent.getExtras().getString("response");
		   empId=intent.getExtras().getString("eid");
		   merchantId=intent.getExtras().getString("mid");
		   empName=intent.getExtras().getString("ename");
		   updateFundBalance=intent.getExtras().getString("balance");

		   pushItem.setAmount(initiatedAmount);
		   pushItem.setEmpId(empId);
		   pushItem.setEmpName(empName);
		   pushItem.setMerchantId(merchantId);
		   pushItem.setResponse(response);
		   pushItem.setUpdateFundBalance(updateFundBalance);
		   if(response.equalsIgnoreCase("1")){
			   message="Approved amount "+initiatedAmount+" by "+empName;  
		   }else{
			   message="Rejected amount "+initiatedAmount+" by "+empName;  
		   }


//		   Log.i("","Push Notification content : Msg : "+message+"Fb id :"+fbid+" User name : "+name+" : pic url : "+picture);

	 */			
	GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
	String messageType = gcm.getMessageType(intent);
	if(extras.isEmpty()){
		if(GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType)){
			//	        		sendNotification("Send error :"+extras.toString());
		}else if (GoogleCloudMessaging.
				MESSAGE_TYPE_DELETED.equals(messageType)) {
			//	                sendNotification("Deleted messages on server: " +
			//	                        extras.toString());
			// If it's a regular GCM message, do some work.
		}
	}else if (GoogleCloudMessaging.
			MESSAGE_TYPE_MESSAGE.equals(messageType)) {
		// This loop represents the service doing some work.
		for (int i=0; i<5; i++) {
			Log.i("", "Working... " + (i+1)
					+ "/5 @ " + SystemClock.elapsedRealtime());
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
			}
		}
		Log.i("", "Completed work @ " + SystemClock.elapsedRealtime());
		// Post notification of received message.
		sendNotification();

		Log.i("", "Received: " + extras.toString());
	}
	GcmBroadcastReceiver.completeWakefulIntent(intent);
}
// Release the wake lock provided by the WakefulBroadcastReceiver.



private void sendNotification(){
	// TODO Auto-generated method stub

	mNotificationManager = (NotificationManager)
			this.getSystemService(Context.NOTIFICATION_SERVICE);

	Intent dismissIntent = new Intent(this, GCMIntentService.class);
	dismissIntent.setAction("ACTION_DISMISS");
	PendingIntent.getService(this, 0, dismissIntent, 0);

	Intent snoozeIntent = new Intent(this,GCMIntentService.class);
	snoozeIntent.setAction("ACTION_SNOOZ");
	PendingIntent.getService(this, 0, snoozeIntent, 0);

	builder =
			new NotificationCompat.Builder(this)
	.setSmallIcon(R.drawable.ic_launcher)
	.setContentTitle("PartyFinder")
	.setAutoCancel(true)
	.setContentText(message)
	.setDefaults(Notification.DEFAULT_ALL) // requires VIBRATE permission
	.setStyle(new NotificationCompat.BigTextStyle()
	.bigText(message));

	Intent resultIntent = new Intent(this, SplashActivity.class);
	resultIntent.putExtra(PartyFinderConstants.GCM_PUSHEXTRA, pushGenericItem);

	// Intent resultIntent = new Intent(this, PaymentsReceivedActivity.class);



	//        resultIntent.putExtra(MerchantAppConstants.EXTRA_PUSH_ITEM, pushItem);
	/*resultIntent.putExtra(CommonConstants.EXTRA_MESSAGE,message);
        resultIntent.putExtra(CommonConstants.EXTRA_FBID,fbid);

        resultIntent.putExtra(CommonConstants.EXTRA_NAME,name);

        resultIntent.putExtra(CommonConstants.EXTRA_PICURL,picture);*/

	resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);


	PendingIntent resultPendingIntent =
			PendingIntent.getActivity(
					this,
					0,
					resultIntent,
					PendingIntent.FLAG_UPDATE_CURRENT
					);
	int id=getNotificationId();
	builder.setContentIntent(resultPendingIntent);
	//        startTimer(mMillis);
	mNotificationManager = (NotificationManager)
			getSystemService(NOTIFICATION_SERVICE);
	// Including the notification ID allows you to update the notification later on.

	mNotificationManager.notify(id, builder.build());
	//mNotificationManager.cancel(id);

	Log.i("","Push Notification Message : "+message);
	//        mBuilder.setContentIntent(contentIntent);
	//        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());	
}

public int getNotificationId() {
	Calendar calendar = Calendar.getInstance();
	calendar.setTimeInMillis(System.currentTimeMillis());
	return Math.abs((int) calendar.getTimeInMillis());
}




}
