package com.boding.http;

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

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.boding.app.AddPassengerInfoActivity;
import com.boding.app.CommonInfoMPassengerActivity;
import com.boding.app.LoginActivity;
import com.boding.app.TicketSearchResultActivity;
import com.boding.constants.Constants;
import com.boding.constants.GlobalVariables;
import com.boding.constants.HTTPAction;
import com.boding.constants.IdentityType;
import com.boding.model.BodingUser;
import com.boding.model.Country;
import com.boding.model.Passenger;
import com.boding.model.domestic.Airlines;
import com.boding.util.Encryption;
import com.boding.util.Util;

public class HttpConnector extends AsyncTask<Object,Void,Object>{
	private Context context;
	private HTTPAction action;

	private String userName;
	private String password;
	
	public HttpConnector(Context context, HTTPAction action){
		this.context = context;
		this.action = action;
	}
	
	public static BodingUser login(String userName,String password){
		BodingUser bodingUser = null;
		StringBuilder sb = new StringBuilder();
		sb.append(Constants.BODINGACCOUNT);
		sb.append(userName);
		sb.append(password);
		String sign = "";
		try {
			sb.append(Encryption.getMD5(Constants.BODINGKEY).toUpperCase());
			sign = Encryption.getMD5(sb.toString()).toUpperCase();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		String urlFormat = "http://api.iboding.com/API/User/UserLogin.ashx?userid=%s&username=%s&password=%s&sign=%s";
		String urlStr =  String.format(urlFormat,Constants.BODINGACCOUNT,userName,password,sign);
		String result = connectingServer(urlStr);
		try {
			JSONObject resultJson = new JSONObject(result);
			String resultCode = resultJson.getString("result");
			if(resultCode.equals("0")){
				bodingUser = new BodingUser(Boolean.parseBoolean(resultJson.getString("activated_state")), resultJson.getString("mobile"),
						resultJson.getString("cardno"),	resultJson.getString("cardid"), resultJson.getString("name"), 
						resultJson.getString("nickname"),resultJson.getString("pictureimages"), resultJson.getString("login_type"));
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return bodingUser;
	}

	public static String connectingServer(String urlStr){
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
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sbr.toString();
	}

	@Override
	protected Object doInBackground(Object... params) {
		Object result = new Object();
		switch (action) {
			case LOGIN:
			case LAUNCHER_LOGIN:
				userName = (String) params[0];
				password = (String) params[1];
				result = login(userName, password);
				break;
			default:
				break;
		}
		return result;
	}
	
	@Override  
	 protected void onPostExecute(Object result) {
		switch (action) {
			case LOGIN:
				LoginActivity loginActivity = (LoginActivity) context;
				if(result == null)
					loginActivity.loginFailed();
				else{
					Util.successLogin(context, (BodingUser)result, userName, password);
					loginActivity.loginSuccess();
				}
				break;
			case LAUNCHER_LOGIN:
				if (result!=null){
					Util.successLogin(context, (BodingUser)result, userName, password);
				}
				break;
			default:
				break;
		}
	}
}
