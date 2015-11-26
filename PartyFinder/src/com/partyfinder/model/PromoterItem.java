package com.partyfinder.model;

import java.io.Serializable;

public class PromoterItem implements Serializable{

	private String promoterImage;
	private String eventCode;
	private String promoterName;
	private String promoterCode;
	private String promoterType;
	
	
	public String getPromoterImage() {
		return promoterImage;
	}
	public void setPromoterImage(String promoterImage) {
		this.promoterImage = promoterImage;
	}
	public String getEventCode() {
		return eventCode;
	}
	public void setEventCode(String eventCode) {
		this.eventCode = eventCode;
	}
	public String getPromoterName() {
		return promoterName;
	}
	public void setPromoterName(String promoterName) {
		this.promoterName = promoterName;
	}
	public String getPromoterCode() {
		return promoterCode;
	}
	public void setPromoterCode(String promoterCode) {
		this.promoterCode = promoterCode;
	}
	public String getPromoterType() {
		return promoterType;
	}
	public void setPromoterType(String promoterType) {
		this.promoterType = promoterType;
	}
	
	
	
}
