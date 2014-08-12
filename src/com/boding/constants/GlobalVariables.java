package com.boding.constants;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.boding.model.Airport;
import com.boding.model.BodingUser;
import com.boding.model.City;
import com.boding.model.Country;
import com.boding.model.FlightDynamics;
import com.boding.model.FlightLine;
import com.boding.model.LowPriceSubscribe;
import com.boding.model.Province;
import com.boding.util.DateUtil;

public class GlobalVariables {
	public static int Version_Code = 1;
	public static String Version_Name = "";
	public static City Fly_From_City = null;
	public static City Fly_To_City = null;
	public static Airport SELECTED_AIRPORT = null;
	public static String From_City;
	public static String To_City;
	public static String Fly_From_Date = DateUtil.getFormatedDate(new Date());
	public static String Fly_Return_Date = DateUtil.getNextDateString();
	public static Boolean isRoundWaySelection = false;
	
	public static int Screen_Width = 0;
	public static int Screen_Height = 0;
	public static int App_Height = 0;
	
	public static City CurrentCity = null;
	public static Airport currentAirport = null;
	
	public static List<City> allCitiesList = new ArrayList<City>();
	public static List<City> domesticCitiesList = new ArrayList<City>();
	public static List<City> interCitiesList = new ArrayList<City>();
	public static List<City> domHotCitiesList = new ArrayList<City>();
	public static List<City> interHotCitiesList = new ArrayList<City>();
	public static List<Airport> domHotAirportsList = new ArrayList<Airport>();
	public static List<Airport> domAirportList = new ArrayList<Airport>();
//	public static List<City> domHistoryCitiesList = new ArrayList<City>();
//	public static List<City> interHistoryCitiesList = new ArrayList<City>();
	public static List<Country> allCountriesList = new ArrayList<Country>();
	public static List<Province> allProvincesList = new ArrayList<Province>();
	
	public static String filepath ="/data/data/com.boding/files/";
	public static String domeCityFile ="domeCities";
	public static String domHotCityFile ="domHotCities";
	public static String interCityFile ="interCities";
	public static String interHotCityFile ="interHotCities";
	public static String allCountriesFile = "allCountries";
	public static String domHotAirportsFile = "domHotAirports";
	public static String domAirportsFile = "domAirports";
	
	public static BodingUser bodingUser = null;
	
	public static FlightLine flightLine = null;
	public static FlightLine roundWayFlightLine = null;
	public static LowPriceSubscribe currentSubscribe = null;
	public static List<FlightDynamics> myFollowedFdList = new ArrayList<FlightDynamics>();
//	public static boolean isFlyToCitySelection = false;
//	public static boolean isInternationCitySelection = false;
}
