package com.boding.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

import com.boding.constants.GlobalVariables;
import com.boding.model.BodingUser;
import com.boding.model.City;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPreferenceUtil {
	public static final String IS_FIRSTRUN = "isFirstRun";
	public static final String IS_AUTOLOGIN = "isAutoLogin";
	public static final String LOGIN_EXPIREDATE = "loginExpireDate";
	public static final String LOGIN_USERNAME = "loginUserName";
	public static final String LOGIN_PASSWORD = "loginPassword";
	public static final String HISTORYCITY_DOMESTIC= "historyCityDomestic";
	public static final String HISTORYCITY_INTERNATIONAL = "historyCityInternational";
	
	public static SharedPreferences getSharedPreferences(Context context){
		return context.getSharedPreferences("share", context.MODE_PRIVATE);  
	}
	
	public static void setBooleanSharedPreferences(Context context, String key, boolean value){
		Editor editor = getSharedPreferences(context).edit();
		editor.putBoolean(key, value);
		editor.commit();
	}
	
	public static void setStringSharedPreferences(Context context, String key, String value){
		Editor editor = getSharedPreferences(context).edit();
		editor.putString(key, value);
		editor.commit();
	}
	
	public static String getStringSharedPreferences(Context context, String key, String defaultValue){
		SharedPreferences sharedPreferences = getSharedPreferences(context);
		return sharedPreferences.getString(key, defaultValue);
	}
	
	public static boolean getBooleanSharedPreferences(Context context, String key, boolean defaultValue){
		SharedPreferences sharedPreferences = getSharedPreferences(context);
		return sharedPreferences.getBoolean(key, defaultValue);
	}
	
	/**
	 * 存储的username默认为卡号
	 * @param context
	 * @param password
	 */
	public static void successLogin(Context context,String password){
		// set autologin
		setBooleanSharedPreferences(context, IS_AUTOLOGIN, true);
		
		// set expire date
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, 1);
		setStringSharedPreferences(context, LOGIN_EXPIREDATE, DateUtil.getFormatedDate(calendar));
		
		// set username and password
		setStringSharedPreferences(context, LOGIN_USERNAME, GlobalVariables.bodingUser.getCardno());
		setStringSharedPreferences(context, LOGIN_PASSWORD, password);
	}
	
	/**
	 * 存储的时候采用citycode,citycode,citycode的形式
	 * @param cityCode
	 * @param context
	 */
	public static void addDomesticHistoryCity(String cityCode,Context context){
		String citiesString = getStringSharedPreferences(context, HISTORYCITY_DOMESTIC, "");
		setStringSharedPreferences(context, HISTORYCITY_DOMESTIC, generateAddHistoryCity(citiesString, cityCode));
	}
	
	private static String generateAddHistoryCity(String citiesString, String cityCode){
		String newCities = "";
		if(citiesString.equals("")){
			newCities = cityCode;
			return newCities;
		}
		String[] tempArray = citiesString.split(",");
		newCities += cityCode;
		boolean alreadyInHistory = false;
		if(tempArray.length == 3){
			for(int i = 0; i < tempArray.length - 1;i++){
				if(tempArray[i].equals(cityCode)){
					alreadyInHistory = true;
					continue;
				}
				newCities += "," + tempArray[i];
			}
			if(alreadyInHistory)
				newCities += "," + tempArray[tempArray.length - 1];
		}else{
			for(int i = 0; i < tempArray.length;i++){
				if(tempArray[i].equals(cityCode)){
					alreadyInHistory = true;
					continue;
				}
				newCities += "," + tempArray[i];
			}
		}
		
		return newCities;
	}
	
	public static void addInternationalHistoryCity(String cityCode,Context context){
		String citiesString = getStringSharedPreferences(context, HISTORYCITY_INTERNATIONAL, "");
		setStringSharedPreferences(context, HISTORYCITY_INTERNATIONAL, generateAddHistoryCity(citiesString, cityCode));
	}
	
	
	public static List<City> getDomesticHistoryCity(Context context){
		String citiesString = getStringSharedPreferences(context, HISTORYCITY_DOMESTIC, "");
		return getHistoryCity(citiesString);
	}
	
	private static List<City> getHistoryCity(String citiesString){
		List<City> cities = new ArrayList<City>();
		if(citiesString.equals("")){
			return cities;
		}
		String[] tempArray = citiesString.split(",");
		for(String cityCode: tempArray){
			cities.add(CityUtil.getCityByCode(cityCode));
		}
		return cities;
	}
	
	public static List<City> getInternationalHistoryCity(Context context){
		String citiesString = getStringSharedPreferences(context, HISTORYCITY_INTERNATIONAL, "");
		return getHistoryCity(citiesString);
	}
	
}
