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

import com.boding.app.AddPassengerInfoActivity;
import com.boding.app.BodingBaseActivity;
import com.boding.app.ChoosePassengerActivity;
import com.boding.app.CommonInfoMPassengerActivity;
import com.boding.constants.Constants;
import com.boding.constants.GlobalVariables;
import com.boding.constants.HTTPAction;
import com.boding.constants.IdentityType;
import com.boding.model.Passenger;
import com.boding.util.Encryption;

public class PassengerTask extends BodingBaseAsyncTask{
	public PassengerTask(Context context, HTTPAction action){
		super(context, action);
	}
	
	public boolean addPassenger(Passenger passenger){
		boolean resultCode = false;
		StringBuilder sb = new StringBuilder();
		sb.append(Constants.BODINGACCOUNT);
		sb.append(GlobalVariables.bodingUser.getCardno());
		String sign = "";
		
		if(passenger.getIdentityType() == IdentityType.NI){
			sb.append(passenger.getName());
			sb.append(passenger.getIdentityType());
			sb.append(passenger.getCardNumber());
		}else{
			sb.append(passenger.geteName());
			sb.append(passenger.getIdentityType());
			sb.append(passenger.getCardNumber());
			sb.append(passenger.getNationality());
			sb.append(passenger.getGender().getGenderCode());
			sb.append(passenger.getBirthday());
			sb.append(passenger.getValidDate());
		}
		
		try {
			sb.append(Encryption.getMD5(Constants.BODINGKEY).toUpperCase());
			sign = Encryption.getMD5(sb.toString()).toUpperCase();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		String urlStr = "";
		if(passenger.getIdentityType() == IdentityType.NI){
			String urlFormat = "http://api.iboding.com/API/User/Passenger/NewPassengerInfo.ashx?userid=%s&cardno=%s&name=%s&paper_code=%s&paper_num=%s&sign=%s";
			try {
				urlStr =  String.format(urlFormat,Constants.BODINGACCOUNT,GlobalVariables.bodingUser.getCardno(),
						URLEncoder.encode(passenger.getName(),"UTF-8"), passenger.getIdentityType(), passenger.getCardNumber(), sign);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			String urlFormat = "http://api.iboding.com/API/User/Passenger/NewPassengerInfo.ashx?userid=%s&cardno=%s&E_name=%s&paper_code=%s&paper_num=%s&nationality=%s&sex=%s&birthday=%s&paper_valid_date=%s&sign=%s";
			try {
				urlStr =  String.format(urlFormat,Constants.BODINGACCOUNT,GlobalVariables.bodingUser.getCardno(),
						URLEncoder.encode(passenger.geteName(),"UTF-8"), passenger.getIdentityType(), passenger.getCardNumber(),
						URLEncoder.encode(passenger.getNationality(),"UTF-8"),passenger.getGender().getGenderCode(), passenger.getBirthday(),
						passenger.getValidDate(), sign);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
	
	public boolean editPassenger(Passenger passenger){
		boolean resultCode = false;
		StringBuilder sb = new StringBuilder();
		sb.append(Constants.BODINGACCOUNT);
		sb.append(GlobalVariables.bodingUser.getCardno());
		String sign = "";
		
		if(passenger.getIdentityType() == IdentityType.NI){
			sb.append(passenger.getAuto_id());
			sb.append(passenger.getName());
			sb.append(passenger.getIdentityType());
			sb.append(passenger.getCardNumber());
		}else{
			sb.append(passenger.getAuto_id());
			sb.append(passenger.geteName());
			sb.append(passenger.getIdentityType());
			sb.append(passenger.getCardNumber());
			sb.append(passenger.getNationality());
			sb.append(passenger.getGender().getGenderCode());
			sb.append(passenger.getBirthday());
			sb.append(passenger.getValidDate());
		}
		
		try {
			sb.append(Encryption.getMD5(Constants.BODINGKEY).toUpperCase());
			sign = Encryption.getMD5(sb.toString()).toUpperCase();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		String urlStr = "";
		if(passenger.getIdentityType() == IdentityType.NI){
			String urlFormat = "http://api.iboding.com/API/User/Passenger/ModifyPassengerInfo.ashx?userid=%s&cardno=%s&id=%s&name=%s&paper_code=%s&paper_num=%s&sign=%s";
			try {
				urlStr =  String.format(urlFormat,Constants.BODINGACCOUNT,GlobalVariables.bodingUser.getCardno(),
						passenger.getAuto_id(),URLEncoder.encode(passenger.getName(),"UTF-8"), passenger.getIdentityType(),
						passenger.getCardNumber(), sign);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			String urlFormat = "http://api.iboding.com/API/User/Passenger/ModifyPassengerInfo.ashx?userid=%s&cardno=%s&id=%s&E_name=%s&paper_code=%s&paper_num=%s&nationality=%s&sex=%s&birthday=%s&paper_valid_date=%s&sign=%s";
			try {
				urlStr =  String.format(urlFormat,Constants.BODINGACCOUNT,GlobalVariables.bodingUser.getCardno(),
						passenger.getAuto_id(),URLEncoder.encode(passenger.geteName(),"UTF-8"), passenger.getIdentityType(), 
						passenger.getCardNumber(),URLEncoder.encode(passenger.getNationality(),"UTF-8"),
						passenger.getGender().getGenderCode(), passenger.getBirthday(),passenger.getValidDate(), sign);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
	
	public boolean deletePassenger(String passengerID){
		boolean resultCode = false;
		StringBuilder sb = new StringBuilder();
		sb.append(Constants.BODINGACCOUNT);
		sb.append(GlobalVariables.bodingUser.getCardno());
		String sign = "";
		
		sb.append(passengerID);
		
		try {
			sb.append(Encryption.getMD5(Constants.BODINGKEY).toUpperCase());
			sign = Encryption.getMD5(sb.toString()).toUpperCase();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		String urlStr = "";
		String urlFormat = "http://api.iboding.com/API/User/Passenger/DelPassengerInfo.ashx?userid=%s&cardno=%s&id=%s&sign=%s";
		urlStr =  String.format(urlFormat,Constants.BODINGACCOUNT,GlobalVariables.bodingUser.getCardno(),
				passengerID, sign);
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
	
	public List<Passenger> getPassengerList(){
		List<Passenger> passengers = new ArrayList<Passenger>();
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
		
		String urlFormat = "http://api.iboding.com/API/User/Passenger/QueryPassengerInfo.ashx?userid=%s&cardno=%s&sign=%s";
		String urlStr =  String.format(urlFormat,Constants.BODINGACCOUNT,GlobalVariables.bodingUser.getCardno(),sign);
		String result = connectingServer(urlStr);
		try {
			JSONObject resultJson = new JSONObject(result);
			JSONArray jsonArray = resultJson.getJSONArray("data");
			for(int i = 0;i<jsonArray.length();i++){
				JSONObject passengerJSON = jsonArray.getJSONObject(i);
				passengers.add(new Passenger(passengerJSON.getString("auto_id"),
						passengerJSON.getString("cardno"),passengerJSON.getString("name"),
						passengerJSON.getString("E_name"),passengerJSON.getString("birthday"),
						passengerJSON.getString("nationality"),passengerJSON.getString("sex"),
						passengerJSON.getString("PassPaper")));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return passengers;
	}

	@Override
	protected Object doInBackground(Object... params) {
		Object result = new Object();
		switch (action) {
			case GET_PASSENGERLIST:
			case GET_PASSENGERLIST_MANAGEMENT:
				result = getPassengerList();
				break;
			case ADD_PASSENGER:
				result = addPassenger((Passenger)params[0]);
				break;
			case EDIT_PASSENGER:
				result = editPassenger((Passenger)params[0]);
				break;
			case DELETE_PASSENGER:
				result = deletePassenger((String)params[0]);
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
			case GET_PASSENGERLIST:
				ChoosePassengerActivity passengerActivity = (ChoosePassengerActivity)context;
				passengerActivity.setPassengerList((List<Passenger>) result);
				break;
			case GET_PASSENGERLIST_MANAGEMENT:
				CommonInfoMPassengerActivity passengerMActivity = (CommonInfoMPassengerActivity)context;
				passengerMActivity.setPassengerList((List<Passenger>) result);
				break;
			case ADD_PASSENGER:
				AddPassengerInfoActivity addPassengerActivity = (AddPassengerInfoActivity)context;
				addPassengerActivity.setAddPassengerResult((Boolean)result);
				break;
			case EDIT_PASSENGER:
				AddPassengerInfoActivity editPassengerActivity = (AddPassengerInfoActivity)context;
				editPassengerActivity.setEditPassengerResult((Boolean)result);
				break;
			case DELETE_PASSENGER:
				AddPassengerInfoActivity deletePassengerActivity = (AddPassengerInfoActivity)context;
				deletePassengerActivity.setDeletePassengerResult((Boolean)result);
				break;
			default:
				break;
		}
	}
}
