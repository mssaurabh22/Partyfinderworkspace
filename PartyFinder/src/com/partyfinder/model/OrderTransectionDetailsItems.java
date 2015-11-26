package com.partyfinder.model;

import java.io.Serializable;
import java.util.ArrayList;

public class OrderTransectionDetailsItems implements Serializable{
	
	private String imageUrl;
	private String userCode;
	private String userName;
	private String userEmail;
	private String orderCode;
	private String transectionCode;
	private String transectionStatus;
	private String transectionStatusCode;
	private String orderDate;
	private String orderDateFor;
	private String totalPrice;
	private String totalTicket;
	private String companyName;
	private String eventName;
	private String eventDate;
	private String eventDateEnd;
	private String eventImageUrl;
	
	
	private ArrayList<OrderTicketItems> orderTicketItems;
	
	
	
	public OrderTransectionDetailsItems(){
		
	}



	public String getImageUrl() {
		return imageUrl;
	}



	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}



	public String getUserCode() {
		return userCode;
	}



	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}



	public String getUserName() {
		return userName;
	}



	public void setUserName(String userName) {
		this.userName = userName;
	}



	public String getUserEmail() {
		return userEmail;
	}



	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}



	public String getOrderCode() {
		return orderCode;
	}



	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}



	public String getTransectionCode() {
		return transectionCode;
	}



	public void setTransectionCode(String transectionCode) {
		this.transectionCode = transectionCode;
	}



	public String getTransectionStatus() {
		return transectionStatus;
	}



	public void setTransectionStatus(String transectionStatus) {
		this.transectionStatus = transectionStatus;
	}
	
	
	public String getTransectionStatusCode() {
		return transectionStatusCode;
	}



	public void setTransectionStatusCode(String transectionStatusCode) {
		this.transectionStatusCode = transectionStatusCode;
	}
	



	public String getOrderDate() {
		return orderDate;
	}



	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}



	public String getOrderDateFor() {
		return orderDateFor;
	}



	public void setOrderDateFor(String orderDateFor) {
		this.orderDateFor = orderDateFor;
	}


	public String getTotalPrice() {
		return totalPrice;
	}



	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}



	public String getTotalTicket() {
		return totalTicket;
	}



	public void setTotalTicket(String totalTicket) {
		this.totalTicket = totalTicket;
	}



	public String getCompanyName() {
		return companyName;
	}



	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}



	public String getEventName() {
		return eventName;
	}



	public void setEventName(String eventName) {
		this.eventName = eventName;
	}



	public String getEventDate() {
		return eventDate;
	}



	public void setEventDate(String eventDate) {
		this.eventDate = eventDate;
	}



	public String getEventDateEnd() {
		return eventDateEnd;
	}



	public void setEventDateEnd(String eventDateEnd) {
		this.eventDateEnd = eventDateEnd;
	}



	public String getEventImageUrl() {
		return eventImageUrl;
	}



	public void setEventImageUrl(String eventImageUrl) {
		this.eventImageUrl = eventImageUrl;
	}



	public ArrayList<OrderTicketItems> getOrderTicketItems() {
		return orderTicketItems;
	}



	public void setOrderTicketItems(ArrayList<OrderTicketItems> orderTicketItems) {
		this.orderTicketItems = orderTicketItems;
	}



	
	
	
	
	
	

}
