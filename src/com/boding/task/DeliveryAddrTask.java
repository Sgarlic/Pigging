package com.boding.task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.boding.app.AddDeliveryAddrActivity;
import com.boding.app.BodingBaseActivity;
import com.boding.app.ChooseDeliveryAddressActivity;
import com.boding.app.CommonInfoMDeliverAddrActivity;
import com.boding.constants.Constants;
import com.boding.constants.GlobalVariables;
import com.boding.constants.HTTPAction;
import com.boding.model.DeliveryAddress;
import com.boding.util.Encryption;

public class DeliveryAddrTask extends BodingBaseAsyncTask{

	public DeliveryAddrTask(Context context, HTTPAction action){
		super(context, action);
	}
	
	public boolean addDeliveryAddr(DeliveryAddress deliveryAddr){
		boolean resultCode = false;
		StringBuilder sb = new StringBuilder();
		sb.append(Constants.BODINGACCOUNT);
		sb.append(GlobalVariables.bodingUser.getCardno());
		String sign = "";
		
		sb.append(deliveryAddr.getRecipientName());
		sb.append(deliveryAddr.getProvinceName());
		sb.append(deliveryAddr.getCity());
		sb.append(deliveryAddr.getDistrict());
		sb.append(deliveryAddr.getDetailedAddr());
		sb.append(deliveryAddr.getZipcode());
		sb.append(deliveryAddr.getMobile());
		sb.append(deliveryAddr.getPhone());
		
		try {
			sb.append(Encryption.getMD5(Constants.BODINGKEY).toUpperCase());
			sign = Encryption.getMD5(sb.toString()).toUpperCase();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		String urlStr = "";
		String urlFormat = "http://api.iboding.com/API/User/Address/NewAddressInfo.ashx?userid=%s&CardNo=%s&ReceiveName=%s&Province=%s&City=%s&Area=%s&Address=%s&ZipCode=%s&Mobile=%s&Phone=%s&sign=%s";
		try {
			urlStr =  String.format(urlFormat,Constants.BODINGACCOUNT,GlobalVariables.bodingUser.getCardno(),
					URLEncoder.encode(deliveryAddr.getRecipientName(),"UTF-8"),
					URLEncoder.encode(deliveryAddr.getProvinceName(),"UTF-8"), 
					URLEncoder.encode(deliveryAddr.getCity(),"UTF-8"),
					URLEncoder.encode(deliveryAddr.getDistrict(),"UTF-8"),
					URLEncoder.encode(deliveryAddr.getDetailedAddr(),"UTF-8"),
					deliveryAddr.getZipcode(),deliveryAddr.getMobile(),deliveryAddr.getPhone(),sign);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String result = connectingServer(urlStr);
		try {
			JSONObject resultJson = new JSONObject(result);
			if (resultJson.getString("result").equals("0"))
				resultCode = true;
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return resultCode;
	}
	
	public boolean editDeliveryAddr(DeliveryAddress deliveryAddr){
		boolean resultCode = false;
		StringBuilder sb = new StringBuilder();
		sb.append(Constants.BODINGACCOUNT);
		sb.append(GlobalVariables.bodingUser.getCardno());
		String sign = "";
		
		sb.append(deliveryAddr.getAddrID());
		sb.append(deliveryAddr.getRecipientName());
		sb.append(deliveryAddr.getProvinceName());
		sb.append(deliveryAddr.getCity());
		sb.append(deliveryAddr.getDistrict());
		sb.append(deliveryAddr.getDetailedAddr());
		sb.append(deliveryAddr.getZipcode());
		sb.append(deliveryAddr.getMobile());
		sb.append(deliveryAddr.getPhone());
		
		try {
			sb.append(Encryption.getMD5(Constants.BODINGKEY).toUpperCase());
			sign = Encryption.getMD5(sb.toString()).toUpperCase();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		String urlStr = "";
		String urlFormat = "http://api.iboding.com/API/User/Address/ModifyAddressInfo.ashx?userid=%s&CardNo=%s&Id=%s&ReceiveName=%s&Province=%s&City=%s&Area=%s&Address=%s&ZipCode=%s&Mobile=%s&Phone=%s&sign=%s";
		try {
			urlStr =  String.format(urlFormat,Constants.BODINGACCOUNT,GlobalVariables.bodingUser.getCardno(),
					deliveryAddr.getAddrID(),
					URLEncoder.encode(deliveryAddr.getRecipientName(),"UTF-8"),
					URLEncoder.encode(deliveryAddr.getProvinceName(),"UTF-8"), 
					URLEncoder.encode(deliveryAddr.getCity(),"UTF-8"),
					URLEncoder.encode(deliveryAddr.getDistrict(),"UTF-8"),
					URLEncoder.encode(deliveryAddr.getDetailedAddr(),"UTF-8"),
					deliveryAddr.getZipcode(),deliveryAddr.getMobile(),deliveryAddr.getPhone(),sign);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String result = connectingServer(urlStr);
		try {
			JSONObject resultJson = new JSONObject(result);
			if (resultJson.getString("result").equals("0"))
				resultCode = true;
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return resultCode;
	}
	
	public boolean deleteDeliveryAddr(String deliveryAddrId){
		boolean resultCode = false;
		StringBuilder sb = new StringBuilder();
		sb.append(Constants.BODINGACCOUNT);
		sb.append(GlobalVariables.bodingUser.getCardno());
		String sign = "";
		
		sb.append(deliveryAddrId);
		
		try {
			sb.append(Encryption.getMD5(Constants.BODINGKEY).toUpperCase());
			sign = Encryption.getMD5(sb.toString()).toUpperCase();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		String urlStr = "";
		String urlFormat = "http://api.iboding.com/API/User/Address/DelAddressInfo.ashx?userid=%s&CardNo=%s&id=%s&sign=%s";
		urlStr =  String.format(urlFormat,Constants.BODINGACCOUNT,GlobalVariables.bodingUser.getCardno(),
				deliveryAddrId, sign);
		String result = connectingServer(urlStr);
		try {
			JSONObject resultJson = new JSONObject(result);
			if (resultJson.getString("result").equals("0"))
				resultCode = true;
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return resultCode;
	}
	
	public List<DeliveryAddress> getDeliveryAddrList(){
		List<DeliveryAddress> deliveryAddrs = new ArrayList<DeliveryAddress>();
		StringBuilder sb = new StringBuilder();
		sb.append(Constants.BODINGACCOUNT);
		sb.append(GlobalVariables.bodingUser.getCardno());
		String sign = "";
		try {
			sb.append(Encryption.getMD5(Constants.BODINGKEY).toUpperCase());
			sign = Encryption.getMD5(sb.toString()).toUpperCase();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		String urlFormat = "http://api.iboding.com/API/User/Address/QueryAddressInfo.ashx?userid=%s&cardno=%s&sign=%s";
		String urlStr =  String.format(urlFormat,Constants.BODINGACCOUNT,GlobalVariables.bodingUser.getCardno(),sign);
		String result = connectingServer(urlStr);
		try {
			JSONObject resultJson = new JSONObject(result);
			JSONArray jsonArray = resultJson.getJSONArray("data");
			for(int i = 0;i<jsonArray.length();i++){
				JSONObject deliveryAddrJson = jsonArray.getJSONObject(i);
				deliveryAddrs.add(new DeliveryAddress(deliveryAddrJson.getString("Id"),
						deliveryAddrJson.getString("ReceiveName"),deliveryAddrJson.getString("Province"),
						deliveryAddrJson.getString("City"),deliveryAddrJson.getString("Area"),
						deliveryAddrJson.getString("Address"),deliveryAddrJson.getString("ZipCode"),
						deliveryAddrJson.getString("Mobile"),deliveryAddrJson.getString("Phone")));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return deliveryAddrs;
	}

	@Override
	protected Object doInBackground(Object... params) {
		Object result = new Object();
		switch (action) {
			case GET_DELIVERYADDRLIST:
			case GET_DELIVERYADDRLIST_MANAGEMENT:
				result = getDeliveryAddrList();
				break;
			case ADD_DELIVERYADDR:
				result = addDeliveryAddr((DeliveryAddress)params[0]);
				break;
			case EDIT_DELIVERYADDR:
				result = editDeliveryAddr((DeliveryAddress)params[0]);
				break;
			case DELETE_DELIVERYADDR:
				result = deleteDeliveryAddr((String)params[0]);
				break;
			default:
				break;
		}
		return result;
	}
	
	@Override  
	 protected void onPostExecute(Object result) {
		if(isTimeout){
			((BodingBaseActivity)context).handleTimeout();
			return;
		}
		switch (action) {
			case GET_DELIVERYADDRLIST:
				ChooseDeliveryAddressActivity deliveryAddrActivity = (ChooseDeliveryAddressActivity)context;
				deliveryAddrActivity.setAddrList((List<DeliveryAddress>) result);
				break;
			case GET_DELIVERYADDRLIST_MANAGEMENT:
				CommonInfoMDeliverAddrActivity deliveryAddrMActivity = 
					(CommonInfoMDeliverAddrActivity)context;
				deliveryAddrMActivity.setDeliveryAddrList((List<DeliveryAddress>) result);
				break;
			case ADD_DELIVERYADDR:
				AddDeliveryAddrActivity addDeliveryAddrActivity = (AddDeliveryAddrActivity)context;
				addDeliveryAddrActivity.setAddDeliveryAddrResult((Boolean)result);
				break;
			case EDIT_DELIVERYADDR:
				AddDeliveryAddrActivity editDeliveryAddrActivity = (AddDeliveryAddrActivity)context;
				editDeliveryAddrActivity.setEditDeliveryAddrResult((Boolean)result);
				break;
			case DELETE_DELIVERYADDR:
				AddDeliveryAddrActivity deleteDeliveryAddrActivity = (AddDeliveryAddrActivity)context;
				deleteDeliveryAddrActivity.setDeleteDeliveryAddrResult((Boolean)result);
				break;
			default:
				break;
		}
	}
}
