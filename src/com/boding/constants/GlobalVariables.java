package com.boding.constants;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import com.boding.model.City;

public class GlobalVariables {
	public static City Fly_From_City = null;
	public static City Fly_To_City = null;
	public static String Fly_From_Date = "2014-06-23";
	public static String Fly_Return_Date = "2014-06-30";
	
	public static int Screen_Width = 0;
	public static int Screen_Height = 0;
	public static int App_Height = 0;
	
	public static List<City> allCitiesList = new ArrayList<City>();
	public static List<City> domesticCitiesList = new ArrayList<City>();
	public static List<City> interCitiesList = new ArrayList<City>();
	public static List<City> domHotCitiesList = new ArrayList<City>();
	public static List<City> interHotCitiesList = new ArrayList<City>();
	public static List<City> domHistoryCitiesList = new ArrayList<City>();
	public static List<City> interHistoryCitiesList = new ArrayList<City>();
	
	public static String filepath ="/data/data/com.boding/files/";
	public static String domeCityFile ="domeCities";
	public static String domHotCityFile ="domHotCities";
	public static String interCityFile ="interCities";
	public static String interHotCityFile ="interHotCities";
//	public static boolean isFlyToCitySelection = false;
//	public static boolean isInternationCitySelection = false;
}
