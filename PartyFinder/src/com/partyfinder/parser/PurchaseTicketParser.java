package com.partyfinder.parser;

import java.util.ArrayList;

import javax.xml.namespace.QName;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.partyfinder.model.PurchaseTicketItem;

import android.util.Log;


public class PurchaseTicketParser  extends DefaultHandler{

	
	static String TAG = "Activity Feed List ";
	private ArrayList<PurchaseTicketItem> purchaseTicketItems;
	private String tempVal = null;
	

	private PurchaseTicketItem mItemTicketItem;
	private  String PARENT_NODE="ticketDetail";
	private String EVENT_CODE="vc_event_code";
	private String TICKET_CODE="vc_ticket_code";
	private String NUMBER_OF_TICKET="vc_no_of_ticket";
	private String TICKET_TYPE="vc_ticket_type";
	private String DC_PRICE="dc_price";
	private String CONSUMPTION="Consumption";
	
	
	public PurchaseTicketParser() {
		purchaseTicketItems = new ArrayList<PurchaseTicketItem>();
	}
	
	public ArrayList<PurchaseTicketItem> getPurchaseTIcket(){
		return purchaseTicketItems;
	}

	//Event Handler
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		// TODO Auto-generated method stub
		tempVal = "";
		if(qName.equalsIgnoreCase(PARENT_NODE)){
			//create a new instance of ActivityFeedItem
			mItemTicketItem = new PurchaseTicketItem();
		}
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		//read the text of each xml node
		tempVal = new String(ch, start, length);
		//tempVal=tempVal.substring(1);
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		// TODO Auto-generated method stub
		if(qName.equalsIgnoreCase(PARENT_NODE)){
			purchaseTicketItems.add(mItemTicketItem);
		
		}else if(qName.equalsIgnoreCase(EVENT_CODE)){
			mItemTicketItem.setVc_event_code(tempVal);
			
		}else if(qName.equalsIgnoreCase(TICKET_CODE)){
			mItemTicketItem.setVc_ticket_code(tempVal);
			
		}else if(qName.equalsIgnoreCase(NUMBER_OF_TICKET)){
			mItemTicketItem.setVc_no_of_ticket(tempVal); 
			
		}else if (qName.equalsIgnoreCase(TICKET_TYPE)) {
			mItemTicketItem.setVc_ticket_type(tempVal);
		}
		
		else if(qName.equalsIgnoreCase(DC_PRICE)){
			mItemTicketItem.setDc_price(tempVal);
		
		}else if(qName.equalsIgnoreCase(CONSUMPTION)){
			mItemTicketItem.setConsumption(tempVal);
			
		}
	}

}
