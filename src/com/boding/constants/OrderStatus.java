package com.boding.constants;

import com.boding.R;

public enum OrderStatus {
	PENDING_AUDIT(0,"�����",R.color.darkgray),
	PENDING_GETTICKET(1,"����Ʊ",R.color.darkgray),
	PENDING_DELIVERY(3,"������",R.color.darkgray),
	PENDING_PAYMENT(4,"������",R.color.darkgray),
	DONE(5,"�����",R.color.darkgray),
	REFUND(6,"��Ʊ",R.color.darkgray);
	
	private int orderStatusCode;
	private String orderStatusName;
	private int orderStatusColorId;
	
	private OrderStatus(int orderStatusCode,String orderStatusName,int orderStatusColorId){
		this.setOrderStatusCode(orderStatusCode);
		this.setOrderStatusName(orderStatusName);
		this.setOrderStatusColorId(orderStatusColorId);
	}

	public int getOrderStatusCode() {
		return orderStatusCode;
	}

	public void setOrderStatusCode(int orderStatusCode) {
		this.orderStatusCode = orderStatusCode;
	}

	public String getOrderStatusName() {
		return orderStatusName;
	}

	public void setOrderStatusName(String orderStatusName) {
		this.orderStatusName = orderStatusName;
	}

	public int getOrderStatusColorId() {
		return orderStatusColorId;
	}

	public void setOrderStatusColorId(int orderStatusColorId) {
		this.orderStatusColorId = orderStatusColorId;
	}
}
