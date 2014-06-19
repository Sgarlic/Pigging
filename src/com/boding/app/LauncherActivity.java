package com.boding.app;

import com.boding.R;
import com.boding.R.layout;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class LauncherActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_launcher);
		something();
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
	}
}
