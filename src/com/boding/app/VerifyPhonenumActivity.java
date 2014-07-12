package com.boding.app;


import com.boding.R;
import com.boding.constants.IntentExtraAttribute;
import com.boding.constants.IntentRequestCode;
import com.boding.util.Util;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;

public class VerifyPhonenumActivity extends Activity {
	private EditText phonenumTextView;
	private EditText verificationNumEditText;
	private LinearLayout sendVerificationNumLinearLayout;
	private LinearLayout confirmLinearLayout;
	
	private String verifyPhonenumType;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_verfiy_phonenum);
		Bundle arguments = getIntent().getExtras();
        if(arguments != null)
        	verifyPhonenumType = arguments.getString(IntentExtraAttribute.VERIFY_PHONENUM_TYPE);
        
		initView();
	}
	
	public void setVerifyPhoneNumberResult(boolean isSuccess){
		
	}
	
	private void initView(){
		LinearLayout returnLinearLayout = (LinearLayout)findViewById(R.id.return_logo_linearLayout);
		returnLinearLayout.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				Util.returnToPreviousPage(VerifyPhonenumActivity.this, IntentRequestCode.VERIFY_PHONENUM);
			}
			
		});

		phonenumTextView = (EditText) findViewById(R.id.verifyphonenum_phoneNum_textView);
		verificationNumEditText = (EditText) findViewById(R.id.verifyphonenum_input_verificationNum_editText);
		
		sendVerificationNumLinearLayout = (LinearLayout) findViewById(R.id.verifyphonenum_sendVerificationNum_linearLayout);
		confirmLinearLayout = (LinearLayout) findViewById(R.id.verifyphonenum_confirm_linearLayout);

		addListeners();
	}
	private void addListeners(){
	}
}
