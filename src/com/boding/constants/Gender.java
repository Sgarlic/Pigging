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
	
	public String getGenderCode(){
		return genderCode;
	}
	
	public String toString(){
		return gender;
	}
	
	public static Gender getGenderFromCode(String code){
		if(code.equals("M"))
			return Male;
		return Female;
	}
	public static Gender getGenderFromName(String code){
		if(code.equals("ÄÐ"))
			return Male;
		return Female;
	}
	
}
