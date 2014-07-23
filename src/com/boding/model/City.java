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
	private String pinyin = "";
	private String initial="";
	private String airportcode;
	
	public String getInitial() {
		return initial;
	}

	public void setInitial(String initial) {
		this.initial = initial;
	}

	public City(){}
	
	public City(String cityName,String cityCode,boolean isInternationcalCity, String belongsToCountry){
		setCityName(cityName);
		setCityCode(cityCode);
		setInternationalCity(isInternationcalCity);
		setBelongsToCountry(belongsToCountry);
	}
	
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
		this.initial = "";
		this.pinyin = "";
		for(char hanziChar : cityName.toCharArray()){
			String charPinyin = Util.getPinYin(String.valueOf(hanziChar));
			String alpha = Util.getAlpha(charPinyin);
			this.pinyin += charPinyin;
			this.initial += alpha;
		}
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
		if(this.cityName.equals(anotherCity.getCityName()))
			return true;
		return false;
		
	}
	
	public String getPinyin() {
		return pinyin;
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

	public String getAirportcode() {
		return airportcode;
	}

	public void setAirportcode(String airportcode) {
		this.airportcode = airportcode;
	}
}
