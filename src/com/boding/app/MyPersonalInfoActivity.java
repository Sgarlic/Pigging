package com.boding.app;

import java.util.ArrayList;
import java.util.List;

import com.boding.R;
import com.boding.constants.IntentRequestCode;
import com.boding.model.Passenger;
import com.boding.util.Util;
import com.boding.view.dialog.SelectionDialog;
import com.boding.view.layout.OrderFlightInfoLayout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class MyPersonalInfoActivity extends Activity {
	private TextView userNameTextView;
	private LinearLayout uploadPortraitLinearLayout;
	private LinearLayout editInfoLinearLayout;
	private TextView nameInfoTextView;
	private TextView genderInfoTextView;
	private TextView birthdayInfoTextView;
	private LinearLayout changePasswordLinearLayout;
	private LinearLayout changePhoneNumLinearLayout;
	private LinearLayout exitLinearLayout;
	
	private String userName = "Àî´ó×ì";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mypersonalinfo);
//		Bundle arguments = getIntent().getExtras();
//        if(arguments != null)
//        	isReturnDateSelection = arguments.getBoolean(Constants.IS_RETURN_DATE_SELECTION);
        
		initView();
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
	}
}
