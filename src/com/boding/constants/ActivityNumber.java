package com.boding.constants;

public enum ActivityNumber {
	MAIN(0),
	TICKET_SEARCH_RESULT(1),
	LOWPRICE_SUBS_LIST(2);

	private int activityNum;

	public int getActivityNum() {
		return activityNum;
	}

	private ActivityNumber(int activityNum){
		this.activityNum =  activityNum;
	}
}
