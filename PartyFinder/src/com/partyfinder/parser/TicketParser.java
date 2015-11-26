package com.partyfinder.parser;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

import com.partyfinder.model.TicketDetailsItem;

public class TicketParser extends DefaultHandler {

	private final String TICKET_DETAILS="ticketDetails";
	private final String TICKET_DETAIL="ticketDetail";
	private final String EVENT_CODE="vc_event_code";
	private final String NO_OF_TICKET="vc_no_of_ticket";
	private final String PRICE="dc_price";
	private final String CONSUMPTION="Consumption";
	private final String TICKET_TYPE="vc_ticket_type";
	private final String TICKET_TYPE_CODE="vc_ticket_code";
	
	private TicketDetailsItem mTicketItem;
	private ArrayList<TicketDetailsItem> ticketList;
	
	private StringBuilder tagVal;

	public TicketParser(){
		tagVal=new StringBuilder();
		ticketList=new ArrayList<TicketDetailsItem>();
	}
	
	
	public ArrayList<TicketDetailsItem> getTicketList() {
		return ticketList;
	}


	public void setTicketList(ArrayList<TicketDetailsItem> ticketList) {
		this.ticketList = ticketList;
	}


	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		// TODO Auto-generated method stub
		tagVal.setLength(0);
		if(qName.equalsIgnoreCase(TICKET_DETAIL)){
			mTicketItem=new TicketDetailsItem();
		}
		super.startElement(uri, localName, qName, attributes);
	}

	
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		// TODO Auto-generated method stub
		tagVal.append(ch,start,length);
		super.characters(ch, start, length);
	}
	
	
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		// TODO Auto-generated method stub
		String tempValue=tagVal.toString();
		if(qName.equalsIgnoreCase(EVENT_CODE)){
			mTicketItem.setEventCode(tempValue);
		}else if(qName.equalsIgnoreCase(NO_OF_TICKET)){
			mTicketItem.setNoOfTicket(tempValue);
		}else if(qName.equalsIgnoreCase(PRICE)){
//			Log.i("", "vvvvvvvvvvvvvvvvvvvvvvv  inside parser :"  +tempValue );
			mTicketItem.setTicketPrice(tempValue);
		}else if(qName.equalsIgnoreCase(CONSUMPTION)){
			mTicketItem.setTicketConsumption(tempValue);
		}else if(qName.equalsIgnoreCase(TICKET_DETAIL)){
			ticketList.add(mTicketItem);
		}else if(qName.equalsIgnoreCase(TICKET_TYPE)){
			mTicketItem.setTicketType(tempValue);
		
		}else if(qName.equalsIgnoreCase(TICKET_TYPE_CODE)){
			mTicketItem.setTicketCode(tempValue);
		}
		
		super.endElement(uri, localName, qName);
	}

	
}
