package com.boding.model;

public interface AirlineInterface {
	public void orderLinesByLeatime(boolean isAsc);
	
	public void orderLinesByPrice(boolean isAsc);
	
	public String getlowestPrice();
	
	public String getGoDate();
}
