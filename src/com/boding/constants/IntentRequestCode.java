package com.boding.constants;

public enum IntentRequestCode {
	START_CITY_SELECTION(0),
	START_DATE_SELECTION(1),
	START_TICKET_SEARCH(2),
	START_VOICE_SEARCH(3),
	START_ORDER_FORM(4),
	START_CHOOSE_PASSENGER(5),
	START_JOURNEYSHEET_DELIVERY(6),
	START_CHOOSE_DELIVERYADDR(7);

	private int requestCode;

	public int getRequestCode() {
		return requestCode;
	}

	private IntentRequestCode(int requestCode){
		this.requestCode =  requestCode;
	}
}
