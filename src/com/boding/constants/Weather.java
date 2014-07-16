package com.boding.constants;

import com.boding.R;


public enum Weather {
	SRAIN("srain","Ð¡À×Óê",R.drawable.weather_sun),
	CLOUD("cloud","¶àÔÆ",R.drawable.weather_cloud),
	FOG("fog","ö²",R.drawable.weather_sun),
	SUN("sun","ÇçÌì",R.drawable.weather_sun);
	
	private String weatherCode;
	private String weatherName;
	private int weatherDrawable;
	
	private Weather(String weatherCode, String weatherName, int weatherDrawable){
		this.weatherCode = weatherCode;
		this.weatherName = weatherName;
		this.weatherDrawable = weatherDrawable;
	}
	
	public static Weather getWeatherFromCode(String weatherCode){
		if(weatherCode.equals("srain"))
			return SRAIN;
		if(weatherCode.equals("cloud"))
			return CLOUD;
		if(weatherCode.equals("fog"))
			return FOG;
		
		return SUN;
	}
	
	public String getWeatherCode() {
		return weatherCode;
	}

	public String getWeatherName() {
		return weatherName;
	}

	public int getWeatherDrawable() {
		return weatherDrawable;
	}

	public void setWeatherDrawable(int weatherDrawable) {
		this.weatherDrawable = weatherDrawable;
	}
}
