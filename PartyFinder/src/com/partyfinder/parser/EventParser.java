package com.partyfinder.parser;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

import com.partyfinder.model.EventItem;
import com.partyfinder.model.UpcomingEventItem;

public class EventParser extends DefaultHandler {
	private ArrayList<EventItem> EventItems;
	private String tempVal=null;
	private EventItem mEvenItem;
	
	private final String EVENT_DETAILS="eventDetails";
	private final String EVENT_DETAIL="eventDetail";
	private final String IMAGE_URL="vc_image_url";
	private final String EVENT_CODE="vc_event_code";
	private final String EVENT_NAME="vc_event_name";
	
	private final String COMPNY_NAME="vc_company_name";
	private final String COMPANY_CODE="vc_company_code";
	private final String HOT_EVENT="ch_hot_event";
	private final String RECOMEND_EVENT="ch_recommend_event";
	
	private final String FAVORITE="ch_favourite";
	private final String IS_PUBLISH="ch_is_publish";
	private final String EVENT_ADDRESS="vc_event_addr";
	private final String EVENT_CITY="vc_event_city";
	
	private final String EVENT_STATE="vc_event_state";
	private final String EVENT_COUNTRY="vc_event_country";
	private final String PINCODE="nu_pin_code";
	private final String EVENT_DATE="dt_event_date";
	
	private final String EVENT_START_TIME="dt_event_start_time";
	private final String EVENT_END_TIME="dt_event_end_time";
	private final String EVENT_DESC="dt_event_description";
	private final String PLANT_NAME="vc_plant_name";
	
	private final String LATITUDE="vc_latitude";
	private final String LONGITUDE="vc_longitude";
	private final String NO_OF_TICKET="vc_no_of_ticket";
	private final String QUANTITY="in_qty";
	
	private final String GUEST_ST_TIME="guest_start_time";
	private final String GUEST_END_TIME="guest_end_time";
	private final String COMMISION="commission";
	
	
	private StringBuilder tagVal=new StringBuilder();		
	
	public EventParser(){
		EventItems=new ArrayList<EventItem>();
	}
	
	public ArrayList<EventItem>getEventItems(){
		return EventItems;
		
	}
	//Event Handler
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		// TODO Auto-generated method stub
		tempVal = "";
		tagVal.setLength(0);
		/*if(qName.equalsIgnoreCase("Event"))*/
		if(qName.equalsIgnoreCase(EVENT_DETAIL)){
			//create a new instance of UpcomingEventItem
			mEvenItem=new EventItem();
		}
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		// TODO Auto-generated method stub
//		tempVal = new String(ch, start, length);
		tagVal.append(ch, start, length);
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		// TODO Auto-generated method stub
		/*if(qName.equalsIgnoreCase("Event"))*/
		tempVal=tagVal.toString();
		if(qName.equalsIgnoreCase(EVENT_DETAIL)){
			//add it to the ArrayList
			EventItems.add(mEvenItem);
		
		}else if(qName.equalsIgnoreCase(IMAGE_URL)){
			mEvenItem.setVcImageUrl(tempVal);
			Log.d("", "ImageUrl :"+ tempVal);
		}else if(qName.equalsIgnoreCase(COMPANY_CODE)){
			mEvenItem.setVcCompanyCode(tempVal);
			Log.d("", "ImageUrl :"+ tempVal);
		}else if(qName.equalsIgnoreCase(EVENT_NAME)){
			mEvenItem.setVcEventName(tempVal);
			Log.d("", "ImageUrl :"+ tempVal);
			
			
		}else if(qName.equalsIgnoreCase(EVENT_ADDRESS)){
			mEvenItem.setVcEventAdd(tempVal);
			Log.d("", "ImageUrl :"+ tempVal);
		}else if(qName.equalsIgnoreCase(EVENT_DATE)){
			mEvenItem.setDtEventDate(tempVal);
			Log.d("", "ImageUrl :"+ tempVal);
		}else if(qName.equalsIgnoreCase(EVENT_START_TIME)){
			mEvenItem.setDtEventStartTime(tempVal);
			Log.d("", "ImageUrl :"+ tempVal);
		}else if(qName.equalsIgnoreCase(EVENT_END_TIME)){
			mEvenItem.setDtEventEndTime(tempVal);
			Log.d("", "ImageUrl :"+ tempVal);
		}else if(qName.equalsIgnoreCase(EVENT_CODE)){
			mEvenItem.setVcEventCode(tempVal);
		}else if(qName.equalsIgnoreCase(COMPNY_NAME)){
			mEvenItem.setVcCompanyName(tempVal);
			
		}else if(qName.equalsIgnoreCase(EVENT_CITY)){
			mEvenItem.setVcEventCity(tempVal);
		}else if(qName.equalsIgnoreCase(EVENT_STATE)){
			mEvenItem.setVcEventState(tempVal);
		}else if(qName.equalsIgnoreCase(EVENT_DESC)){
			mEvenItem.setDtEventDesc(tempVal);
		}else if(qName.equalsIgnoreCase(HOT_EVENT)){
			mEvenItem.setChHotEvent(tempVal);
		}else if(qName.equalsIgnoreCase(RECOMEND_EVENT)){
			mEvenItem.setChRecomendEvent(tempVal);
		}else if(qName.equalsIgnoreCase(IS_PUBLISH)){
			mEvenItem.setChIsPublish(tempVal);
		}else if(qName.equalsIgnoreCase(FAVORITE)){
			mEvenItem.setChFavourite(tempVal);
		}else if(qName.equalsIgnoreCase(EVENT_COUNTRY)){
			mEvenItem.setVcEventCountry(tempVal);
		}else if(qName.equalsIgnoreCase(PINCODE)){
			mEvenItem.setNuPinCode(tempVal);
		}else if(qName.equalsIgnoreCase(PLANT_NAME)){
			mEvenItem.setVcPlantName(tempVal);
		}else if(qName.equalsIgnoreCase(LATITUDE)){
			mEvenItem.setVcLatitude(tempVal);
		}else if(qName.equalsIgnoreCase(LONGITUDE)){
			mEvenItem.setVcLongitude(tempVal);
		}else if(qName.equalsIgnoreCase(NO_OF_TICKET)){
			mEvenItem.setNoOfTicket(tempVal);
		}else if(qName.equalsIgnoreCase(QUANTITY)){
			mEvenItem.setNoOfTicket(tempVal);
		}else if(qName.equalsIgnoreCase(GUEST_ST_TIME)){
			mEvenItem.setGuestStartTime(tempVal);
		}else if(qName.equalsIgnoreCase(GUEST_END_TIME)){
			mEvenItem.setGuestStartTime(tempVal);
		}else if(qName.equalsIgnoreCase(COMMISION)){
			mEvenItem.setCommision(tempVal);
		}
		
	}
}
