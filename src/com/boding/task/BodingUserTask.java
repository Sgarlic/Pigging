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

import com.boding.app.ChangePasswordActivity;
import com.boding.app.ChangePhonenumActivity;
import com.boding.app.EditPersonalInfoActivity;
import com.boding.app.LoginActivity;
import com.boding.app.MyPersonalInfoActivity;
import com.boding.app.RegisterActivity;
import com.boding.app.SetNewPasswordActivity;
import com.boding.app.VerifyPhonenumActivity;
import com.boding.constants.Constants;
import com.boding.constants.GlobalVariables;
import com.boding.constants.HTTPAction;
import com.boding.model.BodingUser;
import com.boding.util.Encryption;
import com.boding.util.SharedPreferenceUtil;

public class BodingUserTask extends AsyncTask<Object,Void,Object> {
	private Context context;
	private HTTPAction action;

	private String password;
	private String verifyPhoneNumType = "1";
	public BodingUserTask(Context context, HTTPAction action){
		this.context = context;
		this.action = action;
	}
	
	public boolean setNewPassword(String cardNo, String newPwd){
		boolean resultCode = false;
		StringBuilder sb = new StringBuilder();
		sb.append(Constants.BODINGACCOUNT);
		sb.append(cardNo);
		sb.append(newPwd);
		String sign = "";
		try {
			sb.append(Encryption.getMD5(Constants.BODINGKEY).toUpperCase());
			sign = Encryption.getMD5(sb.toString()).toUpperCase();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		String urlFormat = "http://api.iboding.com/API/User/MyInfo/SetPwd.ashx?userid=%s&cardno=%s&new_pwd=%s&sign=%s";
		String urlStr =  String.format(urlFormat,Constants.BODINGACCOUNT,
				cardNo,newPwd,sign);
		
		String result = connectingServer(urlStr);
		try {
			JSONObject resultJson = new JSONObject(result);
			String jsonResultCode = resultJson.getString("result"); 
			if(jsonResultCode.equals("0")){
				resultCode = true;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return resultCode;
	}
	
	public String getCardNoFromMobile(String mobile){
		String cardNo = "";
		StringBuilder sb = new StringBuilder();
		sb.append(Constants.BODINGACCOUNT);
		sb.append(mobile);
		String sign = "";
		try {
			sb.append(Encryption.getMD5(Constants.BODINGKEY).toUpperCase());
			sign = Encryption.getMD5(sb.toString()).toUpperCase();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		String urlFormat = "http://api.iboding.com/API/User/CheckExistMobile.ashx?userid=%s&mobile=%s&sign=%s";
		String urlStr =  String.format(urlFormat,Constants.BODINGACCOUNT,
			mobile,sign);
		
		String result = connectingServer(urlStr);
		try {
			JSONObject resultJson = new JSONObject(result);
			if(resultJson.getString("result").equals("0")){
				cardNo = resultJson.getString("cardno");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return cardNo;
	}
	
	public boolean verifyOldMoblie(String oldMobile){
		boolean resultCode = false;
		StringBuilder sb = new StringBuilder();
		sb.append(Constants.BODINGACCOUNT);
		sb.append(GlobalVariables.bodingUser.getCardno());
		sb.append(oldMobile);
		String sign = "";
		try {
			sb.append(Encryption.getMD5(Constants.BODINGKEY).toUpperCase());
			sign = Encryption.getMD5(sb.toString()).toUpperCase();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		String urlFormat = "http://api.iboding.com/API/User/CheckOldMobile.ashx?userid=%s&cardno=%s&oldmobile=%s&sign=%s";
		String urlStr =  String.format(urlFormat,Constants.BODINGACCOUNT,
			GlobalVariables.bodingUser.getCardno(),oldMobile,sign);
		
		String result = connectingServer(urlStr);
		try {
			JSONObject resultJson = new JSONObject(result);
			String jsonResultCode = resultJson.getString("result"); 
			if(jsonResultCode.equals("0")){
				resultCode = true;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return resultCode;
	}
	
	/**
	 * return true, change success
	 * return false, 密码不正确
	 * @param curPwd
	 * @param newPwd
	 * @return
	 */
	public String changePassword(String curPwd, String newPwd){
		String resultCode = "";
		StringBuilder sb = new StringBuilder();
		sb.append(Constants.BODINGACCOUNT);
		sb.append(GlobalVariables.bodingUser.getCardno());
		sb.append(curPwd);
		sb.append(newPwd);
		String sign = "";
		try {
			sb.append(Encryption.getMD5(Constants.BODINGKEY).toUpperCase());
			sign = Encryption.getMD5(sb.toString()).toUpperCase();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		String urlFormat = "http://api.iboding.com/API/User/MyInfo/ModifyPwd.ashx?userid=%s&cardno=%s&cur_pwd=%s&new_pwd=%s&sign=%s";
		String urlStr =  String.format(urlFormat,Constants.BODINGACCOUNT,
			GlobalVariables.bodingUser.getCardno(),curPwd,newPwd,sign);
		
		String result = connectingServer(urlStr);
		try {
			JSONObject resultJson = new JSONObject(result);
			resultCode = resultJson.getString("result"); 
			if(resultCode.equals("0")){
				// 如果修改密码成功，要更新sharedperference里面的密码
				SharedPreferenceUtil.successLogin(context, newPwd);
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return resultCode;
	}
	
	public boolean phoneNumBinding(String mobile){
		boolean resultCode = false;
		StringBuilder sb = new StringBuilder();
		sb.append(Constants.BODINGACCOUNT);
		sb.append(GlobalVariables.bodingUser.getCardno());
		sb.append(mobile);
		String sign = "";
		try {
			sb.append(Encryption.getMD5(Constants.BODINGKEY).toUpperCase());
			sign = Encryption.getMD5(sb.toString()).toUpperCase();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		String urlFormat = "http://api.iboding.com/API/User/BindMobile.ashx?userid=%s&cardno=%s&mobile=%s&sign=%s";
		String urlStr =  String.format(urlFormat,Constants.BODINGACCOUNT,
			GlobalVariables.bodingUser.getCardno(),mobile,sign);
		
		String result = connectingServer(urlStr);
		try {
			JSONObject resultJson = new JSONObject(result);
			if(resultJson.getString("result").equals("0")){
				resultCode = true;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return resultCode;
	}
	
	public boolean activiteUser(){
		boolean resultCode = false;
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
		String urlFormat = "http://api.iboding.com/API/User/UserActivation.ashx?userid=%s&cardno=%s&sign=%s";
		String urlStr =  String.format(urlFormat,Constants.BODINGACCOUNT,
			GlobalVariables.bodingUser.getCardno(),sign);
		
		String result = connectingServer(urlStr);
		try {
			JSONObject resultJson = new JSONObject(result);
			if(resultJson.getString("result").equals("0")){
				resultCode = true;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return resultCode;
	}
	
	//短信类型 1、手机注册 2、账户激活 3、绑定手机或修改手机 4、密码找回
	public String verifyPhoneNumber(String mobile, String type){
		verifyPhoneNumType = type;
		String verifyCode = "";
		String sourceFlag = "android";
		StringBuilder sb = new StringBuilder();
		sb.append(Constants.BODINGACCOUNT);
		sb.append(mobile);
		sb.append(sourceFlag);
		sb.append(type);
		String sign = "";
		try {
			sb.append(Encryption.getMD5(Constants.BODINGKEY).toUpperCase());
			sign = Encryption.getMD5(sb.toString()).toUpperCase();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		String urlFormat = "http://api.iboding.com/API/User/SMSCode.ashx?userid=%s&mobile=%s&source_flag=%s&type=%s&sign=%s";
		String urlStr =  String.format(urlFormat,Constants.BODINGACCOUNT,
				mobile,sourceFlag,type,sign);
		
		String result = connectingServer(urlStr);
		try {
			JSONObject resultJson = new JSONObject(result);
			if(resultJson.getString("result").equals("0")){
				verifyCode = resultJson.getString("code");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return verifyCode;
	}
	
	public boolean register(String mobile, String password){
		boolean resultCode = false;
		StringBuilder sb = new StringBuilder();
		sb.append(Constants.BODINGACCOUNT);
		sb.append(mobile);
		sb.append(password);
		sb.append("android");
		String sign = "";
		try {
			sb.append(Encryption.getMD5(Constants.BODINGKEY).toUpperCase());
			sign = Encryption.getMD5(sb.toString()).toUpperCase();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		String urlFormat = "http://api.iboding.com/API/User/UserRegister.ashx?userid=%s&mobile=%s&password=%s&source_flag=android&sign=%s";
		String urlStr =  String.format(urlFormat,Constants.BODINGACCOUNT,mobile,password,sign);
		
		String result = connectingServer(urlStr);
		try {
			JSONObject resultJson = new JSONObject(result);
			if(resultJson.getString("result").equals("0")){
				resultCode = true;
				GlobalVariables.bodingUser = new BodingUser();
				GlobalVariables.bodingUser.setMobile(mobile);
				GlobalVariables.bodingUser.setCardno(resultJson.getString("cardno"));
				SharedPreferenceUtil.successLogin(context, password);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return resultCode;
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
	
	public boolean login(String userName,String password){
		boolean isSuccess = false;
		this.password = password;
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
				isSuccess = true;
				boolean activatedStatus = false;
				if(resultJson.getString("activated_state").equals("1"))
					activatedStatus = true;
				if(activatedStatus){
					GlobalVariables.bodingUser = new BodingUser(activatedStatus, resultJson.getString("mobile"),
						resultJson.getString("cardno"),	resultJson.getString("cardid"), resultJson.getString("name"), 
						resultJson.getString("nickname"),resultJson.getString("pictureimages"), resultJson.getString("login_type"));
				}else{
					GlobalVariables.bodingUser = new BodingUser(activatedStatus, resultJson.getString("mobile"),
							resultJson.getString("cardno"));
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return isSuccess;
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
			case SETNEWPWD_LOGIN:
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
			case VERIFY_PHONENUMBER:
				result = verifyPhoneNumber((String) params[0], (String) params[1]);
				break;
			case ACTIVIATE:
				result = activiteUser();
				break;
			case CHANGE_PASSWORD:
				result = changePassword((String) params[0], (String) params[1]);
				break;
			case VERIFY_OLD_PHONENUM_CHANGEPHONEACTIVITY:
			case VERIFY_OLD_PHONENUM_VERIFYPHOENACTIVITY:
				result = verifyOldMoblie((String) params[0]);
				break;
			case FORGETPASSWORD_GETCARDNO:
				result = getCardNoFromMobile((String) params[0]);
				break;
			case BIND_NEW_PHONENUM:
				result = phoneNumBinding((String) params[0]);
				break;
			case SET_NEW_PASSWORD:
				result = setNewPassword((String) params[0], (String) params[1]);
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
				if((Boolean)result){
					SharedPreferenceUtil.successLogin(context, password);
					loginActivity.loginSuccess();
				}
				else{
					loginActivity.loginFailed();
				}
				break;
			case SETNEWPWD_LOGIN:
				SetNewPasswordActivity setNewPasswordLoginActivity = (SetNewPasswordActivity)context;
				if((Boolean)result){
					SharedPreferenceUtil.successLogin(context, password);
				}
				setNewPasswordLoginActivity.loginResult();
				break;
			case LAUNCHER_LOGIN:
				if ((Boolean)result){
					SharedPreferenceUtil.successLogin(context, password);
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
			case VERIFY_PHONENUMBER:
				if(verifyPhoneNumType.equals("3")){
					ChangePhonenumActivity changePhonenumActivity = (ChangePhonenumActivity)context;
					changePhonenumActivity.setVerifyCode((String)result);
				}else{
					VerifyPhonenumActivity verifyPhonenumActivity = (VerifyPhonenumActivity)context;
					verifyPhonenumActivity.setVerifyCode((String)result);
				}
				break;
			case ACTIVIATE:
				VerifyPhonenumActivity verifyPhonenumActivity = (VerifyPhonenumActivity)context;
				verifyPhonenumActivity.setActiviteResult((Boolean)result);
				break;
			case CHANGE_PASSWORD:
				ChangePasswordActivity changePwdActivity = (ChangePasswordActivity)context;
				changePwdActivity.setChangePwdResult((String)result);
				break;
			case VERIFY_OLD_PHONENUM_VERIFYPHOENACTIVITY:
				VerifyPhonenumActivity verifyPhonenumVActivity = (VerifyPhonenumActivity)context;
				verifyPhonenumVActivity.verifyOldPhoneNumResult((Boolean)result);
				break;
			case FORGETPASSWORD_GETCARDNO:
				verifyPhonenumVActivity = (VerifyPhonenumActivity)context;
				verifyPhonenumVActivity.getCardNoResult((String)result);
				break;
			case VERIFY_OLD_PHONENUM_CHANGEPHONEACTIVITY:
				ChangePhonenumActivity changePhonenumActivity = (ChangePhonenumActivity)context;
				changePhonenumActivity.verifyOldPhoneNumResult((Boolean)result);
				break;
			case BIND_NEW_PHONENUM:
				ChangePhonenumActivity changePhonenumBActivity = (ChangePhonenumActivity)context;
				changePhonenumBActivity.bindNewPhoneNumResult((Boolean)result);
				break;
			case SET_NEW_PASSWORD:
				SetNewPasswordActivity setNewPasswordActivity = (SetNewPasswordActivity)context;
				setNewPasswordActivity.setNewPasswordResut((Boolean)result);
				break;
			default:
				break;
		}
	}
}
