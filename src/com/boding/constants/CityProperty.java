package com.boding.constants;

public enum CityProperty{
	LOCATECITY("��λ"),
	HISTORY("��ʷ"),
	HOT("����"),
	CityList("�����б�");
	
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
