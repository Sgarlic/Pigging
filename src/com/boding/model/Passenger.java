package com.boding.model;

import java.util.Date;

import com.boding.constants.IdentityType;

public class Passenger {
	private boolean isInternal;
	private String name;
	private String lastName;
	private String firstName;
	private IdentityType IdentityType;
	private String cardNumber;
	private Date validDate;
	private String nationality;
	private boolean gender;//true is male, false is female
	private Date birthDate;
	public boolean isInternal() {
		return isInternal;
	}
	public void setInternal(boolean isInternal) {
		this.isInternal = isInternal;
	}
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
	
	public Passenger(boolean isInternal, String name, String cardNumber){
		this.isInternal = isInternal;
		this.name = name;
		this.cardNumber = cardNumber;
	}
}
