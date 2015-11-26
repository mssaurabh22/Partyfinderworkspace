package com.partyfinder.model;

import java.io.Serializable;

public class VenuesItem implements Serializable {


	private	String vc_image_url;
	private String vc_company_code;
	private String vc_user_code;
	private String NomeFantasia;
	private String vc_addr_street;
	private String nu_addr_number;
	private String vc_comercial_tel;
	private String vc_city_name;
	private String vc_state_name;
	private String vc_country_name;
	private String nu_zip_code;
	private String vc_description;
	private String TotalLike;
	private String vc_like_status;
	private String vc_fav_venue;
	private String companyName;
	private String pinCode;

	public String getPinCode() {
		return pinCode;
	}

	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public VenuesItem(){

	}

	public String getVc_image_url() {
		return vc_image_url;
	}

	public void setVc_image_url(String vc_image_url) {
		this.vc_image_url = vc_image_url;
	}

	public String getVc_company_code() {
		return vc_company_code;
	}

	public void setVc_company_code(String vc_company_code) {
		this.vc_company_code = vc_company_code;
	}

	public String getVc_user_code() {
		return vc_user_code;
	}

	public void setVc_user_code(String vc_user_code) {
		this.vc_user_code = vc_user_code;
	}

	public String getNomeFantasia() {
		return NomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		NomeFantasia = nomeFantasia;
	}

	public String getVc_addr_street() {
		return vc_addr_street;
	}

	public void setVc_addr_street(String vc_addr_street) {
		this.vc_addr_street = vc_addr_street;
	}

	public String getNu_addr_number() {
		return nu_addr_number;
	}

	public void setNu_addr_number(String nu_addr_number) {
		this.nu_addr_number = nu_addr_number;
	}

	public String getVc_comercial_tel() {
		return vc_comercial_tel;
	}

	public void setVc_comercial_tel(String vc_comercial_tel) {
		this.vc_comercial_tel = vc_comercial_tel;
	}

	public String getVc_city_name() {
		return vc_city_name;
	}

	public void setVc_city_name(String vc_city_name) {
		this.vc_city_name = vc_city_name;
	}

	public String getVc_state_name() {
		return vc_state_name;
	}

	public void setVc_state_name(String vc_state_name) {
		this.vc_state_name = vc_state_name;
	}

	public String getVc_country_name() {
		return vc_country_name;
	}

	public void setVc_country_name(String vc_country_name) {
		this.vc_country_name = vc_country_name;
	}

	public String getNu_zip_code() {
		return nu_zip_code;
	}

	public void setNu_zip_code(String nu_zip_code) {
		this.nu_zip_code = nu_zip_code;
	}

	public String getVc_description() {
		return vc_description;
	}

	public void setVc_description(String vc_description) {
		this.vc_description = vc_description;
	}

	public String getTotalLike() {
		return TotalLike;
	}

	public void setTotalLike(String totalLike) {
		TotalLike = totalLike;
	}

	public String getVc_like_status() {
		return vc_like_status;
	}

	public void setVc_like_status(String vc_like_status) {
		this.vc_like_status = vc_like_status;
	}

	public String getVc_fav_venue() {
		return vc_fav_venue;
	}

	public void setVc_fav_venue(String vc_fav_venue) {
		this.vc_fav_venue = vc_fav_venue;
	}



}
