package com.boding.constants;

public enum HTTPAction {
	LOGIN(0);
	
	private int actionCode;
	
	private HTTPAction(int actionCode){
		this.actionCode =actionCode;
	}
}
