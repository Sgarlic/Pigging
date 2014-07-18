package com.boding.constants;

public enum OrderFilterStatus {
	ALL_ORDERS(-1,"ȫ������"),
	PENDING_AUDIT(0,"�����"),
	PENDING_GETTICKET(2,"��Ʊ��"),
	TICKET_ALREADY_GENERATED(34,"�ѳ�Ʊ"),
	PENDING_PAYMENT(5,"��֧��"),
	CENCELED(6,"��ȡ��"),
	COMPLETED(7,"�����"),
	REFUND(8,"��Ʊ");
	
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
