package com.boding.constants;

import com.boding.R;

public enum AirlineType {
	ONE_WAY_NOTRANSI(1,"单程",R.drawable.oneway_blue),
	ROUND_WAY_NOTRANSIT(2,"往返",R.drawable.roundway_blue),
	ONE_WAY_TRANSIT(3,"单程/中转",R.drawable.onewaywithtransit_blue),
	ROUND_WAY_TRANSIT(4,"往返/中转",R.drawable.roundwaywithtransit_blue);
	
	private int airlineTypeCode;
	private String airlineTypeName;
	private int airlineTypePicID;
	
	private AirlineType(int airlineTypeCode, String airlineTypeName, int airlineTypePicID){
		this.setAirlineTypeCode(airlineTypeCode);
		this.setAirlineTypeName(airlineTypeName);
		this.setAirlineTypePicID(airlineTypePicID);
	}

	public int getAirlineTypeCode() {
		return airlineTypeCode;
	}

	public void setAirlineTypeCode(int airlineTypeCode) {
		this.airlineTypeCode = airlineTypeCode;
	}

	public String getAirlineTypeName() {
		return airlineTypeName;
	}

	public void setAirlineTypeName(String airlineTypeName) {
		this.airlineTypeName = airlineTypeName;
	}

	public int getAirlineTypePicID() {
		return airlineTypePicID;
	}

	public void setAirlineTypePicID(int airlineTypePicID) {
		this.airlineTypePicID = airlineTypePicID;
	}
}
