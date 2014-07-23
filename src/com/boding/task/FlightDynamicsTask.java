package com.boding.task;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.boding.app.FlightBoardActivity;
import com.boding.app.FlightDynamicsListActivity;
import com.boding.app.MainActivity;
import com.boding.constants.Constants;
import com.boding.constants.GlobalVariables;
import com.boding.constants.HTTPAction;
import com.boding.model.FlightDynamics;
import com.boding.util.Encryption;

import android.content.Context;
import android.os.AsyncTask;

public class FlightDynamicsTask extends AsyncTask<Object,Void,Object> {
	private Context context;
	private HTTPAction action;
	
	public FlightDynamicsTask(Context context, HTTPAction action){
		this.context = context;
		this.action = action;
	}
	
	public List<FlightDynamics> searchDynamics(String dpt, String arr,
			String flightNo, String date){
		String source_flag = "android";
		List<FlightDynamics> flightDynamicsList = new ArrayList<FlightDynamics>();
		
		StringBuilder sb = new StringBuilder();
		sb.append(Constants.BODINGACCOUNT);
		sb.append(dpt);
		sb.append(arr);
		sb.append(flightNo);
		sb.append(date);
		sb.append(source_flag);
		String sign = "";
		try {
			sb.append(Encryption.getMD5(Constants.BODINGKEY).toUpperCase());
			sign = Encryption.getMD5(sb.toString()).toUpperCase();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		String urlFormat = "http://user.iboarding.cn/API/DataInterface/FlightDynamic.ashx?userid=%s&dpt=%s&arr=%s&flightNo=%s&date=%s&source_flag=%s&sign=%s";
		String urlStr =  String.format(urlFormat,Constants.BODINGACCOUNT,
			dpt,arr,flightNo,date,source_flag,sign);
		String result = connectingServer(urlStr);
		try {
			JSONObject resultJson = new JSONObject(result);
			if (resultJson.getString("result").equals("0")){
				String dateS = resultJson.getString("date");
				JSONArray jsonArray = resultJson.getJSONArray("data");
				for(int i = 0;i<jsonArray.length();i++){
					JSONObject dynJson = jsonArray.getJSONObject(i);
					FlightDynamics flightDynamics = new FlightDynamics();
					flightDynamics.setDate(dateS);
					flightDynamics.setCarrier(dynJson.getString("carrier"));
					flightDynamics.setNum(dynJson.getString("num"));
					flightDynamics.setCar_name(dynJson.getString("car_name"));
					flightDynamics.setPlan_dep_time(dynJson.getString("plan_dep_time"));
					flightDynamics.setActual_dep_time(dynJson.getString("actual_dep_time"));
					flightDynamics.setDep_airport_code(dynJson.getString("dep_airport_code"));
					flightDynamics.setArr_airport_code(dynJson.getString("arr_airport_code"));
					flightDynamics.setDep_airport_name(dynJson.getString("dep_airport_name"));
					flightDynamics.setArr_airport_name(dynJson.getString("arr_airport_name"));
					flightDynamics.setDep_terminal(dynJson.getString("dep_terminal"));
					flightDynamics.setArr_terminal(dynJson.getString("arr_terminal"));
					flightDynamics.setPlan_arr_time(dynJson.getString("plan_arr_time"));
					flightDynamics.setActual_arr_time(dynJson.getString("actual_arr_time"));
					flightDynamics.setFlightStatusByCode(dynJson.getString("status"));
				}			
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return flightDynamicsList;
	}
	
	public List<FlightDynamics> getMyFollowedDynamics(){
		GlobalVariables.myFollowedFdList.clear();
		
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
		
		String urlFormat = "http://api.iboding.com/API/User/AttentionFlight/QueryAttentionFlight.ashx?userid=%s&cardno=%s&sign=%s";
		String urlStr =  String.format(urlFormat,Constants.BODINGACCOUNT,
			GlobalVariables.bodingUser.getCardno(),sign);
		String result = connectingServer(urlStr);
		try {
			JSONObject resultJson = new JSONObject(result);
			JSONArray jsonArray = resultJson.getJSONArray("data");
			for(int i = 0;i<jsonArray.length();i++){
				JSONObject dynJson = jsonArray.getJSONObject(i);
				FlightDynamics flightDynamics = new FlightDynamics();
				flightDynamics.setId(dynJson.getString("id"));
				flightDynamics.setDate(dynJson.getString("date"));
				flightDynamics.setCarrier(dynJson.getString("carrier"));
				flightDynamics.setNum(dynJson.getString("num"));
				flightDynamics.setCar_name(dynJson.getString("car_name"));
				flightDynamics.setPlan_dep_time(dynJson.getString("plan_dep_time"));
				flightDynamics.setExpect_dep_time(dynJson.getString("expect_dep_time"));
				flightDynamics.setActual_dep_time(dynJson.getString("actual_dep_time"));
				flightDynamics.setDep_airport_code(dynJson.getString("dep_airport_code"));
				flightDynamics.setArr_airport_code(dynJson.getString("arr_airport_code"));
				flightDynamics.setDep_airport_name(dynJson.getString("dep_airport_name"));
				flightDynamics.setArr_airport_name(dynJson.getString("arr_airport_name"));
				flightDynamics.setDep_terminal(dynJson.getString("dep_terminal"));
				flightDynamics.setArr_terminal(dynJson.getString("arr_terminal"));
				flightDynamics.setPlan_arr_time(dynJson.getString("plan_arr_time"));
				flightDynamics.setExpect_arr_time(dynJson.getString("expect_arr_time"));
				flightDynamics.setActual_arr_time(dynJson.getString("actual_arr_time"));
				flightDynamics.setFlightStatusByCode(dynJson.getString("status"));
				flightDynamics.setFollowed(true);
				GlobalVariables.myFollowedFdList.add(flightDynamics);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return GlobalVariables.myFollowedFdList;
	}
	
	public boolean unFollowFlightDynamic(String dynamicID){
		boolean resultCode = false;
		StringBuilder sb = new StringBuilder();
		sb.append(Constants.BODINGACCOUNT);
		sb.append(GlobalVariables.bodingUser.getCardno());
		sb.append(dynamicID);
		String sign = "";
		try {
			sb.append(Encryption.getMD5(Constants.BODINGKEY).toUpperCase());
			sign = Encryption.getMD5(sb.toString()).toUpperCase();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		String urlFormat = "http://api.iboding.com/API/User/AttentionFlight/DelAttentionFlight.ashx?userid=%s&cardno=%s&id=%s&sign=%s";
		String urlStr =  String.format(urlFormat,Constants.BODINGACCOUNT,
			GlobalVariables.bodingUser.getCardno(),dynamicID,sign);
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
	
	public boolean followFlightDynamic(FlightDynamics flightDynamics){
		boolean resultCode = false;
		StringBuilder sb = new StringBuilder();
		sb.append(Constants.BODINGACCOUNT);
		sb.append(GlobalVariables.bodingUser.getCardno());
		sb.append(flightDynamics.getDate());
		sb.append(flightDynamics.getCarrier());
		sb.append(flightDynamics.getNum());
		sb.append(flightDynamics.getCar_name());
		sb.append(flightDynamics.getPlan_dep_time());
		sb.append(flightDynamics.getExpect_dep_time());
		sb.append(flightDynamics.getActual_dep_time());
		sb.append(flightDynamics.getDep_airport_code());
		sb.append(flightDynamics.getArr_airport_code());
		sb.append(flightDynamics.getDep_airport_name());
		sb.append(flightDynamics.getArr_airport_name());
		sb.append(flightDynamics.getDep_terminal());
		sb.append(flightDynamics.getArr_terminal());
		sb.append(flightDynamics.getPlan_arr_time());
		sb.append(flightDynamics.getExpect_arr_time());
		sb.append(flightDynamics.getActual_arr_time());
		sb.append(flightDynamics.getFlightStatus().getFlightStatusCode());
		sb.append(flightDynamics.getPunctuality());
		sb.append(flightDynamics.getDep_weatherCodeString());
		sb.append(flightDynamics.getDep_temperature());
		sb.append(flightDynamics.getArr_weatherCodeString());
		sb.append(flightDynamics.getArr_temperature());
		String sign = "";
		try {
			sb.append(Encryption.getMD5(Constants.BODINGKEY).toUpperCase());
			sign = Encryption.getMD5(sb.toString()).toUpperCase();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		String urlStr = "";
		String urlFormat = "http://api.iboding.com/API/User/AttentionFlight/NewAttentionFlight.ashx?userid=%s&cardno=%s&date=%s&carrier=%s&num=%s&car_name=%s&plan_dep_time=%s&expect_dep_time=%s&actual_dep_time=%s&dep_airport_code=%s&arr_airport_code=%s&dep_airport_name=%s&arr_airport_name=%s&dep_terminal=%s&arr_terminal=%s&plan_arr_time=%s&expect_arr_time=%s&actual_arr_time=%s&status=%s&punctuality=%s&dep_weather=%s&dep_temperature=%s&arr_weather=%s&arr_temperature=%s&sign=%s";
		try {
			urlStr =  String.format(urlFormat,Constants.BODINGACCOUNT,
				GlobalVariables.bodingUser.getCardno(),flightDynamics.getDate(),
				flightDynamics.getCarrier(),flightDynamics.getNum(),
				URLEncoder.encode(flightDynamics.getCar_name(),"UTF-8"),
				flightDynamics.getPlan_dep_time(),flightDynamics.getExpect_dep_time(),
				flightDynamics.getActual_dep_time(),flightDynamics.getDep_airport_code(),
				flightDynamics.getArr_airport_code(),
				URLEncoder.encode(flightDynamics.getDep_airport_name(),"UTF-8"),
				URLEncoder.encode(flightDynamics.getArr_airport_name(),"UTF-8"),
				flightDynamics.getDep_terminal(),flightDynamics.getArr_terminal(),
				flightDynamics.getPlan_arr_time(),flightDynamics.getExpect_arr_time(),
				flightDynamics.getActual_arr_time(),
				flightDynamics.getFlightStatus().getFlightStatusCode(),
				flightDynamics.getPunctuality(),flightDynamics.getDep_weatherCodeString(),
				flightDynamics.getDep_temperature(),flightDynamics.getArr_weatherCodeString(),
				flightDynamics.getArr_temperature(),sign);
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
			case GET_MYFOLLOWED_FROM_MAIN:
			case GET_MYFOLLOWED:
				result = getMyFollowedDynamics();
				break;
			case FOLLOW_FLIGHTDYNAMICS:
				result = followFlightDynamic((FlightDynamics) params[0]);
				getMyFollowedDynamics();
				break;
			case UNFOLLOW_FLIGHTDYNAMICS_FROM_MAIN:
			case UNFOLLOW_FLIGHTDYNAMICS:
				result = unFollowFlightDynamic((String) params[0]);
				break;
			case SEARCH_FLIGHTDYNAMICS:
				result = searchDynamics((String)params[0], (String)params[1],
					(String)params[2], (String)params[3]);
				break;
			default:
				break;
		}
		return result;
	}
	@Override  
	protected void onPostExecute(Object result) {
		switch (action) {
			case GET_MYFOLLOWED_FROM_MAIN:
				MainActivity mainActivity = (MainActivity) context;
				mainActivity.setMyFollowsFlightList((List<FlightDynamics>) result);
				break;
			case GET_MYFOLLOWED:
				FlightDynamicsListActivity fListActivity = (FlightDynamicsListActivity) context;
				fListActivity.setMyFollowedFlightDynamicsList((List<FlightDynamics>) result);
				break;
			case FOLLOW_FLIGHTDYNAMICS:
				FlightBoardActivity flightBoardActivity = (FlightBoardActivity) context;
				flightBoardActivity.setFollowFlightResult((Boolean) result);
				break;
			case UNFOLLOW_FLIGHTDYNAMICS_FROM_MAIN:
				mainActivity = (MainActivity) context;
				mainActivity.setUnFollowFlightResult((Boolean) result);
				break;
			case UNFOLLOW_FLIGHTDYNAMICS:
				flightBoardActivity = (FlightBoardActivity) context;
				flightBoardActivity.setUnFollowFlightResult((Boolean) result);
				break;
			case SEARCH_FLIGHTDYNAMICS:
				fListActivity = (FlightDynamicsListActivity) context;
				fListActivity.setSearchedFlightDynamicsList((List<FlightDynamics>) result);
				break;
			default:
				break;
		}
	}

}
