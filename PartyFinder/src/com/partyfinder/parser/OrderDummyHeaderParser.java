package com.partyfinder.parser;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class OrderDummyHeaderParser extends DefaultHandler {

	private String status;
	
	private final String STATUS="status";
	private StringBuilder strBuilder=new StringBuilder();
	public OrderDummyHeaderParser(){
		
	}
	
	

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		// TODO Auto-generated method stub
		strBuilder.setLength(0);
		super.startElement(uri, localName, qName, attributes);
	}



	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {

		strBuilder.append(ch,start,length);
		super.characters(ch, start, length);
	}



	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		String tempVal=strBuilder.toString();
		if(qName.equalsIgnoreCase(STATUS)){
			status=tempVal;
		}
		super.endElement(uri, localName, qName);
	}



	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
	
	
	
}
