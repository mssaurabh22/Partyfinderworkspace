package com.partyfinder.parser;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

import com.partyfinder.model.OrderHistoryItem;
import com.partyfinder.model.OrderHistoryTransection;
import com.partyfinder.model.VenuesItem;

public class OrderHistoryParser extends DefaultHandler {
	
	private String tempVal=null;
	private OrderHistoryTransection mGetOrderHistoryItem;
	private OrderHistoryItem mOrderHistoryItem;
	private ArrayList<OrderHistoryTransection>mGetOrderHistoryItemsList;
	private ArrayList<OrderHistoryItem> mOrderListItem;
	private String TAG="Values";
	
	private final String TRANSECTION="Transection";
	private final String TRANSECT_IMG_URL="vc_image_url";
	private final String TRANSECT_USER_CODE="vc_user_code";
	private final String TRANSECT_USER_NAME="vc_user_name";
	private final String TRANSECT_USER_EMAIL="vc_user_email";
	private final String TRANSECT_USER_ORDERCODE="vc_order_code";
	private final String TRANSECT_USER_TRANSECT_CODE="vc_transection_code";
	private final String TRANSECT_USER_TRANSECT_STATUS="vc_transection_status";
	private final String TRANSECT_ORDER_DATE="vc_order_date";
	private final String TRANSECT_TOT_PRICE="dc_tot_price";
	private final String TRANSECT_TOT_TICKET="TotalTicket";
	private final String TRANSECT_COMPNY_NAME="vc_company_name";
	private final String TRANSECT_EVENT_NAME="vc_event_name";
	private final String TRANSECT_EVENT_IMGURL="vc_eventimg_url";
	
	
	
	private final String ITEM_IMG_URL="vc_image_url";
	private final String ITEM_TYPE="vc_type";
	private final String ITEM_TICKET_PRICE="ticketprice";
	private final String ITEM_CONSUMPTION="Consumption";
	private final String ITEM_COUNT="ItemCount";
	private final String ITEMS="items";
	private final String ITEM="item";
	

	
	public OrderHistoryParser() {
		// TODO Auto-generated constructor stub
	mGetOrderHistoryItemsList= new ArrayList<OrderHistoryTransection>();
	}
	 public ArrayList<OrderHistoryTransection> getOrderHistory() {
		return mGetOrderHistoryItemsList;
		
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		// TODO Auto-generated method stub
		tempVal="";
		if(qName.equalsIgnoreCase(TRANSECTION))
			mGetOrderHistoryItem= new OrderHistoryTransection();
		Log.i("",""+TAG+qName);
		if(qName.equalsIgnoreCase(ITEMS))
			mOrderListItem=new ArrayList<OrderHistoryItem>();
			
		if(qName.equalsIgnoreCase(ITEM)){
			mOrderHistoryItem=new OrderHistoryItem();
		}
				
			
		
	//	super.startElement(uri, localName, qName, attributes);
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		// TODO Auto-generated method stub
		tempVal= new String(ch,start,length);
		//super.characters(ch, start, length);
	}
	
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		// TODO Auto-generated method stub
		if(qName.equalsIgnoreCase("Transection")){
			mGetOrderHistoryItemsList.add(mGetOrderHistoryItem);
		}else if (qName.equalsIgnoreCase("vc_image_url")) {
			mGetOrderHistoryItem.setVc_image_url(tempVal);
			
		}else if (qName.equalsIgnoreCase("vc_user_code")) {
			mGetOrderHistoryItem.setVc_user_code(tempVal);
		}else if (qName.equalsIgnoreCase("vc_user_name")) {
			mGetOrderHistoryItem.setVc_user_name(tempVal);
		}else if (qName.equalsIgnoreCase("vc_user_email")) {
			mGetOrderHistoryItem.setVc_user_email(tempVal);
			
		}else if (qName.equalsIgnoreCase("vc_order_code")) {
			mGetOrderHistoryItem.setVc_order_code(tempVal);
			
		}else if ( qName.equalsIgnoreCase("vc_transection_code")) {
			mGetOrderHistoryItem.setVc_transection_code(tempVal);
		}else if (qName.equalsIgnoreCase("vc_transection_status")) {
			mGetOrderHistoryItem.setVc_transection_status(tempVal);
			
		}else if (qName.equalsIgnoreCase("vc_order_date")) {
			mGetOrderHistoryItem.setVc_order_date(tempVal);
		}else if(qName.equalsIgnoreCase(ITEMS)){
			mGetOrderHistoryItem.setmOrderHistoryList(mOrderListItem);
		}else if(qName.equalsIgnoreCase(ITEM)){
			mOrderHistoryItem.setImageUrl(ITEM_IMG_URL);
			mOrderHistoryItem.setConsumption(ITEM_CONSUMPTION);
			mOrderHistoryItem.setItemCount(ITEM_COUNT);
			mOrderHistoryItem.setTicketPrice(ITEM_TICKET_PRICE);
			Log.i("", "vvvvvvvvvvvvvvvvvvvvvvv  inside parser :"  +ITEM_TICKET_PRICE );
			mOrderHistoryItem.setType(ITEM_TYPE);
			mOrderListItem.add(mOrderHistoryItem);
		}
	
	}
}
