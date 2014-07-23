package com.boding.constants;

import com.boding.R;

public enum FlightStatus {
	TAKEOFF("takeoff","���",R.drawable.flightdynamic_status_takeoff,R.drawable.flightboard_takeoff,
		R.drawable.layoutflightboard_flightdynamicsline_takeoff,R.drawable.layoutflightboard_takeoff,
		R.color.takeOffTextColor, R.color.takeoffLineColor),
	PLAN("plan","�ƻ�",R.drawable.flightdynamic_status_plan,R.drawable.flightboard_plan,
		R.drawable.layoutflightboard_flightdynamicsline_plan,R.drawable.layoutflightboard_plan,
		R.color.textBlue, R.color.arriveLineColor),
	ARRIVE("arrive","����",R.drawable.flightdynamic_status_arrive,R.drawable.flightboard_arrive,
		R.drawable.layoutflightboard_flightdynamicsline_arrive,R.drawable.layoutflightboard_arrive,
		R.color.textBlue, R.color.arriveLineColor),
	DELAY("delay","����",R.drawable.flightdynamic_status_delay,R.drawable.flightboard_delay,
		R.drawable.layoutflightboard_flightdynamicsline_delay,R.drawable.layoutflightboard_delay,
		R.color.panelOrange, R.color.delayLineColor),
	CANCEL("cancel","ȡ��",R.drawable.flightdynamic_status_cancel,R.drawable.flightboard_cancel,
		R.drawable.layoutflightboard_flightdynamicsline_cancel,R.drawable.layoutflightboard_cancel,
		R.color.cancelTextColor, R.color.cancelLineColor),
	ALTERNATE("alternate","����",R.drawable.flightdynamic_status_cancel,R.drawable.flightboard_cancel,
		R.drawable.layoutflightboard_flightdynamicsline_cancel,R.drawable.layoutflightboard_cancel,
		R.color.cancelTextColor, R.color.cancelLineColor);
	
	private String flightStatusCode;
	private String flightStatusName;
	private int flightStatusDrawable;
	private int flightBoardDrawable;
	private int flightStatusLine;
	private int layoutFlightStatus;
	private int flightStatusColor;
	private int layoutLineColor;
	
	private FlightStatus(String flightStatusCode, String flightStatusName, int flightStatusDrawable, 
		int flightBoardDrawable, int flightStatusLine, int layoutFlightStatus, int flightStatusColor,
		int layoutLineColor){
		this.flightStatusCode = flightStatusCode;
		this.flightStatusName = flightStatusName;
		this.flightStatusDrawable = flightStatusDrawable;
		this.flightBoardDrawable = flightBoardDrawable;
		this.flightStatusLine = flightStatusLine;
		this.layoutFlightStatus = layoutFlightStatus;
		this.flightStatusColor = flightStatusColor;
		this.layoutLineColor = layoutLineColor;
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
		if(flightStatusName.equals("���"))
			return TAKEOFF;
		if(flightStatusName.equals("�ƻ�"))
			return PLAN;
		if(flightStatusName.equals("����"))
			return ARRIVE;
		if(flightStatusName.equals("����"))
			return DELAY;
		if(flightStatusName.equals("ȡ��"))
			return CANCEL;
		if(flightStatusName.equals("����"))
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

	public int getLayoutLineColor() {
		return layoutLineColor;
	}

	public void setLayoutLineColor(int layoutLineColor) {
		this.layoutLineColor = layoutLineColor;
	}
}
