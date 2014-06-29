package com.boding.model;

import java.io.Serializable;
import java.util.Comparator;

import com.boding.util.Util;

public class City implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3055460233193084538L;
	private String cityName;
	private String cityCode;
	private boolean isInternationalCity;
	private String belongsToCountry;
	private String pinyin;
	
	public City(){}
	
	public City(String cityName,String cityCode,boolean isInternationcalCity, String belongsToCountry){
		this.cityName = cityName;
		this.cityCode = cityCode;
		this.isInternationalCity = isInternationcalCity;
		this.belongsToCountry = belongsToCountry;
	}
	
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public boolean isInternationalCity() {
		return isInternationalCity;
	}
	public void setInternationalCity(boolean isInternationalCity) {
		this.isInternationalCity = isInternationalCity;
	}
	public String getBelongsToCountry() {
		return belongsToCountry;
	}
	public void setBelongsToCountry(String belongsToCountry) {
		this.belongsToCountry = belongsToCountry;
	}
	public boolean equals(City anotherCity){
		if(anotherCity == null)
			return false;
//		if(this.cityCode .equals(anotherCity.getCityCode()))
//			return true;
//		return false;
		if(this.cityName.equals(anotherCity.getCityName()))
			return true;
		return false;
		
	}
	
	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	public static class CityNameComp implements Comparator<City>{

		@Override
		public int compare(City lhs, City rhs) {
			return lhs.pinyin.compareTo(rhs.pinyin);
		}
		
	}
	
	@Override
	public boolean equals(Object o) {
		City c = (City) o;
        return this.cityName.equals(c.cityName);
    }
}
