package com.boding.model;

public class FlightLine {
	private String flightType;
	private Departure depature;
	private ReturnList returnlist;
	
	public String getFlightType() {
		return flightType;
	}
	public void setFlightType(String flightType) {
		this.flightType = flightType;
	}
	public Departure getDepature() {
		return depature;
	}
	public void setDepature(Departure depature) {
		this.depature = depature;
	}
	public ReturnList getReturnlist() {
		return returnlist;
	}
	public void setReturnlist(ReturnList returnlist) {
		this.returnlist = returnlist;
	}
}
