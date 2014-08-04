package com.boding.constants;

import com.boding.R;


public enum Weather {
	SRAIN("srain","Ð¡À×Óê",R.drawable.weather_thunder, R.drawable.weather_srain_medium),
	CLOUD("cloud","¶àÔÆ",R.drawable.weather_cloud, R.drawable.weather_cloud_medium),
	FOG("fog","ö²",R.drawable.weather_fog, R.drawable.weather_flog_medium),
	SUN("sun","ÇçÌì",R.drawable.weather_sun, R.drawable.weather_sun_medium);
	
	private String weatherCode;
	private String weatherName;
	private int weatherDrawable;
	private int weatherDrawableMedium;
	
	private Weather(String weatherCode, String weatherName, int weatherDrawable, int weatherDrawableMedium){
		this.weatherCode = weatherCode;
		this.weatherName = weatherName;
		this.weatherDrawable = weatherDrawable;
		this.weatherDrawableMedium = weatherDrawableMedium;
	}
	
	public static Weather getWeatherFromCode(String weatherCode){
		if(weatherCode.equals("srain"))
			return SRAIN;
		if(weatherCode.equals("cloud"))
			return CLOUD;
		if(weatherCode.equals("fog"))
			return FOG;
		if(weatherCode.equals("sun"))
			return SUN;
		
		return null;
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

	public int getWeatherDrawableMedium() {
		return weatherDrawableMedium;
	}

	public void setWeatherDrawableMedium(int weatherDrawableMedium) {
		this.weatherDrawableMedium = weatherDrawableMedium;
	}
}
