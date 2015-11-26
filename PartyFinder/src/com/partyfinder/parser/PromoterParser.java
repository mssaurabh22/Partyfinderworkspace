package com.partyfinder.parser;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.partyfinder.model.PromoterItem;

public class PromoterParser extends DefaultHandler {

	private final String PROMOTER_DET="promoterDetail";
	private final String PROMOTER_IMAGE="promoterImage";
	private final String EVENT_CODE="eventCode";
	private final String PROMOTER_NAME="promoterName";
	private final String PROMOTER_CODE="promoterCode";
	private final String PROMOTER_TYPE="promoterType";
	
	private PromoterItem mItem;
	private ArrayList<PromoterItem> promList;
	private StringBuilder tagVal=new StringBuilder();
	public PromoterParser(){
		promList=new ArrayList<PromoterItem>();
		
	}

	
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		// TODO Auto-generated method stub
		tagVal.setLength(0);
		if(qName.equalsIgnoreCase(PROMOTER_DET)){
			mItem=new PromoterItem();
		}
		super.startElement(uri, localName, qName, attributes);
	}


	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		tagVal.append(ch,start,length);
		super.characters(ch, start, length);
	}


	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		String tempVal=tagVal.toString();
		if(qName.equalsIgnoreCase(PROMOTER_DET)){
			promList.add(mItem);
		}else if(qName.equalsIgnoreCase(PROMOTER_IMAGE)){
			mItem.setPromoterImage(tempVal);
		}else if(qName.equalsIgnoreCase(EVENT_CODE)){
			mItem.setEventCode(tempVal);
		}else if(qName.equalsIgnoreCase(PROMOTER_NAME)){
			mItem.setPromoterName(tempVal);
		}else if(qName.equalsIgnoreCase(PROMOTER_CODE)){
			mItem.setPromoterCode(tempVal);
		}else if(qName.equalsIgnoreCase(PROMOTER_TYPE)){
			mItem.setPromoterType(tempVal);
		}
		super.endElement(uri, localName, qName);
	}


	public ArrayList<PromoterItem> getPromList() {
		return promList;
	}


	public void setPromList(ArrayList<PromoterItem> promList) {
		this.promList = promList;
	}
	
	
	
	
}
