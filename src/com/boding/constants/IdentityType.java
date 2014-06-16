package com.boding.constants;

public enum IdentityType {
	ID_CARD("身份证"),
	OTHER_CARD("其他证件");

	private String identityType;

	public String getIdentityType() {
		return identityType;
	}

	private IdentityType(String identityType){
		this.identityType =  identityType;
	}
}
