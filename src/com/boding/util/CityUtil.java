package com.boding.util;

import com.boding.constants.GlobalVariables;
import com.boding.model.City;

public class CityUtil {
	public static boolean isDomesticCity(String cityname){
		City city = new City();
		city.setCityName(cityname);
		return GlobalVariables.domesticCitiesList.contains(city);
	}
	
	public static City getCityByName(String cityname){
		for(City city : GlobalVariables.domesticCitiesList){
			if(city.getCityName().equals(cityname))
				return city;
		}
		for(City city : GlobalVariables.interCitiesList){
			if(city.getCityName().equals(cityname))
				return city;
		}
		return null;
	}
}
