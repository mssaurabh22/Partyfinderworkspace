package com.partyfinder.model;

public class OrderHistoryItem {
	
	
	
	
	private String imageUrl;
	private String type;
	private String ticketPrice;
	private String consumption;
	private String itemCount;

	
	public OrderHistoryItem() {
		
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTicketPrice() {
		return ticketPrice;
	}

	public void setTicketPrice(String ticketPrice) {
		this.ticketPrice = ticketPrice;
	}

	public String getConsumption() {
		return consumption;
	}

	public void setConsumption(String consumption) {
		this.consumption = consumption;
	}

	public String getItemCount() {
		return itemCount;
	}

	public void setItemCount(String itemCount) {
		this.itemCount = itemCount;
	}
	
	
	
}
