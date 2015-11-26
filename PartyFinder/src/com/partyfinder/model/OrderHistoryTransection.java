package com.partyfinder.model;

import java.io.Serializable;
import java.util.ArrayList;

public class OrderHistoryTransection implements Serializable{
	
	
	private String vc_image_url;
	private String vc_user_code;
	private String vc_user_name;
	private String vc_user_email;
	private String vc_order_code;
	private String vc_transection_code;
	private String vc_transection_status;
	private String vc_order_date;
	
	
	private String dc_tot_price;
	private String TotalTicket;
	private String vc_company_name;
	private String vc_event_name;
	private String vc_eventimg_url;
	private ArrayList<OrderHistoryItem> mOrderHistoryList;

public OrderHistoryTransection() {
	// TODO Auto-generated constructor stub
}



public ArrayList<OrderHistoryItem> getmOrderHistoryList() {
	return mOrderHistoryList;
}



public void setmOrderHistoryList(ArrayList<OrderHistoryItem> mOrderHistoryList) {
	this.mOrderHistoryList = mOrderHistoryList;
}



public String getVc_image_url() {
	return vc_image_url;
}

public void setVc_image_url(String vc_image_url) {
	this.vc_image_url = vc_image_url;
}

public String getVc_user_code() {
	return vc_user_code;
}

public void setVc_user_code(String vc_user_code) {
	this.vc_user_code = vc_user_code;
}

public String getVc_user_name() {
	return vc_user_name;
}

public void setVc_user_name(String vc_user_name) {
	this.vc_user_name = vc_user_name;
}

public String getVc_user_email() {
	return vc_user_email;
}

public void setVc_user_email(String vc_user_email) {
	this.vc_user_email = vc_user_email;
}

public String getVc_order_code() {
	return vc_order_code;
}

public void setVc_order_code(String vc_order_code) {
	this.vc_order_code = vc_order_code;
}

public String getVc_transection_code() {
	return vc_transection_code;
}

public void setVc_transection_code(String vc_transection_code) {
	this.vc_transection_code = vc_transection_code;
}

public String getVc_transection_status() {
	return vc_transection_status;
}

public void setVc_transection_status(String vc_transection_status) {
	this.vc_transection_status = vc_transection_status;
}

public String getVc_order_date() {
	return vc_order_date;
}

public void setVc_order_date(String vc_order_date) {
	this.vc_order_date = vc_order_date;
}



public String getDc_tot_price() {
	return dc_tot_price;
}

public void setDc_tot_price(String dc_tot_price) {
	this.dc_tot_price = dc_tot_price;
}

public String getTotalTicket() {
	return TotalTicket;
}

public void setTotalTicket(String totalTicket) {
	TotalTicket = totalTicket;
}

public String getVc_company_name() {
	return vc_company_name;
}

public void setVc_company_name(String vc_company_name) {
	this.vc_company_name = vc_company_name;
}

public String getVc_event_name() {
	return vc_event_name;
}

public void setVc_event_name(String vc_event_name) {
	this.vc_event_name = vc_event_name;
}

public String getVc_eventimg_url() {
	return vc_eventimg_url;
}

public void setVc_eventimg_url(String vc_eventimg_url) {
	this.vc_eventimg_url = vc_eventimg_url;
}


}
