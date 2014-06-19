package com.boding.model;

import java.util.Date;
import java.util.List;

public class Order {
	private String orderCode;
	private boolean internalFlag;// 0������Ʊ 1������Ʊ
	private int airlineType;// 1��ֱ�ɵ��� 2��ֱ������ 3��ת������ 4��ת������
	private int flag;// 0����Ʊ-�����  1����Ʊ-����Ʊ  3������ -������ 4������-������ 5������� 6����Ʊ
	private int tripType;//�г����� 1��ȥ�� 2���س�
	private List<Passenger> passengers;
	private Date bookingDate;
	private String leaveCity;
	private String arriveCity;
	private String leaveAirport;
	private String arriveAirport;
	private Date leaveTimeDate;
	private Date arriveTimeDate;
	private String leaveTerminal;
	private String arriveTerminal;
	private String transitCity;
	private String transitStayDate;//ת��ͣ��ʱ��
	private String sourceFlag = "android";
	private String linkMan;// ��ϵ��
	private String contactPhone;
	private String payMode;
	private String shouldRecvMoney;//Ӧ�ս��
	private String ticketPrice;
	private String tax;
	private String insurance;
	private String insuranceNum;
	private String payAmount;//Ӧ�����
	private String prePayment;//����֧�����
	private Date flightDateTime;
	
	private String carrierCode;//���չ�˾���ִ���
	private String carrierName;//���չ�˾����
	
	private String flightNum;//�����
	
	private String discount;

	private String rules;
	
	private String planeType;//����
}
