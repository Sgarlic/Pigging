package com.boding.app;


import com.boding.R;
import com.boding.constants.IntentRequestCode;
import com.boding.util.Util;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;

public class RegisterActivity extends Activity {
	private EditText userNameEditText;
	private EditText passwordEditText;
	private EditText passwordConfirmEditText;
	private LinearLayout loginLinearLayout;
	private LinearLayout registerLinearLayout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
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
				Util.returnToPreviousPage(RegisterActivity.this, IntentRequestCode.REGISTER);
			}
			
		});
		
		userNameEditText = (EditText) findViewById(R.id.register_input_userName_editText);
		passwordEditText = (EditText) findViewById(R.id.register_input_password_editText);
		passwordConfirmEditText = (EditText) findViewById(R.id.register_input_passwordConfirm_editText);
		loginLinearLayout = (LinearLayout) findViewById(R.id.register_login_linearLayout);
		registerLinearLayout = (LinearLayout) findViewById(R.id.register_login_linearLayout);
		
		addListeners();
	}
	
	private void addListeners(){
		loginLinearLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Util.returnToPreviousPage(RegisterActivity.this, IntentRequestCode.REGISTER);
			}
		});
	}
}
