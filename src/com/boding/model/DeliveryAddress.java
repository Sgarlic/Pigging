package com.boding.model;

public class DeliveryAddress {
	private String addrID;
	private String recipientName;
	private String area;
	private String detailedAddr;
	private String zipcode;
	
	private String Province;
	private String City;
	private String Area;
	
	private String mobile;
	private String phone;
	
	public DeliveryAddress(String recipientName,String area,String detailedAddr,String zipcode){
		this.recipientName = recipientName;
		this.area = area;
		this.detailedAddr = detailedAddr;
		this.zipcode = zipcode;
	}
	
	
	public String getRecipientName() {
		return recipientName;
	}
	public void setRecipientName(String recipientName) {
		this.recipientName = recipientName;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getDetailedAddr() {
		return detailedAddr;
	}
	public void setDetailedAddr(String detailedAddr) {
		this.detailedAddr = detailedAddr;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	
	
}
