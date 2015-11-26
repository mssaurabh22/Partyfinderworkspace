package com.partyfinder.parser;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

import com.partyfinder.model.ForgotPassword;
import com.partyfinder.model.GatewayRespItems;
import com.partyfinder.model.UserLoginItem;

public class GAtewayRespParser extends DefaultHandler{

	private final String GatewayRespItemsHeader="transaction";
	private GatewayRespItems mGatewayRespItems=null;

	private StringBuilder textItem=new StringBuilder();
	private String sentStatus;
	String TRANSECTIONSTATUS = "status";

	private String TAG="Gateway Resp Items Parser";

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		// TODO Auto-generated method stub
		textItem.setLength(0);

		if(qName.equalsIgnoreCase(GatewayRespItemsHeader)){
			mGatewayRespItems= new GatewayRespItems();
			Log.i("","End tag : "+qName);
		}else if(qName.equalsIgnoreCase(TRANSECTIONSTATUS)){

			Log.i("",TAG+"Transection status : "+qName);
		}


		super.startElement(uri, localName, qName, attributes);
	}




	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		// TODO Auto-generated method stub

		String tempVal=textItem.toString();

		if(qName.equalsIgnoreCase(TRANSECTIONSTATUS)){
			mGatewayRespItems.setTransectionStatus(tempVal);


		}
		super.endElement(uri, localName, qName);
	}




	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		// TODO Auto-generated method stub
		textItem.append(ch,start,length);
		//		super.characters(ch, start, length);
	}


	public GatewayRespItems getGateWayResp() {
		return mGatewayRespItems;
	}
	public void getGateWayResp(GatewayRespItems gatewayresp) {
		this.mGatewayRespItems = gatewayresp;
	}



}
