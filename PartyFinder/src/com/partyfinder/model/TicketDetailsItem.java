package com.partyfinder.model;

import java.io.Serializable;

public class TicketDetailsItem implements Serializable{

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	private String eventCode;
	private String ticketCode;
	private String noOfTicket;
	private String ticketType;
	private String ticketPrice;
	private String ticketConsumption;
	private int totalCount=0;
	private double totalTicketPrice=0;
	
	public double getTotalTicketPrice() {
		return totalTicketPrice;
	}

	public void setTotalTicketPrice(double totalTicketPrice) {
		this.totalTicketPrice = totalTicketPrice;
	}

	public TicketDetailsItem(){
		
	}

	public String getEventCode() {
		return eventCode;
	}

	public void setEventCode(String eventCode) {
		this.eventCode = eventCode;
	}

	public String getTicketCode() {
		return ticketCode;
	}

	public void setTicketCode(String ticketCode) {
		this.ticketCode = ticketCode;
	}

	public String getNoOfTicket() {
		return noOfTicket;
	}

	public void setNoOfTicket(String noOfTicket) {
		this.noOfTicket = noOfTicket;
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
	}

	public String getTicketConsumption() {
		return ticketConsumption;
	}

	public void setTicketConsumption(String ticketConsumption) {
		this.ticketConsumption = ticketConsumption;
	}
	
	
}
