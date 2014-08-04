package com.boding.app;

import com.boding.R;
import com.boding.constants.GlobalVariables;
import com.boding.constants.HTTPAction;
import com.boding.constants.IntentExtraAttribute;
import com.boding.constants.IntentRequestCode;
import com.boding.task.BodingUserTask;
import com.boding.util.Util;
import com.boding.view.dialog.NetworkUnavaiableDialog;
import com.boding.view.dialog.ProgressBarDialog;
import com.boding.view.dialog.WarningDialog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;

/**
 * Failed to login
 * http://api.iboding.com/API/User/UserLogin.ashx?userid=boding&username=13262763513&password=123456&sign=B260292035CF2BF08BC618468E5BA6C9
 * 
 * Success Login
 * http://api.iboding.com/API/User/UserLogin.ashx?userid=boding&username=13262763513&password=000000&sign=047AE4A01260191ED4C5F33B07DD50AB
 * @author shiyge
 *
 */

public class LoginActivity extends BodingBaseActivity {
	private LinearLayout forgetPasswordLinearLayout;
	private EditText userNameEditText;
	private EditText passwordEditText;
	private LinearLayout loginLinearLayout;
	private LinearLayout registerLinearLayout;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
        
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
				
				if(userName.equals("")){
					warningDialog.setContent("请输入用户名");
					warningDialog.show();
					return;
				}
				if(password.equals("")){
					warningDialog.setContent("请输入密码");
					warningDialog.show();
					return;
				}
				
				if(!Util.isNetworkAvailable(LoginActivity.this)){
					networkUnavaiableDialog.show();
					return;
				}
				
				// Start logging in
				progressBarDialog.show();
				
				BodingUserTask dfq = new BodingUserTask(LoginActivity.this, HTTPAction.LOGIN);
				dfq.execute(userName,password);
			}
		});
		
		forgetPasswordLinearLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(LoginActivity.this, VerifyPhonenumActivity.class);
				intent.putExtra(IntentExtraAttribute.VERIFY_PHONENUM_TYPE, "4");
				startActivityForResult(intent, IntentRequestCode.VERIFY_PHONENUM.getRequestCode());
			}
		});
	}
	
	public void loginSuccess(){
		progressBarDialog.dismiss();
		if(GlobalVariables.bodingUser.isActivated_state())
			Util.returnToPreviousPage(LoginActivity.this, IntentRequestCode.LOGIN);
		else{
			Intent intent = new Intent();
			intent.setClass(LoginActivity.this, VerifyPhonenumActivity.class);
			intent.putExtra(IntentExtraAttribute.VERIFY_PHONENUM_TYPE, "2");
			startActivity(intent);
			this.finish();
		}
	}
	
	public void loginFailed(){
		progressBarDialog.dismiss();
		WarningDialog warningDialog = new WarningDialog(this);
		warningDialog.setContent("错误的用户名/密码");
		warningDialog.show();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		  super.onActivityResult(requestCode, resultCode, data);
		  if(GlobalVariables.bodingUser!=null && GlobalVariables.bodingUser.isActivated_state()){
			  Util.returnToPreviousPage(LoginActivity.this, IntentRequestCode.LOGIN);
		  }
	 }
}
