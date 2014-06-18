package com.boding.model;

import com.boding.util.Util;

public class Country {
	private String countryName;
	private String countryPinyin;
	
	public Country(String countryName){
		this(countryName,Util.getPinYin(countryName));
	}
	
	public Country(String countryName,String countryPinyin){
		this.setCountryName(countryName);
		this.setCountryPinyin(countryPinyin);
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getCountryPinyin() {
		return countryPinyin;
	}

	public void setCountryPinyin(String countryPinyin) {
		this.countryPinyin = countryPinyin;
	}
}
