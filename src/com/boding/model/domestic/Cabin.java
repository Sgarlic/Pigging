package com.boding.model.domestic;

import android.os.Parcel;
import android.os.Parcelable;

public class Cabin implements Parcelable{
	private String cabin;
	private String gid;
	private String cabinName;
	private String cabinNameLogogram;
	private String code;
	private double filePrice;
	private String status;
	private double adultPrice;
	private double childPrice;
	private double childFuelFee;
	private double childAirportFee;
	private int classType;
	private String tid;
	private String rule;
	public Cabin(){}
	
	public String getCabin() {
		return cabin;
	}
	public void setCabin(String cabin) {
		this.cabin = cabin;
	}
	public String getGid() {
		return gid;
	}
	public void setGid(String gid) {
		this.gid = gid;
	}
	public String getCabinName() {
		return cabinName;
	}
	public void setCabinName(String cabinName) {
		this.cabinName = cabinName;
	}
	public String getCabinNameLogogram() {
		return cabinNameLogogram;
	}
	public void setCabinNameLogogram(String cabinNameLogogram) {
		this.cabinNameLogogram = cabinNameLogogram;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public double getFilePrice() {
		return filePrice;
	}
	public void setFilePrice(double filePrice) {
		this.filePrice = filePrice;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public double getAdultPrice() {
		return adultPrice;
	}
	public void setAdultPrice(double adultPrice) {
		this.adultPrice = adultPrice;
	}
	public double getChildPrice() {
		return childPrice;
	}
	public void setChildPrice(double childPrice) {
		this.childPrice = childPrice;
	}
	public double getChildFuelFee() {
		return childFuelFee;
	}
	public void setChildFuelFee(double childFuelFee) {
		this.childFuelFee = childFuelFee;
	}
	public double getChildAirportFee() {
		return childAirportFee;
	}
	public void setChildAirportFee(double childAirportFee) {
		this.childAirportFee = childAirportFee;
	}
	public int getClassType() {
		return classType;
	}
	public void setClassType(int classType) {
		this.classType = classType;
	}
	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	public String getRule() {
		return rule;
	}
	public void setRule(String rule) {
		this.rule = rule;
	}
	
	@Override
	public int describeContents() {
		return 0;
	}
	
	public Cabin(Parcel in){
		cabin = in.readString();
		gid = in.readString();
		cabinName = in.readString();
		cabinNameLogogram = in.readString();
		code = in.readString();
		filePrice = in.readDouble();
		status = in.readString();
		adultPrice = in.readDouble();
		childPrice = in.readDouble();
		childFuelFee = in.readDouble();
		childAirportFee = in.readDouble();
		classType = in.readInt();
		tid = in.readString();
		rule = in.readString();
	}
	
	@Override
	public void writeToParcel(Parcel dest, int arg1) {
		dest.writeString(cabin);
		dest.writeString(gid);
		dest.writeString(cabinName);
		dest.writeString(cabinNameLogogram);
		dest.writeString(code);
		dest.writeDouble(filePrice);
		dest.writeString(status);
		dest.writeDouble(adultPrice);
		dest.writeDouble(childPrice);
		dest.writeDouble(childFuelFee);
		dest.writeDouble(childAirportFee);
		dest.writeInt(classType);
		dest.writeString(tid);
		dest.writeString(rule);
	}

	public static final Parcelable.Creator<Cabin> CREATOR = new Parcelable.Creator<Cabin>() {   
		//÷ÿ–¥Creator
		  
		 public Cabin createFromParcel(Parcel in) {  
	            return new Cabin(in);  
	        }  
	          
	        public Cabin[] newArray(int size) {  
	            return new Cabin[size];  
	        }  
	 };
}
