package com.partyfinder.parser;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

import com.partyfinder.model.GetFriendListItem;
import com.partyfinder.model.FriendListItem;
import com.partyfinder.model.RequestedFriendItem;

public class GetFriendRequestParser extends DefaultHandler {
	
	private String tempVal=null;
	
	private final String USER_DETAIL="userDetail";
	private final String IMAGE_URL="imageUrl";
	private final String USER_CODE="userCode";
	private final String USER_NAME="userName";
	private final String USER_GENDER="userGender";
	private final String USER_DOB="userDOB";
	private final String USER_EMAIL="userEmail";
	private final String USER_ADD="userAddress";
	private final String USER_MOBILE="userMobile";
	private final String USER_COUNTRY="userCountry";
	private final String USER_STATE="userState";
	private final String USER_CITY="userCity";
	private final String USER_PIN_CODE="userPinCode";
	private final String USER_FB_LOGIN="userFbLogin";
	private final String USER_ACTIVE="userActive";
	private final String USER_AGE="userAge";
	private final String USER_IMAGE="userImage";
	
	
	private RequestedFriendItem mGetFriendListItem;
	private ArrayList<RequestedFriendItem >mFriendListItemsList;
	private String TAG="Values";
	
	public GetFriendRequestParser() {
		// TODO Auto-generated constructor stub
	mFriendListItemsList= new ArrayList<RequestedFriendItem>();
	}
	 public ArrayList<RequestedFriendItem> getGetFriendRequestItem() {
		return mFriendListItemsList;
		
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		// TODO Auto-generated method stub
		tempVal="";
		/*if(qName.equalsIgnoreCase("User"))*/
		if(qName.equalsIgnoreCase(USER_DETAIL))
			mGetFriendListItem= new RequestedFriendItem();
		Log.i("",""+TAG+qName);
		

	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		// TODO Auto-generated method stub
		tempVal= new String(ch,start,length);
		//super.characters(ch, start, length);
	}
	
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		// TODO Auto-generated method stub
		
		if(qName.equalsIgnoreCase(USER_DETAIL)){
			mFriendListItemsList.add(mGetFriendListItem);
		}else if (qName.equalsIgnoreCase(IMAGE_URL)) {
			mGetFriendListItem.setImageUrl(tempVal);
			
		}else if (qName.equalsIgnoreCase(USER_CODE)) {
			mGetFriendListItem.setUserCode(tempVal);
		}else if (qName.equalsIgnoreCase(USER_NAME)) {
			mGetFriendListItem.setUserName(tempVal);
			
		}else if (qName.equalsIgnoreCase(USER_GENDER)) {
			mGetFriendListItem.setUserGender(tempVal);
		}else if (qName.equalsIgnoreCase(USER_DOB)) {
			mGetFriendListItem.setUserDOB(tempVal);
			
		}else if (qName.equalsIgnoreCase(USER_EMAIL)) {
			mGetFriendListItem.setUserEmail(tempVal);
		}else if (qName.equalsIgnoreCase(USER_ADD)) {
			
			mGetFriendListItem.setUserAddress(tempVal);
		}else if (qName.equalsIgnoreCase(USER_MOBILE)) {
			mGetFriendListItem.setUserMobile(tempVal);
		}else if (qName.equalsIgnoreCase(USER_COUNTRY)) {
			
			mGetFriendListItem.setUserCountry(tempVal);
		}else if (qName.equalsIgnoreCase(USER_STATE)) {
			mGetFriendListItem.setUserState(tempVal);
		}else if (qName.equalsIgnoreCase(USER_CITY)) {
			
			mGetFriendListItem.setUserCity(tempVal);
		}else if (qName.equalsIgnoreCase(USER_PIN_CODE)) {
			mGetFriendListItem.setUserPinCode(tempVal);
		}else if (qName.equalsIgnoreCase(USER_FB_LOGIN)) {
			
			mGetFriendListItem.setUserFbLogin(tempVal);
		}else if (qName.equalsIgnoreCase(USER_ACTIVE)) {
			mGetFriendListItem.setUserActive(tempVal);
		}else if (qName.equalsIgnoreCase(USER_AGE)) {
			
			mGetFriendListItem.setUserAge(tempVal);
		}else if (qName.equalsIgnoreCase(USER_IMAGE)) {
			mGetFriendListItem.setUserImage(tempVal);
		}
		
		
		
		
		/*	
		if(qName.equalsIgnoreCase("User")){
			mFriendListItemsList.add(mGetFriendListItem);
		}else if (qName.equalsIgnoreCase("vc_image_url")) {
			mGetFriendListItem.setVc_image_url(tempVal);
			
		}else if (qName.equalsIgnoreCase("vc_user_code")) {
			mGetFriendListItem.setVc_user_code(tempVal);
		}else if (qName.equalsIgnoreCase("vc_user_name")) {
			mGetFriendListItem.setVc_user_name(tempVal);
		}else if (qName.equalsIgnoreCase("vc_user_gender")) {
			mGetFriendListItem.setVc_user_gender(tempVal);
			
		}else if (qName.equalsIgnoreCase("vc_date_of_birth")) {
			mGetFriendListItem.setVc_date_of_birth(tempVal);
			
		}else if ( qName.equalsIgnoreCase("vc_user_email")) {
			mGetFriendListItem.setVc_user_email(tempVal);
		}else if (qName.equalsIgnoreCase("vc_user_add")) {
			mGetFriendListItem.setVc_user_add(tempVal);
			
		}else if (qName.equalsIgnoreCase("vc_user_mobile_no")) {
			mGetFriendListItem.setVc_user_mobile_no(tempVal);
		}else if (qName.equalsIgnoreCase("vc_user_country")) {
		mGetFriendListItem.setVc_user_country(tempVal);	
		}else if (qName.equalsIgnoreCase("vc_user_state")) {
			mGetFriendListItem.setVc_user_state(tempVal);
		}else if (qName.equalsIgnoreCase("vc_user_city")) {
		mGetFriendListItem.setVc_user_city(tempVal);
		
		}else if (qName.equalsIgnoreCase("nu_pin_code")) {
			mGetFriendListItem.setNu_pin_code(tempVal);
		}else if (qName.equalsIgnoreCase("ch_fb_login_user")) {
			mGetFriendListItem.setCh_fb_login_user(tempVal);
			
		}else if (qName.equalsIgnoreCase("ch_user_active")) {
		mGetFriendListItem.setCh_user_active(tempVal);
		}else if (qName.equalsIgnoreCase("friend_count")) {
			mGetFriendListItem.setFriend_count(tempVal);
		}else if (qName.equalsIgnoreCase("Age")) {
			mGetFriendListItem.setAge(tempVal);
		}else if (qName.equalsIgnoreCase("pf_user_image")) {
			mGetFriendListItem.setPf_user_image(tempVal);
		}else if (qName.equalsIgnoreCase("IsFriend")) {
			mGetFriendListItem.setIsFriend(tempVal);
		}*/
	
	}

}
