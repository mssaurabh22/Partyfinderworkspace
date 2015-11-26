package com.partyfinder.parser;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.partyfinder.model.SagupaoSessionItem;

public class SagupaoCardDetailParser {
	
	private SagupaoSessionItem sagupaoCardDet;
	
	
	
	public SagupaoSessionItem parseSagupaoDet(String response){
		
		try {
			JSONObject respns=new JSONObject(response);
			if(respns!=null){
				JSONObject bin=respns.getJSONObject("bin");
				if(bin!=null){
					sagupaoCardDet=new SagupaoSessionItem();
					sagupaoCardDet.setLength(bin.getString("length"));
					JSONObject country=bin.getJSONObject("country");
					if(country!=null){
						sagupaoCardDet.setCountryName(country.getString("name"));
						sagupaoCardDet.setCountryId(country.getString("id"));
						sagupaoCardDet.setCountryIsoCode(country.getString("isoCode"));
						sagupaoCardDet.setCountryIsoCodeThreeDigits(country.getString("isoCodeThreeDigits"));
						
					}
					JSONObject brand=bin.getJSONObject("brand");
					if(brand!=null){
						sagupaoCardDet.setCardBarnd(brand.getString("name"));
					}
					sagupaoCardDet.setCardLevel(bin.getString("cardLevel"));
					sagupaoCardDet.setExpirable(bin.getString("expirable"));
					sagupaoCardDet.setStatusMessg(bin.getString("statusMessage"));	
				}
			
				
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return sagupaoCardDet;
		
	}

}
