package com.boding.exception;

public enum BodingExceptionEnum {
	UNKNOWN_EXCEPTION("10001","δ֪����"),
	
	/**
	 * PID RELATED ERROR
	 */
	PID_RELATED_ERROR("20000","PID����ش���"),
	CONNECT_PID_FAILED("20001","����PIDʧ��"),
	LOGIN_VERIFICATION_FAILED("20002","��¼��֤ʧ��"),
	INVALID_SEARCH_COMMAND("20003","��ѯָ����Ч"),
	NO_CORRECT_DATA_RETURNED_FROM_PID("20004","PID����ȷ���ݷ���"),
	INVALID_CLASS_OR_SEAT("20005","��Ч�Ĳ�λ����λ"),
	NO_ENOUGH_SEATS("20006","���㹻����λ��"),
	
	/**
	 * POLICY RELATED ERROR
	 */
	POLICY_RELATED_ERROR("30000","���߿���ش���"),
	CONNECT_POLICY_DATEBASE_FAILED("30001","�������߿�ʧ��"),
	NO_RELATED_INTERNATIONAL_POLICY_INFO("30002","����Ӧ�Ĺ���������Ϣ"),
	INVALID_ADDON_FLIGHT("30003","��Ч��AddOn����"),
	NO_RELATED_DOMESTIC_POLICY_INFO("30004","����Ӧ�Ĺ���������Ϣ"),
	NOTIN_APPOINTED_FLIGHT_LIST("30005","����ָ�������б�Χ֮��"),
	IN_EXCEPTION_FLIGHT_LIST("30006","���ų������б�Χ֮��"),
	GOFLIGHTINTO_NOTRELACTEDTO_POLICYINTO("30007","ȥ�̺�����Ϣ��������Ϣû��ƥ����"),
	RETURNFLIGHTINTO_NOTRELACTEDTO_POLICYINTO("30008","�س̺�����Ϣ��������Ϣû��ƥ����"),
	NO_MATCH_GORETURN_INFO("30009","ȥ����س�û��ƥ������"),
	
	/**
	 * OTHER ERROR
	 */
	OTHER_OTHER("40000","��������ش���"),
	INTERFACE_EXCEPTION("40001","�ӿڳ������쳣����"),
	INVALID_PARAMETER("40002","��������"),
	TIMEOUT("40003","��ʱ"),
	INVALID_FROMCITYCODE_LENGTH("40004","�����ش��볤�ȴ���"),
	INVALID_TOCITYCODE_LENGTH("40005","����ش��볤�ȴ���"),
	INVALID_DATE("40006","�������ڸ�ʽ����"),
	INVALID_FLIGHTCOMPNAY_CODE("40007","���չ�˾���볤�ȴ���"),
	
	/**
	 * AUTHORITY RELATED ERROR
	 */
	AUTHORITY_RELATED_ERROR("50000","Ȩ������ش���"),
	INVALID_ACCOUNT("50001","��Ч�ʺ�"),
	INVALID_PASSWORD("50002","��Ч����"),
	EXPIRED_ACCOUNT("50003","�ʺŹ���"),
	NOT_PERMITTED_IP("50004","IPû�����"),
	NO_GPRS_FLOW("50005","������"),
	NO_SEARCH_AUTHORITY("50100","�޲�ѯȨ��"),
	NO_ORDER_AUTHORITY("50200","��Ԥ��Ȩ��");
	
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
