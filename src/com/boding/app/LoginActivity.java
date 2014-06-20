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

public class LoginActivity extends Activity {
	private LinearLayout forgetPasswordLinearLayout;
	private EditText userNameEditText;
	private EditText passwordEditText;
	private LinearLayout loginLinearLayout;
	private LinearLayout registerLinearLayout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
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
				Util.returnToPreviousPage(LoginActivity.this, IntentRequestCode.START_CHOOSE_PASSENGER);
			}
			
		});
		
		forgetPasswordLinearLayout = (LinearLayout) findViewById(R.id.login_forgetpassword_linearLayout);
		userNameEditText = (EditText) findViewById(R.id.login_input_userName_editText);
		passwordEditText = (EditText) findViewById(R.id.login_input_password_editText);
		loginLinearLayout = (LinearLayout) findViewById(R.id.login_login_linearLayout);
		registerLinearLayout = (LinearLayout) findViewById(R.id.login_register_linearLayout);
		
		addListeners();
	}
	
	private void addListeners(){
		registerLinearLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(LoginActivity.this, RegisterActivity.class);
				startActivityForResult(intent, IntentRequestCode.START_REGISTER.getRequestCode());
			}
		});
	}
}
