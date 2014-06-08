package com.boding.constants;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;

import com.boding.model.City;

public class GlobalVariables {
	public static City Fly_From_City = null;
	public static City Fly_To_City = null;
	public static String Fly_From_Date = null;
	public static String Fly_To_Date = null;
	
	public static int Screen_Width = 0;
	public static int Screen_Height = 0;
	public static int App_Height = 0;
	
	public static List<ContentValues> allCitiesList = new ArrayList<ContentValues>();
//	public static boolean isFlyToCitySelection = false;
//	public static boolean isInternationCitySelection = false;
}
