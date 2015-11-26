package com.partyfinder.parser;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

import com.partyfinder.model.EventItem;
import com.partyfinder.model.FriendListItem;
import com.partyfinder.model.RecommendItem;
import com.partyfinder.model.VenuesItem;

public class RecommendParser extends DefaultHandler{
	private final String TAG="Recommend Parser";

	private VenuesItem mVenuesItem;
	private EventItem mEventItem;
	private FriendListItem mFriendListItem;
	private RecommendItem mRecommendItem;
	private StringBuilder tagValue=new StringBuilder();


	private final String RECOMMEND_DETAIL="recommendDetail";

	private final String EVENT_DETAIL="eventDetail";

	private final String IMAGE_URL="vc_image_url";//
	private final String EVENT_CODE="vc_event_code";
	private final String EVENT_NAME="vc_event_name";
	private final String COMPANY_NAME="vc_company_name";//
	private final String COMPANY_CODE="vc_company_code";//
	private final String HOT_EVENT="ch_hot_event";
	private final String RECOMMEND_EVENT="ch_recommend_event";
	private final String FAVOURITE="ch_favourite";
	private final String IS_PUBLISH="ch_is_publish";
	private final String EVENT_ADDR="vc_event_addr";
	private final String EVENT_CITY="vc_event_city";
	private final String EVENT_STATE="vc_event_state";
	private final String EVENT_COUNTRY="vc_event_country";//1
	private final String PIN_CODE="nu_pin_code";//
	private final String EVENT_DATE="dt_event_date";
	private final String EVENT_START_TIME="dt_event_start_time";
	private final String EVENT_END_TIME="dt_event_end_time";
	private final String EVENT_DESCRIPTION="dt_event_description";
	private final String PLANT_NAME ="vc_plant_name ";
	private final String LATITUDE="vc_latitude";
	private final String LONGITUDE="vc_longitude";
	private final String UPDATE_DATE="dt_update_date";
	private final String NO_OF_TICKET="vc_no_of_ticket";
	private final String IN_QTY="in_qty";
	private final String GUEST_START_TIME="guest_start_time";
	private final String GUEST_END_TIME="guest_end_time";
	private final String COMMISION="commission";


	private final String USER_DETAIL="userDetail";

	//private final String USER_IMAGE_URL="vc_image_url";
	private final String USER_CODE="vc_user_code";
	private final String USER_NAME="vc_user_name";
	private final String USER_GENDER ="vc_user_gender ";
	private final String DATE_OF_BIRTH="vc_date_of_birth";
	private final String USER_EMAIL="vc_user_email";
	private final String USER_ADD="vc_user_add";
	private final String USER_MOBILE_NO ="vc_user_mobile_no ";
	private final String USER_COUNTRY="vc_user_country";
	private final String USER_STATE="vc_user_state";
	private final String USER_CITY="vc_user_city";
	//private final String USER_PIN_CODE ="nu_pin_code ";
	private final String FB_LOGIN_USER ="ch_fb_login_user ";
	private final String USER_ACTIVE="ch_user_active";

	private final String VENUE_DETAIL="venueDetail";

	private final String VENUE_IMAGE_URL="vc_image_url";
	//private final String VENUE_COMPANY_CODE="vc_company_code";
	//private final String VENUE_COMPANY_NAME="vc_company_name";
	//private final String VENUE_USER_CODE="vc_user_code";
	private final String NOMEFANTASIA="NomeFantasia";
	private final String ADDR_STREET="vc_addr_street";
	private final String NU_ADDR_NUMBER="nu_addr_number";
	private final String COMERCIAL_TEL="vc_comercial_tel";
	private final String CITY_NAME="vc_city_name";
	private final String STATE_NAME="vc_state_name";
	private final String VENUE_COUNTRY_NAME="vc_country_name";
	private final String ZIP_CODE="nu_zip_code";
	private final String DESCRIPTION="vc_description";
	private final String TOTALLIKE="TotalLike";
	private final String LIKE_STATUS="vc_like_status";
	private final String FAV_VENUE="vc_fav_venue";

	private boolean eventDetailFlag;
	private boolean userDetailFlag;
	private boolean venueDetailFlag;

	private ArrayList<RecommendItem> mRecommendedArrayList;
	public RecommendParser() {
		// TODO Auto-generated constructor stub
		mRecommendedArrayList=new ArrayList<RecommendItem>();
	}

