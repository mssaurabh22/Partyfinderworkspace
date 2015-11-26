package com.partyfinder.model;

import java.io.Serializable;

public class EditUserInfoItem implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String statusCode;
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

}
