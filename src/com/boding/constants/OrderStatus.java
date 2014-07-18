package com.boding.constants;

import com.boding.R;

public enum OrderStatus {
	PENDING_AUDIT("0","审核中","待审核",R.color.darkgray),
	PENDING_GETTICKET("2","出票中","待出票",R.color.darkgray),
	PENDING_DELIVERY("3","派送中","待派送",R.color.darkgray),
	PENDING_COLLECTMONEY("4","收银中","待收银",R.color.darkgray),
	PENDING_PAYMENT("5","待支付","待支付",R.color.darkgray),
	CENCELED("6","已取消","已取消",R.color.darkgray),
	COMPLETED("7","已完成","已完成",R.color.darkgray),
	REFUND("8","退票","退票",R.color.darkgray);
	
	private String orderStatusCode;
	private String orderStatusName;
	private String orderDetailStatusName;
	
	public OrderFilterStatus getRelatedOrderFilterStatus(){
		if(this.orderStatusCode.equals("0"))
			return OrderFilterStatus.PENDING_AUDIT;
		if(this.orderStatusCode.equals("2"))
			return OrderFilterStatus.PENDING_GETTICKET;
		if(this.orderStatusCode.equals("3"))
			return OrderFilterStatus.TICKET_ALREADY_GENERATED;
		if(this.orderStatusCode.equals("4"))
			return OrderFilterStatus.TICKET_ALREADY_GENERATED;
		if(this.orderStatusCode.equals("5"))
			return OrderFilterStatus.PENDING_PAYMENT;
		if(this.orderStatusCode.equals("6"))
			return OrderFilterStatus.CENCELED;
		if(this.orderStatusCode.equals("7"))
			return OrderFilterStatus.COMPLETED;
		return OrderFilterStatus.REFUND;
	}
	
	public String getOrderDetailStatusName() {
		return orderDetailStatusName;
	}

	public void setOrderDetailStatusName(String orderDetailStatusName) {
		this.orderDetailStatusName = orderDetailStatusName;
	}

	private int orderStatusColorId;
	
	private OrderStatus(String orderStatusCode,String orderStatusName,String orderDetailStatusName,int orderStatusColorId){
		this.setOrderStatusCode(orderStatusCode);
		this.setOrderStatusName(orderStatusName);
		this.orderDetailStatusName = orderDetailStatusName;
		this.setOrderStatusColorId(orderStatusColorId);
	}

	public String getOrderStatusCode() {
		return orderStatusCode;
	}

	public void setOrderStatusCode(String orderStatusCode) {
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
