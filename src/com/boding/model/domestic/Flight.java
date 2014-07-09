package com.boding.model.domestic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

import com.boding.constants.Gender;
import com.boding.constants.IdentityType;
import com.boding.model.FlightInterface;
import com.boding.model.Passenger;
import com.boding.util.CityUtil;

public class Flight implements FlightInterface, Parcelable{
	private String carrier;
	private String dptAirport;
	private String arrAirport;
	private String dptDate;
	private String arrDate;
	private String dptTime;
	private String arrTime;
	private String flightNum;
	private String codeShare;
	private String adultFuelFee;
	private String adultAirportFee;
	private String yprice;
	private String meal;
	private String plantype;
	private String stops;
	private String dptTerminal;
	private String arrTerminal;
	private String carrierName;
	private String dptAirportName;
	private String arrAirportName;
	private String duration;
	private List<Cabin> cabins = new ArrayList<Cabin>();
	
	private int selectedCabinPos = 0;
	private int defaultShowedCabinPos = 0;
	private List<Cabin> selectedCabins = new ArrayList<Cabin>();
	
	public Flight(){}
	
	
	public Cabin getSelectedCabin() {
		return cabins.get(selectedCabinPos);
	}


	public void setSelectedCabinPos(int selectedCabinPos) {
		this.selectedCabinPos = selectedCabinPos;
	}


	public List<Cabin> getSelectedCabins() {
		return selectedCabins;
	}


	public void setSelectedCabins(List<Cabin> selectedCabins) {
		this.selectedCabins = selectedCabins;
	}


	public static Parcelable.Creator<Flight> getCreator() {
		return CREATOR;
	}


	public void setDefaultShowedCabinPos(int defaultShowedCabinPos) {
		this.defaultShowedCabinPos = defaultShowedCabinPos;
	}


	public String getFlyFromCity(){
		return CityUtil.getCityNameByCode(dptAirport);
	}
	
	public String getFlyToCity(){
		return CityUtil.getCityNameByCode(arrAirport);
	}
	
