package com.boding.model;

import java.util.Date;

import com.boding.constants.IdentityType;

public class Passenger {
	private String auto_id;
	private String cardno;
	private String name;
	private String eName;//姓/名
	private IdentityType IdentityType;
	private String PassPaper;// 证件信息 格式:证件类型^证件号^证件有效期| 
	private String cardNumber;
	private Date validDate;
	private String nationality;
	private boolean isFemale;
	private Date birthDate;//格式:YYYY-MM-DD
	
	// for other identity type
	private String paperCode;
	private String paperNum;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	
	public String getGenderCode(){
		if(isFemale)
			return "F";
		else
			return "M";
	}
	
	public Passenger(String name, String cardNumber, IdentityType identityType){
		this.name = name;
		this.cardNumber = cardNumber;
		this.IdentityType = identityType;
	}
	public IdentityType getIdentityType() {
		return IdentityType;
	}
	public void setIdentityType(IdentityType identityType) {
		IdentityType = identityType;
	}
}
