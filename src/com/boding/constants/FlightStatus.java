package com.boding.constants;

public enum FlightStatus {
	TAKEOFF("takeoff","起飞"),
	PLAN("plan","计划"),
	ARRIVE("arrive","到达"),
	DELAY("delay","延误"),
	CANCEL("cancel","取消"),
	ALTERNATE("alternate","备降");
	
	private String flightStatusCode;
	public String getFlightStatusCode() {
		return flightStatusCode;
	}

	public String getFlightStatusName() {
		return flightStatusName;
	}

	private String flightStatusName;
	private FlightStatus(String flightStatusCode, String flightStatusName){
		this.flightStatusCode = flightStatusCode;
		this.flightStatusName = flightStatusName;
	}
	
	public static FlightStatus getFlightStatusFromCode(String flightStatusCode){
		if(flightStatusCode.equals("takeoff"))
			return TAKEOFF;
		if(flightStatusCode.equals("plan"))
			return PLAN;
		if(flightStatusCode.equals("arrive"))
			return ARRIVE;
		if(flightStatusCode.equals("delay"))
			return DELAY;
		if(flightStatusCode.equals("cancel"))
			return CANCEL;
		return ALTERNATE;
	}
}
