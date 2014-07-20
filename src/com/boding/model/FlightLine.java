package com.boding.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

import com.boding.model.domestic.Cabin;
import com.boding.util.CityUtil;

public class FlightLine implements FlightInterface{
	private String flightType;
	private Departure departure;
	private ReturnList returnlist;
	
	private int selectedClassPos;
	private int defaultShowedCabinPos = 0;
	private List<FlightClass> selectedCabins = new ArrayList<FlightClass>();
	
	private Segment firstSegment = null;
	private Segment lastSegment = null;
	
	public String getCurrentClass(){
		return this.selectedCabins.get(defaultShowedCabinPos).getClassType();
	}
	
	public Departure getDeparture() {
		return departure;
	}

	public void setDeparture(Departure departure) {
		this.departure = departure;
		int segmentSize = getDeparture().getSegments().size();
		firstSegment = getDeparture().getSegments().get(0);
		lastSegment = getDeparture().getSegments().get(segmentSize - 1);
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
		return CityUtil.getCityNameByCode(firstSegment.getLeacode());
	}
	
	public String getFlyToCity(){
		return CityUtil.getCityNameByCode(lastSegment.getLeacode());
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
		return firstSegment.getLeatime();
	}

	public String getArriveTime(){
		return lastSegment.getArrtime();
	}
	
	public boolean hasTransit(){
		return departure.getSegments().size()>1;
	}
	
	public String getFlightPrice(){
		System.out.println(this.getClass().toString() + "  " + this.selectedCabins.size());
		return this.selectedCabins.get(defaultShowedCabinPos).getPrice().getAdult();
	}
	
	public String getLeaveDate(){
		return firstSegment.getLeadate();
	}
	
	public String getArriveDate(){
		return lastSegment.getArrdate();
	}
	
	public String getSeat(){
		return firstSegment.getFclasslist().get(0).getSeat();
	}
	
	public String getLeaveAirCompany(){
		return firstSegment.getCarname();
	}
	
	public String getArriveAirCompany(){
		return lastSegment.getCarname();
	}
	
	public String getLeaveCarrier(){
		return firstSegment.getCarrier();
	}
	
	public String getArriveCarrier(){
		return lastSegment.getCarrier();
	}
	
	public String getLeaveFlightNum(){
		return firstSegment.getNum();
	}
	
	public String getArriveFlightNum(){
		return lastSegment.getNum();
	}
	
	public String getLeaveFlightClassName(){
		return selectedCabins.get(selectedClassPos).getFlightClassName();
	}
	
	public String getLeaveAirport(){
		return firstSegment.getLeaname();
	}
	
	public String getLeaveTerminal(){
		return firstSegment.getLeaTerminal();
	}
	
	public String getArriveAirport(){
		return lastSegment.getArrname();
	}
	
	public String getArriveTerminal(){
		return lastSegment.getArrTerminal();
	}
	
	public int getSegmentSize(){
		return departure.getSegments().size();
	}
	
	public int getFlightClassNum(){
		return firstSegment.getFclasslist().size();
	}
	
	public FlightClass getFlightClassByPos(int position){
		return firstSegment.getFclasslist().get(position);
	}
	
	public int getLeaveTimeInt(){
		return Integer.parseInt(firstSegment.getLeatime());
	}
	
	public int getFlightPriceInt(){
		return Integer.parseInt(firstSegment.getFclasslist().get(0).getPrice().getFile());
	}
	
	public int getSelectedClassLeftTicket(){
		String seat = this.firstSegment.getFclasslist().get(selectedClassPos).getSeat();
		if(seat.equals("A"))
			return 10;
		return Integer.parseInt(seat);
	}
	
	public void setDefaultShowedCabins(String classStr){
		double lowest = Double.MAX_VALUE;
		FlightClass cabin = null;
		selectedCabins = new ArrayList<FlightClass>();
		List<FlightClass> cabins = this.firstSegment.getFclasslist();
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
		this.selectedCabins = this.firstSegment.getFclasslist();
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
	
	public int getLowestPrice(){
		int lowest = Integer.MAX_VALUE;
		int temp;
		for(FlightClass cabin : departure.getSegments().get(0).getFclasslist()){
			temp = Integer.parseInt(cabin.getPrice().getAdult());
			lowest = lowest <= temp ? lowest : temp;
		}
		return lowest;
	}
	
}
