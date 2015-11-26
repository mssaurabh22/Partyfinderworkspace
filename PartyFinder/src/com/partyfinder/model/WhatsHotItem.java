package com.partyfinder.model;

import java.io.Serializable;

import android.graphics.Bitmap;

public class WhatsHotItem implements Serializable {

	private Bitmap imageName;
	private String eventName;
	private String imageUrl;
	
	public WhatsHotItem(){
		
	}
	
	
	
	public String getImageUrl() {
		return imageUrl;
	}



	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}



	public WhatsHotItem(Bitmap imageName,String eventName){
		this.imageName=imageName;
		this.eventName=eventName;
	}
	public Bitmap getImageName() {
		return imageName;
	}
	public void setImageName(Bitmap imageName) {
		this.imageName = imageName;
	}
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	
}
