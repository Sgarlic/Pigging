package com.boding.app;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.boding.R;
import com.boding.constants.Constants;
import com.boding.constants.GlobalVariables;
import com.boding.constants.HTTPAction;
import com.boding.constants.IntentRequestCode;
import com.boding.http.HttpConnector;
import com.boding.model.BodingUser;
import com.boding.model.Passenger;
import com.boding.task.DomeFlightQueryTask;
import com.boding.util.Encryption;
import com.boding.util.Util;
import com.boding.view.dialog.ProgressBarDialog;
import com.boding.view.dialog.SelectionDialog;
import com.boding.view.dialog.WarningDialog;
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
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Failed to login
 * http://api.iboding.com/API/User/UserLogin.ashx?userid=boding&username=13262763513&password=123456&sign=B260292035CF2BF08BC618468E5BA6C9
 * 
 * Success Login
 * http://api.iboding.com/API/User/UserLogin.ashx?userid=boding&username=13262763513&password=000000&sign=047AE4A01260191ED4C5F33B07DD50AB
 * @author shiyge
 *
 */

public class LoginActivity extends Activity {
	private LinearLayout forgetPasswordLinearLayout;
	private EditText userNameEditText;
	private EditText passwordEditText;
	private LinearLayout loginLinearLayout;
	private LinearLayout registerLinearLayout;
	private ProgressBarDialog progressDialog;
	
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
				Util.returnToPreviousPage(LoginActivity.this, IntentRequestCode.CHOOSE_PASSENGER);
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
				startActivityForResult(intent, IntentRequestCode.REGISTER.getRequestCode());
			}
		});
		loginLinearLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				String userName = userNameEditText.getText().toString();
				String password = passwordEditText.getText().toString();
				
				userName = "13262763513";
				password = "000000";
				
				WarningDialog warningDialog = new WarningDialog(LoginActivity.this,Constants.DIALOG_STYLE);
//				if(userName.equals("")){
//					warningDialog.setContent("请输入用户名");
//					warningDialog.show();
//					return;
//				}
//				if(password.equals("")){
//					warningDialog.setContent("请输入密码");
//					warningDialog.show();
//					return;
//				}
				
				// Start logging in
				progressDialog = new ProgressBarDialog(LoginActivity.this, Constants.DIALOG_STYLE);
				progressDialog.show();
				
				HttpConnector dfq = new HttpConnector(LoginActivity.this, HTTPAction.LOGIN);
				dfq.execute(userName,password);
			}
		});
	}
	
	public void loginSuccess(){
		progressDialog.dismiss();
		Util.returnToPreviousPage(LoginActivity.this, IntentRequestCode.LOGIN);
	}
	
	public void loginFailed(){
		progressDialog.dismiss();
		WarningDialog warningDialog = new WarningDialog(this, Constants.DIALOG_STYLE);
		warningDialog.setContent("错误的用户名/密码");
		warningDialog.show();
	}
}
