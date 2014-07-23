package com.boding.constants;

import com.boding.R;

public enum FlightStatus {
	TAKEOFF("takeoff","起飞",R.drawable.flightdynamic_status_takeoff,R.drawable.flightboard_takeoff,
		R.drawable.layoutflightboard_flightdynamicsline_takeoff,R.drawable.layoutflightboard_status_takeoff,
		R.color.takeOffTextColor),
	PLAN("plan","计划",R.drawable.flightdynamic_status_plan,R.drawable.flightboard_plan,
		R.drawable.layoutflightboard_flightdynamicsline_plan,R.drawable.layoutflightboard_status_plan,
		R.color.textBlue),
	ARRIVE("arrive","到达",R.drawable.flightdynamic_status_arrive,R.drawable.flightboard_arrive,
		R.drawable.layoutflightboard_flightdynamicsline_arrive,R.drawable.layoutflightboard_status_arrive,
		R.color.textBlue),
	DELAY("delay","延误",R.drawable.flightdynamic_status_delay,R.drawable.flightboard_delay,
		R.drawable.layoutflightboard_flightdynamicsline_delay,R.drawable.layoutflightboard_status_delay,
		R.color.panelOrange),
	CANCEL("cancel","取消",R.drawable.flightdynamic_status_cancel,R.drawable.flightboard_cancel,
		R.drawable.layoutflightboard_flightdynamicsline_cancel,R.drawable.layoutflightboard_status_cancel,
		R.color.cancelTextColor),
	ALTERNATE("alternate","备降",R.drawable.flightdynamic_status_cancel,R.drawable.flightboard_cancel,
		R.drawable.layoutflightboard_flightdynamicsline_cancel,R.drawable.layoutflightboard_status_cancel,
		R.color.cancelTextColor);
	
	private String flightStatusCode;
	private String flightStatusName;
	private int flightStatusDrawable;
	private int flightBoardDrawable;
	private int flightStatusLine;
	private int layoutFlightStatus;
	private int flightStatusColor;
	
	private FlightStatus(String flightStatusCode, String flightStatusName, int flightStatusDrawable, 
		int flightBoardDrawable, int flightStatusLine, int layoutFlightStatus, int flightStatusColor){
		this.flightStatusCode = flightStatusCode;
		this.flightStatusName = flightStatusName;
		this.flightStatusDrawable = flightStatusDrawable;
		this.flightBoardDrawable = flightBoardDrawable;
		this.flightStatusLine = flightStatusLine;
		this.layoutFlightStatus = layoutFlightStatus;
		this.flightStatusColor = flightStatusColor;
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
		if(flightStatusCode.equals("alternate"))
			return ALTERNATE;
		return null;
	}
	public static FlightStatus getFlightStatusFromName(String flightStatusName){
		if(flightStatusName.equals("起飞"))
			return TAKEOFF;
		if(flightStatusName.equals("计划"))
			return PLAN;
		if(flightStatusName.equals("到达"))
			return ARRIVE;
		if(flightStatusName.equals("延误"))
			return DELAY;
		if(flightStatusName.equals("取消"))
			return CANCEL;
		if(flightStatusName.equals("备降"))
			return ALTERNATE;
		return null;
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

	public int getFlightStatusLine() {
		return flightStatusLine;
	}

	public void setFlightStatusLine(int flightStatusLine) {
		this.flightStatusLine = flightStatusLine;
	}

	public int getLayoutFlightStatus() {
		return layoutFlightStatus;
	}

	public void setLayoutFlightStatus(int layoutFlightStatus) {
		this.layoutFlightStatus = layoutFlightStatus;
	}

	public int getFlightStatusColor() {
		return flightStatusColor;
	}

	public void setFlightStatusColor(int flightStatusColor) {
		this.flightStatusColor = flightStatusColor;
	}
}
