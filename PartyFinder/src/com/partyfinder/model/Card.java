package com.partyfinder.model;

import java.io.Serializable;

public class Card implements Serializable{
	
	private String cardNumber;
	private String lastThreeDigit;
	private String cardName;
	private String cardVal;
	private String cpfNo;
	private String birthDate;
	private String telephone;
	private boolean isNewAdded;
	
	
	

	public boolean isNewAdded() {
		return isNewAdded;
	}
	public void setNewAdded(boolean isNewAdded) {
		this.isNewAdded = isNewAdded;
	}
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	
	public String getLastThreeDigit() {
		return lastThreeDigit;
	}
	public void setLastThreeDigit(String lastThreeDigit) {
		this.lastThreeDigit = lastThreeDigit;
	}

	public String getCardName() {
		return cardName;
	}
	public void setCardName(String cardName) {
		this.cardName = cardName;
	}
	public String getCardVal() {
		return cardVal;
	}
	public void setCardVal(String cardVal) {
		this.cardVal = cardVal;
	}
	public String getCpfNo() {
		return cpfNo;
	}
	public void setCpfNo(String cpfNo) {
		this.cpfNo = cpfNo;
	}
	public String getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	



}
