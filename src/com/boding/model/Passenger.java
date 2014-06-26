package com.boding.model;

import java.util.Date;

import com.boding.constants.IdentityType;

public class Passenger {
	private String auto_id;
	private String cardno;
	private String name;
	private String eName;//��/��
	private IdentityType IdentityType;
	private String PassPaper;// ֤����Ϣ ��ʽ:֤������^֤����^֤����Ч��| 
	private String cardNumber;
	private Date validDate;
	private String nationality;
	private boolean isFemale;
	private Date birthDate;//��ʽ:YYYY-MM-DD
	
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
