package com.boding.model;

import java.util.Date;

import com.boding.constants.IdentityType;
import com.boding.util.Util;

public class Passenger {
	private String auto_id;//�˻���ID
	private String cardno;//�û�����/����
	private String name;//��������
	private String eName;//Ӣ������  ��ʽ:��/��
	private IdentityType identityType = IdentityType.NT;
	private String passPaper;// ֤����Ϣ ��ʽ:֤������^֤����^֤����Ч��| 
	//���ʹ�á�|���ָ�
	//�磺NI^350783199011156570^|PP^G542124854544^2015-05-05|

	private String cardNumber;//
	private String validDate;//
	private String nationality;//���� ��ʽ:����-���Ҵ���
	private boolean isFemale;//
	private String birthday;//���� ��ʽ:YYYY-MM-DD
	
	// for other identity type
	private String paperCode;//
	private String paperNum;//
	
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
		this.identityType = identityType;
	}
	public IdentityType getIdentityType() {
		return identityType;
	}
	public void setIdentityType(IdentityType identityType) {
		identityType = identityType;
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
		this.identityType = Util.getIdentityTypeFromIDCode(passPaperEntity[0]);
		this.cardNumber = passPaperEntity[1];
		if(passPaperEntity.length == 3)
			this.validDate = passPaperEntity[2];
	}
}
