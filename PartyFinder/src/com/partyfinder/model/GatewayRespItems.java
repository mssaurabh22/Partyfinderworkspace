package com.partyfinder.model;

import java.io.Serializable;

import android.util.Log;

public class GatewayRespItems implements Serializable {
	int transectionCode;
	String transectionStatus;
	private String TAG="Purchase Ticket Activity Class";//Gateway Items Model";
	
	
	
	public int getTransectionCode() {
		return transectionCode;
	}

	public void setTransectionCode(int transectionCode) {
		Log.i("",TAG+ "transection code :" +transectionCode);
		this.transectionCode = transectionCode;
	}



	public String getTransectionStatus() {
		return transectionStatus;
	}

	public void setTransectionStatus(String transectionStatus) {
		Log.i("",TAG+ " transection status :" + transectionStatus);
		this.transectionStatus = transectionStatus;
	}
	
	
	

}
