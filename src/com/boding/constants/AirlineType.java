package com.boding.constants;

import com.boding.R;

public enum AirlineType {
	ONE_WAY_NOTRANSI("1","����",R.drawable.oneway_blue),
	ROUND_WAY_NOTRANSIT("2","����",R.drawable.roundway_blue),
	ONE_WAY_TRANSIT("3","����/��ת",R.drawable.onewaywithtransit_blue),
	ROUND_WAY_TRANSIT("4","����/��ת",R.drawable.roundwaywithtransit_blue);
	
	private String airlineTypeCode;
	private String airlineTypeName;
	private int airlineTypePicID;
	
	private AirlineType(String airlineTypeCode, String airlineTypeName, int airlineTypePicID){
		this.setAirlineTypeCode(airlineTypeCode);
		this.setAirlineTypeName(airlineTypeName);
		this.setAirlineTypePicID(airlineTypePicID);
	}

	public String getAirlineTypeCode() {
		return airlineTypeCode;
	}

	public void setAirlineTypeCode(String airlineTypeCode) {
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
