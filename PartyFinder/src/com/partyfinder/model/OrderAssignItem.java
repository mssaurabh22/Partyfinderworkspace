package com.partyfinder.model;

import java.io.Serializable;

public class OrderAssignItem implements Serializable{

	private String imageUrl;
	private String orderCode;
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	
	
}
