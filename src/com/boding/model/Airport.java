package com.boding.model;

import java.io.Serializable;
import java.util.Comparator;

import com.boding.util.Util;

public class Airport  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1057445046003004370L;
	private String airportcode;
	private String airportname;
	private String airportPinyin="";
	private String airportInitial="";
	
	public String getAirportcode() {
		return airportcode;
	}
	public void setAirportcode(String airportcode) {
		this.airportcode = airportcode;
	}
	public String getAirportname() {
		return airportname;
	}
	public void setAirportname(String airportname) {
		if(airportname == null){
			airportname = "…œ∫£";
			this.airportcode = "SHA";
		}
		this.airportname = airportname;
		this.airportPinyin = "";
		this.airportInitial = "";
		for(char hanziChar : airportname.toCharArray()){
			String charPinyin = Util.getPinYin(String.valueOf(hanziChar));
			String alpha = Util.getAlpha(charPinyin);
			this.airportPinyin += charPinyin;
			this.airportInitial += alpha;
		}
	}
	
	public String getAirportPinyin() {
		return airportPinyin;
	}
	public String getAirportInitial() {
		return airportInitial;
	}
	public boolean equals(Airport anotherAirport){
		if(anotherAirport == null)
			return false;
		if(this.airportname.equals(anotherAirport.getAirportname()))
			return true;
		return false;
		
	}
	
	public static class AirportNameComp implements Comparator<Airport>{

		@Override
		public int compare(Airport lhs, Airport rhs) {
			return lhs.airportPinyin.compareTo(rhs.airportPinyin);
		}
		
	}
}
