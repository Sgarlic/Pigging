package com.boding.app;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

import com.boding.R;
import com.boding.constants.HTTPAction;
import com.boding.task.BodingUserTask;
import com.boding.task.InitCityTask;
import com.boding.task.InitCountryTask;
import com.boding.util.AreaXmlParser;
import com.boding.util.DateUtil;
import com.boding.util.SharedPreferenceUtil;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public class LauncherActivity extends Activity {
	private final int SPLASH_DISPLAY_LENGHT = 3000; //延迟三秒   

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_launcher);
		something();
		
		new Handler().postDelayed(new Runnable(){    
	        @Override   
	        public void run() {    
	            Intent mainIntent = new Intent(LauncherActivity.this,MainActivity.class);    
	            LauncherActivity.this.startActivity(mainIntent);    
	            LauncherActivity.this.finish();    
	        }    
       }, SPLASH_DISPLAY_LENGHT); 
	}

	private void initCityList(){
		String testUrl = "http://api.iboding.com/API/Base/QueryAirportCity.ashx?userid=boding&sign=14AD779B4209D8DDC95BD2336D36C015";
		
		new InitCityTask(this).execute(testUrl);
	}
	
	private void something(){
		SharedPreferences sharedPreferences = SharedPreferenceUtil.getSharedPreferences(this);  
        
	    boolean isFirstRun = sharedPreferences.getBoolean(SharedPreferenceUtil.IS_FIRSTRUN, true);  
	    if (isFirstRun)  
	    {  
	        Log.d("debug", "第一次运行");  
	        SharedPreferenceUtil.setBooleanSharedPreferences(this, SharedPreferenceUtil.IS_FIRSTRUN, false);
	    } else  
	    {  
	        Log.d("debug", "不是第一次运行");
	        boolean isAutoLogin = sharedPreferences.getBoolean(SharedPreferenceUtil.IS_AUTOLOGIN, true);
	        if(isAutoLogin){
	        	String expireDate = sharedPreferences.getString(SharedPreferenceUtil.LOGIN_EXPIREDATE,"1991-01-01");
	        	String nowDate = DateUtil.getFormatedDate(Calendar.getInstance());
	        	if(expireDate.compareTo(nowDate) > -1 ){
	        		String userName = sharedPreferences.getString(SharedPreferenceUtil.LOGIN_USERNAME,"");
	        		String password = sharedPreferences.getString(SharedPreferenceUtil.LOGIN_PASSWORD,"");
	        		BodingUserTask httpConnector = new BodingUserTask(this,HTTPAction.LAUNCHER_LOGIN);
	        		httpConnector.execute(userName,password);
	        	}
	        }
	    }  
	    initCityList();
	    new InitCountryTask(this).execute();
	    initAreaList();
	}
	
	private void initAreaList(){
		AssetManager assetManager = getAssets();
	    try {
			InputStream ims = assetManager.open("files/area.xml");
			AreaXmlParser.parse(ims);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
