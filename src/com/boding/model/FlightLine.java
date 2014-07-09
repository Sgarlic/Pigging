package com.boding.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.boding.model.domestic.Cabin;
import com.boding.util.CityUtil;

public class FlightLine implements FlightInterface{
	private String flightType;
	private Departure departure;
	private ReturnList returnlist;
	
	private int selectedClassPos;
	private int defaultShowedCabinPos = 0;
	private List<FlightClass> selectedCabins = new ArrayList<FlightClass>();
	
	public String getCurrentClass(){
		return this.selectedCabins.get(defaultShowedCabinPos).getClassType();
	}
	
	public Departure getDeparture() {
		return departure;
	}

	public void setDeparture(Departure departure) {
		this.departure = departure;
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
		int segmentSize = getDepature().getSegments().size();
		return CityUtil.getCityNameByCode(departure.getSegments().get(segmentSize-1).getLeacode());
	}
	
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
		for(int i=0; i<cabins.size(); ++i){
			cabin = cabins.get(i);
			if(cabin.getClassType().contains(classStr)){
				selectedCabins.add(cabin);
				double p = Double.parseDouble(cabin.getPrice().getAdult());
				if(p < lowest){
					lowest = p;
					defaultShowedCabinPos = i;
				}
			}
		}
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
