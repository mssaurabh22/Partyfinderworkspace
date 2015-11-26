package com.partyfinder.model;

import java.io.Serializable;

import android.graphics.Bitmap;

public class EventItem implements Serializable {

	private String vcImageUrl;
	private String vcEventCode;
	private String vcEventName;
	private String vcCompanyName;
	private String vcCompanyCode;
	private String chHotEvent;
	private String chRecomendEvent;
	private String chFavourite;
	private String chIsPublish;
	private String vcEventAdd;
	private String vcEventCity;
	private String vcEventState;
	private String vcEventCountry;
	private String nuPinCode;
	private String dtEventDate;
	private String dtEventStartTime;
	private String dtEventEndTime;
	private String dtEventDesc;
	private String vcPlantName;
	private String vcLatitude;
	private String vcLongitude;
	private String updateDate;
	private String noOfTicket;
	private String inQty;
	private String guestStartTime;
	private String guestEndTime;
	private String Commision;
	 private String userCode;
//	private Bitmap imageName;
	
	/*public Bitmap getImageName() {
		return imageName;
	}

	public void setImageName(Bitmap imageName) {
		this.imageName = imageName;
	}*/

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getCommision() {
		return Commision;
	}

	public void setCommision(String commision) {
		Commision = commision;
	}

	public String getGuestStartTime() {
		return guestStartTime;
	}

	public void setGuestStartTime(String guestStartTime) {
		this.guestStartTime = guestStartTime;
	}

	public String getGuestEndTime() {
		return guestEndTime;
	}

	public void setGuestEndTime(String guestEndTime) {
		this.guestEndTime = guestEndTime;
	}

	public String getInQty() {
		return inQty;
	}

	public void setInQty(String inQty) {
		this.inQty = inQty;
	}

	public String getNoOfTicket() {
		return noOfTicket;
	}

	public void setNoOfTicket(String noOfTicket) {
		this.noOfTicket = noOfTicket;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public EventItem(){
		
	}

	public String getVcImageUrl() {
		return vcImageUrl;
	}

	public void setVcImageUrl(String vcImageUrl) {
		this.vcImageUrl = vcImageUrl;
	}

	public String getVcEventCode() {
		return vcEventCode;
	}

	public void setVcEventCode(String vcEventCode) {
		this.vcEventCode = vcEventCode;
	}

	public String getVcEventName() {
		return vcEventName;
	}

	public void setVcEventName(String vcEventName) {
		this.vcEventName = vcEventName;
	}

	public String getVcCompanyName() {
		return vcCompanyName;
	}

	public void setVcCompanyName(String vcCompanyName) {
		this.vcCompanyName = vcCompanyName;
	}

	public String getVcCompanyCode() {
		return vcCompanyCode;
	}

	public void setVcCompanyCode(String vcCompanyCode) {
		this.vcCompanyCode = vcCompanyCode;
	}

	public String getChHotEvent() {
		return chHotEvent;
	}

	public void setChHotEvent(String chHotEvent) {
		this.chHotEvent = chHotEvent;
	}

	public String getChRecomendEvent() {
		return chRecomendEvent;
	}

	public void setChRecomendEvent(String chRecomendEvent) {
		this.chRecomendEvent = chRecomendEvent;
	}

	public String getChFavourite() {
		return chFavourite;
	}

	public void setChFavourite(String chFavourite) {
		this.chFavourite = chFavourite;
	}

	public String getChIsPublish() {
		return chIsPublish;
	}

	public void setChIsPublish(String chIsPublish) {
		this.chIsPublish = chIsPublish;
	}

	public String getVcEventAdd() {
		return vcEventAdd;
	}

	public void setVcEventAdd(String vcEventAdd) {
		this.vcEventAdd = vcEventAdd;
	}

	public String getVcEventCity() {
		return vcEventCity;
	}

	public void setVcEventCity(String vcEventCity) {
		this.vcEventCity = vcEventCity;
	}

	public String getVcEventState() {
		return vcEventState;
	}

	public void setVcEventState(String vcEventState) {
		this.vcEventState = vcEventState;
	}

	public String getVcEventCountry() {
		return vcEventCountry;
	}

	public void setVcEventCountry(String vcEventCountry) {
		this.vcEventCountry = vcEventCountry;
	}

	public String getNuPinCode() {
		return nuPinCode;
	}

	public void setNuPinCode(String nuPinCode) {
		this.nuPinCode = nuPinCode;
	}

	public String getDtEventDate() {
		return dtEventDate;
	}

	public void setDtEventDate(String dtEventDate) {
		this.dtEventDate = dtEventDate;
	}

	public String getDtEventStartTime() {
		return dtEventStartTime;
	}

	public void setDtEventStartTime(String dtEventStartTime) {
		this.dtEventStartTime = dtEventStartTime;
	}

	public String getDtEventEndTime() {
		return dtEventEndTime;
	}

	public void setDtEventEndTime(String dtEventEndTime) {
		this.dtEventEndTime = dtEventEndTime;
	}

	public String getDtEventDesc() {
		return dtEventDesc;
	}

	public void setDtEventDesc(String dtEventDesc) {
		this.dtEventDesc = dtEventDesc;
	}

	public String getVcPlantName() {
		return vcPlantName;
	}

	public void setVcPlantName(String vcPlantName) {
		this.vcPlantName = vcPlantName;
	}

	public String getVcLatitude() {
		return vcLatitude;
	}

	public void setVcLatitude(String vcLatitude) {
		this.vcLatitude = vcLatitude;
	}

	public String getVcLongitude() {
		return vcLongitude;
	}

	public void setVcLongitude(String vcLongitude) {
		this.vcLongitude = vcLongitude;
	}
	
	
	
	
}
