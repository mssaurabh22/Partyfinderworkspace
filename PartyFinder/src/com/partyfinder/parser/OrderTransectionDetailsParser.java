package com.partyfinder.parser;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

import com.partyfinder.model.OrderTransectionDetailsItems;
import com.partyfinder.model.OrderTicketItems;
import com.partyfinder.model.TicketDetailsItem;

public class OrderTransectionDetailsParser extends DefaultHandler {

	private String TAG="Values";
	private String tempVal=null;
	private OrderTransectionDetailsItems mOrderTransectionDetails;
	private OrderTicketItems mTicketItems;
	private ArrayList<OrderTransectionDetailsItems> mOrderTransectionDetailList;
	private ArrayList<OrderTicketItems> mTicketItemsList;
	private boolean orderTranFlag=false;
	private boolean itemFlag=false;


	//	orderTransectionDetail
	private final String ORDER_TRANSECTION_DETAIL="orderTransectionDetail";
	private final String IMAGE_URL="vc_image_url";
	private final String USER_CODE="vc_user_code";
	private final String USER_NAME="vc_user_name";
	private final String USER_EMAIL="vc_user_email";
	private final String ORDER_CODE="vc_order_code";
	private final String TRANSECTION_CODE="vc_transection_code";
	private final String TRANSECTION_STATUS="vc_transection_status";
	private final String TRANSECTION_STATUS_CODE="vc_tran_status_code";  
	private final String ORDER_DATE="vc_order_date";
	private final String ORDER_DATE_FOR="order_date_for";

	private final String TOTAL_PRICE="dc_tot_price";
	private final String TOTAL_TICKET="TotalTicket";
	private final String COMPANY_NAME="vc_company_name";
	private final String EVENT_NAME="vc_event_name";
	private final String EVENT_DATE="dt_event_date";
	private final String EVENT_DATE_END="dt_event_date_end";
	private final String EVENT_IMAGE_URL="vc_eventimg_url";


	//	ticketItems
	private final String ITEMS="items";
	private final String ITEM="item";
	private final String TICKET_ITEM_IMAGE_URL="vc_image_url";
	private final String TICKET_TYPE="vc_type";
	private final String TICKET_PRICE="ticketprice";
	private final String TICKET_CONSUMPTION="Consumption";
	private final String ITEM_COUNT="ItemCount";

	private StringBuilder tagVal=new StringBuilder();



	public  OrderTransectionDetailsParser(){
		mOrderTransectionDetailList = new ArrayList<OrderTransectionDetailsItems>();
	}
	public ArrayList<OrderTransectionDetailsItems> getOrderDetails(){
		return mOrderTransectionDetailList; 
	}


	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		// TODO Auto-generated method stub
		tempVal="";
		tagVal.setLength(0);
		if(qName.equalsIgnoreCase(ORDER_TRANSECTION_DETAIL)){
			mOrderTransectionDetails = new OrderTransectionDetailsItems();
			orderTranFlag=true;
		}
		if(qName.equalsIgnoreCase(ITEMS)){
			mTicketItemsList = new ArrayList<OrderTicketItems>();
		}
		if(qName.equalsIgnoreCase(ITEM)){

			mTicketItems = new OrderTicketItems();
			itemFlag=true;
		}
		// 	super.startElement(uri, localName, qName, attributes);
	}



	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		// TODO Auto-generated method stub
		//		tempVal = new String(ch, start, length);
		tagVal.append(ch,start,length);
		//	super.characters(ch, start, length);
	}


	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		// TODO Auto-generated method stub
		 tempVal=tagVal.toString();
		if(qName.equalsIgnoreCase("orderTransectionDetail")){
			mOrderTransectionDetailList.add(mOrderTransectionDetails);

		}else if(qName.equalsIgnoreCase("vc_image_url")){


			if(orderTranFlag){

				mOrderTransectionDetails.setImageUrl(tempVal);
				Log.i("","QRCode order Qrcode "+" "+" OrderTransection "+tempVal);
				orderTranFlag=false;

			}else if(itemFlag){

				mTicketItems.setImageUrl(tempVal);
				Log.i("","QRCode order Qrcode "+" "+" ticketItem "+tempVal);


			}


		}else if(qName.equalsIgnoreCase("vc_user_code")){
			mOrderTransectionDetails.setUserCode(tempVal);

		}else if(qName.equalsIgnoreCase("vc_user_name")){
			mOrderTransectionDetails.setUserName(tempVal);

		}else if(qName.equalsIgnoreCase("vc_user_email")){
			mOrderTransectionDetails.setUserEmail(tempVal);

		}else if(qName.equalsIgnoreCase("vc_order_code")){
			mOrderTransectionDetails.setOrderCode(tempVal);

		}else if(qName.equalsIgnoreCase("vc_transection_code")){
			mOrderTransectionDetails.setTransectionCode(tempVal);

		}else if(qName.equalsIgnoreCase("vc_transection_status")){
			mOrderTransectionDetails.setTransectionStatus(tempVal);

		}else if(qName.equalsIgnoreCase("vc_tran_status_code")){
			mOrderTransectionDetails.setTransectionStatusCode(tempVal);

		}else if(qName.equalsIgnoreCase("vc_order_date")){
			mOrderTransectionDetails.setOrderDate(tempVal);

		}else if(qName.equalsIgnoreCase("order_date_for")){
			mOrderTransectionDetails.setOrderDateFor(tempVal);

		}else if(qName.equalsIgnoreCase("dc_tot_price")){
			mOrderTransectionDetails.setTotalPrice(tempVal);

		}else if(qName.equalsIgnoreCase("TotalTicket")){
			mOrderTransectionDetails.setTotalTicket(tempVal);

		}else if(qName.equalsIgnoreCase("vc_company_name")){
			mOrderTransectionDetails.setCompanyName(tempVal);

		}else if(qName.equalsIgnoreCase("vc_event_name")){
			mOrderTransectionDetails.setEventName(tempVal);

		}else if(qName.equalsIgnoreCase("dt_event_date")){
			mOrderTransectionDetails.setEventDate(tempVal);

		}else if(qName.equalsIgnoreCase("dt_event_date_end")){
			mOrderTransectionDetails.setEventDateEnd(tempVal);

		}else if(qName.equalsIgnoreCase("vc_eventimg_url")){
			mOrderTransectionDetails.setEventImageUrl(tempVal);

		}else if(qName.equalsIgnoreCase("items")){
			mOrderTransectionDetails.setOrderTicketItems(mTicketItemsList);

		}else if(qName.equalsIgnoreCase("vc_type")){
			mTicketItems.setTicketType(tempVal);

		}else if(qName.equalsIgnoreCase("ticketprice")){
			Log.i("", "vvvvvvvvvvvvvvvvvvvvvvv  inside parser  new:"  +tempVal );
			mTicketItems.setTicketPrice(tempVal);

		}else if(qName.equalsIgnoreCase("Consumption")){
			mTicketItems.setTicketConsumption(tempVal);

		}else if(qName.equalsIgnoreCase("ItemCount")){
			mTicketItems.setItemCount(tempVal);



			//		mTicketItems.setImageUrl(IMAGE_URL);

			mTicketItemsList.add(mTicketItems);
			itemFlag=false;
		}

		//	super.endElement(uri, localName, qName);
	}


}
