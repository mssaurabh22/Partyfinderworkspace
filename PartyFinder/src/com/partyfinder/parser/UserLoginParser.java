package com.partyfinder.parser;

import java.io.UnsupportedEncodingException;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.text.TextUtils;
import android.util.Log;

import com.partyfinder.AppConstant.PartyFinderConstants;
import com.partyfinder.model.UserLoginItem;



public class UserLoginParser extends DefaultHandler {
	
	/**
	 * @author Man Square Softwares, aru khanka
	 */
 
 private final String USER="userDetail";	
 private final String IMAGE_URL=" imageUrl";
 private final String USER_CODE="userCode";
 private final String USER_NAME="userName";
 private final String USER_PASSWD="userPassword";
 private final String USER_GENDER="userGender";
 private final String USER_DOB="userDOB";
 private final String USER_EMAIL="userEmail";
 private final String USER_ADD="userAddress";
 private final String USER_MOBILE="userMobile";
 private final String USER_COUNTRY="userCountry";
 private final String USER_STATE="userState";
 private final String USER_CITY="userCity";
 private final String USER_PINCODE="userPinCode";
 private final String USER_LOGIN="userFbLogin";
 private final String USER_ACTIVE="userActive";
 private final String USER_FRIENDCOUNT="userFriendCount";
 private final String USER_AGE="userAge";
 private final String USER_IMAGE="userImage";
 
 private final String USER_UUID="userUUID";
 private final String USER_DOCUMENT="userDocument";
 private final String USER_LOGIN_TIME="userLoginTime";
 private final String USER_STREET_NO="vc_street_no";
 private final String USER_NEIGHBORHOOD="userNeighborhood";
 
 private UserLoginItem userLoginItem= null;
 private boolean mCurrentElement;
 private StringBuilder textItem=new StringBuilder();

@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		// TODO Auto-generated method stub
		textItem.setLength(0);
		if(qName.equalsIgnoreCase(USER)){
			userLoginItem= new UserLoginItem();
			Log.i("","End tag : "+qName);
		}else if(qName.equalsIgnoreCase(IMAGE_URL)){
			mCurrentElement=true;
			Log.i("","Image Url : "+qName);
		}else if(qName.equalsIgnoreCase(USER_CODE)){
			mCurrentElement=true;
		}else if(qName.equalsIgnoreCase(USER_NAME)){
			mCurrentElement=true;
			Log.i("","End tag : "+qName);
		}else if(qName.equalsIgnoreCase(USER_PASSWD)){
			mCurrentElement=true;
		}else if(qName.equalsIgnoreCase(USER_GENDER)){
			mCurrentElement=true;
		}else if(qName.equalsIgnoreCase(USER_DOB)){
			mCurrentElement=true;
		}else if(qName.equalsIgnoreCase(USER_EMAIL)){
			mCurrentElement=true;
		}else if(qName.equalsIgnoreCase(USER_ADD)){
			mCurrentElement=true;
		}else if(qName.equalsIgnoreCase(USER_MOBILE)){
			mCurrentElement=true;
		}else if(qName.equalsIgnoreCase(USER_COUNTRY)){
			mCurrentElement=true;
		}else if (qName.equalsIgnoreCase(USER_STATE)){
			mCurrentElement=true;
		}else if (qName.equalsIgnoreCase(USER_CITY)){
			mCurrentElement=true;
		}else if(qName.equalsIgnoreCase(USER_PINCODE)){
			mCurrentElement=true;
		}else if(qName.equalsIgnoreCase(USER_LOGIN)){
			mCurrentElement=true;
		}else if(qName.equalsIgnoreCase(USER_ACTIVE)){
			mCurrentElement=true;
		}else if(qName.equalsIgnoreCase(USER_FRIENDCOUNT)){
			mCurrentElement=true;
		}else if(qName.equalsIgnoreCase(USER_AGE)){
			mCurrentElement=true;
		}else if(qName.equalsIgnoreCase(USER_IMAGE)){
			mCurrentElement=true;
		}else if (qName.equalsIgnoreCase(USER_UUID)) {
			mCurrentElement=true;
		}else if (qName.equalsIgnoreCase(USER_DOCUMENT)) {
			mCurrentElement=true;
		}else if (qName.equalsIgnoreCase(USER_LOGIN_TIME)) {
			mCurrentElement=true;
		}else if (qName.equalsIgnoreCase(USER_STREET_NO)) {
			mCurrentElement=true;
		}else if(qName.equalsIgnoreCase(USER_NEIGHBORHOOD)) {
			mCurrentElement=true;
		
			}else{
			mCurrentElement=false;
		}
		//super.startElement(uri, localName, qName, attributes);
	}

	
	
	
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		// TODO Auto-generated method stub
		if(mCurrentElement){
//			tempVal=new String(ch,start,length);
			textItem.append(ch,start,length);
		}
		
		//super.characters(ch, start, length);
	}
	
