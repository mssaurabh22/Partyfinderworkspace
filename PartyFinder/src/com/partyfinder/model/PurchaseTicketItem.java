package com.partyfinder.model;

import java.io.Serializable;

public class PurchaseTicketItem implements Serializable {
	
	private String vc_event_code;
	private String vc_ticket_code;
	private String vc_no_of_ticket;
	private String vc_ticket_type;
	private String dc_price;
	private String Consumption;
	
	
	public String getVc_event_code() {
		return vc_event_code;
	}
	public void setVc_event_code(String vc_event_code) {
		this.vc_event_code = vc_event_code;
	}
	public String getVc_ticket_code() {
		return vc_ticket_code;
	}
	public void setVc_ticket_code(String vc_ticket_code) {
		this.vc_ticket_code = vc_ticket_code;
	}
	public String getVc_no_of_ticket() {
		return vc_no_of_ticket;
	}
	public void setVc_no_of_ticket(String vc_no_of_ticket) {
		this.vc_no_of_ticket = vc_no_of_ticket;
	}
	public String getVc_ticket_type() {
		return vc_ticket_type;
	}
	public void setVc_ticket_type(String vc_ticket_type) {
		this.vc_ticket_type = vc_ticket_type;
	}
	public String getDc_price() {
		return dc_price;
	}
	public void setDc_price(String dc_price) {
		this.dc_price = dc_price;
	}
	public String getConsumption() {
		return Consumption;
	}
	public void setConsumption(String consumption) {
		Consumption = consumption;
	}
	
	


}
