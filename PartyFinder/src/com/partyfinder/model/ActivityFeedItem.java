package com.partyfinder.model;

import java.io.Serializable;

public class ActivityFeedItem implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	
	private String activityUser;
	private String activityAction;
	private String activityType;
	private String activityTime;
	private String activityUserImage;
	
	public String getActivityUser() {
		return activityUser;
	}
	public void setActivityUser(String activityUser) {
		this.activityUser = activityUser;
	}
	public String getActivityAction() {
		return activityAction;
	}
	public void setActivityAction(String activityAction) {
		this.activityAction = activityAction;
	}
	public String getActivityType() {
		return activityType;
	}
	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}
	public String getActivityTime() {
		return activityTime;
	}
	public void setActivityTime(String activityTime) {
		this.activityTime = activityTime;
	}
	public String getActivityUserImage() {
		return activityUserImage;
	}
	public void setActivityUserImage(String activityUserImage) {
		this.activityUserImage = activityUserImage;
	}

}
