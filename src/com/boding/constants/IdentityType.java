package com.boding.constants;

public enum IdentityType {
	NI("身份证",true),
	PP("护照", false),
	GA("港澳通行证", false),
	TB("台胞证", false),
	HX("回乡证", false),
	QT("其他证件", true);

	private String identityType;
	private boolean isDomestic;
	
	public String getIdentityName() {
		return identityType;
	}
	
	public boolean isDomestic(){
		return isDomestic;
	}

	private IdentityType(String identityType, boolean isDomestic){
		this.identityType =  identityType;
		this.isDomestic  = isDomestic;
	}
	
	public static IdentityType getIdentityTypeFromIDCode(String idCode){
		if(idCode.equals("NI")){
			return NI;
		}else if(idCode.equals("PP")){
			return PP;
		}else if(idCode.equals("GA")){
			return GA;
		}else if(idCode.equals("TB")){
			return TB;
		}else if(idCode.equals("HX")){
			return HX;
		}
		return QT;
	}
}
