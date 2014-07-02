package com.boding.model.domestic;

import java.util.ArrayList;
import java.util.List;

public class Airlines {
	private String dpt;
	private String arr;
	private String date;
	private List<Flight> flights = new ArrayList<Flight>();
	public String getDpt() {
		return dpt;
	}
	public void setDpt(String dpt) {
		this.dpt = dpt;
	}
	public String getArr() {
		return arr;
	}
	public void setArr(String arr) {
		this.arr = arr;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public List<Flight> getFlights() {
		return flights;
	}
	public void setFlights(List<Flight> flights) {
		this.flights = flights;
	}
	
	public String getlowestPrice(){
		double lowest = Double.MAX_VALUE;
		double temp;
		for(Flight flight : flights){
			temp = flight.getLowestPrice();
			lowest = lowest <= temp ? lowest : temp;
		}
		return String.valueOf(lowest);
	}
}
