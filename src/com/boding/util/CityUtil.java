package com.boding.util;

import java.util.HashMap;
import java.util.Map;

import com.boding.constants.GlobalVariables;
import com.boding.model.Airport;
import com.boding.model.City;

public class CityUtil {
	public static Map<String, String> allCitiesMap;
	public static Map<String, String> allAirportCityMap;
	public static Map<String, String> allAirportsMap;
	
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
	
	public static String getAirportNameByCode(String airportCode){
		if(allAirportsMap == null){
			allAirportsMap = new HashMap<String, String>();
			for(Airport airport : GlobalVariables.domAirportList){
				if(!allAirportsMap.containsKey(airport.getAirportcode()))
					allAirportsMap.put(airport.getAirportcode(), airport.getAirportname());
			}
		}
		return allAirportsMap.get(airportCode);
	}
	
	public static String getCityNameByCode(String cityCode){
		if(allCitiesMap == null){
			allCitiesMap = new HashMap<String, String>();
			allAirportCityMap = new HashMap<String, String>();
			for(City city : GlobalVariables.domesticCitiesList){
				if(!allCitiesMap.containsKey(city.getCityCode()))
					allCitiesMap.put(city.getCityCode(), city.getCityName());
				if(!allAirportCityMap.containsKey(city.getAirportcode()))
					allAirportCityMap.put(city.getAirportcode(), city.getCityName());
			}
			for(City city : GlobalVariables.interCitiesList){
				if(!allCitiesMap.containsKey(city.getCityCode()))
					allCitiesMap.put(city.getCityCode(), city.getCityName());
				if(!allAirportCityMap.containsKey(city.getAirportcode()))
					allAirportCityMap.put(city.getAirportcode(), city.getCityName());
			}
		}
		String cityName = allCitiesMap.get(cityCode);
		if(cityName == null)
			cityName = allAirportCityMap.get(cityCode);
		return cityName;
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
