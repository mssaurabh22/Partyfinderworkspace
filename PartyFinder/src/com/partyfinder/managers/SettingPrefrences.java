package com.partyfinder.managers;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.partyfinder.AppConstant.PartyFinderConstants;
import com.partyfinder.model.Cards;
import com.partyfinder.model.UserLoginItem;

public class SettingPrefrences {

	
	
	public static void saveUserPrefrence(UserLoginItem item,Context context){
		try{
		@SuppressWarnings("deprecation")
		SharedPreferences prefs = context.getSharedPreferences(PartyFinderConstants.PARTY_FINDER_USER_PREF,
				Context.MODE_WORLD_WRITEABLE);
		String serializedData = item.serialize();
		SharedPreferences.Editor prefEditor = prefs.edit();
		prefEditor.putString(PartyFinderConstants.LOGIN_PREFRENCE, serializedData);
		Log.i("","User Prefrence : Data saved successfully");
		prefEditor.commit();
		}catch(Exception e){
			Log.i("","Exception : "+e.toString());
		}
	}
	
	
	
	public static void deleteUserPrefrence(Context context){
		Log.i("","User Prefrence : Data delet ed successfully");
		try{
			@SuppressWarnings("deprecation")
			SharedPreferences prefs = context.getSharedPreferences(PartyFinderConstants.PARTY_FINDER_USER_PREF,
					Context.MODE_WORLD_WRITEABLE);
			Log.i("","User Prefrence : Data deleted successfully1");
		SharedPreferences.Editor prefEditor = prefs.edit();
		prefEditor.remove(PartyFinderConstants.LOGIN_PREFRENCE);
		
		prefEditor.commit();
		}catch(Exception e){
			Log.i("","exception in setting prefrence"+e.toString());
		}
	}
			 
	
	public static UserLoginItem getUserPrefrence(Context context){
		SharedPreferences prefs = context.getSharedPreferences(PartyFinderConstants.PARTY_FINDER_USER_PREF,
				Context.MODE_PRIVATE);
		
		String serializedDataFromPreference = prefs.getString(PartyFinderConstants.LOGIN_PREFRENCE, null);
		UserLoginItem mInsertUpdetedItem = UserLoginItem.create(serializedDataFromPreference);
		Log.i("","User Prefrence : getUserObject : "+mInsertUpdetedItem);
		return mInsertUpdetedItem;
	}
	
	
	
	@SuppressWarnings("deprecation")
	public static void saveCardPrefrence(Cards item,Context context){
		  try{
		  SharedPreferences prefs = context.getSharedPreferences(PartyFinderConstants.PARTY_FINDER_USER_PREF,
		    Context.MODE_WORLD_WRITEABLE);
		  String serializedData = item.serialize();
		  SharedPreferences.Editor prefEditor = prefs.edit();
		  prefEditor.putString(PartyFinderConstants.CARD_PREF, serializedData);
		  Log.i("","User Prefrence : Data saved successfully");
		  prefEditor.commit();
		  }catch(Exception e){
		   Log.i("","Exception : "+e.toString());
		  }
		 }
	
		 
		 public static Cards getCardPrefrence(Context context){
		  SharedPreferences prefs = context.getSharedPreferences(PartyFinderConstants.PARTY_FINDER_USER_PREF,
		    Context.MODE_PRIVATE);
		  
		  String serializedDataFromPreference  = prefs.getString(PartyFinderConstants.CARD_PREF, null);
		  Cards mInsertUpdetedItem = Cards.create(serializedDataFromPreference);
		  Log.i("","Card Prefrence : getUserObject : "+mInsertUpdetedItem);
		  return mInsertUpdetedItem;
		 }

}
