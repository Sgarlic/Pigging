package com.boding.constants;

public enum IntentRequestCode {
	CITY_SELECTION(0),
	DATE_SELECTION(1),
	TICKET_SEARCH(2),
	VOICE_SEARCH(3),
	ORDER_FORM(4),
	CHOOSE_PASSENGER(5),
	JOURNEYSHEET_DELIVERY(6),
	CHOOSE_DELIVERYADDR(7),
	ADD_DELIVERYADDR(8),
	ADD_PASSENGERINFO(9),
	NATIONALITY_SELECTION(10),
	ORDER_PAYEMNT(11),
	BANKCARD_SELECTION(12),
	LOGIN(13),
	MYBODING(14),
	REGISTER(15),
	MYPERSONALINFO(16),
	EDIT_PERSONALINFO(17),
	CHANGE_PASSWORD(18),
	CHANGE_PHONENUM(19),
	VERIFY_PHONENUM(20),
	ORDERS_LIST(21),
	INSURANCE_SELECTION(22);

	private int requestCode;

	public int getRequestCode() {
		return requestCode;
	}

	private IntentRequestCode(int requestCode){
		this.requestCode =  requestCode;
	}
}
