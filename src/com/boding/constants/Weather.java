package com.boding.constants;

public enum Weather {
	SRAIN("srain","Ð¡À×Óê"),
	CLOUD("cloud","¶àÔÆ"),
	FOG("fog","ö²"),
	SUN("sun","ÇçÌì");
	
	private String weatherCode;
	public String getWeatherCode() {
		return weatherCode;
	}

	public String getWeatherName() {
		return weatherName;
	}

	private String weatherName;
	
	private Weather(String weatherCode, String weatherName){
		this.weatherCode = weatherCode;
		this.weatherName = weatherName;
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
}
