package com.partyfinder.model;

import java.io.Serializable;

import android.util.Log;

public class OrderTicketItems implements Serializable{
	
	private String imageUrl;
	private String ticketType;
	private String ticketPrice;
	private String ticketConsumption;
	private String itemCount;
	
	

	public OrderTicketItems(){
		
	}


	public String getImageUrl() {
		return imageUrl;
	}


	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}


	public String getTicketType() {
		return ticketType;
	}


	public void setTicketType(String ticketType) {
		this.ticketType = ticketType;
	}


	public String getTicketPrice() {
		return ticketPrice;
	}


	public void setTicketPrice(String ticketPrice) {
		this.ticketPrice = ticketPrice;
		Log.i("", "vvvvvvvvvvvvvvvvvvvvvvv"  +this.ticketPrice );
		
	}


	public String getTicketConsumption() {
		return ticketConsumption;
	}


	public void setTicketConsumption(String ticketConsumption) {
		this.ticketConsumption = ticketConsumption;
	}


	public String getItemCount() {
		return itemCount;
	}


	public void setItemCount(String itemCount) {
		this.itemCount = itemCount;
	}
	
	
	

}
