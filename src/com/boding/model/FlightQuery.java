package com.boding.model;

import android.os.Parcel;
import android.os.Parcelable;

public class FlightQuery implements Parcelable{
	private String startdate;
	private String returndate;
	private String fromcity;
	private String tocity;
	private String text;
	private int rc;
	
	public FlightQuery(){}
	
	public FlightQuery(Parcel in){
		startdate = in.readString();
		returndate = in.readString();
		fromcity = in.readString();
		tocity = in.readString();
	}
	
	public String getStartdate() {
		return startdate;
	}
	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}
	public String getReturndate() {
		return returndate;
	}
	public void setReturndate(String returndate) {
		this.returndate = returndate;
	}
	public String getFromcity() {
		return fromcity;
	}
	public void setFromcity(String fromcity) {
		this.fromcity = fromcity;
	}
	public String getTocity() {
		return tocity;
	}
	public void setTocity(String tocity) {
		this.tocity = tocity;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public int getRc() {
		return rc;
	}
	public void setRc(int rc) {
		this.rc = rc;
	}
	@Override
	public int describeContents() {
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int arg1) {
		dest.writeString(startdate);
		dest.writeString(returndate);
		dest.writeString(fromcity);
		dest.writeString(tocity);
	}
	
	 public static final Parcelable.Creator<FlightQuery> CREATOR = new Parcelable.Creator<FlightQuery>() {   
		//÷ÿ–¥Creator
		  
		 public FlightQuery createFromParcel(Parcel in) {  
	            return new FlightQuery(in);  
	        }  
	          
	        public FlightQuery[] newArray(int size) {  
	            return new FlightQuery[size];  
	        }  
	 };   
	
}
