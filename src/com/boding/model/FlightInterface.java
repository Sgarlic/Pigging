package com.boding.model;

public interface FlightInterface {
	public String getFlightPrice();
	public String getFlyFromCity();
	public String getFlyToCity();
	public int getSelectedClassLeftTicket();
	public void setSelectedClassPos(int position);
	public int getSelectedClassPos();
}