	public ArrayList<RecommendItem> getRecommendedItem(){
		return mRecommendedArrayList;

	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		// TODO Auto-generated method stub
		super.startElement(uri, localName, qName, attributes);
		tagValue.setLength(0);
		if(qName.equalsIgnoreCase(RECOMMEND_DETAIL)){
			mRecommendItem=new RecommendItem();
		}else
			if(qName.equalsIgnoreCase(EVENT_DETAIL)){
				mEventItem=new EventItem();
				eventDetailFlag=true;
			}else
				if(qName.equalsIgnoreCase(USER_DETAIL)){
					mFriendListItem=new FriendListItem();
					userDetailFlag=true;
				}else
					if(qName.equalsIgnoreCase(VENUE_DETAIL)){
						mVenuesItem=new VenuesItem();
						venueDetailFlag=true;
					}
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		// TODO Auto-generated method stub
		super.characters(ch, start, length);
		tagValue.append(ch,start,length);
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		// TODO Auto-generated method stub
		super.endElement(uri, localName, qName);
		String tagElement=tagValue.toString();
		if(qName.equalsIgnoreCase(RECOMMEND_DETAIL)){
			mRecommendedArrayList.add(mRecommendItem);

		}else if(qName.equalsIgnoreCase(EVENT_DETAIL)){
			mRecommendItem.setmObject(mEventItem);
			eventDetailFlag=false;
		}else if(qName.equalsIgnoreCase(VENUE_DETAIL)){
			mRecommendItem.setmObject(mVenuesItem);
			venueDetailFlag=false;
		}else if(qName.equalsIgnoreCase(USER_DETAIL)){
			mRecommendItem.setmFriendItem(mFriendListItem);
			userDetailFlag=false;
		}else if(qName.equalsIgnoreCase(IMAGE_URL)){
			if(eventDetailFlag){
				mEventItem.setVcImageUrl(tagElement);
				Log.i("", "Tag Value :"+ tagElement);
				//					eventDetailFlag=false;

			}else if(userDetailFlag){
				mFriendListItem.setImageUrl(tagElement);
				Log.i("", "Tag Value :"+ tagElement);
				//					userDetailFlag=false;
			}else{
				mVenuesItem.setVc_image_url(tagElement);
				Log.i("", "Tag Value :"+ tagElement);
				//					venueDetailFlag=false;
			}
		}else if (qName.equalsIgnoreCase(EVENT_CODE)) {
			mEventItem.setVcEventCode(tagElement);
			Log.i("", "Tag Value :"+ tagElement);
		}else if (qName.equalsIgnoreCase(EVENT_NAME)) {
			mEventItem.setVcEventName(tagElement);
			Log.i("", "Tag Value :"+ tagElement);
		}else if (qName.equalsIgnoreCase(COMPANY_NAME)) {

			if(eventDetailFlag){
				mEventItem.setVcCompanyName(tagElement);
				Log.i("", "Tag Value event company name :"+ tagElement);
				//					eventDetailFlag=false;
			}else if (userDetailFlag) {
				mFriendListItem.setCompanyName(tagElement);
				Log.i("", "Tag Value user company name :"+ tagElement);
				//					userDetailFlag=false;
			}else if(venueDetailFlag){
				mVenuesItem.setCompanyName(tagElement);
				Log.i("", "Tag Value venue name :"+ tagElement);

				//venueDetailFlag=false;
			}
		}else if (qName.equalsIgnoreCase(COMPANY_CODE)) {
			if(eventDetailFlag){
				mEventItem.setVcCompanyCode(tagElement);
				Log.i("", "Tag Value :"+ tagElement);
				//					eventDetailFlag=false;
			}else if (userDetailFlag) {
				mFriendListItem.setCompanyCode(tagElement);
				Log.i("", "Tag Value :"+ tagElement);
				//					userDetailFlag=false;
			}else if(venueDetailFlag){
				mVenuesItem.setVc_company_code(tagElement);
				Log.i("", "Tag Value :"+ tagElement);
				//					venueDetailFlag=false;
			}
		}else if (qName.equalsIgnoreCase(HOT_EVENT)) {
			mEventItem.setChHotEvent(tagElement);
			Log.i("", "Tag Value :"+ tagElement);

		}else if (qName.equalsIgnoreCase(RECOMMEND_EVENT)) {
			mEventItem.setChRecomendEvent(tagElement);
			Log.i("", "Tag Value :"+ tagElement);

		}else if (qName.equalsIgnoreCase(FAVOURITE)) {
			mEventItem.setChFavourite(tagElement);
			Log.i("", "Tag Value :"+ tagElement);

		}else if (qName.equalsIgnoreCase(IS_PUBLISH)) {
			mEventItem.setChIsPublish(tagElement);
			Log.i("", "Tag Value :"+ tagElement);

		}else if (qName.equalsIgnoreCase(EVENT_ADDR)) {
			mEventItem.setVcEventAdd(tagElement);
		}else if (qName.equalsIgnoreCase(EVENT_CITY)) {
			mEventItem.setVcEventCity(tagElement);
			Log.i("", "Tag Value :"+ tagElement);

		}else if (qName.equalsIgnoreCase(EVENT_STATE)) {
			mEventItem.setVcEventState(tagElement);
			Log.i("", "Tag Value :"+ tagElement);

		}else if (qName.equalsIgnoreCase(EVENT_COUNTRY)) {
			mEventItem.setVcEventCountry(tagElement);
			Log.i("", "Tag Value :"+ tagElement);

		}else if (qName.equalsIgnoreCase(PIN_CODE)) {

			if(eventDetailFlag){
				mEventItem.setNuPinCode(tagElement);
				Log.i("", "Tag Value :"+ tagElement);
				//eventDetailFlag=false;
			}else if (userDetailFlag) {
				mFriendListItem.setUserPinCode(tagElement);
				//userDetailFlag=false;
			}else if(venueDetailFlag){
				mVenuesItem.setPinCode(tagElement);
				//venueDetailFlag=false;
			}
		}else if (qName.equalsIgnoreCase(EVENT_DATE)) {
			mEventItem.setDtEventDate(tagElement);
			Log.i("", "Tag Value :"+ tagElement);

		}else if (qName.equalsIgnoreCase(EVENT_START_TIME)) {
			mEventItem.setDtEventStartTime(tagElement);
			Log.i("", "Tag Value :"+ tagElement);

		}else if (qName.equalsIgnoreCase(EVENT_END_TIME)) {
			mEventItem.setDtEventEndTime(tagElement);
			Log.i("", "Tag Value :"+ tagElement);

		}else if (qName.equalsIgnoreCase(EVENT_DESCRIPTION)) {
			mEventItem.setDtEventDesc(tagElement);
			Log.i("", "Tag Value :"+ tagElement);

		}else if (qName.equalsIgnoreCase(PLANT_NAME)) {
			mEventItem.setVcPlantName(tagElement);
			Log.i("", "Tag Value :"+ tagElement);

		}else if (qName.equalsIgnoreCase(LATITUDE)) {
			mEventItem.setVcLatitude(tagElement);
			Log.i("", "Tag Value :"+ tagElement);

		}else if (qName.equalsIgnoreCase(LONGITUDE)) {
			mEventItem.setVcLongitude(tagElement);
			Log.i("", "Tag Value :"+ tagElement);

		}else if (qName.equalsIgnoreCase(UPDATE_DATE)) {
			mEventItem.setUpdateDate(tagElement);
			Log.i("", "Tag Value :"+ tagElement);

		}else if (qName.equalsIgnoreCase(NO_OF_TICKET)) {
			mEventItem.setNoOfTicket(tagElement);
			Log.i("", "Tag Value :"+ tagElement);

		}else if (qName.equalsIgnoreCase(IN_QTY)) {
			mEventItem.setInQty(tagElement);
			Log.i("", "Tag Value :"+ tagElement);

		}else if (qName.equalsIgnoreCase(GUEST_START_TIME)) {
			mEventItem.setGuestStartTime(tagElement);
			Log.i("", "Tag Value :"+ tagElement);

		}else if (qName.equalsIgnoreCase(GUEST_END_TIME)) {
			mEventItem.setGuestEndTime(tagElement);
			Log.i("", "Tag Value :"+ tagElement);

		}else if(qName.equalsIgnoreCase(COMMISION)){
			mEventItem.setCommision(tagElement);
		}



		else if (qName.equalsIgnoreCase(USER_NAME)) {
			mFriendListItem.setUserName(tagElement);
			Log.i("", "Tag Value :"+ tagElement);
		}
		else if (qName.equalsIgnoreCase(USER_GENDER)) {
			mFriendListItem.setUserCode(tagElement);
			Log.i("", "Tag Value :"+ tagElement);
		}
		else if (qName.equalsIgnoreCase(DATE_OF_BIRTH)) {
			mFriendListItem.setUserDOB(tagElement);
			Log.i("", "Tag Value :"+ tagElement);
		}
		else if (qName.equalsIgnoreCase(USER_EMAIL)) {
			mFriendListItem.setUserEmail(tagElement);
			Log.i("", "Tag Value :"+ tagElement);
		}
		else if (qName.equalsIgnoreCase(USER_ADD)) {
			mFriendListItem.setUserAddress(tagElement);
			Log.i("", "Tag Value :"+ tagElement);
		}
		else if (qName.equalsIgnoreCase(USER_MOBILE_NO)) {
			mFriendListItem.setUserMobile(tagElement);
			Log.i("", "Tag Value :"+ tagElement);
		}
		else if (qName.equalsIgnoreCase(USER_COUNTRY)) {
			mFriendListItem.setUserCountry(tagElement);
			Log.i("", "Tag Value :"+ tagElement);
		}

		else if (qName.equalsIgnoreCase(FB_LOGIN_USER)) {
			mFriendListItem.setUserFbLogin(tagElement);
			Log.i("", "Tag Value :"+ tagElement);
			
		}else if (qName.equalsIgnoreCase(USER_CODE)) {
				if(eventDetailFlag){
					mEventItem.setUserCode(tagElement);
				}else if (userDetailFlag) {
					mFriendListItem.setUserCode(tagElement);
				}else {
					mVenuesItem.setVc_user_code(tagElement);
				}
			
			

		}
		
		
		else if (qName.equalsIgnoreCase(USER_ACTIVE)) {
			mFriendListItem.setUserActive(tagElement);
			Log.i("", "Tag Value :"+ tagElement);

		}else if (qName.equalsIgnoreCase(USER_STATE)) {
			mFriendListItem.setUserState(tagElement);
			Log.i("", "Tag Value :"+ tagElement);
		}else if (qName.equalsIgnoreCase(USER_CITY)) {
			mFriendListItem.setUserCity(tagElement);
			Log.i("", "Tag Value :"+ tagElement);
		}


		else if (qName.equalsIgnoreCase(NOMEFANTASIA)) {
			mVenuesItem.setNomeFantasia(tagElement);
		}else if (qName.equalsIgnoreCase(ADDR_STREET)) {
			mVenuesItem.setVc_addr_street(tagElement);
		}else if (qName.equalsIgnoreCase(NU_ADDR_NUMBER)) {
			mVenuesItem.setNu_addr_number(tagElement);
		}else if (qName.equalsIgnoreCase(COMERCIAL_TEL)) {
			mVenuesItem.setVc_comercial_tel(tagElement);
		}else if (qName.equalsIgnoreCase(ZIP_CODE)) {
			mVenuesItem.setNu_zip_code(tagElement);
		}else if (qName.equalsIgnoreCase(DESCRIPTION)) {
			mVenuesItem.setVc_description(tagElement);
		}else if (qName.equalsIgnoreCase(TOTALLIKE)) {
			mVenuesItem.setTotalLike(tagElement);
		}else if (qName.equalsIgnoreCase(LIKE_STATUS)) {
			mVenuesItem.setVc_like_status(tagElement);
			Log.i("", "Tag Value :"+ tagElement);
		}else if (qName.equalsIgnoreCase(FAV_VENUE)) {
			mVenuesItem.setVc_fav_venue(tagElement);
			Log.i("", "Tag Value :"+ tagElement);
		}else if (qName.equalsIgnoreCase(VENUE_COUNTRY_NAME)) {
			mVenuesItem.setVc_country_name(tagElement);
			Log.i("", "Tag Value :"+ tagElement);
		}else if (qName.equalsIgnoreCase(EVENT_COUNTRY)) {
			mEventItem.setVcEventCountry(tagElement);
		}

		else if (qName.equalsIgnoreCase(CITY_NAME)) {
			mVenuesItem.setVc_city_name(tagElement);
		}else if (qName.equalsIgnoreCase(STATE_NAME)) {
			mVenuesItem.setVc_state_name(tagElement);
		}

	}
}









