package com.boding.constants;

public enum IdentityType {
	ID_CARD("���֤"),
	OTHER_CARD("����֤��");

	private String identityType;

	public String getIdentityType() {
		return identityType;
	}

	private IdentityType(String identityType){
		this.identityType =  identityType;
	}
}
