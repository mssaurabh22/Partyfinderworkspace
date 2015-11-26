package com.partyfinder.model;

import java.io.Serializable;

import android.graphics.Bitmap;

public class ReserveTableItem implements Serializable {
	
	private String TotalTicketPrice;
	private String imageUrl;
	private String tableType;
	private String consumption;
	private String price;
	private String eventCode;
	private String clubPlant;
	private String tables;
	private String noOfSeat;
	private String sold;
	private String tabCab;
	private String tabCabId;
	private String yCordinate;
	private String xCordinate;
	private String width;
	private String height;
	private String defaultImg;
	private String selectedImg;
	private String soldImg;
//	private String pndImg;
	private Bitmap defaultImage;
	private Bitmap selectedImage;
//	private Bitmap pendingImage;
	private Bitmap soldImage;
	private Bitmap floorBitmap;
	private boolean isSlect=false;


	public Bitmap getFloorBitmap() {
		return floorBitmap;
	}



	public void setFloorBitmap(Bitmap floorBitmap) {
		this.floorBitmap = floorBitmap;
	}



	public ReserveTableItem(){

	}


	
	public boolean isSlect() {
		return isSlect;
	}



	public void setSlect(boolean isSlect) {
		this.isSlect = isSlect;
	}



	public String getImageUrl() {
		return imageUrl;
	}


	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}


	public String getTableType() {
		return tableType;
	}


	public void setTableType(String tableType) {
		this.tableType = tableType;
	}


	public String getConsumption() {
		return consumption;
	}


	public void setConsumption(String consumption) {
		this.consumption = consumption;
	}


	public String getPrice() {
		return price;
	}


	public void setPrice(String price) {
		this.price = price;
	}


	public String getEventCode() {
		return eventCode;
	}


	public void setEventCode(String eventCode) {
		this.eventCode = eventCode;
	}


	public String getClubPlant() {
		return clubPlant;
	}


	public void setClubPlant(String clubPlant) {
		this.clubPlant = clubPlant;
	}


	public String getTables() {
		return tables;
	}


	public void setTables(String tables) {
		this.tables = tables;
	}


	public String getNoOfSeat() {
		return noOfSeat;
	}


	public void setNoOfSeat(String noOfSeat) {
		this.noOfSeat = noOfSeat;
	}


	public String getSold() {
		return sold;
	}


	public void setSold(String sold) {
		this.sold = sold;
	}


	public String getTabCab() {
		return tabCab;
	}


	public void setTabCab(String tabCab) {
		this.tabCab = tabCab;
	}


	public String getTabCabId() {
		return tabCabId;
	}


	public void setTabCabId(String tabCabId) {
		this.tabCabId = tabCabId;
	}


	public String getyCordinate() {
		return yCordinate;
	}


	public void setyCordinate(String yCordinate) {
		this.yCordinate = yCordinate;
	}


	public String getxCordinate() {
		return xCordinate;
	}


	public void setxCordinate(String xCordinate) {
		this.xCordinate = xCordinate;
	}


	public String getWidth() {
		return width;
	}


	public void setWidth(String width) {
		this.width = width;
	}


	public String getHeight() {
		return height;
	}


	public void setHeight(String height) {
		this.height = height;
	}


	public String getDefaultImg() {
		return defaultImg;
	}


	public void setDefaultImg(String defaultImg) {
		this.defaultImg = defaultImg;
	}


	public String getSelectedImg() {
		return selectedImg;
	}


	public void setSelectedImg(String selectedImg) {
		this.selectedImg = selectedImg;
	}


	public String getSoldImg() {
		return soldImg;
	}


	public void setSoldImg(String soldImg) {
		this.soldImg = soldImg;
	}


	public Bitmap getDefaultImage() {
		return defaultImage;
	}


	public void setDefaultImage(Bitmap defaultImage) {
		this.defaultImage = defaultImage;
	}


	public Bitmap getSelectedImage() {
		return selectedImage;
	}


	public void setSelectedImage(Bitmap selectedImage) {
		this.selectedImage = selectedImage;
	}


	public Bitmap getSoldImage() {
		return soldImage;
	}


	public void setSoldImage(Bitmap soldImage) {
		this.soldImage = soldImage;
	}



	
	
	
/*	
	public String getPndImg() {
		return pndImg;
	}



	public void setPndImg(String pndImg) {
		this.pndImg = pndImg;
	}



	public Bitmap getPendingImage() {
		return pendingImage;
	}



	public void setPendingImage(Bitmap pendingImage) {
		this.pendingImage = pendingImage;
	}
*/


	public String getTotalTicketPrice() {
		return TotalTicketPrice;
	}



	public void setTotalTicketPrice(String totalTicketPrice) {
		TotalTicketPrice = totalTicketPrice;
	}
	
	


}
