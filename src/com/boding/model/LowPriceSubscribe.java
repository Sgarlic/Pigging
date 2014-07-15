package com.boding.model;

public class LowPriceSubscribe {
	private String id;
	private String cardNo;
	private int ticketType;//机票类型 1、国内 2、国际
	private int tripType;//行程类型 1、单程 2、往返
	private String leaveCode;
	private String leaveName;
	private String arriveCode;
	private String arriveName;
	private String flightBeginDate;
	private String flightEndDate;
	private int subscribeWay;//订阅方式 1、折扣  2、价格
	private int disCount;//折扣
	private int price;
	private int noticeWay;//通知方式 1、邮箱 2、手机 3、邮箱和手机
	private String email;
	private String mobile;
	private int status;//0、待处理 1、已处理
	private int beforeAfterDay;//可以提前或延后1天 0、否 1、是
	private String doDateTime;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public int getTicketType() {
		return ticketType;
	}
	public void setTicketType(int ticketType) {
		this.ticketType = ticketType;
	}
	public int getTripType() {
		return tripType;
	}
	public void setTripType(int tripType) {
		this.tripType = tripType;
	}
	public String getLeaveCode() {
		return leaveCode;
	}
	public void setLeaveCode(String leaveCode) {
		this.leaveCode = leaveCode;
	}
	public String getLeaveName() {
		return leaveName;
	}
	public void setLeaveName(String leaveName) {
		this.leaveName = leaveName;
	}
	public String getArriveCode() {
		return arriveCode;
	}
	public void setArriveCode(String arriveCode) {
		this.arriveCode = arriveCode;
	}
	public String getArriveName() {
		return arriveName;
	}
	public void setArriveName(String arriveName) {
		this.arriveName = arriveName;
	}
	public String getFlightBeginDate() {
		return flightBeginDate;
	}
	public void setFlightBeginDate(String flightBeginDate) {
		this.flightBeginDate = flightBeginDate;
	}
	public String getFlightEndDate() {
		return flightEndDate;
	}
	public void setFlightEndDate(String flightEndDate) {
		this.flightEndDate = flightEndDate;
	}
	public int getSubscribeWay() {
		return subscribeWay;
	}
	public void setSubscribeWay(int subscribeWay) {
		this.subscribeWay = subscribeWay;
	}
	public int getDisCount() {
		return disCount;
	}
	public void setDisCount(int disCount) {
		this.disCount = disCount;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = (int)price;
	}
	public int getNoticeWay() {
		return noticeWay;
	}
	public void setNoticeWay(int noticeWay) {
		this.noticeWay = noticeWay;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getBeforeAfterDay() {
		return beforeAfterDay;
	}
	public void setBeforeAfterDay(int beforeAfterDay) {
		this.beforeAfterDay = beforeAfterDay;
	}
	public String getDoDateTime() {
		return doDateTime;
	}
	public void setDoDateTime(String doDateTime) {
		this.doDateTime = doDateTime;
	}
}
