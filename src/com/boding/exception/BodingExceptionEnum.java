package com.boding.exception;

public enum BodingExceptionEnum {
	UNKNOWN_EXCEPTION(10001);
	
	int exceptionCode;
	
	private BodingExceptionEnum(int code){
		this.exceptionCode = code;
	}

	public int getExceptionCode() {
		return exceptionCode;
	}

	public void setExceptionCode(int exceptionCode) {
		this.exceptionCode = exceptionCode;
	}
}
