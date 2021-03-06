package com.boding.app;

import com.boding.R;
import com.boding.constants.GlobalVariables;
import com.boding.constants.HTTPAction;
import com.boding.constants.IntentRequestCode;
import com.boding.task.BodingUserTask;
import com.boding.util.SharedPreferenceUtil;
import com.boding.util.Util;
import com.boding.view.dialog.NetworkUnavaiableDialog;
import com.boding.view.dialog.ProgressBarDialog;
import com.boding.view.dialog.WarningDialog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyPersonalInfoActivity extends BodingBaseActivity {
	private TextView userNameTextView;
	private LinearLayout uploadPortraitLinearLayout;
	private LinearLayout editInfoLinearLayout;
	private TextView nameInfoTextView;
	private TextView genderInfoTextView;
	private TextView birthdayInfoTextView;
	private LinearLayout changePasswordLinearLayout;
	private LinearLayout changePhoneNumLinearLayout;
	private LinearLayout exitLinearLayout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mypersonalinfo);
		progressBarDialog = new ProgressBarDialog(this);
		networkUnavaiableDialog = new NetworkUnavaiableDialog (this);
//		Bundle arguments = getIntent().getExtras();
//        if(arguments != null)
//        	isReturnDateSelection = arguments.getBoolean(Constants.IS_RETURN_DATE_SELECTION);
        
		initView();
		getPersonalInfo();
	}
	
	public void setPersonalInfo(){
		userNameTextView.setText(GlobalVariables.bodingUser.getWelcomeName());
		nameInfoTextView.setText(GlobalVariables.bodingUser.getName());
		genderInfoTextView.setText(GlobalVariables.bodingUser.getGender());
		birthdayInfoTextView.setText(GlobalVariables.bodingUser.getBirthdayInfo());
		progressBarDialog.dismiss();
	}
	
	private void getPersonalInfo(){
		if(!Util.isNetworkAvailable(MyPersonalInfoActivity.this)){
			networkUnavaiableDialog.show();
			return;
		}
		progressBarDialog.show();
		(new BodingUserTask(this, HTTPAction.GET_PERSONAL_INFO)).execute();
	}
	
	private void initView(){
		LinearLayout returnLinearLayout = (LinearLayout)findViewById(R.id.return_logo_linearLayout);
		returnLinearLayout.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				Util.returnToPreviousPage(MyPersonalInfoActivity.this, IntentRequestCode.MYPERSONALINFO);
			}
			
		});
		
		
		userNameTextView = (TextView)findViewById(R.id.mypersonalinfo_userName_textView);
		uploadPortraitLinearLayout = (LinearLayout)findViewById(R.id.mypersonalinfo_uploadPortrait_linearLayout);
		editInfoLinearLayout = (LinearLayout)findViewById(R.id.mypersonalinfo_editInfo_linearLayout);
		nameInfoTextView = (TextView)findViewById(R.id.mypersonalinfo_nameInfo_textView);
		genderInfoTextView = (TextView)findViewById(R.id.mypersonalinfo_genderInfo_textView);
		birthdayInfoTextView = (TextView)findViewById(R.id.mypersonalinfo_birthdayInfo_textView);
		changePasswordLinearLayout = (LinearLayout)findViewById(R.id.mypersonalinfo_changePassword_linearLayout);
		changePhoneNumLinearLayout = (LinearLayout)findViewById(R.id.mypersonalinfo_changePhoneNum_linearLayout);
		exitLinearLayout = (LinearLayout)findViewById(R.id.mypersonalinfo_exit_linearLayout);
		
		setPersonalInfo();
		
		addListeners();
	}
	
	private void addListeners(){
		editInfoLinearLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(MyPersonalInfoActivity.this, EditPersonalInfoActivity.class);
				startActivityForResult(intent, IntentRequestCode.EDIT_PERSONALINFO.getRequestCode());
			}
		});
		
		changePasswordLinearLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(MyPersonalInfoActivity.this, ChangePasswordActivity.class);
				startActivityForResult(intent, IntentRequestCode.CHANGE_PASSWORD.getRequestCode());
			}
		});
		
		changePhoneNumLinearLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(MyPersonalInfoActivity.this, ChangePhonenumActivity.class);
				startActivityForResult(intent, IntentRequestCode.CHANGE_PHONENUM.getRequestCode());
			}
		});
		exitLinearLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				GlobalVariables.bodingUser = null;
				SharedPreferenceUtil.setStringSharedPreferences(MyPersonalInfoActivity.this, SharedPreferenceUtil.LOGIN_EXPIREDATE, "1991-01-01");
				SharedPreferenceUtil.setBooleanSharedPreferences(MyPersonalInfoActivity.this, SharedPreferenceUtil.IS_AUTOLOGIN, false);
				
				Util.returnToPreviousPage(MyPersonalInfoActivity.this, IntentRequestCode.MYPERSONALINFO);
			}
		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(data == null)
			return;
		if(requestCode==IntentRequestCode.EDIT_PERSONALINFO.getRequestCode()){
			setPersonalInfo();
		}
	}
}
