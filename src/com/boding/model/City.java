package com.boding.model;

public class City {
	private String cityName;
	private String cityCode;
	private boolean isInternationalCity;
	private String belongsToCountry;
	
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
}
