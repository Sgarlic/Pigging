package com.boding.app;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.boding.R;
import com.boding.constants.GlobalVariables;
import com.boding.constants.HTTPAction;
import com.boding.task.BodingUserTask;
import com.boding.task.InitCityTask;
import com.boding.util.AreaXmlParser;
import com.boding.util.CityUtil;
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
	private final int SPLASH_DISPLAY_LENGHT = 3000; //�ӳ�����   
	public LocationClient mLocationClient;
	public MyLocationListener mMyLocationListener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_launcher);
		
		mLocationClient = new LocationClient(this.getApplicationContext());
		mMyLocationListener = new MyLocationListener();
		mLocationClient.registerLocationListener(mMyLocationListener);
		
		
		initLocation();
		
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

	private void initLocation(){
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);//���ö�λģʽ
		option.setCoorType("gcj02");//���صĶ�λ����ǰٶȾ�γ�ȣ�Ĭ��ֵgcj02
		option.setScanSpan(5000);//���÷���λ����ļ��ʱ��Ϊ5000ms
		option.setIsNeedAddress(true);
		mLocationClient.setLocOption(option);
		mLocationClient.start();
	}
	
	private void initCityList(){
		String testUrl = "http://api.iboding.com/API/Base/QueryAirportCity.ashx?userid=boding&sign=14AD779B4209D8DDC95BD2336D36C015";
		
		new InitCityTask(this).execute(testUrl);
	}
	
	private void something(){
		initCityList();
		SharedPreferences sharedPreferences = SharedPreferenceUtil.getSharedPreferences(this);  
        
	    boolean isFirstRun = sharedPreferences.getBoolean(SharedPreferenceUtil.IS_FIRSTRUN, true);  
	    if (isFirstRun)  
	    {  
	        Log.d("debug", "��һ������");  
	        SharedPreferenceUtil.setBooleanSharedPreferences(this, SharedPreferenceUtil.IS_FIRSTRUN, false);
	    } else  
	    {  
	        Log.d("debug", "���ǵ�һ������");
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
	
	/**
	 * ʵ��ʵλ�ص�����
	 */
	public class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			mLocationClient.stop();
			String city = location.getCity();
			GlobalVariables.CurrentCity = CityUtil.getCityByName(city.substring(0,city.length()-1));
			System.out.println("%%%%%%%%%%%%%%%%%%%%" + GlobalVariables.CurrentCity );
		}


	}
}
