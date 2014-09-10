package com.boding.model;

import java.util.HashSet;

public interface AirlineInterface {
	public void orderLinesByLeatime(boolean isAsc);
	
	public void orderLinesByPrice(boolean isAsc);
	
	public String getlowestPrice();
	
	public String getGoDate();
	
	public HashSet<String> getCompanyInfo();
}
