package com.partyfinder.parser;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

import com.partyfinder.model.EditUserInfoItem;



public class EditUserInfoParser extends DefaultHandler {
	
	private static String TAG = "EditUsersStatus";
	private String tempVal = null;
	private EditUserInfoItem mEditUserInfoItem;
	private String TABLE="table";
	private String STATUS="status";
	
	public EditUserInfoParser(){
		
	}
	public EditUserInfoItem getEditUserInfoStatusCode(){
		return mEditUserInfoItem;
	}
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		Log.d("", TAG + "qName Value "+qName);
		// TODO Auto-generated method stub
		//super.startElement(uri, localName, qName, attributes);
		tempVal = "";
		if(qName.equalsIgnoreCase(TABLE)){
			mEditUserInfoItem = new EditUserInfoItem();
		}
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		// TODO Auto-generated method stub
		//read the text of each node
		tempVal = new String(ch, start, length);
	}
	
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		// TODO Auto-generated method stub
		//super.endElement(uri, localName, qName);
		if(qName.equalsIgnoreCase(STATUS)){
			mEditUserInfoItem.setStatusCode(tempVal);
			Log.d("", TAG + mEditUserInfoItem.getStatusCode());
		}
	}


}
