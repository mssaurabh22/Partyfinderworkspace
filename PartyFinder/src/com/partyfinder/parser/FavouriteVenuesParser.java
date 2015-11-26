package com.partyfinder.parser;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.partyfinder.model.VenuesItem;
import com.partyfinder.model.VenuesItem;

public class FavouriteVenuesParser extends DefaultHandler {
	

	
	static String TAG = "Favourite Venues Parser ";
	private ArrayList<VenuesItem> favouriteVenueItemsList;
	private String tempVal = null;
	private VenuesItem mFavouriteVenueItem;
	
	public FavouriteVenuesParser() {
		favouriteVenueItemsList = new ArrayList<VenuesItem>();
	}
	
	public ArrayList<VenuesItem> getFavouriteVanues(){
		return favouriteVenueItemsList;
	}

	//Event Handler
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		// TODO Auto-generated method stub
		tempVal = "";
		if(qName.equalsIgnoreCase("venueDetail"))
			/*if(qName.equalsIgnoreCase("venueDetails"))*/{
			//create a new instance of ActivityFeedItem
			mFavouriteVenueItem = new VenuesItem();
		}
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		//read the text of each xml node
		tempVal = new String(ch, start, length);
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		// TODO Auto-generated method stub
		if(qName.equalsIgnoreCase("venueDetail"))
			/*if(qName.equalsIgnoreCase("venueDetails"))*/{
			favouriteVenueItemsList.add(mFavouriteVenueItem);
		}else if (qName.equalsIgnoreCase("vc_image_url")) {
			mFavouriteVenueItem.setVc_image_url(tempVal);
			
		}else if (qName.equalsIgnoreCase("vc_company_code")) {
			mFavouriteVenueItem.setVc_company_code(tempVal);
		}else if (qName.equalsIgnoreCase("vc_user_code")) {
			mFavouriteVenueItem.setVc_user_code(tempVal);
		}else if (qName.equalsIgnoreCase("NomeFantasia")) {
			mFavouriteVenueItem.setNomeFantasia(tempVal);
			
		}else if (qName.equalsIgnoreCase("vc_addr_street")) {
			mFavouriteVenueItem.setVc_addr_street(tempVal);
			
		}else if ( qName.equalsIgnoreCase("nu_addr_number")) {
			mFavouriteVenueItem.setNu_addr_number(tempVal);
		}else if (qName.equalsIgnoreCase("vc_comercial_tel")) {
			mFavouriteVenueItem.setVc_comercial_tel(tempVal);
			
		}else if (qName.equalsIgnoreCase("vc_city_name")) {
			mFavouriteVenueItem.setVc_city_name(tempVal);
		}else if (qName.equalsIgnoreCase("vc_state_name")) {
			mFavouriteVenueItem.setVc_state_name(tempVal);	
		}else if (qName.equalsIgnoreCase("vc_country_name")) {
			mFavouriteVenueItem.setVc_country_name(tempVal);
		}else if (qName.equalsIgnoreCase("nu_zip_code")) {
			mFavouriteVenueItem.setNu_zip_code(tempVal);
		
		}else if (qName.equalsIgnoreCase("vc_description")) {
			mFavouriteVenueItem.setVc_description(tempVal);
		}else if (qName.equalsIgnoreCase("TotalLike")) {
			mFavouriteVenueItem.setTotalLike(tempVal);
			
		}else if (qName.equalsIgnoreCase("vc_like_status")) {
			mFavouriteVenueItem.setVc_like_status(tempVal);
		}else if (qName.equalsIgnoreCase("vc_fav_venue")) {
			mFavouriteVenueItem.setVc_fav_venue(tempVal);
		}
	}


	
	
}
