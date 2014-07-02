package com.boding.model;

import java.io.Serializable;
import java.util.Comparator;

import com.boding.util.Util;

public class Country implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6493438543246182704L;
	private String countryCode;//���Ҷ��ִ���
	private String countryName;//��������
	private String countryEng;//Ӣ����
	private String countryPinyin;//ƴ��
	private boolean countryHot;//�Ƿ����� True���� False����
	private int countrySort;//���� �ӵ͵���
	
	public Country(String countryCode, String countryName, String countryEng, String countryPinyin,
			boolean countryHot, int countrySort){
		setCountryCode(countryCode);
		setCountryName(countryName);
		setCountryEng(countryEng);
		setCountryPinyin(countryPinyin);
		setCountryHot(countryHot);
		setCountrySort(countrySort);
	}
	
	public Country(String countryName){
		this(countryName,Util.getPinYin(countryName));
	}
	
	public Country(String countryName,String countryPinyin){
		this.setCountryName(countryName);
		this.setCountryPinyin(countryPinyin);
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getCountryPinyin() {
		return countryPinyin;
	}

	public void setCountryPinyin(String countryPinyin) {
		this.countryPinyin = countryPinyin;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getCountryEng() {
		return countryEng;
	}

	public void setCountryEng(String countryEng) {
		this.countryEng = countryEng;
	}

	public boolean isCountryHot() {
		return countryHot;
	}

	public void setCountryHot(boolean countryHot) {
		this.countryHot = countryHot;
	}

	public int getCountrySort() {
		return countrySort;
	}

	public void setCountrySort(int countrySort) {
		this.countrySort = countrySort;
	}
	
	public static class CountryCamp implements Comparator<Country>{

		@Override
		public int compare(Country lhs, Country rhs) {
			return lhs.countryPinyin.compareTo(rhs.countryPinyin);
		}
		
	}
}
