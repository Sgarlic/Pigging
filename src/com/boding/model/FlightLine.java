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
	
	public String getDepartureTime(){
		return depature.getSegments().get(0).getLeatime();
	}
	
	public String getArriveTime(){
		int segmentSize = getDepature().getSegments().size();
		return depature.getSegments().get(segmentSize-1).getArrtime();
	}
	
	public boolean hasTransit(){
		return depature.getSegments().size()>1;
	}
	
	public String getFlightPrice(){
		return this.depature.getSegments().get(0).getFclasslist().get(0).getPrice().getAdult();
	}
}
