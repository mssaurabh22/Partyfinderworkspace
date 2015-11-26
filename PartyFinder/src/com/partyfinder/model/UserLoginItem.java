package com.partyfinder.model;

import java.io.Serializable;

import com.google.gson.Gson;


public class UserLoginItem implements Serializable  {

	/**
	 * @author Man Square Software, aru khanka
	 */
	
 private String vc_image_url;
 private String vc_user_code;
 private String vc_user_name;
 private String vc_user_pwd;
 private String vc_user_gender;
 private String vc_date_of_birth;
 private String vc_user_email;
 private String vc_user_add;
 private String vc_user_mobile_no;
 private String vc_user_country;
 private String vc_user_state;
 private String vc_user_city;
 private String nu_pin_code;
 private String ch_fb_login_user;
 private String ch_user_active;
 private String friend_count;
 private String Age;
 private String pf_user_image;
 private String UUID;
 private String vc_user_document;
 private String vc_login_time;
 private String vc_user_add_street_no;
 private String vc_neighborhood;
 
 
 
 

 public String serialize() {
     // Serialize this class into a JSON string using GSON
     Gson gson = new Gson();
     return gson.toJson(this);
 }

 static public UserLoginItem create(String serializedData) {
     // Use GSON to instantiate this class using the JSON representation of the state
     Gson gson = new Gson();
     return gson.fromJson(serializedData, UserLoginItem.class);
 }
	

 /**
 * @return the vc_image_url
 */
public String getVc_image_url() {
	return vc_image_url;
}
/**
 * @param vc_image_url the vc_image_url to set
 */
public void setVc_image_url(String vc_image_url) {
	this.vc_image_url = vc_image_url;
}
/**
 * @return the vc_user_code
 */
public String getVc_user_code() {
	return vc_user_code;
}
/**
 * @param vc_user_code the vc_user_code to set
 */
public void setVc_user_code(String vc_user_code) {
	this.vc_user_code = vc_user_code;
}
/**
 * @return the vc_user_name
 */
public String getVc_user_name() {
	return vc_user_name;
}
/**
 * @param vc_user_name the vc_user_name to set
 */
public void setVc_user_name(String vc_user_name) {
	this.vc_user_name = vc_user_name;
}
/**
 * @return the vc_user_pwd
 */
public String getVc_user_pwd() {
	return vc_user_pwd;
}
/**
 * @param vc_user_pwd the vc_user_pwd to set
 */
public void setVc_user_pwd(String vc_user_pwd) {
	this.vc_user_pwd = vc_user_pwd;
}
/**
 * @return the vc_user_gender
 */
public String getVc_user_gender() {
	return vc_user_gender;
}
/**
 * @param vc_user_gender the vc_user_gender to set
 */
public void setVc_user_gender(String vc_user_gender) {
	this.vc_user_gender = vc_user_gender;
}
/**
 * @return the vc_date_of_birth
 */
public String getVc_date_of_birth() {
	return vc_date_of_birth;
}
/**
 * @param vc_date_of_birth the vc_date_of_birth to set
 */
public void setVc_date_of_birth(String vc_date_of_birth) {
	this.vc_date_of_birth = vc_date_of_birth;
}
/**
 * @return the vc_user_email
 */
public String getVc_user_email() {
	return vc_user_email;
}
/**
 * @param vc_user_email the vc_user_email to set
 */
public void setVc_user_email(String vc_user_email) {
	this.vc_user_email = vc_user_email;
}
/**
 * @return the vc_user_add
 */
public String getVc_user_add() {
	return vc_user_add;
}
/**
 * @param vc_user_add the vc_user_add to set
 */
public void setVc_user_add(String vc_user_add) {
	this.vc_user_add = vc_user_add;
}
/**
 * @return the vc_user_mobile_no
 */
public String getVc_user_mobile_no() {
	return vc_user_mobile_no;
}
/**
 * @param vc_user_mobile_no the vc_user_mobile_no to set
 */
public void setVc_user_mobile_no(String vc_user_mobile_no) {
	this.vc_user_mobile_no = vc_user_mobile_no;
}
/**
 * @return the vc_user_country
 */
public String getVc_user_country() {
	return vc_user_country;
}
/**
 * @param vc_user_country the vc_user_country to set
 */
public void setVc_user_country(String vc_user_country) {
	this.vc_user_country = vc_user_country;
}
/**
 * @return the vc_user_state
 */
public String getVc_user_state() {
	return vc_user_state;
}
/**
 * @param vc_user_state the vc_user_state to set
 */
public void setVc_user_state(String vc_user_state) {
	this.vc_user_state = vc_user_state;
}
/**
 * @return the vc_user_city
 */
public String getVc_user_city() {
	return vc_user_city;
}
/**
 * @param vc_user_city the vc_user_city to set
 */
public void setVc_user_city(String vc_user_city) {
	this.vc_user_city = vc_user_city;
}
/**
 * @return the nu_pin_code
 */
public String getNu_pin_code() {
	return nu_pin_code;
}
/**
 * @param nu_pin_code the nu_pin_code to set
 */
public void setNu_pin_code(String nu_pin_code) {
	this.nu_pin_code = nu_pin_code;
}
/**
 * @return the ch_fb_login_user
 */
public String getCh_fb_login_user() {
	return ch_fb_login_user;
}
/**
 * @param ch_fb_login_user the ch_fb_login_user to set
 */
public void setCh_fb_login_user(String ch_fb_login_user) {
	this.ch_fb_login_user = ch_fb_login_user;
}
/**
 * @return the ch_user_active
 */
public String getCh_user_active() {
	return ch_user_active;
}
/**
 * @param ch_user_active the ch_user_active to set
 */
public void setCh_user_active(String ch_user_active) {
	this.ch_user_active = ch_user_active;
}
/**
 * @return the friend_count
 */
public String getFriend_count() {
	return friend_count;
}
/**
 * @param friend_count the friend_count to set
 */
public void setFriend_count(String friend_count) {
	this.friend_count = friend_count;
}
/**
 * @return the age
 */
public String getAge() {
	return Age;
}
/**
 * @param age the age to set
 */
public void setAge(String age) {
	Age = age;
}
/**
 * @return the pf_user_image
 */
public String getPf_user_image() {
	return pf_user_image;
}
/**
 * @param pf_user_image the pf_user_image to set
 */
public void setPf_user_image(String pf_user_image) {
	this.pf_user_image = pf_user_image;
}



public String getUUID() {
	return UUID;
}

public void setUUID(String uUID) {
	UUID = uUID;
}

public String getVc_user_document() {
	return vc_user_document;
}

public void setVc_user_document(String vc_user_document) {
	this.vc_user_document = vc_user_document;
}

public String getVc_login_time() {
	return vc_login_time;
}

public void setVc_login_time(String vc_login_time) {
	this.vc_login_time = vc_login_time;
}

public String getVc_user_add_street_no() {
	return vc_user_add_street_no;
}

public void setVc_user_add_street_no(String vc_user_add_street_no) {
	this.vc_user_add_street_no = vc_user_add_street_no;
}

public String getVc_neighborhood() {
	return vc_neighborhood;
}

public void setVc_neighborhood(String vc_neighborhood) {
	this.vc_neighborhood = vc_neighborhood;
}



}
