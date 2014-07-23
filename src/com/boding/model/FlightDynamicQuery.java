package com.boding.model;

import android.os.Parcel;
import android.os.Parcelable;

public class FlightDynamicQuery implements Parcelable{
	private String date;
	private String fromCityCode = "";
	private String toCityCode = "";
	private String flightNum = "";
	
	public FlightDynamicQuery(){}
	
	public FlightDynamicQuery(Parcel in){
		date = in.readString();
		fromCityCode = in.readString();
		toCityCode = in.readString();
		flightNum = in.readString();
	}
	
	@Override
	public int describeContents() {
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int arg1) {
		dest.writeString(date);
		dest.writeString(fromCityCode);
		dest.writeString(toCityCode);
		dest.writeString(flightNum);
	}
	
	 public static final Parcelable.Creator<FlightDynamicQuery> CREATOR = new Parcelable.Creator<FlightDynamicQuery>() {   
		//÷ÿ–¥Creator
		  
		 public FlightDynamicQuery createFromParcel(Parcel in) {  
	            return new FlightDynamicQuery(in);  
	        }  
	          
	        public FlightDynamicQuery[] newArray(int size) {  
	            return new FlightDynamicQuery[size];  
	        }  
	 };

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getFromCityCode() {
		return fromCityCode;
	}

	public void setFromCityCode(String fromCityCode) {
		this.fromCityCode = fromCityCode;
	}

	public String getToCityCode() {
		return toCityCode;
	}

	public void setToCityCode(String toCityCode) {
		this.toCityCode = toCityCode;
	}

	public String getFlightNum() {
		return flightNum;
	}

	public void setFlightNum(String flightNum) {
		this.flightNum = flightNum;
	}   
	
}
