package com.partyfinder.parser;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

import com.partyfinder.model.ConfirmFrndStatus;

public class SubmitPromoterResponseParser extends DefaultHandler{
	private final String TABLE="table";
	private final String STATUS="status";
	private static String TAG = "Submit Promoter Class";
	private String tempVal = null;
	
	private String status;
	private StringBuilder tagVal=new StringBuilder();
	public SubmitPromoterResponseParser(){
		
	}
	
	
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		// TODO Auto-generated method stub
		super.characters(ch, start, length);
		tagVal.append(ch,start,length);
	}
	
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		// TODO Auto-generated method stub
		super.endElement(uri, localName, qName);
		String tempVal=tagVal.toString();
		if(qName.equalsIgnoreCase(STATUS)){
			
			status=tempVal;
		}
	}
	
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		// TODO Auto-generated method stub
		tagVal.setLength(0);
		super.startElement(uri, localName, qName, attributes);
		
		
		
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
