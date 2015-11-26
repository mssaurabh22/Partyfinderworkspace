package com.partyfinder.model;

import java.io.Serializable;

public class PushTypeGeneric implements Serializable {
	
	
	private String eName;
	private String message;
	private String pushType;
	
	public PushTypeGeneric() {
		// TODO Auto-generated constructor stub
	}

	public String geteName() {
		return eName;
	}

	public void seteName(String eName) {
		this.eName = eName;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPushType() {
		return pushType;
	}

	public void setPushType(String pushType) {
		this.pushType = pushType;
	}

	
	
	
	
	
}
