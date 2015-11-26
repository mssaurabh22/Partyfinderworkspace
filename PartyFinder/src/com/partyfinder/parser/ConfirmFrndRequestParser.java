package com.partyfinder.parser;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

import com.partyfinder.model.ConfirmFrndStatus;

public class ConfirmFrndRequestParser extends DefaultHandler{
	private final String TABLE="table";
	private final String STATUS="status";
	private static String TAG = "ConfirmFrndRequest Class";
	private String tempVal = null;
	private ConfirmFrndStatus mConfirmFrndStatus;

	public ConfirmFrndRequestParser(){
		
	}
	
	public ConfirmFrndStatus getConfirmStatusCode(){
		return mConfirmFrndStatus;
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
			mConfirmFrndStatus.setStatus(tempVal);
			Log.d("", TAG + mConfirmFrndStatus.getStatus());
		}
	}
	
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		// TODO Auto-generated method stub
		super.startElement(uri, localName, qName, attributes);
		
		tempVal = "";
		if(qName.equalsIgnoreCase(TABLE)){
			mConfirmFrndStatus=new ConfirmFrndStatus();
		}
		
	}
	
	
	
	

}
