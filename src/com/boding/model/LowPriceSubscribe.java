package com.boding.model;

public class LowPriceSubscribe {
	private String id;
	private String cardNo;
	private int ticketType;//机票类型 1、国内 2、国际
	private int tripType;//行程类型 1、单程 2、往返
	private String leaveCode;
	private String leaveName;
	private String arriveCode;
	private String arriveName;
	private String flightBeginDate;
	private String flightEndDate;
	private int subscribeWay;//订阅方式 1、折扣  2、价格
	private int disCount;//折扣
	private double price;
	private int noticeWay;//通知方式 1、邮箱 2、手机 3、邮箱和手机
	private String email;
	private String mobile;
	private int status;//0、待处理 1、已处理
	private int beforeAfterDay;//可以提前或延后1天 0、否 1、是
	private String doDateTime;
}
