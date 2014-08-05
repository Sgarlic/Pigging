package com.boding.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.boding.constants.FlightStatus;
import com.boding.constants.Weather;

public class FlightDynamics implements Parcelable{
	private String id;
	private String date;
	private String carrier;
	private String num;
	private String car_name;
	private String plan_dep_time;
	private String expect_dep_time = "";
	private String actual_dep_time = "";
	private String dep_airport_code;
	private String arr_airport_code;
	private String dep_airport_name;
	private String arr_airport_name;
	private String dep_terminal = "";
	private String arr_terminal = "";
	private String plan_arr_time;
	private String expect_arr_time = "";
	private String actual_arr_time = "";
	private FlightStatus flightStatus;
	private String punctuality = "";
	private Weather dep_weather = null;
	private String dep_temperature = "";
	private Weather arr_weather = null;
	private String arr_temperature = "";
	private boolean isFollowed = false;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCarrier() {
		return carrier;
	}
	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getCar_name() {
		return car_name;
	}
	public void setCar_name(String car_name) {
		this.car_name = car_name;
	}
	public String getPlan_dep_time() {
		return plan_dep_time;
	}
	public void setPlan_dep_time(String plan_dep_time) {
		this.plan_dep_time = plan_dep_time;
	}
	public String getExpect_dep_time() {
		return expect_dep_time;
	}
	public void setExpect_dep_time(String expect_dep_time) {
		this.expect_dep_time = expect_dep_time;
	}
	public String getActual_dep_time() {
		return actual_dep_time;
	}
	public void setActual_dep_time(String actual_dep_time) {
		this.actual_dep_time = actual_dep_time;
	}
	public String getDep_airport_code() {
		return dep_airport_code;
	}
	public void setDep_airport_code(String dep_airport_code) {
		this.dep_airport_code = dep_airport_code;
	}
	public String getArr_airport_code() {
		return arr_airport_code;
	}
	public void setArr_airport_code(String arr_airport_code) {
		this.arr_airport_code = arr_airport_code;
	}
	public String getDep_airport_name() {
		return dep_airport_name;
	}
	public void setDep_airport_name(String dep_airport_name) {
		this.dep_airport_name = dep_airport_name;
	}
	public String getArr_airport_name() {
		return arr_airport_name;
	}
	public void setArr_airport_name(String arr_airport_name) {
		this.arr_airport_name = arr_airport_name;
	}
	public String getDep_terminal() {
		return dep_terminal;
	}
	public void setDep_terminal(String dep_terminal) {
		this.dep_terminal = dep_terminal;
	}
	public String getArr_terminal() {
		return arr_terminal;
	}
	public void setArr_terminal(String arr_terminal) {
		this.arr_terminal = arr_terminal;
	}
	public String getPlan_arr_time() {
		return plan_arr_time;
	}
	public void setPlan_arr_time(String plan_arr_time) {
		this.plan_arr_time = plan_arr_time;
	}
	public String getExpect_arr_time() {
		return expect_arr_time;
	}
	public void setExpect_arr_time(String expect_arr_time) {
		this.expect_arr_time = expect_arr_time;
	}
	public String getActual_arr_time() {
		return actual_arr_time;
	}
	public void setActual_arr_time(String actual_arr_time) {
		this.actual_arr_time = actual_arr_time;
	}
	public FlightStatus getFlightStatus() {
		return flightStatus;
	}
	public void setFlightStatus(FlightStatus flightStatus) {
		this.flightStatus = flightStatus;
	}
	public void setFlightStatusByCode(String flightStatusCode) {
		this.flightStatus = FlightStatus.getFlightStatusFromCode(flightStatusCode);
		if(this.flightStatus == null)
			setFlightStatusByName(flightStatusCode);
	}
	public void setFlightStatusByName(String flightStatusName) {
		this.flightStatus = FlightStatus.getFlightStatusFromName(flightStatusName);
		if(this.flightStatus == null)
			this.flightStatus = FlightStatus.PLAN;
	}
	public String getPunctuality() {
		return punctuality;
	}
	public void setPunctuality(String punctuality) {
		this.punctuality = punctuality;
	}
	public String getDep_weatherCodeString() {
		if(this.dep_weather == null)
			return "";
		return dep_weather.getWeatherCode();
	}
	
