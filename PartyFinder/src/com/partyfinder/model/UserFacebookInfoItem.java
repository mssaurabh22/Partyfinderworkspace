package com.partyfinder.model;

import java.io.Serializable;

public class UserFacebookInfoItem implements Serializable {
	
	
private String userFbID;
private String name;
private String email;
private String gender;
private String profilePic;
private String uuid;
private String password;
private String dateOfBirth;
//private String bir


public String getPassword() {
	return password;
}
public String getDateOfBirth() {
	return dateOfBirth;
}
public void setDateOfBirth(String dateOfBirth) {
	this.dateOfBirth = dateOfBirth;
}
public void setPassword(String password) {
	this.password = password;
}
public String getUuid() {
	return uuid;
}
public void setUuid(String uuid) {
	this.uuid = uuid;
}
public String getProfilePic() {
	return profilePic;
}
public void setProfilePic(String profilePic) {
	this.profilePic = profilePic;
}
public String getUserFbID() {
	return userFbID;
}
public void setUserFbID(String userFbID) {
	this.userFbID = userFbID;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getGender() {
	return gender;
}
public void setGender(String gender) {
	this.gender = gender;
}

}
