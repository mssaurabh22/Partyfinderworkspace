package com.partyfinder.parser;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.R.string;
import android.util.Log;

import com.partyfinder.model.ActivityFeedItem;
import com.partyfinder.model.GetEventAgendaItem;

public class GetEventAgendaParser extends DefaultHandler {

	static String TAG = "GetEventagenda List ";
	private ArrayList<GetEventAgendaItem> mAgendaItemsList;
	private String tempVal = null;
	private GetEventAgendaItem mAgendaItem;

	public GetEventAgendaParser() {
		mAgendaItemsList= new ArrayList<GetEventAgendaItem>();
	}

	public ArrayList<GetEventAgendaItem> getEventaAgenda(){
		return mAgendaItemsList;
	}
	//Event Handler
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		// TODO Auto-generated method stub
		tempVal = "";
		if(qName.equalsIgnoreCase("Event")){
			//create a new instance of ActivityFeedItem
			mAgendaItem = new GetEventAgendaItem();
		}
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		//read the text of each xml node
		tempVal = new String(ch, start, length);
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if(qName.equalsIgnoreCase("Event")){
			mAgendaItemsList.add(mAgendaItem);

		}else if (qName.equalsIgnoreCase("vc_image_url")) {
			mAgendaItem.setVc_image_url(tempVal);

		}else if (qName.equalsIgnoreCase("vc_event_code")) {
			mAgendaItem.setVc_event_code(tempVal);
		}else if (qName.equalsIgnoreCase("vc_event_name")) {
			mAgendaItem.setVc_event_name(tempVal);
		}else if (qName.equalsIgnoreCase("vc_company_name")) {
			mAgendaItem.setVc_company_name(tempVal);
		}else if (qName.equalsIgnoreCase("vc_company_code")) {
			mAgendaItem.setVc_company_code(tempVal);
		}else if (qName.equalsIgnoreCase("ch_hot_event")) {
			mAgendaItem.setCh_hot_event(tempVal);

		}else if (qName.equalsIgnoreCase("ch_recommend_event")) {
			mAgendaItem.setCh_recommend_event(tempVal);
		}else if (qName.equalsIgnoreCase("ch_favourite")) {
			mAgendaItem.setCh_favourite(tempVal);
		}else if (qName.equalsIgnoreCase("ch_is_publish")) {
			mAgendaItem.setCh_is_publish(tempVal);
		}else if (qName.equalsIgnoreCase("vc_event_addr")) {
			mAgendaItem.setVc_event_addr(tempVal);
		}else if (qName.equalsIgnoreCase("vc_event_city")) {
			mAgendaItem.setVc_event_city(tempVal);

		}else if (qName.equalsIgnoreCase("vc_event_state")) {
			mAgendaItem.setVc_event_state(tempVal);
		}else if (qName.equalsIgnoreCase("vc_event_country")) {
			mAgendaItem.setVc_event_country(tempVal);
		}else if (qName.equalsIgnoreCase("nu_pin_code")) {
			mAgendaItem.setNu_pin_code(tempVal);

		}else if (qName.equalsIgnoreCase("dt_event_date")) {
			mAgendaItem.setDt_event_date(tempVal);
		}else if (qName.equalsIgnoreCase("dt_event_start_time")) {
			mAgendaItem.setDt_event_start_time(tempVal);
		}else if (qName.equalsIgnoreCase("dt_event_end_time")) {
			mAgendaItem.setDt_event_end_time(tempVal);
		}else if (qName.equalsIgnoreCase("dt_event_description")) {
			mAgendaItem.setDt_event_description(tempVal);
		}else if (qName.equalsIgnoreCase("vc_plant_name")) {
			mAgendaItem.setVc_plant_name(tempVal);
		}else if (qName.equalsIgnoreCase("vc_latitude")) {
			mAgendaItem.setVc_latitude(tempVal);
		}else if (qName.equalsIgnoreCase("vc_longitude")) {
			mAgendaItem.setVc_longitude(tempVal);
		}else if (qName.equalsIgnoreCase("vc_no_of_ticket")) {
			mAgendaItem.setVc_no_of_ticket(tempVal);
		}else if (qName.equalsIgnoreCase("in_qty")) {
			mAgendaItem.setIn_qty(tempVal);
		}

	}
}
