package com.partyfinder.parser;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.partyfinder.model.VanuesLikeStatus;

public class VanuesLikeParser extends DefaultHandler {
	private final String TABLE="table";
	private final String STATUS="status";
	private String tempVal=null;
	private VanuesLikeStatus mVanueLike;
	
	public VanuesLikeParser() {
		// TODO Auto-generated constructor stub
	}
	public VanuesLikeStatus getVanueLikeStatus(){
		return mVanueLike;
		
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
			mVanueLike.setStatus(tempVal);
			
		}
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		// TODO Auto-generated method stub
		super.startElement(uri, localName, qName, attributes);
		tempVal = "";
		if(qName.equalsIgnoreCase(TABLE)){
			mVanueLike = new VanuesLikeStatus();
		}
		
	}
	
	

}
