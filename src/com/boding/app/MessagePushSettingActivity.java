package com.boding.app;


import com.boding.R;
import com.boding.constants.IntentRequestCode;
import com.boding.util.SharedPreferenceUtil;
import com.boding.util.Util;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MessagePushSettingActivity extends Activity {
	private LinearLayout needNotificationToneLinearLayout;
	private ImageView needNotificationToneImageView;
	private LinearLayout needNotificationVibrateLinearLayout;
	private ImageView needNotificationVibarteView;
	
	private boolean needNotificationTone = false;
	private boolean needNotificationVibrate = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_messagepush_setting);

		needNotificationTone = SharedPreferenceUtil.getBooleanSharedPreferences(this, SharedPreferenceUtil.NEED_NOTIFICATION_TONE, false);
		needNotificationVibrate = SharedPreferenceUtil.getBooleanSharedPreferences(this, SharedPreferenceUtil.NEED_NOTIFICATION_VIBRATE, false);
		
		initView();
		setViewContent();
	}
	
	private void initView(){
		LinearLayout returnLinearLayout = (LinearLayout)findViewById(R.id.return_logo_linearLayout);
		returnLinearLayout.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				Util.returnToPreviousPage(MessagePushSettingActivity.this, IntentRequestCode.MESSAGE_PUSH_SETTING);
			}
			
		});
		
		needNotificationToneLinearLayout = (LinearLayout) findViewById(R.id.messagepush_notificationTone_linearLayout);
		needNotificationToneImageView = (ImageView) findViewById(R.id.messagepush_notificationTone_imageView);
		needNotificationVibrateLinearLayout = (LinearLayout) findViewById(R.id.messagepush_notificationVibrate_linearLayout);
		needNotificationVibarteView = (ImageView) findViewById(R.id.messagepush_notificationVibrate_imageView);
		
		addListeners();
	}
	
	private void setViewContent(){
		if(needNotificationTone){
			needNotificationToneImageView.setSelected(true);
		}else{
			needNotificationToneImageView.setSelected(false);
		}
		
		if(needNotificationVibrate){
			needNotificationVibarteView.setSelected(true);
		}else{
			needNotificationVibarteView.setSelected(false);
		}
	}
	
	
	private void addListeners(){
		needNotificationToneLinearLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(needNotificationToneImageView.isSelected()){
					needNotificationToneImageView.setSelected(false);
					SharedPreferenceUtil.setBooleanSharedPreferences(MessagePushSettingActivity.this, 
							SharedPreferenceUtil.NEED_NOTIFICATION_TONE, false);
				}else{
					needNotificationToneImageView.setSelected(true);
					SharedPreferenceUtil.setBooleanSharedPreferences(MessagePushSettingActivity.this, 
							SharedPreferenceUtil.NEED_NOTIFICATION_TONE, true);
				}
			}
		});
		
		needNotificationVibrateLinearLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(needNotificationVibarteView.isSelected()){
					needNotificationVibarteView.setSelected(false);
					SharedPreferenceUtil.setBooleanSharedPreferences(MessagePushSettingActivity.this, 
							SharedPreferenceUtil.NEED_NOTIFICATION_VIBRATE, false);
				}else{
					needNotificationVibarteView.setSelected(true);
					SharedPreferenceUtil.setBooleanSharedPreferences(MessagePushSettingActivity.this, 
							SharedPreferenceUtil.NEED_NOTIFICATION_VIBRATE, true);
				}
			}
		});
	}
}
