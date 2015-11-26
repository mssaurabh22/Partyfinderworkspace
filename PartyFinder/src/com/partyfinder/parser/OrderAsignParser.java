package com.partyfinder.parser;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.partyfinder.model.OrderAssignItem;

public class OrderAsignParser extends DefaultHandler {
	private final String ORDER_QR_CODE="vc_image_url";
	private final String ORDER_CODE="OCode";
	private OrderAssignItem orderItem;
	private final String TABLE="table";
	private StringBuilder tagVal;
	
	public OrderAssignItem getOrderItem() {
		return orderItem;
	}
	public void setOrderItem(OrderAssignItem orderItem) {
		this.orderItem = orderItem;
	}
	public OrderAsignParser(){
		tagVal=new StringBuilder();
	}
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		// TODO Auto-generated method stub
		tagVal.setLength(0);
		if(qName.equalsIgnoreCase(TABLE)){
			orderItem=new OrderAssignItem();
		}
		super.startElement(uri, localName, qName, attributes);
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		// TODO Auto-generated method stub
		tagVal.append(ch,start,length);
		super.characters(ch, start, length);
	}


	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		// TODO Auto-generated method stub
		String tempVal=tagVal.toString();
		if(qName.equalsIgnoreCase(TABLE)){
			
		}else if(qName.equalsIgnoreCase(ORDER_QR_CODE)){
			orderItem.setImageUrl(tempVal);
		}else if(qName.equalsIgnoreCase(ORDER_CODE)){
			orderItem.setOrderCode(tempVal);
		}
		super.endElement(uri, localName, qName);
	}





}
