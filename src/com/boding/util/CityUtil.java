package com.boding.util;

import java.util.HashMap;
import java.util.Map;

import com.boding.constants.GlobalVariables;
import com.boding.model.City;

public class CityUtil {
	public static Map<String, String> allCitiesMap;
	
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
	
	public static String getCityNameByCode(String cityCode){
		if(allCitiesMap == null){
			allCitiesMap = new HashMap<String, String>();
			for(City city : GlobalVariables.domesticCitiesList){
				allCitiesMap.put(city.getCityCode(), city.getCityName());
			}
			for(City city : GlobalVariables.interCitiesList){
				allCitiesMap.put(city.getCityCode(), city.getCityName());
			}
		}
		return allCitiesMap.get(cityCode);
	}
	
	public static City getCityByCode(String cityCode){
		for(City city : GlobalVariables.domesticCitiesList){
			if(city.getCityCode().equals(cityCode))
				return city;
		}
		for(City city : GlobalVariables.interCitiesList){
			if(city.getCityCode().equals(cityCode))
				return city;
		}
		return null;
	}
}
