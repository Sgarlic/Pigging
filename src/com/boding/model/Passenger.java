package com.boding.model;


import java.util.Calendar;

import android.os.Parcel;
import android.os.Parcelable;

import com.boding.constants.Gender;
import com.boding.constants.IdentityType;

public class Passenger implements Parcelable{
	/**
	 * 
	 */
	private String auto_id = "";//乘机人ID
	private String cardno = "";//用户代码/卡号
	private String name = "";//中文姓名
	private String eName = "";//英文姓名  格式:姓/名
	private IdentityType identityType = null;
	private String passPaper = "";// 证件信息 格式:证件类型^证件号^证件有效期| 
	//多个使用“|”分割
	//如：NI^350783199011156570^|PP^G542124854544^2015-05-05|

	private String cardNumber = "";//
	private String validDate = "";//
	private String nationality = "";//国籍 格式:国家-国家代码
	private Gender gender = null;//
	private String birthday = "";//生日 格式:YYYY-MM-DD
	
	public Passenger(){
		
	}
	
	public Passenger(String name, String paper_type, String id_code){
		this.identityType = IdentityType.getIdentityTypeFromIDCode(paper_type);
		if(identityType == IdentityType.NI){
			this.cardNumber = id_code;
			setName(name);
		}
		else{
			this.cardNumber = id_code.split("/")[1];
			seteName(name);
		}
	}
	
	public Passenger(String auto_id, String cardno, String name, String eName, String brithday,
			String nationality, String PassPaper){
		setAuto_id(auto_id);
		setCardno(cardno);
		setName(name);
		seteName(eName);
		setBirthday(brithday);
		setNationality(nationality);
		setPassPaper(PassPaper);
	}
	
	
	public String getDiaplayName(){
		if(identityType == IdentityType.NI)
			return name;
		return eName;
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
	
	public Gender getGender(){
		return gender;
	}
	
	public void setGenderFromGName(String genderName){
		this.gender = Gender.getGenderFromName(genderName);
	}
	
	public Passenger(String name, String cardNumber, IdentityType identityType){
		this.name = name;
		this.cardNumber = cardNumber;
		this.identityType = identityType;
	}
	public IdentityType getIdentityType() {
		return identityType;
	}
	public void setIdentityType(IdentityType identityType) {
		this.identityType = identityType;
	}
	public String getAuto_id() {
		return auto_id;
	}
	public void setAuto_id(String auto_id) {
		this.auto_id = auto_id;
	}
	public String getCardno() {
		return cardno;
	}
	public void setCardno(String cardno) {
		this.cardno = cardno;
	}



	public String geteName() {
		return eName;
	}



	public void seteName(String eName) {
		this.eName = eName;
	}



	public String getBirthday() {
		return birthday;
	}



	public void setBirthday(String birthDate) {
		this.birthday = birthDate;
	}



	public String getNationality() {
		return nationality;
	}



	public void setNationality(String nationality) {
		this.nationality = nationality;
	}



	public String getPassPaper() {
		return passPaper;
	}



	public void setPassPaper(String passPaper) {
		this.passPaper = passPaper;
		if(passPaper.equals(""))
			return;
		String[] passPaperArray = passPaper.split("\\|");
		String[] passPaperEntity = passPaperArray[0].split("\\^");
		this.identityType = IdentityType.getIdentityTypeFromIDCode(passPaperEntity[0]);
		this.cardNumber = passPaperEntity[1];
		if(passPaperEntity.length == 3)
			this.setValidDate(passPaperEntity[2]);
	}

	public String getValidDate() {
		return validDate;
	}

	public void setValidDate(String validDate) {
		this.validDate = validDate;
	}
	
	
	@Override
	public int describeContents() {
		return 0;
	}
	
	public Passenger(Parcel in){
		auto_id = in.readString();
		cardno = in.readString();
		name = in.readString();
		eName = in.readString();
		identityType = IdentityType.getIdentityTypeFromIDCode(in.readString());
		passPaper = in.readString();
		cardNumber = in.readString();
		validDate = in.readString();
		nationality = in.readString();
		gender = Gender.getGenderFromCode(in.readString());
		birthday = in.readString();
	}
	
	@Override
	public void writeToParcel(Parcel dest, int arg1) {
		dest.writeString(auto_id);
		dest.writeString(cardno);
		dest.writeString(name);
		dest.writeString(eName);
		dest.writeString(identityType.toString());
		dest.writeString(passPaper);
		dest.writeString(cardNumber);
		dest.writeString(validDate);
		dest.writeString(nationality);
		if(gender!=null)
			dest.writeString(gender.getGenderCode());
		else
			dest.writeString("");
		dest.writeString(birthday);
	}
	
	 public static final Parcelable.Creator<Passenger> CREATOR = new Parcelable.Creator<Passenger>() {   
		//重写Creator
		  
		 public Passenger createFromParcel(Parcel in) {  
	            return new Passenger(in);  
	        }  
	          
	        public Passenger[] newArray(int size) {  
	            return new Passenger[size];  
	        }  
	 };
	 
	 public boolean isAdult(){
		 int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		 int birthYear = 0;
		 if(identityType == IdentityType.NI){
			 birthYear = Integer.parseInt(cardNumber.substring(6, 10));
		 }else{
			 birthYear = Integer.parseInt(birthday.split("-")[0]);
		 }
		 System.out.println(cardNumber + "    "+ birthYear);
		 if((currentYear - birthYear)>6)
			 return true;
		 return false;
	 }
}
