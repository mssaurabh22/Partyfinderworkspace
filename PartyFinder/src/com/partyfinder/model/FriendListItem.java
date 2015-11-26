package com.partyfinder.model;

import java.io.Serializable;

public class FriendListItem implements  Serializable {
	
	private String imageUrl="NA";
	private String userCode;
	private String userName;
	private String userGender;
	private String userDOB;
	private String userEmail;
	private String userAddress;
	private String userMobile;
	private String userCountry;
	private String userState;
	private String userCity;
	private String userPinCode;
	private String userFbLogin;
	private String userActive;
	private String userFriendCount;
	private String userAge;
	private String userImage;
	private String IsFriend;
	private String companyName;
	private String companyCode;
	private boolean checkStatus;
	
	
	public boolean isCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(boolean checkStatus) {
		this.checkStatus = checkStatus;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public FriendListItem(){
		
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

	public String getUserGender() {
		return userGender;
	}

	public void setUserGender(String userGender) {
		this.userGender = userGender;
	}

	public String getUserDOB() {
		return userDOB;
	}

	public void setUserDOB(String userDOB) {
		this.userDOB = userDOB;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserAddress() {
		return userAddress;
	}

	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}

	public String getUserMobile() {
		return userMobile;
	}

	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}

	public String getUserCountry() {
		return userCountry;
	}

	public void setUserCountry(String userCountry) {
		this.userCountry = userCountry;
	}

	public String getUserState() {
		return userState;
	}

	public void setUserState(String userState) {
		this.userState = userState;
	}

	public String getUserCity() {
		return userCity;
	}

	public void setUserCity(String userCity) {
		this.userCity = userCity;
	}

	public String getUserPinCode() {
		return userPinCode;
	}

	public void setUserPinCode(String userPinCode) {
		this.userPinCode = userPinCode;
	}

	public String getUserFbLogin() {
		return userFbLogin;
	}

	public void setUserFbLogin(String userFbLogin) {
		this.userFbLogin = userFbLogin;
	}

	public String getUserActive() {
		return userActive;
	}

	public void setUserActive(String userActive) {
		this.userActive = userActive;
	}

	public String getUserFriendCount() {
		return userFriendCount;
	}

	public void setUserFriendCount(String userFriendCount) {
		this.userFriendCount = userFriendCount;
	}

	public String getUserAge() {
		return userAge;
	}

	public void setUserAge(String userAge) {
		this.userAge = userAge;
	}

	public String getUserImage() {
		return userImage;
	}

	public void setUserImage(String userImage) {
		this.userImage = userImage;
	}

	public String getIsFriend() {
		return IsFriend;
	}

	public void setIsFriend(String isFriend) {
		IsFriend = isFriend;
	}
}
