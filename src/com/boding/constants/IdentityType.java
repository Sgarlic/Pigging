package com.boding.constants;

public enum IdentityType {
	NT("���֤",true),
	PP("����", false),
	GA("�۰�ͨ��֤", false),
	TB("̨��֤", false),
	HX("����֤", false),
	QT("����֤��", true);

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
}
