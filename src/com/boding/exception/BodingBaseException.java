package com.boding.exception;

public class BodingBaseException extends Exception{
	private BodingExceptionEnum exceptionEnum;

	public BodingBaseException(BodingExceptionEnum exceptionEnum){
		this.exceptionEnum = exceptionEnum;
	}

	public BodingExceptionEnum getExceptionEnum() {
		return exceptionEnum;
	}
}
