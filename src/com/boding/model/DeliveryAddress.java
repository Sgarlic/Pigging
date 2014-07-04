package com.boding.model;

import com.boding.constants.Gender;
import com.boding.constants.GlobalVariables;
import com.boding.constants.IdentityType;

import android.os.Parcel;
import android.os.Parcelable;

public class DeliveryAddress implements Parcelable{
	private String addrID;
	private String recipientName;
	private Province province;
	private String city;
	private String district;
	private String detailedAddr;
	private String zipcode;
	private String mobile;
	private String phone;
	
	public DeliveryAddress(){
		
	}
	
	public DeliveryAddress(String addrId, String recipientName, String provinceName,
			String city, String district, String detailedAddr, String zipcode,
			String mobile, String phone){
		setAddrID(addrId);
		setRecipientName(recipientName);
		setProvince(provinceName);
		setCity(city);
		setDistrict(district);
		setDetailedAddr(detailedAddr);
		setZipcode(zipcode);
		setMobile(mobile);
		setPhone(phone);
	}
	public String getDisplayAddr(){
		return province+city+district+detailedAddr;
	}
	
	public String getRecipientName() {
		return recipientName;
	}
	public void setRecipientName(String recipientName) {
		this.recipientName = recipientName;
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


	public String getAddrID() {
		return addrID;
	}


	public void setAddrID(String addrID) {
		this.addrID = addrID;
	}


	public String getProvinceName() {
		return province.getProvinceName();
	}

	public Province getProvince(){
		return province;
	}

	public void setProvince(Province province){
		this.province = province;
	}
	
	public void setProvince(String province) {
		for(Province provinceTemp : GlobalVariables.allProvincesList){
			if(provinceTemp.getProvinceName().equals(province)){
				this.province = provinceTemp;
				break;
			}
		}
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	public String getDistrict() {
		return district;
	}


	public void setDistrict(String district) {
		this.district = district;
	}


	public String getMobile() {
		return mobile;
	}


	public void setMobile(String mobile) {
		this.mobile = mobile;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Override
	public int describeContents() {
		return 0;
	}
	
	public DeliveryAddress(Parcel in){
		addrID = in.readString();
		recipientName = in.readString();
		setProvince(in.readString());
		city = in.readString();
		district = in.readString();
		detailedAddr = in.readString();
		zipcode = in.readString();
		mobile = in.readString();
		phone = in.readString();
	}
	
	@Override
	public void writeToParcel(Parcel dest, int arg1) {
		dest.writeString(addrID);
		dest.writeString(recipientName);
		dest.writeString(province.getProvinceName());
		dest.writeString(city);
		dest.writeString(district);
		dest.writeString(detailedAddr);
		dest.writeString(zipcode);
		dest.writeString(mobile);
		dest.writeString(phone);
	}
	
	 public static final Parcelable.Creator<DeliveryAddress> CREATOR = new Parcelable.Creator<DeliveryAddress>() {   
		//÷ÿ–¥Creator
		  
		 public DeliveryAddress createFromParcel(Parcel in) {  
	            return new DeliveryAddress(in);  
	        }  
	          
	        public DeliveryAddress[] newArray(int size) {  
	            return new DeliveryAddress[size];  
	        }  
	 };
}
