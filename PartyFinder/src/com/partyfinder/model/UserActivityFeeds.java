package com.partyfinder.model;

import java.io.Serializable;

public class UserActivityFeeds implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ActivityUser;
	private String ActivityAction;
	private String ActivityName;
	private String Activitydate;
	private String Activityimage;

 public UserActivityFeeds(){
	 
 }

/**
 * @return the activityUser
 */
public String getActivityUser() {
	return ActivityUser;
}

/**
 * @param activityUser the activityUser to set
 */
public void setActivityUser(String activityUser) {
	ActivityUser = activityUser;
}

/**
 * @return the activityAction
 */
public String getActivityAction() {
	return ActivityAction;
}

/**
 * @param activityAction the activityAction to set
 */
public void setActivityAction(String activityAction) {
	ActivityAction = activityAction;
}

/**
 * @return the activityName
 */
public String getActivityName() {
	return ActivityName;
}

/**
 * @param activityName the activityName to set
 */
public void setActivityName(String activityName) {
	ActivityName = activityName;
}

/**
 * @return the activitydate
 */
public String getActivitydate() {
	return Activitydate;
}

/**
 * @param activitydate the activitydate to set
 */
public void setActivitydate(String activitydate) {
	Activitydate = activitydate;
}

/**
 * @return the activityimage
 */
public String getActivityimage() {
	return Activityimage;
}

/**
 * @param activityimage the activityimage to set
 */
public void setActivityimage(String activityimage) {
	Activityimage = activityimage;
}
 
 
}
