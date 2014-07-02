package com.boding.constants;

public enum CityProperty{
	LOCATECITY("定位"),
	HISTORY("历史"),
	HOT("热门"),
	CityList("城市列表");
	
	private String property;
	
	private CityProperty(String property){
		this.setProperty(property);
	}

	public String getProperty() {
		return property;
	}

	private void setProperty(String property) {
		this.property = property;
	}
}
