package com.boding.model;

public class OrderFlight {
	private String id;
	private String tripType;//行程类型 1、去程 2、回程
	private String carrier;
	private String carrier_name;
	private String flight_num;
	private String discount;
	private String leave_ariport;
	private String arrive_ariport;
	private String flight_date;
	private String leave_date;
	private String leave_time;
	private String arrive_date;
	private String arrive_time;
	private String leaterminal = "";
	private String arrterminal = "";
	private String reles;
	private String planetype;
	private String duration;
	private String tran_city;
	private String tran_stay_date;
	
	public OrderFlight(){
		
	}
	
	public OrderFlight(String id, String tripType, String carrier, 
			String carrier_name,String flight_num, String discount,
			String leave_airport, String arrive_airport,String flight_date,
			String leave_time_dt,String arrive_time_dt, String leaterminal,
			String arrterminal, String reles, String planetype, String duration,
			String tran_city, String tran_stay_date){
		setId(id);
		setTripType(tripType);
		setCarrier(carrier);
		setCarrier_name(carrier_name);
		setFlight_num(flight_num);
		setDiscount(discount);
		setLeave_ariport(leave_airport);
		setArrive_ariport(arrive_airport);
		setFlight_date(flight_date);
		setLeave_time_dt(leave_time_dt);
		setArrive_time_dt(arrive_time_dt);
		setLeaterminal(leaterminal);
		setArrterminal(arrterminal);
		setReles(reles);
		setPlanetype(planetype);
		setDuration(duration);
		setTran_city(tran_city);
		setTran_stay_date(tran_stay_date);
	}
	
	public String getTripType() {
		return tripType;
	}
	public void setTripType(String tripType) {
		this.tripType = tripType;
	}
	public String getCarrier() {
		return carrier;
	}
	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}
	public String getCarrier_name() {
		return carrier_name;
	}
	public void setCarrier_name(String carrier_name) {
		this.carrier_name = carrier_name;
	}
	public String getFlight_num() {
		return flight_num;
	}
	public void setFlight_num(String flight_num) {
		this.flight_num = flight_num;
	}
	public String getDiscount() {
		return discount;
	}
	public void setDiscount(String discount) {
		this.discount = discount;
	}
	public String getLeave_ariport() {
		return leave_ariport;
	}
	public void setLeave_ariport(String leave_ariport) {
		this.leave_ariport = leave_ariport;
	}
	public String getArrive_ariport() {
		return arrive_ariport;
	}
	public void setArrive_ariport(String arrive_ariport) {
		this.arrive_ariport = arrive_ariport;
	}
	public String getFlight_date() {
		return flight_date;
	}
	public void setFlight_date(String flight_date) {
		this.flight_date = flight_date;
	}
	
	public String getLeave_date(){
		return leave_date;
	}
	
	public String getLeave_time() {
		return leave_time;
	}
	public void setLeave_time_dt(String leave_time_dt) {
		String[] temp = leave_time_dt.split(" ");
		this.leave_date = temp[0];
		this.leave_time = temp[1];
	}
	
	public String getArrive_date(){
		return arrive_date;
	}
	
	public String getArrive_time() {
		return arrive_time;
	}
	public void setArrive_time_dt(String arrive_time_dt) {
		String[] temp = arrive_time_dt.split(" ");
		this.arrive_date = temp[0];
		this.arrive_time = temp[1];
	}
	public String getLeaterminal() {
		return leaterminal;
	}
	public void setLeaterminal(String leaterminal) {
		this.leaterminal = leaterminal;
	}
	public String getArrterminal() {
		return arrterminal;
	}
	public void setArrterminal(String arrterminal) {
		this.arrterminal = arrterminal;
	}
	public String getReles() {
		return reles;
	}
	public void setReles(String reles) {
		this.reles = reles;
	}
	public String getPlanetype() {
		return planetype;
	}
	public void setPlanetype(String planetype) {
		this.planetype = planetype;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getTran_city() {
		return tran_city;
	}
	public void setTran_city(String tran_city) {
		this.tran_city = tran_city;
	}
	public String getTran_stay_date() {
		return tran_stay_date;
	}
	public void setTran_stay_date(String tran_stay_date) {
		this.tran_stay_date = tran_stay_date;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}
