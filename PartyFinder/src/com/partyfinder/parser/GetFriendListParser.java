package com.partyfinder.parser;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

import com.partyfinder.model.GetFriendListItem;


public class GetFriendListParser extends DefaultHandler {
	
	private String tempVal=null;
	private GetFriendListItem mGetFriendListItem;
	private ArrayList<GetFriendListItem >mFriendListItemsList;
	private String TAG="Values";
	
	public GetFriendListParser() {
		// TODO Auto-generated constructor stub
	mFriendListItemsList= new ArrayList<GetFriendListItem>();
	}
	 public ArrayList<GetFriendListItem> getGetFriendListItem() {
		return mFriendListItemsList;
		
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		// TODO Auto-generated method stub
		tempVal="";
		if(qName.equalsIgnoreCase("userDetail"))
			mGetFriendListItem= new GetFriendListItem();
		Log.i("",""+TAG+qName);
		
	//	super.startElement(uri, localName, qName, attributes);
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
		if(qName.equalsIgnoreCase("userDetail")){
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
		}
	
	}

}
