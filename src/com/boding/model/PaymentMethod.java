package com.boding.model;

public enum PaymentMethod {
	BB("≤®∂°±“"),
	Alipay("÷ß∏∂±¶"),
	WX("Œ¢–≈÷ß∏∂");
	
	private String paymentMethod;
	
	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	private PaymentMethod(String paymentMethod){
		this.paymentMethod = paymentMethod;
	}
	
	public static PaymentMethod getPaymentMethodFromCode(String code){
		if(code.equals("BB"))
			return BB;
		if(code.equals("Alipay"))
			return Alipay;
		return WX;
	}
}
