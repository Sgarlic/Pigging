package com.boding.exception;

public enum BodingExceptionEnum {
	UNKNOWN_EXCEPTION("10001","未知错误"),
	
	/**
	 * PID RELATED ERROR
	 */
	PID_RELATED_ERROR("20000","PID类相关错误"),
	CONNECT_PID_FAILED("20001","连接PID失败"),
	LOGIN_VERIFICATION_FAILED("20002","登录验证失败"),
	INVALID_SEARCH_COMMAND("20003","查询指令无效"),
	NO_CORRECT_DATA_RETURNED_FROM_PID("20004","PID无正确数据返回"),
	INVALID_CLASS_OR_SEAT("20005","无效的舱位或座位"),
	NO_ENOUGH_SEATS("20006","无足够的座位数"),
	
	/**
	 * POLICY RELATED ERROR
	 */
	POLICY_RELATED_ERROR("30000","政策库相关错误"),
	CONNECT_POLICY_DATEBASE_FAILED("30001","连接政策库失败"),
	NO_RELATED_INTERNATIONAL_POLICY_INFO("30002","无相应的国际政策信息"),
	INVALID_ADDON_FLIGHT("30003","无效的AddOn航班"),
	NO_RELATED_DOMESTIC_POLICY_INFO("30004","无相应的国内政策信息"),
	NOTIN_APPOINTED_FLIGHT_LIST("30005","不在指定航班列表范围之内"),
	IN_EXCEPTION_FLIGHT_LIST("30006","在排除航班列表范围之内"),
	GOFLIGHTINTO_NOTRELACTEDTO_POLICYINTO("30007","去程航班信息与政策信息没有匹配上"),
	RETURNFLIGHTINTO_NOTRELACTEDTO_POLICYINTO("30008","回程航班信息与政策信息没有匹配上"),
	NO_MATCH_GORETURN_INFO("30009","去程与回程没有匹配数据"),
	
	/**
	 * OTHER ERROR
	 */
	OTHER_OTHER("40000","其它类相关错误"),
	INTERFACE_EXCEPTION("40001","接口程序发生异常错误"),
	INVALID_PARAMETER("40002","参数错误"),
	TIMEOUT("40003","超时"),
	INVALID_FROMCITYCODE_LENGTH("40004","出发地代码长度错误"),
	INVALID_TOCITYCODE_LENGTH("40005","到达地代码长度错误"),
	INVALID_DATE("40006","数字日期格式错误"),
	INVALID_FLIGHTCOMPNAY_CODE("40007","航空公司代码长度错误"),
	
	/**
	 * AUTHORITY RELATED ERROR
	 */
	AUTHORITY_RELATED_ERROR("50000","权限类相关错误"),
	INVALID_ACCOUNT("50001","无效帐号"),
	INVALID_PASSWORD("50002","无效密码"),
	EXPIRED_ACCOUNT("50003","帐号过期"),
	NOT_PERMITTED_IP("50004","IP没有许可"),
	NO_GPRS_FLOW("50005","超流量"),
	NO_SEARCH_AUTHORITY("50100","无查询权限"),
	NO_ORDER_AUTHORITY("50200","无预订权限");
	
	String exceptionCode;
	String exceptionDescription;
	
	public String getExceptionCode() {
		return exceptionCode;
	}

	private BodingExceptionEnum(String code, String exceptionDescription){
		this.exceptionCode = code;
		this.exceptionDescription = exceptionDescription;
	}

	public String getExceptionDescription() {
		return exceptionDescription;
	}
}
