package com.partyfinder.model;

import java.util.ArrayList;

import com.google.gson.Gson;

public class Cards {
	private ArrayList<CardHolder> cardHolderList;

	
	public Cards(){
		cardHolderList=new ArrayList<CardHolder>();
	}
	public ArrayList<CardHolder> getCardHolderList() {
		return cardHolderList;
	}

	public void setCardHolderList(ArrayList<CardHolder> cardHolderList) {
		this.cardHolderList = cardHolderList;
	}
	
	public String serialize() {
	     // Serialize this class into a JSON string using GSON
	     Gson gson = new Gson();
	     return gson.toJson(this);
	 }

	 static public Cards create(String serializedData) {
	     // Use GSON to instantiate this class using the JSON representation of the state
	     Gson gson = new Gson();
	     return gson.fromJson(serializedData, Cards.class);
	 }
	

}
