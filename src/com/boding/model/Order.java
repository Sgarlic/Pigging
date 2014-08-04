package com.boding.model;

import java.util.ArrayList;
import java.util.List;

import com.boding.constants.AirlineType;
import com.boding.constants.OrderStatus;

public class Order {
	private String orderCode = "";
	private boolean internalFlag = false;// 0、国内票 1、国际票
	private AirlineType airlineType = AirlineType.ONE_WAY_NOTRANSI;// 1、直飞单程 2、直飞往返 3、转机单程 4、转机往返
	private OrderStatus orderStatus = OrderStatus.COMPLETED;// 0、订票-待审核  1、出票-待出票  3、派送 -待派送 4、收银-待收银 5、已完成 6、退票
	private List<Passenger> passengers = new ArrayList<Passenger>();
	private List<OrderFlight> flights = new ArrayList<OrderFlight>();
	private String displayPassengerName;
	private String bookingDate = "";
	private String leaveCity = "";
	private String arriveCity = "";
	private String leaveAirport = "";
	private String arriveAirport = "";
	private String leaveDate = "";
	private String leaveTime = "";
	private String arriveDate = "";
	private String arriveTime = "";
	private String leaveTerminal = "";
	private String arriveTerminal = "";
	private String transitCity = "";
	
	private String sourceFlag = "";
	private String linkMan = "";// 联系人
	private String contactPhone = "";
	private String payMode = "";
	private String shouldRecvMoney = "";//应收金额
	private String ticketPrice = "";
	private String tax = "";
	private String insurance = "";
	private String insuranceNum = "";
	private String payAmount = "";//应付金额
	private String prePayment = "";//波丁支付金额
	private String flightDateTime = "";
	
	public Order(){};
	
	/**
	 * new order from search order list result
	 */
	public Order(String orderCode,boolean internalFlag,String airlineTypeCode,String orderStatusCode,
			String displayPassengerName, String bookingDate, String leaveCity, String arriveCity,
			String leaveAirport,String arriveAirport, String leaveTimeDate, String arriveTimeDate, 
			String leaveTerminal,String arriveTerminal, String tranistCity){
		setOrderCode(orderCode);
		setInternalFlag(internalFlag);
		setAirlineType(airlineTypeCode);
		setOrderStatus(orderStatusCode);
		setDisplayPassengerName(displayPassengerName);
		setBookingDate(bookingDate);
		setLeaveCity(leaveCity);
		setArriveCity(arriveCity);
		setLeaveAirport(leaveAirport);
		setArriveAirport(arriveAirport);
		setLeaveTimeDate(leaveTimeDate);
		setArriveTimeDate(arriveTimeDate);
		setLeaveTerminal(leaveTerminal);
		setArriveTerminal(arriveTerminal);
		setTransitCity(tranistCity);
	}
	
	/**
	 * 去掉括号和不能超过四个字
	 * @param cityName
	 * @return
	 */
	private String getFormatedCityName(String cityName){
		String newName = cityName;
		int position = cityName.indexOf('(');
		if(position!=-1){
			newName = cityName.substring(0,position);
		}
		if(newName.length()>4){
			newName = newName.substring(0,4);
		}
		return newName;
	}
	
	public String getLeaveCity() {
		return leaveCity;
	}
	
	public void setLeaveCity(String leaveCity) {
		this.leaveCity = getFormatedCityName(leaveCity);
	}

	public String getArriveCity() {
		return arriveCity;
	}

