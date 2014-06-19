package com.boding.model;

import java.util.Date;
import java.util.List;

public class Order {
	private String orderCode;
	private boolean internalFlag;// 0、国内票 1、国际票
	private int airlineType;// 1、直飞单程 2、直飞往返 3、转机单程 4、转机往返
	private int flag;// 0、订票-待审核  1、出票-待出票  3、派送 -待派送 4、收银-待收银 5、已完成 6、退票
	private int tripType;//行程类型 1、去程 2、回程
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
	private String transitStayDate;//转机停留时长
	private String sourceFlag = "android";
	private String linkMan;// 联系人
	private String contactPhone;
	private String payMode;
	private String shouldRecvMoney;//应收金额
	private String ticketPrice;
	private String tax;
	private String insurance;
	private String insuranceNum;
	private String payAmount;//应付金额
	private String prePayment;//波丁支付金额
	private Date flightDateTime;
	
	private String carrierCode;//航空公司两字代码
	private String carrierName;//航空公司名称
	
	private String flightNum;//航班号
	
	private String discount;

	private String rules;
	
	private String planeType;//机型
}
