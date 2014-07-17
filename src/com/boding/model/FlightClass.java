package com.boding.model;

public class FlightClass {
	private String classType;
	private String id;
	private String code;
	private String seat;
	private Price price;
	private Rule rule;
	private Tax tax;
	private Bind bind;
	private String flightClassName = "";
	
	public String getClassType() {
		return classType;
	}
	public void setClassType(String classType) {
		this.classType = classType;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	
	//���ʲ�λ�ֱ�Ϊ��F ͷ�Ȳ� ��C ����ա�Y��������λ����Ϊ���ò�
	public void setCode(String code) {
		this.code = code;
		if(code.equals("F"))
			this.flightClassName = "ͷ�Ȳ�";
		else if(code.equals("C"))
			this.flightClassName = "�����";
		else
			this.flightClassName = "���ò�";
	}
	public String getSeat() {
		return seat;
	}
	public void setSeat(String seat) {
		this.seat = seat;
	}
	public Price getPrice() {
		return price;
	}
	public void setPrice(Price price) {
		this.price = price;
	}
	public Rule getRule() {
		return rule;
	}
	public void setRule(Rule rule) {
		this.rule = rule;
	}
	public Tax getTax() {
		return tax;
	}
	public void setTax(Tax tax) {
		this.tax = tax;
	}
	public Bind getBind() {
		return bind;
	}
	public void setBind(Bind bind) {
		this.bind = bind;
	}
	public String getFlightClassName() {
		return flightClassName;
	}
}