	public String getCarrier() {
		return carrier;
	}
	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}
	public String getDptAirport() {
		return dptAirport;
	}
	public void setDptAirport(String dptAirport) {
		this.dptAirport = dptAirport;
	}
	public String getArrAirport() {
		return arrAirport;
	}
	public void setArrAirport(String arrAirport) {
		this.arrAirport = arrAirport;
	}
	public String getDptDate() {
		return dptDate;
	}
	public void setDptDate(String dptDate) {
		this.dptDate = dptDate;
	}
	public String getArrDate() {
		return arrDate;
	}
	public void setArrDate(String arrDate) {
		this.arrDate = arrDate;
	}
	public String getDptTime() {
		return dptTime;
	}
	public void setDptTime(String dptTime) {
		this.dptTime = dptTime;
	}
	public String getArrTime() {
		return arrTime;
	}
	public void setArrTime(String arrTime) {
		this.arrTime = arrTime;
	}
	public String getFlightNum() {
		return flightNum;
	}
	public void setFlightNum(String flightNum) {
		this.flightNum = flightNum;
	}
	public String getCodeShare() {
		return codeShare;
	}
	public void setCodeShare(String codeShare) {
		this.codeShare = codeShare;
	}
	public String getAdultFuelFee() {
		return adultFuelFee;
	}
	public void setAdultFuelFee(String adultFuelFee) {
		this.adultFuelFee = adultFuelFee;
	}
	public String getAdultAirportFee() {
		return adultAirportFee;
	}
	public void setAdultAirportFee(String adultAirportFee) {
		this.adultAirportFee = adultAirportFee;
	}
	public String getYprice() {
		return yprice;
	}
	public void setYprice(String yprice) {
		this.yprice = yprice;
	}
	public String getMeal() {
		return meal;
	}
	public void setMeal(String meal) {
		this.meal = meal;
	}
	public String getPlantype() {
		return plantype;
	}
	public void setPlantype(String plantype) {
		this.plantype = plantype;
	}
	
	public String hasStops(){
		if(stops.equals("0"))
			return "false";
		return "true";
	}
	
	public String getStops() {
		return stops;
	}
	public void setStops(String stops) {
		this.stops = stops;
	}
	public String getDptTerminal() {
		return dptTerminal;
	}
	public void setDptTerminal(String dptTerminal) {
		this.dptTerminal = dptTerminal;
	}
	public String getArrTerminal() {
		return arrTerminal;
	}
	public void setArrTerminal(String arrTerminal) {
		this.arrTerminal = arrTerminal;
	}
	public String getCarrierName() {
		return carrierName;
	}
	public void setCarrierName(String carrierName) {
		this.carrierName = carrierName;
	}
	public String getDptAirportName() {
		return dptAirportName;
	}
	public void setDptAirportName(String dptAirportName) {
		this.dptAirportName = dptAirportName;
	}
	public String getArrAirportName() {
		return arrAirportName;
	}
	public void setArrAirportName(String arrAirportName) {
		this.arrAirportName = arrAirportName;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public List<Cabin> getCabins() {
		return cabins;
	}
	public void setCabins(List<Cabin> cabins) {
		this.cabins = cabins;
	}
	
	public int getFlightClassNum(){
		return this.cabins.size();
	}
	
	public Cabin getFlightClassByPos(int position){
		return this.cabins.get(position);
	}
	
	public String getFlightPrice(){
		return String.valueOf((int)this.cabins.get(defaultShowedCabinPos).getAdultPrice());
	}
	
	public int getFlightPriceInt(){
		return (int)this.cabins.get(defaultShowedCabinPos).getAdultPrice();
	}
	
	public String getSeat(){
		return this.cabins.get(0).getStatus();
	}
	
	public double getLowestPrice(){
		double lowest = Double.MAX_VALUE;
		double temp;
		for(Cabin cabin : cabins){
			temp = cabin.getAdultPrice();
			lowest = lowest <= temp ? lowest : temp;
		}
		return lowest;
	}
	
	private int getLeaveTimeInt(){
		return Integer.parseInt(dptTime);
	}
	
	public void setDefaultShowedCabins(String classStr){
		double lowest = Double.MAX_VALUE;
		Cabin cabin = null;
		selectedCabins = new ArrayList<Cabin>();
		for(int i=0; i<cabins.size(); ++i){
			cabin = cabins.get(i);
			if(cabin.getCabinName().contains(classStr)){
				selectedCabins.add(cabin);
				if(cabin.getAdultPrice() < lowest){
					lowest = cabin.getAdultPrice();
					defaultShowedCabinPos = i;
				}
			}
		}
	}
	
	public int getDefaultShowedCabinPos(){
		return defaultShowedCabinPos;
	}
	
	public static class LeatimeComp implements Comparator<Flight>{
		private boolean isAsc = true;
		
		public LeatimeComp(){}
		
		public LeatimeComp(boolean isAsc){
			this.isAsc = isAsc;
		}
		
		@Override
		public int compare(Flight lhs, Flight rhs) {
			int lhsLeatime = lhs.getLeaveTimeInt();
			int rhsLeatime = rhs.getLeaveTimeInt();
			int result = 0;
			if( lhsLeatime < rhsLeatime)
				result = -1;
			else if(lhsLeatime > rhsLeatime)
				result = 1;
			else
				result = 0;
			
			return isAsc ? result : (-result);
		}
		
	}
	
	public int getSelectedClassLeftTicket(){
		String status = cabins.get(selectedCabinPos).getStatus();
		if(status.equals("A"))
			return 10;
		return Integer.parseInt(status);
	}
	
	public static class PriceComp implements Comparator<Flight>{
		private boolean isAsc = true;
		
		public PriceComp(){}
		
		public PriceComp(boolean isAsc){
			this.isAsc = isAsc;
		}
		
		@Override
		public int compare(Flight lhs, Flight rhs) {
			int lhsprice = lhs.getFlightPriceInt();
			int rhsprice = rhs.getFlightPriceInt();
			int result = 0;
			if( lhsprice < rhsprice)
				result = -1;
			else if(lhsprice > rhsprice)
				result = 1;
			else
				result = 0;
			
			return isAsc ? result : (-result);
		}
	}
	
	@Override
	public int describeContents() {
		return 0;
	}
	
	public Flight(Parcel in){
		carrier = in.readString();
		dptAirport = in.readString();
		arrAirport = in.readString();
		dptDate = in.readString();
		arrDate = in.readString();
		dptTime = in.readString();
		arrTime = in.readString();
		flightNum = in.readString();
		codeShare = in.readString();
		adultFuelFee = in.readString();
		adultAirportFee = in.readString();
		yprice = in.readString();
		meal = in.readString();
		plantype = in.readString();
		stops = in.readString();
		dptTerminal = in.readString();
		arrTerminal = in.readString();
		carrierName = in.readString();
		dptAirportName = in.readString();
		arrAirportName = in.readString();
		duration = in.readString();
		selectedCabinPos = in.readInt();
		Parcelable[] cabinArray = in.readParcelableArray(Cabin.class.getClassLoader());
		cabins = Arrays.asList(Arrays.asList(cabinArray).toArray(new Cabin[cabinArray.length]));
	}
	
	@Override
	public void writeToParcel(Parcel dest, int arg1) {
		dest.writeString(carrier);
		dest.writeString(dptAirport);
		dest.writeString(arrAirport);
		dest.writeString(dptDate);
		dest.writeString(arrDate);
		dest.writeString(dptTime);
		dest.writeString(arrTime);
		dest.writeString(flightNum);
		dest.writeString(codeShare);
		dest.writeString(adultFuelFee);
		dest.writeString(adultAirportFee);
		dest.writeString(yprice);
		dest.writeString(meal);
		dest.writeString(plantype);
		dest.writeString(stops);
		dest.writeString(dptTerminal);
		dest.writeString(arrTerminal);
		dest.writeString(carrierName);
		dest.writeString(dptAirportName);
		dest.writeString(arrAirportName);
		dest.writeString(duration);
		dest.writeInt(selectedCabinPos);
		dest.writeParcelableArray(cabins.toArray(new Cabin[cabins.size()]), 0);
	}

	public static final Parcelable.Creator<Flight> CREATOR = new Parcelable.Creator<Flight>() {   
		//÷ÿ–¥Creator
		  
		 public Flight createFromParcel(Parcel in) {  
	            return new Flight(in);  
	        }  
	          
	        public Flight[] newArray(int size) {  
	            return new Flight[size];  
	        }  
	 };

	@Override
	public void setSelectedClassPos(int position) {
		this.selectedCabinPos = position;
	}

	@Override
	public int getSelectedClassPos() {
		return this.selectedCabinPos;
	}
}
