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
import com.boding.task.InitCountryTask;
import com.boding.task.UpdateAppTask;
import com.boding.util.AreaXmlParser;
import com.boding.util.CityUtil;
import com.boding.util.DateUtil;
import com.boding.util.SharedPreferenceUtil;
import com.boding.util.Util;
import com.boding.view.dialog.NetworkUnavaiableDialog;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public class LauncherActivity extends Activity {
	private int SPLASH_DISPLAY_LENGHT = 3000; //延迟三秒   
	public LocationClient mLocationClient;
	public MyLocationListener mMyLocationListener;
	private NetworkUnavaiableDialog networkUnavaiableDialog;
	private boolean isFirstRun;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_launcher);
		
		SharedPreferences sharedPreferences = SharedPreferenceUtil.getSharedPreferences(this);  
		isFirstRun = sharedPreferences.getBoolean(SharedPreferenceUtil.IS_FIRSTRUN, true);
		if(isFirstRun){
			SPLASH_DISPLAY_LENGHT = 6000;
		}
		
		networkUnavaiableDialog = new NetworkUnavaiableDialog (this);

		if(!Util.isNetworkAvailable(this)){
			networkUnavaiableDialog.show();
			return;
		}
		
		getLatesetVersion();
		
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
	
	private void getLatesetVersion(){
		try {
			GlobalVariables.Version_Code = getPackageManager().getPackageInfo(getPackageName(), 0).versionCode;
			GlobalVariables.Version_Name = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
			System.out.println(GlobalVariables.Version_Code);
			System.out.println(GlobalVariables.Version_Name);
			(new UpdateAppTask(this, HTTPAction.CHECK_UPDATES)).execute();
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void initLocation(){
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);//设置定位模式
		option.setCoorType("gcj02");//返回的定位结果是百度经纬度，默认值gcj02
		option.setScanSpan(5000);//设置发起定位请求的间隔时间为5000ms
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
		new InitCountryTask(this).execute();
		
	    if (isFirstRun)  
	    {  
	        Log.d("debug", "第一次运行");  
	        SharedPreferenceUtil.setBooleanSharedPreferences(this, SharedPreferenceUtil.IS_FIRSTRUN, false);
	    } else  
	    {  
	        Log.d("debug", "不是第一次运行");
	        boolean isAutoLogin = SharedPreferenceUtil.getBooleanSharedPreferences(this, SharedPreferenceUtil.IS_AUTOLOGIN, true);
	        if(isAutoLogin){
	        	String expireDate = SharedPreferenceUtil.getStringSharedPreferences(this,SharedPreferenceUtil.LOGIN_EXPIREDATE,"1991-01-01");
	        	String nowDate = DateUtil.getFormatedDate(Calendar.getInstance());
	        	if(expireDate.compareTo(nowDate) > -1 ){
	        		String userName = SharedPreferenceUtil.getStringSharedPreferences(this,SharedPreferenceUtil.LOGIN_USERNAME,"");
	        		String password = SharedPreferenceUtil.getStringSharedPreferences(this,SharedPreferenceUtil.LOGIN_PASSWORD,"");
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
	 * 实现实位回调监听
	 */
	public class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			String city = location.getCity();
			if(city == null){
				System.out.println("%%%%%%%%%%%%%%%%%%%%" + city);
				return;
			}
			mLocationClient.stop();
			while(GlobalVariables.allCitiesList.size() == 0){
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			GlobalVariables.CurrentCity = CityUtil.getCityByName(city.substring(0,city.length()-1));
		}


	}
}
