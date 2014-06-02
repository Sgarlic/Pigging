package com.boding.model;

import java.util.List;

public class AirlineView {
	private String fromCity;
	private String toCity;
	private String goDate;
	private String backDate;
	private String result;
	private int isInternational;
	private List<FlightLine> lines;
	
	public String getFromCity() {
		return fromCity;
	}
	public void setFromCity(String fromCity) {
		this.fromCity = fromCity;
	}
	public String getToCity() {
		return toCity;
	}
	public void setToCity(String toCity) {
		this.toCity = toCity;
	}
	public String getGoDate() {
		return goDate;
	}
	public void setGoDate(String goDate) {
		this.goDate = goDate;
	}
	public String getBackDate() {
		return backDate;
	}
	public void setBackDate(String backDate) {
		this.backDate = backDate;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public int getIsInternational() {
		return isInternational;
	}
	public void setIsInternational(int isInternational) {
		this.isInternational = isInternational;
	}
	public List<FlightLine> getLines() {
		return lines;
	}
	public void setLines(List<FlightLine> lines) {
		this.lines = lines;
	}
	
	public int getlowestPrice(){
		int lowestPrice = Integer.MAX_VALUE;
		int temp = 0;
		for(FlightLine line : lines){
			temp = Integer.parseInt(line.getFlightPrice());
			lowestPrice = lowestPrice < temp ? lowestPrice : temp;
		}
		return lowestPrice;
	}
}