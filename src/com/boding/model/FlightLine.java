package com.boding.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.boding.util.CityUtil;
import com.boding.util.DateUtil;

public class FlightLine implements FlightInterface{
	private String flightType;
	private Departure departure;
	private ReturnList returnlist;
	
	private int selectedClassPos;
	private int defaultShowedCabinPos = 0;
	private List<FlightClass> selectedCabins = new ArrayList<FlightClass>();
	
	int segmentSize = 0;
	
	public String getCurrentClass(){
		return this.selectedCabins.get(defaultShowedCabinPos).getClassType();
	}
	
	public Departure getDeparture() {
		return departure;
	}

	public void setDeparture(Departure departure) {
		this.departure = departure;
		segmentSize = getDeparture().getSegments().size();
	}

	public int getDefaultShowedCabinPos() {
		return defaultShowedCabinPos;
	}

	public void setDefaultShowedCabinPos(int defaultShowedCabinPos) {
		this.defaultShowedCabinPos = defaultShowedCabinPos;
	}

	public List<FlightClass> getSelectedCabins() {
		return selectedCabins;
	}

	public void setSelectedCabins(List<FlightClass> selectedCabins) {
		this.selectedCabins = selectedCabins;
	}

	public String getFlyFromCity(){
		return CityUtil.getCityNameByCode(departure.getSegments().get(0).getLeacode());
	}
	
	public String getFlyToCity(){
		return CityUtil.getCityNameByCode(departure.getSegments().get(segmentSize-1).getLeacode());
	}
	
	public String getFlightType() {
		return flightType;
	}
	public void setFlightType(String flightType) {
		this.flightType = flightType;
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
	
	public String getLeaveToTime(){
		return departure.getSegments().get(0).getArrtime();
	}

	public String getLeaveEstimateTime(){
		return DateUtil.getTimeIntDiff(
			DateUtil.getDateFromString(getLeaveDate(), getLeaveTime()).getTime(), 
			DateUtil.getDateFromString(getLeaveToDate(), getLeaveToTime()).getTime());
	}
	
	public String getArriveFromTime(){
		return departure.getSegments().get(segmentSize-1).getLeatime();
	}
	
	public String getArriveTime(){
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
	
	public String getLeaveToDate(){
		return departure.getSegments().get(0).getArrdate();
	}
	
	public String getArriveFromDate(){
		return departure.getSegments().get(segmentSize-1).getLeadate();
	}
	
	public String getArriveDate(){
		return departure.getSegments().get(segmentSize-1).getArrdate();
	}
	
	public String getSeat(){
		return departure.getSegments().get(0).getFclasslist().get(0).getSeat();
	}
	
	public String getLeaveAirCompany(){
		return departure.getSegments().get(0).getCarname();
	}
	
	public String getLeavePlane(){
		return departure.getSegments().get(0).getPlane();
	}
	
	public String getLeaveCarrier(){
		return departure.getSegments().get(0).getCarrier();
	}
	
	public String getLeaveFlightNum(){
		return departure.getSegments().get(0).getNum();
	}
	
	public String getLeaveFlightClassName(){
		return selectedCabins.get(selectedClassPos).getFlightClassName();
	}
	
	public String getLeaveAirport(){
		return departure.getSegments().get(0).getLeaname();
	}
	
	public String getLeaveTerminal(){
		return departure.getSegments().get(0).getLeaTerminal();
	}
	
	public String getLeaveToAirport(){
		return departure.getSegments().get(0).getArrname();
	}
	
	public String getLeaveToTerminal(){
		return departure.getSegments().get(0).getArrTerminal();
	}
	
	public String getArriveFromAirport(){
		return departure.getSegments().get(segmentSize-1).getLeaname();
	}
	
	public String getArriveFromTerminal(){
		return departure.getSegments().get(segmentSize-1).getLeaTerminal();
	}
	
	public String getArriveAirport(){
		return departure.getSegments().get(segmentSize-1).getArrname();
	}
	
	public String getArriveTerminal(){
		return departure.getSegments().get(segmentSize-1).getArrTerminal();
	}
	
	public int getSegmentSize(){
		return departure.getSegments().size();
	}
	
	public int getFlightClassNum(){
		return departure.getSegments().get(0).getFclasslist().size();
	}
	
	public FlightClass getFlightClassByPos(int position){
		return departure.getSegments().get(0).getFclasslist().get(position);
	}
	
	public int getLeaveTimeInt(){
		return Integer.parseInt(departure.getSegments().get(0).getLeatime());
	}
	
	public int getFlightPriceInt(){
		return Integer.parseInt(departure.getSegments().get(0).getFclasslist().get(0).getPrice().getFile());
	}
	
	public int getSelectedClassLeftTicket(){
		String seat = this.departure.getSegments().get(0).getFclasslist().get(selectedClassPos).getSeat();
		if(seat.equals("A"))
			return 10;
		return Integer.parseInt(seat);
	}
	
	public void setDefaultShowedCabins(String classStr){
		double lowest = Double.MAX_VALUE;
		FlightClass cabin = null;
		selectedCabins = new ArrayList<FlightClass>();
		List<FlightClass> cabins = this.departure.getSegments().get(0).getFclasslist();
		System.out.println(" ((((((((("+cabins.size());
		for(int i=0; i<cabins.size(); ++i){
			cabin = cabins.get(i);
			if(cabin.getFlightClassName().contains(classStr)){
				selectedCabins.add(cabin);
				double p = Double.parseDouble(cabin.getPrice().getAdult());
				if(p < lowest){
					lowest = p;
					defaultShowedCabinPos = i;
				}
			}
		}
	}
	
	public void resetShowedCabins(){
		this.selectedCabins = this.departure.getSegments().get(0).getFclasslist();
	}

	public static class LeatimeComp implements Comparator<FlightLine>{
		private boolean isAsc = true;
		
		public LeatimeComp(){}
		
		public LeatimeComp(boolean isAsc){
			this.isAsc = isAsc;
		}
		
		@Override
		public int compare(FlightLine lhs, FlightLine rhs) {
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
	
	public static class PriceComp implements Comparator<FlightLine>{
		private boolean isAsc = true;
		
		public PriceComp(){}
		
		public PriceComp(boolean isAsc){
			this.isAsc = isAsc;
		}
		
		@Override
		public int compare(FlightLine lhs, FlightLine rhs) {
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
	public void setSelectedClassPos(int position) {
		this.selectedClassPos = position;
	}

	@Override
	public int getSelectedClassPos() {
		return this.selectedClassPos;
	}
}
