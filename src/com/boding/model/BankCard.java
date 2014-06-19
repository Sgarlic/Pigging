package com.boding.model;

import com.boding.util.Util;

public class BankCard {
	private String bankName;
	private String bankPinyin;
	private boolean isCreditCard;
	
	public BankCard(String bankName,boolean isCreditCard){
		this.setBankName(bankName);
		this.setBankPinyin(Util.getPinYin(bankName));
		this.setCreditCard(isCreditCard);
	}

	public String getBankName() {
		if(isCreditCard)
			return bankName + " - –≈”√ø®";
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankPinyin() {
		return bankPinyin;
	}

	public void setBankPinyin(String bankPinyin) {
		this.bankPinyin = bankPinyin;
	}

	public boolean isCreditCard() {
		return isCreditCard;
	}

	public void setCreditCard(boolean isCreditCard) {
		this.isCreditCard = isCreditCard;
	}
	
	
}
