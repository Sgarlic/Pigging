package com.boding.app;

import com.boding.R;
import com.boding.constants.GlobalVariables;
import com.boding.constants.IntentRequestCode;
import com.boding.util.Util;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AboutBodingActivity extends Activity {
	private TextView versionTextView; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_aboutboding);
		initView();
	}
	
    private void initView(){
		LinearLayout returnLinearLayout = (LinearLayout)findViewById(R.id.return_logo_linearLayout);
		returnLinearLayout.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				Util.returnToPreviousPage(AboutBodingActivity.this, IntentRequestCode.ABOUT_BODING);
			}
			
		});
		versionTextView = (TextView) findViewById(R.id.aboutboding_version);
		versionTextView.setText(GlobalVariables.Version_Name);
		
//		Util.setChineseFont(this, (TextView) findViewById(R.id.aboutboding_aboutbodingText));
//		Util.setChineseFont(this, (TextView) findViewById(R.id.aboutboding_bodingtravel));
//		Util.setEnglishFont(this, (TextView) findViewById(R.id.aboutboding_version));
//		Util.setChineseFont(this, (TextView) findViewById(R.id.aboutboding_bodingcompany));
	}
}
