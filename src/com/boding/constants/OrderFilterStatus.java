package com.boding.constants;

public enum OrderFilterStatus {
	ALL_ORDERS(-1,"全部订单"),
	PENDING_AUDIT(0,"待审核"),
	PENDING_GETTICKET(2,"出票中"),
	TICKET_ALREADY_GENERATED(34,"已出票"),
	PENDING_PAYMENT(5,"待支付"),
	CENCELED(6,"已取消"),
	COMPLETED(7,"已完成"),
	REFUND(8,"退票");
	
	private int orderStatusCode;
	private String orderStatusName;
	
	
	private OrderFilterStatus(int orderStatusCode, String orderStatusName){
		this.orderStatusCode = orderStatusCode;
		this.setOrderStatusName(orderStatusName);
	}

	public String getOrderStatusName() {
		return orderStatusName;
	}

	public void setOrderStatusName(String orderStatusName) {
		this.orderStatusName = orderStatusName;
	}

	public int getOrderStatusCode() {
		return orderStatusCode;
	}

	public void setOrderStatusCode(int orderStatusCode) {
		this.orderStatusCode = orderStatusCode;
	}
}
