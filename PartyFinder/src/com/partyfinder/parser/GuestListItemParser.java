package com.partyfinder.parser;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.partyfinder.model.GuestListStatus;
import com.partyfinder.model.VanuesLikeStatus;

public class GuestListItemParser extends DefaultHandler{
	private final String TABLE="table";
	private final String STATUS="status";
	private String tempVal=null;
	private GuestListStatus mGuestListStatus;
	
	public GuestListItemParser() {
		// TODO Auto-generated constructor stub
	}
	public GuestListStatus getStatus(){
		
		return mGuestListStatus;
		
	}
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		// TODO Auto-generated method stub
		super.startElement(uri, localName, qName, attributes);
		tempVal = "";
		if(qName.equalsIgnoreCase(TABLE)){
			mGuestListStatus = new GuestListStatus();
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
			mGuestListStatus.setStatus(tempVal);
			
		}
	}
	
	
	
}
