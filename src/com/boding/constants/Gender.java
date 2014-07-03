package com.boding.constants;

public enum Gender {
	Male("M","��"),
	Female("F","Ů");
	
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
		else if(code.equals("F"))
			return Female;
		return null;
	}
	public static Gender getGenderFromName(String code){
		if(code.equals("��"))
			return Male;
		else if(code.equals("Ů"))
			return Female;
		return null;
	}
	
}
