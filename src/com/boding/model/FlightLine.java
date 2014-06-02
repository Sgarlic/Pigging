package com.boding.model;

public class FlightLine {
	private String flightType;
	private Departure departure;
	private ReturnList returnlist;
	
	public String getFlightType() {
		return flightType;
	}
	public void setFlightType(String flightType) {
		this.flightType = flightType;
	}
	public Departure getDepature() {
		return departure;
	}
	public void setDepature(Departure depature) {
		this.departure = depature;
	}
	public ReturnList getReturnlist() {
		return returnlist;
	}
	public void setReturnlist(ReturnList returnlist) {
		this.returnlist = returnlist;
	}
	
	public String getLeaveTime(){
		return departure.getSegments().get(0).getLeatime();
	}
	
	public String getArriveTime(){
		int segmentSize = getDepature().getSegments().size();
		return departure.getSegments().get(segmentSize-1).getArrtime();
	}
	
	public boolean hasTransit(){
		return departure.getSegments().size()>1;
	}
	
	public String getFlightPrice(){
		return this.departure.getSegments().get(0).getFclasslist().get(0).getPrice().getFile();
	}
	
	public String getLeaveDate(){
		return departure.getSegments().get(0).getLeadate();
	}
	
	public String getArriveDate(){
		int segmentSize = getDepature().getSegments().size();
		return departure.getSegments().get(segmentSize-1).getArrdate();
	}
	
	public String getSeat(){
		return departure.getSegments().get(0).getFclasslist().get(0).getSeat();
	}
	
	public String getAirCompany(){
		return departure.getSegments().get(0).getCarname();
	}
	
	public String getCarrier(){
		return departure.getSegments().get(0).getCarrier();
	}
	
	public String getNum(){
		return departure.getSegments().get(0).getNum();
	}
	
	public String getLeaveAirport(){
		return departure.getSegments().get(0).getLeaname();
	}
	
	public String getArriveAirport(){
		int segmentSize = getDepature().getSegments().size();
		return departure.getSegments().get(segmentSize-1).getArrname();
	}
	
	public int getSegmentSize(){
		return departure.getSegments().size();
	}
	
	public int getFlightClassNum(){
		return departure.getSegments().get(0).getFclasslist().size();
	}
}
