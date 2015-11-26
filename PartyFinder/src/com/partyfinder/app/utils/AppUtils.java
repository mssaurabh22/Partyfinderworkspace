package com.partyfinder.app.utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class AppUtils {
	private static String TAG = "Date";
	
	
/*	public static String formatToatal(double total){
		DecimalFormat df = new DecimalFormat("###.##");
		NumberFormat.getInstance(Locale.getDefault());
		String temp=String.valueOf(total);
		if(!temp.contains(".")){
			temp=temp+".00";
		}
		return temp ;
	}*/
	
	

	 

	
	
	public static String preparedUserRegistrationXml(ArrayList<String> data){
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
	
	public static String preparedVanueLikeXml(ArrayList<String> data){
		String tempXml;
		tempXml="<?xml version="+"\"1.0\""+" encoding="+"\"utf-8\""+" ?>"
				+"<Table>"+
				"<vc_company_code>%s</vc_company_code>"+
				"<vc_user_code>%s</vc_user_code>"+
				"<vc_like_status>%s</vc_like_status>"+
				"</Table>";
		
		
		return String.format(tempXml, data.get(0),data.get(1),data.get(2));
		
	}
	
	public static String preparedVanueFavouriteXml(ArrayList<String> data){
		String tempXml;
		tempXml="<?xml version="+"\"1.0\""+" encoding="+"\"utf-8\""+" ?>"
				+"<Table>"+
				"<vc_company_code>%s</vc_company_code>"+
				"<vc_user_code>%s</vc_user_code>"+
				"<vc_like_status>%s</vc_like_status>"+
				"</Table>";
		
		return String.format(tempXml, data.get(0),data.get(1),data.get(2));
		
	}
	
	public static String preparedSendRecommendedVenueXml(ArrayList<String> data){
		String tempXml;
		tempXml="<?xml version="+"\"1.0\""+" encoding="+"\"utf-8\""+" ?>"
				+"<Table>"+
				"<UserId>%s</UserId>"+
				"<FriendId>%s</FriendId>"+
				"<recommendfor>%s</recommendfor>"+
				"</Table>";
		
		return String.format(tempXml, data.get(0),data.get(1),data.get(2));
		
	}
	
	public static String preparedSendFriendRequestXml(ArrayList<String> data){
		String tempXml;
		tempXml="<?xml version="+"\"1.0\""+" encoding="+"\"utf-8\""+" ?>"
				+"<Table>"+
				"<UserId>%s</UserId>"+
				"<FriendId>%s</FriendId>"+
				"</Table>";
		
		return String.format(tempXml, data.get(0),data.get(1));
		
	}
	
	public static String preparedDeleteFriendRequestXml(ArrayList<String> data){
		String tempXml;
		tempXml="<?xml version="+"\"1.0\""+" encoding="+"\"utf-8\""+" ?>"
				+"<Table>"+
				"<UserId>%s</UserId>"+
				"<FriendId>%s</FriendId>"+
				"</Table>";
		
		return String.format(tempXml, data.get(0),data.get(1));
		
	}
	
//	public static String[] changeDateFormat(String date){
//	
//	//  date=" 2/17/2015";
//	     String[] dateArr=date.split("/");
//	     String months[]={"January","February","March","April","May","June","July","August",
//	       "September","Octuber","November","December"};
//	     //Now index to the above array will be your yymmdd[1]-1 coz array index starts from 0
//	     String yy=dateArr[2];
//	     String dd=dateArr[1];
//	     String mon=dateArr[0].replaceAll("[^a-zA-Z0-9_-]", "");
//	     int month = Integer.parseInt(mon);
//         String mm=months[month-1];
////	     String[] myDate=mm+" "+dd;
//	     String monthDateArrayarr[]={mm,dd};
//	     //dd+"-"+mm+"-"+yy;
//	     Log.i("",TAG+monthDateArrayarr[0]);
//
//	     return monthDateArrayarr;
//	    
//	   }
	
	public static String changeDateFormat(String date){
		
		//  date=" 2/17/2015";
		     String[] dateArr=date.split("/");
		     String months[]={"January","February","March","April","May","June","July","August",
		       "September","October","November","December"};
		     //Now index to the above array will be your yymmdd[1]-1 coz array index starts from 0
		     String yy=dateArr[2];
		     String dd=dateArr[1];
		     String mon=dateArr[0].replaceAll("[^a-zA-Z0-9_-]", "");
		    
		     Log.i("","Date foormat :"+date+":llll");
		     Log.i("","Date foormat :"+dd+":"+dateArr[0]+":"+yy);
		   
		     int month = Integer.parseInt(mon);
//		     int month=Integer.parseInt(mon);
		     Log.i("","Date foormat : "+month);
		     String mm=months[month-1];
		     String myDate=mm+" "+dd;
		     //dd+"-"+mm+"-"+yy;
		     Log.i("",TAG+myDate);

		     return myDate;
		    
		   }
	
	
	public static String[] changeDateFormatArray(String date){
		
	//  date=" 2/17/2015";
		Log.i("","WhatsHotDate"+date);
	     String[] dateArr=date.split("/");
	     String months[]={"January","February","March","April","May","June","July","August",
	       "September","Octuber","November","December"};
	     String dd=dateArr[1];
	     String mon=dateArr[0].replaceAll("[^a-zA-Z0-9_-]", "");
	     int month = Integer.parseInt(mon);
         String mm=months[month-1];
//	     String[] myDate=mm+" "+dd;
	     String monthDateArrayarr[]={mm,dd};
	     //dd+"-"+mm+"-"+yy;
	     Log.i("",TAG+monthDateArrayarr[0]);

	     return monthDateArrayarr;
	    
	   }
	
	
	public static boolean isConnectingToInternet(Context mContext){
		  ConnectivityManager connectivity = (ConnectivityManager)mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
		     if (connectivity != null) 
		     {
		         NetworkInfo[] info = connectivity.getAllNetworkInfo();
		         if (info != null) 
		             for (int i = 0; i < info.length; i++) 
		                 if (info[i].getState() == NetworkInfo.State.CONNECTED)
		                 {
		                     return true;
		                 }
		         }
		     return false;
		}
	
	public static String prepareAssignOrderXml(ArrayList<String> data){
		  String tempXml;
		  tempXml="<?xml version="+"\"1.0\""+" encoding="+"\"utf-8\""+" ?>"
		    +"<Table>"+
		    "<vc_user_code>%s</vc_user_code>"+
		    "<vc_event_code>%s</vc_event_code>"+
		    "</Table>";
		  
		  return String.format(tempXml, data.get(0),data.get(1));
		  
		 }
	
	public static  String preparedSubmitPromoterXml(ArrayList<String> data){
		String tempXml;

		tempXml="<?xml version="+"\"1.0\""+" encoding="+"\"utf-8\""+" ?>"
				+"<Table>"+
				"<vc_order_code>%s</vc_order_code>"+
				"<vc_user_code>%s</vc_user_code>"+
				"</Table>";
		return  String.format(tempXml,data.get(0),data.get(1));
	}
	
	public static String preparedDeleteRecommendXml(ArrayList<String> data){
	    String tempXml;
	    tempXml="<?xml version="+"\"1.0\""+" encoding="+"\"utf-8\""+" ?>"
	      +"<Table>"+
	      "<UserId>%s</UserId>"+
	      "<FriendId>%s</FriendId>"+
	      "<recommendfor>%s</recommendfor>"+
	      "</Table>";
	    
	    return String.format(tempXml, data.get(0),data.get(1),data.get(2));
	    
	   }
	
	
	
	public static String AppendZeroArray(String date){
		if(date.contains(".")){
	    String dd = null;
	    String mon;
	    String String = null;
	    
		
	     String[] dateArr=date.split(".");
	    
		 dd=dateArr[1];
		 mon=dateArr[0];
		 if(dd.length()==1){
			 dd=dd+"0";
		 }

		 String = mon+dd;
		 return String;
		}else{
			return date;	
		}

	     
	    
	   }	public static String newFormatToatal(double total){

			DecimalFormatSymbols separator = new DecimalFormatSymbols();
			separator.setDecimalSeparator('.');
			DecimalFormat df = new DecimalFormat("###.##",separator);
			NumberFormat.getInstance(Locale.getDefault());
			String temp=df.format(total);
				if(!temp.contains(".")){
				temp=temp+".00";
			}
					
			String[] array =temp.split("\\.");
			Log.i("", "array  length :" +array.length);
		     String AFD=array[1];
		     String BFD=array[0];
			if(AFD.length()==1){
				temp = temp+"0";
			}
					
			return temp ;
		}
		 public static String formatToatal(double total){
/*			  
			  DecimalFormatSymbols separator = new DecimalFormatSymbols();
			  separator.setDecimalSeparator('.');
			  
			  
			  DecimalFormat df = new DecimalFormat("###.##",separator);
			  NumberFormat.getInstance(Locale.getDefault());
			  String temp=df.format(total);
			  if(!temp.contains(".")){
			   temp=temp+".00";
			  }
			  return temp ;*/
			 
				DecimalFormatSymbols separator = new DecimalFormatSymbols();
				separator.setDecimalSeparator('.');
				DecimalFormat df = new DecimalFormat("###.##",separator);
				NumberFormat.getInstance(Locale.getDefault());
				String temp=df.format(total);
					if(!temp.contains(".")){
					temp=temp+".00";
				}
						
				String[] array =temp.split("\\.");
				Log.i("", "array  length :" +array.length);
			     String AFD=array[1];
			     String BFD=array[0];
				if(AFD.length()==1){
					temp = temp+"0";
				}
						
				return temp ;
			 }
	

}
