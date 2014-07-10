package com.boding.app;


import com.boding.R;
import com.boding.constants.HTTPAction;
import com.boding.constants.IntentRequestCode;
import com.boding.task.BodingUserTask;
import com.boding.util.RegularExpressionsUtil;
import com.boding.util.Util;
import com.boding.view.dialog.ProgressBarDialog;
import com.boding.view.dialog.WarningDialog;

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
	
	private ProgressBarDialog progressBarDialog;
	private WarningDialog warningDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		progressBarDialog = new ProgressBarDialog(this);
		warningDialog = new WarningDialog(this);
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
		registerLinearLayout = (LinearLayout) findViewById(R.id.register_register_linearLayout);
		
		addListeners();
	}
	
	public void registerResult(boolean isSuccess){
		progressBarDialog.dismiss();
		if(isSuccess){
			Util.returnToPreviousPage(RegisterActivity.this, IntentRequestCode.REGISTER);
		}else{
			warningDialog.setContent("您已经是波丁会员了，请用该手机号码直接登陆");
			warningDialog.show();
		}
	}
	
	private void addListeners(){
		registerLinearLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				String userName = userNameEditText.getText().toString();
				String password = passwordEditText.getText().toString();
				if(userName.equals("") || !RegularExpressionsUtil.checkMobile(userName)){
					warningDialog.setContent("请输入正确的手机号！");
					warningDialog.show();
					return;
				}
				if(password.equals("") || !RegularExpressionsUtil.checkPassword(password)){
					warningDialog.setContent("请输入正确的密码！");
					warningDialog.show();
					return;
				}
				if(!password.equals(passwordConfirmEditText.getText().toString())){
					warningDialog.setContent("两次输入的密码不一致！");
					warningDialog.show();
					return;
				}
				progressBarDialog.show();
				(new BodingUserTask(RegisterActivity.this, HTTPAction.REGISTER)).execute(userName,password);
			}
		});
		loginLinearLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Util.returnToPreviousPage(RegisterActivity.this, IntentRequestCode.REGISTER);
			}
		});
	}
}
