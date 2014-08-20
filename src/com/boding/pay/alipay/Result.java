package com.boding.pay.alipay;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.util.Log;

public class Result {
	
	private static final Map<String, String> sResultStatus;

	private String mResult;
	
	String resultStatus = null;
	String memo = null;
	String result = null;
	boolean isSignOk = false;

	public Result(String result) {
		this.mResult = result;
	}

	static {
		sResultStatus = new HashMap<String, String>();
		sResultStatus.put("9000", "�����ɹ�");
		sResultStatus.put("4000", "ϵͳ�쳣");
		sResultStatus.put("4001", "���ݸ�ʽ����ȷ");
		sResultStatus.put("4003", "���û��󶨵�֧�����˻������������֧��");
		sResultStatus.put("4004", "���û��ѽ����");
		sResultStatus.put("4005", "��ʧ�ܻ�û�а�");
		sResultStatus.put("4006", "����֧��ʧ��");
		sResultStatus.put("4010", "���°��˻�");
		sResultStatus.put("6000", "֧���������ڽ�����������");
		sResultStatus.put("6001", "�û���;ȡ��֧������");
		sResultStatus.put("7001", "��ҳ֧��ʧ��");
	}

	public  String getResult() {
		String src = mResult.replace("{", "");
		src = src.replace("}", "");
		return getContent(src, "memo=", ";result");
	}

	public  void parseResult() {
		
		try {
			String src = mResult.replace("{", "");
			src = src.replace("}", "");
			String rs = getContent(src, "resultStatus=", ";memo");
			if (sResultStatus.containsKey(rs)) {
				resultStatus = sResultStatus.get(rs);
			} else {
				resultStatus = "��������";
			}
			resultStatus += "(" + rs + ")";

			memo = getContent(src, "memo=", ";result");
			result = getContent(src, "result=", null);
			isSignOk = checkSign(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private  boolean checkSign(String result) {
		boolean retVal = false;
		try {
			JSONObject json = string2JSON(result, "&");

			int pos = result.indexOf("&sign_type=");
			String signContent = result.substring(0, pos);

			String signType = json.getString("sign_type");
			signType = signType.replace("\"", "");

			String sign = json.getString("sign");
			sign = sign.replace("\"", "");

			if (signType.equalsIgnoreCase("RSA")) {
				retVal = Rsa.doCheck(signContent, sign, Keys.PUBLIC);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.i("Result", "Exception =" + e);
		}
		Log.i("Result", "checkSign =" + retVal);
		return retVal;
	}

	public  JSONObject string2JSON(String src, String split) {
		JSONObject json = new JSONObject();

		try {
			String[] arr = src.split(split);
			for (int i = 0; i < arr.length; i++) {
				String[] arrKey = arr[i].split("=");
				json.put(arrKey[0], arr[i].substring(arrKey[0].length() + 1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return json;
	}

	private  String getContent(String src, String startTag, String endTag) {
		String content = src;
		int start = src.indexOf(startTag);
		start += startTag.length();

		try {
			if (endTag != null) {
				int end = src.indexOf(endTag);
				content = src.substring(start, end);
			} else {
				content = src.substring(start);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return content;
	}
}
