package com.boding.constants;

public enum Gender {
	Male("M","ÄÐ"),
	Female("F","Å®");
	
	private String genderCode;
	private String gender;
	
	private Gender(String genderCode, String gender){
		this.genderCode = genderCode;
		this.gender = gender;
	}
	
	public String toString(){
		return gender;
	}
}
