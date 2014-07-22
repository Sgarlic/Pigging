package com.boding.app;

import java.util.ArrayList;
import java.util.List;

import com.boding.R;
import com.boding.constants.GlobalVariables;
import com.boding.constants.IntentExtraAttribute;
import com.boding.constants.IntentRequestCode;
import com.boding.model.BodingUser;
import com.boding.model.Passenger;
import com.boding.util.Util;
import com.boding.view.dialog.SelectionDialog;
import com.boding.view.layout.OrderFlightInfoLayout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class MyBodingActivity extends Activity {
	private LinearLayout myAccountLinearLayout;
	private TextView loginTextView;
	private LinearLayout travelOrderListLinearLayout;
	private LinearLayout myFavoriteLinearLayout;
	private LinearLayout settingLinearLayout;
	
	private boolean hasLogin = false;
	
	private String userName = "Àî´ó×ì";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myboding);
//		Bundle arguments = getIntent().getExtras();
//        if(arguments != null)
//        	isReturnDateSelection = arguments.getBoolean(Constants.IS_RETURN_DATE_SELECTION);
		initView();
		viewContentSetting();
	}
	
	private void initView(){
		LinearLayout returnLinearLayout = (LinearLayout)findViewById(R.id.return_logo_linearLayout);
		returnLinearLayout.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				Util.returnToPreviousPage(MyBodingActivity.this, IntentRequestCode.MYBODING);
			}
			
		});
		
		myAccountLinearLayout = (LinearLayout)findViewById(R.id.myboding_myaccount_linearLayout);
		loginTextView = (TextView)findViewById(R.id.myboding_login_textView);
		travelOrderListLinearLayout = (LinearLayout)findViewById(R.id.myboding_travelOrderList_linearLayout);
		myFavoriteLinearLayout = (LinearLayout)findViewById(R.id.myboding_myFavorite_linearLayout);
		settingLinearLayout = (LinearLayout)findViewById(R.id.myboding_setting_linearLayout);
		
		addListeners();
	}
	
	private void viewContentSetting(){
		judgeLogin();
		loginTextViewSetting();
	}
	
	private void addListeners(){
		myAccountLinearLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				openMyAccount();
			}
		});
		
		travelOrderListLinearLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				if(hasLogin){
					intent.setClass(MyBodingActivity.this, OrderListActivity.class);
					startActivityForResult(intent, IntentRequestCode.ORDERS_LIST.getRequestCode());
				}else{
					intent.setClass(MyBodingActivity.this, LoginActivity.class);
					startActivityForResult(intent, IntentRequestCode.LOGIN.getRequestCode());
				}
			}
		});
		settingLinearLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(MyBodingActivity.this, SettingsActivity.class);
				startActivityForResult(intent, IntentRequestCode.SETTINGS.getRequestCode());
			}
		});
		myFavoriteLinearLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				if(GlobalVariables.bodingUser == null){
					intent.setClass(MyBodingActivity.this, LoginActivity.class);
					startActivityForResult(intent, IntentRequestCode.LOGIN.getRequestCode());
					return;
				}else if(!GlobalVariables.bodingUser.isActivated_state()){
					intent.setClass(MyBodingActivity.this, VerifyPhonenumActivity.class);
					intent.putExtra(IntentExtraAttribute.VERIFY_PHONENUM_TYPE, "2");
					startActivityForResult(intent, IntentRequestCode.LOGIN.getRequestCode());
					return;
				}else{
					intent.setClass(MyBodingActivity.this, FlightDynamicsListActivity.class);
					intent.putExtra(IntentExtraAttribute.IS_FOLLOWEDLIST, true);
					startActivityForResult(intent, IntentRequestCode.FLIGHTDYNAMICS_LIST.getRequestCode());
				}
			}
		});
	}
	
	private void openMyAccount(){
		Intent intent = new Intent();
		if(hasLogin){
			intent.setClass(MyBodingActivity.this, MyPersonalInfoActivity.class);
			startActivityForResult(intent, IntentRequestCode.MYPERSONALINFO.getRequestCode());
		}else{
			intent.setClass(MyBodingActivity.this, LoginActivity.class);
			startActivityForResult(intent, IntentRequestCode.LOGIN.getRequestCode());
		}
	}
	
	private void loginTextViewSetting(){
		if(hasLogin)
			loginTextView.setText(userName);
		else
			loginTextView.setText("µÇÂ¼");
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		viewContentSetting();
	}
	
	private void judgeLogin(){
		if(GlobalVariables.bodingUser == null)
			hasLogin = false;
		else{
			hasLogin = true;
			userName = GlobalVariables.bodingUser.getWelcomeName();
		}
	}
}
