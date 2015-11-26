package com.partyfinder.parser;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.partyfinder.model.VenuesItem;

import android.R.string;
import android.util.Log;

public class VanuesParser extends DefaultHandler {

	//	private String tempVal=null;
	private VenuesItem mVenuesItem;
	private ArrayList<VenuesItem >mVenuesItemsList;
	private String TAG="Values";

	private StringBuilder tagVal=new StringBuilder();
	public VanuesParser() {
		// TODO Auto-generated constructor stub
		mVenuesItemsList= new ArrayList<VenuesItem>();
	}
	public ArrayList<VenuesItem> getVanuesItem() {
		return mVenuesItemsList;

	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		// TODO Auto-generated method stub

		tagVal.setLength(0);
		if(qName.equalsIgnoreCase("venueDetail"))
			mVenuesItem= new VenuesItem();
		Log.i("",""+TAG+qName);

		//	super.startElement(uri, localName, qName, attributes);
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		// TODO Auto-generated method stub
		//		tempVal= new String(ch,start,length);
		tagVal.append(ch,start,length);
		//	tempVal=tempVal.substring(1);
		//super.characters(ch, start, length);
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		// TODO Auto-generated method stub
		String tempVal=tagVal.toString();
		if(qName.equalsIgnoreCase("venueDetail")){
			mVenuesItemsList.add(mVenuesItem);
		}else if (qName.equalsIgnoreCase("vc_image_url")) {
			mVenuesItem.setVc_image_url(tempVal);
		}else if (qName.equalsIgnoreCase("vc_company_code")) {
			mVenuesItem.setVc_company_code(tempVal);
		}else if (qName.equalsIgnoreCase("vc_user_code")) {
			mVenuesItem.setVc_user_code(tempVal);
		}else if (qName.equalsIgnoreCase("NomeFantasia")) {
			mVenuesItem.setNomeFantasia(tempVal);
		}else if (qName.equalsIgnoreCase("vc_addr_street")) {
			mVenuesItem.setVc_addr_street(tempVal);
		}else if ( qName.equalsIgnoreCase("nu_addr_number")) {
			mVenuesItem.setNu_addr_number(tempVal);
		}else if (qName.equalsIgnoreCase("vc_comercial_tel")) {
			mVenuesItem.setVc_comercial_tel(tempVal);
		}else if (qName.equalsIgnoreCase("vc_city_name")) {
			mVenuesItem.setVc_city_name(tempVal);
		}else if (qName.equalsIgnoreCase("vc_state_name")) {
			mVenuesItem.setVc_state_name(tempVal);	
		}else if (qName.equalsIgnoreCase("vc_country_name")) {
			mVenuesItem.setVc_country_name(tempVal);
		}else if (qName.equalsIgnoreCase("nu_zip_code")) {
			mVenuesItem.setNu_zip_code(tempVal);
		}else if (qName.equalsIgnoreCase("vc_description")) {
			mVenuesItem.setVc_description(tempVal);
		}else if (qName.equalsIgnoreCase("TotalLike")) {
			mVenuesItem.setTotalLike(tempVal);
		}else if (qName.equalsIgnoreCase("vc_like_status")) {
			mVenuesItem.setVc_like_status(tempVal);
		}else if (qName.equalsIgnoreCase("vc_fav_venue")) {
			mVenuesItem.setVc_fav_venue(tempVal);
		}

	}




}
