package com.boding.model;

public class LowPriceSubscribe {
	private String id;
	private String cardNo;
	private int ticketType;//��Ʊ���� 1������ 2������
	private int tripType;//�г����� 1������ 2������
	private String leaveCode;
	private String leaveName;
	private String arriveCode;
	private String arriveName;
	private String flightBeginDate;
	private String flightEndDate;
	private int subscribeWay;//���ķ�ʽ 1���ۿ�  2���۸�
	private int disCount;//�ۿ�
	private int price;
	private int noticeWay;//֪ͨ��ʽ 1������ 2���ֻ� 3��������ֻ�
	private String email;
	private String mobile;
	private int status;//0�������� 1���Ѵ���
	private int beforeAfterDay;//������ǰ���Ӻ�1�� 0���� 1����
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
