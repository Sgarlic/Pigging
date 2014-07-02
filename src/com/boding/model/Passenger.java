package com.boding.model;

import java.io.Serializable;
import java.util.Date;

import com.boding.constants.Gender;
import com.boding.constants.IdentityType;
import com.boding.util.Util;

public class Passenger implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7738960923064852257L;
	private String auto_id;//乘机人ID
	private String cardno;//用户代码/卡号
	private String name;//中文姓名
	private String eName;//英文姓名  格式:姓/名
	private IdentityType identityType = null;
	private String passPaper;// 证件信息 格式:证件类型^证件号^证件有效期| 
	//多个使用“|”分割
	//如：NI^350783199011156570^|PP^G542124854544^2015-05-05|

	private String cardNumber;//
	private String validDate;//
	private String nationality;//国籍 格式:国家-国家代码
	private Gender gender = null;//
	private String birthday;//生日 格式:YYYY-MM-DD
	
	public Passenger(){
		
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
}
