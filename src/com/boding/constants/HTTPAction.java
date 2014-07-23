package com.boding.constants;

public enum HTTPAction {
	LOGIN,
	LAUNCHER_LOGIN,
	SETNEWPWD_LOGIN,
	GET_PERSONAL_INFO,
	EDIT_PERSONAL_INFO,
	REGISTER,
	VERIFY_PHONENUMBER,
	ACTIVIATE,
	CHANGE_PASSWORD,
	VERIFY_OLD_PHONENUM_VERIFYPHOENACTIVITY,
	VERIFY_OLD_PHONENUM_CHANGEPHONEACTIVITY,
	FORGETPASSWORD_GETCARDNO,
	BIND_NEW_PHONENUM,
	SET_NEW_PASSWORD,
	
	GET_PASSENGERLIST,
	GET_PASSENGERLIST_MANAGEMENT,
	ADD_PASSENGER,
	EDIT_PASSENGER,
	DELETE_PASSENGER,
	
	GET_DELIVERYADDRLIST,
	GET_DELIVERYADDRLIST_MANAGEMENT,
	ADD_DELIVERYADDR,
	EDIT_DELIVERYADDR,
	DELETE_DELIVERYADDR,
	
	CREATE_ORDER_DOMESTIC,
	GET_ORDER_LIST,
	GET_ORDER_DETAIL,
	
	GET_LOWPRICESUBS_LIST,
	ADD_LOWPRICESUB,
	DELETE_LOWPRICESUB,
	
	GET_MYFOLLOWED_FROM_MAIN,
	GET_MYFOLLOWED,
	FOLLOW_FLIGHTDYNAMICS,
	UNFOLLOW_FLIGHTDYNAMICS_FROM_MAIN,
	UNFOLLOW_FLIGHTDYNAMICS,
	SEARCH_FLIGHTDYNAMICS;
}
