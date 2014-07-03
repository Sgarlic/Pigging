package com.boding.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class Province {
	private String provinceName;
	private Map<String, ArrayList<String>> cityList;
	
	public Province(String provinceName){
		setProvinceName(provinceName);
		cityList = new HashMap<String, ArrayList<String>>();
	}
	
	public Iterator<Entry<String, ArrayList<String>>> getCityListIterator(){
		return cityList.entrySet().iterator();
	}
	
	public void addCity(String cityName, ArrayList<String> districtList){
		cityList.put(cityName, districtList);
	}
	
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
}
