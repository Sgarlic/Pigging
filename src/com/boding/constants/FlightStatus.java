package com.boding.constants;

import com.boding.R;

public enum FlightStatus {
	TAKEOFF("takeoff","���",R.drawable.flightdynamic_status_takeoff,R.drawable.flightboard_takeoff),
	PLAN("plan","�ƻ�",R.drawable.flightdynamic_status_plan,R.drawable.flightboard_plan),
	ARRIVE("arrive","����",R.drawable.flightdynamic_status_arrive,R.drawable.flightboard_arrive),
	DELAY("delay","����",R.drawable.flightdynamic_status_delay,R.drawable.flightboard_delay),
	CANCEL("cancel","ȡ��",R.drawable.flightdynamic_status_cancel,R.drawable.flightboard_cancel),
	ALTERNATE("alternate","����",R.drawable.flightdynamic_status_cancel,R.drawable.flightboard_cancel);
	
	private String flightStatusCode;
	private int flightStatusDrawable;
	private int flightBoardDrawable;
	private String flightStatusName;
	private FlightStatus(String flightStatusCode, String flightStatusName, int flightStatusDrawable, int flightBoardDrawable){
		this.flightStatusCode = flightStatusCode;
		this.flightStatusName = flightStatusName;
		this.flightStatusDrawable = flightStatusDrawable;
		this.flightBoardDrawable = flightBoardDrawable;
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
	
	public int getFlightStatusDrawable() {
		return flightStatusDrawable;
	}

	public void setFlightStatusDrawable(int flightStatusDrawable) {
		this.flightStatusDrawable = flightStatusDrawable;
	}

	public String getFlightStatusCode() {
		return flightStatusCode;
	}

	public String getFlightStatusName() {
		return flightStatusName;
	}

	public int getFlightBoardDrawable() {
		return flightBoardDrawable;
	}

	public void setFlightBoardDrawable(int flightBoardDrawable) {
		this.flightBoardDrawable = flightBoardDrawable;
	}
}
