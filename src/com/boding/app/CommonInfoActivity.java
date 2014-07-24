package com.boding.app;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.boding.R;
import com.boding.constants.GlobalVariables;
import com.boding.constants.IntentExtraAttribute;
import com.boding.constants.IntentRequestCode;
import com.boding.util.Util;

public class CommonInfoActivity extends Activity {
	private LinearLayout commonPassengersLinearLayout;
	private LinearLayout commonAddrsLinearLayout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_common_info);
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
				Util.returnToPreviousPage(CommonInfoActivity.this, IntentRequestCode.COMMON_INFO);
			}
			
		});
		
		commonPassengersLinearLayout = (LinearLayout) findViewById(R.id.commoninfo_commonPassengers_linearLayout);
		commonAddrsLinearLayout = (LinearLayout) findViewById(R.id.commoninfo_commonAddrs_linearLayout);
		addListeners();
	}
	
	private void addListeners(){
		commonPassengersLinearLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(GlobalVariables.bodingUser == null){
					openLoginActivity();
					return;
				}else if(!GlobalVariables.bodingUser.isActivated_state()){
					Intent intent = new Intent();
					intent.setClass(CommonInfoActivity.this, VerifyPhonenumActivity.class);
					intent.putExtra(IntentExtraAttribute.VERIFY_PHONENUM_TYPE, "2");
					startActivityForResult(intent, IntentRequestCode.LOGIN.getRequestCode());
					return;
				}
				Intent intent = new Intent();
				intent.setClass(CommonInfoActivity.this, CommonInfoMPassengerActivity.class);
				startActivityForResult(intent, IntentRequestCode.COMMON_INFO_M_PASSENGER.getRequestCode());
			}
		});
		commonAddrsLinearLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(GlobalVariables.bodingUser == null){
					openLoginActivity();
					return;
				}else if(!GlobalVariables.bodingUser.isActivated_state()){
					Intent intent = new Intent();
					intent.setClass(CommonInfoActivity.this, VerifyPhonenumActivity.class);
					intent.putExtra(IntentExtraAttribute.VERIFY_PHONENUM_TYPE, "2");
					startActivityForResult(intent, IntentRequestCode.LOGIN.getRequestCode());
					return;
				}
				Intent intent = new Intent();
				intent.setClass(CommonInfoActivity.this, CommonInfoMDeliverAddrActivity.class);
				startActivityForResult(intent, IntentRequestCode.COMMON_INFO_M_DELIVERADDR.getRequestCode());
			}
		});
	}
	
	private void openLoginActivity(){
		Intent intent = new Intent();
		intent.setClass(CommonInfoActivity.this, LoginActivity.class);
		startActivityForResult(intent, IntentRequestCode.LOGIN.getRequestCode());
	}
}
