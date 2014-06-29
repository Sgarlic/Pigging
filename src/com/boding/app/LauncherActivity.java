package com.boding.app;

import com.boding.R;
import com.boding.R.layout;
import com.boding.task.InitCityTask;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

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
		SharedPreferences sharedPreferences = this.getSharedPreferences("share", MODE_PRIVATE);  
        
	    boolean isFirstRun = sharedPreferences.getBoolean("isFirstRun", true);  
	    Editor editor = sharedPreferences.edit();  	      
	    if (isFirstRun)  
	    {  
	        Log.d("debug", "第一次运行");  
	        editor.putBoolean("isFirstRun", false);  
	        editor.commit();  
	    } else  
	    {  
	        Log.d("debug", "不是第一次运行");  
	    }  
	    
	    initCityList();
	}
}