	public Weather getDep_weather() {
		return dep_weather;
	}
	public void setDep_weatherFromCode(String code) {
		this.dep_weather = Weather.getWeatherFromCode(code);
	}
	public String getDep_temperature() {
		return dep_temperature;
	}
	public void setDep_temperature(String dep_temperature) {
		this.dep_temperature = dep_temperature;
	}
	public String getArr_weatherCodeString() {
		if(arr_weather == null)
			return "";
		return arr_weather.getWeatherCode();
	}
	public Weather getArr_weather() {
		return arr_weather;
	}
	public void setArr_weatherFromCode(String code) {
		this.arr_weather = Weather.getWeatherFromCode(code);
	}
	public String getArr_temperature() {
		return arr_temperature;
	}
	public void setArr_temperature(String arr_temperature) {
		this.arr_temperature = arr_temperature;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	public boolean isFollowed() {
		return isFollowed;
	}
	public void setFollowed(boolean isFollowed) {
		this.isFollowed = isFollowed;
	}
	@Override
	public int describeContents() {
		return 0;
	}
	
	public FlightDynamics(){}
	
	public FlightDynamics(Parcel in){
		id = in.readString();
		date = in.readString();
		carrier = in.readString();
		num = in.readString();
		car_name = in.readString();
		plan_dep_time = in.readString();
		expect_dep_time = in.readString();
		actual_dep_time = in.readString();
		dep_airport_code = in.readString();
		arr_airport_code = in.readString();
		dep_airport_name = in.readString();
		arr_airport_name = in.readString();
		dep_terminal = in.readString();
		arr_terminal = in.readString();
		plan_arr_time = in.readString();
		expect_arr_time = in.readString();
		actual_arr_time = in.readString();
		setFlightStatusByCode(in.readString());
		punctuality = in.readString();
		setDep_weatherFromCode(in.readString());
		dep_temperature = in.readString();
		setArr_weatherFromCode(in.readString());
		arr_temperature = in.readString();
		isFollowed = in.readInt() != 0;
	}
	
	@Override
	public void writeToParcel(Parcel dest, int arg1) {
		dest.writeString(id);
		dest.writeString(date);
		dest.writeString(carrier);
		dest.writeString(num);
		dest.writeString(car_name);
		dest.writeString(plan_dep_time);
		dest.writeString(expect_dep_time);
		dest.writeString(actual_dep_time);
		dest.writeString(dep_airport_code);
		dest.writeString(arr_airport_code);
		dest.writeString(dep_airport_name);
		dest.writeString(arr_airport_name);
		dest.writeString(dep_terminal);
		dest.writeString(arr_terminal);
		dest.writeString(plan_arr_time);
		dest.writeString(expect_arr_time);
		dest.writeString(actual_arr_time);
		dest.writeString(flightStatus.getFlightStatusCode());
		dest.writeString(punctuality);
		dest.writeString(getDep_weatherCodeString());
		dest.writeString(dep_temperature);
		dest.writeString(getArr_weatherCodeString());
		dest.writeString(arr_temperature);
		dest.writeInt(isFollowed? 1 : 0);
	}
	
	 public static final Parcelable.Creator<FlightDynamics> CREATOR = new Parcelable.Creator<FlightDynamics>() {   
		//÷ÿ–¥Creator
		  
		 public FlightDynamics createFromParcel(Parcel in) {  
	            return new FlightDynamics(in);  
	        }  
	          
	        public FlightDynamics[] newArray(int size) {  
	            return new FlightDynamics[size];  
	        }  
	 };
	 
	 @Override
     public boolean equals(Object obj)
     {
		 if(obj == null)
			 return false;
	     if (!(obj instanceof FlightDynamics)) 
	       return false;
	     String curJudger = this.carrier + this.num + this.date;
	     FlightDynamics dynamics = (FlightDynamics) obj;
	     String newJudger = dynamics.carrier + dynamics.num + dynamics.date;
	     return curJudger.equals(newJudger);
     } 
}
