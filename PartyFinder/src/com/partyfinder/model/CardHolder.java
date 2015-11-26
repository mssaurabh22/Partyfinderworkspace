package com.partyfinder.model;

import java.io.Serializable;
import java.util.ArrayList;

public class CardHolder implements Serializable {
	private String userCode;
	private ArrayList<Card> cardList;

	public CardHolder(){
		cardList=new ArrayList<Card>();
	}
	public ArrayList<Card> getCardList() {
		return cardList;
	}

	public void setCardList(ArrayList<Card> cardList) {
		this.cardList = cardList;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	
	
}
