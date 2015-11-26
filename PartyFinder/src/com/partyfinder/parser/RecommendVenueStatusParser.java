package com.partyfinder.parser;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

import com.partyfinder.model.RecommendVenueStatus;
import com.partyfinder.model.RegistrationStatus;

public class RecommendVenueStatusParser extends DefaultHandler{
	private static String TAG = "RecommendVenueStatusParser";
	private final String TABLE="table";
	private final String STATUS="status";
	private String tempVal = null;
	private RecommendVenueStatus mRecommendVenueStatus;
	
	public RecommendVenueStatusParser() {
		// TODO Auto-generated constructor stub
	}
	
	public RecommendVenueStatus getRecommendVenueStatus(){
		return mRecommendVenueStatus;
		
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		// TODO Auto-generated method stub
		super.startElement(uri, localName, qName, attributes);
		tempVal = "";
		if(qName.equalsIgnoreCase(TABLE)){
			mRecommendVenueStatus = new RecommendVenueStatus();
		}
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		// TODO Auto-generated method stub
		super.characters(ch, start, length);
		tempVal = new String(ch, start, length);
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		// TODO Auto-generated method stub
		super.endElement(uri, localName, qName);
		if(qName.equalsIgnoreCase(STATUS)){
			mRecommendVenueStatus.setStatusCode(tempVal);
			Log.d("", TAG + mRecommendVenueStatus.getStatusCode());
		}
	}
	
	

}
