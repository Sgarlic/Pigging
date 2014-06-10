package com.boding.constants;

public enum IntentRequestCode {
	START_CITY_SELECTION(0),
	START_DATE_SELECTION(1),
	START_TICKET_SEARCH(2),
	START_VOICE_SEARCH(3);

	private int requestCode;

	public int getRequestCode() {
		return requestCode;
	}

	private IntentRequestCode(int requestCode){
		this.requestCode =  requestCode;
	}
}