@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		// TODO Auto-generated method stub
	
	String tempVal=textItem.toString();
	

	if(qName.equalsIgnoreCase(IMAGE_URL)){
		userLoginItem.setVc_image_url(tempVal);
		mCurrentElement=false;

	}else if(qName.equals(USER_CODE)){
		userLoginItem.setVc_user_code(tempVal);
		Log.i("","Value of tempVal :"+tempVal);
		mCurrentElement=false;

	}
	else if(qName.equalsIgnoreCase(USER_NAME)){
		userLoginItem.setVc_user_name(tempVal);
		mCurrentElement=false;
		
	}else if(qName.equalsIgnoreCase(USER_PASSWD)){
		userLoginItem.setVc_user_pwd(tempVal);
		mCurrentElement=false;
		Log.i("","End tag Password : "+userLoginItem.getVc_user_pwd());
	}else if(qName.equalsIgnoreCase(USER_GENDER)){
		userLoginItem.setVc_user_gender(tempVal);
		mCurrentElement=false;
	}else if(qName.equalsIgnoreCase(USER_DOB)){
		userLoginItem.setVc_date_of_birth(tempVal);
		mCurrentElement=false;
	}else if(qName.equalsIgnoreCase(USER_EMAIL)){
		userLoginItem.setVc_user_email(tempVal);
		mCurrentElement=false;
		Log.i("","End tag user email : "+userLoginItem.getVc_user_email());
	}else if(qName.equalsIgnoreCase(USER_ADD)){
		userLoginItem.setVc_user_add(tempVal);
		mCurrentElement=false;
	}else if(qName.equalsIgnoreCase(USER_MOBILE)){
		userLoginItem.setVc_user_mobile_no(tempVal);
		mCurrentElement=false;
	}else if(qName.equalsIgnoreCase(USER_COUNTRY)){
		userLoginItem.setVc_user_country(tempVal);
		mCurrentElement=false;
	}else if (qName.equalsIgnoreCase(USER_STATE)){
//		if(!TextUtils.isEmpty(tempVal)){
			userLoginItem.setVc_user_state(tempVal);
			mCurrentElement=false;
//		}else{
//			userLoginItem.setVc_user_state("na");
//		}
		mCurrentElement=false;
	}else if (qName.equalsIgnoreCase(USER_CITY)){
		Log.i("", "City Tag :"+qName);
		userLoginItem.setVc_user_city(tempVal);
		mCurrentElement=false;
	}else if(qName.equalsIgnoreCase(USER_PINCODE)){
		userLoginItem.setNu_pin_code(tempVal);
		mCurrentElement=false;
	}else if(qName.equalsIgnoreCase(USER_LOGIN)){
		userLoginItem.setCh_fb_login_user(tempVal);
		mCurrentElement=false;
	}else if(qName.equalsIgnoreCase(USER_ACTIVE)){
		userLoginItem.setCh_user_active(tempVal);
		mCurrentElement=false;
	}else if(qName.equalsIgnoreCase(USER_FRIENDCOUNT)){
		userLoginItem.setFriend_count(tempVal);
		mCurrentElement=false;
	}else if(qName.equalsIgnoreCase(USER_AGE)){
		userLoginItem.setAge(tempVal);
		mCurrentElement=false;
	}else if(qName.equalsIgnoreCase(USER_IMAGE)){
		userLoginItem.setPf_user_image(tempVal);
		mCurrentElement=false;
	}else if (qName.equalsIgnoreCase(USER_UUID)) {
		userLoginItem.setUUID(tempVal);
		mCurrentElement=false;
	}else if (qName.equalsIgnoreCase(USER_DOCUMENT)) {
		userLoginItem.setVc_user_document(tempVal);
		mCurrentElement=false;
	}else if (qName.equalsIgnoreCase(USER_LOGIN_TIME)) {
		userLoginItem.setVc_login_time(tempVal);
	}else if (qName.equalsIgnoreCase(USER_STREET_NO)) {
		userLoginItem.setVc_user_add_street_no(tempVal);
		mCurrentElement=false;
	}else if (qName.equalsIgnoreCase(USER_NEIGHBORHOOD)) {
		userLoginItem.setVc_neighborhood(tempVal);
		mCurrentElement=false;
	}
	super.endElement(uri, localName, qName);
	}

public UserLoginItem getUserInfo() {
	return userLoginItem;
}
public void setUserInfo(UserLoginItem userInfo) {
	this.userLoginItem = userInfo;
}
}
