package com.boding.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AirlineView implements AirlineInterface{
	private String fromCity;
	private String toCity;
	private String goDate;
	private String backDate;
	private String result;
	private int isInternational;
	private List<FlightLine> lines = new ArrayList<FlightLine>();
	
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
	
	public String getlowestPrice(){
		int lowestPrice = Integer.MAX_VALUE;
		int temp = 0;
		for(FlightLine line : lines){
			temp = line.getLowestPrice();
			lowestPrice = lowestPrice < temp ? lowestPrice : temp;
		}
		if(lowestPrice == Integer.MAX_VALUE)
			return "";
		return String.valueOf(lowestPrice);
	}
	
	public void orderLinesByLeatime(boolean isAsc){
		Collections.sort(lines, new FlightLine.LeatimeComp(isAsc));
	}
	
	public void orderLinesByPrice(boolean isAsc){
		Collections.sort(lines, new FlightLine.PriceComp(isAsc));
	}
}
