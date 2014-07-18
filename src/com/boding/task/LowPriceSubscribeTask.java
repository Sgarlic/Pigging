package com.boding.task;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.boding.app.LowPriceSubscribeActivity;
import com.boding.constants.Constants;
import com.boding.constants.GlobalVariables;
import com.boding.constants.HTTPAction;
import com.boding.model.LowPriceSubscribe;
import com.boding.util.Encryption;

import android.content.Context;
import android.os.AsyncTask;

public class LowPriceSubscribeTask extends AsyncTask<Object,Void,Object> {
	private Context context;
	private HTTPAction action;
	
	public LowPriceSubscribeTask(Context context, HTTPAction action){
		this.context = context;
		this.action = action;
	}
	
	public boolean deleteLowPriceSub(String subId){
		boolean resultCode = false;
		StringBuilder sb = new StringBuilder();
		sb.append(Constants.BODINGACCOUNT);
		sb.append(GlobalVariables.bodingUser.getCardno());
		sb.append(subId);
		
		String sign = "";
		try {
			sb.append(Encryption.getMD5(Constants.BODINGKEY).toUpperCase());
			sign = Encryption.getMD5(sb.toString()).toUpperCase();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		String urlFormat = "http://api.iboding.com/API/User/Subscribe/NewSubscribe.ashx?userid=%s&cardno=%s&id=%s&sign=%s";
		String urlStr =  String.format(urlFormat,Constants.BODINGACCOUNT,GlobalVariables.bodingUser.getCardno(),
				subId,sign);
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
	
	public boolean addLowPriceSub(LowPriceSubscribe subscribe){
		boolean resultCode = false;
		StringBuilder sb = new StringBuilder();
		sb.append(Constants.BODINGACCOUNT);
		sb.append(GlobalVariables.bodingUser.getCardno());
		sb.append(subscribe.getTicketType());
		sb.append(subscribe.getTripType());
		sb.append(subscribe.getLeaveCode());
		sb.append(subscribe.getLeaveName());
		sb.append(subscribe.getArriveCode());
		sb.append(subscribe.getArriveName());
		sb.append(subscribe.getFlightBeginDate());
		sb.append(subscribe.getFlightEndDate());
		sb.append(subscribe.getSubscribeWay());
		sb.append(subscribe.getDisCount());
		sb.append(subscribe.getPrice());
		sb.append(subscribe.getNoticeWay());
		sb.append(subscribe.getEmail());
		sb.append(subscribe.getMobile());
		sb.append(subscribe.getBeforeAfterDay());
		
		String sign = "";
		try {
			sb.append(Encryption.getMD5(Constants.BODINGKEY).toUpperCase());
			sign = Encryption.getMD5(sb.toString()).toUpperCase();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		String urlFormat = "http://api.iboding.com/API/User/Subscribe/NewSubscribe.ashx?userid=%s&CardNo=%s&TicketType=%s&TripType=%s&LeaveCode=%s&LeaveName=%s&ArriveCode=%s&ArriveName=%s&FlightBeginDate=%s&FlightEndDate=%s&SubscribeWay=%s&DisCount=%s&Price=%s&NoticeWay=%s&Email=%s&Mobile=%s&BeforeAfterDay=%s&sign=%s";
		String urlStr =  String.format(urlFormat,Constants.BODINGACCOUNT,GlobalVariables.bodingUser.getCardno(),
				subscribe.getTicketType(),subscribe.getTripType(),subscribe.getLeaveCode(),subscribe.getLeaveName(),
				subscribe.getArriveCode(),subscribe.getArriveName(),subscribe.getFlightBeginDate(),
				subscribe.getFlightEndDate(),subscribe.getSubscribeWay(),subscribe.getDisCount(),
				subscribe.getPrice(),subscribe.getNoticeWay(),subscribe.getEmail(),subscribe.getMobile(),
				subscribe.getBeforeAfterDay(),sign);
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
	
	public List<LowPriceSubscribe> getLowPriceSubsList(String pageSize, String page){
		List<LowPriceSubscribe> subsList = new ArrayList<LowPriceSubscribe>();
		StringBuilder sb = new StringBuilder();
		sb.append(Constants.BODINGACCOUNT);
		sb.append(GlobalVariables.bodingUser.getCardno());
		sb.append(pageSize);
		sb.append(page);
		String sign = "";
		try {
			sb.append(Encryption.getMD5(Constants.BODINGKEY).toUpperCase());
			sign = Encryption.getMD5(sb.toString()).toUpperCase();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		String urlFormat = "http://api.iboding.com/API/User/Subscribe/QuerySubscribe.ashx?userid=%s&cardno=%s&pageSize=%s&page=%s&sign=%s";
		String urlStr =  String.format(urlFormat,Constants.BODINGACCOUNT,GlobalVariables.bodingUser.getCardno(),
				pageSize,page,sign);
		String result = connectingServer(urlStr);
		try {
			JSONObject resultJson = new JSONObject(result);
			JSONArray jsonArray = resultJson.getJSONArray("data");
			for(int i = 0;i<jsonArray.length();i++){
				JSONObject subJson = jsonArray.getJSONObject(i);
				LowPriceSubscribe subscribe = new LowPriceSubscribe();
				subscribe.setId(subJson.getString("Id"));
				subscribe.setCardNo(subJson.getString("CardNo"));
				subscribe.setTicketType(subJson.getInt("TicketType"));
				subscribe.setTripType(subJson.getInt("TripType"));
				subscribe.setLeaveCode(subJson.getString("LeaveCode"));
				subscribe.setLeaveName(subJson.getString("LeaveName"));
				subscribe.setArriveCode(subJson.getString("ArriveCode"));
				subscribe.setArriveName(subJson.getString("ArriveName"));
				subscribe.setFlightBeginDate(subJson.getString("FlightBeginDate"));
				subscribe.setFlightEndDate(subJson.getString("FlightEndDate"));
				subscribe.setSubscribeWay(subJson.getInt("SubscribeWay"));
				subscribe.setDisCount(subJson.getInt("DisCount"));
				subscribe.setPrice(subJson.getDouble("Price"));
				subscribe.setNoticeWay(subJson.getInt("NoticeWay"));
				subscribe.setEmail(subJson.getString("Email"));
				subscribe.setMobile(subJson.getString("Mobile"));
				subscribe.setStatus(subJson.getInt("Status"));
				if(subJson.getString("BeforeAfterDay").equals("False"))
					subscribe.setBeforeAfterDay(false);
				else
					subscribe.setBeforeAfterDay(true);
				subscribe.setDoDateTime(subJson.getString("DoDateTime"));
				
				subsList.add(subscribe);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return subsList;
	}
	
	public String connectingServer(String urlStr){
		System.out.println(urlStr);
		StringBuilder sbr = new StringBuilder();
		URL url;
		try {
			url = new URL(urlStr);
			HttpURLConnection httpc = (HttpURLConnection)url.openConnection();
			httpc.connect();
			
			InputStream is = httpc.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			String lines;
			while((lines = reader.readLine()) != null){
				sbr.append(lines);
			}
			System.out.println(sbr.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sbr.toString();
	}
	
	@Override
	protected Object doInBackground(Object... params) {
		Object result = new Object();
		switch (action) {
			case GET_LOWPRICESUBS_LIST:
				result = getLowPriceSubsList("10", "1");
				break;
			case ADD_LOWPRICESUB:
				result = addLowPriceSub((LowPriceSubscribe) params[0]);
				break;
			case DELETE_LOWPRICESUB:
				result = deleteLowPriceSub((String) params[0]);
				break;
			default:
				break;
		}
		return result;
	}
	@Override  
	protected void onPostExecute(Object result) {
		switch (action) {
			case GET_LOWPRICESUBS_LIST:
				LowPriceSubscribeActivity lowPriceSubscribeActivity = (LowPriceSubscribeActivity)context;
				lowPriceSubscribeActivity.setLowPriceResultList((List<LowPriceSubscribe>) result);
				break;
			default:
				break;
		}
	}

}
