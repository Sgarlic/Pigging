package com.boding.constants;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import com.boding.model.City;

public class GlobalVariables {
	public static City Fly_From_City = null;
	public static City Fly_To_City = null;
	public static String Fly_From_Date = "2014-06-13";
	public static String Fly_Return_Date = "2014-08-23";
	
	public static int Screen_Width = 0;
	public static int Screen_Height = 0;
	public static int App_Height = 0;
	
	public static List<ContentValues> allCitiesList = new ArrayList<ContentValues>();
	public static List<City> domesticCitiesList = new ArrayList<City>();
	public static List<City> interCitiesList = new ArrayList<City>();
	public static List<City> domHotCitiesList = new ArrayList<City>();
	public static List<City> interHotCitiesList = new ArrayList<City>();
	public static List<City> domHistoryCitiesList = new ArrayList<City>();
	public static List<City> interHistoryCitiesList = new ArrayList<City>();
//	public static boolean isFlyToCitySelection = false;
//	public static boolean isInternationCitySelection = false;
}
