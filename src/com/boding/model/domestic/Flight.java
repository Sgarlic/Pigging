package com.boding.model.domestic;

import java.util.ArrayList;
import java.util.List;

import com.boding.model.FlightClass;

public class Flight {
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
		return String.valueOf(this.cabins.get(0).getAdultPrice());
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
}
