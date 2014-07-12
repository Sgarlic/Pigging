package com.boding.task;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;

import com.boding.app.EditPersonalInfoActivity;
import com.boding.app.LoginActivity;
import com.boding.app.MyPersonalInfoActivity;
import com.boding.app.RegisterActivity;
import com.boding.constants.Constants;
import com.boding.constants.GlobalVariables;
import com.boding.constants.HTTPAction;
import com.boding.model.BodingUser;
import com.boding.util.Encryption;
import com.boding.util.SharedPreferenceUtil;

public class BodingUserTask extends AsyncTask<Object,Void,Object> {
	private Context context;
	private HTTPAction action;

	private String userName;
	private String password;
	public BodingUserTask(Context context, HTTPAction action){
		this.context = context;
		this.action = action;
	}
	
	//短信类型 1、手机注册 2、账户激活 3、绑定手机或修改手机 4、密码找回
	public String verifyPhoneNumber(String mobile,String type){
		String verifyCode = "";
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String sourceFlag = "android";
//		StringBuilder sb = new StringBuilder();
//		sb.append(Constants.BODINGACCOUNT);
//		sb.append(mobile);
//		sb.append(password);
//		sb.append("android");
//		String sign = "";
//		try {
//			sb.append(Encryption.getMD5(Constants.BODINGKEY).toUpperCase());
//			sign = Encryption.getMD5(sb.toString()).toUpperCase();
//		} catch (NoSuchAlgorithmException e) {
//			e.printStackTrace();
//		}
//		String urlFormat = "http://api.iboding.com/API/User/UserRegister.ashx?userid=%s&mobile=%s&password=%s&source_flag=android&sign=%s";
//		String urlStr =  String.format(urlFormat,Constants.BODINGACCOUNT,mobile,password,sign);
//		
//		String result = connectingServer(urlStr);
//		try {
//			JSONObject resultJson = new JSONObject(result);
//			if(resultJson.getString("result").equals("0")){
//				resultCode = true;
//			}
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
		
		return verifyCode;
	}
	
	public boolean register(String mobile, String password){
		String cardNo = "dddd";
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		StringBuilder sb = new StringBuilder();
//		sb.append(Constants.BODINGACCOUNT);
//		sb.append(mobile);
//		sb.append(password);
//		sb.append("android");
//		String sign = "";
//		try {
//			sb.append(Encryption.getMD5(Constants.BODINGKEY).toUpperCase());
//			sign = Encryption.getMD5(sb.toString()).toUpperCase();
//		} catch (NoSuchAlgorithmException e) {
//			e.printStackTrace();
//		}
//		String urlFormat = "http://api.iboding.com/API/User/UserRegister.ashx?userid=%s&mobile=%s&password=%s&source_flag=android&sign=%s";
//		String urlStr =  String.format(urlFormat,Constants.BODINGACCOUNT,mobile,password,sign);
//		
//		String result = connectingServer(urlStr);
//		try {
//			JSONObject resultJson = new JSONObject(result);
//			if(resultJson.getString("result").equals("0")){
//				resultCode = true;
//			}
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
		GlobalVariables.bodingUser = new BodingUser();
		GlobalVariables.bodingUser.setMobile(mobile);
		return true;
	}
	
	public boolean editPersonalInfo(){
		boolean resultCode = false;
		StringBuilder sb = new StringBuilder();
		sb.append(Constants.BODINGACCOUNT);
		sb.append(GlobalVariables.bodingUser.getCardno());
		String sign = "";
		sb.append(GlobalVariables.bodingUser.getName());
		sb.append(GlobalVariables.bodingUser.getNickname());
		sb.append(GlobalVariables.bodingUser.getGenderCode());
		sb.append(GlobalVariables.bodingUser.getBirthdayInfo());
//		sb.append(GlobalVariables.bodingUser.getMobile());
		
		try {
			sb.append(Encryption.getMD5(Constants.BODINGKEY).toUpperCase());
			sign = Encryption.getMD5(sb.toString()).toUpperCase();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		String urlStr =  "";
		String urlFormat = "http://api.iboding.com/API/User/MyInfo/UserInfoEdit.ashx?userid=%s&cardno=%s&name=%s&nickname=%s&sex=%s&birthday=%s&sign=%s";
		try {
			urlStr =  String.format(urlFormat,Constants.BODINGACCOUNT,
					GlobalVariables.bodingUser.getCardno(),
					URLEncoder.encode(GlobalVariables.bodingUser.getName(),"UTF-8"),
					URLEncoder.encode(GlobalVariables.bodingUser.getNickname(),"UTF-8"),
					GlobalVariables.bodingUser.getGenderCode(),
					GlobalVariables.bodingUser.getBirthdayInfo(),sign);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String result = connectingServer(urlStr);
		try {
			JSONObject resultJson = new JSONObject(result);
			if(resultJson.getString("result").equals("0"))
				resultCode = true;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return resultCode;
	}
	
	public void getPersonalInfo(){
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
		String urlFormat = "http://api.iboding.com/API/User/MyInfo/QueryUserInfo.ashx?userid=%s&cardno=%s&sign=%s";
		String urlStr =  String.format(urlFormat,Constants.BODINGACCOUNT,
			GlobalVariables.bodingUser.getCardno(),sign);
		String result = connectingServer(urlStr);
		try {
			JSONObject resultJson = new JSONObject(result);
			JSONArray resultArray = resultJson.getJSONArray("data");
			JSONObject userObject = resultArray.getJSONObject(0);
			GlobalVariables.bodingUser.setCardid(userObject.getString("idno"));
			GlobalVariables.bodingUser.setName(userObject.getString("name"));
			GlobalVariables.bodingUser.setGender(userObject.getString("sex"));
			GlobalVariables.bodingUser.setBirthdayInfo(userObject.getString("birthday"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public BodingUser login(String userName,String password){
		this.userName = userName;
		this.password = password;
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
			case LOGIN:
			case LAUNCHER_LOGIN:
				result = login((String) params[0], (String) params[1]);
				break;
			case GET_PERSONAL_INFO:
				getPersonalInfo();
				break;
			case EDIT_PERSONAL_INFO:
				result = editPersonalInfo();
				break;
			case REGISTER:
				result = register((String) params[0], (String) params[1]);
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
					SharedPreferenceUtil.successLogin(context, (BodingUser)result, userName, password);
					loginActivity.loginSuccess();
				}
				break;
			case LAUNCHER_LOGIN:
				if (result!=null){
					SharedPreferenceUtil.successLogin(context, (BodingUser)result, userName, password);
				}
				break;
			case GET_PERSONAL_INFO:
				MyPersonalInfoActivity myPersonalInfoActivity = (MyPersonalInfoActivity) context;
				myPersonalInfoActivity.setPersonalInfo();
				break;
			case EDIT_PERSONAL_INFO:
				EditPersonalInfoActivity editPersonalInfoActivity = (EditPersonalInfoActivity) context;
				editPersonalInfoActivity.editPersonalInfoResult((Boolean)result);
				break;
			case REGISTER:
				boolean registerResult = (Boolean)result;
				RegisterActivity registerActivity = (RegisterActivity)context;
				registerActivity.registerResult(registerResult);
				break;
			default:
				break;
		}
	}
}
