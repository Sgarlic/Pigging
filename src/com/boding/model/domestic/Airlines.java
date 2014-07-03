package com.boding.model.domestic;

import java.util.ArrayList;
import java.util.List;

import com.boding.model.AirlineInterface;

public class Airlines implements AirlineInterface{
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
	public String getGoDate() {
		return date;
	}
	public void setGoDate(String date) {
		this.date = date;
	}
	public List<Flight> getFlights() {
		return flights;
	}
	public void setFlights(List<Flight> flights) {
		this.flights = flights;
	}
	
//	public String getlowestPriceStr(){
//		if(flights.size() == 0)
//			return "";
//		double lowest = Double.MAX_VALUE;
//		double temp;
//		for(Flight flight : flights){
//			temp = flight.getLowestPrice();
//			lowest = lowest <= temp ? lowest : temp;
//		}
//		return "гд"+(int)lowest;
//	}
	
	@Override
	public void orderLinesByLeatime(boolean isAsc) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void orderLinesByPrice(boolean isAsc) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String getlowestPrice() {
		if(flights.size() == 0)
			return "";
		double lowest = Double.MAX_VALUE;
		double temp;
		for(Flight flight : flights){
			temp = flight.getLowestPrice();
			lowest = lowest <= temp ? lowest : temp;
		}
		return "гд"+(int)lowest;
	}
}