	public void setArriveCity(String arriveCity) {
		this.arriveCity = getFormatedCityName(arriveCity);
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

	public String getLeaveDate() {
		return leaveDate;
	}

	public void setLeaveTimeDate(String leaveTimeDate) {
		String[] temp = leaveTimeDate.split(" ");
		this.leaveDate = temp[0];
		this.leaveTime = temp[1];
	}

	public String getArriveDate() {
		return arriveDate;
	}

	public void setArriveTimeDate(String arriveTimeDate) {
		String[] temp = arriveTimeDate.split(" ");
		this.arriveDate = temp[0];
		this.arriveTime = temp[1];
	}

	public AirlineType getAirlineType() {
		return airlineType;
	}

	public void setAirlineType(String airlineTypeCode) {
		for(AirlineType airlineType : AirlineType.values()){
			if(airlineType.getAirlineTypeCode().equals(airlineTypeCode)){
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

	public void setOrderStatus(String orderStatusCode) {
		for(OrderStatus orderStatus : OrderStatus.values()){
			if(orderStatus.getOrderStatusCode().equals(orderStatusCode)){
				this.orderStatus = orderStatus;
			}
		}
	}

	public List<Passenger> getPassengers() {
		return passengers;
	}

	public void addPassenger(Passenger passenger) {
		this.passengers.add(passenger);
	}
	
	public List<OrderFlight> getOrderFlights(){
		return flights;
	}
	
	public void addOrderFlight(OrderFlight orderFlight){
		flights.add(orderFlight);
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public boolean isInternalFlag() {
		return internalFlag;
	}

	public void setInternalFlag(boolean internalFlag) {
		this.internalFlag = internalFlag;
	}

	public String getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(String bookingDate) {
		this.bookingDate = bookingDate;
	}

	public String getDisplayPassengerName() {
		return displayPassengerName;
	}

	public void setDisplayPassengerName(String displayPassengerName) {
		this.displayPassengerName = displayPassengerName;
	}

	public String getLeaveTime() {
		return leaveTime;
	}

	public String getArriveTime() {
		return arriveTime;
	}
	
//	@Override
//	public int describeContents() {
//		return 0;
//	}
//	
//	public Order(Parcel in){
//		setOrderCode(in.readString());
//		setInternalFlag(Boolean.parseBoolean(in.readString()));
//		setAirlineType(in.readString());
//		setOrderStatus(in.readString());
//		setDisplayPassengerName(in.readString());
//		setBookingDate(in.readString());
//		setLeaveCity(in.readString());
//		setArriveCity(in.readString());
//		setLeaveAirport(in.readString());
//		setArriveAirport(in.readString());
//		this.leaveDate = in.readString();
//		this.leaveTime = in.readString();
//		this.arriveDate = in.readString();
//		this.arriveTime = in.readString();
//		setLeaveTerminal(in.readString());
//		setArriveTerminal(in.readString());
//		setTransitCity(in.readString());
//		
//	}
//	
//	@Override
//	public void writeToParcel(Parcel dest, int arg1) {
//		dest.writeString(orderCode);
//		dest.writeString(String.valueOf(internalFlag));
//		dest.writeString(airlineType.getAirlineTypeCode());
//		dest.writeString(orderStatus.getOrderStatusCode());
//		dest.writeString(displayPassengerName);
//		dest.writeString(bookingDate);
//		dest.writeString(leaveCity);
//		dest.writeString(arriveCity);
//		dest.writeString(leaveAirport);
//		dest.writeString(arriveAirport);
//		dest.writeString(leaveDate);
//		dest.writeString(leaveTime);
//		dest.writeString(arriveDate);
//		dest.writeString(arriveTime);
//		dest.writeString(leaveTerminal);
//		dest.writeString(arriveTerminal);
//		dest.writeString(transitCity);
//	}
	
	 public String getSourceFlag() {
		return sourceFlag;
	}

	public void setSourceFlag(String sourceFlag) {
		this.sourceFlag = sourceFlag;
	}

	public String getLinkMan() {
		return linkMan;
	}

	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getPayMode() {
		return payMode;
	}

	public void setPayMode(String payMode) {
		this.payMode = payMode;
	}

	public String getShouldRecvMoney() {
		return shouldRecvMoney;
	}

	public void setShouldRecvMoney(String shouldRecvMoney) {
		this.shouldRecvMoney = shouldRecvMoney;
	}

	public String getTicketPrice() {
		return ticketPrice;
	}

	public void setTicketPrice(String ticketPrice) {
		this.ticketPrice = ticketPrice;
	}

	public String getTax() {
		return tax;
	}

	public void setTax(String tax) {
		this.tax = tax;
	}

	public String getInsurance() {
		return insurance;
	}

	public void setInsurance(String insurance) {
		this.insurance = insurance;
	}

	public String getInsuranceNum() {
		return insuranceNum;
	}

	public void setInsuranceNum(String insuranceNum) {
		this.insuranceNum = insuranceNum;
	}

	public String getPayAmount() {
//		double amount = Double.parseDouble(this.payAmount);
//		return (int)amount+"";
		return this.payAmount;
	}

	public void setPayAmount(String payAmount) {
		this.payAmount = payAmount;
	}

	public String getPrePayment() {
		return prePayment;
	}

	public void setPrePayment(String prePayment) {
		this.prePayment = prePayment;
	}

	public String getFlightDateTime() {
		return flightDateTime;
	}

	public void setFlightDateTime(String flightDateTime) {
		this.flightDateTime = flightDateTime;
	}

//	public static final Parcelable.Creator<Order> CREATOR = new Parcelable.Creator<Order>() {   
//		//重写Creator
//		 public Order createFromParcel(Parcel in) {  
//	            return new Order(in);  
//	        }  
//	          
//	        public Order[] newArray(int size) {  
//	            return new Order[size];  
//	        }  
//	 };
}
