package com.partyfinder.parser;

import java.io.Serializable;
import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

import com.partyfinder.model.GetFriendListItem;
import com.partyfinder.model.FriendListItem;

public class GetFriendListParser1 extends DefaultHandler  {
	private String TAG="Values";
	//	private String tempVal=null;
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
	private final String USER_FRIEND_COUNT="userFriendCount";
	private final String USER_AGE="userAge";
	private final String USER_IMAGE="userImage";
	private final String IS_FRIEND="IsFriend";

	private FriendListItem mGetFriendListItem;

	private ArrayList<FriendListItem> nFriendListItemList;
	private FriendListItem nGetFriendListItem;
	private StringBuilder tagVal=new StringBuilder();

	public GetFriendListParser1() {
		// TODO Auto-generated constructor stub
		nFriendListItemList= new ArrayList<FriendListItem>();
	}
	public ArrayList<FriendListItem> getGetFriendListItem() {
		return nFriendListItemList;

	}
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		// TODO Auto-generated method stub
		super.characters(ch, start, length);
		//		tempVal= new String(ch,start,length);
		tagVal.append(ch,start,length);
	}
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		// TODO Auto-generated method stub
		super.endElement(uri, localName, qName);
		String tempVal=tagVal.toString();
		if(qName.equalsIgnoreCase(USER_DETAIL)){
			nFriendListItemList.add(nGetFriendListItem);

		}else if(qName.equalsIgnoreCase(IMAGE_URL)){
			nGetFriendListItem.setImageUrl(tempVal);

		}else if(qName.equalsIgnoreCase(USER_CODE)){
			nGetFriendListItem.setUserCode(tempVal);

		}else if(qName.equalsIgnoreCase(USER_NAME)){
			nGetFriendListItem.setUserName(tempVal);
			Log.i("", "user name :"+tempVal);
		}else if(qName.equalsIgnoreCase(USER_GENDER)){
			nGetFriendListItem.setUserGender(tempVal);

		}else if(qName.equalsIgnoreCase(USER_DOB)){
			nGetFriendListItem.setUserDOB(tempVal);

		}else if(qName.equalsIgnoreCase(USER_EMAIL)){
			nGetFriendListItem.setUserEmail(tempVal);

		}else if(qName.equalsIgnoreCase(USER_ADD)){
			nGetFriendListItem.setUserAddress(tempVal);

		}else if(qName.equalsIgnoreCase(USER_MOBILE)){
			nGetFriendListItem.setUserMobile(tempVal);

		}else if(qName.equalsIgnoreCase(USER_COUNTRY)){
			nGetFriendListItem.setUserCountry(tempVal);

		}else if(qName.equalsIgnoreCase(USER_STATE)){
			nGetFriendListItem.setUserState(tempVal);

		}else if(qName.equalsIgnoreCase(USER_CITY)){
			nGetFriendListItem.setUserCity(tempVal);

		}else if(qName.equalsIgnoreCase(USER_PIN_CODE)){
			nGetFriendListItem.setUserPinCode(tempVal);

		}else if(qName.equalsIgnoreCase(USER_FB_LOGIN)){
			nGetFriendListItem.setUserFbLogin(tempVal);

		}else if(qName.equalsIgnoreCase(USER_ACTIVE)){
			nGetFriendListItem.setUserActive(tempVal);

		}else if(qName.equalsIgnoreCase(USER_FRIEND_COUNT)){
			nGetFriendListItem.setUserFriendCount(tempVal);
			Log.i("", "Friends xx Friend...."+tempVal);

		}else if(qName.equalsIgnoreCase(USER_AGE)){
			nGetFriendListItem.setUserAge(tempVal);

		}else if(qName.equalsIgnoreCase(USER_IMAGE)){
			nGetFriendListItem.setUserImage(tempVal);

		}else if(qName.equalsIgnoreCase(IS_FRIEND)){
			nGetFriendListItem.setIsFriend(tempVal);
		}


	}
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		// TODO Auto-generated method stub
		super.startElement(uri, localName, qName, attributes);
		tagVal.setLength(0);

		if(qName.equalsIgnoreCase(USER_DETAIL)){
			nGetFriendListItem= new FriendListItem();
			Log.i("",""+TAG+qName);
		}


	}





}
