package com.boding.model;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.boding.constants.AirlineType;
import com.boding.constants.OrderStatus;

public class Order {
	private String orderCode = "";
	private boolean internalFlag = false;// 0������Ʊ 1������Ʊ
	private AirlineType airlineType = AirlineType.ONE_WAY_NOTRANSI;// 1��ֱ�ɵ��� 2��ֱ������ 3��ת������ 4��ת������
	private OrderStatus orderStatus = OrderStatus.DONE;// 0����Ʊ-�����  1����Ʊ-����Ʊ  3������ -������ 4������-������ 5������� 6����Ʊ
	private int tripType = 1;//�г����� 1��ȥ�� 2���س�
	private List<Passenger> passengers;
	private Date bookingDate = Calendar.getInstance().getTime();
	private String leaveCity = "";
	private String arriveCity = "";
	private String leaveAirport = "";
	private String arriveAirport = "";
	private Date leaveTimeDate = Calendar.getInstance().getTime();
	private Date arriveTimeDate = Calendar.getInstance().getTime();
	private String leaveTerminal = "";
	private String arriveTerminal = "";
	private String transitCity = "";
	private String transitStayDate = "";//ת��ͣ��ʱ��
	private String sourceFlag = "android";
	private String linkMan = "";// ��ϵ��
	private String contactPhone = "";
	private String payMode = "";
	private String shouldRecvMoney = "";//Ӧ�ս��
	private String ticketPrice = "";
	private String tax = "";
	private String insurance = "";
	private String insuranceNum = "";
	private String payAmount = "";//Ӧ�����
	private String prePayment = "";//����֧�����
	private Date flightDateTime = Calendar.getInstance().getTime();
	
	private String carrierCode = "";//���չ�˾���ִ���
	private String carrierName = "";//���չ�˾����
	
	private String flightNum = "";//�����
	
	private String discount = "";

	private String rules = "";
	
	private String planeType = "";//����

	public String getLeaveCity() {
		return leaveCity;
	}

	public void setLeaveCity(String leaveCity) {
		this.leaveCity = leaveCity;
	}

	public String getArriveCity() {
		return arriveCity;
	}

	public void setArriveCity(String arriveCity) {
		this.arriveCity = arriveCity;
	}

	public String getLeaveAirport() {
		return leaveAirport;
	}

	public void setLeaveAirport(String leaveAirport) {
		this.leaveAirport = leaveAirport;
	}

	public String getArriveAirport() {
		return arriveAirport;
	}

	public void setArriveAirport(String arriveAirport) {
		this.arriveAirport = arriveAirport;
	}

	public String getLeaveTerminal() {
		return leaveTerminal;
	}

	public void setLeaveTerminal(String leaveTerminal) {
		this.leaveTerminal = leaveTerminal;
	}

	public String getArriveTerminal() {
		return arriveTerminal;
	}

	public void setArriveTerminal(String arriveTerminal) {
		this.arriveTerminal = arriveTerminal;
	}

	public String getTransitCity() {
		return transitCity;
	}

	public void setTransitCity(String transitCity) {
		this.transitCity = transitCity;
	}

	public Date getLeaveTimeDate() {
		return leaveTimeDate;
	}

	public void setLeaveTimeDate(Date leaveTimeDate) {
		this.leaveTimeDate = leaveTimeDate;
	}

	public Date getArriveTimeDate() {
		return arriveTimeDate;
	}

	public void setArriveTimeDate(Date arriveTimeDate) {
		this.arriveTimeDate = arriveTimeDate;
	}

	public AirlineType getAirlineType() {
		return airlineType;
	}

	public void setAirlineType(int airlineTypeCode) {
		for(AirlineType airlineType : AirlineType.values()){
			if(airlineType.getAirlineTypeCode() == airlineTypeCode){
				this.airlineType = airlineType;
			}
		}
	}
	
	public String getFirstPassenger(){
		if(getPassengers()==null || getPassengers().size()==0)
			return "";
		else
			return getPassengers().get(0).getName();
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(int orderStatusCode) {
		for(OrderStatus orderStatus : OrderStatus.values()){
			if(orderStatus.getOrderStatusCode() == orderStatusCode){
				this.orderStatus = orderStatus;
			}
		}
	}

	public List<Passenger> getPassengers() {
		return passengers;
	}

	public void setPassengers(List<Passenger> passengers) {
		this.passengers = passengers;
	}
}
