package com.boding.app;


import com.boding.R;
import com.boding.constants.Constants;
import com.boding.constants.GlobalVariables;
import com.boding.constants.IntentRequestCode;
import com.boding.util.Util;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SettingsActivity extends Activity {
	private LinearLayout messagePushLinearLayout;
	private LinearLayout contactUsLinearLayout;
	private LinearLayout rateLinearLayout;
	private LinearLayout customerServiceLinearLayout;
	private LinearLayout callBodingLinearLayout;
	private TextView phoneNumTextView;
	private LinearLayout cancelLinearLayout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		initView();
		
		setViewContent();
	}
	
	private void initView(){
		LinearLayout returnLinearLayout = (LinearLayout)findViewById(R.id.return_logo_linearLayout);
		returnLinearLayout.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				Util.returnToPreviousPage(SettingsActivity.this, IntentRequestCode.SETTINGS);
			}
			
		});
		
		messagePushLinearLayout = (LinearLayout)findViewById(R.id.settings_messagepush_linearLayout);
		contactUsLinearLayout = (LinearLayout)findViewById(R.id.settings_contactus_linearLayout);
		rateLinearLayout = (LinearLayout)findViewById(R.id.settings_rateapp_linearLayout);
		customerServiceLinearLayout = (LinearLayout) findViewById(R.id.settings_callCustomerService_linearLayout);
		callBodingLinearLayout = (LinearLayout) findViewById(R.id.settings_callboding_linearLayout);
		phoneNumTextView = (TextView) findViewById(R.id.settings_phonenum_textView);
		cancelLinearLayout = (LinearLayout) findViewById(R.id.settings_cancel_linearLayout);
		
		addListeners();
	}
	
	private void setViewContent(){
		customerServiceLinearLayout.setVisibility(View.INVISIBLE);
		phoneNumTextView.setText(Constants.BONDING_PHONENUM);
	}
	
	private void addListeners(){
		messagePushLinearLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(SettingsActivity.this, MessagePushSettingActivity.class);
				startActivityForResult(intent, IntentRequestCode.MESSAGE_PUSH_SETTING.getRequestCode());
			}
		});
		
		contactUsLinearLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				customerServiceLinearLayout.setVisibility(View.VISIBLE);
			}
		});
		callBodingLinearLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Util.dialPhone(SettingsActivity.this);
			}
		});
		cancelLinearLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				customerServiceLinearLayout.setVisibility(View.INVISIBLE);
			}
		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
